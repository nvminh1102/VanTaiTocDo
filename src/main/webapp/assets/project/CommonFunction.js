//Viết các hàm javascript sử dụng chung
CommonFunction = {
	// Đi đến một trang cụ thể trong khi xem danh sách
	// p: trang muốn đi đến, curPage: trang hiện tại,maxPage: trang tối đa có
	// thể đi đến
	goToPageNum : function(p, curPage, maxPage, e) {
		setFilterValue();
		if (e != null) {
			if (e.keyCode == 13 && p > 0 && p != parseInt(curPage)
					&& p <= parseInt(maxPage)) {
				$("#page").val(p);
				$("#frmSearch").submit();
			}
		} else {
			$("#page").val(p);
			$("#frmSearch").submit();
		}
	},
	// Lưu danh sách các bản ghi được chọn
	// item: checkbox;
	selectedItems : function(item) {
		var value = item.value;
		if (value != "0") {
			var ids = $("#hdfIds").val();
			if (item.checked) {
				if (ids == ";") {
					$("#hdfIds").val(ids + value + ";");
				} else {
					if (ids.indexOf(";" + value + ";") < 0)
						$("#hdfIds").val(ids + value + ";");
				}
			} else {
				var re = new RegExp(';' + value + ';', 'g');
				$("#hdfIds").val(ids.replace(re, ";"));
			}
		} else {
			var ids = ";";
			if (item.checked) {
				$("table.table > tbody > tr").each(function() {
					var id = $('td:eq(0) input', this).val();
					if(id != null){
						if (ids == ";")
							ids = ids + id + ";";
						else if (ids.indexOf(";" + id + ";") < 0)
							ids = ids + id + ";";
					}
				});
				$("#hdfIds").val(ids);
			} else {
				$("#hdfIds").val(";");
			}
		}
	},
	// Hiển thị popup thông báo
	// title: tiêu đề thông báo, msg: nội dung thông báo
	// type: error, warning...
	showPopUpMsg : function(title, msg, type) {
		$('#confirm-delete').modal('hide');
		$("#popup-message #myModalLabel").empty();
		if (type.indexOf("error") > -1) {
			$("#popup-message #myModalLabel").append("<i class='fa fa-warning'></i> ");
			$("#popup-message .modal-header").addClass("alert-danger");
		} else if(type.indexOf("success") > -1){
                    $("#popup-message #myModalLabel").append("<i class='fa fa-info'></i> ");
                    $("#popup-message .modal-header").removeClass("alert-danger");
                    $("#popup-message .modal-header").addClass("alert-success");
                } else {
                        $("#popup-message #myModalLabel").append("<i class='fa fa-info'></i> ");
			$("#popup-message .modal-header").removeClass("alert-danger");
                        $("#popup-message .modal-header").addClass("alert-info");
		}
		$("#popup-message #myModalLabel").append(title);
		$("#popup-message .modal-body").text(msg);
		$("#popup-message").modal('show');
	},
	// Xóa một hoặc nhiều bản ghi bằng ajax
	// id: id của bản ghi đó
	deleteItems : function(id) {
		if ($("#hdfIds").val() != ";")
			$("#frmDelete").submit();
		else
			CommonFunction.showPopUpMsg(CommonMessenger.ErrorTitle,
					CommonMessenger.MustChoiceBeforeDelete,
					CommonMessenger.errorType);
	},
	deleteFile : function(id, file) {
		var ids = $("#fileIdsDel").val();
		if (ids == ";") {
			$("#fileIdsDel").val(ids + id + ";");
		} else {
			if (ids.indexOf(";" + id + ";") < 0)
				$("#fileIdsDel").val(ids + id + ";");
		}
		localStorage.setItem("fileDel" + id,$(file).html());
		localStorage.setItem("fileDelImage" + id,$(file).css("background-image"));
		$(file).css("background-image","none");
		$(file).html("<p class=\"reject text-orange\" onclick=\"CommonFunction.rejectDeleteFile("+ id +", this.parentNode)\">Hoàn tác</p>");
	}, 
	rejectDeleteFile : function(id, file){
		if(localStorage.getItem("fileDel" + id))
			$(file).html(localStorage.getItem("fileDel" + id));
		var re = new RegExp(';' + id + ';', 'g');
		$("#fileIdsDel").val($("#fileIdsDel").val().replace(re, ";"));
		if(localStorage.getItem("fileDelImage" + id))
			$(file).css("background-image", localStorage.getItem("fileDelImage" + id));
	},
	downloadFile: function(e, id){
		if(e.target.tagName.indexOf("DIV") > -1)
		{
			var a = document.createElement('a');
			a.href='download.html?id=' + id;
			a.target = '_blank';
			document.body.appendChild(a);
			a.click();
		}
	},
	loadContractTemp: function(contractKindId, type)
	{
		$.ajax({
			url:"../contracttemplate/getByContractKind.html",
			type : "get",
            mimeType:"text/html; charset=UTF-8",
			data: { kindId: contractKindId, type: type },
			success: function(data){
				$("#contractTemplate").empty();
				var decoded = decodeURIComponent(data);
				$("#contractTemplate").append(decoded.replace(/\+/g, ' '));
			}
		});
	},
	loadContractTempHtml: function(id, edit)
	{
		$.ajax({
			url:"../contracttemplate/getContractTempHtml.html",
			type : "get",
            mimeType:"text/html; charset=UTF-8",
			data: { id: id},
			success: function(data){
				if("".indexOf(data) > -1){
					$("#divcontract").addClass("hide");
				}
				else
				{
					$("#divcontract").empty();
					var decoded = decodeURIComponent(data);
					$("#divcontract").append(decoded.replace(/\+/g, ' '));
					//$("#divcontract").append("<div id='div' style='position: absolute;top:0;right:0;z-index:9999;border:solid 1px #e4e4e4;max-width:350px;'></div>");					
					$("#divcontract").removeClass("hide");
				}
				if(edit != null)
					$("#divcontract").loadJSON(JSON.parse($("#contractJsonStr").text()));
			}
		});
	},
	getDisp: function(input, count) {
        if (input == null) {
            return null;
        }
        if (input.length > count ) {
            var index = count;
            if((input.substring(0, index).trim().endsWith("<b") && input.substring(index, input.length).trim().startsWith("style"))
        			|| (input.substring(0, index).trim().endsWith("style='background:") && input.substring(index, input.length).trim().startsWith("yellow"))){
            	index++;
        	}
            while (index < input.length && input.charAt(index) != ' ') {
                index++;
                if(index < input.length && input.charAt(index) == ' ') {
                	if((input.substring(0, index).trim().endsWith("<b") && input.substring(index, input.length).trim().startsWith("style"))
                			|| (input.substring(0, index).trim().endsWith("style='background:") && input.substring(index, input.length).trim().startsWith("yellow"))){
                    	index++;
                	}
                }
            }
            var  titleReturn = (index == input.length) ? input.substring(0, index) : input.substring(0, index) + " ... ";
            return titleReturn;
        }
        return input;
	},
	formatCurrencyHtml : function(ctrl) {
	    var val = ctrl.innerHTML;
	    val = val.replace(/,/g, "");
	    val += '';
	    x = val.split('.');
	    x1 = x[0];
	    x2 = x.length > 1 ? '.' + x[1] : '';

	    var rgx = /(\d+)(\d{3})/;

	    while (rgx.test(x1)) {
	        x1 = x1.replace(rgx, '$1' + ',' + '$2');
	    }

	    ctrl.innerHTML = x1 + x2;
	},
}

