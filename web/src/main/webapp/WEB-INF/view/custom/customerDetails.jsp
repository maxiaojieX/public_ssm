<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>凯盛软件CRM-首页</title>
    <!-- Tell the browser to be responsive to screen width -->
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    <!-- Bootstrap 3.3.6 -->
    <%@include file="../include/css.jsp"%>
    
    <style>
        .td_title {
            font-weight: bold;
        }
    </style>
    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>
<body class="hold-transition skin-blue sidebar-mini">
<!-- Site wrapper -->
<div class="wrapper">

    <!-- 顶部导航栏部分 -->
    <%@include file="../include/header.jsp"%>
    <!-- =============================================== -->

    <!-- 左侧菜单栏 -->
    <jsp:include page="../include/sider.jsp">
        <jsp:param name="menu" value="customer_my"/>
    </jsp:include>

    <!-- =============================================== -->

    <!-- 右侧内容部分 -->
    <div class="content-wrapper">
        <!-- Main content -->
        <section class="content">

            <div class="box">
                <div class="box-header with-border">
                    <h3 class="box-title">客户资料</h3>
                    <div class="box-tools">
                        <a href="/customer" class="btn btn-primary btn-sm"><i class="fa fa-arrow-left"></i> 返回列表</a>
                        <a href="/customer/edit/${customer.id}" class="btn bg-purple btn-sm"><i class="fa fa-pencil"></i> 编辑</a>
                        <button id="transToOthers" class="btn bg-orange btn-sm"><i class="fa fa-exchange"></i> 转交他人</button>
                        <button id="putCustomerToArea" class="btn bg-maroon btn-sm"><i class="fa fa-recycle"></i> 放入公海</button>
                        <button id="deleteCustomerBtn" class="btn btn-danger btn-sm"><i class="fa fa-trash-o"></i> 删除</button>
                    </div>
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
                            <td class="td_title">所属行业</td>
                            <td>${customer.trade}</td>
                            <td class="td_title">客户来源</td>
                            <td>${customer.source}</td>
                            <td class="td_title">级别</td>
                            <td><c:forEach begin="0" end="${customer.level}">★</c:forEach></td>
                        </tr>
                        <c:if test="${not empty customer.address}">
                        <tr>
                            <td class="td_title">地址</td>
                            <td colspan="5">${customer.address}</td>
                        </tr>
                        </c:if>
                        <c:if test="${not empty customer.mark}">
                        <tr>
                            <td class="td_title">备注</td>
                            <td colspan="5">${customer.mark}</td>
                        </tr>
                        </c:if>
                    </table>
                </div>
                <div class="box-footer">
                    <span style="color: #ccc" class="pull-right">
                        <span title="<fmt:formatDate value="${customer.createTime}"/>">创建日期：<fmt:formatDate value="${customer.createTime}" pattern="MM月dd日"/></span> &nbsp;&nbsp;&nbsp;&nbsp;
                        <span title="<fmt:formatDate value="${customer.updateTime}"/>">最后修改日期：<fmt:formatDate value="${customer.updateTime}" pattern="MM月dd日"/></span></span>
                </div>
            </div>

            <div class="row">
                <div class="col-md-8">
                    <div class="box">
                        <div class="box-header with-border">
                            <h3 class="box-title">销售机会</h3>
                        </div>
                        <div class="box-body">
                            <ul class="list-group">
                                <c:forEach items="${saleChanceList}" var="chance">
                                <li class="list-group-item"><a href="/record/details?recordId=${chance.id}">${chance.name}</a></li>
                                </c:forEach>
                            </ul>
                        </div>
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="box">
                        <div class="box-header with-border">
                            <h3 class="box-title">日程安排</h3>　　　　　　　　　　　　　　　　<small><button id="otherSaveTaskBtn" class="btn btn-success btn-xs"><i class="fa fa-plus"></i></button></small>
                        </div>
                        <div class="box-body">
                            暂无安排!
                        </div>
                    </div>
                    <div class="box">
                        <div class="box-header with-border">
                            <h3 class="box-title">相关资料</h3>
                        </div>
                        <div class="box-body">
                            暂无相关资料!
                        </div>
                    </div>
                </div>
            </div>

        </section>
        <!-- /.content -->
    </div>
    <!-- /.content-wrapper -->

    <!-- 底部 -->
    <footer class="main-footer">
        <div class="pull-right hidden-xs">
            <b>Version</b> 1.0
        </div>
        <strong>Copyright &copy; 2010 -2017 <a href="http://almsaeedstudio.com">凯盛软件</a>.</strong> All rights
        reserved.
    </footer>

