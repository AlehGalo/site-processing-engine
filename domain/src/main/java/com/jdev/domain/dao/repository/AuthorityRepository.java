package com.jdev.domain.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jdev.domain.domain.Authority;

/**
 * Spring Data JPA repository for the Authority entity.
 */
public interface AuthorityRepository extends JpaRepository<Authority, String> {
}
