<%@ page isELIgnored="false" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %> 
<%--<script  type="text/javascript">--%>
<%--$("#response-status").show();--%>
<%--setTimeout(function() { $("#response-status").hide(); }, 3000);--%>
<%--</script>--%>
<script src="<%=request.getContextPath()%>/assets/project/parameter/list.js"></script>
<section id="content" ng-app="FrameworkBase"  ng-controller="frameworkCtrl">
    <section class="vbox">
        <section class="scrollable padder" style="background: white">
            <ul class="bg-primary breadcrumb no-border no-radius b-b b-light pull-in">
                <li><a href="<%=request.getContextPath()%>/"><i class="fa fa-home"></i>&nbsp;<spring:message code="label.system.home"/></a></li>
                <li><a href="#"><spring:message code="label.system"/></a></li>
                <li><a href="javascript:void(0)"><spring:message code="label.system.parameter"/></a></li>
            </ul>
            <div class="m-b-md">
                <h3 class="m-b-none" id="response-status" style="color: #009900">
                    <c:if test="${success.length()>0}"><span style="color:red"><spring:message code="${success}"/></c:if>
                    <c:if test="${messageError.length()>0}"><span style="color:red"><spring:message code="${messageError}"/></span></c:if>
                    </h3>
                </div>

                <section class="panel panel-default">
                    <div class="panel-body">
                        <form cssClass="form-inline padder" action="list" role="form" theme="simple">

                            <div class="form-group">
                                <div class="col-md-6">
                                    <div class="col-sm-8">
                                        <input name="paramKey"  ng-model="paramKey" my-enter="search()"  placeholder="<spring:message code='label.system.parameter.key'/>" maxlength="50"  class="form-control"/>
                                </div>
                                <div class="col-sm-4"><a  ng-click="search()" class="btn btn-info"><i class="fa fa-search"></i> <spring:message code="label.button.search"/></a></div>
                            </div>
                        </div>
                        <div class="line line-dashed line-lg pull-in" style="clear:both ;border-top:0px"></div>

                    </form>
                </div>
            </section>
            <div class="col-sm-12 no-padder" style="padding-bottom: 10px !important;">
                <button type="button" class="btn btn-success custom-width" data-toggle="modal" data-target="#updateParamModal" ng-click="onAddParam();" ><i class="fa fa-plus"></i> <spring:message code="label.button.add"/></button>
            </div>

            <div class="table-responsive table-overflow-x-fix">
                <table id="tbl" class="table table-striped b-t m-b-none">
                    <thead>
                        <tr class="bg-info text" style="color: white;">
                            <th>#</th>
                            <th><spring:message code="label.system.parameter.key"/></th>
                            <th><spring:message code="label.system.parameter.value"/></th>
                            <th><spring:message code="label.system.parameter.description"/></th>
                            <th><spring:message code="label.system.parameter.timeCreate"/></th>
                            <th><spring:message code="label.system.parameter.timeModify"/></th>
                                <sec:authorize access="hasRole('ROLE_SYSTEM_PARAMETER_UPDATE')">
                                <th></th>
                                </sec:authorize>
                        </tr>
                    </thead>
                    <tbody>
                        <tr ng-repeat="item in page.items track by $index">
                            <td class="align-center">{{(page.pageNumber - 1) * page.numberPerPage + $index + 1}}</td>
                            <td class="align-center">{{item[1]}}</td>
                            <td class="align-center">{{item[2]}}</td>
                            <td class="align-center"> {{item[3]}}</td>
                            <td class="align-center">{{item[4]|date:'dd/MM/yyyy HH:mm:ss'}}</td>
                            <td class="align-center">{{item[5]|date:'dd/MM/yyyy HH:mm:ss'}}</td>
                            <sec:authorize access="hasRole('ROLE_SYSTEM_PARAMETER_UPDATE')">
                                <td class="text-right">
                                    <div class="col-sm-12 no-padder">
                                    <button type="button" data-toggle="modal" data-target="#updateParamModal" class="btn btn-success custom-width btn-sm font-bold" ng-click="onEditParam(item);" ><i class="fa fa-edit"></i> <spring:message code="label.button.edit"/></button>  
                                    <button type="button" class="btn btn-danger custom-width btn-sm font-bold" data-toggle="modal" data-target="#deleteParamModal" ng-click="onDeleteParam(item[0]);" ><i class="fa fa-times"></i> <spring:message code="label.button.delete"/></button>  
                                    </div>
                                </td>
                            </sec:authorize>
                        </tr>

                    </tbody>
                </table>
            </div>
            <footer class="panel-footer">
                <div class="row">
                    <div class="col-sm-12 text-right text-center-xs">
                        <div class="col-sm-6 text-left">
                            <span><spring:message code="label.page.total"/> <code>{{page.rowCount|currency:"":0}}</code> <spring:message code="label.page.items"/></span>
                        </div>
                        <div class="col-sm-6">
                            <ul class="pagination pagination-sm m-t-none m-b-none">
                                <li ng-if="page.pageNumber > 1"><a href="javascript:void(0)"  ng-click="loadPage(1)">«</a></li>
                                <li ng-repeat="item in page.pageList">
                                    <a href="javascript:void(0)" ng-if="item == page.pageNumber" style="color:mediumvioletred;"> {{item}}</a>
                                    <a href="javascript:void(0)" ng-if="item != page.pageNumber" ng-click="loadPage(item)"> {{item}}</a>
                                </li>
                                <li ng-if="page.pageNumber < page.pageCount" ><a href="javascript:void(0)" ng-click="loadPage(page.pageCount)">»</a></li>
                            </ul>
                        </div>
                    </div>
                </div>
            </footer>    

        </section>
    </section>
    <a href="#" class="hide nav-off-screen-block" data-toggle="class:nav-off-screen" data-target="#nav"></a>

    <div class="modal fade" id="updateParamModal" tabindex="-1" role="dialog" aria-labelledby="updateParamModalLabel" aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header alert-info">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title font-weight-bold" id="exampleModalLabel" ng-if="labelTitle == 'Add'"><spring:message code="label.modal.parameter.add"/></h4>
                    <h4 class="modal-title font-weight-bold" id="exampleModalLabel" ng-if="labelTitle == 'Edit'"><spring:message code="label.modal.parameter.edit"/></h4>
                </div>
                <div class="modal-body">
                    <!--<form enctype="multipart/form-data" class="form-horizontal" data-validate="parsley">-->
                        <div class="form-group">
                            <label for="recipient-name" class="col-form-label font-weight-bold"><spring:message code="label.system.parameter.key"/></label>
                            <input  type="hidden" ng-model="paramItem.id"/>
                            <div>
                                <input ng-if="labelTitle == 'Add'" ng-model="paramItem.key"  id="paramKeyUpdate" name="paramKeyUpdate" class="form-control" 
                                       placeholder="Mã tham số hệ thống" 
                                       maxlength="20"
                                       />
                                <input ng-if="labelTitle == 'Edit'" ng-model="paramItem.key" id="paramKeyUpdate" name="paramKeyUpdate" class="form-control" disabled
                                       placeholder="Mã tham số hệ thống" 
                                       maxlength="20"
                                       />
                            </div>
                            <span class="text-danger">{{paramKey_valid}}</span>
                        </div>
                        <div class="form-group">
                            <label for="message-text" class="col-form-label font-weight-bold"><spring:message code="label.system.parameter.value"/></label>
                            <div>
                                <input ng-model="paramItem.value" class="form-control" id="paramValueUpdate" name="paramValueUpdate"
                                       placeholder="Giá trị tham số"
                                       maxlength="100"
                                       />
                            </div>
                            <span class="text-danger">{{paramValue_valid}}</span>
                        </div>

                        <div class="form-group">
                            <label for="message-text" class="col-form-label font-weight-bold"><spring:message code="label.system.parameter.description"/></label>
                            <div>
                                <textarea ng-model="paramItem.description" id="paramDescriptionUpdate" name="paramDescriptionUpdate" class="form-control" placeholder="Mô tả" rows="5" maxlength="100" /></textarea>
                            </div>
                            <span class="text-danger">{{paramDescription_valid}}</span>
                        </div>
                    <!--</form>-->
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-primary" ng-click="addOrUpdateParam()" ng-if="labelTitle == 'Add'"><i class="fa fa-save"></i> <spring:message code="label.button.save"/></button>
                    <button type="button" class="btn btn-primary" ng-click="addOrUpdateParam()" ng-if="labelTitle == 'Edit'"><i class="fa fa-save"></i> <spring:message code="label.button.update"/></button>
                    <button type="button" class="btn btn-danger" data-dismiss="modal"><i class="fa fa-times"></i> <spring:message code="label.button.close"/></button>
                </div>
            </div>
        </div>
    </div>
    <div class="modal fade" id="deleteParamModal" tabindex="-1" role="dialog"
         aria-labelledby="deleteParamModal" aria-hidden="true" aria-label="Close">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header alert-info">
                    <button type="button" class="close" class="btn btn-default" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h4 class="modal-title"><spring:message code="label.modal.parameter.delete"/></h4>
                </div>
                <div class="modal-body">
                    <label><spring:message code="message.modal.parameter.delete"/></label>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-primary" data-dismiss="modal" ng-click="deleteParam()" style="text-transform: none;"><i class="fa fa-check"></i> <spring:message code="label.button.ok"/></button>
                    <button type="button" class="btn btn-danger" data-dismiss="modal"><i class="fa fa-times"></i> <spring:message code="label.button.close"/></button>
                </div>
            </div>
        </div>
    </div>            

</section>
