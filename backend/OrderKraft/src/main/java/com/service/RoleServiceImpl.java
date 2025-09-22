package com.service;

import com.dto.RoleDTO;
import com.entity.Role;
import com.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepo;

    @Override
    public RoleDTO createRole(RoleDTO roleDTO) {
        Role role = new Role();
        role.setName(roleDTO.getName());
        Role savedRole = roleRepo.save(role);
        return convertToDTO(savedRole);
    }

    @Override
    public List<RoleDTO> getAllRoles() {
        return roleRepo.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public RoleDTO getRoleById(Long id) {
        Role role = roleRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Role not found with ID: " + id));
        return convertToDTO(role);
    }

    // Utility method to convert entity to DTO
    private RoleDTO convertToDTO(Role role) {
        RoleDTO dto = new RoleDTO();
        dto.setId(role.getId());
        dto.setName(role.getName());
        return dto;
    }
}


/*

The utility method convertToDTO(Role role) is written to avoid repeating the 
same conversion logic in multiple places.


Suppose you're converting from Role (entity) to RoleDTO in 3 places:

In createRole()

In getAllRoles()

In getRoleById()

Without a utility method, you’d repeat the same code over and over:


You now only write the mapping logic once, and reuse it like this:

return convertToDTO(savedRole);

*/