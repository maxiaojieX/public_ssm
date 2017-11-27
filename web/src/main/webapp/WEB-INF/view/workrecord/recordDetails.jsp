<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <title>凯盛软件CRM-销售机会详情</title>
    <!-- Tell the browser to be responsive to screen width -->
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    <!-- Bootstrap 3.3.6 -->
    <%@include file="../include/css.jsp"%>
    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
    <style>
        .td_title {
            font-weight: bold;
        }
        .star {
            font-size: 20px;
            color: #ff7400;
        }
    </style>
</head>
<body class="hold-transition skin-blue sidebar-mini">
<!-- Site wrapper -->
<div class="wrapper">
    <!-- 顶部导航栏部分 -->
    <%@include file="../include/header.jsp"%>
    <!-- =============================================== -->

    <!-- 左侧菜单栏 -->
    <jsp:include page="../include/sider.jsp">
        <jsp:param name="menu" value="record_my"/>
    </jsp:include>
    <!-- =============================================== -->

    <!-- 右侧内容部分 -->
    <div class="content-wrapper">

        <!-- Main content -->
        <section class="content">
            <div class="box">
                <div class="box-header with-border">
                    <h3 class="box-title">销售机会基本资料</h3>
                    <div class="box-tools">
                        <a href="/record/my" class="btn btn-primary btn-sm"><i class="fa fa-arrow-left"></i> 返回列表</a>
                        <button id="delBtn" class="btn btn-danger btn-sm"><i class="fa fa-trash-o"></i> 删除</button>
                    </div>
                </div>
                <div class="box-body no-padding">
                    <table class="table">
                        <tr>
                            <td class="td_title">机会名称</td>
                            <td>${salechance.name}</td>
                            <td class="td_title">价值</td>
                            <td>${salechance.worth} </td>
                            <td class="td_title">当前进度</td>
                            <td>
                                ${salechance.process}
                                <button class="btn btn-xs btn-success" id="showChangeProgressModalBtn"><i class="fa fa-pencil"></i></button>
                            </td>
                        </tr>
                    </table>
                </div>
                <div class="box-footer">
                    <span style="color: #ccc" class="pull-right">
                        <fmt:formatDate value="${salechance.createTime}" pattern="yyyy年MM月dd日 HH时mm分"/>
                    </span>
                </div>
            </div>

            <div class="box">
                <div class="box-header with-border">
                    <h3 class="box-title">关联客户资料</h3>
                </div>
                <div class="box-body no-padding">
                    <table class="table">
                        <tr>
                            <td class="td_title">姓名</td>
                            <td>${customer.customerName}</td>
                            <td class="td_title">职位</td>
                            <td>${customer.trade}</td>
                            <td class="td_title">联系电话</td>
                            <td>${customer.customPhone}</td>
                        </tr>
                        <tr>
                            <td class="td_title">客户来源</td>
                            <td>${customer.source}</td>
                            <td class="td_title">级别</td>
                            <td class="star"><c:forEach begin="0" end="${customer.level}">★</c:forEach> </td>
                        </tr>

                        <tr>
                            <td class="td_title">地址</td>
                            <td colspan="5">${customer.address}</td>
                        </tr>


                        <tr>
                            <td class="td_title">备注</td>
                            <td colspan="5">${customer.mark}</td>
                        </tr>

                    </table>
                </div>
            </div>

            <div class="row">
                <div class="col-md-8">
                    <h4>跟进记录
                        <small><button id="showRecordModalBtn" class="btn btn-success btn-xs"><i class="fa fa-plus"></i></button></small>
                    </h4>
                    <ul class="timeline">
                        <c:if test="${empty genJinList}">
                        <li>
                            <!-- timeline icon -->
                            <i class="fa fa-circle-o bg-red"></i>
                            <div class="timeline-item">
                                <div class="timeline-body">
                                    暂无跟进记录
                                </div>
                            </div>
                        </li>
                        </c:if>
                        <%--<li>--%>
                            <%--<!-- timeline icon -->--%>
                            <%--<i class="fa fa-check bg-green"></i>--%>
                            <%--<div class="timeline-item">--%>
                                <%--<span class="time"><i class="fa fa-clock-o"></i> <fmt:formatDate value="${record.createTime}"/></span>--%>
                                <%--<div class="timeline-body">--%>
                                    <%--将当前进度修改为 [成交]--%>
                                <%--</div>--%>
                            <%--</div>--%>
                        <%--</li>--%>

                        <%--<li>--%>
                            <%--<!-- timeline icon -->--%>
                            <%--<i class="fa fa-close bg-red"></i>--%>
                            <%--<div class="timeline-item">--%>
                                <%--<span class="time"><i class="fa fa-clock-o"></i> <fmt:formatDate value="${record.createTime}"/></span>--%>
                                <%--<div class="timeline-body">--%>
                                    <%--将当前进度修改为 [暂时搁置]--%>
                                <%--</div>--%>
                            <%--</div>--%>
                        <%--</li>--%>
                        <c:forEach items="${genJinList}" var="genjin">
                            <li>
                                <!-- timeline icon -->
                                <i class="fa fa-info bg-blue"></i>
                                <div class="timeline-item">
                                    <span class="time"><i class="fa fa-clock-o"></i> <fmt:formatDate value="${genjin.createTime}"/></span>
                                    <div class="timeline-body">
                                        ${genjin.content}
                                    </div>
                                </div>
                            </li>
                        </c:forEach>
                    </ul>
                </div>
                <div class="col-md-4">
                    <div class="box">
                        <div class="box-header with-border">
                            <h3 class="box-title">日程安排</h3>　　　　　　　　　　　　　　　　<small><button id="otherSaveTaskBtn" class="btn btn-success btn-xs"><i class="fa fa-plus"></i></button></small>
                        </div>
                        <div class="box-body">
                            暂无安排！
                        </div>
                    </div>
                    <div class="box">
                        <div class="box-header with-border">
                            <h3 class="box-title">相关资料</h3>
                        </div>
                        <div class="box-body">
                            暂无相关资料！
                        </div>
                    </div>
                </div>
            </div>

            <div class="modal fade" id="recordModal">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span></button>
                            <h4 class="modal-title">添加跟进记录</h4>
                        </div>
                        <div class="modal-body">
                            <form action="/sales/my/new/record" id="recordForm" method="post">
                                <input type="hidden" name="sid" value="${salechance.id}">
                                <textarea id="recordContent"  class="form-control" name="content"></textarea>
                            </form>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                            <button type="button" class="btn btn-primary" id="saveGenjinBtn">保存</button>
                        </div>
                    </div><!-- /.modal-content -->
                </div><!-- /.modal-dialog -->
            </div><!-- /.modal -->



        </section>
        <!-- /.content -->
    </div>
    <!-- /.content-wrapper -->


