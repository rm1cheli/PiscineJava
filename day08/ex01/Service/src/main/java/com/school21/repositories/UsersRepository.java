package com.school21.repositories;

import com.school21.models.User;

import java.util.Optional;

public interface UsersRepository extends CrudRepository<User>{
    Optional<User> findByEmail(String email);
}