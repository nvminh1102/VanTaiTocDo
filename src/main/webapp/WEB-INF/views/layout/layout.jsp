    <%@ page import="java.util.Random" %>
        <%@ page isELIgnored="false" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<!DOCTYPE html>
<html lang="en" class="app">
<head>
    <meta charset="utf-8"/>
    <title><tiles:insertAttribute name="title"/></title>
    <link rel="shortcut icon" type="image/x-icon" href="<%=request.getContextPath()%>/assets/images/icon.png" />
    <meta name="description" content="san sim"/>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1"/>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">

    <%!
        public void randomCache(String urlCache, HttpServletRequest request) {
            request.setAttribute("urlCache",urlCache);
        }
    %>
    <%
        /*clear cache chrome */
        Random rand = new Random();
        float numberCache = rand.nextInt(99999999);
        String urlCache = "?updated=" + numberCache;
        randomCache(urlCache,request);
    %>

    <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/note/css/bootstrap.css" type="text/css" />
    <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/note/css/animate.css" type="text/css" />
    <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/note/css/font-awesome.min.css" type="text/css" />
    <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/note/css/font.css" type="text/css" cache="false"/>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/note/js/datatables/datatables.css" type="text/css"/>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/note/js/select2/select2.css" type="text/css"/>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/note/js/select2/theme.css" type="text/css"/>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/note/js/fuelux/fuelux.css" type="text/css" />
    <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/note/js/slider/slider.css" type="text/css" />
    <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/note/css/bootstrap-datetimepicker.css" type="text/css" />
    <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/note/css/style.css${urlCache}" type="text/css"/>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/note/css/app.css" type="text/css"/>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/project/angularjs/autocomplete/autocomplete.css" type="text/css"/>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/note/css/jquery.flipcountdown.css" >
    <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/note/css/bootstrap-dialog.min.css" type="text/css" />
    <%--<[if lt IE 9]>--%>
    <script src="<%=request.getContextPath()%>/assets/note/js/ie/html5shiv.js" cache="false"></script>
    <script src="<%=request.getContextPath()%>/assets/note/js/ie/respond.min.js" cache="false"></script>
    <script src="<%=request.getContextPath()%>/assets/note/js/ie/excanvas.js" cache="false"></script>
    <%--<[endif]>--%>

    <script src="<%=request.getContextPath()%>/assets/note/js/jquery.min.js"></script>
    <script src="<%=request.getContextPath()%>/assets/note/js/bootstrap.js"></script>
    <script src="<%=request.getContextPath()%>/assets/note/js/app.js"></script>
    <script src="<%=request.getContextPath()%>/assets/note/js/app.plugin.js"></script>
    <script src="<%=request.getContextPath()%>/assets/note/js/slimscroll/jquery.slimscroll.min.js"></script>
    <script src="<%=request.getContextPath()%>/assets/note/js/datatables/jquery.dataTables.min.js"></script>
    <script src="<%=request.getContextPath()%>/assets/note/js/select2/select2.min.js"></script>
    <script src="<%=request.getContextPath()%>/assets/note/js/fuelux/fuelux.js"></script>
    <script src="<%=request.getContextPath()%>/assets/note/js/slider/bootstrap-slider.js"></script>
    <script src="<%=request.getContextPath()%>/assets/note/js/libs/moment.min.js"></script>
    <script src="<%=request.getContextPath()%>/assets/note/js/combodate/combodate.js"></script>
    <script src="<%=request.getContextPath()%>/assets/note/js/parsley/parsley.js" ></script>
    <script src="<%=request.getContextPath()%>/assets/note/js/parsley/custum-parsley.js" ></script>

    <script src="<%=request.getContextPath()%>/assets/project/angularjs/angular.min.js"></script>
    <script src="<%=request.getContextPath()%>/assets/project/angularjs/angular-sanitize.min.js"></script>
    <script src="<%=request.getContextPath()%>/assets/project/common.js${urlCache}"></script>
    <script src="<%=request.getContextPath()%>/assets/project/CommonFunction.js"></script>
    <script src="<%=request.getContextPath()%>/assets/project/angularjs/autocomplete/autocomplete.js"></script>
    
    <script src="<%= request.getContextPath() %>/assets/js/jquery.flipcountdown.js"></script>
    <script src="<%=request.getContextPath()%>/assets/js/bootstrap-dialog.min.js"></script>
    <script src="<%= request.getContextPath()%>/assets/js/jquery.flipcountdown.js"></script>
    <script src="<%=request.getContextPath()%>/assets/js/bootstrap-dialog.min.js"></script>
    <script src="<%=request.getContextPath()%>/assets/js/moment-with-locales.js"></script>
    <script src="<%=request.getContextPath()%>/assets/js/bootstrap-datetimepicker.js"></script>

    <script src="<%=request.getContextPath()%>/assets/js/loading-indicator-view/loadingView.js"></script>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/js/ladda-bootstrap/dist/ladda-themeless.min.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/js/ladda-bootstrap/css/prism.css">
    <script src="<%=request.getContextPath()%>/assets/js/ladda-bootstrap/dist/spin.min.js"></script>
    <script src="<%=request.getContextPath()%>/assets/js/ladda-bootstrap/js/ladda.js"></script>
    <script src="<%=request.getContextPath()%>/assets/js/ladda-bootstrap/js/prism.js"></script>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/note/js/toastr/toastr.min.css" type="text/css" />
    <script src="<%= request.getContextPath() %>/assets/note/js/toastr/toastr.min.js"></script>
    <script src="<%= request.getContextPath() %>/assets/js/function.util.js"></script>

    <script>
        var preUrl='<%=request.getContextPath()%>';
    </script>
</head>
<body>
<section class="vbox">
    <tiles:insertAttribute name="header" />
    <section>
        <section class="hbox stretch">
            <tiles:insertAttribute name="leftpanel" />
            <tiles:insertAttribute name="page" />
        </section>
    </section>
    <%@include file="../popup.jsp"%>
</section>
</body>
</html>
<script>
    console.log("cache: " + '${urlCache}');
    $(document).ready(function() {
        var select2_ = $('.select2').length;
        if(select2_ > 0){
            $(".select2").select2();
            console.log("select2: " + select2_);
        }
    });
    toastr.options = {
        "positionClass": "toast-top-right"
    }
</script>
