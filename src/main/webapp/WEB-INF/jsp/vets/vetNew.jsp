<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<petclinic:layout pageName="vetNew">
	<h2>
    	Nuevo Veterinario
    </h2>
    <form:form modelAttribute="vet" class="form-horizontal" action="/vets/save">
		<input type="hidden" name="id" value="${vet.id}"/>
		<div class="form-group has-feedback">
				<petclinic:inputField label="Nombre" name="firstName"/>
				<petclinic:inputField label="Apellido" name="lastName"/>
		</div>
		<div class="form-group">
			<div class="col-sm-offset-2 col-sm-10">
				<button class="btn btn-default" type="submit">Añadir veterinario</button>
			</div>
		</div>
	</form:form>
</petclinic:layout>