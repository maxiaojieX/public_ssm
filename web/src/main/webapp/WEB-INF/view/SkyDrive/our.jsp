<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>公司网盘</title>
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    <link rel="stylesheet" href="/static/bootstrap/css/bootstrap.min.css">
    <!-- Font Awesome -->
    <link rel="stylesheet" href="/static/plugins/font-awesome/css/font-awesome.min.css">
    <link rel="stylesheet" href="/static/plugins/webuploader/webuploader.css">
    <style>
        .textTitle{
            text-align:center
        }
        #backBtn{
            float: right;
        }
        .hoverButton{
            display: none;
        }
        .webuploader-pick {
            padding: 0px;
            overflow: visible;
            font-size: 14px;
            line-height:1.5;
            font-weight: 400;
        }

    </style>
</head>
<body>

<nav class="navbar navbar-inverse navbar-static-top">
    <div class="container">
        <div id="fileUploadBtn" class="btn btn-info navbar-btn"><i class="fa fa-cloud-upload" aria-hidden="true"></i>上传</div>
        <button type="button" id="createNewDirBtn" class="btn btn-success navbar-btn"><i class="fa fa-plus-square" aria-hidden="true"></i> 新建文件夹</button>
        <c:if test="${pid != 0}">
        <button type="button" id="backBtn" class="btn btn-default navbar-btn"><i class="fa fa-step-backward" aria-hidden="true"></i> 返回上一级</button>
        </c:if>
    </div>
</nav>
<div class="container">
    <div class="row" id="divRow">
        <c:forEach items="${diskList}" var="disk">
            <div class="col-xs-3 col-md-3">
                <a <c:if test="${disk.type == 'dir'}">href="/SkyDrive/our?pid=${disk.id}"</c:if> class="thumbnail ${disk.type == 'dir' ? '' : 'suerFile'}" rel="${disk.id}" isJpg="${disk.name}">
                    <img src="/static/pic/${disk.type}.png" alt="...">
                    <div class="textTitle">
                        <p class="hoverName">${disk.name}</p>
                    </div>
                </a>
            </div>
        </c:forEach>
        <c:if test="${empty diskList}">
            <div class="page-header">
                <h1>　　　　　　　空空如也... </h1>
            </div>
        </c:if>
    </div>
</div>




<script src="/static/plugins/jQuery/jquery-2.2.3.min.js"></script>
<script src="/static/plugins/layer/layer.js"></script>
<script src="/static/plugins/art-template/art-template.js"></script>
<script src="/static/plugins/moment/moment.js"></script>
<script src="/static/plugins/webuploader/webuploader.min.js"></script>

<script type="text/template" id="divTimplate">
    <%--<div class="col-xs-3 col-md-3">--%>
        <%--<a href="/SkyDrive/our?pid={{id}}" class="thumbnail">--%>
            <%--<img src="/static/pic/{{type}}.png" alt="...">--%>
            <%--<div class="textTitle">{{name}}</div>--%>
        <%--</a>--%>
    <%--</div>--%>
    <div class="col-xs-3 col-md-3">
        <a <? if(type == 'dir'){ ?>href="/SkyDrive/our?pid={{id}}"<? } ?> class="thumbnail ${disk.type == 'dir' ? '' : 'suerFile'}" rel="{{id}}" isJpg="{{name}}">
            <img src="/static/pic/{{type}}.png" alt="...">
            <div class="textTitle">
                <p class="hoverName">{{name}}</p>
            </div>
        </a>
    </div>
</script>
<script>
    $(function () {

        var downFileId;
        var isJgp;

        //下载文件
        $(document).delegate(".suerFile","click",function () {
            downFileId = $(this).attr("rel");
            isJgp = $(this).attr("isJpg");
            var houzui = isJgp.substring(isJgp.lastIndexOf("."));
            if(".jpg" == houzui){
                layer.tips(
                    '<button class="btn btn-default" id="yulan">预览</button>　' +
                    '<button class="btn btn-default" id="xiazai">下载</button>', this, {
                        tips: [1, '#ff9'],
                        time: 0
                    });
            }else{
                layer.tips(
                    '<button class="btn btn-default" id="xiazai">下载</button>', this, {
                        tips: [1, '#ff9'],
                        time: 0
                    });
            }
        });


        //给吸附元素绑定委托事件
        $(document).delegate("#yulan","click",function () {
            layer.closeAll('tips');
            //window.open("/SkyDrive/downLoad?diskId="+downFileId+"&fileName="+isJgp+"&yuLan=yes");
            window.open("/SkyDrive/qiniuDownLoad?diskId="+downFileId+"&fileName="+isJgp+"&yuLan=yes");
        });
        $(document).delegate("#xiazai","click",function () {
            layer.closeAll('tips');
            //window.open("/SkyDrive/downLoad?diskId="+downFileId+"&fileName="+isJgp);
            window.open("/SkyDrive/qiniuDownLoad?diskId="+downFileId+"&fileName="+isJgp);
        });




        var token = "${upToken}";
        var pid = ${pid};
        //文件上传
        var uploader = WebUploader.create({
            pick:"#fileUploadBtn",
            swf:'/static/plugins/webuploader/Uploader.swf',
            server:'http://upload-z1.qiniu.com/',
            auto:true,
            fileVal:'file',
            formData:{
//                "pid":pid
                "token":token
            }
        });

        var loadIndex = -1;
        //开始上传
        uploader.on('uploadStart',function (file) {
            loadIndex = layer.load(2);
        });
        //上传成功
        uploader.on('uploadSuccess',function (file,json) {
            var qiniuFileName = json.key;
            var fileRealName = file.name;
            var fileSize = file.size;

            $.post("/SkyDrive/qiniuUp",{"fileRealName":fileRealName,"pid":pid,"qiniuFileName":qiniuFileName,"fileSize":fileSize}).done(function (json) {
                if(json.state == "success"){
                layer.msg("上传成功");
                $("#divRow").html("");
                for(var i = 0;i<json.data.length;i++){
                    var disk = json.data[i];
                    var html = template("divTimplate",disk);
                    $("#divRow").append(html);
                }

            }
            }).error(function () {
                layer.msg("上传失败");
            });

        });
        uploader.on('uploadComplete',function (file) {
            layer.close(loadIndex);
        });





        //返回上一级
        $("#backBtn").click(function () {
            window.history.go(-1);
        });


        //创建文件夹
        $("#createNewDirBtn").click(function () {

            layer.prompt({title: '请输入文件夹名称', formType: 3}, function(text, index){
                layer.close(index);
                layer.msg(text);
                $.get("/SkyDrive/newDir",{"pid":pid,"name":text}).done(function (json) {
                    if(json.state == "success"){
                        layer.msg("创建成功");
                        $("#divRow").html("");
                        for(var i = 0;i<json.data.length;i++){
                            var disk = json.data[i];
                            var html = template("divTimplate",disk);
                            $("#divRow").append(html);
                        }

                    }
                }).error(function () {
                    layer.msg("服务器异常");
                });
            });

        });




    });
    
</script>
</body>
</html>