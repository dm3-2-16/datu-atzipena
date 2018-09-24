/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ud0_pelikulak;

import controller.Kontroladorea;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
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
    final HBox hbox = new HBox(); // horizontal box
    final VBox vbox = new VBox(); // vertical box
    
    @Override
    public void start(Stage stage) { // stage --> Bista/Window
        Scene scene = new Scene(new Group());
        ObservableList<Pelikula> pDatuak = Kontroladorea.datuakKargatu();
        
        stage.setTitle("PELIKULAK"); // Bistari titulua gehitu
        stage.setWidth(800);
        stage.setHeight(750);
        
        /* LABEL bat gehitu - Taularen titulua */
        final Label label = new Label("Pelikulen datuak:");
        label.setFont(new Font("Calibri", 20));

        /* TAULAREN propietateak aldatu */
        taula.setEditable(false);
        taula.setStyle("-fx-background-color:lightcoral");
        //taula.setStyle("-fx-background-color: black");
        //taula.setStyle("-fx-background-color:lightgreen");
        
        
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
        urteZut.setMinWidth(100); // zabalera minimoa
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
        iraupenZut.setMinWidth(100); // zabalera minimoa
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
        
        /* Datuak kargatu */
        taula.setItems(pDatuak); 

        /* Zutabeak taulan gehitu */
        taula.getColumns().addAll(idZut, izenZut, gaiaZut, urteZut, iraupenZut, herrialdeZut);
        
        
        vbox.setSpacing(10); // label eta taularen arteko tartea
        vbox.setPadding(new Insets(20, 0, 0, 20));
        vbox.getChildren().addAll(label, taula, hbox);
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

