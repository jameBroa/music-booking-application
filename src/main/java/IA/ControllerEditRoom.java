package IA;

import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerSlideCloseTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
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
import java.util.ResourceBundle;

public class ControllerEditRoom implements Initializable {
    @FXML
    JFXHamburger hamburgerUser;
    @FXML
    JFXDrawer drawerUser;
    @FXML
    TextField TFFName, TFLName, TFSchoolID, TFID, TFCapacity, TFBookingCapacity, TFBookingDate, TFBookingTime, TFLocation;
    @FXML
    TextArea TAComment, TARoomComment, TARoomDesc, TARoomInstruments, TABookingComment, TABookingPurpose;
    @FXML
    ChoiceBox CBYear, CBClass, CBRoomLocation;
    @FXML
    CheckBox checkBoxChangeImage;
    @FXML
    Button buttonChangeImage;

    RoomsModel editedRoom;
    String path ="";

    public void setEditedRoom(RoomsModel room) {editedRoom = room; }

    public void changeImage(ActionEvent event) {

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
    public boolean checkRoomFormat() {
        String inputtedID = TFCapacity.getText().toString();

        if(inputtedID.length()==0) {
            return true;
        }

        if(inputtedID.matches("[0-9]+") == false) {
            return false;
        }
        return true;
    }





    public void updateRoom(ActionEvent event) throws Exception {
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
            if (TARoomDesc.getText().isEmpty()) {
                roomWithModifications.setRoomDesc(editedRoom.getRoomDesc());
            } else {
                roomWithModifications.setRoomDesc(TARoomDesc.getText().toString());
            }

            if (TFCapacity.getText().isEmpty()) {
                roomWithModifications.setRoomCapacity(editedRoom.getRoomCapacity());
            } else {
                roomWithModifications.setRoomCapacity(Integer.parseInt(TFCapacity.getText()));
            }
            if (TFLocation.getText().toString().isEmpty()) {
                roomWithModifications.setRoomLocation(editedRoom.getRoomLocation());
            } else {
                roomWithModifications.setRoomLocation(TFLocation.getText().toString());
            }

            if (TARoomInstruments.getText().isEmpty()) {
                roomWithModifications.setRoomInstruments((editedRoom.getRoomInstruments()));
            } else {
                roomWithModifications.setRoomInstruments(TARoomInstruments.getText());
            }

            if (TARoomComment.getText().isEmpty()) {
                roomWithModifications.setRoomComment(editedRoom.getRoomComment());
            } else {
                roomWithModifications.setRoomComment(TARoomComment.getText());
            }

            File image = new File(path);
            roomWithModifications.setRoomImage(image);


            roomWithModifications.updateRoomWithImages(roomWithModifications, editedRoom.getRoomID());

            goViewRoomInfo(event);

        } else {

            if (TARoomDesc.getText().isEmpty()) {
                roomWithModifications.setRoomDesc(editedRoom.getRoomDesc());
            } else {
                roomWithModifications.setRoomDesc(TARoomDesc.getText().toString());
            }

            if (TFCapacity.getText().isEmpty()) {
                roomWithModifications.setRoomCapacity(editedRoom.getRoomCapacity());
            } else {
                roomWithModifications.setRoomCapacity(Integer.parseInt(TFCapacity.getText()));
            }
            if (TFLocation.getText().toString().isEmpty()) {
                roomWithModifications.setRoomLocation(editedRoom.getRoomLocation());
            } else {
                roomWithModifications.setRoomLocation(TFLocation.getText().toString());
            }

            if (TARoomInstruments.getText().isEmpty()) {
                roomWithModifications.setRoomInstruments((editedRoom.getRoomInstruments()));
            } else {
                roomWithModifications.setRoomInstruments(TARoomInstruments.getText());
            }

            if (TARoomComment.getText().isEmpty()) {
                roomWithModifications.setRoomComment(editedRoom.getRoomComment());
            } else {
                roomWithModifications.setRoomComment(TARoomComment.getText());
            }

            roomWithModifications.updateRoom(roomWithModifications, editedRoom.getRoomID());

            goViewRoomInfo(event);

        }




    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
//            FXMLLoader loader = new FXMLLoader();
//            loader.setLocation(getClass().getResource("sidePanelEdit.fxml"));
//            Parent root = loader.load();
//            ControllerAdminPage controller = loader.getController();
//            controller.buttonSidePanelUser.setStyle("-fx-background-color: #ffffff; ");

            //TODO make it so the highlighted button is a different colour

            VBox box = FXMLLoader.load(getClass().getResource("sidePanelEdit.fxml"));
            drawerUser.setSidePane(box);

        } catch (Exception ex) {
            System.out.println("Failed to get resource");
            ex.printStackTrace();
        }

        //TODO: store UserIDS in a binary tree

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



        checkBoxChangeImage.selectedProperty().addListener(new ChangeListener<Boolean>() {

            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                // TODO Auto-generated method stub
                if (newValue) { //checkbox ticked

                    buttonChangeImage.setVisible(true);


                } else { //checkbox unticked

                    buttonChangeImage.setVisible(false);

                }
            }
        });
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
}
