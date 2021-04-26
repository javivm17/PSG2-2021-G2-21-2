<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>


<petclinic:layout pageName="donations">
    <jsp:body>
        <h2><c:if test="${donation['new']}">Nueva </c:if>Donación</h2>

 
        <form:form modelAttribute="donation" class="form-horizontal">
            <div class="form-group has-feedback">
                <petclinic:inputField label="Cantidad" name="amount"/>
                <petclinic:selectField label="Cliente" name="owner" names="${owners}" size="1"/>
            </div>

            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    <input type="hidden" name="causeId" value="${donation.cause.id}"/>
                    <button class="btn btn-default" type="submit">Añadir donación</button>
                </div>
            </div>
        </form:form>

    </jsp:body>

</petclinic:layout>
