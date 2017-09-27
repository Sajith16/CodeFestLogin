package org.codefest.app.repository;

import org.codefest.app.domain.LoginFest;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the LoginFest entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LoginFestRepository extends JpaRepository<LoginFest, Long> {

}
