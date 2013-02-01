<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/header.jsp"%>

<%@ include file="template/localHeader.jsp"%>

<div class="box">
<table cellpadding="4" cellspacing="0" style="border: 1px solid #918E90">
<tr><th>Date</th><td>${shrKeyIndicatorReport.date}</td></tr>
<tr><th>Total Encounters Per Day</th><td>${shrKeyIndicatorReport.totalEncounters}</td></tr>
<tr></tr>
	<c:forEach var="patientPerFacility" items="${shrKeyIndicatorReport.patientPerFacility}" varStatus="varStatus">
	<table>
		<tr><th>${patientPerFacility.key}</th></tr>
		
		<c:forEach var="record" items="${patientPerFacility.value.patientPerFacilityObject}" varStatus="varStatus">
		<tr><th>Encounter Type</th><th>Size</th></tr>
		<c:if test="${record.size != 0}">
		<tr <c:if test="${varStatus.index % 2 == 0}">class='evenRow'</c:if>>
		<td valign="top">${record.encounterType}</td>
		<td valign="top">${record.size}</td>
	</tr>
	</c:if>
	</c:forEach> 
	
	<tr><td>Total Encounters</td><td>${patientPerFacility.value.total}</td></tr>
	
	</table>
	</c:forEach>
</table>
</div>

<%@ include file="/WEB-INF/template/footer.jsp"%>