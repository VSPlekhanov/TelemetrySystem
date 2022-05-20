package org.azul.telemetry.data.service;

import org.azul.telemetry.data.model.entity.Role;
import org.azul.telemetry.data.repository.RoleRepository;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Spring service for <code>Role</code> entity.
 *
 * @see Role
 */
@Service
public class RoleService {
    private final RoleRepository roleRepository;

    @Autowired
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    /**
     * Saves role if it is not exists yet.
     *
     * @param role role to save
     * @return <code>true</code> if role was saved
     */
    @Contract(mutates = "param1")
    public boolean saveRole(@NotNull Role role) {
        Optional<Role> roleFromDb = roleRepository.findByName(role.getName());

        if (roleFromDb.isPresent()) {
            return false;
        }

        roleRepository.save(role);
        return true;
    }
}