</div>
<!-- ./wrapper -->

<%@include file="../include/js.jsp"%>
<script src="/static/plugins/layer/layer.js"></script>
<script>
    $(function () {

        $("#otherSaveTaskBtn").click(function () {
            var sid = ${salechance.id};
            window.location.href = "/task/toOtherAdd?sid="+sid;
        });
        
        $("#showRecordModalBtn").click(function () {
            $("#recordModal").modal({
                show:true,
                backdrop:'static'
            });
        });
        $("#saveGenjinBtn").click(function () {
            $("#recordModal").modal('hide');
            $.post("/record/saveGenjin",$("#recordForm").serialize()).done(function (json) {
                if(json.state == "success"){
                    window.history.go(0);
                }else {
                    layer.msg("操作失败!");
                }
            }).error(function () {
                layer.msg("服务器异常，请稍后再试!");
            });

        });



        $("#delBtn").click(function () {
            layer.tips('确定删除吗？' +
                '<br>' +
                '<button class="btn btn-default" id="yesDeleteChance">确定</button>　' +
                '<button class="btn btn-danger" id="noDeleteChance">取消</button>', '#delBtn', {
                tips: [1, '#d94b33'],
                time: 0
            });
        });
        $(document).delegate("#yesDeleteChance","click",function () {
            layer.closeAll('tips');
            var chanceId = ${salechance.id};
            window.location.href = "/record/delete?chanceId="+chanceId;
        });
        $(document).delegate("#noDeleteChance","click",function () {
            layer.closeAll('tips');
        });



        $("#showChangeProgressModalBtn").click(function () {
            layer.tips('选择进度' +
                '<select name="toTrans"  id="toTrans" class="form-control">' +
                '<option value="初访">初访</option>' +
                '<option value="意向">意向</option>' +
                '<option value="议价">议价</option>' +
                '<option value="成交">成交</option>' +
                '<option value="暂时搁置">暂时搁置</option>' +
                '</select>' +
                '<br>'+
                '<button class="btn btn-primary" id="yesProcess">确定</button>　' +
                '<button class="btn btn-default" id="noProcess">取消</button>', '#showChangeProgressModalBtn', {
                tips: [1, '#03b15c'],
                time: 0
            });
        });
        $(document).delegate("#yesProcess","click",function () {
            layer.closeAll('tips');
            var process = $("#toTrans").val();
            $.post("/record/updateProcess",{"process" : process,"chanceId" : ${salechance.id}}).done(function (json) {
                if(json.state == "success"){
                    window.history.go(0);
                }else{
                    layer.msg("操作失败s");
                }
            }).error(function () {
                layer.msg("服务器异常，请稍后再试");
            });
        });
        $(document).delegate("#noProcess","click",function () {
            layer.closeAll('tips');
        });

    });
</script>
</body>
</html>
