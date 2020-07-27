package org.codejudge.sb.repository;

import java.io.Serializable;
import java.util.List;

import org.codejudge.sb.beans.LeadBeans;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LeadRepository extends CrudRepository<LeadBeans,Serializable>   {
	 
	 @Query("select c from LeadBeans c where c.email = ?1")
	 List<LeadBeans> findByEmail(String email);
}
