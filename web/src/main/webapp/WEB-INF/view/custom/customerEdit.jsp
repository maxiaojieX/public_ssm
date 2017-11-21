<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>凯盛软件CRM-修改客户</title>
    <!-- Tell the browser to be responsive to screen width -->
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    <!-- Bootstrap 3.3.6 -->
    <%@include file="../include/css.jsp"%>
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
    </jsp:include>========================================= -->

    <!-- 右侧内容部分 -->
    <div class="content-wrapper">
        <!-- Main content -->
        <section class="content">

            <!-- Default box -->
            <div class="box">
                <div class="box-header with-border">
                    <h3 class="box-title">修改客户</h3>
                    <div class="box-tools pull-right">
                        <a href="/customer" class="btn btn-primary btn-sm"><i class="fa fa-arrow-left"></i> 返回列表</a>
                    </div>
                </div>
                <div class="box-body">
                    <form id="customerEditForm" method="post" action="/customer/upadte">
                        <input type="hidden" name="accountId" value="${accountId}">
                        <input type="hidden" name="id" value="${customer.id}">
                        <div class="form-group">
                            <label>姓名</label>
                            <input type="text" class="form-control" name="customerName" value="${customer.customerName}">
                        </div>
                        <div class="form-group">
                            <label>性别</label><br>
                            <label class="radio-inline">
                                <input type="radio" name="sex" id="inlineRadio2" ${customer.sex == '先生' ? 'checked' : ''} value="先生"> 先生
                            </label>
                            <label class="radio-inline">
                                <input type="radio" name="sex" id="inlineRadio3" ${customer.sex == '女士' ? 'checked' : ''} value="女士"> 女士
                            </label>
                        </div>
                        <div class="form-group">
                            <label>职位</label>
                            <input type="text" class="form-control" name="trade" value="${customer.trade}">
                        </div>
                        <div class="form-group">
                            <label>联系方式</label>
                            <input type="text" class="form-control" name="customPhone" value="${customer.customPhone}">
                        </div>
                        <div class="form-group">
                            <label>地址</label>
                            <input type="text" class="form-control" name="address" value="${customer.address}">
                        </div>
                        <div class="form-group">
                            <label>客户来源</label>
                            <select name="source" class="form-control">
                                <option value="">--选择来源--</option>
                                <c:forEach items="${customerSource}" var="customersource">
                                    <option value="${customersource.source}" ${customersource.source == customer.source ? 'selected' : ''}>${customersource.source}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="form-group">
                            <label>级别</label>
                            <select class="form-control" name="level">
                                <option value="">--选择级别--</option>
                                <option value="1" ${customer.level == 1 ? 'selected' : ''}>★</option>
                                <option value="2" ${customer.level == 2 ? 'selected' : ''}>★★</option>
                                <option value="3" ${customer.level == 3 ? 'selected' : ''}>★★★</option>
                                <option value="4" ${customer.level == 4 ? 'selected' : ''}>★★★★</option>
                                <option value="5" ${customer.level == 5 ? 'selected' : ''}>★★★★★</option>
                            </select>
                        </div>
                        <div class="form-group">
                            <label>备注</label>
                            <input type="text" class="form-control" name="mark" value="${customer.mark}">
                        </div>
                    </form>
                </div>
                <div class="box-footer">
                    <button class="btn btn-primary" id="editCustomerBtn">保存</button>
                </div>
                <!-- /.box-body -->
            </div>
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

<%@include file="../include/js.jsp"%>
<script src="/static/plugins/layer/layer.js"></script>
<script src="/static/plugins/validate/jquery.validate.min.js"></script>
<script>
    $(function () {

        $("#editCustomerBtn").click(function () {
            $("#customerEditForm").submit();
        });

        $("#customerEditForm").validate({
            errorClass:"text-danger",
            errorElement:"span",
            rules:{
                customerName:{
                    required:true
                },
                customPhone:{
                    required:true
                }

            },
            messages:{
                customerName:{
                    required:"请输入客户姓名"
                },
                customPhone:{
                    required:"请输入客户联系方式"
                }

            }
        });



    });
</script>
</body>
</html>
