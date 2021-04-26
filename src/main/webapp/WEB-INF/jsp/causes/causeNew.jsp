<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<petclinic:layout pageName="causes">
<jsp:body>
	<h2>
    	Nueva causa
    </h2>
    <form:form modelAttribute="cause" class="form-horizontal" action="/causes/save">
		<div class="form-group has-feedback">
				<petclinic:inputField label="Nombre" name="name"/>
				<petclinic:inputField label="Descripción" name="description"/>
				<petclinic:inputField label="Organización benéfica" name="organization"/>
				<petclinic:inputField label="Cantidad objetivo" name="target"/>
		</div>
		<div class="form-group">
			<div class="col-sm-offset-2 col-sm-10">
				<input type="hidden" name="causeId" value="${cause.id}"/>
				<button class="btn btn-default" type="submit">Añadir causa</button>
			</div>
		</div>
	</form:form>
	</jsp:body>
</petclinic:layout>