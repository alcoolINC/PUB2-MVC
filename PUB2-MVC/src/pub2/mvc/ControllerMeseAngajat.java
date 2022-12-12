/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pub2.mvc;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author User
 */
public class ControllerMeseAngajat {

    ViewMeseAngajat view;
    ModelMese model;

    public ControllerMeseAngajat(ModelMese model, ViewMeseAngajat view) {
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
            public void mouseClicked(MouseEvent e) {
                // Detectarea mesei apasate    
                try {
                    JButton butonMasaSelectat = (JButton) e.getSource();
                    afiseazaComanda(model.getByButon(butonMasaSelectat).getId());
                } catch (Exception ex) {
                    System.out.println("acolo nu e masa");
                    System.out.println(ex);
                }
            }
        });
    }

    private void initModel() {
        model.citesteDinBd();
        model.adaugaListener(view.getPanou());
        model.adaugaInPanou(view.getPanou());
    }

    private void afiseazaComanda(int idMasa) {
        ViewComanda viewComanda = new ViewComanda();
        ModelComanda modelComanda = model.getById(idMasa).getModelComanda();
        modelComanda.setTable(viewComanda.getTableComanda());
        ModelProduse modelProduse = new ModelProduse();
        modelProduse.setTable(viewComanda.getTableProduse());
        modelProduse.citesteDinBd();
        modelProduse.completeazaTable();
        ControllerComanda c = new ControllerComanda(modelComanda, modelProduse,
                viewComanda);
        c.initView(idMasa);
        c.initController();
    }
}
