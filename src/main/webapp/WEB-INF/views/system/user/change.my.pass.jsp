<%@ page isELIgnored="false" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<script src="<%=request.getContextPath()%>/assets/project/user/change.my.pass.js"></script>
<section id="content" ng-app="FrameworkBase"  ng-controller="frameworkCtrl">
    <label ng-hide="true" id="detrong"><spring:message code="label.validate.isempty"/> </label>
    <label ng-hide="true" id="comfirmpassword"><spring:message code="label.validate.reppassword"/> </label>
    <label ng-hide="true" id="checkoldpass"><spring:message code="label.validate.checkoldpass"/> </label>
    <label ng-hide="true" id="changsuccess"><spring:message code="label.validate.doi.matkhau.thanhcong"/> </label>
    <label ng-hide="true" id="passwordvalidate"><spring:message code="label.validate.passwordvalidate"/> </label>

    <section class="vbox">
        <section class="scrollable padder" style="background: white">
            <ul class="bg-primary breadcrumb no-border no-radius b-b b-light pull-in">
                    <li><a href="<%=request.getContextPath()%>/"><i class="fa fa-home"></i>&nbsp;<spring:message code="label.system.home"></spring:message></a></li>
                <li><a href="#"><spring:message code="label.user"/></a></li>
                <li><a href="javascript:void(0)"><spring:message code="label.button.changepassword"/></a></li>
            </ul>
            <!--            <div class="m-b-md">
                            <h3 class="m-b-none">Đổi mật khẩu</h3><br/>
                        </div>-->
            <div class="col-sm-12">
                <div class="col-sm-3"></div>

                <section class="panel panel-default col-sm-6 no-padder" style="margin-top: 50px;">
                    <header class="panel-heading"><h4><i class="fa fa-edit"></i><spring:message code="label.user.change.infor.password"/></h4></header>
                    <div class=" panel-body">
                        <form method="post"  theme="simple"  enctype="multipart/form-data" class="form-horizontal" cssStyle="" validate="true">
                            <div class="form-group">
                                <label class="col-sm-3 control-label" style="line-height: 30px"><spring:message code="label.password.old"/> <span style="color: red">(*)</span></label>
                                <div class="col-sm-9">
                                    <div class="input-group m-b">
                                        <span class="input-group-addon">
                                            <i class="fa fa-lock" aria-hidden="true"></i>
                                        </span>
                                        <input type="password" ng-model="passwordCurrent" maxlength="50"  style="width:100%;"
                                               placeholder="<spring:message code="label.password.old"/> " class="form-control"/>
                                    </div>
                                    <span style="color:red">{{messageCurrent}}</span>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label" style="line-height: 30px"><spring:message code="label.new.password"/> <span style="color: red">(*)</span></label>
                                <div class="col-sm-9">
                                    <div class="input-group m-b">
                                        <span class="input-group-addon">
                                            <i class="fa fa-lock"></i>
                                        </span>
                                        <input type="password" ng-model="passwordNew" placeholder="<spring:message code="label.new.password"/> " maxlength="50" style="width:100%;"
                                               class="form-control"/>
                                    </div>
                                    <span style="color:red">{{messageNew}}</span>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label"><spring:message code="label.retype.new.password"/> <span style="color: red">(*)</span></label>
                                <div class="col-sm-9">
                                    <div class="input-group m-b">
                                        <span class="input-group-addon">
                                            <i class="fa fa-lock"></i>
                                        </span>
                                        <input type="password" ng-model="confirmPassword" maxlength="50" style="width:100%;" placeholder="<spring:message code="label.retype.new.password"/>" class="form-control" />
                                    </div>
                                    <span style="color:red">{{messageConfirm}}</span>
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="col-sm-12 text-center">
                                    <a href="<%=request.getContextPath()%>/" class="btn btn-default btn-warning"><i class="fa fa-times"></i><spring:message code="message.modal.cancel"/></a>
                                    <a ng-click="change()"  class="btn btn-primary" id="btn-check"><i class="fa fa-save"></i><spring:message code="label.button.changepassword"/></a>
                                </div>
                            </div>
                        </form>

                    </div>
                </section>
                <div class="col-sm-3"></div>
            </div>
        </section>
    </section>
    <a href="#" class="hide nav-off-screen-block" data-toggle="class:nav-off-screen" data-target="#nav"></a>
    <div class="modal fade"  id="Message"  role="dialog" aria-labelledby="myModalLabel1" aria-hidden="true">
        <div class="modal-dialog" style="max-width: 500px;">
            <div class="modal-content"style="max-width: 500px;">
                <div class="modal-header" style="padding: 7px;">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h5 class="modal-title"><i class="fa fa-edit"></i> Đổi mật khẩu</h5>
                </div>

                <div class="modal-body"  style="padding: 10px;">
                    <div class="form-group">
                        <label class="control-label">{{messageStatus}}</label>
                    </div>

                </div>
                <div class="modal-footer" style="padding: 10px;" >
                    <button type="button" class="btn btn-default" data-dismiss="modal">OK</button>
                </div>

            </div>
        </div>
    </div>
</section>

