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

<script src="<%=request.getContextPath()%>/assets/project/thong-ke/list.js" type="text/javascript"></script>

<section id="content" ng-app="FrameworkBase"  ng-controller="frameworkCtrl">
    <section class="vbox background-white">
        <section class="scrollable padder">
            <ul class="bg-light breadcrumb no-border no-radius b-light pull-in">
                <li><a href="<%=request.getContextPath()%>/"><i class="fa fa-home"></i>&nbsp;Trang chủ</a></li>
                <li><a href="#">Thống kê</a></li>
                <li><a href="#" id="title">Danh sách thống kê phiếu giao, nhận hàng</a></li>
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
                                        <label class="control-label text-dark" style="padding: 0px">Nhà xe</label>
                                    </div>
                                    <div class="col-md-8">
                                        <select ng-model="search.bienSo" id="search.bienSo" name ="search.bienSo" class="select2" style="width: 100%">
                                            <option value="" selected >-- Tất cả --</option>
                                            <option ng-repeat="item in nhaXeList track by $index" ng-value="item.bienSo">nhà xe: {{item.nhaXe}} -- biển số: {{item.bienSo}}</option>
                                        </select>
                                    </div>
                                </div>
                                <div class="col-md-6">
                                    <div class="col-md-4">
                                        <label class="control-label text-dark">Loại xe</label>
                                    </div>
                                    <div class="col-md-8">
                                        <input ng-model="search.loaiXe" class="form-control input-sm" placeholder="Loại xe"/>
                                    </div>
                                </div>
                            </div>

                            <div class="row" style="margin-top:10px;">
                                <div class="col-md-6">
                                    <div class="col-md-4">
                                        <label class="control-label text-dark" style="padding: 0px">Hình thức vận chuyển</label>
                                    </div>
                                    <div class="col-md-8">
                                        <select class="form-control" name="search.hinhThucVanChuyen" id="search.hinhThucVanChuyen" ng-model="search.hinhThucVanChuyen">
                                            <option value="" selected>Tất cả</option>
                                            <option ng-value="1">Nhận hàng</option>
                                            <option ng-value="2">Vận chuyển</option>
                                            <option ng-value="3">Giao hàng</option>
                                        </select>
                                    </div>
                                </div>
                            </div>

                            <div class="row" style="margin-top:10px;">
                                <div class="col-md-6">
                                    <div class="col-md-4">
                                        <label class="control-label text-dark">Từ ngày</label>
                                    </div>
                                    <div class="col-md-8">
                                        <input id="fromDate" ng-model="search.fromDate" class="form-control input-sm" placeholder="Từ ngày"/>
                                    </div>
                                </div>
                                <div class="col-md-6">
                                    <div class="col-md-4">
                                        Đến ngày
                                    </div>
                                    <div class="col-md-8">
                                        <input id="toDate" ng-model="search.toDate" class="form-control input-sm" placeholder="đến ngày"/>
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
                        DANH SÁCH THỐNG KÊ PHIẾU GIAO NHẬN HÀNG
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
                        </div>

                        <div class="table-responsive bg-white" style="overflow-x: auto;">
                            <table class="table b-t b-light table-bordered table-hover" style="margin-bottom: 0px;table-layout: fixed;" ng-switch on="page.rowCount">
                                <thead class="bg-gray">
                                <tr>
                                    <th class="text-center v-inherit text-dark" style="width: 60px;">STT</th>
                                    <th class="text-center v-inherit text-dark" style="width: 10%;">Nhà xe</th>
                                    <th class="text-center v-inherit text-dark" style="width: 10%;">Loại xe</th>
                                    <th class="text-center v-inherit text-dark" style="width: 10%;">Biển số</th>
                                    <th class="text-center v-inherit text-dark" style="width: 10%;">Hình thức vận chuyển</th>
                                    <th class="text-center v-inherit text-dark" style="width: 10%;">Mã</th>
                                    <th class="text-center v-inherit text-dark" style="width: 10%;">Số lượng</th>
                                    <th class="text-center v-inherit text-dark" style="width: 10%;">Điểm nhận</th>
                                    <th class="text-center v-inherit text-dark" style="width: 10%;">Điểm giao</th>
                                    <th class="text-center v-inherit text-dark" style="width: 10%;">Ngày</th>
                                </tr>
                                </thead>
                                <tbody>
                                <tr ng-switch-when="0">
                                    <td colspan="8" style="height: 100%;background-color: #ececec; line-height: 3.429;text-align: center; font-style: italic;">
                                        Không có dữ liệu
                                    </td>
                                </tr>
                                <tr ng-switch-default ng-repeat="item in page.items track by $index">
                                    <td class="text-center v-inherit" >{{(page.pageNumber - 1) * page.numberPerPage + $index + 1}}</td>
                                    <td class="text-left v-inherit">{{item.nhaXe}}</td>
                                    <td class="text-left v-inherit">{{item.loaiXe}}</td>
                                    <td class="text-center v-inherit">{{item.bienSo}}</td>
                                    <!--<td class="text-left v-inherit">{{item.hinhThucVanChuyen == 1 ? "Nhận hàng" : item.hinhThucVanChuyen == 2 ? "Vận chuyển" : "Giao hàng"}}</td>-->
                                    <td class="text-left v-inherit">{{item.hinhThucVanChuyen}}</td>
                                    <td class="text-left v-inherit">{{item.receiptCode}}</td>
                                    <td class="text-left v-inherit">{{item.soLuong}}</td>
                                    <td class="text-left v-inherit">{{item.diaChiNhan}}</td>
                                    <td class="text-left v-inherit">{{item.diaChiGiao}}</td>
                                    <td class="text-left v-inherit">{{item.strGenDate}}</td>
                                </tr>
                                </tbody>
                            </table>
                        </div>

                        <footer class="panel-footer">
                            <div class="row">
                                <div class="p-l-0 col-sm-6 text-left text-center-xs m-b-xs">
                                    <sec:authorize access="hasRole('ROLE_TK_GIAO_NHAN_EXPORT_VIEW')">
                                        <button class="btn btn-info btn-s-sm" ng-click="exportExcel();"><i class="fa fa-file-excel-o"></i> Xuất file excel</button>
                                    </sec:authorize>
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

</section>

<script>
    showDropDownOnTable();
</script>