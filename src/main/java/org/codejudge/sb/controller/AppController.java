package org.codejudge.sb.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.codejudge.sb.beans.LeadBeans;
import org.codejudge.sb.beans.LeadErrorResponse;
import org.codejudge.sb.exception.BadRequestException;
import org.codejudge.sb.general.LeadrestExceptionHandler;
import org.codejudge.sb.services.LeadServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.util.JSONPObject;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping
public class AppController {

	
	@Autowired
	LeadServices leadServices;
	@Autowired
	LeadBeans leadBeans;
    @ApiOperation("This is the hello world api")
    @GetMapping("/")
    public String hello() {
        return "Hello World!!";
    }
    
    @ApiOperation("This is the api to give details of lead")
    @GetMapping({"/api/leads/","/api/leads/{lead_id}"})
    public LeadBeans fetchLead(@PathVariable("lead_id") Optional<Integer> leadId , HttpServletResponse response) 
    {
    	if(!leadId.isPresent())
		{
			System.out.println("Bad request");	
			throw new BadRequestException("Lead id is not present");
		}
    	else {
	    	leadBeans=leadServices.getLead(leadId.get());
	    	if(leadBeans!=null)
	    	{
	    		if(!((leadBeans.getCommunication()!=null && !leadBeans.getCommunication().isEmpty())))
		    		leadBeans.setCommunication("");
	    		//leadBeans.setStatus("");
	    		response.setStatus(HttpServletResponse.SC_OK);
	    	}
	    	else
	    	{
	    		response.setStatus(HttpServletResponse.SC_NOT_FOUND);
	    	}
    	}
    	return leadBeans;
    }
    
    @ApiOperation("This is the api to give details of lead")
    @PostMapping("/api/leads/")
    public LeadBeans addLead(@RequestBody LeadBeans leadBeans, HttpServletResponse response) 
    {
    	if(leadBeans!=null && leadBeans.getEmail()!=null && !leadBeans.getEmail().isEmpty() &&
    			leadServices.getLeadByEmail(leadBeans.getEmail())) 
    	{
    		leadServices.add(leadBeans);
    		response.setStatus(HttpServletResponse.SC_CREATED);
    		leadBeans.setCommunication(null);
    		leadBeans.setStatus("Created");
    		return leadBeans;
    	}
    	else
    	{
    		System.out.println("Bad request");	
			throw new BadRequestException("Email id is already Present");
    	}
    	
    }
    
    @ApiOperation("This is the api to give details of lead")
    @PutMapping("/api/leads/{lead_id}")
    public HashMap<String, String> saveLead(@PathVariable("lead_id") int leadId,@RequestBody LeadBeans leadBeans, HttpServletResponse response) 
    {
    	leadBeans.setId(leadId);
    	if(leadBeans!=null && leadServices.isUpdateValid(leadBeans)) 
    	{
    		leadBeans.setCommunication("");
        	leadServices.save(leadBeans);
        	response.setStatus(HttpServletResponse.SC_ACCEPTED);
    		HashMap<String, String> map = new HashMap<>();
    		map.put("status", "success");
    		return map;
    	}
    	else
    	{
    		System.out.println("Bad request");	
			throw new BadRequestException("Either Lead id is not present or Email is already present");
    	}
    	
    }
    
    @ApiOperation("This is the api to give details of lead")
    @PutMapping({"/api/mark_lead/","/api/mark_lead/{lead_id}"})
    public HashMap<String, String> markLead(@PathVariable("lead_id") Optional<Integer> leadId,@RequestBody Map<String, String> values, HttpServletResponse response) 
    {
    	if(!leadId.isPresent())
		{
			System.out.println("Bad request");	
			throw new BadRequestException("Lead id is not present");
		}
    	else {
	    	leadBeans=leadServices.getLead(leadId.get());
	    	if(leadBeans!=null)
	    	{
	    		leadBeans.setCommunication(values.get("communication"));
	    		leadBeans.setStatus("Contacted");
	        	leadServices.save(leadBeans);
	        	response.setStatus(HttpServletResponse.SC_ACCEPTED);
	        	HashMap<String, String> map = new HashMap<>();
	    		map.put("communication", leadBeans.getCommunication());
	    		map.put("status", "Contacted");
	    		return map;
	    	}
	    	else
	    	{
	    		response.setStatus(HttpServletResponse.SC_NOT_FOUND);
	    		return null;
	    	}
    	}
    }
    
    @ApiOperation("This is the api to give details of lead")
    @DeleteMapping({"/api/leads/","/api/leads/{lead_id}"})
    public HashMap<String, String> deleteLead(@PathVariable("lead_id") Optional<Integer> leadId , HttpServletResponse response) 
    {
    	if(!leadId.isPresent())
		{
			System.out.println("Bad request");	
			throw new BadRequestException("Lead id is not present");
		}
    	else {
    		leadServices.delete(leadId.get());
    		response.setStatus(HttpServletResponse.SC_OK);
    		HashMap<String, String> map = new HashMap<>();
    		map.put("status", "success");
    		return map;
    	}
    }


}
