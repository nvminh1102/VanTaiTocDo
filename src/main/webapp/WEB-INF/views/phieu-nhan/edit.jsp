<%@ page isELIgnored="false" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<script src="<%=request.getContextPath()%>/assets/project/manageNotary/manageAppoint/edit.js${urlCache}"></script>
<%--
    Quyết định bổ nhiệm CCV - Chỉnh sửa bổ nhiệm CCV
--%>
<script>
    var idAppoint = '${idAppoint}';
</script>
<label class="hidden" id="notaryEmpty">Bạn chưa chọn công chứng viên</label>
<label class="hidden" id="errMsg2">Vui lòng nhập đầy đủ thông tin</label>
<label class="hidden" id="errMsg1">Số quyết định không được để trống</label>
<label class="hidden" id="errMsg3">Người ban hành không được để trống</label>
<label class="hidden" id="fileEmpty">File đính kèm không được để trống</label>
<label class="hidden" id="error_1">Ngày có hiệu lực không được để trống</label>
<label class="hidden" id="error_2">Lý do từ chối bổ nhiệm không được bỏ trống</label>
<label class="hidden" id="error_3">Ngày ban hành không được để trống</label>

<section id="content" ng-app="osp"  ng-controller="tccc">
    <section class="vbox background-white">
        <section class="scrollable padder">
            <ul class="bg-light breadcrumb no-border no-radius b-light pull-in">
                <li><a href="<%=request.getContextPath()%>/"><i class="fa fa-home"></i>&nbsp;<spring:message code="label.manageNotary.manageAppoint.edit.label.homeTitle"></spring:message></a></li>
                <li><a href="<%=request.getContextPath()%>/quan-ly-bo-nhiem-ccv"><spring:message code="label.manageNotary.manageAppoint.edit.label.parentTitle"></spring:message></a></li>
                <li><a href="javascript:void(0)"><spring:message code="label.manageNotary.manageAppoint.edit.label.pageTitle"></spring:message></a></li>
                </ul>
                <div id="msgId" class="m-b-md" style="display: none;">
                    <script>
                        $(document).ready(function () {
                        /*$("#msgId").addClass("alert-success");*/
                        });
                    </script>
                    <span class="closebtn" onclick="this.parentElement.style.display = 'none';">&times;</span>
                    <div class="title-message">{{logger}}</div>
                </div>
                <section class="panel panel-default" style="margin-bottom: 5px;">
                    <header class="panel-heading">
                        <a href="javascript:void(0)"><h4 class="panel-title text-center font-bold font-size28" data-toggle="collapse" data-target="#collapseOne">
                            <spring:message code="label.manageNotary.manageAppoint.edit.label.titleEditAppoint"></spring:message>
                            </h4></a>
                    </header>
                    <div id="collapseOne" class="panel-collapse collapse in" aria-expanded="true">
                        <div class="panel-body background-xam">
                            <form id="formAdd" class="form-horizontal">

                                <header class="panel-head">
                                    <a href="javascript:void(0)"><h4 class="panel-header" data-toggle="collapse" data-target="#collapseTow">
                                        <spring:message code="label.manageNotary.manageAppoint.edit.label.titleInformationProbationary"></spring:message>
                                        </h4></a>
                                </header>
                                <div id="collapseTow" class="panel-collapse collapse in" aria-expanded="true">

                                    <div class="form-group line-md">
                                        <label class="col-md-2 text-dark"><spring:message code="label.manageNotary.manageAppoint.edit.label.fullName"></spring:message>: </label>
                                        <label class="col-md-4 text-dark">{{notaryInfoView.nameNotaryInfo}}</label>
                                        <label class="col-md-2 text-dark"><spring:message code="label.manageNotary.manageAppoint.edit.label.birthday"></spring:message>: </label>
                                        <label class="col-md-4 text-dark">{{notaryInfoView.birthDay|date:'dd-MM-yyyy'}}</label>
                                    </div>

                                    <div class="form-group line-md">
                                        <label class="col-md-2 text-dark"><spring:message code="label.manageNotary.manageAppoint.edit.label.sex"></spring:message>: </label>
                                        <label class="col-md-4 text-dark">{{notaryInfoView.sex == 1 ? 'Nam' : 'Nữ'}}</label>
                                        <label class="col-md-2 text-dark"><spring:message code="label.manageNotary.manageAppoint.edit.label.identityCard"></spring:message>: </label>
                                        <label class="col-md-4 text-dark">{{notaryInfoView.idNo}}</label>
                                    </div>

                                    <div class="form-group line-md">
                                        <label class="col-md-2 text-dark"><spring:message code="label.manageNotary.manageAppoint.edit.label.dateRange"></spring:message>: </label>
                                        <label class="col-md-4 text-dark">{{notaryInfoView.idNoDate|date:'dd-MM-yyyy'}}</label>
                                        <label class="col-md-2 text-dark"><spring:message code="label.manageNotary.manageAppoint.edit.label.issuedBy"></spring:message>: </label>
                                        <label class="col-md-4 text-dark">{{notaryInfoView.addressIdNo}}</label>
                                    </div>

                                    <div class="form-group line-md">
                                        <label class="col-md-2 text-dark"><spring:message code="label.manageNotary.manageAppoint.edit.label.phoneNumber"></spring:message>: </label>
                                        <label class="col-md-4 text-dark">{{notaryInfoView.phoneNumberNotaryInfo}}</label>
                                        <label class="col-md-2 text-dark"><spring:message code="label.manageNotary.manageAppoint.edit.label.email"></spring:message>: </label>
                                        <label class="col-md-4 text-dark">{{notaryInfoView.emailNotaryInfo}}</label>
                                    </div>

                                    <div class="form-group line-md">
                                        <label class="col-md-2 text-dark"><spring:message code="label.manageNotary.manageAppoint.edit.label.address"></spring:message>: </label>
                                        <label class="col-md-4 text-dark">{{notaryInfoView.addressResident}}</label>
                                        <label class="col-md-2 text-dark">Địa chỉ tạm trú: </label>
                                        <label class="col-md-4 text-dark">{{notaryInfoView.addressNow}}</label>
                                    </div>

                                    <div class="form-group line-md">
                                        <label class="col-md-2 text-dark">Nơi dự kiến hành nghề: </label>
                                        <label class="col-md-4 text-dark">{{notaryInfoView.nameOrgNotaryInfo}}</label>
                                        <label class="col-md-2 text-dark">Số Giấy chứng nhận đạt KQ: </label>
                                        <label class="col-md-4 text-dark">{{notaryInfoView.probationaryCode}}</label>
                                    </div>

                                    <div class="form-group line-md">
                                        <label class="col-md-2 text-dark"><spring:message code="label.manageNotary.manageAppoint.edit.label.addressHeadquarters"></spring:message>: </label>
                                        <label class="col-md-4 text-dark">{{notaryInfoView.address}}</label>
                                        <label class="col-md-2 text-dark">Ngày ban hành: </label>
                                        <label class="col-md-4 text-dark">{{notaryInfoView.dateSign|date:'dd-MM-yyyy'}}</label>
                                    </div>

                                </div>

                                <header class="panel-head">
                                    <a href="javascript:void(0)"><h4 class="panel-header" data-toggle="collapse" data-target="#collapseThree">
                                        <spring:message code="label.manageNotary.manageAppoint.edit.label.titleInfoAppoint"></spring:message>
                                        </h4></a>
                                </header>
                                <div id="collapseThree" class="panel-collapse collapse in" aria-expanded="true">

                                    <div class="form-group">
                                        <label class="col-md-2 control-label text-dark"><spring:message code="label.manageNotary.manageAppoint.edit.label.status"></spring:message></label>
                                        <label class="col-md-4 control-label" style="color: {{appoint.typeAppoint == 1 ? '#4cc0c1': 'red'}}">{{appoint.typeAppoint == 1 ?"Bổ nhiệm" :"Từ chối bổ nhiệm"}}</label>
                                    </div>

                                    <div class="form-group">
                                        <label class="col-md-2 control-label text-dark">Số quyết định bổ nhiệm <span style="color: red;">(*)</span></label>
                                        <div class="col-md-4">
                                            <input ng-model="document.dispatchCode" class="form-control input-sm" data-parsley-trigger="change"
                                                   data-parsley-maxlength="50" data-parsley-maxlength-message="Tối đa 50 ký tự."
                                                   data-parsley-required="true" data-parsley-required-message="Số quyết định không được bỏ trống"/>
                                        </div>
                                        <label class="col-md-2 control-label text-dark"><spring:message code="label.manageNotary.manageAppoint.edit.label.decisionDay"></spring:message> <span style="color: red;">(*)</span></label>
                                        <div class="col-md-4">
                                            <input id="dateSign_" ng-model="document.dateSign | date:'dd-MM-yyyy'" class="datetimepicker form-control input-sm"/>
                                        </div>
                                    </div>
                                    <div ng-show="appoint.typeAppoint == '1'">
                                        <div class="form-group">
                                            <label class="col-md-2 control-label text-dark">Ngày có hiệu lực <span style="color: red;">(*)</span></label>
                                            <div class="col-md-4">
                                                <input id="effectiveDate_" ng-model="document.effectiveDate" class="datetimepicker form-control input-sm"
                                                       data-parsley-trigger="change" data-parsley-required="true" data-parsley-required-message="Ngày có hiệu lực không được để trống"/>
                                            </div>
                                        </div>
                                    </div>
                                    <div ng-show="appoint.typeAppoint == '2'">
                                        <div class="form-group">
                                            <label class="col-md-2 control-label text-dark"><spring:message code="label.manageNotary.manageAppoint.add.label.cause"></spring:message> <span style="color: red;">(*)</span></label>
                                            <div class="col-md-10">
                                                <textarea id="appointReason" class="form-control input-sm" ng-model="appoint.reason" data-parsley-trigger="change"
                                                          data-parsley-maxlength="500" data-parsley-maxlength-message="Tối đa 500 ký tự."
                                                          data-parsley-required="true" data-parsley-required-message="Lý do từ chối bổ nhiệm không được bỏ trống "></textarea>
                                            </div>
                                        </div>
                                    </div>

                                    <div class="form-group">
                                        <label class="col-md-2 control-label text-dark"><spring:message code="label.manageNotary.manageAppoint.edit.label.attachFiles"></spring:message> </label>
                                        <div class="col-md-4">
                                            <div class="col-md-12 p-l-0 p-r-0">
                                                <div class="col-md-12 p-l-0 p-r-0">
                                                    <label class="btn btn-default btn-sm pull-left" for="fileDocument">Chọn tệp</label>
                                                    <%--<label class="btn btn-primary btn-xs pull-right" style="margin-top: 5px;" for="fileDocument">Thêm file</label>--%>
                                                </div>
                                                <input id="fileDocument" type="file" class="hidden col-md-8 input-sm p-l-0"
                                                       file-model="myFile.fileDocument" custom-on-change="uploadFileDocument"/>
                                            </div>

                                            <div class="col-md-12 p-l-0 p-r-0 control-label" ng-repeat="item in myFile.listFileDocument track by $index">
                                                <div><a ng-click="removeFileDocument($index);" href="javascript:void(0)" title="Xóa file"><i class="fa fa-remove"></i></a>&nbsp;{{item.file.name}}
                                                    <a title="Tải file" href="javascript:void(0)" ng-click="downloadFileDocument($index);"><i class="fa fa-download" style="color: #3fb4b5;font-style: italic;font-size: 14px">Tải File</i></a></div>
                                            </div>
                                        </div>
                                        <label class="col-md-6" style="color:red;font-size: 12px;">
                                        <spring:message code="label.manageNotary.manageAppoint.edit.label.attachFilesNote"></spring:message>
                                        </label>
                                    </div>

                                    <div class="form-group" style="margin-bottom: 30px;">
                                        <div class="col-md-12" style="text-align: center;">
                                            <button type="button" class="btn btn-info btn-s-sm" ng-click="update();">
                                                <i class="fa fa-cloud-download"></i>
                                            <spring:message code="label.manageNotary.manageAppoint.edit.button.update"></spring:message>
                                            </button>
                                            <a href="<%=request.getContextPath()%>/quan-ly-bo-nhiem-ccv" type="button" class="btn btn-s-sm btn-gray" style="margin-left: 10px;">
                                            <spring:message code="label.manageNotary.manageAppoint.edit.button.cancel"></spring:message>
                                        </a>
                                    </div>
                                </div>

                            </div>

                        </form>
                    </div>
                </div>
            </section>
                                            <%@include file="../../popupCommon/popup.jsp" %>
        </section>
    </section>
</section>

<script>
    $("#quan-ly-bo-nhiem-ccv").addClass("active");
    $("#quan-ly-cong-chung-vien").addClass("active");
</script>