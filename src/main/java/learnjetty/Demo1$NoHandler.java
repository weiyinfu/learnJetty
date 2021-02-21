package learnjetty;

import org.eclipse.jetty.server.Server;

public class Demo1$NoHandler {
    public static void main(String[] args) throws Exception {
        Server server = new Server(80);
        server.start();
        server.join();
    }
}
