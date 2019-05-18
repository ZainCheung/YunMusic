package com.net;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class JsonPost {

	final static String API = "https://v1.itooi.cn/netease/";
	InputStream is = null;
	File jsonFile = new File("file/SongSearch.json");
	FileWriter writer;
	public String inputString;

	/**
	 * 对参数字符串进行URL编码
	 * 
	 * @param str
	 * @return
	 */
	public static String getURLEncoderString(String str) {
		String result = "";
		if (null == str) {
			return "";
		}
		try {
			result = java.net.URLEncoder.encode(str, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public JsonPost() {
		
	}

	public void search() throws IOException{

		try {
			if (jsonFile.exists()) {
				jsonFile.delete();// 如果文件存在,删除并重建它
				jsonFile.createNewFile();
			} else {
				jsonFile.createNewFile(); // 创建新文件,有同名的文件的话直接覆盖
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		try {

			String str = getURLEncoderString(inputString);// 必须对字符串进行格式化
			//System.out.println("正在爬取页面,请稍等...");
			//URL url = new URL("https://v1.itooi.cn/netease/search?keyword=%E8%8A%B1%E7%B2%A5&type=song&pageSize=100&page=0");
			URL url = new URL(API + "search?keyword=" + str + "&type=song&pageSize=100&page=0");
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();

			connection.addRequestProperty("encoding", "gbk");//注意请求的时候要用gbk
			// 版本：1.1，请求方式：get，编码方式：utf-8
			connection.setDoInput(true);
			connection.setDoOutput(true);

			connection.setRequestMethod("GET");// 这里要用GET, GET为下载,Post会返回错误

			InputStream is = connection.getInputStream();
			InputStreamReader isr = new InputStreamReader(is, "UTF-8");
			BufferedReader br = new BufferedReader(isr);

			String line;
			while ((line = br.readLine()) != null) {
				//System.out.println(line);
				writer = new FileWriter(jsonFile);
				writer.write(line);
				writer.flush();
				writer.close();
			}

//			ByteArrayOutputStream outStream = new ByteArrayOutputStream();
//			byte[] buf = new byte[1024];
//			int len = 0;
//			while((len = is.read(buf)) != -1){
//				outStream.write(buf,0,len);
//			}
//			//is.close();
//			outStream.close();
//			FileOutputStream op = new FileOutputStream(jsonFile);
//			op.write(outStream.toByteArray());
//			op.close();
			//System.out.println("下载完成");

			br.close();
			isr.close();
			is.close();

		} catch (MalformedURLException e) {
			e.printStackTrace();
			System.err.println("网络已经断开");
		}
//		catch (IOException e) {
//			System.err.println("网络断开");
//		}

	}

	public static void main(String[] args) {

		JsonPost post = new JsonPost();
		try {
			post.search();
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}

}
