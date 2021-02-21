package learnjetty;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.AllowSymLinkAliasChecker;
import org.eclipse.jetty.webapp.WebAppContext;

import java.io.File;
import java.lang.management.ManagementFactory;

import org.eclipse.jetty.jmx.MBeanContainer;

public class Demo16$WebAppContext {
public static void main(String[] args) throws Exception {
    Server server = new Server(80);

    // 设置 JMX
    MBeanContainer mbContainer = new MBeanContainer(ManagementFactory.getPlatformMBeanServer());
    server.addBean(mbContainer);

    //下面这个web应用是一个完整的web应用，在这个例子里设置/为根路径，web应用所有的配置都是有效的，
    WebAppContext webapp = new WebAppContext();
    webapp.setContextPath("/");
    File warFile = new File("src/main/webapp");
    webapp.setWar(warFile.getAbsolutePath());
    webapp.addAliasCheck(new AllowSymLinkAliasChecker());

    //将web应用设置到server里
    server.setHandler(webapp);

    server.start();
    server.join();
}
}