//
package com.vti.servicesImp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vti.entity.Role;
import com.vti.repository.RoleRepo;
import com.vti.services.RoleService;

@Service
public class RoleServiceIMP implements RoleService {
	
	@Autowired
	private RoleRepo roleRepo;
	
	@Override
	public Role findByNameRole(String nameRole) {
		return roleRepo.findByNameRole(nameRole);
	}

}
