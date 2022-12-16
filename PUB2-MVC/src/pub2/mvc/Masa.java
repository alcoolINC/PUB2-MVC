/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pub2.mvc;

import com.mysql.jdbc.Connection;
import java.awt.Color;
import java.awt.Point;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JButton;

/**
 *
 * @author User
 */
public class Masa {

    private static int latura = 75;
    private static int xImplicit = 100;
    private static int yImplicit = 100;
    private static Color culoareImplicita = Color.GREEN;
    private static Color culoareOcupat = Color.YELLOW;

    private int id;
    private Comanda comanda;
    private JButton buton;

    public Masa(String text) {
        this(text, xImplicit, yImplicit);
    }

    public Masa(String text, int x, int y) {
        buton = new JButton();
        buton.setLocation(x, y);
        buton.setSize(latura, latura);
        buton.setBackground(culoareImplicita);
        buton.setText(text);
        buton.setVisible(true);
    }

    public Masa(int id, String numar, int x, int y) {
        this(numar, x, y);
        comanda = new Comanda(id, Login.getIdUserLogat());
        this.id = id;
    }

    int getId() {
        return id;
    }

    public Comanda getComanda() {
        return comanda;
    }

    public void setId(int id) {
        this.id = id;
        comanda.setIdMasa(id);
    }

    public void setOcupat() {
        this.buton.setBackground(culoareOcupat);
    }

    public void setLiber() {
        this.buton.setBackground(culoareImplicita);
    }

    public JButton getButon() {
        return buton;
    }

    public static int getLatura() {
        return latura;
    }
    
    public void adaugaComanda() {
        this.comanda = new Comanda(id, Login.getIdUserLogat());
    }

    public Boolean actualizeazaPozitieInBd(Point pozitie) {
        try {
            Connection con = BazaDeDate.getCon();
            String sql = "UPDATE Masa SET x = ?, y = ? WHERE id = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, pozitie.x);
            stmt.setInt(2, pozitie.y);
            stmt.setInt(3, id);
            stmt.execute();
            stmt.close();
        } catch (ClassNotFoundException | SQLException ex) {
            return true;
        }
        return false;
    }
}
