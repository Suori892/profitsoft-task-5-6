package com.profitsoft.task1;


import com.profitsoft.task1.util.Writer;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.w3c.dom.Element;


public class JsonProtocolFormatter {

    private static final ExecutorService service = Executors.newFixedThreadPool(8);

    private JsonProtocolFormatter() {}

    private static final JSONParser parser = new JSONParser();
    private static final ViolationSnapshot snap = new ViolationSnapshot();

    public static void multithreadingFormatProtocol(List<String> pathsToFiles, String output) {
        CompletableFuture.runAsync(() -> {
            pathsToFiles.stream()
                .forEach(path -> {
                    try {
                        var arr = (JSONArray) parser.parse(new FileReader(path));

                        snap.fillMap(arr);
                        snap.sortByValue();
                        formatXmlStructure(snap, output);
                    } catch (IOException | ParseException | ParserConfigurationException e) {
                        throw new RuntimeException(e);
                    }
                });
        }, service);
        service.shutdown();
    }

    public static void formatProtocol(List<String> pathsToFiles, String output)
        throws IOException, ParseException, ParserConfigurationException {
        for (String path : pathsToFiles) {
            var arr = (JSONArray) parser.parse(new FileReader(path));

            snap.fillMap(arr);
        }

        snap.sortByValue();
        formatXmlStructure(snap, output);
    }


    private static void formatXmlStructure(ViolationSnapshot snapshot, String output)
        throws ParserConfigurationException {

        var dbf = DocumentBuilderFactory.newInstance();
        dbf.setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, "");
        dbf.setAttribute(XMLConstants.ACCESS_EXTERNAL_SCHEMA, "");

        var db = dbf.newDocumentBuilder();

        var doc = db.newDocument();

        //Iterator
        Iterator<String> it = snapshot.iterator();

        // root elements
        Element rootElement = doc.createElement("totalStat");
        doc.appendChild(rootElement);

        while (it.hasNext()) {
            var key = it.next();

            var item = doc.createElement("item");
            rootElement.appendChild(item);

            var type = doc.createElement("type");
            item.appendChild(type);
            type.setTextContent(key);

            var amount = doc.createElement("amount");
            item.appendChild(amount);
            amount.setTextContent(String.valueOf(snapshot.getValue(key)));
        }

        Writer.createOutputStream(doc, output);
    }

}
