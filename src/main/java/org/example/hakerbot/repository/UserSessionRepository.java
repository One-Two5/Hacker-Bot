package org.example.hakerbot.repository;

import org.example.hakerbot.entity.UserState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserSessionRepository extends JpaRepository<UserState, Long> {
}
