<%@ page isELIgnored="false" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<script>var id=<sec:authentication property="principal.id" /></script>
<script src="<%=request.getContextPath()%>/assets/project/history/my.history.js"></script>
<section id="content"  ng-app="FrameworkBase"  ng-controller="frameworkCtrl">
    <section class="hbox stretch">
        <!-- .aside -->
        <aside>
            <section class="vbox">
                <header class="header bg-white b-b b-light">
                    <p>Timeline</p>
                </header>
                <section class="scrollable wrapper" >
                    <div class="timeline">
                        <article class="timeline-item active" >
                            <div class="timeline-caption">
                                <div class="panel bg-primary lter no-borders">
                                    <div class="panel-body">
                                        <span class="timeline-icon"><i class="fa fa-bell-o time-icon bg-primary"></i></span>
                                        <span class="timeline-date"><spring:message code="label.history.timelineuser"/></span>
                                        <h5>
                                           <span><spring:message code="label.login.input.username"/>: ${user.username}</span>
                                            <spring:message code="label.user.fullname"/>: ${user.fullName}
                                        </h5>
                                    </div>
                                </div>
                            </div>
                        </article>
                        <div class="timeline" ng-repeat="item in list.items track by $index">
                            <article class="timeline-item" ng-if="$index%2!=0">
                                <div class="timeline-caption">
                                    <div class="panel panel-default">
                                        <div class="panel-body">
                                            <span class="arrow left"></span>
                                            <span class="timeline-icon"><i class="fa fa-pencil-square-o time-icon bg-info"></i></span>
                                            <span class="timeline-date">{{item.genDate|date:'dd/MM/yyyy HH:mm:ss'}}</span>
                                            <h5>
                                                <span>{{item.module}}</span>
                                                {{item.actions}}
                                            </h5>
                                            <p>IP: {{item.ip}}</p>
                                        </div>
                                    </div>
                                </div>
                            </article>
                            <article class="timeline-item alt" ng-if="$index%2==0">
                                <div class="timeline-caption" >
                                    <div class="panel panel-default">
                                        <div class="panel-body">
                                            <span class="arrow right"></span>
                                            <span class="timeline-icon"><i class="fa fa-pencil-square-o time-icon bg-info"></i></span>
                                            <span class="timeline-date">{{item.genDate|date:'dd/MM/yyyy HH:mm:ss'}}</span>
                                            <h5>
                                                <span>{{item.module}}</span>
                                                {{item.actions}}
                                            </h5>
                                            <p>IP: {{item.ip}}</p>
                                        </div>
                                    </div>
                                </div>
                            </article>
                            <div ng-show="loadContinue" ng-if="$index+1==list.items.length" class="timeline-footer"><a href="javascript:void(0)" ng-click="loadPage(page.pageNumber+1)"><i class="fa fa-plus time-icon inline-block bg-dark"></i></a></div>
                        </div>
                    </div>
                </section>
            </section>
        </aside>
        <!-- /.aside -->

    </section>
    <a href="#" class="hide nav-off-screen-block" data-toggle="class:nav-off-screen, open" data-target="#nav,html"></a>
</section>