/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author Oihane Axpe
 * @version V1.0
 */

public class Pelikula {
    /* ATRIBUTOAK */
    private final SimpleStringProperty id;
    private final SimpleStringProperty izena;
    private final SimpleStringProperty gaia;
    private final SimpleIntegerProperty urtea;
    private final SimpleIntegerProperty iraupena;
    private final SimpleStringProperty herrialdea;
    
    /* ERAIKITZAILEA */
    public Pelikula(String pId, String pIzena, String pGaia, int pUrtea, int pIraupena, String pHerrialdea) { 
        this.id = new SimpleStringProperty(pId);
        this.izena = new SimpleStringProperty(pIzena);
        this.gaia = new SimpleStringProperty(pGaia);
        this.urtea = new SimpleIntegerProperty(pUrtea);
        this.iraupena = new SimpleIntegerProperty(pIraupena);
        this.herrialdea = new SimpleStringProperty(pHerrialdea);
    }
    
    /* GETTERS & SETTERS */
    public String getId() {
        return id.get();
    }
    public void setId(String pId) {
        id.set(pId);
    }
    public String getIzena() {
        return izena.get();
    }
    public void setIzena(String pIzena) {
        izena.set(pIzena);
    }
    public String getGaia() {
        return gaia.get();
    }
    public void setGaia(String pGaia) {
        gaia.set(pGaia);
    }
    public int getUrtea() {
        return urtea.get();
    }
    public void setUrtea(int pUrtea) {
        urtea.set(pUrtea);
    }
    public int getIraupena() {
        return iraupena.get();
    }
    public void setIraupena(int pIraupena) {
        iraupena.set(pIraupena);
    }
    public String getHerrialdea() {
        return herrialdea.get();
    }
    public void setLastName(String pHerrialdea) {
        herrialdea.set(pHerrialdea);
    }
}