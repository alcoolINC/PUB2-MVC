/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pub2.mvc;

import com.mysql.jdbc.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author User
 */
public class Produse {

    private ArrayList<Produs> produse;
    private DefaultTableModel table;

    public Produse() {
        produse = null;
        table = null;
    }
    
    public DefaultTableModel getTable() {
        return table;
    }

    public void setTable(JTable table) {
        this.table = (DefaultTableModel) table.getModel();
    }
    
    public int getValoare(int linie, int coloana) {
       return Integer.parseInt((String) table.getValueAt(linie, coloana));
    }

    public void completeazaTable() {
        // Curatenie neecesara din cauza unui workaround
        table.getDataVector().removeAllElements();
        table.fireTableDataChanged();
        
        for (Produs produs : produse) {
            String[] rand = {String.valueOf(produs.getId()), produs.getNume(),
                String.valueOf(produs.getPret())};
            table.addRow(rand);
        }
    }

    public void citesteDinBd() {
        try {
            Connection con = BazaDeDate.getCon();
            Statement stmt = con.createStatement();
            String sql = "SELECT id, nume, pret FROM Produs";
            ResultSet rs = stmt.executeQuery(sql);
            produse = new ArrayList();
            while (rs.next()) {
                int id = rs.getInt(1);
                String nume = rs.getString(2);
                int pret = rs.getInt(3);
                produse.add(new Produs(id, nume, pret));
            }
            rs.close();
            stmt.close();
        } catch (ClassNotFoundException | SQLException ex) {
            JOptionPane.showMessageDialog(new JFrame(), "EROARE EXTRAGERE PRODUSE");
        }
    }

    public Boolean adaugaInBd(String nume, int pret) {
        try {
            Connection con = BazaDeDate.getCon();
            String sql = "INSERT INTO Produs(nume, pret) VALUE (?, ?)";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, nume);
            stmt.setInt(2, pret);
            stmt.execute();
            stmt.close();
        } catch (ClassNotFoundException | SQLException ex) {
            return true;
        }
        return false;
    }
    
    public Boolean stergeDinBd(int id) {
        try {
            Connection con = BazaDeDate.getCon();
            String sql = "DELETE FROM Produs WHERE id = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, id);
            stmt.execute();
            stmt.close();
        } catch (ClassNotFoundException | SQLException ex) {
            return true;
        }
        return false;
    }

    public Boolean modificaInBd(int id, String nume, int pret) {
        try {
            Connection con = BazaDeDate.getCon();
            String sql = "UPDATE Produs SET nume = ?, pret = ? WHERE id = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, nume);
            stmt.setInt(2, pret);
            stmt.setInt(3, id);
            stmt.execute();
            stmt.close();
        } catch (ClassNotFoundException | SQLException ex) {
            return true;
        }
        return false;
    }
}
