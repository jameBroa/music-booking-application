package IA;


import javafx.scene.control.Alert;

import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Random;

public class Connector {

    private static String adminUsername, adminPassword;
    private static List<Integer> allUserIDS = new ArrayList<Integer>();
    private static List<Integer> allRoomIDS = new ArrayList<Integer>();
    private static List<Integer> allBookingIDS = new ArrayList<Integer>();

    public static List<Integer> getAllUserIds() {
        return allUserIDS;
    }

    public static List<Integer> getAllRoomIds() {
        return allRoomIDS;
    }

    public static List<Integer> getAllBookingIds() {return allBookingIDS; }


    public static void main(String args[]) {
        Connector pro = new Connector();
        //createUser();
       // updateUserIDList();
        System.out.println(allUserIDS);
    }

    public static String getAdminUsername() {
        return adminUsername;
    }
    public static String getAdminPassword() {
        return adminPassword;
    }


    public static void setAdminDetails() { //function will set the details of username and password. Should be ran on launch

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://" + User.getIPAddress() + ":3306/CSIA", "myuser", "testpassword");
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM `Admin`");
            while(rs.next()) {
                adminUsername = rs.getString("adminUsername");
                adminPassword = rs.getString("adminPassword");
            }

        }catch(Exception ex){
            ex.printStackTrace();
        }



    }

    public static void printUsers() { //this function just prints the names of all users
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://" + User.getIPAddress() + ":3306/CSIA", "myuser", "testpassword");
            System.out.println("Successfully connected");
            System.out.println("Here are the Users in the database");
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM `User`");

            while(rs.next()) {
                String name = rs.getString("userFirstName");
                System.out.println(name);

            }

        }catch(ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
        }
    }

    //this function updates the list which contains all IDS of a class. This is done to prevent duplicate IDs.
    static void updateList(String listType, List<Integer> listIds, String columnLabel) {
        listIds.clear(); //clears current arraylist of ID's depending on the list
        try {
            Class.forName("com.mysql.jdbc.Driver"); //creates instance of driver class
            //creates connection to database
            Connection con = DriverManager.getConnection("jdbc:mysql://" + User.getIPAddress() + ":3306/CSIA", "myuser", "testpassword");
            Statement stmt = con.createStatement(); //creates statement
            ResultSet rs = stmt.executeQuery("SELECT * FROM `" + listType + "`"); //queries database

            while(rs.next()) {//start while
                int id = rs.getInt(columnLabel);//sets ID from database
                listIds.add(id);
            }//end while
            stmt.close();

        }catch(ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
        }
    }

    //generates a random ID to assign to a table
    static int generateID(List<Integer> listIds, String listType, String columnLabel) {
        updateList(listType, listIds, columnLabel); //updates arraylist of information from database
        Random rand = new Random(); //creates instance of type Random
        boolean check = true; //boolean check
        int tempID;

        do {//start do
            tempID = rand.nextInt((99999 - 10000) + 1) + 10000; //generate random number
            check = checkIDExist(tempID, listIds); //check if ID exists within arraylist (if doesn't it passes)
        } while(check != false);
        //System.out.println(tempID);
        return tempID; //ID generated

    }


    static boolean checkIDExist(int id, List<Integer> listIds) { //checks if an ID exist and returns true or false
        for(int i = 0; i<listIds.size(); i++) { //START FOR LOOP
            if(listIds.get(i) == id) { //START IF
                return true;
            }//END IF
        }// END FOR
        return false;
    }//END FUNCTION

    static void createUser(String fName, String lName, int year, String tutorGroup, int schoolID, int userID, String userComment) { //function that adds an entry into the user database


        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://" + User.getIPAddress() + ":3306/CSIA", "myuser", "testpassword");
            Statement stmt = con.createStatement();
            String dbop = ("INSERT INTO `User` (`userFirstName`, `userLastName`, `userYear`, `userClass`, `userSchoolID`, `userID`, `userComment`) VALUES ('" + fName + "', '" + lName + "', '" + year + "', '" + tutorGroup + "', '" + schoolID + "', '" + userID + "', '" + userComment + "');");
            stmt.execute(dbop);
            stmt.close();
        }catch(Exception ex) {
            ex.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("CREATION");
            alert.setHeaderText("Please take note:");
            alert.setContentText("We were unable to add you to the database, email jabr21@patana.ac.th for help");
            alert.showAndWait();
        }
    }

    static void createRoom(Rooms currentRoom) { //function that adds an entry into the user database




        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://" + User.getIPAddress() + ":3306/CSIA", "myuser", "testpassword");
            PreparedStatement stmt = con.prepareStatement("INSERT INTO `Room` (`roomDesc`, `roomInstruments`, `roomID`, `roomLocation`, `roomCapacity`, `roomComment`, `roomPicture`) VALUES (?,?,?,?,?,?,?);");
            stmt.setString(1, currentRoom.getRoomDesc());
            stmt.setString(2, currentRoom.getRoomInstruments());
            stmt.setInt(3, currentRoom.getRoomID());
            stmt.setString(4, currentRoom.getRoomLocation());
            stmt.setInt(5, currentRoom.getRoomCapacity());
            stmt.setString(6, currentRoom.getRoomComment());
            FileInputStream temp = new FileInputStream(currentRoom.getRoomImage());
            stmt.setBinaryStream(7, (InputStream)temp, (int)(currentRoom.getRoomImage().length()));
            stmt.executeUpdate();
            stmt.close();
        }catch(Exception ex) {
            ex.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("CREATION");
            alert.setHeaderText("Please take note:");
            alert.setContentText("We were unable this room  to the database, email jabr21@patana.ac.th for help");
            alert.showAndWait();
        }
    }

    static void createBooking(Booking newBooking) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://" + User.getIPAddress() + ":3306/CSIA", "myuser", "testpassword");
            Statement stmt = con.createStatement();
            String dbop = ("INSERT INTO `Booking` (`bookingTime`, `bookingDate`, `bookingPurpose`, `bookingID`, `bookingComment`, `userID`, `roomID`, `bookingGuests`) VALUES ('" + newBooking.getBookingTime() + "', '" + newBooking.getBookingDate() + "', '" + newBooking.getBookingPurpose() + "', '" + newBooking.getBookingID() + "', '" + newBooking.getBookingComment() + "', '" + newBooking.getUserID() + "', '" + newBooking.getRoomID() + "', '" + newBooking.getBookingGuests() + "');");
            stmt.execute(dbop);
            stmt.close();
        }catch(Exception ex) {
            ex.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("CREATION");
            alert.setHeaderText("Please take note:");
            alert.setContentText("We were unable this booking to the database, email jabr21@patana.ac.th for help");
            alert.showAndWait();
        }
    }

    static void addYearClasses(ArrayList<String> year7, ArrayList<String> year8, ArrayList<String> year9, ArrayList<String> year10, ArrayList<String> year11, ArrayList<String> year12, ArrayList<String> year13 ) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://" + User.getIPAddress() + ":3306/CSIA", "myuser", "testpassword");
            Statement stmt = con.createStatement();

            for(int i = 0; i < year7.size(); i++) {
                String dbop = ("INSERT INTO `YearClasses` (`Yr7`, `Yr8`, `Yr9`, `Yr10`, `Yr11`, `Yr12`, `Yr13`) VALUES ('" + year7.get(i) + "', '" + year8.get(i) + "', '" + year9.get(i) + "', '" + year10.get(i) + "', '" + year11.get(i) + "', '" + year12.get(i) + "', '" + year13.get(i) + "');");
                stmt.execute(dbop);
            }
            stmt.close();
        }catch(Exception ex) {
            ex.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("CREATION");
            alert.setHeaderText("Please take note:");
            alert.setContentText("We were unable this booking to the database, email jabr21@patana.ac.th for help");
            alert.showAndWait();
        }
    }

    static void deleteYearClasses() {
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://" + User.getIPAddress() + ":3306/CSIA", "myuser", "testpassword");
            Statement stmt = con.createStatement();
            stmt.executeUpdate("DELETE FROM `YearClasses` WHERE 1");
            con.close();
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }

    static void editTutorGroup(int yearGroup, String replaced, String replacer) {

        // CHANGES CLASSES ON YEARCLASSES TABLE
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://" + User.getIPAddress() + ":3306/CSIA", "myuser", "testpassword");
            Statement stmt = con.createStatement();
            String dbop = "UPDATE `YearClasses` SET `Yr" + yearGroup + "` = \"" + replacer + "\" WHERE `Yr" + yearGroup + "` = \"" + replaced + "\"";
            System.out.println(dbop);
            stmt.execute(dbop);
            dbop = "UPDATE `User` SET `userClass` = \"" + replacer + "\" WHERE `userClass`= \"" + replaced + "\"";
            stmt.execute(dbop);
            stmt.close();
        }catch(Exception ex) {
            ex.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("CREATION");
            alert.setHeaderText("Please take note:");
            alert.setContentText("We were unable this booking to the database, email jabr21@patana.ac.th for help");
            alert.showAndWait();
        }
    }

    static void addTutorGroup(String year7, String year8, String year9, String year10, String year11, String year12, String year13) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://" + User.getIPAddress() + ":3306/CSIA", "myuser", "testpassword");
            Statement stmt = con.createStatement();
            String dbop = ("INSERT INTO `YearClasses` (`Yr7`, `Yr8`, `Yr9`, `Yr10`, `Yr11`, `Yr12`, `Yr13`) VALUES ('" + year7 + "', '" + year8 + "', '" + year9 + "', '" + year10 + "', '" + year11 + "', '" + year12 + "', '" + year13 + "');");
            stmt.execute(dbop);
            stmt.close();
        }catch(Exception ex) {
            ex.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("CREATION");
            alert.setHeaderText("Please take note:");
            alert.setContentText("We were unable this booking to the database, email jabr21@patana.ac.th for help");
            alert.showAndWait();
        }
    }





}


