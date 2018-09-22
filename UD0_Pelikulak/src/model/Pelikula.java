/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

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
    private final SimpleStringProperty urtea;
    private final SimpleStringProperty iraupena;
    private final SimpleStringProperty herrialdea;
    
    /* ERAIKITZAILEA */
    public Pelikula(String pId, String pIzena, String pGaia, String pUrtea, String pIraupena, String pHerrialdea) { 
        this.id = new SimpleStringProperty(pId);
        this.izena = new SimpleStringProperty(pIzena);
        this.gaia = new SimpleStringProperty(pGaia);
        this.urtea = new SimpleStringProperty(pUrtea);
        this.iraupena = new SimpleStringProperty(pIraupena);
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
    public String getUrtea() {
        return urtea.get();
    }
    public void setUrtea(String pUrtea) {
        urtea.set(pUrtea);
    }
    public String getIraupena() {
        return iraupena.get();
    }
    public void setIraupena(String pIraupena) {
        iraupena.set(pIraupena);
    }
    public String getHerrialdea() {
        return herrialdea.get();
    }
    public void setLastName(String pHerrialdea) {
        herrialdea.set(pHerrialdea);
    }
}