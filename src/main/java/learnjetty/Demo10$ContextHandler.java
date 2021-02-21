package learnjetty;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.eclipse.jetty.server.handler.ContextHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Demo10$ContextHandler {
static class Myhandler extends AbstractHandler {

    @Override
    public void handle(String s, Request request, HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        resp.getWriter().println("Hello world");
        request.setHandled(true);
    }
}

public static void main(String[] args) throws Exception {
    Server server = new Server(80);
    ContextHandler contextHandler = new ContextHandler("/haha");
    contextHandler.setHandler(new Myhandler());
    server.setHandler(contextHandler);
    server.start();
    server.join();
}
}
