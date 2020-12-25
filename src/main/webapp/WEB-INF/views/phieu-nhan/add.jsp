<%@ page isELIgnored="false" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<script src="<%=request.getContextPath()%>/assets/project/phieu-nhan/add.js${urlCache}"></script>
<section id="content" ng-app="FrameworkBase"  ng-controller="vantai">
    <section class="vbox background-white">
        <section class="scrollable padder">
            <ul class="bg-light breadcrumb no-border no-radius b-light pull-in">
                <li><a href="<%=request.getContextPath()%>/"><i class="fa fa-home"></i>&nbsp;Trang chủ</a></li>
                <li><a href="<%=request.getContextPath()%>/quan-ly-bo-nhiem-ccv">Phiếu biên nhận </a></li>
                <li><a href="javascript:void(0)">Thêm mới phiếu nhận hàng</a></li>
            </ul>
            <div id="msgId" class="m-b-md" style="display: none;">
                <script>
                            $(document).ready(function () {
                                /*$("#msgId").addClass("alert-success");*/
                            });
                </script>
                <span class="closebtn" onclick="this.parentElement.style.display = 'none';">&times;</span>
                <div class="title-message">{{logger}}</div>
            </div>
            <section class="panel panel-default" style="margin-bottom: 5px;">
                <header class="panel-heading">
                    <a href="javascript:void(0)"><h4 class="panel-title text-center font-bold font-size28" data-toggle="collapse" data-target="#collapseOne">
                            THÊM MỚI PHIẾU NHẬN HÀNG
                        </h4></a>
                </header>
                <div id="collapseOne" class="panel-collapse collapse in" aria-expanded="true">
                    <div class="panel-body background-xam">
                        <form id="formAdd" class="form-horizontal"  data-parsley-validate="true">
                            <div id="collapseSearch" class="panel-collapse collapse in" aria-expanded="true">
                                <div class="form-group">
                                    <label class="col-md-2 control-label text-dark">Số phiếu nhận<span style="color: red;">(*)</span></label>
                                    <div class="col-md-4">
                                        <input ng-model="phieuNhan.receiptCode"  data-parsley-required="true" maxlength="200" class="form-control input-sm"/>
                                    </div>
                                    <label class="col-md-2 control-label text-dark">Ngày lập phiếu</label>
                                    <div class="col-md-4">
                                        <input ng-model="phieuNhan.dateReceipt" class="form-control input-sm"/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-2 control-label text-dark">MST nhà xe</label>
                                    <div class="col-md-4">
                                        <input ng-model="phieuNhan.taxCode" class="form-control input-sm"/>
                                    </div>
                                    <label class="col-md-2 control-label text-dark">Tên nhà xe</label>
                                    <div class="col-md-4">
                                        <input ng-model="phieuNhan.partnerName" class="form-control input-sm"/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-2 control-label text-dark">Loại xe</label>
                                    <div class="col-md-4">
                                        <input ng-model="phieuNhan.loaiXe" maxlength="200" class="form-control input-sm"/>
                                    </div>
                                    <label class="col-md-2 control-label text-dark">Biển số</label>
                                    <div class="col-md-4">
                                        <input ng-model="phieuNhan.bienSo" maxlength="50" class="form-control input-sm"/>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <div class="col-md-12 text-center">
                                        <button type="button" class="btn btn-info btn-s-sm" data-toggle="modal" data-target="#chooseBienNhan"><i class="fa fa-plus"></i> Chọn biên nhận</button>
                                    </div>
                                </div>
                                <div class="row" style="margin: 0px;">
                                    <div class="p-r-0 p-l-0">
                                        <label class="input-sm">Số bản ghi: </label>
                                        <label style="color: red;">{{listBienNhanDaChon.rowCount|number}}</label>
                                        <label class="input-sm">Số bản ghi hiển thị: </label>
                                        <select class="input-sm form-control input-s-sm inline" style="width: 60px;" ng-model="numberPerPage" ng-change="setNumberPerPage(numberPerPage);" ng-init="numberPerPage = '5'">
                                            <option value="5">5</option>
                                            <option value="10">10</option>
                                            <option value="20">20</option>
                                            <option value="25">25</option>
                                        </select>
                                    </div>
                                </div>
                                <div class="table-responsive bg-white">
                                    <table class="table b-t b-light table-bordered table-hover" style="margin-bottom: 0px;" ng-switch on="listBienNhanDaChon.rowCount">
                                        <thead class="bg-gray">
                                            <tr>
                                                <th class="text-center v-inherit text-dark">STT</th>
                                                <th class="text-center v-inherit text-dark">Số biên nhận</th>
                                                <th class="text-center v-inherit text-dark">Người gửi</th>
                                                <th class="text-center v-inherit text-dark">Người nhận</th>
                                                <th class="text-center v-inherit text-dark">Điện thoại</th>
                                                <th class="text-center v-inherit text-dark">Địa chỉ</th>
                                                <th class="text-center v-inherit text-dark">Số thùng</th>
                                                <th class="text-center v-inherit text-dark">Số HD</th>
                                                <th class="text-center v-inherit text-dark">GTN</th>
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
                                                <td class="text-center v-inherit" >{{(listBienNhanDaChon.pageNumber - 1) * listBienNhanDaChon.numberPerPage + $index + 1}}</td>
                                                <td class="text-center v-inherit">{{item.receiptCode}}</td>
                                                <td class="text-center v-inherit">{{item.deliveryPartnerName}}</td>
                                                <td class="text-center v-inherit">{{item.receivePartnerName}}</td>
                                                <td class="text-center v-inherit">{{item.receivePartnerMobile}}</td>
                                                <td class="text-center v-inherit">{{item.receivePartnerAddress}}</td>
                                                <td class="text-center v-inherit">{{item.numbers}}</td>
                                                <td class="text-center v-inherit">{{item.numbers}}</td>
                                                <td class="text-center v-inherit">{{item.numbers}}</td>
                                                <td class="text-center v-inherit">{{item.note}}</td>
                                                <td class="text-center v-inherit">
                                                    <input type="radio" name="notarySelect" ng-click="changeIdSelected(item.idNotaryInfo);">
                                                </td>
                                            </tr>
                                        </tbody>
                                    </table>
                                </div>
                                <footer class="panel-footer">
                                    <div class="row">
                                        <div class="p-r-0 col-sm-12 text-right text-center-xs">
                                            <ul class="pagination pagination-sm m-t-none m-b-none">
                                                <li ng-if="listBienNhanDaChon.pageNumber > 1"><a href="javascript:void(0)" ng-click="loadPageData(1)">«</a></li>
                                                <li ng-repeat="item in listBienNhanDaChon.pageList">
                                                    <a href="javascript:void(0)" ng-if="item == listBienNhanDaChon.pageNumber" style="color:mediumvioletred;"> {{item}}</a>
                                                    <a href="javascript:void(0)" ng-if="item != listBienNhanDaChon.pageNumber" ng-click="loadPageData(item)"> {{item}}</a>
                                                </li>
                                                <li ng-if="listBienNhanDaChon.pageNumber < listBienNhanDaChon.pageCount"><a href="javascript:void(0)" ng-click="loadPageData(listBienNhanDaChon.pageCount)">»</a></li>
                                            </ul>
                                        </div>
                                    </div>
                                </footer>
                            </div>
                        </form>                      
                    </div>
                </div>
            </section>
            <%@include file="../popupChooseBienNhan.jsp"%>
        </section>
    </section>
</section>

<script>
    $("#quan-ly-bo-nhiem-ccv").addClass("active");
    $("#quan-ly-cong-chung-vien").addClass("active");
    $("#formAdd").parsley();
</script>