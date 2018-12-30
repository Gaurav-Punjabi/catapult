package com.knowwhere.catapult.services;

import com.google.gson.Gson;
import com.knowwhere.catapult.models.Catapult;

import java.io.*;
import java.nio.file.Files;

import static com.knowwhere.catapult.constants.CommonConstants.SOURCE_FILE_NAME;

public class CatapultService {
      private Catapult catapult;

      public CatapultService() {
            File file = new File(SOURCE_FILE_NAME);
            if(file.exists()) {
                  Gson gson = new Gson();
                  try {
                        String karmaContent = new String(Files.readAllBytes(new File(SOURCE_FILE_NAME).toPath()));
                        Catapult catapult = gson.fromJson(karmaContent, Catapult.class);
                        this.catapult = catapult;
                  } catch (IOException ioe) {
                        System.out.println("Some error occured while watching karma.");
                  }
                  return;
            }
            try {
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

                  this.catapult = new Catapult(packageName, location, port, database, username, password, apiPath);
                  this.saveState();


                  System.out.println("\n\n\nCatapult has taken over the project.");
                  System.out.println("Happy coding!!");
            } catch (IOException ioe) {
                  ioe.printStackTrace();
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
