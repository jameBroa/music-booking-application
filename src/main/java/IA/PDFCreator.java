package IA;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.PdfXrefTable;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.AreaBreak;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.UnitValue;
import com.sun.javafx.font.FontFactory;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class PDFCreator {

    public void createSummaryOfGraduates(int numYear13s, String path) { //FUNCTION TO CREATE SUMMARY OF ALL YEAR13 STUDENTS
        try {
            PdfDocument pdf = new PdfDocument(new PdfWriter(path + "/Post Year 13s bookings.pdf"));
            Document document = new Document(pdf);
            ArrayList<Integer> userIDSFromYear13 = new ArrayList<>(); //CREATES ARRAYLIST FOR ALL USERS IN YEAR 13

                try{
                    //< FILTERS INFORMATION FROM DATABASE FOR USERS IN YEAR 13>
                    Class.forName("com.mysql.jdbc.Driver");
                    Connection con = DriverManager.getConnection("jdbc:mysql://" + User.getIPAddress() + ":3306/CSIA", "myuser", "testpassword");
                    Statement stmt = con.createStatement();
                    ResultSet rs = stmt.executeQuery("SELECT * FROM `User` WHERE `userYear` = 13");

                    while(rs.next()) {
                        userIDSFromYear13.add(rs.getInt("userID"));
                    }
                    // <------------------------------------------------------------------>



                    for(int i = 0; i<numYear13s; i++) { //FOR FLOOP
                        System.out.println(userIDSFromYear13.get(i));
                        rs = stmt.executeQuery("SELECT * FROM `Booking` WHERE `userID` = " + userIDSFromYear13.get(i));
                        User tempUser = new User();
                        tempUser = tempUser.createUser(userIDSFromYear13.get(i));
                        document.add(new Paragraph("Student: " + tempUser.getFirstName()));
                        Table table = new Table(UnitValue.createPercentArray(8)).useAllAvailableWidth();
                        //<-- ADDS INFORMATION TO TABLE -->
                        table.addCell("Booking Time");
                        table.addCell("Booking Date");
                        table.addCell("Booking Purpose");
                        table.addCell("Booking ID");
                        table.addCell("Booking Comment");
                        table.addCell("User ID");
                        table.addCell("Room ID");
                        table.addCell("Booking Guests");
                        // <-------------------------->
                        if(rs.next()) { //FUNCTION RUN IF DATABASE HAS ONE FIELD
                            System.out.println("this is called for user " + tempUser.getFirstName());
                            do {
                                //<-- ADDS INFORMATION TO TABLE -->
                                table.addCell(rs.getString("bookingTime"));
                                table.addCell(rs.getString("bookingDate"));
                                table.addCell(rs.getString("bookingPurpose"));
                                table.addCell(rs.getString("bookingID"));
                                table.addCell(rs.getString("bookingComment"));
                                table.addCell(rs.getString("userID"));
                                table.addCell(rs.getString("roomID"));
                                table.addCell(rs.getString("bookingGuests"));
                            }while(rs.next());
                        } else { //RUN IF THERE IS NOT INFORMATION IN THE DATABASE
                            //<-- ADDS INFORMATION TO TABLE -->
                            table.addCell("n/a");
                            table.addCell("n/a");
                            table.addCell("n/a");
                            table.addCell("n/a");
                            table.addCell("n/a");
                            table.addCell("n/a");
                            table.addCell("n/a");
                            table.addCell("n/a");
                            // <-------------------------->
                        }
                        document.add(table); //ADDS TABLE TO PDF DOCUMENT
                        if(i != numYear13s-1) {
                            document.add(new AreaBreak());
                        }
                    }



                } catch(Exception ex) {
                    ex.printStackTrace();
                }

            document.close();

        }catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public void createReportBookings(String path) { //CREATES REPORT OF BOOKINGS
        try {
            // <-- INSTATIATION OF PDF OBJECT -->
            PdfDocument pdf = new PdfDocument(new PdfWriter(path + "/ALL Booking.pdf"));
            Document document = new Document(pdf);
            // <-------------------------------->
            document.add(new Paragraph("TABLE OF ALL BOOKINGS AND THEIR INFO"));
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection("jdbc:mysql://" + User.getIPAddress() + ":3306/CSIA", "myuser", "testpassword");
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM `Booking` WHERE 1");
                Table table = new Table(UnitValue.createPercentArray(8)).useAllAvailableWidth();
                // <ADDS INFORMATION TO THE TABLE>
                table.addCell("Booking Time");
                table.addCell("Booking Date");
                table.addCell("Booking Purpose");
                table.addCell("Booking ID");
                table.addCell("Booking Comment");
                table.addCell("User ID");
                table.addCell("Room ID");
                table.addCell("Booking Guests");
                // <-------------------------->
                if (rs.next()) { //RAN IF THERE IS ONLY ONE RECORD IN THE DATABASE
                    User user = new User();
                    do {
                        // <ADD INFORMAATION TO TABLE>
                        table.addCell(user.convertTime(rs.getString("bookingTime")));
                        table.addCell(rs.getString("bookingDate"));
                        table.addCell(rs.getString("bookingPurpose"));
                        table.addCell(rs.getString("bookingID"));
                        table.addCell(rs.getString("bookingComment"));
                        table.addCell(rs.getString("userID"));
                        table.addCell(rs.getString("roomID"));
                        table.addCell(rs.getString("bookingGuests"));
                        //<-------------------------->
                    } while (rs.next());
                } else { //RAN IF THERE IS NO INFORMATION IN THE DATABASE
                    // <ADD INFORMATION TO TABLE>
                    table.addCell("n/a");
                    table.addCell("n/a");
                    table.addCell("n/a");
                    table.addCell("n/a");
                    table.addCell("n/a");
                    table.addCell("n/a");
                    table.addCell("n/a");
                    table.addCell("n/a");
                    //<-------------------------->
                }
                document.add(table);
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            document.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void createReportRooms(String path) { //FUNCTION CREATES A REPORT OF ROOMS
        try {
            // <-- INSTATIATION OF PDF OBJECT -->
            PdfDocument pdf = new PdfDocument(new PdfWriter(path + "/ALL ROOMS.pdf"));
            Document document = new Document(pdf);
            document.add(new Paragraph("TABLE OF ALL ROOMS AND THEIR INFO"));
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection("jdbc:mysql://" + User.getIPAddress() + ":3306/CSIA", "myuser", "testpassword");
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM `Room` WHERE 1");
                Table table = new Table(UnitValue.createPercentArray(6)).useAllAvailableWidth();
                //<ADD DATA TO TABLE>
                table.addCell("Room Desc");
                table.addCell("Room Instruments");
                table.addCell("Room ID");
                table.addCell("Room Location");
                table.addCell("Room Capacity");
                table.addCell("Room Comment");

                if (rs.next()) { //RAN IF THERE IS ONE RECORD ON THE DATABASE
                    do {
                        //<ADD DATA TO TABLE>

                        table.addCell(rs.getString("roomDesc"));
                        table.addCell(rs.getString("roomInstruments"));
                        table.addCell(rs.getString("roomID"));
                        table.addCell(rs.getString("roomLocation"));
                        table.addCell(rs.getString("roomCapacity"));
                        table.addCell(rs.getString("roomComment"));
                    } while (rs.next());
                } else { //RAN IF THERE IS NO INFORMATION IN THE DATABASE
                    //<ADD DATA TO TABLE>

                    table.addCell("n/a");
                    table.addCell("n/a");
                    table.addCell("n/a");
                    table.addCell("n/a");
                    table.addCell("n/a");
                    table.addCell("n/a");
                }
                document.add(table); //ADDS TABLE TO PDF
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            document.close(); //CLOSESE EDITING OF PDF

            } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void createReport(UserModel user, String path) { //CREATES REPORT OF USER
        try {
            // <-- INSTANTIATION OF PDF OBJECT -->
            PdfDocument pdf = new PdfDocument(new PdfWriter(path + "/" +
                    user.getNameFirst() + "'s report.pdf"));
            Document document = new Document(pdf);
            // <---------------------------->
            User tempUser = new User();

            // <-- ADDS BASIC INFORMATION ABOUT THE USER TO THE PDF -->
            document.add(new Paragraph("// REPORT FOR " + user.getNameFirst() + " //"));
            document.add(new Paragraph(""));
            document.add(new Paragraph("NAME: " + user.getNameFirst() + user.getNameLast()));
            document.add(new Paragraph("YEAR: " + user.getYearUser()));
            document.add(new Paragraph("CLASS: " + user.getClassUser()));
            document.add(new Paragraph("SCHOOL ID: " + user.getSchoolIDUser()));
            document.add(new Paragraph("APPLICATION ID: " + user.getIDUser()));
            document.add(new Paragraph("USER COMMENT: " + user.getCommentUser()));
            document.add(new Paragraph("///////////////////////////////////"));
            document.add(new Paragraph(""));
            document.add(new Paragraph("// INFORMATION ABOUT USER BELOW //"));
            String contents = tempUser.createReport(user); //FUNCTION GENERATES STATISTICAL INFORMATION ABOUT THE USER
            document.add(new Paragraph(contents));
            document.add(new Paragraph(""));
            document.add(new Paragraph("///////////////////////////////////"));

            // <----------------------------------------------------------------------------->


            document.add(new AreaBreak()); //CREATES A NEW PAGE
            document.add(new Paragraph("TABLE OF ALL BOOKINGS MADE BY: " + user.getNameFirst()));
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection("jdbc:mysql://" + User.getIPAddress() + ":3306/CSIA", "myuser", "testpassword");
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM `Booking` WHERE `userID` = " + user.getIDUser());
                Table table = new Table(UnitValue.createPercentArray(8)).useAllAvailableWidth();
                //<ADDS INFORMATION TO TABLE>
                table.addCell("Booking Time");
                table.addCell("Booking Date");
                table.addCell("Booking Purpose");
                table.addCell("Booking ID");
                table.addCell("Booking Comment");
                table.addCell("User ID");
                table.addCell("Room ID");
                table.addCell("Booking Guests");

                if(rs.next()) { //RAN IF THERE IS ONE RECORD IN THE DATABASE
                    System.out.println("this is called for user " + tempUser.getFirstName());
                    do {
                        //<ADDS INFORMATION TO TABLE>

                        table.addCell(tempUser.convertTime(rs.getString("bookingTime")));
                        table.addCell(rs.getString("bookingDate"));
                        table.addCell(rs.getString("bookingPurpose"));
                        table.addCell(rs.getString("bookingID"));
                        table.addCell(rs.getString("bookingComment"));
                        table.addCell(rs.getString("userID"));
                        table.addCell(rs.getString("roomID"));
                        table.addCell(rs.getString("bookingGuests"));
                    }while(rs.next());
                } else { //RAN IF THERE IS NO INFORMATION IN THE DATABASE
                    //<ADDS INFORMATION TO TABLE>

                    table.addCell("n/a");
                    table.addCell("n/a");
                    table.addCell("n/a");
                    table.addCell("n/a");
                    table.addCell("n/a");
                    table.addCell("n/a");
                    table.addCell("n/a");
                    table.addCell("n/a");
                }
                document.add(table);

            } catch(Exception ex) {
                ex.printStackTrace();
            }
            document.close();
            System.out.println("successful pdf output");

        } catch(Exception ex) {
            System.out.println("PDF creation failed!");
        }


    }
}
