<%@page import="com.osp.model.User" %>
<%@ page isELIgnored="false" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<%!
    public boolean isActive(String navPath, HttpServletRequest request) {
        String namespace = (String) request.getAttribute("javax.servlet.forward.request_uri");
        if (namespace.startsWith(navPath)) {
            return true;
        }
        return false;
    }

    ;

    public boolean isActiveIndex(String navPath, HttpServletRequest request) {
        String namespace = (String) request.getAttribute("javax.servlet.forward.request_uri");
        if (namespace.equals(navPath)) {
            return true;
        }
        return false;
    }

    public boolean isNavXs(HttpServletRequest request) {
        if (request.getSession().getAttribute("nav-xs") != null) {
            Boolean thugon = (Boolean) request.getSession().getAttribute("nav-xs");
            if (thugon.equals(true)) {
                return true;
            }
        }
        return false;
    }

%>
<%

    User user = (User) request.getSession().getAttribute("userLogin");
    request.setAttribute("userLogin", user);


%>

<aside class="bg-dark lter aside-md hidden-print hidden-xs" id="nav">
    <section class="vbox">
        <section class="w-f scrollable" style="background-color: #2A2C54;">
            <div class="slim-scroll" data-height="auto" data-disable-fade-out="true" data-distance="0" data-size="5px"
                 data-color="#333333">

                <!-- nav -->
                <nav class="nav-primary hidden-xs">
                    <ul class="nav">
                        <li class="<%= isActive(request.getContextPath()+"/system",request) ? "active" : "" %>">
                            <a href="<%=request.getContextPath()%>/">
                                <i class="fa fa-dashboard icon">
                                    <b class="bg-success"></b>
                                </i>
                                <span class="pull-right">

                        </span>
                                <span><spring:message code="label.system.home"></spring:message></span>
                            </a>

                        </li>
                        <sec:authorize
                                access="hasAnyRole('ROLE_SYSTEM_USER_VIEW','ROLE_SYSTEM_GROUP_VIEW','ROLE_SYSTEM_LOG_VIEW')">

                            <li>
                                <a href="<%=request.getContextPath()%>/system/user/list">
                                    <i class="fa fa-cogs icon">
                                        <b class="bg-warning"></b>
                                    </i>
                                    <span class="pull-right">
                          <i class="fa fa-angle-down text"></i>
                          <i class="fa fa-angle-up text-active"></i>
                         </span>
                                    <span><spring:message code="label.system.manager"></spring:message></span>
                                </a>
                                <ul class="nav lt">
                                    <sec:authorize access="hasRole('ROLE_SYSTEM_USER_VIEW')">

                                        <li>
                                            <a href="<%=request.getContextPath()%>/system/user/list">
                                                <i class="fa fa-angle-right"></i>
                                                <span><spring:message code="label.user"></spring:message></span> </a>
                                            </a>
                                        </li>
                                    </sec:authorize>
                                    <sec:authorize access="hasRole('ROLE_SYSTEM_GROUP_VIEW')">

                                        <li>
                                            <a href="<%=request.getContextPath()%>/system/group/list">
                                                <i class="fa fa-angle-right"></i>
                                                <span><spring:message code="lable.group.role"></spring:message></span>
                                            </a>
                                        </li>
                                    </sec:authorize>

                                    <sec:authorize access="hasRole('ROLE_SYSTEM_LOG_VIEW')">

                                        <li>
                                            <a href="<%=request.getContextPath()%>/system/history/list">
                                                <i class="fa fa-angle-right"></i>
                                                <span><spring:message
                                                        code="label.system.history"></spring:message></span>
                                            </a>
                                        </li>
                                    </sec:authorize>
                                </ul>
                            </li>
                        </sec:authorize>

                        <li class="<%= isActive(request.getContextPath() + "/bienNhan", request) ? "active" : ""%>">
                            <a href="#" class=""> <i class="fa fa-credit-card icon"> <b class="bg-warning"></b> </i> <span
                                    class="pull-right"> <i class="fa fa-angle-down text"></i> <i
                                    class="fa fa-angle-up text-active"></i> </span> <span>Biên nhận</span> </a>
                            <ul class="nav lt">
                                <li class="<%= isActive(request.getContextPath() + "/bienNhan/preAdd", request) ? "active" : ""%>">
                                    <a href="<%=request.getContextPath()%>/bienNhan/preAdd" class=""> <i
                                            class="fa fa-angle-right"></i> <span>Thêm mới biên nhận</span> </a></li>
                                <li class="<%= isActive(request.getContextPath() + "/bienNhan/danh-sach", request) ? "active" : ""%>">
                                    <a href="<%=request.getContextPath()%>/bienNhan/list" class=""> <i
                                            class="fa fa-angle-right"></i> <span>Danh sách biên nhận</span> </a></li>
                            </ul>
                            <ul class="nav lt">
                                <li class="<%= isActive(request.getContextPath() + "/phieu-nhan-hang/list", request) ? "active" : ""%>">
                                    <a href="<%=request.getContextPath()%>/phieu-nhan-hang/list" class=""> <i
                                            class="fa fa-angle-right"></i> <span>Phiếu nhận hàng</span> </a></li>
                            </ul>
                        </li>

                        <li>
                            <a href="<%=request.getContextPath()%>/history">
                                <i class="fa fa-clock-o icon">
                                    <b class="bg-danger"></b>
                                </i>
                                <span class="pull-right">

                        </span>
                                <span><spring:message code="label.system.myhistory"></spring:message></span>
                            </a>

                        </li>

                    </ul>
                </nav>
                <!-- / nav -->
            </div>
        </section>

        <footer class="footer lt hidden-xs b-t b-dark">
            <div id="chat" class="dropup">
                <section class="dropdown-menu on aside-md m-l-n">
                    <section class="panel bg-white">
                        <header class="panel-heading b-b b-light">Active chats</header>
                        <div class="panel-body animated fadeInRight">
                            <p class="text-sm">No active chats.</p>
                            <p><a href="#" class="btn btn-sm btn-default">Start a chat</a></p>
                        </div>
                    </section>
                </section>
            </div>
            <div id="invite" class="dropup">
                <section class="dropdown-menu on aside-md m-l-n">
                    <section class="panel bg-white">
                        <header class="panel-heading b-b b-light">
                            John <i class="fa fa-circle text-success"></i>
                        </header>
                        <div class="panel-body animated fadeInRight">
                            <p class="text-sm">No contacts in your lists.</p>
                            <p><a href="#" class="btn btn-sm btn-facebook"><i class="fa fa-fw fa-facebook"></i> Invite
                                from Facebook</a></p>
                        </div>
                    </section>
                </section>
            </div>
            <a href="#nav" data-toggle="class:nav-xs" class="pull-right btn btn-sm btn-dark btn-icon">
                <i class="fa fa-angle-left text"></i>
                <i class="fa fa-angle-right text-active"></i>
            </a>
            <%--<div class="btn-group hidden-nav-xs">--%>
                <%--<button type="button" title="Chats" class="btn btn-icon btn-sm btn-dark" data-toggle="dropdown"--%>
                        <%--data-target="#chat"><i class="fa fa-comment-o"></i></button>--%>
                <%--<button type="button" title="Contacts" class="btn btn-icon btn-sm btn-dark" data-toggle="dropdown"--%>
                        <%--data-target="#invite"><i class="fa fa-facebook"></i></button>--%>
            <%--</div>--%>
        </footer>
    </section>
</aside>
<script type="text/javascript">
    $(document).ready(function () {
        $('#changeNavXS').click(function () {
            $.ajax({
                type: "get",
                url: "<%=request.getContextPath()%>/change-nav",
                success: function (msg) {
                }
            });
        });
        <%--$.ajax({--%>
        <%--type: "get",--%>
        <%--url: "<%=request.getContextPath()%>/sun-moon",--%>
        <%--success: function(msg){--%>
        <%--}--%>
        <%--});--%>

    });
</script>