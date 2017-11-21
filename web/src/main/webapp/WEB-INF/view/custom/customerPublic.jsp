<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>凯盛软件CRM-公海客户</title>
    <!-- Tell the browser to be responsive to screen width -->
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    <!-- Bootstrap 3.3.6 -->
    <%@include file="../include/css.jsp"%>
    <style>
        .name-avatar {
            display: inline-block;
            width: 50px;
            height: 50px;
            background-color: #ccc;
            border-radius: 50%;
            text-align: center;
            line-height: 50px;
            font-size: 24px;
            color: #FFF;
        }
        .table>tbody>tr:hover {
            cursor: pointer;
        }
        .table>tbody>tr>td {
            vertical-align: middle;
        }
        .star {
            font-size: 20px;
            color: #ff7400;
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
        <jsp:param name="menu" value="customer_public"/>
    </jsp:include>
    <!-- =============================================== -->

    <!-- 右侧内容部分 -->
    <div class="content-wrapper">
        <!-- Main content -->
        <section class="content">

            <!-- Default box -->
            <div class="box">
                <div class="box-header with-border">
                    <h3 class="box-title">公海客户</h3>
                </div>
                <div class="box-body no-padding">
                    <table class="table table-hover">
                        <tbody>
                        <tr>
                            <th width="80"></th>
                            <th>姓名</th>
                            <th>职位</th>
                            <th>跟进时间</th>
                            <th>操作</th>
                        </tr>
                        <c:forEach items="${page.list}" var="customer">
                            <tr class="tr" rel="${customer.id}">
                                <td><span class="name-avatar ${customer.sex == '女士' ? 'pink' :''}">${fn:substring(customer.customerName,0,1)}</span></td>
                                <td>${customer.customerName}</td>
                                <td>${customer.trade}</td>
                                <td><fmt:formatDate value="${customer.lastChatTime}"/></td>
                                <td><button class="btn btn-default want">认领</button></td>
                            </tr>
                        </c:forEach>
                        <c:if test="${empty page.list}">
                            <tr>
                                <th width="80"></th>
                                <th>暂无数据！</th>
                                <th></th>
                                <th></th>
                                <th></th>
                            </tr>
                        </c:if>
                        </tbody>
                    </table>
                </div>
                <!-- /.box-body -->
            </div>
            <ul id="pagination-demo" class="pagination-sm"></ul>
            <!-- /.box -->
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

<!-- jQuery 2.2.3 -->
<%@include file="../include/js.jsp"%>
<script src="/static/plugins/layer/layer.js"></script>
<script src="/static/plugins/pageinfo/twbsPagination.min.js"></script>
<script>
    $(function () {

        $('#pagination-demo').twbsPagination({
            totalPages: ${page.pages},
            visiblePages: 5,
            first:'首页',
            last:'末页',
            prev:'上一页',
            next:'下一页',
            href:"/customer/public?p={{number}}"
        });
            
        $(".want").click(function () {
            var customerTr = $(this).parents(".tr");
            var customerId = customerTr.attr("rel");
            //layer.msg(customerId);
            $.post("/customer/getCustomer/"+customerId).done(function (json) {
                if(json.state == "success"){
                    customerTr.remove();
                    layer.msg(json.message);
                }else {
                    layer.msg(json.message);
                }
            }).error(function () {
                layer.msg("服务器异常，认领失败!",{icon:2});
            });



        });
        


    });
</script>
</body>
</html>

