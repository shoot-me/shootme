package cz.vse.java.shootme.server.models;

import jeda00.db.Query;

public class User extends Model<Integer> {

    public String getName() {
        return getString("name");
    }

    public void setName(String name) {
        setString("name", name);
    }

    public String getPassword() {
        return getString("password");
    }

    public void setPassword(String password) {
        setString("password", password);
    }

    public static Query<User> query() {
        return new Query<>(User.class);
    }
}
