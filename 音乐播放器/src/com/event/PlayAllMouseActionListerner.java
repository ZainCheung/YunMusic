package com.event;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.UIManager;

import com.view.DownloadPanel;

public class PlayAllMouseActionListerner implements MouseListener {

	public PlayAllMouseActionListerner() {
		//UIManager.put("Button.select", new Color(177, 35, 35));// 点击按钮瞬间颜色
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		AbstractButton button = (AbstractButton) e.getComponent();
		button.setBackground(new Color(177, 35, 35));// 按压按钮时背景色
	}

	@Override
	public void mousePressed(MouseEvent e) {

		AbstractButton button = (AbstractButton) e.getComponent();
		button.setBackground(new Color(177, 35, 35));// 松开按钮时背景色

	}

	@Override
	public void mouseReleased(MouseEvent e) {

		AbstractButton button = (AbstractButton) e.getComponent();
		button.setBackground(new Color(177, 35, 35));// 按压按钮时背景色
	}

	@Override
	public void mouseEntered(MouseEvent e) {

		AbstractButton button = (AbstractButton) e.getComponent();
		button.setBackground(new Color(177, 35, 35));// 进入按钮时背景色
	}

	@Override
	public void mouseExited(MouseEvent e) {

		AbstractButton button = (AbstractButton) e.getComponent();
		button.setBackground(new Color(198, 47, 47));// 离开按钮时背景色
	}

}
