/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dto.Injection;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import util.validation;

/**
 *
 * @author asus
 */
public class InjectionDao extends ArrayList<Injection> implements IDao {

    private static final int IDINJECTION = 0;
    private static final int DATE1_COLUMN = 1;
    private static final int PLACE1_COLUMN = 2;
    private static final int DATE2_COLUMN = 3;
    private static final int PLACE2_COLUMN = 4;
    private static final int STUDEN_ID = 5;
    private static final int VACCINE_ID = 6;
    StudentDao student;
    VaccineDao vaccine;

    public InjectionDao() {
        super();
    }

    public InjectionDao(StudentDao student, VaccineDao vaccine) {
        this.student = student;
        this.vaccine = vaccine;
    }

    @Override
    public void addInj() {
        String id = "";
        LocalDate ldate1;
        String place1;
        LocalDate ldate2 = null;
        String place2 = null;
        String sID;
        String vID;
        final String IGNORE = "";
        while (true) {
            id = validation.getStringByPattern("Enter id: ", "Id is not allow null!");
            if (isExist(id)) {
                System.out.println("Duplicate id, Please another id");
            } else {
                break;
            }
        }

        ldate1 = validation.getLDate("Enter date(yyyy-MM-dd): ", "Invalid date!", "yyyy-MM-dd");
        place1 = validation.getStringByPattern("Enter 1st place: ", "Place is not allow null!");
        ldate2 = validation.getLDate2("Enter 2st date(yyyy-MM-dd): ", "Invalid date!", "yyyy-MM-dd",
                ldate1, " The second dose of vaccine must be given 4 to 12 weeks after the first injection", IGNORE);
        if (ldate2 != null) {
            place2 = validation.getStringByPattern("Enter 2st place:", "Place is not allow null!");
        }
        sID = validation.getStudentID("Enter student id: ", "Student not found", this.student,
                " The student has injecttion, Please choose another!");
        vID = validation.getVaccineID("Enter vaccine id: ", "Vaccine not found", this.vaccine);
        this.add(new Injection(id, ldate1, place1, ldate2, place2, sID, vID));
        this.student.search(sID).num++;
        savetoFile("injection.dat");
        System.out.println("Added sucessful");
    }

    @Override
    public void updateInj() {
        if (this.isEmpty()) {
            System.out.println("Empty list");
        } else {
            LocalDate ldate2 = null;
            String place2 = null;
            final String IGNORE = "";
            String id = validation.getStringByPattern("Enter id to update: ", "Id is not allow null");
            int index = indexId(id);
            if (index != -1) {
                Injection t = this.get(index);
                ldate2 = validation.getLDate2("Enter 2st date(yyyy-MM-dd): ", "Invalid date!", "yyyy-MM-dd",
                        this.get(index).date1, " The second dose of vaccine must be given 4 to 12 weeks after the first injection", IGNORE);
                if (ldate2 != null) {
                    place2 = validation.getStringByPattern("Enter 2st place:", "Place is not allow null!");
                    this.get(index).setDate2(ldate2);
                    this.get(index).setPlace2(place2);
                    System.out.println("Student has completed 2 injections!");
                    savetoFile("injection.dat");
                } else {
                    System.out.println("Update keep the same!");
                }
            } else {
                System.out.println("Id not found");
            }
        }
    }

    @Override
    public void deleteInj(String id) {
        int mark = 0;
        if (!this.isEmpty()) {
            List<Injection> tmpList = new ArrayList<>();
            for (Injection t : this) {
                if (t.getInjectionID().equals(id)) {
                    tmpList.add(t);
                    mark = 1;
                }
            }
            if (mark == 1) {
                String answer = new String();
                answer = validation.validyn("Are you want to delete this injection yes or no?",
                        "Please[yes/no]");
                if (answer.equalsIgnoreCase("yes")) {
                    if (this.removeAll(tmpList)) {
                        System.out.println("Delete successful.");
                        savetoFile("injection.dat");
                    } else {
                        System.out.println("Delete failed.");
                    }
                } else {
                    System.out.println("Cancel by user.");
                }
            } else {
                System.out.println("Not exist injection.");
            }
        } else {
            System.out.println("Not thing in list.");
        }
    }

