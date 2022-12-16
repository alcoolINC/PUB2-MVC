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

/**
 *
 * @author User
 */
public class Login {

    private static int idUserLogat;
    
    private String username;
    private String parola;

    public Login(String user, String parola) {
        this.username = user;
        this.parola = parola;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setParola(String parola) {
        this.parola = parola;
    }

    public static int getIdUserLogat(){
        return idUserLogat;
    }
    
    public int valideaza() {
        int status = -1;
        try {
            Connection con = BazaDeDate.getCon();
            String sql = "SELECT username, parola, rol, id FROM Utilizator WHERE username = ? AND parola = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, parola);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                // Daca a returnat ceva query-ul
                idUserLogat = rs.getInt(4);/////////////////////////////////
                int rol = rs.getInt(3);
                if (rol == 0) {
                    status = 0;
                } else {
                    status = 1;
                }
            }
            rs.close();
            stmt.close();
        } catch (ClassNotFoundException | SQLException ex) {
            return -2;
        }
        return status;
    }
}
