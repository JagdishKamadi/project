package com.epam.repository;


import com.epam.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, Integer> {

    // considering the email as username in our case
    Optional<AppUser> findByEmail(String email);
}
