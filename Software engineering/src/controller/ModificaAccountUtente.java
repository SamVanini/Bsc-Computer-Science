package controller;

import dao.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.Genitori;
import model.Utente;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.lang.Integer.parseInt;


public class ModificaAccountUtente implements Initializable {

    @FXML
    private Label errNomeG;
    @FXML
    private Label errTelefonoG;
    @FXML
    private Label errCognomeG;
    @FXML
    private Label errMailG;
    @FXML
    private Label errHobby;
    @FXML
    private Label errNome;
    @FXML
    private Label errCognome;
    @FXML
    private Label errEta;
    @FXML
    private Label errNumeroCivico;
    @FXML
    private Label errTelefono;
    @FXML
    private Label errCitta;
    @FXML
    private Label errVia;
    @FXML
    private Label errMail;
    @FXML
    private Label errPassword;
    @FXML
    private TextField Telefono;
    @FXML
    private TextField Mail;
    @FXML
    private TextField Hobby;
    @FXML
    private TextField Password;
    @FXML
    private TextField NomeG;
    @FXML
    private TextField CognomeG;
    @FXML
    private TextField TelefonoG;
    @FXML
    private TextField MailG;
    @FXML
    private ComboBox Allergia1;
    @FXML
    private ComboBox Allergia2;
    @FXML
    private Label Errore;
    @FXML
    private TextField Nome;
    @FXML
    private TextField Cognome;
    @FXML
    private TextField Eta;
    @FXML
    private TextField Via;
    @FXML
    private TextField NumeroCivico;
    @FXML
    private TextField Citta;

    private Utente utente = Utente.getInstance();
    private Genitori genitore = Genitori.getInstance();

    private void setComboBox() {
        String url = "jdbc:postgresql://localhost:5432/VacanzeStudio";
        String user = "postgres";
        String password = "password";

        String query = "SELECT * FROM ALLERGIE";

        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            Logger log = Logger.getLogger(ModificaAccountUtente.class.getName());
            log.log(Level.SEVERE, e.getMessage(), e);
        }

