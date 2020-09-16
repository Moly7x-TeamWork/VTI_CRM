//
package com.vti.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vti.entity.Source;

@Repository
public interface SourceRepo extends JpaRepository<Source, Long>{
	// Find source by sourceDetails. 
	Source findBySourceDetail(String sourceDetail);
}
