package com.knowwhere.catapult.models;

import java.util.List;
import java.util.Objects;

public class Model {
      private String name;
      private List<Attribute> attributes;

      public Model(String name, List<Attribute> attributes) {
            this.name = name;
            this.attributes = attributes;
      }

      public String getName() {
            return name;
      }

      public void setName(String name) {
            this.name = name;
      }

      public List<Attribute> getAttributes() {
            return attributes;
      }

      public void setAttributes(List<Attribute> attributes) {
            this.attributes = attributes;
      }

      @Override
      public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Model model = (Model) o;
            return Objects.equals(name, model.name) &&
                    Objects.equals(attributes, model.attributes);
      }

      @Override
      public int hashCode() {
            return Objects.hash(name, attributes);
      }

      @Override
      public String toString() {
            return "Model{" +
                    "name='" + name + '\'' +
                    ", attributes=" + attributes +
                    '}';
      }
}
