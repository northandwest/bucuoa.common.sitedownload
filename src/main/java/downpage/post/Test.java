package downpage.post;

import java.io.IOException;
import java.util.List;

import org.jsoup.nodes.Element;

import cn.wanghaomiao.xpath.exception.NoSuchAxisException;
import cn.wanghaomiao.xpath.exception.NoSuchFunctionException;
import cn.wanghaomiao.xpath.exception.XpathSyntaxErrorException;
import cn.wanghaomiao.xpath.model.JXDocument;

public class Test {

	public static void main(String[] args) {

		String url = "http://xinjinqiao.tprtc.com/admin/main/pro!prodetail.do?id=1744";
		
		try {
			String html = JsoupUtil.httpGet(url);
//			System.out.println(html);
			
			String xpath="/html/BODY/TABLE[1]/TBODY/TR/TD/text()";
			JXDocument jxDocument = new JXDocument(html);
			List<Object> rs = null;
			try {
				rs = jxDocument.sel(xpath);
			} catch (NoSuchAxisException e) {
				e.printStackTrace();
			} catch (NoSuchFunctionException e) {
				e.printStackTrace();
			} catch (XpathSyntaxErrorException e) {
				e.printStackTrace();
			}
			for (Object o:rs){
			    if (o instanceof Element){
			        int index = ((Element) o).siblingIndex();
			        System.out.println(index);
			    }
			    System.out.println(o.toString());
			}
			
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
