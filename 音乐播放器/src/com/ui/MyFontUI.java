package com.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;
import java.text.AttributedCharacterIterator;

public class MyFontUI extends Font {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String name;

	public MyFontUI(String name, int style, int size) {
		super(name, style, size);
		this.name= name;
		
	}
	
	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g.create();
		//¿¹¾â³ÝÐ§¹û
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		g2d.setPaint(Color.BLACK);
//		try {
			g2d.setFont(new Font("ËÎÌå", Font.PLAIN, 22));//loadFont()
//		} catch (IOException e) {
//			e.printStackTrace();
//		} catch (FontFormatException e) {
//			e.printStackTrace();
//		}
		g2d.drawString(name, 10, 50);//
		g2d.dispose();
	}

	public Font loadFont() throws IOException, FontFormatException {

		String fontfilename = "font/myFont.ttf";
		Font actionJson = Font.createFont(Font.TRUETYPE_FONT, new File(fontfilename));// is
		Font actionJsonBase = actionJson.deriveFont(Font.BOLD, 20);// 
		return actionJsonBase;
	}
}