package IA;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;


public class RoomsModel {

    // <-- DATA MEMBERS -->

    private SimpleStringProperty roomDesc;
    private SimpleStringProperty roomInstruments;
    private SimpleIntegerProperty roomID;
    private SimpleStringProperty roomLocation;
    private SimpleIntegerProperty roomCapacity;
    private SimpleStringProperty roomComment;

    // <--------------------->


    public RoomsModel(String desc, String instruments, int id, String location, int capacity, String comment) { //CONSTRUCTOR
        this.roomDesc = new SimpleStringProperty(desc);
        this.roomInstruments = new SimpleStringProperty(instruments);
        this.roomID = new SimpleIntegerProperty(id);
        this.roomLocation = new SimpleStringProperty(location);
        this.roomCapacity = new SimpleIntegerProperty(capacity);
        this.roomComment = new SimpleStringProperty(comment);
    }

    // <-- MEMBER FUNCTIONS -->

    public int getRoomID() {
        return roomID.get();
    }

    public String getRoomDesc() {
        return roomDesc.get();
    }

    public String getRoomInstruments() {
        return roomInstruments.get();
    }

    public int getRoomCapacity() {
        return roomCapacity.get();
    }

    public String getRoomComment() {
        return roomComment.get();
    }

    public String getRoomLocation() {
        return roomLocation.get();
    }

    public void setRoomInstruments(String roomInstruments) {
        this.roomInstruments.set(roomInstruments);
    }

    public void setRoomDesc(String roomDesc) {
        this.roomDesc.set(roomDesc);
    }

    public void setRoomID(int roomID) {
        this.roomID.set(roomID);
    }

    public void setRoomLocation(String roomLocation) {
        this.roomLocation.set(roomLocation);
    }

    public void setRoomComment(String roomComment) {
        this.roomComment.set(roomComment);
    }

    public void setRoomCapacity(int roomCapacity) {
        this.roomCapacity.set(roomCapacity);
    }

    // <----------------------------->
}
