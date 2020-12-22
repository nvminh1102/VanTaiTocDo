<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %> 
<div class="modal fade" id="confirm-delete" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="alert-warning modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-hidden="true">&times;</button>
                            <h4 class="modal-title" id="myModalLabel"><i class="fa fa-warning"></i><spring:message code="label.warning"/></h4>
			</div>
			<div class="modal-body"><spring:message code="message.modal.question.remove"/></div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal"><spring:message code="message.modal.cancel"/></button>
                                <a class="btn btn-danger btn-ok"><i class="fa fa-check"></i> <spring:message code="label.button.delete"/></a>
			</div>
		</div>
	</div>
</div>
<script>
	$('#confirm-delete').on('show.bs.modal',function(e) {
		if ($("#hdfIds").val() != ";")
			$(this).find('.btn-ok').attr('onclick',	'CommonFunction.deleteItems();');
		else {
			CommonFunction.showPopUpMsg(CommonMessenger.ErrorTitle,	CommonMessenger.MustChoiceBeforeDelete,	CommonMessenger.errorType);
			e.preventDefault();
		}
	});
</script>
<div class="modal fade" id="popup-message" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-hidden="true">&times;</button>
				<h4 class="modal-title" id="myModalLabel"></h4>
			</div>
			<div class="modal-body"></div>
			<div class="modal-footer">
                            <button type="button" class="btn btn-danger" data-dismiss="modal"><i class="fa fa-times"></i> <spring:message code="label.button.close"/></button>
			</div>
		</div>
	</div>
</div>
