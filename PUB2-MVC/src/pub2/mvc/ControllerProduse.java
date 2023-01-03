/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pub2.mvc;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author User
 */
public class ControllerProduse {

    private final Produse model;
    private final ViewProduse view;

    public ControllerProduse(Produse model, ViewProduse view) {
        this.model = model;
        this.view = view;

        initView();
        initController();
    }

    private void initView() {
        view.setVisible(true);
        view.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        view.setResizable(false);
    }

    private void updateView() {
        view.getTable().repaint();
        view.getCampNume().setText("");
        view.getCampPret().setText("");
        view.getComboBoxCategorie().setSelectedIndex(0);
    }

    private void initController() {
        view.getButonAdaugare().addActionListener(e -> adauga());
        view.getButonStergere().addActionListener(e -> sterge());
        view.getButonModificare().addActionListener(e -> modifica());
        
        view.getTable().addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (view.getTable().getSelectedRowCount() != 1) {
                    return;
                }
                
                int indexRand = view.getTable().getSelectedRow();
                view.getCampNume().setText(
                        (String) model.getTable().getValueAt(indexRand, 1));
                view.getCampPret().setText(
                        (String) model.getTable().getValueAt(indexRand, 2));
                view.getComboBoxCategorie().setSelectedItem(
                        (String) model.getTable().getValueAt(indexRand, 3));
            }
        });
    }

    private void adauga() {
        String nume = view.getCampNume().getText();
        int pret = Integer.parseInt(view.getCampPret().getText());
        String categorie = (String) view.getComboBoxCategorie().getSelectedItem();

        Boolean eroare = model.adaugaInBd(nume, pret, categorie);
        if (eroare) {
            JOptionPane.showMessageDialog(new JFrame(), "EROARE ADAUGARE PRODUS");
            return;
        }

        int id = BazaDeDate.returneazaUltimaCheie();
        Produs produs = new Produs(id, nume, pret, categorie);

        model.citesteDinBd();
        model.completeazaTable();
        model.serializeaza();
        updateView();
    }

    private void sterge() {
        if (view.getTable().getSelectedRowCount() != 1) {
            return;
        }

        int indexRand = view.getTable().getSelectedRow();
        int id = Integer.parseInt(
                (String) model.getTable().getValueAt(indexRand, 0));

        Boolean eroare = model.stergeDinBd(id);
        if (eroare) {
            JOptionPane.showMessageDialog(new JFrame(), "EROARE STEGERE PRODUS");
            return;
        }

        model.citesteDinBd();
        model.completeazaTable();
        model.serializeaza();
        updateView();
    }

    private void modifica() {
        if (view.getTable().getSelectedRowCount() != 1) {
            return;
        }

        int indexRand = view.getTable().getSelectedRow();
        int id = Integer.parseInt(
                (String) model.getTable().getValueAt(indexRand, 0));
        String nume = view.getCampNume().getText();
        int pret = Integer.parseInt(view.getCampPret().getText());
        String categorie = (String) view.getComboBoxCategorie().getSelectedItem();

        Boolean eroare = model.modificaInBd(id, nume, pret, categorie);
        if (eroare) {
            JOptionPane.showMessageDialog(new JFrame(), "EROARE ACTUALIZARE PRODUS");
            return;
        }

        model.citesteDinBd();
        model.completeazaTable();
        model.serializeaza();
        updateView();
    }

    public void completeazaDate() {

    }
}
