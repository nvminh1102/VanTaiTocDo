<%@ page isELIgnored="false" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %> 
<div class="panel-group" id="accordionAuthorityGroup" style="padding-top: 25px;">
    <div class="panel panel-default">
        <div class="panel-heading">
            <h3 class="panel-title " style="text-transform: none;" data-toggle="collapse"
                data-parent="#accordionAuthorityGroup" href="#collapseAuthorityGroup">
                <a style="text-transform: none;" >
                    <spring:message code="list.function.group.role"/> <font style="font-weight: bold">${groupView.groupName}</font> </a><i class="indicator glyphicon glyphicon-chevron-down  pull-right"></i>
            </h3>
        </div>
        <div id="collapseAuthorityGroup" class="panel-collapse collapse in">
            <div class="panel-body" >
                <div class="table-responsive table-overflow-x-fix">
                    <div class="table-responsive table-overflow-x-fix">
                        <table id="tblAuthorityByGroup" class="table table-striped m-b-none">
                            <thead>
                                <tr>
                                    <th style="width: 1%">#</th>
                                    <!--<th style="width: 1%"><input type="checkbox" name="chkAll" id="chkAll" onclick="checkAll('chkAll', 'chk');" /></th>-->
                                    <th style="width: 25%"><spring:message code="label.function.code"/></th>
                                    <th style="width: 25%"><spring:message code="label.function.name"/></th>
                                    <th style="width: 74%"><spring:message code="label.system.parameter.description"/></th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach items="${pageViewAuthority.items}" var="item" varStatus="stat">
                                    <tr>
                                        <td class="align-center">${stat.count+((pageViewAuthority.pageNumber-1)*pageViewAuthority.numberPerPage)}</td>
                                        <!--<td class="align-center"><input type="checkbox" value="${item.id}" class="selected_item" alt="chk" onclick="choose();"></td>-->
                                        <td class="align-center">${item.authKey}</td>
                                        <td class="align-center">${item.authority}</td>
                                        <td class="align-center">${item.description}</td>
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
                            <span><spring:message code="label.total"/><code  class="currencyHtml" >${pageViewAuthority.rowCount}</code><spring:message code="label.data"/></span>
                        </div>
                        <div class="col-sm-6">
                            <ul class="pagination pagination-sm m-t-none m-b-none">
                                <c:if test="${pageViewAuthority.pageNumber > 1}">
                                    <li>
                                        <a href="javascript:void(0);" onclick="search_result_authority_by_group_page(${groupView.id}, 1);">«</a>
                                    </li>
                                </c:if>
                                <c:forEach items="${pageViewAuthority.pageList}" var="item" varStatus="stat">
                                    <c:choose>
                                        <c:when test="${pageViewAuthority.pageNumber==item}">
                                            <li><a style="color: #aa1111" href="javascript:void(0);" onclick="search_result_authority_by_group_page(${groupView.id},${item});">${item}</a></li>
                                            </c:when>
                                            <c:otherwise>
                                            <li><a  href="javascript:void(0);" onclick="search_result_authority_by_group_page(${groupView.id},${item});">${item}</a></li>
                                            </c:otherwise>
                                        </c:choose>

                                </c:forEach>
                                <c:if test="${pageViewAuthority.pageNumber < pageViewAuthority.getPageCount()}">
                                    <li>
                                        <a href="javascript:void(0);" onclick="search_result_authority_by_group_page(${groupView.id},${pageViewAuthority.getPageCount()});">»</a>
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
        function search_result_authority_by_group_page(id, page) {
            if (id === '' || page === '') {
                CommonFunction.showPopUpMsg("<spring:message code="message"/>", "<spring:message code="message.have.error"/>", "error");
            } else {
                $("#searchResultAuthorityByGroup").html('<div class="text-center"><img src="<%=request.getContextPath()%>/assets/images/loading.gif" /><spring:message code="label.loading.data"/></div>');
                $("#searchResultAuthorityByGroup").load("<%=request.getContextPath()%>/system/group/search-authority-by-group-" + id + "?p=" + page);
            }
            return false;
        }
    </script>
