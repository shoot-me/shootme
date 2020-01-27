package cz.vse.java.shootme.server;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {

    private static Connection connection;

    private static EntityManagerFactory EMF;

    public void createEntityManagerFactory() {
        EMF = Persistence.createEntityManagerFactory("punit");
    }

    public synchronized static EntityManager getEntityManager() {
        EntityManager em = EMF.createEntityManager();
        return em;
    }

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
