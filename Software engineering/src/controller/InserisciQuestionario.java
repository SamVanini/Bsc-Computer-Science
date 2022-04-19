package controller;

import dao.LockDAO;
import dao.PrenotazioneDAO;
import dao.QuestionarioDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import model.Utente;

import java.io.IOException;

import static java.lang.Integer.parseInt;

public class InserisciQuestionario {

    @FXML
    private Button noResta;
    @FXML
    private Button siEsci;
    @FXML
    private Button exit;
    @FXML
    private Label errore;
    @FXML
    private TextField Prenotazione;
    @FXML
    private TextArea Commento;
    @FXML
    private TextField Voto;

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

    private boolean controllaVoto(String string){
        char ch;
        for(int i = 0 ; i < string.length() ; i++){
            ch = string.charAt(i);
            if(ch < '0' || ch > '9'){
                return false;
            }
        }

        int voto = Integer.parseInt(string);

        return voto > 0 && voto < 11;
    }

    public void inserisci(ActionEvent actionEvent) throws IOException {
        Main nuovaScena = new Main();
        Utente utente = Utente.getInstance();
        if (Commento.getText().isBlank() || Voto.getText().isBlank() || !controllaVoto(Voto.getText())) {
            if(!controllaVoto(Voto.getText())){
                Voto.setText("");
            }
            errore.setText("Errore!");
        } else {
            while (LockDAO.isLocked("questionario") == 1);
            LockDAO.lock("questionario");
            QuestionarioDAO.insertQuestionario(parseInt(Voto.getText()), Commento.getText());
            int id = QuestionarioDAO.selectID(parseInt(Voto.getText()), Commento.getText());
            LockDAO.unlock("questionario");
            while (LockDAO.isLocked("prenotazione") == 1);
            LockDAO.lock("prenotazione");
            PrenotazioneDAO.selectPrenotazione(parseInt(Prenotazione.getText()));
            PrenotazioneDAO.insertQuestionario(parseInt(Prenotazione.getText()), id);
            LockDAO.unlock("prenotazione");
            nuovaScena.changeScene("HomePageUtente.fxml");
        }
    }
}
