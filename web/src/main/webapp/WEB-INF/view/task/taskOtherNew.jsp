<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
    <link rel="stylesheet" href="/static/plugins/datetimepicker/css/bootstrap-datetimepicker.min.css">
    <link rel="stylesheet" href="/static/plugins/datepicker/datepicker3.css">

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
        <jsp:param name="menu" value="task"/>
    </jsp:include>

    <!-- =============================================== -->

    <!-- 右侧内容部分 -->
    <div class="content-wrapper">

        <!-- Main content -->
        <section class="content">

            <!-- Default box -->
            <div class="box">
                <div class="box-header with-border">
                    <h3 class="box-title">新增待办任务</h3>

                    <div class="box-tools pull-right">
                        <button type="button" class="btn btn-box-tool">
                            <i class="fa fa-plus"></i> 返回列表
                        </button>
                    </div>
                </div>
                <div class="box-body">
                    <form action="/task/otherAdd" method="post" id="addTaskForm">
                        <div class="form-group">
                            <label>任务名称</label>
                            <input type="hidden" name="cid" value="${cid}">
                            <input type="hidden" name="sid" value="${sid}">
                            <input type="text" class="form-control" name="title">
                        </div>
                        <div class="form-group">
                            <label>完成日期</label>
                            <input type="text" class="form-control" id="datepicker" name="finishTime">
                        </div>
                        <div class="form-group">
                            <label>提醒时间</label>
                            <input type="text" class="form-control" id="datepicker2" name="remindTime">
                        </div>
                    </form>
                </div>
                <!-- /.box-body -->
                <div class="box-footer">
                    <button class="btn btn-primary" id="saveTaskBtn">保存</button>
                </div>
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

<!-- jQuery 2.2.3 -->
<%@include file="../include/js.jsp"%>
<script src="/static/plugins/layer/layer.js"></script>


<script src="/static/plugins/datetimepicker/js/bootstrap-datetimepicker.min.js"></script>
<script src="/static/plugins/datetimepicker/js/locales/bootstrap-datetimepicker.zh-CN.js"></script>
<script src="/static/plugins/moment/moment.js"></script>
<script src="/static/plugins/datepicker/bootstrap-datepicker.js"></script>
<script src="/static/plugins/datepicker/locales/bootstrap-datepicker.zh-CN.js"></script>
<script src="/static/plugins/validate/jquery.validate.min.js"></script>
<script>

    $("#saveTaskBtn").click(function () {
        $("#addTaskForm").submit();
    });

    //  ***************  模态框表单验证  *****************
    $("#addTaskForm").validate({
        errorClass:"text-danger",
        errorElement:"span",
        rules:{
            title:{
                required:true
            },
            finishTime:{
                required:true
            }

        },
        messages:{
            title:{
                required:"请输入标题"
            },
            finishTime:{
                required:"请选择最终时间s"
            }
        }
    });




    //****************************
    $(function () {

        var picker = $('#datepicker').datepicker({
            format: "yyyy-mm-dd",
            language: "zh-CN",
            autoclose: true,
            todayHighlight: true,
            startDate:moment().format("yyyy-MM-dd")
        });
        picker.on("changeDate",function (e) {
            var today = moment().format("YYYY-MM-DD");
            $('#datepicker2').datetimepicker('setStartDate',today);
            $('#datepicker2').datetimepicker('setEndDate', e.format('yyyy-mm-dd'));
        });


        var timepicker = $('#datepicker2').datetimepicker({
            format: "yyyy-mm-dd hh:ii",
            language: "zh-CN",
            autoclose: true,
            todayHighlight: true
        });
    });
</script>
</body>
</html>
