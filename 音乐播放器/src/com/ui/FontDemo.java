package com.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class FontDemo extends JPanel {
	private static final long serialVersionUID = 1L;
	private String string;
	public FontDemo(String string) {
		super();
		this.string=string;
	}

	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g.create();
		//抗锯齿效果
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		g2d.setPaint(Color.BLACK);
//		try {
			g2d.setFont(new Font("宋体", Font.PLAIN, 22));//loadFont()
//		} catch (IOException e) {
//			e.printStackTrace();
//		} catch (FontFormatException e) {
//			e.printStackTrace();
//		}
		g2d.drawString(string, 10, 50);//
		g2d.dispose();
	}

	public Font loadFont() throws IOException, FontFormatException {

		String fontfilename = "font/myFont.ttf";
		InputStream is =new FileInputStream(new File(fontfilename));
		//InputStream is = this.getClass().getResourceAsStream(fontfilename);
		Font actionJson = Font.createFont(Font.TRUETYPE_FONT, new File(fontfilename));// is
		Font actionJsonBase = actionJson.deriveFont(Font.BOLD, 20);// 
		return actionJsonBase;
	}

	public static void main(String args[]) {
		JFrame ui = new JFrame("Font Demo Graphics2D");
		ui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ui.getContentPane().setLayout(new BorderLayout());
		ui.getContentPane().add(new FontDemo("你好"), BorderLayout.CENTER);
		ui.setPreferredSize(new Dimension(380, 380));//
		ui.pack();
		ui.setVisible(true);
	}

}
