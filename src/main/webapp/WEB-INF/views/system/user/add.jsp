<%@ page isELIgnored="false" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<section id="content">
    <section class="vbox">
        <section class="scrollable padder" style="background: white">
            <ul class="bg-primary breadcrumb no-border no-radius b-b b-light pull-in">
                <li><a href="<%=request.getContextPath()%>/"><i class="fa fa-home"></i>&nbsp;<spring:message code="label.system.home"/></a></li>
                <li><a href="#"><spring:message code="label.user"/></a></li>
                <li><a href="javascript:void(0)"><spring:message code="label.add.user"/></a></li>
            </ul>
            <%--<div class="m-b-md">--%>
                <%--<span style="color:red"><spring:message code="${messageError}"/></span>--%>
            <%--</div>--%>

            <section class="panel panel-default">
                <header class="panel-heading"><h4><spring:message code="label.add.user"/></h4></header>
                <div class="panel-body">
                    <form method="post" action="add"  enctype="multipart/form-data" class="form-horizontal" data-validate="parsley">
                        <div class="form-group">
                            <label class="col-sm-2 control-label" style="line-height: 30px"><spring:message code="label.login.input.username"/> <span style="color: red">*</span></label>
                            <div class="col-sm-10">
                                <div class="input-group m-b col-sm-4">
                                    <span class="input-group-addon">
                                        <i class="fa fa-user" aria-hidden="true"></i>
                                    </span>
                                    <input name="username" maxlength="100" style="width:100%;" value="${user.username}"
                                           placeholder="<spring:message code="label.login.input.username"/> " class="form-control"/>

                                </div>
                                <form:errors cssStyle="color: red" path="user.username" />
                            </div>
                        </div>
                        <div class="line line-dashed line-lg pull-in"></div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label" style="line-height: 30px"><spring:message code="label.login.input.password"/> <span style="color: red">*</span></label>
                            <div class="col-sm-10">
                                <div class="input-group m-b col-sm-4">
                                    <span class="input-group-addon">
                                        <i class="fa fa-lock"></i>

                                    </span>
                                    <input type="password" name="password" placeholder="<spring:message code="label.login.input.password"/>" maxlength="50" style="width:100%;" value="${user.password}"
                                           class="form-control"/>
                                </div>
                                <form:errors style="color: red" path="user.password" />
                            </div>
                        </div>
                        <div class="line line-dashed line-lg pull-in"></div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label"><spring:message code="label.user.comfirm.password"/><span style="color: red">*</span></label>
                            <div class="col-sm-10">
                                <div class="input-group m-b col-sm-4">
                                    <span class="input-group-addon">
                                        <i class="fa fa-lock"></i>
                                    </span>
                                    <input type="password" name="confirmPassword" maxlength="50" style="width:100%;" value="${confirmPassword}" placeholder="<spring:message code="label.user.comfirm.password"/>" class="form-control" />
                                </div>
                                <span style="color:red">${messagePassword}</span>
                            </div>
                        </div>
                        <div class="line line-dashed line-lg pull-in"></div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label" style="line-height: 30px"><spring:message code="label.user.fullname"></spring:message><span style="color: red">*</span></label>
                            <div class="col-sm-10">
                                <div class="input-group m-b col-sm-4">
                                    <span class="input-group-addon">
                                        <i class="fa fa-envelope" style="font-size: 11px" aria-hidden="true"></i>
                                    </span>
                                    <input name="fullName" placeholder="<spring:message code="label.login.input.username"></spring:message>" value="${user.fullName}" style="width:100%;" class="form-control" maxlength="50"/>
                                </div>
                                <form:errors style="color: red" path="user.fullName" />
                            </div>
                        </div>
<%--<!--                        <div class="line line-dashed line-lg pull-in"></div>--%>
                        <%--<div class="form-group">--%>
                            <%--<label class="col-sm-2 control-label" style="line-height: 30px">Số điện thoại <span style="color: red">*</span></label>--%>
                            <%--<div class="col-sm-10">--%>
                                <%--<div class="input-group m-b col-sm-4">--%>
                                    <%--<span class="input-group-addon">--%>
                                        <%--<i class="fa fa-envelope" style="font-size: 11px" aria-hidden="true"></i>--%>
                                    <%--</span>--%>
                                    <%--<input name="msisdn" placeholder="Số điện thoại" value="${user.msisdn}" style="width:100%;" class="form-control" maxlength="15"/>--%>
                                <%--</div>--%>
                                <%--<form:errors style="color: red" path="user.msisdn" />--%>
                            <%--</div>--%>
                        <%--</div>    -->--%>
                        <div class="line line-dashed line-lg pull-in"></div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label"><spring:message code="label.system.parameter.description"/></label>
                            <div class="col-sm-10">
                                <div class="input-group m-b col-sm-4">
                                    <span class="input-group-addon">
                                        <i class="fa fa-pencil" aria-hidden="true" style="font-size: 10px"></i>
                                    </span>
                                    <input name="description" value="${user.description}" maxlength="200" style="width:100%;" placeholder="<spring:message code="label.system.parameter.description"/>" class="form-control" />
                                </div>

                            </div>
                        </div>
                        <div class="line line-dashed line-lg pull-in" style="clear:both ;margin-bottom: 30px"></div>
                        <div class="form-group">
                            <div class="col-sm-2">

                            </div>
                            <div class="col-sm-4 ">
                                <a href="<%=request.getContextPath()%>/system/user/list" class="btn btn-danger"><i class="fa fa-times"></i><spring:message code="message.modal.cancel"/></a>
                                <button type="submit" data-loading-text="Thêm mới" class="btn btn-primary"><i class="fa fa-save"></i><spring:message code="label.button.add"/></button>
                            </div>

                        </div>
                    </form>

                </div>
            </section>
        </section>
        <!--<footer class="footer bg-white b-t b-light"><small>Sàn sim số &copy; 2018</small></footer>-->
    </section>
    <a href="#" class="hide nav-off-screen-block" data-toggle="class:nav-off-screen" data-target="#nav"></a>
