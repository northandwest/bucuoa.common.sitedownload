package downpage.post;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
/**
 * 抓取房子信息
 * @author wujiang3
 *1.手工获取列表json
 *2.抓取并保存成sql文件
 */
public class CrawlHouseInfomation {
	 public static final Gson gson = new Gson();  
	 
	public static void main(String[] args) {
		File file = new File("d:/clac/fang/data/json.txt");
		
		List<String> ids = new ArrayList<String>();
		try {
			String json = FileUtils.readFileToString(file);
			
			  List<Map<String, String>> datas = gson.fromJson(json, new TypeToken<List<Map<String, String>>>(){}.getType());
			  for(int i = 0 ;i < datas.size() ; i ++)
			  {
		
				  Map<String, String> mapData = datas.get(i);
				  {
						  ids.add(mapData.get("id"));
				  }
			  }
			  
		} catch (IOException e1) {
			e1.printStackTrace();
		}

//		String keys[] = new String[] { "1537", "1697" };

		for (String key : ids) {
			getDataAndSave(key);
			
			try {
				Thread.sleep(new Random().nextInt(2000));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private static void getDataAndSave(String key) {
		try {

			String html = crawl(key);
			if (html == null) {
				return;
			}

			String sql = parse(key, html);
			FileUtils.writeStringToFile(new File("d://clac//fangdata"), sql, true);
			System.out.println(sql);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static String crawl(String key) {
		String url = "http://xinjinqiao.tprtc.com/admin/main/pro!prodetail.do?id=" + key;
		String html = null;
		try {
			html = JsoupUtil.httpGet(url);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return html;
	}

	private static String parse(String key, String html) throws IOException {

		Document doc = Jsoup.parse(html);
		Map<String, String> map = new HashMap<String, String>();

		map.put("code", "body > table:nth-child(2) > tbody > tr:nth-child(1) > td.xmtd2");
		map.put("title", "body > table:nth-child(1) > tbody > tr > td");
		map.put("vendor", "body > table:nth-child(2) > tbody > tr:nth-child(2) > td.xmtd2");

		map.put("start_time", "body > table:nth-child(2) > tbody > tr:nth-child(9) > td.xmtd2");

		map.put("end_time", "body > table:nth-child(2) > tbody > tr:nth-child(10) > td.xmtd2");

		map.put("nums", "body > table:nth-child(3) > tbody > tr:nth-child(3) > td:nth-child(4)");

		map.put("money", "body > table:nth-child(4) > tbody:nth-child(2) > tr:nth-child(1) > td.xmtd2");

		map.put("proxy", "body > table:nth-child(4) > tbody:nth-child(2) > tr:nth-child(6) > td:nth-child(2)");

		map.put("proxy_user_name",
				"body > table:nth-child(4) > tbody:nth-child(2) > tr:nth-child(6) > td:nth-child(4)");

		map.put("proxy_user_tel", "body > table:nth-child(4) > tbody:nth-child(2) > tr:nth-child(6) > td:nth-child(6)");

		StringBuilder sb = new StringBuilder();
		StringBuilder sbfiled = new StringBuilder();
		StringBuilder sbvalue = new StringBuilder();

		sb.append("insert into fang (");
		sbvalue.append(" values (");
		for (Entry<String, String> entry : map.entrySet()) {
			Elements select = doc.select(entry.getValue());
			String value = "";
			try {

				value = select.text().trim();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			sbvalue.append("'").append(value).append("',");
			sbfiled.append(entry.getKey()).append(",");
		
		}

		sbfiled.append("keyx");
		sb.append(sbfiled);
		sb.append(")");
		
		sbvalue.append(key);
		sbvalue.append(")");
		sb.append(sbvalue).append(";\r\n");

		String sql = sb.toString();
		sql = sql.replace(",)", ")");
		sql = sql.replace(" ", "");
		sql = sql.replace("（万元）", "");
		return sql;
	}
	
	private boolean validater(Entry<String, String> entry,String value)
	{
		boolean isOk = true;
		if(value == null || value.equals(""))
		{
			isOk = false;
		}
		
		return isOk;
	}

}
