package edu.school21.sockets.services;

import edu.school21.sockets.models.User;
import edu.school21.sockets.repositories.UsersRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

public class UsersServiceImpl implements UsersService{
    private UsersRepository repository;

    public UsersServiceImpl(UsersRepository repository) {
        this.repository = repository;
    }

    @Override
    public String signUp(String name, String password) {
        Optional<User> optionalUser = repository.findByName(name);
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
        if (optionalUser.isPresent()) {
            password = repository.findPasswordUser(optionalUser.get().getId());
        } else {
            password = encoder.encode(password);
            repository.saveByName(name, password);
        }
        return password;
    }
}