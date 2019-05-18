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
 * ����ָ�������ļ���
 * 
 * @author Jack
 *
 */
public class DownloadFile {

	File tempMusicFile = new File("file", "tempMusic.mp3");
	File tempImageFile = new File("file", "tempImage.jpg");
	File tempLyricFile = new File("file", "tempLyric.lrc");// ��ʱ����ĸ��
	InputStream is = null;
	URL url;
	File tempFile;

	public static final int MUSIC = 1;// �����ļ�����

	public static final int IMAGE = 2;// ͼƬ�ļ�����

	public static final int LYRIC = 3;// ����ļ�����

	public void download(String urlString) {
		if (urlString == null) {
			System.out.println("urlΪnull");
			return;
		}

		try {
			URL url = new URL("http://music.163.com/song/media/outer/url?id=" + urlString);
			// URL url = new
			// URL("https://api.itooi.cn/music/netease/url?id="+urlString+"&key=579621905");
			if (tempMusicFile.exists()) {
				tempMusicFile.delete();// ����ļ�����,ɾ�����ؽ���
				tempMusicFile.createNewFile();
			} else {
				tempMusicFile.createNewFile(); // �������ļ�,��ͬ�����ļ��Ļ�ֱ�Ӹ���
			}
			// URL("https://api.itooi.cn/music/netease/url?id=1338695683&key=579621905");
			// ������������
			URLConnection con = url.openConnection();

//			con.setRequestMethod("GET");// GETΪ����

			con.setRequestProperty("Content-type", "audio/mpeg");

			is = con.getInputStream();
			// ��תվ�����ݷŵ�outStream��
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
			System.out.println("�������");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			System.err.println("���س���");
			e.printStackTrace();
		}

	}

	/**
	 * ����ָ���ļ�
	 * 
	 * @param urlString
	 */
	public void downloadFile(String id, int fileType) {
		if (id == null) {
			System.out.println("idΪnull");
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
				tempFile.delete();// ����ļ�����,ɾ�����ؽ���
				tempFile.createNewFile();
			} else {
				tempFile.createNewFile(); // �������ļ�,��ͬ�����ļ��Ļ�ֱ�Ӹ���
			}
			// URL("https://api.itooi.cn/music/netease/url?id=1338695683&key=579621905");
			// ������������
			URLConnection con = url.openConnection();

//			con.setRequestMethod("GET");// GETΪ����
			con.addRequestProperty("encoding", "gbk");// ע�������ʱ��Ҫ��gbk
			con.setRequestProperty("Content-type", "audio/mpeg");

			is = con.getInputStream();
			// ��תվ�����ݷŵ�outStream��
			ByteArrayOutputStream outStream = new ByteArrayOutputStream();
			byte[] buf = new byte[20480];// ���������Сԭ����1024,���ڳ��ԸĴ��,��Ϊ20480
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
			System.out.println("�������");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			System.err.println("���س���");
			e.printStackTrace();
		}

	}

	/**
	 * ����ָ���ļ�������
	 * 
	 * @param urlString
	 */
	public void downloadFile(String id, String title, String singer) {
		if (id == null) {
			System.out.println("idΪnull");
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
						files[i].delete();// ����ļ�����,ɾ�����ؽ���
						files[i].createNewFile();
					} else {
						files[i].createNewFile(); // �������ļ�,��ͬ�����ļ��Ļ�ֱ�Ӹ���
					}

						// URL("https://api.itooi.cn/music/netease/url?id=1338695683&key=579621905");
						// ������������
						URLConnection con = urls[i].openConnection();

//						con.setRequestMethod("GET");// GETΪ����
						con.addRequestProperty("encoding", "gbk");// ע�������ʱ��Ҫ��gbk
						con.setRequestProperty("Content-type", "audio/mpeg");

						is = con.getInputStream();
						// ��תվ�����ݷŵ�outStream��
						ByteArrayOutputStream outStream = new ByteArrayOutputStream();
						byte[] buf = new byte[20480];// ���������Сԭ����1024,���ڳ��ԸĴ��,��Ϊ20480
						int len = 0;
						while ((len = is.read(buf)) != -1) {
							outStream.write(buf, 0, len);
						}
						is.close();
						outStream.close();
						FileOutputStream op = new FileOutputStream(files[i]);
						op.write(outStream.toByteArray());
						op.close();
						System.out.println("�������");
					} catch (IOException e) {
						e.printStackTrace();
					} catch (Exception e) {
						System.err.println("���س���");
						e.printStackTrace();
					}
				}

			}
		}).start();
	}

	public static void main(String[] args) {

		DownloadFile download = new DownloadFile();
		download.downloadFile("1345848098", "��ɫ","��ѩ��");
	}
}
