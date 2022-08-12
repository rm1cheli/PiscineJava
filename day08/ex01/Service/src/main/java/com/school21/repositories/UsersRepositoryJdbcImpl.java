package com.school21.repositories;

import com.school21.models.User;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UsersRepositoryJdbcImpl implements UsersRepository{
    private Connection connection;

    private final String FIND_BY_ID = "SELECT * FROM chat.user WHERE id=?";
    private final String SAVE = "INSERT INTO chat.user (email) VALUES (?)";
    private final String UPDATE = "UPDATE chat.user SET " + "email = ?, " + "WHERE id = ?";
    private final String DELETE= "DELETE FROM chat.user WHERE id=?";
    private final String FIND_BY_EMAIL = "SELECT * FROM chat.user WHERE email=?";
    private final String FIND_BY_ALL = "SELECT * FROM chat.user";

    public UsersRepositoryJdbcImpl(DataSource dataSource) {
        try {
            this.connection = dataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<User> findById(Long id){
        User user = null;
        try {
            PreparedStatement statement = connection.prepareStatement(FIND_BY_ID);
            statement.setLong(1, id);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                user = new User(result.getLong("id"), result.getString("email"));
            }
        } catch (SQLException e) {
            System.err.println("Error findById");
        }
        return Optional.ofNullable(user);
    }

    @Override
    public List<User> findAll() {
        ResultSet rs = null;
        List<User> users = new ArrayList<>();
        try {
            PreparedStatement ps = connection.prepareStatement(FIND_BY_ALL);
            rs = ps.executeQuery();
            while(rs.next()){
                users.add( new User(rs.getLong("id"), rs.getString("email")));
            }
        } catch (SQLException e) {
            System.err.println("Error findAll");
        }
        return users;
    }

    @Override
    public void save(User entity) {
        ResultSet resultSet = null;
        try {
            PreparedStatement query = connection.prepareStatement(SAVE);
            query.setString(1, entity.getEmail());
            query.execute();
        } catch (SQLException e) {
            System.err.println("Error save");
        }
    }

    @Override
    public void update(User entity) {
        try {
            PreparedStatement query = connection.prepareStatement(UPDATE);
            query.setString(1, entity.getEmail());
            query.setLong(2, entity.getId());
            query.execute();
        } catch (SQLException e) {
            System.err.println("Error update");
        }
    }

    @Override
    public void delete(Long id) {
        try {
            if (findById(id).isPresent()) {
                PreparedStatement statement = connection.prepareStatement(DELETE);
                statement.setLong(1, id);
                statement.execute();
            }
        } catch (SQLException e) {
            System.err.println("Error delete");
        }
    }

    @Override
    public Optional<User> findByEmail(String email) {
        User user = null;
        try {
            PreparedStatement statement = connection.prepareStatement(FIND_BY_EMAIL);
            statement.setString(1, email);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                user = new User(result.getLong("id"), result.getString("email"));
            }
        } catch (SQLException e) {
            System.err.println("Error findByEmail");
        }
        return Optional.ofNullable(user);
    }
}