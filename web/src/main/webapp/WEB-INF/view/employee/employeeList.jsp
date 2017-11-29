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
    <%@include file="../include/css.jsp"%>
    <link rel="stylesheet" href="/static/plugins/tree/css/metroStyle/metroStyle.css">
    <link rel="stylesheet" href="/static/plugins/datatables/jquery.dataTables.min.css">
    
    <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <style>
        #addDept{
            display: inline-block;
        }
        #delDept{
            display: inline-block;
        }

    </style>
</head>
<body class="hold-transition skin-blue sidebar-mini">
<!-- Site wrapper -->
<div class="wrapper">

    <%@include file="../include/header.jsp"%>
    <!-- =============================================== -->

    <!-- 左侧菜单栏 -->
    <jsp:include page="../include/sider.jsp">
        <jsp:param name="menu" value="list"/>
    </jsp:include>

    <!-- 右侧内容部分 -->
    <div class="content-wrapper">

        <!-- Main content -->
        <section class="content">

            <div class="row">
                <div class="col-md-2">
                    <div class="box" style="width: 185px">
                        <div class="box-body">
                            <button class="btn btn-default" id="addDept">添加部门</button>
                            <button class="btn btn-default" id="delDept" style="width: 78px">删除部门</button>
                            <ul id="ztree" class="ztree"></ul>
                        </div>
                    </div>
                </div>
                <div class="col-md-10">
                    <!-- Default box -->
                    <div class="box" >
                        <div class="box-header with-border">
                            <h3 class="box-title">员工管理</h3>
                            <div class="box-tools pull-right">

                                <a href="javascript:;" class="btn btn-box-tool"  title="Collapse" id="addAccount">
                                    <i class="fa fa-plus"></i> 添加员工</a>

                            </div>
                        </div>
                        <input type="hidden" id="deptId">
                        <div class="box-body">
                            <table class="table" id="dataTable">
                                <thead>
                                <tr>
                                    <th>ID</th>
                                    <th>姓名</th>
                                    <th>部门</th>
                                    <th>手机</th>
                                    <th>操作</th>
                                </tr>
                                </thead>
                            </table>
                        </div>
                    </div>
                    <!-- /.box -->
                </div>
            </div>

        </section>
        <!-- /.content -->
    </div>
    <!-- /.content-wrapper -->
    <%--模态框--%>
        <div class="modal fade" tabindex="-1" role="dialog" id="addAccountModel">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                        <h4 class="modal-title">添加员工</h4>
                    </div>
                    <form id="addAccountForm">
                        <div class="form-group">
                            <label>用户名</label>
                            <input type="text" class="form-control" name="username" autofocus>
                        </div>
                        <div class="form-group">
                            <label>手机号码</label>
                            <input type="text" class="form-control" name="phone">
                        </div>
                        <div class="form-group">
                            <label>密码(默认000000)</label>
                            <input type="password" class="form-control" value="000000" name="password">
                        </div>
                        <div class="form-group">
                            <label>部门</label><br>
                            <div id="checkDepts"></div>
                        </div>


                    </form>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                        <button type="button" class="btn btn-primary" id="addAccountBtn">保存</button>
                    </div>
                </div><!-- /.modal-content -->
            </div><!-- /.modal-dialog -->
        </div><!-- /.modal --




    <%@include file="../include/footer.jsp"%>

</div>
<!-- ./wrapper -->

