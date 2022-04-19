package controller;

import dao.QuestionarioDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Questionario;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class VotoMedio implements Initializable {

    @FXML
    private Button exit;
    @FXML
    private Button siEsci;
    @FXML
    private Button noResta;

    ObservableList<Questionario> Elenco = FXCollections.observableArrayList();

    @FXML
    private TableColumn<Questionario, Integer> Vacanza;
    @FXML
    private TableColumn<Questionario,Integer> Voto;
    @FXML
    private TableView <Questionario> VotoMedio;

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
        nuovaScena.changeScene("HomePageAmministratore.fxml");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Elenco.clear();
        Vacanza.setCellValueFactory(new PropertyValueFactory<>("vacanza"));
        Voto.setCellValueFactory(new PropertyValueFactory<>("voto"));
        Elenco = QuestionarioDAO.avgQuestionario();
        VotoMedio.setItems(Elenco);
    }
}
