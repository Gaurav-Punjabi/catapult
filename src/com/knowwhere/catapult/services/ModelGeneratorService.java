package com.knowwhere.catapult.services;

import com.knowwhere.catapult.models.Attribute;
import com.knowwhere.catapult.models.Catapult;
import com.knowwhere.catapult.models.Model;

import static com.knowwhere.catapult.constants.DatatypeConstants.NUMBER;
import static com.knowwhere.catapult.constants.DatatypeConstants.TEXT;
import static com.knowwhere.catapult.constants.DatatypeConstants.VARCHAR;

public class ModelGeneratorService {
      // Just a constant for readable code
      private static final String NEXT_LINE = "\n";
      private Catapult catapult;

      public ModelGeneratorService(Catapult catapult) {
            this.catapult = catapult;
      }

      /**
       * This method returns the code for the given model.
       * @param model The model that needs to be generated.
       * @return String containing code for the given model.
       */
      public String generateModel(Model model) {
            StringBuffer modelCode = new StringBuffer();

            // Adding the package
            modelCode.append(this.getPackageCode()).append(NEXT_LINE);

            // Adding the import statements
            modelCode.append(ModelConstants.IMPORTS).append(NEXT_LINE);

            // Adding the class it self
            modelCode.append("@Entity").append(NEXT_LINE);
            modelCode.append("@Table(name=\"" +model.getName() + "\")").append(NEXT_LINE);
            modelCode.append("public class " + model.getName() + " { ").append(NEXT_LINE);

            // Adding all the attributes
            for(Attribute attribute : model.getAttributes()) {
                  if("id".equals(attribute.getName())) {
                        modelCode.append('\t').append("@Id").append(NEXT_LINE);
                        modelCode.append('\t').append("@GeneratedValue").append(NEXT_LINE);
                        modelCode.append('\t').append("private int id;").append(NEXT_LINE);
                  }
                  else {
                        String attributeCode = "";
                        switch (attribute.getDataType()) {
                              case VARCHAR :
                                    attributeCode = this.generateAttribute(attribute.getName(), "String");
                                    break;

                              case NUMBER :
                                    attributeCode = this.generateAttribute(attribute.getName(), "int");
                                    break;

                              case TEXT :
                                    attributeCode = this.generateAttribute(attribute.getName(), "String");
                                    break;
                        }
                        modelCode.append(attributeCode);
                  }
            }

            // Adding Parameterized Constructor
            if(model.getAttributes().size() > 0) {
                  modelCode.append('\t').append("public " + model.getName() + "(");
                  // Adding all the attributes in the constructor
                  for(Attribute attribute : model.getAttributes()) {
                        switch(attribute.getDataType()) {
                              case VARCHAR :
                                    modelCode.append(" String " + attribute.getName() + ",");
                                    break;

                              case NUMBER :
                                    modelCode.append(" int " + attribute.getName() + ",");
                                    break;

                              case TEXT :
                                    modelCode.append(" String " + attribute.getName() + ",");
                                    break;
                        }
                  }
                  // Removing the extra comma at the end.
                  modelCode.deleteCharAt(modelCode.length() - 1);
                  modelCode.append(") { ").append(NEXT_LINE);
                  for(Attribute attribute : model.getAttributes()) {
                        modelCode.append('\t').append("this.")
                                 .append(attribute.getName())
                                 .append(" = ")
                                 .append(attribute.getName())
                                 .append(';')
                                 .append(NEXT_LINE);
                  }
                  modelCode.append('\t').append("}").append(NEXT_LINE);
            }

            // Adding Default Constructor for JPA
            modelCode.append('\t').append("public ")
                     .append(model.getName())
                     .append("() {}")
                     .append(NEXT_LINE);


            // Adding getters and setters
            for(Attribute attribute : model.getAttributes()) {
                  // Adding getter
                  if ("id".equals(attribute.getName())) {
                        modelCode.append('\t').append("public int getId() {").append(NEXT_LINE);
                        modelCode.append('\t').append('\t').append("return this.id;").append(NEXT_LINE);
                        modelCode.append('\t').append("}").append(NEXT_LINE);
                  } else {
                        String name = attribute.getName().replaceAll("^[a-z]]", "^[A-Z]");
                        String dataType = "";
                        switch (attribute.getDataType()) {
                              case VARCHAR:
                                    dataType = "String";
                                    break;

                              case TEXT:
                                    dataType = "String";
                                    break;

                              case NUMBER:
                                    dataType = "int";
                                    break;
                        }
                        modelCode.append('\t').append("public ")
                                .append(dataType)
                                .append(" get")
                                .append(name)
                                .append("() {")
                                .append(NEXT_LINE);
                        modelCode.append('\t').append("\treturn this.").append(attribute.getName()).append(";\n\t}");

                        modelCode.append('\t').append("public void set")
                                .append(name)
                                .append("(")
                                .append(dataType)
                                .append(' ')
                                .append(attribute.getName())
                                .append(") {")
                                .append(NEXT_LINE)
                                .append('\t')
                                .append("this.")
                                .append(attribute.getName())
                                .append(" = ")
                                .append(attribute.getName())
                                .append(';')
                                .append(NEXT_LINE)
                                .append('\t')
                                .append('}');
                  }
            }

            // Adding the last closing braces
            modelCode.append('}');

            return modelCode.toString();
      }

      private String generateAttribute(String name, String dataType) {
            StringBuffer attribute = new StringBuffer();
            attribute.append('\t').append("@Column(name=\"")
                    .append(name)
                    .append("\")")
                    .append(NEXT_LINE);
            attribute.append('\t').append("private " + dataType + " " + name + ";")
                     .append(NEXT_LINE);
            return attribute.toString();
      }

      private String getPackageCode() {
            return ModelConstants.PACAKGE + this.catapult.getPackageName() + ".models;\n";
      }


      private interface ModelConstants {
            String PACAKGE = "package ";

            String IMPORTS = "import com.fasterxml.jackson.annotation.JsonIgnore;\n" +
                    "\n" +
                    "import javax.persistence.*;\n" +
                    "import java.util.Objects;\n";
      }
}
