package com.net;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * 下载指定类型文件类
 * 
 * @author Jack
 *
 */
public class DownloadFile {

	File tempMusicFile = new File("file", "tempMusic.mp3");
	File tempImageFile = new File("file", "tempImage.jpg");
	File tempLyricFile = new File("file", "tempLyric.lrc");// 临时缓存的歌词
	InputStream is = null;
	URL url;
	File tempFile;

	public static final int MUSIC = 1;// 音乐文件类型

	public static final int IMAGE = 2;// 图片文件类型

	public static final int LYRIC = 3;// 歌词文件类型

	public void download(String urlString) {
		if (urlString == null) {
			System.out.println("url为null");
			return;
		}

		try {
			URL url = new URL("http://music.163.com/song/media/outer/url?id=" + urlString);
			// URL url = new
			// URL("https://api.itooi.cn/music/netease/url?id="+urlString+"&key=579621905");
			if (tempMusicFile.exists()) {
				tempMusicFile.delete();// 如果文件存在,删除并重建它
				tempMusicFile.createNewFile();
			} else {
				tempMusicFile.createNewFile(); // 创建新文件,有同名的文件的话直接覆盖
			}
			// URL("https://api.itooi.cn/music/netease/url?id=1338695683&key=579621905");
			// 建立网络连接
			URLConnection con = url.openConnection();

//			con.setRequestMethod("GET");// GET为下载

			con.setRequestProperty("Content-type", "audio/mpeg");

			is = con.getInputStream();
			// 中转站，数据放到outStream中
			ByteArrayOutputStream outStream = new ByteArrayOutputStream();
			byte[] buf = new byte[1024];
			int len = 0;
			while ((len = is.read(buf)) != -1) {
				outStream.write(buf, 0, len);
			}
			is.close();
			outStream.close();
			File tempMusicFile = new File("file", "tempMusic.mp3");
			FileOutputStream op = new FileOutputStream(tempMusicFile);
			op.write(outStream.toByteArray());
			op.close();
			System.out.println("下载完成");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			System.err.println("下载出错");
			e.printStackTrace();
		}

	}

	/**
	 * 下载指定文件
	 * 
	 * @param urlString
	 */
	public void downloadFile(String id, int fileType) {
		if (id == null) {
			System.out.println("id为null");
			return;
		}

		if (fileType == MUSIC) {
			try {
				url = new URL("http://music.163.com/song/media/outer/url?id=" + id);
				tempFile = tempMusicFile;
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
		}

		if (fileType == IMAGE) {
			try {
				url = new URL("https://v1.itooi.cn/netease/pic?id=" + id);
				tempFile = tempImageFile;
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
		}

		if (fileType == LYRIC) {
			try {
				url = new URL("https://v1.itooi.cn/netease/lrc?id=" + id);
				tempFile = tempLyricFile;
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
		}

		try {

			// URL("https://api.itooi.cn/music/netease/url?id="+urlString+"&key=579621905");
			if (tempFile.exists()) {
				tempFile.delete();// 如果文件存在,删除并重建它
				tempFile.createNewFile();
			} else {
				tempFile.createNewFile(); // 创建新文件,有同名的文件的话直接覆盖
			}
			// URL("https://api.itooi.cn/music/netease/url?id=1338695683&key=579621905");
			// 建立网络连接
			URLConnection con = url.openConnection();

//			con.setRequestMethod("GET");// GET为下载
			con.addRequestProperty("encoding", "gbk");// 注意请求的时候要用gbk
			con.setRequestProperty("Content-type", "audio/mpeg");

			is = con.getInputStream();
			// 中转站，数据放到outStream中
			ByteArrayOutputStream outStream = new ByteArrayOutputStream();
			byte[] buf = new byte[20480];// 缓存数组大小原来是1024,现在尝试改大点,改为20480
			int len = 0;
			while ((len = is.read(buf)) != -1) {
				outStream.write(buf, 0, len);
			}
			is.close();
			outStream.close();
//			File tempMusicFile = new File("file", "tempMusic.mp3");
			FileOutputStream op = new FileOutputStream(tempFile);
			op.write(outStream.toByteArray());
			op.close();
			System.out.println("下载完成");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			System.err.println("下载出错");
			e.printStackTrace();
		}

	}

	/**
	 * 下载指定文件到本地
	 * 
	 * @param urlString
	 */
	public void downloadFile(String id, String title, String singer) {
		if (id == null) {
			System.out.println("id为null");
			return;
		}
		
		File[] files = new File[3];
		files[0] = new File("D:/YunDownload/music", title + " - " + singer + ".mp3");
		files[1] = new File("D:/YunDownload/image", title + " - " + singer + ".jpg");
		files[2] = new File("D:/YunDownload/lyric", title + " - " + singer + ".lrc");

		URL[] urls = new URL[3];
		try {
			urls[0] = new URL("http://music.163.com/song/media/outer/url?id=" + id);
			urls[1] = new URL("https://v1.itooi.cn/netease/pic?id=" + id);
		urls[2] = new URL("https://v1.itooi.cn/netease/lrc?id=" + id);
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		}

		new Thread(new Runnable() {

			@Override
			public void run() {
				for (int i = 0; i < 3; i++) {

					try {
						if (files[i].exists()) {
						files[i].delete();// 如果文件存在,删除并重建它
						files[i].createNewFile();
					} else {
						files[i].createNewFile(); // 创建新文件,有同名的文件的话直接覆盖
					}

						// URL("https://api.itooi.cn/music/netease/url?id=1338695683&key=579621905");
						// 建立网络连接
						URLConnection con = urls[i].openConnection();

//						con.setRequestMethod("GET");// GET为下载
						con.addRequestProperty("encoding", "gbk");// 注意请求的时候要用gbk
						con.setRequestProperty("Content-type", "audio/mpeg");

						is = con.getInputStream();
						// 中转站，数据放到outStream中
						ByteArrayOutputStream outStream = new ByteArrayOutputStream();
						byte[] buf = new byte[20480];// 缓存数组大小原来是1024,现在尝试改大点,改为20480
						int len = 0;
						while ((len = is.read(buf)) != -1) {
							outStream.write(buf, 0, len);
						}
						is.close();
						outStream.close();
						FileOutputStream op = new FileOutputStream(files[i]);
						op.write(outStream.toByteArray());
						op.close();
						System.out.println("下载完成");
					} catch (IOException e) {
						e.printStackTrace();
					} catch (Exception e) {
						System.err.println("下载出错");
						e.printStackTrace();
					}
				}

			}
		}).start();
	}

	public static void main(String[] args) {

		DownloadFile download = new DownloadFile();
		download.downloadFile("1345848098", "绿色","陈雪凝");
	}
}
