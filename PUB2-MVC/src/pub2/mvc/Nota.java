/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pub2.mvc;

import com.mysql.jdbc.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author User
 */
public class Nota {

    private int idUser;
    private int idMasa;
    private int total;

    public Nota(int idUser, int idMasa, int total) {
        this.idUser = idUser;
        this.idMasa = idMasa;
        this.total = total;
    }

    public void setIdMasa(int idMasa) {
        this.idMasa = idMasa;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getTotal() {
        return total;
    }

    public Boolean adaugaInBd() {
        try {
            Connection con = BazaDeDate.getCon();
            String sql = "INSERT INTO Nota(id_user, id_masa, total) VALUE (?, ?, ?)";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, idUser);
            stmt.setInt(2, idMasa);
            stmt.setInt(3, total);
            stmt.execute();
            stmt.close();
        } catch (ClassNotFoundException | SQLException ex) {
            return true;
        }
        return false;
    }
}
