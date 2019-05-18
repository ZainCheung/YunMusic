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
 * ���������б���
 * 
 * @author Jack
 *
 */
public class MusicList {

	DownloadFile download = new DownloadFile();// �����ļ�����

	MusicBase musicBase = new MusicBase();

	ReadJSON readJson = new ReadJSON();

	List<String> oneMusicData = new ArrayList<String>();// һ����Ϣ

	List<List<String>> allMusicData = new ArrayList<List<String>>();// �����б����и�����Ϣ

	public static final int LOCALMUSIC = 1;// ��������

	public static final int ONLINEMUSIC = 2;// ��������
	
	ExecutorService service = Executors.newSingleThreadExecutor();

	Timer timer;

	TimerTask task;

	String songTitle;

	String singer;

	int timeLength;

	int songNumber;// �����б����������

	int songCount = 0;

	boolean play = true;

	public MusicList() {

	}

	public void newPlayList(int listType, int location) {

		songCount = location;

//		this.allMusicData = allMusicData;

//		if (listType == ONLINEMUSIC) {//�������������
//
//			for (int i = 0; i < allMusicData.size(); i++) {
//
//				download.downloadFile(allMusicData.get(i+location).get(6), DownloadFile.MUSIC);// ��������
//
//				musicBase.playFile("file/tempMusic.mp3");// �������ߵ���ʱ�ļ�
//
//			}
//		}

		if (listType == LOCALMUSIC) {// ����Ǳ�������
			this.allMusicData = readJson.getLocalMusicList();

			// while (play) {
			for (int i = songCount; i < 10; i++) {
				stopPlay();
				musicBase.setFileToPlay(allMusicData.get(songCount).get(6));
				service.execute(musicBase);
				//musicBase.playFile(allMusicData.get(songCount).get(6));// ���ű�������,������Ϊ�����ļ���URL
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
	 * �ر���Ƶ����
	 */
	public void stopPlay() {

		musicBase.stop();

	}

}
