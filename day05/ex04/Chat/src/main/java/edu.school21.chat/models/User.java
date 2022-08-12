package edu.school21.chat.models;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;

public class User {
    private long id;
    private String login;
    private String password;
    private ArrayList<Chatroom> createRooms;
    private ArrayList<Chatroom> rooms;

    public User(long id, String login, String password, ArrayList<Chatroom> createRooms, ArrayList<Chatroom> rooms) throws SQLException {
        this.id = id;
        if (login == null){
            throw new SQLException();
        }
        this.login = login;
        this.password = password;
        this.createRooms = createRooms;
        this.rooms = rooms;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public long getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public ArrayList<Chatroom> getCreateRooms() {
        return createRooms;
    }

    public ArrayList<Chatroom> getRooms() {
        return rooms;
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        User user = (User)obj;
        return id == user.getId() && login.equals(user.getLogin()) && password.equals(user.getPassword())
                && createRooms.equals(user.getCreateRooms()) && rooms.equals(user.getRooms());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, login, password, createRooms, rooms);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", createdRooms=" + createRooms +
                ", rooms=" + rooms +
                "}";
    }
}