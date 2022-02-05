package dbaccess;

import dbconnection.DBConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginValidator {
    public static boolean validateLogin(String username,String password) {
        boolean isLoginValid = false;
        try{
            String query = "SELECT * from users";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String myUser = rs.getString("User_Name");
                String myPassword = rs.getString("Password");
                if (username.equals(myUser) && password.equals(myPassword)) {
                    isLoginValid = true;
                    break;
                }
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return isLoginValid;
    }
}

