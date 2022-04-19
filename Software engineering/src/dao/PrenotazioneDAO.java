package dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Certificati;
import model.Prenotazione;
import model.Utente;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PrenotazioneDAO {

    public static void insertPrenotazione (String data, int ragazzo, int vacanza, int college, int famiglia, String metodopagamento, String nomeamico, String cognomeamico, String mailamico, String certificato){
        String url = "jdbc:postgresql://localhost:5432/VacanzeStudio";
        String user = "postgres";
        String password = "password";

        String query = "INSERT INTO prenotazione(data, ragazzo, vacanza, college, famiglia, metodopagamento, nomeamico, cognomeamico, mailamico, certificatoacquisito, questionario)" +
                " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try(Connection con = DriverManager.getConnection(url, user, password)){
            PreparedStatement statement = con.prepareStatement(query);

            statement.setString(1, data);
            statement.setInt(2, ragazzo);
            statement.setInt(3, vacanza);
            statement.setInt(4, college);
            statement.setInt(5, famiglia);
            statement.setString(6, metodopagamento);
            statement.setString(7, nomeamico);
            statement.setString(8, cognomeamico);
            statement.setString(9,mailamico);
            statement.setString(10,certificato);
            statement.setInt(11, 1);

            int affectedRows = statement.executeUpdate();

            if(affectedRows > 0){
                System.out.println("Table PRENOTAZIONE successfully updated");
            } else
                System.out.println("Update of PRENOTAZIONE badly failed");
        }
        catch (SQLException e) {
            Logger log = Logger.getLogger(PrenotazioneDAO.class.getName());
            log.log(Level.SEVERE, e.getMessage(), e);
        }
    }

    public static void updatePrenotazione (int key, String certificato){
        String url = "jdbc:postgresql://localhost:5432/VacanzeStudio";
        String user = "postgres";
        String password = "password";

        String query = "UPDATE prenotazione SET certificatoacquisito = ?" +
                "WHERE id =" + key;

        try(Connection con = DriverManager.getConnection(url, user, password)){
            PreparedStatement statement = con.prepareStatement(query);

            statement.setString(1,certificato);

            int affectedRows = statement.executeUpdate();

            if(affectedRows > 0){
                System.out.println("Table PRENOTAZIONE successfully updated");
            } else
                System.out.println("Update of PRENOTAZIONE badly failed");
        }
        catch (SQLException e) {
            Logger log = Logger.getLogger(PrenotazioneDAO.class.getName());
            log.log(Level.SEVERE, e.getMessage(), e);
        }
    }

    public static void insertQuestionario (int key, int questionario){
        String url = "jdbc:postgresql://localhost:5432/VacanzeStudio";
        String user = "postgres";
        String password = "password";
        Prenotazione prenotazione = Prenotazione.getInstance();

        String query = "UPDATE prenotazione SET data = ?, ragazzo = ?, vacanza = ?, college = ?, famiglia = ?, metodopagamento = ?, nomeamico = ?, cognomeamico = ?, mailamico = ?, certificatoacquisito = ?, questionario = ?" +
                "WHERE id =" + key;

        try(Connection con = DriverManager.getConnection(url, user, password)){
            PreparedStatement statement = con.prepareStatement(query);

            statement.setString(1, prenotazione.getDataprenotazione());
            statement.setInt(2, prenotazione.getRagazzo());
            statement.setInt(3, prenotazione.getVacanza());
            statement.setInt(4, prenotazione.getCollege());
            statement.setInt(5, prenotazione.getFamiglia());
            statement.setString(6, prenotazione.getMetodoPagamento());
            statement.setString(7, prenotazione.getNomeAmico());
            statement.setString(8, prenotazione.getCognomeAmico());
            statement.setString(9, prenotazione.getMailAmico());
            statement.setString(10, prenotazione.getCertficatoAcquisito());
            statement.setInt(11,questionario);

            int affectedRows = statement.executeUpdate();

            if(affectedRows > 0){
                System.out.println("Table PRENOTAZIONE successfully updated");
            } else
                System.out.println("Update of PRENOTAZIONE badly failed");
        }
        catch (SQLException e) {
            Logger log = Logger.getLogger(PrenotazioneDAO.class.getName());
            log.log(Level.SEVERE, e.getMessage(), e);
        }
    }

    public static void selectPrenotazione (int key){
        String url = "jdbc:postgresql://localhost:5432/VacanzeStudio";
        String user = "postgres";
        String password = "password";
        Prenotazione prenotazione = Prenotazione.getInstance();

        String query = "SELECT *  FROM prenotazione WHERE id = ?";

        try(Connection con = DriverManager.getConnection(url, user, password)){
            PreparedStatement statement = con.prepareStatement(query);

            statement.setInt(1, key);

            ResultSet result = statement.executeQuery();

            while(result.next()){
                prenotazione.setDataprenotazione(result.getString("data"));
                prenotazione.setRagazzo(result.getInt("ragazzo"));
                prenotazione.setVacanza(result.getInt("vacanza"));
                prenotazione.setCollege(result.getInt("college"));
                prenotazione.setFamiglia(result.getInt("famiglia"));
                prenotazione.setMetodoPagamento(result.getString("metodopagamento"));
                prenotazione.setNomeAmico(result.getString("nomeamico"));
                prenotazione.setCognomeAmico(result.getString("cognomeamico"));
                prenotazione.setMailAmico(result.getString("mailamico"));
                prenotazione.setCertficatoAcquisito(result.getString("certificatoacquisito"));
            }
        }
        catch (SQLException e) {
            Logger log = Logger.getLogger(PrenotazioneDAO.class.getName());
            log.log(Level.SEVERE, e.getMessage(), e);
        }
    }

    public static ObservableList<Certificati> getCertificati(){
        String url = "jdbc:postgresql://localhost:5432/VacanzeStudio";
        String user = "postgres";
        String password = "password";
        Utente utente = Utente.getInstance();
        ObservableList<Certificati> Elenco = FXCollections.observableArrayList();

        String query = "SELECT vacanza, certificatoacquisito  FROM prenotazione WHERE ragazzo = ?";

        try(Connection con = DriverManager.getConnection(url, user, password)){
            PreparedStatement statement = con.prepareStatement(query);

            statement.setInt(1, utente.getId());

            ResultSet result = statement.executeQuery();

            while(result.next()){
                Elenco.add(new Certificati(result.getInt("vacanza"), result.getString("certificatoacquisito")));
            }
        }
        catch (SQLException e) {
            Logger log = Logger.getLogger(PrenotazioneDAO.class.getName());
            log.log(Level.SEVERE, e.getMessage(), e);
        }

        return Elenco;
    }
}
