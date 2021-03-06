package com.hotel.demo.repository;

import com.hotel.demo.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Integer> {

    Optional<User> findByUsername(String username);

    Optional<User> findByPhone(String phone);
}