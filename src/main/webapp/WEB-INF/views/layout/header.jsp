<%@ page isELIgnored="false" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%--<header class="bg-secondary dk header navbar navbar-fixed-top-xs">--%>

    <%--<div class="navbar-header aside-md ">--%>
        <%--<a class="btn btn-link visible-xs" data-toggle="class:nav-off-screen,open" data-target="#nav,html">--%>
            <%--<i class="fa fa-bars"></i>--%>
        <%--</a>--%>
        <%--<a href="<%=request.getContextPath()%>/" class="navbar-brand" data-toggle="fullscreen1111"><img src="<%=request.getContextPath()%>/assets/images/logo-osp.png" class="m-r-sm" style="max-height: 45px;"></a>--%>
        <%--<a class="btn btn-link visible-xs" data-toggle="dropdown" data-target=".nav-user">--%>
            <%--<i class="fa fa-cog"></i>--%>
        <%--</a>--%>
    <%--</div>--%>

    <%--<ul class="nav navbar-nav navbar-right hidden-xs nav-user">--%>
        <%--<li class="hidden-xs">--%>
            <%--<a href="#" class="dropdown-toggle dk" data-toggle="dropdown">--%>
                <%--<spring:message code="label.button.help"></spring:message>--%>
            <%--</a>--%>
            <%--<section class="dropdown-menu aside-xl">--%>
                <%--<section class="panel bg-white">--%>
                    <%--<header class="panel-heading b-light bg-light">--%>
                        <%--<strong><spring:message code="label.select.document"></spring:message></strong>--%>
                    <%--</header>--%>
                    <%--<div class=" list-group-alt animated fadeInRight">--%>
                        <%--<a href="#" class="media list-group-item">--%>
                              <%--<span class="pull-left thumb-sm">--%>
                                 <%--<i class="fa fa-book fa-3x"></i>--%>
                              <%--</span>--%>
                            <%--<span class="media-body block m-b-none">--%>
                                                       <%--<strong><spring:message code="label.button.support"></spring:message></strong>--%>

                              <%--</span>--%>
                        <%--</a>--%>
                    <%--</div>--%>
                <%--</section>--%>
            <%--</section>--%>

        <%--</li>--%>
       <%----%>
        <%--<li class="dropdown" style="margin-right:14px;">--%>
            <%--<a href="#" class=" dropdown-toggle "  data-toggle="dropdown" > <span class="thumb-sm avatar pull-left"> <img src="<%=request.getContextPath()%>/assets/images/user.png"> </span></span> <sec:authentication property="principal.fullName" /> <b class="caret"></b> </a>--%>
            <%--<ul class="dropdown-menu animated fadeInRight "><span class="arrow top"></span>--%>
                <%--<li><a href="#"><spring:message code="label.profile" ></spring:message></a></li>--%>
                <%--<li ><a href="<%=request.getContextPath()%>/system/user/change-my-pass"><spring:message code="label.button.changepassword" ></spring:message></a></li>--%>
                <%--<li class="divider"></li>--%>
                <%--<li><a href="<%=request.getContextPath()%>/j_spring_security_logout"><spring:message code="label.button.logout" ></spring:message></a></li>--%>
            <%--</ul>--%>
        <%--</li>--%>
        <%--<li class="hidden-xs"> <a href="?lang=en" style="float: left;padding-top: 10px;"><img src="<%=request.getContextPath()%>/assets/images/icon/language_en.png" title="English"></a>  <a style="float: left;padding-top: 10px;margin-right: 10px" href="?lang=vi"><img src="<%=request.getContextPath()%>/assets/images/icon/language_vi.png" title="Vietnamese"></a></li>--%>

    <%--</ul>--%>
<%--</header>--%>
<header class="bg-dark dk header navbar navbar-fixed-top-xs" style="background-color: #2A2C54">
    <div class="navbar-header aside-md" style="background-color: #2A2C54;">
        <a class="btn btn-link visible-xs" data-toggle="class:nav-off-screen,open" data-target="#nav,html">
            <i class="fa fa-bars"></i>
        </a>
        <a href="<%=request.getContextPath()%>/" class="navbar-brand" data-toggle="fullscreen">
            <img src="<%=request.getContextPath()%>/assets/images/VanTai-VN.png" style="max-height: 45px;">
            QUẢN LÝ VẬN TẢI
        </a>
        <a class="btn btn-link visible-xs" data-toggle="dropdown" data-target=".nav-user">
            <i class="fa fa-cog"></i>
        </a>
    </div>

    <ul class="nav navbar-nav navbar-right hidden-xs nav-user">
        <li class="hidden-xs">
            <a href="#" class="dropdown-toggle dk" data-toggle="dropdown" style="background-color: unset;color: white;">
                <spring:message code="label.button.help"></spring:message>
                </a>
            <section class="dropdown-menu aside-xl">
                <section class="panel bg-white">
                    <header class="panel-heading b-light bg-light">
                        <strong><spring:message code="label.select.document"></spring:message></strong>
                    </header>
                    <div class=" list-group-alt animated fadeInRight">
                        <a href="#" class="media list-group-item">
                              <span class="pull-left thumb-sm">
                                 <i class="fa fa-book fa-3x"></i>
                              </span>
                            <span class="media-body block m-b-none">
                                                       <strong><spring:message code="label.button.support"></spring:message></strong>

                              </span>
                        </a>
                    </div>
                </section>
            </section>
        </li>

        <li class="dropdown" id="dropdownUser"  style="margin-right:14px;">
            <a href="#" class=" dropdown-toggle "  data-toggle="dropdown" > <span class="thumb-sm avatar pull-left"> <img src="<%=request.getContextPath()%>/assets/images/user.png"> </span></span> <sec:authentication property="principal.fullName" /> <b class="caret"></b> </a>
            <ul class="dropdown-menu animated fadeInRight "><span class="arrow top"></span>
                <li><a href="<%=request.getContextPath()%>/system/user/edit/<sec:authentication property="principal.id" />"><spring:message code="label.profile" ></spring:message></a></li>
                <li ><a href="<%=request.getContextPath()%>/system/user/change-my-pass"><spring:message code="label.button.changepassword" ></spring:message></a></li>
                <li class="divider"></li>
                <li><a href="<%=request.getContextPath()%>/j_spring_security_logout"><spring:message code="label.button.logout" ></spring:message></a></li>
            </ul>
        </li>
    </ul>
</header>


