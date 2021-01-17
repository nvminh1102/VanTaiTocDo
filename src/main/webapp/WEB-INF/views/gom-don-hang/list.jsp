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

<script src="<%=request.getContextPath()%>/assets/project/gom-don-hang/list.js${urlCache}" type="text/javascript"></script>

<section id="content" ng-app="FrameworkBase"  ng-controller="vantai">
    <section class="vbox background-white">
        <section class="scrollable padder">
            <ul class="bg-light breadcrumb no-border no-radius b-light pull-in">
                <li><a href="<%=request.getContextPath()%>/"><i class="fa fa-home"></i>&nbsp;Trang chủ</a></li>
            </ul>
            <h3 class="m-b-none" style="color: red"><c:if test="${!empty messageError}"><spring:message code="${messageError}"/></c:if></h3>
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
<!--                                    <div class="col-md-6">
                                        <div class="col-md-4">
                                            <label class="control-label text-dark" style="padding: 0px">Số phiếu giao hàng</label>
                                        </div>
                                        <div class="col-md-8">
                                            <input ng-model="search.maPhieuGiao" class="form-control input-sm" placeholder=""/>
                                        </div>
                                    </div>-->
                                    <div class="col-md-6">
                                        <div class="col-md-4">
                                            <label class="control-label text-dark">Biển số xe</label>
                                        </div>
                                        <div class="col-md-8">
                                            <select ng-model="search.bienSo" id="search.bienSo" name ="search.bienSo" class="select2" style="width: 100%" >
                                                <option value="" selected >-- Lựa chọn --</option>
                                                <option ng-repeat="item in nhaXeList track by $index" ng-value="item.bienSo">nhà xe: {{item.nhaXe}} -- biển số: {{item.bienSo}}</option>
                                            </select>
                                        </div>
                                    </div>
                                </div>

                                <div class="row" style="margin-top:10px;">
                                    <div class="col-md-6">
                                        <div class="col-md-4">
                                            <label class="control-label text-dark">Ngày chọn xe</label>
                                        </div>
                                        <div class="col-md-8">
                                            <div class='input-group date' id="startDate1">
                                                <input ng-model="search.fromGenDate" type='text' class="form-control datepickerEnter" id="fromGenDate" name="fromGenDate" placeholder="dd-MM-yyyy"/>
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
                                            <div class='input-group date' id="startDate2">
                                                <input ng-model="search.toGenDate" type='text' class="form-control datepickerEnter" id="toGenDate" name="toGenDate" placeholder="dd-MM-yyyy"/>
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
                                    <sec:authorize access="hasRole('ROLE_XEM_CHON_XE_NHAN_HANG_VIEW')">
                                        <button ng-disabled="_custum_searchSuccess" ng-init="_custum_searchSuccess = false;" type="button" class="btn btn-info btn-s-sm" ng-click="searchSelect($event);">
                                            <i class="fa fa-search"></i>Tìm kiếm
                                        </button>
                                    </sec:authorize>
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
                            DANH SÁCH XE NHẬN HÀNG
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
                                <sec:authorize access="hasRole('ROLE_THEM_CHON_XE_NHAN_HANG_ADD')">
                                <a href="<%=request.getContextPath()%>/managerVanTai/gom-don-hang/preAdd" class="pull-right btn btn-s-sm btn-info"><i class="fa fa-plus"></i>Chọn xe nhận hàng</a>
                                </sec:authorize>
                            </div>
                        </div>

                        <div class="table-responsive bg-white" style="overflow-x: auto;">
                            <table class="table b-t b-light table-bordered table-hover" style="margin-bottom: 0px;table-layout: fixed;" ng-switch on="listData.rowCount">
                                <thead class="bg-gray">
                                    <tr>
                                        <th class="text-center v-inherit text-dark" style="width: 60px;">STT</th>
                                        <th class="text-center v-inherit text-dark" style="width: 60px;">Tool</th>
                                        <!--<th class="text-center v-inherit text-dark" style="width: 10%;">Số phiếu</th>-->
                                        <th class="text-center v-inherit text-dark" style="width: 10%;">Ngày chọn</th>
                                        <th class="text-center v-inherit text-dark" style="width: 10%;">Nhà xe</th>
                                        <th class="text-center v-inherit text-dark" style="width: 10%;">Loại xe</th>
                                        <th class="text-center v-inherit text-dark" style="width: 10%;">Biển số</th>
                                        <th class="text-center v-inherit text-dark" style="width: 10%;">Người giao</th>
                                        <th class="text-center v-inherit text-dark" style="width: 10%;">Người lập</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <tr ng-switch-when="0">
                                        <td colspan="8" style="height: 100%;background-color: #ececec; line-height: 3.429;text-align: center; font-style: italic;">
                                            Không có dữ liệu
                                        </td>
                                    </tr>
                                    <tr ng-switch-default ng-repeat="item in listData.items track by $index">
                                        <td class="text-center v-inherit" >{{(listData.pageNumber > 0 ? (listData.pageNumber - 1) : 0) * listData.numberPerPage + $index + 1}}</td>
                                        <td class="text-center">
                                            <div class="btn-group">
                                                <button class="btn btn-default btn-xs dropdown-toggle" data-toggle="dropdown"><i
                                                        class="glyphicon glyphicon-cog"></i></button>
                                                <ul class="dropdown-menu pull-left" style="width: 230px;">
                                                    <sec:authorize access="hasRole('ROLE_IN_CHON_XE_NHAN_HANG_EXPORT')">
