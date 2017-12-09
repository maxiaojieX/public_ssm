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
            padding: 5px 0px;
        }
        .centerbox{
            width: 1100px;
            height: 10px;
            background-color: #bdc3c7;
            z-index: inherit;
            position: relative;
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

            </div>
            <div class="centerbox"></div>
            <div class="sendbox">
                <textarea class="form-control" id="messageContent" rows="3" style="height: 130px"></textarea>
            </div>
            <button type="button" id="sendMessageBtn" class="btn btn-default ibtn">发送</button>
        </section>
    </div>


</div>

<%@include file="../include/js.jsp"%>
<script src="/static/plugins/layer/layer.js"></script>
<script>

    var accountId= ${accountId};
    var accountName = "${accountName}";

    var ws = new WebSocket("ws://192.168.1.105:8888");

    ws.onopen = function () {

    }
    /**
     * 接收消息
     * @param json
     */
    ws.onmessage = function (json) {
        var newMessage = json.data;

        var message1 = newMessage.substring(0,newMessage.lastIndexOf("#"));
        var message2 = newMessage.substring(newMessage.lastIndexOf("#")+1);

        var html = '<p class="text-muted">'+ message2 + ': '+ message1 +'</p>';
        $(html).appendTo(".ibox");

    }

    /**
     * 按钮发送消息
     */
    $("#sendMessageBtn").click(function () {
        var imessage = $("#messageContent").val();
        var websocketMessage = imessage+"#"+accountName;
        $("#messageContent").val("");
        ws.send(websocketMessage);
    });

    $(document).keypress(function(e) {
        var eCode = e.keyCode ? e.keyCode : e.which ? e.which : e.charCode;
        var imessage = $("#messageContent").val();

        if (eCode == 13 && imessage != null && imessage != ""){

            var websocketMessage = imessage+"#"+accountName;
            $("#messageContent").val("");
            ws.send(websocketMessage);

        }
    });

</script>


</body>
</html>
