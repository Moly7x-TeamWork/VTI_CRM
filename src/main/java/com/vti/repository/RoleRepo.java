//
package com.vti.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vti.entity.Role;

@Repository
public interface RoleRepo extends JpaRepository<Role, Long>{
	
	Role findByNameRole(String nameRole);

}
