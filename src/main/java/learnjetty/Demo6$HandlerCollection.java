package learnjetty;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.eclipse.jetty.server.handler.HandlerCollection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

/**
 * HandlerCollection：一个集合中的Handler都会被访问
 */
public class Demo6$HandlerCollection {
static class EncodingHandler extends AbstractHandler {

    @Override
    public void handle(String s, Request request, HttpServletRequest httpServletRequest, HttpServletResponse resp) throws IOException, ServletException {
        resp.setCharacterEncoding("utf8");
        resp.setContentType("text/plain");
    }
}

static class LogHandler extends AbstractHandler {

    @Override
    public void handle(String s, Request request, HttpServletRequest req, HttpServletResponse httpServletResponse) throws IOException, ServletException {
        System.out.println(new Date() + " " + req.getRequestURL());
    }
}

static class MyHandler extends AbstractHandler {
    String name;

    MyHandler(String name) {
        this.name = name;
    }

    @Override
    public void handle(String s, Request request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException, ServletException {
        httpServletResponse.getWriter().println(name);
        request.setHandled(true);
    }
}

public static void main(String[] args) throws Exception {
    Server server = new Server(80);
    HandlerCollection handler = new HandlerCollection();
    handler.addHandler(new EncodingHandler());
    handler.addHandler(new LogHandler());
    handler.addHandler(new MyHandler("one"));
    handler.addHandler(new MyHandler("two"));
    handler.addHandler(new MyHandler("three"));
    server.setHandler(handler);
    server.start();
    server.join();
}
}
