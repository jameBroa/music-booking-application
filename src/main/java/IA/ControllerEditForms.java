package IA;

import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;
import com.jfoenix.transitions.hamburger.HamburgerBasicCloseTransition;
import com.jfoenix.transitions.hamburger.HamburgerNextArrowBasicTransition;
import com.jfoenix.transitions.hamburger.HamburgerSlideCloseTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

public class ControllerEditForms implements Initializable {
    @FXML JFXHamburger hamburgerUser;
    @FXML JFXDrawer drawerUser;
    @FXML TextField TFFName, TFLName, TFSchoolID, TFID, TFCapacity, TFBookingCapacity, TFBookingDate, TFBookingTime, TFLocation;
    @FXML TextArea TAComment, TARoomComment, TARoomDesc, TARoomInstruments, TABookingComment, TABookingPurpose;
    @FXML ChoiceBox CBYear, CBClass, CBRoomLocation;
    @FXML CheckBox checkBoxChangeImage;
    @FXML Button buttonChangeImage;
    UserModel editedUser;

    String path = "";
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        try {


            VBox box = FXMLLoader.load(getClass().getResource("sidePanelEdit.fxml"));
            drawerUser.setSidePane(box);

        } catch (Exception ex) {
            System.out.println("Failed to get resource");
            ex.printStackTrace();
        }


