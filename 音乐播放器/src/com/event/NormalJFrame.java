package com.event;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

import com.start.MusicDemo;

public class NormalJFrame implements ActionListener {

JFrame jframe;

public NormalJFrame(JFrame jframe) {
	this.jframe = jframe;
}

@Override
public void actionPerformed(ActionEvent e) {
	jframe.setExtendedState(JFrame.NORMAL);// 正常化窗体
	MusicDemo.maxButton.removeActionListener(MusicDemo.musicDemo.normal);
	MusicDemo.maxButton.addActionListener(MusicDemo.musicDemo.max);
}
}
