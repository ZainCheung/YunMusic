package com.event;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

import com.start.MusicDemo;

public class MaxJFrame implements ActionListener {

	JFrame jframe;

	public MaxJFrame(JFrame jframe) {
		this.jframe = jframe;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		jframe.setExtendedState(JFrame.MAXIMIZED_BOTH);// 最大化窗体
		MusicDemo.maxButton.removeActionListener(MusicDemo.musicDemo.max);
		MusicDemo.maxButton.addActionListener(MusicDemo.musicDemo.normal);
	}
}
