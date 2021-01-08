<%@page import="com.osp.model.User"%>
<%@ page isELIgnored="false" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<script src="<%=request.getContextPath()%>/assets/project/index.js${urlCache}"></script>
<%
    User user = (User) request.getSession().getAttribute("userLogin");
    request.setAttribute("userLogin", user);
%>


<style>
    .count{
        font-size:30px;
        font-weight: 600;
    }
    .green-special{
        color:#1ABB9C;
    }
    .nau-xam{
        color:#73879C;
        font-weight: 400;
        font-size: 13px;
    }
    .red{
        color:#E74C3C;
    }
    #container {
        min-height: 350px;
        max-height: 600px;
        margin: 0 auto;
    }
    td {
        white-space: break-spaces !important; 
    }
</style>

<!--<section id="content">
    <section class="vbox">
        <section class="scrollable padder" style="background: white">
            <ul class="bg-primary breadcrumb no-border no-radius b-b b-light pull-in">
                <li><a href="<%=request.getContextPath()%>/"><i class="fa fa-home"></i>&nbsp;Home</a></li>
            </ul>
            <div class="m-b-md">
                <small><span class="text-success"><sec:authentication property="principal.fullName" /></span>, mừng bạn trở lại.</small>
            </div>
        </section>
    </section>
    <a href="#" class="hide nav-off-screen-block" data-toggle="class:nav-off-screen" data-target="#nav"></a>
