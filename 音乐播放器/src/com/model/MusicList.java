package com.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.net.DownloadFile;
import com.net.ReadJSON;
import com.tool.MyTool;

/**
 * 歌曲播放列表类
 * 
 * @author Jack
 *
 */
public class MusicList {

	DownloadFile download = new DownloadFile();// 下载文件对象

	MusicBase musicBase = new MusicBase();

	ReadJSON readJson = new ReadJSON();

	List<String> oneMusicData = new ArrayList<String>();// 一条信息

	List<List<String>> allMusicData = new ArrayList<List<String>>();// 播放列表所有歌曲信息

	public static final int LOCALMUSIC = 1;// 本地音乐

	public static final int ONLINEMUSIC = 2;// 在线音乐
	
	ExecutorService service = Executors.newSingleThreadExecutor();

	Timer timer;

	TimerTask task;

	String songTitle;

	String singer;

	int timeLength;

	int songNumber;// 播放列表歌曲总数量

	int songCount = 0;

	boolean play = true;

	public MusicList() {

	}

	public void newPlayList(int listType, int location) {

		songCount = location;

//		this.allMusicData = allMusicData;

//		if (listType == ONLINEMUSIC) {//如果是在线音乐
//
//			for (int i = 0; i < allMusicData.size(); i++) {
//
//				download.downloadFile(allMusicData.get(i+location).get(6), DownloadFile.MUSIC);// 缓存音乐
//
//				musicBase.playFile("file/tempMusic.mp3");// 播放在线的临时文件
//
//			}
//		}

		if (listType == LOCALMUSIC) {// 如果是本地音乐
			this.allMusicData = readJson.getLocalMusicList();

			// while (play) {
			for (int i = songCount; i < 10; i++) {
				stopPlay();
				musicBase.setFileToPlay(allMusicData.get(songCount).get(6));
				service.execute(musicBase);
				//musicBase.playFile(allMusicData.get(songCount).get(6));// 播放本地音乐,第六个为音乐文件的URL
				int timeLength = MyTool.timeToInt(allMusicData.get(songCount).get(4));
				timer = new Timer();
				task = new TimerTask() {
					int second = 1;

					@Override
					public void run() {
						second++;
						if (second == timeLength) {
							timer.cancel();
							songCount++;
						}
					}

				};
				timer.schedule(task, 1000L, 1000L);

			}

		}

	}

	/**
	 * 关闭音频方法
	 */
	public void stopPlay() {

		musicBase.stop();

	}

}
