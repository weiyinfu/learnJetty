package learnjetty;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Server;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Demo5$Handler {
public static void main(String[] args) throws Exception {
    Server server = new Server(80);
    server.setHandler(new Handler() {
        @Override
        public void handle(String s, Request request, HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
            request.setHandled(true);
            resp.setContentType("text/plain");
            resp.setCharacterEncoding("utf8");
            resp.getWriter().println("天下大势为我所控");
        }

        @Override
        public void setServer(Server server) {

        }

        @Override
        public Server getServer() {
            return null;
        }

        @Override
        public void destroy() {

        }

        @Override
        public void start() throws Exception {

        }

        @Override
        public void stop() throws Exception {

        }

        @Override
        public boolean isRunning() {
            return false;
        }

        @Override
        public boolean isStarted() {
            return false;
        }

        @Override
        public boolean isStarting() {
            return false;
        }

        @Override
        public boolean isStopping() {
            return false;
        }

        @Override
        public boolean isStopped() {
            return false;
        }

        @Override
        public boolean isFailed() {
            return false;
        }

        @Override
        public void addLifeCycleListener(Listener listener) {

        }

        @Override
        public void removeLifeCycleListener(Listener listener) {

        }
    });
    server.start();
    server.join();
}
}
