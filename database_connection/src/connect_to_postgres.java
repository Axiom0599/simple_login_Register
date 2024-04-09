import java.sql.*;

public class connect_to_postgres {
    public static void connection(String textField1, String passwordField1, String textField2) {
        String jdbcURL = "jdbc:postgresql://localhost:5432/registrations";
        String username = "postgres";
        String password = "0000";

        try {
            Connection connection = DriverManager.getConnection(jdbcURL, username, password);
            System.out.println("Connected to PostgreSQL server");

            String sql = "INSERT INTO students VALUES (?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1, textField1);
            statement.setString(2, passwordField1);
            statement.setString(3, textField2);

            int rows = statement.executeUpdate();
            if (rows > 0) {
                System.out.println("A new contact has been inserted");
            }
            connection.close();
        } catch (SQLException e) {
            System.out.println("Error in connecting to PostgreSQL");
            e.printStackTrace();
        }


    }

}
