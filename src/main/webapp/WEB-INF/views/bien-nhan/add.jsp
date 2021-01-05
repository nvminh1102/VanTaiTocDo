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

<style>
    table {
        font-size: 1em;
    }

    .ui-draggable, .ui-droppable {
        background-position: top;
        z-index: 999999999999999;
    }
    /*.modal {*/
    /*z-index: unset;*/
    /*}*/
    .ui-widget {
        font-family: Arial,Helvetica,sans-serif;
        font-size: 1em;
    }
    .ui-widget input, .ui-widget select, .ui-widget textarea, .ui-widget button {
        font-family: Arial,Helvetica,sans-serif;
        font-size: 1em;
    }

    .ui-widget.ui-widget-content {
        border: 1px solid #c5c5c5;
    }
    .ui-menu {
        list-style: none;
        padding: 0;
        margin: 0;
        display: block;
        outline: 0;
    }
    .ui-autocomplete {
        position: absolute;
        top: 0;
        left: 0;
        cursor: default;
    }
    .ui-front {
        z-index: 100;
    }
    .ui-widget.ui-widget-content {
        border: 1px solid #c5c5c5;
        display: none;
        top: 145px;
        left: 397px;
        z-index: 999999999;
        width: 700px;
    }

</style>
<script src="<%=request.getContextPath()%>/assets/project/bien-nhan/add.js"></script>
<section id="content" ng-app="FrameworkBase"  ng-controller="frameworkCtrl">
    <section class="vbox">
        <section class="scrollable padder" style="background: white;padding-bottom: 200px;">
            <ul class="bg-primary breadcrumb no-border no-radius b-b b-light pull-in">
                <li><a href="<%=request.getContextPath()%>/"><i class="fa fa-home"></i>&nbsp;Trang chủ</a></li>
                <li><a href="<%=request.getContextPath()%>/managerVanTai/bienNhan/list">Phiếu nhận hàng</a></li>
                <li><a href="#" id="title">Thêm mới phiếu nhận hàng</a></li>
            </ul>
            <%--<div class="m-b-md">--%>
            <%--<span style="color:red">${messageError}</span>--%>
            <%--</div>--%>

            <section class="panel panel-default">
                <header class="panel-heading" style="text-align: center"><h4>THÊM MỚI PHIẾU NHẬN HÀNG</h4></header>
                <div class="panel-body">
                    <form method="post" action="javascript:void(0)"  enctype="multipart/form-data" class="form-horizontal" id="addBienNhanForm" name="addBienNhanForm">
                        <div class="row" style="padding-top: 1%; padding-bottom: 1%">
                            <div class="col-md-12">
                                <div class="col-sm-2"  style="text-align: right">
                                    <label>Số phiếu nhận</label>
                                </div>
                                <div class="col-sm-4">
                                    <input ng-model="info.bienNhan.receiptCode" name="receiptCode" id="receiptCode" maxlength="250"
                                           class="input-sm form-control" readonly/>
                                </div>

                                <div class="col-sm-2"  style="text-align: right">
                                    <label>Kho nhận <span style="color: red">*</span></label>
                                </div>
                                <div class="col-sm-4">
                                    <input ng-model="info.bienNhan.nameStock" name="nameStock" id="nameStock" maxlength="250"
                                           class="input-sm form-control"/>
                                </div>
                            </div>
                        </div>

                        <div class="row" style="padding-top: 1%; padding-bottom: 1%">
                            <div class="col-md-12">
                                <div class="col-sm-2"  style="text-align: right">
                                    <label>Ngày nhận</label>
                                </div>
                                <div class="col-sm-4">
                                    <div class='input-group date' id="startDate1">
                                        <input ng-model="info.bienNhan.dateReceipt" type='text' class="form-control datepickerEnter" id="dateReceipt" name="dateReceipt"/>
                                        <span class="input-group-addon">
                                            <span class="glyphicon glyphicon-calendar"></span>
                                        </span>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <div class="row" style="padding-top: 1%; padding-bottom: 1%">
                            <div class="col-md-12">
                                <div class="col-sm-2"  style="text-align: right">
                                    <label>Loại thanh toán <span style="color: red">*</span></label>
                                </div>
                                <div class="col-sm-4">
                                    <select class="form-control" name="paymentType" id="paymentType" ng-model="info.bienNhan.paymentType">
                                        <option value="" selected>Loại thanh toán</option>
                                        <option ng-value="1">Trả trước</option>
                                        <option ng-value="2">Trả sau</option>
                                        <option ng-value="3">Công nợ</option>
                                    </select>
                                </div>
                                <div class="col-sm-2" style="text-align: right">
                                    <label>Người thanh toán<span style="color: red">*</span></label>
                                </div>
                                <div class="col-md-4">
                                    <select ng-model="info.bienNhan.nguoiThanhToanId" id="info.bienNhan.nguoiThanhToanId" name ="info.bienNhan.nguoiThanhToanId"  class="select2" style="width: 100%">
                                        <option value="" selected >-- Lựa chọn --</option>
                                        <option ng-value="-2">Người gửi</option>
                                        <option ng-value="-3">Người nhận</option>
                                        <option ng-repeat="item in khachHangList track by $index"  ng-value="item.id">{{item.fullName}}</option>
                                    </select>
                                </div>

                            </div>
                        </div>

                        <div class="row" style="padding-top: 1%; padding-bottom: 1%">
                            <div class="col-md-12">
                                <div class="col-sm-2" style="text-align: right">
                                    <label>MST/CMND người gửi <span style="color: red">*</span></label>
                                </div>
                                <div class="col-sm-4">
                                    <input ng-model="info.nguoiGui.taxCode" name="nguoiGui.taxCode" id="nguoiGui.taxCode" maxlength="250"
                                           class="input-sm form-control"/>
                                </div>

                                <div class="col-sm-2" style="text-align: right">
                                    <label>Tên người gửi <span style="color: red">*</span></label>
                                </div>
                                <div class="col-sm-4">
                                    <input ng-model="info.nguoiGui.fullName" name="nguoiGui.fullName" id="nguoiGui.fullName" maxlength="250"
                                           class="input-sm form-control"/>
                                </div>

                            </div>
                        </div>

                        <div class="row" style="padding-top: 1%; padding-bottom: 1%">
                            <div class="col-md-12">
                                <div class="col-sm-2"  style="text-align: right">
                                    <label>Số điện thoại</label>
                                </div>
                                <div class="col-sm-4">
                                    <input ng-model="info.nguoiGui.mobile" name="nguoiGui.mobile" id="nguoiGui.mobile" maxlength="20"
                                           class="input-sm form-control"/>
                                </div>

                                <div class="col-sm-2"  style="text-align: right">
                                    <label>Địa chỉ người gửi</label>
                                </div>
                                <div class="col-sm-4">
                                    <input ng-model="info.nguoiGui.address" name="nguoiGui.address" id="nguoiGui.address" maxlength="500"
                                           class="input-sm form-control"/>
                                </div>
                            </div>
                        </div>
                        <div class="row" style="padding-top: 1%; padding-bottom: 1%">
                            <div class="col-md-12">
                                <div class="col-sm-2"  style="text-align: right">
                                    <label>Số hợp đồng</label>
                                </div>
                                <div class="col-sm-4">
                                    <input ng-model="info.nguoiGui.soHopDong" name="nguoiGui.soHopDong" id="nguoiGui.soHopDong" maxlength="200"
                                           class="input-sm form-control"/>
                                </div>
                            </div>
                        </div>

                        <%--người nhận--%>

                        <div class="row" style="padding-top: 1%; padding-bottom: 1%">
                            <div class="col-md-12">
                                <div class="col-sm-2" style="text-align: right">
                                    <label>MST/CMND người nhận <span style="color: red">*</span></label>
                                </div>
                                <div class="col-sm-4">
                                    <input ng-model="info.nguoiNhan.taxCode" name="nguoiNhan.taxCode" id="nguoiNhan.taxCode" maxlength="250"
                                           class="input-sm form-control"/>
                                </div>

                                <div class="col-sm-2" style="text-align: right">
                                    <label>Tên người nhận <span style="color: red">*</span></label>
                                </div>
                                <div class="col-sm-4">
                                    <input ng-model="info.nguoiNhan.fullName" name="nguoiNhan.fullName" id="nguoiNhan.fullName" maxlength="250"
                                           class="input-sm form-control"/>
                                </div>

                            </div>
                        </div>

                        <div class="row" style="padding-top: 1%; padding-bottom: 1%">
                            <div class="col-md-12">
                                <div class="col-sm-2"  style="text-align: right">
                                    <label>Số điện thoại</label>
                                </div>
                                <div class="col-sm-4">
                                    <input ng-model="info.nguoiNhan.mobile" name="nguoiNhan.mobile" id="nguoiNhan.mobile" maxlength="20"
                                           class="input-sm form-control"/>
                                </div>

                                <div class="col-sm-2"  style="text-align: right">
                                    <label>Địa chỉ người nhận</label>
                                </div>
                                <div class="col-sm-4">
                                    <input ng-model="info.nguoiNhan.address" name="nguoiNhan.address" id="nguoiNhan.address" maxlength="500"
                                           class="input-sm form-control"/>
                                </div>
                            </div>
                        </div>
                        <div class="row" style="padding-top: 1%; padding-bottom: 1%">
                            <div class="col-md-12">
                                <div class="col-sm-2"  style="text-align: right">
                                    <label>Số hợp đồng</label>
                                </div>
                                <div class="col-sm-4">
                                    <input ng-model="info.nguoiNhan.soHopDong" name="nguoiNhan.soHopDong" id="nguoiNhan.soHopDong" maxlength="200"
                                           class="input-sm form-control"/>
                                </div>
                            </div>
                        </div>

                        <%--nhà xe--%>

                        <div class="row" style="padding-top: 1%; padding-bottom: 1%">
                            <div class="col-md-12">
                                <div class="col-sm-2"  style="text-align: right">
                                    <label>Biển số</label>
                                </div>
                                <div class="col-sm-4">
                                    <%--<input ng-model="info.bienNhan.bienSo" name="bienNhan.bienSo" id="bienNhan.bienSo" maxlength="20"--%>
                                    <%--class="input-sm form-control"/>--%>
                                    <select ng-model="info.bienNhan.bienSo" id="info.bienNhan.bienSo" name ="info.bienNhan.bienSo" class="select2" style="width: 100%" ng-change="onChangeBienSo()">
                                        <option value="" selected >-- Lựa chọn --</option>
                                        <option ng-repeat="item in nhaXeList track by $index" ng-value="item.bienSo">nhà xe: {{item.nhaXe}} -- biển số: {{item.bienSo}}</option>
                                    </select>

                                </div>
                                <div class="col-sm-2"  style="text-align: right">
                                    <label>Nhà xe</label>
                                </div>
                                <div class="col-sm-4">
                                    <input ng-model="info.bienNhan.nhaXe" name="bienNhan.nhaXe" id="bienNhan.nhaXe" maxlength="250"
                                           class="input-sm form-control" readonly/>
                                </div>
                            </div>
                        </div>

                        <div class="row" style="padding-top: 1%; padding-bottom: 1%">
                            <div class="col-md-12">
                                <div class="col-sm-2"  style="text-align: right">
                                    <label>Loại xe</label>
                                </div>
                                <div class="col-sm-4">
                                    <input ng-model="info.bienNhan.loaiXe" name="bienNhan.loaiXe" id="bienNhan.loaiXe" maxlength="250"
                                           class="input-sm form-control" readonly/>
                                </div>

                                <div class="col-sm-2"  style="text-align: right">
                                    <label>Nhân viên nhà xe</label>
                                </div>
                                <div class="col-sm-4">
                                    <input ng-model="info.bienNhan.employee" name="bienNhan.employee" id="bienNhan.employee" maxlength="250"
                                           class="input-sm form-control" readonly/>
                                </div>
                            </div>
                        </div>

                        <div class="row" style="padding-top: 1%;">
                            <div class="col-md-12" style="text-align: right">
                                <button type="button" class="btn btn-primary" style="width: 135px; background: #0e388c; margin-right: 15px; display: ${info.id != null ? "none" : ""}" data-toggle="modal" data-target="#addPropertyInfo" id="btnThemMatHang" ng-click="onAddProperty()">Thêm mặt hàng</button>
                            </div>
                        </div>

                        <div class="row" style="padding-top: 1%; padding-bottom: 1%">
                            <div class="table-responsive table-overflow-x-fix" style="padding-top: 1%; padding-bottom: 1%">
                                <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 col-xl-12">
                                    <table id="tblAsset" class="table table-striped table-hover m-b-none">
                                        <thead>
                                            <tr>
                                                <th style="text-align: center">STT</th>
                                                <th style="text-align: center">Mặt hàng</th>
                                                <th style="text-align: center">Đơn vị tính</th>
                                                <th style="text-align: center">Số lượng</th>
                                                <th style="text-align: center">Trọng lượng</th>
                                                <th style="text-align: center">Kích thước</th>
                                                <th style="text-align: center">Thành tiền</th>
                                                    <%--<th style="text-align: center">Giấy tờ kèm theo</th>--%>
                                                <th style="text-align: center">Ghi chú</th>
                                                <th style="text-align: center">Tools</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <tr ng-repeat="item in info.property track by $index">
                                                <td style="text-align: center; font-weight: normal">{{$index + 1}}</td>
                                                <td style="text-align: center; font-weight: normal">{{item.name}}</td>
                                                <td style="text-align: center; font-weight: normal">{{item.unit}}</td>
                                                <td style="text-align: center; font-weight: normal">{{item.numbers}}</td>
                                                <td style="text-align: center; font-weight: normal">{{item.weight}}</td>
                                                <td style="text-align: center; font-weight: normal">{{item.sizes}}</td>
                                                <td style="text-align: center; font-weight: normal">{{item.cost > 0 ? item.cost : null|number}}</td>
                                                <%--<td style="text-align: center; font-weight: normal">file đính kèm</td>--%>
                                                <td style="text-align: center; font-weight: normal">{{item.note}}</td>
                                                <td style="text-align: center; font-weight: normal">
                                                    <span ng-click="editProperty($index)">
                                                        <i title="Chỉnh sửa" class="fa fa-pencil-square-o" style="color:#428bca" id="icnEditProperty"></i>
                                                    </span>
                                                    <span ng-click="deleteProperty($index)">
                                                        <i title="Xóa"  class="fa fa-times" style="color:red"></i>
                                                    </span>
                                                </td>
                                            </tr>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                        </div>

                        <div class="row" style="padding-top: 1%; padding-bottom: 1%">
                            <div class="col-md-12" style="text-align: center; padding-bottom: 30px; padding-top: 30px">
                                <button id="btnSaveAuctionInfo" class="btn btn-primary" style="width: 115px" ng-click="saveAuctionInfo()">Lưu biên nhận</button>
                                <a href="<%=request.getContextPath()%>/managerVanTai/bienNhan/list" class="btn btn-default" style="width: 115px">Hủy bỏ</a>
                            </div>
                        </div>
                    </form>
                </div>
            </section>
        </section>

        <div class="modal fade"  id="addPropertyInfo"  role="dialog" aria-labelledby="myModalLabel1" aria-hidden="true" data-keyboard="false" data-backdrop="static">
            <div class="modal-dialog" style="width: 60%">
                <div class="modal-content">
                    <div class="modal-header alert-success" style="padding: 7px;">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                        <h5 class="modal-title" id="myModalLable" style="font-size: 14pt">Thêm mới mặt hàng</h5>
                    </div>
                    <%--<form id="addPropertyInfoForm" action="javascript:void(0)" enctype="multipart/form-data" cssClass="form-horizontal">--%>
                    <div class="modal-body"  style="padding: 10px;">
                        <div class="form-group ui-widget">
                            Tên hàng hóa(<font color="red">*</font>)
                            <input ng-model="property.name" name="name" id="name" maxlength="500" style="width: 95%; height: 60%" class="form-control"  type="text"/>
                        </div>
                        <div class="form-group">
                            Đơn vị tính
                            <input ng-model="property.unit" name="unit" id="unit" maxlength="100" style="width: 95%; height: 60%" class="form-control"  type="text"/>
                        </div>

                        <div class="form-group">
                            Số lượng
                            <input ng-model="property.numbers" name="numbers" id="numbers" maxlength="15" style="width:95%; height: 60%" class="form-control"/>
                        </div>

                        <div class="form-group">
                            Trọng lượng
                            <input ng-model="property.weight" name="weight" id="weight" maxlength="200" style="width:95%; height: 60%" class="form-control"/>
                        </div>

                        <div class="form-group">
                            Kích thước
                            <input ng-model="property.sizes" name="sizes" id="sizes" maxlength="200" style="width:95%; height: 60%" class="form-control"/>
                        </div>

                        <div class="form-group">
                            Thành tiền
                            <input ng-model="property.cost" name="cost" id="cost" maxlength="15" style="width:95%; height: 60%" class="form-control" data-type="currency" />
                        </div>

                        <%--<div class="form-group">--%>
                        <%--File đính kèm--%>
                        <%--</div>--%>

                        <div class="form-group">
                            Ghi chú
                            <textarea class="form-control" ng-model="property.note" rows="2" id="note" name="note" style="width: 95%" maxlength="500"></textarea>
                        </div>
                    </div>
                    <div style="text-align: center; padding-bottom: 30px; padding-top: 30px">
                        <button type="button" class="btn btn-default" data-dismiss="modal" style="width: 100px" ng-click="resetFormAsset()">Hủy bỏ</button>
                        <button id="btnSavePropertyInfo" class="btn btn-primary" style="width: 100px" ng-click="saveProperty()">Thêm</button>
                    </div>
                    <%--</form>--%>
                </div>
            </div>
        </div>

        <%--<div class="modal fade"  id="deleteAsset"  role="dialog" aria-labelledby="myModalLabel1" aria-hidden="true">--%>
        <%--<div class="modal-dialog" style="max-width: 500px;">--%>
        <%--<div class="modal-content"style="max-width: 500px;">--%>
        <%--<div class="modal-header" style="padding: 7px;">--%>
        <%--<h5 class="modal-title" id="myModalLable2" style="font-size: 14pt">Xác nhận xóa tài sản đã nhập</h5>--%>
        <%--<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>--%>
        <%--</div>--%>
        <%--<form id="deleteAssetForm" method="post" action="javascript:void(0)" theme="simple" enctype="multipart/form-data" cssClass="form-horizontal" cssStyle="" validate="false">--%>
        <%--<div class="modal-body"  style="padding: 10px;">--%>
        <%--<label>Bạn có chắc chắn muốn xóa tài sản này</label>--%>
        <%--</div>--%>
        <%--<div style="text-align: center; padding-bottom: 30px">--%>
        <%--<button type="button" class="btn btn-default" data-dismiss="modal" style="width: 80px; background-color: #c4c4c4 ">Hủy bỏ</button>--%>
        <%--<button id="btnDeleteAsset" class="btn btn-primary" style="width: 80px">Xóa</button>--%>
        <%--</div>--%>
        <%--</form>--%>
        <%--</div>--%>
        <%--</div>--%>
        <%--</div>--%>

    </section>
