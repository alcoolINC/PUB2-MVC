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
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author User
 */
public class ModelUtilizatori {

    private ArrayList<Utilizator> utilizatori;
    private DefaultTableModel table;

    public ModelUtilizatori() {
        utilizatori = null;
        table = null;
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

        for (Utilizator utilizator : utilizatori) {
            String[] rand = {String.valueOf(utilizator.getId()),
                utilizator.getUser(), utilizator.getNume(),
                String.valueOf(utilizator.getRol())};
            table.addRow(rand);
        }
    }

    public Boolean citesteDinBd() {
        try {
            Connection con = BazaDeDate.getCon();
            Statement stmt = con.createStatement();
            String sql = "SELECT id, username, nume, rol FROM Utilizator";
            ResultSet rs = stmt.executeQuery(sql);
            utilizatori = new ArrayList();
            while (rs.next()) {
                int id = rs.getInt(1);
                String user = rs.getString(2);
                String nume = rs.getString(3);
                int rol = rs.getInt(4);
                utilizatori.add(new Utilizator(id, user, nume, rol));
            }
            rs.close();
            stmt.close();
        } catch (ClassNotFoundException | SQLException ex) {
            return true;
        }
        return false;
    }

    public Boolean adaugaInBd(String user, String parola,
            String nume, int rol) {
        try {
            Connection con = BazaDeDate.getCon();
            String sql = "INSERT INTO Utilizator(username, parola, nume, rol) VALUE (?, ?, ?, ?)";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, user);
            stmt.setString(2, parola);
            stmt.setString(3, nume);
            stmt.setInt(4, rol);
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
            String sql = "DELETE FROM Utilizator WHERE id = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, id);
            stmt.execute();
            stmt.close();
        } catch (ClassNotFoundException | SQLException ex) {
            return true;
        }
        return false;
    }

}
