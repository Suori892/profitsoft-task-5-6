package com.profitsoft.task2.model;

import com.profitsoft.task2.annotation.Property;
import java.time.Instant;

public class ClassToFill {

    private String stringProperty;

    @Property(name="numberProperty")
    private int myNumber;

    @Property(format="dd.MM.yyyy HH:mm")
    private Instant timeProperty;

    public String getStringProperty() {
      return stringProperty;
    }

    public void setStringProperty(String stringProperty) {
      this.stringProperty = stringProperty;
    }

    public int getMyNumber() {
      return myNumber;
    }

    public void setMyNumber(int myNumber) {
      this.myNumber = myNumber;
    }

    public Instant getTimeProperty() {
      return timeProperty;
    }

    public void setTimeProperty(Instant timeProperty) {
      this.timeProperty = timeProperty;
    }

    @Override
    public String toString() {
      return "ClassToFill{" +
          "stringProperty='" + stringProperty + '\'' +
          ", myNumber=" + myNumber +
          ", timeProperty=" + timeProperty +
          '}';
    }
}
