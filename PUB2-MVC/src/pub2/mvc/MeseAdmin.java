/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pub2.mvc;

import com.mysql.jdbc.Connection;
import java.awt.Point;
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
    private Boolean modStergere;

    public MeseAdmin() {
        super();
        pozitieStart = null;
        masaSelectata = null;
        modStergere = false;

    }

    public void setPozitieStart(Point p) {
        pozitieStart = p;
    }

    public Point getPozitieStart() {
        return pozitieStart;
    }

    public void setMasaSelectata(JButton m) {
        masaSelectata = m;
    }

    public JButton getMasaSelectata() {
        return masaSelectata;
    }

    public Boolean getModStergere() {
        return modStergere;
    }

    public void setModStergere(Boolean status) {
        modStergere = status;
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

    public void stergeDinMemorie() {
        getListaMese().remove(getByButon(masaSelectata));
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

    public Boolean stergeDinBd() {
        try {
            java.sql.Connection con = BazaDeDate.getCon();
            String sql = "DELETE FROM Masa WHERE id = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, getByButon(masaSelectata).getId());
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

    public Masa getMasa(Point p) {
        for (Masa masa : getListaMese()) {
            if (masa.getButon().contains(p)) {
                return masa;
            }
        }
        return null;
    }

}
