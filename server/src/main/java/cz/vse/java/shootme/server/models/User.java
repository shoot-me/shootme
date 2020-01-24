package cz.vse.java.shootme.server.models;

import cz.vse.java.shootme.server.Database;
import cz.vse.java.shootme.server.game.entities.Player;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "token")
    private String token;

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public UserStatistics getStatistics() {
        EntityManager em = Database.getEntityManager();

        try {

            Query query = em.createQuery("from UserStatistics u where u.userId = " + id);
            UserStatistics userStatistics = (UserStatistics) query.getSingleResult();

            return userStatistics;
        } catch (Exception e) {
            e.printStackTrace();
        }

        em.close();
        return null;

    }
}
