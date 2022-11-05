package IA;

// <-- IMPORTS -->

import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;
import com.jfoenix.transitions.hamburger.HamburgerSlideCloseTransition;
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
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.function.Predicate;

public class ControllerUserModel implements Initializable { //START OF CLASS

    // <-- INITIALIZATION OF VARIABLES FROM FXML FILE (GUI ELEMENTS) -->

    // <-- INITIALIZATION OF KEY PARTS OF THE TABLE -->
    @FXML TableView<UserModel> tableUsers; //TABLE
    @FXML TableColumn<UserModel, String> col_firstName, col_LastName, col_Class, col_Comment; //COLUMNS WITH STRINGS
    @FXML TableColumn <UserModel, Long> col_SchoolID; //COLUMNS WITH LONGS
    @FXML TableColumn <UserModel, Integer> col_AppID, col_YearGroup; //COLUMNS WITH INTEGERS
    // <---------------------------------------------->

    @FXML TextField TFIDSearch, TFNameSearch;
    @FXML JFXHamburger hamburger;
    @FXML JFXDrawer drawerAdmin;
    @FXML AnchorPane anchorPaneViewUserInfo;

    // <-------------------------------------------------------------->

    private User currentUser;


    public void setLoggedInUser(User user) {
        currentUser = user;
        System.out.println("this is now the current user: " + currentUser.getFirstName());
    }

    public User getLoggedInUser(){
        return currentUser;
    }


    @Override
    public void initialize(URL Location, ResourceBundle resources) {

        // <-- SET TABLE COLUMNS -->

        col_firstName.setCellValueFactory(new PropertyValueFactory<>("nameFirst"));
        col_LastName.setCellValueFactory(new PropertyValueFactory<>("nameLast"));
        col_YearGroup.setCellValueFactory(new PropertyValueFactory<>("yearUser"));
        col_Class.setCellValueFactory(new PropertyValueFactory<>("classUser"));
        col_SchoolID.setCellValueFactory(new PropertyValueFactory<>("schoolIDUser"));
        col_AppID.setCellValueFactory(new PropertyValueFactory<>("IDUser"));
        col_Comment.setCellValueFactory(new PropertyValueFactory<>("commentUser"));

        // <------------------------>

        //CREATES LIST OF ALL ELEMENTS IN TABLE
        ObservableList<UserModel> userObservableList = setUserModelObservableList();

        tableUsers.setItems(userObservableList); //SETS ELEMENTS IN TABLE BY THE LIST

        // <-------------------------------------------------------------------------->

        // <-- set drawerer

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


        // <----



        // <--- set hamburger --->

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

        // <-------------------->

    }

    private ArrayList<Integer> years = new ArrayList<Integer>() {{
        add(7);
        add(8);
        add(9);
        add(10);
        add(11);
        add(12);
        add(13);
    }};

    private UserModel selectedUser;

    public void getSelectedUser(){
        UserModel user = tableUsers.getSelectionModel().getSelectedItem();
        if(user==null) {
            System.out.println("nothing selected");
        } else {
            selectedUser = user;
            String name = user.getNameFirst();
            int id = user.getIDUser();
            System.out.println("You have selected: " + name + " with ID: " + id);
        }
    }

    public void deleteUser() {

        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://" + User.getIPAddress() + ":3306/CSIA", "myuser", "testpassword");
            Statement stmt = con.createStatement();
            stmt.executeUpdate("DELETE FROM `User` WHERE `userID`= \"" + selectedUser.getIDUser() + "\"");
            stmt.executeUpdate("DELETE FROM `Booking` WHERE `userID`= \"" + selectedUser.getIDUser() + "\"");
            con.close();
        } catch(Exception ex) {
            ex.printStackTrace();
        }

