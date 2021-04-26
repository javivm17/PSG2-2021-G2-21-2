<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>


<petclinic:layout pageName="adoption">

	<div style="text-align: center">
		<h2>SOLICITUDES PENDIENTES</h2>
	 </div><br>
	<c:choose>
		<c:when test="${numRequests>0}">
			<table class="table table-striped">
				<thead>
			            <tr>
			                <th>Nombre macota</th>
			                <th>Solicitante</th>
			                <th>Mensaje de solicitud</th>
			                <th></th>
						 	<th></th>
			            </tr>
				</thead>
				<c:forEach var="request" items="${requests}">
		            <tr>
		                <th><c:out value="${request.pet.name}"/></th>
		                <th><c:out value="${request.owner.firstName} ${request.owner.lastName}"/></th>
		                <th><c:out value="${request.description}"/></th>
		                <th><a href="/adoption/accept/${request.id}"><span class="glyphicon glyphicon-ok"></span></a></th>
		                <th><a href="/adoption/deny/${request.id}"><span class="glyphicon glyphicon-remove"></span></a></th>
		            </tr>
				</c:forEach>
			</table>
		</c:when>
		<c:otherwise>
			<h4>No tienes solicitudes pendientes</h4>	
		</c:otherwise>
	</c:choose>
	
	
</petclinic:layout>