<!--                                                    <li>
                                                        <a href="javascript:void(0)" ng-click="exportPhieu(item.id)" >In phiếu thu</a>
                                                    </li>-->
                                                    </sec:authorize>
                                                    <sec:authorize access="hasRole('ROLE_SUA_CHON_XE_NHAN_HANG_EDIT')">
                                                        <li>
                                                            <div class="line line-dashed m-b-none m-t-none"></div>
                                                            <a href="<%=request.getContextPath()%>/managerVanTai/gom-don-hang/preEdit/{{item.id}}">
                                                                Chỉnh sửa
                                                            </a>
                                                        </li>
                                                    </sec:authorize>

                                                    <sec:authorize access="hasRole('ROLE_XOA_CHON_XE_NHAN_HANG_DELETE')">
                                                    <li>
                                                        <div class="line line-dashed m-b-none m-t-none"></div>
                                                        <a href="#" ng-click="preXoa(item)">
                                                            Xóa
                                                        </a>
                                                    </li>
                                                    </sec:authorize>
                                                </ul>
                                            </div>
                                        </td>
                                        <!--<td class="text-left v-inherit">{{item.maPhieuGiao}}</td>-->
                                        <td class="text-center v-inherit">{{item.genDate | date:"dd/MM/yyyy"}}</td>
                                        <td class="text-left v-inherit">{{item.nhaXe}}</td>
                                        <td class="text-left v-inherit">{{item.loaiXe}}</td>
                                        <td class="text-left v-inherit">{{item.bienSo}}</td>
                                        <td class="text-left v-inherit">{{item.tenLaiXe}}</td>
                                        <td class="text-left v-inherit">{{item.createdBy}}</td>
                                    </tr>
                                </tbody>
                            </table>
                        </div>

                        <footer class="panel-footer">
                            <div class="row">
                                <div class="p-l-0 col-sm-6 text-left text-center-xs m-b-xs">
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

    <div class="modal fade" id="xoaPhieu" tabindex="-1" role="dialog"
         aria-labelledby="xoaPhieu" aria-hidden="true" aria-label="Close">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header alert-info">
                    <button type="button" class="btn btn-default close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h4 class="modal-title">Xóa chọn xe nhận hàng</h4>
                </div>
                <div class="modal-body">
                    <span>Bạn có chắc chắn muốn xóa bản ghi này </span><span style="color: #3c763d">{{bienSo}}</span> <span> hay không?</span>

                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-primary" data-dismiss="modal" ng-click="xoaPhieu()" style="text-transform: none;"><i class="fa fa-check"></i> <spring:message code="label.button.ok"/></button>
                    <button type="button" class="btn btn-danger" data-dismiss="modal">Hủy bỏ</button>
                </div>
            </div>
        </div>
    </div>
</section>
<script>
    showDropDownOnTable();
</script>