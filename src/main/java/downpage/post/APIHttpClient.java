package downpage.post;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class APIHttpClient {

	// 接口地址
	private static String apiURL = "http://xinjinqiao.tprtc.com/admin/main/pro!prodetail.do?id=1744";
	private Log logger = LogFactory.getLog(this.getClass());
	private static final String pattern = "yyyy-MM-dd HH:mm:ss:SSS";
	private HttpClient httpClient = null;
	private HttpPost method = null;
	private long startTime = 0L;
	private long endTime = 0L;
	private int status = 0;

	/**
	 * 接口地址
	 * 
	 * @param url
	 */
	public APIHttpClient(String url) {

		if (url != null) {
			this.apiURL = url;
		}
		if (apiURL != null) {
			httpClient = new DefaultHttpClient();
			method = new HttpPost(apiURL);

		}
	}

	/**
	 * 调用 API
	 * 
	 * @param parameters
	 * @return
	 */
	public String post(String parameters) {
		String body = null;
		logger.info("parameters:" + parameters);

		if (method != null & parameters != null && !"".equals(parameters.trim())) {
			try {

				// 建立一个NameValuePair数组，用于存储欲传送的参数
				method.setHeader("Content-type", "application/json; charset=utf-8");
				method.setHeader("Accept", "application/json");
				method.setHeader("Host", "xinjinqiao.tprtc.com");
				method.setHeader("Origin", "http://xinjinqiao.tprtc.com");
				method.setHeader("Referer", "http://xinjinqiao.tprtc.com/admin/main/flrpro.do");
				method.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.103 Safari/537.36");
				method.setHeader("X-Requested-With", "XMLHttpRequest");
				
				method.setEntity(new StringEntity(parameters, Charset.forName("UTF-8")));
				startTime = System.currentTimeMillis();

				HttpResponse response = httpClient.execute(method);

				endTime = System.currentTimeMillis();
				int statusCode = response.getStatusLine().getStatusCode();

				logger.info("statusCode:" + statusCode);
				logger.info("调用API 花费时间(单位：毫秒)：" + (endTime - startTime));
				if (statusCode != HttpStatus.SC_OK) {
					logger.error("Method failed:" + response.getStatusLine());
					status = 1;
				}

				// Read the response body
				body = EntityUtils.toString(response.getEntity());

			} catch (IOException e) {
				// 网络错误
				status = 3;
			} finally {
				logger.info("调用接口状态：" + status);
			}

		}
		return body;
	}

	public static void main(String[] args) {
		APIHttpClient ac = new APIHttpClient(apiURL);
		JsonArray arry = new JsonArray();
		JsonObject j = new JsonObject();

		j.addProperty("name", "flr");
		j.addProperty("nowpage", "1");
		j.addProperty("pagesize", "40");

		arry.add(j);
		System.out.println(ac.post(arry.toString()));
	}

	/**
	 * 0.成功 1.执行方法失败 2.协议错误 3.网络错误
	 * 
	 * @return the status
	 */
	public int getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(int status) {
		this.status = status;
	}

	/**
	 * @return the startTime
	 */
	public long getStartTime() {
		return startTime;
	}

	/**
	 * @return the endTime
	 */
	public long getEndTime() {
		return endTime;
	}
}