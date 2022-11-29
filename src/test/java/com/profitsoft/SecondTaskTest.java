package com.profitsoft;

import com.profitsoft.task2.JsonProtocolFormatter;
import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.xml.parsers.ParserConfigurationException;
import org.assertj.core.api.Assertions;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Test;

class SecondTaskTest {
    @Test
    void checkViolatingFormatting()
        throws IOException, ParseException, ParserConfigurationException {


        List<String> pathList = List.of("src/main/resources/task2/input/2020.json",
            "src/main/resources/task2/input/2021.json",
            "src/main/resources/task2/input/2022.json");

        JsonProtocolFormatter.formatProtocol(pathList,
            "src/test/resources/expected/task2/testViolationStat.xml");

        File output = new File("src/test/resources/expected/task2/testViolationStat.xml");
        File expectedOutput = new File("src/test/resources/expected/task2/violationStat.xml");
        Assertions.assertThat(expectedOutput).hasSameTextualContentAs(output);
    }
}
