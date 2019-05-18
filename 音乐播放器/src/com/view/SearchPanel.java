package com.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableCellRenderer;

import com.event.MyTableMouseMotionListener;
import com.model.MyMusicTableModel;
import com.model.SearchMusicTableModel;
import com.start.MusicDemo;
import com.test.AddShowButton;
import com.tool.ReadAndWriteFile;

import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.SwingConstants;

public class SearchPanel extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public JPanel searchPanel;
	JButton songButton;
	JButton singerButton;
	JButton albumButton;
	JButton songListButton;
	public static Color whiteColor = new Color(250, 250, 250);// 白色
	public List<List<String>> searchMusicData = new ArrayList<List<String>>();
	SearchMusicTableModel titleModel;// 标题模型
	public SearchMusicTableModel dataModel;// MP3列表模型
	public JTable titleLable;// 标题栏表格
	public JTable table;// 内容表格
	ReadAndWriteFile readfile = new ReadAndWriteFile();
	
	public JLabel searchTextLabel;
	public JLabel resultLabel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SearchPanel frame = new SearchPanel();
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
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public SearchPanel() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1313, 846);
		searchPanel = new JPanel();
		searchPanel.setBorder(null);
		searchPanel.setBackground(new Color(250, 250, 250));
		searchPanel.setLayout(new BorderLayout(0, 0));
		setContentPane(searchPanel);

		JPanel topPanel = new JPanel();
		topPanel.setPreferredSize(new Dimension(1313, 205));
		topPanel.setBackground(new Color(250, 250, 250));
		searchPanel.add(topPanel, BorderLayout.NORTH);
		topPanel.setLayout(new BorderLayout(0, 0));

		JPanel showPanel = new JPanel() {
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.setColor(Color.gray);
				g.drawLine(0, 160, getContentPane().getWidth(), 160);
			}
		};
		showPanel.setPreferredSize(new Dimension(1313, 205));
		showPanel.setBackground(new Color(250, 250, 250));
		topPanel.add(showPanel, BorderLayout.CENTER);
		showPanel.setLayout(null);

		JLabel searchLabel = new JLabel("\u641C\u7D22");
		searchLabel.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		searchLabel.setBounds(47, 30, 50, 30);
		showPanel.add(searchLabel);

		searchTextLabel = new JLabel("10");
		searchTextLabel.setForeground(Color.BLUE);
		searchTextLabel.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		searchTextLabel.setBounds(97, 30, 590, 30);
		showPanel.add(searchTextLabel);

		resultLabel = new JLabel("\u627E\u5230100\u9996\u5355\u66F2");
		resultLabel.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		resultLabel.setBounds(47, 58, 239, 30);
		showPanel.add(resultLabel);

		songButton = new JButton("\u5355\u66F2");
		songButton.setVerticalAlignment(SwingConstants.TOP);
		songButton.setFocusPainted(false);
		songButton.setBorderPainted(false);
		songButton.setBackground(whiteColor);
		songButton.setContentAreaFilled(false);// 取消按钮按压时的背景框
		songButton.setCursor(new Cursor(Cursor.HAND_CURSOR));// 为按钮添加小手鼠标效果
		songButton.setFont(new Font("黑体", Font.PLAIN, 25));
		songButton.setBounds(100, 110, 90, 60);
		showPanel.add(songButton);

		singerButton = new JButton("\u6B4C\u624B");
		singerButton.setVerticalAlignment(SwingConstants.TOP);
		singerButton.setFocusPainted(false);
		singerButton.setBorderPainted(false);
		singerButton.setBackground(whiteColor);
		singerButton.setContentAreaFilled(false);// 取消按钮按压时的背景框
		singerButton.setCursor(new Cursor(Cursor.HAND_CURSOR));// 为按钮添加小手鼠标效果
		singerButton.setFont(new Font("黑体", Font.PLAIN, 25));
		singerButton.setBounds(270, 110, 90, 60);
		showPanel.add(singerButton);

		albumButton = new JButton("\u4E13\u8F91");
		albumButton.setVerticalAlignment(SwingConstants.TOP);
		albumButton.setFocusPainted(false);
		albumButton.setBorderPainted(false);
		albumButton.setBackground(whiteColor);
		albumButton.setContentAreaFilled(false);// 取消按钮按压时的背景框
		albumButton.setCursor(new Cursor(Cursor.HAND_CURSOR));// 为按钮添加小手鼠标效果
		albumButton.setFont(new Font("黑体", Font.PLAIN, 25));
		albumButton.setBounds(440, 110, 90, 60);
		showPanel.add(albumButton);

		songListButton = new JButton("\u6B4C\u5355");
		songListButton.setVerticalAlignment(SwingConstants.TOP);
		songListButton.setFocusPainted(false);
		songListButton.setBorderPainted(false);
		songListButton.setBackground(whiteColor);
		songListButton.setContentAreaFilled(false);// 取消按钮按压时的背景框
		songListButton.setCursor(new Cursor(Cursor.HAND_CURSOR));// 为按钮添加小手鼠标效果
		songListButton.setFont(new Font("黑体", Font.PLAIN, 25));
		songListButton.setBounds(610, 110, 90, 60);
		showPanel.add(songListButton);

		titleModel = new SearchMusicTableModel();//标题栏模型
		titleModel.data = new Vector(1, 1);
		titleModel.data.add("");
		titleModel.data.add("操作");
		titleModel.data.add("音乐标题");
		titleModel.data.add("歌手");
		titleModel.data.add("专辑");
		titleModel.data.add("时长");
		titleModel.titles = new Vector(1, 1);
		for (int i = 0; i < 6; i++) {
			titleModel.titles.add(i);
		}
		//测试数据
		//searchMusicData = readfile.getMyMusicListData(new File("file//myMusicList.txt"));
		searchMusicData = null;
		dataModel = new SearchMusicTableModel();
		dataModel.data = new Vector(1, 1);
		if (searchMusicData != null) {
			for (int i = 0; i < 100; i++) {//searchMusicData.size()
				for (int j = 0; j < 6; j++) {
					if (j == 0) {
						dataModel.data.add("   " + searchMusicData.get(i).get(j));// "手动"居中
					} else {
						dataModel.data.add(searchMusicData.get(i).get(j-1));
					}
				}
			}
		} else {
			// 提示无歌曲
			//System.out.println("当前歌曲为空");
		}

		dataModel.titles = new Vector(1, 1);
		for (int i = 0; i < 6; i++) {
			dataModel.titles.add(i);
		}

		
		titleLable = new JTable(titleModel);// 生成标题栏
		titleLable.setRowHeight(44);
		titleLable.setShowVerticalLines(false);
		titleLable.setShowHorizontalLines(false);
		titleLable.setColumnModel(titleModel.getColumn(titleLable, titleModel.columnWidth));
		titleLable.setBackground(new Color(245, 245, 247));
		titleLable.setSelectionBackground(new Color(250, 250, 250));
		titleLable.setFont(new Font("微软雅黑", Font.PLAIN, 19));
		titleLable.selectAll();
		topPanel.add(titleLable, BorderLayout.SOUTH);
		
		// 生成音乐列表
		table = new JTable(dataModel)//
