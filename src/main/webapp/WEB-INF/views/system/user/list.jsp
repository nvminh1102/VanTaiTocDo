<%@ page isELIgnored="false" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<section id="content">
    <section class="vbox">
        <section class="scrollable padder">
            <ul class="breadcrumb no-border no-radius b-b b-light pull-in">
                <li><a href="<%=request.getContextPath()%>/admin"><i class="fa fa-home"></i>&nbsp;<spring:message code="label.system.home"/></a></li>
                <li><a href="#"><spring:message code="label.system.manager"/></a></li>
                <li><a href="javascript:void(0)"><spring:message code="label.list.user"/></a></li>
            </ul>
            <div class="m-b-md"><h3 class="m-b-none" id="sansim-status" style="color: #009900"><c:if test="${success.length()>0}"><spring:message code="${success}"/></c:if></h3>
            </div>

            <section class="panel panel-default">
                <header class="panel-heading"><i class="fa fa-caret-square-o-right"></i> Danh sách tài khoản

                </header>
                <div class="text-sm wrapper bg-light lt">
                    <form cssClass="form-inline padder" action="list" role="form" theme="simple" >
                        <div class="form-group">
                            <div class="col-md-6">
                                <div class="col-sm-8">
                                    <input name="filterUsername"  placeholder="Username" maxlength="50" value="${filterUsername}" class="input-sm form-control"/>
                                </div>
                                <div class="col-sm-4"><button type="submit" class="btn btn-sm btn-primary pull-right"><spring:message code="label.button.search"/></button></div>
                            </div>
                            <div class="col-md-6">
                                <sec:authorize access="hasRole('ROLE_SYSTEM_USER_ADD')">
                                    <a class="btn btn-sm btn-primary pull-right"
                                       href="<%=request.getContextPath()%>/system/user/add"><i class="fa fa-plus"></i><spring:message code="label.button.add"/></a>
                                </sec:authorize>
                            </div>
                        </div>
                        <div class="line line-dashed line-lg pull-in" style="clear:both ;border-top:0px"></div>
                    </form>
                </div>

                <div class="table-responsive">
                    <table class="table table-striped m-b-none">
                        <thead>
                        <tr>
                            <th class="box-shadow-inner small_col text-center">#</th>
                            <th class="box-shadow-inner small_col text-center">Username</th>
                            <th class="box-shadow-inner small_col text-center"><spring:message code="label.user.fullname"/></th>
                            <th class="box-shadow-inner small_col text-center"><spring:message code="label.status"/></th>
                            <th class="box-shadow-inner small_col text-center"><spring:message code="label.description"/> </th>
                            <th class="box-shadow-inner small_col text-center action-thead-3-items"><spring:message code="label.action"/></th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${page.items}" var="item" varStatus="stat">
                            <tr>
                                <td class="align-center">${stat.count+((page.pageNumber-1)*page.numberPerPage)}</td>
                                <td class="align-center">${item.username}</td>
                                <td class="align-center">${item.fullName}</td>
                                <td class="align-center">${item.status==1?"Hoạt động":"Vô hiệu hóa"}</td>
                                <td class="align-center">${item.description}</td>
                                <sec:authorize access="hasAnyRole('ROLE_SYSTEM_USER_EDIT','ROLE_SYSTEM_USER_AUTHORITY','ROLE_SYSTEM_LOG_VIEW')">
                                    <td class="align-center">
                                        <div class="btn-group">
                                            <button class="btn btn-default btn-xs dropdown-toggle" data-toggle="dropdown"><i
                                                    class="fa fa-th-list"></i></button>
                                            <ul class="dropdown-menu pull-right">
                                                <sec:authorize access="hasRole('ROLE_SYSTEM_USER_EDIT')">
                                                    <li><a href="<%=request.getContextPath()%>/system/user/edit/${item.id}"><img src="<%=request.getContextPath()%>/assets/images/edit.png" style="max-height: 18px; "> <font style="font-weight: bold;"> <spring:message code="label.edit.info"/></font></a></li>
                                                    <div class="line line-dashed m-b-none m-t-none"></div>
                                                </sec:authorize>
                                                <sec:authorize access="hasRole('ROLE_SYSTEM_USER_AUTHORITY')">
                                                    <li><a href="<%=request.getContextPath()%>/system/user/user-group/${item.id}"><img src="<%=request.getContextPath()%>/assets/images/icon-singin.png" style="max-height: 18px; "> <font style="font-weight: bold;"> <spring:message code="label.set.role"/></font></a></li>
                                                    <div class="line line-dashed m-b-none m-t-none"></div>
                                                </sec:authorize>
                                                <sec:authorize access="hasRole('ROLE_SYSTEM_LOG_VIEW')">
                                                    <li><a href="<%=request.getContextPath()%>/system/history/${item.id}"><img src="<%=request.getContextPath()%>/assets/images/icon-history.png" style="max-height: 18px; "> <font style="font-weight: bold;"> <spring:message code="label.view.history"/></font></a></li>
                                                    <div class="line line-dashed m-b-none m-t-none"></div>
                                                </sec:authorize>
                                                <sec:authorize access="hasRole('ROLE_SYSTEM_USER_EDIT')">
                                                    <li><a href="javascript:void(0)" onclick="restorePassword(${item.id})"><img src="<%=request.getContextPath()%>/assets/images/icon-reset.png" style="max-height: 18px; "> <font style="font-weight: bold;"> <spring:message code="label.reset.password"/></font></a></li>
                                                </sec:authorize>
                                            </ul>
                                        </div>
                                    </td>
                                </sec:authorize>
                            </tr>

                        </c:forEach>
                        </tbody>
                    </table>
                </div>
                <footer class="panel-footer">
                    <div class="row">
                        <div class="col-sm-12 text-right text-center-xs">
                            <div class="col-sm-6 text-left">
                                <span><spring:message code="label.total"/> <code  class="currencyHtml">${page.rowCount}</code> dữ liệu</span>
                            </div>
                            <div class="col-sm-6">
                                <ul class="pagination pagination-sm m-t-none m-b-none">
                                    <c:if test="${page.pageNumber > 1}">
                                        <li>
                                            <a href="<%=request.getContextPath()%>/system/user/list?p=1&filterUsername=${filterUsername}">«</a>
                                        </li>
                                    </c:if>
                                    <c:forEach items="${page.pageList}" var="item" varStatus="stat">
                                        <c:choose>
                                            <c:when test="${page.pageNumber==item}">
                                                <li><a style="color: #aa1111"
                                                       href="<%=request.getContextPath()%>/system/user/list?p=${item}&filterUsername=${filterUsername}">${item}</a></li>
                                            </c:when>
                                            <c:otherwise>
                                                <li><a  href="<%=request.getContextPath()%>/system/user/list?p=${item}&filterUsername=${filterUsername}">${item}</a></li>
                                            </c:otherwise>
                                        </c:choose>

                                    </c:forEach>
                                    <c:if test="${page.pageNumber < page.getPageCount()}">
                                        <li>
                                            <a href="<%=request.getContextPath()%>/system/user/list?p=${page.getPageCount()}&filterUsername=${filterUsername}">»</a>
                                        </li>
                                    </c:if>
                                </ul>
                            </div>
                        </div>
                    </div>
                </footer>
            </section>
        </section>
    </section>
    <a href="#" class="hide nav-off-screen-block" data-toggle="class:nav-off-screen" data-target="#nav"></a>
</section>

<script>
    $(document).ready(function () {

        $('#tblUser').dataTable({
            "bFilter": false,
            "bPaginate": false,
            "bAutoWidth": false,
            "sPaginationType": "full_numbers"
        });

    });
    function restorePassword(userId){
        $.ajax({
            url: "<%= request.getContextPath() %>/system/user/khoi-phuc-mat-khau",
            type: "POST",
            data : {userId: userId},
            success: function(data){
                if(data === 'ok')
                {
                    CommonFunction.showPopUpMsg("Thông báo", "Khôi phục mật khẩu thành công","success");
                } else{
                    CommonFunction.showPopUpMsg("Thông báo", data, "error");
                }
            },
            error: function(data){
                CommonFunction.showPopUpMsg("Thông báo", "Có lỗi xảy ra. Vui lòng thử lại sau!", "error");
            }
        });
        return false;
    }
</script>
