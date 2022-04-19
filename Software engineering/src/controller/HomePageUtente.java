package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import model.Genitori;
import model.Utente;
import java.io.IOException;

public class HomePageUtente{

    @FXML
    private Button exit;
    @FXML
    private Button siEsci;
    @FXML
    private Button noResta;

    private Utente utente = Utente.getInstance();
    private Genitori genitore = Genitori.getInstance();

    public void prenota(ActionEvent actionEvent) throws IOException {
        Main nuovaScena = new Main();
        nuovaScena.changeScene("PrenotaVacanza.fxml");
    }

    public void cercaPartenza(ActionEvent actionEvent) throws IOException {
        Main nuovaScena = new Main();
        nuovaScena.changeScene("ElencoVacanzePerDataPartenza.fxml");
    }

    public void cercaDurata(ActionEvent actionEvent) throws IOException {
        Main nuovaScena = new Main();
        nuovaScena.changeScene("ElencoVacanzePerDurata.fxml");
    }

    public void storico(ActionEvent actionEvent) throws IOException {
        Main nuovaScena = new Main();
        nuovaScena.changeScene("ElencoVacanzePrenotate.fxml");
    }

    public void certificati(ActionEvent actionEvent) throws IOException {
        Main nuovaScena = new Main();
        nuovaScena.changeScene("ElencoCertificati.fxml");
    }

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

    public void profilo(ActionEvent actionEvent) throws IOException {
        Main nuovaScena = new Main();
        nuovaScena.changeScene("ModificaAccountUtente.fxml");
    }

    public void cercaDestinazione(ActionEvent actionEvent) throws IOException {
        Main nuovaScena = new Main();
        nuovaScena.changeScene("ElencoVacanzePerDestinazione.fxml");
    }

    public void compila(ActionEvent actionEvent) throws IOException {
        Main nuovaScena = new Main();
        nuovaScena.changeScene("InserisciQuestionario.fxml");
    }
}
