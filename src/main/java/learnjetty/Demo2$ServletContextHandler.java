package learnjetty;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Demo2$ServletContextHandler {
    public static class MyServlet extends HttpServlet {
        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            resp.setContentType("text/plain");
            resp.setCharacterEncoding("utf8");
            System.out.println("hello");
            resp.getWriter().println("天下大势为我所控");
        }

    }

    public static void main(String[] args) throws Exception {
        Server server = new Server(80);
        ServletContextHandler root = new
                ServletContextHandler(null, "/", ServletContextHandler.SESSIONS);
        root.addServlet(new ServletHolder(MyServlet.class), "/");
        server.setHandler(root);
        server.start();
        server.join();
    }
}
