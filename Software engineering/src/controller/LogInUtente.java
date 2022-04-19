package controller;

import dao.CredenzialiDAO;
import dao.GenitoriDAO;
import dao.RagazziDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import model.Utente;

import java.io.IOException;

public class LogInUtente {
    @FXML
    private Label erroreAccesso;
    @FXML
    private TextField username;
    @FXML
    private PasswordField password;

    private Utente utente = Utente.getInstance();

    public void accesso(ActionEvent actionEvent) throws IOException {
        Main nuovaScena = new Main();

        int control = CredenzialiDAO.checkCredenziali(username.getText(), password.getText());
        if (control == 1){
            utente.setMail(username.getText());
            RagazziDAO.setUtente(username.getText());
            GenitoriDAO.setGenitore();
            nuovaScena.changeScene("HomePageUtente.fxml");
        }
        else{
            erroreAccesso.setText("Credenziali errate: reinserire");
            username.setText("");
            password.setText("");
        }
    }

    public void accessoAdmin(ActionEvent actionEvent) throws IOException {
        Main nuovaScena = new Main();
        nuovaScena.changeScene("LogInAdmin.fxml");
    }

    public void creazioneNuovoAccount(ActionEvent actionEvent) throws IOException {
        Main nuovaScena = new Main();
        nuovaScena.changeScene("NuovoAccountUtente.fxml");
    }
}