//		{
//		    @Override
//		    public Component prepareRenderer(TableCellRenderer renderer, int row, int column){
//		        Component comp = super.prepareRenderer(renderer,row,column);
//		        Point p = getMousePosition();
//		        if(p!=null){
//		            int rowUnderMouse = rowAtPoint(p);
//		            int columnUnderMouse = columnAtPoint(p);
//		            if(rowUnderMouse == row){
//		                  comp.setBackground(new Color(236,237,238));//鼠标悬停颜色
//		           } else{
//		                  //comp.setBackGround(DefaultLookup.getColor(this,ui,"Table.alternateRowColor"));
//		           }
//		            if (columnUnderMouse==1) {
//		                	  setCursor(new Cursor(Cursor.HAND_CURSOR));
//					}else {
//						 setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
//					}
//		        }
//		        return comp;
//		    }
//		}
		;
		
		table.setDefaultRenderer(Object.class, dataModel);// 设置表格属性new MyMusicTableModel()
		table.setRowHeight(45);
		table.setShowVerticalLines(false);
		table.setShowHorizontalLines(false);
		table.setColumnModel(dataModel.getColumn(table, dataModel.columnWidth));
		table.setBackground(new Color(250, 250, 250));// 245, 245, 247
		table.setSelectionBackground(new Color(227, 227, 229));
		table.setFont(new Font("宋体", Font.PLAIN, 22));
		table.addMouseMotionListener(new MyTableMouseMotionListener());
		MyMusicTableModel.setColumnColor(table);// 设置间隔色
		
		new AddShowButton(table, 1);

		searchPanel.add(table, BorderLayout.CENTER);
	}
}
