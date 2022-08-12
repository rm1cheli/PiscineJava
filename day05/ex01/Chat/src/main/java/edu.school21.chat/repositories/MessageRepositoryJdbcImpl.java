package edu.school21.chat.repositories;

import edu.school21.chat.models.Message;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
        return Optional.ofNullable(message);
    }
}