        HamburgerSlideCloseTransition transition2 = new HamburgerSlideCloseTransition(hamburgerUser);
        transition2.setRate(-1);
        hamburgerUser.addEventHandler(MouseEvent.MOUSE_CLICKED, (e)->{
            transition2.setRate(transition2.getRate()*-1);
            transition2.play();
            if (drawerUser.isOpened()) {
                drawerUser.close();
            } else {
                drawerUser.open();

            }

        });
    }

    //< -- DATA MEMBERS -->

    RoomsModel editedRoom;
    BookingModel editedBooking;

     // <-------------------->

    // <-- SETS MEMBER FUNCTIONS -->

    public void setEditedUser(UserModel user) {
        editedUser = user;
    }

    public void setEditedRoom(RoomsModel room) {editedRoom = room; }

    public void setEditedBooking(BookingModel booking) {editedBooking = booking; }

    //< ---------------------------->

    public void changeImage(ActionEvent event) { //FUNCTION TO CHANGE IMAGE OF

        try {
            FileChooser fileChooser = new FileChooser();
            FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("IMAGES *.png", "*.png", "*.jpeg", "*.jpg");
            fileChooser.getExtensionFilters().add(extensionFilter);

            File file = fileChooser.showOpenDialog(null);
            //Stage stage = (Stage) addRoomAnchorpane.getScene().getWindow();

            if (file != null) {
                path = file.getAbsolutePath();
                System.out.println("Path: " + file.getAbsolutePath());
                buttonChangeImage.setText("IMAGE ADDED");
                buttonChangeImage.setStyle("-fx-text-fill: green;");
            }

        }catch (Exception ex) {
            ex.printStackTrace();
            buttonChangeImage.setText("FAILED TO ADD IMAGE");
            buttonChangeImage.setStyle("-fx-text-fill: red;");
        }

    }

    public void updateClassChoiceBox(ActionEvent event) { //FUNCTION TO UPDATE THE TUTOR GROUP OPTION WHEN CHANGING THE YEAR CHOICEBOX

        String yearGroup = CBYear.getValue().toString(); //gets the current value from the dropdown menu
        ControllerInputForms temp = new ControllerInputForms();
        int numYearGroup = temp.getNumYear(yearGroup); //FUNCTION TO GET THE YEAR FROM THE CHOICEBOX
        //System.out.println(numYearGroup);

        switch(numYearGroup){ //SWITCH STATEMENT
            case 7:
                CBClass.setItems(FXCollections.observableList(temp.getYear7()));
                CBClass.getSelectionModel().selectFirst();
                break;
            case 8:
                CBClass.setItems(FXCollections.observableList(temp.getYear8()));
                CBClass.getSelectionModel().selectFirst();
                break;
            case 9:
                CBClass.setItems(FXCollections.observableList(temp.getYear9()));
                CBClass.getSelectionModel().selectFirst();
                break;
            case 10:
                CBClass.setItems(FXCollections.observableList(temp.getYear10()));
                CBClass.getSelectionModel().selectFirst();
                break;
            case 11:
                CBClass.setItems(FXCollections.observableList(temp.getYear11()));
                CBClass.getSelectionModel().selectFirst();
                break;
            case 12:
                CBClass.setItems(FXCollections.observableList(temp.getYear12()));
                CBClass.getSelectionModel().selectFirst();
                break;
            case 13:
                CBClass.setItems(FXCollections.observableList(temp.getYear13()));
                CBClass.getSelectionModel().selectFirst();
                break;
        }

    }

    public void updateBooking(ActionEvent event) throws Exception { //FUNCTION TO UPDATE BOOKING
        Booking bookingWithModifications = new Booking(); //TEMPORARY ISNTANCE OF A BOOKING

        // <-- SETS DATA MEMBERS OF BOOKING -->
        bookingWithModifications.setUserID(editedBooking.getUserID());
        bookingWithModifications.setRoomID(editedBooking.getRoomID());
        bookingWithModifications.setBookingID(editedBooking.getBookingID());
        // <------------------------------------>

        if(TFBookingTime.getText().isEmpty()) { //OPEN IF
            bookingWithModifications.setBookingTime(editedBooking.getBookingTime());
        } else {
            bookingWithModifications.setBookingTime(TFBookingTime.getText());
        } //END ELSE

        if(TFBookingDate.getText().isEmpty()) { //OPEN IF
            bookingWithModifications.setBookingDate(editedBooking.getBookingDate());
        } else {
            bookingWithModifications.setBookingDate(TFBookingDate.getText());
        }//END ELSE

        if(TABookingPurpose.getText().isEmpty()) {//OPEN IF
            bookingWithModifications.setBookingPurpose(editedBooking.getBookingPurpose());
        } else {
            bookingWithModifications.setBookingPurpose(TABookingPurpose.getText());
        }//END ELSE

        if (TFBookingCapacity.getText().isEmpty()) {//OPEN IF
            bookingWithModifications.setBookingGuests(editedBooking.getBookingGuests());
        } else {
            bookingWithModifications.setBookingGuests(Integer.parseInt(TFBookingCapacity.getText()));
        }//END ELSE

        if(TABookingComment.getText().isEmpty()) {//OPEN IF
            bookingWithModifications.setBookingComment(editedBooking.getBookingComment());
        } else {
            bookingWithModifications.setBookingComment(TABookingComment.getText());
        }//END ELSE
        bookingWithModifications.updateBooking(bookingWithModifications, editedBooking.getBookingID()); //UPDATES BOOKING
        goViewBookingInfo(event);//RETURNS BACK TO BOOKING ADMIN PAGE


    }

    public void goViewBookingInfo(ActionEvent event) throws Exception { //FUNCTION TO GO VIEW BOOKING ADMIN PAGE
        Parent root = FXMLLoader.load(getClass().getResource("viewBookingInfo.fxml")); //links .java file to .fxml file
        Stage primaryStage = new Stage();
        primaryStage.setTitle("Management booking system");
        primaryStage.resizableProperty().setValue(false);
        primaryStage.setScene(new Scene(root, 600, 450)); //sets dimensions of the window
        primaryStage.show();
        ((Node)(event.getSource())).getScene().getWindow().hide();
        System.out.println("you went to edit rooms ");
    }

    public boolean checkRoomFormat() { //FUNCTION TO CHECK FORMAT OF TEXTFIELDS
        String inputtedID = TFCapacity.getText().toString();

        if(inputtedID.matches("[0-9]+") == false) { //CONDITIONAL STATEMENT TO CHECK IF TEXTFIELD IS ONLY NUMBERS
            return false;
        }
        return true;
    }


    public void updateRoom(ActionEvent event) throws Exception { //FUNCTION TO UPDATE ROOMS
        Rooms roomWithModifications = new Rooms();

        boolean formattedCorrectlyRoom = checkRoomFormat();
        System.out.println("update room: " + formattedCorrectlyRoom);

        if(!formattedCorrectlyRoom) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("Please take note:");
            alert.setContentText("Please enter data correctly (no letters in capacity)");
            alert.showAndWait();
        } else if(checkBoxChangeImage.isSelected()) {
            if (TARoomDesc.getText().isEmpty()) {//OPEN IF
                roomWithModifications.setRoomDesc(editedRoom.getRoomDesc());
            } else {
                roomWithModifications.setRoomDesc(TARoomDesc.getText().toString());
            }//END ELSE

            if (TFCapacity.getText().isEmpty()) {//OPEN IF
                roomWithModifications.setRoomCapacity(editedRoom.getRoomCapacity());
            } else {
                roomWithModifications.setRoomCapacity(Integer.parseInt(TFCapacity.getText()));
            }//END ELSE
            if (TFLocation.getText().toString().isEmpty()) {//OPEN IF
                roomWithModifications.setRoomLocation(editedRoom.getRoomLocation());
            } else {
                roomWithModifications.setRoomLocation(TFLocation.getText().toString());
            }//END ELSE

            if (TARoomInstruments.getText().isEmpty()) {//OPEN IF
                roomWithModifications.setRoomInstruments((editedRoom.getRoomInstruments()));
            } else {
                roomWithModifications.setRoomInstruments(TARoomInstruments.getText());
            }//END ELSE

            if (TARoomComment.getText().isEmpty()) {//OPEN IF
                roomWithModifications.setRoomComment(editedRoom.getRoomComment());
            } else {
                roomWithModifications.setRoomComment(TARoomComment.getText());
            }//END ELSE

            File image = new File(path);
            roomWithModifications.setRoomImage(image); //SETS ROOM IMAGE

            roomWithModifications.updateRoomWithImages(roomWithModifications, editedRoom.getRoomID());

            goViewRoomInfo(event); //RETURN BACK TO ROOM INFORMATION


        } else { //RUN IF THE USER DOESN'T DECIDE TO CHANGE THE IMAGE

            if (TARoomDesc.getText().isEmpty()) {//OPEN IF
                roomWithModifications.setRoomDesc(editedRoom.getRoomDesc());
            } else {
                roomWithModifications.setRoomDesc(TARoomDesc.getText().toString());
            }

            if (TFCapacity.getText().isEmpty()) {//OPEN IF
                roomWithModifications.setRoomCapacity(editedRoom.getRoomCapacity());
            } else {
                roomWithModifications.setRoomCapacity(Integer.parseInt(TFCapacity.getText()));
            }//END ELSE
            if (TFLocation.getText().toString().isEmpty()) {//OPEN IF
                roomWithModifications.setRoomLocation(editedRoom.getRoomLocation());
            } else {
                roomWithModifications.setRoomLocation(TFLocation.getText().toString());
            }//END ELSE

            if (TARoomInstruments.getText().isEmpty()) {//OPEN IF
                roomWithModifications.setRoomInstruments((editedRoom.getRoomInstruments()));
            } else {
                roomWithModifications.setRoomInstruments(TARoomInstruments.getText());
            }//END ELSE

            if (TARoomComment.getText().isEmpty()) {//OPEN IF
                roomWithModifications.setRoomComment(editedRoom.getRoomComment());
            } else {
                roomWithModifications.setRoomComment(TARoomComment.getText());
            }//END ELSE

            roomWithModifications.updateRoom(roomWithModifications, editedRoom.getRoomID()); //UPDATES ROOM

            goViewRoomInfo(event); //RETURN BACK TO ROOM ADMIN PAGE

        }




    }

    public boolean checkUserFormat() { //CHECKS FORMAT OF TEXTFIELDS


        String fName = TFFName.getText().toString();
        char[] fNameChar = fName.toCharArray();
        for(char i : fNameChar) {
            if(Character.isDigit(i)) { //CHECKS TO SEE IF A STRING CONTAINS NUMBERS
                return false;
            }
        }
        String lName = TFLName.getText().toString();
        char[] lNameChar = lName.toCharArray();
        for(char i : lNameChar) {
            if(Character.isDigit(i)) { //CHECKS TO SEE IF A STRING CONTAINS NUMBERS
                return false;
            }
        }

        String inputtedID = TFSchoolID.getText().toString();

        if(inputtedID.length() == 0) {
            return true;
        }

        if(inputtedID.matches("[0-9]+") == false || inputtedID.length() > 6 ) { //CHECKS TO SEE IF IT CONTAINS NUMBERS ONLY AND IS LESS THAN A CERTAIN LENGTH
            return false;
        }


        return true;
    }

    public void updateUser(ActionEvent event) throws Exception { //FUNCTION TO UPDATE USER
        int id = editedUser.getIDUser();
        User userWithModifications = new User();

        boolean formattedCorrectly = checkUserFormat();

        if(!formattedCorrectly) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("Please take note:");
            alert.setContentText("Please enter data correctly (no numbers in names, max length student ID 6)");
            alert.showAndWait();
        } else {

            // <-- FIRST NAME -->
            if (TFFName.getText().isEmpty()) {
                userWithModifications.setFirstName(editedUser.getNameFirst());
            } else {
                userWithModifications.setFirstName(TFFName.getText());
            }
            // <---------------->

            // <-- LAST NAME -->

            if (TFLName.getText().isEmpty()) {
                userWithModifications.setLastName(editedUser.getNameLast());
            } else {
                userWithModifications.setLastName(TFLName.getText());
            }
            // <--------------->

            if (TFSchoolID.getText().isEmpty()) {
                userWithModifications.setUserSchoolID(editedUser.getSchoolIDUser());
            } else {
                userWithModifications.setUserSchoolID(Long.parseLong(TFSchoolID.getText()));
            }

            if (TFID.getText().isEmpty()) {
                userWithModifications.setUserID(editedUser.getIDUser());
            } else {
                userWithModifications.setUserID(Integer.parseInt(TFID.getText()));
            }

            byte year = (byte) Integer.parseInt(CBYear.getValue().toString().substring(5));

            userWithModifications.setUserYear(year);

            userWithModifications.setUserClass(CBClass.getValue().toString());

            if (TAComment.getText().isEmpty()) {
                userWithModifications.setUserComment(editedUser.getCommentUser());
            } else {
                userWithModifications.setUserComment(TAComment.getText());
            }

            userWithModifications.updateUser(userWithModifications, id);

            goViewUserInfo(event);

        }
    }


    public void goViewUserInfo(ActionEvent event) throws Exception { //FUNCTION TO VIEW USER ADMIN PAGE

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

    public void goViewRoomInfo(ActionEvent event) throws Exception { //FUNCTION TO VIEW ROOM ADMIN PAGE
        Parent root = FXMLLoader.load(getClass().getResource("viewRoomInfo.fxml")); //links .java file to .fxml file
        Stage primaryStage = new Stage();
        primaryStage.setTitle("Management booking system");
        primaryStage.resizableProperty().setValue(false);
        primaryStage.setScene(new Scene(root, 600, 450)); //sets dimensions of the window
        primaryStage.show();
        ((Node)(event.getSource())).getScene().getWindow().hide();
        System.out.println("you went to edit rooms ");
    }




}
