package cz.vse.java.shootme.server;

import cz.vse.java.shootme.server.models.Skin;
import cz.vse.java.shootme.server.net.requests.Request;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.function.Consumer;

public class Database {

    private static EntityManagerFactory EMF;

    public static void init() {
        EMF = Persistence.createEntityManagerFactory("punit");
    }

    public static void migrate() {
        EntityManager em = getEntityManager();

        em.getTransaction().begin();

        Skin orange = new Skin();
        orange.setName("Orange knight");
        orange.setPath("img/players/knight_orange.png");
        em.persist(orange);

        Skin pink = new Skin();
        pink.setName("Pink knight");
        pink.setPath("img/players/knight_pink.png");
        em.persist(pink);

        em.getTransaction().commit();

        em.close();
    }

    public synchronized static EntityManager getEntityManager() {
        return EMF.createEntityManager();
    }


    public static void transaction(Consumer<EntityManager> callback) {
        EntityManager entityManager = getEntityManager();

        try {
            entityManager.getTransaction().begin();

            callback.accept(entityManager);

            entityManager.getTransaction().commit();
        } catch (Exception e1) {
            try {
                entityManager.getTransaction().rollback();
            } catch (Exception e2) {
                System.err.println(e2.getMessage());
            }
            System.err.println(e1.getMessage());
        } finally {
            entityManager.close();
        }
    }

    public static void transaction(Request request, Consumer<EntityManager> callback) {
        EntityManager entityManager = getEntityManager();

        try {
            entityManager.getTransaction().begin();

            callback.accept(entityManager);

            entityManager.getTransaction().commit();
        } catch (Exception e1) {
            try {
                entityManager.getTransaction().rollback();
            } catch (Exception e2) {
                request.respondError(e2.getMessage());
            }
            request.respondError(e1.getMessage());
        } finally {
            entityManager.close();
        }
    }
}
