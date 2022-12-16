/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pub2.mvc;

/**
 *
 * @author User
 */
public class PUB2MVC {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        ControllerLogin c = new ControllerLogin(new Login("", ""),
                new ViewLogin());
    }
    
}
