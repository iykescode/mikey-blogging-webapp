package com.iykescode.blog.mikeybloggingwebapp.repository;

import com.iykescode.blog.mikeybloggingwebapp.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PersonRepository extends JpaRepository<Person, Integer> {

    Optional<Person> readByEmail(String email);

    Optional<Person> readByUsername(String username);

    boolean existsByEmail(String email);

    boolean existsByUsername(String username);

    boolean existsById(Integer id);
}
