<%@ page isELIgnored="false" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<script src="<%=request.getContextPath()%>/assets/project/chooseBienNhan.js${urlCache}"></script>

<div class="modal fade" id="chonPhieuNhanHang" role="dialog" aria-labelledby="myModalLabel1" aria-hidden="true" style="overflow: auto;" ng-controller="popupPhieuNhan">
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
                    <button ng-disabled="_custum_searchSuccess" ng-init="_custum_searchSuccess = false;" type="button" class="btn btn-info btn-s-sm" ng-click="loadListData($event);">
                        <i class="fa fa-search"></i> Tìm kiếm
                    </button>
                    <button style="margin-left: 10px" type="button" class="btn btn-l btn-gray btn-default" ng-click="clear();">Xóa điều kiện</button>
                </div>


                <div class="row padding" style="padding-top: 0px;">
                    <label class="input-sm">Tổng số phiếu nhận hàng: </label>
                    <label style="color: red;">{{listBienNhan.length}}</label>
                </div>

                <div class="table-responsive bg-white"  style="overflow-x: auto">
                    <table class="table b-t b-light table-bordered table-hover" style="margin-bottom: 0px;table-layout: fixed;" ng-switch on="listBienNhan.length">
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
                                <th class="text-center v-inherit text-dark" style="width: 5%;"><input id="select_allBN" type="checkbox" ng-model="checkAll" ng-change="selectAll(checkAll)"/></th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr ng-switch-when="0">
                                <td colspan="13" style="height: 100%;background-color: #ececec; line-height: 3.429;text-align: center; font-style: italic;">
                                    Không có dữ liệu
                                </td>
                            </tr>
                            <tr ng-switch-default ng-repeat="item in listBienNhan track by $index">
                                <td class="text-center v-inherit">{{$index + 1}}</td>
                                <td class="text-left v-inherit">{{item.receiptCode}}</td>
                                <td class="text-center v-inherit">{{item.tenNguoiGui}}</td>
                                <td class="text-center v-inherit">{{item.tenNguoiNhan}}</td>
                                <td class="text-center v-inherit">{{item.mobileNguoiNhan}}</td>
                                <td class="text-center v-inherit">{{item.diaChiNguoiNhan}}</td>
                                <td class="text-center v-inherit">{{item.numbers}}</td>
                                <td class="text-center v-inherit">{{item.numbers}}</td>
                                <td class="text-center v-inherit">{{item.note}}</td>
                                <td class="text-center v-inherit"><input type="checkbox" class="onChangeBNSelectBox_" ng-model="checked[$index]" ng-init="checked[$index] = false" ng-change="chooseBienNhan(item, checked[$index])"/></td>
                            </tr>
                        </tbody>
                    </table>
                </div>
                    
               <div class="row padding" style="padding-top: 0px;">
                    <label class="input-sm">Tổng số hàng hóa: </label>
                    <label style="color: red;">{{listHangHoa.length}}</label>
                </div>

                <div class="table-responsive bg-white"  style="overflow-x: auto">
                    <table class="table b-t b-light table-bordered table-hover" style="margin-bottom: 0px;table-layout: fixed;" ng-switch on="listHangHoa.length">
                        <thead class="bg-gray">
                            <tr>
                                <th class="text-center v-inherit text-dark" style="width: 7%;">STT</th>
                                <th class="text-center v-inherit text-dark" style="width: 10%;">Mã phiếu nhận hàng</th>
                                <th class="text-center v-inherit text-dark" style="width: 10%;">Tên hàng</th>
                                <th class="text-center v-inherit text-dark" style="width: 10%;">Đơn vị tính</th>
                                <th class="text-center v-inherit text-dark" style="width: 10%;">Số lượng</th>
                                <th class="text-center v-inherit text-dark" style="width: 10%;">Trọng lượng</th>
                                <th class="text-center v-inherit text-dark" style="width: 20%;">Kích thước</th>
                                <th class="text-center v-inherit text-dark" style="width: 10%;">Thành tiền</th>
                                <th class="text-center v-inherit text-dark" style="width: 10%;">Ghi chú</th>
                                <th class="text-center v-inherit text-dark" style="width: 5%;"><input id="select_allHH" type="checkbox" ng-model="checkAllHH" ng-change="selectAllHH(checkAllHH)"/></th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr ng-switch-when="0">
                                <td colspan="13" style="height: 100%;background-color: #ececec; line-height: 3.429;text-align: center; font-style: italic;">
                                    Không có dữ liệu
                                </td>
                            </tr>
                            <tr ng-switch-default ng-repeat="item2 in listHangHoa track by $index">
                                <td class="text-center v-inherit">{{$index + 1}}</td>
                                <td class="text-left v-inherit">{{item2.receiptCode}}</td>
                                <td class="text-left v-inherit">{{item2.name}}</td>
                                <td class="text-center v-inherit">{{item2.unit}}</td>
                                <td class="text-center v-inherit">{{item2.numbers}}</td>
                                <td class="text-center v-inherit">{{item2.weight}}</td>
                                <td class="text-center v-inherit">{{item2.sizes}}</td>
                                <td class="text-center v-inherit">{{item2.cost}}</td>
                                <td class="text-center v-inherit">{{item2.note}}</td>
                                <td class="text-center v-inherit"><input type="checkbox" class="onChangeHHSelectBox_" ng-model="checkedHH[$index]" ng-init="checkedHH[$index] = false" ng-change="chonHH(item2, checkedHH[$index])"/></td>
                            </tr>
                        </tbody>
                    </table>
                </div>
                    
            </div>
            <div class="modal-footer text-center" style="text-align: center;">
                <button type="button" ng-click="addListBienNhan();" class="btn btn-1 btn-info btn-default" data-dismiss="modal"><i class="fa fa-check"></i>Đồng ý</button>
                <button style="margin-left: 10px;" type="button" class="btn btn-1 btn-gray btn-default" data-dismiss="modal">Hủy bỏ</button>
            </div>

        </div>
    </div>
</div>