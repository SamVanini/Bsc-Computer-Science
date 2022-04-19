package controller;

import dao.PrenotazioneDAO;
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

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class ElencoCertificati implements Initializable {

    public Button siEsci;
    public Button noResta;
    @FXML
    private Button exit;

    ObservableList<Certificati> Elenco = FXCollections.observableArrayList();

    @FXML
    public TableView<Certificati> TabellaCert;

    @FXML
    private TableColumn<Certificati, Integer> Vacanza;

    @FXML
    private TableColumn<Certificati, String> Certificato;

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
        loadData();
    }

    private void loadData() {
        Vacanza.setCellValueFactory(new PropertyValueFactory<>("vacanza"));
        Certificato.setCellValueFactory(new PropertyValueFactory<>("certificatoAcquisito"));
        Elenco = PrenotazioneDAO.getCertificati();
        TabellaCert.setItems(Elenco);
    }
}
