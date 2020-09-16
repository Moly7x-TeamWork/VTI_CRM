//
package com.vti.servicesImp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vti.entity.Source;
import com.vti.repository.SourceRepo;
import com.vti.services.SourceService;

@Service
public class SourceServiceIMP implements SourceService{
	
	@Autowired
	private SourceRepo sourceRepo;
	
	/* 
	* @see com.vti.services.SourceService#findBySourceDetail(java.lang.String)
	*/
	@Override
	public Source findBySourceDetail(String sourceDetail) {
		return sourceRepo.findBySourceDetail(sourceDetail);
	}

	/* 
	* @see com.vti.services.SourceService#getAllSource()
	*/
	@Override
	public List<Source> getAllSource() {
		return sourceRepo.findAll();
	}

}
