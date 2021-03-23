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
public class HtmlBody3 {
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
            fos = new FileOutputStream("D:\\cc\\index"+new Date().getTime() +".html");
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
       String[] url = {"https://v.qq.com/x/cover/mzc00200dfbfsrw/e003452crbe.html",
               "https://v.qq.com/x/cover/mzc00200dfbfsrw/r0034vp5h7v.html",
               "https://v.qq.com/x/cover/mzc00200dfbfsrw/k00345iwymt.html",
               "https://v.qq.com/x/cover/mzc00200dfbfsrw/t0034fl5xgc.html",
               "https://v.qq.com/x/cover/mzc00200dfbfsrw/d00344itxeu.html",
               "https://v.qq.com/x/cover/mzc00200dfbfsrw/i0034kn9ki3.html",
               "https://v.qq.com/x/cover/mzc00200dfbfsrw/o0034xzlnje.html",
               "https://v.qq.com/x/cover/mzc00200dfbfsrw/c0034j8dlzh.html",
               "https://v.qq.com/x/cover/mzc00200dfbfsrw/e003498s1c9.html",
               "https://v.qq.com/x/cover/mzc00200dfbfsrw/e0034iod8pk.html",
               "https://v.qq.com/x/cover/mzc00200dfbfsrw/s00345ljuyi.html",
               "https://v.qq.com/x/cover/mzc00200dfbfsrw/l0034wfeq2r.html",
               "https://v.qq.com/x/cover/mzc00200dfbfsrw/o0034rd6t1s.html",
               "https://v.qq.com/x/cover/mzc00200dfbfsrw/w003402gtdn.html",
               "https://v.qq.com/x/cover/mzc00200dfbfsrw/w0034dxwgeb.html",
               "https://v.qq.com/x/cover/mzc00200dfbfsrw/t0034rshrtq.html",
               "https://v.qq.com/x/cover/mzc00200dfbfsrw/s0034tsvayh.html",
               "https://v.qq.com/x/cover/mzc00200dfbfsrw/f00340lvvk7.html",
               "https://v.qq.com/x/cover/mzc00200dfbfsrw/s0034t17d67.html",
               "https://v.qq.com/x/cover/mzc00200dfbfsrw/v00342kbj81.html",
               "https://v.qq.com/x/cover/mzc00200dfbfsrw/t00349mtirh.html",
               "https://v.qq.com/x/cover/mzc00200dfbfsrw/e0034uoncm6.html",
               "https://v.qq.com/x/cover/mzc00200dfbfsrw/e0034zp847t.html",
               "https://v.qq.com/x/cover/mzc00200dfbfsrw/i0034lrbepa.html",
               "https://v.qq.com/x/cover/mzc00200dfbfsrw/j0034aszidf.html",
               "https://v.qq.com/x/cover/mzc00200dfbfsrw/o0034tpld4v.html",
               "https://v.qq.com/x/cover/mzc00200dfbfsrw/g0034i7sk8j.html",
               "https://v.qq.com/x/cover/mzc00200dfbfsrw/g0034l9d8i8.html",
               "https://v.qq.com/x/cover/mzc00200dfbfsrw/r0034g7yz7o.html",
               "https://v.qq.com/x/cover/mzc00200dfbfsrw/c003430lihh.html",
               "https://v.qq.com/x/cover/mzc00200dfbfsrw/j0034kqrzp2.html",
               "https://v.qq.com/x/cover/mzc00200dfbfsrw/q0034bsvg68.html",
               "https://v.qq.com/x/cover/mzc00200dfbfsrw/r0034sn01t0.html",
               "https://v.qq.com/x/cover/mzc00200dfbfsrw/v00346pepn1.html",
               "https://v.qq.com/x/cover/mzc00200dfbfsrw/f0034651jha.html",
               "https://v.qq.com/x/cover/mzc00200dfbfsrw/e0034c7bfu1.html",
               "https://v.qq.com/x/cover/mzc00200dfbfsrw/a0034pe3gl1.html",
               "https://v.qq.com/x/cover/mzc00200dfbfsrw/v0034uffy2p.html",
               "https://v.qq.com/x/cover/mzc00200dfbfsrw/f003460jd4c.html",
               "https://v.qq.com/x/cover/mzc00200dfbfsrw/j0034dpydx8.html",
               "https://v.qq.com/x/cover/mzc00200dfbfsrw/k0034p8q0c4.html",
               "https://v.qq.com/x/cover/mzc00200dfbfsrw/g0034ahh6mm.html",
               "https://v.qq.com/x/cover/mzc00200dfbfsrw/s0034vjtbki.html",
               "https://v.qq.com/x/cover/mzc00200dfbfsrw/f00349xa3mm.html",
               "https://v.qq.com/x/cover/mzc00200dfbfsrw/t0034av7y4s.html",
               "https://v.qq.com/x/cover/mzc00200dfbfsrw/b003432oljk.html",
               "https://v.qq.com/x/cover/mzc00200dfbfsrw/r0034c8sxan.html",
               "https://v.qq.com/x/cover/mzc00200dfbfsrw/x0034nl7fdq.html",
               "https://v.qq.com/x/cover/mzc00200dfbfsrw/w0034cmgocm.html",
               "https://v.qq.com/x/cover/mzc00200dfbfsrw/o0034j49oe8.html",
               "https://v.qq.com/x/cover/mzc00200dfbfsrw/y0034o4nvag.html",
               "https://v.qq.com/x/cover/mzc00200dfbfsrw/o0034kvu2ip.html",
               "https://v.qq.com/x/cover/mzc00200dfbfsrw/w0034dungnq.html",
               "https://v.qq.com/x/cover/mzc00200dfbfsrw/f0034yfm2l8.html",
               "https://v.qq.com/x/cover/mzc00200dfbfsrw/p0034l5kdgm.html",
               "https://v.qq.com/x/cover/mzc00200dfbfsrw/n0034gkpobs.html"};
        for(String url_temp:url){
            String title = "";
            String body = getBody(url_temp);
            String pattern_title = "\"series_part_title\":\"([^\"]+)\"";
            Pattern r_title = Pattern.compile(pattern_title);
            Matcher m_title = r_title.matcher(body);
            if(m_title.find()){
                title = m_title.group(0);
            }

            String pattern = "\"view_all_count\":([0-9]+)";
            Pattern r = Pattern.compile(pattern);
            Matcher m = r.matcher(body);
            String out = title;
            while(m.find()){
                out=out+"   "+m.group(0);
            }
            System.out.println(out.replaceAll("\"view_all_count\":",""));
            //htmlToDisk(body);
            try{
                Thread.sleep(1000*2);
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }

}
