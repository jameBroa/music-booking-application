package IA;



import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXTextArea;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import javax.mail.MessagingException;
import javax.swing.*;
import java.io.*;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.*;

public class ControllerMain implements Initializable {

    @FXML private Button buttonLogin, buttonMonday, buttonTuesday, buttonWednesday, buttonThursday, buttonFriday, buttonEnterProgram, buttonRegisterUser, buttonHome, buttonReport, buttonAdmin, loggedInUserButton;
    @FXML private Label adminTitle, labelMonRoomAvail, labelMonBookAvail, labelTueRoomAvail, labelTueBookAvail, labelWedRoomAvail, labelWedBookAvail, labelThuRoomAvail, labelThuBookAvail, labelFriRoomAvail, labelFriBookAvail, labelEnterStudentID, loggedInUserID, reportLabel;
    @FXML protected TextField TFUsername, TFPassword, TFEnterID;
    @FXML private Separator seperatorLogin;
    @FXML private HBox topBanner;
    @FXML private Pane paneSideBar, paneMainFunctions;
    @FXML private JFXDrawer drawerUserOptions;
    @FXML private JFXButton submitReportButton, exportTableButton, monButton, monRooms, monBookings, tueButton, tueRooms, tueBookings, wedButton, wedRooms, wedBookings, thurButton, thurRooms, thurBookings, friButton, friRooms, friBookings;
    @FXML private TableView<BookingModel> tableUserBookings;
    @FXML TableColumn<BookingModel, String> col_bookingTime, col_bookingDate, col_bookingPurpose, col_bookingComment;
    @FXML TableColumn <BookingModel, Integer> col_roomID, col_userID, col_bookingID, col_bookingGuests;
    @FXML JFXTextArea reportCommentTextArea;
    @FXML AnchorPane anchorPaneMainFunctions, monAnchor, tueAnchor, wedAnchor, thurAnchor, friAnchor, monButtonPane, monButtonRoomsPane, monButtonBookingsPane, tueButtonPane, tueButtonRoomsPane, tueButtonBookingsPane, wedButtonPane, wedButtonRoomsPane, wedButtonBookingsPane, thurButtonPane, thurButtonRoomsPane, thurButtonBookingsPane, friButtonPane, friButtonRoomsPane, friButtonBookingsPane;
    private String dateOfBooking;
    private List<String> dates = new ArrayList<String>();
    private Booking bookingToHappen = new Booking();
    private User loggedInUser = new User();
    private String stringDays[] = new String[5];
    private int intDays[] = new int[5];
    private int startingTime = 1215;
    private int clickCounter = 0;
    private String monColour ="#45cf9b";
    private String tueColour ="#45cf9b";
    private String wedColour ="#45cf9b";
    private String thurColour ="#45cf9b";
    private String friColour = "#45cf9b";
    private boolean IPFailure = false;

    @FXML public Button buttonConfigureIP;
    private Alert alert = new Alert(Alert.AlertType.ERROR);

    public void adminLogin(ActionEvent event) { //function that calls when you login via admin page
        Connector.setAdminDetails();
        String username = Connector.getAdminUsername();
        String password = Connector.getAdminPassword();
        String usernameInput = TFUsername.getText();
        String passwordInput = TFPassword.getText();

        if(username.equals(usernameInput) && password.equals(passwordInput)) { //if the password matches the one in the database
            System.out.println("you did it");
            openAdminPage(event);
        } else {
            alert.setTitle("ERROR");
            alert.setHeaderText("There was a problem logging in");
            alert.setContentText("Your login details are incorrect, try again");
            alert.showAndWait();
            TFUsername.setText("");
            TFPassword.setText("");
            System.out.println("try again");
        }
    }



