<%@ page isELIgnored="false" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<script src="<%=request.getContextPath()%>/assets/js/checkbox.js"></script>
<link rel="stylesheet" href="<%=request.getContextPath()%>/assets/note/js/nestable/nestable.css" type="text/css" />
<div class="panel-body">
    <h5 class="m-t-none font-bold"><spring:message code="label.tree.user.role"/></h5>
    <div class="col-sm-12 no-padder">
        <div class="col-sm-8" style="padding-left: 15px"> <button id="nestable-menu" class="btn btn-xs btn-default active" data-toggle="class:show">
                <i class="fa fa-plus text"></i>
                <span class="text font-bold">Expand All</span>
                <i class="fa fa-minus text-active"></i>
                <span class="text-active font-bold">Collapse All</span>
            </button>
        </div>
    </div>

    <div class="col-sm-12">
        <div class="dd" id="nestableGroup">
            <ol class="dd-list">
                <c:forEach var="group" items="${groups}" varStatus="stat">        
                    <div >              
                        <li class="dd-item" data-id="${group.parent.id}">
                            <div class="dd-handle font-bold">${group.parent.description}</div>
                            <ol class="dd-list">
                                <c:forEach var="children" items="${group.childrens}" varStatus="start">
                                    <li class="dd-item" data-id="${children.id}"><div class="dd-handle">${start.count}. ${children.description}</div></li>
                                    </c:forEach>
                            </ol>
                        </li>        
                    </div>
                </c:forEach> 
            </ol>
        </div>
    </div>
</div>
<script src="<%=request.getContextPath()%>/assets/note/js/nestable/jquery.nestable.js"></script>
<script>
    $(document).ready(function () {
        // activate Nestable for class #nestableGroup
        $('#nestableGroup').nestable({
        });

        var $expandAll = false;
        $('#nestable-menu').on('click', function (e)
        {
            if ($expandAll) {
                $expandAll = false;
                $('.dd').nestable('expandAll');
            } else {
                $expandAll = true;
                $('.dd').nestable('collapseAll');
            }
        });

        $('.dd').nestable('collapseAll');

    });
</script>