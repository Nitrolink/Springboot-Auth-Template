package com.careerDevs.app.controllers;


import com.careerDevs.app.models.auth.User;
import com.careerDevs.app.models.profile.Profile;
import com.careerDevs.app.repositories.ProfileRepository;
import com.careerDevs.app.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/profiles")
public class ProfileController {
    @Autowired
    private ProfileRepository repository;

    @Autowired
    UserService userService;

    @GetMapping
    public @ResponseBody
    List<Profile> getProfiles() {return repository.findAll();}

    @GetMapping("/{id}")
    public ResponseEntity<?> getProfileById(@PathVariable Long id) {

        Profile profile = repository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND)
        );
        return new ResponseEntity<>(profile, HttpStatus.OK);
    }

    @GetMapping("/self")
    public @ResponseBody Profile getSelf() {
        User currentUser = userService.getCurrentUser();

        if (currentUser == null) {
            return null;
        }
        Profile currentProf =  repository.findByUser_id(currentUser.getId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return currentProf;
    }

    @PostMapping("/create")
    public ResponseEntity<Profile> createProfile(@RequestBody Profile newProfile) {

        User currentUser = userService.getCurrentUser();

        if (currentUser == null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        newProfile.setUser(currentUser);

        Profile prof = repository.save(newProfile);

        return new ResponseEntity<>(prof, HttpStatus.CREATED);
    }

    @PutMapping
    public @ResponseBody Profile updateProfile(@RequestBody Profile updates) {
        User currentUser = userService.getCurrentUser();

        if (currentUser == null) {
            return null;
        }
        Profile developer = repository.findByUser_id(currentUser.getId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if (updates.getName() != null) developer.setName(updates.getName());
        if (updates.getEmail() != null) developer.setEmail(updates.getEmail());

        return repository.save(developer);
    }

    @DeleteMapping
    public ResponseEntity<String> destroyProfile() {
        User currentUser = userService.getCurrentUser();

        if (currentUser == null) {
            return null;
        }
        repository.deleteByUser_id(currentUser.getId());
        return new ResponseEntity<>("Deleted", HttpStatus.OK);
    }
}
