package com.profitsoft.task1;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XmlParser {

  private XmlParser() {

  }

  public static void formatXmlInputFile(String input, String output) {
    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

    //Help to prevent XXE attack
    dbf.setExpandEntityReferences(false);

    try (FileOutputStream outputFile = new FileOutputStream(output)) {


      DocumentBuilder db = dbf.newDocumentBuilder();
      Document doc = db.parse(new File(input));

      NodeList list = doc.getElementsByTagName("person");


      for (int temp = 0; temp < list.getLength(); temp++) {

        Node node = list.item(temp);

        if (node.getNodeType() == Node.ELEMENT_NODE) {
          Element element = (Element) node;

          String name = element.getAttribute("name");
          String surname = element.getAttribute("surname");
          element.setAttribute("name", name + " " + surname);
          element.removeAttribute("surname");
        }
      }
      writeXml(doc, outputFile);
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

    //Hide the xml declaration
    transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");

    transformer.transform(source, result);

  }
}
