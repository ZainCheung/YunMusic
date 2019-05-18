package com.net;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.JsonArray;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.model.MusicBase;
import com.tool.MyTool;
import com.view.SliderPanel;

public class ReadJSON {

	String jsonString;
	String jsonFilePath = "file/songSearch.json";
	JSONObject jsonObject;
	JsonParser parse;
	String songId;//
	String songName;//
	String singer;//
	String picName;//
	String picUrl;//
	public int songLength;// ����ʱ��
	public int count;// �����������׸���

	private JsonObject songs_1;
	private JsonArray ar;
	private JsonObject ar_1;
	private JsonObject al;

	public List<String> IdList;//ID����
	public List<String> oneDataList;//ÿһ���������ݼ���
	public List<List<String>> allList;//������������
	
	public List<String> loaclUrlList;//��������URL����

	public ReadJSON() {
		allList = new ArrayList<List<String>>();
		oneDataList = new ArrayList<String>();
		IdList = new ArrayList<String>();
		loaclUrlList =  new ArrayList<String>();
	}

	public void getJsonObject() {

		try {
			jsonObject = new JSONObject(jsonString);
			String string = jsonObject.getString("code");
			System.out.println(string);
		} catch (JSONException e1) {
			e1.printStackTrace();
		}
	}

	public void search() {

		parse = new JsonParser(); //JSON������

		try {
			JsonObject json = (JsonObject) parse.parse(new FileReader(jsonFilePath)); //
			JsonObject data = json.get("data").getAsJsonObject();//
			JsonArray songs = data.get("songs").getAsJsonArray();//

			count = data.get("songCount").getAsInt();// ��������
			int tempCount = data.get("songCount").getAsInt();
			// System.out.println("һ��������:"+count+"�׸���"); //

			IdList = new ArrayList<String>();
			allList = new ArrayList<List<String>>();

			if (tempCount > 100) {
				tempCount = 100;
			}
			for (int i = 0; i < tempCount; i++) {//

				songs_1 = songs.get(i).getAsJsonObject();// songs
				ar = songs_1.get("ar").getAsJsonArray();//
				ar_1 = ar.get(0).getAsJsonObject();//
				al = songs_1.get("al").getAsJsonObject();//

				songId = songs_1.get("id").getAsString();
				songName = songs_1.get("name").getAsString();
				songLength = songs_1.get("dt").getAsInt() / 1000; // ����ʱ��
				singer = ar_1.get("name").getAsString();

				if (al.has("name")) {
					picName = al.get("name").getAsString();
				}
				if (al.has("picUrl")) {
					picUrl = al.get("picUrl").getAsString();
				}
				oneDataList = new ArrayList<String>();
				oneDataList.add(Integer.toString(i + 1));
				oneDataList.add(songName);
				oneDataList.add(singer);
				oneDataList.add(picName);
				oneDataList.add(MyTool.getFormattime(songLength));
				oneDataList.add(picUrl);
				oneDataList.add(songId);
				
				allList.add(oneDataList);
				IdList.add(songId);

			}
			
			

		} catch (JsonIOException e) {
			e.printStackTrace();
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			System.out.println("�޸���");
		}
	}
	/*
	 * ��ȡ����������Ϣ
	 */
	public List<List<String>> getLocalMusicList( ){
		
		List<List<String>> allList = new ArrayList<List<String>>();
		List<String> oneDataList = new ArrayList<String>();
		loaclUrlList =  new ArrayList<String>();
		
		parse = new JsonParser(); //JSON������
		
		JsonObject result;
		try {
			result = (JsonObject) parse.parse(new FileReader("file/localMusicJson.json"));
		    JsonArray songs = result.get("songs").getAsJsonArray();//
		    
		    int num = result.get("num").getAsInt();//��������
		    
		    JsonObject song;
		    
		    String title;
		    String singer;
		    String album;
		    String id;
		    String time;
		    String size;
		    String pic;
		    String lrc;
		    String url;
		    
		    
		    for (int i = 0; i < num; i++) {//
		    	
		    	oneDataList = new ArrayList<String>();
		    	song = songs.get(i).getAsJsonObject();//ÿ�׸�
		    	
		    	title = song.get("title").getAsString();
		    	singer = song.get("singer").getAsString();
		    	album = song.get("album").getAsString();
		    	id = song.get("id").getAsString();
		    	time = song.get("time").getAsString();
		    	size = song.get("size").getAsString();
		    	pic = song.get("pic").getAsString();
		    	lrc = song.get("lrc").getAsString();
		    	url = song.get("url").getAsString();
		    	
		    	oneDataList.add(Integer.toString(i+1));
		    	oneDataList.add(title);
		    	oneDataList.add(singer);
		    	oneDataList.add(album);
		    	oneDataList.add(time);
		    	oneDataList.add(size);
		    	//oneDataList.add(url);
		    	loaclUrlList.add(url);
		    	allList.add(oneDataList);
		    	
		    }
		    
		    
		} catch (JsonIOException e) {
			e.printStackTrace();
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} //
		
		
		return allList;
		
		
		
	}
	/**
	 * ��ȡ��������JSON��URL
	 * @return url����
	 */
	public List<String> getLocalUrlList( ){

		return loaclUrlList;	
	}
}
