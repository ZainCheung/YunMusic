package com.view;

import com.fileChooser.CustomFileChooser;

public class AllPanel {

	public static CustomFileChooser fileChooser ;//文件选择器= new CustomFileChooser("D://")
	public static AddFolderDialog addFolderDialog =null;//本地音乐文件目录窗口
	public static MyMusicList musicList = new MyMusicList();//本地音乐面板
}
