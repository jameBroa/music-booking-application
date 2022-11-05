package IA;

import java.sql.*;
import java.util.ArrayList;

public class Booking extends Rooms {


    // <-- INITIALIZING VARIABLES -->
    private String bookingDate;
    private String reportMessage;
    private String bookingComment;
    private int bookingGuests;
    private String bookingTime;
    private String bookingPurpose;
    private int bookingID;
    // <---------------------------->



    private int startingTime = 1215; //TODO CHECK IF I NEED THIS


    // <---- SETTERS & GETTERS ---->



    public int determineBookingValue(int duration) {
        switch(duration) {
            case 15:
                return 1;

            case 30:
                return 2;

            case 45:
                return 3;


        }
        return 0;
    }

    public void updateBooking(Booking booking, int id) { //FUNCTION TO UPDATE BOOKING
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://" + User.getIPAddress() + ":3306/CSIA", "myuser", "testpassword");
            Statement stmt = con.createStatement();
            stmt.executeUpdate("UPDATE `Booking` SET `bookingTime`= \"" + booking.getBookingTime() + "\",`bookingDate`= \"" + booking.getBookingDate() + "\",`bookingPurpose`= \"" + booking.getBookingPurpose() + "\",`bookingID`= \"" + id + "\",`bookingComment`= \"" + booking.getBookingComment() + "\",`userID`= \"" + booking.getUserID() + "\",`roomID`= \"" + booking.getRoomID() + "\",`bookingGuests`= \"" + booking.getBookingGuests() + "\" WHERE `bookingID`= \"" + id + "\";");
            con.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // <-- GETTERS AND SETTER FUNCTIONS -->

    public void setBookingPurpose(String bookingPurpose) {
        this.bookingPurpose = bookingPurpose;
    }

    public void setBookingDate(String bookingDate) {
        this.bookingDate = bookingDate;
    }

    public void setBookingComment(String bookingComment) {
        this.bookingComment = bookingComment;
    }

    public void setBookingGuests(int bookingGuests) {
        this.bookingGuests = bookingGuests;
    }

    public void setBookingTime(String bookingTime) {
        this.bookingTime = bookingTime;
    }

    public void setReportMessage(String reportMessage) {
        this.reportMessage = reportMessage;
    }

    public int getBookingID() {
        return bookingID;
    }

    public int getStartingTime() {
        return startingTime;
    }

    public void setStartingTime(int startingTime) {
        this.startingTime = startingTime;
    }

    public void setBookingID(int bookingID) {
        this.bookingID = bookingID;
    }

    public int getBookingGuests() {
        return bookingGuests;
    }


    public String getBookingComment() {
        return bookingComment;
    }

    public String getBookingDate() {
        return bookingDate;
    }

    public String getBookingPurpose() {
        return bookingPurpose;
    }

    public String getBookingTime() {
        return bookingTime;
    }

    public String getReportMessage() {
        return reportMessage;
    }

    // <---------------------------------->

    public ArrayList<Booking> getAllBooking() { //FUNCTION WHICH CREATES A LIST OF TYPE BOOKING OF ALL BOOKINGS IN THE DATABASE
            int numBookings = 0;

            try{
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection("jdbc:mysql://" + User.getIPAddress() + ":3306/CSIA", "myuser", "testpassword");
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT COUNT(*) AS NumberOfBookings FROM Booking");

                while(rs.next()) {
                    numBookings = rs.getInt("NumberOfBookings");
                }
            } catch(Exception ex) {
                ex.printStackTrace();
            }

            // <INITIALIZATION OF ARRAYLIST OF ALL DATAMEMBERS WITHIN A BOOKING>
            ArrayList<Booking> allBookingsList = new ArrayList<Booking>();
            ArrayList<String> bookingTimeList = new ArrayList<String>();
            ArrayList<String> bookingDateList = new ArrayList<String>();
            ArrayList<String> bookingPurposeList = new ArrayList<String>();
            ArrayList<Integer> bookingIDList = new ArrayList<Integer>();
            ArrayList<String> bookingCommentList = new ArrayList<String>();
            ArrayList<Integer> bookingUserIDList = new ArrayList<Integer>();
            ArrayList<Integer> bookingRoomIDList = new ArrayList<Integer>();
            ArrayList<Integer> bookingNumGuestsList = new ArrayList<Integer>();
            // <---------------------------------------------------------------->

            int counter = 0; //INITAILAITION OF A POINTER;
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection("jdbc:mysql://" + User.getIPAddress() + ":3306/CSIA", "myuser", "testpassword");
                System.out.println("Successfully connected");
                System.out.println("Here are the Users in the database");
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM `Booking`");

                while(rs.next()) {
                    bookingTimeList.add(rs.getString("bookingTime"));
                    counter++;
                }
                rs = stmt.executeQuery("SELECT * FROM `Booking`");

                while(rs.next()) {
                    bookingDateList.add(rs.getString("bookingDate"));
                    counter++;
                }

                rs = stmt.executeQuery("SELECT * FROM `Booking`");

                while(rs.next()) {
                    bookingPurposeList.add(rs.getString("bookingPurpose"));
                    counter++;
                }

                rs = stmt.executeQuery("SELECT * FROM `Booking`");

                while(rs.next()) {
                    bookingIDList.add(rs.getInt("bookingID"));
                    counter++;
                }

                rs = stmt.executeQuery("SELECT * FROM `Booking`");

                while(rs.next()) {
                    bookingCommentList.add(rs.getString("bookingComment"));
                    counter++;
                }

                rs = stmt.executeQuery("SELECT * FROM `Booking`");

                while(rs.next()) {
                    bookingUserIDList.add(rs.getInt("userID"));
                    counter++;
                }

                rs = stmt.executeQuery("SELECT * FROM `Booking`");

                while(rs.next()) {
                    bookingRoomIDList.add(rs.getInt("roomID"));
                    counter++;
                }

                rs = stmt.executeQuery("SELECT * FROM `Booking`");

                while(rs.next()) {
                    bookingNumGuestsList.add(rs.getInt("bookingGuests"));
                    counter++;
                }

                for(int i = 0;i < numBookings; i++) {//START FOR LOOP
                    Booking tempBooking = new Booking(); //INSTANTIATION OF A TEMPORARY BOOKING OBEJCT
                    //<-- SETTING THE DATAMEMBERS OF TEMPBOOKING AS VALUES WITHIN ARRAYLISTS -->
                    tempBooking.setBookingTime(bookingTimeList.get(i));
                    tempBooking.setBookingDate(bookingDateList.get(i));
                    tempBooking.setBookingPurpose(bookingPurposeList.get(i));
                    tempBooking.setBookingID(bookingIDList.get(i));
                    tempBooking.setBookingComment(bookingCommentList.get(i));
                    tempBooking.setUserID(bookingUserIDList.get(i));
                    tempBooking.setRoomID(bookingRoomIDList.get(i));
                    tempBooking.setBookingGuests(bookingNumGuestsList.get(i));
                    allBookingsList.add(tempBooking);
                    // <------------------------------------------------------->
                }//END FOR LOOP
            }catch(ClassNotFoundException | SQLException ex) {
                ex.printStackTrace();
            }
            return allBookingsList;

    }

