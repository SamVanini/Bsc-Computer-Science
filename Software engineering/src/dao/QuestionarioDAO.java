package dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Questionario;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class QuestionarioDAO {

    public static void insertQuestionario (int voto, String commento){
        String url = "jdbc:postgresql://localhost:5432/VacanzeStudio";
        String user = "postgres";
        String password = "password";

        String query = "INSERT INTO QUESTIONARIO(voto, commento) VALUES (?, ?)";

        try(Connection con = DriverManager.getConnection(url, user, password)){
            PreparedStatement statement = con.prepareStatement(query);

            statement.setInt(1, voto);
            statement.setString(2, commento);

            int affectedRows = statement.executeUpdate();

            if(affectedRows > 0){
                System.out.println("Table QUESTIONARIO successfully updated");
            } else
                System.out.println("Update of QUESTIONARIO badly failed");
        }
        catch (SQLException e) {
            Logger log = Logger.getLogger(QuestionarioDAO.class.getName());
            log.log(Level.SEVERE, e.getMessage(), e);
        }
    }

    public static ObservableList<Questionario> avgQuestionario(){
        String url = "jdbc:postgresql://localhost:5432/VacanzeStudio";
        String user = "postgres";
        String password = "password";
        ObservableList<Questionario> Elenco = FXCollections.observableArrayList();

        String query = "SELECT PRENOTAZIONE.vacanza, AVG(voto) AS media FROM QUESTIONARIO, PRENOTAZIONE WHERE QUESTIONARIO.id = PRENOTAZIONE.questionario GROUP BY PRENOTAZIONE.vacanza";

        try(Connection con = DriverManager.getConnection(url, user, password)){

            ResultSet result = con.createStatement().executeQuery(query);

            while(result.next()){
                Elenco.add(new Questionario(result.getInt("vacanza"), result.getInt("media")));
            }
        }
        catch (SQLException e){
            Logger log = Logger.getLogger(VacanzeDAO.class.getName());
            log.log(Level.SEVERE, e.getMessage(), e);
        }
        return Elenco;
    }

    public static ObservableList<Questionario> selectQuestionario(){
        String url = "jdbc:postgresql://localhost:5432/VacanzeStudio";
        String user = "postgres";
        String password = "password";
        ObservableList<Questionario> Elenco = FXCollections.observableArrayList();

        String query = "SELECT QUESTIONARIO.*, PRENOTAZIONE.vacanza FROM QUESTIONARIO, PRENOTAZIONE WHERE QUESTIONARIO.id = PRENOTAZIONE.questionario";

        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            Logger log = Logger.getLogger(GiteDAO.class.getName());
            log.log(Level.SEVERE, e.getMessage(), e);
        }

        try(Connection con = DriverManager.getConnection(url, user, password)){
            ResultSet rs = con.createStatement().executeQuery(query);

            while(rs.next()){
                Elenco.add(new Questionario(rs.getInt("vacanza"), rs.getInt("voto"), rs.getString("Commento")));
            }
        }
        catch (SQLException e){
            Logger log = Logger.getLogger(VacanzeDAO.class.getName());
            log.log(Level.SEVERE, e.getMessage(), e);
        }
        return Elenco;
    }

    public static int selectID(int voto, String commento){
        String url = "jdbc:postgresql://localhost:5432/VacanzeStudio";
        String user = "postgres";
        String password = "password";
        int id = -1;
        String query = "SELECT * FROM QUESTIONARIO WHERE voto = ? AND commento = ?";

        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            Logger log = Logger.getLogger(GiteDAO.class.getName());
            log.log(Level.SEVERE, e.getMessage(), e);
        }

        try(Connection con = DriverManager.getConnection(url, user, password)){
            PreparedStatement statement = con.prepareStatement(query);

            statement.setInt(1, voto);
            statement.setString(2, commento);

            ResultSet result = statement.executeQuery();

            while(result.next()){
                id = result.getInt("id");
            }
        }
        catch (SQLException e){
            Logger log = Logger.getLogger(VacanzeDAO.class.getName());
            log.log(Level.SEVERE, e.getMessage(), e);
        }
        return id;
    }
}
