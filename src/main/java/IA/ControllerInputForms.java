package IA;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.*;

public class ControllerInputForms {





    private TextField jerome;
    @FXML Label inputFormLabel;
    @FXML AnchorPane anchorPaneAddUser, addRoomAnchorpane;
    @FXML Button buttonSubmit, buttonReturnWhenFail, buttonImage;
    @FXML ChoiceBox choiceBoxClass, choiceBoxYearGroup, choiceBoxDate, choiceBoxRooms, choiceBoxBookingDuration, choiceBoxBookingStart;
    @FXML  TextField textFieldFirstName, textFieldLastName, textFieldComment, textFieldStudentID, textFieldRoomLocation, textFieldRoomDesc, textFieldRoomCapacity, textFieldRoomInstruments, textFieldRoomComment;
    @FXML private TextField textFieldBookingGuests, textFieldBookingComment, textFieldStuID, textFieldBookingPurpose;
    @FXML Label labelBookingsThatExist, labelSelectYear, labelFirstName, labelLastName, labelStudentID, labelComment, labelSelectClass;
    private static Connection con;
    private User currentUser;
    private String path = "";

    static {
        try {
            con = DriverManager.getConnection("jdbc:mysql://" + User.getIPAddress() + ":3306/CSIA", "myuser", "testpassword");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<String> getYear7() {
        return year7;
    }

    public ArrayList<String> getYear8() {
        return year8;
    }

    public ArrayList<String> getYear9() {
        return year9;
    }

    public ArrayList<String> getYear10() {
        return year10;
    }

    public ArrayList<String> getYear11() {
        return year11;
    }

    public ArrayList<String> getYear12() {
        return year12;
    }

    public ArrayList<String> getYear13() {
        return year13;
    }


    protected ArrayList<String> empty = new ArrayList<String>() {{
        add("APPLICATION NEEDS SETUP");
        add("APPLICATION NEEDS SETUP");
        add("APPLICATION NEEDS SETUP");
        add("APPLICATION NEEDS SETUP");
        add("APPLICATION NEEDS SETUP");
    }};

    protected ArrayList<String> year7 = new ArrayList<String>() {{
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM `YearClasses`");

            while(rs.next()) {
                String tutorClass = rs.getString("Yr" + 7);
                add(tutorClass);

            }

        }catch(ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
        }
    }};
    protected ArrayList<String> year8 = new ArrayList<String>() {{
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM `YearClasses`");

            while(rs.next()) {
                String tutorClass = rs.getString("Yr" + 8);
                add(tutorClass);

            }

        }catch(ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
        }
    }};
    protected ArrayList<String> year9 = new ArrayList<String>() {{
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM `YearClasses`");

            while(rs.next()) {
                String tutorClass = rs.getString("Yr" + 9);
                add(tutorClass);

            }

        }catch(ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
        }
    }};
    protected ArrayList<String> year10 = new ArrayList<String>() {{
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM `YearClasses`");

            while(rs.next()) {
                String tutorClass = rs.getString("Yr" + 10);
                add(tutorClass);

            }

        }catch(ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
        }
    }};
    protected ArrayList<String> year11 = new ArrayList<String>() {{
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM `YearClasses`");

            while(rs.next()) {
                String tutorClass = rs.getString("Yr" + 11);
                add(tutorClass);

            }

        }catch(ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
        }
    }};
    protected ArrayList<String> year12 = new ArrayList<String>() {{
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM `YearClasses`");

            while(rs.next()) {
                String tutorClass = rs.getString("Yr" + 12);
                add(tutorClass);

            }

        }catch(ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
        }
    }};
    protected ArrayList<String> year13 = new ArrayList<String>() {{
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM `YearClasses`");

            while(rs.next()) {
                String tutorClass = rs.getString("Yr" + 13);
                add(tutorClass);

            }

        }catch(ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
        }
    }};

    private ArrayList<String> defaultStartingTimes = new ArrayList<String>() {{
       add("12:15");
       add("12:30");
       add("12:45");
    }};

    private ArrayList<String> firstStartingTime = new ArrayList<String>() {{
        add("15 minutes");
        add("30 minutes");
        add("45 minutes");
    }};

    private ArrayList<String> secondStartingTime = new ArrayList<String>() {{
        add("15 minutes");
        add("30 minutes");
    }};

    private ArrayList<String> lastStartingTime = new ArrayList<String>() {{
        add("15 minutes");
    }};

    public void returnHome(ActionEvent event) throws Exception{ //maybe try making functions private
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("main.fxml"));
        Parent root = loader.load();
        ControllerMain controller = loader.getController();