</div>
<!-- ./wrapper -->

<%@include file="../include/js.jsp"%>
<script src="/static/plugins/layer/layer.js"></script>
<script>
    $(function () {
        
        $("#otherSaveTaskBtn").click(function () {
            var cid = ${customer.id};
            window.location.href = "/task/toOtherAdd?cid="+cid;
        });
        
        $("#transToOthers").click(function () {
            layer.tips('请选择您想转交给谁？' +
                '<select name="toTrans"  id="toTrans" class="form-control">' +
                <c:forEach items="${accountList}" var="account">
                <c:if test="${account.id != customer.accountId}">
                '<option value="${account.id}">${account.username}</option>' +
                </c:if>
                </c:forEach>
                '</select>' +
                '<br>'+
                '<button class="btn btn-danger" id="yesTrans">确定</button>　' +
                '<button class="btn btn-default" id="noTrans">取消</button>', '#transToOthers', {
                tips: [1, '#ff8400'],
                time: 0
            });
        });
        //给吸附元素绑定委托事件
        $(document).delegate("#yesTrans","click",function () {
            layer.closeAll('tips');
            var toAccountId = $("#toTrans").val();
            window.location.href = "/customer/trans/${customer.id}/to/"+toAccountId;
        });
        $(document).delegate("#noTrans","click",function () {
            layer.closeAll('tips');
        });


        $("#putCustomerToArea").click(function () {
            layer.tips('确定把此人流放公海？' +
                '<br>'+
                '<br>'+
                '<button class="btn btn-danger" id="yesPublic">确定</button>　' +
                '<button class="btn btn-default" id="noPublic">取消</button>' +
                '', '#putCustomerToArea', {
                tips: [1, '#d81b5d'],
                time: 0
            });
        });
        //给吸附元素绑定委托事件
        $(document).delegate("#yesPublic","click",function () {
            layer.closeAll('tips');
            window.location.href = "/customer/public/${customer.id}";
        });
        $(document).delegate("#noPublic","click",function () {
            layer.closeAll('tips');
        });
        
        
        var message = null;
        message = "${message}";
        if(message){
            layer.msg(message,{icon: 1});
        };
        
        
        <%--$("#deleteCustomerBtn").click(function () {--%>
            <%--layer.confirm("确定要删除此人吗？",function (index) {--%>
                <%--layer.close(index);--%>
                <%--window.location.href = "/customer/delete/${customer.id}";--%>
            <%--})--%>
        <%--});--%>
        $("#deleteCustomerBtn").click(function () {
            layer.tips('确定要删除此人吗？' +
                '<br>'+
                '<br>'+
                '<button class="btn btn-default" id="yesDelete">确定</button>　' +
                '<button class="btn btn-danger" id="noDelete">取消</button>' +
                '', '#deleteCustomerBtn', {
                tips: [1, '#d94b33'],
                time: 0
            });
        });
        $(document).delegate("#yesDelete","click",function () {
            layer.closeAll('tips');
            window.location.href = "/customer/delete/${customer.id}";
        });
        $(document).delegate("#noDelete","click",function () {
            layer.closeAll('tips');
        });
        
        
    });
</script>
</body>
</html>
