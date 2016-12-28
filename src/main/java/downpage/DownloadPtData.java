package downpage;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
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

public class DownloadPtData {

	public static void main(String[] args) throws MalformedURLException,
			IOException {
//		 tt4();
//		createAllUrl();
//		downlists();
//		downProductDetailPage();
//		getAllPageUrlList();
		start();
//		test();
//		ttk("1232");
	}
	static String root ="d:\\TEMP\\data2\\";
	
	private static void createAllUrl() throws IOException, MalformedURLException {

		List<String> hascount = FileUtils.readLines(new File("D:\\temp\\data\\pagesize.txt"), "UTF-8");
		Map map = new HashMap();
		
		StringBuffer sb = new StringBuffer();
		for(String tt: hascount)
		{
			String id = tt.split(",")[0];
			
			String pagesize = tt.split(",")[1];
			int size = Integer.parseInt(pagesize);
			
			for(int i =1 ; i<=size; i ++)
			{
				String urls = "http://beijing.xinyao.com.cn/category-"+id+"-b0-min0-max0-attr0-"+i+"-g.last_update-DESC.html,"+id+"\r\n";
				System.out.println(urls);
				sb.append(urls);
			}
					
		}
		
		FileUtils.writeStringToFile(new File(root+"/allurl.txt"),sb.toString());

	}
	
	private static void tt4() throws IOException, MalformedURLException {
		String url = "http://beijing.xinyao.com.cn/category-287-b0.html";

		Document doc = Jsoup.connect(url).userAgent("Mozilla").cookie("auth", "token").timeout(3000).get();
		
		Elements html = doc.getElementsByClass("nav_bg");
		Elements alink = html.select("a");

		List<String> hascount = FileUtils.readLines(new File("D:\\temp\\data\\pagesize.txt"), "UTF-8");
		Map map = new HashMap();
		for(String tt: hascount)
		{
			map.put(tt.split(",")[0],tt.split(",")[1]);
		}
		
		for(Element el: alink)
		{
			String href = el.absUrl("href");
			String attr = el.attr("href");
			attr = attr.replace("category-", "");
			attr = attr.replace("-b0.html", "");
			attr = attr.replace("/", "");
			
			if(!attr.equals(""))
			{
//				System.out.println(href+"-->"+attr+"."+el.text());
//				System.out.println("insert into cate (id,parent_id,name,type,status,city_id) values("+attr+",0,'"+el.text()+"',8,1,0);");
				tt5(href,attr,map);
			}
		}
//		

//		Elements imgs = html.getElementsByTag("img");
//		loadImgs(imgs);
		//dv_spmiaosu
//		
//		Elements links = doc.getElementsByTag("link");
//		loadcss(links);
//		
//		
//		Elements script = doc.getElementsByTag("script");
//		loadjs(script);
//		
//		modifyHtml(imgs);
//		modifyHtmlCss(links);
//		modifyHtmlJs(script);
		
//		FileUtils.writeStringToFile(new File(root+"/"+getFileNameBy(url)),html.html());

	}
	
	private static void test() throws IOException, MalformedURLException {
		String url = "http://beijing.xinyao.com.cn/category-345-b0.html";

		Document doc = Jsoup.connect(url).userAgent("Mozilla").cookie("auth", "token").timeout(3000).get();
		String size = getPageCount(doc);
		System.out.println(size);  
	}
	
	private static void tt5(String url,String parentId,Map map) throws IOException, MalformedURLException {
//		String url = "http://beijing.xinyao.com.cn/category-345-b0.html";

		Document doc = Jsoup.connect(url).userAgent("Mozilla").cookie("auth", "token").timeout(3000).get();
		
		Element html = doc.getElementById("TypeClassShow");
		Elements alink = html.select("a");
		
		
		for(Element el: alink)
		{
			String href = el.absUrl("href");
			String attr = el.attr("href");
			attr = attr.replace("category-", "");
			attr = attr.replace("-b0.html", "");

//			System.out.println("insert into cate (id,parent_id,name,type,status,city_id) values("+attr+","+parentId+",'"+el.text()+"',8,1,0);");
			Object object = map.get(attr);
			if(object == null)
			{
				ttk(href,attr);
			}
//			System.out.println(href+"-->"+attr+"."+el.text());

//			System.out.println(href+"-->"+attr+"."+el.text());
		}

	}
	
