/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pub2.mvc;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author User
 */
public class ControllerProduse {

    private Produse model;
    private ViewProduse view;

    public ControllerProduse(Produse model, ViewProduse view) {
        this.model = model;
        this.view = view;

        initController();
        initView();
        initModel();
        updateView();
    }

    private void initView() {
        //view.setAlwaysOnTop(true);
        view.setVisible(true);
        view.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
    }

    private void updateView() {
        view.getTable().repaint();
        view.getCampNume().setText("");
        view.getCampPret().setText("");
        view.getCampCategorie().setText("");
    }

    private void initController() {
        view.getButonAdaugare().addActionListener(e -> adauga());
        view.getButonStergere().addActionListener(e -> sterge());
        view.getButonModificare().addActionListener(e -> modifica());
    }

    private void initModel() {
        model.setTable(view.getTable());
        model.citesteDinBd();
        model.completeazaTable();
    }

    private void adauga() {
        String nume = view.getCampNume().getText();
        int pret = Integer.parseInt(view.getCampPret().getText());
        String categorie = view.getCampCategorie().getText();

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
        int id = model.getValoare(indexRand, 0);

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
        if (view.getTable().getSelectedColumnCount() != 1) {
            return;
        }

        int indexRand = view.getTable().getSelectedRow();
        int id = model.getValoare(indexRand, 0);
        String nume = view.getCampNume().getText();
        int pret = Integer.parseInt(view.getCampPret().getText());
        String categorie = view.getCampCategorie().getText();

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
}
