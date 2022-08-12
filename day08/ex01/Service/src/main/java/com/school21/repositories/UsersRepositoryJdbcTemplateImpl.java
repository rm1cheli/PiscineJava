package com.school21.repositories;

import javax.sql.DataSource;

import com.school21.models.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.util.List;
import java.util.Optional;

public class UsersRepositoryJdbcTemplateImpl implements UsersRepository {
    private JdbcTemplate template;


    private final String FIND_BY_ID = "SELECT * FROM chat.user WHERE id=?";
    private final String SAVE = "INSERT INTO chat.user (email) VALUES (?)";
    private final String UPDATE = "UPDATE chat.user SET " + "email = ?, " + "WHERE id = ?";
    private final String DELETE = "DELETE FROM chat.user WHERE id=?";
    private final String FIND_BY_EMAIL = "SELECT * FROM chat.user WHERE email=?";
    private final String FIND_BY_ALL = "SELECT * FROM chat.user";

    private RowMapper<User> userRowMapper = (rs, rowNum) ->
            new User(rs.getLong("id"), rs.getString("email"));

    public UsersRepositoryJdbcTemplateImpl(DataSource dataSource) {
        this.template = new JdbcTemplate(dataSource);
    }

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
    public void save(User entity) {
        template.update(SAVE, entity.getEmail());
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
    public Optional<User> findByEmail(String email) {
        List<User> users =  template.query(FIND_BY_EMAIL, userRowMapper, email);
        if (users.isEmpty())
            return Optional.empty();
        return Optional.of(users.get(0));
    }
}