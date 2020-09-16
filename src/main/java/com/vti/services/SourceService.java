//
package com.vti.services;

import java.util.List;

import com.vti.entity.Source;

public interface SourceService {
	/**
	 * 
	 * This method is Find source by sourceDetails. 
	 * 
	 * @author: Tân
	 * @create_date: 15/09/2020
	 * @param sourceDetail
	 * @return Source
	 */
	Source findBySourceDetail(String sourceDetail);
	
	/**
	 * 
	 * This method is get All Source. 
	 * 
	 * @author: Tân
	 * @create_date: 15/09/2020
	 * @return List of Source
	 */
	List<Source> getAllSource();
}
