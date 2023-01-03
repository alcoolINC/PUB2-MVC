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
public class ControllerLogin {

    Login model;
    ViewLogin view;

    public ControllerLogin(Login model, ViewLogin view) {
        this.model = model;
        this.view = view;
        initController();
        initView();
    }

    public void initView() {
        view.setVisible(true);
        view.setResizable(false);
    }
    
    public void updateView() {
        view.getCampUser().setText("");
        view.getCampParola().setText("");
    }

    public void initController() {
        view.getButonLogin().addActionListener(e -> valideaza());
    }

    private void valideaza() {
        model.setUsername(view.getCampUser().getText());
        model.setParola(view.getCampParola().getText());
        int status = model.valideaza();
        switch (status) {
            case -1:
                JOptionPane.showMessageDialog(new JFrame(), "User sau parola gresita!");
                updateView();
                return;
            case 0:
                new ControllerMeseAngajat(new MeseAngajat(), new ViewMeseAngajat());
                view.dispose();
                return;
            case 1:
                new ControllerMeseAdmin(new MeseAdmin(), new ViewMeseAdmin());
                view.dispose();
                return;
            case -2:
                JOptionPane.showMessageDialog(new JFrame(), "EROARE CONECTARE LA BD");
                return;
        }

    }
}
