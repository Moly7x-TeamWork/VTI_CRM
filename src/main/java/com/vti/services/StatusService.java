//
package com.vti.services;

import java.util.List;

import com.vti.entity.Status;

public interface StatusService {
	/**
	 * 
	 * This method is Find target by statusDetails. 
	 * 
	 * @author: Tân
	 * @create_date: 15/09/2020
	 * @param sourceDetail
	 * @return Source
	 */
	Status findByStatusDetail(String statusDetail);
	
	/**
	 * 
	 * This method is get All Status. 
	 * 
	 * @author: Tân
	 * @create_date: 15/09/2020
	 * @return List of Source
	 */
	List<Status> getAllStatus();
}