        try(Connection con = DriverManager.getConnection(url, user, password)){

            ResultSet rs = con.createStatement().executeQuery(query);
            ObservableList data = FXCollections.observableArrayList();

            while(rs.next()){
                data.add(new String(rs.getString("nome")));
            }
            Allergia1.setItems(data);
            Allergia2.setItems(data);
        }
        catch (SQLException e){
            Logger log = Logger.getLogger(ModificaAccountUtente.class.getName());
            log.log(Level.SEVERE, e.getMessage(), e);
        }
    }

    private void setFieldsRagazzo() {
        Nome.setText(utente.getNome());
        Cognome.setText(utente.getNome());
        Eta.setText(utente.Anni());
        Via.setText(utente.getVia());
        NumeroCivico.setText(utente.getNumeroCivico());
        Citta.setText(utente.getPaese());
        Telefono.setText(utente.getTelefono());
        Mail.setText(utente.getMail());
        Hobby.setText(utente.getHobby());
    }

    private void setFieldsGenitore() {
        NomeG.setText(genitore.getNome());
        CognomeG.setText(genitore.getCognome());
        TelefonoG.setText(genitore.getTelefono());
        MailG.setText(genitore.getMail());
    }

    private boolean controllaPassword(String string) {
        if(string.length() < 8 || string.length() > 32){
            return false;
        }

        if(string.contains(" ")){
            return false;
        }

        boolean minuscola = false;
        boolean maiuscola = false;
        boolean speciale = false;
        boolean numero = false;

        String[] spec = {"@", ".", "-", "_", "!", "?"};

        char ch;
        for(int i = 0 ; i < string.length() ; i++){
            ch = string.charAt(i);
            if(ch > '0' && ch < '9'){
                numero = true;
            }else if(ch > 'a' && ch < 'z'){
                minuscola = true;
            }else if(ch > 'A' && ch < 'Z') {
                maiuscola = true;
            }
        }

        for(String s : spec){
            if(string.contains(s)) {
                speciale = true;
            }
        }

        return minuscola && maiuscola && speciale && numero;
    }

    private boolean controllaEta(String string) {
        int eta = Integer.parseInt(string);

        return eta > 14 && eta < 21;
    }

    private boolean controllaNumeroCivico(String string) {
        char ch = string.charAt(0);
        if((ch == '0') || (ch > 'a' && ch < 'z') || (ch > 'A' && ch < 'Z')){
            return false;
        }

        return true;
    }

    private boolean contienteSoloLettere(String string) {
        char ch;
        for(int i = 0 ; i < string.length() ; i++){
            ch = string.charAt(i);
            if(ch != ' '){
                if(ch < 'a' || ch > 'z'){
                    if(ch < 'A' || ch > 'Z') {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private boolean controllaMail(String string) {
        String[] car = {"com", "it", "libero", "yahoo", "gmail", ".", "@"};
        int token = 0;

        for (String s : car) {
            if (string.contains(s)) {
                token++;
            }
        }

        String temp = string.substring(0, 1);
        for(String s : car){
            if(s.equals(temp)){
                return false;
            }
        }

        return token == 4;
    }

    private boolean controllaTelefono(String string) {
        char ch;

        if(string.length() != 10){
            return false;
        }

        for(int i = 0 ; i < string.length() ; i++){
            ch = string.charAt(i);
            if(ch < '0' || ch > '9'){
                return false;
            }
        }

        return true;
    }


    public void tornaIndietro(ActionEvent actionEvent) throws IOException {
        Main nuovaScena = new Main();
        nuovaScena.changeScene("HomePageUtente.fxml");
    }

    public void modificaAccount(ActionEvent actionEvent) throws IOException {
        Main nuovaScena = new Main();
        if(Nome.getText().isBlank() || Cognome.getText().isBlank()|| Eta.getText().isBlank() ||
                Via.getText().isBlank() || NumeroCivico.getText().isBlank() || Citta.getText().isBlank() ||
                Telefono.getText().isBlank() || Mail.getText().isBlank()||
                Password.getText().isBlank() || NomeG.getText().isBlank() || CognomeG.getText().isBlank() ||
                TelefonoG.getText().isBlank() || MailG.getText().isBlank() || Hobby.getText().isBlank() ||
                !contienteSoloLettere(Nome.getText()) || !contienteSoloLettere(Cognome.getText()) ||
                !contienteSoloLettere(NomeG.getText()) || !contienteSoloLettere(CognomeG.getText()) ||
                !controllaMail(Mail.getText()) || !controllaMail(MailG.getText()) ||
                !controllaTelefono(Telefono.getText()) || !controllaTelefono(TelefonoG.getText()) ||
                !contienteSoloLettere(Citta.getText()) || !contienteSoloLettere(Via.getText()) ||
                !controllaEta(Eta.getText()) || !controllaNumeroCivico(NumeroCivico.getText()) ||
                !controllaPassword(Password.getText()) || !contienteSoloLettere(Hobby.getText()) ||
                Allergia1.getSelectionModel().getSelectedItem() == null ||
                Allergia2.getSelectionModel().getSelectedItem() == null){
            Errore.setText("Campi errati reinserire");
            if(Nome.getText().isBlank() || !contienteSoloLettere(Nome.getText())){
                errNome.setText("campo errato");
                Nome.setText("");
            }else{
                errNome.setText("");
            }
            if(Cognome.getText().isBlank() || !contienteSoloLettere(Cognome.getText())){
                errCognome.setText("campo errato");
                Cognome.setText("");
            }else{
                errCognome.setText("");
            }
            if(NomeG.getText().isBlank() || !contienteSoloLettere(NomeG.getText())){
                errNomeG.setText("campo errato");
                NomeG.setText("");
            }else{
                errNomeG.setText("");
            }
            if(CognomeG.getText().isBlank() || !contienteSoloLettere(CognomeG.getText())){
                errCognomeG.setText("campo errato");
                CognomeG.setText("");
            }else{
                errCognomeG.setText("");
            }
            if(Mail.getText().isBlank() || !controllaMail(Mail.getText())){
                errMail.setText("campo errato");
                Mail.setText("");
            }else{
                errMail.setText("");
            }
            if(MailG.getText().isBlank() || !controllaMail(MailG.getText())){
                errMailG.setText("campo errato");
                MailG.setText("");
            }else{
                errMailG.setText("");
            }
            if(Telefono.getText().isBlank() || !controllaTelefono(Telefono.getText())){
                errTelefono.setText("campo errato");
                Telefono.setText("");
            }else{
                errTelefono.setText("");
            }
            if(TelefonoG.getText().isBlank() || !controllaTelefono(TelefonoG.getText())){
                errTelefonoG.setText("campo errato");
                TelefonoG.setText("");
            }else{
                errTelefonoG.setText("");
            }
            if(Citta.getText().isBlank() || !contienteSoloLettere(Citta.getText())){
                errCitta.setText("campo errato");
                Citta.setText("");
            }else{
                errCitta.setText("");
            }
            if(Via.getText().isBlank() || !contienteSoloLettere(Via.getText())){
                errVia.setText("campo errato");
                Via.setText("");
            }else{
                errVia.setText("");
            }
            if(Eta.getText().isBlank() || !controllaEta(Eta.getText())){
                errEta.setText("campo errato: 14 > eta < 21");
                Eta.setText("");
            }else{
                errEta.setText("");
            }
            if(NumeroCivico.getText().isBlank() || !controllaNumeroCivico(NumeroCivico.getText())){
                errNumeroCivico.setText("campo errato");
                NumeroCivico.setText("");
            }else{
                errNumeroCivico.setText("");
            }
            if(Password.getText().isBlank() || !controllaPassword(Password.getText())){
                errPassword.setText("campo errato: da 8 a 15 caratteri \n no spazi vuoti\n deve contenere almeno:\n una lettera maiuscola\n una lettera minuscola\n un numero\n . - _ @ ! ?");
                Password.setText("");
            }else{
                errPassword.setText("");
            }
            if(Hobby.getText().isBlank() || !contienteSoloLettere(Hobby.getText())){
                errHobby.setText("campo errato");
                Hobby.setText("");
            }else{
                errHobby.setText("");
            }
            if(Allergia1.getSelectionModel().getSelectedItem() == null){
                Errore.setText("Combobox vuoto!");
            }
            if(Allergia2.getSelectionModel().getSelectedItem() == null){
                Errore.setText("Combobox vuoto!");
            }
        }else {
            if(Allergia1.getSelectionModel().getSelectedItem() != null && Allergia2.getSelectionModel().getSelectedItem() != null)
                while (LockDAO.isLocked("genitori") == 1);
                LockDAO.lock("genitori");
                GenitoriDAO.updateGenitori(utente.getGenitore(), NomeG.getText(), CognomeG.getText(), TelefonoG.getText(), MailG.getText());
                LockDAO.unlock("genitori");
                while(LockDAO.isLocked("ragazzi") == 1);
                LockDAO.lock("ragazzi");
                RagazziDAO.updateRagazzi(utente.getId(), Nome.getText(), Cognome.getText(), parseInt(Eta.getText()), Via.getText(), NumeroCivico.getText(),
                    Citta.getText(), Telefono.getText(), Mail.getText(), Hobby.getText(), Allergia1.getSelectionModel().getSelectedItem().toString(),
                    Allergia2.getSelectionModel().getSelectedItem().toString(), utente.getGenitore());
                LockDAO.unlock("ragazzi");
                while(LockDAO.isLocked("credenziali") == 1);
                LockDAO.lock("credenziali");
                CredenzialiDAO.updateCredenziali(Mail.getText(), Password.getText(), utente.getMail());
                LockDAO.unlock("credenziali");
                utente.setNome(Nome.getText());
                utente.setCognome(Cognome.getText());
                utente.setEta(parseInt(Eta.getText()));
                utente.setVia(Via.getText());
                utente.setNumeroCivico(NumeroCivico.getText());
                utente.setPaese(Citta.getText());
                utente.setTelefono(Telefono.getText());
                utente.setMail(Mail.getText());
                utente.setHobby(Hobby.getText());
                utente.setAllergia1(Allergia1.getSelectionModel().getSelectedItem().toString());
                utente.setAllergia2(Allergia2.getSelectionModel().getSelectedItem().toString());
                genitore.setNome(NomeG.getText());
                genitore.setCognome(CognomeG.getText());
                genitore.setMail(MailG.getText());
                genitore.setTelefono(TelefonoG.getText());
                nuovaScena.changeScene("HomePageUtente.fxml");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setFieldsRagazzo();
        setFieldsGenitore();
        setComboBox();
    }
}
