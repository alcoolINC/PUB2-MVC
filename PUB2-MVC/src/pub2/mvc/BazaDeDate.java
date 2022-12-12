/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pub2.mvc;

import com.mysql.jdbc.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author User
 */
public class BazaDeDate {

    private static Connection con;

    private BazaDeDate() {
    }

    public static Connection getCon() throws ClassNotFoundException, SQLException {
        if (con == null) {
            String driver = "com.mysql.jdbc.Driver";
            String url = "jdbc:mysql://127.0.0.1:3306/pub";
            String user = "root";
            String parola = "";

            Class.forName(driver);
            con = (Connection) DriverManager.getConnection(url, user, parola);
        }
        return con;
    }

    public static int returneazaUltimaCheie() {
        int id = -1;
        try {
            Connection con = getCon();
            Statement stmt = con.createStatement();
            String sql = "SELECT @@IDENTITY;";
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                id = rs.getInt(1);
            } else {
                System.out.println("EROARE CHEIE PRIMARA");
            }
        } catch (ClassNotFoundException | SQLException ex) {
            JOptionPane.showMessageDialog(new JFrame(), "EROARE AFLARE CHEIE PRIMARA UTILIZATOR");
        }
        return id;
    }
    
    
    public static Boolean genereazaRaport(DefaultTableModel model) {
        try {
            Connection con = getCon();
            String sql = "SELECT nota.id_user, utilizator.nume, SUM(total) FROM Nota, Utilizator WHERE nota.id_user = utilizator.id GROUP BY id_user";
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                String[] rand = {rs.getString(1), rs.getString(2), rs.getString(3)};
                model.addRow(rand);
            }
            stmt.close();
        } catch (ClassNotFoundException | SQLException ex) {
            return true;
        }
        return false;
    }
}
