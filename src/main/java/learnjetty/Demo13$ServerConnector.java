package learnjetty;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.DefaultHandler;

public class Demo13$ServerConnector {
public static void main(String[] args) throws Exception {
/**
 * 有一个连接的Server
 */
    Server server = new Server();

    // 创建一个HTTP的连接，配置监听主机，端口，以及超时时间
    ServerConnector http = new ServerConnector(server);
    http.setHost("localhost");
    http.setPort(80);
    http.setIdleTimeout(30000);

    // 将此连接添加到Server
    server.addConnector(http);

    // 设置一个处理器
    server.setHandler(new DefaultHandler());

    // 启动Server
    server.start();
    server.join();
}
}
