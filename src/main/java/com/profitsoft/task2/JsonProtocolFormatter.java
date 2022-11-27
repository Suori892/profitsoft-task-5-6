package com.profitsoft.task2;


import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;


public class JsonProtocolFormatter {

  private static Map<String, Double> map = new LinkedHashMap<>();

  private JsonProtocolFormatter() {

  }

  public static void formatProtocol(List<String> pathsToFiles)
      throws IOException, ParseException, ParserConfigurationException {
    JSONParser parser = new JSONParser();
    for (String path : pathsToFiles) {
      JSONArray arr = (JSONArray) parser.parse(new FileReader(path));

      fillMap(arr);
    }

    map = sortByValue(map);
    formatXml();
  }

  private static void fillMap(JSONArray arr) {
    for (Object obj : arr) {
      JSONObject violation = (JSONObject) obj;
      String type = (String) violation.get("type");
      Double amount = (Double) violation.get("fine_amount");

      if (map.containsKey(type)) {
        map.put(type, map.get(type) + amount);
      } else {
        map.put(type, amount);
      }

    }
  }

  public static void formatXml() throws ParserConfigurationException {
    DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
    DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

    // root elements
    Document doc = docBuilder.newDocument();
    Element rootElement = doc.createElement("totalStat");
    doc.appendChild(rootElement);

    for (Map.Entry<String, Double> entry : map.entrySet()) {
      Element item = doc.createElement("item");
      rootElement.appendChild(item);

      Element type = doc.createElement("type");
      item.appendChild(type);
      type.setTextContent(entry.getKey());

      Element amount = doc.createElement("amount");
      item.appendChild(amount);
      amount.setTextContent(String.valueOf(entry.getValue()));
    }

    try (FileOutputStream output = new FileOutputStream(
        "src/main/resources/task2/output/violationStat.xml")) {
      writeXml(doc, output);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }


  private static void writeXml(Document doc, OutputStream output) throws TransformerException {

    TransformerFactory transformerFactory = TransformerFactory.newInstance();

    //Help to prevent XXE attack
    transformerFactory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
    transformerFactory.setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, "");
    transformerFactory.setAttribute(XMLConstants.ACCESS_EXTERNAL_STYLESHEET, "");

    Transformer transformer = transformerFactory.newTransformer();

    DOMSource source = new DOMSource(doc);
    StreamResult result = new StreamResult(output);


    //Stylization of xml file
    transformer.setOutputProperty(OutputKeys.INDENT, "yes");
    transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");

    transformer.transform(source, result);
  }

  private static Map<String, Double> sortByValue(Map<String, Double> unsortedMap) {
    Map<String, Double> sortedMap = new LinkedHashMap<>();
    unsortedMap.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
        .forEachOrdered(x -> sortedMap.put(x.getKey(), x.getValue()));
    return sortedMap;
  }
}
