package com.example.SwiggyClone.repository;

import com.example.SwiggyClone.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
// Make sure the second generic type is Long, to match the ID in your Role entity.
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String name);
}