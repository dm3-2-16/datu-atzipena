/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ud1_pelikulak;

import controller.Kontroladorea;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import javafx.util.converter.IntegerStringConverter;
import model.Pelikula;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;



/**
 *
 * @author Oihane Axpe
 * @version V2.0
 */
public class UD1_Pelikulak extends Application { // Application klasetik heredatzeko
    /* ATRIBUTOAK */
    private String btnStyle = "-fx-pref-width: 150px; -fx-background-color:lightcoral; -fx-font: 20px \"Serif\"; -fx-text-fill: white; -fx-alignment: CENTER;"; // BOTOIEN ESTILOA DEFINITU
    private String btnStyleTxiki = "-fx-background-color:darkred; -fx-font: 15px \"Serif\"; -fx-text-fill: white; -fx-alignment: CENTER; -fx-font-weight: bold;"; // BOTOIEN (txikien) ESTILOA DEFINITU
    private File fitxategia; // fitxategia aldatzen joango da, baina beti taulan dauden datuak kargatuta dagoen fitxategia gordeko da
    private ObservableList<Pelikula> obListDatuak;
    
    @Override
    public void start(Stage lehenStage) throws FileNotFoundException, MalformedURLException { // stage --> Bista/Window
        VBox vbox = new VBox(); // vertical box
        HBox hbox1 = new HBox(); // horizontal box
        HBox hbox2 = new HBox(); // horizontal box
        Button btnIrten = new Button();
        
        Scene scenePrincipal = new Scene(new Group(), 350, 400); // zabalera eta altuera parametro bezala pasatzen dira
        lehenStage.setTitle("PELIKULAK");
        lehenStage.setResizable(false); // tamaina ez aldatu

        /* LABEL bat gehitu - Taularen titulua */
        final Label labelTituloa = new Label("PELIKULAK KUDEATU");
        tituluEstiloa(labelTituloa);
        
        final Label labelGaldera = new Label("Zer egin nahi duzu?");
        labelGaldera.setFont(Font.font("Calibri", FontWeight.SEMI_BOLD, 20));
        final Label fitxAukeratu = new Label("Fitxategia aukeratu");
        labelEstiloa(fitxAukeratu);
        final Button btnAukeratu = new Button("...");
        btnAukeratu.setStyle(btnStyleTxiki);
        final Label fitxBerriaSortu = new Label("Fitxategi berria sortu");
        labelEstiloa(fitxBerriaSortu);
        final Button btnBerria = new Button("..."); 
        btnBerria.setStyle(btnStyleTxiki);
        
        ImageView irudiaPeli = new ImageView(new Image("\\Irudiak\\pelikula.png", 200, 150, true, true));

        btnAukeratu.setOnAction((ActionEvent e) -> {
            try {
                Stage bigarrenStage = new Stage();
                this.fitxategia = fileChooserErabili(lehenStage, true);
                Scene scenePeliDatuak = scenePeliDatuak(bigarrenStage); // pelikulen datuak dauden stage-a kargatzen du
                bigarrenStage.setScene(scenePeliDatuak);
                bigarrenStage.show();
            }
            catch (NullPointerException ex)  {
                System.err.println("EZ DUZU FITXATEGIA AUKERATU.\n");
            }
            catch (ArrayIndexOutOfBoundsException aioobe) {
                System.err.println("Aukeratutako fitxategiak ez dauka formatu egokia.\n");
                Alert dialogoAlerta = new Alert(Alert.AlertType.ERROR); // Ikonoa
                dialogoAlerta.setTitle("KONTUZ!"); // Titulua
                dialogoAlerta.setHeaderText(null);
                dialogoAlerta.setContentText("Aukeratutako fitxategiak ez dauka formatu egokia."); // Mezua
                dialogoAlerta.initStyle(StageStyle.UTILITY);
                dialogoAlerta.showAndWait();
            }
        });
        
        btnBerria.setOnAction((ActionEvent e) -> {
            try {
                Stage bigarrenStage = new Stage();
                this.fitxategia = fileChooserErabili(lehenStage, false); 
                Scene scenePeliDatuak = scenePeliDatuak(bigarrenStage); // pelikulen datuak dauden stage-a kargatzen du
                bigarrenStage.setScene(scenePeliDatuak);
                bigarrenStage.show();
            }
            catch (NullPointerException ex)  {
                System.err.println("EZ DUZU FITXATEGIA SORTU.\n");
            }
        });
        
        btnIrten.setStyle(btnStyle);
        btnIrten.setText("Irten");
        btnIrten.setOnAction((ActionEvent e) -> {
            /* Aplikazioa itxi */
            System.exit(0);
        });
        
        hbox1.getChildren().addAll(fitxAukeratu, btnAukeratu);
        hbox1.setSpacing(20); // hbox1-eko elementuen arteko espazioa gehitu
        
        hbox2.getChildren().addAll(fitxBerriaSortu, btnBerria);
        hbox2.setSpacing(10); // hbox2-eko elementuen arteko espazioa gehitu
        
        /* Espazioak gehitu horizontal box-etan (goian eta ezkerrean) */
        hbox1.setPadding(new Insets(20, 0, 0, 40));
        hbox2.setPadding(new Insets(10, 0, 0, 40));
        vbox.getChildren().addAll(labelTituloa, labelGaldera, hbox1, hbox2, irudiaPeli, btnIrten);
        vbox.setPadding(new Insets(20, 0, 0, 20));
        vbox.setSpacing(10); // vbox-eko elementuen arteko tartea
        ((Group) scenePrincipal.getRoot()).getChildren().addAll(vbox);
        
        /* BISTA erakusteko */
        lehenStage.setScene(scenePrincipal);
        
        //lehenStage.initStyle(StageStyle.UNIFIED);
        lehenStage.show();
    }
    
