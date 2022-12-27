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
public class Produs {

    private String nume;
    private String categorie;
    private int pret;
    private int id;

    public Produs(int id, String nume, int pret, String categorie) {
        this.id = id;
        this.nume = nume;
        this.pret = pret;
        this.categorie = categorie;
    }

    public String getNume() {
        return nume;
    }

    public int getId() {
        return id;
    }

    public int getPret() {
        return pret;
    }
    
    public String getCategorie() {
        return categorie;
    }
}
