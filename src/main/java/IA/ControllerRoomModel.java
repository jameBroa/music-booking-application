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
import javafx.scene.control.ChoiceBox;
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

public class ControllerRoomModel implements Initializable {
    @FXML
    TableView<RoomsModel> tableRooms;
    @FXML TableColumn<RoomsModel, String> col_roomDesc, col_roomInstruments, col_roomLocation, col_roomComment;
    @FXML TableColumn <RoomsModel, Integer> col_roomID, col_roomCapacity;

    @FXML TextField TFIDSearch, TFLocationSearch, TFInstrumentSearch;

    @FXML JFXHamburger hamburger;
    @FXML JFXDrawer drawerAdmin;
    @FXML AnchorPane anchorPaneViewRoomInfo;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        col_roomDesc.setCellValueFactory(new PropertyValueFactory<>("roomDesc"));
        col_roomInstruments.setCellValueFactory(new PropertyValueFactory<>("roomInstruments"));
        col_roomLocation.setCellValueFactory(new PropertyValueFactory<>("roomLocation"));
        col_roomComment.setCellValueFactory(new PropertyValueFactory<>("roomComment"));
        col_roomID.setCellValueFactory(new PropertyValueFactory<>("roomID"));
        col_roomCapacity.setCellValueFactory(new PropertyValueFactory<>("roomCapacity"));

        ObservableList<RoomsModel> roomsModelObservableList = setRoomObservableList(); //uses a function  to set list

        tableRooms.setItems(roomsModelObservableList);

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

    public void createPDF(ActionEvent event) {
        String path ="";
        try {
            DirectoryChooser chooser = new DirectoryChooser();
            Stage stage = (Stage) anchorPaneViewRoomInfo.getScene().getWindow();
            File file = chooser.showDialog(stage);

            if (file != null) {
                path = file.getAbsolutePath();
                System.out.println("Path: " + file.getAbsolutePath());
            }
            PDFCreator temp = new PDFCreator();
            temp.createReportRooms(path);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    FilteredList filter = new FilteredList(setRoomObservableList(), e->true);

    public void searchID() {

        TFIDSearch.textProperty().addListener((observableValue, oldValue, newValue) -> {
            filter.setPredicate((Predicate<? super RoomsModel>) (RoomsModel std) -> {

                if(newValue.isEmpty() || newValue==null) {
                    return true;
                } else if(Integer.toString(std.getRoomID()).contains(newValue)) {
                    return true;
                }

                return false;
            });
        });
        SortedList sort = new SortedList(filter);
        sort.comparatorProperty().bind(tableRooms.comparatorProperty());
        tableRooms.setItems(sort);
    }

    public void searchInstruments() {

        TFInstrumentSearch.textProperty().addListener((observableValue, oldValue, newValue) -> {
            filter.setPredicate((Predicate<? super RoomsModel>) (RoomsModel std) -> {

                if(newValue.isEmpty() || newValue==null) {
                    return true;
                } else if(std.getRoomInstruments().contains(newValue)) {
                    return true;
                }

                return false;
            });
        });
        SortedList sort = new SortedList(filter);
        sort.comparatorProperty().bind(tableRooms.comparatorProperty());
        tableRooms.setItems(sort);
    }


    public void searchLocation() {

        TFLocationSearch.textProperty().addListener((observableValue, oldValue, newValue) -> {
            filter.setPredicate((Predicate<? super RoomsModel>) (RoomsModel std) -> {
                if(newValue.isEmpty() || newValue==null) {
                    return true;
                } else if(std.getRoomLocation().toLowerCase().contains(newValue.toLowerCase())) {
                    return true;
                }

                return false;
            });
        });
        SortedList sort = new SortedList(filter);
        sort.comparatorProperty().bind(tableRooms.comparatorProperty());
        tableRooms.setItems(sort);
    }




    public ObservableList<RoomsModel> setRoomObservableList() {
        ObservableList<RoomsModel> temp = FXCollections.observableArrayList();

        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://" + User.getIPAddress() + ":3306/CSIA", "myuser", "testpassword");
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM `Room`");

            while(rs.next()) {
                ObservableList<RoomsModel> temp2 = FXCollections.observableArrayList(new RoomsModel(rs.getString("roomDesc"), rs.getString("roomInstruments"), rs.getInt("roomID"), rs.getString("roomLocation"), rs.getInt("roomCapacity"), rs.getString("roomComment")));
                temp.add(temp2.get(0));
            }

        } catch(Exception ex) {
            ex.printStackTrace();
        }

        return temp;
    }

    public void deleteRoom(ActionEvent event) {
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://" + User.getIPAddress() + ":3306/CSIA", "myuser", "testpassword");
            Statement stmt = con.createStatement();
            stmt.executeUpdate("DELETE FROM `Room` WHERE `roomID`= \"" + roomsModel.getRoomID() + "\"");
            con.close();
        } catch(Exception ex) {
            ex.printStackTrace();
        }

        ObservableList<RoomsModel> userObservableList = setRoomObservableList(); //uses a function  to set list
        tableRooms.setItems(userObservableList);
        TFInstrumentSearch.setText("");
        TFLocationSearch.setText("");
        TFIDSearch.setText("");
        filter = new FilteredList(setRoomObservableList(), e->true);
    }

    public void editRoom(ActionEvent event) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("editExistingRoomForm.fxml"));
        Parent root = loader.load();
        ControllerEditRoom controller = loader.getController();

        controller.setEditedRoom(roomsModel);
        controller.TFCapacity.setPromptText(Integer.toString(roomsModel.getRoomCapacity()));
        controller.TFLocation.setPromptText(roomsModel.getRoomLocation());
        controller.TARoomDesc.setPromptText(roomsModel.getRoomDesc());
        controller.TARoomInstruments.setPromptText(roomsModel.getRoomInstruments());
        controller.TARoomComment.setPromptText(roomsModel.getRoomComment());

        Rooms rooms = new Rooms();

        List<String> roomNames = rooms.getRoomNames();


        //todo make it so it automatically selects the current room

        Stage primaryStage = new Stage();
        primaryStage.setTitle("Management booking system");
        primaryStage.resizableProperty().setValue(false);
        primaryStage.setScene(new Scene(root, 400, 400)); //sets dimensions of the window
        primaryStage.show();
        ((Node)(event.getSource())).getScene().getWindow().hide();
        System.out.println("you went to add booking ");

    }






    private RoomsModel roomsModel;

    public void getSelectedRoom(){
        RoomsModel rooms = tableRooms.getSelectionModel().getSelectedItem();
        if(rooms==null) {
            System.out.println("nothing selected");
        } else {
            roomsModel = rooms;
            String name = rooms.getRoomLocation();
            int id = rooms.getRoomID();
            System.out.println("You have selected: " + name + " with ID: " + id);
        }
    }

    public void goMainAdminPage(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("adminPage.fxml"));
        Parent root = loader.load();
        Stage primaryStage = new Stage();
        primaryStage.setTitle("Management booking system");
        primaryStage.resizableProperty().setValue(false);
        primaryStage.setScene(new Scene(root, 600, 400)); //sets dimensions of the window
        primaryStage.show();
        ((Node)(event.getSource())).getScene().getWindow().hide();
        System.out.println("you went to add booking ");
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


}
