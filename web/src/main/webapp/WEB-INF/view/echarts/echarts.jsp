<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>凯盛软件CRM-修改客户</title>
    <!-- Tell the browser to be responsive to screen width -->
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    <!-- Bootstrap 3.3.6 -->
    <%@include file="../include/css.jsp"%>
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
    <style>
        #chance{
            display: inline-block;
        }
        #reg{
            display: inline-block;
        }


    </style>
</head>
<body class="hold-transition skin-blue sidebar-mini">

<!-- 顶部导航栏部分 -->
<%@include file="../include/header.jsp"%>
<!-- =============================================== -->

<!-- 左侧菜单栏 -->
<jsp:include page="../include/sider.jsp">
    <jsp:param name="menu" value="echarts"/>
</jsp:include>

<!-- 右侧内容部分 -->
    <div class="content-wrapper">
        <%--销售机会--%>
        <div id="chance" style="width: 500px;height:350px"></div>

        <div id="reg" style="width: 500px;height:350px"></div>

     </div>

<%@include file="../include/js.jsp"%>
<script src="/static/plugins/layer/layer.js"></script>
<script src="/static/plugins/echarts/echarts.min.js"></script>
<script>

    var regChart = echarts.init($("#reg")[0]);
    var regOption = {
        title: {
            text: "客户新增折线图",
            left: 'center'
        },
        tooltip: {},
        legend: {},
        xAxis: {
            type: 'category',
            data: []
        },
        yAxis: {},
        series: {
            name: "人数",
            type: 'line',
            data:[]
        }
    }

    regChart.setOption(regOption);

    $.get("/echarts/getCustomer.json").done(function (json) {
        if(json.state == "success"){

            var data = json.data;
            var nameArray = [];
            var valueArray = [];

            for(var i = 0;i<data.length;i++){
                var obj = data[i];
                nameArray.push(obj.time);
                valueArray.push(obj.value);
            }
            regChart.setOption({
                xAxis:{
                    data:nameArray
                },
                series:{
                    data:valueArray
                }

            });



        }

    }).error(function () {
        layer.msh("数据获取失败");
    });





    //*****************漏斗图**********************
    var myChart = echarts.init($("#chance")[0]);
    option = {
        title: {
            text: '销售统计',
            left:'center'
        },
        tooltip: {},
        series: [
            {
                name:'漏斗图',
                type:'funnel',
                left: '10%',
                top: 60,
                //x2: 80,
                bottom: 60,
                width: '80%',
                // height: {totalHeight} - y - y2,
                min: 0,
                minSize: '0%',
                maxSize: '100%',
                sort: 'descending',
                gap: 2,
                label: {
                    normal: {
                        show: true,
                        position: 'inside'
                    },
                    emphasis: {
                        textStyle: {
                            fontSize: 20
                        }
                    }
                },
                labelLine: {
                    normal: {
                        length: 10,
                        lineStyle: {
                            width: 1,
                            type: 'solid'
                        }
                    }
                },
                itemStyle: {
                    normal: {
                        borderColor: '#fff',
                        borderWidth: 1
                    }
                },
                data: []
            }
        ]
    };
    myChart.setOption(option);
    $.get("/echarts/getProcess.json").done(function (json) {
        if(json.state == "success"){

            var data = json.data;
            var maxNum = 0;
            for(var i = 0;i<data.length;i++){
                var a = data[i].value;
                if(a > maxNum){
                    maxNum = a;
                }
            }
            myChart.setOption({
                series:{
                    data:data,
                    max:maxNum
                }
            });
        }
    }).error(function () {
        layer.msg("获取数据失败");
    });

</script>
</body>
</html>
