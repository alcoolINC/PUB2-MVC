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
public class ModelLogin {

    private static int idUserLogat;
    
    private String user;
    private String parola;

    public ModelLogin(String user, String parola) {
        this.user = user;
        this.parola = parola;
    }

    public void setUser(String user) {
        this.user = user;
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
            stmt.setString(1, user);
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