    public void openAdminPage(ActionEvent event) { //this functions opens another window which
        try {
           // System.out.println("this is the user " +loggedInUser.getFirstName());

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("adminPage.fxml"));
            Parent root = loader.load();
            ControllerAdminPage controller = loader.getController();

            Stage primaryStage = new Stage();
            primaryStage.setTitle("Management booking system");
            primaryStage.resizableProperty().setValue(false);
            primaryStage.setScene(new Scene(root, 600, 400)); //sets dimensions of the window
            primaryStage.show();
            ((Node)(event.getSource())).getScene().getWindow().hide();
            System.out.println("you went to add booking ");

        }catch (Exception ex){
            ex.printStackTrace();
        }


    }

    private BookingModel bookingModel;

    public void getSelectedBooking(){
        BookingModel booking = tableUserBookings.getSelectionModel().getSelectedItem();
        if(booking==null) {
            System.out.println("nothing selected");
        } else {
            bookingModel = booking;
            String name = booking.getBookingPurpose();
            int id = booking.getBookingID();
            System.out.println("The Booking ID is " + id);
        }
    }

    public void goReport(ActionEvent event) throws Exception{ //when you click the report button

        tableUserBookings.setVisible(true);
        submitReportButton.setVisible(true);
        reportCommentTextArea.setVisible(true);
        reportLabel.setVisible(true);
        exportTableButton.setVisible(true);

        // <-- ADJUST GUI ELEMENTS -->
        buttonReport.setStyle("-fx-background-color:#457B9D;-fx-border-radius:10; -fx-background-radius:10; -fx-border-color:#6eb1db; -fx-border-width:3;");
        buttonHome.setStyle("-fx-background-color:#457B9D;");
        buttonAdmin.setStyle("-fx-background-color:#457B9D");

        monAnchor.setVisible(false);
        tueAnchor.setVisible(false);
        wedAnchor.setVisible(false);
        thurAnchor.setVisible(false);
        friAnchor.setVisible(false);

//        buttonMonday.setVisible(false);
//        buttonTuesday.setVisible(false);
//        buttonWednesday.setVisible(false);
//        buttonThursday.setVisible(false);
//        buttonFriday.setVisible(false);
//        labelMonBookAvail.setVisible(false);
//        labelMonRoomAvail.setVisible(false);
//        labelTueBookAvail.setVisible(false);
//        labelTueRoomAvail.setVisible(false);
//        labelWedBookAvail.setVisible(false);
//        labelWedRoomAvail.setVisible(false);
//        labelThuBookAvail.setVisible(false);
//        labelThuRoomAvail.setVisible(false);
//        labelFriBookAvail.setVisible(false);
//        labelFriRoomAvail.setVisible(false);


        adminTitle.setVisible(false);
        buttonLogin.setVisible(false);
        TFUsername.setVisible(false);
        TFPassword.setVisible(false);
        buttonLogin.setVisible(false);

        // <------------------------->
    }

    public void goAdmin(ActionEvent event) throws Exception{ //when you click the admin button
        System.out.println("You went admin");

        buttonAdmin.setStyle("-fx-background-color:#457B9D;-fx-border-radius:10; -fx-background-radius:10; -fx-border-color:#6eb1db; -fx-border-width:3;");
        buttonHome.setStyle("-fx-background-color:#457B9D;");
        buttonReport.setStyle("-fx-background-color:#457B9D");

        tableUserBookings.setVisible(false);
        submitReportButton.setVisible(false);
        reportCommentTextArea.setVisible(false);
        exportTableButton.setVisible(false);
        reportLabel.setVisible(false);

        monAnchor.setVisible(false);
        tueAnchor.setVisible(false);
        wedAnchor.setVisible(false);
        thurAnchor.setVisible(false);
        friAnchor.setVisible(false);



//        buttonMonday.setVisible(false);
//        buttonTuesday.setVisible(false);
//        buttonWednesday.setVisible(false);
//        buttonThursday.setVisible(false);
//        buttonFriday.setVisible(false);
//        labelMonBookAvail.setVisible(false);
//        labelMonRoomAvail.setVisible(false);
//        labelTueBookAvail.setVisible(false);
//        labelTueRoomAvail.setVisible(false);
//        labelWedBookAvail.setVisible(false);
//        labelWedRoomAvail.setVisible(false);
//        labelThuBookAvail.setVisible(false);
//        labelThuRoomAvail.setVisible(false);
//        labelFriBookAvail.setVisible(false);
//        labelFriRoomAvail.setVisible(false);

        adminTitle.setVisible(true);
        buttonLogin.setVisible(true);
        TFUsername.setVisible(true);
        TFPassword.setVisible(true);
        buttonLogin.setVisible(true);

    }

    //todo make it so the booking info is saved in a textfile so it doesn't connect to the database all the time


    public void goBooking(ActionEvent event) throws IOException {
        System.out.println("you went to booking");



           // <-- CHANGING GUI ELEMENTS -->

            buttonHome.setStyle("-fx-background-color:#457B9D;-fx-border-radius:10; -fx-background-radius:10; -fx-border-color:#6eb1db; -fx-border-width:3;");
            buttonAdmin.setStyle("-fx-background-color:#457B9D;");
            buttonReport.setStyle("-fx-background-color:#457B9D");


        monAnchor.setVisible(true);
        tueAnchor.setVisible(true);
        wedAnchor.setVisible(true);
        thurAnchor.setVisible(true);
        friAnchor.setVisible(true);


        tableUserBookings.setVisible(false);
        submitReportButton.setVisible(false);
        reportCommentTextArea.setVisible(false);
        reportLabel.setVisible(false);
        exportTableButton.setVisible(false);

       adminTitle.setVisible(false);
        buttonLogin.setVisible(false);
        TFUsername.setVisible(false);
        TFPassword.setVisible(false);
        buttonLogin.setVisible(false);

        // <----------------------------------------------------------------------------->


        intDays = setCalendarDates();
        stringDays = setCalendarEndings(intDays);


        monButton.setText("Monday " + stringDays[0]);
        tueButton.setText("Tuesday " + stringDays[1]);
        wedButton.setText("Wednesday " + stringDays[2]);
       thurButton.setText("Thursday " + stringDays[3]);
        friButton.setText("Friday " + stringDays[4]);

    }

    public void sendReport(ActionEvent event) throws MessagingException { //FUNCTION TO SEND REPORT VIA EMAIL TO MUSIC DEPARTMENT
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        try {
            // <-- FETCHING INFORMATION ABOUT THE USER, ROOM AND BOOKING CONCERNED WITH REPORT -->
            User tempUser = new User();
            System.out.println(bookingModel.getUserID());
            int bookingID = bookingModel.getBookingID();
            int roomID = bookingModel.getRoomID();
            Rooms tempRoom = new Rooms();
            String roomLocation = tempRoom.getRoomLocation(roomID);
            // <---------------------------------------------------------------------------------->

            // <-- CREATING MESSAGE TO BE DISPLAYED IN ALERT MESSAGE -->
            String sender = "Report from: " + loggedInUser.getFirstName() + " with ID " + tempUser.getLoggedInUserID();
            JavaMail.sendMail("jabr21@patana.ac.th", "BOOKING REPORT (ID: " + bookingID + ") CONCERNING ROOM " + roomLocation + " (ID: " + roomID + "). \nMESSAGE IS BELOW: \n" + reportCommentTextArea.getText().toString(), sender);
            alert.setTitle("NOTICE");
            alert.setHeaderText("Please take note:");
            alert.setContentText("Your report has been sent");
            // <------------------------------------------------------->

            alert.showAndWait();
            reportCommentTextArea.setText("");
        } catch(Exception ex) { //EXCEPTION IF ERROR OCCURS
            alert.setTitle("Warning");
            alert.setHeaderText("Please take note:");
            alert.setContentText("Please select a booking before submitting a report");
            alert.showAndWait();
        }
    }

    private void updateAvailableBookings() { //this function gets all the rooms and sets it the user knows how many bookings are available

        Rooms room = new Rooms();

        int numRooms= room.getNumRooms();
        int maxNumBookingsAvailable = numRooms * 3;

        Booking temp = new Booking();
        ArrayList<Booking> tempBooking = new ArrayList<Booking>();
        tempBooking = temp.getAllBooking();

        Calendar calendar = Calendar.getInstance();
        if(tempBooking.size() != 0) {

            // <-- MONDAY -->

            calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
            String fullDate = calendar.getTime().toString();
            fullDate = fullDate.substring(0, 10)+ " " + fullDate.substring(24);

            int numFullMon = findNumFullRooms(fullDate);
            int numRoomsMon = numRooms - numFullMon;
            int numAvailbleBookings = findNumAvailableBookings(fullDate);

//            labelMonRoomAvail.setText("Rooms available: " + numRoomsMon);
//            labelMonBookAvail.setText("Bookings available: " + numAvailbleBookings);

            monRooms.setText("ROOMS:\n" + numRoomsMon);
            monBookings.setText("BOOKINGS:\n" + numAvailbleBookings);


            // <-- SETS COLOUR AS GREEN -->
            monButtonPane.setStyle("-fx-background-color: #45cf9b");
            monButtonRoomsPane.setStyle("-fx-background-color: #45cf9b");
            monButtonBookingsPane.setStyle("-fx-background-color: #45cf9b");
            // <--------------------------->

            // <-- SETS COLOUR AS ORANGE -->
            if(numRoomsMon <= numRooms/2 || numAvailbleBookings <= (maxNumBookingsAvailable*3)/4) {//START IF
                monButtonPane.setStyle("-fx-background-color: #ef8a46");
                monButtonRoomsPane.setStyle("-fx-background-color: #ef8a46");
                monButtonBookingsPane.setStyle("-fx-background-color: #ef8a46");
                monColour="#ef8a46";
            }//END IF
            // <--------------------------->

            // <-- SETS COLOUR AS RED -->
            if(numRoomsMon <= numRooms/4 || numAvailbleBookings <= (maxNumBookingsAvailable)/4) {//START IF
                monButtonPane.setStyle("-fx-background-color: #de6666");
                monButtonRoomsPane.setStyle("-fx-background-color: #de6666");
                monButtonBookingsPane.setStyle("-fx-background-color: #de6666");
                monColour="#de6666";
            }//ENDIF
            // <--------------------------->

            // <-- SETS COLOUR AS BLACK -->
            if(numRoomsMon == 0) {//START IF
                monButtonPane.setStyle("-fx-background-color: BLACK");
                monButtonRoomsPane.setStyle("-fx-background-color: BLACK");
                monButtonBookingsPane.setStyle("-fx-background-color: BLACK");
                monColour="BLACK";
            }//END IF
            // <------------>

            // <-- TUESDAY -->

            calendar.set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY);
            fullDate = calendar.getTime().toString();
            fullDate = fullDate.substring(0, 10)+ " " + fullDate.substring(24);

             numFullMon = findNumFullRooms(fullDate);
             numRoomsMon = numRooms - numFullMon;
             numAvailbleBookings = findNumAvailableBookings(fullDate);


            tueRooms.setText("ROOMS:\n" + numRoomsMon);
            tueBookings.setText("BOOKINGS:\n" + numAvailbleBookings);

            System.out.println("this is the numAvailableBookings: " + numAvailbleBookings);

            tueButtonPane.setStyle("-fx-background-color: #45cf9b");
            tueButtonRoomsPane.setStyle("-fx-background-color: #45cf9b");
            tueButtonBookingsPane.setStyle("-fx-background-color: #45cf9b");

            if(numRoomsMon <= numRooms/2 || numAvailbleBookings <= (maxNumBookingsAvailable*0.75)) {
                tueButtonPane.setStyle("-fx-background-color: #ef8a46");
                tueButtonRoomsPane.setStyle("-fx-background-color: #ef8a46");
                tueButtonBookingsPane.setStyle("-fx-background-color: #ef8a46");
                tueColour="#ef8a46";
            }

            if(numRoomsMon <= numRooms/4 || numAvailbleBookings <= (maxNumBookingsAvailable)/4) {
                tueButtonPane.setStyle("-fx-background-color: #de6666");
                tueButtonRoomsPane.setStyle("-fx-background-color: #de6666");
                tueButtonBookingsPane.setStyle("-fx-background-color: #de6666");
                tueColour="#de6666";
            }

            if(numRoomsMon == 0) {
                tueButtonPane.setStyle("-fx-background-color: BLACK");
                tueButtonRoomsPane.setStyle("-fx-background-color: BLACK");
                tueButtonBookingsPane.setStyle("-fx-background-color: BLACK");
                tueColour="BLACK";
            }

            // <------------->

            // <-- WEDNESDAY -->

            calendar.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY);
            fullDate = calendar.getTime().toString();
            fullDate = fullDate.substring(0, 10)+ " " + fullDate.substring(24);

            numFullMon = findNumFullRooms(fullDate);
            numRoomsMon = numRooms - numFullMon;
            numAvailbleBookings = findNumAvailableBookings(fullDate);

            wedRooms.setText("ROOMS:\n" + numRoomsMon);
            wedBookings.setText("BOOKINGS:\n" + numAvailbleBookings);

            wedButtonPane.setStyle("-fx-background-color: #45cf9b");
            wedButtonRoomsPane.setStyle("-fx-background-color: #45cf9b");
            wedButtonBookingsPane.setStyle("-fx-background-color: #45cf9b");

            if(numRoomsMon <= numRooms/2 || numAvailbleBookings <= (maxNumBookingsAvailable*3)/4) {
                wedButtonPane.setStyle("-fx-background-color: #ef8a46");
                wedButtonRoomsPane.setStyle("-fx-background-color: #ef8a46");
                wedButtonBookingsPane.setStyle("-fx-background-color: #ef8a46");
                wedColour="#ef8a46";
            }

            if(numRoomsMon <= numRooms/4 || numAvailbleBookings <= (maxNumBookingsAvailable)/4) {
                wedButtonPane.setStyle("-fx-background-color: #de6666");
                wedButtonRoomsPane.setStyle("-fx-background-color: #de6666");
                wedButtonBookingsPane.setStyle("-fx-background-color: #de6666");
                wedColour="#de6666";
            }

            if(numRoomsMon == 0) {
                wedButtonPane.setStyle("-fx-background-color: BLACK");
                wedButtonRoomsPane.setStyle("-fx-background-color: BLACK");
                wedButtonBookingsPane.setStyle("-fx-background-color: BLACK");
                wedColour="BLACK";
            }

            // <-------------->

            // <-- THURSDAY -->

            calendar.set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY);
            fullDate = calendar.getTime().toString();
            fullDate = fullDate.substring(0, 10)+ " " + fullDate.substring(24);

            numFullMon = findNumFullRooms(fullDate);
            numRoomsMon = numRooms - numFullMon;
            numAvailbleBookings = findNumAvailableBookings(fullDate);

