package util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {

    public static boolean isEmpty(String str) {
        return str == null || str.trim().length() == 0 || str.trim().equals("-") || str.trim().equals("null");
    }

    public static boolean isNull(String str) {
        return str == null || str.trim().length() == 0 || str.trim().equals("-") || str.trim().equals("0") || str.trim().equals("null");
    }

    public static boolean isNum(String str) {
        return str != null &&  str.trim().length() > 0 && str.trim().matches("\\d{1,}") ;
    }

    public static boolean isDouble(String str) {
        if(str==null || str.trim().length()==0){
            return false;
        }else{
            if(str.contains(".")){
                return Pattern.compile("([1-9]+[0-9]*|0)(\\.[\\d]+)?").matcher(str).matches();
            }else{
                return str.trim().matches("\\d{1,}") ;
            }
        }
    }

    public static String isnull(String src,String defaultvalue) {
        if(src == null || src.trim().equals("") || src.trim().equals("null") || src.trim().equals("-")){
            return defaultvalue;
        }else{
            return src;
        }
    }

    public static String isnull(String src) {
        if(src == null || src.trim().equals("") || src.trim().equals("null") || src.trim().equals("-")){
            return "0";
        }else{
            return "1";
        }
    }

    public static int parseInt(String src,int defaultvalue){
        if(isNum(src)){
            return Integer.parseInt(src);
        }else{
            return defaultvalue;
        }
    }

    public static long parseLong(String src,long defaultvalue){
        if(isNum(src)){
            return Long.parseLong(src);
        }else{
            return defaultvalue;
        }
    }

    public static byte parseByte(String src,byte defaultvalue){
        if(isNum(src)){
            return Byte.parseByte(src);
        }else{
            return defaultvalue;
        }
    }

    public static double parseDouble(String src,double defaultvalue){
        if(isDouble(src)){
            return Double.parseDouble(src);
        }else{
            return defaultvalue;
        }
    }

    public static String findMarach(String partten, String content) {
        Matcher m2 = Pattern.compile(partten,
                Pattern.DOTALL | Pattern.CASE_INSENSITIVE).matcher(content);
        String strret = "-";
        while (m2.find()) {
            strret = m2.group(1);
            break;
        }
        // System.out.println(strret);
        return strret;
    }

    public static List<String> findAllMarach(String partten, String content) {
        Matcher m2 = Pattern.compile(partten,
                Pattern.DOTALL | Pattern.CASE_INSENSITIVE).matcher(content);
        List<String> list = new ArrayList<String>();
        while (m2.find()) {
            list.add(m2.group(1));
        }
        return list;
    }

    /**替换非法字符
     * @param inputString
     * @return
     */
    public static String replaceIllegalChar(String inputString) {
        if(inputString == null || inputString.length() == 0) {
            return "";
        }
        String outputString = inputString;
        outputString = outputString.replace(" ", "");
        outputString = outputString.replace("\001", "");
        outputString = outputString.replace("\r", "");
        outputString = outputString.replace("\n", "");
        return outputString;
    }

    public static void main(String[] args) {
        String [] str = "1412113214466.".split("\\.");
        System.out.println(str.length);
        String str2 = str[0]+str[1];
        System.out.println(str2);
        Long time = Long.parseLong(str2);
        System.out.println(time);
    }
}
