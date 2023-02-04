package com.profitsoft;

import static junit.framework.Assert.assertEquals;

import com.profitsoft.task2.ReflectionUtil;
import com.profitsoft.task2.model.ClassToFill;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Path;
import java.time.Instant;
import javax.naming.OperationNotSupportedException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ReflectionUtilTest {
    ClassToFill classToFill;

    @Test
    @DisplayName("should return class with fields " +
        "filled by values from property file")
    void loadFromProperties() {
        try {
            classToFill = ReflectionUtil.loadFromProperties(ClassToFill.class,
                Path.of("src/main/resources/task2/values.properties"));
            System.out.println(classToFill.toString());
        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException |
                 IllegalAccessException | IOException | NoSuchFieldException |
                 OperationNotSupportedException e) {
            throw new RuntimeException(e);
        }

        assertEquals(classToFill.getStringProperty(), "value1");
        assertEquals(classToFill.getMyNumber(), 10);
        assertEquals(classToFill.getTimeProperty(), Instant.parse("2022-11-29T18:30:00Z"));
    }
}
