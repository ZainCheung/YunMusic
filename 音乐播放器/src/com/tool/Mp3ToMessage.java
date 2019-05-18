package com.tool;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.jaudiotagger.audio.mp3.MP3AudioHeader;
import org.jaudiotagger.audio.mp3.MP3File;
import org.jaudiotagger.tag.id3.AbstractID3v2Tag;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 解析本地MP3文件,得到对应的MP3信息列表(二维)
 * 
 * @author Jack
 *
 */
public class Mp3ToMessage {

	List<List<String>> musicAllList = new ArrayList<List<String>>();
	List<String> musicList = new ArrayList<String>();
	String[] musicListArray = new String[6];// 临时数组,用来有序的储存音乐列表信息
	List<String> urlList = new ArrayList<String>();
	private String mp3Path = "D:\\CloudMusic\\徐良 - 天真.mp3";
	private MP3File mp3File;
	private final int START = 6;

	JSONObject result;
	JSONArray songsArray;
	JSONObject songObject;

	ScanFolder scan = new ScanFolder();

	/**
	 * 得到本地音乐的JSON
	 * 
	 * @return
	 */
	public JSONObject getLocalMusicJson() {

		new Thread(new Runnable() {

			@Override
			public void run() {

				urlList = scan.getMyMusicUrlList("D:\\CloudMusic");

				try {
					result.put("num", getMusicAllList().size());// 添加歌曲数量

					for (int i = 0; i < getMusicAllList().size(); i++) {
						songObject = new JSONObject();

						songObject.put("count", (i + 1));
						songObject.put("title", getMusicAllList().get(i).get(1));
						songObject.put("singer", getMusicAllList().get(i).get(2));
						songObject.put("album", getMusicAllList().get(i).get(3));
						songObject.put("url", urlList.get(i));
						songObject.put("id", "null");
						songObject.put("local", true);
						songObject.put("time", getMusicAllList().get(i).get(4));
						songObject.put("size", getMusicAllList().get(i).get(5));
						songObject.put("pic", "null");
						songObject.put("lrc", "null");

						songsArray.put(songObject);
					}
					result.put("songs", songsArray);
					writeFile("file/localMusicJson.json", result.toString());

				} catch (JSONException | IOException e) {
					e.printStackTrace();
				}
			}
		}).start();

		return result;

	}

	/**
	 * 
	 * @return 音乐信息二维集合
	 */
	public List<List<String>> getMusicAllList() {

		return musicAllList;
	}

	/**
	 * 有参构造方法,解析参数url集合,生成对应的音乐信息二维集合 可以通过调用getMusicAllList()方法获得音乐信息二维集合
	 * 
	 * @param urlList
	 */
	public Mp3ToMessage(List<String> urlList) {

		result = new JSONObject();

		songObject = new JSONObject();

		songsArray = new JSONArray();

		if (urlList != null) {
			this.urlList = urlList;

			for (int i = 0; i < urlList.size(); i++) {
				mp3Path = urlList.get(i);
				if (i < 10)
					musicListArray[0] = "0" + Integer.toString(i + 1);
				else
					musicListArray[0] = Integer.toString(i + 1);
				getMp3Message(mp3Path);// 添加mp3的标题,歌手,专辑,时长信息
				getFileSize(mp3Path);// 添加文件大小信息
				musicList = new ArrayList<String>(Arrays.asList(musicListArray));
				musicAllList.add(musicList);// 完成一条记录的添加
				musicList = new ArrayList<String>();

			}

		}

	}

	public Mp3ToMessage() {

	}

