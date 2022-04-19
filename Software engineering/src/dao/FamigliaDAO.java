package dao;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FamigliaDAO {

    public static void insertFamiglia (String nome, String cognome, String telefono, String paeseresidenza, int animalidomestici, int numerocamere, int numerobagni, int distanzadalcentro){
        String url = "jdbc:postgresql://localhost:5432/VacanzeStudio";
        String user = "postgres";
        String password = "password";

        String query = "INSERT INTO FAMIGLIA(nome, cognome, telefono, mail, paeseresidenza, componenti, animalidomestici, numerocamere, numerobagni, distanzadalcentro) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try(Connection con = DriverManager.getConnection(url, user, password)){
            PreparedStatement statement = con.prepareStatement(query);

            statement.setString(1, nome);
            statement.setString(2, cognome);
            statement.setString(3, telefono);
            statement.setString(4, paeseresidenza);
            statement.setInt(5, animalidomestici);
            statement.setInt(6, numerocamere);
            statement.setInt(7, numerobagni);
            statement.setInt(8, distanzadalcentro);

            int affectedRows = statement.executeUpdate();

            if(affectedRows > 0){
                System.out.println("Table FAMIGLIA successfully updated");
            } else
                System.out.println("Update of FAMIGLIA badly failed");
        }
        catch (SQLException e) {
            Logger log = Logger.getLogger(FamigliaDAO.class.getName());
            log.log(Level.SEVERE, e.getMessage(), e);
        }
    }

    public static int selectFamiglia (String nome) {
        String url = "jdbc:postgresql://localhost:5432/VacanzeStudio";
        String user = "postgres";
        String password = "password";
        int id = -1;

        String query = "SELECT id FROM FAMIGLIA WHERE nome = ?";

        try (Connection con = DriverManager.getConnection(url, user, password)) {
            PreparedStatement statement = con.prepareStatement(query);

            statement.setString(1, nome);


            ResultSet rs = statement.executeQuery();

            while(rs.next()){
                id = rs.getInt("id");
            }
        } catch (SQLException e) {
            Logger log = Logger.getLogger(FamigliaDAO.class.getName());
            log.log(Level.SEVERE, e.getMessage(), e);
        }
         return id;
    }
}