	private static void ttk(String url,String parentId) throws IOException, MalformedURLException {
//		String url = "http://beijing.xinyao.com.cn/category-345-b0.html";

		Document doc = Jsoup.connect(url).userAgent("Mozilla").cookie("auth", "token").timeout(3000).get();
//		System.out.println(doc.html());
		Elements html = doc.getElementsByClass("search_list");
		Elements alink = html.select("a");
		
		String size = getPageCount(doc);
		System.out.println(parentId+"-->"+size);
		
//		for(Element el: alink)
//		{
//			String tt = el.baseUri();
//			el.setBaseUri(tt);
//			String href = el.absUrl("href");
//
//			String attr = href;
//			attr = attr.replace("category-", "");
//			attr = attr.replace("-b0.html", "");
//			
//			if(!el.text().equals("") && !href.equals(""))
//			System.out.println(href+","+el.text()+","+parentId);
//		}

	}
	
	private static void downlists() throws IOException, MalformedURLException {
	
		String parent = "";
		String url = "";

		List<String> pageurls = FileUtils.readLines(new File("D:\\temp\\data\\allurl.txt"), "UTF-8");
		Map map = new HashMap();
		
		StringBuffer sb = new StringBuffer();
		
		int i = 0;
		for(String tt: pageurls)
		{
			 ++i;
			 
			 parent = tt.split(",")[1];
			 url 	= tt.split(",")[0];
			
			int random = new Random().nextInt(20000);
			try {
				
				Collection<File> javaGbkFileCol = FileUtils.listFiles(new File("d:/temp/allurl-product/"), new String[]{"txt"}, true);
				boolean has = false;
				for (File javaGbkFile : javaGbkFileCol) {
					String fname1 = javaGbkFile.getName();
					String tempx = fname1.replace(".txt", "");
					
					String fname = tempx.split("-")[0];
					String page = tempx.split("-")[1];
					
					String urlz = "http://beijing.xinyao.com.cn/category-"+fname+"-b0-min0-max0-attr0-"+page+"-g.last_update-DESC.html";
					if(urlz.equals(url))
					{
						has = true;
						break;
					}
				}
				if(!has)
				{
				Thread.sleep(random);
				System.out.println(url+",sleep--->"+random+",left page-->"+(pageurls.size()-i));
				}else
				{
					System.out.println("已经下载"+url);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			try {
				tt6(url,parent);
			} catch (Exception e) {
				System.out.println(url+","+parent+",error");
//				e.printStackTrace();
			}
					
		}
		
//		FileUtils.writeStringToFile(new File(root+"/allurl.txt"),sb.toString());
		
	}
	private static void tt6(String url,String parentId) throws IOException, MalformedURLException {
//		String url = "http://beijing.xinyao.com.cn/category-345-b0.html";

		Document doc = Jsoup.connect(url).userAgent("Mozilla").cookie("auth", "token").timeout(3000).get();
//		System.out.println(doc.html());
		Elements html = doc.getElementsByClass("search_list");
		Elements alink = html.select("a");
		
		StringBuffer sb  = new StringBuffer();
		for(Element el: alink)
		{
			String href = el.absUrl("href");

			String attr = href;
			attr = attr.replace("category-", "");
			attr = attr.replace("-b0.html", "");
			
			if(!el.text().equals("") && !href.equals(""))
			{
				sb.append(href+","+el.text()+","+parentId).append("\r\n");
			}
			
		}
		//http://beijing.xinyao.com.cn/category-346-b0-min0-max0-attr0-1-g.last_update-DESC.html
		
		String page = url;
		page = page.replace("http://beijing.xinyao.com.cn/category-"+parentId+"-b0-min0-max0-attr0-", "");
		page = page.replace("-g.last_update-DESC.html", "");
		
		FileUtils.writeStringToFile(new File("d:/temp/allurl-product/"+parentId+"-"+page+".txt"),sb.toString());
	}
	
	private static void getAllPageUrlList() throws IOException, MalformedURLException 
		{
		Collection<File> javaGbkFileCol = FileUtils.listFiles(new File("d:/temp/allurl-product/"), new String[]{"txt"}, true);
		boolean has = false;
		StringBuffer  all = new StringBuffer();
		for (File javaGbkFile : javaGbkFileCol) {
			String fname1 = javaGbkFile.getName();
			List<String> list = FileUtils.readLines(new File("d:/temp/allurl-product/"+fname1), "utf-8");
			for(String tt : list)
			{
				all.append(tt).append("\r\n");
			}
		}
		
		FileUtils.writeStringToFile(new File(root+"/allurllist.txt"),all.toString());
			
		}
	
	private static void start() throws IOException, MalformedURLException 
	{
		List<String> list = FileUtils.readLines(new File("d:/temp/data/allurllist.txt"), "utf-8");
		int i = 0;
		for(String tt : list)
		{
			String url = tt.split(",")[0];
			String cateId= tt.split(",")[2];
			++i;
			try {
				List<String> lines = FileUtils.readLines(new File(root+"/logs.txt"), "utf-8");
				
				int random = new Random().nextInt(20000);
				if(lines.contains(url))
				{
					System.out.println(url+",已经下载了");
					continue;
				}
				System.out.println(url+",sleep--->"+random+",left page-->"+(list.size()-i));

				Thread.sleep(random);
				downProductDetailPage(cateId,url);
				
				lines.add(url);
				FileUtils.writeLines(new File(root+"/logs.txt"),lines);
			} catch (Exception e) {
				e.printStackTrace();
				
				List<String> lines = FileUtils.readLines(new File(root+"/errors.txt"), "utf-8");
				lines.add(url);
				FileUtils.writeLines(new File(root+"/errors.txt"),lines);
			}
			
		}
		
		
		
	}
	
	private static void downProductDetailPage(String cateId ,String url) throws IOException, MalformedURLException {
//		String url = "http://beijing.xinyao.com.cn/goods-6977.html";
//		String cateId = "123";

		String id = url.replace("http://beijing.xinyao.com.cn/goods-", "").replace(".html", "");
		
		Document doc = Jsoup.connect(url).userAgent("Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.154 Safari/537.36").cookie("auth", "token").timeout(3000).get();
		
		String path = initdir(cateId,id);
		
		String rpath = cateId+"/"+id;
		
		StringBuffer info = new StringBuffer();
		
		Element html = doc.getElementById("item-content");
		
		Element drupinfo = doc.getElementById("uDrugsInfo");
		Elements dl = drupinfo.getElementsByTag("dd");
		
		Element dname = doc.getElementById("li_detail_drugname");
		Elements bigpic = doc.getElementsByClass("mainpic");
		Elements bpics = bigpic.select("a");
		StringBuffer imgk = new StringBuffer();
		for(Element pic :bpics)
		{
			String src = pic.attr("rel");
				imgk.append("{"+src.split(",")[1]+","+src.split(",")[2]).append(",");
		}
//		System.out.println(imgk.toString());
		
		info.append("id=").append(id).append(";").append("name=").append(dname.text()).append(";");
		info.append("cateId=").append(cateId).append(";");
		for(Element tt : dl)
		{
			String text = tt.text();
			if(text.indexOf("门店价格")!=-1)
			{
				text = text.substring(text.indexOf("门店价格")+6,text.indexOf("（此价实体店有效）"));
				info.append("price=").append(text).append(";");
			}
			if(text.indexOf("批准文号")!=-1)
			{
				text = text.replace("批准文号：", "");
				info.append("apply=").append(text).append(";");
			}
			if(text.indexOf("生产厂家")!=-1)
			{
				text = text.replace("生产厂家：", "");
				info.append("vendor=").append(text).append(";");
			}
			if(text.indexOf("规 格")!=-1)
			{
				text = text.replace("规 格：", "");
				info.append("unit=").append(text).append(";");
			}
			if(text.indexOf("通 用 名")!=-1)
			{
				text = text.replace("通 用 名：", "");
				info.append("unit=").append(text).append(";");
			}
//			System.out.println(text);
		}
		
		info.append("images=[").append(imgk).append("]");
//		System.out.println(info.toString());
//		doc.getElementsByClass("nav_bg)

		Elements imgs = html.getElementsByTag("img");
		loadImgs(imgs,path);
		modifyHtml(imgs,rpath);
		
		FileUtils.writeStringToFile(new File(path+"/"+id+".txt"),info.toString(),"utf-8");
		FileUtils.writeStringToFile(new File(path+"/"+getFileNameBy(url)),html.html(),"utf-8");
		
		String newurl =  "http://beijing.xinyao.com.cn/goods-"+id+"-p1.html";

		Document doc2 = Jsoup.connect(newurl).userAgent("Mozilla").cookie("auth", "token").timeout(3000).get();
		
		try {
			Element html2 = doc2.getElementById("item-content");
			FileUtils.writeStringToFile(new File(path+"/goods-"+id+"-p1.html"),html2.html(),"utf-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void tt2() throws IOException, MalformedURLException {
		String url = "http://beijing.xinyao.com.cn/goods-7502-p0.html";

		Document doc = Jsoup.connect(url).userAgent("Mozilla") //.header("Accept-Charset", "GBK")
				.cookie("auth", "token").timeout(3000).get();
		
		initdir("","");
		
		Elements imgs = doc.getElementsByTag("img");
		loadImgs(imgs);
		//dv_spmiaosu
//		String html = doc.getElementById("dv_spmiaosu").html();
		
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
		
		for (Element link : links) {
			
			String href = link.attr("abs:href");
			System.out.println("开始下载css" + href);
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
		
		for (Element link : imgs) {
			String href = link.attr("abs:src");
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
	
	private static void loadImgs(Elements imgs,String path) {
		
		for (Element link : imgs) {
			String href = link.attr("abs:src");
			System.out.println("开始下载图片" + href);
			if(href == null || href.equals(""))
			{
				continue;
			}
			try {
				downloandImage(href,path);
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
	
	private static void modifyHtml(Elements imgs,String path) {
		Iterator<Element> iterator3 = imgs.iterator();  
        while(iterator3.hasNext()){  
            Element element=iterator3.next();  
            String src=element.attr("src");//将所有的相对地址换为绝对地址;  
            element.attr("src","");//装换为  
            element.attr("src","images/"+getFileNameBy(src));//装换为  
        }
	}

	private static String initdir(String cateId,String id) {
		String url = "";
		if(!id.equals(""))
		{
			url += root + "\\"+cateId+"\\"+id;
		}
		File dir2 = new File(url);
		if(!dir2.exists())
		{
			dir2.mkdirs();
		}
		
		File dir = new File(url+"\\images");
		if(!dir.exists())
		{
			dir.mkdir();
		}
		
		File jsdir = new File(url+"\\js");
		if(!jsdir.exists())
		{
			jsdir.mkdir();
		}
		
		File cssdir = new File(url+"\\css");
		if(!cssdir.exists())
		{
			cssdir.mkdir();
		}
		return url;
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
	
	
	private static String getPageCount(Document doc2) {
		List<String> arr = new ArrayList();
		String csscontent = doc2.toString();
		String regEx = "共&nbsp;(.*?)&nbsp;页";  
		String size = "0";  
		Pattern pat = Pattern.compile(regEx);  
		Matcher mat = pat.matcher(csscontent);  
		int i=0;
		while(mat.find())
		{
		String src = mat.group();
		src = src.replace("共&nbsp;", "");
		src = src.replace("&nbsp;页", "");
		
		size = src;
		break;
		}
		
		return size;
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

	private static String getFileNameBy2(String url) {
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
	
	public static void downloandImage(String url,String path) {
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

			fos = new FileOutputStream(path+"\\images\\"+fileDest);

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
