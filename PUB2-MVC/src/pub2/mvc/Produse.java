/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pub2.mvc;

import com.google.gson.Gson;
import com.mysql.jdbc.Connection;
import java.io.FileWriter;
import java.io.IOException;
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
 * Definește modelul de date al unei liste de produse.
 *
 * @author User
 */
public class Produse {

    /**
     * Colecție de produse.
     */
    private ArrayList<Produs> produse;
    /**
     * Model date JTable.
     */
    private DefaultTableModel table;

    /**
     * Constructor fără parametri.
     */
    public Produse() {
        produse = null;
        table = null;
    }

    /**
     * Returnează un model de JTable ce conține detaliile produselor.
     *
     * @return model JTable
     */
    public DefaultTableModel getTable() {
        return table;
    }

    /**
     * Atribuie la modelul de date al tabelei de produse modelul unui JTable
     *
     * @param table un JTable al cărui model va fi prelucrat de clasa Produse
     */
    public void setTable(JTable table) {
        this.table = (DefaultTableModel) table.getModel();
    }


    /**
     * Completează modelul JTable cu datele obiectelor din colecția de produse.
     */
    public void completeazaTable() {
        // Curatenie neecesara din cauza unui workaround
        table.getDataVector().removeAllElements();
        table.fireTableDataChanged();

        for (Produs produs : produse) {
            String[] rand = {String.valueOf(produs.getId()), produs.getNume(),
                String.valueOf(produs.getPret()), produs.getCategorie()};
            table.addRow(rand);
        }
    }

    /**
     * Extrage produsele din baza de date.
     */
    public void citesteDinBd() {
        try {
            Connection con = BazaDeDate.getCon();
            Statement stmt = con.createStatement();
            String sql = "SELECT id, nume, pret, categorie FROM Produs";
            ResultSet rs = stmt.executeQuery(sql);
            produse = new ArrayList();
            while (rs.next()) {
                int id = rs.getInt(1);
                String nume = rs.getString(2);
                int pret = rs.getInt(3);
                String categorie = rs.getString(4);
                produse.add(new Produs(id, nume, pret, categorie));
            }
            rs.close();
            stmt.close();
        } catch (ClassNotFoundException | SQLException ex) {
            JOptionPane.showMessageDialog(new JFrame(), "EROARE EXTRAGERE PRODUSE");
        }
    }

    /**
     * Adaugă un produs nou în baza de date.
     *
     * @param nume numele produsului nou
     * @param pret prețul produsului nou
     * @return true în caz de eroare false în cazul inserării cu succes
     */
    public Boolean adaugaInBd(String nume, int pret, String categorie) {
        try {
            Connection con = BazaDeDate.getCon();
            String sql = "INSERT INTO Produs(nume, pret, categorie) VALUE (?, ?, ?)";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, nume);
            stmt.setInt(2, pret);
            stmt.setString(3, categorie);
            stmt.execute();
            stmt.close();
        } catch (ClassNotFoundException | SQLException ex) {
            return true;
        }
        return false;
    }

    /**
     * Șterge un produs cu identificator cunoscut din baza de date
     *
     * @param id identificatorul produsului care se va șterge
     * @return true în caz de eroare false în cazul ștergerii cu succes
     */
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

    /**
     * Modifică un produs în baza de date.
     *
     * @param id identificatorul produsului
     * @param nume numele nou al produsului
     * @param pret prețul nou al produsului
     * @return true în caz de eroare false dacă modificările au fost efectuate
     * cu succes
     */
    public Boolean modificaInBd(int id, String nume, int pret, String categorie) {
        try {
            Connection con = BazaDeDate.getCon();
            String sql = "UPDATE Produs SET nume = ?, pret = ?, categorie = ? WHERE id = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, nume);
            stmt.setInt(2, pret);
            stmt.setString(3, categorie);
            stmt.setInt(4, id);
            stmt.execute();
            stmt.close();
        } catch (ClassNotFoundException | SQLException ex) {
            return true;
        }
        return false;
    }

    public void serializeaza() {
        Gson gson = new Gson();
        try (FileWriter writer = new FileWriter("jSonProduse.json")) {
           writer.write("{\n\"products\":");
           String dateSerializate = gson.toJson(produse);
           writer.write(dateSerializate);
           writer.write("\n}");
        } catch (IOException e) {
        }
    }
}
