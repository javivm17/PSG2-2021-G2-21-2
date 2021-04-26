<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="adopt">
	<h2>Mascotas disponible para adoptar</h2>
	<table id="petsTable" class="table table-striped">
        <thead>
        <tr>
            <th>Nombre</th>
            <th>Propietario</th>
            <th>Tipo</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${pets}" var="pet">
			<tr>
                <td>
                    <c:out value="${pet.name}"/>
                </td>
                <td>
                	<c:forEach items="${owners}" var="owner">
                		<c:if test="${pet.owner.id == owner.id}">${owner.firstName}&nbsp;${owner.lastName}</c:if>
                	</c:forEach>
                </td>
                <td>
                    <c:out value="${pet.type.name}"/>
                </td>
                <td>
    				<a href="/adoption/new/${pet.id}" class="btn btn-default">Adoptar</a>
                </td>
            </tr>
		</c:forEach>
        </tbody>
    </table>
</petclinic:layout>