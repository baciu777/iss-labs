package com.example.labs;

import domain.Admin;
import domain.Agent;
import domain.User;
import domain.validator.ValidationException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import repository.RepoProduct;
import repository.interfaces.Repository;
import repository.interfaces.RepositoryAdmin;
import repository.interfaces.RepositoryAgent;
import repository.interfaces.RepositoryProduct;

import java.io.IOException;
import java.util.Objects;

public class LoginController {



        @FXML
        private AnchorPane content;
        @FXML
        private TextField textFieldId;

        User user;
        @FXML
        private TextField textFieldPassword;

        @FXML
        private Button loginButton;


        Stage dialogStage;
        RepositoryAgent repositoryAgent;
        RepositoryAdmin repositoryAdmin;
        RepoProduct repoProduct;
        Parent mainChatParent;
        public void set(RepositoryAgent repo1, RepositoryAdmin repo2, RepoProduct repo3, Stage stage)
        {
                repositoryAdmin=repo2;
                repositoryAgent=repo1;
                repoProduct=repo3;
                this.dialogStage = stage;
        }
        public void handleLogin(ActionEvent actionEvent) {
                String username = textFieldId.getText();
                String password = textFieldPassword.getText();

                try {

                        User user=new User(username,password);

                        if(Objects.equals(username, password)) {
                                Admin adm= (Admin) repositoryAdmin.findOneByUsername(username);
                                if(adm!=null && Objects.equals(adm.getPassword(), password))
                                {
                                        showMessageTaskEditDialog(adm,"admin.fxml");
                                        textFieldId.setText("");
                                        textFieldPassword.setText("");
                                }
                                else{
                                        textFieldPassword.setText("");
                                        MessageAlert.showErrorMessage(null,"credintiale incorecte");

                                }

                        }
                        else {
                                Agent agn = (Agent) repositoryAgent.findOneByUsername(username);
                                if (agn != null && Objects.equals(agn.getPassword(), password)) {
                                        showMessageTaskEditDialog(agn, "agentEmployee.fxml");
                                        textFieldId.setText("");
                                        textFieldPassword.setText("");
                                } else {
                                        textFieldPassword.setText("");
                                        MessageAlert.showErrorMessage(null, "credintiale incorecte");
                                }

                        }


                        System.out.println(user);



                }

                catch (Exception e) {
                        System.out.println(e);
                        textFieldPassword.setText("");

                }


        }
        public void showMessageTaskEditDialog(Admin user,String fxml) {
                try {
                        // create a new stage for the popup dialog.
                        FXMLLoader loader = new FXMLLoader();
                        loader.setLocation(getClass().getResource(fxml));

                        AnchorPane root = (AnchorPane) loader.load();

                        Stage dialogStage = new Stage();
                        Scene scene = new Scene(root);
                        dialogStage.setScene(scene);

                        MainController menuController = loader.getController();
                        menuController.set(user,repositoryAgent,repositoryAdmin,repoProduct,dialogStage);


                        dialogStage.show();

                } catch (IOException e) {
                        e.printStackTrace();
                }
        }
        public void showMessageTaskEditDialog(Agent user,String fxml) {
                try {
                        // create a new stage for the popup dialog.
                        FXMLLoader loader = new FXMLLoader();
                        loader.setLocation(getClass().getResource(fxml));

                        AnchorPane root = (AnchorPane) loader.load();


                        Stage dialogStage = new Stage();
                        Scene scene = new Scene(root);
                        dialogStage.setScene(scene);
                        //pentru admin agent
                        EmplController menuController = loader.getController();
                        menuController.set(user,repositoryAgent,repositoryAdmin,repoProduct,dialogStage);


                        dialogStage.show();

                } catch (IOException e) {
                        e.printStackTrace();
                }
        }


}
