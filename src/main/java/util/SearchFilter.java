/**
 * 
 */
package util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

/**
 * 检索的清洗工具类
 * 
 */
public class SearchFilter {

	/**
	 * 爬虫字样列表,后续需考虑黑名单
	 */
	private static final String[] SPIDER_NAMES = { "spider",
			"apache-httpclient", "jakartacommons-httpclient", "bot",
			"winhttprequest", "python-urllib", "yahoo!slurp", "spi_der" };

	/**
	 * 判断是否是爬虫
	 * 
	 * @param userAgent
	 * @return
	 */
	public static boolean isSpider(String userAgent) {
		userAgent = userAgent.toLowerCase().replaceAll(" ","");
		for (String str : SPIDER_NAMES) {
			if (userAgent.indexOf(str) > -1) {
				return true;
			}
		}
		return false;
	}

	/**j
	 * 判断adjson串是否为空
	 * 
	 * @param adjson
	 * @return
	 */
	public static boolean isEmptyAdjson(String adjson) {

		// return false; // for test
		// 下面是线上真正要用的

		List<String> seg1 = StringUtil.findAllMarach("\\{(.*?)\\}", adjson);
		for (String seg : seg1) {
			String a = StringUtil.findMarach("\"a\"\\:\\[(.*?)\\]", seg);
			if (!a.trim().equals("")) {
				String[] seg2 = a.split(",");
				for (String strad : seg2) {
					if (!strad.trim().equals("")) {
						return false;
					}
				}
			}
		}
		return true;
	}

	public static void main1(String[] args) throws IOException {
		String path = "/demo1.log";
		BufferedReader in = new BufferedReader(new InputStreamReader(
				SearchFilter.class.getResourceAsStream(path)));
		String line = "";
		while ((line = in.readLine()) != null) {
			System.out.println(isEmptyAdjson(line));
		}
	}
}
