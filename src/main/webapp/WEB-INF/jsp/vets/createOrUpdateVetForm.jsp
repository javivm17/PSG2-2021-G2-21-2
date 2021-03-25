<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<petclinic:layout pageName="vets">
	<h1>
    	Actualizar Veterinario
    </h1>
    <h2>
    	<c:out value="${vet.firstName} ${vet.lastName}"/>
    </h2>
    <h4>Especialidades que posee:</h4>
    <c:forEach items="${vet.specialties}" var="vetSpecialty">
		<c:out value="${vetSpecialty.name}"/>				
        <spring:url value="/vets/{vetId}/delete/{specId}" var="deleteUrl">
			<spring:param name="vetId" value="${vet.id}"/>
			<spring:param name="specId" value="${vetSpecialty.id}"/>
		</spring:url>
		<a href="${fn:escapeXml(deleteUrl)}" style="padding-left: 25px;">&#10060;</a>	
		<br/>	
	</c:forEach>
	<c:if test="${vet.nrOfSpecialties == 0}">Ninguna<br/></c:if>
	<br/>
	
	<h4>Especialidades que no posee:</h4>
	<c:forEach items="${haveNotSpec}" var="Nspecialty">
		<c:out value="${Nspecialty.name}"/>
		<spring:url value="/vets/{vetId}/add/{specId}" var="addUrl">
			<spring:param name="vetId" value="${vet.id}"/>
			<spring:param name="specId" value="${Nspecialty.id}"/>
		</spring:url>
		<a href="${fn:escapeXml(addUrl)}" style="padding-left: 25px;">&#9989;</a>
		<br/>
	</c:forEach>
	<!-- <a href="../../vets" class="btn btn-default">Volver</a> -->
</petclinic:layout>
