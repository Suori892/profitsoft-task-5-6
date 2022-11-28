package com.profitsoft.task1;

import com.profitsoft.util.Writer;
import java.io.File;
import java.io.IOException;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XmlParser {

  private XmlParser() {

  }

  public static void newModifiedXml(String input, String output)
      throws ParserConfigurationException, IOException, SAXException {

    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
    dbf.setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, "");
    dbf.setAttribute(XMLConstants.ACCESS_EXTERNAL_SCHEMA, "");

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

    Writer.createOutputStream(doc, output);
  }
}
