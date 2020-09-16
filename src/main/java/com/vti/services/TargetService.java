//
package com.vti.services;

import java.util.List;

import com.vti.entity.Target;

public interface TargetService {
	/**
	 * 
	 * This method is Find target by targetDetails. 
	 * 
	 * @author: Tân
	 * @create_date: 15/09/2020
	 * @param sourceDetail
	 * @return Source
	 */
	Target findByTargetDetail(String targetDetail);
	
	/**
	 * 
	 * This method is get All Target. 
	 * 
	 * @author: Tân
	 * @create_date: 15/09/2020
	 * @return List of Source
	 */
	List<Target> getAllTarget();

}
