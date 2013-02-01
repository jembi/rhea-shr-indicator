package org.openmrs.module.shrindicator;

import java.util.List;

public class PatientPerFacilityList {
	
	private Integer total;
	private Integer patientCount;
	private List<PatientPerFacilityObject> PatientPerFacilityObject;
	
	public Integer getTotal() {
		return total;
	}
	public void setTotal(Integer total) {
		this.total = total;
	}
	
	public Integer getPatientCount() {
		return patientCount;
	}
	public void setPatientCount(Integer patientCount) {
		this.patientCount = patientCount;
	}
	
	public List<PatientPerFacilityObject> getPatientPerFacilityObject() {
		return PatientPerFacilityObject;
	}
	public void setPatientPerFacilityObject(
			List<PatientPerFacilityObject> patientPerFacilityObject) {
		PatientPerFacilityObject = patientPerFacilityObject;
	}

}
