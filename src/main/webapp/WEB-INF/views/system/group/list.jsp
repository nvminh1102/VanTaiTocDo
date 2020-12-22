<%@ page isELIgnored="false" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %> 
<section id="content">
    <section class="vbox">
        <section class="scrollable padder" style="background: white">
            <ul class="breadcrumb breadcrumb no-border no-radius b-b b-light pull-in">
               <li><a href="<%=request.getContextPath()%>/"><i class="fa fa-home"></i>&nbsp;<spring:message code="label.system.home"/></a></li>
                <li><a href="<%=request.getContextPath()%>/system/group/list"><spring:message code="label.group"/></a></li>
                <li><a href="javascript:void(0)">Danh sách nhóm quyền</a></li>
            </ul>
            <div class="m-b-md" id="sansim-status"><h3 class="m-b-none" style="color: #009900"><c:if test="${!empty success}"><spring:message code="${success}"/></c:if></h3>
                <h3 class="m-b-none" style="color: red"><c:if test="${!empty messageError}"><spring:message code="${messageError}"/></c:if></h3>
                </div>

                <section class="panel panel-default">
                    <div class="panel-body">
                        <form cssClass="form-inline padder" action="list" id="formItem" role="form" theme="simple" >
                            <div class="form-group">
                                <div class="col-md-6">
                                    <div class="col-sm-8">
                                        <input name="filterName"  placeholder="Tên nhóm" maxlength="50" value="${filterName}" class="input form-control"/>
                                </div>
                                <div class="col-sm-4"><button type="submit" class="btn btn-info"><i class="fa fa-search"></i><spring:message code="label.button.search"/></button></div>
                            </div>
<!--                            <div class="col-md-6">
                                <a class="btn btn-primary pull-right"
                                   href="<%=request.getContextPath()%>/system/group/add"><i class="fa fa-plus"></i> Thêm mới</a>
                            </div>-->
                        </div>
                        <div class="line line-dashed line-lg pull-in" style="clear:both ;border-top:0px"></div>
                    </form>
                </div>
            </section>
            <div class="table-responsive table-overflow-x-fix">
                <table id="tblGroup" class="table table-striped m-b-none">
                    <thead>
                        <tr>
                            <th style="width: 2%">STT</th>
                            <th style="width: 1%"><input type="checkbox" name="chkAll" id="chkAll" onclick="checkAllGroup('chkAll', 'chk');" /></th>
                            <th>Tên nhóm</th>
                            <th>Mô tả</th>
                            <sec:authorize access="hasAnyRole('ROLE_SYSTEM_GROUP_EDIT','ROLE_SYSTEM_GROUP_DELETE')">
                                <th></th>
                            </sec:authorize>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${page.items}" var="item" varStatus="stat">
                            <tr>
                                <td class="align-center">${stat.count+((page.pageNumber-1)*page.numberPerPage)}</td>
                                <td class="align-center"><input type="checkbox" value="${item.id}" class="selected_item" alt="chk" onclick="chooseGroup();"></td>
                                <td class="align-center">${item.groupName}</td>
                                <td class="align-center">${item.description}</td>
                                <sec:authorize access="hasAnyRole('ROLE_SYSTEM_GROUP_EDIT','ROLE_SYSTEM_GROUP_DELETE')">
                                    <td class="text-right">
                                        <div class="col-sm-12">
                                                <sec:authorize access="hasAnyRole('ROLE_SYSTEM_GROUP_EDIT')">
                                                    <a  class="btn btn-success btn-sm font-bold" href="<%=request.getContextPath()%>/system/group/edit/${item.id}"><i class="fa fa-edit"></i> <spring:message code="label.button.edit"/></a>
                                                    </sec:authorize>
                                                    <sec:authorize access="hasAnyRole('ROLE_SYSTEM_GROUP_DELETE')">
                                                        <a class="btn btn-danger btn-sm font-bold deleteItem"
                                                           data-toggle="modal" data-target="#deleteItem"
                                                           data-item.id="${item.id}"
                                                           data-item.name="${item.groupName}"><i class="fa fa-times"></i> <spring:message code="label.button.delete"/></a>
                                                </sec:authorize>
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
                        <div class="col-sm-6 text-right" style="vertical-align: middle;">
                            <sec:authorize access="hasRole('ROLE_SYSTEM_GROUP_ADD')">
                                <a class="btn btn-primary pull-left" style="margin-left: -30px;"
                                   href="<%=request.getContextPath()%>/system/group/add"><i class="fa fa-plus"></i><spring:message code="button.add.group"/></a>
                            </sec:authorize>
                            <%--<sec:authorize access="hasRole('ROLE_SYSTEM_GROUP_DELETE')">--%>
