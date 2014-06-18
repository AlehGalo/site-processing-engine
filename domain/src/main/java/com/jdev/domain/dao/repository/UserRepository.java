package com.jdev.domain.dao.repository;

import java.util.List;

import org.joda.time.LocalDate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.jdev.domain.entity.User;

/**
 * Spring Data JPA repository for the User entity.
 */
public interface UserRepository extends JpaRepository<User, String> {

    @Query("select u from User u where u.activationKey = ?1")
    User getUserByActivationKey(String activationKey);

    @Query("select u from User u where u.activated = false and u.createdDate > ?1")
    List<User> findNotActivatedUsersByCreationDateBefore(LocalDate localDate);

}
