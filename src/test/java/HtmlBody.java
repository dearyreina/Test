import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;


@SuppressWarnings("deprecation")
public class HtmlBody {
    /**
     * 根据url获取html页面
     *
     * @param url
     * @return
     */
    public static String getBody(String url) {
        // 实例一个http客户端
        DefaultHttpClient httpClient = new DefaultHttpClient();
        // 实例一个httpGet请求，url放进去
        HttpGet httpGet = new HttpGet(url);
        try {
            // 用客户端执行get请求，并得到一个response回应，这里的执行时间根据客户端请求服务器的时间来决定
            // 如连接不成功会有IO异常
            HttpResponse response = httpClient.execute(httpGet);
            // 获取response里面的内容等。
            HttpEntity entity = response.getEntity();
            // 将entity元素通过Entity工具类转化为字符串形式，此时即为url页面html的字符串,这里的UTF_8为页面的实际编码
            String body = EntityUtils.toString(entity, HTTP.UTF_8);
            return body;
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * HTML页面存盘
     *
     * @param body
     */
    public static void htmlToDisk(String body) {
        FileOutputStream fos = null;
        try {
            // 定义文件名，./代表项目目录
            fos = new FileOutputStream("D:\\test2\\index"+new Date().getTime() +".html");
            // 写文件流
            fos.write(body.getBytes());
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            try {
                fos.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        }
    }

    public static void main(String[] args) { 
        String url = "https://v.qq.com/x/cover/mzc002000w08m6u.html";
        String url2 = "https://v.qq.com/x/cover/mzc002004jql691.html";
        while (true){
            String body = getBody(url);
            //String body2 = getBody(url2);
//            body.replaceAll("票","\003");
//            String pattern = "em class=\"person\">([^\003]+)\003";
//            Pattern r = Pattern.compile(pattern);
//            Matcher m = r.matcher(body);
//            StringBuilder sb = new StringBuilder();
//            while(m.find()){
//                sb.append(m.group(0)).append("\n");
//            }
            String pattern = "\"view_all_count\":([0-9]+)";
            Pattern r = Pattern.compile(pattern);
            Matcher m = r.matcher(body);
            //Matcher m2 = r.matcher(body2);
            String out = new Date().toString();
            while(m.find()){
                out=out+"   "+m.group(0);
            }
//            while(m2.find()){
//                out=out+"   "+m2.group(0);
//            }
            System.out.println(out.replaceAll("\"view_all_count\":",""));
            htmlToDisk(body);
            try{
                Thread.sleep(1000*60*10-600);
                //Thread.sleep(1000*60-1000);
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }

}
