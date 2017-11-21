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
    <title>凯盛软件CRM-首页</title>
    <!-- Tell the browser to be responsive to screen width -->
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    <!-- Bootstrap 3.3.6 -->
    <link rel="stylesheet" href="/static/bootstrap/css/bootstrap.min.css">
    <!-- Font Awesome -->
    <link rel="stylesheet" href="/static/plugins/font-awesome/css/font-awesome.min.css">
    <!-- Theme style -->
    <link rel="stylesheet" href="/static/dist/css/AdminLTE.min.css">
    <!-- AdminLTE Skins. Choose a skin from the css/skins
         folder instead of downloading all of them to reduce the load. -->
    <link rel="stylesheet" href="/static/dist/css/skins/_all-skins.min.css">
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
        .pink {
            background-color: pink;
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

            <!-- Default box -->
            <div class="box">
                <div class="box-header with-border">
                    <h3 class="box-title">我的客户</h3>
                    <div class="box-tools pull-right">
                        <a href="/customer/add" class="btn btn-success btn-sm" ><i class="fa fa-plus"></i> 新增客户</a>
                        <div class="btn-group">
                            <button type="button" class="btn btn-primary btn-sm dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                <i class="fa fa-file-excel-o"></i> 导出Excel <span class="caret"></span>
                            </button>
                            <ul class="dropdown-menu">
                                <li><a href="/customer/export/csv">导出为csv</a></li>
                                <li><a href="/customer/export/xls">导出为xls</a></li>
                            </ul>
                        </div>
                    </div>
                </div>
                <div class="box-body no-padding">
                    <table class="table table-hover">
                        <tbody>
                        <tr>
                            <th width="80"></th>
                            <th>姓名</th>
                            <th>职业</th>
                            <th>跟进时间</th>
                            <th>级别</th>
                            <th>联系方式</th>
                        </tr>
                        <c:forEach items="${page.list}" var="customer">
                            <tr class="tr" rel="${customer.id}">
                                <td><span class="name-avatar ${customer.sex == '女士' ? 'pink' :''}">${fn:substring(customer.customerName,0,1)}</span></td>
                                <td>${customer.customerName}</td>
                                <td>${customer.trade}</td>
                                <td><fmt:formatDate value="${customer.lastChatTime}"/></td>
                                <td class="star"><c:forEach begin="0" end="${customer.level}">★</c:forEach> </td>
                                <td><i class="fa fa-phone"></i> ${customer.customPhone} <br></td>
                            </tr>
                        </c:forEach>
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
<script src="/static/plugins/validate/jquery.validate.min.js"></script>
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
            href:"/customer?p={{number}}"
        });

        var message = null;
        message = "${message}";
        if(message){
            layer.msg(message,{icon: 1});
        };

        $(".tr").click(function () {
            var customerId = $(this).attr("rel");
            window.location.href = "/customer?customerId="+customerId;
        });



    });
</script>
</body>
</html>