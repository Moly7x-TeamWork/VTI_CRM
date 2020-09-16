//
package com.vti.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vti.entity.Status;

@Repository
public interface StatusRepo extends JpaRepository<Status, Long> {
	//Find target by statusDetails. 
	Status findByStatusDetail(String statusDetail);
}
