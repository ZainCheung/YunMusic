package com.start;

import java.awt.EventQueue;

public class MainStart {

	public static MusicDemo frame = new MusicDemo();

	public static void main(String[] args) {

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				frame.setVisible(true);
			}
		});
	}

}
