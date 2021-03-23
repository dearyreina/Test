package util;


import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.compress.CompressionCodec;
import org.apache.hadoop.io.compress.CompressionCodecFactory;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;


public class Constant {
	public static Map<String, String> dispCity_listnameMap = new HashMap<String, String>();// localId,cityId
	public static Map<String, String> dispCate_listnameMap = new HashMap<String, String>();// localId,cityId
	public static Map<String, String> ipBlackList = new HashMap<String, String>();
	public static Map<String, String> innerIpList = new HashMap<String, String>();
	public static Set<String> paramSet = new HashSet<String>();
	public static Map<String, String> spmMap = new HashMap<String,String>();
	public final static String[] needParams = { "92", "28921","26506","8538","833","143","8512", "8581", "8640", "8703", "26508", "26509", "26510", "35945"};
	
	public static final String INPUT_MESSAGE_SEPERATER = ",";
	public static final String DELIMITER = "\001";
	public static final String TAB="\t";
	public static final String DEFAULTSTR = "-";
	public static final String VALIDST = "1";

	@SuppressWarnings("deprecation")
	public synchronized static void initParamMap(String daystr) throws IOException {
		try {
			if (paramSet.size() > 0) {
				return;
			}
			Configuration conf = new Configuration();
			FileSystem fs = FileSystem.get(conf);
			// 初始化配置文件
			String[] temp = null;
			Path f = new Path("/dsap/rawdata/cate_white_list/" + daystr + "/part-m-00000");
			FileStatus[] status = fs.listStatus(f);
			for (int i = 0; i < status.length; i++) {
				FSDataInputStream hdfsInStream = fs.open(status[i].getPath());
				String text;
				while ((text = hdfsInStream.readLine()) != null) {
					temp = text.split("\t");
//					if (temp.length >= 8) {
//						String cate1_id = temp[1];
//						if(find(needParams,cate1_id)){
//							String param_id = temp[3];
//							paramSet.add(param_id);
//						}
//					}
					if (temp.length >= 8) {
						String cate1_id = temp[1];
						String cate2_id = temp[2];
						if(find(needParams,cate1_id,cate2_id)){
							String param_id = temp[3];
							paramSet.add(param_id);
						}
					}
				}
				hdfsInStream.close();
			}
		} catch (Exception e) {
		}
	}

	public static long getValidLongNum(String in){
		try{
			if(StringUtils.isNumeric(in)){
				Long number = new Long(in);
				return number.longValue();
			}
		}catch (Exception e){
			return 0l;
		}
		return 0l;
	}

	public static int getValidIntNum(String in){
		try{
			if(StringUtils.isNumeric(in)){
				Integer number = new Integer(in);
				return number.intValue();
			}
		}catch (Exception e){
			return 0;
		}
		return 0;
	}

	public static float getValidFloatNum(String in){
		try{
			Float number = new Float(in);
			return number.floatValue();
		}catch (Exception e){
			return 0f;
		}
	}


	public static boolean find(String[] args,String cate){
		for(String arg:args){
			if(arg.equals(cate)){
				return true;
			}
		}
		return false;
	}
	
	public static boolean find(String[] args,String cate1,String cate2){
		if(cate2 != null && (cate2.equals("103") || cate2.equals("14502"))){
			return false;
		}
		for(String arg:args){
			if(arg.equals(cate1)){
				return true;
			}
		}
		return false;
	}
	
	@SuppressWarnings("deprecation")
	public synchronized static void initSpmMap(String daystr) throws IOException{
		try {
			if(spmMap.size()>0){
				return ;
			}
			Configuration conf = new Configuration();
			FileSystem fs = FileSystem.get(conf);
			// 初始化配置文件
			String[] temp = null;
			Path f = new Path("/flume/dsap/rawdata/lianmeng/spm_client_name/"+daystr);
			FileStatus[] status = fs.listStatus(f);
			for (int i = 0; i < status.length; i++) {
				FSDataInputStream hdfsInStream = fs.open(status[i].getPath());
				String text;
				while((text = hdfsInStream.readLine()) != null){
					temp = text.split("\t");
					if(temp.length>1){
						String spm = temp[0];
						spmMap.put(spm,"1");
					}
				}
				hdfsInStream.close();
			}
		} catch (Exception e) {
		}
	}
	

	
	public static InputStream getFileStream(Path path, Configuration conf)
			throws IOException {
		FileSystem fs = FileSystem.get(conf);
		CompressionCodecFactory factory = new CompressionCodecFactory(conf);
		CompressionCodec codec = factory.getCodec(path);
		if (null == codec) {
			return fs.open(path);
		} // 若文件无压缩会得到一个null，此时直接打开文件流
		else {
			return codec.createInputStream(fs.open(path)); //返回解压缩之后的文件流
		}
	}
	
	public synchronized static void initInnerIpList(Mapper.Context pContext) throws IOException{
		try {
			String datestr = pContext.getConfiguration().get("datestr");
			datestr = "20151127";
			Configuration conf = new Configuration();
			FileSystem fs = FileSystem.get(conf);
			String path1 = "/home/hdp_lbg_supin/rawdata/supin/ipblacklist/"+datestr+"/";
			Path f1 = new Path(path1);
			Vector<InputStream> vector1 = new Vector<InputStream>();
			FileStatus[] status1 = fs.listStatus(f1);
			for (int i = 0; i < status1.length; i++) {
				vector1.add(fs.open(status1[i].getPath()));
			}
			Enumeration<InputStream> e1 = vector1.elements();
		    SequenceInputStream sis1 = null;
		    sis1 = new SequenceInputStream(e1);
		    
			BufferedReader br = new BufferedReader(new InputStreamReader(sis1));
			String line = null;
			while ((line = br.readLine()) != null) {
				String[] arr = line.trim().split("\t");
				innerIpList.put(arr[0], "");
			}
			br.close();
		    
		} catch (Exception e) {
			e.printStackTrace();
			throw new IOException(e);
		}
	}
	

	
}
