package dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GiteDAO {

    public ComboBox comboBox;

    public static void insertGite (String destinazione, int costo, int numeroore, String descrizione){
        String url = "jdbc:postgresql://localhost:5432/VacanzeStudio";
        String user = "postgres";
        String password = "password";

        String query = "INSERT INTO GITE(destinazione, costo, numeroore, descrizione) VALUES (?, ?, ?, ?)";

        try(Connection con = DriverManager.getConnection(url, user, password)){
            PreparedStatement statement = con.prepareStatement(query);

            statement.setString(1, destinazione);
            statement.setInt(2, costo);
            statement.setInt(3, numeroore);
            statement.setString(4, descrizione);

            int affectedRows = statement.executeUpdate();

            if(affectedRows > 0){
                System.out.println("Table GITE successfully updated");
            } else
                System.out.println("Update of GITE badly failed");
        }
        catch (SQLException e) {
            Logger log = Logger.getLogger(AllergieDAO.class.getName());
            log.log(Level.SEVERE, e.getMessage(), e);
        }
    }

    /*public static void selectGITE (String destinazione){
        String url = "jdbc:postgresql://localhost:5432/VacanzeStudio";
        String user = "postgres";
        String password = "password";

        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            Logger log = Logger.getLogger(AllergieDAO.class.getName());
            log.log(Level.SEVERE, e.getMessage(), e);
        }

        String query = "SELECT * FROM GITE";

        try(Connection con = DriverManager.getConnection(url, user, password)){


            ResultSet result = con.createStatement().executeQuery(query);

            while(result.next()){

            }

        }
        catch (SQLException e){
            Logger log = Logger.getLogger(GenitoriDAO.class.getName());
            log.log(Level.SEVERE, e.getMessage(), e);
        }

    }

    public void setComboBox() {
        String url = "jdbc:postgresql://localhost:5432/VacanzeStudio";
        String user = "postgres";
        String password = "password";

        String query = "SELECT * FROM GITE";

        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            Logger log = Logger.getLogger(GiteDAO.class.getName());
            log.log(Level.SEVERE, e.getMessage(), e);
        }

        try(Connection con = DriverManager.getConnection(url, user, password)){

            ResultSet rs = con.createStatement().executeQuery(query);
            ObservableList data = FXCollections.observableArrayList();

            while(rs.next()){
                data.add(new String(rs.getString("destinazione")));
            }
            comboBox.setItems(data);
        }
        catch (SQLException e){
            Logger log = Logger.getLogger(GenitoriDAO.class.getName());
            log.log(Level.SEVERE, e.getMessage(), e);
        }
    }*/
}