//        User user = new User();
//        controller.TFEnterID.setText(Integer.toString(user.getLoggedInUserID()));
//        controller.enterProgram(event);

        Stage primaryStage = new Stage();
        primaryStage.setTitle("Management booking system");
        primaryStage.resizableProperty().setValue(false);
        primaryStage.setScene(new Scene(root, 600, 489)); //sets dimensions of the window
        primaryStage.show();
        ((Node) (event.getSource())).getScene().getWindow().hide();
        System.out.println("you went to add booking ");

    }

    public void returnBacktoBookingScreen(ActionEvent event) throws Exception{ //maybe try making functions private
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("main.fxml"));
        Parent root = loader.load();
        ControllerMain controller = loader.getController();
        User user = new User();
        controller.TFEnterID.setText(Integer.toString(user.getLoggedInUserID()));
        controller.enterProgram(event);

        Stage primaryStage = new Stage();
        primaryStage.setTitle("Management booking system");
        primaryStage.resizableProperty().setValue(false);
        primaryStage.setScene(new Scene(root, 600, 489)); //sets dimensions of the window
        primaryStage.show();
        ((Node) (event.getSource())).getScene().getWindow().hide();
        System.out.println("you went to add booking ");

    }



    public void clearFields(ActionEvent event) {
        choiceBoxYearGroup.getSelectionModel().selectFirst();
        textFieldComment.setText("");
        textFieldFirstName.setText("");
        textFieldLastName.setText("");
        textFieldStudentID.setText("");
    }

    public void updateClassChoiceBox(ActionEvent event) { //FUNCTION UPDATES CHOICEBOX ON CHANGE

        String yearGroup = choiceBoxYearGroup.getValue().toString(); //gets the current value from the dropdown menu
        int numYearGroup = getNumYear(yearGroup); //DETERMINES YEAR GROUP SELECTED
        switch(numYearGroup){//START OF SWITCH STATEMENT TAKING THE YEAR GROUP AS AN INPUT
            case 7:
                choiceBoxClass.setItems(FXCollections.observableList(year7)); //SETS CLASS CHOICEBOX
                choiceBoxClass.getSelectionModel().selectFirst(); //SETS THE FIRST OPTION TO THE TOP OF THE LIST
                break;
            case 8:
                choiceBoxClass.setItems(FXCollections.observableList(year8));//SETS CLASS CHOICEBOX
                choiceBoxClass.getSelectionModel().selectFirst();//SETS THE FIRST OPTION TO THE TOP OF THE LIST
                break;
            case 9:
                choiceBoxClass.setItems(FXCollections.observableList(year9));//SETS CLASS CHOICEBOX
                choiceBoxClass.getSelectionModel().selectFirst();//SETS THE FIRST OPTION TO THE TOP OF THE LIST
                break;
            case 10:
                choiceBoxClass.setItems(FXCollections.observableList(year10));//SETS CLASS CHOICEBOX
                choiceBoxClass.getSelectionModel().selectFirst();//SETS THE FIRST OPTION TO THE TOP OF THE LIST
                break;
            case 11:
                choiceBoxClass.setItems(FXCollections.observableList(year11));//SETS CLASS CHOICEBOX
                choiceBoxClass.getSelectionModel().selectFirst();//SETS THE FIRST OPTION TO THE TOP OF THE LIST
                break;
            case 12:
                choiceBoxClass.setItems(FXCollections.observableList(year12));//SETS CLASS CHOICEBOX
                choiceBoxClass.getSelectionModel().selectFirst();//SETS THE FIRST OPTION TO THE TOP OF THE LIST
                break;
            case 13:
                choiceBoxClass.setItems(FXCollections.observableList(year13));//SETS CLASS CHOICEBOX
                choiceBoxClass.getSelectionModel().selectFirst();//SETS THE FIRST OPTION TO THE TOP OF THE LIST
                break;
        } //END OF SWITCH STATEMENT

    } //END OF FUNCTION

    public void updateStartingTimeChoiceBox(ActionEvent event) {
        //updateBookingText();

        String bookingDuration = choiceBoxBookingStart.getValue().toString();
        if (!bookingDuration.equals("FULL")) {
            int bookingStart = Integer.parseInt(bookingDuration.substring(3, 5));
            System.out.println(bookingStart);
            int numBookings = 0;
            Booking booking = new Booking();
            Rooms tempRoom = new Rooms();
            ArrayList<Rooms> allRooms = tempRoom.getAllRooms();
            int index = 0;
            try {
                if (!choiceBoxRooms.getValue().toString().isEmpty()) {
                    for (int i = 0; i < allRooms.size(); i++) {
                        if (choiceBoxRooms.getValue().toString().equals(allRooms.get(i).getRoomLocation())) {
                            index = i;
                            break;
                        }
                    }
                }
                numBookings = booking.checkHowManyBookings(choiceBoxDate.getValue().toString(), allRooms.get(index).getRoomID());
            } catch (Exception ex) {
                System.out.println("failed to get booking");
            }

            if (numBookings == 0) {
                switch (bookingStart) {
                    case 15:
                        choiceBoxBookingDuration.setItems(FXCollections.observableList(firstStartingTime));
                        choiceBoxBookingDuration.getSelectionModel().selectFirst();
                        break;
                    case 30:
                        choiceBoxBookingDuration.setItems(FXCollections.observableList(secondStartingTime));
                        choiceBoxBookingDuration.getSelectionModel().selectFirst();
                        break;
                    case 45:
                        choiceBoxBookingDuration.setItems(FXCollections.observableList(lastStartingTime));
                        choiceBoxBookingDuration.getSelectionModel().selectFirst();
                        break;
                }
            } else {
                ArrayList<Booking> bookingsThisDay = booking.getBookingsFromDate(choiceBoxDate.getValue().toString(), allRooms.get(index).getRoomID());
                ArrayList<Integer> startingTimes = new ArrayList<>();
                ArrayList<Integer> endingTimes = new ArrayList<>();
                ArrayList<Integer> duration = new ArrayList<>();

                String temp = "";
                for (int i = 0; i < bookingsThisDay.size(); i++) {
                    System.out.println(bookingsThisDay.get(i).getBookingPurpose());
                    int startingTime = 1200 + Integer.parseInt(bookingsThisDay.get(i).getBookingTime().substring(0, 2));
                    int endingTime = startingTime + Integer.parseInt(bookingsThisDay.get(i).getBookingTime().substring(2, 4));
                    int durationTime = endingTime - startingTime;
                    startingTimes.add(startingTime);
                    endingTimes.add(endingTime);
                    duration.add(durationTime);
//                updateText.add("One booking between " + startingTime + " and " + endingTime);
                    temp += ("One booking between " + startingTime + " and " + endingTime + "\n");
                }

                ArrayList<String> setDurationList = determinePossibleBookingDurations(duration, numBookings, startingTimes.get(0));
                choiceBoxBookingDuration.setItems(FXCollections.observableList(setDurationList));
                choiceBoxBookingDuration.getSelectionModel().selectFirst();
            }

        }

    }

    public int getNumYear(String yearGroup) {

        if(yearGroup.length() == 6) {
            return Integer.parseInt(yearGroup.substring(5,6));
        } else {
            return Integer.parseInt(yearGroup.substring(5,7));
        }

    }

    public boolean checkFormat() {


        String fName = textFieldFirstName.getText().toString();
        char[] fNameChar = fName.toCharArray();
        for(char i : fNameChar) {
            if(Character.isDigit(i)) {
                return false;
            }
        }
        String lName = textFieldLastName.getText().toString();
        char[] lNameChar = lName.toCharArray();
        for(char i : lNameChar) {
            if(Character.isDigit(i)) {
                return false;
            }
        }

        String inputtedID = textFieldStudentID.getText().toString();

        if(inputtedID.matches("[0-9]+") == false || inputtedID.length() > 6) {
            return false;
        }


        return true;
    }

    //SUBMIT CREATE NEW USER
    public void submit(ActionEvent event) throws IOException {
        System.out.println("testestetstestsetset");
        boolean fieldsEmpty = checkFields(); //BOOLEAN FOR VERIFICATION
        boolean formattedCorrectly = checkFormat();
        System.out.println("the data is " + formattedCorrectly);

        //IF FIELDS AREN'T INPUTTED
        if (fieldsEmpty == true) {  //START IF
            //<-- CREATE ALERT -->
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Warning");
            alert.setHeaderText("Please take note:");
            alert.setContentText("Please enter all fields before continuing");
            alert.showAndWait();
            // <----------------->
            } else if (!formattedCorrectly) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("Please take note:");
            alert.setContentText("Please enter data correctly (no numbers in names, max length student ID 6)");
            alert.showAndWait();

            textFieldFirstName.setText("");
            textFieldLastName.setText("");
            textFieldStudentID.setText("");
            textFieldComment.setText("");


        }else { //START ELSE (FIELDS INPUTTED CORRECTLY)

            // <-- generates ID for user -->

            List<Integer> temp = Connector.getAllUserIds();
            Connector.updateList("User", temp, "userID");
            int ID = Connector.generateID(temp, "User", "userID");


            // <--------------------------->


            // <-- Creates temporary variables of information


            String fName = textFieldFirstName.getText().toString();
            String lName = textFieldLastName.getText().toString();
            String yearGroup = choiceBoxYearGroup.getValue().toString();
            int year = getNumYear(yearGroup);
            String tutorGroup = choiceBoxClass.getValue().toString();
            int schoolID = Integer.parseInt(textFieldStudentID.getText().toString());
            String userComment = textFieldComment.getText().toString();


            // <-------------------------------------------->

            // <-- set up object of user -->





            // <--------------------------->

            // <-- notifies user of their personal ID -->
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("CREATION");
            alert.setHeaderText("Please take note:");
            alert.setContentText("Your personal ID for your account is " + ID);
            alert.showAndWait();

            // <---------------------------------------->

            // <-- Goes back to home page -->

            goHome(event);

            // <---------------------------->

            // <-- Adds user to the database -->

            Connector.createUser(fName, lName, year, tutorGroup, schoolID, ID, userComment);

            // <------------------------------->



        }
        }



        // TO DO MAKE THE FUNCTION BELOW MORE ABSTRACT

    private boolean checkFields() { //error checking

        if(choiceBoxClass.getValue().equals("CLASS") || textFieldStudentID.getText().isEmpty() == true || textFieldFirstName.getText().isEmpty() == true || textFieldLastName.getText().isEmpty() == true ||  textFieldComment.getText().isEmpty() == true) {
            return true;
        }

        return false;
    }

    public boolean checkRoomFields() {
        if (textFieldRoomLocation.getText().toString().isEmpty() || textFieldRoomDesc.getText().toString().isEmpty() || textFieldRoomCapacity.getText().toString().isEmpty() || textFieldRoomInstruments.getText().toString().isEmpty() || textFieldRoomComment.getText().toString().isEmpty()) {
            return false;
        }
        return true;
    }

    public boolean checkRoomFormat() {
        String inputtedID = textFieldRoomCapacity.getText().toString();

        if(inputtedID.matches("[0-9]+") == false) {
            return false;
        }
        return true;
    }

    public void submitRoom(ActionEvent event) throws Exception { //                   <-------- MAKE THE SUBMIT BUTTON MORE ABSTRACT ------->

        // <-- Creates variable of type Rooms -->

        boolean fieldsEmptyRoom = checkRoomFields();
        boolean formattedCorrectlyRooms = checkRoomFormat();

        if(!fieldsEmptyRoom) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Warning");
            alert.setHeaderText("Please take note:");
            alert.setContentText("Please enter all fields before continuing");
            alert.showAndWait();
        } else if (!formattedCorrectlyRooms) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Warning");
            alert.setHeaderText("Please take note:");
            alert.setContentText("Please enter data correctly into each field (no letters in capacity)");
            alert.showAndWait();
        } else {

            Rooms currentRoom = new Rooms();


            currentRoom.setRoomLocation(textFieldRoomLocation.getText().toString());
            currentRoom.setRoomDesc(textFieldRoomDesc.getText().toString());
            currentRoom.setRoomCapacity(Integer.parseInt(textFieldRoomCapacity.getText().toString()));
            ;
            currentRoom.setRoomInstruments(textFieldRoomInstruments.getText().toString());
            currentRoom.setRoomComment(textFieldRoomComment.getText().toString());
            File image = new File(path);
            currentRoom.setRoomImage(image);

            List<Integer> temp = Connector.getAllRoomIds();
            Connector.updateList("Room", temp, "roomID");
            currentRoom.setRoomID(Connector.generateID(temp, "Room", "roomID"));


            // <----------------------------------------->


            // <-- Add room to the database -->

            Connector.createRoom(currentRoom);

            // <------------------------------>

            // <-- Go back to home page -->

            goViewRoomInfo(event);

            // <-------------------------->

        }
    }

    public void submitBooking(ActionEvent event) throws IOException { //START OF FUNCTION

        // <-- INITIALIZING OBJECTS -->
        Rooms tempRoom = new Rooms();
        Booking newBooking = new Booking();
        // <-------------------------->

        if(choiceBoxBookingDuration.getValue().toString().equals("FULL")) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Notice");
            alert.setHeaderText("Please take note:");
            alert.setContentText("There is already a maximum number of bookings for this room");
            alert.showAndWait();
            textFieldBookingPurpose.setText("");
            textFieldStuID.setText("");
            textFieldBookingGuests.setText("");
            textFieldBookingComment.setText("");
        }


        // <--- DETERMINING THE ROOM ID OF THE SELECTED ROOM --->

        ArrayList<Rooms> allRooms = tempRoom.getAllRooms();
        int index = 0;

        for(int i = 0; i < allRooms.size(); i++) {
            System.out.println(choiceBoxRooms.getValue().toString());
            if(choiceBoxRooms.getValue().toString().equals(allRooms.get(i).getRoomLocation())) {
                index = i;
                break;
            }
        }

        // <---------------------------------------------------->

        int numBookings = newBooking.checkHowManyBookings(choiceBoxDate.getValue().toString(), allRooms.get(index).getRoomID());
        System.out.println("The number of bookings on this day and this room is " + numBookings);

        if(numBookings == 3) {

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Notice");
            alert.setHeaderText("Please take note:");
            alert.setContentText("There is already a maximum number of bookings for today");
            alert.showAndWait();
            goHome(event);
        } else {


            // <--------- CREATING BOOKING OBJECT ---------->

            //int startTime = newBooking.getStartingTime();
            //int bookingTime = startTime + Integer.parseInt(choiceBoxBookingDuration.getValue().toString().substring(0, 2));

            String bookingTime = choiceBoxBookingStart.getValue().toString();
            bookingTime = bookingTime.substring(3) + choiceBoxBookingDuration.getValue().toString().substring(0, 2) ;
            System.out.println(bookingTime);

            int duration = Integer.parseInt(choiceBoxBookingDuration.getValue().toString().substring(0, 2));





            if(textFieldBookingPurpose.getText().toString().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Warning");
                alert.setHeaderText("Please take note:");
                alert.setContentText("Please enter at least your purpose");
                alert.showAndWait();
            } else {

                newBooking.setRoomID(allRooms.get(index).getRoomID());
                newBooking.setBookingTime(bookingTime);
                newBooking.setBookingPurpose(textFieldBookingPurpose.getText().toString());
                newBooking.setBookingDate(choiceBoxDate.getValue().toString());
                    try{
                        int numGuests;
                        if(textFieldBookingGuests.getText().toString().isEmpty()) {
                            numGuests = 0;
                        } else {
                            numGuests = Integer.parseInt(textFieldBookingGuests.getText().toString());
                        }
                        Rooms tempRoom2 = new Rooms();
                        int roomSelectedCapacity = tempRoom2.getRoomCapacity(allRooms.get(index).getRoomID());

                        if(numGuests <= roomSelectedCapacity) {
                            newBooking.setBookingGuests(numGuests);

                            if(textFieldBookingComment.getText().isEmpty()) {
                                newBooking.setBookingComment("");
                            } else {
                                newBooking.setBookingComment(textFieldBookingComment.getText().toString());
                            }

                            newBooking.setBookingDate(choiceBoxDate.getValue().toString());

                            String userInput = "";
                            User tempUser = new User();
                            boolean check = true;
                            if (textFieldStuID.getText().isEmpty()) {

                                userInput = Integer.toString(tempUser.getLoggedInUserID());
                            } else {
                                userInput = textFieldStuID.getText();
                            }
                            ArrayList<Integer> temp = new ArrayList<>();
                                try {
                                    System.out.println("THIS IS WHATS BEING CHECKED " + userInput);
//                                    boolean check = Connector.checkIDExist(Integer.parseInt(userInput), temp);
                                     check = tempUser.checkUserIDExist(Integer.parseInt(userInput));
                                    System.out.println("THIS IS THE RESULT " + check);
                                    if(check == true) {
                                        System.out.println("this is called");
                                        newBooking.setUserID(Integer.parseInt(userInput));
                                        // <-- DETERMINING THE RANDOM BOOKING ID -->
                                        System.out.println("THIS IS WHAT WAS SET: "+newBooking.getUserID());

                                        List<Integer> temp2 = Connector.getAllBookingIds();
                                        Connector.updateList("Booking", temp2, "bookingID");
                                        newBooking.setBookingID(Connector.generateID(temp2, "Booking", "bookingID"));

                                        // <--------------------------------------->

                                        // <------------------------------------------->

                                        // <-- INPUTS BOOKING INTO DATABASE -->

                                        Connector.createBooking(newBooking);

                                        // <---------------------------------->

                                        // <-- GOES HOME -->
                                        Booking booking = new Booking();
                                        ArrayList<Booking> bookingtemp = booking.getAllBooking();
                                        System.out.println(bookingtemp);


                                        //goHome(event);
                                        FXMLLoader loader = new FXMLLoader();
                                        loader.setLocation(getClass().getResource("main.fxml"));
                                        Parent root = loader.load();
                                        ControllerMain controller = loader.getController();
                                        User user = new User();
                                        controller.TFEnterID.setText(Integer.toString(user.getLoggedInUserID()));
                                        controller.enterProgram(event);

                                        Stage primaryStage = new Stage();
                                        primaryStage.setTitle("Management booking system");
                                        primaryStage.resizableProperty().setValue(false);
                                        primaryStage.setScene(new Scene(root, 600, 489)); //sets dimensions of the window
                                        primaryStage.show();
                                        ((Node) (event.getSource())).getScene().getWindow().hide();
                                        System.out.println("you went to add booking ");

                                    } else {
                                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                        alert.setTitle("Warning");
                                        alert.setHeaderText("Please take note:");
                                        alert.setContentText("We could not find your ID on the database, please try again");
                                        alert.showAndWait();

                                    }
                                }catch (Exception ex) {
                                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                    alert.setTitle("Warning");
                                    alert.setHeaderText("Please take note:");
                                    alert.setContentText("Please enter only numbers for ID");
                                    alert.showAndWait();
                                }


                        } else {
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("Warning");
                            alert.setHeaderText("Please take note:");
                            alert.setContentText("You have too many guests. \nThis room has a maximum of " + roomSelectedCapacity + " ppl.");
                            alert.showAndWait();
                        }


                    } catch(Exception ex) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Warning");
                        alert.setHeaderText("Please take note:");
                        alert.setContentText("Please enter a number for number of guests");
                        alert.showAndWait();
                    }







                // <--------------->

                //TODO fix the admin page layout situation. PARTITION IT BETTER
                //TODO make it so you can go back to the admin page after editing rooms/users and stuff
                //TODO look at the bug where you try it with a different user and bookings become 0 somehow

            } //END IF


        }

    }// END OF FUNCTION



    public void setDateChoiceBox(List<String> dates, int index){
        choiceBoxDate.setItems(FXCollections.observableList(dates));

        choiceBoxDate.getSelectionModel().select(index);
        choiceBoxDate.setDisable(true);
    }

    public void setRoomChoiceBox(List<String> rooms) {
        choiceBoxRooms.setItems(FXCollections.observableList(rooms));
        choiceBoxRooms.getSelectionModel().selectFirst();

    }

    public void initializeClassChoiceBox(List<String> classes) {
        choiceBoxClass.setItems(FXCollections.observableList(year7));
        choiceBoxClass.getSelectionModel().selectFirst();
    }

    public void setCurrentUser(User user) {
        currentUser = user;
    }

    public void bookingFillInUserID() {
        //textFieldStudentID.setPromptText("testestestest");
        System.out.println("This is the current user " + currentUser.getFirstName());
        textFieldStuID.setPromptText(currentUser.getFirstName() + " with ID " + currentUser.getUserID());
    }

    public void updateBookingText() { //updates text on bottom of form telling how the room is booked

        // <------ INITIALIZE VARIABLES ------>
        int defaultMinTime = 1000000;
        int defaultMaxTime = 0;
        Booking booking = new Booking();
        Rooms tempRoom = new Rooms();
        // <---------------------------------->

        // <--------- FIND NUMBER OF BOOKINGS --------->
        ArrayList<Rooms> allRooms = tempRoom.getAllRooms(); //CREATES ARRAYLIST OF TYPE ROOM OF ALL ROOMS BY QUERYING DATABASE
        int index = 0; //SETS VARIABLE INDEX AS 0
        try{
            if(!choiceBoxRooms.getValue().toString().isEmpty()) { //START IF
                for (int i = 0; i < allRooms.size(); i++) { //START FOR LOOP || ITERATES THROUGH ARRAYLIST OF ROOMS
                    if (choiceBoxRooms.getValue().toString().equals(allRooms.get(i).getRoomLocation())) { //START IF | CHECKS IF THE CHOICEBOX IS EQUAL TO A ROOM LOCATION WITHIN THE ARRAYLIST
                        index = i; //IF EQUAL, THE INDEX OF THE ROOM IS RECORDED SO WE KNOW WHICH ROOM IS SELECTED
                        break; //STOPS FOR LOOP
                    } //END IF
                }//END FOR LOOP
            } //END IF

         //CALLS A FUNCTION TO DETERMINE NUMBER OF BOOKINGS FOR A PARTICULAR ROOM TAKING THE DATE AND ROOMID
        int numBookings = booking.checkHowManyBookings(choiceBoxDate.getValue().toString(), allRooms.get(index).getRoomID());
        //<-------------------------------------------->

        //<------------- CREATE LIST OF BOOKINGS ON DATE SELECTED --------------->
        ArrayList<Booking> bookingsThisDay = booking.getBookingsFromDate(choiceBoxDate.getValue().toString(), allRooms.get(index).getRoomID());
        //<---------------------------------------------------------------------->

        //<-------------- UPDATES LABEL TELLING INFORMATION ON BOOKINGS THAT EXIST ---------------->

            System.out.println("bookings this day: " + bookingsThisDay);

        // <-- INITIALIZE ARRAYLISTS -->
        ArrayList<Integer> startingTimes = new ArrayList<>();
        ArrayList<Integer> endingTimes = new ArrayList<>();
        ArrayList<Integer> duration = new ArrayList<>();
        // <--------------------------->

            String temp = ""; //TEMP VARIABLES
            for(int i = 0; i<bookingsThisDay.size(); i++) { //ITERATES THROUGH ALL BOOKINGS THIS DAY START FOR LOOP
                int startingTime = 1200 + Integer.parseInt(bookingsThisDay.get(i).getBookingTime().substring(0,2)); //SETS STARTING TIME
                int endingTime = startingTime + Integer.parseInt(bookingsThisDay.get(i).getBookingTime().substring(2,4)); //SETS ENDING TIME
                int durationTime = endingTime - startingTime; //DETERMINES DURATION
                startingTimes.add(startingTime); //ADDS STARTING TIME TO A LIST
                endingTimes.add(endingTime); //ADDS ENDING TIME TO A LIST
                duration.add(durationTime); //ADDS DURATION TO A LIST

                if(endingTime== 1260) {
                    endingTime=1300; //REPLACES 1260 WITH 1300
                }
                temp += ("One booking between " + startingTime + " and " + endingTime + "\n"); //APPENDS TO A STRING OF THE BOOKINGS
            }

            labelBookingsThatExist.setText(temp); //SETS GUI LABEL WITH THE TEXT OF WHEN THERE ARE BOOKINGS
            //<------------------------------------------------------------------------------------->


            //<---------------- DETERMINES CHOICEBOXES FOR STARTING/DURATION ------------------>

            System.out.println("This is durations: " + duration);
            // <---- DEFAULT STARTING TIMES (IF THERE ARE NO BOOKINGS)----->
            choiceBoxBookingStart.setItems(FXCollections.observableList(defaultStartingTimes));
            choiceBoxBookingStart.getSelectionModel().selectFirst();
            //<------------------------------------------------------------>

            if(numBookings!=0) {

               // sortOutTimes(duration, numBookings, startingTimes.get(0), startingTimes, endingTimes);

               // <----------------------------- STARTING TIMES ----------------------------------->
            ArrayList<String> newPossibleTimes = determinePossibleTimes(startingTimes, endingTimes, numBookings, duration);
            choiceBoxBookingStart.setItems(FXCollections.observableList(newPossibleTimes));
            choiceBoxBookingStart.getSelectionModel().selectFirst();
                //<-------------------------------------------------------------------------------->

                //<-------------------------------- DURATION -------------------------------------->
            ArrayList<String> setDurationList = determinePossibleBookingDurations(duration, numBookings, startingTimes.get(0));
            choiceBoxBookingDuration.setItems(FXCollections.observableList(setDurationList));
            choiceBoxBookingDuration.getSelectionModel().selectFirst();
               // <-------------------------------------------------------------------------------->
            }

        } catch(Exception ex) {
            ex.printStackTrace();
            System.out.println("Booking not found");
        }

    }//END OF FUNCTION

    public ArrayList<String> determinePossibleBookingDurations(ArrayList<Integer> durationList, int numBookings, int startingTime) {
        ArrayList<String> possibleDurations = new ArrayList<>();
        int sumDuration = 0;
        for(int i = 0; i < durationList.size(); i++) {
            sumDuration += durationList.get(i);
        }

        System.out.println(sumDuration);
        System.out.println(startingTime);

        switch(numBookings){
            case 0: {
                possibleDurations.add("15 minutes");
                possibleDurations.add("30 minutes");
                possibleDurations.add("45 minutes");
                break;
            }
            case 1:
                switch(sumDuration) {
                    case 15:
                        switch(startingTime) {
                            case 1215:
                                if(choiceBoxBookingStart.getValue().toString().substring(3, 5).equals("45")) {
                                    possibleDurations.add("15 minutes");
                                } else {
                                    possibleDurations.add("15 minutes");
                                    possibleDurations.add("30 minutes");
                                }
                                break;
                            case 1230:
                                possibleDurations.add("15 minutes");
                                break;
                            case 1245:
                                if(choiceBoxBookingStart.getValue().toString().substring(3,5).equals("30")) {
                                    possibleDurations.add("15 minutes");
                                } else{
                                    possibleDurations.add("15 minutes");
                                    possibleDurations.add("30 minutes");
                                }
                        }
                        break;
                    case 30:
                        possibleDurations.add("15 minutes");
                        break;
                    case 45:
                        possibleDurations.add("FULL");
                        break;
                }

                break;
            case 2:
                    switch(sumDuration) {
                        case 30:
                            possibleDurations.add("15 minutes");
                            break;
                        case 45:
                            possibleDurations.add("FULL");
                    }
                break;
            case 3:
                possibleDurations.add("FULL");
                break;
        }
        return possibleDurations;
    }


    // <-- FUNCTION WHICH DETERMINS POSSIBLE BOOKING TIMES -->
    public ArrayList<String> determinePossibleTimes(ArrayList<Integer> minTimesList, ArrayList<Integer> maxTimesList, int numBookings, ArrayList<Integer> durations) {
        ArrayList<String> possibleTimes = new ArrayList<>(); //CREATES ARRAYLIST OF POSSIBLE TIMES

        // <-- INITIALIZE HASHSETS -->
        Set<Integer> setMinTimes = new HashSet<Integer>();
        Set<Integer> setMaxTimes = new HashSet<Integer>();
        Set<Integer> setAllTimes = new HashSet<Integer>();

        // <------------------------>

        // <-- POPULATES HASHSET FROM A LIST -->

        for(int i = 0; i<minTimesList.size(); i++) {
            setMinTimes.add(minTimesList.get(i));

            setAllTimes.add(minTimesList.get(i));
            setAllTimes.add(maxTimesList.get(i));
        }

        // <----------------------------------->

        // <--- INITIALIZE DEFAULT TIMES FOR BOOKINGS HASHSET --->

        Set<Integer> defaultTimes = new HashSet<Integer>();
        defaultTimes.add(1215);
        defaultTimes.add(1230);
        defaultTimes.add(1245);

        // <----------------------------------------------------->

        // <--- CREATES HASHSET BY CREATING A UNION OF MINTIMES AND MAX TIMES --->
        Set<Integer> union = new HashSet<Integer>(setMinTimes);
        union.addAll(setMaxTimes);
        // <--------------------------------------------------------------------->

        // <--- CREATES HASHSET BY CREATING A DIFFERENCE BETWEEN THE UNION AND DEFAULT TIMES --->

        Set<Integer> differenceBetweenUnion = new HashSet<Integer>(defaultTimes);
        differenceBetweenUnion.removeAll(union);
        System.out.println("Union difference with default times: " + differenceBetweenUnion);

        // <----------------------------------------------------------------------------------->

        // <-- SWITCH STATEMENT OF NUMBOOKINGS COMBINED WITH HASHSET ABOVE TO DETERMINE POSSIBLE TIMES -->

        switch(numBookings) { //START OF SWITCH
            case 0: //CASE 0:

                // <-- ADDS TIMES TO LIST -->
                possibleTimes.add("12:15");
                possibleTimes.add("12:30");
                possibleTimes.add("12:45");
                // <------------------------>

                break;
            case 1:
                if(setAllTimes.contains(1215) && setAllTimes.contains(1260)) {//START OF IF
                    possibleTimes.add("FULL");
                    break;
                } else if (setAllTimes.contains(1215) && setAllTimes.contains(1245)) { //START OF ELSE IF
                    possibleTimes.add("12:45");
                    break;
                }//END OF IF

                ArrayList<String> temp = new ArrayList<>(); //CREATES A TEMPORARY LIST OF TYPE STRING
                Set<Integer> sortedSet = new TreeSet<Integer>(differenceBetweenUnion); //CREATES TREESET OF TYPE INTEGER OF HASHSET DIFFERENCEBETWEENUNION
                for(int value : sortedSet) { //START OF FOR LOOP || ITERATES THROUGH TREESET
                    String strNum = Integer.toString(value); //SETS TEMP VARIABLE OF TREESET
                    strNum = strNum.substring(0,2) + ":" + strNum.substring(2,strNum.length()); //ADJUSTS STRNUM
                    temp.add(strNum); //ADDS SORTEDLIST ELEMENTS TO LIST
                } //END OF FOR LOOP
                return temp; //RETURNS SORTED LIST

            case 2:

                for(int i = 0; i < durations.size(); i++) {//START OF FOR LOOP
                    if (durations.get(i) == 30) { //START OF IF
                        possibleTimes.add("FULL");
                        return possibleTimes;
                    }// END OF IF
                }// ENDS OF FOR LOOP

                Set<Integer> sortedSet2 = new TreeSet<Integer>(differenceBetweenUnion); //CREATES TREESET OF TYPE INTEGER OF HASHSET DIFFERENCEBETWEEN UNION
                ArrayList<String> temp2 = new ArrayList<>(); //CREATES TEMPORARY LIST OF TYPE STRING
                for(int value : sortedSet2) { // START OF FOR LOOP || ITERATES THROUGH TREESET
                    String strNum = Integer.toString(value); //CREATES TEMPORARY VARIABLE FROM TREESET
                    strNum = strNum.substring(0,2) + ":" + strNum.substring(2,strNum.length()); //ADJUSTS THE ELEMENT
                    temp2.add(strNum); //ADDS ELEMENT TO LIST
                } //END OF FOR LOOP
                return temp2; //RETURNS LIST
            case 3:
                possibleTimes.add("FULL");

        }//END OF SWITCH

        // <---------------------------------------------------------------------------------------------------------->

        return possibleTimes; //RETURNS LIST OF AVAILABLE TIMES TO BOOK
    } //END OF FUNCTION

    public void goHome(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("main.fxml")); //links .java file to .fxml file
        Stage primaryStage = new Stage();
        primaryStage.setTitle("Management booking system");
        primaryStage.resizableProperty().setValue(false);
        primaryStage.setScene(new Scene(root, 600, 490)); //sets dimensions of the window
        primaryStage.show();
        ((Node)(event.getSource())).getScene().getWindow().hide();
        System.out.println("you went home");
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

    public void selectRoomImage(ActionEvent event) {
        try {
            FileChooser fileChooser = new FileChooser();
            FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("IMAGES *.png", "*.png", "*.jpeg", "*.jpg");
            fileChooser.getExtensionFilters().add(extensionFilter);



            File file = fileChooser.showOpenDialog(null);
            //Stage stage = (Stage) addRoomAnchorpane.getScene().getWindow();

            if (file != null) {
                path = file.getAbsolutePath();
                    System.out.println("Path: " + file.getAbsolutePath());
                    buttonImage.setText("IMAGE ADDED");
                    buttonImage.setStyle("-fx-text-fill: green;");
            }

        }catch (Exception ex) {
            ex.printStackTrace();
            buttonImage.setText("FAILED TO ADD IMAGE");
            buttonImage.setStyle("-fx-text-fill: red;");
        }
    }



}
