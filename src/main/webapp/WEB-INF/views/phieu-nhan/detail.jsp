<%@ page isELIgnored="false" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<script src="<%=request.getContextPath()%>/assets/project/manageNotary/manageAppoint/detail.js${urlCache}"></script>
<%--
    chi tiết thông tin bổ nhiệm ccv
--%>
<script>
    var idNotary = '${idNotary}';
    var tab = '${tab}'
</script>
<section id="content" ng-app="osp" ng-controller="tccc">
    <section class="vbox background-white">
        <section class="scrollable padder">
            <ul class="bg-light breadcrumb no-border no-radius b-light pull-in">
                <li><a href="<%=request.getContextPath()%>/"><i class="fa fa-home"></i>&nbsp;<spring:message code="label.manageNotary.manageAppoint.detail.label.homeTitle"></spring:message></a></li>
                <li><a href="<%=request.getContextPath()%>">Quản lý công chứng viên</a></li>
                <li>Chi tiết thông tin CCV</li>
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
            <section class="panel panel-default">
                <header class="panel-heading">
                    <a href="javascript:void(0)"><h4 class="panel-title text-center font-bold font-size28" data-toggle="collapse" data-target="#collapseOne">
                            CHI TIẾT THÔNG TIN CÔNG CHỨNG VIÊN
                            </h4></a>
                </header>
                <div id="collapseOne" class="panel-collapse collapse in" aria-expanded="true">

                    <div class="panel-body background-xam">
                        <form class="form-horizontal">

                            <div class="form-group">
                                <label class="col-md-2 text-dark"><spring:message code="label.manageNotary.manageAppoint.detail.label.status"></spring:message>: </label>
                                <label class="col-md-4 text-info">{{notaryInfoView.statusNotaryInfoStr}}</label>
                            </div>

                            <div class="form-group">
                                <label class="col-md-2 text-dark"><spring:message code="label.manageNotary.manageAppoint.detail.label.fullName"></spring:message>: </label>
                                <label class="col-md-4 text-dark">{{notaryInfoView.nameNotaryInfo}}</label>
                                <label class="col-md-2 text-dark"><spring:message code="label.manageNotary.manageAppoint.detail.label.birthday"></spring:message>: </label>
                                <label class="col-md-4 text-dark">{{notaryInfoView.birthDay|date:'dd-MM-yyyy'}}</label>
                            </div>

                            <div class="form-group">
                                <label class="col-md-2 text-dark"><spring:message code="label.manageNotary.manageAppoint.detail.label.sex"></spring:message>: </label>
                                <label class="col-md-4 text-dark">{{notaryInfoView.sex == 1 ? "Nam" : "Nữ"}}</label>
                                <label class="col-md-2 text-dark"><spring:message code="label.manageNotary.manageAppoint.detail.label.identityCard"></spring:message>: </label>
                                <label class="col-md-4 text-dark">{{notaryInfoView.idNo}}</label>
                            </div>

                            <div class="form-group">
                                <label class="col-md-2 text-dark"><spring:message code="label.manageNotary.manageAppoint.detail.label.dateRange"></spring:message>: </label>
                                <label class="col-md-4 text-dark">{{notaryInfoView.idNoDate|date:'dd-MM-yyyy'}}</label>
                                <label class="col-md-2 text-dark"><spring:message code="label.manageNotary.manageAppoint.detail.label.issuedBy"></spring:message>: </label>
                                <label class="col-md-4 text-dark">{{notaryInfoView.addressIdNo}}</label>
                            </div>

                            <div class="form-group">
                                <label class="col-md-2 text-dark"><spring:message code="label.manageNotary.manageAppoint.detail.label.phoneNumber"></spring:message>: </label>
                                <label class="col-md-4 text-dark">{{notaryInfoView.phoneNumberNotaryInfo}}</label>
                                <label class="col-md-2 text-dark"><spring:message code="label.manageNotary.manageAppoint.detail.label.email"></spring:message>: </label>
                                <label class="col-md-4 text-dark">{{notaryInfoView.emailNotaryInfo}}</label>

                            </div>

                            <div class="form-group">
                                <label class="col-md-2 text-dark"><spring:message code="label.manageNotary.manageAppoint.detail.label.address"></spring:message>: </label>
                                <label class="col-md-4 text-dark">{{notaryInfoView.addressResident}}</label>
                                <label class="col-md-2 text-dark">Địa chỉ tạm trú: </label>
                                <label class="col-md-4 text-dark">{{notaryInfoView.addressNow}}</label>
                            </div>
                            <div class="form-group">
                                <label class="col-md-2 text-dark"><spring:message code="label.manageNotary.manageAppoint.detail.label.notaryOrganization"></spring:message>: </label>
                                <label class="col-md-4 text-dark">{{notaryInfoView.nameOrgNotaryInfo}}</label>
                                <label class="col-md-2 text-dark"><spring:message code="label.manageNotary.manageAppoint.detail.label.addressHeadquarters"></spring:message>: </label>
                                <label class="col-md-4 text-dark">{{notaryInfoView.address}}</label>

                            </div>

                        </form>

                    </div>

                    <ul class="nav nav-tabs">
                        <li ng-click="whenClick_bonhiem_ccv()" class="{{tab == '1' ? 'active':''}} text-center"><a class="btn btn-s-lg" style="padding: 0px; border: 1px solid #ddd;" data-toggle="tab" href="#bonhiem_ccv">
                            <spring:message code="label.manageNotary.manageAppoint.detail.label.infoAppoint"></spring:message>
                                (<span class="truong-text-colorred">{{listData_bonhiem_ccv.rowCount}}</span>)</a></li>

                        <li ng-click="whenClick_tuchoibonhiem_ccv()" class=" {{tab == '2' ? 'active':''}} text-center"><a class="btn btn-s-lg" style="padding: 0px; border: 1px solid #ddd;" data-toggle="tab" href="#tuchoibonhiem_ccv">
                            <spring:message code="label.manageNotary.manageAppoint.detail.label.infoRefuseAppoint"></spring:message>
                                (<span class="truong-text-colorred">{{listData_tuchoibonhiem_ccv.rowCount}}</span>)</a></li>

                        <li ng-click="whenClick_miennhiem_ccv()" class="{{tab == '3' ? 'active':''}} text-center"><a class="btn btn-s-lg" style="padding: 0px; border: 1px solid #ddd;" data-toggle="tab" href="#miennhiem_ccv">
                            <spring:message code="label.manageNotary.manageAppoint.detail.label.infoResignAppoint"></spring:message>
                                (<span class="truong-text-colorred">{{listData_miennhiem_ccv.rowCount}}</span>)</a></li>

                        <li ng-click="whenClick_tuchoimiennhiem_ccv()" class="{{tab == '10' ? 'active':''}} text-center"><a class="btn btn-s-lg" style="padding: 0px; border: 1px solid #ddd;" data-toggle="tab" href="#tuchoimiennhiem_ccv">
                                Thông tin từ chối<br/>miễn nhiệm CCV
                                (<span class="truong-text-colorred">{{listData_tcmiennhiem_ccv.rowCount}}</span>)</a></li>

                        <li ng-click="whenClick_bonhiemlai_ccv()" class="{{tab == '4' ? 'active':''}} text-center"><a class="btn btn-s-lg" style="padding: 0px; border: 1px solid #ddd;" data-toggle="tab" href="#bonhiemlai_ccv">
                            <spring:message code="label.manageNotary.manageAppoint.detail.label.infoReAppointed"></spring:message>
                                (<span class="truong-text-colorred">{{listData_bonhiemlai_ccv.rowCount}}</span>)</a></li>

                        <li ng-click="whenClick_tuchoibonhiemlai_ccv()" class="{{tab == '12' ? 'active':''}} text-center"><a class="btn btn-s-lg" style="padding: 0px; border: 1px solid #ddd;" data-toggle="tab" href="#tuchoibonhiemlai_ccv">
                            Thông tin từ chối<br/> bổ nhiệm lại CCV
                            (<span class="truong-text-colorred">{{listData_tuchoibonhiemlai_ccv.rowCount}}</span>)</a></li>

                        <li ng-click="whenClick_dky_hncc_ccv()" class="{{tab == '5' ? 'active':''}} text-center"><a class="btn btn-s-lg" style="padding: 0px; border: 1px solid #ddd;" data-toggle="tab" href="#dky_hncc_ccv">
                                Đăng ký HNCC<br/> và cấp thẻ CCV
                                (<span class="truong-text-colorred">{{listData_dky_hncc_ccv.rowCount}}</span>)</a></li>

                        <li ng-click="whenClick_dinhchi_hncc_ccv()" class="{{tab == '6' ? 'active':''}} text-center"><a class="btn btn-s-lg" style="padding: 0px; border: 1px solid #ddd;" data-toggle="tab" href="#dinhchi_hncc_ccv">
                                Tạm đình <br/> chỉ HNCC
                                (<span class="truong-text-colorred">{{listData_dinhchi_hncc_ccv.rowCount}}</span>)</a></li>

                        <li ng-show="false" ng-click="whenClick_huy_dinhchi_hncc_ccv()" class="{{tab == '13' ? 'active':''}} text-center"><a class="btn btn-s-lg" style="padding: 0px; border: 1px solid #ddd;" data-toggle="tab" href="#huy_dinhchi_hncc_ccv">
                            Hủy tạm đình <br/> chỉ HNCC
                            (<span class="truong-text-colorred">{{listData_huy_dinhchi_hncc_ccv.rowCount}}</span>)</a></li>

                        <li ng-click="whenClick_caplaithe_ccv()" class="{{tab == '7' ? 'active':''}} text-center"><a class="btn btn-s-lg" style="padding: 0px; border: 1px solid #ddd;" data-toggle="tab" href="#caplaithe_ccv">
                                Cấp lại <br/> thẻ CCV
                                (<span class="truong-text-colorred">{{listData_caplaithe_ccv.rowCount}}</span>)</a></li>

                        <li ng-show="false" ng-click="whenClick_tuchoicaplaithe_ccv()" class="{{tab == '11' ? 'active':''}} text-center"><a class="btn btn-s-lg" style="padding: 0px; border: 1px solid #ddd;" data-toggle="tab" href="#tuchoicaplaithe_ccv">
                                Từ chối cấp lại <br/> thẻ CCV
                                (<span class="truong-text-colorred">{{listData_tuchoicaplaithe_ccv.rowCount}}</span>)</a></li>

                        <li ng-click="whenClick_thuhoithe_ccv()" class="{{tab == '8' ? 'active':''}} text-center"><a class="btn btn-s-lg" style="padding: 0px; border: 1px solid #ddd;" data-toggle="tab" href="#thuhoithe_ccv">
                                Xóa đăng ký HNCC <br/>và thu hồi thẻ CCV
                                (<span class="truong-text-colorred">{{listData_thuhoithe_ccv.rowCount}}</span>)</a></li>

                        <li ng-click="whenClick_xuphat_ccv()" class="{{tab == '9' ? 'active':''}} text-center"><a class="btn btn-s-lg" style="padding: 0px; border: 1px solid #ddd;" data-toggle="tab" href="#xuphat_ccv">
                                Xử lý <br/>vi phạm
                                (<span class="truong-text-colorred">{{listData_xuphat_ccv.rowCount}}</span>)</a></li>
                    </ul>


                    <div class="table-responsive tab-content bg-white">

                        <div id="bonhiem_ccv" class="tab-pane fade in">
                            <table class="table b-t b-light table-bordered table-hover" style="margin-bottom: 0px;" ng-switch on="listData_bonhiem_ccv.rowCount">
                                <thead class="bg-gray">
                                    <tr>
                                        <th class="text-center v-inherit text-dark"><spring:message code="label.manageNotary.manageAppoint.detail.label.numericalOrder"></spring:message></th>
                                        <th class="text-center v-inherit text-dark">Xem</th>
                                        <th class="text-left v-inherit text-dark">Loại quyết định</th>
                                        <th class="text-left v-inherit text-dark">Số quyết định bổ nhiệm/<br>đề nghị bổ nhiệm</th>
                                        <th class="text-left v-inherit text-dark">Ngày quyết định</th>
                                        <th class="text-left v-inherit text-dark">Ngày có hiệu lực</th>
                                        <th class="text-left v-inherit text-dark">Người ký</th>
                                        <th class="text-left v-inherit text-dark">File đính kèm</th>

                                    </tr>
                                </thead>
                                <tbody class="background-white">
                                    <tr ng-switch-when="0">
                                        <td colspan="8" style="height: 100%;background-color: #ececec; line-height: 3.429;text-align: center; font-style: italic;">
                                            Không có dữ liệu
                                        </td>
                                    </tr>
                                    <tr ng-switch-default ng-repeat="item in listData_bonhiem_ccv.items track by $index">
                                        <th class="text-center v-inherit" >{{$index + 1}}</th>
                                        <td class="text-center"><a ng-click="getNotaryHisByType(item.idAppoint,notaryInfoView.idNotaryInfo, 1, item.typeAppoint);" href="javascript:void(0);"><i class="fa fa-1-5x fa-eye"></i></a></td>
                                        <td class="text-left v-inherit">{{item.name}}</td>
                                        <td class="text-left v-inherit">{{item.dispatchCode}}</td>
                                        <td class="text-left v-inherit">{{item.dateSign|date:'dd-MM-yyyy'}}</td>
                                        <td class="text-left v-inherit">{{item.effectiveDate|date:'dd-MM-yyyy'}}</td>
                                        <td class="text-left v-inherit">{{item.signer}}</td>
                                        <td class="text-left v-inherit">
                                            <div ng-repeat="em in item.linkFile.split(';') track by $index">
                                                <a style="color: #3fb4b5;text-decoration: underline" title="Tải file" href="javascript:void(0)" ng-click="download(item.fileName.split(';')[$index], item.linkFile.split(';')[$index]);">{{item.fileName.split(';')[$index]}}</a><br>
                                            </div>
                                        </td>
                                    </tr>
                                </tbody>

                            </table>
                        </div>

                        <div id="tuchoibonhiem_ccv" class="tab-pane fade in">
                            <table class="table b-t b-light table-bordered table-hover" style="margin-bottom: 0px;" ng-switch on="listData_tuchoibonhiem_ccv.rowCount">
                                <thead class="bg-gray">
                                <tr>
                                    <th class="text-center v-inherit text-dark"><spring:message code="label.manageNotary.manageAppoint.detail.label.numericalOrder"></spring:message></th>
                                    <th class="text-center v-inherit text-dark">Xem</th>
                                    <th class="text-left v-inherit text-dark">Số quyết định từ chối bổ nhiệm</th>
                                    <th class="text-left v-inherit text-dark">Ngày quyết định</th>
                                    <th class="text-left v-inherit text-dark">Người ký</th>
                                    <th class="text-left v-inherit text-dark">Lý do từ chối bổ nhiệm</th>
                                    <th class="text-left v-inherit text-dark">File đính kèm</th>
                                </tr>
                                </thead>
                                <tbody class="background-white">
                                <tr ng-switch-when="0">
                                    <td colspan="7" style="height: 100%;background-color: #ececec; line-height: 3.429;text-align: center; font-style: italic;">
                                        Không có dữ liệu
                                    </td>
                                </tr>
                                <tr ng-switch-default ng-repeat="item in listData_tuchoibonhiem_ccv.items track by $index">
                                    <th class="text-center v-inherit">{{$index + 1}}</th>
                                    <td class="text-center"><a ng-click="getNotaryHisByType(item.idAppoint,notaryInfoView.idNotaryInfo, 2, item.typeAppoint);" href="javascript:void(0);"><i class="fa fa-1-5x fa-eye"></i></a></td>
                                    <td class="text-left v-inherit">{{item.dispatchCode}}</td>
                                    <td class="text-left v-inherit">{{item.dateSign|date:'dd-MM-yyyy'}}</td>
                                    <td class="text-left v-inherit">{{item.signer}}</td>
                                    <td class="text-left v-inherit">{{item.reason}}</td>
                                    <td class="text-left v-inherit ">
                                        <div ng-repeat="em in item.linkFile.split(';') track by $index">
                                            <a style="color: #3fb4b5;text-decoration: underline" title="Tải file" href="javascript:void(0)" ng-click="download(item.fileName.split(';')[$index], item.linkFile.split(';')[$index]);">{{item.fileName.split(';')[$index]}}</a><br>
                                        </div>
                                    </td>
                                </tr>
                                </tbody>
                            </table>
                        </div>

                        <div id="miennhiem_ccv" class="tab-pane fade in">
                            <table class="table b-t b-light table-bordered table-hover" style="margin-bottom: 0px;" ng-switch on="listData_miennhiem_ccv.rowCount">
                                <thead class="bg-gray">
                                <tr>
                                    <th class="text-center v-inherit text-dark"><spring:message code="label.manageNotary.manageAppoint.detail.label.numericalOrder"></spring:message></th>
                                    <th class="text-center v-inherit text-dark">Xem</th>
                                    <th class="text-left v-inherit text-dark">Loại quyết định</th>
                                    <th class="text-left v-inherit text-dark">Số quyết định miễn nhiệm/<br>đề nghị miễn nhiệm</th>
                                    <th class="text-left v-inherit text-dark">Ngày quyết định</th>
                                    <th class="text-left v-inherit text-dark">Người ký</th>
                                    <th class="text-left v-inherit text-dark">Ngày có hiệu lực</th>
                                    <th class="text-left v-inherit text-dark">Trường hợp miễn nhiệm</th>
                                    <th class="text-left v-inherit text-dark">Lý do miễn nhiệm</th>
                                    <th class="text-left v-inherit text-dark">Ghi chú</th>
                                    <th class="text-left v-inherit text-dark">File đính kèm</th>
                                </tr>
                                </thead>
                                <tbody class="background-white">
                                <tr ng-switch-when="0">
                                    <td colspan="11" style="height: 100%;background-color: #ececec; line-height: 3.429;text-align: center; font-style: italic;">
                                        Không có dữ liệu
                                    </td>
                                </tr>
                                <tr ng-switch-default ng-repeat="item in listData_miennhiem_ccv.items track by $index">
                                    <th class="text-center v-inherit">{{$index + 1}}</th>
                                    <td class="text-center"><a ng-click="getNotaryHisByType(item.idDismissed,notaryInfoView.idNotaryInfo, 3, item.status);" href="javascript:void(0);"><i class="fa fa-1-5x fa-eye"></i></a></td>
                                    <td class="text-left v-inherit">{{item.name}}</td>
                                    <td class="text-left v-inherit">{{item.dispatchCode}}</td>
                                    <td class="text-left v-inherit">{{item.dateSign|date:'dd-MM-yyyy'}}</td>
                                    <td class="text-left v-inherit">{{item.signer}}</td>
                                    <td class="text-left v-inherit">{{item.effectiveDate|date:'dd-MM-yyyy'}}</td>
                                    <td class="text-left v-inherit">{{item.typeDismissed == '1' ? 'Bị miễn nhiệm' : item.typeDismissed == '2'? 'Được miễn nhiệm': 'BTP tự miễn nhiệm CCV'}}</td>
                                    <td class="text-left v-inherit">{{item.reason}}</td>
                                    <td class="text-left v-inherit">{{item.note}}</td>
                                    <td class="text-left v-inherit ">
                                        <div ng-repeat="em in item.linkFile.split(';') track by $index">
                                            <a style="color: #3fb4b5;text-decoration: underline" title="Tải file" href="javascript:void(0)" ng-click="download(item.fileName.split(';')[$index], item.linkFile.split(';')[$index]);">{{item.fileName.split(';')[$index]}}</a><br>
                                        </div>
                                    </td>
                                </tr>
                                </tbody>
                            </table>
                        </div>

                        <div id="tuchoimiennhiem_ccv" class="tab-pane fade in">
                            <table class="table b-t b-light table-bordered table-hover" style="margin-bottom: 0px;" ng-switch on="listData_tcmiennhiem_ccv.rowCount">
                                <thead class="bg-gray">
                                <tr>
                                    <th class="text-center v-inherit text-dark"><spring:message code="label.manageNotary.manageAppoint.detail.label.numericalOrder"></spring:message></th>
                                    <th class="text-center v-inherit text-dark">Xem</th>
                                    <th class="text-center v-inherit text-dark">Số văn bản từ chối miễn nhiệm</th>
                                    <th class="text-center v-inherit text-dark">Ngày quyết định</th>
                                    <th class="text-center v-inherit text-dark">Người ký</th>
                                    <th class="text-center v-inherit text-dark">Lý do miễn nhiệm</th>
                                    <th class="text-center v-inherit text-dark">File đính kèm</th>
                                </tr>
                                </thead>
                                <tbody class="background-white">
                                <tr ng-switch-when="0">
                                    <td colspan="7" style="height: 100%;background-color: #ececec; line-height: 3.429;text-align: center; font-style: italic;">
                                        Không có dữ liệu
                                    </td>
                                </tr>
                                <tr ng-switch-default ng-repeat="item in listData_tcmiennhiem_ccv.items track by $index">
                                    <th class="text-center v-inherit">{{$index + 1}}</th>
                                    <td class="text-center"><a ng-click="getNotaryHisByType(item.idDismissed,notaryInfoView.idNotaryInfo, 10, item.status);" href="javascript:void(0);"><i class="fa fa-1-5x fa-eye"></i></a></td>
                                    <td class="text-left v-inherit">{{item.dispatchCode}}</td>
                                    <td class="text-left v-inherit">{{item.dateSign|date:'dd-MM-yyyy'}}</td>
                                    <td class="text-left v-inherit">{{item.signer}}</td>
                                    <td class="text-left v-inherit">{{item.reason}}</td>
                                    <td class="text-left v-inherit " >
                                        <div ng-repeat="em in item.linkFile.split(';') track by $index">
                                            <a style="color: #3fb4b5;text-decoration: underline" title="Tải file" href="javascript:void(0)" ng-click="download(item.fileName.split(';')[$index], item.linkFile.split(';')[$index]);">{{item.fileName.split(';')[$index]}}</a><br>
                                        </div>
                                    </td>
                                </tr>
                                </tbody>
                            </table>
                        </div>

                        <div id="bonhiemlai_ccv" class="tab-pane fade in">
                            <table class="table b-t b-light table-bordered table-hover" style="margin-bottom: 0px;" ng-switch on="listData_bonhiemlai_ccv.rowCount">
                                <thead class="bg-gray">
                                <tr>
                                    <th class="text-center v-inherit text-dark"><spring:message code="label.manageNotary.manageAppoint.detail.label.numericalOrder"></spring:message></th>
                                    <th class="text-center v-inherit text-dark">Xem</th>
                                    <th class="text-left v-inherit text-dark">Số quyết định bổ nhiệm lại/<br>đề nghị bổ nhiệm lại</th>
                                    <th class="text-left v-inherit text-dark">Ngày quyết định</th>
                                    <th class="text-left v-inherit text-dark">Người ký</th>
                                    <th class="text-left v-inherit text-dark">Ngày có hiệu lực</th>
                                    <%--<th class="text-left v-inherit text-dark">Lý do bổ nhiệm lại</th>--%>
                                    <th class="text-left v-inherit text-dark">File đính kèm</th>
                                </tr>
                                </thead>
                                <tbody class="background-white">
                                <tr ng-switch-when="0">
                                    <td colspan="7" style="height: 100%;background-color: #ececec; line-height: 3.429;text-align: center; font-style: italic;">
                                        Không có dữ liệu
                                    </td>
                                </tr>
                                <tr ng-switch-default ng-repeat="item in listData_bonhiemlai_ccv.items track by $index">
                                    <th class="text-center v-inherit">{{$index + 1}}</th>
                                    <td class="text-center"><a ng-click="getNotaryHisByType(item.idReAppoint,notaryInfoView.idNotaryInfo, 4, item.typeAppoint);" href="javascript:void(0);"><i class="fa fa-1-5x fa-eye"></i></a></td>
                                    <td class="text-left v-inherit">{{item.dispatchCode}}</td>
                                    <td class="text-left v-inherit">{{item.dateSign|date:'dd-MM-yyyy'}}</td>
                                    <td class="text-left v-inherit">{{item.signer}}</td>
                                    <td class="text-left v-inherit">{{item.effectiveDate|date:'dd-MM-yyyy'}}</td>
                                    <%--<td class="text-left v-inherit">{{item.reason}}</td>--%>
                                    <td class="text-left v-inherit">
                                        <div ng-repeat="em in item.linkFile.split(';') track by $index">
                                            <a style="color: #3fb4b5;text-decoration: underline" title="Tải file" href="javascript:void(0)" ng-click="download(item.fileName.split(';')[$index], item.linkFile.split(';')[$index]);">{{item.fileName.split(';')[$index]}}</a><br>
                                        </div>
                                    </td>

                                </tr>
                                </tbody>
                            </table>
                        </div>

                        <div id="dky_hncc_ccv" class="tab-pane fade in">
                            <table class="table b-t b-light table-bordered table-hover" style="margin-bottom: 0px;" ng-switch on="listData_dky_hncc_ccv.rowCount">
                                <thead class="bg-gray">
                                <tr>
                                    <th class="text-center v-inherit text-dark"><spring:message code="label.manageNotary.manageAppoint.detail.label.numericalOrder"></spring:message></th>
                                    <th class="text-center v-inherit text-dark">Xem</th>
                                    <th class="text-left v-inherit text-dark">Trạng thái</th>
                                    <th class="text-left v-inherit text-dark">Số quyết định cấp Thẻ CCV/ Số văn bản thông báo</th>
                                    <th class="text-left v-inherit text-dark">Ngày quyết định/ Ngày thông báo</th>
                                    <th class="text-left v-inherit text-dark">Người ký</th>
                                    <th class="text-left v-inherit text-dark">Ngày có hiệu lực</th>
                                    <th class="text-left v-inherit text-dark">Số thẻ CCV</th>
                                    <th class="text-left v-inherit text-dark">Tổ chức HNCC</th>
                                    <th class="text-left v-inherit text-dark">Lý do từ chối cấp Thẻ</th>
                                    <th class="text-left v-inherit text-dark">File đính kèm</th>
                                </tr>
                                </thead>
                                <tbody class="background-white">
                                <tr ng-switch-when="0">
                                    <td colspan="11" style="height: 100%;background-color: #ececec; line-height: 3.429;text-align: center; font-style: italic;">
                                        Không có dữ liệu
                                    </td>
                                </tr>
                                <tr ng-switch-default ng-repeat="item in listData_dky_hncc_ccv.items track by $index">
                                    <th class="text-center v-inherit">{{$index + 1}}</th>
                                    <td class="text-center"><a ng-click="getNotaryHisByType(item.idNotaryRegPractice,notaryInfoView.idNotaryInfo, 5, item.status_prac);" href="javascript:void(0);"><i class="fa fa-1-5x fa-eye"></i></a></td>
                                    <td class="text-left v-inherit">{{item.status_prac_Str}}</td>
                                    <td class="text-left v-inherit">{{item.dispatchCode}}</td>
                                    <td class="text-left v-inherit">{{item.dateSign|date:'dd-MM-yyyy'}}</td>
                                    <td class="text-left v-inherit">{{item.signer}}</td>
                                    <td class="text-left v-inherit">{{item.effectiveDate|date:'dd-MM-yyyy'}}</td>
                                    <td class="text-left v-inherit">{{item.numberCad}}</td>
                                    <td class="text-left v-inherit">{{item.nameOrgNotaryInfo}}</td>
                                    <td class="text-left v-inherit">{{item.reason}}</td>
                                    <td class="text-left v-inherit">
                                        <div ng-repeat="em in item.linkFile.split(';') track by $index">
                                            <a style="color: #3fb4b5;text-decoration: underline" title="Tải file" href="javascript:void(0)" ng-click="download(item.fileName.split(';')[$index], item.linkFile.split(';')[$index]);">{{item.fileName.split(';')[$index]}}</a><br>
                                        </div>
                                    </td>
                                </tr>
                                </tbody>
                            </table>
                        </div>

                        <div id="dinhchi_hncc_ccv" class="tab-pane fade in">
                            <table class="table b-t b-light table-bordered table-hover" style="margin-bottom: 0px;" ng-switch on="listData_dinhchi_hncc_ccv.rowCount">
                                <thead class="bg-gray">
                                <tr>
                                    <th class="text-center v-inherit text-dark"><spring:message code="label.manageNotary.manageAppoint.detail.label.numericalOrder"></spring:message></th>
                                    <th class="text-center v-inherit text-dark">Xem</th>
                                    <th class="text-left v-inherit text-dark">Trạng thái</th>
                                    <th class="text-left v-inherit text-dark">Số quyết định tạm đình chỉ/<br> hủy bỏ QĐ tạm đình chỉ</th>
                                    <th class="text-left v-inherit text-dark">Ngày quyết định</th>
                                    <th class="text-left v-inherit text-dark">Ngày có hiệu lực</th>
                                    <th class="text-left v-inherit text-dark">Thời gian tạm đình chỉ</th>
                                    <th class="text-left v-inherit text-dark">Người ký</th>
                                    <th class="text-left v-inherit text-dark">Lý do tạm đình chỉ/<br> hủy bỏ QĐ tạm đình chỉ</th>
                                    <th class="text-left v-inherit text-dark">File đính kèm</th>
                                </tr>
                                </thead>
                                <tbody class="background-white">
                                <tr ng-switch-when="0">
                                    <td colspan="10" style="height: 100%;background-color: #ececec; line-height: 3.429;text-align: center; font-style: italic;">
                                        Không có dữ liệu
                                    </td>
                                </tr>
                                <tr ng-switch-default ng-repeat="item in listData_dinhchi_hncc_ccv.items track by $index">
                                    <th class="text-center v-inherit">{{$index + 1}}</th>
                                    <td class="text-center"><a ng-click="getNotaryHisByType(item.supendId,notaryInfoView.idNotaryInfo, 6, item.typeSupend);" href="javascript:void(0);"><i class="fa fa-1-5x fa-eye"></i></a></td>
                                    <td class="text-left v-inherit">{{item.typeSupendStr}}</td>
                                    <td class="text-left v-inherit">{{item.dispatchCode}}</td>
                                    <td class="text-left v-inherit">{{item.dateSign|date:'dd-MM-yyyy'}}</td>
                                    <td class="text-left v-inherit">{{item.effectiveDate|date:'dd-MM-yyyy'}}</td>
                                    <td class="text-left v-inherit">{{item.termOfSuspension|date:'dd-MM-yyyy'}}</td>
                                    <td class="text-left v-inherit">{{item.signer}}</td>
                                    <td class="text-left v-inherit">{{item.reason}}</td>
                                    <td class="text-left v-inherit">
                                        <div ng-repeat="em in item.linkFile.split(';') track by $index">
                                            <a style="color: #3fb4b5;text-decoration: underline" title="Tải file" href="javascript:void(0)" ng-click="download(item.fileName.split(';')[$index], item.linkFile.split(';')[$index]);">{{item.fileName.split(';')[$index]}}</a><br>
                                        </div>
                                    </td>
                                </tr>
                                </tbody>
                            </table>
                        </div>

                        <div id="huy_dinhchi_hncc_ccv" class="tab-pane fade in">
                            <table class="table b-t b-light table-bordered table-hover" style="margin-bottom: 0px;" ng-switch on="listData_huy_dinhchi_hncc_ccv.rowCount">
                                <thead class="bg-gray">
                                <tr>
                                    <th class="text-center v-inherit text-dark"><spring:message code="label.manageNotary.manageAppoint.detail.label.numericalOrder"></spring:message></th>
                                    <th class="text-center v-inherit text-dark">Xem</th>
                                    <th class="text-left v-inherit text-dark">Số quyết định hủy tạm đình chỉ</th>
                                    <th class="text-left v-inherit text-dark">Ngày quyết định</th>
                                    <th class="text-left v-inherit text-dark">Người ký</th>
                                    <th class="text-left v-inherit text-dark">Ngày có hiệu lực</th>
                                    <th class="text-left v-inherit text-dark">Lý do hủy tạm đình chỉ</th>
                                    <th class="text-left v-inherit text-dark">File đính kèm</th>
                                </tr>
                                </thead>
                                <tbody class="background-white">
                                <tr ng-switch-when="0">
                                    <td colspan="8" style="height: 100%;background-color: #ececec; line-height: 3.429;text-align: center; font-style: italic;">
                                        Không có dữ liệu
                                    </td>
                                </tr>
                                <tr ng-switch-default ng-repeat="item in listData_huy_dinhchi_hncc_ccv.items track by $index">
                                    <th class="text-center v-inherit">{{$index + 1}}</th>
                                    <td class="text-center"><a ng-click="getNotaryHisByType(item.supendId,notaryInfoView.idNotaryInfo, 13, item.typeSupend);" href="javascript:void(0);"><i class="fa fa-1-5x fa-eye"></i></a></td>
                                    <td class="text-left v-inherit">{{item.dispatchCode}}</td>
                                    <td class="text-left v-inherit">{{item.dateSign|date:'dd-MM-yyyy'}}</td>
                                    <td class="text-left v-inherit">{{item.signer}}</td>
                                    <td class="text-left v-inherit">{{item.effectiveDate|date:'dd-MM-yyyy'}}</td>
                                    <td class="text-left v-inherit">{{item.reason}}</td>
                                    <td class="text-left v-inherit">
                                        <div ng-repeat="em in item.linkFile.split(';') track by $index">
                                            <a style="color: #3fb4b5;text-decoration: underline" title="Tải file" href="javascript:void(0)" ng-click="download(item.fileName.split(';')[$index], item.linkFile.split(';')[$index]);">{{item.fileName.split(';')[$index]}}</a><br>
                                        </div>
                                    </td>
                                </tr>
                                </tbody>
                            </table>
                        </div>

                        <div id="caplaithe_ccv" class="tab-pane fade in">
                            <table class="table b-t b-light table-bordered table-hover" style="margin-bottom: 0px;" ng-switch on="listData_caplaithe_ccv.rowCount">
                                <thead class="bg-gray">
                                    <tr>
                                        <th class="text-center v-inherit text-dark"><spring:message code="label.manageNotary.manageAppoint.detail.label.numericalOrder"></spring:message></th>
                                        <th class="text-center v-inherit text-dark">Xem</th>
                                        <th class="text-left v-inherit text-dark">Trạng thái</th>
                                        <th class="text-left v-inherit text-dark">Số quyết định cấp lại Thẻ/<br> Số văn bản thông báo</th>
                                        <th class="text-left v-inherit text-dark">Ngày quyết định/<br> Ngày thông báo</th>
                                        <th class="text-left v-inherit text-dark">Số thẻ CCV</th>
                                        <th class="text-left v-inherit text-dark">Người ký</th>
                                        <th class="text-left v-inherit text-dark">Ngày có hiệu lực</th>
                                        <th class="text-left v-inherit text-dark">Lý do cấp lại Thẻ CCV/<br> từ chối cấp lại Thẻ</th>
                                        <th class="text-left v-inherit text-dark">File đính kèm</th>
                                    </tr>
                                </thead>
                                <tbody class="background-white">
                                    <tr ng-switch-when="0">
                                        <td colspan="10" style="height: 100%;background-color: #ececec; line-height: 3.429;text-align: center; font-style: italic;">
                                            Không có dữ liệu
                                        </td>
                                    </tr>
                                    <tr ng-switch-default ng-repeat="item in listData_caplaithe_ccv.items track by $index">
                                        <th class="text-center v-inherit">{{$index + 1}}</th>
                                        <td class="text-center"><a ng-click="getNotaryHisByType(item.idNotaryCard,notaryInfoView.idNotaryInfo, 7, item.statusNotaryCard);" href="javascript:void(0);"><i class="fa fa-1-5x fa-eye"></i></a></td>
                                        <td class="text-left v-inherit">{{item.statusNotaryCardStr}}</td>
                                        <td class="text-left v-inherit">{{item.dispatchCode}}</td>
                                        <td class="text-left v-inherit">{{item.dateSign|date:'dd-MM-yyyy'}}</td>
                                        <td class="text-left v-inherit">{{item.numberCad}}</td>
                                        <td class="text-left v-inherit">{{item.signer}}</td>
                                        <td class="text-left v-inherit">{{item.effectiveDate|date:'dd-MM-yyyy'}}</td>
                                        <td class="text-left v-inherit">{{item.reason}}</td>
                                        <td class="text-left v-inherit">
                                            <div ng-repeat="em in item.linkFile.split(';') track by $index">
                                                <a style="color: #3fb4b5;text-decoration: underline" title="Tải file" href="javascript:void(0)" ng-click="download(item.fileName.split(';')[$index], item.linkFile.split(';')[$index]);">{{item.fileName.split(';')[$index]}}</a><br>
                                            </div>
                                        </td>
                                    </tr>
                                </tbody>
                            </table>
                        </div>

                        <div id="tuchoicaplaithe_ccv" class="tab-pane fade in">
                            <table class="table b-t b-light table-bordered table-hover" style="margin-bottom: 0px;" ng-switch on="listData_tuchoicaplaithe_ccv.rowCount">
                                <thead class="bg-gray">
                                <tr>
                                    <th class="text-center v-inherit text-dark"><spring:message code="label.manageNotary.manageAppoint.detail.label.numericalOrder"></spring:message></th>
                                    <th class="text-left v-inherit text-dark">Số quyết định từ chối cấp lại thẻ</th>
                                    <th class="text-left v-inherit text-dark">Ngày quyết định</th>
                                    <th class="text-left v-inherit text-dark">Người ký</th>
                                    <%--<th class="text-left v-inherit text-dark">Ngày có hiệu lực</th>--%>
                                    <th class="text-left v-inherit text-dark">Lý do từ chối cấp lại thẻ</th>
                                    <th class="text-left v-inherit text-dark">File đính kèm</th>
                                </tr>
                                </thead>
                                <tbody class="background-white">
                                <tr ng-switch-when="0">
                                    <td colspan="6" style="height: 100%;background-color: #ececec; line-height: 3.429;text-align: center; font-style: italic;">
                                        Không có dữ liệu
                                    </td>
                                </tr>
                                <tr ng-switch-default ng-repeat="item in listData_tuchoicaplaithe_ccv.items track by $index">
                                    <th class="text-center v-inherit">{{$index + 1}}</th>
                                    <td class="text-left v-inherit">{{item.dispatchCode}}</td>
                                    <td class="text-left v-inherit">{{item.dateSign|date:'dd-MM-yyyy'}}</td>
                                    <td class="text-left v-inherit">{{item.signer}}</td>
                                    <%--<td class="text-left v-inherit">{{item.effectiveDate|date:'dd-MM-yyyy'}}</td>--%>
                                    <td class="text-left v-inherit">{{item.reason}}</td>
                                    <td class="text-left v-inherit">
                                        <div ng-repeat="em in item.linkFile.split(';') track by $index">
                                            <a style="color: #3fb4b5;text-decoration: underline" title="Tải file" href="javascript:void(0)" ng-click="download(item.fileName.split(';')[$index], item.linkFile.split(';')[$index]);">{{item.fileName.split(';')[$index]}}</a><br>
                                        </div>
                                    </td>
                                </tr>
                                </tbody>
                            </table>
                        </div>

                        <div id="thuhoithe_ccv" class="tab-pane fade in">
                            <table class="table b-t b-light table-bordered table-hover" style="margin-bottom: 0px;" ng-switch on="listData_thuhoithe_ccv.rowCount">
                                <thead class="bg-gray">
                                <tr>
                                    <th class="text-center v-inherit text-dark"><spring:message code="label.manageNotary.manageAppoint.detail.label.numericalOrder"></spring:message></th>
                                    <th class="text-center v-inherit text-dark">Xem</th>
                                    <th class="text-left v-inherit text-dark">Số quyết định thu hồi thẻ</th>
                                    <th class="text-left v-inherit text-dark">Ngày quyết định</th>
                                    <th class="text-left v-inherit text-dark">Người ký</th>
                                    <th class="text-left v-inherit text-dark">Ngày có hiệu lực</th>
                                    <th class="text-left v-inherit text-dark">Lý do thu hồi thẻ</th>
                                    <th class="text-left v-inherit text-dark">File đính kèm</th>
                                </tr>
                                </thead>
                                <tbody class="background-white">
                                <tr ng-switch-when="0">
                                    <td colspan="8" style="height: 100%;background-color: #ececec; line-height: 3.429;text-align: center; font-style: italic;">
                                        Không có dữ liệu
                                    </td>
                                </tr>
                                <tr ng-switch-default ng-repeat="item in listData_thuhoithe_ccv.items track by $index">
                                    <th class="text-center v-inherit">{{$index + 1}}</th>
                                    <td class="text-center"><a ng-click="getNotaryHisByType(item.idNotaryCard,notaryInfoView.idNotaryInfo, 8, item.typeDocument);" href="javascript:void(0);"><i class="fa fa-1-5x fa-eye"></i></a></td>
                                    <td class="text-left v-inherit">{{item.dispatchCode}}</td>
                                    <td class="text-left v-inherit">{{item.dateSign|date:'dd-MM-yyyy'}}</td>
                                    <td class="text-left v-inherit">{{item.signer}}</td>
                                    <td class="text-left v-inherit">{{item.effectiveDate|date:'dd-MM-yyyy'}}</td>
                                    <td class="text-left v-inherit">{{item.reason}}</td>
                                    <td class="text-left v-inherit">
                                        <div ng-repeat="em in item.linkFile.split(';') track by $index">
                                            <a style="color: #3fb4b5;text-decoration: underline" title="Tải file" href="javascript:void(0)" ng-click="download(item.fileName.split(';')[$index], item.linkFile.split(';')[$index]);">{{item.fileName.split(';')[$index]}}</a><br>
                                        </div>
                                    </td>
                                </tr>
                                </tbody>
                            </table>
                        </div>

                        <div id="xuphat_ccv" class="tab-pane fade in">
                            <table class="table b-t b-light table-bordered table-hover" style="margin-bottom: 0px;" ng-switch on="listData_xuphat_ccv.rowCount">
                                <thead class="bg-gray">
                                <tr>
                                    <th class="text-center v-inherit text-dark"><spring:message code="label.manageNotary.manageAppoint.detail.label.numericalOrder"></spring:message></th>
                                    <th class="text-center v-inherit text-dark">Xem</th>
                                    <th class="text-left v-inherit text-dark">Số quyết định xử lý vi phạm</th>
                                    <th class="text-left v-inherit text-dark">Ngày quyết định</th>
                                    <th class="text-left v-inherit text-dark">Người ký</th>
                                    <th class="text-left v-inherit text-dark">Ngày có hiệu lực</th>
                                    <th class="text-left v-inherit text-dark">Loại xử lý vi phạm</th>
                                    <th class="text-left v-inherit text-dark">Đơn vị xử lý</th>
                                    <th class="text-left v-inherit text-dark">Hình thức kỷ luật</th>
                                    <th class="text-left v-inherit text-dark">Hình thức xử phạt bổ sung</th>
                                    <th class="text-left v-inherit text-dark">Số tiền</th>
                                    <th class="text-left v-inherit text-dark">Lý do</th>
                                    <th class="text-left v-inherit text-dark">File đính kèm</th>
                                </tr>
                                </thead>
                                <tbody class="background-white">
                                <tr ng-switch-when="0">
                                    <td colspan="13" style="height: 100%;background-color: #ececec; line-height: 3.429;text-align: center; font-style: italic;">
                                        Không có dữ liệu
                                    </td>
                                </tr>
                                <tr ng-switch-default ng-repeat="item in listData_xuphat_ccv.items track by $index">
                                    <td class="text-center v-inherit">{{$index + 1}}</td>
                                    <td class="text-center"><a ng-click="getNotaryHisByType(item.supendId,notaryInfoView.idNotaryInfo, 9, item.typePenalize);" href="javascript:void(0);"><i class="fa fa-1-5x fa-eye"></i></a></td>
                                    <td class="text-left v-inherit">{{item.dispatchCode}}</td>
                                    <td class="text-left v-inherit">{{item.dateSign|date:'dd-MM-yyyy'}}</td>
                                    <td class="text-left v-inherit">{{item.signer}}</td>
                                    <td class="text-left v-inherit">{{item.effectiveDate|date:'dd-MM-yyyy'}}</td>
                                    <td class="text-left v-inherit">{{item.leverPenalizeStr}}</td>
                                    <td class="text-left v-inherit">{{item.value}}</td>
                                    <td class="text-left v-inherit">{{item.typePenalizeStr}}</td>
                                    <td class="text-left v-inherit">{{item.additionalPenaltyStr}}</td>
                                    <td class="text-left v-inherit">{{item.moneyPenalty}}</td>
                                    <td class="text-left v-inherit">{{item.reason}}</td>
                                    <td class="text-left v-inherit">
                                        <div ng-repeat="em in item.linkFile.split(';') track by $index">
                                            <a style="color: #3fb4b5;text-decoration: underline" title="Tải file" href="javascript:void(0)" ng-click="download(item.fileName.split(';')[$index], item.linkFile.split(';')[$index]);">{{item.fileName.split(';')[$index]}}</a><br>
                                        </div>
                                    </td>
                                </tr>
                                </tbody>
                            </table>
                        </div>

                        <div id="tuchoibonhiemlai_ccv" class="tab-pane fade in">
                            <table class="table b-t b-light table-bordered table-hover" style="margin-bottom: 0px;" ng-switch on="listData_tuchoibonhiemlai_ccv.rowCount">
                                <thead class="bg-gray">
                                <tr>
                                    <th class="text-center v-inherit text-dark"><spring:message code="label.manageNotary.manageAppoint.detail.label.numericalOrder"></spring:message></th>
                                    <th class="text-center v-inherit text-dark">Xem</th>
                                    <th class="text-left v-inherit text-dark">Số quyết định từ chối bổ nhiệm lại</th>
                                    <th class="text-left v-inherit text-dark">Ngày quyết định</th>
                                    <th class="text-left v-inherit text-dark">Người ký</th>
                                    <th class="text-left v-inherit text-dark">Lý do từ chối bổ nhiệm lại</th>
                                    <th class="text-left v-inherit text-dark">File đính kèm</th>
                                </tr>
                                </thead>
                                <tbody class="background-white">
                                <tr ng-switch-when="0">
                                    <td colspan="7" style="height: 100%;background-color: #ececec; line-height: 3.429;text-align: center; font-style: italic;">
                                        Không có dữ liệu
                                    </td>
                                </tr>
                                <tr ng-switch-default ng-repeat="item in listData_tuchoibonhiemlai_ccv.items track by $index">
                                    <th class="text-center v-inherit">{{$index + 1}}</th>
                                    <td class="text-center"><a ng-click="getNotaryHisByType(item.idReAppoint,notaryInfoView.idNotaryInfo, 12, item.typeAppoint);" href="javascript:void(0);"><i class="fa fa-1-5x fa-eye"></i></a></td>
                                    <td class="text-left v-inherit">{{item.dispatchCode}}</td>
                                    <td class="text-left v-inherit">{{item.dateSign|date:'dd-MM-yyyy'}}</td>
                                    <td class="text-left v-inherit">{{item.signer}}</td>
                                    <td class="text-left v-inherit">{{item.reason}}</td>
                                    <td class="text-center v-inherit">
                                        <div ng-repeat="em in item.linkFile.split(';') track by $index">
                                            <a style="color: #3fb4b5;text-decoration: underline" title="Tải file" href="javascript:void(0)" ng-click="download(item.fileName.split(';')[$index], item.linkFile.split(';')[$index]);">{{item.fileName.split(';')[$index]}}</a><br>
                                        </div>
                                    </td>
                                </tr>
                                </tbody>
                            </table>
                        </div>

                    </div>

                </div>

                <div>
                    <footer class="panel-footer">
                        <div class="row">
                            <div class="col-sm-12 text-right text-center-xs">
                                <ul class="pagination pagination-sm m-t-none m-b-none">
                                </ul>
                            </div>
                        </div>
                    </footer>

                </div>
            </section>

        </section>
    </section>

    <%@include file="../../popupCommon/popup.jsp"%>

</section>


