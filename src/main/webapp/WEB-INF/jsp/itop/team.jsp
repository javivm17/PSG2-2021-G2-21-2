<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<petclinic:layout pageName="team">
	<h2>Miembros del equipo</h2>

	<table id="team" class="table table-striped">
		<thead>
			<tr>
				<th>Email</th>
				<th>Telefono</th>
			</tr>
		</thead>
		<tbody id="members">

		</tbody>
	</table>
</petclinic:layout>


<script type="text/javascript">
	$.ajax({
		method : "POST",
		url : "http://localhost/web/webservices/rest.php?version=1.3",
		dataType : "json",
		crossDomain : true,
		data : {
			auth_user : "admin",
			auth_pwd : "admin",
			json_data : {
				"operation" : "core/get",
				"class" : "Person",
				"key" : "SELECT Person",
				"output_fields" : "email,phone"
			}
		}
	}).then(function(data) {
		Object.values(data.objects).forEach(e=>{
			let tr = $("<tr>");
			let tdEmail = $("<td>").text(e.fields.email);
			let tdPhone = $("<td>").text(e.fields.phone);
			tr.append(tdEmail);
			tr.append(tdPhone);
			$("#members").append(tr);
		});
	}, function(error, status, msg) {
		console.log(error,status, msg);
	});
</script>