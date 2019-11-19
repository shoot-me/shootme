package cz.vse.java.shootme.server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {

    private static Connection connection;

    public static Connection getConnection() {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection("jdbc:sqlite:database.sqlite");
            } catch (SQLException e) {
                e.printStackTrace();
                System.exit(1);
            }
        }

        return connection;
    }
}
