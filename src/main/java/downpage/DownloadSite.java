package downpage;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class DownloadSite {

	public static void main(String[] args) throws MalformedURLException,
			IOException {
		// tt1();
//		String strUtf8 = "\u4e2d\u56fd\u4f01\u4e1a\u5bb6\u6742\u5fd7"; 
//		System.out.println(strUtf8); 
		tt2();
	}
	static String root ="d:\\temp\\vote\\index";
	private static void tt2() throws IOException, MalformedURLException {
		String url = "http://www.omlzz.com/index.php/toupiao/h5/index?vid=08xmwqyw9pegmo9";

		Document doc = Jsoup.connect(url).userAgent("Mozilla").timeout(3000).get();
		
		initdir();
		
		Elements imgs = doc.getElementsByTag("img");
		loadImgs(imgs);
		
		Elements links = doc.getElementsByTag("link");
		loadcss(links);
		
		
		Elements script = doc.getElementsByTag("script");
		loadjs(script);
		
		modifyHtml(imgs);
		modifyHtmlCss(links);
		modifyHtmlJs(script);
		
		FileUtils.writeStringToFile(new File(root+"/"+getFileNameBy(url)),doc.html());

	}

	private static void loadjs(Elements script) {
		
		for (Element link : script) {
			String src = link.attr("abs:src");
			System.out.println("开始下载js" + src);
			if(src == null || src.equals(""))
			{
				continue;
			}
			try {

				downloandString((src),"js");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	
	}

	private static void modifyHtmlJs(Elements script) {
		Iterator<Element> iterator=script.iterator();  
        while(iterator.hasNext()){  
            Element element=iterator.next();  
            String src=element.attr("src");//将所有的相对地址换为绝对地址;  
            element.attr("src","");//装换为  
            element.attr("src","js/"+getFileNameBy(src));//装换为  
        }
	}

	private static void loadcss(Elements links) {
		List data2 = new ArrayList();
		for (Element link : links) {
			
			String href = link.attr("abs:href");
			System.out.println("开始下载css" + href);
			if(data2.contains(href)){
				continue;
			}else
			{
				data2.add(href);
			}
			if(href == null || href.equals(""))
			{
				continue;
			}
			try {
				Document doc2 = Jsoup.connect(href).userAgent("Mozilla")
						.cookie("auth", "token").timeout(3000).get();
				// 抽取css里的 图片资源 
				 
//				String csscontent = doc2.toString();
				
				downloandString((href),"css");
				
				List data = parsarCssImgUrl(doc2);
				System.out.println(href+"-->"+data.size()+"图片");
				
				List downlist = new ArrayList();
				for(int i = 0; i < data.size(); i ++)
				{
					String imgurl = data.get(i).toString();
					if(!downlist.contains(imgurl))
					{
					System.out.println("下载"+i+","+href+"--->"+imgurl);
					downloandImage(imgurl);
					downlist.add(imgurl);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		
	}

	private static void modifyHtmlCss(Elements links) {
		Iterator<Element> iterator2 = links.iterator();  
        while(iterator2.hasNext()){  
            Element element=iterator2.next();  
            String src=element.attr("href");//将所有的相对地址换为绝对地址;  
            element.attr("href","");//装换为 
            element.attr("href","css/"+getFileNameBy(src));//装换为  
        }
	}

	private static void loadImgs(Elements imgs) {
		List data = new ArrayList();
		for (Element link : imgs) {
			String href = link.attr("abs:src");
			if(data.contains(href)){
				continue;
			}else
			{
				data.add(href);
			}
			System.out.println("开始下载图片" + href);
			if(href == null || href.equals(""))
			{
				continue;
			}
			try {
				downloandImage(href);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		
	}

	private static void modifyHtml(Elements imgs) {
		Iterator<Element> iterator3 = imgs.iterator();  
        while(iterator3.hasNext()){  
            Element element=iterator3.next();  
            String src=element.attr("src");//将所有的相对地址换为绝对地址;  
            element.attr("src","");//装换为  
            element.attr("src","images/"+getFileNameBy(src));//装换为  
        }
	}

	private static void initdir() {
		File dir2 = new File(root);
		if(!dir2.exists())
		{
			dir2.mkdirs();
		}
		
		File dir = new File(root+"\\images");
		if(!dir.exists())
		{
			dir.mkdir();
		}
		
		File jsdir = new File(root+"\\js");
		if(!jsdir.exists())
		{
			jsdir.mkdir();
		}
		
		File cssdir = new File(root+"\\css");
		if(!cssdir.exists())
		{
			cssdir.mkdir();
		}
	}
	
	private static String getDomainBy(String url){
		String domain = "";
		try {
			URL urlx = new URL(url);
			domain = "http://"+urlx.getHost();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return domain;
	}
	
	private static List<String> parsarCssImgUrl(Document doc2) {
		List<String> arr = new ArrayList();
		String csscontent = doc2.toString();
		csscontent = csscontent.replace(" ", "");
		csscontent = csscontent.replace("\r", "");
		csscontent = csscontent.replace("\n", "");
		csscontent = csscontent.replace("(", "<");
		csscontent = csscontent.replace(")", ">");
		csscontent = csscontent.replace("..", "--");
//		System.out.println(csscontent);  
		
		String regEx = "<--(.*?)>";  
		String s = csscontent;  
		Pattern pat = Pattern.compile(regEx);  
		Matcher mat = pat.matcher(s);  
//			boolean rs = mat.find();  
		System.out.println("---count--->"+mat.groupCount());
//			for(int i=1;i<=mat.groupCount();i++)
		int i=0;
		String baseUri = doc2.baseUri();
		System.out.println("..................<"+baseUri);
		while(mat.find())
		{
		String src = mat.group();
		src = src.replace("<", "");
		src = src.replace(">", "");
		src = src.replace("--",getDomainBy(baseUri));
		arr.add(src);
//		System.out.println(i+"---->"+src);  
		i++;
		}
		
		return arr;
	}

	private static String getFileNameBy(String url) {
		String fname = url.substring(url.lastIndexOf("/") + 1, url.length());
		if(fname == null || fname.equals(""))
		{
			fname=System.currentTimeMillis()+".html";
		}
		if(fname.indexOf("?")!=-1)
		{
			fname = fname.substring(0,fname.lastIndexOf("?"));
		}
		return fname;
	}

	public static void downloandImage(String url) {
		BasicCookieStore cookieStore = new BasicCookieStore();
		CloseableHttpClient httpclient = HttpClients.custom()
				.setDefaultCookieStore(cookieStore).build();
		CloseableHttpResponse response1 = null;

		HttpGet httpget = new HttpGet(url);
		
		InputStream inputStream = null;
		FileOutputStream fos=null;
		try {
			
			response1 = httpclient.execute(httpget);
			HttpEntity entity = response1.getEntity();
			inputStream = response1.getEntity().getContent();
			
			File fileDest = new File(getFileNameBy(url));

			fos = new FileOutputStream(root+"\\images\\"+fileDest);

			IOUtils.copy(inputStream, fos);

			EntityUtils.consume(entity);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally {

			IOUtils.closeQuietly(inputStream);

			IOUtils.closeQuietly(fos);

		}

	}
	
	public static void downloandString(String url,String type) {
		BasicCookieStore cookieStore = new BasicCookieStore();
		CloseableHttpClient httpclient = HttpClients.custom()
				.setDefaultCookieStore(cookieStore).build();
		CloseableHttpResponse response1 = null;
System.out.println("-------->"+url+"<----");
if(url == null || url.equals(""))
{
	return;
}
		HttpGet httpget = new HttpGet(url);
		
		InputStream inputStream = null;
		FileOutputStream fos=null;
		try {
			
			response1 = httpclient.execute(httpget);
			HttpEntity entity = response1.getEntity();
			inputStream = response1.getEntity().getContent();
			
			File fileDest = new File(getFileNameBy(url));

			fos = new FileOutputStream(root+"\\"+type+"\\"+fileDest);

			IOUtils.copy(inputStream, fos);

			EntityUtils.consume(entity);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally {

			IOUtils.closeQuietly(inputStream);

			IOUtils.closeQuietly(fos);

		}

	}

	private static void tt1() throws IOException, MalformedURLException {
		InputStream in5 = new URL("http://beijing.xinyao.com.cn/charity.html")
				.openStream();
		List<String> list = IOUtils.readLines(in5, "UTF-8");
		Iterator<String> iter = list.iterator();
		StringBuffer html = new StringBuffer();
		while (iter.hasNext()) {
			String s = iter.next();
			html.append(s);
			// System.out.println(s);
		}
		System.out.println(html.toString());
		FileUtils.writeStringToFile(new File("data.txt"), html.toString());
	}

}
