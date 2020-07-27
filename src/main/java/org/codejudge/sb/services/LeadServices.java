package org.codejudge.sb.services;

import org.codejudge.sb.beans.LeadBeans;
import org.codejudge.sb.repository.LeadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LeadServices {
	
	@Autowired
	LeadRepository leadRepository;
	
	public LeadBeans getLead(int leadId)
	{
		if(leadRepository.findById(leadId).isPresent())
			return leadRepository.findById(leadId).get();
		else
			return null;
	}
	
	public boolean getLeadByEmail(String email)
	{
		if(leadRepository.findByEmail(email).isEmpty()) {
			System.out.println("Found");
			return true;
		}
		else {
			System.out.println("Not Found");
			return false;
		}
	}
	
	public void add(LeadBeans leadBeans)   
	{  
		leadRepository.save(leadBeans);  
	}  
	
	public void save(LeadBeans leadBeans)   
	{  
		leadRepository.save(leadBeans);  
	}  
	
	public boolean delete(int leadId)   
	{  
		if(leadRepository.findById(leadId).isPresent())
		{
			leadRepository.deleteById(leadId);
			System.out.println("deleted");
			return true;
		}
		else
		{
			return false;
		}
	} 
	
	public boolean isUpdateValid(LeadBeans leadBeans) {
		//System.out.println(leadRepository.findById(leadBeans.getId()).isPresent() +" "+  leadRepository.findByEmail(leadBeans.getEmail()).isEmpty());
		if(leadRepository.findById(leadBeans.getId()).isPresent() 
				&& (leadRepository.findById(leadBeans.getId()).get().getEmail().equals(leadBeans.getEmail()) 
				|| leadRepository.findByEmail(leadBeans.getEmail()).isEmpty())) {
			return true;
		}
		else
		{
			return false;
		}
	}
}
