package IA;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;

public class UserModel {

    // <DECLARE DATA MEMBERS>
    private SimpleStringProperty nameFirst;
    private SimpleStringProperty nameLast;
    private SimpleIntegerProperty yearUser;
    public SimpleStringProperty classUser;
    public SimpleLongProperty schoolIDUser;
    public SimpleIntegerProperty IDUser;
    public SimpleStringProperty commentUser;
    // <---------------------->

    public UserModel(String fName, String lName, int year, String userClass, long schoolID, int appID, String comment) { //CONSTRUCTOR FUNCTION
        this.nameFirst = new SimpleStringProperty(fName);
        this.nameLast = new SimpleStringProperty(lName);
        this.yearUser = new SimpleIntegerProperty(year);
        this.classUser = new SimpleStringProperty(userClass);
        this.schoolIDUser = new SimpleLongProperty(schoolID);
        this.IDUser = new SimpleIntegerProperty(appID);
        this.commentUser = new SimpleStringProperty(comment);
    }

    //< -- MEMBER FUNCTIONS -->

    public String getNameFirst() {
        return nameFirst.get();
    }

    public String getNameLast() {
        return nameLast.get();
    }

    public int getIDUser() {
        return IDUser.get();
    }

    public int getYearUser() {
        return yearUser.get();
    }

    public long getSchoolIDUser() {
        return schoolIDUser.get();
    }

    public String getClassUser() {
        return classUser.get();
    }

    public String getCommentUser() {
        return commentUser.get();
    }

    public void setNameFirst(String nameFirst) {
        this.nameFirst.set(nameFirst);
    }

    public void setNameLast(String nameLast) {
        this.nameLast.set(nameLast);
    }

    public void setClassUser(String classUser) {
        this.classUser.set(classUser);
    }

    public void setCommentUser(String commentUser) {
        this.commentUser.set(commentUser);
    }

    public void setIDUser(int IDUser) {
        this.IDUser.set(IDUser);
    }

    public void setSchoolIDUser(long schoolIDUser) {
        this.schoolIDUser.set(schoolIDUser);
    }

    public void setYearUser(int yearUser) {
        this.yearUser.set(yearUser);
    }

    // <------------------------------------------------>


}
