/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Pelikula;

/**
 *
 * @author Oihane Axpe
 * @version V2.0
 */
public class Kontroladorea {
    /* Pelikulak gordetzeko fitxategia src\fitxategiak karpetan egongo dira */
    private static File dirFitx = new File("src\\fitxategiak");
    private static File fitxategia = new File(dirFitx+"\\pelikula.txt");
    
    public static ObservableList<Pelikula> datuakKargatu(){
        // Bistako taulan kargatzeko datuak
        return FXCollections.observableArrayList(
            // Pelikulen instantzia berriak sortu
            new Pelikula("P0001", "El mundo es suyo", "Komedia", 2018, 92, "Espainia", "Alfonso Sánchez Fernández"),
            new Pelikula("P0002", "Johnny English: De nuevo en acción", "Komedia", 2018, 88, "Erresuma Batua", "David Kerr"),
            new Pelikula("P0003", "La vida es bella", "Drama", 1997, 117, "Italia", "Roberto Benigni"),
            new Pelikula("P0004", "Matrix", "Zeintzia fikzioa", 1999, 131, "Estatu Batuak", "Lilly Wachowski, Lana Wachowski")
        );
    }        

    /**
     * ObservableList-ean kargatuta dauden pelikulak fitxategian gehitzeko metodoa
     * @param oList
     */  
    public static void fitxategianGorde(ObservableList<Pelikula> oList) {
        FileWriter fw = null;
        BufferedWriter bw = null;
        
        /* fitxategiak karpeta ez bada existitzen, sortu egingo du */
        if (!dirFitx.exists()) {
            dirFitx.mkdir();
        }
        try {
            fw = new FileWriter(fitxategia, false); // fitxategia matxakatzeko
            bw = new BufferedWriter(fw);
            /* Pelikularen datuak fitxategian idatzi */
            for (int i = 0; i<oList.size(); i++) {
                bw.write(oList.get(i).getId()+","+oList.get(i).getIzena()+","+oList.get(i).getGaia()+","+oList.get(i).getIraupena()+","+oList.get(i).getUrtea()+","+oList.get(i).getHerrialdea()+","+oList.get(i).getZuzendaria());
                bw.newLine();
            }

            bw.flush(); // Datuak fitxategian kargatzea behartu
        } catch (IOException e) {
            e.printStackTrace();
        } 
        finally {
            try {
                fw.close();
                bw.close();
            } catch (IOException ex) {
                Logger.getLogger(Kontroladorea.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
