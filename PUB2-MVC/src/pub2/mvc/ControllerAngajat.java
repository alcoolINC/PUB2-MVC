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
public class ControllerAngajat {
     ViewRaport view;
    
    public ControllerAngajat(){
        initView();
        
    }
    private void initView(){
        view.setAlwaysOnTop(true);
        view.setVisible(true);
        view.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
    }   
}
