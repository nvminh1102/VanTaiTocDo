<%@ page isELIgnored="false" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>


<script src="<%=request.getContextPath()%>/assets/project/toa-hang/add.js${urlCache}" type="text/javascript"></script>
<script>
    var id = '${id}';
    var toaHangCode = '${toaHangCode}';
</script>
<section id="content" ng-app="FrameworkBase"  >
    <section class="vbox background-white">
        <section class="scrollable padder">
            <ul class="bg-light breadcrumb no-border no-radius b-light pull-in">
                <li><a href="<%=request.getContextPath()%>/"><i class="fa fa-home"></i>&nbsp;Trang chủ</a></li>
                <li><a href="<%=request.getContextPath()%>/toa-hang/list">Toa hàng</a></li>
                <li><a href="javascript:void(0)">Thông tin toa hàng</a></li>
            </ul>
            <section class="panel panel-default" style="margin-bottom: 5px;"  ng-controller="vantai">
                <header class="panel-heading">
                    <a href="javascript:void(0)"><h4 class="panel-title text-center font-bold font-size28" data-toggle="collapse" data-target="#collapseOne">TOA HÀNG</h4></a>
                </header>
                <div id="collapseOne" class="panel-collapse collapse in" aria-expanded="true">
                    <div class="panel-body background-xam">
                        <form id="formAdd" class="form-horizontal"  data-parsley-validate="true">
                            <div id="collapseSearch" class="panel-collapse collapse in" aria-expanded="true">
                                <div class="form-group">
                                    <label class="col-md-2 control-label text-dark">Số toa hàng<span style="color: red;">(*)</span></label>
                                    <div class="col-md-4">
                                        <input ng-model="toaHang.toaHangCode"  data-parsley-required="true"  data-parsley-error-message="Số phiều bắt buộc nhập!!" maxlength="200" class="form-control input-sm"/>
                                    </div>
                                    <label class="col-md-2 control-label text-dark">Nơi đi</label>
                                    <div class="col-md-4">
                                        <input ng-model="toaHang.noiDi"  maxlength="500" class="form-control input-sm"/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-2 control-label text-dark">Nơi đến</label>
                                    <div class="col-md-4">
                                        <input ng-model="toaHang.noiDen"  maxlength="500" class="form-control input-sm"/>
                                    </div>
                                    <label class="col-md-2 control-label text-dark">Biển số</label>
                                    <div class="col-md-4">
                                        <input ng-model="toaHang.bienSo"  maxlength="50" class="form-control input-sm"/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-2 control-label text-dark">Tên lái xe</label>
                                    <div class="col-md-4">
                                        <input ng-model="toaHang.tenLaiXe" maxlength="200" class="form-control input-sm"/>
                                    </div>
                                    <label class="col-md-2 control-label text-dark">Số điện thoại lái xe</label>
                                    <div class="col-md-4">
                                        <input ng-model="toaHang.sdtLaiXe" maxlength="20" class="form-control input-sm"/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-2 control-label text-dark">Người nhận</label>
                                    <div class="col-md-4">
                                        <input ng-model="toaHang.nguoiNhan" maxlength="200" class="form-control input-sm"/>
                                    </div>
                                    <label class="col-md-2 control-label text-dark">Nơi nhận</label>
                                    <div class="col-md-4">
                                        <input ng-model="toaHang.noiNhan" maxlength="500" class="form-control input-sm"/>
                                    </div>
                                </div>
                                <div class="row" style="margin: 0px;">
                                    <div class="p-r-0 p-l-0 col-md-6">
                                        <label class="input-sm">Số bản ghi: </label>
                                        <label style="color: red;">{{listBienNhanDaChon.rowCount}}</label>
                                    </div>
                                    <div class="p-r-0 p-l-0 col-md-6">
                                        <button type="button" class="pull-right btn btn-info btn-s-sm" data-toggle="modal" data-target="#chonPhieuNhanHang"><i class="fa fa-plus"></i> Chọn phiếu nhận hàng</button>
                                    </div>
                                </div>
                                <div class="table-responsive bg-white" style="margin-bottom: 10px;">
                                    <table class="table b-t b-light table-bordered table-hover" style="margin-bottom: 0px;" ng-switch on="listBienNhanDaChon.rowCount">
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
                                                <td class="text-center v-inherit">{{item.tenNguoiGui}}</td>
                                                <td class="text-center v-inherit">{{item.tenNguoiNhan}}</td>
                                                <td class="text-center v-inherit">{{item.mobileNguoiNhan}}</td>
                                                <td class="text-center v-inherit">{{item.diaChiNguoiNhan}}</td>
                                                <td class="text-center v-inherit">{{item.numbers}}</td>
                                                <td class="text-center v-inherit">{{item.numbers}}</td>
                                                <td class="text-center v-inherit">{{item.numbers}}</td>
                                                <td class="text-center v-inherit">{{item.note}}</td>
                                                <td class="text-center v-inherit">
                                                    <!--<input type="radio" name="notarySelect" ng-click="changeIdSelected(item.idNotaryInfo);">-->
                                                </td>
                                            </tr>
                                        </tbody>
                                    </table>
                                </div>
                                <div class="form-group">
                                    <div class="col-md-12 text-center">
                                        <button type="button" class="btn btn-info btn-s-sm" data-toggle="modal"  ng-click="saveToaHang()" ><i class="fa fa-plus"></i>Lưu thông tin</button>
                                        <a href="<%=request.getContextPath()%>/toa-hang/list" class="btn btn-danger"><i class="fa fa-times"></i>Hủy</a>
                                    </div>
                                </div>
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