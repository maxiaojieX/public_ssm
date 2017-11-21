package com.ma.controller.ChatUtil;

import org.java_websocket.WebSocketImpl;
import javax.servlet.*;
import java.io.IOException;
import java.net.UnknownHostException;

/**
 * tomcat启动时启动websocket
 * Created by Administrator on 2017/11/19 0018.
 */
public class StartSocket implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

        System.out.println("开始启动websocket");
        WebSocketImpl.DEBUG = false;
        int port = 8888;  // 端口随便设置，只要不跟现有端口重复就可以
        WebSocket s = null;
        try {
            s = new WebSocket(port);
        } catch (UnknownHostException e) {
            System.out.println("启动websocket失败！");
            e.printStackTrace();
        }
        s.start();
        System.out.println("启动websocket成功！");

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        chain.doFilter(request,response);
    }

    @Override
    public void destroy() {

    }
}
