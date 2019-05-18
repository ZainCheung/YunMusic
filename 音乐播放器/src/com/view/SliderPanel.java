package com.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.UIDefaults;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.metal.MetalSliderUI;

import com.event.CurSorMouseListener;
import com.event.TimePlayActionTest;
import com.model.MusicBase;
import com.model.PlayRunable;
import com.tool.SystemUtils;
import com.ui.MySliderUI;
import javax.swing.ImageIcon;

public class SliderPanel extends JFrame {

	public static SliderPanel newsliderPanel = new SliderPanel();
	public MusicBase musicBase = MusicBase.getMusicBase();// 音乐播放器
	CurSorMouseListener cursorListener = new CurSorMouseListener();// 鼠标监听器
	SystemUtils systemUtils=new SystemUtils();//控制系统音量的工具
	public JPanel playerControlPane;// 进度条主面板
	public JPanel sliderPanel;
	MySliderUI timeSliderUI = new MySliderUI();//
	MySliderUI voiceSliderUI = new MySliderUI();
	// UIDefaults timeSliderUI = new UIDefaults();
	Color playPanelColor = new Color(246, 246, 248);// 播放面板颜色
	public JLabel nowTimeLabel;// 当前播放量标签
	public JLabel endTimelabel;// 播放时长标签
	JSlider timeSlider;// 时间进度条
	JSlider voiceSlider;// 音量进度条
	public JButton lastSongButton;// 上一首按钮
	public JButton pouseButton;// 播放暂停按钮
	public JButton nextSongButton;// 下一首按钮
	public JButton voiceButton;// 静音按钮
	public JButton playModelButton;// 播放模式按钮
	public JButton playListButton;// 播放列表按钮
	public Timer timer = new Timer();// 计时器
	public TimerTask task;// 任务
	public int timeLenth = 0;// 音乐时长
	int voiceValue=-25;//声音大小初始值
	int tempVoice ;
	int count = 1;// 秒数计数
	int time=0;//当前歌曲总时长
	int number;
	
	String musicPath ;//当前歌曲绝对路径
	String second = null;// 秒
	String minute = null;// 分
	public boolean paused = true;// 暂停状态,初始化为暂停
	private boolean noVoice=false;//静音

