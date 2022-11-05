package IA;

import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Predicate;

public class ControllerBookingModel implements Initializable {
    @FXML
    TableView<BookingModel> tableBooking;
    @FXML TableColumn<BookingModel, String> col_bookingTime, col_bookingDate, col_bookingPurpose, col_bookingComment;
    @FXML TableColumn <BookingModel, Integer> col_roomID, col_userID, col_bookingID, col_bookingGuests;
    @FXML AnchorPane anchorPaneViewBookingInfo;
    @FXML TextField TFUserIDSearch, TFDateSearch;

    @FXML JFXHamburger hamburger;
    @FXML JFXDrawer drawerAdmin;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        col_bookingTime.setCellValueFactory(new PropertyValueFactory<>("bookingTime"));
        col_bookingDate.setCellValueFactory(new PropertyValueFactory<>("bookingDate"));
        col_bookingPurpose.setCellValueFactory(new PropertyValueFactory<>("bookingPurpose"));
        col_bookingComment.setCellValueFactory(new PropertyValueFactory<>("bookingComment"));
        col_roomID.setCellValueFactory(new PropertyValueFactory<>("roomID"));
        col_userID.setCellValueFactory(new PropertyValueFactory<>("userID"));
        col_bookingID.setCellValueFactory(new PropertyValueFactory<>("bookingID"));
        col_bookingGuests.setCellValueFactory(new PropertyValueFactory<>("bookingGuests"));

        ObservableList<BookingModel> bookingModelObservableList = setBookingObservableList(); //uses a function  to set list

        tableBooking.setItems(bookingModelObservableList);


        try {

            VBox box = FXMLLoader.load(getClass().getResource("sidePanelAdmin.fxml"));
            drawerAdmin.setSidePane(box);

//            FXMLLoader loader = new FXMLLoader(getClass().getResource("sidePanelAdmin.fxml"));
//            VBox box = loader.load();
//            ControllerAdminPage controller = loader.getController();
//            drawerAdmin.setSidePane(box);
        } catch (Exception ex) {
            System.out.println("Failed to get resource");
            ex.printStackTrace();
        }

        HamburgerBackArrowBasicTransition transition = new HamburgerBackArrowBasicTransition(hamburger); //creates instance of transition
        transition.setRate(-1); //sets transition rate as -1


