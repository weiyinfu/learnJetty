package learnjetty;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.eclipse.jetty.server.handler.HandlerCollection;
import org.eclipse.jetty.server.session.SessionHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class Demo11$SessionHandler {
static class Myhandler extends AbstractHandler {

    @Override
    public void handle(String s, Request request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException, ServletException {
        HttpSession sess = httpServletRequest.getSession();
        if (sess.getAttribute("cnt") == null) {
            sess.setAttribute("cnt", 1);
        } else {
            sess.setAttribute("cnt", (int) sess.getAttribute("cnt") + 1);
        }
        int cnt = (int) sess.getAttribute("cnt");
        httpServletResponse.getWriter().println("这是您第" + cnt + "次访问此页面了");
        request.setHandled(true);
    }
}

public static void main(String[] args) throws Exception {
    Server server = new Server(80);
    HandlerCollection collection = new HandlerCollection();
    collection.addHandler(new SessionHandler());
    collection.addHandler(new Demo6$HandlerCollection.EncodingHandler());
    collection.addHandler(new Myhandler());
    server.setHandler(collection);
    server.start();
    server.join();
}
}
