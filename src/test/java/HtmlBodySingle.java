import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@SuppressWarnings("deprecation")
public class HtmlBodySingle {
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
        httpGet.setHeader("Connection","keep-alive");
        httpGet.setHeader("Cache-Control","max-age=0");
        httpGet.setHeader("sec-ch-ua","\"Chromium\";v=\"88\", \"Google Chrome\";v=\"88\", \";Not A Brand\";v=\"99\"");
        httpGet.setHeader("sec-ch-ua-mobile","?0");
        httpGet.setHeader("Upgrade-Insecure-Requests","1");
        httpGet.setHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/88.0.4324.190 Safari/537.36");
        httpGet.setHeader("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9");
        httpGet.setHeader("Sec-Fetch-Site","none");
        httpGet.setHeader("Sec-Fetch-Mode","navigate");
        httpGet.setHeader("Sec-Fetch-User","?1");
        httpGet.setHeader("Sec-Fetch-Dest","document");
        httpGet.setHeader("Accept-Language","zh-CN,zh;q=0.9");
        //httpGet.setHeader("Cookie","ll=\"108288\"; bid=UkwSJU19rgo; _pk_ref.100001.8cb4=%5B%22%22%2C%22%22%2C1615296027%2C%22https%3A%2F%2Fwww.baidu.com%2Flink%3Furl%3DZKiL5g7ukHUEA9zz0JKIJDQz-STOWABOxuCa6ShvZAm78q9eatE9GmUl1yPVT6Ee%26wd%3D%26eqid%3Dc4efa7360012d3960000000260477616%22%5D; _pk_ses.100001.8cb4=*; __utma=30149280.350961490.1615296031.1615296031.1615296031.1; __utmz=30149280.1615296031.1.1.utmcsr=baidu|utmccn=(organic)|utmcmd=organic; dbcl2=\"230779084:cR3bWFoikM4\"; ap_v=0,6.0; push_noty_num=0; push_doumail_num=0; __utmv=30149280.23077; __yadk_uid=SpO0rChbplz8TUbKNbIkxqFyyVjrVipq; douban-fav-remind=1; ck=IT8x; __utmc=30149280; __gads=ID=c430fcc72f62a42b-222ee0a64bc6007a:T=1615296458:RT=1615296458:S=ALNI_MbuBx4SXkhtbK1ZT70XYVKBCFPHCQ; __utmt=1; _pk_id.100001.8cb4=10b4a119bb509e3c.1615296027.1.1615300742.1615296027.; __utmb=30149280.254.5.1615300742537");
        httpGet.setHeader("Cookie","Cookie: ll=\"108288\"; bid=pRGIZleHqA0; _pk_ref.100001.8cb4=%5B%22%22%2C%22%22%2C1615386732%2C%22https%3A%2F%2Fwww.baidu.com%2Flink%3Furl%3D59xyzq4tikiizDg6_JOnZxMCPgie0M_w8IbEchi6V44vC7skYRlxByhJBJV5DR7W%26wd%3D%26eqid%3Dc22257f400075299000000026048d869%22%5D; _pk_ses.100001.8cb4=*; __utma=30149280.1770972597.1615386733.1615386733.1615386733.1; __utmc=30149280; __utmz=30149280.1615386733.1.1.utmcsr=baidu|utmccn=(organic)|utmcmd=organic; __utmt=1; ap_v=0,6.0; __yadk_uid=uGSYewUUNh7UUeRIJvFVuzs3sVlWXNRm; __gads=ID=b039b18d6fe98640-22aaa39952c6001b:T=1615386740:RT=1615386740:S=ALNI_MZUHkE_5JW1KFBHh8onpRoB7JDcRw; _pk_id.100001.8cb4=316ea44175ed6cfc.1615386732.1.1615386743.1615386732.; __utmb=30149280.8.5.1615386739382");
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
    public static void htmlToDisk(String body,String url,int num) {
        String filename = "-";
        Pattern p = Pattern.compile("topic/([0-9]+)/");
        //Pattern p = Pattern.compile("group/([0-9]+)/");
        Matcher m = p.matcher(url);
        if(m.find()){
            filename = num+"#"+m.group(1);
        }
        FileOutputStream fos = null;
        try {
            // 定义文件名，./代表项目目录
            fos = new FileOutputStream("D:\\dbtest\\20210310-5\\"+filename +".html");
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
        HashMap<String,Integer> urlMap = new HashMap<String,Integer>();
        urlMap.put("https://www.douban.com/group/topic/210240430/",14);
        for (Map.Entry<String, Integer> temp:urlMap.entrySet()){
            int page = temp.getValue();
            for(int i = 0 ; i < page ; i ++){
                String url = temp.getKey();
                try{
                    Thread.sleep(1500);
                    //Thread.sleep(1000*60-1000);
                }catch(Exception e){
                    e.printStackTrace();
                }
                if(i == 0){
                    System.out.println(url);
                    String body = getBody(url);
                    htmlToDisk(body,url,i);
                }else{
                    url = url+"?start="+100*i;
                    //url = url+"?start="+35*i;
                    System.out.println(url);
                    String body = getBody(url);
                    htmlToDisk(body,url,i);
                }
            }

        }

    }

}