        hamburger.addEventHandler(MouseEvent.MOUSE_CLICKED, (e)->{ //opening of anonymous function || event handler
            transition.setRate(transition.getRate()*-1); //resets transition rate, allows opening and closing
            transition.play(); //plays animation

            if (drawerAdmin.isOpened()) { //start of IF | | checks if the drawer is opened
                drawerAdmin.close(); //close drawer
            } else {
                drawerAdmin.open(); //open drawer
            } //end if
        }); //close anonymous function

    }

    FilteredList filter = new FilteredList(setBookingObservableList(), e->true);

    public void createPDF(ActionEvent event) { //FUNCTION TO CREATE PDF REPORT
        String path ="";
        try {
            DirectoryChooser chooser = new DirectoryChooser(); //CREATES DIRECTORYCHOOSER OBJECT TO ALLOW USER TO DECIDE
            // WHERE TO SAVE THE PDF
            Stage stage = (Stage) anchorPaneViewBookingInfo.getScene().getWindow();
            File file = chooser.showDialog(stage); //DISPLAYS THE ABILITY TO CHOOSE

            if (file != null) { //CODE RUN IF A DIRECTORY IS CHOSEN
                path = file.getAbsolutePath();
                System.out.println("Path: " + file.getAbsolutePath());
            }
            PDFCreator temp = new PDFCreator();
            temp.createReportBookings(path); //FUNCTION TO CREATE REPORT IS CALLED WITH THE PATH DETERMINED
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    public void searchUserID() { //FUNCTION TO FILTER DATA IN TABLE BY ID

        TFUserIDSearch.textProperty().addListener((observableValue, oldValue, newValue) -> {
            filter.setPredicate((Predicate<? super BookingModel>) (BookingModel std) -> {

                if(newValue.isEmpty() || newValue==null) {
                    return true;
                } else if(Integer.toString(std.getUserID()).contains(newValue)) {
                    return true;
                }

                return false;
            });
        });
        SortedList sort = new SortedList(filter);
        sort.comparatorProperty().bind(tableBooking.comparatorProperty());
        tableBooking.setItems(sort);
    }

    public void searchDate() { //FUNCTION TO FILTER DATA IN TABLE BY DATE

        TFDateSearch.textProperty().addListener((observableValue, oldValue, newValue) -> {
            filter.setPredicate((Predicate<? super BookingModel>) (BookingModel std) -> {

                if(newValue.isEmpty() || newValue==null) {
                    return true;
                } else if(std.getBookingDate().contains(newValue)) {
                    return true;
                }

                return false;
            });
        });
        SortedList sort = new SortedList(filter);
        sort.comparatorProperty().bind(tableBooking.comparatorProperty());
        tableBooking.setItems(sort);
    }


    public ObservableList<BookingModel> setBookingObservableList() { //FUNCTION TO SET THE VALUES WITHIN THE TABLE
        ObservableList<BookingModel> temp = FXCollections.observableArrayList();

        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://" + User.getIPAddress() + ":3306/CSIA", "myuser", "testpassword");
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM `Booking`");

            while(rs.next()) {
                ObservableList<BookingModel> temp2 = FXCollections.observableArrayList(new BookingModel(rs.getString("bookingDate"), rs.getString("bookingComment"), rs.getInt("bookingGuests"), rs.getString("bookingTime"), rs.getString("bookingPurpose"), rs.getInt("bookingID"), rs.getInt("roomID"), rs.getInt("userID")));
                temp.add(temp2.get(0));
            }

        } catch(Exception ex) {
            ex.printStackTrace();
        }

        return temp;
    }

    public void goMainAdminPage(ActionEvent event) throws IOException { //FUNCTION TO RETURN BACK TO THE MAIN ADMIN PAGE
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("adminPage.fxml")); //LINKS .FXML FILE TO .JAVA FILE
        Parent root = loader.load();
        Stage primaryStage = new Stage();
        primaryStage.setTitle("Management booking system");
        primaryStage.resizableProperty().setValue(false);
        primaryStage.setScene(new Scene(root, 600, 400)); //sets dimensions of the window
        primaryStage.show();
        ((Node)(event.getSource())).getScene().getWindow().hide();
        System.out.println("you went to add booking ");
    }

    public void editBooking(ActionEvent event) throws Exception { //FUNCTION TO VISIT EDIT BOOKING INPUT FORM
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("editExistingBookingForm.fxml"));//LINKS .FXML FILE TO .JAVA FILE
        Parent root = loader.load();
        ControllerEditForms controller = loader.getController(); //CREATES INSTANCE OF CONTROLLER CLASS OF TARGET FXML FILE

        // <-- MANIPULATION OF GUI ELEMENTS -->
        controller.setEditedBooking(selectedBooking);
        controller.TFBookingTime.setPromptText(selectedBooking.getBookingTime());
        controller.TFBookingCapacity.setPromptText(Integer.toString(selectedBooking.getBookingGuests()));
        controller.TABookingPurpose.setPromptText(selectedBooking.getBookingPurpose());
        controller.TFBookingDate.setPromptText(selectedBooking.getBookingDate());
        controller.TABookingComment.setPromptText(selectedBooking.getBookingComment());
        // <---------------------------------->

        //<-- OPENING OF NEW STAGE -->
        Stage primaryStage = new Stage();
        primaryStage.setTitle("Management booking system");
        primaryStage.resizableProperty().setValue(false);
        primaryStage.setScene(new Scene(root, 400, 400)); //sets dimensions of the window
        primaryStage.show();
        ((Node)(event.getSource())).getScene().getWindow().hide();
        System.out.println("you went to add booking ");

        // <-------------------------->

    }


    private BookingModel selectedBooking;

    public void getSelectedBooking(){ //FUNCTION TO GET SELECTED BOOKING WITHIN THE TABLE
        BookingModel booking = tableBooking.getSelectionModel().getSelectedItem();
        if(booking==null) {
            System.out.println("nothing selected");
        } else {
            selectedBooking = booking;
            int userID = booking.getUserID();
            int bookingID = booking.getBookingID();
            System.out.println("You have selected: " + userID + " with Booking ID: " + bookingID);
        }
    }

    public void deleteBooking() { //FUNCTION TO DELETE BOOKING WITHIN THE TABLE

        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://" + User.getIPAddress() + ":3306/CSIA", "myuser", "testpassword");
            Statement stmt = con.createStatement();
            stmt.executeUpdate("DELETE FROM `Booking` WHERE `bookingID`= \"" + selectedBooking.getBookingID() + "\"");
            con.close();
        } catch(Exception ex) {
            ex.printStackTrace();
        }

        ObservableList<BookingModel> userObservableList = setBookingObservableList(); //uses a function  to set list
        tableBooking.setItems(userObservableList);
        TFDateSearch.setText("");
        TFUserIDSearch.setText("");
        filter = new FilteredList(setBookingObservableList(), e->true);
    }
}
