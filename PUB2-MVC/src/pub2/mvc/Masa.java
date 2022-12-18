/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pub2.mvc;

import com.mysql.jdbc.Connection;
import java.awt.Color;
import java.awt.Point;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JButton;

/**
 * Definește modelul datelor unei mese.
 *
 * @author User
 */
public class Masa {

    /**
     * Lungimea în pixeli a laturii unei mese din panou.
     */
    private static int latura = 75;
    /**
     * Valoare implicită a abscisei punctului unde este instanțiată o masă nouă.
     */
    private static int xImplicit = 100;
    /**
     * Valoare implicită a ordonatei punctului unde este instanțiată o masă
     * nouă.
     */
    private static int yImplicit = 100;
    /**
     * Culoarea butonului din panou corespunzător cu o masă liberă.
     */
    private static Color culoareImplicita = Color.GREEN;
    /**
     * Culoarea butonului din panou corespunzător cu o masă ocupată.
     */
    private static Color culoareOcupat = Color.YELLOW;
    /**
     * Identificatorul unic al unei mese.
     */
    private int id;
    /**
     * Componenta JButton vizibilă în JPanel.
     */
    private JButton buton;
    /**
     * Obiect al clasei Comanda. Conține modelul datelor unei comenzi.
     */
    private Comanda comanda;

    /**
     * Constructorul cu un parametru.
     *
     * @param text textul care apare pe butonul mesei din panou
     */
    public Masa(String text) {
        this(text, xImplicit, yImplicit);
    }

    /**
     * Contrsutctorul cu trei parametri.
     *
     * @param text textul care apare pe butonul mesei din panou
     * @param x abscisa punctului în care este poziționat colțul stânga-sus al
     * butonului mesei în panou
     * @param y ordonata punctului în care este poziționat colțul stânga-sus al
     * butonului mesei din panou
     */
    public Masa(String text, int x, int y) {
        buton = new JButton();
        buton.setLocation(x, y);
        buton.setSize(latura, latura);
        buton.setBackground(culoareImplicita);
        buton.setText(text);
        buton.setVisible(true);
        comanda = new Comanda(id, Login.getIdUserLogat());
    }

    /**
     * Contructorul cu patru parametri.
     *
     * @param id identificatorul unic al mesei
     * @param numar textul scris pe butonul mesei
     * @param x abscisa butonului mesei
     * @param y ordonata butonului mesei
     */
    public Masa(int id, String numar, int x, int y) {
        this(numar, x, y);
        this.id = id;
    }

    /**
     * Returnează identificatorul mesei.
     *
     * @return identificator masă
     */
    int getId() {
        return id;
    }

    /**
     * Returnează comanda corespunzătoare mesei.
     *
     * @return obiect Comanda
     */
    public Comanda getComanda() {
        return comanda;
    }

    /**
     * Modifică identificatorul mesei.
     *
     * @param id identificatorul nou
     */
    public void setId(int id) {
        this.id = id;
        comanda.setIdMasa(id);
    }

    /**
     * Modifică culoarea butonului mesei.
     */
    public void setOcupat() {
        this.buton.setBackground(culoareOcupat);
    }

    /**
     * Modifică culoarea butonului mesei.
     */
    public void setLiber() {
        this.buton.setBackground(culoareImplicita);
    }

    /**
     * Returnează butonul mesei.
     *
     * @return butonul mesei
     */
    public JButton getButon() {
        return buton;
    }

    /**
     * Returnează latura mesei.
     *
     * @return latura mesei
     */
    public static int getLatura() {
        return latura;
    }

    /**
     * Actualizează poziția unei mese în baza de date
     *
     * @param pozitie punctul care corespunde cu poziția nouă
     * @return true în caz de eroare <br>
     * false în caz de succes
     */
    public Boolean actualizeazaPozitieInBd(Point pozitie) {
        try {
            Connection con = BazaDeDate.getCon();
            String sql = "UPDATE Masa SET x = ?, y = ? WHERE id = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, pozitie.x);
            stmt.setInt(2, pozitie.y);
            stmt.setInt(3, id);
            stmt.execute();
            stmt.close();
        } catch (ClassNotFoundException | SQLException ex) {
            return true;
        }
        return false;
    }
}
