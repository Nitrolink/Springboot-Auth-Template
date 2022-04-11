package com.careerDevs.app.repositories;

import com.careerDevs.app.models.auth.ERole;
import com.careerDevs.app.models.auth.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}
