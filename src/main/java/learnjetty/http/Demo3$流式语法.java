package learnjetty.http;

import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.client.api.ContentResponse;
import org.eclipse.jetty.http.HttpMethod;

import java.nio.file.Paths;

/**
 * 本段程序只进行演示，不要运行
 */
public class Demo3$流式语法 {
    public static void main(String[] args) throws Exception {
        HttpClient client = new HttpClient();
        client.start();
        ContentResponse response = client.newRequest("http://www.cnblogs.com")
                .method(HttpMethod.HEAD)
                .agent("Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:17.0) Gecko/20100101 Firefox/17.0")
                .send();
        //post请求
        response = client.POST("http://domain.com/entity/1")
                .param("p", "value")
                .send();
        //上传文件
        response = client.newRequest("http://domain.com/upload")
                .method(HttpMethod.POST)
                .file(Paths.get("file_to_upload.txt"), "text/plain")
                .send();

        client.stop();
    }
}
