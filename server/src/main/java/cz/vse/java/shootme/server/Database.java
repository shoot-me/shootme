package cz.vse.java.shootme.server;

import cz.vse.java.shootme.server.models.Skin;
import cz.vse.java.shootme.server.net.requests.Request;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

public class Database {

    private static final Logger logger = LogManager.getLogger(Database.class);

    private static EntityManagerFactory EMF;

    public static void init() {
        logger.info("Initializing database");

        EMF = Persistence.createEntityManagerFactory("punit");
    }

    public static void migrate() {
        logger.info("Migrating database");

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


    public static boolean transaction(Consumer<EntityManager> callback) {
        EntityManager entityManager = getEntityManager();

        try {
            entityManager.getTransaction().begin();

            callback.accept(entityManager);

            entityManager.getTransaction().commit();

            return true;
        } catch (Exception e1) {
            try {
                entityManager.getTransaction().rollback();
            } catch (Exception e2) {
                logger.error("Transaction rollback failed", e2);
            }
            logger.error("Transaction failed", e1);

            return false;
        } finally {
            entityManager.close();
        }
    }

    public static boolean transaction(Request request, Consumer<EntityManager> callback) {
        EntityManager entityManager = getEntityManager();

        try {
            entityManager.getTransaction().begin();

            callback.accept(entityManager);

            entityManager.getTransaction().commit();

            return true;
        } catch (Exception e1) {
            try {
                entityManager.getTransaction().rollback();
            } catch (Exception e2) {
                request.respondError(e2.getMessage());
            }
            request.respondError(e1.getMessage());

            return false;
        } finally {
            entityManager.close();
        }
    }

    public static <T> Optional<T> transaction(Request request, Function<EntityManager, T> callback) {
        EntityManager entityManager = getEntityManager();
        T response;

        try {
            entityManager.getTransaction().begin();

            response = callback.apply(entityManager);

            entityManager.getTransaction().commit();

            return Optional.ofNullable(response);
        } catch (Exception e1) {
            try {
                entityManager.getTransaction().rollback();
            } catch (Exception e2) {
                request.respondError(e2.getMessage());
            }
            request.respondError(e1.getMessage());

            return Optional.empty();
        } finally {
            entityManager.close();
        }
    }
}
