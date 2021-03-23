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

public class TestPC {
    public static void main(String[] args) {
        //String url = "https://weibo.com/aj/v6/comment/big?ajwvr=6&id=4611807652872995&root_comment_max_id=4611821174787080&root_comment_max_id_type=1&root_comment_ext_param=&page=215&filter=hot&sum_comment_number=2069&filter_tips_before=0";
        String url = "https://weibo.com/aj/v6/comment/big?ajwvr=6&id=4611807652872995&filter=all&from=singleWeiBo";
        // 实例一个http客户端
        DefaultHttpClient httpClient = new DefaultHttpClient();
        // 实例一个httpGet请求，url放进去
        HttpGet httpGet = new HttpGet(url);
        httpGet.setHeader("authority","weibo.com");
        //httpGet.setHeader("accept-encoding","gzip, deflate, br");
        httpGet.setHeader("accept-language","zh-CN,zh;q=0.9");
        httpGet.setHeader("content-type","application/x-www-form-urlencoded");
        httpGet.setHeader("cookie","SINAGLOBAL=7609560327721.387.1615263257687; ULV=1615292084597:2:2:2:3336758330497.84.1615292084466:1615263257696; SUB=_2A25NQxaKDeRhGeNG6VYX9SzFyTuIHXVuOQ9CrDV8PUNbmtAKLXDHkW9NS2RJwnrkAi3QIRmf3ijQwRQypJU7PKw9; SUBP=0033WrSXqPxfM725Ws9jqgMF55529P9D9WFnb.iBBs7Hh76Vr7q4ucls5JpX5KzhUgL.Fo-ReoBcSKz4eoM2dJLoI0YLxKBLB.BLBK5LxK-L12qL1hnLxK-L1-eLBKnLxKnLBKML1hqLxK-LBK-LBonLxKqL1hnL1K2LxK-L1K5L1KMt; login_sid_t=a943f08bf0ac45994ae7df1a0fc1cc13; cross_origin_proto=SSL; WBStorage=8daec78e6a891122|undefined; _s_tentry=-; UOR=,,login.sina.com.cn; wb_view_log=1366*7681; Apache=3336758330497.84.1615292084466; ALF=1646828121; SSOLoginState=1615292122; wvr=6; wb_view_log_5824652927=1366*7681; webim_unReadCount=%7B%22time%22%3A1615292155030%2C%22dm_pub_total%22%3A4%2C%22chat_group_client%22%3A0%2C%22chat_group_notice%22%3A0%2C%22allcountNum%22%3A11%2C%22msgbox%22%3A0%7D");
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
            htmlToDisk(body);


            for(int i=0 ; i<= 600 ; i++){
                String urlpart = "";
                String pattern = "[comment_loading|click_more_comment]{1}.*id=4611807652872995&(.*)&filter_tips_before";
                Pattern r = Pattern.compile(pattern);
                try{
                    Thread.sleep(2000);
                    //Thread.sleep(1000*60-1000);
                }catch(Exception e){
                    e.printStackTrace();
                }
                Matcher m = r.matcher(body);
                if(m.find()){
                    urlpart = m.group(1);
                }
                url = "https://weibo.com/aj/v6/comment/big?ajwvr=6&id=4611807652872995&from=singleWeiBo&"+urlpart+"&filter_tips_before=0"+"&__rnd="+new Date().getTime();
                System.out.println(url);
                httpGet = new HttpGet(url);
                httpGet.setHeader("authority","weibo.com");
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
                response = httpClient.execute(httpGet);
                entity = response.getEntity();
                body = EntityUtils.toString(entity, HTTP.UTF_8);
                htmlToDisk(body);
                try{
                    Thread.sleep(2500);
                    //Thread.sleep(1000*60-1000);
                }catch(Exception e){
                    e.printStackTrace();
                }
            }


        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void htmlToDisk(String body) {
        FileOutputStream fos = null;
        try {
            // 定义文件名，./代表项目目录
            fos = new FileOutputStream("D:\\test4\\test"+new Date().getTime() +".html");
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
}
