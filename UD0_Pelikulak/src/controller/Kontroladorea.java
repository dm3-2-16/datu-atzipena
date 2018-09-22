/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Pelikula;

/**
 *
 * @author Oihane Axpe
 * @version V1.0
 */
public class Kontroladorea {
    
    public static ObservableList<Pelikula> datuakKargatu(){
        // Bistako taulan kargatzeko datuak
        return FXCollections.observableArrayList(
            // Pelikulen instantzia berriak sortu
            new Pelikula("01", "El mundo es suyo", "Komedia", "2018", "92min", "Espainia"),
            new Pelikula("02", "Johnny English: De nuevo en acción", "Komedia", "2018", "88min", "Erresuma Batua"),
            new Pelikula("03", "La vida es bella", "Drama", "1997", "117min", "Italia"),
            new Pelikula("04", "Matrix", "Zeintzia fikzioa", "1999", "131min", "Estatu Batuak")
        );
    }         
}
