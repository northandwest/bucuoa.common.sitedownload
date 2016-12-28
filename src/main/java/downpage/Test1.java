package downpage;

import java.net.MalformedURLException;
import java.net.URL;

public class Test1 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String url="http://www.sxsoft.com/js/formValidatorRegex.js?v=2013111217";
		String fname = getDomainBy(url);//url.substring(url.lastIndexOf("/") , url.length());
//		if(fname == null || fname.equals(""))
//		{
//			fname=System.currentTimeMillis()+".html";
//		}
//		if(fname.indexOf("?")!=-1)
//		{
//			fname = fname.substring(0,fname.lastIndexOf("?"));
//		}
		System.out.print(fname);
		
		
	}
	
	private static String getDomainBy(String url){
		String domain = "";
		try {
			URL urlx = new URL(url);
			domain = urlx.getHost();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return domain;
	}

}
