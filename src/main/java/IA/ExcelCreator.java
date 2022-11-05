package IA;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class ExcelCreator {


    public int findNumRows(TableView<BookingModel> table, TableColumn<BookingModel, String> bookingColumn) throws IOException { //FUNCTION FINDS THE NUMBER OF ROWS IN A TABLE
        int counter = 0;
        for(BookingModel item : table.getItems()) {
            counter++;
            System.out.println(bookingColumn.getCellObservableValue(item).getValue());
        }
        return counter;
    }

    //FUNCTION THAT EXPORTS THE BOOKINGS MADE BY THE PERSON
    public void exportSelfBookings(TableView<BookingModel> table, TableColumn<BookingModel, String> bookingColumn, TableColumn<BookingModel, String> bookingDate, TableColumn<BookingModel, String> bookingPurpose, TableColumn<BookingModel, Integer> bookingID, TableColumn<BookingModel, Integer> roomID, TableColumn<BookingModel, Integer> guests, String path) throws IOException {
        HSSFWorkbook workbook = new HSSFWorkbook(); //CREATES WORKBOOK
        User user =  new User();
        HSSFSheet sheet = workbook.createSheet("FirstExcelSheet"); //CREATES SHEET

        int numRows = findNumRows(table, bookingColumn)+1; //DETERMINES THE NUMBER OF ROWS OF THE TABLE

        HSSFRow row = sheet.createRow(0); //CREATES HEADING ROW

        ArrayList<HSSFRow> rowList = new ArrayList<HSSFRow>(); //CREATES ARRAYLIST WHICH CAN CHANGE

        for(int i = 0; i < numRows; i++) { //START OF FOR LOOP
            HSSFRow temp = sheet.createRow(i); //CREATES A TEMPORARY ROW OF ROW NUMBER 'i'
            rowList.add(temp); //ADDS THE ROW INTO THE LIST
        }// END OF FOR LOOP

        //<-- SETS THE HEADINGS NAME FOR EACH COLUMN -->

        HSSFCell cell = row.createCell(0);
        cell.setCellValue("Time");
        HSSFCell cell1 = row.createCell(1);
        cell1.setCellValue("Date");
        HSSFCell cell2 = row.createCell(2);
        cell2.setCellValue("Purpose");
        HSSFCell cell3 = row.createCell(3);
        cell3.setCellValue("Booking ID");
        HSSFCell cell4 = row.createCell(4);
        cell4.setCellValue("Room ID");
        HSSFCell cell5 = row.createCell(5);
        cell5.setCellValue("Guests");

        // <------------------------------------------>

        int counter = 1; //CREATES POINTER FOR ITERATION OF COLUMNS IN TABLE
        for(BookingModel item : table.getItems()) { //START OF FOR LOOP

            // <-- CREATES A TEMPORARY CELL WITHIN A COLUMN AND ADDS TO IT DEPENDING ON THE COLUMN -->

            //GETS SPECIFIC ROW AND CREATES A CELL IN COLUMN 0 OF THE SPREADSHEET
            HSSFCell tempCell = rowList.get(counter).createCell(0);
            //SETS VALUE WITHIN CELL EQUAL TO THE VALUE WITHIN THE COLUMN WITHIN THE TABLE
            tempCell.setCellValue(user.convertTime(bookingColumn.getCellObservableValue(item).getValue()));

            // <-- SAME PRINCIPLE APPLIES BELOW AS ABOVE -->

            tempCell = rowList.get(counter).createCell(1);
            tempCell.setCellValue(bookingDate.getCellObservableValue(item).getValue());

            tempCell = rowList.get(counter).createCell(2);
            tempCell.setCellValue(bookingPurpose.getCellObservableValue(item).getValue());

            tempCell = rowList.get(counter).createCell(3);
            tempCell.setCellValue(bookingID.getCellObservableValue(item).getValue());

            tempCell = rowList.get(counter).createCell(4);
            tempCell.setCellValue(roomID.getCellObservableValue(item).getValue());

            tempCell = rowList.get(counter).createCell(5);
            tempCell.setCellValue(guests.getCellObservableValue(item).getValue());

            // <-- ---------------------------------------- -->
            counter++; //ITERATION OF COUNTER
            //<--------------------------------------------------------------------------------->
        } //END OF FOR LOOP

        // <-- DETERMINES THE NAME OF THE USER WHO'S BOOKING TABLE IS BEING PROCURED -->
        User tempUser = new User(); //CREATES TEMPORARY INSTANCE OF USER
        int loggedinID = tempUser.getLoggedInUserID(); //DETERMINES ID OF USER LOGGED IN
        tempUser = tempUser.createUser(loggedinID); //CREATES A FULL USER BY CONNECTING TO DATABASE
        // <--------------------------------------------------------------------------->
        //CREATES EXCEL FILE AND WRITES IT TO A LOCATION
        workbook.write(new FileOutputStream(path + "/" + tempUser.getFirstName() + " Bookings.xls"));
        workbook.close(); //CLOSES WORKBOOK
    } //END OF FUNCTION





}
