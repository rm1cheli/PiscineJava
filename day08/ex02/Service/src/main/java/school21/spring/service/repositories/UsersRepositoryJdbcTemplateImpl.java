package school21.spring.service.repositories;

import javax.sql.DataSource;
import school21.spring.service.models.User;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.util.List;
import java.util.Optional;

public class UsersRepositoryJdbcTemplateImpl implements UsersRepository {
    private JdbcTemplate template;


    private final String FIND_BY_ID = "SELECT * FROM users WHERE id=?";
    private final String SAVE = "INSERT INTO users (email) VALUES (?)";
    private final String UPDATE = "UPDATE users SET " + "email = ?, " + "WHERE id = ?";
    private final String DELETE = "DELETE FROM users WHERE id=?";
    private final String FIND_BY_EMAIL = "SELECT * FROM users WHERE email=?";
    private final String FIND_BY_ALL = "SELECT * FROM users";
    private final String SAVE_BY_EMAIL = "INSERT INTO users (email, password) VALUES (?, ?)";
    private final String SQL_FIND_PASSWORD_BY_ID = "SELECT password FROM users WHERE id = ?";


    public UsersRepositoryJdbcTemplateImpl(DataSource dataSource) {
        this.template = new JdbcTemplate(dataSource);
    }

    private final RowMapper<User> userRowMapper = (rs, rowNum) ->
            new User(rs.getLong("id"), rs.getString("email"));

    private final RowMapper<String> stringRowMapper = (rs, rowNum) ->
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
        template.update(SAVE, entity.getEmail());
    }

    @Override
    public void saveByEmail(String email, String password) {
        template.update(SAVE_BY_EMAIL, email, password);
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