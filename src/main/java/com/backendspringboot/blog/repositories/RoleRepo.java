package com.backendspringboot.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backendspringboot.blog.entities.Role;

public interface RoleRepo extends JpaRepository<Role, Integer>  {

}
