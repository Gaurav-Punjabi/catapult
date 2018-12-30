package com.knowwhere.catapult.services;

import com.knowwhere.catapult.utilities.CodeUtils;
import com.knowwhere.catapult.models.Catapult;

import static com.knowwhere.catapult.constants.CodeConstants.IMPORTS;
import static com.knowwhere.catapult.constants.CommonConstants.INDENT;
import static com.knowwhere.catapult.constants.CommonConstants.NEXT_LINE;

public class ControllerGeneratorService {
      private Catapult catapult;

      public ControllerGeneratorService(Catapult catapult) {
            this.catapult = catapult;
      }

      public String generateController(String modelName, String endpointName) {
            StringBuffer controllerCode = new StringBuffer();

            // Package
            controllerCode.append("package " + catapult.getPackageName() + ".controllers;").append(NEXT_LINE);

            // Imports
            controllerCode.append(IMPORTS).append(NEXT_LINE);
            controllerCode.append("import org.springframework.beans.factory.annotation.Autowired;\n" +
                                "import org.springframework.http.ResponseEntity;\n" +
                                "import org.springframework.web.bind.annotation.GetMapping;\n" +
                                "import org.springframework.web.bind.annotation.PathVariable;\n" +
                                "import org.springframework.web.bind.annotation.RequestMapping;\n" +
                                "import org.springframework.web.bind.annotation.RestController;").append(NEXT_LINE);
            controllerCode.append("import " + catapult.getPackageName() + ".models.*;").append(NEXT_LINE).append(NEXT_LINE);

            controllerCode.append("@RestController").append(NEXT_LINE);
            controllerCode.append("@RequestMapping(\"").append(this.catapult.getApiPath() + endpointName + "/").append("\")").append(NEXT_LINE);
            controllerCode.append("public class " + modelName + "Controller {").append(NEXT_LINE).append(NEXT_LINE);
            controllerCode.append(INDENT).append("@Autowired").append(NEXT_LINE);
            controllerCode.append(INDENT).append("private " + modelName + "Service " + CodeUtils.toCamelCase(modelName) + "Service;").append(NEXT_LINE).append(NEXT_LINE);

            controllerCode.append(INDENT).append("@GetMapping(\"getAll\")").append(NEXT_LINE);
            controllerCode.append(INDENT).append("public ResponseEntity<?> getAll() {").append(NEXT_LINE);
            controllerCode.append(INDENT).append(INDENT).append("return ResponseEntity.ok(this." + CodeUtils.toCamelCase(modelName) + "Service.findAll());").append(NEXT_LINE);
            controllerCode.append(INDENT).append("}").append(NEXT_LINE).append(NEXT_LINE);

            controllerCode.append(INDENT).append("@GetMapping(\"{id}\")").append(NEXT_LINE);
            controllerCode.append(INDENT).append("public ResponseEntity<?> findById(@PathVariable(\"id\") int id) {").append(NEXT_LINE);
            controllerCode.append(INDENT).append(INDENT).append("return ResponseEntity.ok(this." + CodeUtils.toCamelCase(modelName) + "Service.findById(id));").append(NEXT_LINE);
            controllerCode.append(INDENT).append("}").append(NEXT_LINE).append(NEXT_LINE).append(NEXT_LINE);

            controllerCode.append("}").append(NEXT_LINE);

            return controllerCode.toString();
      }
}
