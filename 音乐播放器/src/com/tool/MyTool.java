package com.tool;
/**
 * 工具类
 * @author Jack
 *
 */
public class MyTool {
	
	/**
	 * 将时间格式化输出例如将120秒转换为02:00的字符串
	 * 
	 * @param count
	 * @return
	 */
	public static String getFormattime(int count) {
		String second = null;
		String minute = null;
		if (count / 60 < 10) {
			minute = "0" + (count / 60);
		} else {
			minute = "" + (count / 60);
		}
		if (count % 60 < 10) {
			second = ":0" + count % 60;
		} else {
			second = ":" + count % 60;
		}
		return minute + second;

	}
	/**
	 * 将时间格式化输出例如将02:00的字符串转换为120秒整形
	 * @param time
	 * @return
	 */
	public static int timeToInt(String time) {
		if (time==null||time.equals("--:--")) {
			return 0;
		}
		String[] times=time.split(":");
		int minute = Integer.parseInt(times[0]);
		int second = Integer.parseInt(times[1]);
		return minute*60+second;
	}

}
