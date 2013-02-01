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
package org.openmrs.module.shrindicator;

import java.io.Serializable;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.openmrs.BaseOpenmrsObject;
import org.openmrs.BaseOpenmrsMetadata;

/**
 * It is a model class. It should extend either {@link BaseOpenmrsObject} or {@link BaseOpenmrsMetadata}.
 */
public class SHRKeyIndicatorReport extends BaseOpenmrsObject implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private Date date;
	private Integer totalEncounters;
	private Map<String, Integer> encountersByEncounterType;
	private Map<String, Integer> encountersByLocation;
	private Map<String, PatientPerFacilityList> patientPerFacility;

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Integer getTotalEncounters() {
		return totalEncounters;
	}

	public void setTotalEncounters(Integer totalEncounters) {
		this.totalEncounters = totalEncounters;
	}

	public Map<String, Integer> getEncountersByEncounterType() {
		return encountersByEncounterType;
	}

	public void setEncountersByEncounterType(
			Map<String, Integer> encountersByEncounterType) {
		this.encountersByEncounterType = encountersByEncounterType;
	}
	
	public Map<String, Integer> getEncountersByLocation() {
		return encountersByLocation;
	}

	public void setEncountersByLocation(Map<String, Integer> encountersByLocation) {
		this.encountersByLocation = encountersByLocation;
	}
	
	public Map<String, PatientPerFacilityList> getPatientPerFacility() {
		return patientPerFacility;
	}

	public void setPatientPerFacility(
			Map<String, PatientPerFacilityList> patientPerFacility) {
		this.patientPerFacility = patientPerFacility;
	}
	
	@Override
	public Integer getId() {
		return id;
	}
	
	@Override
	public void setId(Integer id) {
		this.id = id;
	}
	
}