</section>-->
<section id="content" ng-app="FrameworkBase"  ng-controller="vantai">
    <section class="vbox">
        <section class="scrollable padder">
            <!--            <ul class="breadcrumb no-border no-radius b-b b-light pull-in">
                            <li><a href="<%=request.getContextPath()%>/"><i class="fa fa-home"></i>&nbsp;Trang chủ</a></li>
                        </ul>-->

            <sec:authorize access="hasRole('ROLE_TK_CHI_TIET_VIEW')">
                
                <section class="panel panel-default">
                    <header class="panel-heading">
                        <a href="javascript:void(0)"><h4 class="panel-title font-bold" data-toggle="collapse" data-target="#collapseTwo">
                                DỮ LIỆU THỐNG KÊ
                            </h4></a>
                    </header>
                    <div id="collapseTwo" class="panel-collapse collapse in" aria-expanded="true">
                        <div class="panel-body background-xam">
                            <div class="table-responsive bg-white" style="overflow-x: auto;">
                                <table class="table b-t b-light table-bordered table-hover" style="margin-bottom: 0px;table-layout: fixed;">
                                    <thead class="bg-gray">
                                        <tr style="background-color: #ccf3cd;">
                                            <th class="text-center v-inherit text-dark" style="width: 150px;"></th>
                                            <th class="text-center v-inherit text-dark" style="width: 150px;">Số phiếu nhận</th>
                                            <th class="text-center v-inherit text-dark" style="width: 150px;">Số phiếu lên toa</th>
                                            <th class="text-center v-inherit text-dark" style="width: 150px;">Số phiếu giao</th>
                                            <th class="text-center v-inherit text-dark" style="width: 150px;">Trả trước</th>
                                            <th class="text-center v-inherit text-dark" style="width: 150px;">Trả sau</th>
                                            <th class="text-center v-inherit text-dark" style="width: 150px;">Công nợ</th>
                                            <th class="text-center v-inherit text-dark" style="width: 150px;">Số tiền đã thu</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <tr>
                                            <td class="text-left v-inherit" style="font-weight: bold">Dữ liệu trong ngày</td>
                                            <td class="text-left v-inherit">{{thongKeGiaoNhanViewNgay.soDonNhan}}</td>
                                            <td class="text-left v-inherit">{{thongKeGiaoNhanViewNgay.soDonVanChuyen}}</td>
                                            <td class="text-left v-inherit">{{thongKeGiaoNhanViewNgay.soDonGiao}}</td>
                                            <td class="text-left v-inherit">{{thongKeGiaoNhanViewNgay.traTruoc}}</td>
                                            <td class="text-left v-inherit">{{thongKeGiaoNhanViewNgay.traSau}}</td>
                                            <td class="text-left v-inherit">{{thongKeGiaoNhanViewNgay.congNo}}</td>
                                            <td class="text-left v-inherit">{{thongKeGiaoNhanViewNgay.tienDaTra}}</td>
                                        </tr>
                                        <tr>
                                            <td class="text-left v-inherit" style="font-weight: bold">Dữ liệu trong tháng</td>
                                            <td class="text-left v-inherit">{{thongKeGiaoNhanViewMonth.soDonNhan}}</td>
                                            <td class="text-left v-inherit">{{thongKeGiaoNhanViewMonth.soDonVanChuyen}}</td>
                                            <td class="text-left v-inherit">{{thongKeGiaoNhanViewMonth.soDonGiao}}</td>
                                            <td class="text-left v-inherit">{{thongKeGiaoNhanViewMonth.traTruoc}}</td>
                                            <td class="text-left v-inherit">{{thongKeGiaoNhanViewMonth.traSau}}</td>
                                            <td class="text-left v-inherit">{{thongKeGiaoNhanViewMonth.congNo}}</td>
                                            <td class="text-left v-inherit">{{thongKeGiaoNhanViewMonth.tienDaTra}}</td>
                                        </tr>
                                        <tr>
                                            <td class="text-left v-inherit" style="font-weight: bold">Dữ liệu trong năm</td>
                                            <td class="text-left v-inherit">{{thongKeGiaoNhanViewYear.soDonNhan}}</td>
                                            <td class="text-left v-inherit">{{thongKeGiaoNhanViewYear.soDonVanChuyen}}</td>
                                            <td class="text-left v-inherit">{{thongKeGiaoNhanViewYear.soDonGiao}}</td>
                                            <td class="text-left v-inherit">{{thongKeGiaoNhanViewYear.traTruoc}}</td>
                                            <td class="text-left v-inherit">{{thongKeGiaoNhanViewYear.traSau}}</td>
                                            <td class="text-left v-inherit">{{thongKeGiaoNhanViewYear.congNo}}</td>
                                            <td class="text-left v-inherit">{{thongKeGiaoNhanViewYear.tienDaTra}}</td>
                                        </tr>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                </section>
                
                
                <section class="panel panel-default">
                    <header class="panel-heading">
                        <a href="javascript:void(0)"><h4 class="panel-title font-bold" data-toggle="collapse" data-target="#collapseTwo">
                                DANH SÁCH PHIẾU NHẬN HÀNG TRONG NGÀY
                            </h4></a>
                    </header>
                    <div id="collapseTwo" class="panel-collapse collapse in" aria-expanded="true">
                        <div class="panel-body background-xam">
                            <div class="table-responsive bg-white" style="overflow-x: auto;">
                                <table class="table b-t b-light table-bordered table-hover" style="margin-bottom: 0px;table-layout: fixed;" ng-switch on="thongKeGiaoNhanViews.length">
                                    <thead class="bg-gray">
                                        <tr style="background-color: #ccf3cd;">
                                            <th class="text-center v-inherit text-dark" style="width: 60px;">STT</th>
                                            <th class="text-center v-inherit text-dark" style="width: 80px;">Trạng thái</th>
                                            <th class="text-center v-inherit text-dark" style="width: 120px;">Phiếu nhận</th>
                                            <th class="text-center v-inherit text-dark" style="width: 120px;">Toa hàng</th>
                                            <th class="text-center v-inherit text-dark" style="width: 100px;">Trả trước</th>
                                            <th class="text-center v-inherit text-dark" style="width: 100px;">Trả sau</th>
                                            <th class="text-center v-inherit text-dark" style="width: 100px;">Công nợ</th>
                                            <th class="text-center v-inherit text-dark" style="width: 100px;">Đã thu</th>
                                            <th class="text-center v-inherit text-dark" style="width: 220px;">Xe nhận hàng</th>
                                            <th class="text-center v-inherit text-dark" style="width: 220px;">Xe vận chuyển</th>
                                            <th class="text-center v-inherit text-dark" style="width: 220px;">Xe giao hàng</th>
                                            <th class="text-center v-inherit text-dark" style="width: 120px;">Cảnh báo</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <tr ng-switch-when="0">
                                            <td colspan="12" style="height: 100%;background-color: #ececec; line-height: 3.429;text-align: center; font-style: italic;">
                                                Chưa có phiếu nhận hàng nào trong ngày
                                            </td>
                                        </tr>
                                        <tr ng-switch-default ng-repeat="item in thongKeGiaoNhanViews track by $index">
                                            <td class="text-center v-inherit" >{{$index + 1}}</td>
                                            <td class="text-left v-inherit">{{item.status == 1 ? "Nhận hàng" : item.status == 2 ? "Lên toa" : item.status == 3 ? "Đã giao" : ""}}</td>
                                            <td class="text-left v-inherit">{{item.receiptCode}}</td>
                                            <td class="text-left v-inherit">{{item.toaHang}}</td>
                                            <td class="text-right v-inherit">{{item.paymentType == 1 ? item.tongTien : 0}}</td>
                                            <td class="text-right v-inherit">{{item.paymentType == 2 ? item.tongTien : 0}}</td>
                                            <td class="text-right v-inherit">{{item.paymentType == 3 ? item.tongTien : 0}}</td>
                                            <td class="text-right v-inherit">{{item.tienDaTra}}</td>
                                            <td class="text-left v-inherit">{{item.xeNhanHang}}</td>
                                            <td class="text-left v-inherit">{{item.xeVanChuyen}}</td>
                                            <td class="text-left v-inherit">{{item.xeGiaoHang}}</td>
                                            <td class="text-left v-inherit" style=" color: red;">{{item.chuaThuTien}}</td>
                                        </tr>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                </section>
                
            </sec:authorize>


            <!--        <div class="m-b-md">
                            <h3 class="m-b-none">Workset</h3>
                            <small>Welcome back, Noteman</small>
                        </div>
                        <section class="panel panel-default">
                            <div class="row m-l-none m-r-none bg-light lter">
                                <div class="col-sm-6 col-md-3 padder-v b-r b-light">
                                <span class="fa-stack fa-2x pull-left m-r-sm">
                                  <i class="fa fa-circle fa-stack-2x text-info"></i>
                                  <i class="fa fa-male fa-stack-1x text-white"></i>
                                </span>
                                    <a class="clear" href="#">
                                        <span class="h3 block m-t-xs"><strong>52,000</strong></span>
                                        <small class="text-muted text-uc">New robots</small>
                                    </a>
                                </div>
                                <div class="col-sm-6 col-md-3 padder-v b-r b-light lt">
                                <span class="fa-stack fa-2x pull-left m-r-sm">
                                  <i class="fa fa-circle fa-stack-2x text-warning"></i>
                                  <i class="fa fa-bug fa-stack-1x text-white"></i>
                                  <span class="easypiechart pos-abt" data-percent="100" data-line-width="4" data-track-Color="#fff" data-scale-Color="false" data-size="50" data-line-cap='butt' data-animate="2000" data-target="#bugs" data-update="3000"></span>
                                </span>
                                    <a class="clear" href="#">
                                        <span class="h3 block m-t-xs"><strong id="bugs">468</strong></span>
                                        <small class="text-muted text-uc">Bugs intruded</small>
                                    </a>
                                </div>
                                <div class="col-sm-6 col-md-3 padder-v b-r b-light">
                                <span class="fa-stack fa-2x pull-left m-r-sm">
                                  <i class="fa fa-circle fa-stack-2x text-danger"></i>
                                  <i class="fa fa-fire-extinguisher fa-stack-1x text-white"></i>
                                  <span class="easypiechart pos-abt" data-percent="100" data-line-width="4" data-track-Color="#f5f5f5" data-scale-Color="false" data-size="50" data-line-cap='butt' data-animate="3000" data-target="#firers" data-update="5000"></span>
                                </span>
                                    <a class="clear" href="#">
                                        <span class="h3 block m-t-xs"><strong id="firers">359</strong></span>
                                        <small class="text-muted text-uc">Extinguishers ready</small>
                                    </a>
                                </div>
                                <div class="col-sm-6 col-md-3 padder-v b-r b-light lt">
                                <span class="fa-stack fa-2x pull-left m-r-sm">
                                  <i class="fa fa-circle fa-stack-2x icon-muted"></i>
                                  <i class="fa fa-clock-o fa-stack-1x text-white"></i>
                                </span>
                                    <a class="clear" href="#">
                                        <span class="h3 block m-t-xs"><strong>31:50</strong></span>
                                        <small class="text-muted text-uc">Left to exit</small>
                                    </a>
                                </div>
                            </div>
                        </section>
                        <div class="row">
                            <div class="col-md-8">
                                <section class="panel panel-default">
                                    <header class="panel-heading font-bold">Statistics</header>
                                    <div class="panel-body">
                                        <div id="flot-1ine" style="height:210px"></div>
                                    </div>
                                    <footer class="panel-footer bg-white no-padder">
                                        <div class="row text-center no-gutter">
                                            <div class="col-xs-3 b-r b-light">
                                                <span class="h4 font-bold m-t block">5,860</span>
                                                <small class="text-muted m-b block">Orders</small>
                                            </div>
                                            <div class="col-xs-3 b-r b-light">
                                                <span class="h4 font-bold m-t block">10,450</span>
                                                <small class="text-muted m-b block">Sellings</small>
                                            </div>
                                            <div class="col-xs-3 b-r b-light">
                                                <span class="h4 font-bold m-t block">21,230</span>
                                                <small class="text-muted m-b block">Items</small>
                                            </div>
                                            <div class="col-xs-3">
                                                <span class="h4 font-bold m-t block">7,230</span>
                                                <small class="text-muted m-b block">Customers</small>
                                            </div>
                                        </div>
                                    </footer>
                                </section>
                            </div>
                            <div class="col-md-4">
                                <section class="panel panel-default">
                                    <header class="panel-heading font-bold">Data graph</header>
                                    <div class="bg-light dk wrapper">
                                        <span class="pull-right">Friday</span>
                                        <span class="h4">$540<br>
                                    <small class="text-muted">+1.05(2.15%)</small>
                                  </span>
                                        <div class="text-center m-b-n m-t-sm">
                                            <div class="sparkline" data-type="line" data-height="65" data-width="100%" data-line-width="2" data-line-color="#dddddd" data-spot-color="#bbbbbb" data-fill-color="" data-highlight-line-color="#fff" data-spot-radius="3" data-resize="true" values="280,320,220,385,450,320,345,250,250,250,400,380"></div>
                                            <div class="sparkline inline" data-type="bar" data-height="45" data-bar-width="6" data-bar-spacing="6" data-bar-color="#65bd77">10,9,11,10,11,10,12,10,9,10,11,9,8</div>
                                        </div>
                                    </div>
                                    <div class="panel-body">
                                        <div>
                                            <span class="text-muted">Total:</span>
                                            <span class="h3 block">$2500.00</span>
                                        </div>
                                        <div class="line pull-in"></div>
                                        <div class="row m-t-sm">
                                            <div class="col-xs-4">
                                                <small class="text-muted block">Market</small>
                                                <span>$1500.00</span>
                                            </div>
                                            <div class="col-xs-4">
                                                <small class="text-muted block">Referal</small>
                                                <span>$600.00</span>
                                            </div>
                                            <div class="col-xs-4">
                                                <small class="text-muted block">Affiliate</small>
                                                <span>$400.00</span>
                                            </div>
                                        </div>
                                    </div>
                                </section>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-8">
                                <h4 class="m-t-none">Todos</h4>
                                <ul class="list-group gutter list-group-lg list-group-sp sortable">
                                    <li class="list-group-item box-shadow">
                                        <a href="#" class="pull-right" data-dismiss="alert">
                                            <i class="fa fa-times icon-muted"></i>
                                        </a>
                                        <span class="pull-left media-xs">
                                    <i class="fa fa-sort icon-muted fa m-r-sm"></i>
                                    <a  href="#todo-1" data-toggle="class:text-lt text-success" class="active">
                                      <i class="fa fa-square-o fa-fw text"></i>
                                      <i class="fa fa-check-square-o fa-fw text-active text-success"></i>
                                    </a>
                                  </span>
                                        <div class="clear text-success text-lt" id="todo-1">
                                            Browser compatibility
                                        </div>
                                    </li>
                                    <li class="list-group-item box-shadow">
                                        <a href="#" class="pull-right" data-dismiss="alert">
                                            <i class="fa fa-times icon-muted"></i>
                                        </a>
                                        <span class="pull-left media-xs">
                                    <i class="fa fa-sort icon-muted fa m-r-sm"></i>
                                    <a  href="#todo-2" data-toggle="class:text-lt text-danger">
                                      <i class="fa fa-square-o fa-fw text"></i>
                                      <i class="fa fa-check-square-o fa-fw text-active text-danger"></i>
                                    </a>
                                  </span>
                                        <div class="clear" id="todo-2">
                                            Looking for more example templates
                                        </div>
                                    </li>
                                    <li class="list-group-item box-shadow">
                                        <a href="#" class="pull-right" data-dismiss="alert">
                                            <i class="fa fa-times icon-muted"></i>
                                        </a>
                                        <span class="pull-left media-xs">
                                    <i class="fa fa-sort icon-muted fa m-r-sm"></i>
                                    <a  href="#todo-3" data-toggle="class:text-lt">
                                      <i class="fa fa-square-o fa-fw text"></i>
                                      <i class="fa fa-check-square-o fa-fw text-active text-success"></i>
                                    </a>
                                  </span>
                                        <div class="clear" id="todo-3">
                                            Customizing components
                                        </div>
                                    </li>
                                    <li class="list-group-item box-shadow">
                                        <a href="#" class="pull-right" data-dismiss="alert">
                                            <i class="fa fa-times icon-muted"></i>
                                        </a>
                                        <span class="pull-left media-xs">
                                    <i class="fa fa-sort icon-muted fa m-r-sm"></i>
                                    <a  href="#todo-4" data-toggle="class:text-lt">
                                      <i class="fa fa-square-o fa-fw text"></i>
                                      <i class="fa fa-check-square-o fa-fw text-active text-success"></i>
                                    </a>
                                  </span>
                                        <div class="clear" id="todo-4">
                                            The fastest way to get started
                                        </div>
                                    </li>
                                    <li class="list-group-item box-shadow">
                                        <a href="#" class="pull-right" data-dismiss="alert">
                                            <i class="fa fa-times icon-muted"></i>
                                        </a>
                                        <span class="pull-left media-xs">
                                    <i class="fa fa-sort icon-muted fa m-r-sm"></i>
                                    <a  href="#todo-5" data-toggle="class:text-lt">
                                      <i class="fa fa-square-o fa-fw text"></i>
                                      <i class="fa fa-check-square-o fa-fw text-active text-success"></i>
                                    </a>
                                  </span>
                                        <div class="clear" id="todo-5">
                                            HTML5 doctype required
                                        </div>
                                    </li>
                                    <li class="list-group-item box-shadow">
                                        <a href="#" class="pull-right" data-dismiss="alert">
                                            <i class="fa fa-times icon-muted"></i>
                                        </a>
                                        <span class="pull-left media-xs">
                                    <i class="fa fa-sort icon-muted fa m-r-sm"></i>
                                    <a  href="#todo-6" data-toggle="class:text-lt">
                                      <i class="fa fa-square-o fa-fw text"></i>
                                      <i class="fa fa-check-square-o fa-fw text-active text-success"></i>
                                    </a>
                                  </span>
                                        <div class="clear" id="todo-6">
                                            LessCSS compiling
                                        </div>
                                    </li>
                                </ul>
                            </div>
                            <div class="col-md-4">
                                <section class="panel b-light">
                                    <header class="panel-heading bg-primary dker no-border"><strong>Calendar</strong></header>
                                    <div id="calendar" class="bg-primary m-l-n-xxs m-r-n-xxs"></div>
                                    <div class="list-group">
                                        <a href="#" class="list-group-item text-ellipsis">
                                            <span class="badge bg-danger">7:30</span>
                                            Meet a friend
                                        </a>
                                        <a href="#" class="list-group-item text-ellipsis">
                                            <span class="badge bg-success">9:30</span>
                                            Have a kick off meeting with .inc company
                                        </a>
                                        <a href="#" class="list-group-item text-ellipsis">
                                            <span class="badge bg-light">19:30</span>
                                            Milestone release
                                        </a>
                                    </div>
                                </section>
                            </div>
                        </div>
                        <div>
                            <div class="btn-group m-b" data-toggle="buttons">
                                <label class="btn btn-sm btn-default active">
                                    <input type="radio" name="options" id="option1"> Timeline
                                </label>
                                <label class="btn btn-sm btn-default">
                                    <input type="radio" name="options" id="option2"> Activity
                                </label>
                            </div>
                            <section class="comment-list block">
                                <article id="comment-id-1" class="comment-item">
                                <span class="fa-stack pull-left m-l-xs">
                                  <i class="fa fa-circle text-info fa-stack-2x"></i>
                                  <i class="fa fa-play-circle text-white fa-stack-1x"></i>
                                </span>
                                    <section class="comment-body m-b-lg">
                                        <header>
                                            <a href="#"><strong>John smith</strong></a> shared a <a href="#" class="text-info">video</a> to you
                                            <span class="text-muted text-xs">
                                      24 minutes ago
                                    </span>
                                        </header>
                                        <div>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Morbi id neque quam.</div>
                                    </section>
                                </article>
                                 .comment-reply 
                                <article id="comment-id-2" class="comment-reply">
                                    <article class="comment-item">
                                        <a class="pull-left thumb-sm">
                                            <img src="images/avatar_default.jpg" class="img-circle">
                                        </a>
                                        <section class="comment-body m-b-lg">
                                            <header>
                                                <a href="#"><strong>John smith</strong></a>
                                                <span class="text-muted text-xs">
                                        26 minutes ago
                                      </span>
                                            </header>
                                            <div> Morbi id neque quam. Aliquam.</div>
                                        </section>
                                    </article>
                                    <article class="comment-item">
                                        <a class="pull-left thumb-sm">
                                            <img src="images/avatar.jpg" class="img-circle">
                                        </a>
                                        <section class="comment-body m-b-lg">
                                            <header>
                                                <a href="#"><strong>Mike</strong></a>
                                                <span class="text-muted text-xs">
                                        26 minutes ago
                                      </span>
                                            </header>
                                            <div>Good idea.</div>
                                        </section>
                                    </article>
                                </article>
                                 / .comment-reply 
                                <article id="comment-id-2" class="comment-item">
                                <span class="fa-stack pull-left m-l-xs">
                                  <i class="fa fa-circle text-danger fa-stack-2x"></i>
                                  <i class="fa fa-file-o text-white fa-stack-1x"></i>
                                </span>
                                    <section class="comment-body m-b-lg">
                                        <header>
                                            <a href="#"><strong>John Doe</strong></a>
                                            <span class="text-muted text-xs">
                                      1 hour ago
                                    </span>
                                        </header>
                                        <div>Lorem ipsum dolor sit amet, consecteter adipiscing elit.</div>
                                    </section>
                                </article>
                                <article id="comment-id-2" class="comment-item">
                                <span class="fa-stack pull-left m-l-xs">
                                  <i class="fa fa-circle text-success fa-stack-2x"></i>
                                  <i class="fa fa-check text-white fa-stack-1x"></i>
                                </span>
                                    <section class="comment-body m-b-lg">
                                        <header>
                                            <a href="#"><strong>Jonathan</strong></a> completed a task
                                            <span class="text-muted text-xs">
                                      1 hour ago
                                    </span>
                                        </header>
                                        <div>Consecteter adipiscing elit.</div>
                                    </section>
                                </article>
                            </section>
                            <a href="#" class="btn btn-default btn-sm m-b"><i class="fa fa-plus icon-muted"></i> more</a>
                        </div>-->
        </section>
    </section>
    <a href="#" class="hide nav-off-screen-block" data-toggle="class:nav-off-screen, open" data-target="#nav,html"></a>
