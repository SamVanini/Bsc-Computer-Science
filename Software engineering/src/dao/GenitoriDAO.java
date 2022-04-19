package dao;

import model.Genitori;
import model.Utente;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GenitoriDAO {

    public static void insertGenitori (String nome, String cognome, String telefono, String mail){
        String url = "jdbc:postgresql://localhost:5432/VacanzeStudio";
        String user = "postgres";
        String password = "password";

        String query = "INSERT INTO GENITORI(nome, cognome, telefono, mail) VALUES (?, ?, ?, ?)";

        try(Connection con = DriverManager.getConnection(url, user, password)){
            PreparedStatement statement = con.prepareStatement(query);

            statement.setString(1, nome);
            statement.setString(2, cognome);
            statement.setString(3, telefono);
            statement.setString(4, mail);

            int affectedRows = statement.executeUpdate();

            if(affectedRows > 0){
                System.out.println("Table Genitori successfully updated");
            } else
                System.out.println("Update of GENITORI badly failed");
        }
        catch (SQLException e) {
            Logger log = Logger.getLogger(GenitoriDAO.class.getName());
            log.log(Level.SEVERE, e.getMessage(), e);
        }
    }

    public static int selectGenitori(String nome, String cognome, String telefono, String mail){
        String url = "jdbc:postgresql://localhost:5432/VacanzeStudio";
        String user = "postgres";
        String password = "password";

        String query = "SELECT GENITORI.id FROM GENITORI WHERE nome = ? AND cognome = ? AND telefono = ? AND mail = ?";

        try(Connection con = DriverManager.getConnection(url, user, password)){
            PreparedStatement statement = con.prepareStatement(query);

            statement.setString(1, nome);
            statement.setString(2, cognome);
            statement.setString(3, telefono);
            statement.setString(4, mail);

            ResultSet result = statement.executeQuery();

            while(result.next()){
                int id = result.getInt(1);
                return id;
            }
        }
        catch (SQLException e){
            Logger log = Logger.getLogger(GenitoriDAO.class.getName());
            log.log(Level.SEVERE, e.getMessage(), e);
        }

        return -1;
    }

    public static void updateGenitori (int key, String nome, String cognome, String telefono, String mail){
        String url = "jdbc:postgresql://localhost:5432/VacanzeStudio";
        String user = "postgres";
        String password = "password";

        String query = "UPDATE GENITORI SET nome = ?, cognome = ?, telefono = ?, mail = ?" +
                "WHERE id =" + key;

        try(Connection con = DriverManager.getConnection(url, user, password)){
            PreparedStatement statement = con.prepareStatement(query);

            statement.setString(1, nome);
            statement.setString(2, cognome);
            statement.setString(3, telefono);
            statement.setString(4, mail);

            int affectedRows = statement.executeUpdate();

            if(affectedRows > 0){
                System.out.println("Table GENITORI successfully updated");
            } else
                System.out.println("Update of GENITORI badly failed");
        }
        catch (SQLException e) {
            Logger log = Logger.getLogger(GenitoriDAO.class.getName());
            log.log(Level.SEVERE, e.getMessage(), e);
        }
    }

    public static void setGenitore (){
        String url = "jdbc:postgresql://localhost:5432/VacanzeStudio";
        String user = "postgres";
        String password = "password";
        Utente utente = Utente.getInstance();
        Genitori genitore = Genitori.getInstance();

        String query = "SELECT GENITORI.* FROM GENITORI WHERE id = ?";


        try(Connection con = DriverManager.getConnection(url, user, password)){
            PreparedStatement statement = con.prepareStatement(query);

            statement.setInt(1, utente.getGenitore());

            ResultSet rs = statement.executeQuery();
            while(rs.next()){
                genitore.setId(rs.getInt("id"));
                genitore.setNome(rs.getString("nome"));
                genitore.setCognome(rs.getString("cognome"));
                genitore.setMail(rs.getString("mail"));
                genitore.setTelefono(rs.getString("telefono"));
            }
        }
        catch (SQLException e) {
            Logger log = Logger.getLogger(GenitoriDAO.class.getName());
            log.log(Level.SEVERE, e.getMessage(), e);
        }
    }
}
