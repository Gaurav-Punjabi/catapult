package com.knowwhere.catapult;

import com.knowwhere.catapult.constants.Commands;
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
            CatapultService catapultService = null;
            ModelService modelService = new ModelService();
            ModelGeneratorService modelGeneratorService = null;
            ServiceGeneratorService serviceGeneratorService = null;
            RepositoryGeneratorService repositoryGeneratorService = null;
            ControllerGeneratorService controllerGeneratorService = null;
            QueryGeneratorService queryGeneratorService = new QueryGeneratorService();

            if (args.length == 0) {
                  System.out.println("No arguments were passed");
                  return;
            }
            String command = args[0];
            switch (command) {
                  case Commands.INIT:
                        System.out.println(LOGO);
                        catapultService = new CatapultService();
                        break;

                  case Commands.GENERATE :
                        if(args.length >= 2) {
                              String component = args[1];
                              switch(component) {
                                    case Commands.MODEL_COMPONENT :
                                          if(args.length > 3) {
                                                catapultService = new CatapultService();
                                                modelGeneratorService = new ModelGeneratorService(catapultService.getCatapult());

                                                // Acquiring the properties of the model
                                                String modelContent[] = new String [args.length - 2];
                                                System.arraycopy(args, 2, modelContent, 0, args.length - 2);

                                                Model model= modelService.parseModel(modelContent);
                                                String pojo = modelGeneratorService.generateModel(model);
                                                String query = queryGeneratorService.generateQueryFromModel(model);

                                                catapultService.getCatapult().putQuery(model.getName(), query);
                                                catapultService.saveState();

                                                try {
                                                      File file = new File("./src/main/java/" + catapultService.getCatapult().getPackageName().replaceAll("\\.", "/") + "/models/" + model.getName() + ".java");
                                                      file.getParentFile().mkdirs();
                                                      Files.createFile(file.toPath());
                                                      new FileOutputStream(file).write(pojo.getBytes());;
                                                } catch (IOException ioe) {
                                                      ioe.printStackTrace();;
                                                }
                                          }

                                          break;

                                    case Commands.REPOSITORY_COMPONENT :
                                          if(args.length >= 3)  {
                                                System.out.println("Generating Repository");
                                                catapultService = new CatapultService();
                                                repositoryGeneratorService = new RepositoryGeneratorService(catapultService.getCatapult());

                                                String modelName = args[2];
                                                String repositoryCode = repositoryGeneratorService.generateRepository(modelName);
                                                try {
                                                      File file = new File("src/main/java/" + catapultService.getCatapult().getPackageName().replaceAll("\\.", "/") + "/repositories/" + modelName + "Repository.java");
                                                      new FileOutputStream(file).write(repositoryCode.getBytes());
                                                      System.out.println(modelName + "Repository has been created");
                                                } catch (IOException ioe) {
                                                      ioe.printStackTrace();;
                                                }
                                          }
                                          break;

                                    case Commands.SERVICE_COMPONENT :
                                          if(args.length >= 3) {
                                                catapultService = new CatapultService();
                                                serviceGeneratorService = new ServiceGeneratorService(catapultService.getCatapult());

                                                String modelName = args[2];
                                                String serviceCode = serviceGeneratorService.generateService(modelName);
                                                try {
                                                      File file = new File("src/main/java/" + catapultService.getCatapult().getPackageName().replaceAll("\\.", "/") + "/services/" + modelName + "Service.java");
                                                      new FileOutputStream(file).write(serviceCode.getBytes());
                                                      System.out.println(modelName + "Service has been generated");
                                                } catch (IOException ioe) {
                                                      ioe.printStackTrace();
                                                }
                                          }
                                          break;

                                    case Commands.CONTROLLER_COMPONENT :
                                          if(args.length >= 3) {
                                                catapultService = new CatapultService();
                                                controllerGeneratorService = new ControllerGeneratorService(catapultService.getCatapult());

                                                String modelName = args[2];
                                                System.out.print("Enter the name of the endpoint : ");
                                                String endpoint = new Scanner(System.in).nextLine(); System.out.println("");
                                                String controllerCode  = controllerGeneratorService.generateController(modelName, endpoint);
                                                try {
                                                      File file = new File("src/main/java/" + catapultService.getCatapult().getPackageName().replaceAll("\\.", "/") + "/controllers/" + modelName + "Controller.java");
                                                      new FileOutputStream(file).write(controllerCode.getBytes());
                                                      System.out.println(modelName + "Contoller has been generated");
                                                } catch (IOException ioe) {
                                                      ioe.printStackTrace();
                                                }
                                          }
                              }
                              break;
                        }
                        System.out.println("Please specify the component you want to generate.");
                        break;


            }
      }
}
