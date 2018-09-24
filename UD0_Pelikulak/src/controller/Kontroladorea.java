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
            new Pelikula("P0001", "El mundo es suyo", "Komedia", 2018, 92, "Espainia"),
            new Pelikula("P0002", "Johnny English: De nuevo en acción", "Komedia", 2018, 88, "Erresuma Batua"),
            new Pelikula("P0003", "La vida es bella", "Drama", 1997, 117, "Italia"),
            new Pelikula("P0004", "Matrix", "Zeintzia fikzioa", 1999, 131, "Estatu Batuak")
        );
    }         
}
