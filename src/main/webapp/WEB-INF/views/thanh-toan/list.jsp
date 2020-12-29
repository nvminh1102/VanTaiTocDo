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

<script src="<%=request.getContextPath()%>/assets/project/thanh-toan/list.js" type="text/javascript"></script>

<section id="content" ng-app="FrameworkBase"  ng-controller="frameworkCtrl">
    <section class="vbox background-white">
        <section class="scrollable padder">
            <ul class="bg-light breadcrumb no-border no-radius b-light pull-in">
                <li><a href="<%=request.getContextPath()%>/"><i class="fa fa-home"></i>&nbsp;Trang chủ</a></li>
                <li><a href="#">Thanh toán</a></li>
                <li><a href="#" id="title">Danh sách thanh toán</a></li>
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
                                        <label class="control-label text-dark" style="padding: 0px">Số phiếu nhận</label>
                                    </div>
                                    <div class="col-md-8">
                                        <input ng-model="search.soPhieuNhan" class="form-control input-sm" placeholder="GH­2012­36"/>
                                    </div>
                                </div>
                                <div class="col-md-6">
                                    <div class="col-md-4">
                                        <label class="control-label text-dark">Khách hàng</label>
                                    </div>
                                    <div class="col-md-8">
                                        <input ng-model="search.nguoiGui" class="form-control input-sm" placeholder="Tên khách hàng"/>
                                    </div>
                                </div>
                            </div>

                            <div class="row" style="margin-top:10px;">
                                <div class="col-md-6">
                                    <div class="col-md-4">
                                        <label class="control-label text-dark" style="padding: 0px">Loại thanh toán</label>
                                    </div>
                                    <div class="col-md-8">
                                        <select class="form-control" name="search.typePayment" id="search.typePayment" ng-model="search.typePayment">
                                            <option value="" selected>Tất cả</option>
                                            <option ng-value="1">Trả trước</option>
                                            <option ng-value="2">Trả sau</option>
                                            <option ng-value="3">Công nợ</option>
                                        </select>
                                    </div>
                                </div>
                                <div class="col-md-6">
                                    <div class="col-md-4">
                                        <label class="control-label text-dark" style="padding: 0px">Thanh toán</label>
                                    </div>
                                    <div class="col-md-8">
                                        <select class="form-control" name="search.isPayment" id="search.isPayment" ng-model="search.isPayment">
                                            <option value="" selected>Tất cả</option>
                                            <option ng-value="1">Đã thanh toán</option>
                                            <option ng-value="2">Chưa thanh toán</option>
                                        </select>
                                    </div>
                                </div>

                            </div>

                            <div class="row" style="margin-top:10px;">
                                <div class="col-md-6">
                                    <div class="col-md-4">
                                        <label class="control-label text-dark">Ngày nhận hàng từ</label>
                                    </div>
                                    <div class="col-md-8">
                                        <input id="fromDateReceipt" ng-model="search.fromDateReceipt" class="form-control input-sm" placeholder="Từ ngày"/>
                                    </div>
                                </div>
                                <div class="col-md-6">
                                    <div class="col-md-4">
                                        Ngày nhận hàng đến
                                    </div>
                                    <div class="col-md-8">
                                        <input id="toDateReceipt" ng-model="search.toDateReceipt" class="form-control input-sm" placeholder="đến ngày"/>
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
                        DANH SÁCH THANH TOÁN
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
                                    <th class="text-center v-inherit text-dark" style="width: 10%;">Tool</th>
                                    <th class="text-center v-inherit text-dark" style="width: 10%;">Tên khách hàng</th>
                                    <th class="text-center v-inherit text-dark" style="width: 10%;">Số phiếu nhận hàng</th>
                                    <th class="text-center v-inherit text-dark" style="width: 10%;">Số tiền</th>
                                    <th class="text-center v-inherit text-dark" style="width: 10%;">Trả trước</th>
                                    <th class="text-center v-inherit text-dark" style="width: 10%;">Trả sau</th>
                                    <th class="text-center v-inherit text-dark" style="width: 10%;">Công nợ</th>
                                    <th class="text-center v-inherit text-dark" style="width: 10%;">Số còn phải thu</th>
                                    <th class="text-center v-inherit text-dark" style="width: 10%;">Ghi chú</th>
                                </tr>
                                </thead>
                                <tbody>
                                <tr ng-switch-when="0">
                                    <td colspan="12" style="height: 100%;background-color: #ececec; line-height: 3.429;text-align: center; font-style: italic;">
                                        Không có dữ liệu
                                    </td>
                                </tr>
                                <tr ng-switch-default ng-repeat="item in page.items track by $index">
                                    <td class="text-center v-inherit" >{{(page.pageNumber - 1) * page.numberPerPage + $index + 1}}</td>
                                    <td class="text-center">
                                        <%--<sec:authorize access="hasAnyRole('ROLE_SYSTEM_PUBLISH_AUCTION_VIEW', 'ROLE_SYSTEM_PUBLISH_AUCTION_EDIT')">--%>
                                        <div class="btn-group"  ng-click="checkShowThanhToan(item)">
                                            <button class="btn btn-default btn-xs dropdown-toggle" data-toggle="dropdown"><i
                                                    class="glyphicon glyphicon-cog"></i></button>
                                            <ul class="dropdown-menu pull-left" style="width: 230px;" ng-show="item.tienDaTra == null || tienPhaiTra < item.tienDaTra">
                                                    <div class="line line-dashed m-b-none m-t-none"></div>
                                                    <a href="#" ng-click="preThanhToan(item)">
                                                        Thanh toán
                                                    </a>
                                                </li>
                                            </ul>
                                        </div>
                                        <%--</sec:authorize>--%>
                                    </td>
                                    <td class="text-left v-inherit">{{item.tenNguoiGui}}</td>
                                    <td class="text-left v-inherit">{{item.receiptCode}}</td>
                                    <td class="text-center v-inherit">{{item.tongTien|number}}</td>
                                    <td class="text-left v-inherit">{{item.tienDaTra|number}}</td>
                                    <td class="text-left v-inherit">{{item.tienDaTra|number}}</td>
                                    <td class="text-left v-inherit">{{item.tienDaTra|number}}</td>
                                    <td class="text-left v-inherit">{{(item.tongTien - item.tienDaTra)|number}}</td>
                                    <td class="text-left v-inherit">{{item.tienDaTra != null && item.tienDaTra != '' ? "Đã thanh toán" :  "Chưa thanh toán"}}</td>
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
    <div class="modal fade" id="thanhToanModal" tabindex="-1" role="dialog"
         aria-labelledby="thanhToanModal" aria-hidden="true" aria-label="Close">
        <div class="modal-dialog">
            <div class="modal-content" style="width: 940px;">
                <div class="modal-header alert-info">
                    <button type="button" class="btn btn-default close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h4 class="modal-title">Thanh toán phiếu nhận hàng</h4>
                </div>
                <div class="modal-body">
                    <input style="display: none" name="info.id" ng-value="info.id" ng-model="info.id" class="form-control"/>
                    <div class="row">
                        <label class="col-sm-2 control-label" style="line-height: 30px">Số phiếu nhận hàng</label>
                        <div class="col-sm-4">
                            <input readonly name="info.soPhieuHang" id="info.soPhieuHang" ng-value="info.soPhieuHang" class="form-control"/>
                        </div>
                        <label class="col-sm-2 control-label" style="line-height: 30px">Số tiền thanh toán <span style="color: red"> (*)</span></label>
                        <div class="col-sm-4">
                            <input name="info.tienDaTra" id="info.tienDaTra"  ng-model="info.tienDaTra" maxlength="20" class="form-control" data-type="currency"/>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <div class="row" style="text-align: center">
                        <button class="btn btn-primary" ng-click="thanhToan()" style="text-transform: none;"><i class="fa fa-check"></i> <spring:message code="label.button.ok"/></button>
                        <button type="button" class="btn btn-danger" data-dismiss="modal">Hủy bỏ</button>
                    </div>

                </div>
            </div>
        </div>
    </div>
</section>

<script>
  showDropDownOnTable();

  $("input[data-type='currency']").on({
    keyup: function() {
      formatCurrency($(this));
    },
    blur: function() {
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
      return Number(currency.replace(/[^0-9.-]+/g,""));
    }
  }
  function formatCurrency(input, blur) {
    // appends $ to value, validates decimal side
    // and puts cursor back in right position.

    // get input value
    var input_val = input.val();

    // don't validate empty input
    if (input_val === "") { return; }

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
</script>