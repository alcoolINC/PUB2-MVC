/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pub2.mvc;

import java.awt.event.MouseListener;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author User
 */
public class MeseAngajat {

    private ArrayList<Masa> mese = null;

    public Masa getById(int id) {
        for (Masa masa : mese) {
            if (masa.getId() == id) {
                return masa;
            }
        }
        return null;
    }

    public Masa getByButon(JButton butonMasaSelectat) {
        for (Masa masa : mese) {
            if (masa.getButon() == butonMasaSelectat) {
                return masa;
            }
        }
        System.out.println("EROARE IDENTIFICARE MASA LA APASARE");
        return null;
    }

    public void citesteDinBd() {
        if (mese != null) {
            return;
        }

        try {
            java.sql.Connection con = BazaDeDate.getCon();
            Statement stmt = con.createStatement();
            String sql = "SELECT id, numar, x, y FROM Masa";
            ResultSet rs = stmt.executeQuery(sql);
            mese = new ArrayList();
            while (rs.next()) {
                // Extragere pozitie masa
                int id = rs.getInt(1);
                String numar = rs.getString(2);
                int x = rs.getInt(3);
                int y = rs.getInt(4);

                // Instantiere masa
                Masa masa = new Masa(id, numar, x, y);
                mese.add(masa);
            }
            rs.close();
            stmt.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(new JFrame(), "EROARE EXTRAGERE MESE DIN BD");
        }
    }

    public void adaugaInPanou(JPanel panou) {
        for (Masa masa : mese) {
            panou.add(masa.getButon());
        }
    }

    public void adaugaListener(JPanel view) {
        MouseListener[] listeneri = view.getMouseListeners();
        for (Masa masa : mese) {
            for (MouseListener listener : listeneri) {
                masa.getButon().addMouseListener(listener);
            }
        }
    }

    public ArrayList<Masa> getListaMese() {
        return mese;
    }

}
