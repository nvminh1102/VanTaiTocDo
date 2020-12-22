<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script>
	window.setTimeout(function() {
		  $("#success-alert").fadeTo(1000, 0).slideUp(1000, function(){
		      $(this).hide('fade');
		  });
	}, 5000);
	window.setTimeout(function() {
		  $("#error-alert").fadeTo(1000, 0).slideUp(1000, function(){
		      $(this).hide('fade');
		  });
	}, 5000);
</script>
<c:if test="${!empty msg }">
	<div id="success-alert" class="alert alert-success fade in">
		<strong>${ msg }</strong>
	</div>
</c:if>
<c:if test="${!empty msgError }">
	<div id="error-alert" class="alert alert-danger fade in">
		<strong>${ msgError }</strong>
	</div>
</c:if>