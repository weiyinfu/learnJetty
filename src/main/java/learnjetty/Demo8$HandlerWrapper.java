package learnjetty;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.eclipse.jetty.server.handler.HandlerCollection;
import org.eclipse.jetty.server.handler.HandlerWrapper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Random;

public class Demo8$HandlerWrapper {
static class MyHandlerWraper extends HandlerWrapper {
    @Override
    public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        long beg = System.currentTimeMillis();
        super.handle(target, baseRequest, request, response);
        long end = System.currentTimeMillis();
        response.getWriter().println("处理请求总共用时" + (end - beg) / 1000.0 + "秒");
    }
}

public static void main(String[] args) throws Exception {
    Server server = new Server(80);
    HandlerCollection collection = new HandlerCollection();
    collection.addHandler(new Demo6$HandlerCollection.EncodingHandler());
    HandlerWrapper wrapper = new MyHandlerWraper();
    wrapper.setHandler(new AbstractHandler() {
        Random random = new Random();

        @Override
        public void handle(String s, Request request, HttpServletRequest httpServletRequest, HttpServletResponse resp) throws IOException, ServletException {
            int t = random.nextInt(5) + 3;
            resp.getWriter().println("I need about " + t + " seconds to handle this request");
            try {
                Thread.sleep(t * 1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            request.setHandled(true);
        }
    });
    collection.addHandler(wrapper);
    server.setHandler(collection);
    server.start();
    server.join();
}
}
