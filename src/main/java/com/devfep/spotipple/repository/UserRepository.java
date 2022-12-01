package com.devfep.spotipple.repository;

import com.devfep.spotipple.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User readByEmail(String email);

}
