package controller;

import dao.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.Genitori;
import model.Utente;

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

public class PrenotaVacanza implements Initializable {

    @FXML
    private Button siEsci;
    @FXML
    private Button noResta;
    @FXML
    private Button exit;
    @FXML
    private Label errore;
    @FXML
    private TextField Data;
    @FXML
    private TextField MetodoPagamento;
    @FXML
    private TextField NomeA;
    @FXML
    private TextField CognomeA;
    @FXML
    private TextField MailA;
    @FXML
    private ComboBox Vacanza;
    @FXML
    private ComboBox College;
    @FXML
    private ComboBox Famiglia;

    private Utente utente = Utente.getInstance();
    private Genitori genitore = Genitori.getInstance();
    private String nomeF = "";
    private String tipologia = "";
    private String nomeC = "";
    private String destinazione = "";
    private String data = "";
    private String numeroS = "";
    private String lingua = "";

    private void setComboBoxF() {
        String url = "jdbc:postgresql://localhost:5432/VacanzeStudio";
        String user = "postgres";
        String password = "password";

        String query = "SELECT * FROM FAMIGLIA";

        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            Logger log = Logger.getLogger(PrenotaVacanza.class.getName());
            log.log(Level.SEVERE, e.getMessage(), e);
        }

        try(Connection con = DriverManager.getConnection(url, user, password)){

            ResultSet rs = con.createStatement().executeQuery(query);
            ObservableList data = FXCollections.observableArrayList();

            while(rs.next()){
                String add = "";
                add = add + rs.getString("nome") + " " + rs.getString("cognome") + " " + rs.getString("telefono") + " " + rs.getString("mail") + " "+
                        rs.getString("paeseresidenza") + " ";
                data.add(add);
            }
            Famiglia.setItems(data);
        }
        catch (SQLException e){
            Logger log = Logger.getLogger(PrenotaVacanza.class.getName());
            log.log(Level.SEVERE, e.getMessage(), e);
        }
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

    public void back(ActionEvent actionEvent) throws IOException {
        Main nuovaScena = new Main();
        nuovaScena.changeScene("HomePageUtente.fxml");
    }

