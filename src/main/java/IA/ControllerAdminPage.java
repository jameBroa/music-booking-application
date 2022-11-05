package IA;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javafx.util.Pair;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class ControllerAdminPage  {

    private User currentUser;

    @FXML Button buttonSidePanelUser, buttonNextImage, buttonPrevImage, buttonBack, submitIPChange;
    @FXML ChoiceBox choiceBoxTutorGroups, choiceBoxYearGroupsEdit;
    @FXML TextField TFYear7Add, TFYear8Add, TFYear9Add, TFYear10Add, TFYear11Add, TFYear12Add, TFYear13Add, TFEditedTutorGroup, TFUsername, TFPassword, TFIP;
    @FXML AnchorPane editClassesAnchorPane;
    @FXML ImageView imageView;
    @FXML Label roomDescription, roomLocation, labelTitle, labelHostChange;

    private Boolean justConfigured = false;

    ArrayList<Image> roomImages = new ArrayList<>();
    ArrayList<String> roomDescriptions = new ArrayList<>();
    ArrayList<String> roomLocations = new ArrayList<>();
    int currentImagePointer;

    public void setLoggedInUser(User user) {
        currentUser = user;
        System.out.println("this is the current user: " + currentUser.getFirstName());
    }

    public void returnToMain(ActionEvent event) throws Exception{ //goes back to the main program
        Parent root = FXMLLoader.load(getClass().getResource("main.fxml")); //links .java file to .fxml file
        Stage primaryStage = new Stage();
        primaryStage.setTitle("Management booking system");
        primaryStage.resizableProperty().setValue(false);
        primaryStage.setScene(new Scene(root, 600, 489)); //sets dimensions of the window
        primaryStage.show();
        ((Node)(event.getSource())).getScene().getWindow().hide();
        System.out.println("you returned back to main");
    }

    public void goEditUsers(ActionEvent event) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("AdminEditUsers.fxml"));
        Parent root = loader.load();

        // ----

