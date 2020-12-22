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
                    <spring:message code="label.list.grouprole.user"/> <font style="font-weight: bold">${groupView.groupName}</font> </a><i class="indicator glyphicon glyphicon-chevron-down  pull-right"></i>
            </h3>
        </div>
        <div id="collapseUserGroup" class="panel-collapse collapse in">
            <div class="panel-body" >
                <div class="table-responsive table-overflow-x-fix">
                    <div class="table-responsive table-overflow-x-fix">
                        <table id="tblUserByGroup" class="table table-striped m-b-none">
                            <thead>
                                <tr>
                                    <th style="width: 1%">#</th>
                                    <!--<th style="width: 1%"><input type="checkbox" name="chkAll" id="chkAll" onclick="checkAll('chkAll', 'chk');" /></th>-->
                                    <th  style="width: 8%"><spring:message code="label.login.input.username"/></th>
                                    <th  style="width: 15%"><spring:message code="label.user.fullname"/></th>
                                    <th  style="width: 10%"><spring:message code="label.status"/></th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach items="${pageViewUser.items}" var="item" varStatus="stat">
                                    <tr>
                                        <td class="align-center">${stat.count+((pageViewUser.pageNumber-1)*pageViewUser.numberPerPage)}</td>
                                        <!--<td class="align-center"><input type="checkbox" value="${item.id}" class="selected_item" alt="chk" onclick="choose();"></td>-->
                                        <td class="align-center">${item.username}</td>
                                        <td class="align-center">${item.fullName}</td>
                                        <td><div style="color:${item.status ==1 ? 'green' : 'red'}; width:100px;cursor:pointer;font-weight:600;">${item.status ==1 ? 'Kích hoạt' : 'Khóa'}</div></td>					
                                    </tr>

                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                    
                </div>
            </div>
            <footer class="panel-footer">
                <div class="row">
                    <div class="col-sm-12 text-right text-center-xs">
                        <div class="col-sm-6 text-right" style="vertical-align: middle; padding-top: 10px;">
                            <span>Tổng số <code  class="currencyHtml" >${pageViewUser.rowCount}</code> dữ liệu</span>
                        </div>
                        <div class="col-sm-6">
                            <ul class="pagination pagination-sm m-t-none m-b-none">
                                <c:if test="${pageViewUser.pageNumber > 1}">
                                    <li>
                                        <a href="javascript:void(0);" onclick="search_result_user_by_group_page(${groupView.id},1);">«</a>
                                    </li>
                                </c:if>
                                <c:forEach items="${pageViewUser.pageList}" var="item" varStatus="stat">
                                    <c:choose>
                                        <c:when test="${pageViewUser.pageNumber==item}">
                                            <li><a style="color: #aa1111" href="javascript:void(0);" onclick="search_result_user_by_group_page(${groupView.id},${item});">${item}</a></li>
                                            </c:when>
                                            <c:otherwise>
                                            <li><a  href="javascript:void(0);" onclick="search_result_user_by_group_page(${groupView.id},${item});">${item}</a></li>
                                            </c:otherwise>
                                        </c:choose>

                                </c:forEach>
                                <c:if test="${pageViewUser.pageNumber < pageViewUser.getPageCount()}">
                                    <li>
                                        <a href="javascript:void(0);" onclick="search_result_user_by_group_page(${groupView.id},${pageViewUser.getPageCount()});">»</a>
                                    </li>
                                </c:if>
                            </ul>
                        </div>
                    </div>
                </div>
            </footer>            
        </div>
    </div> 
    <script>
        $(document).ready(function () {

        });
        function search_result_user_by_group_page(id,page) {
        if (id === '' || page === '') {
CommonFunction.showPopUpMsg("<spring:message code="message"/>", "<spring:message code="message.have.error"/>", "error");       
 }else{
            $("#searchResultUserByGroup").html('<div class="text-center"><img src="<%=request.getContextPath()%>/assets/images/loading.gif" /> Đang tải dữ liệu...</div>');
            $("#searchResultUserByGroup").load("<%=request.getContextPath()%>/system/group/search-user-by-group-" + id + "?p="+page);
        }
        return false;
    };
        
    </script>
