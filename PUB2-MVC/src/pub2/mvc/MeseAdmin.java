/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pub2.mvc;

import com.mysql.jdbc.Connection;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseMotionListener;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 *
 * @author User
 */
public class MeseAdmin extends MeseAngajat {

    private Point pozitieStart;
    private JButton masaSelectata;

    public MeseAdmin() {
        super();
        pozitieStart = null;
        masaSelectata = null;
    }

    public void setPozitieStart(Point pozitieStart) {
        this.pozitieStart = pozitieStart;
    }

    public Point getPozitieStart() {
        return pozitieStart;
    }

    public void setMasaSelectata(JButton butonMasa) {
        masaSelectata = butonMasa;
    }

    public JButton getMasaSelectata() {
        return masaSelectata;
    }

    public void adaugaMotionListener(JPanel panou) {
        MouseMotionListener[] listeneri = panou.getMouseMotionListeners();
        ArrayList<Masa> mese = getListaMese();
        for (Masa masa : mese) {
            for (MouseMotionListener listener : listeneri) {
                masa.getButon().addMouseMotionListener(listener);
            }
        }
    }

    public Boolean adaugaInMemorie(Masa masa) {
        if (seSuprapune(masa)) {
            return true;
        }
        getListaMese().add(masa);
        return false;
    }

    public void stergeDinMemorie(Masa masa) {
        getListaMese().remove(masa);
    }

    public Boolean adaugaInBd(Masa masa) {
        try {
            Connection con = BazaDeDate.getCon();
            String sql = "INSERT INTO Masa(numar, x, y) value (?, ?, ?)";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, masa.getButon().getText());
            stmt.setInt(2, masa.getButon().getX());
            stmt.setInt(3, masa.getButon().getY());
            stmt.execute();
            stmt.close();
        } catch (ClassNotFoundException | SQLException ex) {
            return true;
        }
        return false;
    }

    public Boolean stergeDinBd(int id) {
        try {
            java.sql.Connection con = BazaDeDate.getCon();
            String sql = "DELETE FROM Masa WHERE id = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, id);
            stmt.execute();
            stmt.close();
        } catch (ClassNotFoundException | SQLException ex) {
            return true;
        }
        return false;
    }

    public Boolean seSuprapune(Masa masa) {
        for (Masa i : getListaMese()) {
            if (masa.getButon().getBounds().intersects(i.getButon().getBounds())
                    & (masa.getButon() != i.getButon())) {
                return true;
            }
        }
        return false;
    }

    public Masa getMasa(Point punctDinPanou) {
        for (Masa masa : getListaMese()) {
            if (new Rectangle(masa.getButon().getLocation(),
                    new Dimension(masa.getLatura(),
                            masa.getLatura())).contains(punctDinPanou)) {
                return masa;
            }
        }
        return null;
    }

}
