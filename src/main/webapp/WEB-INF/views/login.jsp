<%@ page isELIgnored="false" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %> 
<!DOCTYPE html>
<html lang="en" class="bg-dark">
<head>
    <meta charset="utf-8"/>
    <title><spring:message code="label.login.header"></spring:message></title>
    <meta name="description" content=""/>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1"/>
    <link rel="shortcut icon" type="image/x-icon" href="<%=request.getContextPath()%>/assets/images/icon.png"/>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/note/css/bootstrap.css" type="text/css"/>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/note/css/animate.css" type="text/css"/>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/note/css/font-awesome.min.css" type="text/css"/>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/note/css/font.css" type="text/css"/>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/note/css/app.css" type="text/css"/>
    <!--[if lt IE 9]>
    <script src="<%=request.getContextPath()%>/assets/note/js/ie/html5shiv.js"></script>
    <script src="<%=request.getContextPath()%>/assets/note/js/ie/respond.min.js"></script>
    <script src="<%=request.getContextPath()%>/assets/note/js/ie/excanvas.js"></script>
    <![endif]-->
</head>
<body class="">
<style>
    .navbar-brand img {
        max-height: 50px;
    }
    .errorss {
	padding: 15px;
	margin-bottom: 20px;
	border: 1px solid transparent;
	border-radius: 4px;
	color: #a94442;
	background-color: #f2dede;
	border-color: #ebccd1;

}
    html {
        height: 100%;
        width: 100%;
        padding: 0;
        margin: 0;
        background: url(/assets/images/icon/anhmanlogin.png) center center no-repeat;;
        -webkit-background-size: cover;
        -moz-background-size: cover;
        -o-background-size: cover;
        background-size: 100%;
        background-position: center;
        background-color:#034c84 !important;
    }


</style>
<div class="forgot-grid">
                       </div>
<section id="content" class="m-t-lg wrapper-md animated fadeInUp" >
    <div class="container aside-xxl">
        <img src="<%=request.getContextPath()%>/assets/images/VanTai-VN.png" class="m-r-sm" style="display: block;margin-left: auto;margin-right: auto;width: 39%;">
        <a class="navbar-brand block" style="color:white">VẬN TẢI TỐC ĐỘ VIỆT NAM</a>
        <%--<a class="navbar-brand block" href="index.html"><img src="#" class="img-rounded"></a>--%>
        <section class="panel panel-default bg-white m-t-lg">
            <header class="panel-heading text-center" style="font-size: 24px;">
                <spring:message code="label.login.title"/>
            </header>
            <form id="frmLogin" onsubmit="return checkLogin()" action="<c:url value='/j_spring_security_check'/>"  class="panel-body wrapper-lg" method="post"
                  class="form-group ">
                <c:if test="${not empty error}">
                        <div class="errorss">
                <spring:message code="${error}"/>
                       </div>
                </c:if>
                <div class="errorss hidden" id="formerror">
                    <spring:message code="label.system.authority.account.notcorrect"/>
                </div>
                <div class="errorss hidden" id="formerrorcaptcha">
                    <spring:message code="lable.confirm.captcha"/>
                </div>
                <div class="form-group">
                    <label class="control-label" style="font-size: 16px;"><spring:message code="label.login.input.username"/></label>
                    <input id="username" name="username" class="form-control input-lg"/>
                </div>
                <div class="form-group">
                    <label class="control-label" style="font-size: 16px;"><spring:message code="label.login.input.password"/></label>
                    <input type="password" id="password" name="password" autocomplete="off" class="form-control input-lg"/>
                </div>

                <div class="form-group hidden" id="captcha">
                    <label class="control-label" style="font-size: 16px;">Captcha</label>
                    <input id="code_captcha" name="code_captcha"
                           class="form-control input-lg">
                    <img src="<%=request.getContextPath()%>/owner/captcha.html" style="max-width: 30%;" id="imgCaptcha">
                    <a href="javascript:void(0)" onclick="changeCaptcha();">
                        <span style="font-size: 25px;" class="fa fa-refresh"></span>
                    </a>
                </div>
                <div class="checkbox">
                    <label>
                        <input type="checkbox" id="rememberme" name="remember-me" style="margin-top: 2px;"> <spring:message code="label.login.remember"/>
                    </label>
                </div>


                <button id="btnLogin" class="btn bg-primary" style="font-size: 24px;width:100%;"><spring:message code="label.login.title"/></button>
                <div class="line line-dashed"></div>
            </form>
        </section>
    </div>
