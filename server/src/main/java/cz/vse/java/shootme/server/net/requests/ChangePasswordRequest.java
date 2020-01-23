package cz.vse.java.shootme.server.net.requests;

public class ChangePasswordRequest extends Request {

    public final String oldPassword;

    public final String newPassword;

    public ChangePasswordRequest(String oldPassword, String newPassword) {
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
    }
}
