package com.knowwhere.catapult.models;

import com.google.gson.annotations.SerializedName;

import java.util.HashMap;
import java.util.Map;

public class Catapult {
      @SerializedName("packageName")
      private String packageName;

      @SerializedName("server")
      private String server;

      @SerializedName("port")
      private String port;

      @SerializedName("databaseName")
      private String databaseName;

      @SerializedName("username")
      private String username;

      @SerializedName("password")
      private String password;

      @SerializedName("apiPath")
      private String apiPath;

      @SerializedName("queries")
      private Map<String, String> queries;

      public Catapult(String packageName, String server, String port, String databaseName, String username, String password, String apiPath) {
            this.packageName = packageName;
            this.server = server;
            this.port = port;
            this.databaseName = databaseName;
            this.username = username;
            this.password = password;
            this.apiPath = apiPath;
            this.queries = new HashMap<>();
      }

      public String getPackageName() {
            return packageName;
      }

      public void setPackageName(String packageName) {
            this.packageName = packageName;
      }

      public String getServer() {
            return server;
      }

      public void setServer(String server) {
            this.server = server;
      }

      public String getPort() {
            return port;
      }

      public void setPort(String port) {
            this.port = port;
      }

      public String getDatabaseName() {
            return databaseName;
      }

      public void setDatabaseName(String databaseName) {
            this.databaseName = databaseName;
      }

      public String getUsername() {
            return username;
      }

      public void setUsername(String username) {
            this.username = username;
      }

      public String getPassword() {
            return password;
      }

      public void setPassword(String password) {
            this.password = password;
      }

      public String getApiPath() {
            return apiPath;
      }

      public void setApiPath(String apiPath) {
            this.apiPath = apiPath;
      }

      public void putQuery(String model, String query) {
            this.queries.put(model, query);
      }

      public String getQuery(String model) {
            return this.queries.get(model);
      }
}
