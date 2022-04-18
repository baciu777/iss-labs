package com.example.labs;

import domain.Admin;
import domain.Agent;
import domain.validator.AdminValidator;
import domain.validator.AgentValidator;
import domain.validator.ProductValidation;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import repository.RepoAdmin;
import repository.RepoAgent;
import repository.RepoProduct;
import repository.interfaces.Repository;
import repository.interfaces.RepositoryAdmin;
import repository.interfaces.RepositoryAgent;
import repository.interfaces.RepositoryProduct;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {


        RepositoryAdmin repoAdmins = new RepoAdmin("jdbc:postgresql://localhost:5432/agency", "postgres", "ioana", new AdminValidator());
        repoAdmins.findAll().forEach(System.out::println);

        RepositoryAgent repoAgents = new RepoAgent("jdbc:postgresql://localhost:5432/agency", "postgres", "ioana", new AgentValidator());
        repoAgents.findAll().forEach(System.out::println);
        RepositoryProduct repoProduct = new RepoProduct("jdbc:postgresql://localhost:5432/agency", "postgres", "ioana", new ProductValidation());
        repoAgents.findAll().forEach(System.out::println);
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("login.fxml"));
        Parent root= fxmlLoader.load();
        LoginController loginController=fxmlLoader.getController();
        Scene scene = new Scene(root, 700, 640);
        loginController.set(repoAgents,repoAdmins,repoProduct,stage);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}