    @Override
    public void loadfromFile(String filename) {
        try {
            BufferedReader in = new BufferedReader(new FileReader(filename));
            String str = "";
            while ((str = in.readLine()) != null) {
                String data[] = str.split("[;]");
                String id = data[IDINJECTION];
                LocalDate ldate1 = LocalDate.parse(data[DATE1_COLUMN], DateTimeFormatter.ISO_LOCAL_DATE);
                String place1 = data[PLACE1_COLUMN];
                LocalDate ldate2 = data[DATE2_COLUMN].equals("null") ? null : LocalDate.parse(data[DATE2_COLUMN], DateTimeFormatter.ISO_LOCAL_DATE);
                String place2 = data[PLACE2_COLUMN].equals("null") ? null : data[PLACE2_COLUMN];
                String stuID = data[STUDEN_ID];
                String vacID = data[VACCINE_ID];
                if (this.student.isIDExist(stuID)) {
                    this.add(new Injection(id, ldate1, place1, ldate2, place2, stuID, vacID));
                    this.student.search(stuID).num++;
                }
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(InjectionDao.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(InjectionDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void savetoFile(String fileName) {
        try {
            File file = new File(fileName);
            FileWriter fw = new FileWriter(file);
            for (Injection t : this) {
                String result = t.getInjectionID() + ";" + t.getDate1() + ";"
                        + t.getPlace1() + ";" + t.getDate2() + ";" + t.getPlace2() + ";"
                        + t.getStudentID() + ";" + t.getVaccineID();
                fw.write(result);
                fw.write("\n");
            }
            fw.close();
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    private List<Injection> getInjectionByStudent(String stu) {
        List<Injection> listVaccineByStudent = new ArrayList<Injection>();

        for (Injection inj : this) {
            if (inj.getStudentID().equals(stu)) {
                listVaccineByStudent.add(inj);
            }
        }

        return listVaccineByStudent;
    }

    private boolean isExist(String id) {
        for (Injection i : this) {
            if (i.getInjectionID().equals(id)) {
                return true;
            }
        }
        return false;
    }

    private int indexId(String id) {
        for (int i = 0; i < this.size(); i++) {
            Injection t = this.get(i);
            if (t.getInjectionID().equals(id)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public void searchInj() {
        if (this.isEmpty()) {
            System.out.println("Empty list");
        } else {
            String stuID = validation.getSearchID("Enter ID student to search: ", "Not exit student", student);
            System.out.format("%-15s%-15s%-15s%-15s%-15s%-15s%-15s\n",
                    "id", "Date1", "Place1", "Date2", "Place2", "StudentId", "VaccineId");
            for (Injection t : this) {
                if (t.getStudentID().equals(stuID)) {
                    System.out.println(t);
                }
            }
        }
    }
    
    @Override
    public void searchInjbyName() {
        if (this.isEmpty()) {
            System.out.println("Empty list");
        } else {
            String stuName = validation.getSearchName("Enter name student: ", "Student not found!!!", this.student);
            boolean check = false;
            for (Injection x : this) {
                if (this.student.search(x.studentID).name.equals(stuName)){
                    check = true;
                }
            }
            if (check == false) {
                System.out.println(stuName + " " + "not inject.");
            } else {
                System.out.println("Injection Information:");
                System.out.format("%-15s%-15s%-15s%-15s%-15s%-15s%-15s\n",
                        "id", "Date1", "Place1", "Date2", "Place2", "StudentId", "VaccineId");
                for (Injection t : this) {
                    if (this.student.search(t.studentID).name.equals(stuName)) {
                        System.out.println(t);
                    }
                }
            }
        }
    }
}
