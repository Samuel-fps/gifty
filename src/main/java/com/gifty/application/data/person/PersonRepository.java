package com.gifty.application.data.person;

import com.gifty.application.data.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PersonRepository extends JpaRepository<Person, UUID> {
    // Person findAllByUser(User user);
}
