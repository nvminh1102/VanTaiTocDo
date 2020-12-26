<%@ page isELIgnored="false" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<script src="<%=request.getContextPath()%>/assets/project/chooseBienNhan.js${urlCache}"></script>

<div class="modal fade" id="chooseBienNhan" role="dialog" aria-labelledby="myModalLabel1" aria-hidden="true" style="overflow: auto;" ng-controller="popupPhieuNhan">
    <div class="modal-dialog" style="width: 90%;max-width: 1500px;">
        <div class="modal-content">
            <div class="bg-info modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true" >&times;</button>
                <h4 class="modal-title color-modal text-center" style="font-size: 20px"> Chọn biên nhận</h4>
            </div>
            <div class="modal-body">
                <div class="row form-group text-dark">
                    <label class="col-md-2" style="padding: 5px">Số biên nhận</label>
                    <div class="col-md-4">
                        <input ng-model="searchBienNhan.receiptCode" class="form-control input-sm">
                    </div>
                    <label class="col-md-2" style="padding: 5px"></label>
                    <div class="col-md-4">
                    </div>
                </div>
                <div class="row form-group text-dark">
                    <label class="col-md-2" style="padding: 5px">Ngày nhận</label>
                    <div class="col-md-4">
                        <div class='input-group date'>
                            <input ng-model="searchBienNhan.fromDeceipt" type='text' class="form-control datepickerEnter" id="fromDeceipt" name="fromDeceipt"/>
                            <span class="input-group-addon">
                                <span class="glyphicon glyphicon-calendar"></span>
                            </span>
                        </div>
                    </div>
                    <label class="col-md-2" style="padding: 5px"></label>
                    <div class="col-md-4">
                        <div class='input-group date'>
                            <input ng-model="searchBienNhan.toDeceipt" type='text' class="form-control datepickerEnter" id="toDeceipt" name="toDeceipt"/>
                            <span class="input-group-addon">
                                <span class="glyphicon glyphicon-calendar"></span>
                            </span>
                        </div>
                    </div>
                </div>
                <div class="row form-group text-dark">
                    <label class="col-md-2" style="padding: 5px">Nhà xe</label>
                    <div class="col-md-4">
                        <input ng-model="searchBienNhan.nhaXe" class="form-control input-sm">
                    </div>
                    <label class="col-md-2" style="padding: 5px">Kho hàng</label>
                    <div class="col-md-4">
                        <input ng-model="searchBienNhan.nameStock" class="form-control input-sm">
                    </div>
                </div>

                <div class="row form-group text-center">
                    <img class="hidden _custum_searchSuccess" src="<%=request.getContextPath()%>/assets/images/loading.gif" />
                    <button ng-disabled="_custum_searchSuccess" ng-init="_custum_searchSuccess = false;" type="button" class="btn btn-info btn-s-sm" ng-click="searchData($event);">
                        <i class="fa fa-search"></i> Tìm kiếm
                    </button>
                    <button style="margin-left: 10px" type="button" class="btn btn-l btn-gray btn-default" ng-click="clear();">Xóa điều kiện</button>
                </div>


                <div class="row padding" style="padding-top: 0px;">
                    <label class="input-sm">Tổng số: </label>
                    <label style="color: red;">{{listBienNhan.rowCount}}</label>
                    <label class="input-sm">Hiển thị: </label>
                    <select class="input-sm form-control input-s-sm inline" style="width: 60px;" ng-model="numberPerPage" ng-change="setNumberPerPage(numberPerPage);" ng-init="numberPerPage = '25'">
                        <option value="10">10</option>
                        <option value="20">20</option>
                        <option value="25">25</option>
                    </select>
                </div>

                <div class="table-responsive bg-white"  style="overflow-x: auto">
                    <table class="table b-t b-light table-bordered table-hover" style="margin-bottom: 0px;table-layout: fixed;" ng-switch on="listBienNhan.rowCount">
                        <thead class="bg-gray">
                            <tr>
                                <th class="text-center v-inherit text-dark" style="width: 7%;">STT</th>
                                <th class="text-center v-inherit text-dark" style="width: 10%;">Số biên nhận</th>
                                <th class="text-center v-inherit text-dark" style="width: 10%;">Người gửi</th>
                                <th class="text-center v-inherit text-dark" style="width: 10%;">Người nhận</th>
                                <th class="text-center v-inherit text-dark" style="width: 10%;">Điện thoại</th>
                                <th class="text-center v-inherit text-dark" style="width: 20%;">Địa chỉ</th>
                                <th class="text-center v-inherit text-dark" style="width: 10%;">Số thùng</th>
                                <th class="text-center v-inherit text-dark" style="width: 10%;">Số HD</th>
                                <th class="text-center v-inherit text-dark" style="width: 10%;">Ghi chú</th>
                                <th class="text-center v-inherit text-dark" style="width: 5%;"><input id="select_all" type="checkbox" ng-model="checkAll" ng-change="selectAll(checkAll)"/></th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr ng-switch-when="0">
                                <td colspan="13" style="height: 100%;background-color: #ececec; line-height: 3.429;text-align: center; font-style: italic;">
                                    Không có dữ liệu
                                </td>
                            </tr>
                            <tr ng-switch-default ng-repeat="item in listBienNhan.items track by $index">
                                <td class="text-center v-inherit">{{(listBienNhan.pageNumber>0 ? (listBienNhan.pageNumber - 1):0) * listBienNhan.numberPerPage + $index + 1}}</td>
                                <td class="text-left v-inherit">{{item.receiptCode}}</td>
                                <td class="text-center v-inherit">{{item.deliveryPartnerName}}</td>
                                <td class="text-center v-inherit">{{item.receivePartnerName}}</td>
                                <td class="text-center v-inherit">{{item.receivePartnerMobile}}</td>
                                <td class="text-center v-inherit">{{item.receivePartnerAddress}}</td>
                                <td class="text-center v-inherit">{{item.numbers}}</td>
                                <td class="text-center v-inherit">{{item.numbers}}</td>
                                <td class="text-center v-inherit">{{item.note}}</td>
                                <td class="text-center v-inherit"><input type="checkbox" class="onChangeSelectBox_" ng-model="checked[$index]" ng-init="checked[$index] = false" ng-change="chooseBienNhan(item, checked[$index])"/></td>
                            </tr>
                        </tbody>
                    </table>
                </div>

                <footer class="panel-footer">
                    <div class="row">
                        <div class="p-r-0 col-sm-12 text-right text-center-xs">
                            <ul class="pagination pagination-sm m-t-none m-b-none">
                                <li ng-if="listBienNhan.pageNumber > 1"><a href="javascript:void(0)" ng-click="loadPageData(1)">«</a></li>
                                <li ng-repeat="item in listBienNhan.pageList">
                                    <a href="javascript:void(0)" ng-if="item == listBienNhan.pageNumber" style="color:mediumvioletred;"> {{item}}</a>
                                    <a href="javascript:void(0)" ng-if="item != listBienNhan.pageNumber" ng-click="loadPageData(item)"> {{item}}</a>
                                </li>
                                <li ng-if="listBienNhan.pageNumber < listBienNhan.pageCount"><a href="javascript:void(0)" ng-click="loadPageData(listBienNhan.pageCount)">»</a></li>
                            </ul>
                        </div>
                    </div>
                </footer>
            </div>
            <div class="modal-footer text-center" style="text-align: center;">
                <button type="button" ng-click="addListBienNhan();" class="btn btn-1 btn-info btn-default" data-dismiss="modal"><i class="fa fa-check"></i>Đồng ý</button>
                <button style="margin-left: 10px;" type="button" class="btn btn-1 btn-gray btn-default" data-dismiss="modal">Hủy bỏ</button>
            </div>

        </div>
    </div>
</div>