	String[] array = new String[2];
	int[] times = new int[2];

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}

	public String getMusicPath() {
		return musicPath;
	}

	public void setMusicPath(String musicPath) {
		this.musicPath = musicPath;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public JButton getLastSongButton() {
		return lastSongButton;
	}

	public void setLastSongButton(JButton lastSongButton) {
		this.lastSongButton = lastSongButton;
	}

	public JButton getNextSongButton() {
		return nextSongButton;
	}

	public void setNextSongButton(JButton nextSongButton) {
		this.nextSongButton = nextSongButton;
	}

	public JLabel getEndTimelabel() {
		return endTimelabel;
	}

	/**
	 * 设置歌曲时长
	 * @param time
	 */
	public void setEndTimelabel(int time) {
		this.endTimelabel.setText(getFormattime(time));;
	}

	public JSlider getTimeSlider() {
		return timeSlider;
	}

	public void setTimeSlider(JSlider timeSlider) {
		this.timeSlider = timeSlider;
	}

	public JSlider getVoiceSlider() {
		return voiceSlider;
	}

	public void setVoiceSlider(JSlider voiceSlider) {
		this.voiceSlider = voiceSlider;
	}

	public JButton getPouseButton() {
		return pouseButton;
	}

	public void setPouseButton(JButton pouseButton) {
		this.pouseButton = pouseButton;
	}

	public int getTimeLenth() {
		return timeLenth;
	}

	public void setTimeLenth(int timeLenth) {
		this.timeLenth = timeLenth;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	/**
	 * 将时间格式化输出例如将120秒转换为02:00的字符串
	 * 
	 * @param count
	 * @return
	 */
	public static String getFormattime(int count) {
		String second = null;
		String minute = null;
		if (count / 60 < 10) {
			minute = "0" + (count / 60);
		} else {
			minute = "" + (count / 60);
		}
		if (count % 60 < 10) {
			second = ":0" + count % 60;
		} else {
			second = ":" + count % 60;
		}
		return minute + second;

	}
	/**
	 * 将时间格式化输出例如将02:00的字符串转换为120秒整形
	 * @param time
	 * @return
	 */
	public static int timeToInt(String time) {
		if (time==null||time.equals("--:--")) {
			return 0;
		}
		String[] times=time.split(":");
		int minute = Integer.parseInt(times[0]);
		int second = Integer.parseInt(times[1]);
		return minute*60+second;
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SliderPanel frame = new SliderPanel();
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
	public SliderPanel() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
		setBounds(150, 50, screensize.width - 300, 132);// 1310, 771
		playerControlPane = new JPanel();
		playerControlPane.setBorder(new EmptyBorder(2, 0, 0, 0));
		setContentPane(playerControlPane);
		playerControlPane.setLayout(new BorderLayout(0, 0));

		JPanel playPanel = new JPanel();
		playPanel.setBackground(playPanelColor);
		playPanel.setPreferredSize(new Dimension(300, 200));
		playerControlPane.add(playPanel, BorderLayout.WEST);
		playPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

		lastSongButton = new JButton("");
		lastSongButton.setIcon(new ImageIcon(SliderPanel.class.getResource("/begin/lastsong.png")));
		lastSongButton.setFocusPainted(false);
		lastSongButton.setBorderPainted(false);
		lastSongButton.setBackground(playPanelColor);
		lastSongButton.setContentAreaFilled(false);//取消按钮按压时的背景框
		lastSongButton.setCursor(new Cursor(Cursor.HAND_CURSOR));//为按钮添加小手鼠标效果
		playPanel.add(lastSongButton);

		pouseButton = new JButton("");
		pouseButton.setIcon(new ImageIcon(SliderPanel.class.getResource("/begin/player.png")));
		pouseButton.setFocusPainted(false);
		pouseButton.setBorderPainted(false);
		pouseButton.setBackground(playPanelColor);
		pouseButton.setContentAreaFilled(false);//取消按钮按压时的背景框
		pouseButton.setCursor(new Cursor(Cursor.HAND_CURSOR));//为按钮添加小手鼠标效果
		playPanel.add(pouseButton);

		nextSongButton = new JButton("");
		nextSongButton.setIcon(new ImageIcon(SliderPanel.class.getResource("/begin/nextsong.png")));
		nextSongButton.setFocusPainted(false);
		nextSongButton.setBorderPainted(false);
		nextSongButton.setBackground(playPanelColor);
		nextSongButton.setContentAreaFilled(false);//取消按钮按压时的背景框
		nextSongButton.setCursor(new Cursor(Cursor.HAND_CURSOR));//为按钮添加小手鼠标效果
		playPanel.add(nextSongButton);

		sliderPanel = new JPanel();
		sliderPanel.setLayout(new BorderLayout(0, 0));
		playerControlPane.add(sliderPanel, BorderLayout.CENTER);

		JPanel timePanel = new JPanel();
		timePanel.setBackground(new Color(246, 246, 248));
		timePanel.setPreferredSize(new Dimension(playerControlPane.getWidth() - 730, 70));
		timePanel.setLayout(new BorderLayout(20, 0));// 边界布局横间隔为20
		sliderPanel.add(timePanel, BorderLayout.CENTER);

		nowTimeLabel = new JLabel("00:00");
		nowTimeLabel.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		timePanel.add(nowTimeLabel, BorderLayout.WEST);

		timeSlider = new JSlider(0, timeLenth, 0);
		timeSlider.setBackground(new Color(246, 246, 248));
		timeSlider.setUI(timeSliderUI);// timesliderui
		timeSlider.setEnabled(false);// 不可编辑
		timePanel.add(timeSlider, BorderLayout.CENTER);

		endTimelabel = new JLabel(getFormattime(timeLenth));
		endTimelabel.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		timePanel.add(endTimelabel, BorderLayout.EAST);

		JPanel voicePanel = new JPanel();
		voicePanel.setBackground(new Color(246, 246, 248));
		voicePanel.setPreferredSize(new Dimension(430, 70));
		FlowLayout fl_voicePanel = new FlowLayout();
		fl_voicePanel.setHgap(5);// 水平间距
		fl_voicePanel.setVgap(20);// 组件垂直间距
		voicePanel.setLayout(fl_voicePanel);
		sliderPanel.add(voicePanel, BorderLayout.EAST);
		
		

		voiceButton = new JButton("");
		voiceButton.setIcon(new ImageIcon(SliderPanel.class.getResource("/begin/voice.png")));
		voiceButton.setFocusPainted(false);
		voiceButton.setBorderPainted(false);
		voiceButton.setBackground(playPanelColor);
		voiceButton.addMouseListener(cursorListener);
		voiceButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				tempVoice = voiceSlider.getValue();

				if (!noVoice) {
					voiceButton.setIcon(new ImageIcon(SliderPanel.class.getResource("/begin/noVoice.png")));
					musicBase.voiceValue=-80;
					voiceSlider.setValue(-50);//静音时进度条清零
				}else {
					voiceButton.setIcon(new ImageIcon(SliderPanel.class.getResource("/begin/voice.png")));
					musicBase.voiceValue = tempVoice;
					voiceSlider.setValue(tempVoice);//解除静音时回到静音时大小
				}
				noVoice = !noVoice;
			}
		});
		voicePanel.add(voiceButton);

		voiceSlider = new JSlider(-50,0,0);//音量只能从-80到6
		voiceSlider.setUI(voiceSliderUI);// voiceSliderUI
		voicePanel.add(voiceSlider);
		
		
		
		voiceSlider.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				
				musicBase.voiceValue = voiceSlider.getValue();
				if (voiceSlider.getValue()==-50) {
					musicBase.voiceValue=-80;
				}
				voiceValue = voiceSlider.getValue();
				tempVoice = getVoiceSlider().getValue();
			}
		});

		playModelButton = new JButton("");
		playModelButton.setIcon(new ImageIcon(SliderPanel.class.getResource("/playmodel/sequence.png")));
		playModelButton.setFocusPainted(false);
		playModelButton.setBorderPainted(false);
		playModelButton.setBackground(playPanelColor);
		playModelButton.addMouseListener(cursorListener);
		voicePanel.add(playModelButton);

		playListButton = new JButton("");
		playListButton.setIcon(new ImageIcon(SliderPanel.class.getResource("/begin/playList.png")));
		playListButton.setFocusPainted(false);
		playListButton.setBorderPainted(false);
		playListButton.setBackground(playPanelColor);
		playListButton.addMouseListener(cursorListener);
		voicePanel.add(playListButton);

