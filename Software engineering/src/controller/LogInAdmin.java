package controller;

import dao.ResponsabiliDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;

public class LogInAdmin {
    @FXML
    private Label erroreAccesso;
    @FXML
    private TextField username;
    @FXML
    private PasswordField password;

    public void tornaIndietro(ActionEvent actionEvent) throws IOException {
        Main nuovaScena = new Main();
        nuovaScena.changeScene("LogInUtente.fxml");
    }

    public void accessoAdmin(ActionEvent actionEvent) throws IOException {
        Main nuovaScena = new Main();

        int control = ResponsabiliDAO.checkCredenziali(username.getText(), password.getText());
        if (control == 1)
            nuovaScena.changeScene("HomePageAmministratore.fxml");
        else{
            erroreAccesso.setText("Credenziali errate: reinserire");
            username.setText("");
            password.setText("");
        }
    }
}
