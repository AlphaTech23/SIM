package org.example.sim.repositories;

import org.example.sim.models.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserEntityRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findAllByName(String name);
    UserEntity findUserByName(String name);
}
