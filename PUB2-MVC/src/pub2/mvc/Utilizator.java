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
public class Utilizator {

    private String user;
    private String nume;
    private int id;
    private int rol;

    public Utilizator(int id, String user, String nume, int rol) {
        this.id = id;
        this.user = user;
        this.nume = nume;
        this.rol = rol;
    }
    
    public String getUser() {
        return user;
    }
    
    public String getNume() {
        return nume;
    }

    public int getId() {
        return id;
    }

    public int getRol() {
        return rol;
    }
}
