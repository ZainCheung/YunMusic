package com.fileChooser;

import java.awt.Component;
import java.awt.Graphics;

import javax.swing.Icon;

/**
 * 将原Icon位置移动的Icon
 * 
 * @author PC
 * 
 */
public  class MoveIcon implements Icon {
	Icon icon;
	int moveX;
	int moveY;

	public MoveIcon(Icon icon, int moveX, int moveY) {
		this.icon = icon;
		this.moveX = moveX;
		this.moveY = moveY;
	}

	@Override
	public void paintIcon(Component c, Graphics g, int x, int y) {
		icon.paintIcon(c, g, x + moveX, y + moveY);
	}

	@Override
	public int getIconWidth() {
		return icon.getIconWidth();
	}

	@Override
	public int getIconHeight() {
		return icon.getIconHeight();
	}
}