/**
 * The contents of this file are subject to the OpenMRS Public License
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://license.openmrs.org
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * Copyright (C) OpenMRS, LLC.  All Rights Reserved.
 */
package org.openmrs.module.shrindicator.web.controller;

import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Encounter;
import org.openmrs.EncounterType;
import org.openmrs.Location;
import org.openmrs.Patient;
import org.openmrs.api.context.Context;
import org.openmrs.module.shrindicator.PatientPerFacilityList;
import org.openmrs.module.shrindicator.PatientPerFacilityObject;
import org.openmrs.module.shrindicator.SHRKeyIndicatorReport;
import org.openmrs.util.OpenmrsUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * The main controller.
 */
@Controller
public class  SHRKeyIndicatorReportManageController {
	
	protected final Log log = LogFactory.getLog(getClass());
	
	@RequestMapping(value = "/module/shrindicator/manage", method = RequestMethod.GET)
	public void manage(ModelMap model, @RequestParam(value = "date", required = true) String date) {
		model.addAttribute("user", Context.getAuthenticatedUser());
		
		SHRKeyIndicatorReport shrKeyIndicatorReport = generateReport(date);
		
		model.addAttribute("shrKeyIndicatorReport", shrKeyIndicatorReport);
	}

	private SHRKeyIndicatorReport generateReport(String dateString) {
		List<Integer> patientIds = new ArrayList<Integer>();
		SHRKeyIndicatorReport shrKeyIndicatorReport = new SHRKeyIndicatorReport();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			date = sdf.parse(dateString);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("Begining report for " + date + "...................");
		shrKeyIndicatorReport.setDate(date);
		
		List<Encounter> encounterList = Context.getEncounterService()						
				.getEncounters(null, null, date, date, null, null,
						null, false);
		System.out.println("Total number of encounters Recorded per day " + encounterList.size());
		shrKeyIndicatorReport.setTotalEncounters(encounterList.size());
		
		List<EncounterType> encounterTypes = Context.getEncounterService().getAllEncounterTypes();
		System.out.println("Total number of encounter types in the Database " + encounterTypes.size());
		
		Map<String, Integer> encountersByEncounterType = new HashMap<String, Integer>();
		
		shrKeyIndicatorReport.setEncountersByEncounterType(encountersByEncounterType);
		for (EncounterType encounterType : encounterTypes) {
			List<EncounterType> encounterTypesToExtract = new ArrayList<EncounterType>();
			encounterTypesToExtract.add(encounterType);
			
			List<Encounter> encounterListByEncounterType = Context.getEncounterService()						
					.getEncounters(null, null, date, date, null, encounterTypesToExtract,
							null, false);
			
			encountersByEncounterType.put(encounterType.getName(), encounterListByEncounterType.size());
			System.out.println("Total number of encounters for " + encounterType.getName() + " for " + date.toLocaleString() + " is " + encounterListByEncounterType.size());
		}
		
		List<Location> locations = Context.getLocationService().getAllLocations();
		
		Map<String, Integer> encountersByLocation = new HashMap<String, Integer>();
		
		shrKeyIndicatorReport.setEncountersByLocation(encountersByLocation);
		for(Location location : locations) {
			System.out.println("Location is " + location.getName());
			List<Encounter> encounterListByLocation = Context.getEncounterService()						
					.getEncounters(null, location, date, date, null, null,
							null, false);
			encountersByLocation.put(location.getName(), encounterListByLocation.size());
			System.out.println(encounterListByLocation.size());
		}
		shrKeyIndicatorReport.setEncountersByEncounterType(encountersByEncounterType);
		
		List<Encounter> allEncounters = Context.getEncounterService()						
				.getEncounters(null, null, date, date, null, null,
						null, false);
		for(Encounter encounter : allEncounters) {
			Integer patientId = encounter.getPatientId();
			
			     if (!patientIds.contains(patientId)) {
			    	 patientIds.add(patientId);
			    	 System.out.println("Patient Id " +patientId);
			      }

		}
		
		Map<String, PatientPerFacilityList> patientsPerFacility = new TreeMap<String, PatientPerFacilityList>();
		shrKeyIndicatorReport.setPatientPerFacility(patientsPerFacility);
		
		for(Location location : locations) {	
			System.out.println("location name " + location.getName());
			PatientPerFacilityList patientPerFacilityList = new PatientPerFacilityList();
			
			List<Integer> patientIdList = new ArrayList<Integer>();
			
			List<PatientPerFacilityObject> objects = new ArrayList<PatientPerFacilityObject>();	
			int total = 0;
				for(EncounterType encounterType : encounterTypes) {
					List<EncounterType> encounterTypeList = new ArrayList<EncounterType>();	
					PatientPerFacilityObject patientPerFacilityObject = new PatientPerFacilityObject();

					encounterTypeList.add(encounterType);
					
					
					List<Encounter> encountersByPatientAndFacility = Context.getEncounterService()						
							.getEncounters(null, location, date, date, null,
									encounterTypeList, null, false);
					
					//if(encountersByPatientAndFacility.size() > 0) {
					
					patientPerFacilityObject.setEncounterType(encounterType.getName());
					
					System.out.println("encounter type " + encounterType.getName());
					
					patientPerFacilityObject.setSize(encountersByPatientAndFacility.size());
					System.out.println("encounter size " + encountersByPatientAndFacility.size());
					total = total + encountersByPatientAndFacility.size();
					objects.add(patientPerFacilityObject);
					
					
					for(Encounter e : encountersByPatientAndFacility) {
						Integer i = e.getPatient().getPatientId();
						
							  if (!patientIdList.contains(i)) {
								  patientIdList.add(i);
							  }
						
						
					}
					
					
					
				//	}
				
			}
			patientPerFacilityList.setPatientPerFacilityObject(objects);
			patientPerFacilityList.setTotal(total);
			patientPerFacilityList.setPatientCount(patientIdList.size());	
			patientsPerFacility.put(location.getName(), patientPerFacilityList);	
		}
		
		OpenmrsUtil.getApplicationDataDirectory();
		String reportName = "ShrIndicatorReport-"+dateString+".csv";
		
		generateCsvFile(OpenmrsUtil.getApplicationDataDirectory() + "\\"+reportName, patientsPerFacility, date);
		
		return shrKeyIndicatorReport;
	}
	
	
	
