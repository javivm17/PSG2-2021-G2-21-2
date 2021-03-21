<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="owners">
    <jsp:attribute name="customScript">
        <script>
            $(function () {
                $("#initialDate").datepicker({dateFormat: 'yy/mm/dd'});
                $("#endDate").datepicker({dateFormat: 'yy/mm/dd'});
            });
            
            
        </script>
    </jsp:attribute>    
    <jsp:body>
        <center>
        	<h2>
            	Add booking
        	</h2>
        </center>
        
        <b>Pet</b>
        <table class="table table-striped">
            <thead>
            <tr>
                <th>Name</th>
                <th>Birth Date</th>
                <th>Type</th>
                <th>Owner</th>
            </tr>
            </thead>
            <tr>
                <td><c:out value="${booking.pet.name}"/></td>
                <td><petclinic:localDate date="${booking.pet.birthDate}" pattern="yyyy/MM/dd"/></td>
                <td><c:out value="${booking.pet.type.name}"/></td>
                <td><c:out value="${booking.pet.owner.firstName} ${booking.pet.owner.lastName}"/></td>
            </tr>
        </table>
        
        <br/>
        
        <form:form modelAttribute="booking"
                   class="form-horizontal">
            <div class="form-group has-feedback">
                <petclinic:inputField label="Initial date" name="initialDate"/>
                <petclinic:inputField label="End date" name="endDate"/>
            </div>
            
            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    <input type="hidden" name="petId" value="${booking.pet.id}"/>
                    <button class="btn btn-default" type="submit">Add Booking</button>
                </div>
            </div>
        </form:form>
        
        <br/>
        <b>Previous bookings</b>
        <table class="table table-striped">
            <tr>
                <th>Initial date</th>
                <th>End date</th>
            </tr>
            <c:forEach var="booking" items="${booking.pet.bookings}">
                <c:if test="${!booking['new']}">
                    <tr>
                        <td><petclinic:localDate date="${booking.initialDate}" pattern="yyyy/MM/dd"/></td>
                        <td><petclinic:localDate date="${booking.endDate}" pattern="yyyy/MM/dd"/></td>
                    </tr>
                </c:if>
            </c:forEach>
        </table>

    </jsp:body>
</petclinic:layout>
