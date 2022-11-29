package com.profitsoft;

import com.profitsoft.task1.XmlParser;
import java.io.File;
import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.xml.sax.SAXException;


class FirstTaskTest {

    @Test
    void testXmlAttributeConcatenation()
        throws ParserConfigurationException, IOException, SAXException {


        XmlParser.newModifiedXml("src/main/resources/task1/input.xml",
            "src/test/resources/expected/task1/testOutput.xml");

        File output = new File("src/test/resources/expected/task1/testOutput.xml");
        File expectedOutput = new File("src/test/resources/expected/task1/output.xml");

        Assertions.assertThat(expectedOutput).hasSameTextualContentAs(output);
    }

}
