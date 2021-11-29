/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import dao.InjectionDao;
import dao.StudentDao;
import dao.VaccineDao;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;
import util.validation;

/**
 *
 * @author asus
 */
public class Main {

    private static final int IDINJECTION = 0;
    private static final int DATE1_COLUMN = 1;
    private static final int PLACE1_COLUMN = 2;
    private static final int DATE2_COLUMN = 3;
    private static final int PLACE2_COLUMN = 4;
    private static final int STUDEN_ID = 5;
    private static final int VACCINE_ID = 6;

    public static void main(String[] args) {
        String studentFile = "student.dat";
        String vaccineFile = "vaccine.dat";
        String injFile = "injection.dat";
        StudentDao student = new StudentDao(studentFile);
        VaccineDao vaccine = new VaccineDao(vaccineFile);
        InjectionDao inj = new InjectionDao(student, vaccine);
        inj.loadfromFile(injFile);
        while (true) {
            menu();
            int option = validation.getAnInteger("Your choice: ", "Please choice[1-6]", 1, 6);
            switch (option) {
                case 1:
                    viewFile(injFile);
                    break;
                case 2:
                    int flag = 1;
                    do {
                        inj.addInj();
                        String answer = new String();
                        answer = validation.validyn("Do you want to add more yes or no?",
                                "Please enter[yes/no]!!!");
                        if (answer.equalsIgnoreCase("yes")) {
                            flag = 1;
                        } else {
                            flag = 0;
                        }
                    } while (flag == 1);
                    break;
                case 3:
                    inj.updateInj();
                    break;
                case 4:
                    String input = new String();
                    input = validation.getStringByPattern("Enter id to delete: ", "Id is not allow null");
                    inj.deleteInj(input);
                    break;
                case 5: {
                    int mark = 1;
                    do {
                        Choose();
                        option = validation.getAnInteger("Your choice:", "Please choice[1-2]", 1,2);
                        switch (option) {
                            case 1:
                                inj.searchInj();
                                break;
                            case 2:
                                inj.searchInjbyName();
                                break;
                        }
                        mark = 0;
                    } while (mark == 1);
                    break;
                }
                case 6:
                    System.exit(0);
                    break;
            }

        }
    }

    private static void menu() {
        System.out.println("\n---------------------------");
        System.out.println("Select the options below:");
        System.out.println("1. Show information all students have been injected");
        System.out.println("2. Add student's vaccine injection information");
        System.out.println("3. Updating information of students' vaccine injection");
        System.out.println("4. Delete student vaccine injection information");
        System.out.println("5. Search for injection information");
        System.out.println("6. Quit");

    }

    public static void viewFile(String filename) {
        System.out.format("%-15s%-15s%-15s%-15s%-15s%-15s%-15s\n",
                "id", "Date1", "Place1", "Date2", "Place2", "StudentId", "VaccineId");
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
                System.out.format("%-15s%-15s%-15s%-15s%-15s%-15s%-15s\n",
                        id, ldate1, place1, ldate2, place2, stuID, vacID);
            }

        } catch (FileNotFoundException ex) {
            Logger.getLogger(InjectionDao.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(InjectionDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void Choose() {
        System.out.println("---------------------------");
        System.out.println("5.1 Search by student ID.");
        System.out.println("5.2 Search by student name. ");
    }
}
