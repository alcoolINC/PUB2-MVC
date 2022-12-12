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
public class ControllerUtilizatori {

    private ModelUtilizatori model;
    private ViewUtilizatori view;

    public ControllerUtilizatori(ModelUtilizatori model, ViewUtilizatori view) {
        this.model = model;
        this.view = view;

        initController();
        initView();
        initModel();
        updateView();
    }

    private void initView() {
        view.setAlwaysOnTop(true);
        view.setVisible(true);
        view.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
    }

    private void updateView() {
        view.getTable().repaint();
    }

    private void initController() {
        view.getButonAdaugare().addActionListener(e -> adauga());
        view.getButonStergere().addActionListener(e -> sterge());
    }

    private void initModel() {
        model.setTable(view.getTable());
        model.citesteDinBd();
        model.completeazaTable();
    }

    private void adauga() {
        String nume = view.getCampNume().getText();
        String user = view.getCampUser().getText();
        String parola = view.getCampParola().getText();
        int rol = Integer.parseInt(view.getCampRol().getText());

        Boolean eroare = model.adaugaInBd(user, parola, nume, rol);
        if (eroare) {
            JOptionPane.showMessageDialog(new JFrame(), "EROARE INSERARE UTILIZATOR");
            return;
        }

        model.citesteDinBd();
        model.completeazaTable();
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
            JOptionPane.showMessageDialog(new JFrame(), "EROARE STEGERE UTILIZATOR");
            return;
        }

        model.citesteDinBd();
        model.completeazaTable();
        updateView();
    }
}
