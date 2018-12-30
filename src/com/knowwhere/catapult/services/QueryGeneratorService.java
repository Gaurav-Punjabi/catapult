package com.knowwhere.catapult.services;

import com.knowwhere.catapult.models.Model;

public class QueryGeneratorService {
      public String generateQueryFromModel(Model model) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("create table " + model.getName() + " ( ");
            for(int i=0;i<model.getAttributes().size();i++) {
                  if("id".equals(model.getAttributes().get(i).getName()))
                        stringBuilder.append("id integer primary key auto_increment, ");
                  else
                        stringBuilder.append(model.getAttributes().get(i).getName() + " " + model.getAttributes().get(i).getDataType() + (model.getAttributes().get(i).getSize() == "" ? "" : "(" + model.getAttributes().get(i).getSize() + ") "));
            }
            return stringBuilder.append(" );").toString();
      }
}