</section>

<script>
    var receiptCode = '${receiptCode}';
    $(document).ready(function () {
        $('#startDate1').datetimepicker({
            format: 'DD/MM/YYYY',
            sideBySide: true
        }).data('autoclose', true);
    });

    $("input[data-type='currency']").on({
        keyup: function () {
            formatCurrency($(this));
        },
        blur: function () {
            formatCurrency($(this), "blur");
        }
    });

    function formatNumber(n) {
        // format number 1000000 to 1,234,567
        return n.replace(/\D/g, "").replace(/\B(?=(\d{3})+(?!\d))/g, ",")
    }

    function convertToNumber(currency) {
        if (currency == '' || currency == null || currency == 'undefined') {
            return "";
        } else {
            return Number(currency.replace(/[^0-9.-]+/g, ""));
        }
    }
    function formatCurrency(input, blur) {
        // appends $ to value, validates decimal side
        // and puts cursor back in right position.

        // get input value
        var input_val = input.val();

        // don't validate empty input
        if (input_val === "") {
            return;
        }

        // original length
        var original_len = input_val.length;

        // initial caret position
        var caret_pos = input.prop("selectionStart");

        // check for decimal
        if (input_val.indexOf(".") >= 0) {

            // get position of first decimal
            // this prevents multiple decimals from
            // being entered
            var decimal_pos = input_val.indexOf(".");

            // split number by decimal point
            var left_side = input_val.substring(0, decimal_pos);
            var right_side = input_val.substring(decimal_pos);

            // add commas to left side of number
            left_side = formatNumber(left_side);

            // validate right side
            right_side = formatNumber(right_side);

            // On blur make sure 2 numbers after decimal
            if (blur === "blur") {
                right_side += "";
            }

            // Limit decimal to only 2 digits
            right_side = right_side.substring(0, 2);

            // join number by .
            input_val = "" + left_side + "." + right_side;

        } else {
            // no decimal entered
            // add commas to number
            // remove all non-digits
            input_val = formatNumber(input_val);
            input_val = "" + input_val;

            // final formatting
            if (blur === "blur") {
                input_val += "";
            }
        }

        // send updated string to input
        input.val(input_val);

        // put caret back in the right position
        var updated_len = input_val.length;
        caret_pos = updated_len - original_len + caret_pos;
        input[0].setSelectionRange(caret_pos, caret_pos);
    }
    // $("#btnSaveAuctionInfo").click(function() {
    //     for ( instance in CKEDITOR.instances )
    //         CKEDITOR.instances[instance].updateElement();
    // });
    $(".datepickerEnter").keydown(function (e) {
        if (e.keyCode == 13) {
            $(".datepickerEnter").blur();
        }
    });
</script>