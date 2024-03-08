package com.CRUD.controller;

import com.CRUD.model.User;
import com.CRUD.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@CrossOrigin
@RestController
public class UserController {

    @Autowired
    private final UserRepo repository;
    public UserController(UserRepo repository) {
        this.repository = repository;
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers(){
        return ResponseEntity.ok(repository.findAll());
    }

    @PostMapping("/users")
    public ResponseEntity<User> createUser(@RequestBody User toCreate){
        User created = repository.save(toCreate);
        return ResponseEntity.created(URI.create("/user/"+created.getId())).body(created);
    }

    @PutMapping("user/{id}")
    public ResponseEntity<User> updateUserById(@RequestBody User toUpdate){
        if(!repository.existsById(toUpdate.getId())){
            return ResponseEntity.notFound().build();
        }
        User updated = repository.save(toUpdate);
        return ResponseEntity.accepted().body(updated);
    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity<?> deleteUserById(@PathVariable int id){
        if(!repository.existsById(id)){
            return ResponseEntity.notFound().build();
        }
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
