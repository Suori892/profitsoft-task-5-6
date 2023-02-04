package com.profitsoft;


import com.profitsoft.task1.JsonProtocolFormatter;
import com.profitsoft.task2.ReflectionUtil;
import com.profitsoft.task2.model.ClassToFill;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Path;
import java.util.List;
import java.util.Properties;
import javax.naming.OperationNotSupportedException;

/**
 * Homework lecture 5-6
 *
 * Results for task 1:
 * 1 thread - 1300 milliseconds,
 * 2 threads - 86 milliseconds
 * 4 threads - 31 milliseconds
 * 8 threads - 25 milliseconds
 */
public class App {
    public static void main(String[] args) {

        // path.properties file with paths for two tasks
        String propertiesFileName = "src/main/resources/path.properties";
        Properties properties = new Properties();

        //task 1
        try (var inputStream = new FileInputStream(propertiesFileName)) {
            properties.load(inputStream);

            List<String> pathList = List.of(
                properties.getProperty("task1.json.input.2010"),
                properties.getProperty("task1.json.input.2011"),
                properties.getProperty("task1.json.input.2012"),
                properties.getProperty("task1.json.input.2013"),
                properties.getProperty("task1.json.input.2014"),
                properties.getProperty("task1.json.input.2015"),
                properties.getProperty("task1.json.input.2016"),
                properties.getProperty("task1.json.input.2017"),
                properties.getProperty("task1.json.input.2018"),
                properties.getProperty("task1.json.input.2019"),
                properties.getProperty("task1.json.input.2020"),
                properties.getProperty("task1.json.input.2021"),
                properties.getProperty("task1.json.input.2022")
            );

            long start = System.currentTimeMillis();
            JsonProtocolFormatter.multithreadingFormatProtocol(
                pathList,
                properties.getProperty("task1.xml.output.stat")
            );
            long end = System.currentTimeMillis();

            System.out.println("milliseconds - " + (end - start));

        } catch (Exception e) {
            e.printStackTrace();
        }

        //task 2
        try {
            var classToFill = ReflectionUtil.loadFromProperties(ClassToFill.class,
                Path.of("src/main/resources/task2/values.properties"));
            System.out.println(classToFill.toString());
        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException |
                 IllegalAccessException | IOException | NoSuchFieldException |
                 OperationNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }
}
