<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>AdminLTE 2 | Blank Page</title>
    <!-- Tell the browser to be responsive to screen width -->
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    <!-- Bootstrap 3.3.6 -->
    <%@include file="../include/css.jsp"%>

    <link rel="stylesheet" href="/static/layim/css/layim.css">
    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
    <style>
        .ibox{
            width: 1100px;
            height: 355px;
            background-color: white;
            z-index: inherit;
            overflow: auto;
        }
        .sendbox{
            width: 1100px;
            height: 150px;
            background-color: white;
            z-index: inherit;
            position: relative;
            top:-20px;
        }
        .centerbox{
            width: 1100px;
            height: 10px;
            background-color: #bdc3c7;
            z-index: inherit;
            position: relative;
            top: -20px;
        }
        .ibtn{
            position: relative;
            top: -70px;
            left: 990px;
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
        <jsp:param name="menu" value="home"/>
    </jsp:include>


    <!-- 右侧内容部分 -->
    <div class="content-wrapper">
        <section class="content">
            <div class="alert alert-info" role="alert" style="margin:0px ;"><center>公司群聊</center></div>
            <div class="ibox" style="padding: 5px 15px">
                <p class="text-muted">宇腾：我是智00障</p>
            </div>
            <div class="centerbox"></div>
            <div class="sendbox">
                <textarea class="form-control" rows="3"></textarea>
            </div>
            <button type="button" class="btn btn-default ibtn">发送</button>
        </section>
    </div>


</div>

<%@include file="../include/js.jsp"%>
<script src="/static/plugins/layer/layer.js"></script>
<script>

    var accountId= ${accountId};
    var accountName = "${accountName}";

    var ws = new WebSocket("ws://192.168.1.104:8888");
//    var ws = new WebSocket("ws://127.0.0.1:8888");
    ws.onopen = function () {
        //存入在线表
//        $.post("/chat/saveOnline?accountId="+accountId).done(function (json) {
//            if(json.state == "success"){
//                layer.msg("加入群聊...");
//            }
//        }).error(function () {
//            layer.msg("您的网络好像有点慢...");
//        });

    }


    ws.onmessage = function (json) {
        var newMessage = json.data;
        //layer.msg(newMessage);
        imessage = newMessage.substring(0,newMessage.lastIndexOf("#"));

        //layer.msg(imessage);
        var html = "<span>" + imessage+ "</span><br>";
        $(html).appendTo(".ibox");

    }

    //*********************
    $("#sendMessageBtn").click(function () {
        var imessage = $("#messageContent").val();
        var websocketMessage = imessage+"#"+accountName;
        ws.send(websocketMessage);
    });

    //测试




</script>


</body>
</html>
