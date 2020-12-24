<%@ page isELIgnored="false" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<head>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/css/jquery-ui.css">
    <script src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script>
</head>

<script src="<%=request.getContextPath()%>/assets/project/phieu-nhan/list.js${urlCache}" type="text/javascript"></script>

<section id="content" ng-app="tudo"  ng-controller="vantaivn">
    <section class="vbox background-white">
        <section class="scrollable padder">
            <ul class="bg-light breadcrumb no-border no-radius b-light pull-in">
                <li><a href="<%=request.getContextPath()%>/"><i class="fa fa-home"></i>&nbsp;Trang chủ</a></li>
            </ul>
            <div id="msgId" class="m-b-md" style="display: none;">
                <span class="closebtn" onclick="this.parentElement.style.display = 'none';">&times;</span>
                <div class="title-message">{{logger}}</div>
            </div>
            <section class="panel panel-default" style="margin-bottom: 5px;">
                <header class="panel-heading">
                    <a href="javascript:void(0)"><h4 class="panel-title font-bold" data-toggle="collapse" data-target="#collapseOne">
                            THÔNG TIN TÌM KIẾM
                        </h4></a>
                </header>
                <div id="collapseOne" class="panel-collapse collapse in" aria-expanded="true">
                    <div class="panel-body background-xam">
                        <form class="form-horizontal">

                            <div class="row">
                                <div class="col-md-6">
                                    <div class="col-md-4">
                                        <label class="control-label text-dark" style="padding: 0px">Số phiếu nhận</label>
                                    </div>
                                    <div class="col-md-8">
                                        <input ng-model="search.receiptCode" class="form-control input-sm" placeholder="GH­2012­0036"/>
                                    </div>
                                </div>
                                <div class="col-md-6">
                                    <div class="col-md-4">
                                        <label class="control-label text-dark">Nhà xe</label>
                                    </div>
                                    <div class="col-md-8">
                                        <input ng-model="search.truckPartner" class="form-control input-sm" placeholder="Nhà xe Minh Anh"/>
                                    </div>
                                </div>
                            </div>

                            <div class="row" style="margin-top:10px;">
                                <div class="col-md-6">
                                    <div class="col-md-4">
                                        <label class="control-label text-dark">Loại xe</label>
                                    </div>
                                    <div class="col-md-8">
                                        <input id="" ng-model="search.loaiXe" class="form-control input-sm" placeholder="Xe tải 1 tấn"/>
                                    </div>
                                </div>
                                <div class="col-md-6">
                                    <div class="col-md-4">
                                        <label class="control-label text-dark">Biển số xe</label>
                                    </div>
                                    <div class="col-md-8">
                                        <input id="" ng-model="search.bienSo" class="form-control input-sm" placeholder="29C - 012 34"/>
                                    </div>
                                </div>
                            </div>

                            <div class="row" style="margin-top:10px;">
                                <div class="col-md-6">
                                    <div class="col-md-4">
                                        <label class="control-label text-dark">Ngày gửi hàng</label>
                                    </div>
                                    <div class="col-md-8">
                                        <input id="fromDelivery" ng-model="search.fromDelivery" class="form-control input-sm" placeholder="Từ ngày"/>
                                    </div>
                                </div>
                                <div class="col-md-6">
                                    <div class="col-md-4">

                                    </div>
                                    <div class="col-md-8">
                                        <input id="toDelivery" ng-model="search.toDelivery" class="form-control input-sm" placeholder="đến ngày"/>
                                    </div>
                                </div>
                            </div>
                            <div class="row" style="margin-top:10px;">
                                <div class="col-md-6">
                                    <div class="col-md-4">
                                        <label class="control-label text-dark">Ngày lập phiếu nhận</label>
                                    </div>
                                    <div class="col-md-8">
                                        <input id="fromReceive" ng-model="search.fromReceive" class="form-control input-sm" placeholder="Từ ngày"/>
                                    </div>
                                </div>
                                <div class="col-md-6">
                                    <div class="col-md-4">

                                    </div>
                                    <div class="col-md-8">
                                        <input id="toReceive" ng-model="search.toReceive" class="form-control input-sm" placeholder="đến ngày"/>
                                    </div>
                                </div>
                            </div>
                            <div class="row" style="margin-top:30px;margin-bottom: 10px;">
                                <div class="col-md-12" style="text-align: center;">
                                    <img class="hidden _custum_searchSuccess" src="<%=request.getContextPath()%>/assets/images/icon/loading.gif" />

                                    <button ng-disabled="_custum_searchSuccess" ng-init="_custum_searchSuccess = false;" type="button" class="btn btn-info btn-s-sm" ng-click="searchSelect($event);">
                                        <i class="fa fa-search"></i>Tìm kiếm
                                    </button>
                                    <button style="margin-left: 10px;" type="button" class="btn btn-gray btn-s-sm" ng-click="clear();">Xóa điều kiện</button>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </section>

            <section class="panel panel-default">
                <header class="panel-heading">
                    <a href="javascript:void(0)"><h4 class="panel-title font-bold" data-toggle="collapse" data-target="#collapseTwo">
                            DANH SÁCH PHIẾU NHẬN HÀNG
                        </h4></a>
                </header>
                <div id="collapseTwo" class="panel-collapse collapse in" aria-expanded="true">
                    <div class="panel-body background-xam">
                        <div class="row" style="margin: 0px;">
                            <div class="p-r-0 p-l-0 col-md-6">
                                <label class="input-sm">Tổng số: </label>
                                <label style="color: red;">{{listData.rowCount}}</label>
                                <label class="input-sm">Hiển thị: </label>
                                <select class="input-sm form-control input-s-sm inline" style="width: 60px;" ng-model="numberPerPage" ng-change="setNumberPerPage(numberPerPage);" ng-init="numberPerPage = '25'">
                                    <option value="10">10</option>
                                    <option value="20">20</option>
                                    <option value="25">25</option>
                                </select>
                            </div>
                            <div class="p-r-0 p-l-0 col-md-6">
                                <button data-toggle="modal" ng-click="loadDataWating();" data-target="#list-cho-tn" class="btn btn-info btn-sm btn-s-sm pull-right" style="margin-right: 10px;">
                                    Thêm mới</button>
                            </div>
                        </div>

                        <div class="table-responsive bg-white" style="overflow-x: auto;">
                            <table class="table b-t b-light table-bordered table-hover" style="margin-bottom: 0px;table-layout: fixed;" ng-switch on="listData.rowCount">
                                <thead class="bg-gray">
                                    <tr>
                                        <th class="text-center v-inherit text-dark" style="width: 60px;">STT</th>
                                        <th class="text-center v-inherit text-dark" style="width: 10%;">Số phiếu nhận</th>
                                        <th class="text-center v-inherit text-dark" style="width: 10%;">Ngày lập phiếu</th>
                                        <th class="text-center v-inherit text-dark" style="width: 10%;">Nhà xe</th>
                                        <th class="text-center v-inherit text-dark" style="width: 10%;">Loại xe</th>
                                        <th class="text-center v-inherit text-dark" style="width: 10%;">Biển số xe</th>
                                        <th class="text-center v-inherit text-dark" style="width: 10%;">Ngày chạy</th>
                                        <th class="text-center v-inherit text-dark" style="width: 10%;">Người giao</th>
                                        <th class="text-center v-inherit text-dark" style="width: 10%;">Người lập</th>
                                        <th class="text-center v-inherit text-dark" style="width: 10%;">Tổng tiền phải thu</th>
                                        <th class="text-center v-inherit text-dark" style="width: 10%;">Tool</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <tr ng-switch-when="0">
                                        <td colspan="17" style="height: 100%;background-color: #ececec; line-height: 3.429;text-align: center; font-style: italic;">
                                            Không có dữ liệu
                                        </td>
                                    </tr>
                                    <tr ng-switch-default ng-repeat="item in listData.items track by $index">
                                        <td class="text-center v-inherit" >{{(listData.pageNumber - 1) * listData.numberPerPage + $index + 1}}</td>
                                        <td class="text-center v-inherit"><a target="_blank" href="<%=request.getContextPath()%>/quan-ly-chi-tiet-ho-so-ccv/{{item.idNotaryInfo}}"><i class="fa-hover fa fa-eye" style="color: blue;font-size: 15px"></i></a></td>

                                    <td class="text-left v-inherit">{{item.receiptCode}}</td>
                                    <td class="text-center v-inherit">{{item.dateReceive|date:'dd/MM/yyyy'}}</td>
                                    <td class="text-left v-inherit">{{item.truckPartnerName}}</td>
                                    <td class="text-left v-inherit">{{item.loaiXe}}</td>
                                    <td class="text-left v-inherit">{{item.bienSo}}</td>
                                    <td class="text-center v-inherit">{{item.dateReceive|date:'dd/MM/yyyy'}}</td>
                                    <td class="text-left v-inherit">{{item.partnerNameDelivery}}</td>
                                    <td class="text-left v-inherit">{{item.partnerNameReceive}}</td>
                                    <td class="text-left v-inherit">{{item.totalDebt}}</td>
                                </tr>
                            </tbody>
                        </table>
                    </div>

                    <footer class="panel-footer">
                        <div class="row">
                            <div class="p-l-0 col-sm-6 text-left text-center-xs m-b-xs">
                                <button class="btn btn-info btn-s-sm" ng-click="export();"><i class="fa fa-file-excel-o"></i> Xuất excel</button>
                            </div>
                            <div class="p-r-0 col-sm-6 text-right text-center-xs">
                                <ul class="pagination pagination-sm m-t-none m-b-none">
                                    <li ng-if="listData.pageNumber > 1"><a href="javascript:void(0)" ng-click="loadPageData(1)">«</a></li>
                                    <li ng-repeat="item in listData.pageList">
                                        <a href="javascript:void(0)" ng-if="item == listData.pageNumber" style="color:mediumvioletred;"> {{item}}</a>
                                        <a href="javascript:void(0)" ng-if="item != listData.pageNumber" ng-click="loadPageData(item)"> {{item}}</a>
                                    </li>
                                    <li ng-if="listData.pageNumber < listData.pageCount"><a href="javascript:void(0)" ng-click="loadPageData(listData.pageCount)">»</a></li>
                                </ul>
                            </div>
                        </div>
                    </footer>
                </div>
            </div>
        </section>

    </section>
