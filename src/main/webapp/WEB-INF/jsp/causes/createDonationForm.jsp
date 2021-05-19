<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="donations">
    <jsp:body>
        <h2>Donación para la causa "${cause.name}"</h2>

 
        <form:form modelAttribute="donation" class="form-horizontal">
            <spring:bind path="amount">
    		<c:set var="cssGroup" value="form-group ${status.error ? 'has-error' : '' }"/>
   			<c:set var="valid" value="${not status.error and not empty status.actualValue}"/>
    		<div class="${cssGroup}">
      		<label class="col-sm-2 control-label">Cantidad</label>

     		<div class="col-sm-10">
        	<input type="number" step="1" min="0" name="amount"> 
        	<c:if test="${valid}">
                   <span class="glyphicon glyphicon-ok form-control-feedback" aria-hidden="true"></span>
        	</c:if>
        	<c:if test="${status.error}">
                <span class="glyphicon glyphicon-remove form-control-feedback" aria-hidden="true"></span>
                <span class="help-inline">${status.errorMessage}</span>
        	</c:if>
      		</div>
    		</div>
			</spring:bind>

            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    <input type="hidden" name="causeId" value="${donation.cause.id}"/>
                    <button class="btn btn-default" type="submit">Añadir donación</button>
                </div>
            </div>
        </form:form>

    </jsp:body>

</petclinic:layout>
