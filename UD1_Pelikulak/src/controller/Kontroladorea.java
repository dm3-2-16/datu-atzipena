/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonWriter;
import org.w3c.dom.Document;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
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
        /* Fitxategiaren extentsioa zein den begiratu */
        String fitxExt = fitxategia.getName().substring(fitxategia.getName().length()-4).toLowerCase();
        
        /* fitxategiak karpeta ez bada existitzen, sortu egingo du */
        if (!fitxategia.exists()) {
            fitxategia.mkdir();
        }
        /* ObservableList-a hutsik baldin badago, leihoa ixterakoan ez du gordeko */
        if (oList != null) {
            /* Fitxategien extentsioa zein den konprobatu eta bakoitza idazteko funtzioari deitu */
            if (fitxExt.equals(".txt")) {
                txtFitxategianGorde(oList, fitxategia);
            }
            else if (fitxExt.equals(".xml")) {
                xmlFitxategianGorde(oList, fitxategia);
            }
            else if (fitxExt.equals("json")) {
                jsonFitxategianGorde(oList, fitxategia);
            }
        }
        else 
            System.out.println(fitxategia.getName() + " fitxategia hutsik sortu da.");
            
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
        
    /**
     * ObservableList-ean kargatuta dauden pelikulak, txt fitxategian gehitzeko metodoa
     * @param oList datuak ObservableListatik hartuko ditu
     * @param fitxategia zein txt fitxategitan gorde nahi diren datuak
     */
    public static void txtFitxategianGorde(ObservableList<Pelikula> oList, File fitxategia) {
        FileWriter fw = null;
        BufferedWriter bw = null;
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
     * ObservableList-ean kargatuta dauden pelikulak, xml fitxategian gehitzeko metodoa
     * @param oList datuak ObservableListatik hartuko ditu
     * @param fitxategia zein xml fitxategitan gorde nahi diren datuak
     */
    public static void xmlFitxategianGorde(ObservableList<Pelikula> oList, File fitxategia) {
        
        try {
            //Zuhaitza Sortu fitxategitik abiatuta
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            //Document dok = builder.parse(fitxategia);
            
            // DOM zuhaitza sortu
            Document dokBerria = builder.newDocument();
            
            Element elemAita = dokBerria.createElement("pelikula");
            dokBerria.appendChild(elemAita); // elemento RAIZ gehitu <pelikula>
            dokBerria.setTextContent("\n"); // salto de linea gehitu fitxategian

            for (int i = 0; i<oList.size(); i++) {
                Element elemPeli = dokBerria.createElement("peli");
                
                /* <pelikula> elementuari, <peli> elementu bakoitza gehitu */
                elemAita.appendChild(elemPeli);
                elemPeli.setAttribute("id", oList.get(i).getId());
                
                /* <peli> elementuaren semeak eta bakoitzaren textua sortu */
                Element elemIzena = dokBerria.createElement("izena");
                elemIzena.appendChild(dokBerria.createTextNode(oList.get(i).getIzena()));
                
                Element elemGaia = dokBerria.createElement("gaia");
                elemGaia.appendChild(dokBerria.createTextNode(oList.get(i).getGaia()));
                
                Element elemIraupena = dokBerria.createElement("iraupena");
                elemIraupena.appendChild(dokBerria.createTextNode(String.valueOf(oList.get(i).getIraupena())));
                
                Element elemUrtea = dokBerria.createElement("urtea");
                elemUrtea.appendChild(dokBerria.createTextNode(String.valueOf(oList.get(i).getUrtea())));
                
                Element elemHerrialdea = dokBerria.createElement("herrialdea");
                elemHerrialdea.appendChild(dokBerria.createTextNode(oList.get(i).getHerrialdea()));
                
                Element elemZuzendaria = dokBerria.createElement("zuzendaria");
                elemZuzendaria.appendChild(dokBerria.createTextNode(oList.get(i).getZuzendaria()));
                
                /* <peli> bakoitzaren elementuak (izena, gaia...) gehitu */
                elemPeli.appendChild(elemIzena);
                elemPeli.appendChild(elemGaia);
                elemPeli.appendChild(elemIraupena);
                elemPeli.appendChild(elemUrtea);
                elemPeli.appendChild(elemHerrialdea);
                elemPeli.appendChild(elemZuzendaria);
                
                /* Fitxategian idatzi */
                TransformerFactory transfFactory = TransformerFactory.newInstance();
                Transformer transf = transfFactory.newTransformer();
                DOMSource source = new DOMSource(dokBerria);
                StreamResult rs = new StreamResult(fitxategia);
                transf.transform(source, rs);
            }  
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(Kontroladorea.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TransformerConfigurationException ex) {
            Logger.getLogger(Kontroladorea.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TransformerException ex) {
            Logger.getLogger(Kontroladorea.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * ObservableList-ean kargatuta dauden pelikulak, json fitxategian gehitzeko metodoa
     * @param oList datuak ObservableListatik hartuko ditu
     * @param fitxategia zein json fitxategitan gorde nahi diren datuak
     */
    public static void jsonFitxategianGorde(ObservableList<Pelikula> oList, File fitxategia) {
        JsonWriter jsonWriter = null;
        
        try { 
            JsonArrayBuilder arrayB = Json.createArrayBuilder(); // JsonArray-a sortu
            JsonObjectBuilder objectB = Json.createObjectBuilder(); // Json Objektua sortu
            
            for (Pelikula peli : oList) {
                /* Objektuak, elementu bakoitza gehitu */
                objectB.add("id", peli.getId()); 
                objectB.add("izena", peli.getIzena());
                objectB.add("gaia", peli.getGaia());
                objectB.add("iraupena", String.valueOf(peli.getIraupena()));
                objectB.add("urtea", String.valueOf(peli.getUrtea()));
                objectB.add("herrialdea", peli.getHerrialdea());
                objectB.add("zuzendaria", peli.getZuzendaria());
                
                JsonObject jsonObjPeli = objectB.build(); 
                arrayB.add(jsonObjPeli); // Objektuak array-era gehitu
            }
            JsonArray jsonArrayPeli = arrayB.build();
            jsonWriter = Json.createWriter(new FileOutputStream(fitxategia, false)); //JsonWriter-ekin, jsonArray-a fitxategian idazteko
            jsonWriter.write(jsonArrayPeli); // datuak fitxategian idatzi
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Kontroladorea.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
}