</section>

<!--    Chi tiết bổ nhiệm-->
<div class="modal fade" id="chi-tiet-bo-nhiem" role="dialog" aria-hidden="true" style="overflow: auto;">
    <div class="modal-dialog" style="width: 90%;max-width: 1500px;">
        <div class="modal-content">
            <div class="bg-info modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true" >&times;</button>
                <h4 class="modal-title color-modal text-center" style="font-size: 20px">{{notaryInfo.status == 7 ? "THÔNG TIN BỔ NHIỆM CÔNG CHỨNG VIÊN" : "THÔNG TIN TỪ CHỐI BỔ NHIỆM CÔNG CHỨNG VIÊN"}}</h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal">
                    <header class="panel-head">
                        <h4 class="panel-header" data-toggle="collapse" data-target="">
                            <u>Thông tin người tập sự</u>
                        </h4>
                    </header>
                    <div id="collapseTow" class="panel-collapse collapse in" aria-expanded="true">
                        <div class="form-group">
                            <label class="col-md-2 text-dark"><spring:message code="label.manageNotary.manageAppoint.detail.label.status"></spring:message>: </label>
                                <label class="col-md-4 text-info">{{notaryInfo.status == 7 ? "Đã bổ nhiệm" : "Từ chối bổ nhiệm"}}</label>
                            </div>
                            <div class="form-group">
                                <label class="col-md-2 text-dark"><spring:message code="label.manageNotary.manageAppoint.detail.label.fullName"></spring:message>: </label>
                                <label class="col-md-4 text-dark">{{notaryInfo.name}}</label>
                                <label class="col-md-2 text-dark"><spring:message code="label.manageNotary.manageAppoint.detail.label.email"></spring:message>: </label>
                                <label class="col-md-4 text-dark">{{notaryInfo.email}}</label>
                            </div>
                            <div class="form-group">
                                <label class="col-md-2 text-dark"><spring:message code="label.manageNotary.manageAppoint.detail.label.sex"></spring:message>: </label>
                                <label class="col-md-4 text-dark">{{notaryInfo.sex == 1 ? "Nam" : "Nữ"}}</label>
                                <label class="col-md-2 text-dark"><spring:message code="label.manageNotary.manageAppoint.detail.label.address"></spring:message>: </label>
                                <label class="col-md-4 text-dark">{{notaryInfo.addressResident}}</label>
                            </div>
                            <div class="form-group">
                                <label class="col-md-2 text-dark"><spring:message code="label.manageNotary.manageAppoint.detail.label.birthday"></spring:message>: </label>
                                <label class="col-md-4 text-dark">{{notaryInfo.birthDay|date:'dd-MM-yyyy'}}</label>
                                <label class="col-md-2 text-dark"><spring:message code="label.manageNotary.manageAppoint.detail.label.identityCard"></spring:message>: </label>
                                <label class="col-md-4 text-dark">{{notaryInfo.idNo}}</label>
                            </div>

                            <div class="form-group">
                                <label class="col-md-2 text-dark"><spring:message code="label.manageNotary.manageAppoint.detail.label.dateRange"></spring:message>: </label>
                                <label class="col-md-4 text-dark">{{notaryInfo.idNoDate|date:'dd-MM-yyyy'}}</label>
                                <label class="col-md-2 text-dark"><spring:message code="label.manageNotary.manageAppoint.detail.label.issuedBy"></spring:message>: </label>
                                <label class="col-md-4 text-dark">{{notaryInfo.addressIdNo}}</label>
                            </div>
                            <div class="form-group">
                                <label class="col-md-2 text-dark"><spring:message code="label.manageNotary.manageAppoint.detail.label.phoneNumber"></spring:message>: </label>
                                <label class="col-md-4 text-dark">{{notaryInfo.phoneNumber}}</label>
                                <label class="col-md-2 text-dark">Địa chỉ tạm trú: </label>
                                <label class="col-md-4 text-dark">{{notaryInfo.addressNow}}</label>
                            </div>
                        </div>
                        <div ng-show="notaryInfo.status == 7">
                            <header class="panel-head">
                                <h4 class="panel-header" data-toggle="collapse" data-target="">
                                    <u>Thông tin bổ nhiệm công chứng viên</u>
                                </h4>
                            </header>
                            <div class="panel-collapse collapse in" aria-expanded="true">
                                <div class="form-group">
                                    <label class="col-md-2 text-dark">Số quyết định bổ nhiệm: </label>
                                    <label class="col-md-4 text-dark">{{document.dispatchCode}}</label>

                                    <label class="col-md-2 text-dark">Ngày quyết định: </label>
                                    <label class="col-md-4 text-dark">{{document.dateSign|date:'dd-MM-yyyy'}}</label>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-2 control-label text-dark">Người ban hành</label>
                                    <label class="col-md-4 control-label text-dark">{{document.signer}}</label>

                                    <label class="col-md-2 control-label text-dark">Ngày có hiệu lực</label>
                                    <label class="col-md-4 control-label text-dark">{{document.effectiveDate|date:'dd-MM-yyyy'}}</label>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-2 control-label text-dark"><spring:message code="label.manageNotary.manageAppoint.add.label.attachFiles"></spring:message> </label>
                                    <div class="col-md-4 control-label text-dark">
                                        <div ng-repeat="em in document.linkFile.split(';') track by $index">
                                            {{document.fileName.split(';')[$index]}}<br>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>   
                        <div ng-show="notaryInfo.status == 14">
                            <header class="panel-head">
                                <h4 class="panel-header" data-toggle="collapse" data-target="">
                                    <u>Thông tin từ chối bổ nhiệm công chứng viên</u>
                                </h4>
                            </header>
                            <div id="collapsefore" class="panel-collapse collapse in" aria-expanded="true">
                                <div class="form-group">
                                    <label class="col-md-2 text-dark">Số quyết định từ chối bổ nhiệm: </label>
                                    <label class="col-md-4 text-dark">{{document.dispatchCode}}</label>

                                    <label class="col-md-2 text-dark">Ngày quyết định: </label>
                                    <label class="col-md-4 text-dark">{{document.dateSign|date:'dd-MM-yyyy'}}</label>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-2 control-label text-dark">Người ban hành</label>
                                    <label class="col-md-4 control-label text-dark">{{document.signer}}</label>

                                    <label class="col-md-2 control-label text-dark">Lý do từ chối bổ nhiệm:</label>
                                    <label class="col-md-4 control-label text-dark">{{appoint.reason}}</label>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-2 control-label text-dark"><spring:message code="label.manageNotary.manageAppoint.add.label.attachFiles"></spring:message> </label>
                                    <div class="col-md-4 control-label text-dark">
                                        <div ng-repeat="em in document.linkFile.split(';') track by $index">
                                            {{document.fileName.split(';')[$index]}}<br>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </form>
                </div>
                <div class="modal-footer" style="text-align: center;">
                    <button type="button" class="btn btn-1 btn-gray btn-default" data-dismiss="modal">Đóng</button>
                </div> 
            </div>

        </div>
    </div>

<%--confirm tiếp nhận--%>
<div class="modal fade" id="confirm-tiep-nhan" role="dialog">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header bg-info text-center" style="color: white;">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title"> Thông báo</h4>
            </div>
            <div class="modal-body">Bạn có muốn tiếp nhận Công chứng viên đã chọn.</div>
            <div class="modal-footer">
                <a class="btn btn-info btn-s-sm" ng-click="confirmTiepNhan();"><i class="fa fa-check"></i> Đồng ý</a>
                <button type="button" class="btn btn-default btn-s-sm" data-dismiss="modal"><spring:message code="label.popupCommon.popup.confirmDelete.button.cancel"></spring:message></button>
                </div>
            </div>
        </div>
    </div>

<%@include file="../popupCommon/popup.jsp"%>
</section>