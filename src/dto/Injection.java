/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

import java.time.LocalDate;

/**
 *
 * @author asus
 */
public class Injection {

    public String injectionID;
    public LocalDate date1;
    public String place1;
    public LocalDate date2;
    public String place2;
    public String studentID;
    public String vaccineID;

    public Injection() {
        super();
    }

    public Injection(String injectionID, LocalDate date1, String place1, LocalDate date2, String place2, String studentID, String vaccineID) {
        this.injectionID = injectionID;
        this.date1 = date1;
        this.place1 = place1;
        this.date2 = date2;
        this.place2 = place2;
        this.studentID = studentID;
        this.vaccineID = vaccineID;
    }

    public String getInjectionID() {
        return injectionID;
    }

    public void setInjectionID(String injectionID) {
        this.injectionID = injectionID;
    }

    public LocalDate getDate1() {
        return date1;
    }

    public void setDate1(LocalDate date1) {
        this.date1 = date1;
    }

    public String getPlace1() {
        return place1;
    }

    public void setPlace1(String place1) {
        this.place1 = place1;
    }

    public LocalDate getDate2() {
        return date2;
    }

    public void setDate2(LocalDate date2) {
        this.date2 = date2;
    }

    public String getPlace2() {
        return place2;
    }

    public void setPlace2(String place2) {
        this.place2 = place2;
    }

    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public String getVaccineID() {
        return vaccineID;
    }

    public void setVaccineID(String vaccineID) {
        this.vaccineID = vaccineID;
    }

    @Override
    public String toString() {
        return String.format("%-15s%-15s%-15s%-15s%-15s%-15s%-15s",
                        injectionID, date1, place1, date2, place2, studentID, vaccineID);
    }
}
