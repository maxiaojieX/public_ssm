<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>Title</title>
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    <link rel="stylesheet" href="/static/bootstrap/css/bootstrap.min.css">
    <style>
        * {
            margin: 0;
            padding: 0;
        }
        #box {
            position: absolute;
            top: 100px;
            left: 350px;
            width: 600px;
            border-radius: 65px;
        }
        #bar {
            padding-left:20px;
            height: 50px;
            line-height: 50px;
            color: white;
            background-color: rgba(44, 62, 80,1.0);
            cursor: move;

        }
        #content {
            padding:30px 0 0 50px ;
            height: 400px;
            background-color: #eee;
        }
    </style>
</head>
<body>
<h1>Hello,jsp</h1>

<a href="#">测试</a>
<a href="#">测试</a>
<a href="#">测试</a>
<a href="#">测试</a>
<button id="test">按钮</button>
<div id="box" class="modal fade" tabindex="-1" role="dialog">
    <div class="modal-dialog" role="document">
    <div id="bar" >讨论组</div>
    <div id="content">内容</div>
    </div>
</div>


<script>


    /*
     * zzw.drag 2017-3
     * js实现div可拖拽
     * @params bar 可以点击拖动的元素
     * @params box 拖动的整体元素 必须是position: absolute;
     * 思想：鼠标的clienX/clientY相对值设置为父元素的left/top的相对值
     */

    var dragBox = function (drag, wrap) {

        function getCss(ele, prop) {
            return parseInt(window.getComputedStyle(ele)[prop]);
        }

        var initX,
            initY,
            dragable = false,
            wrapLeft = getCss(wrap, "left"),
            wrapRight = getCss(wrap, "top");

        drag.addEventListener("mousedown", function (e) {
            dragable = true;
            initX = e.clientX;
            initY = e.clientY;
        }, false);

        document.addEventListener("mousemove", function (e) {
            if (dragable === true ) {
                var nowX = e.clientX,
                    nowY = e.clientY,
                    disX = nowX - initX,
                    disY = nowY - initY;
                wrap.style.left = wrapLeft + disX + "px";
                wrap.style.top = wrapRight + disY + "px";
            }
        });

        drag.addEventListener("mouseup", function (e) {
            dragable = false;
            wrapLeft = getCss(wrap, "left");
            wrapRight = getCss(wrap, "top");
        }, false);

    };

    dragBox(document.querySelector("#bar"), document.querySelector("#box"));
</script>
$("#test").click(function () {

$("#box").modal({
show:true,
backdrop:false
});


});
<script src="/static/plugins/jQuery/jquery-2.2.3.min.js"></script>
<!-- Bootstrap 3.3.6 -->
<script src="/static/bootstrap/js/bootstrap.min.js"></script>
<script src="/static/layui/layui.all.js"></script>
<script>




</script>


</body>
</html>