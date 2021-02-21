package learnjetty;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.eclipse.jetty.server.handler.HandlerCollection;
import org.eclipse.jetty.server.handler.HandlerList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Handler链：一个处理之后不再向后传递
 */
public class Demo7$HandlerList {
    static class MyHandler extends AbstractHandler {
        int beg = Integer.MAX_VALUE;
        int end = Integer.MIN_VALUE;
        String msg;

        MyHandler(int beg, int end, String msg) {
            this.beg = beg;
            this.end = end;
            this.msg = msg;
        }

        @Override
        public void handle(String s, Request request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException, ServletException {
            int height = Integer.parseInt(httpServletRequest.getParameter("height"));
            if (height >= beg && height <= end) {
                httpServletResponse.getWriter().println(msg);
                request.setHandled(true);
            }
        }
    }

    public static void main(String[] args) throws Exception {
        Server server = new Server(80);
        HandlerCollection collection = new HandlerCollection();
        collection.addHandler(new Demo6$HandlerCollection.EncodingHandler());
        HandlerList list = new HandlerList();
        collection.addHandler(list);
        list.addHandler(new MyHandler(0, 100, "你长得太矮了"));
        list.addHandler(new MyHandler(160, 180, "你长得中等个"));
        list.addHandler(new MyHandler(180, 190, "你他妈的是个傻大个"));
        list.addHandler(new MyHandler(200, Integer.MAX_VALUE, "长那么高真的有用吗"));
        list.addHandler(new MyHandler(Integer.MIN_VALUE, Integer.MAX_VALUE, "你的身高让我无言以对"));
        server.setHandler(collection);
        server.start();
        server.join();
    }
}
