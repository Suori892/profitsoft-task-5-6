package com.profitsoft;

import java.io.File;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;


class FirstTaskTest {

  @Test
  void testXmlAttributeConcatenation() {
    File output = new File("src/main/resources/task1/output.xml");
    File expectedOutput = new File("src/test/resources/expected/task1/output.xml");
    Assertions.assertThat(expectedOutput).hasSameTextualContentAs(output);
  }
}
