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
	 * �Բ����ַ�������URL����
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
				jsonFile.delete();// ����ļ�����,ɾ�����ؽ���
				jsonFile.createNewFile();
			} else {
				jsonFile.createNewFile(); // �������ļ�,��ͬ�����ļ��Ļ�ֱ�Ӹ���
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		try {

			String str = getURLEncoderString(inputString);// ������ַ������и�ʽ��
			//System.out.println("������ȡҳ��,���Ե�...");
			//URL url = new URL("https://v1.itooi.cn/netease/search?keyword=%E8%8A%B1%E7%B2%A5&type=song&pageSize=100&page=0");
			URL url = new URL(API + "search?keyword=" + str + "&type=song&pageSize=100&page=0");
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();

			connection.addRequestProperty("encoding", "gbk");//ע�������ʱ��Ҫ��gbk
			// �汾��1.1������ʽ��get�����뷽ʽ��utf-8
			connection.setDoInput(true);
			connection.setDoOutput(true);

			connection.setRequestMethod("GET");// ����Ҫ��GET, GETΪ����,Post�᷵�ش���

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
			//System.out.println("�������");

			br.close();
			isr.close();
			is.close();

		} catch (MalformedURLException e) {
			e.printStackTrace();
			System.err.println("�����Ѿ��Ͽ�");
		}
//		catch (IOException e) {
//			System.err.println("����Ͽ�");
//		}

	}

	public static void main(String[] args) {

		JsonPost post = new JsonPost();
		try {
			post.search();
		} catch (IOException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
	}

}
