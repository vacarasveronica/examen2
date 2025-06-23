package mpp.persistance.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class JdbcUtils {

    private Properties jdbcProps;
    //private static final Logger logger = LogManager.getLogger();
    private Connection instance = null;

    public JdbcUtils(Properties props) {
        jdbcProps = props;
    }

    private Connection getNewConnection() {
        String driver = jdbcProps.getProperty("jdbc.driver");
        String url = jdbcProps.getProperty("jdbc.url");
        String user = jdbcProps.getProperty("jdbc.user");
        String pass = jdbcProps.getProperty("jdbc.pass");
        Connection con = null;
        try {
            Class.forName(driver);
            con = DriverManager.getConnection(url, user, pass);
        } catch (ClassNotFoundException e) {
            System.out.println("Error loading driver " + e);
        } catch (SQLException e) {
            //logger.error(e);
            System.out.println("Error getting connection " + e);
        }
        System.out.println("baza de date");
        return con;
    }

    public Connection getConnection() {
        try {
            if (instance == null || instance.isClosed()) {
                instance = getNewConnection();
            }
        } catch (SQLException e) {

            System.out.println("Error DB " + e);
        }
        return instance;
    }
}
