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
		//UIManager.put("Button.select", new Color(177, 35, 35));// �����ť˲����ɫ
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		AbstractButton button = (AbstractButton) e.getComponent();
		button.setBackground(new Color(177, 35, 35));// ��ѹ��ťʱ����ɫ
	}

	@Override
	public void mousePressed(MouseEvent e) {

		AbstractButton button = (AbstractButton) e.getComponent();
		button.setBackground(new Color(177, 35, 35));// �ɿ���ťʱ����ɫ

	}

	@Override
	public void mouseReleased(MouseEvent e) {

		AbstractButton button = (AbstractButton) e.getComponent();
		button.setBackground(new Color(177, 35, 35));// ��ѹ��ťʱ����ɫ
	}

	@Override
	public void mouseEntered(MouseEvent e) {

		AbstractButton button = (AbstractButton) e.getComponent();
		button.setBackground(new Color(177, 35, 35));// ���밴ťʱ����ɫ
	}

	@Override
	public void mouseExited(MouseEvent e) {

		AbstractButton button = (AbstractButton) e.getComponent();
		button.setBackground(new Color(198, 47, 47));// �뿪��ťʱ����ɫ
	}

}
