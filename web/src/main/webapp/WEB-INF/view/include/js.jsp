<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%--可拖拽弹出层js--%>
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

<script src="/static/plugins/jQuery/jquery-2.2.3.min.js"></script>
<script>

    $("#imTest").click(function () {
        $("#box").attr("display","block");
    });



</script>
<!-- Bootstrap 3.3.6 -->
<script src="/static/bootstrap/js/bootstrap.min.js"></script>
<!-- SlimScroll -->
<script src="/static/plugins/slimScroll/jquery.slimscroll.min.js"></script>
<!-- FastClick -->
<script src="/static/plugins/fastclick/fastclick.js"></script>
<!-- AdminLTE App -->
<script src="/static/dist/js/app.min.js"></script>