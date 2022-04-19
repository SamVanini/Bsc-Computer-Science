package dao;

import model.Utente;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RagazziDAO {

    public static void insertRAGAZZI(String nome, String cognome, int eta, String via, String numerocivico, String paese, String telefono, String mail, String hobby, String allergia1, String allergia2, int genitore){
        String url = "jdbc:postgresql://localhost:5432/VacanzeStudio";
        String user = "postgres";
        String password = "password";

        String query = "INSERT INTO RAGAZZI(nome, cognome, eta, via, numerocivico, paese, telefono, mail, hobby, allergia1, allergia2, genitore) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try(Connection con = DriverManager.getConnection(url, user, password)){
            PreparedStatement statement = con.prepareStatement(query);

            statement.setString(1, nome);
            statement.setString(2, cognome);
            statement.setInt(3, eta);
            statement.setString(4, via);
            statement.setString(5, numerocivico);
            statement.setString(6, paese);
            statement.setString(7, telefono);
            statement.setString(8, mail);
            statement.setString(9, hobby);
            statement.setString(10, allergia1);
            statement.setString(11, allergia2);
            statement.setInt(12, genitore);

            int affectedRows = statement.executeUpdate();

            if(affectedRows > 0){
                System.out.println("Table RAGAZZI successfully updated");
            } else
                System.out.println("Update of RAGAZZI badly failed");
        }
        catch (SQLException e) {
            Logger log = Logger.getLogger(RagazziDAO.class.getName());
            log.log(Level.SEVERE, e.getMessage(), e);
        }
    }

    public static void updateRagazzi (int key, String nome, String cognome, int eta, String via, String numerocivico, String paese, String telefono, String mail, String hobby, String allergia1, String allergia2, int genitore){
        String url = "jdbc:postgresql://localhost:5432/VacanzeStudio";
        String user = "postgres";
        String password = "password";

        String query = "UPDATE RAGAZZI SET nome = ?, cognome = ?, eta = ?, via = ?, numerocivico = ?, paese = ?, telefono = ?, mail = ?, hobby = ?, allergia1 = ?, allergia2 = ?, genitore = ?" +
                "WHERE id =" + key;

        try(Connection con = DriverManager.getConnection(url, user, password)){
            PreparedStatement statement = con.prepareStatement(query);

            statement.setString(1, nome);
            statement.setString(2, cognome);
            statement.setInt(3, eta);
            statement.setString(4, via);
            statement.setString(5, numerocivico);
            statement.setString(6, paese);
            statement.setString(7, telefono);
            statement.setString(8, mail);
            statement.setString(9, hobby);
            statement.setString(10, allergia1);
            statement.setString(11, allergia2);
            statement.setInt(12, genitore);

            int affectedRows = statement.executeUpdate();

            if(affectedRows > 0){
                System.out.println("Table RAGAZZI successfully updated");
            } else
                System.out.println("Update of RAGAZZI badly failed");
        }
        catch (SQLException e) {
            Logger log = Logger.getLogger(RagazziDAO.class.getName());
            log.log(Level.SEVERE, e.getMessage(), e);
        }
    }

    public static int selectRagazzi (String nome, String cognome, int eta, String via, String numerocivico, String paese, String telefono, String mail, String hobby, String allergia1, String allergia2, int genitore){
        String url = "jdbc:postgresql://localhost:5432/VacanzeStudio";
        String user = "postgres";
        String password = "password";
        int result = 0;

        String query = "SELECT id FROM RAGAZZI WHERE nome = ? AND cognome = ? AND eta = ? AND via = ? AND numerocivico = ? AND paese = ? AND telefono = ? AND mail = ? AND hobby = ? AND allergia1 = ? AND allergia2 = ? AND genitore = ?";

        try(Connection con = DriverManager.getConnection(url, user, password)){
            PreparedStatement statement = con.prepareStatement(query);

            statement.setString(1, nome);
            statement.setString(2, cognome);
            statement.setInt(3, eta);
            statement.setString(4, via);
            statement.setString(5, numerocivico);
            statement.setString(6, paese);
            statement.setString(7, telefono);
            statement.setString(8, mail);
            statement.setString(9, hobby);
            statement.setString(10, allergia1);
            statement.setString(11, allergia2);
            statement.setInt(12, genitore);

            ResultSet rs = statement.executeQuery();
            while(rs.next()) {
                result = rs.getInt("id");
                return result;
            }
        }
        catch (SQLException e) {
            Logger log = Logger.getLogger(RagazziDAO.class.getName());
            log.log(Level.SEVERE, e.getMessage(), e);
        }
        return -1;
    }

    public static void setUtente (String mail){
        String url = "jdbc:postgresql://localhost:5432/VacanzeStudio";
        String user = "postgres";
        String password = "password";
        Utente utente = Utente.getInstance();

        String query = "SELECT * FROM RAGAZZI WHERE mail = ?";

        try(Connection con = DriverManager.getConnection(url, user, password)){
            PreparedStatement statement = con.prepareStatement(query);

            statement.setString(1, mail);

            ResultSet rs = statement.executeQuery();
            while(rs.next()){
                utente.setId(rs.getInt("id"));
                utente.setNome(rs.getString("nome"));
                utente.setCognome(rs.getString("cognome"));
                utente.setEta(rs.getInt("eta"));
                utente.setVia(rs.getString("via"));
                utente.setPaese(rs.getString("paese"));
                utente.setNumeroCivico(rs.getString("numerocivico"));
                utente.setTelefono(rs.getString("telefono"));
                utente.setMail(rs.getString("mail"));
                utente.setHobby(rs.getString("hobby"));
                utente.setAllergia1(rs.getString("allergia1"));
                utente.setAllergia2(rs.getString("allergia2"));
                utente.setGenitore(rs.getInt("genitore"));
            }
        }
        catch (SQLException e) {
            Logger log = Logger.getLogger(RagazziDAO.class.getName());
            log.log(Level.SEVERE, e.getMessage(), e);
        }
    }
}