	public void getMp3Message(String mp3Path) {
		try {
			mp3File = new MP3File(mp3Path);// 封装好的类
			MP3AudioHeader header = mp3File.getMP3AudioHeader();
			String musictext = mp3File.displayStructureAsPlainText();

			String[] allText = mp3File.displayStructureAsPlainText().replaceAll("\n", ";").split("frame:");
			String[] simple = null;
			for (int i = 0; i < allText.length; i++) {
				String string = allText[i];
				if (string.startsWith("TIT2")) {
					simple = string.split(";");
					musicListArray[1] = simple[12].replace("          Text:", "");// 10个空格

				} else if (string.startsWith("TPE1")) {
					simple = string.split(";");
					musicListArray[2] = simple[12].replace("          Text:", "");
				} else if (string.startsWith("TALB")) {
					simple = string.split(";");
					if (simple[12].replace("          Text:", "").equals("   ")) {
						musicListArray[3] = "未知专辑";
					} else {
						musicListArray[3] = simple[12].replace("          Text:", "");
					}

				}
			}

			int time = header.getTrackLength();
			String timeLength = null;
			String time1 = null;
			if (time / 60 < 10) {
				time1 = "0" + (time / 60);
			} else {
				time1 = "" + (time / 60);
			}
			if (time % 60 < 10) {
				timeLength = ":0" + time % 60;
			} else {
				timeLength = ":" + time % 60;
			}
			musicListArray[4] = time1 + timeLength;
			// musicList.set(4,time1+timeLength);// 添加时长

		} catch (Exception e) {
			musicListArray[1] = "未知歌曲";
			musicListArray[2] = "未知歌手";
			musicListArray[3] = "未知专辑";
			musicListArray[4] = "--:--";
		}
	}

