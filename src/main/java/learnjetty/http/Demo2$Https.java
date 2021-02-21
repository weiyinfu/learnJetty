package learnjetty.http;

import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.client.api.ContentResponse;
import org.eclipse.jetty.util.ssl.SslContextFactory;

public class Demo2$Https {
    public static void main(String[] args) throws Exception {
        //创建并配置SslContextFactory
        SslContextFactory sslContextFactory = new SslContextFactory();

//通过 SslContextFactory创建HttpClient
        HttpClient httpClient = new HttpClient(sslContextFactory);

//配置HttpClient
        httpClient.setFollowRedirects(false);

//启动HttpClient
        httpClient.start();
        ContentResponse resp = httpClient.GET("https://www.baidu.com");
        System.out.println(resp.getContentAsString());
    }
}
