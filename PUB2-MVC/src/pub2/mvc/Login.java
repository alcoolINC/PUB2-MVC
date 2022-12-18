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
 * Definește modelul datelor de autentificare folosit de ControllerLogin.
 *
 * @author User
 */
public class Login {

    /**
     * Reține identificatorul userului autentificat.
     */
    private static int idUserLogat;
    /**
     * Numele de utilizator care trebuie validat.
     */
    private String username;
    /**
     * Parola care trebuie validat.
     */
    private String parola;

    /**
     * Constructorul clasei Login.
     * @param username Numele de utilizator atribuit instanței modelului
     * @param parola Parola atribuită instanței modelului
     */
    public Login(String username, String parola) {
        this.username = username;
        this.parola = parola;
    }

    /**
     * Modifică numele de utilizator al modelului de autentificare.
     *
     * @param username numele de utilizator care este atribuit modelului
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Modifică parola modelului de autentificare.
     *
     * @param parola parola care este atribuită modelului
     */
    public void setParola(String parola) {
        this.parola = parola;
    }

    /**
     * Returnează identificatorul userului autentificat.
     *
     * @return identificatorul userului autentificat
     */
    public static int getIdUserLogat() {
        return idUserLogat;
    }

    /**
     * Validează datele de autentificare.
     *
     * @return -2 eroare la conectarea cu baza de date <br>
     * -1 date incorecte <br>
     * 0 cont existent cu rol de angajat <br>
     * 1 cont existent cu rol de admin 
     */
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
                idUserLogat = rs.getInt(4);
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