<!--                                <a class="btn btn-danger pull-left" style="margin-left: 10px;"
                                   href="<%=request.getContextPath()%>/system/group/add"><i class="fa fa-minus-square"></i> Xóa nhóm</a>-->
                            <%--</sec:authorize>--%>
                            <sec:authorize access="hasRole('ROLE_SYSTEM_GROUP_VIEW')">
                                <a class="btn btn-primary pull-left" style="margin-left: 10px;" href="javascript:void(0);" onclick="search_result_authority_by_group();"><i class="fa fa-list"></i> <spring:message code="button.function.group"/></a>
                            </sec:authorize>
                            <sec:authorize access="hasRole('ROLE_SYSTEM_GROUP_VIEW')">
                                <a class="btn btn-primary pull-left" style="margin-left: 10px;" href="javascript:void(0);" onclick="search_result_user_by_group();"><i class="fa fa-list"></i><spring:message code="button.user.group"/></a>
                            </sec:authorize>
                                <span><spring:message code="label.total"/> <code  class="currencyHtml" >${page.rowCount}</code><spring:message code="label.data"/> </span>
                        </div>
                        <div class="col-sm-6">
                            <ul class="pagination pagination-sm m-t-none m-b-none">
                                <c:if test="${page.pageNumber > 1}">
                                    <li>
                                        <a href="<%=request.getContextPath()%>/system/group/list.html?p=1&filterUsername=${filterUsername}">«</a>
                                    </li>
                                </c:if>
                                <c:forEach items="${page.pageList}" var="item" varStatus="stat">
                                    <c:choose>
                                        <c:when test="${page.pageNumber==item}">
                                            <li><a style="color: #aa1111" href="<%=request.getContextPath()%>/system/group/list.html?p=${item}&filterUsername=${filterUsername}">${item}</a></li>
                                            </c:when>
                                            <c:otherwise>
                                            <li><a  href="<%=request.getContextPath()%>/system/group/list.html?p=${item}&filterUsername=${filterUsername}">${item}</a></li>
                                            </c:otherwise>
                                        </c:choose>

                                </c:forEach>
                                <c:if test="${page.pageNumber < page.getPageCount()}">
                                    <li>
                                        <a href="<%=request.getContextPath()%>/system/group/list.html?p=${page.getPageCount()}&&filterUsername=${filterUsername}">»</a>
                                    </li>
                                </c:if>
                            </ul>
                        </div>
                    </div>
                </div>
            </footer>
            <div class="col-sm-12 no-padder">
                <div id="searchResultAuthorityByGroup"></div>
            </div>
            <div class="col-sm-12 no-padder">
                <div id="searchResultUserByGroup"></div>
            </div>            
        </section>
    </section>
    <a href="#" class="hide nav-off-screen-block" data-toggle="class:nav-off-screen" data-target="#nav"></a>
