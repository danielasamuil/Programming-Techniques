package dataAccessLayer;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class establishes the connection between the database and the application
 */
public class ConnectionFactory {

    private static final Logger LOGGER = Logger.getLogger(ConnectionFactory.class.getName());
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DBURL = "jdbc:mysql://localhost:3306/assignment3?useSSL=false";
    private static final String USER = "root";
    private static final String PASS = "Daniella.4ever!";

    private static ConnectionFactory singleInstance = new ConnectionFactory();

    private ConnectionFactory(){
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    /**
     * The method establishes the connection needed in order for the application to work correctly
     *
     * @return it returns the connection to the database
     */
    private Connection createConnection(){
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(DBURL, USER, PASS);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "An error occured while trying to connect to the database");
        }
        return connection;
    }

    public static Connection getConnection() {
        return singleInstance.createConnection();
    };

    /**
     * The method closes the connection
     *
     * @param connection the used connection
     */
    public static void close(Connection connection){
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                LOGGER.log(Level.WARNING, "Error closing the connection");
            }
        }
    }

    /**
     * The method closes the current statement
     *
     * @param statement the used statement
     */
    public static void  close(Statement statement){
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                LOGGER.log(Level.WARNING, "Error closing the statement");
            }
        }
    }

    /**
     * The method closes the current result set
     *
     * @param resultSet the used result set
     */
    public static void close(ResultSet resultSet){
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                LOGGER.log(Level.WARNING, "Error closing the Result Set");
            }
        }
    }
    }

