/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pub2.mvc;

import java.util.ArrayList;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author User
 */
public class ModelComanda {

    private Masa masa; // Camp masa necesar pt workaround
    private ArrayList<Produs> produse;
    private Nota nota;
    private DefaultTableModel tableComanda;

    public ModelComanda(Masa masa, int idUser) {
        this.masa = masa;
        nota = new Nota(idUser, masa.getId(), 0);
        produse = new ArrayList();
        tableComanda = new DefaultTableModel(new String[]{"id", "nume", "pret"} , 0);
    }

    public DefaultTableModel getTable() {
        return tableComanda;
    }
    
    public Masa getMasa() {
        return masa;
    }

    public void setIdMasa(int idMasa) {
        nota.setIdMasa(idMasa);
    }
    
    public int getTotal(){
        return nota.getTotal();
    }

    public void adaugaProdus(ModelProduse modelProduse, int indexRand) {
        DefaultTableModel tableProduse = modelProduse.getTable();
        int id = Integer.parseInt((String) tableProduse.getValueAt(indexRand, 0));
        String nume = (String) tableProduse.getValueAt(indexRand, 1);
        int pret = Integer.parseInt((String) tableProduse.getValueAt(indexRand, 2));
        Produs produs = new Produs(id, nume, pret);
        produse.add(produs);
        nota.setTotal(nota.getTotal() + produs.getPret());
        String[] rand = {String.valueOf(produs.getId()), produs.getNume(),
            String.valueOf(produs.getPret())};
        tableComanda.addRow(rand);
    }

    public void reseteaza() {
        produse = new ArrayList();
        nota.setTotal(0);
        tableComanda.getDataVector().removeAllElements();
        tableComanda.fireTableDataChanged();
    }

    public void plateste() {
        nota.adaugaInBd();
    }
}
