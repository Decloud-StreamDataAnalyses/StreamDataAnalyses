package me.zhengjie.utils.compiler_post_jar;



import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import java.io.File;
import java.io.IOException;


public class PostRunJar {

    public static String PostJar(File file) throws IOException {
        System.out.println(file.exists());

        if (file.exists()) {
            HttpClient client = new DefaultHttpClient();

            HttpPost httpPost = new HttpPost("http://10.5.83.175:8081/jars/upload");
            MultipartEntity entity = new MultipartEntity();
            entity.addPart("file", new FileBody(file));

            httpPost.setEntity(entity);
            HttpResponse response = client.execute(httpPost);

            HttpEntity responseEntity = response.getEntity();
            String result = EntityUtils.toString(responseEntity);
            System.out.println("res:"+result);
            int i1 = result.indexOf("flink-web-upload/");
            int i2 = result.indexOf(".jar");
            System.out.println(result.substring(i1+17,i2+4));
            return result.substring(i1+17,i2+4);
        }
        return null;
    }

    public static void RunJar(String jar) throws IOException {

        HttpClient client = new DefaultHttpClient();
        System.out.println("http://10.5.83.175:8081/jars/"+jar+"/upload");
        HttpPost httpPost = new HttpPost("http://10.5.83.175:8081/jars/"+jar+"/run");
        HttpResponse response = client.execute(httpPost);

        HttpEntity responseEntity = response.getEntity();
        String result = EntityUtils.toString(responseEntity);
        System.out.println("res:"+result);

    }

}
