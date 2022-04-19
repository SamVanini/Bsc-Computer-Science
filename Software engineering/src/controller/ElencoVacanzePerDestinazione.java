package controller;

import dao.VacanzeDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Vacanze;

import java.io.IOException;

public class ElencoVacanzePerDestinazione {

    @FXML
    private Button siEsci;
    @FXML
    private Button noResta;
    @FXML
    private Button exit;
    @FXML
    private Label errore;

    ObservableList<Vacanze> Elenco = FXCollections.observableArrayList();

    @FXML
    private TableView<Vacanze> TabellaVacanzeDest;
    @FXML
    private TextField Dest;
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

    public void cerca(ActionEvent actionEvent) {
        if (Dest.getText().isBlank()){
            errore.setText("errore!!");
        }else {
            Elenco.clear();
            Destinazione.setCellValueFactory(new PropertyValueFactory<>("destinazione"));
            DataPartenza.setCellValueFactory(new PropertyValueFactory<>("dataPartenza"));
            NumeroSettimane.setCellValueFactory(new PropertyValueFactory<>("numeroSettimane"));
            LinguaStudiata.setCellValueFactory(new PropertyValueFactory<>("linguaStudiata"));
            Gita1.setCellValueFactory(new PropertyValueFactory<>("gita1"));
            Gita2.setCellValueFactory(new PropertyValueFactory<>("gita2"));
            Costo.setCellValueFactory(new PropertyValueFactory<>("costo"));
            Elenco = VacanzeDAO.selectVacanzeDestinazione(Dest.getText());
            TabellaVacanzeDest.setItems(Elenco);
        }
    }
}
