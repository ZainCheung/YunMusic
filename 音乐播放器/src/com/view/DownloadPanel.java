package com.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import com.event.PlayAllMouseActionListerner;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class DownloadPanel extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public JPanel downloadPane;
	JButton downloadingButton;
	JButton downloadedButton;
	JButton[] buttons;
	PlayAllMouseActionListerner mouseAction;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DownloadPanel frame = new DownloadPanel();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public DownloadPanel() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1313, 846);
		downloadPane = new JPanel();
		downloadPane.setBackground(new Color(250, 250, 250));
		downloadPane.setBorder(null);
		downloadPane.setLayout(new BorderLayout(0, 0));
		setContentPane(downloadPane);

		JPanel topPanel = new JPanel();
		topPanel.setPreferredSize(new Dimension(1313, 235));
		topPanel.setBackground(new Color(250, 250, 250));
		downloadPane.add(topPanel, BorderLayout.NORTH);
		topPanel.setLayout(new BorderLayout(0, 0));

		JPanel showPanel = new JPanel() {
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.setColor(Color.gray);
				g.drawLine(0, 105, getContentPane().getWidth(), 105);
			}
		};
		showPanel.setPreferredSize(new Dimension(1313, 190));
		showPanel.setBackground(new Color(250, 250, 250));
		topPanel.add(showPanel, BorderLayout.CENTER);
		showPanel.setLayout(new BorderLayout(0, 0));

		JPanel buttonPanel = new JPanel();
		buttonPanel.setPreferredSize(new Dimension(1313, 90));
		buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 30));
		buttonPanel.setBackground(new Color(250, 250, 250));
		showPanel.add(buttonPanel, BorderLayout.NORTH);

		downloadedButton = new JButton("\u5DF2\u4E0B\u8F7D\u5355\u66F2");
		downloadedButton.setFocusPainted(false);
		downloadedButton.setBackground(new Color(124, 125, 133));
		downloadedButton.setForeground(new Color(250, 250, 250));
		// downloadedButton.setContentAreaFilled(false);// 取消按钮按压时的背景框
		downloadedButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
		downloadedButton.setFont(new Font("微软雅黑", Font.PLAIN, 22));
		buttonPanel.add(downloadedButton);

		downloadingButton = new JButton("\u6B63\u5728\u4E0B\u8F7D\u4E2D");
		downloadingButton.setFocusPainted(false);
		downloadingButton.setBackground(new Color(250, 250, 250));
		downloadingButton.setForeground(new Color(92, 92, 92));
		// downloadingButton.setContentAreaFilled(false);
		downloadingButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
		downloadingButton.setFont(new Font("微软雅黑", Font.PLAIN, 22));
		buttonPanel.add(downloadingButton);
		
		JPanel playPanel = new JPanel(){
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.setColor(Color.gray);
				g.drawLine(0, 15 ,getContentPane().getWidth(), 15);
			}
		};
		playPanel.setBackground(new Color(250, 250, 250));
		showPanel.add(playPanel, BorderLayout.CENTER);
		playPanel.setLayout(null);
		
		JButton playAllButton = new JButton("\u64AD\u653E\u5168\u90E8");
		playAllButton.setForeground(Color.WHITE);
		playAllButton.setBackground(new Color(198, 47, 47));
		playAllButton.setFocusPainted(false);
		playAllButton.setBorderPainted(false);
		playAllButton.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		//playAllButton.setContentAreaFilled(false);//取消按钮按压时的背景框
		playAllButton.setCursor(new Cursor(Cursor.HAND_CURSOR));// 为按钮添加小手鼠标效果
		playAllButton.addMouseListener(new PlayAllMouseActionListerner());
		playAllButton.setBounds(50, 35, 140, 45);
		playPanel.add(playAllButton);
		
		JLabel tipLabel = new JLabel("\u5B58\u50A8\u76EE\u5F55:");
		tipLabel.setFont(new Font("微软雅黑", Font.PLAIN, 22));
		tipLabel.setBounds(318, 34, 99, 45);
		playPanel.add(tipLabel);
		
		JButton changeFloderButton = new JButton("\u66F4\u6362\u76EE\u5F55");
		changeFloderButton.setHorizontalAlignment(SwingConstants.LEFT);
		changeFloderButton.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		changeFloderButton.setFocusPainted(false);
		changeFloderButton.setBorderPainted(false);
		changeFloderButton.setBackground(new Color(250, 250, 250));
		changeFloderButton.setForeground(new Color(130, 210, 250));
		changeFloderButton.setContentAreaFilled(false);
		changeFloderButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
		changeFloderButton.setBounds(192, 35, 126, 45);
		playPanel.add(changeFloderButton);
		
		JLabel floderLabel = new JLabel("D:/YunMusic");
		floderLabel.setFont(new Font("微软雅黑", Font.PLAIN, 22));
		floderLabel.setBounds(432, 35, 493, 45);
		playPanel.add(floderLabel);

		downloadedButton.addActionListener(new ButtonAction());
		downloadingButton.addActionListener(new ButtonAction());

		//UIManager.put("Button.select", new Color(250, 250, 250));// 点击按钮瞬间颜色new Color(124, 125, 133)
	}

	class ButtonAction implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			
			

			if (e.getSource() == downloadedButton) {
				
				/**
				 * EDT处理界面事件
				 */
				SwingUtilities.invokeLater(() -> {
					
					downloadedButton.setBackground(new Color(124, 125, 133));
					downloadedButton.setForeground(new Color(250, 250, 250));
					
					downloadingButton.setBackground(new Color(250, 250, 250));
					downloadingButton.setForeground(new Color(92, 92, 92));
					downloadPane.validate();
					downloadPane.repaint();
				});
			}  if (e.getSource() == downloadingButton) {
				
				SwingUtilities.invokeLater(() -> {
					
					downloadingButton.setBackground(new Color(124, 125, 133));
					downloadingButton.setForeground(new Color(250, 250, 250));
					
					downloadedButton.setBackground(new Color(250, 250, 250));
					downloadedButton.setForeground(new Color(92, 92, 92));
					downloadPane.validate();
					downloadPane.repaint();
				});

			}
		}
	}
}
