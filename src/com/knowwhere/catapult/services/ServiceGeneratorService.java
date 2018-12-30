package com.knowwhere.catapult.services;

import com.knowwhere.catapult.utilities.CodeUtils;
import com.knowwhere.catapult.models.Catapult;

import static com.knowwhere.catapult.constants.CodeConstants.IMPORTS;
import static com.knowwhere.catapult.constants.CommonConstants.INDENT;
import static com.knowwhere.catapult.constants.CommonConstants.NEXT_LINE;

public class ServiceGeneratorService {
      private Catapult catapult;

      public ServiceGeneratorService(Catapult catapult) {
            this.catapult = catapult;
      }

      public String generateService(String modelName) {
            StringBuffer serviceCode = new StringBuffer();
            serviceCode.append("package " + catapult.getPackageName() + ".services;");

            serviceCode.append(IMPORTS).append(NEXT_LINE);
            serviceCode.append("import org.springframework.beans.factory.annotation.Autowired;\n" +
                               "import org.springframework.stereotype.Service;").append(NEXT_LINE);
            serviceCode.append("import " + catapult.getPackageName() + ".repositories.*;").append(NEXT_LINE);
            serviceCode.append("import " + catapult.getPackageName() + ".models.*;").append(NEXT_LINE).append(NEXT_LINE);

            serviceCode.append("@Service").append(NEXT_LINE);
            serviceCode.append("public " + modelName + "Service {").append(NEXT_LINE).append(NEXT_LINE);
            serviceCode.append(INDENT).append("@Autowired").append(NEXT_LINE);
            serviceCode.append(INDENT).append("private " + modelName + "Repository" + " " + CodeUtils.toCamelCase(modelName) + ";").append(NEXT_LINE).append(NEXT_LINE);

            // Generating findById method
            serviceCode.append(INDENT).append("public " + modelName + " findById(int id) {").append(NEXT_LINE);
            serviceCode.append(INDENT).append(INDENT).append("return this." + CodeUtils.toCamelCase(modelName) + "Repository.findById(id).orElse(null);").append(NEXT_LINE);
            serviceCode.append(INDENT).append('}').append(NEXT_LINE);

            // Generating getAll method
            serviceCode.append(NEXT_LINE).append(INDENT).append("public List<" + modelName + "> findAll() {").append(NEXT_LINE);
            serviceCode.append(INDENT).append(INDENT).append("return this." + CodeUtils.toCamelCase(modelName) + ".findAll();").append(NEXT_LINE);
            serviceCode.append(INDENT).append('}').append(NEXT_LINE).append(NEXT_LINE);

            serviceCode.append('}').append(NEXT_LINE);

            return serviceCode.toString();
      }

      public Catapult getCatapult() {
            return catapult;
      }
}
