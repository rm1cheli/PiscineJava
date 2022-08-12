package edu.school21.sockets.repositories;

import edu.school21.sockets.models.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

public class UsersRepositoryImpl implements UsersRepository{
    private JdbcTemplate template;


    private final String FIND_BY_ID = "SELECT * FROM users WHERE id=?";
    private final String SAVE = "INSERT INTO users (name) VALUES (?)";
    private final String UPDATE = "UPDATE users SET " + "name = ?, " + "WHERE id = ?";
    private final String DELETE = "DELETE FROM users WHERE id=?";
    private final String FIND_BY_NAME = "SELECT * FROM users WHERE name=?";
    private final String FIND_BY_ALL = "SELECT * FROM users";
    private final String SAVE_BY_NAME = "INSERT INTO users (name, password) VALUES (?, ?)";
    private final String SQL_FIND_PASSWORD_BY_ID = "SELECT password FROM users WHERE id = ?";


    public UsersRepositoryImpl(DataSource dataSource) {
        this.template = new JdbcTemplate(dataSource);
    }

    private RowMapper<User> userRowMapper = (rs, rowNum) ->
            new User(rs.getLong("id"), rs.getString("name"));

    private RowMapper<String> stringRowMapper = (rs, rowNum) ->
            rs.getString("password");

    @Override
    public Optional<User> findById(Long id) {
        List<User> users =  template.query(FIND_BY_ID, userRowMapper, id);
        if (users.isEmpty())
            return Optional.empty();
        return Optional.of(users.get(0));
    }

    @Override
    public List<User> findAll() {
        return template.query(FIND_BY_ALL, userRowMapper);
    }

    @Override
    public String findPasswordUser(Long id) {
        List<String> passwords =  template.query(SQL_FIND_PASSWORD_BY_ID, stringRowMapper, id);
        if (passwords.isEmpty())
            return "";
        return passwords.get(0);
    }

    @Override
    public void save(User entity) {
        template.update(SAVE, entity.getName());
    }

    @Override
    public void saveByName(String name, String password) {
        template.update(SAVE_BY_NAME, name, password);
    }

    @Override
    public void update(User entity) {
        template.update(UPDATE, entity);
    }

    @Override
    public void delete(Long id) {
        template.update(DELETE, id);
    }

    @Override
    public Optional<User> findByName(String name) {
        List<User> users =  template.query(FIND_BY_NAME, userRowMapper, name);
        if (users.isEmpty())
            return Optional.empty();
        return Optional.of(users.get(0));
    }
}