package com.view;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.border.EmptyBorder;

import com.ui.DemoScrollBarUI;

import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.UIManager;

public class CardLayoutDemo extends JFrame {

	private JPanel contentPane;
	int count=1;//卡片当前数量
	int location=1;//卡片当前位置
	final int CARDNUM=100;//设置卡片可生成总数量
	JButton oneButton;
	JButton twoButton;
	JButton threeButton;
	JButton fourButton;
	JButton previousButton;
	JButton nextButton;
	CardLayout card;
	JPanel cardPanel;
	JPanel firstPanel;
	JPanel secondPanel;
	JPanel thirdPanel;
	JPanel fourthPanel;
	MyMusicList musicList = new MyMusicList(); 
	String[] cardNames;
	ProduceCardPanelActionListener cardAction = new ProduceCardPanelActionListener();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CardLayoutDemo frame = new CardLayoutDemo();
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
	public CardLayoutDemo() {
		
		cardNames=new String[CARDNUM];
		for (int i = 0; i <cardNames.length; i++) {
			cardNames[i]= Integer.toString(i);
			
		}
		
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1563, 846);
		contentPane = new JPanel();
		contentPane.setBorder(null);
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		
		JPanel leftPanel = new JPanel();
		leftPanel.setBackground(Color.WHITE);
		leftPanel.setPreferredSize(new Dimension(200, 800));
		contentPane.add(leftPanel, BorderLayout.WEST);
		leftPanel.setLayout(null);
		
		oneButton = new JButton("\u754C\u9762\u4E00");
		oneButton.setBounds(15, 15, 132, 43);
		oneButton.setFont(new Font("微软雅黑", Font.PLAIN, 25));
		oneButton.addActionListener(cardAction);
		leftPanel.add(oneButton);
		
		twoButton = new JButton("\u754C\u9762\u4E8C");
		twoButton.setFont(new Font("微软雅黑", Font.PLAIN, 25));
		twoButton.setBounds(15, 73, 132, 43);
		twoButton.addActionListener(cardAction);
		leftPanel.add(twoButton);
		
		threeButton = new JButton("\u754C\u9762\u4E09");
		threeButton.setFont(new Font("微软雅黑", Font.PLAIN, 25));
		threeButton.setBounds(15, 131, 132, 43);
		threeButton.addActionListener(cardAction);
		leftPanel.add(threeButton);
		
		fourButton = new JButton("\u754C\u9762\u56DB");
		fourButton.setFont(new Font("微软雅黑", Font.PLAIN, 25));
		fourButton.setBounds(15, 189, 132, 43);
		fourButton.addActionListener(cardAction);
		leftPanel.add(fourButton);
		
		previousButton = new JButton("\u4E0A\u4E00\u4E2A");
		previousButton.setFont(new Font("微软雅黑", Font.PLAIN, 25));
		previousButton.setBounds(15, 253, 132, 43);
		previousButton.addActionListener(cardAction);
		previousButton.setEnabled(false);
		leftPanel.add(previousButton);
		
		nextButton = new JButton("\u4E0B\u4E00\u4E2A");
		nextButton.setFont(new Font("微软雅黑", Font.PLAIN, 25));
		nextButton.setBounds(15, 311, 132, 43);
		nextButton.addActionListener(cardAction);
		nextButton.setEnabled(false);
		leftPanel.add(nextButton);
		
		JScrollPane scrollPane1 = new JScrollPane();
		scrollPane1.setBackground(Color.WHITE);
		scrollPane1.setPreferredSize(new Dimension(200, 800));
		scrollPane1.getVerticalScrollBar().setUI(new DemoScrollBarUI());
		// 下面三句话设置滚动条的灵敏度
		JScrollBar Bar1 = null;
		Bar1 = scrollPane1.getVerticalScrollBar();
		Bar1.setUnitIncrement(40);
		contentPane.add(scrollPane1, BorderLayout.CENTER);
		
		card=new CardLayout();
		cardPanel = new JPanel();
		cardPanel.setLayout(card);
		firstPanel = musicList.myMusicListPane;//new JPanel()
		firstPanel.setBackground(Color.RED);
		cardPanel.add(cardNames[count],firstPanel);
		card.show(cardPanel, cardNames[count]);
		
		scrollPane1.setViewportView(cardPanel);
		//contentPane.add(cardPanel, BorderLayout.CENTER);
	}
	
	class ProduceCardPanelActionListener implements ActionListener{

	@Override
	public void actionPerformed(ActionEvent e) {
		
		previousButton.setEnabled(true);
		nextButton.setEnabled(true);
		
		if (e.getSource()==oneButton) {
			firstPanel = musicList.myMusicListPane;
			firstPanel.setBackground(Color.RED);
			cardPanel.add(cardNames[count],firstPanel);
			//card.next(cardPanel);//这里改用显示下一个页面,解决了第一个页面不显示问题
			card.show(cardPanel, cardNames[count]);
			count++;
			location++;
			nextButton.setEnabled(false);
			System.out.println("一共有:"+count+"张卡片");
			System.out.println("当前在第"+location+"张");
		}
		if (e.getSource()==twoButton) {
			secondPanel = new JPanel();
			secondPanel.setBackground(Color.YELLOW);
			cardPanel.add(cardNames[count],secondPanel);
			card.show(cardPanel, cardNames[count]);
			count++;
			location++;
			nextButton.setEnabled(false);
			System.out.println("一共有:"+count+"张卡片");
			System.out.println("当前在第"+location+"张");
		}
		if (e.getSource()==threeButton) {
			thirdPanel = new JPanel();
			thirdPanel.setBackground(Color.BLUE);
			cardPanel.add(cardNames[count],thirdPanel);
			card.show(cardPanel, cardNames[count]);
			count++;
			location++;
			nextButton.setEnabled(false);
			System.out.println("一共有:"+count+"张卡片");
			System.out.println("当前在第"+location+"张");
		}
		if (e.getSource()==fourButton) {
			fourthPanel = new JPanel();
			fourthPanel.setBackground(Color.GREEN);
			cardPanel.add(cardNames[count],fourthPanel);
			card.show(cardPanel, cardNames[count]);
			count++;
			location++;
			nextButton.setEnabled(false);
			System.out.println("一共有:"+count+"张卡片");
			System.out.println("当前在第"+location+"张");
		}
		if (e.getSource()==previousButton) {
			card.previous(cardPanel);
			location--;
			System.out.println("一共有:"+count+"张卡片");
			System.out.println("当前在第"+location+"张");
		}
		if (e.getSource()==nextButton) {
			card.next(cardPanel);
			location++;
			System.out.println("一共有:"+count+"张卡片");
			System.out.println("当前在第"+location+"张");
		}
		if (location==1) {//当前已经到第一页时
			previousButton.setEnabled(false);
		}
		if (location==count) {//当前是最后一页时
			nextButton.setEnabled(false);
		}
		
	}
	
}

}

