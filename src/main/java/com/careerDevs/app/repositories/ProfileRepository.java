package com.careerDevs.app.repositories;

import com.careerDevs.app.models.profile.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProfileRepository  extends JpaRepository<Profile, Long> {
    Optional<Profile> findByUser_id(Long id);
    void deleteByUser_id(Long id);
}
