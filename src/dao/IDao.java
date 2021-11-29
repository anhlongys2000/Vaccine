/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

/**
 *
 * @author asus
 */
public interface IDao {

    public void addInj();

    public void updateInj();

    public void deleteInj(String id);

    public void searchInj();
    
    public void searchInjbyName();
    
    public void loadfromFile(String filename);
    
    public void savetoFile(String fileName);
    
}
