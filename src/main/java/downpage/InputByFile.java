package downpage;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import downpage.jdbc.DbUtil;

public class InputByFile {

	public static void main(String[] args) throws Exception {
//		create();
		updateGoodsInfo3();
	}

	private static void createFileList() throws IOException {
		File root = new File("D:\\temp\\data2");
		String filter[] = {"txt"};
	Collection<File> files = FileUtils.listFiles(root,filter, true);    
	StringBuffer sb = new StringBuffer();
		for(File t : files)
		{
			System.out.println(t.getParent());
			//D:\\temp\\yao-data\\data\\8\\4359\\4359.txt
//			File file = new File("D:\\temp\\data2\\92\\8318\\8318.txt");
			sb.append(t.getAbsolutePath()).append("\r\n");
			List lines = FileUtils.readLines(t);
			
			System.out.println(lines.toString());
			String temp = lines.toString();
			
			String[] pros = temp.split(";");
			Map map = new HashMap();
			for(int i = 0 ; i < pros.length; i ++)
			{
				String key = pros[i].split("")[0];
				String value = pros[i].split("")[1];
				map.put(key, value);
			}
//			System.out.println(pros[0]);
//			System.out.println(pros[1]);
//			System.out.println(pros[2]);
//			System.out.println(pros[3]);
//			System.out.println(pros[4]);
//			System.out.println(pros[5]);
//			System.out.println(pros[5]);
//			System.out.println(pros[6]);
//			System.out.println(pros[7]);
//			createProduct(pros);
		}
		FileUtils.writeStringToFile(new File("d:\\temp\\yaodata.txt"), sb.toString());
	}
	
	public static void create() throws Exception {
		File root = new File("D:\\temp\\yaodata.txt");
	List<String> data = 	FileUtils.readLines(root, "UTF-8");
	StringBuffer sb = new StringBuffer();
		for(String  t : data)
		{
			System.out.println(t);
			//D:\\temp\\yao-data\\data\\8\\4359\\4359.txt
			File file = new File(t);
//			sb.append(t.getAbsolutePath()).append("\r\n");
			List<String> lines = FileUtils.readLines(file);
			
			System.out.println(lines.toString());
			for(String tt : lines)
			{
				
			String temp = lines.toString();
			
			String[] pros = temp.split(";");
			Map map = new HashMap();
			for(int i = 0 ; i < pros.length; i ++)
			{
			
				try {
					String key = pros[i].split("=")[0];
					String value = pros[i].split("=")[1];
					key = key.replace("[", "");
					map.put(key, value);
				} catch (Exception e) {
					System.out.println(pros[i]);
					e.printStackTrace();
				}
			
			}
			createProduct(map);
		}
//			System.out.println(pros[0]);
//			System.out.println(pros[1]);
//			System.out.println(pros[2]);
//			System.out.println(pros[3]);
//			System.out.println(pros[4]);
//			System.out.println(pros[5]);
//			System.out.println(pros[5]);
//			System.out.println(pros[6]);
//			System.out.println(pros[7]);
		
		}
//		FileUtils.writeStringToFile(new File("d:\\temp\\yaodata.txt"), sb.toString());
	}

