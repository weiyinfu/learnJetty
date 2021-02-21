package learnjetty;

import org.eclipse.jetty.deploy.DeploymentManager;
import org.eclipse.jetty.deploy.PropertiesConfigurationManager;
import org.eclipse.jetty.deploy.bindings.DebugBinding;
import org.eclipse.jetty.deploy.providers.WebAppProvider;
import org.eclipse.jetty.http.HttpVersion;
import org.eclipse.jetty.jmx.MBeanContainer;
import org.eclipse.jetty.rewrite.handler.RewriteHandler;
import org.eclipse.jetty.security.HashLoginService;
import org.eclipse.jetty.server.*;
import org.eclipse.jetty.server.handler.*;
import org.eclipse.jetty.util.ssl.SslContextFactory;
import org.eclipse.jetty.util.thread.QueuedThreadPool;
import org.eclipse.jetty.util.thread.ScheduledExecutorScheduler;
import org.eclipse.jetty.webapp.Configuration;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.management.ManagementFactory;

/**
 * Starts the Jetty Distribution's demo-base directory using entirely
 * embedded jetty techniques.
 */
public class Demo17$UseJettyConfig {
public static void main(String[] args) throws Exception {
    // Path to as-built jetty-distribution directory
    String jettyHomeBuild = "../../jetty-distribution/target/distribution";

    // Find jetty home and base directories
    String homePath = System.getProperty("jetty.home", jettyHomeBuild);
    File start_jar = new File(homePath, "start.jar");
    if (!start_jar.exists()) {
        homePath = jettyHomeBuild = "jetty-distribution/target/distribution";
        start_jar = new File(homePath, "start.jar");
        if (!start_jar.exists())
            throw new FileNotFoundException(start_jar.toString());
    }

    File homeDir = new File(homePath);

    String basePath = System.getProperty("jetty.base", homeDir + "/demo-base");
    File baseDir = new File(basePath);
    if (!baseDir.exists()) {
        throw new FileNotFoundException(baseDir.getAbsolutePath());
    }

    // Configure jetty.home and jetty.base system properties
    String jetty_home = homeDir.getAbsolutePath();
    String jetty_base = baseDir.getAbsolutePath();
    System.setProperty("jetty.home", jetty_home);
    System.setProperty("jetty.base", jetty_base);


    // === jetty.xml ===
    // Setup Threadpool
    QueuedThreadPool threadPool = new QueuedThreadPool();
    threadPool.setMaxThreads(500);

    // Server
    Server server = new Server(threadPool);

    // Scheduler
    server.addBean(new ScheduledExecutorScheduler());

    // HTTP Configuration
    HttpConfiguration http_config = new HttpConfiguration();
    http_config.setSecureScheme("https");
    http_config.setSecurePort(8443);
    http_config.setOutputBufferSize(32768);
    http_config.setRequestHeaderSize(8192);
    http_config.setResponseHeaderSize(8192);
    http_config.setSendServerVersion(true);
    http_config.setSendDateHeader(false);
    http_config.addCustomizer(new ForwardedRequestCustomizer());

    // Handler Structure
    HandlerCollection handlers = new HandlerCollection();
    ContextHandlerCollection contexts = new ContextHandlerCollection();
    handlers.setHandlers(new Handler[]{contexts, new DefaultHandler()});
    server.setHandler(handlers);

    // Extra options
    server.setDumpAfterStart(false);
    server.setDumpBeforeStop(false);
    server.setStopAtShutdown(true);

    // === jetty-jmx.xml ===
    MBeanContainer mbContainer = new MBeanContainer(
            ManagementFactory.getPlatformMBeanServer());
    server.addBean(mbContainer);


    // === jetty-http.xml ===
    ServerConnector http = new ServerConnector(server,
            new HttpConnectionFactory(http_config));
    http.setPort(8080);
    http.setIdleTimeout(30000);
    server.addConnector(http);


    // === jetty-https.xml ===
    // SSL Context Factory
    SslContextFactory sslContextFactory = new SslContextFactory();
    sslContextFactory.setKeyStorePath(jetty_home + "/../../../jetty-server/src/test/config/etc/keystore");
    sslContextFactory.setKeyStorePassword("OBF:1vny1zlo1x8e1vnw1vn61x8g1zlu1vn4");
    sslContextFactory.setKeyManagerPassword("OBF:1u2u1wml1z7s1z7a1wnl1u2g");
    sslContextFactory.setTrustStorePath(jetty_home + "/../../../jetty-server/src/test/config/etc/keystore");
    sslContextFactory.setTrustStorePassword("OBF:1vny1zlo1x8e1vnw1vn61x8g1zlu1vn4");
    sslContextFactory.setExcludeCipherSuites("SSL_RSA_WITH_DES_CBC_SHA",
            "SSL_DHE_RSA_WITH_DES_CBC_SHA", "SSL_DHE_DSS_WITH_DES_CBC_SHA",
            "SSL_RSA_EXPORT_WITH_RC4_40_MD5",
            "SSL_RSA_EXPORT_WITH_DES40_CBC_SHA",
            "SSL_DHE_RSA_EXPORT_WITH_DES40_CBC_SHA",
            "SSL_DHE_DSS_EXPORT_WITH_DES40_CBC_SHA");

    // SSL HTTP Configuration
    HttpConfiguration https_config = new HttpConfiguration(http_config);
    https_config.addCustomizer(new SecureRequestCustomizer());

    // SSL Connector
    ServerConnector sslConnector = new ServerConnector(server,
            new SslConnectionFactory(sslContextFactory, HttpVersion.HTTP_1_1.asString()),
            new HttpConnectionFactory(https_config));
    sslConnector.setPort(8443);
    server.addConnector(sslConnector);


    // === jetty-deploy.xml ===
    DeploymentManager deployer = new DeploymentManager();
    DebugHandler debug = new DebugHandler();
    debug.setOutputStream(System.out);
    server.addBean(debug);
    deployer.addLifeCycleBinding(new DebugBinding());
    deployer.setContexts(contexts);
    deployer.setContextAttribute(
            "org.eclipse.jetty.server.webapp.ContainerIncludeJarPattern",
            ".*/servlet-api-[^/]*\\.jar$");

    WebAppProvider webapp_provider = new WebAppProvider();
    webapp_provider.setMonitoredDirName(jetty_base + "/webapps");
    webapp_provider.setDefaultsDescriptor(jetty_home + "/etc/webdefault.xml");
    webapp_provider.setScanInterval(1);
    webapp_provider.setExtractWars(true);
    webapp_provider.setConfigurationManager(new PropertiesConfigurationManager());

    deployer.addAppProvider(webapp_provider);
    server.addBean(deployer);

    // === setup jetty plus ==
    Configuration.ClassList.setServerDefault(server).addAfter(
            "org.eclipse.jetty.webapp.FragmentConfiguration",
            "org.eclipse.jetty.plus.webapp.EnvConfiguration",
            "org.eclipse.jetty.plus.webapp.PlusConfiguration");

    // === jetty-stats.xml ===
    StatisticsHandler stats = new StatisticsHandler();
    stats.setHandler(server.getHandler());
    server.setHandler(stats);
    ConnectorStatistics.addToAllConnectors(server);

    // === Rewrite Handler
    RewriteHandler rewrite = new RewriteHandler();
    rewrite.setHandler(server.getHandler());
    server.setHandler(rewrite);

    // === jetty-requestlog.xml ===
    NCSARequestLog requestLog = new NCSARequestLog();
    requestLog.setFilename(jetty_home + "/logs/yyyy_mm_dd.request.log");
    requestLog.setFilenameDateFormat("yyyy_MM_dd");
    requestLog.setRetainDays(90);
    requestLog.setAppend(true);
    requestLog.setExtended(true);
    requestLog.setLogCookies(false);
    requestLog.setLogTimeZone("GMT");
    RequestLogHandler requestLogHandler = new RequestLogHandler();
    requestLogHandler.setRequestLog(requestLog);
    handlers.addHandler(requestLogHandler);


    // === jetty-lowresources.xml ===
    LowResourceMonitor lowResourcesMonitor = new LowResourceMonitor(server);
    lowResourcesMonitor.setPeriod(1000);
    lowResourcesMonitor.setLowResourcesIdleTimeout(200);
    lowResourcesMonitor.setMonitorThreads(true);
    lowResourcesMonitor.setMaxConnections(0);
    lowResourcesMonitor.setMaxMemory(0);
    lowResourcesMonitor.setMaxLowResourcesTime(5000);
    server.addBean(lowResourcesMonitor);


    // === test-realm.xml ===
    HashLoginService login = new HashLoginService();
    login.setName("Test Realm");
    login.setConfig(jetty_base + "/etc/realm.properties");
//    login.setHotReload(false);
    server.addBean(login);

    // Start the server
    server.start();
    server.join();
}
}