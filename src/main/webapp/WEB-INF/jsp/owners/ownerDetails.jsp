<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="owners">

    <h2>Información del dueño</h2>


    <table class="table table-striped">
        <tr>
            <th>Nombre</th>
            <td><b><c:out value="${owner.firstName} ${owner.lastName}"/></b></td>
        </tr>
        <tr>
            <th>Dirección</th>
            <td><c:out value="${owner.address}"/></td>
        </tr>
        <tr>
            <th>Ciudad</th>
            <td><c:out value="${owner.city}"/></td>
        </tr>
        <tr>
            <th>Teléfono</th>
            <td><c:out value="${owner.telephone}"/></td>
        </tr>
    </table>

    <spring:url value="{ownerId}/edit" var="editUrl">
        <spring:param name="ownerId" value="${owner.id}"/>
    </spring:url>
    <a href="${fn:escapeXml(editUrl)}" class="btn btn-default">Editar dueño</a>

    <spring:url value="{ownerId}/pets/new" var="addUrl">
        <spring:param name="ownerId" value="${owner.id}"/>
    </spring:url>
    <a href="${fn:escapeXml(addUrl)}" class="btn btn-default">Añadir nueva mascota</a>
    
     <spring:url value="{ownerId}/delete" var="deleteUrl">
        <spring:param name="ownerId" value="${owner.id}"/>
    </spring:url>
    <a href="${fn:escapeXml(deleteUrl)}" class="btn btn-default">Borrar dueño</a>

    <br/>
    <br/>
    <br/>
    <h2>Mascotas y visitas</h2>

    <table class="table table-striped">
        <c:forEach var="pet" items="${owner.pets}">

            <tr>
                <td valign="top">
                    <dl class="dl-horizontal">
                        <dt>Nombre</dt>
                        <dd><c:out value="${pet.name}"/></dd>
                        <dt>Fecha de nacimiento</dt>
                        <dd><petclinic:localDate date="${pet.birthDate}" pattern="yyyy-MM-dd"/></dd>
                        <dt>Tipo</dt>
                        <dd><c:out value="${pet.type.name}"/></dd>
                    </dl>
                </td>
                <td valign="top">
                    <table class="table-condensed">
                        <thead>
                        <tr>
                            <th>Fecha de la visita</th>
                            <th>Descripción</th>
                            <th>Borrar visita</th>
                        </tr>
                        </thead>
                        <c:forEach var="visit" items="${pet.visits}">
                            <tr>
                                <td><petclinic:localDate date="${visit.date}" pattern="yyyy-MM-dd"/></td>
                                <td><c:out value="${visit.description}"/></td>
                                <td>
                                <spring:url value="/owners/{ownerId}/visit/pet/{petId}/delete/{visitId}" var="visitdeleteUrl">
                                    <spring:param name="ownerId" value="${owner.id}"/>
                                    <spring:param name="petId" value="${pet.id}"/>
                                    <spring:param name="visitId" value="${visit.id}"/>
                                </spring:url>
                                <a href="${fn:escapeXml(visitdeleteUrl)}">Borrar visita</a>
                                </td>
                            </tr>
                        </c:forEach>
                        <tr>
                            <td>
                                <spring:url value="/owners/{ownerId}/pets/{petId}/edit" var="petUrl">
                                    <spring:param name="ownerId" value="${owner.id}"/>
                                    <spring:param name="petId" value="${pet.id}"/>
                                </spring:url>
                                <a href="${fn:escapeXml(petUrl)}">Editar mascota</a>
                            </td>
                            <td>
                                <spring:url value="/owners/{ownerId}/pets/{petId}/visits/new" var="visitUrl">
                                    <spring:param name="ownerId" value="${owner.id}"/>
                                    <spring:param name="petId" value="${pet.id}"/>
                                </spring:url>
                                <a href="${fn:escapeXml(visitUrl)}">Añadir visita</a>
                            </td>
                            <td>
                                <spring:url value="/owners/{ownerId}/pets/{petId}/delete" var="deletePetUrl">
                                    <spring:param name="ownerId" value="${owner.id}"/>
                                    <spring:param name="petId" value="${pet.id}"/>
                                </spring:url>
                                <a href="${fn:escapeXml(deletePetUrl)}">Borrar mascota</a>
                            </td>
                        </tr>
                    </table>
                </td>              
                
                <td valign="top">
                    <table class="table-condensed">
                        <thead>
                        <tr>
                            <th>Fecha de inicio de la reserva</th>
                            <th>Fecha de fin de la reserva</th>
                        </tr>
                        </thead>
                        
                        <c:forEach var="booking" items="${pet.bookings}">
                            <tr>
                                <td><petclinic:localDate date="${booking.initialDate}" pattern="yyyy-MM-dd"/></td>
								<td><petclinic:localDate date="${booking.endDate}" pattern="yyyy-MM-dd"/></td> 
							</tr>
                        </c:forEach>
                        
                        <tr>
                            <td>
                                <spring:url value="/owners/{ownerId}/pets/{petId}/booking/new" var="petUrl">
                                    <spring:param name="ownerId" value="${owner.id}"/>
                                    <spring:param name="petId" value="${pet.id}"/>
                                </spring:url>
                                <a href="${fn:escapeXml(petUrl)}">Añadir reserva</a>
                            </td>
                            <td>
                          
                            </td>
                        </tr>
                    </table>
                </td>
                
                
                
                
            </tr>

        </c:forEach>
    </table>

</petclinic:layout>
