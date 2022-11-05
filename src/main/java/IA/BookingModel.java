package IA;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class BookingModel {

    private SimpleStringProperty bookingDate;
    private SimpleStringProperty bookingComment;
    private SimpleIntegerProperty bookingGuests;
    private SimpleStringProperty bookingTime;
    private SimpleStringProperty bookingPurpose;
    private SimpleIntegerProperty bookingID;
    private SimpleIntegerProperty roomID;
    private SimpleIntegerProperty userID;

    public BookingModel(String date, String comment, int guests, String time, String purpose, int id, int idRoom, int idUser) { //CONSTRUCTOR
        User user = new User();
        this.bookingDate = new SimpleStringProperty(date);
        this.bookingComment = new SimpleStringProperty(comment);
        this.bookingGuests = new SimpleIntegerProperty(guests);
        this.bookingTime = new SimpleStringProperty(time);
        this.bookingPurpose = new SimpleStringProperty(purpose);
        this.bookingID = new SimpleIntegerProperty(id);
        this.roomID = new SimpleIntegerProperty(idRoom);
        this.userID = new SimpleIntegerProperty(idUser);
    }

    // <-- ALL FUNCTION GETTERS AND SETTERS -->

    public int getRoomID() {
        return roomID.get();
    }

    public int getUserID() {
        return userID.get();
    }

    public void setRoomID(int roomID) {
        this.roomID.set(roomID);
    }

    public void setUserID(int userID) {
        this.userID.set(userID);
    }

    public void setBookingComment(String bookingComment) {
        this.bookingComment.set(bookingComment);
    }

    public void setBookingDate(String bookingDate) {
        this.bookingDate.set(bookingDate);
    }

    public void setBookingID(int bookingID) {
        this.bookingID.set(bookingID);
    }

    public void setBookingGuests(int bookingGuests) {
        this.bookingGuests.set(bookingGuests);
    }

    public void setBookingTime(String bookingTime) {
        this.bookingTime.set(bookingTime);
    }

    public void setBookingPurpose(String bookingPurpose) {
        this.bookingPurpose.set(bookingPurpose);
    }

    public String getBookingDate() {
        return bookingDate.get();
    }

    public int getBookingGuests() {
        return bookingGuests.get();
    }

    public String getBookingComment() {
        return bookingComment.get();
    }

    public int getBookingID() {
        return bookingID.get();
    }

    public String getBookingPurpose() {
        return bookingPurpose.get();
    }

    public String getBookingTime() {
        return bookingTime.get();
    }

    // <--------------------------------------------------------------------------------->

}
