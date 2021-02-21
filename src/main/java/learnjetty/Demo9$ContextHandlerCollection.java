package learnjetty;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Demo9$ContextHandlerCollection {
    static class MyHandler extends AbstractHandler {
        String name;

        MyHandler(String name) {
            this.name = name;
        }

        @Override
        public void handle(String s, Request request, HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
            resp.getWriter().println("hello , I am '" + name + "'");
            System.out.println("haha");
            request.setHandled(true);
        }
    }

    public static void main(String[] args) throws Exception {
        Server server = new Server(80);
        ContextHandlerCollection contextCollection = new ContextHandlerCollection();
        ContextHandler one = contextCollection.addContext("/one", "");
        one.setHandler(new MyHandler("one"));
        ContextHandler two = contextCollection.addContext("/two", "");
        two.setHandler(new MyHandler("two"));
        ContextHandler three = contextCollection.addContext("/three", "");
        three.setHandler(new MyHandler("three"));
        server.setHandler(contextCollection);
        server.start();
        server.join();
    }
}
