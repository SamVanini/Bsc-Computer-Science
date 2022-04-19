package dao;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ResponsabiliDAO {

    public static int checkCredenziali(String login, String passw){
        String url = "jdbc:postgresql://localhost:5432/VacanzeStudio";
        String user = "postgres";
        String password = "password";

        String query = "SELECT * FROM responsabili_agenzia WHERE login = ? AND password = ?";

        try(Connection con = DriverManager.getConnection(url, user, password)){
            PreparedStatement statement = con.prepareStatement(query);


            statement.setString(1, login);
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
}
