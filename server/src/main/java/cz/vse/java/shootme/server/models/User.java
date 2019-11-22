package cz.vse.java.shootme.server.models;

import jeda00.db.Query;

public class User extends Model<Integer> {

    public String getUsername() {
        return getString("username");
    }

    public void setUsername(String username) {
        setString("username", username);
    }

    public String getPassword() {
        return getString("password");
    }

    public void setPassword(String password) {
        setString("password", password);
    }

    public String getToken() {
        return getString("token");
    }

    public void setToken(String token) {
        setString("token", token);
    }

    public static Query<User> query() {
        return new Query<>(User.class);
    }
}
