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

<script src="<%=request.getContextPath()%>/assets/project/phieu-thu/list.js${urlCache}" type="text/javascript"></script>

<section id="content" ng-app="FrameworkBase"  ng-controller="vantai">
    <section class="vbox background-white">
        <section class="scrollable padder">
            <ul class="bg-light breadcrumb no-border no-radius b-light pull-in">
                <li><a href="<%=request.getContextPath()%>/"><i class="fa fa-home"></i>&nbsp;Trang chủ</a></li>
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
                                        <label class="control-label text-dark" style="padding: 0px">Số phiếu thu</label>
                                    </div>
                                    <div class="col-md-8">
                                        <input ng-model="search.receiptCode" class="form-control input-sm" placeholder=""/>
                                    </div>
                                </div>
                                <div class="col-md-6">
                                    <div class="col-md-4">
                                        <label class="control-label text-dark">Kho tiếp nhận</label>
                                    </div>
                                    <div class="col-md-8">
                                        <input ng-model="search.nameStock" class="form-control input-sm" placeholder=""/>
                                    </div>
                                </div>
                            </div>
                            <div class="row" style="margin-top:10px;">
                                <div class="col-md-6">
                                    <div class="col-md-4">
                                        <label class="control-label text-dark">Ngày thu</label>
                                    </div>
                                    <div class="col-md-8">
                                        <div class='input-group date' id="startDate1">
                                            <input ng-model="search.fromPushStock" type='text' class="form-control datepickerEnter" id="fromPushStock" name="fromPushStock" placeholder="Từ ngày"/>
                                            <span class="input-group-addon">
                                                <span class="glyphicon glyphicon-calendar"></span>
                                            </span>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-md-6">
                                    <div class="col-md-4">

                                    </div>
                                    <div class="col-md-8">
                                        <div class='input-group date' id="startDate1">
                                            <input ng-model="search.toPushStock" type='text' class="form-control datepickerEnter" id="toPushStock" name="toPushStock" placeholder="Đến ngày"/>
                                            <span class="input-group-addon">
                                                <span class="glyphicon glyphicon-calendar"></span>
                                            </span>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="row" style="margin-top:30px;margin-bottom: 10px;">
                                <div class="col-md-12" style="text-align: center;">
                                    <img class="hidden _custum_searchSuccess" src="<%=request.getContextPath()%>/assets/images/loading.gif" />

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
                            DANH SÁCH PHIẾU THU
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
                                <a href="<%=request.getContextPath()%>/phieu-thu/preAdd" class="pull-right btn btn-s-sm btn-info"><i class="fa fa-plus"></i>Thêm phiếu thu</a>
                            </div>
                        </div>

                        <div class="table-responsive bg-white" style="overflow-x: auto;">
                            <table class="table b-t b-light table-bordered table-hover" style="margin-bottom: 0px;table-layout: fixed;" ng-switch on="listData.rowCount">
                                <thead class="bg-gray">
                                    <tr>
                                        <th class="text-center v-inherit text-dark" style="width: 60px;">STT</th>
                                        <th class="text-center v-inherit text-dark" style="width: 10%;">Số phiếu thu</th>
                                        <th class="text-center v-inherit text-dark" style="width: 10%;">Ngày thu</th>
                                        <th class="text-center v-inherit text-dark" style="width: 10%;">Kho tiếp nhận</th>
                                        <th class="text-center v-inherit text-dark" style="width: 10%;">Người gửi</th>
                                        <th class="text-center v-inherit text-dark" style="width: 10%;">Người nhận</th>
                                        <th class="text-center v-inherit text-dark" style="width: 10%;">Địa điểm nhận</th>
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
                                        <td class="text-center v-inherit" >{{(listData.pageNumber > 0 ? (listData.pageNumber - 1): 0) * listData.numberPerPage + $index + 1}}</td>
                                        <td class="text-left v-inherit">{{item.receiptCode}}</td>
                                        <td class="text-center v-inherit">{{item.dateReceive|date:'dd/MM/yyyy'}}</td>
                                        <td class="text-left v-inherit">{{item.tenNguoiGui}}</td>
                                        <td class="text-left v-inherit">{{item.tenNguoiNhan}}</td>
                                        <td class="text-left v-inherit">{{item.diaChiNguoiNhan}}</td>
                                        <td class="text-center v-inherit"><a href="<%=request.getContextPath()%>/phieu-thu/preEdit/{{item.id}}"><i class="fa-hover fa fa-eye" style="color: blue;font-size: 15px"></i></a></td>
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
</section>