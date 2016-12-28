package downpage.test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class NextTime {

	public static String getDate() {
		SimpleDateFormat ft = new SimpleDateFormat("yyyy/MM/dd");
		Date dd = new Date();
		return ft.format(dd);
	}

	public static long getQuot(String time1, String time2) {
		long quot = 0;
		SimpleDateFormat ft = new SimpleDateFormat("yyyy/MM/dd");
		try {
			Date date1 = ft.parse(time1);
			Date date2 = ft.parse(time2);
			quot = date1.getTime() - date2.getTime();
			quot = quot / 1000 / 60 / 60 / 24;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return quot;
	}
	
	public static Date calc(String time1, int days) {
		long quot = 0;
		Date date2 = new Date();
		long day = 24 * 60 * 60 * 1000;
		SimpleDateFormat ft = new SimpleDateFormat("yyyy/MM/dd");
		try {
			Date date1 = ft.parse(time1);
//			Date date2 = ft.parse(time2);
			quot = 142 * day;
			quot = date1.getTime() + quot;
			date2.setTime(quot);
			
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
		 return date2;
	}

	public static void main(String[] args) throws Exception {
		String date1 = "2014/6/3";
		Date day = calc(date1, 142);
		SimpleDateFormat ft = new SimpleDateFormat("yyyy/MM/dd");
		System.out.println("���� " + date1 + " 142����� " + ft.format(day));
	}

}
