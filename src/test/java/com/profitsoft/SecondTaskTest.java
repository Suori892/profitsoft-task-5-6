package com.profitsoft;

import java.io.File;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class SecondTaskTest {
  @Test
  void checkViolatingFormatting() {
    File output = new File("src/main/resources/task2/output/violationStat.xml");
    File expectedOutput = new File("src/test/resources/expected/task2/violationStat.xml");
    Assertions.assertThat(expectedOutput).hasSameTextualContentAs(output);
  }
}
