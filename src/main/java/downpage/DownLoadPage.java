package downpage;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class DownLoadPage {

	public static void main(String[] args) {
		try {
			String url = "https://bj.lianjia.com/api/headerSearch?channel=ershoufang&cityId=110000&keyword=%E7%94%B0%E5%9B%AD%E9%A3%8E%E5%85%89";
			Document doc2 = Jsoup.connect(url).userAgent("Mozilla")
					.cookie("auth", "token").timeout(3000).get();
//			parsarCssImgUrl(doc2);
			System.out.println(doc2.toString()+"---->ok<----");  

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void parsarCssImgUrl(Document doc2) {
		String csscontent = doc2.toString();
		csscontent = csscontent.replace(" ", "");
		csscontent = csscontent.replace("\r", "");
		csscontent = csscontent.replace("\n", "");
		csscontent = csscontent.replace("(", "<");
		csscontent = csscontent.replace(")", ">");
		csscontent = csscontent.replace("..", "--");
		System.out.println(csscontent);  

		
		String regEx = "<--(.*?)>";  
		String s = csscontent;  
		Pattern pat = Pattern.compile(regEx);  
		Matcher mat = pat.matcher(s);  
//			boolean rs = mat.find();  
		System.out.println("---count--->"+mat.groupCount());
//			for(int i=1;i<=mat.groupCount();i++)
		int i=0;
		while(mat.find())
		{
		String src = mat.group();
		src = src.replace("<", "");
		src = src.replace(">", "");
		src = src.replace("--", "..");
		System.out.println(i+"---->"+src);  
		i++;
		}
	}

}
