package com.knowwhere.catapult;

import com.knowwhere.catapult.constants.Commands;
import com.knowwhere.catapult.models.Catapult;
import com.knowwhere.catapult.models.Model;
import com.knowwhere.catapult.services.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Scanner;

import static com.knowwhere.catapult.constants.CommonConstants.LOGO;

public class Main {
      public static void main(String[] args) {

            if (args.length == 0) {
                  System.out.println("No arguments were passed");
                  return;
            }
            String command = args[0];
            switch (command) {
                  case Commands.INIT:
                        System.out.println(LOGO);
                        CatapultService catapultService = CatapultService.init();
                        if(catapultService == null) {
                              System.out.println("Could not initialize catapult");
                        } else {
                              System.out.println("\n\n\nCatapult has taken over the project.");
                              System.out.println("Happy coding!!");
                        }
                        break;

                  case Commands.GENERATE :
                        catapultService = CatapultService.fetch();
                        if(catapultService == null) {
                              System.out.println("This is not the root catapult directory or it has not been initialized yet.");
                              System.out.println("Either go to the root directory of or initialize this directory as catapult");
                        } else if(args.length >= 2) {
                              String component = args[1];
                              switch(component) {
                                    case Commands.MODEL_COMPONENT :
                                          if(args.length > 3) {
                                                catapultService.generateModel(args);
                                          }
                                          break;

                                    case Commands.REPOSITORY_COMPONENT :
                                          if(args.length >= 3) {
                                                String modelName = args[2];
                                                catapultService.generateRepository(modelName);
                                          }
                                          break;

                                    case Commands.SERVICE_COMPONENT :
                                          if(args.length >= 3) {
                                                String modelName = args[2];
                                                catapultService.generateService(modelName);
                                          }
                                          break;

                                    case Commands.CONTROLLER_COMPONENT :
                                          if(args.length >= 3) {
                                                String modelName = args[2];
                                                catapultService.generateRepository(modelName);
                                          }
                                          break;
                              }
                              break;
                        }
                        System.out.println("Please specify the component you want to generate.");
                        break;


            }
      }
}
