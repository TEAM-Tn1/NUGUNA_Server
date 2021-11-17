package io.github.tn1.server.domain.user.domain.repository;

import io.github.tn1.server.domain.user.domain.User;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, String> {
}
