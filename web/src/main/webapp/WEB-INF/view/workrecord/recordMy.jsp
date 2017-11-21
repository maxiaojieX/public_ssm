<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>凯盛软件CRM-我的销售记录</title>
    <!-- Tell the browser to be responsive to screen width -->
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    <!-- Bootstrap 3.3.6 -->
    <%@include file="../include/css.jsp"%>
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

            <!-- Default box -->
            <div class="box">
                <div class="box-header with-border">
                    <h3 class="box-title">我的销售机会</h3>

                    <div class="box-tools pull-right">
                        <button type="button" class="btn btn-box-tool" id="addChanceBtn">
                            <i class="fa fa-plus"></i> 添加机会
                        </button>
                    </div>
                </div>
                <div class="box-body no-padding">
                    <table class="table table-hover">
                        <thead>
                        <tr>
                            <td>机会名称</td>
                            <td>关联客户</td>
                            <td>机会价值</td>
                            <td>当前进度</td>
                            <td>最后跟进时间</td>
                            <td>
                                #
                            </td>
                        </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${page.list}" var="salechance">
                            <tr class="tr" rel="${salechance.id}">
                                <td>${salechance.name}</td>
                                <td>${salechance.other}</td>
                                <td>${salechance.worth}</td>
                                <td>${salechance.process}</td>
                                <td><fmt:formatDate value="${salechance.genjinTime}" pattern="yyyy年MM月dd日 HH时mm分"/></td>
                                <td>
                                    #
                                </td>
                            </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
                <!-- /.box-body -->
            </div>
            <!-- /.box -->

        </section>
        <!-- /.content -->
    </div>
    <!-- /.content-wrapper -->

    <%--模态框--%>
    <div class="modal fade" tabindex="-1" role="dialog" id="addChanceModel">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title">添加销售机会</h4>
                </div>
                <form action="/record/addChance" method="post" id="addChanceForm">
                    <div class="form-group">
                        <label>机会名称</label>
                        <input type="text" class="form-control" name="name">
                    </div>
                    <div class="form-group">
                        <label>关联客户</label>
                        <div id="customerWho"></div>

                        <select name="cid"  class="form-control">
                            <c:forEach items="${customerList}" var="customer">
                            <option value="${customer.id}">${customer.customerName}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="form-group">
                        <label>机会价值</label>
                        <input type="text" class="form-control" name="worth">
                    </div>
                    <div class="form-group">
                        <label>当前进度</label>
                        <select name="process" class="form-control">
                            <option value="初访">初访</option>
                            <option value="意向">意向</option>
                            <option value="报价">报价</option>
                            <option value="成交">成交</option>
                            <option value="暂时搁置">暂时搁置</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label>详细内容</label>
                        <textarea name="content" class="form-control" ></textarea>
                    </div>
                </form>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                    <button type="button" class="btn btn-primary" id="saveChanceBtn">保存</button>
                </div>
            </div><!-- /.modal-content -->
        </div><!-- /.modal-dialog -->
    </div><!-- /.modal --



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
        $(".tr").click(function () {
            var recordId = $(this).attr("rel");
            window.location.href = "/record/details?recordId="+recordId;
        });




//        提交新增表单
        $("#saveChanceBtn").click(function () {
            $("#addChanceForm").submit();
        });


        //模态框显示
        $("#addChanceBtn").click(function () {
            $("#addChanceModel").modal({
                show:true,
                backdrop:'static'
            });
        });


    });
</script>
</body>
</html>
