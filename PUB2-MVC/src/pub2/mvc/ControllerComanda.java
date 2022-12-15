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

    private ModelComanda modelComanda;
    private ModelProduse modelProduse;
    private ViewComanda view;

    public ControllerComanda(ModelComanda modelComanda, ModelProduse modelProduse,
            ViewComanda view) {
        this.modelComanda = modelComanda;
        this.modelProduse = modelProduse;
        this.view = view;
    }

    public void initView(int idMasa) {
        view.setAlwaysOnTop(true);
        view.setVisible(true);
        view.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        view.setTitle(String.valueOf(idMasa));
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
        modelComanda.getMasa().setOcupat();
        int indexRand = view.getTableProduse().getSelectedRow();
        modelComanda.adaugaProdus(modelProduse, indexRand);
        view.getCampTotal().setText(String.valueOf(modelComanda.getTotal()));
    }

    private void anuleaza() {
        modelComanda.reseteaza();
        view.getCampTotal().setText("0");
        modelComanda.getMasa().setLiber();
    }

    private void plateste() {
        modelComanda.plateste();
        // Reseteaza produsele si totalul
        anuleaza();
    }
}
