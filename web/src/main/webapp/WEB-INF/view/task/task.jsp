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
    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <!--<link rel="stylesheet" href="/static/plugins/datetimepicker/css/bootstrap-datetimepicker.min.csss">-->
    <!--<link rel="stylesheet" href="/static/plugins/datepicker/datepicker3.css">-->

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
                    <h3 class="box-title">计划任务</h3>

                    <div class="box-tools pull-right">
                        <button class="btn btn-success btn-sm" id="addTaskBtn"><i class="fa fa-plus"></i> 新增任务</button>
                        <button class="btn btn-primary btn-sm" id="showAllBtn"><i class="fa fa-eye"></i> 显示所有任务</button>
                    </div>
                </div>
                <div class="box-body">

                    <ul class="todo-list">
                        <c:forEach items="${page.list}" var="task">
                        <li class="${task.done == 1 ? 'done' : ''}" rel="${task.id}">
                            <input type="checkbox" class="checkBtn">
                            <span class="text">${task.title}</span>

                            <c:if test="${not empty task.cid}">
                            <a href=""><i class="fa fa-user-o"></i> ${task.customer.customerName}</a>
                            </c:if>
                            <c:if test="${not empty task.sid}">
                            <a href=""><i class="fa fa-money"></i> ${task.saleChance.name}</a>
                            </c:if>
                            <small class="label ${task.overTime ? 'label-danger' : 'label-success'} "><i class="fa fa-clock-o"></i> <fmt:formatDate value="${task.finishTime}"/></small>
                            <div class="tools">
                                <i class="fa fa-edit"></i>
                                <i class="fa fa-trash-o"></i>
                            </div>
                        </li>
                        </c:forEach>
                    </ul>
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



<script>
    $(function () {

        var taskList = $(".done");
        taskList.hide();

        $("#showAllBtn").click(function () {
            taskList.show();
        });


        
        $(".checkBtn").click(function () {
            var doneTaskId = $(this).parent().attr("rel");
            $.post("/task/done?taskId="+doneTaskId).done(function (json) {
                if(json.state == "success"){
                    window.history.go(0);
                }
            }).error(function () {
                layer.msg("服务器异常");
            });
        });
        
        $(".fa-edit").click(function () {
            var taskId = $(this).parent().parent().attr("rel");
            window.location.href = "/task/toEdit?taskId="+taskId;
        });
        
        
        $(".fa-trash-o").click(function () {
            var taskId = $(this).parent().parent().attr("rel");
            layer.confirm("确定删除此提醒吗？",function (index) {
                layer.close(index);
                $.post("/task/delete/"+taskId).done(function (json) {
                    if(json.state == "success"){
                        window.history.go(0);
                    }
                }).error(function () {
                    layer.msg("服务器异常");
                });
            });

        });
        
        $("#addTaskBtn").click(function () {
            window.location.href = "/task/add";
        });

//        var picker = $("#datepicker").datepicker({
//            format:"yyyy-MM-dd",
//            language:'zh:CN',
//            autoclose:true,
//            todayHighlight: true,
//            startDate:moment().format("yyyy-MM-dd")
//        });
//        picker.on("changeDate",function (e) {
//            var today = moment().format("YYYY-MM-DD");
//            $('#datepicker2').datetimepicker('setStartDate',today);
//            $('#datepicker2').datetimepicker('setEndDate', e.format('yyyy-mm-dd'));
//        });
//
//
//        var timepicker = $('#datepicker2').datetimepicker({
//            format: "yyyy-mm-dd hh:ii",
//            language: "zh-CN",
//            autoclose: true,
//            todayHighlight: true
//        });


//        $("#saveTaskBtn").click(function () {
//            $("#taskForm").submit();
//        });
//
////  ***************  模态框表单验证  *****************
//        $("#taskForm").validate({
//            errorClass:"text-danger",
//            errorElement:"span",
//            rules:{
//                title:{
//                    required:true
//                },
//                finishTime:{
//                    required:true
//                }
//            },
//            messages:{
//                title:{
//                    required:"请输入标题"
//                },
//                finishTime:{
//                    required:"请选择最终时间"
//                }
//            },
//            submitHandler:function (form) {
////                var title = $("#myTitle").val();
////                var finishTime = $("#datepicker").val();
//                $.post("/task/add",$(form).serialize()).done(function (json) {
//                    if(json.state == "success"){
//                        $("#addTaskModel").modal('hide');
//                        history.go(0);
//                    }
//
//                }).error(function () {
//                    layer.msg("服务器异常，请稍后再试...");
//                });
//            }
//        });


        //**********************************************
//        $("#saveTaskBtn").click(function () {
//            $("#taskForm").submit();
//        });
//
////  ***************  模态框表单验证  *****************
//        $("#addTaskModel").validate({
//            errorClass:"text-danger",
//            errorElement:"span",
//            rules:{
//                title:{
//                    required:true
//                },
////                finishTime:{
////                    required:true,
////                },
////                remindTime:{
////                    required:true
////                },
//            },
//            messages:{
//                title:{
//                    required:"请输入待办标题"
//                },
////                finishTime:{
////                    required:"请输入最终时间"
////                },
////                remindTime:{
////                    required:"请输入提醒时间"
////                },
//            },
//            submitHandler:function () {
//                var title = $("#myTitle").val();
//                var finishTime = $("#datepicker").val();
//
//
//                $.post("/task/add",{"title":title}).done(function (json) {
//                    if(json.state == "success"){
//                        $("#addTaskModel").modal('hide');
//                        layer.msg(json.message);
//                        window.history.go(0);
//                    }else{
//                        $("#addTaskModel").modal('hide');
//                        layer.msg(json.message, {icon: 5});
//                    }
//
//                }).error(function () {
//                    layer.msg("服务器异常，请稍后再试...");
//                });
//            }
//        });




//        $("taskForm").validate({
//            errorClass:"text-danger",
//            errorElement:"span",
//            rules:{
//                title:{
//                    required:true
//                },
//                finishTime:{
//                    required:true
//                },
//                remindTime:{
//                    required:true
//                }
//            },
//            messages:{
//                title:{
//                    required:"请输入待办标题"
//                },
//                finishTime:{
//                    required:"请输入最终时间"
//                },
//                remindTime:{
//                    required:"请输入提醒时间"
//                }
//            },
//            submitHandler:function () {
//                $.post("/task/add").done(function (json) {
//                    if(json.state == "success"){
//                        window.history.go(0);
//                    }else {
//                        layer.msg("操作失败");
//                    }
//                }).error(function () {
//                    layer.msg("服务器异常");
//                });
//            }
//        });


//        $("#saveTaskBtn").click(function () {
//            $("#addTaskModel").modal('hide');
//            var title = $("#myTitle").val();
//            $.post("/task/add",{"title":title}).done(function (json) {
//                if(json.state == "success"){
//                    window.history.go(0);
//                }else {
//                    layer.msg("操作失败");
//                }
//            }).error(function () {
//                layer.msg("服务器异常");
//            });
//        });

//        $("#addTaskBtn").click(function () {
//            $("#addTaskModel").modal({
//                show:true,
//                backdrop:'static'
//            });
//        });

    });
</script>

</body>
</html>
