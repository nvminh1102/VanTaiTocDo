<%@ page isELIgnored="false" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<div class="panel-group" id="accordionUserGroup" style="padding-top: 25px;">
    <div class="panel panel-default">
        <div class="panel-heading">
            <h3 class="panel-title " style="text-transform: none;" data-toggle="collapse"
                   data-parent="#accordionUserGroup" href="#collapseUserGroup">
                <a style="text-transform: none;" >
                    <spring:message code="label.user.list.groug.managerment"/> <font style="font-weight: bold">${userView.username}</font> </a><i class="indicator glyphicon glyphicon-chevron-down  pull-right"></i>
            </h3>
        </div>
        <div id="collapseUserGroup" class="panel-collapse collapse in">
            <div class="panel-body" >
                <div class="table-responsive table-overflow-x-fix">
                    <table id="tblGroup" class="table table-hover table-bordered m-b-none">
                        <thead>
                            <tr class="bg-info text" style="color: white;">
                                <th style="width: 2%">#</th>
                                <th style="width: 25%"><spring:message code="label.group.name"/></th>
                                <th style="width: 73%"><spring:message code="label.system.parameter.description"/></th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${pageViewGroup.items}" var="item" varStatus="stat">
                                <tr>
                                    <td class="align-center">${stat.count+((page.pageNumber-1)*page.numberPerPage)}</td>
                                    <td class="align-center">${item.groupName}</td>
                                    <td class="align-center">${item.description}</td>
                                </tr>

                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
            <footer class="panel-footer">
                <div class="row">
                    <div class="col-sm-12 text-right text-center-xs">
                        <div class="col-sm-6 text-right" style="vertical-align: middle; padding-top: 10px;">
                           <sec:authorize access="hasRole('ROLE_SYSTEM_USER_AUTHORITY')">
                            <a class="btn btn-primary pull-left" style="margin-left: -15px;"
                               href="<%=request.getContextPath()%>/system/user/user-group/${userView.id}"><img src="<%=request.getContextPath()%>/assets/images/icon-singin.png" style="max-height: 18px; "><spring:message code="label.set.role"/></a>
                        </sec:authorize>
                        <%--<sec:authorize access="hasRole('ROLE_SYSTEM_USER_AUTHORITY')">--%>
<!--                            <a class="btn btn-danger pull-left" style="margin-left: 10px;"
                               href="<%=request.getContextPath()%>/system/user/add"><i class="fa fa-minus"></i> Loại bỏ nhóm quyền</a>-->
                        <%--</sec:authorize>--%>
                        <span><spring:message code="label.total"/> <code  class="currencyHtml" >${pageViewGroup.rowCount}</code> <spring:message code="label.data"/></span>
                        </div>
                        <div class="col-sm-6">
                            <ul class="pagination pagination-sm m-t-none m-b-none">
                                <c:if test="${pageViewGroup.pageNumber > 1}">
                                    <li>
                                        <a href="javascript:void(0);" onclick="search_result_group_by_user_page(${userView.id},1);">«</a>
                                    </li>
                                </c:if>
                                <c:forEach items="${pageViewGroup.pageList}" var="item" varStatus="stat">
                                    <c:choose>
                                        <c:when test="${pageViewGroup.pageNumber==item}">
                                            <li><a style="color: #aa1111" href="javascript:void(0);" onclick="search_result_group_by_user_page(${userView.id},${item});">${item}</a></li>
                                            </c:when>
                                            <c:otherwise>
                                            <li><a  href="javascript:void(0);" onclick="search_result_group_by_user_page(${userView.id},${item});">${item}</a></li>
                                            </c:otherwise>
                                        </c:choose>

                                </c:forEach>
                                <c:if test="${pageViewGroup.pageNumber < pageViewGroup.getPageCount()}">
                                    <li>
                                        <a href="javascript:void(0);" onclick="search_result_group_by_user_page(${userView.id},${pageViewGroup.getPageCount()});">»</a>
                                    </li>
                                </c:if>
                            </ul>
                        </div>
                    </div>
                </div>
            </footer>        
        </div>
    </div>
</div> 

<script>
    $(document).ready(function () {

    });
    function search_result_group_by_user_page(id,page) {
        if (id === '' || page === '') {
            CommonFunction.showPopUpMsg("<spring:message code="message"/>", "<spring:message code="message.have.error"/>", "error");
        }else{
            $("#searchResultAuthority").html('<div class="text-center"><img src="<%=request.getContextPath()%>/assets/images/loading.gif" /> Đang tải dữ liệu...</div>');
            $("#searchResultAuthority").load("<%=request.getContextPath()%>/system/user/search-group-by-user-" + id + "?p="+page);
        }
        return false;
    };
</script>