	public static void updateGoodsInfo() throws Exception {
		File root = new File("D:\\temp\\yaodata.txt");
	List<String> data = 	FileUtils.readLines(root, "UTF-8");
	StringBuffer sb = new StringBuffer();
		for(String  t : data)
		{
			
			//D:\\temp\\yao-data\\data\\8\\4359\\4359.txt
			File file = new File(t);
			String tname = file.getName();
			String id = tname.replace(".txt", "");
			
			try {
//				File hfile = new File(file.getParent()+"/goods-"+id+".html");

				System.out.println(file.getAbsolutePath());
//			sb.append(t.getAbsolutePath()).append("\r\n");
				String lines = FileUtils.readFileToString(file);
				
				String[] tt = lines.split(";");
//				System.out.println(lines.toString());
				for(String tempstr : tt)
				{
					String[] str1 = tempstr.split("=");
					if(str1[0].equals("images"))
					{
						String tttt = str1[1];
						tttt = tttt.replaceAll("'", "\"");
						tttt = tttt.replace("[", "");
						tttt = tttt.replace("]", "");
						tttt = tttt.replace("{", "");
//						System.out.println(tttt);
						
						String temp[] = tttt.split("},");
						for(int i = 0; i < temp.length; i ++){
							try {
								String images = temp[i];
								String tttttt[] = images.split(",");
								String res =  tttttt[1].split(":")[1];
								res = res.replace("\"", "");
								System.out.println(res);
								updateProductPhoto(id,res.trim());
								break;
							} catch (Exception e) {
							}
						}
						System.out.println("---------------------------");
					}
				}
//				String info = lines;
//				System.out.println(info);
//				updateProductInfo(id,info);
			} catch (Exception e) {
				e.printStackTrace();
			}
//			break;
		}
	}
	public static void updateGoodsInfo3() throws Exception {
		File root = new File("D:\\temp\\yaodata.txt");
	List<String> data = 	FileUtils.readLines(root, "UTF-8");
		for(String  t : data)
		{
			
//			D:\\temp\\yao-data\\data\\8\\4359\\4359.txt
			File file = new File(t);
//			File file = new File("D:\\temp\\yao-data\\data\\302\\7734\\7734.txt");
			String tname = file.getName();
			String id = tname.replace(".txt", "");
			
			try {
				File hfile = new File(file.getParent()+"/goods-"+id+".html");
				String lines = FileUtils.readFileToString(hfile);
				
				String tt1 = file.getParent();
				tt1 = tt1.replace("\\", "=");
				String tk[] = tt1.split("=");
				String parent = tk[3]+"/"+id;
				
				System.out.println(file.getParent()+"------>"+id+"----->"+parent);
				
				Document doc = Jsoup.parse(lines);
				Elements imgs = doc.getElementsByTag("img");
				modifyHtml(imgs,parent);
				
				String html = doc.getElementsByTag("body").html();
//				System.out.println(html);
				
				updateProductInfo(id,html);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
//			break;
		}
	}
	
	private static void modifyHtml(Elements imgs,String url) {
		Iterator<Element> iterator3 = imgs.iterator();  
        while(iterator3.hasNext()){  
            Element element=iterator3.next();  
            String src = element.attr("src");//将所有的相对地址换为绝对地址;  
            src = src.substring(src.lastIndexOf("/")+1,src.length());
            System.out.println(src);
            element.attr("src","");//装换为  
            element.attr("src","/images/"+url+"/images/"+src);//装换为  
        }
	}
		
		public static void updateGoodsInfo2() throws Exception {
			File root = new File("D:\\temp\\yaodata.txt");
		List<String> data = 	FileUtils.readLines(root, "UTF-8");
		StringBuffer sb = new StringBuffer();
			for(String  t : data)
			{
				
				//D:\\temp\\yao-data\\data\\8\\4359\\4359.txt
				File file = new File(t);
				String tname = file.getName();
				String id = tname.replace(".txt", "");
				
				try {
//					File hfile = new File(file.getParent()+"/goods-"+id+".html");

					System.out.println(file.getAbsolutePath());
//				sb.append(t.getAbsolutePath()).append("\r\n");
					String lines = FileUtils.readFileToString(file);
					
					String[] tt = lines.split(";");
//					System.out.println(lines.toString());
					String apply = "";
					String unit = "";
					for(String tempstr : tt)
					{
						String[] str1 = tempstr.split("=");
						
						if(str1[0].equals("apply"))
						{
							 apply = str1[1];
						}else if(str1[0].equals("unit"))
						{
							 unit = str1[1];
						}
						
					}
					System.out.println(id+","+unit+","+apply);
					updateProduct(id,unit,apply);
//					String info = lines;
//					System.out.println(info);
//					updateProductInfo(id,info);
				} catch (Exception e) {
					e.printStackTrace();
				}
//				break;
			}
		
		
//		FileUtils.writeStringToFile(new File("d:\\temp\\yaodata.txt"), sb.toString());
	}
	
	 public static boolean createProduct(String[] pp)
	 {
 
		boolean result = false;
		
		String id = pp[0].split("=")[1];
		String name = pp[1].split("=")[1];
		String cate = pp[2].split("=")[1];
		String price = pp[3].split("=")[1];
		result = DbUtil.update("insert into shop_goods (id,goods_name,shop_price,cat_id,goods_desc) values("+id+",'"+name+"',"+price+","+cate+",'test')",null);
		 return result;
	 }
	 
	 public static boolean updateProduct(String id,String unit,String provider_name)
	 {
 
		boolean result = false;
		
		result = DbUtil.update("update shop_goods set unit='"+unit+"', approve_no='"+provider_name+"' where id="+id,null);
		 return result;
	 }
	 
	 public static boolean updateProductPhoto(String id,String url)
	 {
 
		boolean result = false;
		
		result = DbUtil.update("update shop_goods set goods_img='"+url+"' where id="+id,null);
		 return result;
	 }
	 public static boolean createProductPhoto(String id,String url)
	 {
 
		boolean result = false;
		
		result = DbUtil.update("insert into shop_goods_photo (goods_id,url,typex,creater,mainp) values("+id+",'"+url+"',1,1,1)",null);
		 return result;
	 }
	 
	 public static boolean updateProductInfo(String id, String info)
	 {
 
		boolean result = false;
		
		result = DbUtil.update("update shop_goods set goods_desc='"+info+"' where id="+id,null);
		 return result;
	 }
	 
	 public static boolean createProduct(Map map)
	 {
 
		boolean result = false;
		
		String id = (String)map.get("id");
		String name =(String)map.get("name");
		String cate = (String)map.get("cateId");
		String price =(String) map.get("price");
		result = DbUtil.update("insert into shop_goods (id,goods_name,shop_price,cat_id,goods_desc) values("+id+",'"+name+"',"+price+","+cate+",'test')",null);
		 return result;
	 }

}
