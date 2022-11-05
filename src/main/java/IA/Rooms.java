package IA;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Rooms extends User { //START OF CLASS

    // <-- DECLARE DATA MEMBERS -->
    private int numRooms;
    private int numBooked;
    private int numAvailable;
    private String roomDesc;
    private String roomInstruments;
    private int roomID;
    private String roomLocation;
    private int roomCapacity;
    private String roomComment;
    // <-------------------------->


    private File roomImage;

    private List<String> roomNames = new ArrayList<String>();

    //<MEMBERS FUNCTIONS>

    public void setRoomImage(File roomImage) {
        this.roomImage = roomImage;
    }

    public File getRoomImage() {
        return roomImage;
    }

    public int getRoomID() {
        return roomID;
    }

    public int getNumAvailable() {
        return numAvailable;
    }

    public int getNumBooked() {
        return numBooked;
    }

    public int getRoomCapacity() {
        return roomCapacity;
    }

    public String getRoomComment() {
        return roomComment;
    }

    public String getRoomDesc() {
        return roomDesc;
    }

    public String getRoomInstruments() {
        return roomInstruments;
    }

    public String getRoomLocation() {
        return roomLocation;
    }

    public void setRoomID(int roomID) {
        this.roomID = roomID;
    }

    public void setNumAvailable(int numAvailable) {
        this.numAvailable = numAvailable;
    }

    public void setNumBooked(int numBooked) {
        this.numBooked = numBooked;
    }

    public void setNumRooms(int numRooms) {
        this.numRooms = numRooms;
    }

    public void setRoomCapacity(int roomCapacity) {
        this.roomCapacity = roomCapacity;
    }

    public void setRoomComment(String roomComment) {
        this.roomComment = roomComment;
    }

    public void setRoomDesc(String roomDesc) {
        this.roomDesc = roomDesc;
    }

    public void setRoomInstruments(String roomInstruments) {
        this.roomInstruments = roomInstruments;
    }

    public void setRoomLocation(String roomLocation) {
        this.roomLocation = roomLocation;
    }

    public void setRoomNames(List<String> roomNames) {
        this.roomNames = roomNames;
    }

    // <-------------->

    public int getNumRooms() {
        updateNumRooms();

        return numRooms;
    }

    public void updateRoom(Rooms rooms, int id) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://" + User.getIPAddress() + ":3306/CSIA", "myuser", "testpassword");
            Statement stmt = con.createStatement();
            stmt.executeUpdate("UPDATE `Room` SET `roomDesc`= \"" + rooms.getRoomDesc() + "\",`roomInstruments`= \"" + rooms.getRoomInstruments() + "\",`roomID`= \"" + id + "\",`roomLocation`= \"" + rooms.getRoomLocation() + "\",`roomCapacity`= \"" + rooms.getRoomCapacity() + "\",`roomComment`= \"" + rooms.getRoomComment() + "\" WHERE `roomID`= \"" + id + "\";");
            con.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void updateRoomWithImages(Rooms rooms, int id) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://" + User.getIPAddress() + ":3306/CSIA", "myuser", "testpassword");
            PreparedStatement stmt = con.prepareStatement("UPDATE `Room` SET `roomDesc` = ?, `roomInstruments` = ?, `roomID` = ?, `roomLocation` = ?, `roomCapacity` = ?, " +
                    "`roomComment` = ?, `roomPicture` = ? WHERE `roomID` = " + id + ";");
            FileInputStream temp = new FileInputStream(rooms.getRoomImage());
//            con.setAutoCommit(false);
            stmt.setString(1, rooms.getRoomDesc());
            stmt.setString(2, rooms.getRoomInstruments());
            stmt.setInt(3, id);
            stmt.setString(4, rooms.getRoomLocation());
            stmt.setInt(5, rooms.getRoomCapacity());
            stmt.setString(6,rooms.getRoomComment());
            stmt.setBinaryStream(7, (InputStream)temp, (int)(rooms.getRoomImage().length()));
            System.out.println(stmt.toString());
            stmt.executeLargeUpdate();
//            con.commit();
            stmt.close();
            con.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public List<String> getRoomNames() {
        updateRoomNameList();
        return roomNames;
    }

    private void updateRoomNameList() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://" + User.getIPAddress() + ":3306/CSIA", "myuser", "testpassword");
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM `Room`");

            while(rs.next()) {
                String name = rs.getString("roomLocation");
                roomNames.add(name);
            }
            stmt.close();

        }catch(ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
        }

    }

    private void updateNumRooms()  {
        int counter = 0;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://" + User.getIPAddress() + ":3306/CSIA", "myuser", "testpassword");
            System.out.println("Successfully connected");
            System.out.println("Here are the Users in the database");
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM `Room`");

            while(rs.next()) {
                counter++;

            }

        }catch(ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
        }

        numRooms = counter;
    }

    public ArrayList<Rooms> getAllRooms() {
        int numRooms = 0;

        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://" + User.getIPAddress() + ":3306/CSIA", "myuser", "testpassword");
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT COUNT(*) AS NumberOfRooms FROM Room");

            while(rs.next()) {
                numRooms = rs.getInt("NumberOfRooms");
            }
        } catch(Exception ex) {
            ex.printStackTrace();
        }




        ArrayList<Rooms> allRoomsList = new ArrayList<Rooms>();
        ArrayList<String> roomDescList = new ArrayList<String>();
        ArrayList<String> roomInstrumentList = new ArrayList<String>();
        ArrayList<Integer> roomIDList = new ArrayList<Integer>();
        ArrayList<String> roomLocationList = new ArrayList<String>();
        ArrayList<Integer> roomCapacityList = new ArrayList<Integer>();
        ArrayList<String> roomCommentList = new ArrayList<String>();

        int counter = 0;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://" + User.getIPAddress() + ":3306/CSIA", "myuser", "testpassword");
            System.out.println("Successfully connected");
            System.out.println("Here are the Users in the database");
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM `Room`");

            while(rs.next()) {
                roomDescList.add(rs.getString("roomDesc"));
                counter++;
            }
            rs = stmt.executeQuery("SELECT * FROM `Room`");

            while(rs.next()) {
                roomInstrumentList.add(rs.getString("roomInstruments"));
                counter++;
            }

            rs = stmt.executeQuery("SELECT * FROM `Room`");

            while(rs.next()) {
                roomIDList.add(rs.getInt("roomID"));
                counter++;
            }

            rs = stmt.executeQuery("SELECT * FROM `Room`");

            while(rs.next()) {
                roomLocationList.add(rs.getString("roomLocation"));
                counter++;
            }

            rs = stmt.executeQuery("SELECT * FROM `Room`");

            while(rs.next()) {
                roomCapacityList.add(rs.getInt("roomCapacity"));
                counter++;
            }

            rs = stmt.executeQuery("SELECT * FROM `Room`");

            while(rs.next()) {
                roomCommentList.add(rs.getString("roomComment"));
                counter++;
            }

            for(int i = 0;i < numRooms; i++) {
                Rooms tempRoom = new Rooms();
                tempRoom.setRoomDesc(roomDescList.get(i));
                tempRoom.setRoomInstruments(roomInstrumentList.get(i));
                tempRoom.setRoomID(roomIDList.get(i));
                tempRoom.setRoomLocation(roomLocationList.get(i));
                tempRoom.setRoomCapacity(roomCapacityList.get(i));
                tempRoom.setRoomComment(roomCommentList.get(i));
                allRoomsList.add(tempRoom);
            }
        }catch(ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
        }
        return allRoomsList;
    }

    public int getNumFullRooms(int day) {
        int fullRooms = 0;
        Calendar calendar = Calendar.getInstance();

        switch(day) {
            case 1:
                calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
                String date = calendar.getTime().toString().substring(0,10);


                fullRooms = 1;
                break;
        }

        return fullRooms;
    }

    public int getRoomCapacity(int roomID) {
        int capacity = 0;
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://" + User.getIPAddress() + ":3306/CSIA", "myuser", "testpassword");
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM `Room` WHERE `roomID` = \" " + roomID + "\"");

            while(rs.next()) {
                capacity = rs.getInt("roomCapacity");
            }

        } catch(Exception ex) {
            ex.printStackTrace();
        }
        return capacity;
    }




    public String getRoomLocation(int roomID) {
        String roomLocation = "";
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://" + User.getIPAddress() + ":3306/CSIA", "myuser", "testpassword");
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM `Room` WHERE `roomID` = \" " + roomID + "\"");

            while(rs.next()) {
                roomLocation = rs.getString("roomLocation");
            }

        } catch(Exception ex) {
            ex.printStackTrace();
        }
        return roomLocation;
    }

}
