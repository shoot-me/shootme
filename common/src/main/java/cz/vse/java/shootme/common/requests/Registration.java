package cz.vse.java.shootme.common.requests;

public class Registration extends Request {

    public final String name;

    public final String password;

    public Registration(String name, String password) {
        this.name = name;
        this.password = password;
    }

    @Override
    public String toString() {
        return "Register{" +
                "name='" + name + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

}
