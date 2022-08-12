package edu.school21.services;

import edu.school21.exceptions.AlreadyAuthenticatedException;
import edu.school21.models.User;
import edu.school21.repositories.UsersRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsersServiceImpl implements UsersRepository{

    private final Connection connection;

    public UsersServiceImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public User findByLogin(String login) throws SQLException {
        String COMAND = "SELECT * FROM user WHERE login=?";
        PreparedStatement statement = null;
        statement = connection.prepareStatement(COMAND);
        statement.setString(1, login);
        ResultSet reulst = statement.executeQuery();
        User user = null;
        if (reulst.next()) {
            user = new User(reulst.getLong("id"),
                    reulst.getString("login"),
                    reulst.getString("password"),
                    reulst.getBoolean("success"));
        }
        return user;
    }

    @Override
    public void update(User user) throws SQLException {
        final String QUERY_TEMPLATE = "UPDATE user SET " +
                "login = ?, " +
                "password = ?, " +
                "success = ? " +
                " WHERE id = ?";
        PreparedStatement query = connection.prepareStatement(QUERY_TEMPLATE);
        query.setString(1, user.getLogin());
        query.setString(2, user.getPassword());
        query.setBoolean(3, user.isSuccess());
        query.setLong(4, user.getId());
        query.execute();
    }
    @Override
    public boolean authenticate(String login, String password) throws AlreadyAuthenticatedException, SQLException {
        User user = findByLogin(login);


        if(user.isSuccess()){
            throw new AlreadyAuthenticatedException();
        }

        if(!password.equals(user.getPassword())) {
            return false;
        }
        user.setSuccess(true);
        update(user);
        return true;
    }
}