package learnjetty;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.AbstractHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Demo4$AbstractHandler {
    public static void main(String[] args) throws Exception {
        Server server = new Server(80);
        server.setHandler(new AbstractHandler() {
            @Override
            public void handle(String s, Request request, HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
                resp.setCharacterEncoding("utf8");
                resp.setContentType("text/plain");
                resp.getWriter().print("天下大势为我所控");
                request.setHandled(true);
            }
        });
        server.start();
        server.join();
    }
}
