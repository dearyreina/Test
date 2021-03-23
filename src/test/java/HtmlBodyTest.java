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
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@SuppressWarnings("deprecation")
public class HtmlBodyTest {
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
        httpGet.setHeader("authority","weibo.com");
        //httpGet.setHeader("accept-encoding","gzip, deflate, br");
        httpGet.setHeader("accept-language","zh-CN,zh;q=0.9");
        httpGet.setHeader("content-type","application/x-www-form-urlencoded");
        httpGet.setHeader("cookie","SINAGLOBAL=2873808890871.985.1614855723699; wvr=6; UOR=,,login.sina.com.cn; wb_view_log_5824652927=1366*7681; ALF=1646800240; SUBP=0033WrSXqPxfM725Ws9jqgMF55529P9D9WFnb.iBBs7Hh76Vr7q4ucls5JpX5K-hUgL.Fo-ReoBcSKz4eoM2dJLoI0YLxKBLB.BLBK5LxK-L12qL1hnLxK-L1-eLBKnLxKnLBKML1hqLxK-LBK-LBonLxKqL1hnL1K2LxK-L1K5L1KMt; _s_tentry=weibo.com; Apache=8634853689399.253.1615272951982; ULV=1615272952066:6:6:5:8634853689399.253.1615272951982:1615270582640; SCF=Ao7EUyJaut1Q5Cs9y9rpRHp6myamTleX64NVcDoo7jji0SF1PHflQ1acVItzq2qPyllSG2MNn3wIhAWwWpH0Qkc.; SUB=_2A25NQ2wyDeRhGeNG6VYX9SzFyTuIHXVuOdr6rDV8PUJbmtANLRf6kW9NS2RJwlx3UQrCeqSO1wbgrR2b_qfz27bF; SSOLoginState=1615273059; webim_unReadCount=%7B%22time%22%3A1615284432084%2C%22dm_pub_total%22%3A4%2C%22chat_group_client%22%3A0%2C%22chat_group_notice%22%3A0%2C%22allcountNum%22%3A11%2C%22msgbox%22%3A0%7D");
        httpGet.setHeader("referer","https://weibo.com/6335992819/K4XaBc3oD?type=comment");
        httpGet.setHeader("sec-ch-ua","\"Chromium\";v=\"88\", \"Google Chrome\";v=\"88\", \";Not A Brand\";v=\"99\"");
        httpGet.setHeader("sec-ch-ua-mobile","?0");
        httpGet.setHeader("sec-fetch-dest","empty");
        httpGet.setHeader("sec-fetch-mode","cors");
        httpGet.setHeader("sec-fetch-site","same-origin");
        httpGet.setHeader("user-agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/88.0.4324.190 Safari/537.36");
        httpGet.setHeader("x-requested-with","XMLHttpRequest");
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
            fos = new FileOutputStream("D:\\test2\\test"+new Date().getTime() +".html");
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
String url = "https://weibo.com/aj/v6/comment/big?ajwvr=6&id=4611807652872995&from=singleWeiBo";
        String body = getBody(url);
        System.out.println(body);
        htmlToDisk(body);
    }

}