var CommonMessenger = {
	errorType : 'error',
	ErrorTitle : ' Thông báo lỗi',
	Info : 'Thông báo',
	Warning : 'Cảnh báo',
	Success : 'Cập nhật thành công!',
	Delete : 'Bạn có chắc chắn xóa dữ liệu này không?',
	Error : 'Đã có lỗi trong quá trình xử lý!',
	SureToDelete : 'Bạn có chắc chắn muốn xóa dữ liệu này không?',
	NothingToDownload : 'Dữ liệu này không có tập tin đính kèm!',
	ConfirmWrongPassword : 'Nhắc lại mật khẩu mới chưa đúng!',
	OnlyChoiceToView : 'Bạn chỉ được chọn một giá trị để xem chi tiết!',
	OnlyChoiceToEdit : 'Bạn chỉ được chọn một giá trị để thực hiện thay đổi nội dung!',
	OnlyChoiceToAction : 'Bạn chỉ được chọn một giá trị để {0}!',
	MustFillAll : 'Bạn phải nhập đầy đủ thông tin!',
	MustChoiceBeforeAction : 'Bạn phải chọn dữ liệu cần {0} trước!',
	MustChoiceBeforeAction2 : 'Bạn phải chọn {0} cần {1} trước!',
	MustChoiceBeforeView : 'Bạn phải chọn dữ liệu cần xem trước!',
	MustChoiceBeforePhanCong : 'Bạn phải chọn hồ sơ để phân công rà soát!',
	MustChoiceBeforeEdit : 'Bạn phải chọn dữ liệu cần sửa trước!',
	MustChoiceBeforeTrinh : 'Bạn phải chọn hồ sơ trước khi trình lãnh đạo!',
	MustChoiceBeforeDelete : 'Bạn phải chọn dữ liệu cần xóa trước!',
	MustChoiceBeforePrint : 'Bạn phải chọn dữ liệu cần in trước!',
	MustChoiceBeforeSendMail : 'Bạn phải chọn dữ liệu cần phải gửi email trước!',
	MustChoiceBeforeDownload : 'Bạn phải chọn tài liệu đính kèm cần tải xuống trước!',
	MustChoiceDeXuatBeforeAttach : 'Bạn phải chọn đề xuất cần đính kèm tài liệu trước!',
	MustChoiceDeTaiDuAnBeforeAttach : 'Bạn phải chọn đề tài - dự án cần đính kèm tài liệu trước!',
	MustChoiceBeforeAddAttach : 'Bạn phải chọn {0} cần đính kèm tài liệu trước!',
	MustChoiceBeforeEditAttach : 'Bạn phải chọn tài liệu {0} cần sửa thông tin trước!',
	MustChoiceBeforeDeleteAttach : 'Bạn phải chọn tài liệu {0} cần xóa trước!',
	MustChoiceBeforeUpdate : 'Bạn phải chọn dữ liệu cần cập nhật thông tin trước!',
	MustChoiceBeforeSecurity : 'Bạn phải chọn ứng dụng cần phân quyền trước!',
	MustChoiceBeforeAddUserOrGroup : 'Bạn phải chọn nhóm quyền cần thêm nhóm/người dùng trước!',
	MustChoiceBeforeDanhGia : 'Bạn phải chọn tài liệu {0} cần đánh giá trước!',
	OnlyChoiceOneRecode : 'Bạn chỉ được chọn một bản ghi ',

	successNotice : function(title, msg) {
		window.Ext.net.Notification.show({
			// iconCls: 'icon-information',
			// icon: windown.Ext.Net.Icon.Information,
			icon : window.Ext.Msg.INFO,
			hideDelay : 3000,
			autoHide : true,
			closeVisible : true,
			html : msg,
			title : CommonMessenger.Info,
			alignToCfg : {
				offset : [ 0, 20 ],
				position : 'tr-tr'
			},
			showFx : {
				args : [ 't', {} ],
				fxName : 'slideIn'
			},
			hideFx : {
				args : [ 't', {} ],
				fxName : 'ghost'
			}
		});
	},
	successHandler : function(form, action) {
		if (action.result && action.result.msg) {
		}
		CommonFunction.CloseWindow();
		// successNotice('Thông báo', 'Lưu dữ liệu thành công');
	},

	failureHandler : function(form, action) {
		var msg = '';

		if (action.failureType == 'client'
				|| (action.result && action.result.errors && action.result.errors.length > 0)) {
			msg = "Không nhập đủ thông tin";
		} else if (action.result && action.result.msg) {
			msg = action.result.msg;
		} else if (action.response) {
			msg = action.response.responseText;
		}
		CommonMessenger.showErrorMessage('', msg);
	},

	showInfoMessage : function(title, msg) {
		window.Ext.Msg.show({
			title : CommonMessenger.Info,
			msg : msg,
			buttons : window.Ext.Msg.OK,
			icon : window.Ext.Msg.INFO
		});
	},

	showErrorMessage : function(title, msg) {
		window.Ext.Msg.show({
			title : CommonMessenger.ErrorTitle,
			msg : msg,
			buttons : window.Ext.Msg.OK,
			icon : window.Ext.Msg.ERROR
		});
	},

	showWarningMessage : function(title, msg) {
		window.Ext.Msg.show({
			title : CommonMessenger.Warning,
			msg : msg,
			buttons : window.Ext.Msg.OK,
			icon : window.Ext.Msg.WARNING
		});
	},

	showConfirmMessage : function(title, msg, func) {
		Ext.Msg.show({
			title : CommonMessenger.Info,
			msg : msg,
			buttons : Ext.MessageBox.YESNO,
			icon : Ext.MessageBox.QUESTION,
			fn : func,
			width : 350
		});
	},

	showWindowMessage : function(title, msg, func) {
		window.Ext.Msg.show({
			title : title,
			msg : msg,
			buttons : window.Ext.Msg.OK,
			icon : window.Ext.Msg.INFO
		});
	}
	
};

$(document).ready(function(){
	$(".currencyHtml").each(function(){
		CommonFunction.formatCurrencyHtml(this);
	});
});