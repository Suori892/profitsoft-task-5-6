package com.profitsoft.task2;


import com.profitsoft.util.Writer;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;


public class JsonProtocolFormatter {
  private JsonProtocolFormatter() {

  }

  public static void formatProtocol(List<String> pathsToFiles, String output)
      throws IOException, ParseException, ParserConfigurationException {

    JSONParser parser = new JSONParser();

    ViolationSnapshot snap = new ViolationSnapshot();

    for (String path : pathsToFiles) {
      JSONArray arr = (JSONArray) parser.parse(new FileReader(path));

      snap.fillMap(arr);
    }

    snap.sortByValue();
    formatXmlStructure(snap, output);
  }


  private static void formatXmlStructure(ViolationSnapshot snapshot, String output)
      throws ParserConfigurationException {

    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
    dbf.setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, "");
    dbf.setAttribute(XMLConstants.ACCESS_EXTERNAL_SCHEMA, "");

    DocumentBuilder db = dbf.newDocumentBuilder();

    Document doc = db.newDocument();

    //Iterator
    Iterator<String> it = snapshot.iterator();

    // root elements
    Element rootElement = doc.createElement("totalStat");
    doc.appendChild(rootElement);

    while (it.hasNext()) {
      String key = it.next();

      Element item = doc.createElement("item");
      rootElement.appendChild(item);

      Element type = doc.createElement("type");
      item.appendChild(type);
      type.setTextContent(key);

      Element amount = doc.createElement("amount");
      item.appendChild(amount);
      amount.setTextContent(String.valueOf(snapshot.getValue(key)));
    }

    Writer.createOutputStream(doc, output);
  }

}
