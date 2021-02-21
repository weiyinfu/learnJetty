package learnjetty;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.DefaultHandler;

public class Demo2$DefaultHandler {
public static void main(String[] args) throws Exception {
    Server server = new Server(80);
    server.setHandler(new DefaultHandler());
    server.start();
    server.join();
}
}