//        System.out.println("This is what should've been sent: " + currentUser.getFirstName());
//        ControllerAdminPage controller = loader.getController();
//        controller.setLoggedInUser(currentUser);

        Stage primaryStage = new Stage();
        primaryStage.setTitle("Management booking system");
        primaryStage.resizableProperty().setValue(false);
        primaryStage.setScene(new Scene(root, 450, 450)); //sets dimensions of the window
        primaryStage.show();
        ((Node)(event.getSource())).getScene().getWindow().hide();
        System.out.println("you went to add booking ");
    }

    public void addUserInputForm(ActionEvent event) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("addUserInputForm.fxml")); //links .java file to .fxml file
        Stage primaryStage = new Stage();
        primaryStage.setTitle("Management booking system");
        primaryStage.resizableProperty().setValue(false);
        primaryStage.setScene(new Scene(root, 450, 450)); //sets dimensions of the window
        primaryStage.show();
        ((Node)(event.getSource())).getScene().getWindow().hide();
        System.out.println("you went to add users ");






    }

    public void addRoomInputForm(ActionEvent event) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("addRoomInputForm.fxml")); //links .java file to .fxml file
        Stage primaryStage = new Stage();
        primaryStage.setTitle("Management booking system");
        primaryStage.resizableProperty().setValue(false);
        primaryStage.setScene(new Scene(root, 450, 450)); //sets dimensions of the window
        primaryStage.show();
        ((Node)(event.getSource())).getScene().getWindow().hide();
        System.out.println("you went to add room ");
    }

    public void goViewUserInfo(ActionEvent event) throws Exception {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("viewUserInfo.fxml"));
        Parent root = loader.load();

        Stage primaryStage = new Stage();
        primaryStage.setTitle("Management booking system");
        primaryStage.resizableProperty().setValue(false);
        primaryStage.setScene(new Scene(root, 600, 450)); //sets dimensions of the window
        primaryStage.show();
        ((Node)(event.getSource())).getScene().getWindow().hide();
        System.out.println("you went to add booking ");


    }
    public void returnMainPage(ActionEvent event) throws IOException {

        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("main.fxml"));
            Parent root = loader.load();
            ControllerMain controller = loader.getController();
            User user = new User();
            controller.TFEnterID.setText(Integer.toString(user.getLoggedInUserID()));
            controller.enterProgram(event);
            //controller.goAdmin(event);

            Stage primaryStage = new Stage();
            primaryStage.setTitle("Management booking system");
            primaryStage.resizableProperty().setValue(false);
            primaryStage.setScene(new Scene(root, 600, 489)); //sets dimensions of the window
            primaryStage.show();
            ((Node) (event.getSource())).getScene().getWindow().hide();
            System.out.println("you went to add booking ");
        } catch (IOException ex) {
            goHome(event);
        } catch (Exception e) {
            goHome(event);
        }
    }



    public void returnHomeAdmin(ActionEvent event) throws IOException {

        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("main.fxml"));
            Parent root = loader.load();
            ControllerMain controller = loader.getController();
            User user = new User();
            controller.TFEnterID.setText(Integer.toString(user.getLoggedInUserID()));
            controller.enterProgramOutsideMain(event);
            //controller.goAdmin(event);

            Stage primaryStage = new Stage();
            primaryStage.setTitle("Management booking system");
            primaryStage.resizableProperty().setValue(false);
            primaryStage.setScene(new Scene(root, 600, 489)); //sets dimensions of the window
            primaryStage.show();
            ((Node) (event.getSource())).getScene().getWindow().hide();
            System.out.println("you went to add booking ");
        } catch (IOException ex) {
            goHome(event);
        } catch (Exception e) {
            goHome(event);
        }
    }


    public void returnToAdminPage(ActionEvent event) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("adminPage.fxml")); //links .java file to .fxml file
        Stage primaryStage = new Stage();
        primaryStage.setTitle("Management booking system");
        primaryStage.resizableProperty().setValue(false);
        primaryStage.setScene(new Scene(root, 450, 450)); //sets dimensions of the window
        primaryStage.show();
        ((Node)(event.getSource())).getScene().getWindow().hide();
        System.out.println("you returned back to main");

    }

    public void goViewRoomInfo(ActionEvent event) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("viewRoomInfo.fxml")); //links .java file to .fxml file
        Stage primaryStage = new Stage();
        primaryStage.setTitle("Management booking system");
        primaryStage.resizableProperty().setValue(false);
        primaryStage.setScene(new Scene(root, 600, 450)); //sets dimensions of the window
        primaryStage.show();
        ((Node)(event.getSource())).getScene().getWindow().hide();
        System.out.println("you went to edit rooms ");
    }

    public void goViewBookingInfo(ActionEvent event) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("viewBookingInfo.fxml")); //links .java file to .fxml file
        Stage primaryStage = new Stage();
        primaryStage.setTitle("Management booking system");
        primaryStage.resizableProperty().setValue(false);
        primaryStage.setScene(new Scene(root, 600, 450)); //sets dimensions of the window
        primaryStage.show();
        ((Node)(event.getSource())).getScene().getWindow().hide();
        System.out.println("you went to edit rooms ");
    }

    public void goEditExistingClasses(ActionEvent event) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("editUserClasses.fxml"));
        Parent root = loader.load();
        ControllerAdminPage controller = loader.getController();
        ControllerInputForms temp2 = new ControllerInputForms();
        controller.choiceBoxTutorGroups.setItems(FXCollections.observableList(temp2.getYear7()));
        controller.choiceBoxTutorGroups.getSelectionModel().selectFirst();

        Stage primaryStage = new Stage();
        primaryStage.setTitle("Management booking system");
        primaryStage.resizableProperty().setValue(false);
        primaryStage.setScene(new Scene(root, 600, 323)); //sets dimensions of the window
        primaryStage.show();
        ((Node)(event.getSource())).getScene().getWindow().hide();
        System.out.println("you went to add booking ");
    }

    public int getNumYear(String yearGroup) {

        if(yearGroup.length() == 6) {
            return Integer.parseInt(yearGroup.substring(5,6));
        } else {
            return Integer.parseInt(yearGroup.substring(5,7));
        }

    }

    public void updateTutorGroups(ActionEvent event) {
        String yearGroup = choiceBoxYearGroupsEdit.getValue().toString(); //gets the current value from the dropdown menu
        int numYearGroup = getNumYear(yearGroup); //DETERMINES YEAR GROUP SELECTED
        ControllerInputForms temp = new ControllerInputForms();

        switch(numYearGroup){//START OF SWITCH STATEMENT TAKING THE YEAR GROUP AS AN INPUT
            case 7:
                choiceBoxTutorGroups.setItems(FXCollections.observableList(temp.getYear7())); //SETS CLASS CHOICEBOX
                choiceBoxTutorGroups.getSelectionModel().selectFirst(); //SETS THE FIRST OPTION TO THE TOP OF THE LIST
                break;
            case 8:
                choiceBoxTutorGroups.setItems(FXCollections.observableList(temp.getYear8()));//SETS CLASS CHOICEBOX
                choiceBoxTutorGroups.getSelectionModel().selectFirst();//SETS THE FIRST OPTION TO THE TOP OF THE LIST
                break;
            case 9:
                choiceBoxTutorGroups.setItems(FXCollections.observableList(temp.getYear9()));//SETS CLASS CHOICEBOX
                choiceBoxTutorGroups.getSelectionModel().selectFirst();//SETS THE FIRST OPTION TO THE TOP OF THE LIST
                break;
            case 10:
                choiceBoxTutorGroups.setItems(FXCollections.observableList(temp.getYear10()));//SETS CLASS CHOICEBOX
                choiceBoxTutorGroups.getSelectionModel().selectFirst();//SETS THE FIRST OPTION TO THE TOP OF THE LIST
                break;
            case 11:
                choiceBoxTutorGroups.setItems(FXCollections.observableList(temp.getYear11()));//SETS CLASS CHOICEBOX
                choiceBoxTutorGroups.getSelectionModel().selectFirst();//SETS THE FIRST OPTION TO THE TOP OF THE LIST
                break;
            case 12:
                choiceBoxTutorGroups.setItems(FXCollections.observableList(temp.getYear12()));//SETS CLASS CHOICEBOX
                choiceBoxTutorGroups.getSelectionModel().selectFirst();//SETS THE FIRST OPTION TO THE TOP OF THE LIST
                break;
            case 13:
                choiceBoxTutorGroups.setItems(FXCollections.observableList(temp.getYear13()));//SETS CLASS CHOICEBOX
                choiceBoxTutorGroups.getSelectionModel().selectFirst();//SETS THE FIRST OPTION TO THE TOP OF THE LIST
                break;
        } //END

    }







    public void goEditRooms(ActionEvent event) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("AdminEditRooms.fxml")); //links .java file to .fxml file
        Stage primaryStage = new Stage();
        primaryStage.setTitle("Management booking system");
        primaryStage.resizableProperty().setValue(false);
        primaryStage.setScene(new Scene(root, 450, 450)); //sets dimensions of the window
        primaryStage.show();
        ((Node)(event.getSource())).getScene().getWindow().hide();
        System.out.println("you went to edit rooms ");
    }

    public void goEditBookings(ActionEvent event) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("AdminEditBookings.fxml")); //links .java file to .fxml file
        Stage primaryStage = new Stage();
        primaryStage.setTitle("Management booking system");
        primaryStage.resizableProperty().setValue(false);
        primaryStage.setScene(new Scene(root, 450, 450)); //sets dimensions of the window
        primaryStage.show();
        ((Node)(event.getSource())).getScene().getWindow().hide();
        System.out.println("you went to edit users ");
    }

    public void goHome(ActionEvent event) throws IOException {
        String fileName = "loggedInUser.txt"; // SETS STRING OF TEXTFILE LOCATED WITHIN PACKAGE
//        try
//        { //OPEN TRY
//            FileWriter writer = new FileWriter(fileName, false ); //creating a variable called 'writer' of the type FileWriter. False replaces file, true adds onto the file
//            writer.write("");  //SETS TEXT INSIDE FILE EQUIVALENT TO ID
//            writer.close(); //CLOSE ACCESS TO FILE
//        } catch ( IOException iox ) { //try catch statement. Throws an exception.
//            System.out.println("Problem writing " + fileName);
//            iox.printStackTrace();
//        } //END TRY

        Parent root = FXMLLoader.load(getClass().getResource("main.fxml")); //links .java file to .fxml file
        Stage primaryStage = new Stage();
        primaryStage.setTitle("Management booking system");
        primaryStage.resizableProperty().setValue(false);
        primaryStage.setScene(new Scene(root, 600, 490)); //sets dimensions of the window
        primaryStage.show();
        ((Node)(event.getSource())).getScene().getWindow().hide();
        System.out.println("you went home");
    }

    public void goAdmin(ActionEvent event) throws Exception{

        ControllerUserModel temp = new ControllerUserModel();


        //currentUser = temp.getLoggedInUser();



        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("main.fxml"));
        Parent root = loader.load();

        Stage primaryStage = new Stage();
        primaryStage.setTitle("Management booking system");
        primaryStage.resizableProperty().setValue(false);
        primaryStage.setScene(new Scene(root, 600, 489)); //sets dimensions of the window
        primaryStage.show();
        ((Node)(event.getSource())).getScene().getWindow().hide();
        System.out.println("you went to add booking ");

        // ----

        ControllerMain controller = loader.getController();
        controller.goStraightAdmin(currentUser, event);

    }


    public void editTutorGroup(ActionEvent event) {
        String userInput = TFEditedTutorGroup.getText().toString();
        String tutorGroup = choiceBoxTutorGroups.getValue().toString();
        ControllerInputForms tutorGroups = new ControllerInputForms();
        ArrayList<String> year7 = tutorGroups.getYear7();
        ArrayList<String> year8 = tutorGroups.getYear8();
        ArrayList<String> year9 = tutorGroups.getYear9();
        ArrayList<String> year10 = tutorGroups.getYear10();
        ArrayList<String> year11 = tutorGroups.getYear11();
        ArrayList<String> year12 = tutorGroups.getYear12();
        ArrayList<String> year13 = tutorGroups.getYear13();

        int numYear = getNumYear(choiceBoxYearGroupsEdit.getValue().toString());

        boolean classAlreadyExist = false;

        switch(numYear) {
            case 7:
                for(int i = 0; i<year7.size(); i++) {
                    if (userInput.equals(year7.get(i))) {
                        classAlreadyExist = true;
                    }
                }
                break;
            case 8:
                for(int i = 0; i<year7.size(); i++) {
                    if (userInput.equals(year8.get(i))) {
                        classAlreadyExist = true;
                    }
                }
                break;
            case 9:
                for(int i = 0; i<year7.size(); i++) {
                    if (userInput.equals(year9.get(i))) {
                        classAlreadyExist = true;
                    }
                }
                break;
            case 10:
                for(int i = 0; i<year7.size(); i++) {
                    if (userInput.equals(year10.get(i))) {
                        classAlreadyExist = true;
                    }
                }
                break;
            case 11:
                for(int i = 0; i<year7.size(); i++) {
                    if (userInput.equals(year11.get(i))) {
                        classAlreadyExist = true;
                    }
                }
                break;
            case 12:
                for(int i = 0; i<year7.size(); i++) {
                    if (userInput.equals(year12.get(i))) {
                        classAlreadyExist = true;
                    }
                }
                break;
            case 13:
                for(int i = 0; i<year7.size(); i++) {
                    if (userInput.equals(year13.get(i))) {
                        classAlreadyExist = true;
                    }
                }
                break;
        }

        if(!classAlreadyExist) {
            Connector.editTutorGroup(numYear, tutorGroup, userInput);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("CLASS CREATER");
            alert.setHeaderText("Please take note:");
            alert.setContentText("CLASS SUCCESSFULLY UPDATED");
            alert.showAndWait();
            TFEditedTutorGroup.setText("");
            choiceBoxYearGroupsEdit.getSelectionModel().selectFirst();
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("CLASS CREATER");
            alert.setHeaderText("Please take note:");
            alert.setContentText(userInput + " is already a class which exists");
            alert.showAndWait();
        }

    }

    public boolean checkTutorInput(ArrayList<String> inputs){

        int yearGroupCounter =7;
        for(int i = 0; i < 3; i++) {
            if(inputs.get(i).length() > 3 || !inputs.get(i).contains(Integer.toString(yearGroupCounter))) {
                return false;
            }
            yearGroupCounter++;
        }
        for(int i = 3; i < 7; i++) {
            if(inputs.get(i).length() > 4 || !inputs.get(i).contains(Integer.toString(yearGroupCounter))) {
                return false;
            }
            yearGroupCounter++;
        }
        return true;
    }

    public void addNewTutorGroup(ActionEvent event) {
            ControllerInputForms tutorGroups = new ControllerInputForms(); //instance of controllerinputforms class

        // <-- CREATE VARIABLES DICTATED BY USER INPUT -->
            String year7Input = TFYear7Add.getText().toString();
            String year8Input = TFYear8Add.getText().toString();
            String year9Input = TFYear9Add.getText().toString();
            String year10Input = TFYear10Add.getText().toString();
            String year11Input = TFYear11Add.getText().toString();
            String year12Input = TFYear12Add.getText().toString();
            String year13Input = TFYear13Add.getText().toString();
         // <-------------------------------------------->

        // <-- CREATES ARRAYLIST OF USER INPUTS FOR VALIDATION -->
            ArrayList<String> tutorGroupInputs = new ArrayList<>();

            tutorGroupInputs.add(year7Input);
            tutorGroupInputs.add(year8Input);
            tutorGroupInputs.add(year9Input);
            tutorGroupInputs.add(year10Input);
            tutorGroupInputs.add(year11Input);
            tutorGroupInputs.add(year12Input);
            tutorGroupInputs.add(year13Input);
            // <------------------------------------------------>

        //CALLS A FUNCTION WITH THE INPUT OF ARRAYLIST FOR VALIDATION
            boolean inputValid = checkTutorInput(tutorGroupInputs);

        //IF STATEMENT FOR IF VALIDATION HOLDS TRUE:
        if (inputValid) { //START IF

            // <-- GETS ARRAYLIST OF EXISTING TUTORGROUPS -->
            ArrayList<String> year7 = tutorGroups.getYear7();
            ArrayList<String> year8 = tutorGroups.getYear8();
            ArrayList<String> year9 = tutorGroups.getYear9();
            ArrayList<String> year10 = tutorGroups.getYear10();
            ArrayList<String> year11 = tutorGroups.getYear11();
            ArrayList<String> year12 = tutorGroups.getYear12();
            ArrayList<String> year13 = tutorGroups.getYear13();
            // <--------------------------------------------->

            int yearGroupDuplicate = 0; //VARIABLE FOR DETERMINING WHICH YEAR GROUP HAS BEEN DUPLICATED
            boolean duplicateClass = false; //BOOLEAN FOR FURTHER VALIDATION

            for (int i = 0; i < year7.size(); i++) { //START FOR LOOP RUNNING THROUGH ALL ARRAYLISTS
                if (year7.get(i).equals(year7Input)) {
                    yearGroupDuplicate = 7;
                    duplicateClass = true;
                }
                if (year8.get(i).equals(year8Input)) {
                    yearGroupDuplicate = 8;
                    duplicateClass = true;
                }
                if (year9.get(i).equals(year7Input)) {
                    yearGroupDuplicate = 9;
                    duplicateClass = true;
                }
                if (year10.get(i).equals(year7Input)) {
                    yearGroupDuplicate = 10;
                    duplicateClass = true;
                }
                if (year11.get(i).equals(year7Input)) {
                    yearGroupDuplicate = 11;
                    duplicateClass = true;
                }
                if (year12.get(i).equals(year7Input)) {
                    yearGroupDuplicate = 12;
                    duplicateClass = true;
                }
                if (year13.get(i).equals(year7Input)) {
                    yearGroupDuplicate = 13;
                    duplicateClass = true;
                }
            }

            if (!duplicateClass) {
                Connector.addTutorGroup(year7Input, year8Input, year9Input, year10Input, year11Input, year12Input, year13Input);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("CLASS CREATER");
                alert.setHeaderText("Please take note:");
                alert.setContentText("Tutor groups have been successfully added");
                alert.showAndWait();
                clearFieldsEditClasses(event);
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning");
                alert.setHeaderText("Please take note:");
                switch (yearGroupDuplicate) {
                    case 7:
                        alert.setContentText("Class " + year7Input + " already exists");
                        break;
                    case 8:
                        alert.setContentText("Class " + year8Input + " already exists");
                        break;
                    case 9:
                        alert.setContentText("Class " + year9Input + " already exists");
                        break;
                    case 10:
                        alert.setContentText("Class " + year10Input + " already exists");
                        break;
                    case 11:
                        alert.setContentText("Class " + year11Input + " already exists");
                        break;
                    case 12:
                        alert.setContentText("Class " + year12Input + " already exists");
                        break;
                    case 13:
                        alert.setContentText("Class " + year13Input + " already exists");
                        break;

                }

                alert.showAndWait();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("CLASS CREATER");
            alert.setHeaderText("Please take note:");
            alert.setContentText("Please enter tutor groups in the correct format");
            alert.showAndWait();
        }

        updateTutorGroups(event);


    }

    public void clearFieldsEditClasses(ActionEvent event) {
        TFYear7Add.setText("");
        TFYear8Add.setText("");
        TFYear9Add.setText("");
        TFYear10Add.setText("");
        TFYear11Add.setText("");
        TFYear12Add.setText("");
        TFYear13Add.setText("");
    }

    public ArrayList<String> updateArrayListTutorGroup(ArrayList<String> year) {
        for(int i = 0; i < year.size(); i++) {
            String tutorGroup = year.get(i).substring(year.get(i).length()-1, year.get(i).length());
            int yeargroup = Integer.parseInt(year.get(i).substring(0, year.get(i).length()-1)) + 1;
            String temp = Integer.toString(yeargroup) + tutorGroup;
            year.set(i, temp);
        }
        return year;
    }

    public ArrayList<String> updateArrayListTutorGroupDown(ArrayList<String> year) {
        for(int i = 0; i < year.size(); i++) {
            String tutorGroup = year.get(i).substring(year.get(i).length()-1, year.get(i).length());
            System.out.println(tutorGroup);
            int yeargroup = Integer.parseInt(year.get(i).substring(0, year.get(i).length()-1)) - 1;
            System.out.println(yeargroup);
            String temp = Integer.toString(yeargroup) + tutorGroup;
            year.set(i, temp);
        }
        return year;
    }

    public void decreaseYearGroups(ActionEvent event) {
        ControllerInputForms tutorGroups = new ControllerInputForms();

        //<-- SETS ARRAYLISTS OF TO BE SET TUTORGROUPS BY FETCHING THE YEAR FROM ABOVE -->
        ArrayList<String> year7 = tutorGroups.getYear8();
        ArrayList<String> year8 = tutorGroups.getYear9();
        ArrayList<String> year9 = tutorGroups.getYear10();
        ArrayList<String> year10 = tutorGroups.getYear11();
        ArrayList<String> year11 = tutorGroups.getYear12();
        ArrayList<String> year12 = tutorGroups.getYear13();
        ArrayList<String> year13 = new ArrayList<>();
        // <----------------------------------------------------------------------------->


        int numTutorGroups = year8.size(); //sets a variable containing the number of tutorgroups


        // <-- CREATION OF DYNAMICALLY CREATED BUTTONS -->

        Dialog dialog = new Dialog<>(); //INITIALIZATION OF DIALOG
        dialog.setTitle("NEW ACADEMIC YEAR"); //SETS THE TITLE OF THE WINDOW
        dialog.setHeaderText("PLEASE ENTER THE OLD YEAR 13 CLASSES"); //SETS PROMPT MESSAGE

        ButtonType loginButtonType = new ButtonType("Submit", ButtonBar.ButtonData.OK_DONE); //CREATES SUBMIT BUTTON
        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL); //CREATES CANCEL BUTTON

        GridPane grid = new GridPane(); //SETS GRIDPANE
        // <-- ADJUSTS PADDING AND GAPS -->
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 100, 10, 10));
        // <------------------------------>

        //AN ARRAYLIST OF TEXTFIELDS || ALLOWS FOR DYNAAMIC TEXTFIELD CREATION
        ArrayList<TextField> TFTutorGroups = new ArrayList<>();

        for(int i = 0; i < numTutorGroups; i++) { //FOR LOOP TO INITIALIZE EACH TEXTFIELD WITHIN ARRAYLIST
            int temp2 = i+1;
            TextField temp = new TextField(); //CREATES TEMPORARY INSTANCE
            TFTutorGroups.add(temp); //ADDS INSTANCE TO ARRAYLIST
            Label label = new Label(); //CREATES INSTANCE OF A LABEL
            label.setText("Tutor Group #" + temp2); //SETS TEXT OF LABEL
            grid.add(label, 0, i); //SETS GUI POSITION OF LABEL
            grid.add(TFTutorGroups.get(i), 1, i); //SETS GUI POSITION OF TEXTFIELD
        } //END FOR LOOP

        dialog.setResultConverter(dialogButton -> { //IF OKAY BUTTON IS PRESSED
            if (dialogButton == loginButtonType) {
                System.out.println(TFTutorGroups.get(0).getText().toString());
                for(int i = 0; i < numTutorGroups; i++) { //FOR LOOP RUNNING THROUGH THE NUMBER OF TUTOR GROUPS
                    String input = TFTutorGroups.get(i).getText().toString(); //CONVERTS TEXTFIELD INPUT INTO STRING
                    if(input.length() != 3 || !input.contains("13")) { //VALIDATION CHECK
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("Warning");
                        alert.setHeaderText("Please take note:");
                        alert.setContentText("PLEASE ENTER TUTOR GROUPS IN THE CORRECT FORMAT");
                        alert.showAndWait();

                    } else {
                        year13.add(input); //ADDS ACCEPTABLE INPUT TO ARRAYLIST OF YEAR 13S
                    }

                }



            }
            return null;
        });



        dialog.getDialogPane().setContent(grid);

        Optional<Pair<String, String>> result = dialog.showAndWait();

        // <-- CHANGING ARRAYLISTS OF TUTOR GROUPS -->
        year7 = updateArrayListTutorGroupDown(year7);
        year8 = updateArrayListTutorGroupDown(year8);
        year9 = updateArrayListTutorGroupDown(year9);
        year10 = updateArrayListTutorGroupDown(year10);
        year11 = updateArrayListTutorGroupDown(year11);
        year12 = updateArrayListTutorGroupDown(year12);
        // <---------------------------------------->


        Connector.deleteYearClasses(); //DELETES ALL TUTORGROUPS ON THE DATABASE
        Connector.addYearClasses(year7, year8, year9, year10, year11, year12, year13); //SETS NEW TUTORGROUPS

        User tempUser = new User();
        // <-- DELETES INFORMATION FROM YEAR 7 -->
        tempUser.removeBookingsYear(7);
        tempUser.removeYear(7);
        // <---------------------------------------->

        tempUser.decreaseYearForAllUsers(); //DECREASES ALL STUDENT YEARS
    }

    public void increaseYearGroups(ActionEvent event) {

        // <-------------- ESTABLISH TUTOR GROUPS -------------------:


        ControllerInputForms tutorGroups = new ControllerInputForms();

        ArrayList<String> year7 = new ArrayList<>();
        ArrayList<String> year8 = tutorGroups.getYear7();
        ArrayList<String> year9 = tutorGroups.getYear8();
        ArrayList<String> year10 = tutorGroups.getYear9();
        ArrayList<String> year11 = tutorGroups.getYear10();
        ArrayList<String> year12 = tutorGroups.getYear11();
        ArrayList<String> year13 = tutorGroups.getYear12();

        int numTutorGroups = year8.size(); //sets a variable containing the number of tutorgroups


        // <-- CREATION OF DYNAMICALLY CREATED BUTTONS -->

        Dialog dialog = new Dialog<>(); //INITIALIZATION OF DIALOG
        dialog.setTitle("NEW ACADEMIC YEAR"); //SETS THE TITLE OF THE WINDOW
        dialog.setHeaderText("PLEASE ENTER THE NEW YEAR 7 CLASSES"); //SETS PROMPT MESSAGE

        ButtonType loginButtonType = new ButtonType("Submit", ButtonBar.ButtonData.OK_DONE); //CREATES SUBMIT BUTTON
        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL); //CREATES CANCEL BUTTON

        GridPane grid = new GridPane(); //SETS GRIDPANE
        // <-- ADJUSTS PADDING AND GAPS -->
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 100, 10, 10));
        // <------------------------------>

        //AN ARRAYLIST OF TEXTFIELDS || ALLOWS FOR DYNAAMIC TEXTFIELD CREATION
        ArrayList<TextField> TFTutorGroups = new ArrayList<>();

        for(int i = 0; i < numTutorGroups; i++) { //FOR LOOP TO INITIALIZE EACH TEXTFIELD WITHIN ARRAYLIST
            int temp2 = i+1;
            TextField temp = new TextField(); //CREATES TEMPORARY INSTANCE
            TFTutorGroups.add(temp); //ADDS INSTANCE TO ARRAYLIST
            Label label = new Label(); //CREATES INSTANCE OF A LABEL
            label.setText("Tutor Group #" + temp2); //SETS TEXT OF LABEL
            grid.add(label, 0, i); //SETS GUI POSITION OF LABEL
            grid.add(TFTutorGroups.get(i), 1, i); //SETS GUI POSITION OF TEXTFIELD
        } //END FOR LOOP

        dialog.setResultConverter(dialogButton -> { //IF OKAY BUTTON IS PRESSED
            if (dialogButton == loginButtonType) {
                System.out.println(TFTutorGroups.get(0).getText().toString());
                for(int i = 0; i < numTutorGroups; i++) { //FOR LOOP RUNNING THROUGH THE NUMBER OF TUTOR GROUPS
                    String input = TFTutorGroups.get(i).getText().toString(); //CONVERTS TEXTFIELD INPUT INTO STRING
                    if(input.length() != 2 || !input.contains("7")) { //VALIDATION CHECK
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("Warning");
                        alert.setHeaderText("Please take note:");
                        alert.setContentText("PLEASE ENTER TUTOR GROUPS IN THE CORRECT FORMAT");
                        alert.showAndWait();

                    } else {
                        year7.add(input); //ADDS ACCEPTABLE INPUT TO ARRAYLIST OF YEAR 7S
                    }

                }



            }
            return null;
        });



        dialog.getDialogPane().setContent(grid);

        Optional<Pair<String, String>> result = dialog.showAndWait();

        System.out.println(year7);

        year8 = updateArrayListTutorGroup(year8);
        year9 = updateArrayListTutorGroup(year9);
        year10 = updateArrayListTutorGroup(year10);
        year11 = updateArrayListTutorGroup(year11);
        year12 = updateArrayListTutorGroup(year12);
        year13 = updateArrayListTutorGroup(year13);


        System.out.println(year8);
        System.out.println(year9);
        System.out.println(year10);
        System.out.println(year11);
        System.out.println(year12);
        System.out.println(year13);

        // <------------------------------------------------------------------------->

        User tempUser = new User();

        int numYear13s = tempUser.getNumYear13s();

        // <-------- CREATE A BIG DOCUMENT OF REPORTS FROM EACH USER --------->

        if (numYear13s != 0) {

            String path = "";
            try {
                DirectoryChooser chooser = new DirectoryChooser();
                Stage stage = (Stage) editClassesAnchorPane.getScene().getWindow();
                File file = chooser.showDialog(stage);

                if (file != null) {
                    path = file.getAbsolutePath();
                    System.out.println("Path: " + file.getAbsolutePath());
                }
                PDFCreator pdfCreator = new PDFCreator();
                pdfCreator.createSummaryOfGraduates(numYear13s, path);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("TUTOR GROUP EDITOR");
                alert.setHeaderText("NEW ACADEMIC YEAR");
                alert.setContentText("SUCCESSFULLY DONE");
                alert.showAndWait();
            } catch (Exception ex) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("TUTOR GROUP EDITOR");
                alert.setHeaderText("ERROR");
                alert.setContentText("UNABLE TO DO");
                alert.showAndWait();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("TUTOR GROUP EDITOR");
            alert.setHeaderText("ERROR");
            alert.setContentText("NO YEAR 13s");
            alert.showAndWait();
        }



        // <------------------------------------------------------------------>


        // <---- ALTERS YEARCLASSES TABLE ---->

        Connector.deleteYearClasses();
        Connector.addYearClasses(year7, year8, year9, year10, year11, year12, year13);
        // <---------------------------------->

        // <---- REMOVE STUDENTS WHO GRADUATED ---->

        tempUser.removeBookingsYear(13);
        tempUser.removeYear(13);

        tempUser.incrementYearForAllUsers();

        // <--------------------------------------->


    }

    public void deleteAllYears(ActionEvent event) { //FUNCTION THAT DELETES ALL YEARS
        try{
            Connector.deleteYearClasses();
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://" + User.getIPAddress() + ":3306/CSIA", "myuser", "testpassword");
            Statement stmt = con.createStatement();
            stmt.executeUpdate("DELETE FROM `User` WHERE 1");
            stmt.executeUpdate("DELETE FROM `Booking` WHERE 1");
            updateTutorGroups(event);
            con.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    public void viewRoomImages(ActionEvent event) throws IOException { //FUNCTION TO TO VISIT PAGE TO SEE ROOMS

        // <-- SETTING UP THE NEXT SCENE -->
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("roomImages.fxml"));
        Parent root = loader.load();
        ControllerAdminPage controller = loader.getController();
        // <------------------------------->

        // <-- INITIALIZATION OF ARRAYLISTS OF ALL INFORMATION TO BE PRESENTED -->
        ArrayList<Image> tempImages = new ArrayList<>(); //ARRAYLIST OF IMAGES
        ArrayList<String> tempDesc = new ArrayList<>(); //ARRAYLIST OF DESCRIPTIONS
        ArrayList<String> tempLocation = new ArrayList<>(); //ARRAYLIST OF LOCATIONS
        // <--------------------------------------------------------------------->

        //<-- CONNECT TO THE DATABASE IN ORDER TO RETRIEVE ROOM INFORMAATION -->

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://" + User.getIPAddress() + ":3306/CSIA", "myuser", "testpassword");
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM `Room`");

            while(rs.next()) { //LOOP TO ADD INFORMATION FROM DATABASE TO ARRAYLISTS
                // <-- FETCHING IMAGE INFORMATION AS A BLOB -->
                java.sql.Blob blob = rs.getBlob("roomPicture");
                InputStream in = blob.getBinaryStream();
                BufferedImage image = ImageIO.read(in);
                Image temp = SwingFXUtils.toFXImage(image, null);
                // <------------------------------------------>
                // <-- ADDING INFORMATION INTO ARRAYLISTS -->
                tempImages.add(temp);
                tempDesc.add(rs.getString("roomDesc"));
                tempLocation.add(rs.getString("roomLocation"));
                // <---------------------------------------->
            }

            if(rs.next()) { //BLOCK OF CODE RUN IF THE DATABASE HAS ONLY ONE ROOM
                // <-- FETCHING IMAGE INFORMATION AS A BLOB -->
                java.sql.Blob blob = rs.getBlob("roomPicture");
                InputStream in = blob.getBinaryStream();
                BufferedImage image = ImageIO.read(in);
                Image temp = SwingFXUtils.toFXImage(image, null);
                // <---------------------------------------->

                // <-- ADDING INFORMATION INTO ARRAYLISTS -->
                tempImages.add(temp);
                tempDesc.add(rs.getString("roomDesc"));
                tempLocation.add(rs.getString("roomLocation"));
                // <---------------------------------------->

            }

            //<-- SETTING GUI COMPONENTS TO FIRST ELEMENT IN EACH ARRAYLIST
            controller.imageView.setImage(tempImages.get(0));
            controller.roomDescription.setWrapText(true);
            controller.roomDescription.setText(tempDesc.get(0));
            controller.roomLocation.setText(tempLocation.get(0));
            controller.currentImagePointer = 0;
            controller.roomImages = tempImages;
            controller.roomDescriptions = tempDesc;
            controller.roomLocations = tempLocation;
            // <---------------------------------------->

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        //<--------------------------------------------------------------------->


        //<-- SWITCHING OF STAGE -->
            Stage primaryStage = new Stage();
        primaryStage.setTitle("Management booking system");
        primaryStage.resizableProperty().setValue(false);
        primaryStage.setScene(new Scene(root, 400, 400)); //sets dimensions of the window
        primaryStage.show();
        ((Node)(event.getSource())).getScene().getWindow().hide();
        System.out.println("you went to add booking ");
        // <---------------------------------------->


    }

    public void nextImage(ActionEvent event) { //FUNCTION TO VIEW NEXT IMAGE IN THE VIEW IMAGE PAGE
        int maxSize = roomImages.size(); //SETS MAX SIZE OF CAROUSEL TO NUMBER OF IMAGES
        if(currentImagePointer < maxSize-1) { //PREVENTS VISITNG AN ELEMENT WHICH DOESN'T EXIST
            currentImagePointer++;
            imageView.setImage(roomImages.get(currentImagePointer));
            roomDescription.setText(roomDescriptions.get(currentImagePointer));
            roomLocation.setText(roomLocations.get(currentImagePointer));
        }
    }

    public void prevImage(ActionEvent event) {//FUNCTION TO VIEW NEXT IMAGE IN THE VIEW IMAGE PAGE
        if(currentImagePointer > 0) {//PREVENTS VISITNG A NEGATIVE ELEMENT IN THE ARRAY LIST
            currentImagePointer--;
            imageView.setImage(roomImages.get(currentImagePointer));
            roomDescription.setText(roomDescriptions.get(currentImagePointer));
            roomLocation.setText(roomLocations.get(currentImagePointer));
        }
    }

    public void goEditAdminInfo(ActionEvent event) throws IOException { //FUNCTION TO VISIT THE ADMIN EDIT PAGE
        Parent root = FXMLLoader.load(getClass().getResource("AdminEditAdmin.fxml")); //links .java file to .fxml file
        Stage primaryStage = new Stage();
        primaryStage.setTitle("Management booking system");
        primaryStage.resizableProperty().setValue(false);
        primaryStage.setScene(new Scene(root, 500, 250)); //sets dimensions of the window
        primaryStage.show();
        ((Node)(event.getSource())).getScene().getWindow().hide();
        System.out.println("you returned back to main");

    }

    public void goMainAdmin(ActionEvent event) throws IOException { //FUNCTION TO RETURN TO MAIN ADMIN PAGE
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("adminPage.fxml"));//links .java file to .fxml file
        Parent root = loader.load();
        ControllerAdminPage controller = loader.getController(); //CREATES INSTANCE OF CONTROLLER CLASS OF TARGET FXML FILE

        Stage primaryStage = new Stage();
        primaryStage.setTitle("Management booking system");
        primaryStage.resizableProperty().setValue(false);
        primaryStage.setScene(new Scene(root, 600, 400)); //sets dimensions of the window
        primaryStage.show();
        ((Node)(event.getSource())).getScene().getWindow().hide();
        System.out.println("you went to add booking ");
    }

    public void changeAdminDetails(ActionEvent event) { //FUNCTION TO CHANGE ADMIN LOGIN DETAILS
        if(TFUsername.getText().toString().isEmpty() || TFUsername.getText().toString().isEmpty()) { //CODE RUN IF TEXTFIELDS ARE EMPTY
            // <-- WARNING TELLING THE USER THEY HAVE TO ENTER INFORMATION
            Alert alert = new Alert(Alert.AlertType.WARNING); //INSTANTIATION OF THE ALART OBJECT
            alert.setTitle("ADMIN DETAILS"); //SETTING TITLE
            alert.setHeaderText("Please take note:"); //SETTING HEADER TEXT
            alert.setContentText("Please enter information in the fields"); //SETTING CONTEXT TEXT
            alert.showAndWait(); //SHOWING GUI ELEMENT
            // <---------------------------------------->

        } else {
            try{
                // <-- INITIALIZATION OF VARIABLES -->
                String oldUsername = "";
                String newUsername = TFUsername.getText().toString();
                String newPassword = TFPassword.getText().toString();
                // <-------------------------->


                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection("jdbc:mysql://" + User.getIPAddress() + ":3306/CSIA", "myuser", "testpassword");
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM `Admin`");

                if(rs.next() == true) {
                    oldUsername = rs.getString("adminUsername");
                }
                stmt.executeUpdate("UPDATE `Admin` SET `adminUsername` = '" +  newUsername + "', `adminPassword` = '" + newPassword + "' WHERE `adminUsername` = '" + oldUsername + "'");
                con.close();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("ADMIN DETAILS");
                alert.setHeaderText("Please take note:");
                alert.setContentText("Admin information has been updated");
                alert.showAndWait();
                TFUsername.setText("");
                TFPassword.setText("");
            } catch(Exception ex) { //EXCEPTION HANDLING
                ex.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("ADMIN DETAILS");
                alert.setHeaderText("Please take note:");
                alert.setContentText("Admin information has failed to update");
                alert.showAndWait();
            }


        }
    }

    public void changeIPAddress(ActionEvent event) { //FUNCTION TO CHANGE IP ADDRESS
        if(TFIP.getText().toString().isEmpty()) { //CODE RUN IF TEXTFIELD IS EMPTY
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("ADMIN DETAILS");
            alert.setHeaderText("Please take note:");
            alert.setContentText("Please enter information in the fields");
            alert.showAndWait();
        } else {

                String fileName = "ipAddressToConnect.txt"; // SETS STRING OF TEXTFILE LOCATED WITHIN PACKAGE
                String ipAddress = TFIP.getText().toString();
                try { //OPEN TRY
                    if (justConfigured) {
                        FileWriter writer = new FileWriter(fileName, false);
                        writer.write(ipAddress);  //SETS TEXT INSIDE FILE EQUIVALENT TO NOTHING
                        writer.close(); //CLOSE ACCESS TO FILE
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("ADMIN DETAILS");
                        alert.setHeaderText("Please take note:");
                        alert.setContentText("Host IP Address Updated!");
                        alert.showAndWait();
                        TFIP.setText("");
                        justConfigured = false;

                        FXMLLoader loader = new FXMLLoader();
                        loader.setLocation(getClass().getResource("main.fxml"));
                        Parent root = loader.load();
                        ControllerMain controller = loader.getController();

//                        controller.setIPFailure(false);
//                        controller.setButtonRegisterUserDisable(false);

                        Stage primaryStage = new Stage();
                        primaryStage.setTitle("Management booking system");
                        primaryStage.resizableProperty().setValue(false);
                        primaryStage.setScene(new Scene(root, 600, 489)); //sets dimensions of the window
                        primaryStage.show();
                        ((Node) (event.getSource())).getScene().getWindow().hide();
                        System.out.println("you went to add booking ");
                    } else {
                        FileWriter writer = new FileWriter(fileName, false);
                        writer.write(ipAddress);  //SETS TEXT INSIDE FILE EQUIVALENT TO NOTHING
                        writer.close(); //CLOSE ACCESS TO FILE
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("ADMIN DETAILS");
                        alert.setHeaderText("Please take note:");
                        alert.setContentText("Host IP Address Updated!");
                        alert.showAndWait();
                        TFIP.setText("");
                        justConfigured = false;

                        FXMLLoader loader = new FXMLLoader();
                        loader.setLocation(getClass().getResource("main.fxml"));
                        Parent root = loader.load();
                        ControllerMain controller = loader.getController();



                        Stage primaryStage = new Stage();
                        primaryStage.setTitle("Management booking system");
                        primaryStage.resizableProperty().setValue(false);
                        primaryStage.setScene(new Scene(root, 600, 489)); //sets dimensions of the window
                        primaryStage.show();
                        ((Node) (event.getSource())).getScene().getWindow().hide();
                        System.out.println("you went to add booking ");
                    }

                    } catch(IOException iox){ //try catch statement. Throws an exception.
                        System.out.println("Problem writing " + fileName);
                        iox.printStackTrace();
                    } //END TRY

        }
    }

    public void setJustConfigured(Boolean justConfigured) {
        this.justConfigured = justConfigured;
    } //FUNCTION TO CHECK IF CONFIGURED
}
