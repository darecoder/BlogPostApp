package com.ekta.BlogPostApp.repository;

import com.ekta.BlogPostApp.models.Role;
import com.ekta.BlogPostApp.models.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleName roleName);
}
