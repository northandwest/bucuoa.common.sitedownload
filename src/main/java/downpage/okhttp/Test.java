package downpage.okhttp;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import downpage.json.KJsonUtils;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Test {
	final static Map<String, Object> map = new ConcurrentHashMap<String, Object>();

	public static void main(String[] args) {

		String[] keywords = new String[] { "æÿ’Û", "ÊÍ¡˙‘∞", "¡˙Ï˚", "ÃÔ‘∞∑Áπ‚","∫Õ–≥","»—©¥∫Ã√","∫ËÀ≥‘∞" };
		for(String kw : keywords)
		{
			try {
				kw = URLDecoder.decode(kw, "UTF-8");
			} catch (UnsupportedEncodingException e1) {
				e1.printStackTrace();
			}
			String url = "https://bj.lianjia.com/api/headerSearch?channel=ershoufang&cityId=110000&keyword=" + kw;
			try {
				Thread.sleep(new Random().nextInt(1000));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			searchKeyword(url);
		}
		
		Set<Entry<String, Object>> entrySet = map.entrySet();
		
		for(Entry<String, Object> entry : entrySet)
		{
			System.out.println(entry.getKey()+":"+entry.getValue());
		}

	}

	private static void searchKeyword(String url) {
		String content = getContent(url);

		try {
			Map<String, Object> rs = KJsonUtils.json2map(content);

			Map<String, Object> data = (Map<String, Object>) rs.get("data");

			List<Map<String, Object>> result = (List<Map<String, Object>>) data.get("result");

			for (Map<String, Object> t : result) {
				Object object = t.get("count");
				Object title = t.get("title");
				Object communityId = t.get("communityId");

				map.put(title.toString(), object);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static String getContent(String url) {
		OkHttpClient client = new OkHttpClient();
		Request request = new Request.Builder().url(url).build();
		Response response;
		try {
			response = client.newCall(request).execute();

			if (response.isSuccessful()) {
				String html = response.body().string();
				return html;
			} else {
				throw new IOException("Unexpected code " + response);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return "";
	}

}
