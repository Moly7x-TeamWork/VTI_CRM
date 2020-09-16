//
package com.vti.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vti.entity.Target;

@Repository
public interface TargetRepo extends JpaRepository<Target, Long> {
	//Find target by targetDetails. 
	Target findByTargetDetail(String targetDetail);
	
}
