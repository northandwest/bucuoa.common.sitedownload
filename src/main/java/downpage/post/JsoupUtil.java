package downpage.post;

import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;

import org.jsoup.Connection;
import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class JsoupUtil {
	
	/**
	 * 发送get请求获取返回body
	 * @param url  请求地址
	 * @param cookie  请求cookie
	 * @return
	 * @throws IOException
	 */
	public static String httpGet(String url,String cookie) throws IOException{
		//获取请求连接
		Connection con = Jsoup.connect(url);
		//请求头设置，特别是cookie设置
		con.header("Accept", "text/html, application/xhtml+xml, */*"); 
		con.header("Content-Type", "application/x-www-form-urlencoded");
		con.header("User-Agent", "Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; WOW64; Trident/5.0))"); 
		con.header("Cookie", cookie);
		//解析请求结果
		Document doc=con.get(); 
		//获取标题
		//System.out.println(doc.title());
		return doc.toString(); 
	}
	
	
	public static String httpGet(String url) throws IOException{
		//获取请求连接
		Connection con = Jsoup.connect(url);
		//请求头设置，特别是cookie设置
		con.header("Accept", "text/html, application/xhtml+xml, */*"); 
		con.header("Content-Type", "application/x-www-form-urlencoded");
		con.header("User-Agent", "Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; WOW64; Trident/5.0))"); 

		//解析请求结果
		Document doc=con.get(); 
		//获取标题
		//System.out.println(doc.title());
		return doc.toString(); 
	}
	
	/**
	 * 发送get请求获取返回headers具体值
	 * @param url  请求地址
	 * @param cookie  请求cookie
	 * @return
	 * @throws IOException
	 */
	public static String httpGetHeader(String url,String cook,String cookieName) throws IOException{
		//获取请求连接
		Connection con = Jsoup.connect(url);
		//请求头设置，特别是cookie设置
		con.header("Accept", "text/html, application/xhtml+xml, */*"); 
		con.header("Content-Type", "application/x-www-form-urlencoded");
		con.header("User-Agent", "Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; WOW64; Trident/5.0))"); 
		con.header("Cookie", cook);
		//发送请求
		Response resp=con.method(Method.GET).execute();		
		//获取cookie名称为__bsi的值
		String cookieValue = resp.cookie(cookieName);
//		System.out.println("cookie  __bsi值：  "+cookieValue);
//		//获取返回cookie所值
//		Map<String,String> cookies = resp.cookies();
//		System.out.println("所有cookie值：  "+cookies);
//		//获取返回头文件值
//		String headerValue = resp.header(header);
//		System.out.println("头文件"+header+"的值："+headerValue);
//		//获取所有头文件值
//		Map<String,String> headersOne =resp.headers();
//		System.out.println("所有头文件值："+headersOne);
		return cookieValue; 
		
	}
	
	/**
	 * 发送post请求获取返回body
	 * @param url  请求地址
	 * @param map  请求参数
	 * @param cookie  请求cookie
	 * @return
	 * @throws IOException
	 */
	public static String httpPost(String url,Map<String,String> map,String cookie) throws IOException{
		//获取请求连接
		Connection con = Jsoup.connect(url);
		//遍历生成参数
		if(map!=null){
			for (Entry<String, String> entry : map.entrySet()) { 	
	           //添加参数
				con.data(entry.getKey(), entry.getValue());
	           } 
		}
		//插入cookie（头文件形式）
		con.header("Accept", "text/html, application/xhtml+xml, */*"); 
		con.header("Content-Type", "application/x-www-form-urlencoded");
		con.header("User-Agent", "Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; WOW64; Trident/5.0))"); 
		con.header("Cookie", cookie);
		Document doc = con.post();  
		return doc.toString();
	}
	
	
	
	/**
	 * 发送post请求获取返回头文件获这cookie
	 * @param url  请求地址
	 * @param map  请求参数
	 * @param cookie  请求cookie
	 * @param header  返回获取cookie或者头文件
	 * @return
	 * @throws IOException
	 */
	public static String httpPost(String url,Map<String,String> map,String cookie,String header) throws IOException{
		//获取请求连接
		Connection con = Jsoup.connect(url);
		//遍历生成参数
		if(map!=null){
			for (Entry<String, String> entry : map.entrySet()) { 	
	           //添加参数
				con.data(entry.getKey(), entry.getValue());
	           } 
		}
		con.header("Accept", "text/html, application/xhtml+xml, */*"); 
		con.header("Content-Type", "application/x-www-form-urlencoded");
		con.header("User-Agent", "Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; WOW64; Trident/5.0))"); 
		//插入cookie（头文件形式）
		con.header("Cookie", cookie);		
		//发送请求
		Response resp=con.method(Method.POST).execute();		
		//获取cookie名称为__bsi的值
		String cookieValue = resp.cookie(header);
		System.out.println(cookieValue);
		return cookieValue;
	}
	
	

}
