package dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Utente;
import model.Vacanze;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.lang.Integer.parseInt;

public class VacanzeDAO {

    public static void insertVacanze (String destinazione, String datapartenza, int numerosettimane, String linguastudiata, String gita1, String gita2, int costo){
        String url = "jdbc:postgresql://localhost:5432/VacanzeStudio";
        String user = "postgres";
        String password = "password";

        String query = "INSERT INTO VACANZE(destinazione, datapartenza, numerosettimane, linguastudiata, gita1, gita2, costo) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try(Connection con = DriverManager.getConnection(url, user, password)){
            PreparedStatement statement = con.prepareStatement(query);

            statement.setString(1, destinazione);
            statement.setString(2, datapartenza);
            statement.setInt(3, numerosettimane);
            statement.setString(4, linguastudiata);
            statement.setString(5, gita1);
            statement.setString(6, gita2);
            statement.setInt(7, costo);

            int affectedRows = statement.executeUpdate();

            if(affectedRows > 0){
                System.out.println("Table VACANZE successfully updated");
            } else
                System.out.println("Update of VACANZE badly failed");
        }
        catch (SQLException e) {
            Logger log = Logger.getLogger(VacanzeDAO.class.getName());
            log.log(Level.SEVERE, e.getMessage(), e);
        }
    }

    public static ObservableList<Vacanze> selectVacanzeDataPartenza(String datapartenza){
        String url = "jdbc:postgresql://localhost:5432/VacanzeStudio";
        String user = "postgres";
        String password = "password";
        ObservableList<Vacanze> Elenco = FXCollections.observableArrayList();

        String query = "SELECT * FROM VACANZE WHERE datapartenza = ?";

        try(Connection con = DriverManager.getConnection(url, user, password)){
            PreparedStatement statement = con.prepareStatement(query);

            statement.setString(1, datapartenza);

            ResultSet result = statement.executeQuery();

            while(result.next()){
                Elenco.add(new Vacanze(result.getString("destinazione"), result.getString("datapartenza"), result.getInt("numerosettimane"),
                        result.getString("linguaStudiata"), result.getString("gita1"), result.getString("gita2"), result.getInt("costo")));
            }
        }
        catch (SQLException e){
            Logger log = Logger.getLogger(VacanzeDAO.class.getName());
            log.log(Level.SEVERE, e.getMessage(), e);
        }
        return Elenco;
    }

    public static ObservableList<Vacanze> selectVacanzeDurata (int numeroSettimane){
        String url = "jdbc:postgresql://localhost:5432/VacanzeStudio";
        String user = "postgres";
        String password = "password";
        ObservableList<Vacanze> Elenco = FXCollections.observableArrayList();

        String query = "SELECT * FROM VACANZE WHERE numerosettimane = ?";

        try(Connection con = DriverManager.getConnection(url, user, password)){
            PreparedStatement statement = con.prepareStatement(query);

            statement.setInt(1, numeroSettimane);

            ResultSet result = statement.executeQuery();

            while(result.next()){
                Elenco.add(new Vacanze(result.getString("destinazione"), result.getString("datapartenza"), result.getInt("numerosettimane"),
                        result.getString("linguaStudiata"), result.getString("gita1"), result.getString("gita2"), result.getInt("costo")));
            }
        }
        catch (SQLException e){
            Logger log = Logger.getLogger(VacanzeDAO.class.getName());
            log.log(Level.SEVERE, e.getMessage(), e);
        }

        return Elenco;
    }

    public static ObservableList<Vacanze> selectVacanzeDestinazione(String destinazione){
        String url = "jdbc:postgresql://localhost:5432/VacanzeStudio";
        String user = "postgres";
        String password = "password";
        ObservableList<Vacanze> Elenco = FXCollections.observableArrayList();

        String query = "SELECT * FROM VACANZE WHERE destinazione = ?";

        try(Connection con = DriverManager.getConnection(url, user, password)){
            PreparedStatement statement = con.prepareStatement(query);

            statement.setString(1, destinazione);

            ResultSet result = statement.executeQuery();

            while(result.next()){
                Elenco.add(new Vacanze(result.getString("destinazione"), result.getString("datapartenza"), result.getInt("numerosettimane"),
                        result.getString("linguaStudiata"), result.getString("gita1"), result.getString("gita2"), result.getInt("costo")));
            }
        }
        catch (SQLException e){
            Logger log = Logger.getLogger(VacanzeDAO.class.getName());
            log.log(Level.SEVERE, e.getMessage(), e);
        }

        return Elenco;
    }

    public static ObservableList<Vacanze> selectVacanzePrenotate(){
        String url = "jdbc:postgresql://localhost:5432/VacanzeStudio";
        String user = "postgres";
        String password = "password";
        ObservableList<Vacanze> Elenco = FXCollections.observableArrayList();
        Utente utente = Utente.getInstance();

        String query = "SELECT VACANZE.* FROM VACANZE, PRENOTAZIONE WHERE vacanze.id = prenotazione.vacanza AND prenotazione.ragazzo = ?";

        try(Connection con = DriverManager.getConnection(url, user, password)){
            PreparedStatement statement = con.prepareStatement(query);

            statement.setInt(1, utente.getId());

            ResultSet result = statement.executeQuery();

            while(result.next()){
                Elenco.add(new Vacanze(result.getString("destinazione"), result.getString("datapartenza"), result.getInt("numerosettimane"),
                        result.getString("linguaStudiata"), result.getString("gita1"), result.getString("gita2"), result.getInt("costo")));
            }
        }
        catch (SQLException e){
            Logger log = Logger.getLogger(VacanzeDAO.class.getName());
            log.log(Level.SEVERE, e.getMessage(), e);
        }

        return Elenco;
    }

    public static int getIdVacanza(String destinazione, String dataPartenza, int numeroSettimane, String lingua){
        String url = "jdbc:postgresql://localhost:5432/VacanzeStudio";
        String user = "postgres";
        String password = "password";
        int id = -1;
        String query = "SELECT id FROM VACANZE WHERE destinazione = ? AND datapartenza = ? AND numerosettimane = ? AND linguastudiata = ?";

        try(Connection con = DriverManager.getConnection(url, user, password)){
            PreparedStatement statement = con.prepareStatement(query);

            statement.setString(1, destinazione);
            statement.setString(2, dataPartenza);
            statement.setInt(3, numeroSettimane);
            statement.setString(4, lingua);

            ResultSet result = statement.executeQuery();

            while(result.next()){
                id = result.getInt("id");
                System.out.println(result.getInt("id"));
            }
        }
        catch (SQLException e){
            Logger log = Logger.getLogger(VacanzeDAO.class.getName());
            log.log(Level.SEVERE, e.getMessage(), e);
        }
        return id;
    }
}
