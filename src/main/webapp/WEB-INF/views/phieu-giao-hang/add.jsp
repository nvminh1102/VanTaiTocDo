<%@ page isELIgnored="false" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>


<script src="<%=request.getContextPath()%>/assets/project/phieu-giao-hang/add.js${urlCache}" type="text/javascript"></script>
<script>
    var id = '${id}';
    var maPhieuGiao = '${maPhieuGiao}';</script>
<section id="content" ng-app="FrameworkBase"  >
    <section class="vbox background-white">
        <section class="scrollable padder">
            <ul class="bg-light breadcrumb no-border no-radius b-light pull-in">
                <li><a href="<%=request.getContextPath()%>/"><i class="fa fa-home"></i>&nbsp;Trang chủ</a></li>
                <li><a href="<%=request.getContextPath()%>/managerVanTai/phieu-giao-hang/list">Phiếu giao hàng</a></li>
                <li><a href="javascript:void(0)">Thông tin phiêu giao hàng</a></li>
            </ul>
            <section class="panel panel-default" style="margin-bottom: 5px;"  ng-controller="vantai">
                <header class="panel-heading">
                    <a href="javascript:void(0)"><h4 class="panel-title text-center font-bold font-size28" data-toggle="collapse" data-target="#collapseOne">PHIẾU GIAO HÀNG</h4></a>
                </header>
                <div id="collapseOne" class="panel-collapse collapse in" aria-expanded="true">
                    <div class="panel-body background-xam">
                        <form id="formAdd" class="form-horizontal"  data-parsley-validate="true">
                            <div id="collapseSearch" class="panel-collapse collapse in" aria-expanded="true">
                                <div class="form-group">
                                    <label class="col-md-2 control-label text-dark">Số phiếu giao hàng<span style="color: red;">(*)</span></label>
                                    <div class="col-md-4">
                                        <input ng-model="phieuGiao.maPhieuGiao"  data-parsley-required="true"  data-parsley-error-message="Số phiều giao hàng bắt buộc nhập!!" maxlength="200" class="form-control input-sm"/>
                                    </div>
                                    <label class="col-md-2 control-label text-dark">Biển số</label>
                                    <div class="col-md-4">
                                        <select ng-model="phieuGiao.bienSo" id="idBienSo" class="select2" style="width: 100%" ng-change="onChangeBienSo()">
                                            <option value="" selected >-- Lựa chọn --</option>
                                            <option ng-repeat="item in nhaXeList"  value="{{item.bienSo}}">nhà xe: {{item.nhaXe}} -- biển số: {{item.bienSo}}</option>
                                        </select>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-2 control-label text-dark">Nhà xe</label>
                                    <div class="col-md-4">
                                        <input ng-model="phieuGiao.nhaXe" maxlength="200" class="form-control input-sm" readonly/>
                                    </div>
                                    <label class="col-md-2 control-label text-dark">Loại xe</label>
                                    <div class="col-md-4">
                                        <input ng-model="phieuGiao.loaiXe" maxlength="200" class="form-control input-sm" readonly/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-2 control-label text-dark">Tên lái xe</label>
                                    <div class="col-md-4">
                                        <input ng-model="phieuGiao.tenLaiXe" maxlength="200" class="form-control input-sm" readonly/>
                                    </div>
                                    <label class="col-md-2 control-label text-dark">Số điện thoại lái xe</label>
                                    <div class="col-md-4">
                                        <input ng-model="phieuGiao.sdtLaiXe" maxlength="20" class="form-control input-sm" readonly/>
                                    </div>
                                </div>
                                <div class="row" style="margin: 0px;">
                                    <div class="p-r-0 p-l-0 col-md-6">
                                        <label class="input-sm">Số bản ghi: </label>
                                        <label style="color: red;">{{listBienNhanDaChon.items.length}} - {{listHangHoa.items.length}}</label>
                                    </div>
                                    <div class="p-r-0 p-l-0 col-md-6">
                                        <sec:authorize access="hasRole('ROLE_THEM_GIAO_HANG_ADD')">
                                            <button type="button" class="pull-right btn btn-info btn-s-sm" data-toggle="modal" data-target="#chonPhieuNhanHang"><i class="fa fa-plus"></i> Chọn phiếu nhận hàng</button>
                                        </sec:authorize>
                                    </div>
                                </div>
                                <div class="table-responsive bg-white" style="margin-bottom: 10px;">
                                    <table class="table b-t b-light table-bordered table-hover" style="margin-bottom: 0px;" ng-switch on="listBienNhanDaChon.items.length">
                                        <thead class="bg-gray">
                                            <tr>
                                                <th class="text-center v-inherit text-dark">STT</th>
                                                <th class="text-center v-inherit text-dark">Số phiếu nhận</th>
                                                <th class="text-center v-inherit text-dark">Người gửi</th>
                                                <th class="text-center v-inherit text-dark">Người nhận</th>
                                                <th class="text-center v-inherit text-dark">Điện thoại</th>
                                                <th class="text-center v-inherit text-dark">Địa chỉ</th>
                                                <th class="text-center v-inherit text-dark">Số thùng</th>
                                                <th class="text-center v-inherit text-dark">Số HD</th>
                                                <th class="text-center v-inherit text-dark">Phiếu thu số</th>
                                                <th class="text-center v-inherit text-dark">Ghi chú</th>
                                                <th class="text-center v-inherit text-dark">Tool</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <tr ng-switch-when="0">
                                                <td colspan="11" style="height: 100%;background-color: #ececec; line-height: 3.429;text-align: center; font-style: italic;">
                                                    Không có dữ liệu
                                                </td>
                                            </tr>
                                            <tr ng-switch-default ng-repeat="item in listBienNhanDaChon.items track by $index">
                                                <td class="text-center v-inherit" >{{$index + 1}}</td>
                                                <td class="text-center v-inherit">{{item.receiptCode}}</td>
                                                <td class="text-center v-inherit">{{item.tenNguoiGui}}</td>
                                                <td class="text-center v-inherit">{{item.tenNguoiNhan}}</td>
                                                <td class="text-center v-inherit">{{item.mobileNguoiNhan}}</td>
                                                <td class="text-center v-inherit">{{item.diaChiNguoiNhan}}</td>
                                                <td class="text-center v-inherit">{{item.soLuong}}</td>
                                                <td class="text-center v-inherit">{{item.soHopDong}}</td>
                                                <td class="text-center v-inherit">{{item.maPhieuThu}}</td>
                                                <td class="text-center v-inherit">{{item.note}}</td>
                                                <td class="text-center v-inherit">
                                                    <a  class="btn btn-success btn-sm font-bold" href="" ng-click="boChonBienNhan(item)"><i class="fa fa-edit"></i>xóa</a>
                                        <sec:authorize access="hasRole('ROLE_EXPORT_PHIEU_VIEW')">
                                            <a ng-show="item.paymentType != null && item.paymentType == '1'"  class="btn btn-success btn-sm font-bold" href="" ng-click="exportPhieuThu(item.id)"><i class="fa fa-edit"></i>In phiếu thu</a>
                                        </sec:authorize>
                                        </td>
                                        </tr>
                                        </tbody>
                                    </table>
                                </div>
                                <div class="form-group">
                                    <div class="col-md-12 text-center">
                                        <sec:authorize access="hasRole('ROLE_THEM_GIAO_HANG_ADD')">
                                            <button type="button" class="btn btn-info btn-s-sm" data-toggle="modal"  ng-click="savePhieu()" ><i class="fa fa-plus"></i>Lưu thông tin</button>
                                        </sec:authorize>
                                        <a href="<%=request.getContextPath()%>/managerVanTai/phieu-giao-hang/list" class="btn btn-danger"><i class="fa fa-times"></i>Hủy</a>
                                    </div>
                                </div>
                            </div>
                        </form>                      
                    </div>
                </div>

                <div class="modal fade" id="chonPhieuNhanHang" role="dialog" aria-labelledby="myModalLabel1" aria-hidden="true" style="overflow: auto;" >
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
                                                <th class="text-center v-inherit text-dark" style="width: 10%;">Phiếu thu số</th>
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
                                                <td class="text-center v-inherit">{{item.soLuong}}</td>
                                                <td class="text-center v-inherit">{{item.soHopDong}}</td>
                                                <td class="text-center v-inherit">{{item.soLuong}}</td>
                                                <td class="text-center v-inherit">{{item.maPhieuThu}}</td>
                                                <td class="text-center v-inherit"><input type="checkbox" class="onChangeBNSelectBox_" ng-model="checked[$index]" ng-init="checked[$index] = false" ng-change="chooseBienNhan(item, checked[$index])"/></td>
                                            </tr>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                            <div class="modal-footer text-center" style="text-align: center;">
                                <button type="button" class="btn btn-1 btn-info btn-default" data-dismiss="modal"><i class="fa fa-check"></i>Đồng ý</button>
                                <button style="margin-left: 10px;" type="button" class="btn btn-1 btn-gray btn-default" data-dismiss="modal">Hủy bỏ</button>
                            </div>

                        </div>
                    </div>
                </div>


            </section>

        </section>
    </section>
</section>