    public int checkHowManyBookings(String date, int roomID) { //function which tells you how many bookings on a particular day
        int counter = 0;
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://" + User.getIPAddress() + ":3306/CSIA", "myuser", "testpassword");
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM `Booking` WHERE `bookingDate` = \"" + date + "\" AND `roomID` = \"" + roomID + "\"");

            while(rs.next()) {
                counter++;
            }
            return counter;
        } catch(Exception ex) {
            ex.printStackTrace();
        }
        return 0;

    }

    //FUNCTION BELOW CREATES AN ARRAYLIST OF TYPE BOOKING OF ALL BOOKINGS ON A SPECIFIC DAY IN A SPECIFIC ROOM
    public ArrayList<Booking> getBookingsFromDate(String date, int roomID) {

        // <--- INITIALIZES ARRAYLISTS --->
        ArrayList<Booking> bookingArrayList = new ArrayList<>();
        ArrayList<String> bookingTimeList = new ArrayList<>();
        ArrayList<String> bookingDateList = new ArrayList<>();
        ArrayList<String> bookingPurposeList = new ArrayList<>();
        ArrayList<Integer> bookingIDList = new ArrayList<>();
        ArrayList<String> bookingCommentList = new ArrayList<>();
        ArrayList<Integer> userIDList = new ArrayList<>();
        ArrayList<Integer> roomIDList = new ArrayList<>();
        ArrayList<Integer> bookingGuestsList = new ArrayList<>();

        // <------------------------------>

        // <--- CONNECT TO DATABASE AND QUERY --->
        try{
            Class.forName("com.mysql.jdbc.Driver"); //CREATES INSTANCE OF THE DRIVER CLASS
            Connection con = DriverManager.getConnection("jdbc:mysql://" + User.getIPAddress() + ":3306/CSIA", "myuser", "testpassword"); //SETS CONNECTION TO DATABASE
            Statement stmt = con.createStatement(); //CREATES STMT OBJECT
            ResultSet rs = stmt.executeQuery("SELECT * FROM `Booking` WHERE `bookingDate` = \"" + date + "\" AND `roomID` = \"" + roomID + "\""); //CREATES RESULTSET AND EXECUTES QUERY

            while(rs.next()) { //WHILE THERE IS ANOTHER ROW TO GO...
                // <-- ADDS DIFFERENT COLUMNS TO THEIR RESPECTIVE ARRAYLIST -->
                bookingTimeList.add(rs.getString("bookingTime"));
                bookingDateList.add(rs.getString("bookingDate"));
                bookingPurposeList.add(rs.getString("bookingPurpose"));
                bookingIDList.add(rs.getInt("bookingID"));
                bookingCommentList.add(rs.getString("bookingComment"));
                userIDList.add(rs.getInt("userID"));
                roomIDList.add(rs.getInt("roomID"));
                bookingGuestsList.add(rs.getInt("bookingGuests"));
                // <--------------------------------------------------------->
            }
        } catch(Exception ex) {
            ex.printStackTrace();
        }

        // ITERATES THROUGH ALL BOOKINGS AND CREATES AN INSTANCE OF TYPE BOOKING AND ADDS IT TO THE ARRAYLIST OF BOOKING FOR ALL BOOKINGS
        for(int i = 0; i <bookingTimeList.size(); i++) {// START OF FOR LOOP
            Booking tempBooking = new Booking(); //TEMPORARY INSTANCE OF TYPE BOOKING
            // <-- ADDS DATAMEMBER TO INSTANCE OF CLASS -->
            tempBooking.setBookingTime(bookingTimeList.get(i));
            tempBooking.setBookingDate(bookingDateList.get(i));
            tempBooking.setBookingPurpose(bookingPurposeList.get(i));
            tempBooking.setBookingID(bookingIDList.get(i));
            tempBooking.setBookingComment(bookingCommentList.get(i));
            tempBooking.setRoomID(roomIDList.get(i));
            tempBooking.setUserID(userIDList.get(i));
            tempBooking.setBookingGuests(bookingGuestsList.get(i));
            // <------------------------------------------->

            bookingArrayList.add(tempBooking); //ADDS TEMPORARY BOOKING TO ARRAYLIST OF BOOKING
        } //END OF FOR LOOP

        return bookingArrayList; //RETURNS FINAL ARRAYLIST OF ALL BOOKINGS
    }//END OF FUNCTION



}