//            labelThuRoomAvail.setText("Rooms available: " + numRoomsMon);
//            labelThuBookAvail.setText("Bookings available: " + numAvailbleBookings);

            thurRooms.setText("ROOMS:\n" + numRoomsMon);
            thurBookings.setText("BOOKINGS:\n" + numAvailbleBookings);

            thurButtonPane.setStyle("-fx-background-color: #45cf9b");
            thurButtonRoomsPane.setStyle("-fx-background-color: #45cf9b");
            thurButtonBookingsPane.setStyle("-fx-background-color: #45cf9b");

            if(numRoomsMon <= numRooms/2 || numAvailbleBookings <= (maxNumBookingsAvailable*3)/4) {
                thurButtonPane.setStyle("-fx-background-color: #ef8a46");
                thurButtonRoomsPane.setStyle("-fx-background-color: #ef8a46");
                thurButtonBookingsPane.setStyle("-fx-background-color: #ef8a46");
                thurColour="#ef8a46";
            }

            if(numRoomsMon <= numRooms/4 || numAvailbleBookings <= (maxNumBookingsAvailable)/4) {
                thurButtonPane.setStyle("-fx-background-color: #de6666");
                thurButtonRoomsPane.setStyle("-fx-background-color: #de6666");
                thurButtonBookingsPane.setStyle("-fx-background-color: #de6666");
                thurColour="#de6666";
            }

            if(numRoomsMon == 0) {
                thurButtonPane.setStyle("-fx-background-color: BLACK");
                thurButtonRoomsPane.setStyle("-fx-background-color: BLACK");
                thurButtonBookingsPane.setStyle("-fx-background-color: BLACK");
                thurColour="BLACK";
            }

            // <------------->

            // <-- FRIDAY -->

            calendar.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
            fullDate = calendar.getTime().toString();
            fullDate = fullDate.substring(0, 10)+ " " + fullDate.substring(24);

            numFullMon = findNumFullRooms(fullDate);
            numRoomsMon = numRooms - numFullMon;
            numAvailbleBookings = findNumAvailableBookings(fullDate);

