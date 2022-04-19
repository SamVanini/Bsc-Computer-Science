package dao;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CamereCollegeDAO {

    public static void insertCamereCollege (String tipologia, String college){
        String url = "jdbc:postgresql://localhost:5432/VacanzeStudio";
        String user = "postgres";
        String password = "password";

        String query = "INSERT INTO camere(tipologia, college) VALUES (?, ?)";

        try(Connection con = DriverManager.getConnection(url, user, password)){
            PreparedStatement statement = con.prepareStatement(query);

            statement.setString(1, tipologia);
            statement.setString(2, college);

            int affectedRows = statement.executeUpdate();

            if(affectedRows > 0){
                System.out.println("Table CAMERE successfully updated");
            } else
                System.out.println("Update of CAMERE badly failed");
        }
        catch (SQLException e) {
            Logger log = Logger.getLogger(CamereCollegeDAO.class.getName());
            log.log(Level.SEVERE, e.getMessage(), e);
        }
    }

    public static int getIndexCamera(String tipologia, String college){
        String url = "jdbc:postgresql://localhost:5432/VacanzeStudio";
        String user = "postgres";
        String password = "password";
        int id = -1;

        String query = "SELECT id FROM CAMERE WHERE tipologia = ? AND college = ?";

        try(Connection con = DriverManager.getConnection(url, user, password)) {
            PreparedStatement statement = con.prepareStatement(query);

            statement.setString(1, tipologia);
            statement.setString(2, college);

            ResultSet rs = statement.executeQuery();

            while(rs.next()){
                id = rs.getInt("id");
            }
        }
        catch (SQLException e) {
            Logger log = Logger.getLogger(CamereCollegeDAO.class.getName());
            log.log(Level.SEVERE, e.getMessage(), e);
        }
        return id;
    }
}
