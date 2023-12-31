package com.iykescode.blog.mikeybloggingwebapp.repository;

import com.iykescode.blog.mikeybloggingwebapp.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

    Role getByRoleName(String roleName);
}
