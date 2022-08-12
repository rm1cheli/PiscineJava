package edu.school21.chat.repositories;

import edu.school21.chat.exceptions.NotSavedException;
import edu.school21.chat.models.Message;

import java.sql.*;
import java.util.Optional;

public class MessageRepositoryJdbcImpl implements MessageRepository{

    private final Connection connection;
    private final String COMAND = "SELECT * FROM chat.message WHERE id=?";

    public MessageRepositoryJdbcImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Optional<Message> findById(Long id) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(COMAND);
        statement.setLong(1, id);
        UserRepositoryJdbcImpl author = new UserRepositoryJdbcImpl(connection);
        ChatroomRepositoryJdbcImpl room = new ChatroomRepositoryJdbcImpl(connection);
        ResultSet reulst = statement.executeQuery();
        Message message = null;
        if(reulst.next()){
            message = new Message(reulst.getLong("id"),
                    author.findById(reulst.getLong("author")).orElse(null),
                    room.findById((reulst.getLong("room"))).orElse(null),
                    reulst.getString("text_message"),
                    reulst.getTimestamp("timestamp").toLocalDateTime());
        }
        reulst.close();
        statement.close();
        return Optional.ofNullable(message);
    }

    @Override
    public void save(Message message) throws SQLException {
        final String QUERY_TEMPLATE = "INSERT INTO chat.message (author, room, text_message, timestamp) VALUES (?, ?, ?, ?) RETURNING *";

        ResultSet resultSet = null;
        UserRepositoryJdbcImpl user = new UserRepositoryJdbcImpl(connection);
        ChatroomRepositoryJdbcImpl chat = new ChatroomRepositoryJdbcImpl(connection);
        PreparedStatement query = null;
        if(user.findById(message.getAuthor().getId()).isPresent() || chat.findById(message.getRoom().getId()).isPresent()) {
            System.out.println(user.findById(message.getAuthor().getId()).get().equals(message.getAuthor()));
            System.out.println(chat.findById(message.getRoom().getId()).get().equals(message.getRoom()));
            if(!user.findById(message.getAuthor().getId()).get().equals(message.getAuthor())
                    || !chat.findById(message.getRoom().getId()).get().equals(message.getRoom()))
                throw new NotSavedException();
        } else {
            throw new NotSavedException();
        }
        try {
                query = connection.prepareStatement(QUERY_TEMPLATE);
                query.setLong(1, message.getAuthor().getId());
                query.setLong(2, message.getRoom().getId());
                query.setString(3, message.getText());
                query.setTimestamp(4, Timestamp.valueOf(message.getTime()));
                resultSet = query.executeQuery();
                resultSet.next();
                message.setId(resultSet.getLong("id"));
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if(query != null) {
                query.close();
            }
            if (resultSet != null) {
                resultSet.close();
            }
        }
    }
}