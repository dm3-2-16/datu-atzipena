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
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.w3c.dom.Document;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import model.Pelikula;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author Oihane Axpe
 * @version V2.0
 */
public class Kontroladorea {
    /**
     * 
     * @return ComboBox-ean kargatu nahi den elementuen ArrayList bat bueltatzen du
     */
    public static ArrayList<String> comboBoxGaiaKargatu() {
        ArrayList<String> arrLGaia = new ArrayList<>();
        arrLGaia.add("Drama");
        arrLGaia.add("Komedia");
        arrLGaia.add("Zientzia fikzioa");
        arrLGaia.add("Akzioa");
        arrLGaia.add("Animazioa");
        arrLGaia.add("Dokumentala");
        return arrLGaia;
    }
    
    /**
     * Main-etik, metodo hau deituko da. Fitxategiaren extentsioaren arabera irakurriko dira datuak
     * @param fitxategia xml edo txt fitxategi bat hartzen du parametro bezala, bertatik irakurtzeko
     * @return Pelikula objektuen ObservableList-bat bueltatzen du
     */
    public static ObservableList<Pelikula> datuakKargatu(File fitxategia) {
        /* Fitxategiaren extentsioa zein den begiratu */
        String fitxExt = fitxategia.getName().substring(fitxategia.getName().length()-4).toLowerCase();
        
        /* Fitxategien extentsioa zein den konprobatu eta bakoitza irakurtzeko funtzioari deitu */
        if (fitxExt.equals(".txt")) {
            return txtDatuakKargatu(fitxategia);
        }
        else if (fitxExt.equals(".xml")) {
            return xmlDatuakKargatu(fitxategia);
        }
        return null;
    }     

    /**
     * ObservableList-ean kargatuta dauden pelikulak fitxategian gehitzeko metodoa
     * @param oList datuak ObservableListatik hartuko ditu
     * @param fitxategia zein fitxategitan gorde nahi diren datuak
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
    
    /**
     * txt fitxategia irakurtzen du
     * @param fitxategia txt fitxategi bat hartzen du parametro bezala, bertatik irakurtzeko
     * @return Pelikula objektuen ObservableList-bat bueltatzen du
     */
    public static ObservableList<Pelikula> txtDatuakKargatu(File fitxategia) {
        FileReader fr = null;
        BufferedReader br = null;
        ObservableList<Pelikula> peliObList = FXCollections.observableArrayList();
        String[] atributoak;
        
        try {
            fr = new FileReader(fitxategia);
            br = new BufferedReader(fr);
            String lerroa;
            /* Lerroz lerro irakurri, fitxategiaren bukaerararte heldu arte */
            while ((lerroa = br.readLine()) != null) {
                atributoak = lerroa.split(",");
                Pelikula peli = new Pelikula(atributoak[0].toUpperCase(), atributoak[1], atributoak[2], Integer.parseInt(atributoak[3]), Integer.parseInt(atributoak[4]), atributoak[5], atributoak[6]);
                peliObList.add(peli);
            }
            return peliObList;
        } catch (IOException ex) {
            System.out.println("Errorea egon da");
        }
        finally {
            try {
                fr.close();
                br.close();
            } catch (IOException ex) {
                Logger.getLogger(Kontroladorea.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;        
    }
    
    /**
     * xml fitxategi bat irakurtzen du
     * @param fitxategia xml fitxategi bat hartzen du parametro bezala, bertatik irakurtzeko
     * @return Pelikula objektuen ObservableList-bat bueltatzen du
     */
    public static ObservableList<Pelikula> xmlDatuakKargatu(File fitxategia) {
        try {
            String idPeli = null, izenaPeli = null, gaiaPeli = null, iraupenaPeli = null, urteaPeli = null, zuzendariaPeli = null, herrialdeaPeli = null;
            int iraupena = 0, urtea = 0;
            ObservableList<Pelikula> peliObList = FXCollections.observableArrayList();
            
            /* DOM zuhaitza sortu, xml fitxategi batetik abiatuta */
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document domDok = builder.parse(fitxategia); // fitxategitik DOM tree-ra pasatu
            
            /* DOM zuhaitza irakurri */
            NodeList peliNodoak = domDok.getElementsByTagName("peli"); // <peli> nodo guztiak hartzen ditu
            for (int i = 0; i<peliNodoak.getLength(); i++) {
                Node peliBakoitza = peliNodoak.item(i); // <peli> elementu bakoitza hartu
                Element peliElem = (Element)peliBakoitza;
                idPeli = peliElem.getAttribute("id"); // <peli> bakoitzaren id-a hartu
                NodeList peliNodoSemeak = peliBakoitza.getChildNodes(); // <peli> elementuaren elementu semeak
                for (int j = 0; j<peliNodoSemeak.getLength(); j++) {
                    Node semea = peliNodoSemeak.item(j);
                    if (semea.getNodeName() == "izena") {
                        izenaPeli = ((Element)semea.getChildNodes()).getTextContent();
                    }
                    else if (semea.getNodeName() == "gaia") {
                        gaiaPeli = ((Element)semea.getChildNodes()).getTextContent();
                    }
                    else if (semea.getNodeName() == "iraupena") {
                        iraupenaPeli = ((Element)semea.getChildNodes()).getTextContent();
                    }
                    else if (semea.getNodeName() == "urtea") {
                        urteaPeli = ((Element)semea.getChildNodes()).getTextContent();
                    }
                    else if (semea.getNodeName() == "herrialdea") {
                        herrialdeaPeli = ((Element)semea.getChildNodes()).getTextContent();
                    }
                    else if (semea.getNodeName() == "zuzendaria") {
                        zuzendariaPeli = ((Element)semea.getChildNodes()).getTextContent();
                    }
                }
                try {
                    iraupena = Integer.parseInt(iraupenaPeli);
                    urtea = Integer.parseInt(urteaPeli);
                }
                catch (NumberFormatException nfe) {
                    System.out.println("Errore bat egon da iraupena edo urtea jasotzerakoan");
                }
                Pelikula peli = new Pelikula(idPeli, izenaPeli, gaiaPeli, iraupena, urtea, herrialdeaPeli, zuzendariaPeli); // Pelikula objektua sortu, xml-ko datuekin
                peliObList.add(peli); // ObservableList-ean gehitu
            }   return peliObList;
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(Kontroladorea.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SAXException ex) {
            Logger.getLogger(Kontroladorea.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Kontroladorea.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
