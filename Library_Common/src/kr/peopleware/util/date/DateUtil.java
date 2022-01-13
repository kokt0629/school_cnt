package kr.peopleware.util.date;

import java.util.Date;

public class DateUtil {
	public static Date getDate(long time){
		Date date = new Date(time);
		return date;
	}
}
