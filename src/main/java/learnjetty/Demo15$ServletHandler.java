package learnjetty;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHandler;

public class Demo15$ServletHandler {
public static void main(String[] args) throws Exception {
    Server server = new Server(80);
    ServletHandler servletHandler = new ServletHandler();
    servletHandler.addServletWithMapping(Demo2$ServletContextHandler.MyServlet.class, "/haha");
    server.setHandler(servletHandler);
    server.start();
    server.join();
}
}
