/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pub2.mvc;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import static javax.swing.SwingUtilities.convertPoint;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author User
 */
public class ControllerMeseAdmin {

    private ModelMeseAdmin model;
    private ViewMeseAdmin view;

    public ControllerMeseAdmin(ModelMeseAdmin model, ViewMeseAdmin view) {
        this.model = model;
        this.view = view;
        initView();
        initController();
        initModel();
        updateView();
    }

    private void initView() {
        view.setAlwaysOnTop(true);
        view.setVisible(true);
        view.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
    }

    private void updateView() {
        view.getPanou().revalidate();
        view.getPanou().repaint();
    }

    private void initController() {
        view.getPanou().addMouseListener(new MouseAdapter() {
            //public void mouseClicked(MouseEvent e) {
            //}
            public void mousePressed(MouseEvent e) {
                mouseApasat(e);
            }

            public void mouseReleased(MouseEvent e) {
                mouseEliberat();
            }
        });

        view.getPanou().addMouseMotionListener(new MouseAdapter() {

            public void mouseDragged(MouseEvent e) {
                mouseTras(e);
            }
        });

        view.getButonAdaugareMasa().addActionListener(e -> adaugaMasa());
        view.getButonModStergere().addActionListener(e -> schimbaMod());
        view.getButonGestionareProduse().addActionListener(e -> gestioneazaProduse());
        view.getButonGestionareUtilizatori().addActionListener(e -> gestioneazaUtilizatori());
        view.getButonRaport().addActionListener(e -> genereazaRaport());
    }

    private void initModel() {
        model.citesteDinBd();
        model.adaugaListener(view.getPanou());
        model.adaugaMotionListener(view.getPanou());
        model.adaugaInPanou(view.getPanou());
    }

    private void mouseApasat(MouseEvent e) {
        try {
            model.setMasaSelectata((JButton) e.getSource());
        } catch (Exception ex) {
            // Daca nu a fost selectata o masa
            return;
        }

        // Mod stergere
        if (model.getModStergere() == true) {
            Boolean eroare = model.stergeDinBd();
            if (eroare) {
                JOptionPane.showMessageDialog(new JFrame(), "EROARE STERGERE MASA IN BD");
                return;
            }
            model.stergeDinMemorie();
            view.getPanou().remove(model.getMasaSelectata());
            updateView();
            return;
        }

        model.setPozitieStart(SwingUtilities.convertPoint(model.getMasaSelectata(),
                e.getPoint(), model.getMasaSelectata().getParent()));
    }

    private void mouseEliberat() {
        model.setPozitieStart(null);
        model.setMasaSelectata(null);
    }

    private void mouseTras(MouseEvent e) {
        JButton masaSelectata = model.getMasaSelectata();
        Point pozitieMouse = convertPoint(masaSelectata, e.getPoint(),
                masaSelectata.getParent());

        if (!(view.getPanou().getBounds().contains(pozitieMouse))) {
            return;
        }

        Point pozitieStart = model.getPozitieStart();
        Point pozitieVeche = masaSelectata.getLocation();
        Point pozitieNoua = masaSelectata.getLocation();
        pozitieNoua.translate(pozitieMouse.x - pozitieStart.x, pozitieMouse.y - pozitieStart.y);

        // Prevenim depasirea panoului
        pozitieNoua.x = Math.max(pozitieNoua.x, 0);
        pozitieNoua.y = Math.max(pozitieNoua.y, 0);
        pozitieNoua.x = Math.min(pozitieNoua.x, masaSelectata.getParent().getWidth()
                - masaSelectata.getWidth());
        pozitieNoua.y = Math.min(pozitieNoua.y, masaSelectata.getParent().getHeight()
                - masaSelectata.getHeight());

        // Prevenim coliziunea
        ArrayList<Masa> mese = model.getListaMese();
        for (int i = 0; i < mese.size(); i++) {
            Rectangle masaInViitor = new Rectangle(pozitieNoua.x, pozitieNoua.y,
                    Masa.getLatura(), Masa.getLatura());

            if (masaInViitor.intersects(mese.get(i).getButon().getBounds())
                    & (mese.get(i).getButon() != masaSelectata)) {
                return;
            }
        }

        // Actualizare locatie
        model.setPozitieStart(pozitieMouse);
        masaSelectata.setLocation(pozitieNoua);
        Boolean eroare = model.getByButon(masaSelectata).actualizeazaPozitieInBd(pozitieNoua);
        if (eroare) {
            JOptionPane.showMessageDialog(new JFrame(), "EROARE ACTUALIZARE LOCATIE MASA IN BD");
        }
        updateView();
    }

    public void adaugaMasa() {
        String numar = view.getCampNumar().getText();
        Masa masa = new Masa(numar);
        Boolean eroare = model.adaugaInMemorie(masa);
        if (eroare) {
            JOptionPane.showMessageDialog(new JFrame(), "Exista alta masa in pozitia de spawn.");
            return;
        }
        masa.adaugaModelComanda();
        eroare = model.adaugaInBd(masa);
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

        model.adaugaInMemorie(masa);
        view.getPanou().add(masa.getButon());
        updateView();
    }

    public void schimbaMod() {
        model.setModStergere(!model.getModStergere());
        JOptionPane.showMessageDialog(new JFrame(), "MOD SCHIMBAT");
    }

    public void gestioneazaProduse() {
        new ControllerProduse(new ModelProduse(), new ViewProduse());
    }

    public void gestioneazaUtilizatori() {
        new ControllerUtilizatori(new ModelUtilizatori(), new ViewUtilizatori());
    }

    public void genereazaRaport() {
        ViewRaport view = new ViewRaport();
        DefaultTableModel model = (DefaultTableModel) view.getTable().getModel();
        new ControllerRaport(view, model);
    }

}
