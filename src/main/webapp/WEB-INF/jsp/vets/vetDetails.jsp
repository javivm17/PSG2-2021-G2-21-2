<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<petclinic:layout pageName="vetDet">
    <h2>Informacion del Veterinario</h2>
    <table class="table table-striped">
    	<thead>
    	<tr>
    		<th>Nombre y apellidos</th>
    		<th>Especialidad</th>
    		<th></th>
    	</tr>
    	</thead>
    	<tbody>
    		<tr>
    			<td>
    				<b><c:out value="${vet.firstName} ${vet.lastName}"/></b>
    			</td>
    			<td>
    			<c:forEach items="${vet.specialties}" var="vetSpecialty">
					<c:out value="${vetSpecialty.name}"/>
					<br/>
    			</c:forEach>
    			<c:if test="${vet.nrOfSpecialties == 0}">Ninguna</c:if>
    			</td>
    		</tr>
    	</tbody>
    </table>
</petclinic:layout>
