package db;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
public class DBConnection {
    //final so that values cannot be changed later
    private static final String url = "jdbc:postgresql://localhost:5432/expensetracker";
    private static final String username = "irishprajapati";
    private static final String password = "4696";
    public static Connection getConnection() throws SQLException{
        return DriverManager.getConnection(url, username, password);
    }
}
