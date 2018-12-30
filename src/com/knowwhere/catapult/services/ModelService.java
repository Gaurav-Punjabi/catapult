package com.knowwhere.catapult.services;

import com.knowwhere.catapult.models.Attribute;
import com.knowwhere.catapult.models.Model;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ModelService {
      private String ATTRIBTUE_REGEX = "(\\w+)=([\\w\\:\\d]+)";
      private String DATATYPE_REGEX = "(\\w+)(:(\\d+))?";

      public Model parseModel(String arguments[]) {
            Model model = null;
            List<Attribute> attributeList = new ArrayList<>();

            String modelName = arguments[0];
            System.out.println("modelName = " + modelName);

            Pattern attributePattern = Pattern.compile(ATTRIBTUE_REGEX);

            for(int i=1;i<arguments.length;i++) {
                  Matcher matcher = attributePattern.matcher(arguments[i]);
                  if(matcher.matches()) {
                        String attributeName = matcher.group(1);

                        Pattern datatypePattern = Pattern.compile(DATATYPE_REGEX);
                        Matcher datatypeMatcher = datatypePattern.matcher(matcher.group(2));
                        if(datatypeMatcher.matches()) {
                              String dataType = datatypeMatcher.group(1);
                              String size = datatypeMatcher.group(3) == null ? "" : datatypeMatcher.group(3);
                              attributeList.add(new Attribute(attributeName, dataType, size));
                        }
                  }
            }

            model = new Model(modelName, attributeList);

            return model;
      }

      public String generateModel(Model model) {
            StringBuilder modelFile = new StringBuilder();
            modelFile.append("public class " + model.getName() + " { \n\t");
            for(Attribute attribute : model.getAttributes()) {
                  if(attribute.getName().equals("id")) {
                        modelFile.append("@Id\n\t@GeneratedValue\n\t@Column(name=\"id\")\n\tprivate int id;\n");
                  }
                  modelFile.append("\n\t@Column(name=\"" + attribute.getName() + "\")");
                  switch(attribute.getDataType()) {
                        case "varchar" :
                              modelFile.append("\n\tprivate String " + attribute.getName() + ";\n");
                              break;

                        case "tinyint" :
                              modelFile.append("\n\tprivate boolean " + attribute.getName() + ";\n");
                              break;

                        case "integer" :
                              modelFile.append("\n\tprivate int " + attribute.getName() + ";\n");
                              break;
                  }
            }
            modelFile.append("\n}");
            return modelFile.toString();
      }
}
