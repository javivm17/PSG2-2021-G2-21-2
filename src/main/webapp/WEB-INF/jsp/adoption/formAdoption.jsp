<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="formAdoption">
<h2>Solicitud de adopcion</h2>
<table class="table table-striped">
<tr>
                <td valign="top">
                    <dl class="dl-horizontal">
                    	<dt>Dirigida a</dt>
                        <dd><c:out value="${pet.owner.firstName} ${pet.owner.lastName}"/></dd>
                        <dt>Nombre de la mascota</dt>
                        <dd><c:out value="${pet.name}"/></dd>
                        <dt>Tipo de mascota</dt>
                        <dd><c:out value="${pet.type.name}"/></dd>
                    </dl>                    
                </td>
</tr>
</table>
        <form:form modelAttribute="adoption" class="form-horizontal" action="/adoption/new/${pet.id}" method="POST">
            <input type="hidden" name="pet" value="${pet.id}"/>
            <input type="hidden" name="owner" value="${owner.id}"/>
            <label>¿Como cuidaras la mascota?</label>
				<petclinic:inputField label="Descripcion" name="description"/>                
            <div class="form-group">
                <div class="col-sm-2 col-sm-10">
                   <button href="/adoption/requestsent" class="btn btn-default" type="submit">Enviar solicitud</button>
                </div>
            </div>
        </form:form>
</petclinic:layout>
