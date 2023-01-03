/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pub2.mvc;

import javax.swing.JFrame;

/**
 *
 * @author User
 */
public class ControllerComanda {

    private final Masa masa;
    private final Produse modelProduse;
    private final ViewComanda view;

    public ControllerComanda(Masa masa, Produse modelProduse,
            ViewComanda view) {
        this.masa = masa;
        this.modelProduse = modelProduse;
        this.view = view;
    }

    public void initView() {
        view.setAlwaysOnTop(true);
        view.setVisible(true);
        view.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        view.setTitle(masa.getButon().getText());
        view.setResizable(false);
    }

    public void initController() {
        view.getButonAdaugareProdus().addActionListener(e -> adaugaProdus());
        view.getButonAnulare().addActionListener(e -> anuleaza());
        view.getButonPlata().addActionListener(e -> plateste());
    }

    private void adaugaProdus() {
        if (view.getTableProduse().getSelectedRowCount() != 1) {
            return;
        }
        masa.setOcupat();
        int indexRand = view.getTableProduse().getSelectedRow();
        masa.getComanda().adaugaProdus(modelProduse, indexRand);
        view.getCampTotal().setText(String.valueOf(masa.getComanda().getTotal()));
    }

    private void anuleaza() {
        masa.getComanda().reseteaza();
        view.getCampTotal().setText("0");
        masa.setLiber();
    }

    private void plateste() {
        masa.getComanda().getNota().adaugaInBd();
        // Reseteaza produsele si totalul
        anuleaza();
    }
}
