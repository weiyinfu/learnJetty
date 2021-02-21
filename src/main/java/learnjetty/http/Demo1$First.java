package learnjetty.http;

import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.client.api.ContentResponse;

public class Demo1$First {
    public static void main(String[] args) throws Exception {
        HttpClient client = new HttpClient();
        client.start();
        ContentResponse resp = client.GET("http://www.tvmao.com/drama/KSExaik=/episode/0-3");
        client.stop();
        System.out.println(resp.getContentAsString());
    }
}
