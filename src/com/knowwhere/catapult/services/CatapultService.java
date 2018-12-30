package com.knowwhere.catapult.services;

import com.google.gson.Gson;
import com.knowwhere.catapult.models.Catapult;
import com.knowwhere.catapult.models.Model;

import java.io.*;
import java.nio.file.Files;

import static com.knowwhere.catapult.constants.CommonConstants.SOURCE_FILE_NAME;

public class CatapultService {
      private Catapult catapult;

      public CatapultService(Catapult catapult) {
            this.catapult = catapult;
      }

      public static CatapultService fetch() {
            File file = new File(SOURCE_FILE_NAME);
            if(file.exists()) {
                  Gson gson = new Gson();
                  try {
                        String karmaContent = new String(Files.readAllBytes(new File(SOURCE_FILE_NAME).toPath()));
                        Catapult catapult = gson.fromJson(karmaContent, Catapult.class);
                        return new CatapultService(catapult);
                  } catch (IOException ioe) {
                        System.out.println("Some error occured while watching karma.");
                  }
                  return null;
            }
            return null;
      }

      public static CatapultService init() {
            try {
                  File file = new File(SOURCE_FILE_NAME);
                  Files.createFile(file.toPath());
                  BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

                  System.out.print("Enter the package of the current project : ");
                  String packageName = bufferedReader.readLine();

                  System.out.print("Enter the location of the server : ");
                  String location = bufferedReader.readLine();

                  System.out.print("Enter the port number of the server : ");
                  String port = bufferedReader.readLine();

                  System.out.print("Enter the name of the database : ");
                  String database = bufferedReader.readLine();

                  System.out.print("Enter the username for the database : ");
                  String username = bufferedReader.readLine();

                  System.out.print("Enter the password for the database : ");
                  String password = bufferedReader.readLine();

                  System.out.print("Enter the base path for the api : ");
                  String apiPath = bufferedReader.readLine();

                  Catapult catapult = new Catapult(packageName, location, port, database, username, password, apiPath);

                  CatapultService catapultService = new CatapultService(catapult);
                  catapultService.saveState();

                  return catapultService;
            } catch (IOException ioe) {
                  ioe.printStackTrace();
            }
            return null;
      }

      public void generateModel(String args[]) {
            ModelGeneratorService modelGeneratorService = new ModelGeneratorService(this.catapult);
            ModelService modelService = new ModelService();
            QueryGeneratorService queryGeneratorService = new QueryGeneratorService();

            // Acquiring the properties of the model
            String modelContent[] = new String [args.length - 2];
            System.arraycopy(args, 2, modelContent, 0, args.length - 2);

            Model model= modelService.parseModel(modelContent);
            String modelCode = modelGeneratorService.generateModel(model);
            String query = queryGeneratorService.generateQueryFromModel(model);

            this.getCatapult().putQuery(model.getName(), query);
            this.saveState();

            try {
                  File file = new File("./src/main/java/" + this.getCatapult().getPackageName().replaceAll("\\.", "/") + "/models/" + model.getName() + ".java");
                  file.getParentFile().mkdirs();
                  Files.createFile(file.toPath());
                  new FileOutputStream(file).write(modelCode.getBytes());;
            } catch (IOException ioe) {
                  ioe.printStackTrace();;
            }
      }

      public void saveState() {
            try {
                  new FileOutputStream(SOURCE_FILE_NAME).write(new Gson().toJson(catapult).getBytes());
            } catch (IOException ioe) {
                  ioe.printStackTrace();
            }
      }

      public Catapult getCatapult() {
            return catapult;
      }

      public void setCatapult(Catapult catapult) {
            this.catapult = catapult;
      }
}
