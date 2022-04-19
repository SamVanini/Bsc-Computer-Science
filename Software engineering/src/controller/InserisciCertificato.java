package controller;

import dao.LockDAO;
import dao.PrenotazioneDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;

import static java.lang.Integer.parseInt;

public class InserisciCertificato {

    @FXML
    private Button siEsci;
    @FXML
    private Button noResta;
    @FXML
    private Button exit;
    @FXML
    private TextField IDRagazzo;
    @FXML
    private TextField Certificato;
    @FXML
    private Label errore;

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

    private boolean controllaId(String string){
        char ch;
        for(int i = 0 ; i < string.length() ; i++){
            ch = string.charAt(i);
            if(ch < '0' || ch > '9'){
                return false;
            }
        }

        return true;
    }

    public void inserisci(ActionEvent actionEvent) throws IOException {
        Main nuovaScena = new Main();

        if(IDRagazzo.getText().isBlank() || Certificato.getText().isBlank()){
            errore.setText("errore!");
            if(IDRagazzo.getText().isBlank() || !controllaId(IDRagazzo.getText())){
                IDRagazzo.setText("");
            }
        }else {
            while(LockDAO.isLocked("prenotazione") == 1);
            LockDAO.lock("prenotazione");
            PrenotazioneDAO.updatePrenotazione(parseInt(IDRagazzo.getText()), Certificato.getText());
            LockDAO.unlock("prenotazione");
            nuovaScena.changeScene("HomePageAmministratore.fxml");
        }
    }
}
