package downpage.post;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.jsoup.Connection;
import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class JsoupPost {

	public static void main(String[] args) {
		String cookie="JSESSIONID=963D45730F73A7DF6E477871BA10144D; _gscu_1531904469=83510898cuzj5k40; _gscs_1531904469=t83585242aie9io16|pv:4; _gscbrs_1531904469=1; Hm_lvt_cdeaf19da2aafc4215f925d58ce44a38=1483510899,1483581985,1483585242; Hm_lpvt_cdeaf19da2aafc4215f925d58ce44a38=1483586956";
//		String url = "http://xinjinqiao.tprtc.com/admin/main/pro!lrprolist.do?date="+new Date().getTime();
		String url ="http://xinjinqiao.tprtc.com/admin/main/flrpro.do";
		
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("name", "flr");
		map.put("nowpage", "1");
		map.put("pagesize", "40");
		
//		Accept:application/json, text/javascript, */*; q=0.01
//		Accept-Encoding:gzip, deflate
//		Accept-Language:zh-CN,zh;q=0.8
//		Connection:keep-alive
//		Content-Length:30
//		Content-Type:application/x-www-form-urlencoded; charset=UTF-8
//		Cookie:JSESSIONID=963D45730F73A7DF6E477871BA10144D; _gscu_1531904469=83510898cuzj5k40; _gscs_1531904469=t83585242aie9io16|pv:4; _gscbrs_1531904469=1; Hm_lvt_cdeaf19da2aafc4215f925d58ce44a38=1483510899,1483581985,1483585242; Hm_lpvt_cdeaf19da2aafc4215f925d58ce44a38=1483586956
//		Host:xinjinqiao.tprtc.com
//		Origin:http://xinjinqiao.tprtc.com
//		Referer:http://xinjinqiao.tprtc.com/admin/main/flrpro.do
//		User-Agent:Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.103 Safari/537.36
//		X-Requested-With:XMLHttpRequest
		
		try {
			String httpPost = httpPost(url,map,cookie);
			System.out.println(httpPost);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String httpPost(String url, Map<String, String> map, String cookie) throws IOException {
		// 获取请求连接
		Connection con = Jsoup.connect(url);
		
//		con.header("Accept", "application/json, text/javascript, */*; q=0.01");
        con.header("Accept", "text/html, application/xhtml+xml, */*"); 

		con.header("Accept-Encoding", "gzip, deflate");
		con.header("Accept-Language", "zh-CN,zh;q=0.8");
		con.header("Connection", "keep-alive");
		con.header("Content-Length", "30");
//		con.header("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        con.header("Content-Type", "text/*, application/xml");

//		con.header("Mimetype", "application/json;charset=UTF-8");
		con.header("Host", "xinjinqiao.tprtc.com");
		con.header("Origin", "http://xinjinqiao.tprtc.com");
		con.header("Referer", "http://xinjinqiao.tprtc.com/admin/main/flrpro.do");
		con.header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.103 Safari/537.36");
		con.header("X-Requested-With", "XMLHttpRequest");
		con.timeout(5000);
		// 遍历生成参数
		if (map != null) {
			for (Entry<String, String> entry : map.entrySet()) {
				// 添加参数
				con.data(entry.getKey(), entry.getValue());
			}
		}
		// 插入cookie（头文件形式）
		
		 Response resp=con.method(Method.POST).execute();        
		 Map<String, String> cookies = resp.cookies();
	     
	        System.out.println(cookies);
	        
		con.header("Cookie", cookie);
		Document doc = con.post();
		System.out.println(doc);
		return doc.toString();
	}

}