</section>

<script>
    $(document).ready(function () {
//        $("#type").change(function () {
//            if (this.value == 0) {
//                var x = document.getElementById("storeId");
//                var option = document.createElement("option");
//                option.value = "0";
//                option.text = "- Đơn vị giao hàng -";
//                x.add(option);
//                $("#storeId").val("0");
//                $("#storeId").attr("disabled", "true");
//                var regionId = document.getElementById("region");
//                regionId.removeAttribute("disabled");
//                $("#region option[value='-1']").remove();
//                $("#localityIds").attr("disabled", "true");
//                $("#localityIds").empty();
//            } else {
//                var status = document.getElementById("storeId");
//                $("#storeId option[value='0']").remove();
//                status.removeAttribute("disabled");
//                var x = document.getElementById("region");
//                var option = document.createElement("option");
//                option.value = "-1";
//                option.text = "- Công ty khu vực -";
//                x.add(option);
//                $("#region").val("-1");
//                $("#region").attr("disabled", "true");
//
//                if (this.value == 2) {
//                    var regionId = document.getElementById("localityIds");
//                    regionId.removeAttribute("disabled");
//                    var regionid = $("#storeId").val();
//                    if (parseInt(regionid) > 0)
//                    {
//                        $.ajax({
//                            url: "<%=request.getContextPath()%>/mobishop/shipper/getprovincebystorechange.html",
//                            type: "get",
//                            mimeType: "text/html; charset=UTF-8",
//                            data: {regionid: regionid},
//                            success: function (data) {
//                                var decoded = decodeURIComponent(data);
//                                $("#localityIds").empty();
//                                $("#localityIds").append(decoded.replace(/\+/g, ' '));
//                            }
//                        });
//                    }
//                } else {
//                    $("#localityIds").attr("disabled", "true");
//                    $("#localityIds").empty();
//                }
//
//
//
//            }
//        });
//        if ($("#type").val() == 0) {
//            var x = document.getElementById("storeId");
//            var option = document.createElement("option");
//            option.value = "0";
//            option.text = "- Đơn vị giao hàng -";
//            x.add(option);
//            $("#storeId").val("0");
//            $("#storeId").attr("disabled", "true");
//            var regionId = document.getElementById("region");
//            regionId.removeAttribute("disabled");
//            $("#region option[value='-1']").remove();
//        } else {
//            var mobiShopId = document.getElementById("mobiShopId");
//            mobiShopId.removeAttribute("disabled");
//            $("#storeId option[value='0']").remove();
//            var x = document.getElementById("region");
//            var option = document.createElement("option");
//            option.value = "-1";
//            option.text = "- Công ty khu vực -";
//            x.add(option);
//            $("#region").val("-1");
//            $("#region").attr("disabled", "true");
//
//            if (this.value == 2) {
//                var regionId = document.getElementById("localityIds");
//                regionId.removeAttribute("disabled");
//                var regionid = $("#storeId").val();
//                if (parseInt(regionid) > 0)
//                {
//                    $.ajax({
//                        url: "<%=request.getContextPath()%>/mobishop/shipper/getprovincebystorechange.html",
//                        type: "get",
//                        mimeType: "text/html; charset=UTF-8",
//                        data: {regionid: regionid},
//                        success: function (data) {
//                            var decoded = decodeURIComponent(data);
//                            $("#localityIds").empty();
//                            $("#localityIds").append(decoded.replace(/\+/g, ' '));
//                        }
//                    });
//                }
//            } else {
//                $("#localityIds").attr("disabled", "true");
//                $("#localityIds").empty();
//            }
//
//
//        }

    });


//    function storechange()
//    {
//        if ($("#type").val() == 2) {
//
//            var regionId = document.getElementById("localityIds");
//            regionId.removeAttribute("disabled");
//            var regionid = $("#storeId").val();
//            if (parseInt(regionid) > 0)
//            {
//                $.ajax({
//                    url: "<%=request.getContextPath()%>/mobishop/shipper/getprovincebystorechange.html",
//                    type: "get",
//                    mimeType: "text/html; charset=UTF-8",
//                    data: {regionid: regionid},
//                    success: function (data) {
//                        var decoded = decodeURIComponent(data);
//                        $("#localityIds").empty();
//                        $("#localityIds").append(decoded.replace(/\+/g, ' '));
//                    }
//                });
//            }
//        } else {
//            $("#localityIds").attr("disabled", "true");
//            $("#localityIds").empty();
//        }
//    }
//    ;

</script>
