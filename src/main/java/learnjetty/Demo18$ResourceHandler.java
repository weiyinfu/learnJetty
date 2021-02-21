package learnjetty;

import java.io.File;

import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.server.handler.DefaultHandler;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.util.resource.Resource;

public class Demo18$ResourceHandler {
static ContextHandler getContextHandler(String contextPath, String file) {
    ResourceHandler rh = new ResourceHandler();
    ContextHandler contextHandler = new ContextHandler();
    contextHandler.setContextPath(contextPath);
    contextHandler.setBaseResource(Resource.newResource(new File(file)));
    contextHandler.setHandler(rh);
    return contextHandler;
}

public static void main(String[] args) throws Exception {
    Server server = new Server();
    ServerConnector connector = new ServerConnector(server);
    connector.setPort(80);
    server.setConnectors(new Connector[]{connector});

    //创建一个ContextHandlerCollection集合来包含所有资源处理器，若都匹配则按顺序来
    ContextHandlerCollection contexts = new ContextHandlerCollection();
    contexts.setHandlers(new Handler[]{
            getContextHandler("/", "assets"),
            getContextHandler("/", "src")});

    server.setHandler(contexts);
    server.start();

    // 输出server状态
    System.out.println(server.dump());
    server.join();
}
}