    /**
     * BIGARREN STAGE-a kargatzeko metodoa. Fitxategia parametro bezala pasatu behar da, datuak bertatik kargatzeko
     * @param stage
     * @param fitxategia
     * @return 
     */   
    private Scene scenePeliDatuak(Stage stage) {
        TableView<Pelikula> taula = new TableView<>(); // taula sortzeko instantzia
        HBox hbox1 = new HBox(); // horizontal box
        HBox hBoxLabelFitxAuk = new HBox();
        HBox hboxBotoiak = new HBox(); // horizontal box
        VBox vbox = new VBox(); // vertical box

        String labelTxtAukFitx = "Aukeratutako fitxategia: ";
        final Label labelFitxAukIzena = new Label(); // Momentuan aukeratuta dagoen fitxategiaren izena erakusten du
        labelFitxAukEstiloa(labelFitxAukIzena);
        final Label labelFitxIreki = new Label("Ireki: ");
        labelFitxAukEstiloa(labelFitxIreki);
        final Button btnIrekiFitx = new Button();
        btnIrekiFitx.setText("...");
        btnIrekiFitx.setStyle(btnStyleTxiki);
        
        Scene scenePeliDatuak = new Scene(new Group(), 1000, 800);
        
        /* Datuak ObservableList<Pelikula>-tik kargatu */
        this.obListDatuak = Kontroladorea.datuakKargatu(this.fitxategia);
        labelFitxAukIzena.setText(labelTxtAukFitx+this.fitxategia.getName()); // aukeratutako fitxategiaren izena aldatu
        
        stage.setTitle("PELIKULAK"); // Bistari titulua gehitu
        
        /* LABEL bat gehitu - Taularen titulua */
        final Label labelTaula = new Label("Pelikulen datuak:");
        tituluEstiloa(labelTaula);

        /* TAULAREN propietateak aldatu */
        taula.setEditable(true);
        taula.setStyle("-fx-background-color:lightcoral"); 
        
        /* Pelikulen ID-a definitu */
        TableColumn<Pelikula, String> idZut = new TableColumn<>("Id-a"); // zutabearen titulua
        idZut.setMinWidth(75); // zabalera minimoa
        idZut.setCellValueFactory(new PropertyValueFactory<>("id"));
        idZut.setCellFactory(TextFieldTableCell.<Pelikula>forTableColumn());
        idZut.setOnEditCommit(
            (TableColumn.CellEditEvent<Pelikula, String> t) -> {
            ((Pelikula) t.getTableView().getItems().get(
            t.getTablePosition().getRow())
            ).setId(t.getNewValue());
            });
        
        /* Pelikulen IZENA definitu */
        TableColumn<Pelikula, String> izenZut = new TableColumn<>("Izena"); // zutabearen titulua
        izenZut.setMinWidth(250); // zabalera minimoa
        izenZut.setCellValueFactory(new PropertyValueFactory<>("izena"));
        izenZut.setCellFactory(TextFieldTableCell.<Pelikula>forTableColumn());
        izenZut.setOnEditCommit(
            (TableColumn.CellEditEvent<Pelikula, String> t) -> {
            ((Pelikula) t.getTableView().getItems().get(
            t.getTablePosition().getRow())
            ).setIzena(t.getNewValue());
            });
        
        /* Pelikulen GAIA definitu */
        TableColumn<Pelikula, String> gaiaZut = new TableColumn<>("Gaia"); // zutabearen titulua
        gaiaZut.setMinWidth(100); // zabalera minimoa
        gaiaZut.setCellValueFactory(new PropertyValueFactory<>("gaia"));
        gaiaZut.setCellFactory(TextFieldTableCell.<Pelikula>forTableColumn());
        gaiaZut.setOnEditCommit(
            (TableColumn.CellEditEvent<Pelikula, String> t) -> {
            ((Pelikula) t.getTableView().getItems().get(
            t.getTablePosition().getRow())
            ).setGaia(t.getNewValue());
            });
        
        /* Pelikulen IRAUPENA definitu */
        TableColumn<Pelikula, Integer> iraupenZut = new TableColumn<>("Iraupena");
        iraupenZut.setMinWidth(75); // zabalera minimoa
        iraupenZut.setCellValueFactory(new PropertyValueFactory<Pelikula, Integer>("iraupena"));
        iraupenZut.setCellFactory(TextFieldTableCell.<Pelikula, Integer>forTableColumn(new IntegerStringConverter())); // Integerrera bihurtu
        iraupenZut.setOnEditCommit(
            (TableColumn.CellEditEvent<Pelikula, Integer> t) -> {
            ((Pelikula) t.getTableView().getItems().get(
            t.getTablePosition().getRow())
            ).setIraupena(Integer.parseInt(t.getNewValue().toString()));
            });
        
        /* Pelikulen URTEA definitu */
        TableColumn<Pelikula, Integer> urteZut = new TableColumn<>("Urtea"); // zutabearen titulua
        urteZut.setMinWidth(75); // zabalera minimoa
        urteZut.setCellValueFactory(new PropertyValueFactory<Pelikula, Integer>("urtea"));
        urteZut.setCellFactory(TextFieldTableCell.<Pelikula, Integer>forTableColumn(new IntegerStringConverter())); // Integerrera bihurtu
        urteZut.setOnEditCommit(
            (TableColumn.CellEditEvent<Pelikula, Integer> t) -> {
            ((Pelikula) t.getTableView().getItems().get(
            t.getTablePosition().getRow())
            ).setUrtea(Integer.parseInt(t.getNewValue().toString()));
            });
             
        /* Pelikulen HERRIALDEA definitu */
        TableColumn<Pelikula, String> herrialdeZut = new TableColumn<>("Herrialdea");
        herrialdeZut.setMinWidth(100); // zabalera minimoa
        herrialdeZut.setCellValueFactory(new PropertyValueFactory<>("herrialdea"));
        herrialdeZut.setCellFactory(TextFieldTableCell.<Pelikula>forTableColumn());
        herrialdeZut.setOnEditCommit(
            (TableColumn.CellEditEvent<Pelikula, String> t) -> {
            ((Pelikula) t.getTableView().getItems().get(
            t.getTablePosition().getRow())
            ).setHerrialdea(t.getNewValue());
            });
        
        /* Pelikulen ZUZENDARIA definitu */
        TableColumn<Pelikula, String> zuzendariaZut = new TableColumn<>("Zuzendaria");
        zuzendariaZut.setMinWidth(200); // zabalera minimoa
        zuzendariaZut.setCellValueFactory(new PropertyValueFactory<>("zuzendaria"));
        zuzendariaZut.setCellFactory(TextFieldTableCell.<Pelikula>forTableColumn());
        zuzendariaZut.setOnEditCommit(
            (TableColumn.CellEditEvent<Pelikula, String> t) -> {
            ((Pelikula) t.getTableView().getItems().get(
            t.getTablePosition().getRow())
            ).setZuzendaria(t.getNewValue());
            });
        
        /* Datuak kargatu */
        taula.setItems(this.obListDatuak); // taulara, observableList-eko datuak gehitu

        /* Zutabeak taulan gehitu */
        taula.getColumns().addAll(idZut, izenZut, gaiaZut, iraupenZut, urteZut, herrialdeZut, zuzendariaZut);
        
        /* BEHEKO ZATIA - DATUAK GEHITU, EZABATU... */
        // Label-ak definitu
        final Label labelId = new Label();
        labelId.setText("Id-a:");
        labelEstiloa(labelId);
        labelId.setMaxWidth(idZut.getPrefWidth());
        
        final Label labelIzena = new Label();
        labelIzena.setText("Izena:");
        labelEstiloa(labelIzena);
        
        final Label labelGaia = new Label();
        labelGaia.setText("Gaia:");
        labelEstiloa(labelGaia);
        
        final Label labelIraupena = new Label();
        labelIraupena.setText("Iraupena:");
        labelEstiloa(labelIraupena);
        
        final Label labelUrtea = new Label();
        labelUrtea.setText("Urtea:");
        labelEstiloa(labelUrtea);
        
        final Label labelHerrialdea = new Label();
        labelHerrialdea.setText("Herrialdea:");
        labelEstiloa(labelHerrialdea);
        
        final Label labelZuzendaria = new Label();
        labelZuzendaria.setText("Zuzendaria:");
        labelEstiloa(labelZuzendaria);
        
        
        // TextField-ak definitu
        final TextField gehituId = new TextField();
        gehituId.setPromptText("Id");
        gehituId.setMaxWidth(idZut.getPrefWidth());
        
        final TextField gehituIzena = new TextField();
        gehituIzena.setPromptText("Izena");
        gehituIzena.setMaxWidth(izenZut.getPrefWidth()*2);

        final ComboBox gehituGaia = new ComboBox(FXCollections.observableList(Kontroladorea.comboBoxGaiaKargatu()));
        gehituGaia.setMaxWidth(gaiaZut.getPrefWidth()*2);
        gehituGaia.getSelectionModel().select(0);

        final TextField gehituIraupena = new TextField();
        gehituIraupena.setPromptText("Iraupena");
        gehituIraupena.setMaxWidth(iraupenZut.getPrefWidth()*2);
        
        final TextField gehituUrtea = new TextField();
        gehituUrtea.setPromptText("Urtea");
        gehituUrtea.setMaxWidth(urteZut.getPrefWidth());
        
        final TextField gehituHerrialdea = new TextField();
        gehituHerrialdea.setPromptText("Herrialdea");
        gehituHerrialdea.setMaxWidth(herrialdeZut.getPrefWidth()*2);
        
        final TextField gehituZuzendaria = new TextField();
        gehituZuzendaria.setPromptText("Zuzendaria");
        gehituZuzendaria.setMaxWidth(zuzendariaZut.getPrefWidth()*2);
        
        // GEHITU botoia definitu
        final Button btnGehitu = new Button("Gehitu"); 
        btnGehitu.setStyle(btnStyle);
        btnGehitu.setOnAction((ActionEvent e) -> {    
            int urtea=2018, iraupena = 100;
            boolean urteaOndo = true, iraupenaOndo = true, gordeta = false; // aldagai boleanoak
            
            /* TextBox-ak beteta daudela konprobatzen du */
            if (gehituId.getText().isEmpty() || gehituIzena.getText().isEmpty() || gehituGaia.getSelectionModel().getSelectedIndex()==0
                    || gehituIraupena.getText().isEmpty() || gehituUrtea.getText().isEmpty() 
                    || gehituHerrialdea.getText().isEmpty() || gehituZuzendaria.getText().isEmpty()) {
                Alert dialogoAlerta = new Alert(Alert.AlertType.ERROR); // Ikonoa
                dialogoAlerta.setTitle("KONTUZ!"); // Titulua
                dialogoAlerta.setHeaderText(null);
                dialogoAlerta.setContentText("Daturenbat betetzea ahaztu zaizu.\nBete itzazu."); // Mezua
                dialogoAlerta.initStyle(StageStyle.UTILITY);
                dialogoAlerta.showAndWait();
                gordeta=false;
            }
            else {
                /* TextBox guztiak beteta baldin badaude, urtea eta iraupena zenbakiak direla konprobatzen du */
                try {
                    urtea = Integer.parseInt(gehituUrtea.getText()); // urtea jaso
                }
                catch (NumberFormatException nfeUrtea) { // zenbakia ez bada, errore mezua erakutsiko du
                    Alert dialogoAlerta = new Alert(Alert.AlertType.ERROR); // Ikonoa
                    dialogoAlerta.setTitle("KONTUZ!"); // Titulua
                    dialogoAlerta.setHeaderText(null);
                    dialogoAlerta.setContentText("Urtea gaizki sartu duzu!"); // Mezua
                    dialogoAlerta.initStyle(StageStyle.UTILITY);
                    dialogoAlerta.showAndWait();
                    urteaOndo = false;
                }
                try {
                    iraupena = Integer.parseInt(gehituIraupena.getText()); // iraupena jaso
                }
                catch (NumberFormatException nfeIraupena) { // zenbakia ez bada, errore mezua erakutsiko du
                    Alert dialogoAlerta = new Alert(Alert.AlertType.ERROR); // Ikonoa
                    dialogoAlerta.setTitle("KONTUZ!"); // Titulua
                    dialogoAlerta.setHeaderText(null);
                    dialogoAlerta.setContentText("Iraupena gaizki sartu duzu!\nZenbaki oso bat izan behar da"); // Mezua
                    dialogoAlerta.initStyle(StageStyle.UTILITY);
                    dialogoAlerta.showAndWait();
                    iraupenaOndo = false;
                }
                if (urteaOndo && iraupenaOndo) { // urtea eta iraupena ondo jaso badira (formatu egokian)
                    Pelikula peli = new Pelikula(
                        gehituId.getText().toUpperCase(),
                        gehituIzena.getText(),
                        gehituGaia.getSelectionModel().getSelectedItem().toString(), // Aukeratutako item-a hartu
                        iraupena,
                        urtea,
                        gehituHerrialdea.getText(),
                        gehituZuzendaria.getText()
                    );
                    this.obListDatuak.add(peli); // pelikula berria observableList-ean (taulan) gehitu
                    Kontroladorea.fitxategianGorde(this.obListDatuak, this.fitxategia); // ObservableList-ean dauden pelikula guztiak fitxategian idatzi
                    gordeta = true; // gorde egingo da
                }
                if(!urteaOndo) {
                    gehituUrtea.clear(); // urtea gaizki sartu badu, textua hustu
                }
                if (!iraupenaOndo) {
                    gehituIraupena.clear(); // iraupena gaizki sartu badu, textua hustu
                }
            }
            
            if (gordeta) {
                /* Pelikula berria gorde bada, informazio mezu bat erakutsi */
                Alert dialogoAlerta = new Alert(Alert.AlertType.INFORMATION); // Ikonoa
                dialogoAlerta.setTitle("ERREGISTRATUTA!"); // Titulua
                dialogoAlerta.setHeaderText(null);
                dialogoAlerta.setContentText("Pelikula berri bat gorde da."); // Mezua
                dialogoAlerta.initStyle(StageStyle.UTILITY);
                dialogoAlerta.showAndWait();
                
                // TextBox-ean dagoena garbitu
                gehituId.clear();
                gehituIzena.clear();
                gehituGaia.getSelectionModel().select(0);
                gehituUrtea.clear();
                gehituIraupena.clear();
                gehituHerrialdea.clear();
                gehituZuzendaria.clear();
            }    
        });
        
        // EZABATU botoia definitu
        final Button btnEzabatu = new Button("Ezabatu"); 
        btnEzabatu.setStyle(btnStyle);
        btnEzabatu.setOnAction((ActionEvent e) -> {
            Object aukeratuta = taula.getSelectionModel().getSelectedItem();
            /* Ezabatu aurretik, konprobatu pelikularik aukeratuta dagoen edo ez */
            if (aukeratuta!=null) {
                Pelikula pelikula1 = (Pelikula)aukeratuta;    
                this.obListDatuak.remove(pelikula1);
                Kontroladorea.fitxategianGorde(this.obListDatuak, this.fitxategia); // ObservableList-ean dauden pelikula guztiak fitxategian idatzi
                /* Pelikula ezabatu bada, informazio mezu bat erakutsi */
                Alert dialogoAlerta = new Alert(Alert.AlertType.INFORMATION); // Ikonoa
                dialogoAlerta.setTitle("EZABATUTA!"); // Titulua
                dialogoAlerta.setHeaderText(null);
                dialogoAlerta.setContentText("Aukeratutako pelikula ezabatu da."); // Mezua
                dialogoAlerta.initStyle(StageStyle.UTILITY);
                dialogoAlerta.showAndWait();
            }
            else {
                Alert dialogoAlerta = new Alert(AlertType.WARNING); // Ikonoa
                dialogoAlerta.setTitle("KONTUZ!"); // Titulua
                dialogoAlerta.setHeaderText(null);
                dialogoAlerta.setContentText("Ez duzu pelikularik aukeratu!"); // Mezua
                dialogoAlerta.initStyle(StageStyle.UTILITY);
                dialogoAlerta.showAndWait();
            } 
        });
        
        // IRTEN botoia definitu
        final Button btnIrten = new Button("Irten"); 
        btnIrten.setStyle(btnStyle);
        btnIrten.setOnAction((ActionEvent e) -> {
            /* Leihoa ixterakoan irten botoiarekin, datuak fitxategian gorde */
            Kontroladorea.fitxategianGorde(this.obListDatuak, this.fitxategia);
            System.exit(0);
        });
        
        // GORDE HONELA botoia definitu
        final Button btnGordeHonela = new Button("Gorde honela...");
        btnGordeHonela.setStyle(btnStyleTxiki);
        btnGordeHonela.setOnAction((ActionEvent e) -> {
            try {
                File f = fileChooserErabili(stage, false);
                Kontroladorea.fitxategianGorde(this.obListDatuak, f);
                Kontroladorea.datuakKargatu(f);
                this.fitxategia = f;
                labelFitxAukIzena.setText(labelTxtAukFitx+this.fitxategia.getName()); // aukeratutako fitxategiaren izena aldatu
            }
            catch (NullPointerException ex)  {
                System.err.println("EZ DUZU FITXATEGIA SORTU.\n");
            } 
        });
        
        /* Fitxategi berria irekitzeko botoia */
        btnIrekiFitx.setOnAction((ActionEvent e) -> {
            try {
                this.fitxategia = fileChooserErabili(stage, true);
                this.obListDatuak = Kontroladorea.datuakKargatu(this.fitxategia); // datuak observableList-ean erreferentziaz pasatu
                taula.setItems(this.obListDatuak);  // taulara, observableList-eko datuak gehitu
                labelFitxAukIzena.setText(labelTxtAukFitx+this.fitxategia.getName()); // aukeratutako fitxategiaren izena aldatu
            }
            catch (NullPointerException ex)  {
                System.err.println("EZ DUZU FITXATEGIA SORTU.\n");
            } 
        });
        
        /* LABEL bat gehitu - Pelikularen datuak gehitzeko titulua */
        final Label labelGehitu = new Label("Pelikulak gehitu:");
        tituluEstiloa(labelGehitu);

        // textBox eta label-ak alineatzeko, vbox-ak sortu
        VBox vBoxLabel1 = new VBox(); // vertical box
        vBoxLabel1.getChildren().addAll(labelId, labelUrtea );
        vBoxLabel1.setSpacing(20);
        
        VBox vBoxTxtBox1 = new VBox();
        vBoxTxtBox1.getChildren().addAll(gehituId, gehituUrtea);
        vBoxTxtBox1.setSpacing(10);
        
        VBox vBoxLabel2 = new VBox();
        vBoxLabel2.getChildren().addAll(labelIzena, labelHerrialdea);
        vBoxLabel2.setSpacing(20);
        
        VBox vBoxTxtBox2 = new VBox();
        vBoxTxtBox2.getChildren().addAll(gehituIzena, gehituHerrialdea);
        vBoxTxtBox2.setSpacing(10);
        
        VBox vBoxLabel3 = new VBox();
        vBoxLabel3.getChildren().addAll(labelGaia, labelZuzendaria);
        vBoxLabel3.setSpacing(20);
        
        VBox vBoxTxtBox3 = new VBox();
        vBoxTxtBox3.getChildren().addAll(gehituGaia, gehituZuzendaria);
        vBoxTxtBox3.setSpacing(10);
        
        VBox vBoxLabel4 = new VBox();
        vBoxLabel4.getChildren().addAll(labelIraupena);
        vBoxLabel4.setSpacing(20);
        
        VBox vBoxTxtBox4 = new VBox();
        vBoxTxtBox4.getChildren().addAll(gehituIraupena);
        vBoxTxtBox4.setSpacing(10);

        /* Ireki label-a eta fitxategi berria irekitzeko botoia dituen horizontalBox-a */
        HBox hBoxIrekiFitx = new HBox();
        hBoxIrekiFitx.getChildren().addAll(labelFitxIreki, btnIrekiFitx);
        hBoxIrekiFitx.setSpacing(10);
        
        /* Aukeratuta dagoen fitxategia eta fitxategi berria kargatzeko aukerak ematen dituen horizontalBox-a */
        hBoxLabelFitxAuk.getChildren().addAll(labelFitxAukIzena, hBoxIrekiFitx);
        hBoxLabelFitxAuk.setSpacing(100);
        
        /* ZATIAK banatzeko lerro bat sartu */
        Line lerroa = new Line(10, 10, 900, 10);
        lerroa.setStroke(Color.DARKRED); // lerroaren kolorea aldatu
        lerroa.setStrokeWidth(3); // lerroaren lodiera aldatu
        
        /* Pelikulak erregistratzeko label eta textBox-ak dituen horizontalBox-a */
        hbox1.setPadding(new Insets(10, 0, 0, 10));
        hbox1.getChildren().addAll(vBoxLabel1, vBoxTxtBox1, vBoxLabel2, vBoxTxtBox2, vBoxLabel3, vBoxTxtBox3, vBoxLabel4, vBoxTxtBox4);
        hbox1.setSpacing(10); //textField-en arteko espazioa
        
        /* GEHITU, EZABATU eta IRTEN botoiak dituen horizontalBox-a */
        hboxBotoiak.getChildren().addAll(btnGehitu, btnEzabatu, btnIrten);
        hboxBotoiak.setSpacing(50);
        hboxBotoiak.setPadding(new Insets(20, 0, 0, 20));
        
        /* -------------------------------------------------------------- */
        
        /* Elementu guztiak, verticalBox-ean sartu */
        vbox.setSpacing(10); // label eta taularen arteko tartea
        vbox.setPadding(new Insets(20, 0, 0, 20));
        vbox.getChildren().addAll(labelTaula, hBoxLabelFitxAuk, taula, btnGordeHonela, lerroa, labelGehitu, hbox1,  hboxBotoiak);
        
        ((Group) scenePeliDatuak.getRoot()).getChildren().addAll(vbox);
        
        /* Leihoa ixterakoan, datuak fitxategian gordeko ditu */
        stage.setOnCloseRequest((WindowEvent event) -> {
            Kontroladorea.fitxategianGorde(this.obListDatuak, this.fitxategia);
        });
        
        return scenePeliDatuak; 
    }
    
