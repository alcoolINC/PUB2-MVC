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
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;
import static javax.swing.SwingUtilities.convertPoint;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author User
 */
public class ControllerMeseAdmin {

    private final MeseAdmin model;
    private final ViewMeseAdmin view;

    public ControllerMeseAdmin(MeseAdmin model, ViewMeseAdmin view) {
        this.model = model;
        this.view = view;
        initView();   
        initController();
        initModel();
        updateView();
    }

    private void initView() {
        view.setVisible(true);
        view.getLabel().setText(String.valueOf(Login.getIdUserLogat()));
    }

    private void updateView() {
        view.getPanou().revalidate();
        view.getPanou().repaint();
        view.getCampNumar().setText("");
    }

    private void initController() {
        view.getPanou().addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                mouseApasat(e);
                afiseazaMeniu(e);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                mouseEliberat();
            }
        });

        view.getPanou().addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                mouseTras(e);
            }
        });
        
        view.getButonModStergere().addActionListener(e -> schimbaMod());
        view.getButonGestionareProduse().addActionListener(e -> gestioneazaProduse());
        view.getButonGestionareUtilizatori().addActionListener(e -> gestioneazaUtilizatori());
        view.getButonRaport().addActionListener(e -> genereazaRaport());
    }
    
    public void afiseazaMeniu(MouseEvent e){
        if (SwingUtilities.isRightMouseButton(e)){
            JPopupMenu meniu = new MeniuClick(view, model);
            meniu.show(view.getPanou(), e.getX(), e.getY());
        }
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

    public void schimbaMod() {
        model.setModStergere(!model.getModStergere());
        JOptionPane.showMessageDialog(new JFrame(), "MOD SCHIMBAT");
    }

    public void gestioneazaProduse() {
        new ControllerProduse(new Produse(), new ViewProduse());
    }

    public void gestioneazaUtilizatori() {
        new ControllerUseri(new Useri(), new ViewUseri());
    }

    public void genereazaRaport() {
        ViewRaport viewRaport = new ViewRaport();
        DefaultTableModel modelTabelaRaport = (DefaultTableModel) viewRaport.getTable().getModel();
        new ControllerRaport(viewRaport, modelTabelaRaport);
    }

}
