package com.jdev.ngui.repository;

import com.jdev.ngui.domain.Test;
        import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the Test entity.
 */
public interface TestRepository extends JpaRepository<Test, Long> {

}