<input type="hidden" value="" name="ids" id="ids" >
<div class="modal fade"  id="deleteItem"  role="dialog" aria-labelledby="myModalLabel1" aria-hidden="true">
    <div class="modal-dialog" style="max-width: 500px;">
        <div class="modal-content"style="max-width: 500px;">
            <div class="modal-header" style="padding: 7px;">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h5 class="modal-title" id="myModalLable"><spring:message code="label.group.delete.role"/></h5>
            </div>
            <form id="filter" method="POST"  action="<%=request.getContextPath()%>/system/group/delete" theme="simple" enctype="multipart/form-data" cssClass="form-horizontal" cssStyle="" validate="false">
                <div class="modal-body"  style="padding: 10px;">
                    <div class="form-group">
                        <label class="control-label"><spring:message code="message.modal.question.remove"/></label>
                        <input name="id"  type="hidden" class="form-control info_id" />
                    </div>

                </div>
                <div class="modal-footer" style="padding: 10px;" >
                    <button type="button" class="btn btn-default" data-dismiss="modal"><spring:message code="message.modal.cancel"/></button>
                    <button type="submit" class="btn btn-danger"><spring:message code="label.button.delete"/></button>
                </div>
            </form>
        </div>
    </div>
</div>
</section>
<script>
    $(document).ready(function () {

        $('#tblUser').dataTable({
            "bFilter": false,
            "bPaginate": false,
            "bAutoWidth": false,
            "sPaginationType": "full_numbers"
        });

        $(".deleteItem").click(function () {
            var id = $(this).data('item.id');
            var name = $(this).data('item.name');

            $(".info_id").val(id);
            $(".modal-title").text('Xóa nhóm quyền "' + name + '"');
        });
        $("input").keypress(function (event) {
            if (event.which == 13) {
                event.preventDefault();
                $("#formItem").submit();
            }
        });
    });
    
    function search_result_authority_by_group() {
        if ($("#ids").val() === '' || $("#ids").val() === null) {
            CommonFunction.showPopUpMsg("<spring:message code="message"/>", "<spring:message code="message.selected.arecord"/>", "error");
        } else if($("#ids").val().indexOf(",") > -1 ){
            CommonFunction.showPopUpMsg("<spring:message code="message"/>", "<spring:message code="message.view.arecord"/>", "error");
        }else{
            var id = $("#ids").val();
            $("#searchResultAuthorityByGroup").html('<div class="text-center"><img src="<%=request.getContextPath()%>/assets/images/loading.gif" /> Đang tải dữ liệu...</div>');
            $("#searchResultAuthorityByGroup").load("<%=request.getContextPath()%>/system/group/search-authority-by-group-" + id);
        }
        return false;
    };
    function search_result_user_by_group() {
        if ($("#ids").val() === '' || $("#ids").val() === null) {
            CommonFunction.showPopUpMsg("<spring:message code="message"/>", "<spring:message code="message.selected.arecord"/>", "error");
        } else if($("#ids").val().indexOf(",") > -1 ){
            CommonFunction.showPopUpMsg("<spring:message code="message"/>", "<spring:message code="message.view.arecord"/>", "error");
        }else{
            var id = $("#ids").val();
            $("#searchResultUserByGroup").html('<div class="text-center"><img src="<%=request.getContextPath()%>/assets/images/loading.gif" /> Đang tải dữ liệu...</div>');
            $("#searchResultUserByGroup").load("<%=request.getContextPath()%>/system/group/search-user-by-group-" + id);
        }
        return false;
    };
    
    function chooseGroup() {
            var selected_val = "";
            var chkAll = true;
            $('.selected_item').each(function () {
                if (this.checked) {
                    if(selected_val === ""){
                        selected_val = $(this).val();
                    }else{
                        selected_val = selected_val + "," + $(this).val();
                    }
                }else{
                    chkAll = false;
                    $("#chkAll").prop("checked", false);
                }
            });
            if(chkAll){
                $("#chkAll").prop("checked", true);
            }
            
            $("#ids").val(selected_val);
    }
    function checkAllGroup(selector_fire, alt_name) {
	    $('input[alt=' + alt_name + ']').prop('checked', $('#' + selector_fire).is(':checked'));
            var selected_val = "";
            $('.selected_item').each(function () {
                if (this.checked) {
                    if(selected_val === ""){
                        selected_val = $(this).val();
                    }else{
                        selected_val = selected_val + "," + $(this).val();
                    }
                }
            });
            $("#ids").val(selected_val);
    }  
    
    function beforeDelete(id){
        $("#id").val(id);
    }
    
    
    
</script>
