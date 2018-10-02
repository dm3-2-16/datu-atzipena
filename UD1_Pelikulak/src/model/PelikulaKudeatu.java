/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Oihane Axpe
 * @version V2.0
 */
public class PelikulaKudeatu {
    private static File dirFitx = new File("src\\fitxategiak");
    private static File fitxategia = new File(dirFitx+"\\pelikula.txt");
    /**
     * Pelikulak fitxategian gehitzeko metodoa
     */
    public static void pelikulaGehitu(Pelikula peli) {
        FileWriter fw = null;
        BufferedWriter bw = null;
        
        /* fitxategiak karpeta ez bada existitzen, sortu egingo du */
        if (!dirFitx.exists()) {
            dirFitx.mkdir();
        }
        
        try {
            fw = new FileWriter(fitxategia, true); // fitxategia matxakatzeko
            bw = new BufferedWriter(fw);
            /* Pelikularen datuak fitxategian idatzi */
            bw.write(peli.getId()+","+peli.getIzena()+","+peli.getGaia()+","+peli.getUrtea()+","+peli.getIraupena()+","+peli.getHerrialdea()+","+peli.getZuzendaria());
            bw.newLine();

            bw.flush(); // Datuak fitxategian kargatzea behartu
        } catch (IOException e) {
            e.printStackTrace();
        } 
        finally {
            try {
                fw.close();
                bw.close();
            } catch (IOException ex) {
                Logger.getLogger(PelikulaKudeatu.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
