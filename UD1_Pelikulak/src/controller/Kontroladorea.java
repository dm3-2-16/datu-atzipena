/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
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
    
    public static ObservableList<Pelikula> datuakKargatu(File fitxategia) {
        //BufferedReader br = null;
        ObservableList<Pelikula> peliObList = FXCollections.observableArrayList(); ;
        String[] atributoak;
        try {
            FileReader fr = new FileReader(fitxategia);
            BufferedReader br = new BufferedReader(fr);
            String lerroa;
            /* Lerroz lerro irakurri, fitxategiaren bukaerararte heldu arte */
            while ((lerroa = br.readLine()) != null) {
                atributoak = lerroa.split(",");
                Pelikula peli = new Pelikula(atributoak[0].toUpperCase(), atributoak[1], atributoak[2], Integer.parseInt(atributoak[3]), Integer.parseInt(atributoak[4]), atributoak[5], atributoak[6]);
                peliObList.add(peli);
            }
            return peliObList;
        } catch (IOException ex) {
            //Logger.getLogger(Kontroladorea.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("errorea egon da");
        }
        return null;
    }        

    /**
     * ObservableList-ean kargatuta dauden pelikulak fitxategian gehitzeko metodoa
     * @param oList
     */  
    public static void fitxategianGorde(ObservableList<Pelikula> oList, File fitxategia) {
        FileWriter fw = null;
        BufferedWriter bw = null;

        /* fitxategiak karpeta ez bada existitzen, sortu egingo du */
        if (!fitxategia.exists()) {
            fitxategia.mkdir();
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