//            labelFriRoomAvail.setText("Rooms available: " + numRoomsMon);
//            labelFriBookAvail.setText("Bookings available: " + numAvailbleBookings);

            System.out.println("This is the number of rooms: " + numRooms);

            friButtonPane.setStyle("-fx-background-color: #45cf9b");
            friButtonRoomsPane.setStyle("-fx-background-color: #45cf9b");
            friButtonBookingsPane.setStyle("-fx-background-color: #45cf9b");

            friRooms.setText("ROOMS:\n" + numRoomsMon);
            friBookings.setText("BOOKINGS:\n" + numAvailbleBookings);

            if(numRoomsMon <= (numRooms/2) || numAvailbleBookings <= (maxNumBookingsAvailable*3)/4) {
                friButtonPane.setStyle("-fx-background-color: #ef8a46");
                friButtonRoomsPane.setStyle("-fx-background-color: #ef8a46");
                friButtonBookingsPane.setStyle("-fx-background-color: #ef8a46");
                friColour="#ef8a46";
            }

            if(numRoomsMon <= (numRooms/4) || numAvailbleBookings <= (maxNumBookingsAvailable)/4) {
                friButtonPane.setStyle("-fx-background-color: #de6666");
                friButtonRoomsPane.setStyle("-fx-background-color: #de6666");
                friButtonBookingsPane.setStyle("-fx-background-color: #de6666");
                friColour="#de6666";
            }

            if(numRoomsMon == 0) {
                friButtonPane.setStyle("-fx-background-color: BLACK");
                friButtonRoomsPane.setStyle("-fx-background-color: BLACK");
                friButtonBookingsPane.setStyle("-fx-background-color: BLACK");
                friColour="black";
            }

            // <------------->

        } else {
            monRooms.setText("ROOMS:\n" + numRooms);
            monBookings.setText("BOOKINGS:\n" + numRooms*3);
            monButtonPane.setStyle("-fx-background-color: #45cf9b");
            monButtonRoomsPane.setStyle("-fx-background-color: #45cf9b");
            monButtonBookingsPane.setStyle("-fx-background-color: #45cf9b");

            tueRooms.setText("ROOMS:\n" + numRooms);
            tueBookings.setText("BOOKINGS:\n" + numRooms*3);
            tueButtonPane.setStyle("-fx-background-color: #45cf9b");
            tueButtonRoomsPane.setStyle("-fx-background-color: #45cf9b");
            tueButtonBookingsPane.setStyle("-fx-background-color: #45cf9b");

            wedRooms.setText("ROOMS:\n" + numRooms);
            wedBookings.setText("BOOKINGS:\n" + numRooms*3);
            wedButtonPane.setStyle("-fx-background-color: #45cf9b");
            wedButtonRoomsPane.setStyle("-fx-background-color: #45cf9b");
            wedButtonBookingsPane.setStyle("-fx-background-color: #45cf9b");

            thurRooms.setText("ROOMS:\n" + numRooms);
            thurBookings.setText("BOOKINGS:\n" + numRooms*3);
            thurButtonPane.setStyle("-fx-background-color: #45cf9b");
            thurButtonRoomsPane.setStyle("-fx-background-color: #45cf9b");
            thurButtonBookingsPane.setStyle("-fx-background-color: #45cf9b");

            friRooms.setText("ROOMS:\n" + numRooms);
            friBookings.setText("BOOKINGS:\n" + numRooms*3);
            friButtonPane.setStyle("-fx-background-color: #45cf9b");
            friButtonRoomsPane.setStyle("-fx-background-color: #45cf9b");
            friButtonBookingsPane.setStyle("-fx-background-color: #45cf9b");

        }


    }

    public void setMonHover() {
        monButton.setStyle("-fx-background-color: WHITE; -fx-background-radius:10; -fx-border-radius:8; -fx-border-color:" + monColour + ";");
    }

    public void removeMonHover() {
        monButton.setStyle("-fx-background-color: WHITE; -fx-background-radius:10px;");
    }

    public void setTueHover() {
        tueButton.setStyle("-fx-background-color: WHITE; -fx-background-radius:10; -fx-border-radius:8; -fx-border-color:" + tueColour + ";");
    }

    public void removeTueHover() {
        tueButton.setStyle("-fx-background-color: WHITE;");
    }

    public void setWedHover() {
        wedButton.setStyle("-fx-background-color: WHITE; -fx-background-radius:10; -fx-border-radius:8; -fx-border-color:" + wedColour + ";");
    }

    public void removeWedHover() {
        wedButton.setStyle("-fx-background-color: WHITE;");
    }

    public void setThurHover() {
        thurButton.setStyle("-fx-background-color: WHITE; -fx-background-radius:10; -fx-border-radius:8; -fx-border-color:" + thurColour + ";");
    }

    public void removeThurHover() {
        thurButton.setStyle("-fx-background-color: WHITE;");
    }

        public void setFriHover() {
        friButton.setStyle("-fx-background-color: WHITE; -fx-background-radius:10; -fx-border-radius:8; -fx-border-color:" + friColour + ";");
    }

    public void removeFriHover() {
        friButton.setStyle("-fx-background-color: WHITE;");
    }

    public int findNumAvailableBookings(String day) {
        int availableBookings = 0;

        Rooms room = new Rooms();
        ArrayList<Rooms> allRooms = room.getAllRooms();
        int numFull = 0;

        for(int i = 0; i <allRooms.size(); i++) {
            int durationSum = 0;

            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection("jdbc:mysql://" + User.getIPAddress() + ":3306/CSIA", "myuser", "testpassword");
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM `Booking` WHERE `bookingDate`=\"" + day + "\" AND `roomID`=\"" + allRooms.get(i).getRoomID() + "\"");

                while (rs.next()) {
                    String temp = rs.getString("bookingTime");
                    temp = temp.substring(2, 4);
                    durationSum += Integer.parseInt(temp);
                }

            } catch (Exception ex) {
                ex.printStackTrace();
            }

            durationSum = durationSum/15;

            switch(durationSum) {
                case 0:
                    availableBookings +=3;
                    break;

                case 1:
                    availableBookings += 2;
                    break;
                case 2:
                    availableBookings +=1;
                    break;
                case 3:
                    availableBookings += 0;
                    break;

            }

        }

        return availableBookings;
    }

    public int findNumFullRooms(String day) {

        Rooms room = new Rooms();
        ArrayList<Rooms> allRooms = room.getAllRooms();
        int numFull = 0;

        for(int i = 0; i <allRooms.size(); i++) {
            int durationSum = 0;

            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection("jdbc:mysql://"+ User.getIPAddress() + ":3306/CSIA", "myuser", "testpassword");
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM `Booking` WHERE `bookingDate`=\"" + day + "\" AND `roomID`=\"" + allRooms.get(i).getRoomID() + "\"");

                while (rs.next()) {
                    String temp = rs.getString("bookingTime");
                    temp = temp.substring(2, 4);
                    durationSum += Integer.parseInt(temp);
                }

            } catch (Exception ex) {
                ex.printStackTrace();
            }

            if(durationSum == 45) {
                numFull++;
            }
        }
        System.out.println("The number of Rooms full is " + numFull);
        return numFull;

    }

    public void goBookingForm(ActionEvent event) throws IOException {  //USE THIS TO FIX THE CLASS ISSUE FOR THE USER INPUT FORM
        dateOfBooking = getButtonDate(event);
        System.out.println("this is the current user " + loggedInUser.getFirstName());

        // decides on the new fxml file

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("addBookingInputForm.fxml"));
        Parent root = loader.load();

        // ----

        ControllerInputForms controller = loader.getController(); // sets up a loader so i can access fxml files from this class

        //gets the date of the booking to be put into the choicebox in javafx

        setDateList(); //puts the array of dates (just numbers (i.e. 15) into a list)
        addMonthToDateList(); //adds the month to the date list
        try {
            int index = getIndexOfDate(dateOfBooking);
            controller.setDateChoiceBox(dates, index);
            Rooms rooms = new Rooms();
            ArrayList<String> allRoomsString = (ArrayList<String>) rooms.getRoomNames();
            controller.setCurrentUser(loggedInUser);
            controller.setRoomChoiceBox(allRoomsString);
            controller.bookingFillInUserID();
            controller.updateBookingText();
        } catch(Exception ex) {
            System.out.println("Error in opening booking form");
        }

        // shows the booking window

        Stage primaryStage = new Stage();
        primaryStage.setTitle("Management booking system");
        primaryStage.resizableProperty().setValue(false);
        primaryStage.setScene(new Scene(root, 450, 450)); //sets dimensions of the window
        primaryStage.show();
        ((Node)(event.getSource())).getScene().getWindow().hide();
        System.out.println("you went to add booking ");

        // -------------

    }

    private void addMonthToDateList() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        String fullDate = calendar.getTime().toString();
        dates.set(0, calendar.getTime().toString().substring(0,10)+ " " + calendar.getTime().toString().substring(24));
        System.out.println("this is the date: " + fullDate.substring(0,10) + " " + fullDate.substring(24));
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY);
        dates.set(1, calendar.getTime().toString().substring(0,10)+ " " + calendar.getTime().toString().substring(24));
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY);
        dates.set(2, calendar.getTime().toString().substring(0,10)+ " " + calendar.getTime().toString().substring(24));
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY);
        dates.set(3, calendar.getTime().toString().substring(0,10)+ " " + calendar.getTime().toString().substring(24));
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
        dates.set(4, calendar.getTime().toString().substring(0,10)+ " " + calendar.getTime().toString().substring(24));
    }

    private int getIndexOfDate(String day) {
        int[] temp = new int[5];                                                    //maybe make this variable global
        temp = setCalendarDates();

        for(int i = 0; i < temp.length; i++) {
            String stringTemp = Integer.toString(temp[i]);
            if(day.contains(stringTemp)) {
                System.out.println(i);
                return i;
            }
        }
        return -1;
    }

    private void setDateList() {
        int[] temp = new int[5];
        temp = setCalendarDates();
        String[] temp2 = setCalendarEndings(temp);

        dates = Arrays.asList(temp2);
    }

    private String getButtonDate(ActionEvent event) {
        String actionEvent = event.getTarget().toString();
        int beginningIndex = actionEvent.indexOf("'");
        int lastIndex= actionEvent.lastIndexOf("'");
        beginningIndex++;
        actionEvent = actionEvent.substring(beginningIndex, lastIndex);
        return actionEvent;
    }

    private int[] setCalendarDates() {
        int[] dates = new int[5];
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        dates[0] = Integer.parseInt(calendar.getTime().toString().substring(8,10));
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY);
        dates[1] = Integer.parseInt(calendar.getTime().toString().substring(8,10));
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY);
        dates[2] = Integer.parseInt(calendar.getTime().toString().substring(8,10));
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY);
        dates[3] = Integer.parseInt(calendar.getTime().toString().substring(8,10));
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
        dates[4] = Integer.parseInt(calendar.getTime().toString().substring(8,10));
        return dates;
    }

    private String[] setCalendarEndings(int[] dates) {

        String[] day = new String[5];

        for(int i = 0; i < dates.length; i++) {

            String temp = Integer.toString(dates[i]);
            temp = temp.substring(temp.length()-1, temp.length());
            System.out.println(temp);
            int endingNum = Integer.parseInt(temp);
            switch(endingNum) {
                case 1:
                    day[i] = dates[i] + "st";
                    break;
                case 2:
                    day[i] = dates[i] + "nd";
                    break;
                case 3:
                    day[i] = dates[i] + "rd";
                    break;
                case 4:
                    day[i] = dates[i] + "th";
                    break;
                case 5:
                    day[i] = dates[i] + "th";
                    break;
                case 6:
                    day[i] = dates[i] + "th";
                    break;
                case 7:
                    day[i] = dates[i] + "th";
                    break;
                case 8:
                    day[i] = dates[i] + "th";
                    break;
                case 9:
                    day[i] = dates[i] + "th";
                    break;
                case 0:
                    day[i] = dates[i] + "th";
                    break;
            }
            if (dates[i] == 11) {
                day[i] = dates[i] + "th";
            }

        }
        return day;

    }

    public void goStraightAdmin(User user, ActionEvent event) throws Exception {
        loggedInUser = user;
        System.out.println("this is the frist name: " + user.getFirstName());
        buttonEnterProgram.setVisible(false);
        TFEnterID.setVisible(false);
        buttonRegisterUser.setVisible(false);
        topBanner.setVisible(false);
        seperatorLogin.setVisible(false);

        paneMainFunctions.setVisible(true);
        paneSideBar.setVisible(true);
        buttonHome.setVisible(true);
        buttonReport.setVisible(true);
        buttonAdmin.setVisible(true);
        goAdmin(event);
        loggedInUserButton.setVisible(true);
        loggedInUserButton.setText(user.getFirstName().substring(0,1) + "." + user.getLastName().substring(0,1));
    }


    public void enterProgram(ActionEvent event) throws Exception {

        //maybe surround with try catch if incorrect data type is inputted

        System.out.println("test");
        System.out.println(TFEnterID.getText().toString());

        if(TFEnterID.getText().toString().isEmpty() == true) {//START OF IF
            // <--- CREATE POPUP PROMPT TO ENTER A VALID ID OR SIGNUP FOR ONE --->
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Warning");
            alert.setHeaderText("Please take note:");
            alert.setContentText("Please enter a valid user ID, if you don't have an ID, please register for one...");
            alert.showAndWait();
            // <----------------------------------------------------------------->
        } else if (TFEnterID.getText().toString().equals("CONFIGURE") && IPFailure == true) {

            goConfigureIP(event);

        }else if(TFEnterID.getText().toString().equals("ADMIN")) {

            String fileName = "loggedInUser.txt"; // SETS STRING OF TEXTFILE LOCATED WITHIN PACKAGE

            try
            { //OPEN TRY
                FileWriter writer = new FileWriter(fileName, false ); //creating a variable called 'writer' of the type FileWriter. False replaces file, true adds onto the file
                writer.write("");  //SETS TEXT INSIDE FILE EQUIVALENT TO NOTHING
                writer.close(); //CLOSE ACCESS TO FILE
            } catch ( IOException iox ) { //try catch statement. Throws an exception.
                System.out.println("Problem writing " + fileName);
                iox.printStackTrace();
            } //END TRY

            buttonEnterProgram.setVisible(false);
            TFEnterID.setVisible(false);
            buttonRegisterUser.setVisible(false);
            topBanner.setVisible(false);
            seperatorLogin.setVisible(false);

            paneMainFunctions.setVisible(true);
            paneSideBar.setVisible(true);
            buttonHome.setVisible(true);
            buttonHome.setDisable(true);
            buttonReport.setVisible(true);
            buttonReport.setDisable(true);
            buttonAdmin.setVisible(true);

            goAdmin(event);




        } else {

            int userInputID = Integer.parseInt(TFEnterID.getText().toString()); // Sets the UserID as input from TextField
            List<Integer> temp = new ArrayList<Integer>(); // CREATES A TEMPORARY ARRAYLIST OF TYPE STRING FOR THE USERIDS
            Connector.updateList("User", temp, "userID"); //UPDATES ARRAYLIST OF ALL USERIDS BY QUERYING DATABASE
            boolean check = Connector.checkIDExist(userInputID, temp); //CHECKS IF ID INPUTTED INTO TEXTFIELD EXISTS IN THE DATABASE

            if(check) {// IF ID INPUTTED DOES EXIST

                System.out.println("this determines it " + User.getIPAddress());

                createUser(userInputID); //CREATES AN INSTANCE OF A USER WITH THE USERID INPUTTED

                // <-- UPDATES TXT FILE OF LOGGED IN USER -->

                String fileName = "loggedInUser.txt"; // SETS STRING OF TEXTFILE LOCATED WITHIN PACKAGE

                try
                { //OPEN TRY
                    FileWriter writer = new FileWriter(fileName, false ); //instance of FileWriter created
                    writer.write(Integer.toString(userInputID));  //SETS TEXT INSIDE FILE EQUIVALENT TO ID
                    writer.close(); //CLOSE ACCESS TO FILE
                    System.out.println("text file updated with " + Integer.toString(userInputID));
                } catch ( IOException iox ) { //try catch statement. Throws an exception.
                    System.out.println("Problem writing " + fileName);
                    iox.printStackTrace();
                } //END TRY

                // <----------------------------------------->

                // <-- UPDATES TABLE OF BOOKINGS FOR REPORT SECTION -->
                ObservableList<BookingModel> bookingModelObservableList = setUserBookingObservableList(); //uses a function  to set list
                tableUserBookings.setItems(bookingModelObservableList);
                // <-------------------------------------------------->

                // <--- ENABLES GUI ELEMENTS TO ALLOW HCI --->

                loggedInUserButton.setVisible(true);
                loggedInUserButton.setText(loggedInUser.getFirstName().substring(0,1) + "." + loggedInUser.getLastName().substring(0,1));
                buttonEnterProgram.setVisible(false);
                TFEnterID.setVisible(false);
                buttonRegisterUser.setVisible(false);
                topBanner.setVisible(false);
                seperatorLogin.setVisible(false);

                paneMainFunctions.setVisible(true);
                paneSideBar.setVisible(true);
                buttonHome.setVisible(true);
                buttonReport.setVisible(true);
                buttonAdmin.setVisible(true);

                // <---------------------------------------->

                goBooking(event); // GO TO THE BOOKING PAGE
                updateAvailableBookings();



            } else {//START ELSE || IF THE ID DOESN'T EXIST
                // <--- POPUP FOR USER PROMPTING REENTERING OF DATA OR SIGN UP --->
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning");
                alert.setHeaderText("Please take note:");
                alert.setContentText("We could not find your ID in the database, please try again or if you haven't registered, please sign up");
                alert.showAndWait();
                // <-------------------------------------------------------------->

            }//END OF IF
        }//END OF IF

    }//END OF FUNCTION


    public void enterProgramOutsideMain(ActionEvent event) throws Exception {

        updateAvailableBookings();
        User user = new User();
        int userInputID = user.getLoggedInUserID();
        List<Integer> temp = new ArrayList<Integer>(); // CREATES A TEMPORARY ARRAYLIST OF TYPE STRING FOR THE USERIDS
        Connector.updateList("User", temp, "userID"); //UPDATES ARRAYLIST OF ALL USERIDS BY QUERYING DATABASE
        //boolean check = Connector.checkIDExist(userInputID, temp); //CHECKS IF ID INPUTTED INTO TEXTFIELD EXISTS IN THE DATABASE
        createUser(userInputID);



        // <--- ENABLES GUI ELEMENTS TO ALLOW HCI --->

            loggedInUserButton.setVisible(true);
            loggedInUserButton.setText(loggedInUser.getFirstName().substring(0,1) + "." + loggedInUser.getLastName().substring(0,1));
            buttonEnterProgram.setVisible(false);
            TFEnterID.setVisible(false);
            buttonRegisterUser.setVisible(false);
            topBanner.setVisible(false);
            seperatorLogin.setVisible(false);

            paneMainFunctions.setVisible(true);
            paneSideBar.setVisible(true);
            buttonHome.setVisible(true);
            buttonReport.setVisible(true);
            buttonAdmin.setVisible(true);

            // <---------------------------------------->

            goAdmin(event); // GO TO THE BOOKING PAGE


    }

    public void createUser(int userID) { // MAKE THIS A FUNCTION IN THE USER CLASS

        //connect to DATABASE and then create an instance of User so we know whos booking the room etc
        loggedInUser.setUserID(userID);

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://" + User.getIPAddress() + ":3306/CSIA", "myuser", "testpassword");
            System.out.println("Successfully connected");
            System.out.println("Here are the Users in the database");
            Statement stmt = con.createStatement();

            ResultSet rs = stmt.executeQuery("SELECT `userFirstName` FROM `User` WHERE `userID`= \"" + userID + "\"");
            if(rs.first()) {
                loggedInUser.setFirstName(rs.getString(1));
            }

            rs = stmt.executeQuery("SELECT `userLastName` FROM `User` WHERE `userID`= \"" + userID + "\"");
            if(rs.first()) {
                loggedInUser.setLastName(rs.getString("userLastName"));
            }
            rs = stmt.executeQuery("SELECT `userYear` FROM `User` WHERE `userID`= \"" + userID + "\"");
            if(rs.first()) {
                loggedInUser.setUserYear((byte) rs.getInt(1));
            }
            rs = stmt.executeQuery("SELECT `userClass` FROM `User` WHERE `userID`= \"" + userID + "\"");
            if(rs.first()) {
                loggedInUser.setUserClass(rs.getString(1));
            }
            rs = stmt.executeQuery("SELECT `userSchoolID` FROM `User` WHERE `userID`= \"" + userID + "\"");
            if(rs.first()) {
                loggedInUser.setUserSchoolID((long) rs.getInt(1));
            }
            rs = stmt.executeQuery("SELECT `userComment` FROM `User` WHERE `userID`= \"" + userID + "\"");
            if(rs.first()) {
                loggedInUser.setUserComment(rs.getString(1));
            }

        }catch(ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void goSignup(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("addUserInputForm.fxml"));
        Parent root = loader.load();
        ControllerInputForms controller = loader.getController();


        ControllerInputForms temp = new ControllerInputForms();

        if(temp.year7.size() ==0) {
            controller.labelSelectYear.setVisible(false);
            controller.textFieldStudentID.setVisible(false);
            controller.textFieldFirstName.setVisible(false);
            controller.textFieldLastName.setVisible(false);
            controller.textFieldComment.setVisible(false);
            controller.labelComment.setVisible(false);
            controller.labelFirstName.setVisible(false);
            controller.labelLastName.setVisible(false);
            controller.choiceBoxYearGroup.setVisible(false);
            controller.choiceBoxClass.setVisible(false);
            controller.labelSelectClass.setVisible(false);
            controller.labelStudentID.setVisible(false);
            controller.buttonSubmit.setVisible(false);
            controller.buttonReturnWhenFail.setVisible(true);
            controller.inputFormLabel.setPrefHeight(50);
            controller.inputFormLabel.setText("NO CLASSES \nCONTACT ADMIN TO FIX");


            Stage primaryStage = new Stage();
            primaryStage.setTitle("Management booking system");
            primaryStage.resizableProperty().setValue(false);
            primaryStage.setScene(new Scene(root, 450, 200)); //sets dimensions of the window
            primaryStage.show();
            ((Node)(event.getSource())).getScene().getWindow().hide();
            System.out.println("you went to add booking ");

        } else {
            controller.choiceBoxClass.setItems(FXCollections.observableList(temp.year7));
            controller.choiceBoxClass.getSelectionModel().selectFirst();
            Stage primaryStage = new Stage();
            primaryStage.setTitle("Management booking system");
            primaryStage.resizableProperty().setValue(false);
            primaryStage.setScene(new Scene(root, 450, 450)); //sets dimensions of the window
            primaryStage.show();
            ((Node)(event.getSource())).getScene().getWindow().hide();
            System.out.println("you went to add booking ");
        }




    }

    private String getDateOfBooking() {
        return dateOfBooking;
    }

    public User getUser() {
        return loggedInUser;
    }

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


    public ObservableList<BookingModel> setUserBookingObservableList() {
        ObservableList<BookingModel> temp = FXCollections.observableArrayList();
        User tempUser = new User();
        int ID = tempUser.getLoggedInUserID();


        try{

            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://" + User.getIPAddress() + ":3306/CSIA?connectTimeout=5000", "myuser", "testpassword");
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM `Booking` WHERE `userID`= '" + ID + "'");

            while(rs.next()) {
                ObservableList<BookingModel> temp2 = FXCollections.observableArrayList(new BookingModel(rs.getString("bookingDate"), rs.getString("bookingComment"), rs.getInt("bookingGuests"), rs.getString("bookingTime"), rs.getString("bookingPurpose"), rs.getInt("bookingID"), rs.getInt("roomID"), rs.getInt("userID")));
                temp.add(temp2.get(0));
            }

        } catch(Exception ex) {
            ex.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("Database connection Error");
            alert.setContentText("We were unable to connect you to the database, please type 'CONFIGURE' to setup the IP Address");
            alert.showAndWait();
            buttonRegisterUser.setDisable(true);
            IPFailure = true;
        }

        return temp;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        submitReportButton.setOnAction(new EventHandler() {
            @Override
            public void handle(Event event) {
                try {
                    sendReport((ActionEvent) event);

                } catch (MessagingException e) {
                    e.printStackTrace();
                }
            }
        });

        monButton.setOnAction(new EventHandler() {

            @Override
            public void handle(Event event) {
                try {
                    goBookingForm((ActionEvent) event);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        tueButton.setOnAction(new EventHandler() {

            @Override
            public void handle(Event event) {
                try {
                    goBookingForm((ActionEvent) event);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        wedButton.setOnAction(new EventHandler() {

            @Override
            public void handle(Event event) {
                try {
                    goBookingForm((ActionEvent) event);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        thurButton.setOnAction(new EventHandler() {

            @Override
            public void handle(Event event) {
                try {
                    goBookingForm((ActionEvent) event);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        friButton.setOnAction(new EventHandler() {

            @Override
            public void handle(Event event) {
                try {
                    goBookingForm((ActionEvent) event);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        exportTableButton.setOnAction(new EventHandler() {
            @Override
            public void handle(Event event) {
                String path ="";
                try {
                DirectoryChooser chooser = new DirectoryChooser();
                Stage stage = (Stage) anchorPaneMainFunctions.getScene().getWindow();
                File file = chooser.showDialog(stage);

                if(file!=null) {
                     path = file.getAbsolutePath();
                    System.out.println("Path: " + file.getAbsolutePath());
                }


                ExcelCreator temp = new ExcelCreator();

                    temp.exportSelfBookings(tableUserBookings, col_bookingTime, col_bookingDate, col_bookingPurpose, col_bookingID, col_roomID, col_bookingGuests, path);
                    System.out.println("Export Successful");
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Warning");
                    alert.setHeaderText("Please take note:");
                    alert.setContentText("Your file has been saved at: " + path);
                    alert.showAndWait();
                } catch (IOException e) {
                    e.printStackTrace();
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Warning");
                    alert.setHeaderText("Please take note:");
                    alert.setContentText("An excel sheet was unable to be created. \nPlease select a destination");
                    alert.showAndWait();
                }



            }
        });


            col_bookingTime.setCellValueFactory(new PropertyValueFactory<>("bookingTime"));
            col_bookingDate.setCellValueFactory(new PropertyValueFactory<>("bookingDate"));
            col_bookingPurpose.setCellValueFactory(new PropertyValueFactory<>("bookingPurpose"));
            col_roomID.setCellValueFactory(new PropertyValueFactory<>("roomID"));
            col_bookingID.setCellValueFactory(new PropertyValueFactory<>("bookingID"));
            col_bookingGuests.setCellValueFactory(new PropertyValueFactory<>("bookingGuests"));

            ObservableList<BookingModel> bookingModelObservableList = setUserBookingObservableList(); //uses a function  to set list

            tableUserBookings.setItems(bookingModelObservableList);


        //todo add the booking table thing in the report section to allow the user to report a problem.


        try {
            VBox box = FXMLLoader.load(getClass().getResource("sidePanelHome.fxml"));
            drawerUserOptions.setSidePane(box);

        } catch (Exception ex) {
            System.out.println("Failed to get resource");
            ex.printStackTrace();
        }

        loggedInUserButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (e)->{

            if (drawerUserOptions.isOpened()) {
                drawerUserOptions.close();
            } else {
                drawerUserOptions.open();

            }

        });

    }

    public void goConfigureIP(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("AdminEditAdmin.fxml"));
        Parent root = loader.load();
        ControllerAdminPage controller = loader.getController();

        controller.buttonBack.setVisible(false);
        controller.labelTitle.setLayoutX(38);
        controller.labelTitle.setStyle("-fx-font-size:18;");
        controller.labelHostChange.setLayoutY(59);
        controller.labelHostChange.setLayoutX(29);
        controller.TFIP.setLayoutY(99);
        controller.TFIP.setLayoutX(44);
        controller.submitIPChange.setLayoutY(150);
        controller.submitIPChange.setLayoutX(89);

        controller.setJustConfigured(true);




        Stage primaryStage = new Stage();
        primaryStage.setTitle("Management booking system");
        primaryStage.resizableProperty().setValue(false);
        primaryStage.setScene(new Scene(root, 240, 200)); //sets dimensions of the window
        primaryStage.show();
        ((Node)(event.getSource())).getScene().getWindow().hide();
        System.out.println("you went to add booking ");




    }

    public void setIPFailure(Boolean bool) {
        this.IPFailure = bool;
    }

    public void setButtonRegisterUserDisable(boolean bool) {
        this.buttonRegisterUser.setDisable(bool);
    }
}


