/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dto.Student;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author asus
 */
public class StudentDao extends ArrayList<Student> {

    private static final int STUDENT_ID = 0;
    private static final int STUDENT_NAME = 1;

    public StudentDao(String fileName) {
        try {
            BufferedReader in = new BufferedReader(new FileReader(fileName));
            String str = "";
            while ((str = in.readLine()) != null) {
                String data[] = str.split("[;]");
                String id = data[STUDENT_ID];
                String name = data[STUDENT_NAME];
                this.add(new Student(id, name));
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(StudentDao.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(StudentDao.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public Student search(String id) {
        for (Student s : this) {
            if (s.getID().equals(id)) {
                return s;
            }
        }
        return null;
    }

    public boolean isIDExist(String id) {
        for (Student s : this) {
            if (s.getID().equals(id)) {
                return true;
            }
        }
        return false;
    }

    public boolean isSearchName(String data) {
        for (Student s : this) {
            if (s.getName().equals(data)) {
                return true;
            }
        }
        return false;
    }

}
