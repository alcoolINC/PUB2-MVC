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
public class User {

    private final String username;
    private final String alias;
    private final int id;
    private final int rol;

    public User(int id, String username, String alias, int rol) {
        this.id = id;
        this.username = username;
        this.alias = alias;
        this.rol = rol;
    }
    
    public String getUsername() {
        return username;
    }
    
    public String getAlias() {
        return alias;
    }

    public int getId() {
        return id;
    }

    public int getRol() {
        return rol;
    }
}