    /**
     * 
     * @param lehenStage Stagea
     * @param b booleanoa, True: fitx zabaldu. False: fitx gorde 
     * @return aukeratutako fitxategia bueltatzen du
     */
    private File fileChooserErabili(Stage lehenStage, boolean b){
        File aukFitx;
        FileChooser fileChooser = new FileChooser();
        File defaultDirectory = new File(".\\src\\fitxategiak"); // Defektuz, fitxategia aukeratzeko proiektuko karpeta horretan zabalduko du
        fileChooser.setInitialDirectory(defaultDirectory);
        fileChooser.getExtensionFilters().addAll(
                new ExtensionFilter("Text Files", "*.txt"),
                new ExtensionFilter("XML Files", "*.xml"),
                new ExtensionFilter("JSON Files", "*.json"));
//                new ExtensionFilter("All Files", "*.*"));
        if (b) { // TRUE
            fileChooser.setTitle("Ireki fitxategia...");
            aukFitx = fileChooser.showOpenDialog(lehenStage);
        }
        else { // FALSE
            fileChooser.setTitle("Gorde fitxategia honela...");
            aukFitx = fileChooser.showSaveDialog(lehenStage);
            // fitxategia ez denez existitzen, sortu egingo da
            try {
                aukFitx.getAbsoluteFile().createNewFile();
            } 
            catch (IOException ex) {
                Logger.getLogger(UD1_Pelikulak.class.getName()).log(Level.SEVERE, null, ex);
            }
        }   
        return aukFitx; 
    }
    
    /**
     * Label-ei, estiloa eman
     * @param label 
     */
    private void labelEstiloa(Label label) {
        label.setFont(Font.font("Calibri", FontWeight.BOLD, 14));
    }
    
    /**
     * Bistako tituluei estiloa (kolorea, tamaina...) eman
     * @param label 
     */
    private void tituluEstiloa(Label label) {
        label.setFont(Font.font("Calibri", FontWeight.BOLD, 24));
        label.setStyle("-fx-stroke: black;-fx-text-fill: #7a2334");
    }
    
    /**
     * Kargatzen ari den fitxategiaren izena eta berri bat irekitzeko aukera ematen duten label-en estiloa
     * @param label 
     */
    private void labelFitxAukEstiloa(Label label) {
        label.setFont(Font.font("Arial", FontPosture.ITALIC, 15));
        label.setTextFill(Color.web("#1047a0"));
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args); // start metodoa exekutatzen du
    }
}