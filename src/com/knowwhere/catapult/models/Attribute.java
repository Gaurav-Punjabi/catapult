package com.knowwhere.catapult.models;

public class Attribute {
      private String name, dataType, size;

      public Attribute(String name, String dataType, String size) {
            this.name = name;
            this.dataType = dataType;
            this.size = size;
      }

      public String getName() {
            return name;
      }

      public void setName(String name) {
            this.name = name;
      }

      public String getDataType() {
            return dataType;
      }

      public void setDataType(String dataType) {
            this.dataType = dataType;
      }

      public String getSize() {
            return size;
      }

      public void setSize(String size) {
            this.size = size;
      }

      @Override
      public String toString() {
            return "Attribute{" +
                    "name='" + name + '\'' +
                    ", dataType='" + dataType + '\'' +
                    ", size='" + size + '\'' +
                    '}';
      }
}
