//
package com.vti.servicesImp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vti.entity.Target;
import com.vti.repository.TargetRepo;
import com.vti.services.TargetService;

@Service
public class TargetServiceIMP implements TargetService{
	
	@Autowired
	TargetRepo targetRepo;
	
	/* 
	* @see com.vti.services.TargetService#findByTargetDetail(java.lang.String)
	*/
	@Override
	public Target findByTargetDetail(String targetDetail) {
		return targetRepo.findByTargetDetail(targetDetail);
	}

	/* 
	* @see com.vti.services.TargetService#getAllTarget()
	*/
	@Override
	public List<Target> getAllTarget() {
		return targetRepo.findAll();
	}

}
