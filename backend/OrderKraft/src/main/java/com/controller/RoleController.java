package com.controller;


import com.entity.Role;

import com.repository.RoleRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/roles")
//@CrossOrigin(origins = "*")  
public class RoleController {

    @Autowired
    private RoleRepository roleRepository;

    @PostMapping("/add")
    public ResponseEntity<Role> addRole(@RequestBody Role role) {
        Role savedRole = roleRepository.save(role);
        return ResponseEntity.ok(savedRole);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Role>> getAllRoles() {
        List<Role> roles = roleRepository.findAll();
        return ResponseEntity.ok(roles);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Role> getRoleById(@PathVariable Long id) {
        return roleRepository.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
}
