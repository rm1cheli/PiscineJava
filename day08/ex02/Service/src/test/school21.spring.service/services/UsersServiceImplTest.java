package school21.spring.service.services;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import school21.spring.service.config.ApplicationConfigTest;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
public class UsersServiceImplTest{
    @Test
    public void UsersServiceTest() throws IllegalAccessException{
        ApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfigTest.class);
        UsersService userService = context.getBean(UsersService.class);
        String password = userService.signUp("user@mail.com");
        assertNotEquals(password, "");
    }
}