<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="causes">
    <h2>Lista de causas</h2>

    <table id="causesTable" class="table table-striped">
        <thead>
        <tr>
            <th>Nombre</th>
            <th>Total donado</th>
            <th>Meta a alcanzar</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${causes}" var="cs">
            <tr>
                <td>
                    <c:out value="${cs.name}"/>
                </td>
                <td>
                    <c:out value="${cs.donated} $"/>
                </td>
                <td>
                    <c:out value="${cs.target} $"/>
                </td>
                <td>
                	<!-- Informacion -->
                	<spring:url value="causes/{causeId}" var="infoUrl">
        				<spring:param name="causeId" value="${cause.id}"/>
    				</spring:url>
    				<a href="${fn:escapeXml(viewUrl)}" class="btn btn-default">Más información</a>
                	<!-- Añadir donacion -->
                	<spring:url value="" var="donateUrl">
        				<spring:param name="causeId" value="${cause.id}"/>
    				</spring:url>
    				<a href="${fn:escapeXml(editUrl)}" class="btn btn-default">Donar</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    
    <!-- Crear -->
    <a href="causes/new" class="btn btn-default">Añadir una causa</a>
    <br/>
	<br/>
</petclinic:layout>
