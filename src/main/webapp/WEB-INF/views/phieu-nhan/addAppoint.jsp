<%@ page isELIgnored="false" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<script src="<%=request.getContextPath()%>/assets/project/manageNotary/manageAppoint/add.js${urlCache}"></script>
<%--
    Quyết định bổ nhiệm CCV - Thêm mới bổ nhiệm CCV
--%>

<section id="content" ng-app="osp"  ng-controller="tccc">
    <section class="vbox background-white">
        <section class="scrollable padder">
            <ul class="bg-light breadcrumb no-border no-radius b-light pull-in">
                <li><a href="<%=request.getContextPath()%>/"><i class="fa fa-home"></i>&nbsp;<spring:message code="label.manageNotary.manageAppoint.add.label.homeTitle"></spring:message></a></li>
                <li><a href="<%=request.getContextPath()%>/quan-ly-bo-nhiem-ccv"><spring:message code="label.manageNotary.manageAppoint.add.label.parentTitle"></spring:message></a></li>
                <li><a href="javascript:void(0)"><spring:message code="label.manageNotary.manageAppoint.add.label.pageTitle"></spring:message></a></li>
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
                        <spring:message code="label.manageNotary.manageAppoint.add.label.titleAddAppoint"></spring:message>
                        </h4></a>
                </header>
                <div id="collapseOne" class="panel-collapse collapse in" aria-expanded="true">
                    <div class="panel-body background-xam">
                        <div class="form-horizontal">
                            <header class="panel-head">
                                <a href="javascript:void(0)"><h4 class="panel-header" data-toggle="collapse" data-target="#collapseTow">
                                    <spring:message code="label.manageNotary.manageAppoint.add.label.titleInformationProbationary"></spring:message>
                                    </h4></a>
                            </header>
                            <div id="collapseTow" class="panel-collapse collapse in" aria-expanded="true">

                                <div class="form-group line-md">
                                    <label class="col-md-2 text-dark"><spring:message code="label.manageNotary.manageAppoint.add.label.fullName"></spring:message>: </label>
                                    <label class="col-md-4 text-dark">Nguyễn Thanh Tú</label>
                                    <label class="col-md-2 text-dark"><spring:message code="label.manageNotary.manageAppoint.add.label.birthday"></spring:message>: </label>
                                    <label class="col-md-4 text-dark">18/09/1960</label>
                                </div>

                                <div class="form-group line-md">
                                    <label class="col-md-2 text-dark"><spring:message code="label.manageNotary.manageAppoint.add.label.sex"></spring:message>: </label>
                                    <label class="col-md-4 text-dark">Nam</label>
                                    <label class="col-md-2 text-dark"><spring:message code="label.manageNotary.manageAppoint.add.label.identityCard"></spring:message>: </label>
                                    <label class="col-md-4 text-dark">123456799</label>
                                </div>

                                <div class="form-group line-md">
                                    <label class="col-md-2 text-dark"><spring:message code="label.manageNotary.manageAppoint.add.label.dateRange"></spring:message>: </label>
                                    <label class="col-md-4 text-dark">12/11/2010</label>
                                    <label class="col-md-2 text-dark"><spring:message code="label.manageNotary.manageAppoint.add.label.issuedBy"></spring:message>: </label>
                                    <label class="col-md-4 text-dark">CA Hà Nội</label>
                                </div>

                                <div class="form-group line-md">
                                    <label class="col-md-2 text-dark"><spring:message code="label.manageNotary.manageAppoint.add.label.phoneNumber"></spring:message>: </label>
                                    <label class="col-md-4 text-dark">Nam</label>
                                    <label class="col-md-2 text-dark"><spring:message code="label.manageNotary.manageAppoint.add.label.email"></spring:message>: </label>
                                    <label class="col-md-4 text-dark">thanhtu123@gmail.com</label>
                                </div>

                                <div class="form-group line-md">
                                    <label class="col-md-2 text-dark"><spring:message code="label.manageNotary.manageAppoint.add.label.address"></spring:message>: </label>
                                    <label class="col-md-4 text-dark">số 28 Phạm Huy Thông, Ngọc Khánh, Ba Đình</label>
                                    <label class="col-md-2 text-dark"><spring:message code="label.manageNotary.manageAppoint.add.label.timeStartProbationary"></spring:message>: </label>
                                    <label class="col-md-4 text-dark">31/12/2020</label>
                                </div>

                                <div class="form-group line-md">
                                    <label class="col-md-2 text-dark"><spring:message code="label.manageNotary.manageAppoint.add.label.organizationPresent"></spring:message>: </label>
                                    <label class="col-md-4 text-dark">Văn phòng công chứng Nguyễn Tú</label>
                                    <label class="col-md-2 text-dark"><spring:message code="label.manageNotary.manageAppoint.add.label.timeEndProbationary"></spring:message>: </label>
                                    <label class="col-md-4 text-dark">31/12/2020</label>
                                </div>

                                <div class="form-group line-md">
                                    <label class="col-md-2 text-dark"><spring:message code="label.manageNotary.manageAppoint.add.label.addressHeadquarters"></spring:message>: </label>
                                    <label class="col-md-4 text-dark">số 28 Phạm Huy Thông, Ngọc Khánh, Ba Đình</label>
                                </div>

                            </div>
                            <header class="panel-head">
                                <a href="javascript:void(0)"><h4 class="panel-header" data-toggle="collapse" data-target="#collapseThree">
                                    <spring:message code="label.manageNotary.manageAppoint.add.label.titleInfoAppoint"></spring:message>
                                    </h4></a>
                            </header>
                            <div id="collapseThree" class="panel-collapse collapse in" aria-expanded="true">
                                <div class="form-group">
                                    <label class="col-md-2 control-label text-dark"><spring:message code="label.manageNotary.manageAppoint.add.label.decisionNumber"></spring:message> <span style="color: red;">(*)</span></label>
                                    <div class="col-md-4">
                                        <input class="form-control input-sm"/>
                                    </div>
                                    <label class="col-md-2 control-label text-dark"><spring:message code="label.manageNotary.manageAppoint.add.label.decisionDay"></spring:message> <span style="color: red;">(*)</span></label>
                                    <div class="col-md-4">
                                        <input class="datetimepicker form-control input-sm"/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-2 control-label text-dark"><spring:message code="label.manageNotary.manageAppoint.add.label.attachFiles"></spring:message> <span style="color: red;">(*)</span></label>
                                    <div class="col-md-4">
                                        <input type="file" class="form-control input-sm"/>
                                        
                                    </div>
                                    <label class="col-md-6" style="color:red;font-size: 12px;">
                                    <spring:message code="label.manageNotary.manageAppoint.add.label.attachFilesNote"></spring:message>
                                    </label>
                                </div>

                                <div class="form-group" style="margin-bottom: 30px;">
                                    <div class="col-md-12 text-center">
                                        <button type="button" class="btn btn-danger btn-s-sm" ng-click="save();">
                                            <i class="fa fa-save"></i> <spring:message code="label.manageNotary.manageAppoint.add.button.add"></spring:message>
                                        </button>
                                        <a href="<%=request.getContextPath()%>/quan-ly-bo-nhiem-ccv" type="button" class="btn btn-s-sm btn-gray" style="margin-left: 10px;">
                                        <spring:message code="label.manageNotary.manageAppoint.add.button.cancel"></spring:message>
                                    </a>
                                </div>
                            </div>

                        </div>
                        </div>
                    </div>
                </div>
            </section>

        </section>
    </section>
</section>

<script>
    $("#quan-ly-bo-nhiem-ccv").addClass("active");
    $("#quan-ly-cong-chung-vien").addClass("active");
</script>