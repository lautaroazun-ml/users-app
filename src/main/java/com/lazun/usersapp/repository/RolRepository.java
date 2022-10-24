package com.lazun.usersapp.repository;

import com.lazun.usersapp.models.entity.RoleEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RolRepository extends JpaRepository<RoleEntity, Long> {

  Optional<RoleEntity> findByName(String username);
}