</section>

<script src="<%=request.getContextPath()%>/assets/note/js/charts/easypiechart/jquery.easy-pie-chart.js"></script>
<script src="<%=request.getContextPath()%>/assets/note/js/charts/sparkline/jquery.sparkline.min.js"></script>
<script src="<%=request.getContextPath()%>/assets/note/js/charts/flot/jquery.flot.min.js"></script>
<script src="<%=request.getContextPath()%>/assets/note/js/charts/flot/jquery.flot.tooltip.min.js"></script>
<script src="<%=request.getContextPath()%>/assets/note/js/charts/flot/jquery.flot.resize.js"></script>
<script src="<%=request.getContextPath()%>/assets/note/js/charts/flot/jquery.flot.grow.js"></script>
<script src="<%=request.getContextPath()%>/assets/note/js/charts/flot/demo.js"></script>

<script src="<%=request.getContextPath()%>/assets/note/js/calendar/bootstrap_calendar.js"></script>
<script src="<%=request.getContextPath()%>/assets/note/js/calendar/demo.js"></script>

<script src="<%=request.getContextPath()%>/assets/note/js/sortable/jquery.sortable.js"></script>

<script src="<%=request.getContextPath()%>/assets/note/js/jvectormap/jquery-jvectormap-1.2.2.min.js"></script>
<script src="<%=request.getContextPath()%>/assets/note/js/jvectormap/jquery-jvectormap-world-mill-en.js"></script>
<script src="<%=request.getContextPath()%>/assets/note/js/jvectormap/demo.js"></script>
<script src="<%=request.getContextPath()%>/assets/note/js/app.plugin.js"></script>
