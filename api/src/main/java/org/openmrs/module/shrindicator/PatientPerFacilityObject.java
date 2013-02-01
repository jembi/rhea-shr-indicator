package org.openmrs.module.shrindicator;

public class PatientPerFacilityObject {

	private Integer patientId;
	private String encounterType;
	private Integer size;
	
	public Integer getPatientId() {
		return patientId;
	}
	public void setPatientId(Integer patientId) {
		this.patientId = patientId;
	}
		
	public String getEncounterType() {
		return encounterType;
	}
	public void setEncounterType(String encounterType) {
		this.encounterType = encounterType;
	}
	
	public Integer getSize() {
		return size;
	}
	public void setSize(Integer size) {
		this.size = size;
	}

}
