<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- Bootstrap 3.3.6 -->
<link rel="stylesheet" href="/static/bootstrap/css/bootstrap.min.css">
<%--<link rel="stylesheet" href="/static/bootstrap/css/bootstrap.my.css">--%>
<!-- Font Awesome -->
<link rel="stylesheet" href="/static/plugins/font-awesome/css/font-awesome.min.css">
<!-- Theme style -->
<link rel="stylesheet" href="/static/dist/css/AdminLTE.min.css">
<!-- AdminLTE Skins. Choose a skin from the css/skins
folder instead of downloading all of them to reduce the load. -->
<link rel="stylesheet" href="/static/dist/css/skins/_all-skins.min.css">
<link rel="stylesheet" href="/static/plugins/tree/css/metroStyle/metroStyle.css">
<%--可拖拽弹出层css--%>
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