	private static void generateCsvFile(String sFileName, Map<String, PatientPerFacilityList> patientsPerFacility, Date date)
	   {
		try
		{
			FileWriter writer = new FileWriter(sFileName);
			
		  	writer.append("Query Date " + date.toString());
		  	writer.append(',');
		  	writer.append('\n');
		  	writer.append('\n');
		  	writer.append("facility ID");
		  	writer.append(',');
		  	writer.append("No. of Patients");
		  	writer.append(',');
		  	writer.append("No. of Encounters");
		  	writer.append(',');
		  	
		  	for(EncounterType encounterType : Context.getEncounterService().getAllEncounterTypes()) {
		  		writer.append(encounterType.getName());
		  		writer.append(',');
		  	}
		  	writer.append('\n');
		  	
			for(Map.Entry<String,PatientPerFacilityList> entry : patientsPerFacility.entrySet()) {
				  String key = entry.getKey();
				  PatientPerFacilityList value = entry.getValue();

				  System.out.println(key + " => " + key);
				  
				  String encounterTypeString = "";
				  List<String> encounterTypes = new ArrayList<String>();
				  List<Integer> sizes = new ArrayList<Integer>();
				  
				  String sizeString = "";
				  
				 // if (value.getPatientPerFacilityObject().size() > 0) {
				
				  encounterTypes.add("No. of Patients");
				  sizes.add(value.getPatientCount());
					  
				  encounterTypes.add("No. of Encounters");
				  sizes.add(value.getTotal());
				  
			//}
				  
				  for(PatientPerFacilityObject o : value.getPatientPerFacilityObject()){
					  encounterTypeString = encounterTypeString + "|" + o.getEncounterType();
					  sizeString = sizeString +"|" + o.getSize();
					  
					  encounterTypes.add(o.getEncounterType());
					  sizes.add(o.getSize());

				  }
						  	
				  	writer.append(key);
				  	writer.append(',');
				  	
				
				  	
				  for(Integer i : sizes) {
					writer.append(i.toString());
					writer.append(',');
				  }
					writer.append('\n');  
				    
				  System.out.println("Encounter types ="+ encounterTypeString);
				  System.out.println("Size =" + sizeString);
				}							 	
	 
		    //generate whatever data you want
	 
		    writer.flush();
		    writer.close();
		}
		catch(IOException e)
		{
		     e.printStackTrace();
		} 
	    }
	
	
}
