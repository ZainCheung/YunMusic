package com.tool;
/**
 * ������
 * @author Jack
 *
 */
public class MyTool {
	
	/**
	 * ��ʱ���ʽ��������罫120��ת��Ϊ02:00���ַ���
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
	 * ��ʱ���ʽ��������罫02:00���ַ���ת��Ϊ120������
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
