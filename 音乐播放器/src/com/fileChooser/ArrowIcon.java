package com.fileChooser;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.AffineTransform;

import javax.swing.Icon;
import javax.swing.SwingConstants;

/**
 * 箭头Icon
 * 
 * @author tang
 */
public  class ArrowIcon implements Icon {

	protected int iconWidth;
	protected int iconHeight;
	protected int triangleWidth;
	protected int triangleHeight;
	protected Color triangleColor;
	protected int direction;
	protected Polygon triangle = new Polygon();

	public ArrowIcon(int width, int height, Color arrowColor, int direction) {
		this(width, height, width, height, arrowColor, direction);
	}

	public ArrowIcon(int iconWidth, int iconHeight, int triangleWidth, int triangleHeight, Color triangleColor, int direction) {
		this.iconWidth = iconWidth;
		this.iconHeight = iconHeight;
		this.triangleWidth = triangleWidth;
		this.triangleHeight = triangleHeight;
		this.triangleColor = triangleColor;
		this.direction = direction;

		createTriangle();
	}

	protected void createTriangle() {
		int x = (iconWidth - triangleWidth) / 2;
		int y = (iconHeight - triangleHeight) / 2;

		if (direction == SwingConstants.TOP) {// 箭头向上
			triangle.addPoint(triangleWidth / 2 + x, y);
			triangle.addPoint(triangleWidth + x, triangleHeight + y);
			triangle.addPoint(x, triangleHeight + y);
		} else if (direction == SwingConstants.BOTTOM) {// 箭头向下
			triangle.addPoint(x, y);
			triangle.addPoint(triangleWidth + x, y);
			triangle.addPoint(triangleWidth / 2 + x, triangleHeight + y);
		} else if (direction == SwingConstants.LEFT) {
			triangle.addPoint(x, triangleHeight / 2 + y);
			triangle.addPoint(triangleWidth + x, y);
			triangle.addPoint(triangleWidth + x, triangleHeight + y);
		} else if (direction == SwingConstants.RIGHT) {
			triangle.addPoint(x, y);
			triangle.addPoint(triangleWidth + x, triangleHeight / 2 + y);
			triangle.addPoint(x, triangleHeight + y);
		}
	}

	@Override
	public void paintIcon(Component c, Graphics g, int x, int y) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setColor(triangleColor);
		AffineTransform af = new AffineTransform();
		af.translate(x, y);
		Shape shape = af.createTransformedShape(triangle);
		g2.fill(shape);
	}

	@Override
	public int getIconWidth() {
		return iconWidth;
	}

	@Override
	public int getIconHeight() {
		return iconHeight;
	}
}
