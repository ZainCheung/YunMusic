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
	public MusicBase musicBase = MusicBase.getMusicBase();// ���ֲ�����
	CurSorMouseListener cursorListener = new CurSorMouseListener();// ��������
	SystemUtils systemUtils=new SystemUtils();//����ϵͳ�����Ĺ���
	public JPanel playerControlPane;// �����������
	public JPanel sliderPanel;
	MySliderUI timeSliderUI = new MySliderUI();//
	MySliderUI voiceSliderUI = new MySliderUI();
	// UIDefaults timeSliderUI = new UIDefaults();
	Color playPanelColor = new Color(246, 246, 248);// ���������ɫ
	public JLabel nowTimeLabel;// ��ǰ��������ǩ
	public JLabel endTimelabel;// ����ʱ����ǩ
	JSlider timeSlider;// ʱ�������
	JSlider voiceSlider;// ����������
	public JButton lastSongButton;// ��һ�װ�ť
	public JButton pouseButton;// ������ͣ��ť
	public JButton nextSongButton;// ��һ�װ�ť
	public JButton voiceButton;// ������ť
	public JButton playModelButton;// ����ģʽ��ť
	public JButton playListButton;// �����б�ť
	public Timer timer = new Timer();// ��ʱ��
	public TimerTask task;// ����
	public int timeLenth = 0;// ����ʱ��
	int voiceValue=-25;//������С��ʼֵ
	int tempVoice ;
	int count = 1;// ��������
	int time=0;//��ǰ������ʱ��
	int number;
	
	String musicPath ;//��ǰ��������·��
	String second = null;// ��
	String minute = null;// ��
	public boolean paused = true;// ��ͣ״̬,��ʼ��Ϊ��ͣ
	private boolean noVoice=false;//����

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
	 * ���ø���ʱ��
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
	 * ��ʱ���ʽ��������罫120��ת��Ϊ02:00���ַ���
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
	 * ��ʱ���ʽ��������罫02:00���ַ���ת��Ϊ120������
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
		lastSongButton.setContentAreaFilled(false);//ȡ����ť��ѹʱ�ı�����
		lastSongButton.setCursor(new Cursor(Cursor.HAND_CURSOR));//Ϊ��ť���С�����Ч��
		playPanel.add(lastSongButton);

		pouseButton = new JButton("");
		pouseButton.setIcon(new ImageIcon(SliderPanel.class.getResource("/begin/player.png")));
		pouseButton.setFocusPainted(false);
		pouseButton.setBorderPainted(false);
		pouseButton.setBackground(playPanelColor);
		pouseButton.setContentAreaFilled(false);//ȡ����ť��ѹʱ�ı�����
		pouseButton.setCursor(new Cursor(Cursor.HAND_CURSOR));//Ϊ��ť���С�����Ч��
		playPanel.add(pouseButton);

		nextSongButton = new JButton("");
		nextSongButton.setIcon(new ImageIcon(SliderPanel.class.getResource("/begin/nextsong.png")));
		nextSongButton.setFocusPainted(false);
		nextSongButton.setBorderPainted(false);
		nextSongButton.setBackground(playPanelColor);
		nextSongButton.setContentAreaFilled(false);//ȡ����ť��ѹʱ�ı�����
		nextSongButton.setCursor(new Cursor(Cursor.HAND_CURSOR));//Ϊ��ť���С�����Ч��
		playPanel.add(nextSongButton);

		sliderPanel = new JPanel();
		sliderPanel.setLayout(new BorderLayout(0, 0));
		playerControlPane.add(sliderPanel, BorderLayout.CENTER);

		JPanel timePanel = new JPanel();
		timePanel.setBackground(new Color(246, 246, 248));
		timePanel.setPreferredSize(new Dimension(playerControlPane.getWidth() - 730, 70));
		timePanel.setLayout(new BorderLayout(20, 0));// �߽粼�ֺ���Ϊ20
		sliderPanel.add(timePanel, BorderLayout.CENTER);

		nowTimeLabel = new JLabel("00:00");
		nowTimeLabel.setFont(new Font("΢���ź�", Font.PLAIN, 20));
		timePanel.add(nowTimeLabel, BorderLayout.WEST);

		timeSlider = new JSlider(0, timeLenth, 0);
		timeSlider.setBackground(new Color(246, 246, 248));
		timeSlider.setUI(timeSliderUI);// timesliderui
		timeSlider.setEnabled(false);// ���ɱ༭
		timePanel.add(timeSlider, BorderLayout.CENTER);

		endTimelabel = new JLabel(getFormattime(timeLenth));
		endTimelabel.setFont(new Font("΢���ź�", Font.PLAIN, 20));
		timePanel.add(endTimelabel, BorderLayout.EAST);

		JPanel voicePanel = new JPanel();
		voicePanel.setBackground(new Color(246, 246, 248));
		voicePanel.setPreferredSize(new Dimension(430, 70));
		FlowLayout fl_voicePanel = new FlowLayout();
		fl_voicePanel.setHgap(5);// ˮƽ���
		fl_voicePanel.setVgap(20);// �����ֱ���
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
					voiceSlider.setValue(-50);//����ʱ����������
				}else {
					voiceButton.setIcon(new ImageIcon(SliderPanel.class.getResource("/begin/voice.png")));
					musicBase.voiceValue = tempVoice;
					voiceSlider.setValue(tempVoice);//�������ʱ�ص�����ʱ��С
				}
				noVoice = !noVoice;
			}
		});
		voicePanel.add(voiceButton);

		voiceSlider = new JSlider(-50,0,0);//����ֻ�ܴ�-80��6
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
		array[0] = "song//BINGBIAN����.mp3";
		array[1] = "song//�����.mp3";
		times[1] = 247;
		times[0] = 325;

	}

	class TimePlayAction implements ActionListener {

		
		

		@Override
		public void actionPerformed(ActionEvent e) {
			
			if (musicPath==null) {
				return;
			}
			
			if (paused == true) {// ���������ͣ״̬
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