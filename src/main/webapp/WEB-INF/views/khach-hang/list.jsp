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

<script src="<%=request.getContextPath()%>/assets/project/khach-hang/list.js" type="text/javascript"></script>

<section id="content" ng-app="FrameworkBase"  ng-controller="frameworkCtrl">
    <section class="vbox background-white">
        <section class="scrollable padder">
            <ul class="bg-light breadcrumb no-border no-radius b-light pull-in">
                <li><a href="<%=request.getContextPath()%>/"><i class="fa fa-home"></i>&nbsp;Trang chủ</a></li>
                <li><a href="#">Khách hàng</a></li>
                <li><a href="#" id="title">Danh sách Khách hàng</a></li>
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
                                        <label class="control-label text-dark" style="padding: 0px">Số CMTND</label>
                                    </div>
                                    <div class="col-md-8">
                                        <input ng-model="search.taxCode" class="form-control input-sm" placeholder="Số CMTND"/>
                                    </div>
                                </div>
                                <div class="col-md-6">
                                    <div class="col-md-4">
                                        <label class="control-label text-dark">Tên khách hàng</label>
                                    </div>
                                    <div class="col-md-8">
                                        <input ng-model="search.fullName" class="form-control input-sm" placeholder="Tên khách hàng"/>
                                    </div>
                                </div>
                            </div>

                            <div class="row" style="margin-top:10px;">
                                <div class="col-md-6">
                                    <div class="col-md-4">
                                        <label class="control-label text-dark">Số điện thoại</label>
                                    </div>
                                    <div class="col-md-8">
                                        <input id="mobile" ng-model="search.mobile" class="form-control input-sm" placeholder="Số điện thoại khách hàng"/>
                                    </div>
                                </div>
                                <div class="col-md-6">
                                    <div class="col-md-4">
                                        Địa chỉ
                                    </div>
                                    <div class="col-md-8">
                                        <input id="address" ng-model="search.address" class="form-control input-sm" placeholder="Địa chỉ khách hàng"/>
                                    </div>
                                </div>
                            </div>

                            <div class="row" style="margin-top:10px;">
                                <div class="col-md-6">
                                    <div class="col-md-4">
                                        <label class="control-label text-dark">Loại khách hàng</label>
                                    </div>
                                    <div class="col-md-8">
                                        <select class="form-control" name="search.typePartner" id="search.typePartner" ng-model="search.typePartner">
                                            <option value="" selected>Tất cả</option>
                                            <option ng-value="2">Người gửi</option>
                                            <option ng-value="3">Người nhận</option>
                                            <option ng-value="1">Khách hàng</option>
                                        </select>
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
                        DANH SÁCH KHÁCH HÀNG
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
                                <sec:authorize access="hasRole('ROLE_THEM_KHACH_HANG_ADD')">
                                    <a ng-click="preAddKhachHang()" class="pull-right btn btn-s-sm btn-info"><i class="fa fa-plus"></i>Thêm mới khách hàng</a>
                                </sec:authorize>
                            </div>
                        </div>

                        <div class="table-responsive bg-white" style="overflow-x: auto;">
                            <table class="table b-t b-light table-bordered table-hover" style="margin-bottom: 0px;table-layout: fixed;" ng-switch on="page.rowCount">
                                <thead class="bg-gray">
                                <tr>
                                    <th class="text-center v-inherit text-dark" style="width: 60px;">STT</th>
                                    <th class="text-center v-inherit text-dark" style="width: 60px;">Tool</th>
                                    <th class="text-center v-inherit text-dark" style="width: 10%;">Tên khách hàng</th>
                                    <th class="text-center v-inherit text-dark" style="width: 10%;">Số CMTND</th>
                                    <th class="text-center v-inherit text-dark" style="width: 10%;">Số điện thoại</th>
                                    <th class="text-center v-inherit text-dark" style="width: 20%;">Địa chỉ</th>
                                    <th class="text-center v-inherit text-dark" style="width: 10%;">Loại khách hàng</th>
                                    <th class="text-center v-inherit text-dark" style="width: 10%;">Số HD</th>
                                    <th class="text-center v-inherit text-dark" style="width: 10%;">Ngày tạo</th>
                                </tr>
                                </thead>
                                <tbody>
                                <tr ng-switch-when="0">
                                    <td colspan="9" style="height: 100%;background-color: #ececec; line-height: 3.429;text-align: center; font-style: italic;">
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
                                                <sec:authorize access="hasRole('ROLE_SUA_KHACH_HANG_EDIT')">
                                                    <li>
                                                        <div class="line line-dashed m-b-none m-t-none"></div>
                                                        <a href="#" ng-click="preEdit(item)">
                                                            Chỉnh sửa
                                                        </a>
                                                    </li>
                                                </sec:authorize>

                                                <sec:authorize access="hasRole('ROLE_XOA_KHACH_HANG_DELETE')">
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
                                    <td class="text-left v-inherit">{{item.fullName}}</td>
                                    <td class="text-left v-inherit">{{item.taxCode}}</td>
                                    <td class="text-left v-inherit">{{item.mobile}}</td>
                                    <td class="text-left v-inherit">{{item.address}}</td>
                                    <td class="text-left v-inherit">{{item.typePartner == 2 ? "Người gửi" : item.typePartner == 3 ? "Người nhận" : item.typePartner == 1 ? "Khách hàng" : ""}}</td>
                                    <td class="text-left v-inherit">{{item.soHopDong}}</td>
                                    <td class="text-center v-inherit">{{item.genDate|date:'dd/MM/yyyy'}}</td>
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
    <div class="modal fade" id="xoaKhachHang" tabindex="-1" role="dialog"
         aria-labelledby="xoaKhachHang" aria-hidden="true" aria-label="Close">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header alert-info">
                    <button type="button" class="btn btn-default close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h4 class="modal-title">Xóa khách hàng</h4>
                </div>
                <div class="modal-body">
                    <span>Bạn có chắc chắn muốn xóa khách hàng</span><br/>
                    <span style="color: red">{{delFullName}}</span> <span> hay không?</span>

                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-primary" data-dismiss="modal" ng-click="xoaKhachHang()" style="text-transform: none;"><i class="fa fa-check"></i> <spring:message code="label.button.ok"/></button>
                    <button type="button" class="btn btn-danger" data-dismiss="modal">Hủy bỏ</button>
                </div>
            </div>
        </div>
    </div>

    <div class="modal fade" id="addOrEditKhachHang" tabindex="-1" role="dialog"
         aria-labelledby="addOrEditKhachHang" aria-hidden="true" aria-label="Close">
        <div class="modal-dialog">
            <div class="modal-content" style="width: 150%">
                <div class="modal-header alert-info">
                    <button type="button" class="btn btn-default close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h4 class="modal-title">{{titleAddOrEdit}}</h4>
                </div>
                <div class="modal-body">
                    <input style="display: none" name="info.id" ng-value="info.id" ng-model="info.id" class="form-control"/>
                    <div class="row">
                        <label class="col-sm-2 control-label" style="line-height: 30px">Số CMTND <span style="color: red"> (*)</span></label>
                        <div class="col-sm-4">
                            <input name="info.taxCode" id="info.taxCode" ng-value="info.taxCode" ng-model="info.taxCode" maxlength="200" class="form-control"/>
                        </div>
                        <label class="col-sm-2 control-label" style="line-height: 30px">Loại khách hàng <span style="color: red"> (*)</span></label>
                        <div class="col-sm-4">
                            <select class="form-control" name="info.typePartner" id="info.typePartner" ng-model="info.typePartner">
                                <option ng-value="2">Người gửi</option>
                                <option ng-value="3">Người nhận</option>
                                <option ng-value="1">Khách hàng</option>
                            </select>
                        </div>
                    </div>

                    <div class="row" style="padding-top: 2%">
                        <label class="col-sm-2 control-label" style="line-height: 30px">Tên khách hàng <span style="color: red"> (*)</span></label>
                        <div class="col-sm-4">
                            <input name="info.fullName" id="info.fullName" ng-value="info.fullName" ng-model="info.fullName" maxlength="200" class="form-control"/>
                        </div>
                        <label class="col-sm-2 control-label" style="line-height: 30px">Số điện thoại</label>
                        <div class="col-sm-4">
                            <input name="info.mobile" id="info.mobile" ng-value="info.mobile" ng-model="info.mobile" maxlength="50" class="form-control"/>
                        </div>
                    </div>

                    <div class="row" style="padding-top: 2%">
                        <label class="col-sm-2 control-label" style="line-height: 30px">Địa chỉ</label>
                        <div class="col-sm-4">
                            <input name="info.address" id="info.address" ng-value="info.address" ng-model="info.address" maxlength="500" class="form-control"/>
                        </div>
                        <label class="col-sm-2 control-label" style="line-height: 30px">Email</label>
                        <div class="col-sm-4">
                            <input name="info.email" id="info.email" ng-value="info.email" ng-model="info.email" maxlength="50" class="form-control"/>
                        </div>
                    </div>
                    <div class="row" style="padding-top: 2%">
                        <label class="col-sm-2 control-label" style="line-height: 30px">Số hợp đồng</label>
                        <div class="col-sm-4">
                            <input name="info.soHopDong" id="info.soHopDong" ng-value="info.soHopDong" ng-model="info.soHopDong" maxlength="200" class="form-control"/>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <div class="row" style="text-align: center">
                        <button ng-show="info.id == null || info.id == '' || info.id == 'undefined'" type="button" class="btn btn-default" ng-click="resetValueAdd()" style="text-transform: none;">Reset</button>
                        <button class="btn btn-primary" ng-click="addKhachHang()">Lưu</button>
                        <button type="button" class="btn btn-danger" data-dismiss="modal">Hủy bỏ</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>

<script>
  showDropDownOnTable();
</script>