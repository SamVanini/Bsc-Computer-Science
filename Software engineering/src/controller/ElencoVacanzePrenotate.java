package controller;

import dao.VacanzeDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Certificati;
import model.Vacanze;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ElencoVacanzePrenotate implements Initializable {

    @FXML
    private Button noResta;
    @FXML
    private Button siEsci;
    @FXML
    private Button exit;

    ObservableList<Vacanze> Elenco = FXCollections.observableArrayList();

    @FXML
    private TableView<Vacanze> ElencoVacanzePrenotate;
    @FXML
    private TableColumn<Vacanze, String> Destinazione;
    @FXML
    private TableColumn<Vacanze, String> DataPartenza;
    @FXML
    private TableColumn<Vacanze, Integer> NumeroSettimane;
    @FXML
    private TableColumn<Vacanze, String> LinguaStudiata;
    @FXML
    private TableColumn<Vacanze, String> Gita1;
    @FXML
    private TableColumn<Vacanze, String> Gita2;
    @FXML
    private TableColumn<Vacanze, Integer> Costo;

    public void logOut(ActionEvent actionEvent) {
        exit.setText("Vuoi uscire?");
        siEsci.setOpacity(50);
        noResta.setOpacity(50);
    }

    public void uscita(ActionEvent actionEvent) throws IOException {
        Main nuovaScena = new Main();
        nuovaScena.changeScene("LogInUtente.fxml");
    }

    public void rimani(ActionEvent actionEvent) {
        exit.setText("Log Out");
        siEsci.setOpacity(0);
        noResta.setOpacity(0);
    }

    public void back(ActionEvent actionEvent) throws IOException {
        Main nuovaScena = new Main();
        nuovaScena.changeScene("HomePageUtente.fxml");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Elenco.clear();
        Destinazione.setCellValueFactory(new PropertyValueFactory<>("destinazione"));
        DataPartenza.setCellValueFactory(new PropertyValueFactory<>("dataPartenza"));
        NumeroSettimane.setCellValueFactory(new PropertyValueFactory<>("numeroSettimane"));
        LinguaStudiata.setCellValueFactory(new PropertyValueFactory<>("linguaStudiata"));
        Gita1.setCellValueFactory(new PropertyValueFactory<>("gita1"));
        Gita2.setCellValueFactory(new PropertyValueFactory<>("gita2"));
        Costo.setCellValueFactory(new PropertyValueFactory<>("costo"));
        Elenco = VacanzeDAO.selectVacanzePrenotate();
        ElencoVacanzePrenotate.setItems(Elenco);
    }
}
