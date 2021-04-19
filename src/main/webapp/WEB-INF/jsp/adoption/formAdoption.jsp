<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="formAdoption">
<h2>Solicitud de adopcion</h2>
        <form:form modelAttribute="AdoptionApplications" class="form-horizontal" action="/adoption/new" method="POST">
            <input type="hidden" name="pet" value="${pet.id}"/>
            <input type="hidden" name="owner" value="${owner.id}"/>
            <div class="form-group has-feedback">
                <div class="form-group">
                    <label class="col-sm-2 control-label">Propietario</label>
                    <div class="col-sm-10">
                        <c:out value="${pet.owner.firstName} ${pet.owner.lastName}"/>
                    </div>
                   <input label="Descripcion" name="description"/>
                </div>
            </div>
            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                   <button class="btn btn-default" type="submit">Enviar solicitud</button>
                </div>
            </div>
        </form:form>
</petclinic:layout>
