package IA;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

public class User {

    // <-- DECLARE DATA MEMBERS -->
    private String firstName;
    private String lastName;
    private byte userYear;
    public String userClass;
    public long userSchoolID;
    public int userID;
    public String userComment;
    // <------------------------->

    // <-- DECLARE GET AND SET FUNCTIONS FOR DATA MEMBERS -->

    public String getFirstName(User user) {
        return user.firstName;
    }

    public int getUserID() {
        return userID;
    }

    public byte getUserYear() {
        return userYear;
    }

    public long getUserSchoolID() {
        return userSchoolID;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getUserClass() {
        return userClass;
    }

    public String getUserComment() {
        return userComment;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setUserClass(String userClass) {
        this.userClass = userClass;
    }

    public void setUserComment(String userComment) {
        this.userComment = userComment;
    }

    public void setUserSchoolID(long userSchoolID) {
        this.userSchoolID = userSchoolID;
    }

    public void setUserYear(byte userYear) {
        this.userYear = userYear;
    }

    // <---------------------------------------------------->

    public void updateUser(User user, int id) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://" + User.getIPAddress() + ":3306/CSIA", "myuser", "testpassword");
            Statement stmt = con.createStatement();
            stmt.executeUpdate("UPDATE `User` SET `userFirstName`= \"" + user.getFirstName() + "\",`userLastName`= \"" + user.getLastName() + "\",`userYear`= \"" + user.getUserYear() + "\",`userClass`= \"" + user.getUserClass() + "\",`userSchoolID`= \"" + user.getUserSchoolID() + "\",`userID`= \"" + user.getUserID() + "\",`userComment`= \"" + user.getUserComment() + "\" WHERE `userID`= \"" + id + "\";");
            con.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public String createReport(UserModel user) {
        String contents = "";
        int monCounter = 0;
        int tueCounter = 0;
        int wedCounter = 0;
        int thurCounter = 0;
        int friCounter = 0;
        int counter = 0;
        Rooms tempRooms = new Rooms();
        ArrayList<Rooms> allRooms = tempRooms.getAllRooms();
        ArrayList<Integer> roomsGoneToCounter = new ArrayList<>();
        for(int i = 0; i < allRooms.size(); i++) {
            roomsGoneToCounter.add(0);
        }
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://" + User.getIPAddress() + ":3306/CSIA", "myuser", "testpassword");
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM `Booking` WHERE `userID` = \" " + user.getIDUser() + "\"");

            while(rs.next()) {
                counter++;
                for(int i = 0; i < allRooms.size(); i++) {
                    if(rs.getString("roomID").equals(allRooms.get(i).getRoomID())) {
                        int temp = roomsGoneToCounter.get(i) + 1;
                        roomsGoneToCounter.set(i, temp);

                    }
                }
                String date = rs.getString("bookingDate");
                switch(date.substring(0,2)) {
                    case "Mon":
                        monCounter++;
                        break;
                    case "Tue":
                        tueCounter++;
                        break;
                    case "Wed":
                        wedCounter++;
                        break;
                    case "Thu":
                        thurCounter++;
                        break;
                    case "Fri":
                        friCounter++;
                        break;
                }
            }

        } catch(Exception ex) {
            ex.printStackTrace();
        }

        ArrayList<Integer> dayCounters = new ArrayList<>();
        dayCounters.add(monCounter);
        dayCounters.add(tueCounter);
        dayCounters.add(wedCounter);
        dayCounters.add(thurCounter);
        dayCounters.add(friCounter);

       String mostVisitedDay = findMostVisitedDay(dayCounters);

        int highestCounter = 0;
        int index = 0;

        for(int i = 0; i < allRooms.size(); i++) {
            if(roomsGoneToCounter.get(i) > highestCounter) {
                highestCounter = roomsGoneToCounter.get(i);
                index = i;
            }
        }

        String roomVisitedMost = allRooms.get(index).getRoomLocation();

        contents = "The number of bookings made are: " + counter + "\nThe room most visited is: " + roomVisitedMost + "\nThe day most visited is: " + mostVisitedDay;

        return contents;
    }

