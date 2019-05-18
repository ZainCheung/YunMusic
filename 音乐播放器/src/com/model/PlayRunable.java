package com.model;

public class PlayRunable implements Runnable {

	String filePath;
	MusicBase musicBase;
	public PlayRunable(String filePath){
		this.filePath=filePath;
	}
	
	@Override
	public void run() {
		musicBase=new MusicBase();
		musicBase.playFile(filePath);
	}

}
