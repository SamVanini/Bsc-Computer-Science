package controller;

import dao.GiteDAO;
import dao.LockDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.IOException;

import static java.lang.Integer.parseInt;

public class InserisciGita {

    @FXML
    private Button exit;
    @FXML
    private Button siEsci;
    @FXML
    private Button noResta;
    @FXML
    private Label errore;
    @FXML
    private TextField Destinazione;
    @FXML
    private TextField Costo;
    @FXML
    private TextField NumeroOre;
    @FXML
    private TextArea Descrizione;

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

    private boolean contienteSoloLettere(String string) {
        char ch;
        for(int i = 0 ; i < string.length() ; i++){
            ch = string.charAt(i);
            if(ch < 'a' || ch > 'z'){
                if(ch < 'A' || ch > 'Z') {
                    if(ch != ' ') {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private boolean controllaCifre(String string) {
        char ch;
        int k = 0;
        int j = 0;

        for(int i = 0 ; i < string.length() ; i++){
            ch = string.charAt(i);
            if(ch < '0' || ch > '9'){
                if(ch != ','){
                    return false;
                }else {
                    k++;
                    if(k == 1){
                        j = i;
                    }else {
                        return false;
                    }
                }
            }
        }

        int num;
        String s = "";
        if(string.contains(",")){
            s = string.substring(0, j) + string.substring(j + 1);
            num = Integer.parseInt(s);
        }else {
            num = Integer.parseInt(string);
        }

        return num > 0;
    }

    public void submit(ActionEvent actionEvent) throws IOException {
        Main nuovaScena = new Main();
        if(Destinazione.getText().isBlank() || Costo.getText().isBlank() || NumeroOre.getText().isBlank() ||
                Descrizione.getText().isBlank() || !contienteSoloLettere(Destinazione.getText()) ||
                !controllaCifre(Costo.getText()) || !controllaCifre(NumeroOre.getText())){
            errore.setText("Errore!!");
            if(Destinazione.getText().isBlank() || !contienteSoloLettere(Destinazione.getText())){
                Destinazione.setText("");
            }
            if(Costo.getText().isBlank() || !controllaCifre(Costo.getText())){
                Costo.setText("");
            }
            if(NumeroOre.getText().isBlank() || !controllaCifre(NumeroOre.getText())){
                NumeroOre.setText("");
            }
        }else {
            while(LockDAO.isLocked("gite") == 1);
            LockDAO.lock("gite");
            GiteDAO.insertGite(Destinazione.getText(), parseInt(Costo.getText()), parseInt(NumeroOre.getText()), Descrizione.getText());
            LockDAO.unlock("gite");
            nuovaScena.changeScene("HomePageAmministratore.fxml");
        }
    }
}
