package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AttivitaDAO {

    public static void insertAttivita (String nome, String descrizione){
        String url = "jdbc:postgresql://localhost:5432/VacanzeStudio";
        String user = "postgres";
        String password = "password";

        String query = "INSERT INTO ATTIVITA(nome, descrizione) VALUES (?, ?)";

        try(Connection con = DriverManager.getConnection(url, user, password)){
            PreparedStatement statement = con.prepareStatement(query);

            statement.setString(1, nome);
            statement.setString(2, descrizione);

            int affectedRows = statement.executeUpdate();

            if(affectedRows > 0){
                System.out.println("Table ATTIVITA successfully updated");
            } else
                System.out.println("Update of ATTIVITA badly failed");
        }
        catch (SQLException e) {
            Logger log = Logger.getLogger(AttivitaDAO.class.getName());
            log.log(Level.SEVERE, e.getMessage(), e);
        }
    }
}
