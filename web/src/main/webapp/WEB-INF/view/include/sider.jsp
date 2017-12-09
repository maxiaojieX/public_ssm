<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<!-- 左侧菜单栏 -->
<aside class="main-sidebar">
    <!-- sidebar: style can be found in sidebar.less -->
    <section class="sidebar">

        <!-- 搜索表单，不需要删除即可 -->
        <!--<form action="#" method="get" class="sidebar-form">
          <div class="input-group">
            <input type="text" name="q" class="form-control" placeholder="Search...">
                <span class="input-group-btn">
                  <button type="submit" name="search" id="search-btn" class="btn btn-flat"><i class="fa fa-search"></i>
                  </button>
                </span>
          </div>
        </form>-->
        <!-- /.search form -->
        <!-- 菜单 -->
        <ul class="sidebar-menu">
            <li class="${param.menu == 'home' ? 'active' : ''}"><a href="/account"><i class="fa fa-share-alt"></i> <span>首页</span></a></li>
            <li class="header">系统功能</li>
            <!-- 客户管理 -->
            <li class="treeview ${fn:startsWith(param.menu, 'customer_') ? 'active' : ''}">
                <a href="#">
                    <i class="fa fa-address-book-o"></i> <span>客户管理</span>
                    <span class="pull-right-container">
              <i class="fa fa-angle-left pull-right"></i>
            </span>
                </a>
                <ul class="treeview-menu">
                    <li class="${param.menu == 'customer_my' ? 'active' : ''}"><a href="/customer"><i class="fa fa-circle-o"></i> 我的客户</a></li>
                    <li class="${param.menu == 'customer_public' ? 'active' : ''}"><a href="/customer/public"><i class="fa fa-circle-o"></i> 公海客户</a></li>
                </ul>
            </li>
            <!-- 工作记录 -->
            <li class="treeview ${fn:startsWith(param.menu, 'record_') ? 'active' : ''}">
                <a href="#">
                    <i class="fa fa-bars"></i> <span>工作记录</span>
                    <span class="pull-right-container">
              <i class="fa fa-angle-left pull-right"></i>
            </span>
                </a>
                <ul class="treeview-menu">
                    <li class="${param.menu == 'record_my' ? 'active' : ''}"><a href="/record/my"><i class="fa fa-circle-o"></i> 我的记录</a></li>
                    <%--<li class="${param.menu == 'record_public' ? 'active' : ''}"><a href="#"><i class="fa fa-circle-o"></i> 公共记录</a></li>--%>
                </ul>
            </li>
            <!-- 待办事项 -->
            <li class="treeview ${param.menu == 'task' ? 'active' : ''}">
                <a href="#">
                    <i class="fa fa-calendar"></i> <span>待办事项</span>
                    <span class="pull-right-container">
              <i class="fa fa-angle-left pull-right"></i>
            </span>
                </a>
                <ul class="treeview-menu">
                    <li class="${param.menu == 'task' ? 'active' : ''}"><a href="/task/my"><i class="fa fa-circle-o"></i> 待办列表</a></li>
                    <%--<li><a href=""><i class="fa fa-circle-o"></i> 逾期事项</a></li>--%>
                </ul>
            </li>
            <!-- 统计报表 -->
            <li class="treeview ${param.menu == 'echarts' ? 'active' : ''}">
                <a href="#">
                    <i class="fa fa-pie-chart"></i> <span>统计报表</span>
                    <span class="pull-right-container">
              <i class="fa fa-angle-left pull-right"></i>
            </span>
                </a>
                <ul class="treeview-menu">
                    <li class="${param.menu == 'echarts' ? 'active' : ''}"><a href="/echarts/toEcharts"><i class="fa fa-circle-o"></i> 项目统计</a></li>
                </ul>
            </li>

<%--<a href="/admin/fileupload"    onclick="window.open(this.href,'_blank','scrollbars=0,resizable=0,top=143,left=481,width=380,height=400');return    false">--%>
            <li><a href="/SkyDrive/our"    onclick="window.open(this.href,'_blank','scrollbars=0,resizable=0,top=123,left=281,width=680,height=480');return    false"><i class="fa fa-share-alt"></i> <span>公司网盘</span></a></li>
            <li class="header">系统管理</li>
            <!-- 部门员工管理 -->
            <shiro:hasRole name="开发部">
                <li class="${param.menu == 'list' ? 'active' : ''}"><a href="/account/list2"><i class="fa fa-users"></i> <span>员工管理</span></a></li>
            </shiro:hasRole>
            <%--<li><a href="/chat/toChat"   onclick="window.open(this.href,'_blank','scrollbars=0,resizable=0,top=123,left=281,width=680,height=480');return    false"><i class="fa fa-users"></i> <span>公司微信</span></a></li>--%>
            <li><a href="/chat/toChat"><i class="fa fa-weixin"></i> <span>公司微信</span></a></li>
            <!--<li><a href="#"><i class="fa fa-circle-o text-yellow"></i> <span>Warning</span></a></li>
            <li><a href="#"><i class="fa fa-circle-o text-aqua"></i> <span>Information</span></a></li>-->
        </ul>
    </section>
    <!-- /.sidebar -->
</aside>

<%--<div class="layui-layer layui-layer-page layui-box layui-layim-chat" id="layui-layer3" type="page" times="3" showtime="0" contype="string" style="z-index: 19891017; width: 600px; top: -1px; left: 382px; min-width: 500px; min-height: 420px; background-image: url(&quot;http://res.layui.com/layui/dist/css/modules/layim/skin/3.jpg&quot;);">--%>

<%--</div>--%>
