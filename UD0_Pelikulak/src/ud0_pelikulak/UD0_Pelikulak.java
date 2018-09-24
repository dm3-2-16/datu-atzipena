/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ud0_pelikulak;

import controller.Kontroladorea;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.converter.IntegerStringConverter;
import model.Pelikula;



/**
 *
 * @author Oihane Axpe
 * @version V1.0
 */
public class UD0_Pelikulak extends Application { // Application klasetik heredatzeko
    /* ATRIBUTOAK */
    private final TableView<Pelikula> taula = new TableView<>(); // taula sortzeko instantzia
    final HBox hbox1 = new HBox(); // horizontal box
    final HBox hbox2 = new HBox(); // horizontal box
    final HBox hbox3 = new HBox(); // horizontal box
    final VBox vbox = new VBox(); // vertical box
    private String btnStyle = "-fx-background-color:lightcoral; -fx-font: 20px \"Serif\"; -fx-text-fill: white; -fx-alignment: CENTER;"; // BOTOIEN ESTILOA DEFINITU
    
    @Override
    public void start(Stage stage) { // stage --> Bista/Window
        Scene scene = new Scene(new Group());
        ObservableList<Pelikula> pDatuak = Kontroladorea.datuakKargatu();
        
        stage.setTitle("PELIKULAK"); // Bistari titulua gehitu
        stage.setWidth(900);
        stage.setHeight(650);
        
        /* LABEL bat gehitu - Taularen titulua */
        final Label label = new Label("Pelikulen datuak:");
        label.setFont(new Font("Calibri", 22));

        /* TAULAREN propietateak aldatu */
        taula.setEditable(false);
        taula.setStyle("-fx-background-color:lightcoral");        
        
        /* Pelikulen ID-a definitu */
        TableColumn<Pelikula, String> idZut = new TableColumn<>("Id-a"); // zutabearen titulua
        idZut.setMinWidth(50); // zabalera minimoa
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
            ).setId(t.getNewValue());
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
            ).setId(t.getNewValue());
            });
        
        /* Pelikulen URTEA definitu */
        TableColumn<Pelikula, Integer> urteZut = new TableColumn<>("Urtea"); // zutabearen titulua
        urteZut.setMinWidth(50); // zabalera minimoa
        urteZut.setCellValueFactory(new PropertyValueFactory<Pelikula, Integer>("urtea"));
        urteZut.setCellFactory(TextFieldTableCell.<Pelikula, Integer>forTableColumn(new IntegerStringConverter())); // Integerrera bihurtu
        urteZut.setOnEditCommit(
            (TableColumn.CellEditEvent<Pelikula, Integer> t) -> {
            ((Pelikula) t.getTableView().getItems().get(
            t.getTablePosition().getRow())
            ).setId(t.getNewValue().toString());
            });
        
        /* Pelikulen IRAUPENA definitu */
        TableColumn<Pelikula, Integer> iraupenZut = new TableColumn<>("Iraupena");
        iraupenZut.setMinWidth(50); // zabalera minimoa
        iraupenZut.setCellValueFactory(new PropertyValueFactory<Pelikula, Integer>("iraupena"));
        iraupenZut.setCellFactory(TextFieldTableCell.<Pelikula, Integer>forTableColumn(new IntegerStringConverter())); // Integerrera bihurtu
        iraupenZut.setOnEditCommit(
            (TableColumn.CellEditEvent<Pelikula, Integer> t) -> {
            ((Pelikula) t.getTableView().getItems().get(
            t.getTablePosition().getRow())
            ).setId(t.getNewValue().toString());
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
            ).setId(t.getNewValue());
            });
        
        /* Pelikulen ZUZENDARIA definitu */
        TableColumn<Pelikula, String> zuzendariaZut = new TableColumn<>("Zuzendaria");
        zuzendariaZut.setMinWidth(150); // zabalera minimoa
        zuzendariaZut.setCellValueFactory(new PropertyValueFactory<>("zuzendaria"));
        zuzendariaZut.setCellFactory(TextFieldTableCell.<Pelikula>forTableColumn());
        zuzendariaZut.setOnEditCommit(
            (TableColumn.CellEditEvent<Pelikula, String> t) -> {
            ((Pelikula) t.getTableView().getItems().get(
            t.getTablePosition().getRow())
            ).setId(t.getNewValue());
            });
        
        /* Datuak kargatu */
        taula.setItems(pDatuak); 

        /* Zutabeak taulan gehitu */
        taula.getColumns().addAll(idZut, izenZut, gaiaZut, urteZut, iraupenZut, herrialdeZut, zuzendariaZut);
        
        /* BEHEKO ZATIA - DATUAK GEHITU, EZABATU... */
        final TextField gehituId = new TextField();
        gehituId.setPromptText("Id");
        gehituId.setMaxWidth(idZut.getPrefWidth());
        
        final TextField gehituIzena = new TextField();
        gehituIzena.setPromptText("Izena");
        gehituIzena.setMaxWidth(izenZut.getPrefWidth()*2);
        
        final TextField gehituGaia = new TextField();
        gehituGaia.setPromptText("Gaia");
        gehituGaia.setMaxWidth(gaiaZut.getPrefWidth()*2);
        
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
                Integer.parseInt(gehituIraupena.getText()); // iraupena jaso
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
                    gehituId.getText(),
                    gehituIzena.getText(),
                    gehituGaia.getText(),
                    urtea,
                    iraupena,
                    gehituHerrialdea.getText(),
                    gehituZuzendaria.getText()
                );
                pDatuak.add(peli); 
                gordeta = true; // gorde egingo da
            }
            if(!urteaOndo) {
                gehituUrtea.clear(); // urtea gaizki sartu badu, textua hustu
            }
            if (!iraupenaOndo) {
                gehituIraupena.clear(); // iraupena gaizki sartu badu, textua hustu
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
                gehituGaia.clear();
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
                pDatuak.remove(pelikula1);
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
            System.exit(0);
        });
        
        hbox1.getChildren().addAll(gehituId, gehituIzena, gehituGaia, gehituIraupena);
        hbox1.setSpacing(10); //textField-en arteko espazioa
        hbox2.getChildren().addAll(gehituUrtea, gehituHerrialdea, gehituZuzendaria);
        hbox2.setSpacing(10); //textField-en arteko espazioa
        hbox3.getChildren().addAll(btnGehitu, btnEzabatu, btnIrten);
        hbox3.setSpacing(30);
        
        /* -------------------------------------------------------------- */
        vbox.setSpacing(10); // label eta taularen arteko tartea
        vbox.setPadding(new Insets(20, 0, 0, 20));
        vbox.getChildren().addAll(label, taula, hbox1, hbox2, hbox3);
        ((Group) scene.getRoot()).getChildren().addAll(vbox);
        
        /* BISTA erakusteko */
        stage.setScene(scene);
        stage.show();
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args); // start metodoa exekutatzen du
    }
}

