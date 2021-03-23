import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.hive.ql.udf.UDFJson;
import org.apache.hadoop.io.Text;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test {
    public static void main(String[] args) throws IOException {
        String [] arr = {"a","b"};
        List<String> list = Arrays.asList(arr);
        for(String temp : list){
            System.out.println(temp);
        }

        int[] num = {1,2,3};
        List num_list = Arrays.asList(num);
        System.out.println(num_list.size());
        for(Object temp : num_list){
            System.out.println(temp);
        }
    }
}
