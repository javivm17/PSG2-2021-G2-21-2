<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<petclinic:layout pageName="causes">
    <h1>Información de la causa</h1>
    <h4>Nombre</h4>
	<c:out value="${cause.name}"></c:out>

    <h4>Descripción</h4>
    <c:out value="${cause.description}"></c:out>
    
    <h4>Organización</h4>
    <c:out value="${cause.organization}"></c:out>
    <h4>Estado</h4>
	<c:out value="${cause.closed ? 'Finalizada' : 'Abierta'}"></c:out> 
    <h4>Estado de las donaciones</h4>
    <progress id="donationbar" max="${cause.target}" value="${cause.donated}"></progress>
    <br>
    <c:if test="${!cs.closed}"> 
	    <spring:url value="{causeId}/donations/new" var="donateUrl">
	 		<spring:param name="causeId" value="${cause.id}"/>
		</spring:url>
		<a href="${fn:escapeXml(donateUrl)}" class="btn btn-default">Donar</a>
    </c:if>
    <br>
    <br>
    <h1>Información de las donaciones</h1>
    <table class="table table-striped">
    	<thead>
    	<tr>
    		<th>Fecha de la donación</th>
    		<th>Cantidad</th>
    		<th>Cliente</th>
    	</tr>
    	</thead>
    	<tbody>
    		<c:forEach items="${donations}" var="donation">
    		<tr>
    			<td>
    				<c:out value="${donation.date}"/>
    			</td>
    			<td>
					<c:out value="${donation.amount}"/>
    			</td>
    			<td>
    				<c:out value="${donation.owner.firstName} ${donation.owner.lastName}"/>
    			</td>
    		</tr>
    		</c:forEach>
    	</tbody>
    </table>
</petclinic:layout>
