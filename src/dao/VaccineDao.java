/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dto.Vaccine;
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
public class VaccineDao extends ArrayList<Vaccine> {

    private static final int VACCINE_ID = 0;
    private static final int VACCINE_NAME = 1;

    public VaccineDao(String fileName) {
        try {
            BufferedReader in = new BufferedReader(new FileReader(fileName));
            String str = "";
            while ((str = in.readLine()) != null) {
                String data[] = str.split("[;]");
                String id = data[VACCINE_ID];
                String name = data[VACCINE_NAME];
                this.add(new Vaccine(id, name));
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(StudentDao.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(StudentDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean isIDExist(String id) {
        for (Vaccine v : this) {
            if (v.getID().equals(id)) {
                return true;
            }
        }
        return false;
    }

    public boolean isSearchVacName(String data) {
        for (Vaccine v : this) {
            if (v.getName().equals(data)) {
                return true;
            }
        }
        return false;
    }

    public Vaccine search(String vaccineID) {
        for (Vaccine s : this) {
            if (s.getID().equals(vaccineID)) {
                return s;
            }
        }
        return null;
    }
   
    
}