//		task = new TimerTask() {
//			@Override
//			public void run() {
//				nowTimeLabel.setText(getFormattime(count));
//				timeSlider.setValue(count);
//				count++;
//				if (count == timeLenth) {
//					cancel();
//					timeSlider.setValue(0);
//					count = 0;
//					nowTimeLabel.setText(getFormattime(count));
//				}
//			}
//		};
		array[0] = "song//BINGBIAN病变.mp3";
		array[1] = "song//起风了.mp3";
		times[1] = 247;
		times[0] = 325;

	}

	class TimePlayAction implements ActionListener {

		
		

		@Override
		public void actionPerformed(ActionEvent e) {
			
			if (musicPath==null) {
				return;
			}
			
			if (paused == true) {// 如果处于暂停状态
				pouseButton.setIcon(new ImageIcon(SliderPanel.class.getResource("/click/player.png")));
			
					
					musicBase = new MusicBase();
					musicBase.playFile(musicPath);
					timer = new Timer();
					task = new TimerTask() {
						@Override
						public void run() {
							nowTimeLabel.setText(getFormattime(count));
							timeSlider.setValue(count);
							count++;
							if (count == timeLenth) {// ||!paused

								timer.cancel();//
								pouseButton.setIcon(new ImageIcon(SliderPanel.class.getResource("/begin/player.png")));
								timeSlider.setValue(0);
								count = 1;
								nowTimeLabel.setText("00:00");// getFormattime(count)
							}
						}
					};
				
				timer.schedule(task, 1000L, 1000L);

			} else {
				pouseButton.setIcon(new ImageIcon(SliderPanel.class.getResource("/begin/player.png")));
				musicBase.stop();
				timer.cancel();
				timeSlider.setValue(0);
				count = 1;
				nowTimeLabel.setText("00:00");// getFormattime(count)
			}
			paused = !paused;
		}
		
		public void playTimeSlider(int time,String playUrl) {
			
		}

	}
}