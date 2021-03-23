import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class TestUrl {

    /**
     * @param args
     */
    public static void main(String[] args) {
        String url = "https://weibo.com/aj/v6/comment/big?ajwvr=6&id=4611834257084538&root_comment_max_id_type=0&root_comment_ext_param=&page=10&filter=hot&filter_tips_before=0&from=singleWeiBo";
        String json = loadJSON(url);
        System.out.println(json);
    }

    public static String loadJSON(String url) {
        StringBuilder json = new StringBuilder();
        try {
            URL oracle = new URL(url);
            URLConnection yc = oracle.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    yc.getInputStream(),"utf-8"));//防止乱码
            String inputLine = null;
            while ((inputLine = in.readLine()) != null) {
                json.append(inputLine);
            }
            in.close();
        } catch (MalformedURLException e) {
        } catch (IOException e) {
        }
        return json.toString();
    }
}