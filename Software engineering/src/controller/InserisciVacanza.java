package controller;

import dao.AllergieDAO;
import dao.GenitoriDAO;
import dao.LockDAO;
import dao.VacanzeDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.lang.Integer.parseInt;

public class InserisciVacanza implements Initializable {

    @FXML
    private Button siEsci;
    @FXML
    private Button noResta;
    @FXML
    private Button exit;
    @FXML
    private Label errore;
    @FXML
    private TextField Destinazione;
    @FXML
    private TextField DataPartenza;
    @FXML
    private TextField NumeroSettimane;
    @FXML
    private TextField LinguaStudiata;
    @FXML
    private TextField Costo;
    @FXML
    private ComboBox Gita1;
    @FXML
    private ComboBox Gita2;

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

    public void submit(ActionEvent actionEvent) throws IOException {
        Main nuovaScena = new Main();
        if(Destinazione.getText().isBlank() || DataPartenza.getText().isBlank() ||
                NumeroSettimane.getText().isBlank() || LinguaStudiata.getText().isBlank() ||
                Costo.getText().isBlank() || !contienteSoloLettere(Destinazione.getText()) ||
                !contienteSoloLettere(LinguaStudiata.getText()) || !controllaCosto(Costo.getText()) ||
                !controllaSettimane(NumeroSettimane.getText()) || dataErrata(DataPartenza.getText()) ||
                Gita1.getSelectionModel().getSelectedItem() == null ||
                Gita2.getSelectionModel().getSelectedItem() == null){
            errore.setText("Errore!!!");
            if(Destinazione.getText().isBlank() || !contienteSoloLettere(Destinazione.getText())){
                Destinazione.setText("");
            }
            if(DataPartenza.getText().isBlank() || dataErrata(DataPartenza.getText())){
                DataPartenza.setText("");
            }
            if(NumeroSettimane.getText().isBlank() || !controllaSettimane(NumeroSettimane.getText())){
                NumeroSettimane.setText("");
            }
            if(LinguaStudiata.getText().isBlank() || !contienteSoloLettere(LinguaStudiata.getText())){
                LinguaStudiata.setText("");
            }
            if(Costo.getText().isBlank() || !controllaCosto(Costo.getText())){
                Costo.setText("");
            }
        }else {
            if(Gita1.getSelectionModel().getSelectedItem() != null && Gita2.getSelectionModel().getSelectedItem() != null)
                while (LockDAO.isLocked("vacanze") == 1);
                LockDAO.lock("vacanze");
                VacanzeDAO.insertVacanze(Destinazione.getText(), DataPartenza.getText(), parseInt(NumeroSettimane.getText()), LinguaStudiata.getText(), Gita1.getSelectionModel().getSelectedItem().toString(),
                    Gita2.getSelectionModel().getSelectedItem().toString(), parseInt(Costo.getText()));
                LockDAO.unlock("vacanze");
                nuovaScena.changeScene("HomePageAmministratore.fxml");
        }
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

    private boolean controllaSettimane(String string) {
        char ch;
        for(int i = 0 ; i < string.length() ; i++){
            ch = string.charAt(i);
            if(ch < '0' || ch > '9'){
                return false;
            }
        }

        int num = Integer.parseInt(string);

        return num > 0;
    }

    private boolean controllaCosto(String string) {
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

    private boolean dataErrata(String string) {
        //data corretta es: 05/10/00
        String correct = "05/10/22";
        if(string.length() != correct.length()){
            return true;
        }else if(!(string.charAt(2) == '/') || !(string.charAt(5) == '/')){
            return true;
        }

        String temp = string.substring(0, 2) + string.substring(3, 5) + string.substring(6);
        for(int i = 0 ; i < temp.length() ; i++){
            char ch = temp.charAt(i);
            if(ch < '0' || ch > '9'){
                return true;
            }
        }

        String g = temp.substring(0,2);
        String m = temp.substring(2,4);
        String y = temp.substring(4);
        int giorno;
        int mese;
        int anno = 2000;

        try {
            giorno = Integer.parseInt(g);
            mese = Integer.parseInt(m);
            anno += Integer.parseInt(y);
        }
        catch (NumberFormatException e)
        {
            return true;
        }

        int[] mesi = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        if(anno < 2020 || anno > 2025){
            return true;
        }else if(mese < 1 || mese > 12){
            return true;
        }else if((anno % 400 == 0) || (anno %4 == 0 && anno % 100 != 0)){
            if(mese == 2) {
                if (giorno > 29) {
                    return true;
                }
            }
        }else if(giorno > mesi[mese - 1]){
            return true;
        }

        return false;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setCombobox();
    }

    private void setCombobox() {
        String url = "jdbc:postgresql://localhost:5432/VacanzeStudio";
        String user = "postgres";
        String password = "password";

        String query = "SELECT * FROM GITE";

        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            Logger log = Logger.getLogger(AllergieDAO.class.getName());
            log.log(Level.SEVERE, e.getMessage(), e);
        }

        try(Connection con = DriverManager.getConnection(url, user, password)){

            ResultSet rs = con.createStatement().executeQuery(query);
            ObservableList data = FXCollections.observableArrayList();

            while(rs.next()){
                data.add(new String(rs.getString("destinazione")));
            }
            Gita1.setItems(data);
            Gita2.setItems(data);
        }
        catch (SQLException e){
            Logger log = Logger.getLogger(GenitoriDAO.class.getName());
            log.log(Level.SEVERE, e.getMessage(), e);
        }
    }
}
