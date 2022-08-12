package edu.school21.chat.app;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import edu.school21.chat.models.Chatroom;
import edu.school21.chat.models.Message;
import edu.school21.chat.models.User;
import edu.school21.chat.repositories.ChatroomRepositoryJdbcImpl;
import edu.school21.chat.repositories.MessageRepositoryJdbcImpl;
import edu.school21.chat.repositories.UserRepositoryJdbcImpl;
import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;

public class Program {

    private static final String DB_URL = "jdbc:postgresql://localhost/postgres";
    private static final String DB_USER = "postgres";
    private static final String BD_PWD = "";
    private static final String DB_SCHEMA = "/resources/schema.sql";
    private static final String DB_DATA = "/resources/data.sq";

    public static void main(String[] args) throws FileNotFoundException, SQLException {
        Connection connection = connect();
        runQueriesFromFile(connection, "../src/main/resources/schema.sql");
        runQueriesFromFile(connection, "../src/main/resources/data.sql");

        UserRepositoryJdbcImpl us = new UserRepositoryJdbcImpl(connection);
        ChatroomRepositoryJdbcImpl ch = new ChatroomRepositoryJdbcImpl(connection);
        MessageRepositoryJdbcImpl mes = new MessageRepositoryJdbcImpl(connection);
        User creator = new User(1L, "Bob", "000000", new ArrayList<>(), new ArrayList<>());
        User author = creator;
        Chatroom room = new Chatroom(1L, "Random", creator, new ArrayList<>());
        Message message = new Message(null, author, room, "Hello!", LocalDateTime.now());
        if(us.findById(3L).isPresent() && ch.findById(3L).isPresent()) {
            Message mess = new Message(null, creator, room, "Hello!", LocalDateTime.now());
            mes.save(message);
            System.out.println(message.getId());
        } else {
            System.out.println("Error");
        }
        connection.close();
    }

    public static Connection connect(){
        Connection conn = null;
        try {
            conn = HikariConnect().getConnection();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }
    private static HikariDataSource HikariConnect() {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(DB_URL);
        hikariConfig.setUsername(DB_USER);
        hikariConfig.setPassword(BD_PWD);
        return (new HikariDataSource(hikariConfig));
    }

    public static void runQueriesFromFile(Connection connection, String filename) throws FileNotFoundException, SQLException {
        try (Scanner scanner = new Scanner(new File(filename)).useDelimiter(";"); Statement statement = connection.createStatement()) {
            try {
                while (scanner.hasNext()) {
                    statement.execute(scanner.next().trim());
                }
            } catch (SQLException throwables) {
                System.out.println(throwables.getMessage());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}