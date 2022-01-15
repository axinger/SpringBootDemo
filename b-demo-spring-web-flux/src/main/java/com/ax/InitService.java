package com.ax;

import com.ax.handler.UserHandler;
import com.ax.service.UserService;
import com.ax.service.UserServiceImpl;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.HttpHandler;
import org.springframework.http.server.reactive.ReactorHttpHandlerAdapter;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.netty.http.server.HttpServer;

import java.io.IOException;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.toHttpHandler;

public class InitService {

    public static void main(String[] args) throws IOException {
        InitService server = new InitService();

        server.createServer();
        System.out.println("InitService.main");
        System.in.read();
    }

    // 路由
    public RouterFunction<ServerResponse> routerFunction() {

        UserService userService = new UserServiceImpl();
        UserHandler userHandler = new UserHandler(userService);


        return RouterFunctions.route(
                GET("/user/{id}").and(accept(MediaType.APPLICATION_JSON)),
                userHandler::getUserById
        ).andRoute(
                GET("/users").and(accept(MediaType.APPLICATION_JSON)),
                userHandler::getAllUser
        );
    }

    // 创建服务器完成适配
    public void createServer() {

        //路由和handler适配

        RouterFunction<ServerResponse> routes = routerFunction();
        HttpHandler httpHandler = toHttpHandler(routes);

        ReactorHttpHandlerAdapter adapter = new ReactorHttpHandlerAdapter(httpHandler);

        // 创建服务器
        HttpServer httpServer = HttpServer.create();
        httpServer.port(8082);
        httpServer.handle(adapter).bindNow();

    }

}