    public int getNumYear13s() {
        int counter = 0;
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://" + User.getIPAddress() + ":3306/CSIA", "myuser", "testpassword");
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM `User` WHERE `userYear` = 13");

            while(rs.next()) {
                counter++;
            }

        } catch(Exception ex) {
            ex.printStackTrace();
        }
        return counter;
    }

    public void deleteUsersBookings() {

    }

    public void removeYear(int year) { //START FUNCTION (TAKES AN INT, THE YEAR GROUP BEING DELETED)
        try{//START TRY
            Class.forName("com.mysql.jdbc.Driver"); //INITIALIZATION OF DRIVER CLASS
            Connection con = DriverManager.getConnection("jdbc:mysql://" + User.getIPAddress() + ":3306/CSIA", "myuser", "testpassword");
            //CONNECTION SECURED
            Statement stmt = con.createStatement(); //CREATES A STATEMENT FOR SQL INPUT
            stmt.executeUpdate("DELETE FROM `User` WHERE `userYear` = " + year + ""); //DELETE COMMAND EXECUTED
            con.close();//CLOSES CONNECTION WITH DATABASE
        } catch(Exception ex) {
            ex.printStackTrace();
        }//END TRY
    }//END FUNCTION
    public String findMostVisitedDay(ArrayList<Integer> counters) {
        int temp = 0;
        for(int i = 0; i<counters.size(); i++) {
            if(counters.get(i) > temp) {
                temp = i;
            }
        }
        switch(temp) {
            case 0:
                return "Monday";

            case 1:
                return "Tuesday";

            case 2:
                return "Wednesday";

            case 3:
                return "Thursday";

            case 4:
                return "Friday";

        }
        return "null";
    }

    public void decreaseYearForAllUsers() {
        System.out.println("test 1 reached");
        try{
            System.out.println("test 2 reached");
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://" + User.getIPAddress() + ":3306/CSIA", "myuser", "testpassword");
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM `User` WHERE 1");
            System.out.println("test 3 reached: " + rs.next());
            ArrayList<User> updatedUsers = new ArrayList<>();
            do{
                User currentUser = new User();
                currentUser.setFirstName(rs.getString("userFirstName"));
                currentUser.setLastName(rs.getString("userLastName"));

                currentUser.setUserID(rs.getInt("userID"));
                currentUser.setUserSchoolID(rs.getInt("userSchoolID"));
                currentUser.setUserComment(rs.getString("userComment"));

                int currentYear = rs.getInt("userYear");
                String tutorGroup = rs.getString("userClass");
                int newYear = currentYear - 1;
                String newTutorGroup = newYear + tutorGroup.substring(tutorGroup.length()-1, tutorGroup.length());

                currentUser.setUserYear((byte) newYear);
                currentUser.setUserClass(newTutorGroup);
                updatedUsers.add(currentUser);


            }while(rs.next());

            for(int i = 0; i<updatedUsers.size(); i++) {
                stmt.executeUpdate("UPDATE `User` SET `userFirstName`= \"" + updatedUsers.get(i).getFirstName() + "\",`userLastName`= \"" + updatedUsers.get(i).getLastName() + "\",`userYear`= \"" + updatedUsers.get(i).getUserYear() + "\",`userClass`= \"" + updatedUsers.get(i).getUserClass() + "\",`userSchoolID`= \"" + updatedUsers.get(i).getUserSchoolID() + "\",`userID`= \"" + updatedUsers.get(i).getUserID() + "\",`userComment`= \"" + updatedUsers.get(i).getUserComment() + "\" WHERE `userID`= \"" + updatedUsers.get(i).getUserID() + "\";");
            }

        } catch(Exception ex) {

        }

    }

    public void removeBookingsYear(int year) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://" + User.getIPAddress() + ":3306/CSIA", "myuser", "testpassword");
            Statement stmt = con.createStatement();

            ResultSet resultSet = stmt.executeQuery("SELECT * FROM `User` WHERE `userYear` = " + year + " ");
            ArrayList<Integer> IDS = new ArrayList<>();
            resultSet.first();
                do {
                    IDS.add(resultSet.getInt("userID"));

                } while (resultSet.next());



            for(int i = 0; i < IDS.size(); i++) {
                stmt.executeUpdate("DELETE FROM `Booking` WHERE `userID` = '" + IDS.get(i) + "'");
            }
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }


    public void incrementYearForAllUsers() {
        System.out.println("test 1 reached");
        try{
            System.out.println("test 2 reached");
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://" + User.getIPAddress() + ":3306/CSIA", "myuser", "testpassword");
            Statement stmt = con.createStatement();



            ResultSet rs = stmt.executeQuery("SELECT * FROM `User` WHERE 1");
            System.out.println("test 3 reached: " + rs.next());
            ArrayList<User> updatedUsers = new ArrayList<>();
            do{
                User currentUser = new User();
                currentUser.setFirstName(rs.getString("userFirstName"));
                currentUser.setLastName(rs.getString("userLastName"));

                currentUser.setUserID(rs.getInt("userID"));
                currentUser.setUserSchoolID(rs.getInt("userSchoolID"));
                currentUser.setUserComment(rs.getString("userComment"));

                int currentYear = rs.getInt("userYear");
                String tutorGroup = rs.getString("userClass");
                int newYear = currentYear+ 1;
                String newTutorGroup = newYear + tutorGroup.substring(tutorGroup.length()-1, tutorGroup.length());

                currentUser.setUserYear((byte) newYear);
                currentUser.setUserClass(newTutorGroup);
                updatedUsers.add(currentUser);


            }while(rs.next());

            for(int i = 0; i<updatedUsers.size(); i++) {
                stmt.executeUpdate("UPDATE `User` SET `userFirstName`= \"" + updatedUsers.get(i).getFirstName() + "\",`userLastName`= \"" + updatedUsers.get(i).getLastName() + "\",`userYear`= \"" + updatedUsers.get(i).getUserYear() + "\",`userClass`= \"" + updatedUsers.get(i).getUserClass() + "\",`userSchoolID`= \"" + updatedUsers.get(i).getUserSchoolID() + "\",`userID`= \"" + updatedUsers.get(i).getUserID() + "\",`userComment`= \"" + updatedUsers.get(i).getUserComment() + "\" WHERE `userID`= \"" + updatedUsers.get(i).getUserID() + "\";");
            }

        } catch(Exception ex) {

        }

    }

    public User createUser(int userID) { // MAKE THIS A FUNCTION IN THE USER CLASS
        User loggedInUser = new User();
        //connect to DATABASE and then create an instance of User so we know whos booking the room etc
        loggedInUser.setUserID(userID);

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://" + User.getIPAddress() + ":3306/CSIA", "myuser", "testpassword");
            System.out.println("Successfully connected");
            System.out.println("Here are the Users in the database");
            Statement stmt = con.createStatement();

            ResultSet rs = stmt.executeQuery("SELECT `userFirstName` FROM `User` WHERE `userID`= \"" + userID + "\"");
            if(rs.first()) {
                loggedInUser.setFirstName(rs.getString(1));
            }

            rs = stmt.executeQuery("SELECT `userLastName` FROM `User` WHERE `userID`= \"" + userID + "\"");
            if(rs.first()) {
                loggedInUser.setLastName(rs.getString("userLastName"));
            }
            rs = stmt.executeQuery("SELECT `userYear` FROM `User` WHERE `userID`= \"" + userID + "\"");
            if(rs.first()) {
                loggedInUser.setUserYear((byte) rs.getInt(1));
            }
            rs = stmt.executeQuery("SELECT `userClass` FROM `User` WHERE `userID`= \"" + userID + "\"");
            if(rs.first()) {
                loggedInUser.setUserClass(rs.getString(1));
            }
            rs = stmt.executeQuery("SELECT `userSchoolID` FROM `User` WHERE `userID`= \"" + userID + "\"");
            if(rs.first()) {
                loggedInUser.setUserSchoolID((long) rs.getInt(1));
            }
            rs = stmt.executeQuery("SELECT `userComment` FROM `User` WHERE `userID`= \"" + userID + "\"");
            if(rs.first()) {
                loggedInUser.setUserComment(rs.getString(1));
            }

        }catch(ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
        }
        return loggedInUser;
    }

    public boolean checkUserIDExist(int id) {
        boolean check = false;

        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://" + User.getIPAddress() + ":3306/CSIA", "myuser", "testpassword");
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM `User` WHERE `userID` = \" " + id + "\"");

            if(rs.next() == false) {
                return false;
            }

        } catch(Exception ex) {
            ex.printStackTrace();
        }
        return true;
    }

    public static String getIPAddress() {
        String line; //
        String fileName = "ipAddressToConnect.txt";
        String temp = "";

        try {
            BufferedReader in = new BufferedReader(new FileReader(fileName)); //creative a variable of the type BufferedReader. "in" is your file that you've selected in fileName.

            line = in.readLine(); //the string line is one line from the file "in".
            while (line != null)  // while not end of file. While the file hasn't reached the end of the file
            {
                temp += line;
                line = in.readLine(); //moves onto the next line.
            }
            in.close(); //closes textfile
        } //end try
        catch (IOException iox) {//catch statement.  It can give errors. If a function doesn't throw an IOException, you must use a try catch statement when calling function in main.
            iox.printStackTrace();
        }
        return temp;
    }




    public int getLoggedInUserID() {
        int id;
        String line; //
        String fileName = "loggedInUser.txt";
        String temp = "";

        try {
            BufferedReader in = new BufferedReader(new FileReader(fileName)); //creative a variable of the type BufferedReader. "in" is your file that you've selected in fileName.

            line = in.readLine(); //the string line is one line from the file "in".
            while (line != null)  // while not end of file. While the file hasn't reached the end of the file
            {
                temp += line;
                line = in.readLine(); //moves onto the next line.
            }
            in.close(); //closes textfile
        } //end try
        catch (IOException iox) {//catch statement.  It can give errors. If a function doesn't throw an IOException, you must use a try catch statement when calling function in main.
            iox.printStackTrace();
        }
        System.out.println(temp);
        if(temp.equals("")) {
            return 1;
        }
        return Integer.parseInt(temp);
    }

    public String convertTime(String input) { //CONVERTS TIME FROM ONE FORMAT TO ANOTHER
        String beginningTime = input.substring(0,2);
        int intBeginningTime = Integer.parseInt(beginningTime);
        System.out.println("this is beginning time: " + beginningTime);

        String Duration = input.substring(2,4);
        int intDuration = Integer.parseInt(Duration);
        System.out.println("this is duration time: " + Duration);

        beginningTime = "12:" + beginningTime;

        int sum = intBeginningTime + intDuration;

        System.out.println("this is the sum: " + sum);

        if(sum == 60) {
            return beginningTime + " -> 13:00";
        } else {
            return beginningTime + " -> 12:" + sum;
        }


    }

}
