/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pub2.mvc;

import java.awt.Point;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;

/**
 *
 * @author User
 */
public class MeniuClick extends JPopupMenu {

    private final ViewMeseAdmin view;
    private final MeseAdmin model;
    private final Point pozitieMeniu;

    public MeniuClick(ViewMeseAdmin view, MeseAdmin model, Point pozitieMeniu) {

        this.view = view;
        this.model = model;
        this.pozitieMeniu = pozitieMeniu;

        JMenuItem optiuneAdaugare = new JMenuItem("adauga masa");
        optiuneAdaugare.addActionListener(e -> adaugaMasa());
        this.add(optiuneAdaugare);

        JMenuItem optiuneStergere = new JMenuItem("sterge masa");
        optiuneStergere.addActionListener(e -> stergeMasa());
        this.add(optiuneStergere);
    }

    public void adaugaMasa() {
        String numar = view.getCampNumar().getText();
        Point p = view.getPanou().getMousePosition();
        Masa masa = new Masa(numar, p.x, p.y);
        if (model.seSuprapune(masa)) {
            JOptionPane.showMessageDialog(new JFrame(), "EROARE SUPRAPUNERE");
            return;
        }
        model.getListaMese().add(masa);

        Boolean eroare = model.adaugaInBd(masa);
        if (eroare) {
            JOptionPane.showMessageDialog(new JFrame(), "EROARE ADAUGARE MASA IN BD");
            // Exista sansa sa fie masa adaugata in memorie anterior
            model.stergeDinMemorie(masa);
            return;
        }
        masa.setId(BazaDeDate.returneazaUltimaCheie());

        // Adauga listeneri
        MouseListener[] m = view.getPanou().getMouseListeners();
        for (MouseListener i : m) {
            masa.getButon().addMouseListener(i);
        }
        MouseMotionListener[] n = view.getPanou().getMouseMotionListeners();
        for (MouseMotionListener i : n) {
            masa.getButon().addMouseMotionListener(i);
        }

        view.getPanou().add(masa.getButon());
        updateView();
    }

    public void stergeMasa() {
        Masa deSters = model.getMasa(pozitieMeniu);
        if (deSters == null) {
            return;
        }
        model.stergeDinMemorie(deSters);
        model.stergeDinBd(deSters.getId());
        view.getPanou().remove(deSters.getButon());
        updateView();
    }

    public void updateView() {
        view.revalidate();
        view.repaint();
    }
}