	/**
	 * 得到参数mp3Path所指向的文件的大小
	 * 
	 * @param mp3Path
	 */
	public void getFileSize(String mp3Path) {
		File file = new File(mp3Path);
		DecimalFormat df = new DecimalFormat("######0.00");
		FileInputStream fis = null;
		try {
			if (file.exists() && file.isFile()) {
				String fileName = file.getName();
				String fileSize = null;
				fis = new FileInputStream(file);
				int size = fis.available();
				// System.out.println("文件"+fileName+"的大小是："+fis.available()+"\n");
				if (size < 1024) {// 不到1KB
					fileSize = Integer.toString(size);
					musicListArray[5] = fileSize + "B";
					// System.out.println(fileSize+"B");
				} else if (size < 1048576) {// 不到1MB
					if (size % 1024 == 0) {
						fileSize = Integer.toString(size / 1024);
						musicListArray[5] = fileSize + "KB";
						// System.out.println(fileSize+"KB");
					} else {
						double doubleSize = (size / 1024) + ((double) (size % 1024) / 1024);
						fileSize = df.format(doubleSize);
						musicListArray[5] = fileSize + "KB";
						// System.out.println(fileSize+"KB");
					}
				} else if (size < 1073741824) {// 不到1GB
					if (size % 1024 == 0) {
						fileSize = Integer.toString(size / 1048576);
						musicListArray[5] = fileSize + "MB";
						// System.out.println(fileSize+"MB");
					} else {
						double doubleSize = (size / 1048576) + ((double) (size % 1048576) / 1048576);
						fileSize = df.format(doubleSize);
						musicListArray[5] = fileSize + "MB";
						// System.out.println(fileSize+"MB");
					}
				}
			}
		} catch (Exception e) {
			musicList.add("未知大小");
			e.printStackTrace();
		} finally {
			if (null != fis) {
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static void main(String[] args) {

		ScanFolder scan = new ScanFolder();
		Mp3ToMessage mp3 = new Mp3ToMessage(scan.getMyMusicUrlList("D:\\CloudMusic"));
		mp3.getLocalMusicJson();
	}

	private void getHead(String mp3Path) {
		try {
			System.out.println("----------------Loading...Head-----------------");
			mp3File = new MP3File(mp3Path);// 封装好的类

			MP3AudioHeader header = mp3File.getMP3AudioHeader();
			System.out.println("时长: " + header.getTrackLength()); // 获得时长
			System.out.println("比特率: " + header.getBitRate()); // 获得比特率
			System.out.println("音轨长度: " + header.getTrackLength()); // 音轨长度
			System.out.println("格式: " + header.getFormat()); // 格式，例 MPEG-1
			System.out.println("声道: " + header.getChannels()); // 声道
			System.out.println("采样率: " + header.getSampleRate()); // 采样率
			System.out.println("MPEG: " + header.getMpegLayer()); // MPEG
			System.out.println("MP3起始字节: " + header.getMp3StartByte()); // MP3起始字节
			System.out.println("精确的音轨长度: " + header.getPreciseTrackLength()); // 精确的音轨长度
			int time = header.getTrackLength();
			String timeLength = time / 60 + ":" + time % 60;
			musicList.add(timeLength);
			for (Iterator iterator = musicList.iterator(); iterator.hasNext();) {
				String string = (String) iterator.next();
				System.out.println(string);
			}
		} catch (Exception e) {
			System.out.println("没有获取到任何信息");
		}

	}

	private void getContent(String mp3Path) {
		try {
			System.out.println("----------------Loading...Content-----------------");
			mp3File = new MP3File(mp3Path);// 封装好的类
			AbstractID3v2Tag id3v2tag = mp3File.getID3v2Tag();
			String songName = new String(id3v2tag.frameMap.get("TIT2").toString().getBytes("ISO-8859-1"), "GB2312");
			String singer = new String(id3v2tag.frameMap.get("TPE1").toString().getBytes("ISO-8859-1"), "GB2312");
			String author = new String(id3v2tag.frameMap.get("TALB").toString().getBytes("ISO-8859-1"), "GB2312");
			System.out.println("歌名：" + songName.substring(START, songName.length() - 3));
			System.out.println("歌手:" + singer.substring(START, singer.length() - 3));
			System.out.println("专辑名：" + author.substring(START, author.length() - 3));
		} catch (Exception e) {
			System.out.println("没有获取到任何信息");
		}
		int a = 0;
		String musictext = mp3File.displayStructureAsPlainText();
//		Pattern p = Pattern.compile("Text:(.*?)\\s*frame");// 正则表达式，取=和|之间的字符串，不包括=和|
//		Matcher m = p.matcher(musictext);
//		while (m.find()) {
//			if (a>1) {
//				break;//只取前两条结果,即歌名和专辑
//			}
//			musicList.add(m.group(1));
//			System.out.println(m.group(1));// m.group(1)不包括这两个字符
//			a++;
//		}
//
//		String[] messages = musictext.split("Text:");
//		for (int i = 0; i < messages.length; i++) {
//			String string = messages[i];
//			System.out.println("********"+string);
//		}
//		System.out.println(messages.length);
//		if (messages[3].length()>10) {
//			musicList.add(1,"未知歌手");
//		}else {
//			musicList.add(1, messages[3].replace("\n", ""));
//		}

		System.out.println("All Info：" + mp3File.displayStructureAsPlainText());// .replaceAll("\n", ";")
		String[] all = mp3File.displayStructureAsPlainText().replaceAll("\n", ";").split("frame:");
		String[] simple = null;
		for (int i = 0; i < all.length; i++) {
			String string = all[i];
			System.out.println(string);
			if (string.startsWith("TIT2")) {
				simple = string.split(";");
				System.out.println("歌名:" + simple[12].replace("         Text:", ""));
				musicList.add(simple[12].replace("         Text:", ""));
			} else if (string.startsWith("TPE1")) {
				simple = string.split(";");
				System.out.println("歌手:" + simple[12].replace("         Text:", ""));
				musicList.add(simple[12].replace("         Text:", ""));
			} else if (string.startsWith("TALB")) {
				simple = string.split(";");
				System.out.println("专辑:" + simple[12].replace("         Text:", ""));
				musicList.add(simple[simple.length - 1].replace("         Text:", ""));
			}
		}
	}

	public static void writeFile(String filePath, String sets) throws IOException {
		FileWriter fw = new FileWriter(filePath);
		PrintWriter out = new PrintWriter(fw);
		out.write(sets);
		out.println();
		fw.close();
		out.close();
	}

}
