package dao;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AllergieDAO {

    public static void insertAllergie (String nome, String trattamento){
        String url = "jdbc:postgresql://localhost:5432/VacanzeStudio";
        String user = "postgres";
        String password = "password";

        String query = "INSERT INTO ALLERGIE(nome, trattamento) VALUES (?, ?)";

        try(Connection con = DriverManager.getConnection(url, user, password)){
            PreparedStatement statement = con.prepareStatement(query);

            statement.setString(1, nome);
            statement.setString(2, trattamento);

            int affectedRows = statement.executeUpdate();

            if(affectedRows > 0){
                System.out.println("Table ALLERGIA successfully updated");
            } else
                System.out.println("Update of ALLERGIA badly failed");
        }
        catch (SQLException e) {
            Logger log = Logger.getLogger(AllergieDAO.class.getName());
            log.log(Level.SEVERE, e.getMessage(), e);
        }
    }

    public static String selectALLERGIE (String nome){
        String url = "jdbc:postgresql://localhost:5432/VacanzeStudio";
        String user = "postgres";
        String password = "password";
        String trattamento = "";

        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            Logger log = Logger.getLogger(AllergieDAO.class.getName());
            log.log(Level.SEVERE, e.getMessage(), e);
        }

        String query = "SELECT ALLERGIE.trattamento FROM ALLERGIE WHERE nome = ?";

        try(Connection con = DriverManager.getConnection(url, user, password)){
            PreparedStatement statement = con.prepareStatement(query);

            statement.setString(1, nome);

            ResultSet result = statement.executeQuery();

            while(result.next()){
                trattamento = trattamento + result.getString("trattamento");
                return trattamento;
            }

        }
        catch (SQLException e){
            Logger log = Logger.getLogger(GenitoriDAO.class.getName());
            log.log(Level.SEVERE, e.getMessage(), e);
        }

        return trattamento;
    }
}