    private boolean contienteSoloLettere(String string) {
        char ch;
        for(int i = 0 ; i < string.length() ; i++){
            ch = string.charAt(i);
            if(ch < 'a' || ch > 'z'){
                if(ch < 'A' || ch > 'Z') {
                    return false;
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

    public void prenota(ActionEvent actionEvent) throws IOException {
        if(NomeA.getText().isBlank() || CognomeA.getText().isBlank() || MailA.getText().isBlank() ||
                MetodoPagamento.getText().isBlank() || Data.getText().isBlank() ||
                Vacanza.getSelectionModel().getSelectedItem() == null || Famiglia.getSelectionModel().getSelectedItem() == null
                || College.getSelectionModel().getSelectedItem() == null){
            errore.setText("errore!!");
            if(NomeA.getText().isBlank() || !contienteSoloLettere(NomeA.getText())){
                NomeA.setText("");
            }
            if(CognomeA.getText().isBlank() || !contienteSoloLettere(CognomeA.getText())){
                CognomeA.setText("");
            }
            if(MetodoPagamento.getText().isBlank() || !contienteSoloLettere(MetodoPagamento.getText())){
                MetodoPagamento.setText("");
            }
            if(MailA.getText().isBlank() || !controllaMail(MailA.getText())){
                MailA.setText("");
            }
            if(Data.getText().isBlank() || dataErrata(Data.getText())){
                Data.setText("");
            }
            if(Vacanza.getSelectionModel().getSelectedItem() == null){
                errore.setText("Combobox vuoto!");
            }
            if(Famiglia.getSelectionModel().getSelectedItem() == null){
                errore.setText("Combobox vuoto!");
            }
            if(College.getSelectionModel().getSelectedItem() == null){
                errore.setText("Combobox vuoto!");
            }
        } else {
            if(College.getSelectionModel().getSelectedItem() != null && Vacanza.getSelectionModel().getSelectedItem() != null && Famiglia.getSelectionModel().getSelectedItem() != null) {
                String college = College.getSelectionModel().getSelectedItem().toString();
                String famiglia = Famiglia.getSelectionModel().getSelectedItem().toString();
                String vacanza = Vacanza.getSelectionModel().getSelectedItem().toString();
                Main nuovaScena = new Main();
                int idF = traceFamiglia(famiglia);
                int idC = traceCollege(college);
                int idV = traceVacanza(vacanza);

                while (LockDAO.isLocked("prenotazione") == 1) ;
                LockDAO.lock("prenotazione");
                PrenotazioneDAO.insertPrenotazione(Data.getText(), utente.getId(), idV, idC, idF, MetodoPagamento.getText(), NomeA.getText(), CognomeA.getText(), MailA.getText(), "Nessuno");
                LockDAO.unlock("prenotazione");
                nuovaScena.changeScene("HomePageUtente.fxml");
            }
        }
    }

    private int traceVacanza(String vacanza) {
        char c;
        int init = 0;
        int field = 0;
        for(int i = 0; i < vacanza.length(); i++){
            c = vacanza.charAt(i);
            if(c == ' '){
                switch (field) {
                    case 0:
                        destinazione = destinazione + vacanza.substring(init, i);
                        init = i + 1;
                        field++;
                        break;
                    case 1:
                        data = data + vacanza.substring(init, i);
                        init = i + 1;
                        field++;
                        break;
                    case 2:
                        numeroS = numeroS + vacanza.substring(init, i);
                        init = i + 1;
                        field++;
                        break;
                    case 3:
                        lingua = lingua + vacanza.substring(init, i);
                        init = i + 1;
                        field++;
                        break;
                }
            }
        }

        int idV = VacanzeDAO.getIdVacanza(destinazione, data, parseInt(numeroS), lingua);
        return idV;
    }

    private int traceCollege(String college) {
        char c;
        int init = 0;
        int field = 0;
        for(int i = 0; i <college.length(); i++){
            c = college.charAt(i);
            if(c == ' '){
                switch (field) {
                    case 0:
                        tipologia = tipologia + college.substring(init, i);
                        init = i + 1;
                        field++;
                        break;
                    case 1:
                        nomeC = nomeC + college.substring(init, i);
                        init = i + 1;
                        field++;
                        break;
                }
            }
        }

        int idC = CamereCollegeDAO.getIndexCamera(tipologia, nomeC);
        return idC;
    }

    private int traceFamiglia(String famiglia) {
        char c;
        int init = 0;
        int field = 0;
        for(int i = 0; i <famiglia.length(); i++){
            c = famiglia.charAt(i);
            if(field == 0){
                if(c == ' '){
                        nomeF = nomeF +famiglia.substring(init, i);
                        field++;
                        break;
                }
            }else
                break;
        }

        int idF = FamigliaDAO.selectFamiglia(nomeF);
        return idF;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setComboBoxF();
        setComboBoxC();
        setComboBoxV();
    }

    private void setComboBoxV() {
        String url = "jdbc:postgresql://localhost:5432/VacanzeStudio";
        String user = "postgres";
        String password = "password";

        String query = "SELECT VACANZE.destinazione, VACANZE.datapartenza, VACANZE.numerosettimane, VACANZE.linguastudiata FROM VACANZE";

        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            Logger log = Logger.getLogger(PrenotaVacanza.class.getName());
            log.log(Level.SEVERE, e.getMessage(), e);
        }

        try(Connection con = DriverManager.getConnection(url, user, password)){

            ResultSet rs = con.createStatement().executeQuery(query);
            ObservableList data = FXCollections.observableArrayList();

            while(rs.next()){
                String add = "";
                add = add + rs.getString("destinazione") + " " + rs.getString("datapartenza") + " " + rs.getString("numerosettimane") + " " + rs.getString("linguastudiata") + " ";
                data.add(add);
            }
            Vacanza.setItems(data);
        }
        catch (SQLException e){
            Logger log = Logger.getLogger(PrenotaVacanza.class.getName());
            log.log(Level.SEVERE, e.getMessage(), e);
        }
    }

    private void setComboBoxC() {
        String url = "jdbc:postgresql://localhost:5432/VacanzeStudio";
        String user = "postgres";
        String password = "password";

        String query = "SELECT CAMERE.tipologia, COLLEGE.nome, COLLEGE.citta FROM CAMERE, COLLEGE WHERE CAMERE.college = COLLEGE.nome";

        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            Logger log = Logger.getLogger(PrenotaVacanza.class.getName());
            log.log(Level.SEVERE, e.getMessage(), e);
        }

        try(Connection con = DriverManager.getConnection(url, user, password)){

            ResultSet rs = con.createStatement().executeQuery(query);
            ObservableList data = FXCollections.observableArrayList();

            while(rs.next()){
                String add = "";
                add = add + rs.getString("tipologia") + " " + rs.getString("nome") + " " + rs.getString("citta") + " ";
                data.add(add);
            }
            College.setItems(data);
        }
        catch (SQLException e){
            Logger log = Logger.getLogger(PrenotaVacanza.class.getName());
            log.log(Level.SEVERE, e.getMessage(), e);
        }
    }
}
