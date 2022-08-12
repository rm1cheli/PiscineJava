package edu.school21.services;

import edu.school21.exceptions.AlreadyAuthenticatedException;
import edu.school21.models.User;
import edu.school21.repositories.UsersRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import java.sql.SQLException;

public class UsersServiceImplTest {
    private UsersRepository usersRepository;

    @BeforeEach
    void init() throws SQLException {
        usersRepository = new UsersServiceImpl(new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.HSQL)
                .addScript("schema.sql")
                .addScript("data.sql")
                .build().getConnection());
    }



    @ParameterizedTest
    @ValueSource(strings = {"rmicheli1", "rmicheli2", "rmicheli3"})
    void testTrue(String login) throws AlreadyAuthenticatedException, SQLException {
        User user = usersRepository.findByLogin(login);
        Assertions.assertTrue(usersRepository.authenticate(user.getLogin(), user.getPassword()));
    }

    @ParameterizedTest
    @ValueSource(strings = {"rmicheli7", "rmicheli8", "rmicheli9"})
    void testException(String login) throws AlreadyAuthenticatedException, SQLException {
        User user = usersRepository.findByLogin(login);
        usersRepository.authenticate(user.getLogin(), user.getPassword());
        User user1 = usersRepository.findByLogin(login);
        System.out.println(user1.isSuccess());
        Assertions.assertThrows(AlreadyAuthenticatedException.class , ()->usersRepository.authenticate(user1.getLogin(), user1.getPassword()));
    }

    @ParameterizedTest
    @ValueSource(strings = {"rmicheli4", "rmicheli5", "rmicheli6"})
    void testFalse(String login) throws AlreadyAuthenticatedException, SQLException {
        User user = usersRepository.findByLogin(login);
        Assertions.assertFalse(usersRepository.authenticate(user.getLogin(), "00"));
    }
}