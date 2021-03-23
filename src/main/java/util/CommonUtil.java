package util;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.regex.Pattern;

public class CommonUtil {
	@SuppressWarnings("rawtypes")
	public static Map<String,String> loadIpBlackList(Mapper.Context context) {
		BufferedReader br = null;
		Map<String,String> ipBlackListMap = new HashMap<String,String>();
		try {
			@SuppressWarnings("deprecation")
			Path[] files = context.getLocalCacheFiles();
			for (Path path : files) {
				br = new BufferedReader(new FileReader(path.toString()));
				String line = null;
				while ((line = br.readLine()) != null) {
					String[] parts = line.split("\t");
					if (parts.length < 4) {
						continue;
					}
					String ip = parts[0];
					String type = parts[1];
					if (ipBlackListMap.containsKey(ip)) {
						if ("1".equals(type)) {
							ipBlackListMap.put(ip, type);
						}
					} else {
						ipBlackListMap.put(ip, type);
					}
				}
			}
			
		} catch (Exception e) {
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {}
			}
		}
		return ipBlackListMap;
	}

	public static String getInfoTypeJsonValue(String inStr,String cate){
		if(isJsonObject(inStr)){
			JSONObject object = JSONObject.parseObject(inStr);
			if("9224".equals(cate) && object.containsKey("111566")){
				return object.getString("111566");
			}
			if("13941".equals(cate) && object.containsKey("111567")){
				return object.getString("111567");
			}
		}
		return "";
	}

	public static boolean isJsonObject(String content) {
		if(StringUtils.isBlank(content))
			return false;
		try {
			JSONObject object = JSONObject.parseObject(content);
			if(null == object){
				return false;
			}
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public static int getValidState(int platform, String ip, Map<String,String> ipBlackListMap) {
		int validst = 1;
		if (StringUtils.isBlank(ip)) {
			validst = 0;
		} else {
			if (platform == 1) {
				if (ipBlackListMap.containsKey(ip)) {
					validst = 0;
				}
			} else if (platform == 2) {
				if (ipBlackListMap.containsKey(ip)) {
					validst = 0;
				}
			} else if (platform == 3) {
				if (ipBlackListMap.containsKey(ip)) {
					validst = 0;
				}
			} else {
				if ("1".equals(ipBlackListMap.get(ip))) {
					validst = 0;
				}
			}
		}
		return validst;
	}
	
	public static int getValidState(String platform, String ip, Map<String,String> ipBlackListMap) {
		int validst = 1;
		if (StringUtils.isBlank(ip)) {
			validst = 0;
		} else {
			if ("pc".equals(platform)) {
				if (ipBlackListMap.containsKey(ip)) {
					validst = 0;
				}
			} else if ("app".equals(platform)) {
				if (ipBlackListMap.containsKey(ip)) {
					validst = 0;
				}
			} else if ("m".equals(platform)) {
				if (ipBlackListMap.containsKey(ip)) {
					validst = 0;
				}
			} else {
				if ("1".equals(ipBlackListMap.get(ip))) {
					validst = 0;
				}
			}
		}
		return validst;
	}
	
	public static String safeString(String str){
		if(null==str||str.length()==0){
			return "-";
		}else {
			return str;
		}
	}
	
	/**
	 * 获取文件输出路径
	 * @param path
	 * @param data
	 * @return
	 */
	public static final int LIANMENG=1;
	public static final int AS=2;
	
	/**
	 * input:scate1,paramStr,格式:key1:value1,key2:value2,key3:value3..
	 * output:符合条件的value1,value2
	 */
	public static String getItemScate3(String scate1,String scate2, String paramStr) {
		if (StringUtil.isEmpty(scate1) || StringUtil.isEmpty(paramStr)) {
			return Constant.DEFAULTSTR;
		}
		if(Constant.find(Constant.needParams,scate1,scate2)){
			String[] paramArrayList = paramStr.split(",");
			for (String eachParam : paramArrayList) {
				String[] paramList = eachParam.split(":");
				String paramKey = paramList[0];
				if (Constant.paramSet.contains(paramKey)) {
					return eachParam.replace(":", "v");
				}
			}
		}
		return Constant.DEFAULTSTR;
	}
	public static String getOutputPath(String path , String data){
		String outputPath = path;
		String[] str = path.split("/");
		if(str[str.length-1].startsWith("201")){
			if(!outputPath.endsWith("/")){
				outputPath = outputPath+"/";
			}
			return outputPath;
		}else{
			if(outputPath.endsWith("/")){
				outputPath = outputPath+""+data+"/";
			}else{
				outputPath = outputPath+"/"+data+"/";
			}
		}
		return outputPath;
	}
	
	/**
	 * 标准化类别和城市路径
	 * @param path
	 * @param max
	 * @param linker
	 * @param cityCateUtil
	 * @param kind
	 * @return
	 */

	/**
	 * 标准化类别和城市路径时的函数，
	 * 按输入长度和分隔符标准化路径字符串
	 * @param path
	 * @param max
	 * @param linker
	 * @return
	 */
	public static String generateKey(String path,int max,String linker){
		if(path==null){
			path = "";
		}
		String[] ret = new String[max];
		String[] arg = path.split(",");
		for(int i=0;i<ret.length;i++){
			if(i<arg.length){
				if(arg[i] == null || arg[i].equals("")){
					ret[i] = "-";
				}else{
					ret[i] = arg[i];
				}
			}else{
				ret[i] = "-";
			}
		}
		String rets = "";
		for(int i=0;i<ret.length;i++){
			if(i==0){
				rets=ret[i];
			}else{
				rets+=linker+ret[i];
			}
		}
		return rets;
	}

    
    public static String sortParam(String param) {
    	if("-".equals(param) || "".equals(param)  || param == null){
    		return "-";
    	}
    	if(param.indexOf(',')>0){
        	String[] params = param.split(",");
    		List<String> list = new ArrayList<String>();
    		String[] pId = null;
        	for(String p:params){
        		pId = p.split(":");
        		list.add(pId[0]);
        	}
        	Collections.sort(list);
        	StringBuffer sb = new StringBuffer();
        	for(String p:list){
        		sb.append(p).append(',');
        	}
        	sb.deleteCharAt(sb.length()-1);
        	return sb.toString();
    	}else{
    		String[] pId = param.split(":");
    		return pId[0];
    	}
	}
    
    public static String sortParamValue(String param) {
    	if("-".equals(param) || "".equals(param)  || param == null){
    		return "-";
    	}
    	if(param.indexOf(',')>0){
        	String[] params = param.split(",");
    		List<String> list = new ArrayList<String>();
        	for(String p:params){
        		list.add(p);
        	}
        	Collections.sort(list);
        	StringBuffer sb = new StringBuffer();
        	for(String p:list){
        		sb.append(p).append(',');
        	}
        	return ","+sb.toString();
    	}else{
    		return ","+param+",";
    	}
	}
    
    /**
     * 通过refer获取页码
     * @param refer
     * @return
     */
    public static String getPn(String refer) {
    	if(refer == null || "".equals(refer)){
    		return "-";
    	}
    	return StringUtil.findMarach("/pn(\\d{1,}?)/", refer);
	}
    
    /**
     * 通过refer获取页面类型
     * @param refer
     * @return
     */
    public static int getPage_type(String refer) {
    	int page_type = 0;
    	if(refer!=null){
    		refer = refer.split("\\?")[0];
    	}else{
    		return page_type = 0;
    	}
    	if(refer.indexOf("x.shtml")>-1){
			page_type = 1;
		}
    	return page_type;
	}
    
    
    /**
     * 列表页和详情页，智能列表页第一页是首页，精准没有单元参数的列表页是首页
     * @param source
     * @param refer
     * @param page
     * @param param
     * @return
     */
    public static int getisindex(String source,String refer,String page,String param) {
    	int isindex = 0;
    	if(refer!=null){
    		refer = refer.split("\\?")[0];
    	}else{
    		return isindex;
    	}
    	if("1".equals(source)){
    		if((param.equals("-") || param.equals(""))
    				&& refer.indexOf("x.shtml")==-1){
				isindex  = 1;
			}
		}else{
			if(( page.equals("-") || page.equals("1") || page.equals(""))  
					&& ( refer.indexOf("x.shtml")==-1)){
				isindex  = 1;
			}
		}
    	return isindex;
	}
    /**
     * 
     * @param source
     * @param refer
     * @param page
     * @param param
     * @param citypath
     * @param catepath
     * @param spliter
     * @return
     */
    public static int getisindex2(String source,String refer,String page,String param,String citypath,String catepath,String spliter) {
    	int isindex = getisindex(source,refer,page,param);
    	if(citypath==null || catepath==null ){
    		return isindex;
    	}
    	String[] citypath_array = citypath.split(spliter);
    	String[] catepath_array = catepath.split(spliter);
    	String city1 = "";
    	String cate2 = "";
    	if(spliter.equals(",")){
    		if( citypath_array.length> 1  ){
        		isindex = 0;
        	}else if(catepath_array.length> 2 ){
        		isindex = 0;
        	}
    	}else{
    		if( citypath_array.length> 1 &&  !citypath_array[1].equals("-")  ){
        		isindex = 0;
        	}else if(catepath_array.length> 2  &&  !catepath_array[2].equals("-")  ){
        		isindex = 0;
        	}
    	}
    	
    	if(source.equals("0")){
    		if (citypath_array.length>0){
    			city1 = citypath_array[0];
    		}
    		if (catepath_array.length>1){
    			cate2 = catepath_array[1];
    		}
    		String cityname = Constant.dispCity_listnameMap.get(city1);
    		String catename = Constant.dispCate_listnameMap.get(cate2);
    		//http://bj.58.com/dazhong/
    		if(refer.equals("http://"+cityname+".58.com/"+catename)
    				|| refer.equals("http://"+cityname+".58.com/"+catename+"/")){
    			isindex = 1;
    		}
    	}
    	
    	return isindex;
	}
    
    /**
     * 处理是否扩展
     * 1：扩展   0：否扩展
     * @param isextend
     * @return
     */
    public static String getisextend(String isextend){
    	if(isextend==null || isextend.equals("") ||  isextend.equals("-")  ||  isextend.equals("0")||isextend.equals("false")){
    		isextend = "0";
    	}else if(isextend.equals("1") ||isextend.equals("true")){
    		isextend = "1";
    	}
    	//isextend = isextend.toLowerCase();
    	return isextend ;
    }
    
    public static boolean isMultipath(String path){
    	if(path==null || path.contains("^") || path.equals("")|| path.contains("-")){
    		return false;
    	}
    	
    	boolean isNumberFlag = !Pattern.compile("(?i)[a-z]").matcher(path).find();
    	if(!isNumberFlag){
    		return false;
    	}
    	return true;
    }
  
    /**
     * 合并城市或类别的路径
     * 
     * @param a
     * @param linker
     * @return
     */
    public static String  findcommon(String[] a,String linker){
    	 if(a==null || a.length==0){
    		 return "";
    	 }
    	 String str = "";
    	 String[] first = a[0].split(linker);
    	 for(int i=0;i<first.length;i++){
    		 String other = first[i];
    		 boolean isequal = true;
    		 for(int j=1;j<a.length;j++){
    			 String[] b = a[j].split(linker); 
    			 if(!b[i].equals(first[i])){
    				 isequal = false;
    			 }
    			 other +="|"+b[i];
    		 }
    		 if(isequal){
    			 str+=linker+first[i];
    		 }else{
    			 str+=linker+other;
    		 }
    	 }
    	 if(str.startsWith(linker)){
    		 str = str.substring(1);
    	 }
    	 return str;
    }
    
    /**
     * 测试函数
     * @param args
     */
    public static void main(String[] args) {
        String value = "";
		System.out.println(isJsonObject(value));
		//System.out.println(getInfoTypeJsonValue(value,"9224")+"!");
    }
    
}
