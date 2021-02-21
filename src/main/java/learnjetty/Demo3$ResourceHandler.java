package learnjetty;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.util.resource.Resource;

import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Demo3$ResourceHandler {
    public static void main(String[] args) throws Exception {
        Server server = new Server(80);
        ResourceHandler resourceHandler = new ResourceHandler();
        resourceHandler.setDirectoriesListed(true);
        Path path = Paths.get("").toAbsolutePath();
        String pathString = "file://" + path;
        System.out.println(pathString);
        resourceHandler.setBaseResource(Resource.newResource(new URL(pathString)));
        server.setHandler(resourceHandler);
        server.start();
        server.join();
    }
}
