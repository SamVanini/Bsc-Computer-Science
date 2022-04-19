package dao;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CredenzialiDAO {

    public static void insertCredenziali(String mail, String passw){
        String url = "jdbc:postgresql://localhost:5432/VacanzeStudio";
        String user = "postgres";
        String password = "password";

        String query = "INSERT INTO CREDENZIALI(mail, password) VALUES (?, ?)";

        try(Connection con = DriverManager.getConnection(url, user, password)){
            PreparedStatement statement = con.prepareStatement(query);


            statement.setString(1, mail);
            statement.setString(2, passw);

            int affectedRows = statement.executeUpdate();

            if(affectedRows > 0){
                System.out.println("Table CREDENZIALI successfully updated");
            } else
                System.out.println("Update of CREDENZIALI badly failed");
        }
        catch (SQLException e) {
            Logger log = Logger.getLogger(CredenzialiDAO.class.getName());
            log.log(Level.SEVERE, e.getMessage(), e);
        }

    }

    public static int checkCredenziali(String mail, String passw){
        String url = "jdbc:postgresql://localhost:5432/VacanzeStudio";
        String user = "postgres";
        String password = "password";

        String query = "SELECT * FROM credenziali WHERE mail = ? AND password = ?";

        try(Connection con = DriverManager.getConnection(url, user, password)){
            PreparedStatement statement = con.prepareStatement(query);


            statement.setString(1, mail);
            statement.setString(2, passw);

            ResultSet result = statement.executeQuery();

            while(result.next()){
                return 1;
            }
        }
        catch (SQLException e) {
            Logger log = Logger.getLogger(CredenzialiDAO.class.getName());
            log.log(Level.SEVERE, e.getMessage(), e);
        }

        return 0;
    }

    public static void updateCredenziali(String mail, String pword, String AlterMail){
        String url = "jdbc:postgresql://localhost:5432/VacanzeStudio";
        String user = "postgres";
        String password = "password";

        String query = "UPDATE CREDENZIALI SET mail = ?, password = ? WHERE mail = ?";

        try(Connection con = DriverManager.getConnection(url, user, password)){
            PreparedStatement statement = con.prepareStatement(query);


            statement.setString(1, mail);
            statement.setString(2, pword);
            statement.setString(3, AlterMail);

            int affectedRows = statement.executeUpdate();

            if(affectedRows > 0){
                System.out.println("Table CREDENZIALI successfully updated");
            } else
                System.out.println("Update of CREDENZIALI badly failed");
        }
        catch (SQLException e) {
            Logger log = Logger.getLogger(CredenzialiDAO.class.getName());
            log.log(Level.SEVERE, e.getMessage(), e);
        }
    }
}
