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

<script src="<%=request.getContextPath()%>/assets/project/bien-nhan/list.js" type="text/javascript"></script>

<section id="content" ng-app="FrameworkBase"  ng-controller="frameworkCtrl">
    <section class="vbox background-white">
        <section class="scrollable padder">
            <ul class="bg-light breadcrumb no-border no-radius b-light pull-in">
                <li><a href="<%=request.getContextPath()%>/"><i class="fa fa-home"></i>&nbsp;Trang chủ</a></li>
                <li><a href="#">Phiếu nhận hàng</a></li>
                <li><a href="#" id="title">Danh sách phiếu nhận hàng</a></li>
            </ul>
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
                                        <label class="control-label text-dark">Kho nhận</label>
                                    </div>
                                    <div class="col-md-8">
                                        <input ng-model="search.nameStock" class="form-control input-sm" placeholder="Kho nhận"/>
                                    </div>
                                </div>
                            </div>

                            <div class="row" style="margin-top:10px;">
                                <div class="col-md-6">
                                    <div class="col-md-4">
                                        <label class="control-label text-dark">Ngày nhận hàng từ</label>
                                    </div>
                                    <div class="col-md-8">
                                        <input id="fromDateReceipt" ng-model="search.fromDateReceipt" class="form-control input-sm" placeholder="Từ ngày"/>
                                    </div>
                                </div>
                                <div class="col-md-6">
                                    <div class="col-md-4">
                                        Ngày nhận hàng đến
                                    </div>
                                    <div class="col-md-8">
                                        <input id="toDateReceipt" ng-model="search.toDateReceipt" class="form-control input-sm" placeholder="đến ngày"/>
                                    </div>
                                </div>
                            </div>

                            <div class="row" style="margin-top:30px;margin-bottom: 10px;">
                                <div class="col-md-12" style="text-align: center;">
                                    <button type="button" class="btn btn-info btn-s-sm" ng-click="searchSelect();">
                                        <i class="fa fa-search"></i>Tìm kiếm
                                    </button>
                                    <button style="margin-left: 10px;" type="button" class="btn btn-gray btn-s-sm" ng-click="resetValue();">Xóa điều kiện</button>
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
                                <label style="color: red;">{{page.rowCount}}</label>
                                <label class="input-sm">Hiển thị: </label>
                                <select class="input-sm form-control input-s-sm inline" style="width: 60px;" ng-model="numberPerPage" ng-change="setNumberPerPage(numberPerPage);" ng-init="numberPerPage = '25'">
                                    <option value="10">10</option>
                                    <option value="20">20</option>
                                    <option value="25">25</option>
                                </select>
                            </div>
                            <div class="p-r-0 p-l-0 col-md-6">
                                <sec:authorize access="hasRole('ROLE_THEM_PHIEU_NHAN_HANG_ADD')">
                                    <a href="<%=request.getContextPath()%>/bienNhan/preAdd" class="pull-right btn btn-s-sm btn-info"><i class="fa fa-plus"></i>Thêm phiếu nhận hàng</a>
                                </sec:authorize>
                            </div>
                        </div>

                        <div class="table-responsive bg-white" style="overflow-x: auto;">
                            <table class="table b-t b-light table-bordered table-hover" style="margin-bottom: 0px;table-layout: fixed;" ng-switch on="listData.rowCount">
                                <thead class="bg-gray">
                                <tr>
                                    <th class="text-center v-inherit text-dark" style="width: 60px;">STT</th>
                                    <th class="text-center v-inherit text-dark" style="width: 10%;">Tool</th>
                                    <th class="text-center v-inherit text-dark" style="width: 10%;">Số phiếu nhận hàng</th>
                                    <th class="text-center v-inherit text-dark" style="width: 10%;">Trạng thái</th>
                                    <th class="text-center v-inherit text-dark" style="width: 10%;">Ngày nhận</th>
                                    <th class="text-center v-inherit text-dark" style="width: 10%;">Kho tiếp nhận</th>
                                    <th class="text-center v-inherit text-dark" style="width: 10%;">Nhân viên tiếp nhận</th>
                                    <th class="text-center v-inherit text-dark" style="width: 10%;">Nhà xe</th>
                                    <th class="text-center v-inherit text-dark" style="width: 10%;">Người gửi</th>
                                    <th class="text-center v-inherit text-dark" style="width: 10%;">Địa chỉ</th>
                                    <th class="text-center v-inherit text-dark" style="width: 10%;">Người nhận</th>
                                    <th class="text-center v-inherit text-dark" style="width: 10%;">địa chỉ</th>
                                    <th class="text-center v-inherit text-dark" style="width: 10%;">Thanh toán</th>
                                    <th class="text-center v-inherit text-dark" style="width: 10%;">Người thanh toán</th>
                                </tr>
                                </thead>
                                <tbody>
                                <tr ng-switch-when="0">
                                    <td colspan="12" style="height: 100%;background-color: #ececec; line-height: 3.429;text-align: center; font-style: italic;">
                                        Không có dữ liệu
                                    </td>
                                </tr>
                                <tr ng-switch-default ng-repeat="item in page.items track by $index">
                                    <td class="text-center v-inherit" >{{(page.pageNumber - 1) * page.numberPerPage + $index + 1}}</td>
                                    <td class="text-center">
                                        <%--<sec:authorize access="hasAnyRole('ROLE_SYSTEM_PUBLISH_AUCTION_VIEW', 'ROLE_SYSTEM_PUBLISH_AUCTION_EDIT')">--%>
                                        <div class="btn-group">
                                            <button class="btn btn-default btn-xs dropdown-toggle" data-toggle="dropdown"><i
                                                    class="glyphicon glyphicon-cog"></i></button>
                                            <ul class="dropdown-menu pull-left" style="width: 230px;">
                                                <sec:authorize access="hasRole('ROLE_THEM_PHIEU_NHAN_HANG_ADD')">
                                                    <li>
                                                        <a href="javascript:void(0)" ng-click="exportPhieuNhanHang(item.id)" >In phiếu nhận hàng</a>
                                                    </li>
                                                </sec:authorize>
                                                <sec:authorize access="hasRole('ROLE_THEM_PHIEU_NHAN_HANG_ADD')">
                                                    <li>
                                                        <a href="javascript:void(0)" ng-click="exportPhieuThu(item.id)" >In phiếu thu</a>
                                                    </li>
                                                </sec:authorize>

                                                <sec:authorize access="hasRole('ROLE_SUA_PHIEU_NHAN_HANG_EDIT')">
                                                    <li>
                                                        <div class="line line-dashed m-b-none m-t-none"></div>
                                                        <a href="#" ng-click="preEdit(item.id)">
                                                            Chỉnh sửa
                                                        </a>
                                                    </li>
                                                </sec:authorize>

                                                <sec:authorize access="hasRole('ROLE_XOA_PHIEU_NHAN_HANG_DELETE')">
                                                    <li>
                                                        <div class="line line-dashed m-b-none m-t-none"></div>
                                                        <a href="#" ng-click="preXoa(item)">
                                                            Xóa
                                                        </a>
                                                    </li>
                                                </sec:authorize>

                                            </ul>
                                        </div>
                                        <%--</sec:authorize>--%>
                                    </td>
                                    <td class="text-left v-inherit">{{item.receiptCode}}</td>
                                    <td class="text-left v-inherit">{{item.status == 1 ? "Nhận hàng" : item.status == 2 ? "Lên toa" : item.status == 3 ? "Đã giao" : ""}}</td>
                                    <td class="text-center v-inherit">{{item.dateReceipt|date:'dd/MM/yyyy'}}</td>
                                    <td class="text-left v-inherit">{{item.nameStock}}</td>
                                    <td class="text-left v-inherit">{{item.employee}}</td>
                                    <td class="text-left v-inherit">{{item.nhaXe}}</td>
                                    <%--<td class="text-left v-inherit">{{ item.bienSo}}</td>--%>
                                    <td class="text-center v-inherit">{{item.tenNguoiGui}}</td>
                                    <td class="text-left v-inherit">{{item.diaChiNguoiGui}}</td>
                                    <td class="text-left v-inherit">{{item.tenNguoiNhan}}</td>
                                    <td class="text-left v-inherit">{{item.diaChiNguoiNhan}}</td>
                                    <td class="text-left v-inherit">{{item.paymentType == 1 ? "Trả trước" : item.paymentType == 2 ? "Trả sau" : "Công nợ"}}</td>
                                    <td class="text-left v-inherit">{{item.payer}}</td>
                                </tr>
                                </tbody>
                            </table>
                        </div>

                        <footer class="panel-footer">
                            <div class="row">
                                <div class="p-l-0 col-sm-6 text-left text-center-xs m-b-xs">
                                    <%--<button class="btn btn-info btn-s-sm" ng-click="export();"><i class="fa fa-file-excel-o"></i> Xuất excel</button>--%>
                                </div>
                                <div class="p-r-0 col-sm-6 text-right text-center-xs">
                                    <ul class="pagination pagination-sm m-t-none m-b-none">
                                        <li ng-if="page.pageNumber > 1"><a href="javascript:void(0)"  ng-click="loadPage(1)">«</a></li>
                                        <li ng-repeat="item in page.pageList">
                                            <a href="javascript:void(0)" ng-if="item == page.pageNumber" style="color:mediumvioletred;"> {{item}}</a>
                                            <a href="javascript:void(0)" ng-if="item != page.pageNumber" ng-click="loadPage(item)"> {{item}}</a>
                                        </li>
                                        <li ng-if="page.pageNumber < page.pageCount" ><a href="javascript:void(0)" ng-click="loadPage(page.pageCount)">»</a></li>
                                    </ul>
                                </div>
                            </div>
                        </footer>
                    </div>
                </div>
            </section>

        </section>
    </section>
    <div class="modal fade" id="xoaBienNhan" tabindex="-1" role="dialog"
         aria-labelledby="xoaBienNhan" aria-hidden="true" aria-label="Close">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header alert-info">
                    <button type="button" class="btn btn-default close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h4 class="modal-title">Xóa phiếu nhận hàng</h4>
                </div>
                <div class="modal-body">
                    <span>Bạn có chắc chắn muốn xóa phiếu nhận</span><br/>
                    <span style="color: #3c763d">{{delReceiptCode}}</span> <span> hay không?</span>

                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-primary" data-dismiss="modal" ng-click="xoaBienNhan()" style="text-transform: none;"><i class="fa fa-check"></i> <spring:message code="label.button.ok"/></button>
                    <button type="button" class="btn btn-danger" data-dismiss="modal">Hủy bỏ</button>
                </div>
            </div>
        </div>
    </div>
    <form id="frmEdit" action="<%=request.getContextPath()%>/bienNhan/preEdit" method='POST'>
        <input type="hidden" name='editId' id='editId' value="">
    </form>
</section>

<script>
  showDropDownOnTable();
</script>