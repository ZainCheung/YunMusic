package com.model;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.tool.Mp3ToMessage;
import com.tool.ReadAndWriteFile;
import com.tool.ScanFolder;

/**
 * д��JSON��
 * @author Jack
 *
 */
public class WriteJSON {
	
	Mp3ToMessage mp3ToMessage = new Mp3ToMessage();
	ScanFolder scan = new ScanFolder();
	ReadAndWriteFile  readAndWrite = new ReadAndWriteFile();
	
	List<String> urlList = new ArrayList<String>();
	List<List<String>> musicAllList = new ArrayList<List<String>>();
	
	JSONObject result;
	JSONArray songsArray;
	JSONObject songObject;
	
	public List<String> getUrlList() {
		urlList = new ArrayList<String>();
		List<String> urlTempList = readAndWrite.getMyMusicFolder(new File("file\\myMusicUrl.txt"));// ��myMusicUrl�ļ��еõ�URL����
		getLocalMusicJson(urlTempList);
		return urlList;
	}

	/**
	 * ���ַ���setsд��·��ΪfilePath���ļ���
	 * @param filePath
	 * @param sets
	 * @throws IOException
	 */
	public static void writeFile(String filePath, String sets)  
            throws IOException {  
        FileWriter fw = new FileWriter(filePath);  
        PrintWriter out = new PrintWriter(fw);  
        out.write(sets);  
        out.println();  
        fw.close();  
        out.close();  
    } 

	/**
	 * �õ�����������Ϣ��JSON
	 * @param �ļ���·��
	 * @return
	 */
	public JSONObject getLocalMusicJson(List<String> urlList) {
		
		result = new JSONObject();

		songObject = new JSONObject();

		songsArray = new JSONArray();
		
		mp3ToMessage = new Mp3ToMessage(urlList);//"D:\\CloudMusic"
		
		musicAllList = mp3ToMessage.getMusicAllList();
		



//		new Thread(new Runnable() {
//
//			@Override
//			public void run() {

				//urlList = scan.getMyMusicUrlList("D:\\CloudMusic");

				try {
					result.put("num", musicAllList.size());// ��Ӹ�������

					int tempCount = 0;
					for (int i = 0; i < musicAllList.size(); i++) {
						songObject = new JSONObject();

						songObject.put("count", (i + 1));
						songObject.put("title", musicAllList.get(i).get(1));
						songObject.put("singer", musicAllList.get(i).get(2));
						songObject.put("album", musicAllList.get(i).get(3));
						songObject.put("url", urlList.get(i));
						songObject.put("id", "null");
						songObject.put("local", true);
						songObject.put("time", musicAllList.get(i).get(4));
						songObject.put("size", musicAllList.get(i).get(5));
						songObject.put("pic", "null");
						songObject.put("lrc", "null");

						if (!musicAllList.get(i).get(1).equals("δ֪����")) {
							songsArray.put(songObject);
							this.urlList.add(urlList.get(i));
							tempCount ++ ;
						}
	
					}
					result.put("num", tempCount);// ��Ӹ�������
					result.put("songs", songsArray);
					writeFile("file/localMusicJson.json", result.toString());

				} catch (JSONException | IOException e) {
					e.printStackTrace();
				}
//			}
//		}).start();

		return result;

	}
}
