package com.knowwhere.catapult.utilities;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static com.knowwhere.catapult.constants.CommonConstants.BASE_PATH;

public class CodeUtils {
      public static String toCamelCase(String word) {
            StringBuffer camelCase = new StringBuffer(word);
            camelCase.setCharAt(0, (char)(word.charAt(0) + 32));
            return camelCase.toString();
      }

      public static String generatePath(String packageName) {
            return BASE_PATH + packageName;
      }

      public static String getPathFromPackage(String packageName) {
            return packageName.replaceAll("\\.", "/");
      }

      public static void createDirectory(String path) throws IOException {
            System.out.println("path = " + path);
            Path pathObject = Paths.get(path);
            if(!Files.exists(pathObject)) {
                  System.out.println("System.getProperty(\"user.dir\") = " + System.getProperty("user.dir"));
                  Files.createDirectories(pathObject);
            }
      }
}
