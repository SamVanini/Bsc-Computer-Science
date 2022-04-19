package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CollegeDAO {

    public static void insertCollege (String nome, String citta, String indirizzo, String attivita1, String attivita2){
        String url = "jdbc:postgresql://localhost:5432/VacanzeStudio";
        String user = "postgres";
        String password = "password";

        String query = "INSERT INTO COLLEGE(nome, citta, indirizzo, attivita1, attivita2) VALUES (?, ?, ?, ?, ?)";

        try(Connection con = DriverManager.getConnection(url, user, password)){
            PreparedStatement statement = con.prepareStatement(query);

            statement.setString(1, nome);
            statement.setString(2, citta);
            statement.setString(3, indirizzo);
            statement.setString(4, attivita1);
            statement.setString(5, attivita2);

            int affectedRows = statement.executeUpdate();

            if(affectedRows > 0){
                System.out.println("Table COLLEGE successfully updated");
            } else
                System.out.println("Update of COLLEGE badly failed");
        }
        catch (SQLException e) {
            Logger log = Logger.getLogger(CollegeDAO.class.getName());
            log.log(Level.SEVERE, e.getMessage(), e);
        }
    }
}
