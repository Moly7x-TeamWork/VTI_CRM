//
package com.vti.servicesImp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vti.entity.Status;
import com.vti.repository.StatusRepo;
import com.vti.services.StatusService;

@Service
public class StatusServiceIMP implements StatusService{
	
	@Autowired
	StatusRepo statusRepo;
	
	/* 
	* @see com.vti.services.StatusService#findByStatusDetail(java.lang.String)
	*/
	@Override
	public Status findByStatusDetail(String statusDetail) {
		return statusRepo.findByStatusDetail(statusDetail);
	}

	/* 
	* @see com.vti.services.StatusService#getAllStatus()
	*/
	@Override
	public List<Status> getAllStatus() {
		return statusRepo.findAll();
	}

}
