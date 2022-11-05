package IA;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javax.swing.*;

public class Main extends Application {

    public User currentUser;

    public User getCurrentUser(){
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }



    @Override
    public void start(Stage primaryStage) throws Exception{ //generates the gui

        // <ALERT TO INFORM USER ABOUT THE PROGRAM>
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Notice");
        alert.setHeaderText("Please take note:");
        alert.setContentText("Before booking a room, make sure you have registered");
        alert.showAndWait();
        // <--------------------------------------->

        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("main.fxml")); //links .java file to .fxml file
        primaryStage.setTitle("Management booking system");
        primaryStage.resizableProperty().setValue(false);
        primaryStage.setScene(new Scene(root, 600, 489)); //sets dimensions of the window

        primaryStage.show(); //SHOWS STAGE

    }



    public static void main(String[] args) { //start of main
       // Connector.printUsers();
        launch(args); //RUNS THE FUNCTION TO START THE PROGRAM

    }//end of main
}
