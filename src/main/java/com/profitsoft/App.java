package com.profitsoft;


import com.profitsoft.task1.XmlParser;
import com.profitsoft.task2.JsonProtocolFormatter;
import java.io.FileInputStream;
import java.util.List;
import java.util.Properties;

/**
 * Homework 2
 */
public class App {
    public static void main(String[] args) {

        // path.properties file with paths for two tasks
        String propertiesFileName = "src/main/resources/path.properties";
        Properties properties = new Properties();

        try (var inputStream = new FileInputStream(propertiesFileName)) {
            properties.load(inputStream);

            //Task 1
            XmlParser.newModifiedXml(properties.getProperty("task1.xml.input"),
                properties.getProperty("task1.xml.output"));

            //Task 2
            List<String> pathList = List.of(properties.getProperty("task2.json.input.2020"),
                properties.getProperty("task2.json.input.2021"),
                properties.getProperty("task2.json.input.2022"));

            JsonProtocolFormatter.formatProtocol(pathList,
                properties.getProperty("task2.xml.output.stat"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
