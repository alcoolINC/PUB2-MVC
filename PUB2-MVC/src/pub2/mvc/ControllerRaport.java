/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pub2.mvc;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author User
 */
public class ControllerRaport {

    ViewRaport view;
    DefaultTableModel table;

    public ControllerRaport(ViewRaport view, DefaultTableModel table) {
        this.view = view;
        this.table = table;

        initController();
        initView();
        initModel();
        updateView();
    }

    private void initView() {
        view.setVisible(true);
        view.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        view.setResizable(false);
    }

    private void updateView() {
        view.repaint();
    }

    private void initController() {
        view.getButonExport().addActionListener(e -> exporta());
    }

    private void initModel() {
        Boolean eroare = BazaDeDate.genereazaRaport(table);
        if (eroare) {
            JOptionPane.showMessageDialog(new JFrame(), "EROARE GENERARE RAPORT");
        }
    }

    private void exporta() {
        
    }

}
