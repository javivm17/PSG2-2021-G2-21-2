<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="vets">
    <h2>Veterinarians</h2>

    <table id="vetsTable" class="table table-striped">
        <thead>
        <tr>
            <th>Nombre</th>
            <!-- <th>Especialidad</th> -->
            <th></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${vets.vetList}" var="vet">
            <tr>
                <td>
                    <c:out value="${vet.firstName} ${vet.lastName}"/>
                </td>
                <!--
                <td>
                    <c:forEach var="specialty" items="${vet.specialties}">
                        <c:out value="${specialty.name} "/>
                    </c:forEach>
                    <c:if test="${vet.nrOfSpecialties == 0}">none</c:if>
                </td>
                 -->
                <td>
                	<!-- Informacion -->
                	<spring:url value="vets/{vetId}" var="viewUrl">
        				<spring:param name="vetId" value="${vet.id}"/>
    				</spring:url>
    				<a href="${fn:escapeXml(viewUrl)}" class="btn btn-default">Mas informacion</a>
                	<!-- Editar -->
                	<spring:url value="vets/{vetId}/edit" var="editUrl">
        				<spring:param name="vetId" value="${vet.id}"/>
    				</spring:url>
    				<a href="${fn:escapeXml(editUrl)}" class="btn btn-default">Editar</a>
    				<!-- Borrar -->
    				<spring:url value="vets/{vetId}/delete" var="deleteUrl">
        				<spring:param name="vetId" value="${vet.id}"/>
    				</spring:url>
    				<a href="${fn:escapeXml(deleteUrl)}" class="btn btn-default">Borrar</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    
    <!-- Crear -->
    <a href="vets/new" class="btn btn-default">Añadir un veterinario</a>
    
    <br/>
	<br/>
    <table class="table-buttons">
        <tr>
            <td>
                <a href="<spring:url value="/vets/xml" htmlEscape="true" />">Verlo como XML</a>
            </td>            
        </tr>
    </table>
</petclinic:layout>
