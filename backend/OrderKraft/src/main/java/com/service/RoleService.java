package com.service;

import com.dto.RoleDTO;
import java.util.List;

public interface RoleService {
    RoleDTO createRole(RoleDTO roleDTO);
    List<RoleDTO> getAllRoles();
    RoleDTO getRoleById(Long id);
}
