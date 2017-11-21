<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>公司微信</title>
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    <link rel="stylesheet" href="/static/bootstrap/css/bootstrap.min.css">
    <!-- Font Awesome -->
    <link rel="stylesheet" href="/static/plugins/font-awesome/css/font-awesome.min.css">
    <link rel="stylesheet" href="/static/plugins/webuploader/webuploader.css">
</head>
<body>

<nav class="navbar navbar-inverse navbar-static-top">
    <div class="container">
        <div class="btn btn-info navbar-btn"><i class="fa fa-cloud-upload" aria-hidden="true"></i>多人聊天</div>
    </div>
</nav>
<div class="container">
    <div id="message" style="width: auto;height: 100px">

    </div>
    <br>
    <form id="messageForm" method="post" action="/chat/message">
        <textarea id="messageContent" class="form-control" rows="3"></textarea>
    </form>
    <button type="button" id="sendMessageBtn" class="btn btn-success">发送</button>

</div>





<script src="/static/plugins/jQuery/jquery-2.2.3.min.js"></script>
<script src="/static/plugins/layer/layer.js"></script>
<script src="/static/plugins/art-template/art-template.js"></script>
<script src="/static/plugins/moment/moment.js"></script>
<script src="/static/plugins/webuploader/webuploader.min.js"></script>

<script>

    var accountId= ${accountId};

    var ws = new WebSocket("ws://192.168.1.104:8888");

    ws.onopen = function () {
        //存入在线表
        $.post("/chat/saveOnline?accountId="+accountId).done(function (json) {
            if(json.state == "success"){
                layer.msg("加入群聊...");
            }
        }).error(function () {
            layer.msg("您的网络好像有点慢...");
        });

    }


    ws.onmessage = function (json) {
        var newMessage = json.data;
        layer.msg(newMessage);
        var html = "<span>" + newMessage+ "</span><br>";
        $(html).appendTo("#message");

    }
    
    //*********************
    $("#sendMessageBtn").click(function () {
        var imessage = $("#messageContent").val();
        var websocketMessage = accountId+"#"+imessage;
        ws.send(websocketMessage);
    });



</script>
</body>
</html>