</section>

<!-- footer -->
<footer id="footer">
    <div class="text-center padder">
        <p>
            <small>VTTD VN &copy; 2019</small>
        </p>
    </div>
</footer>
<!-- / footer -->
<script src="<%=request.getContextPath()%>/assets/note/js/jquery.min.js"></script>
<!-- Bootstrap -->
<script src="<%=request.getContextPath()%>/assets/note/js/bootstrap.js"></script>
<!-- App -->
<script src="<%=request.getContextPath()%>/assets/note/js/app.js"></script>
<script src="<%=request.getContextPath()%>/assets/note/js/slimscroll/jquery.slimscroll.min.js"></script>
<script src="<%=request.getContextPath()%>/assets/note/js/app.plugin.js"></script>
<script>
    var preUrl = '<%=request.getContextPath()%>';
    var count = 0;
    $(document).ready(function () {
        if(sessionStorage.getItem("checkcaptcha")=='true'){
            $("#captcha").removeClass("hidden");
            sessionStorage.setItem("checkcaptcha",true);
        }
    });

    function checkLogin() {

        var formData = $("#frmLogin").serialize();
        var _uri = "<%=request.getContextPath()%>";
        if ($('#username').val().length === 0 || $('#username').val() == null) {
            $("#formerror").removeClass("hidden");
            changeCaptcha();
            return false;
        } else if ($('#password').val().length === 0 || $('#password').val() == null) {
            $("#formerror").removeClass("hidden");
            changeCaptcha();
            return false;
        } else {

            if (count >= 6 || sessionStorage.getItem("checkcaptcha")=='true') {
                if($("#code_captcha").val().length === 0 || $('#code_captcha').val() == null){
                    count++;
                    changeCaptcha();
                    $("#captcha").removeClass("hidden");
                    sessionStorage.setItem("checkcaptcha",true);
                    $("#formerrorcaptcha").removeClass("hidden");
                    $("#formerror").addClass("hidden");

                    return false;
                } else {
                    $.ajax({
                        url: preUrl + "/owner/checkLogin",
                        type: "POST",
                        data: formData,
                        dataType: "JSON",
                        success: function (response) {
                            switch (Number(response)) {
                                case 0:
                                    count++;
                                    changeCaptcha();
                                    $("#formerror").removeClass("hidden");
                                    $("#formerrorcaptcha").addClass("hidden");
                                    return false;
                                case 1:
                                    sessionStorage.clear();
                                    document.getElementById("frmLogin").submit();
                                    $("#formerror").addClass("hidden");
                                case 2:
                                    count++;
                                    $("#formerrorcaptcha").removeClass("hidden");
                                    $("#formerror").addClass("hidden");
                                    changeCaptcha();
                                    return false;
                            }
                        },
                        error: function (data) {
                            alert("Có lỗi trong quá trình xử lý, vui lòng thử lại");
                        }
                    });
                    return false;
                }
            } else {
                if(count >= 5 || sessionStorage.getItem("checkcaptcha")=='true'){
                    $("#captcha").removeClass("hidden");
                    sessionStorage.setItem("checkcaptcha",true);
                }
                $.ajax({
                    url: preUrl + "/owner/checkLogin",
                    type: "POST",
                    data: formData,
                    dataType: "JSON",
                    success: function (response) {
                        switch (Number(response)) {
                            case 0:
                                count++;
                                $("#formerror").removeClass("hidden");
                                return false;
                            case 1:
                                sessionStorage.clear();
                                document.getElementById("frmLogin").submit();
                                $("#formerror").addClass("hidden");
                        }
                    },
                    error: function (data) {
                        alert("Có lỗi trong quá trình xử lý, vui lòng thử lại");
                    }
                });
                return false;
            }



    }}
    function changeCaptcha(){

            $("#imgCaptcha").attr("src", preUrl + "/owner/captcha.html?u=" + new Date());

    }

</script>
</body>
</html>