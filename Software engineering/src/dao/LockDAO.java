package dao;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LockDAO {

    public static void lock (String table){
        String url = "jdbc:postgresql://localhost:5432/VacanzeStudio";
        String user = "postgres";
        String password = "password";

        String query = "UPDATE LOCK SET modifica = ?" +
                "WHERE tabella = ?";

        try(Connection con = DriverManager.getConnection(url, user, password)){
            PreparedStatement statement = con.prepareStatement(query);

            statement.setInt(1, 1);
            statement.setString(2, table);

            int affectedRows = statement.executeUpdate();

            if(affectedRows > 0){
                System.out.println("LOCKED");
            } else
                System.out.println("Update of LOCK badly failed");
        }
        catch (SQLException e) {
            Logger log = Logger.getLogger(LockDAO.class.getName());
            log.log(Level.SEVERE, e.getMessage(), e);
        }
    }

    public static void unlock (String table){
        String url = "jdbc:postgresql://localhost:5432/VacanzeStudio";
        String user = "postgres";
        String password = "password";

        String query = "UPDATE LOCK SET modifica = ?" +
                "WHERE tabella = ?";

        try(Connection con = DriverManager.getConnection(url, user, password)){
            PreparedStatement statement = con.prepareStatement(query);

            statement.setInt(1, 0);
            statement.setString(2, table);

            int affectedRows = statement.executeUpdate();

            if(affectedRows > 0){
                System.out.println("UNLOCKED");
            } else
                System.out.println("Update of LOCK badly failed");
        }
        catch (SQLException e) {
            Logger log = Logger.getLogger(LockDAO.class.getName());
            log.log(Level.SEVERE, e.getMessage(), e);
        }
    }

    public static int isLocked (String table){
        String url = "jdbc:postgresql://localhost:5432/VacanzeStudio";
        String user = "postgres";
        String password = "password";
        int lock = -1;

        String query = "SELECT modifica FROM LOCK " +
                "WHERE tabella = ?";

        try(Connection con = DriverManager.getConnection(url, user, password)){
            PreparedStatement statement = con.prepareStatement(query);

            statement.setString(1, table);

            ResultSet result = statement.executeQuery();

            while(result.next()){
                lock = result.getInt("modifica");
            }
        }
        catch (SQLException e) {
            Logger log = Logger.getLogger(LockDAO.class.getName());
            log.log(Level.SEVERE, e.getMessage(), e);
        }

        return lock;
    }
}
