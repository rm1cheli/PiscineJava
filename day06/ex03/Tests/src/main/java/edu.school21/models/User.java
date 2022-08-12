package edu.school21.models;

import sun.security.util.Password;

import java.util.Objects;

public class User {
    private Long id;
    private String login;
    private String password;
    private boolean success;

    public User(Long id, String login, String password, boolean success) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.success = success;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        User user = (User) obj;
        return id == user.getId() &&
                login.equals(user.getLogin()) &&
                password.equals(user.getPassword()) &&
                success == user.success;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, password, login, success);
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", password=" + password +
                ", login=" + login +
                ", success=" + success +
                "}";
    }
}