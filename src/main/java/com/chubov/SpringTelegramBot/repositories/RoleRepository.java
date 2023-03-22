package com.chubov.SpringTelegramBot.repositories;

import com.chubov.SpringTelegramBot.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    Set<Role> findByRoleName(String roleName);
}