        ObservableList<UserModel> userObservableList = setUserModelObservableList(); //uses a function  to set list
        tableUsers.setItems(userObservableList);
        TFNameSearch.setText("");
        TFIDSearch.setText("");
        filter = new FilteredList(setUserModelObservableList(), e->true);
    }



    FilteredList filter = new FilteredList(setUserModelObservableList(), e->true);

    public void searchID() {
        TFIDSearch.textProperty().addListener((observableValue, oldValue, newValue) -> {
            filter.setPredicate((Predicate<? super UserModel>) (UserModel std) -> {

                if(newValue.isEmpty() || newValue==null) {
                    return true;
                } else if(Integer.toString(std.getIDUser()).contains(newValue)) {
                    return true;
                }

            return false;
            });
        });
        SortedList sort = new SortedList(filter);
        sort.comparatorProperty().bind(tableUsers.comparatorProperty());
        tableUsers.setItems(sort);
    }

    public void searchName() {

        TFNameSearch.textProperty().addListener((observableValue, oldValue, newValue) -> {
            filter.setPredicate((Predicate<? super UserModel>) (UserModel std) -> {
                if(newValue.isEmpty() || newValue==null) {
                    return true;
                } else if(std.getNameFirst().toLowerCase().contains(newValue.toLowerCase())) {
                    return true;
                }

                return false;
            });
        });
        SortedList sort = new SortedList(filter);
        sort.comparatorProperty().bind(tableUsers.comparatorProperty());
        tableUsers.setItems(sort);
    }


    public ObservableList<UserModel> setUserModelObservableList() {
        ObservableList<UserModel> temp = FXCollections.observableArrayList();

        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://" + User.getIPAddress() + ":3306/CSIA", "myuser", "testpassword");
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM `User`");

            while(rs.next()) {
                 ObservableList<UserModel> temp2 = FXCollections.observableArrayList(new UserModel(rs.getString("userFirstName"), rs.getString("userLastName"), rs.getInt("userYear"), rs.getString("userClass"), rs.getLong("userSchoolID"), rs.getInt("userID"), rs.getString("userComment")));
                 temp.add(temp2.get(0));
            }

        } catch(Exception ex) {
            ex.printStackTrace();
        }

        return temp;

    }

    public void goEditUsers(ActionEvent event) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("AdminEditUsers.fxml")); //links .java file to .fxml file
        Stage primaryStage = new Stage();
        primaryStage.setTitle("Management booking system");
        primaryStage.resizableProperty().setValue(false);
        primaryStage.setScene(new Scene(root, 450, 450)); //sets dimensions of the window
        primaryStage.show();
        ((Node)(event.getSource())).getScene().getWindow().hide();
        System.out.println("you went to edit users ");
    }

    public void addUser(ActionEvent event) throws Exception{
        ControllerMain temp = new ControllerMain();
        temp.goSignup(event);
    }

    public void createPDF(ActionEvent event) {
        String path ="";
        try {
            DirectoryChooser chooser = new DirectoryChooser();
            Stage stage = (Stage) anchorPaneViewUserInfo.getScene().getWindow();
            File file = chooser.showDialog(stage);

            if (file != null) {
                path = file.getAbsolutePath();
                System.out.println("Path: " + file.getAbsolutePath());
            }
            PDFCreator temp = new PDFCreator();
            temp.createReport(selectedUser, path);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void editUser(ActionEvent event) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("editExistingUserForm.fxml"));
        Parent root = loader.load();
        ControllerEditForms controller = loader.getController();

        controller.setEditedUser(selectedUser);
        controller.TFFName.setPromptText(selectedUser.getNameFirst());
        controller.TFLName.setPromptText(selectedUser.getNameLast());
        controller.TFSchoolID.setPromptText((Long.toString(selectedUser.getSchoolIDUser())));
        controller.TFID.setPromptText(Integer.toString(selectedUser.getIDUser()));
        controller.TAComment.setPromptText(selectedUser.getCommentUser());

        int year = selectedUser.getYearUser();
        String userClass = selectedUser.getClassUser();
        ControllerInputForms temp = new ControllerInputForms();
        int counter = 0;
        switch(year) {
            case 7:
                controller.CBYear.getSelectionModel().selectFirst();

                controller.CBClass.setItems(FXCollections.observableList(temp.getYear7()));
                controller.CBClass.getSelectionModel().selectFirst();
                counter = 0;
                for(int i = 0; i < temp.getYear7().size(); i++) {
                    if(temp.getYear7().get(i).equals(userClass)) {
                        break;
                    }
                    counter++;
                }
                for(int i = 0; i < counter; i++) {
                    controller.CBClass.getSelectionModel().selectNext();
                }

                break;
            case 8:
                controller.CBYear.getSelectionModel().selectFirst();
                controller.CBYear.getSelectionModel().selectNext();

                controller.CBClass.setItems(FXCollections.observableList(temp.getYear8()));
                controller.CBClass.getSelectionModel().selectFirst();
                counter = 0;
                for(int i = 0; i < temp.getYear8().size(); i++) {
                    if(temp.getYear8().get(i).equals(userClass)) {
                        break;
                    }
                    counter++;
                }
                for(int i = 0; i < counter; i++) {
                    controller.CBClass.getSelectionModel().selectNext();
                }



                break;
            case 9:
                controller.CBYear.getSelectionModel().selectFirst();
                controller.CBYear.getSelectionModel().selectNext();
                controller.CBYear.getSelectionModel().selectNext();

                controller.CBClass.setItems(FXCollections.observableList(temp.getYear9()));
                controller.CBClass.getSelectionModel().selectFirst();
                counter = 0;
                for(int i = 0; i < temp.getYear9().size(); i++) {
                    if(temp.getYear9().get(i).equals(userClass)) {
                        break;
                    }
                    counter++;
                }
                for(int i = 0; i < counter; i++) {
                    controller.CBClass.getSelectionModel().selectNext();
                }

                break;
            case 10:
                controller.CBYear.getSelectionModel().selectLast();
                controller.CBYear.getSelectionModel().selectPrevious();
                controller.CBYear.getSelectionModel().selectPrevious();
                controller.CBYear.getSelectionModel().selectPrevious();

                controller.CBClass.setItems(FXCollections.observableList(temp.getYear10()));
                controller.CBClass.getSelectionModel().selectFirst();
                counter = 0;
                for(int i = 0; i < temp.getYear10().size(); i++) {
                    if(temp.getYear10().get(i).equals(userClass)) {
                        break;
                    }
                    counter++;
                }
                for(int i = 0; i < counter; i++) {
                    controller.CBClass.getSelectionModel().selectNext();
                }
                break;
            case 11:
                controller.CBYear.getSelectionModel().selectLast();
                controller.CBYear.getSelectionModel().selectPrevious();
                controller.CBYear.getSelectionModel().selectPrevious();

                controller.CBClass.setItems(FXCollections.observableList(temp.getYear11()));
                controller.CBClass.getSelectionModel().selectFirst();
                counter = 0;
                for(int i = 0; i < temp.getYear11().size(); i++) {
                    if(temp.getYear11().get(i).equals(userClass)) {
                        break;
                    }
                    counter++;
                }
                for(int i = 0; i < counter; i++) {
                    controller.CBClass.getSelectionModel().selectNext();
                }

                break;
            case 12:
                controller.CBYear.getSelectionModel().selectLast();
                controller.CBYear.getSelectionModel().selectPrevious();

                controller.CBClass.setItems(FXCollections.observableList(temp.getYear12()));
                controller.CBClass.getSelectionModel().selectFirst();
                counter = 0;
                for(int i = 0; i < temp.getYear12().size(); i++) {
                    if(temp.getYear12().get(i).equals(userClass)) {
                        break;
                    }
                    counter++;
                }
                for(int i = 0; i < counter; i++) {
                    controller.CBClass.getSelectionModel().selectNext();
                }
                break;

            case 13:
                controller.CBYear.getSelectionModel().selectLast();
                controller.CBClass.setItems(FXCollections.observableList(temp.getYear13()));
                controller.CBClass.getSelectionModel().selectFirst();
                counter = 0;
                for(int i = 0; i < temp.getYear13().size(); i++) {
                    if(temp.getYear13().get(i).equals(userClass)) {
                        break;
                    }
                    counter++;
                }
                for(int i = 0; i < counter; i++) {
                    controller.CBClass.getSelectionModel().selectNext();
                }
                break;
        }

        Stage primaryStage = new Stage();
        primaryStage.setTitle("Management booking system");
        primaryStage.resizableProperty().setValue(false);
        primaryStage.setScene(new Scene(root, 400, 400)); //sets dimensions of the window
        primaryStage.show();
        ((Node)(event.getSource())).getScene().getWindow().hide();
        System.out.println("you went to add booking ");

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

}
