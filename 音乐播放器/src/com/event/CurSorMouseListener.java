package com.event;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.AbstractButton;

import com.start.MusicDemo;

public class CurSorMouseListener implements MouseListener {
	
	@Override
	public void mouseClicked(MouseEvent e) {
		
		
	}

	@SuppressWarnings("deprecation")
	@Override
	public void mouseEntered(MouseEvent e) {
		AbstractButton button = (AbstractButton) e.getComponent();
		//MusicDemo.frame.setCursor(Cursor.HAND_CURSOR);
		button.setForeground(Color.BLACK);//����ʱ��ť�������
	}

	@Override
	public void mouseExited(MouseEvent e) {
		AbstractButton button = (AbstractButton) e.getComponent();
		//MusicDemo.frame.setCursor(Cursor.DEFAULT_CURSOR);
		button.setForeground(new Color(92,92,92));//�뿪ʱ��ť����ָ�
	}

	@Override
	public void mousePressed(MouseEvent e) {
		
		

	}

	@Override
	public void mouseReleased(MouseEvent e) {

		
	}

}