<%@include file="../include/js.jsp"%>
<script src="/static/plugins/tree/js/jquery.ztree.all.min.js"></script>
<script src="/static/plugins/layer/layer.js"></script>
<script src="/static/plugins/datatables/jquery.dataTables.js"></script>
<script src="/static/plugins/validate/jquery.validate.min.js"></script>
<script>
    $(function(){
        var readyTree;

        $("#delDept").click(function () {
            if(readyTree == null) {
                layer.msg("你还未选择要删除的部门");
            }else if(readyTree == 1){
                layer.msg("不能删除根部门");
            }else{
                $.post("/account/delDept?id="+readyTree).done(function (json) {
                    if(json.state == "success"){
                        layer.msg(json.message);
//                        ****
                        var treeObj = $.fn.zTree.getZTreeObj("ztree");
                        treeObj.reAsyncChildNodes(null, "refresh");

                    }else{
                        layer.msg(json.message);
                    }
                }).error(function () {
                    layer.msg("服务器异常");
                });
            }
        });




        //************************************
        //    添加部门
        $("#addDept").click(function () {
            layer.prompt({title: '请输入部门名称', formType: 3}, function(text, index){
                layer.close(index);

                $.post("/dept/add",{"deptName":text}).done(function (json) {
                    //添加成功
                    if(json.state == "success"){
                        layer.msg(json.message,{icon: 1});
                        var treeObj = $.fn.zTree.getZTreeObj("ztree");
                        treeObj.reAsyncChildNodes(null, "refresh");
                    }else{
                        layer.msg(json.message,{icon: 2});
                    }
                }).error(function () {
                    layer.msg("服务器异常，添加失败",{icon: 2});
                });



            });
        });

// ******************  添加员工  ****************
        $("#addAccount").click(function () {
            $("#checkDepts").html("");
            $.get("/dept/dept.json").done(function (json) {

                for(var i = 0;i<json.length;i++){
                    var dept = json[i];
                    if(dept.id != 1){
                        var html = '<label class="checkbox-inline"><input type="checkbox" name="deptId" value="'+dept.id+'">'+dept.deptName+'</label>';
                        $("#checkDepts").append(html);
                    }
                }
                $("#addAccountModel").modal({
                    show:true,
                    backdrop:'static'
                });
            }).error(function () {
                layer.alert("获取部门失败");
            });
        });
        
        $("#addAccountBtn").click(function () {
            $("#addAccountForm").submit();
        });

//  ***************  模态框表单验证  *****************
        $("#addAccountForm").validate({
            errorClass:"text-danger",
            errorElement:"span",
            rules:{
                username:{
                    required:true
                },
                phone:{
                    required:true,
                    digits:true
                },
                password:{
                    required:true
                },
                deptId:{
                    required:true
                }
            },
            messages:{
                username:{
                    required:"请输入姓名"
                },
                phone:{
                    required:"请输入手机号",
                    digits:"非正确手机号码格式"
                },
                password:{
                    required:"请输入密码"
                },
                deptId:{
                    required:"请选择部门"
                }
            },
            submitHandler:function () {
                $.post("/account/add",$("#addAccountForm").serialize()).done(function (json) {
                    if(json.state == "success"){
                        $("#addAccountModel").modal('hide');
                        layer.msg(json.message);
                        dataTable.ajax.reload();
                    }else{
                        $("#addAccountModel").modal('hide');
                        layer.msg(json.message, {icon: 5});
                    }

                }).error(function () {
                    layer.msg("服务器异常，请稍后再试...");
                });
            }
        });

// *************  删除员工-事件委托机制  ***************


        $(document).delegate(".deleteAccount","click",function () {
            var id = $(this).attr("rel");
            layer.msg(id);
            layer.confirm('确定要删除吗？', {
                btn: ['删除','取消'] //按钮
            }, function(){
                $.post("/account/delete?id="+id).done(function (json) {
                    if(json.state == "success"){
                        dataTable.ajax.reload();
                        layer.msg(json.message, {icon: 1});
                    }else{
                        layer.msg(json.message, {icon: 5});
                    }

                }).error(function () {
                    
                });
            });


        });

//   ***************** table框架  *******************
        var dataTable = $("#dataTable").DataTable({
            "processing": true,
            "serverSide": true,
            "ajax": {
                "url" : "/account/getTable",
                "data" : function(data){
                    data.deptId = $("#deptId").val();
                }
            },
            "lengthChange": false,
            "pageLength": 25,
            "columns":[
                {"data":"id"},
                {"data":"username"},
                {"data":function(row){
                    var deptArray = row.deptList;
                    var str = "";
                    for(var i = 0;i < deptArray.length;i++) {
                        str += deptArray[i].deptName + " ";
                    }
                    return str;
                }},
                {"data":"phone"},
                {"data":function(row){
                    return "<a href='javascript:;' rel='"+row.id+"' class='deleteAccount'>删除</a>";
                }}
            ],
            "columnDefs": [
                {
                    "targets": [2,3,4],
                    "orderable": false
                },
                {
                    "targets": [0],
                    "visible": false
                }
            ],
            language:{
                "search":"账号:",
                "info": "显示从 _START_ 到 _END_ 条数据，共 _TOTAL_ 条",
                "infoEmpty":"没有任何数据",
                "emptyTable":"暂无数据",
                "processing":"加载中...",
                "paginate": {
                    "first":      "首页",
                    "last":       "末页",
                    "next":       "上一页",
                    "previous":   "下一页"
                }
            }
        });


//   **************** 左侧 zTree   ********
        var setting = {
            data: {
                simpleData: {
                    enable: true,
                    pIdKey:"pid"
                },
                key: {
                    name: "deptName"
                }
            },
            async:{
                enable:true,
                url:"/dept/dept.json",
                type:"get",
                dataFilter:ajaxDataFilter
            },
            callback: {
                onClick: function (event, treeId, treeNode, clickFlag) {
                    readyTree = treeNode.id;
                    if (treeNode.id != 1) {
                        $("#deptId").val(treeNode.id);
//                        刷新table
                        dataTable.ajax.reload();
                    }
                }
            }
        };
        function ajaxDataFilter(treeId, parentNode, responseData) {
            if (responseData) {
                for(var i =0; i < responseData.length; i++) {
                    if(responseData[i].id == 1) {
                        responseData[i].open = true;
                        break;
                    }
                }
            }
            return responseData;
        }

//        var zNodes =[
//            { id:1, pId:0, name:"凯盛软件", open:true},
//            { id:2, pId:1, name:"设计部"},
//            { id:3, pId:1, name:"开发部"},
//            { id:4, pId:1, name:"管理部"},
//            { id:5, pId:1, name:"财务部"}
//        ];
        $.fn.zTree.init($("#ztree"), setting);

    });
</script>
</body>
</html>