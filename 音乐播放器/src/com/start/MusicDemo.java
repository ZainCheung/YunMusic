package com.start;

import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.Point;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.time.Clock;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import com.event.CurSorMouseListener;
import com.event.MaxJFrame;
import com.event.MyTableMouseMotionListener;
import com.event.NormalJFrame;
import com.model.MyMusicTableModel;
import com.model.SearchMusicTableModel;
import com.net.DownloadFile;
import com.net.JsonPost;
import com.net.ReadJSON;
import com.sun.awt.AWTUtilities;
import com.test.AddShowButton;
import com.tool.ReadAndWriteFile;
import com.tool.ShadowBorder;
import com.ui.DemoScrollBarUI;
import com.ui.MySkin;
import com.ui.TextBorderUtlis;
import com.view.DownloadPanel;
import com.view.MyMusicList;
import com.view.SearchPanel;
import com.view.SliderPanel;
import com.model.*;

public class MusicDemo extends JFrame {

	private JPanel contentPane;
	private Point pressedPoint;
	public static MusicDemo musicDemo = new MusicDemo();

	MyMusicTableModel tbModel;// MP3列表模型
	List<List<String>> data;
	List<String> urlData;
	ReadAndWriteFile readfile = new ReadAndWriteFile();

	public static final String MUSICTITLE = "云音乐";
	public static JButton maxButton;
	public static JButton normalButton;
	TextBorderUtlis borderTool = new TextBorderUtlis(new Color(250, 250, 250));// ,5,true边框
	public MaxJFrame max = new MaxJFrame(this);// 最大化事件监听器
	Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
	public NormalJFrame normal = new NormalJFrame(this);
	public static Color redColor = new Color(198, 47, 47);// 酒红色经典
	public static Color playStateColor = new Color(246, 246, 248);// 播放状态栏颜色
	public static Color whiteColor = new Color(250, 250, 250);// 白色
	public static int BUTTONWIDTH = 50;
	public static int BUTTONHIGTH = 50;
	int count = 1;// 卡片当前数量
	int location = 1;// 卡片当前位置
	int timeCount;
	final int CARDNUM = 200;// 设置卡片可生成总数量
	String searchText = null;
	JScrollPane bodyScrollPane;
	private JTextField searchField;
	JButton backButton;// 上一个页面按钮
	JButton nextButton;// 下一个页面按钮
	JButton searchButton;// 搜索按钮
	JButton onlineMusicButton;// 在线音乐按钮
	JButton localMusicButton;// 本地音乐按钮
	JButton downloadButton;// 下载音乐按钮
	JButton myLoveButton;// 我喜欢的音乐按钮

	List<JPanel> panelList = new ArrayList<JPanel>();
	JPanel bodyPanel;// 主面板
	JPanel myMusicPanel; // 本地音乐面板
	JPanel downloadPanel; // 本地音乐面板
	JPanel secondPanel;
	JPanel thirdPanel;
	JPanel fourthPanel;
	JPanel songPanel;// 歌曲播放详情小面板
	MyMusicList musicList = new MyMusicList();// 本地音乐窗口对象,得到它的主面板
	DownloadPanel download = new DownloadPanel();// 下载面板对象 ,得到它的主面板
	public SliderPanel slider = new SliderPanel();// 进度条面板对象
	public SearchPanel search = new SearchPanel();// 搜索面板对象
	MusicList musicPlayList = new MusicList();
	public JPanel leftPanel;// 左面板,展开歌曲歌词页面时需要隐藏起来
	public JPanel searchPanel = search.searchPanel;// 搜索面板,可以搜索歌曲歌手歌单等
	public JPanel sliderPanel = slider.playerControlPane;// 播放面板,带有时间以及音量进度条
	ProduceCardPanelActionListener cardAction = new ProduceCardPanelActionListener();// 控制卡片事件
	CardLayout card;// 卡片布局对象
	String[] cardNames;// 卡片名字数组
	public static MusicDemo frame = new MusicDemo();
	ReadJSON readJson = new ReadJSON();
	// 这一句是自定义鼠标图标;
	public static Cursor mouseHand = Toolkit.getDefaultToolkit()
			.createCustomCursor(new ImageIcon("other/mouseHand.png").getImage(), new Point(10, 20), "stick");
	CurSorMouseListener cursorListener = new CurSorMouseListener();// 鼠标监听器

	List<List<String>> searchMusicData = new ArrayList<List<String>>();// 列表模型

	JButton songPhotoButton;// 小面板歌曲封面图
	JLabel songTitleLabel;// 显示小面板歌曲的标题
	JLabel songSingerLabel;// 显示小面板歌曲的歌手
	JLabel searchLoadingLabel;//搜索加载中Gif标签

	JsonPost post = new JsonPost();
	ReadJSON test = new ReadJSON();
	DownloadFile downloadFile = new DownloadFile();// 下载文件对象

	ImageIcon icon = new ImageIcon("file/tempImage.jpg");// 歌曲缓存到的图片
	Image img;

	ImageIcon netErrIcon = new ImageIcon("icon/tip/noNet.png");// 歌曲缓存到的图片
	Image netErrImg;
	JLabel netErrLabel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) throws Exception {
		EventQueue.invokeLater(new Runnable() {
			public void run() {

				frame.setVisible(true);
			}
		});
//		frame.setVisible(true);
	}

	/**
	 * Create the frame.
	 */
	public MusicDemo() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(MusicDemo.class.getResource("/other/WangYiYun.png")));

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("云音乐");
		setUndecorated(true);
		AWTUtilities.setWindowOpaque(this, false);

		if (SystemTray.isSupported()) {// 判断系统是否托盘
			// 创建一个托盘图标对象
			TrayIcon trayIcon = new TrayIcon(
					Toolkit.getDefaultToolkit().getImage(Clock.class.getResource("/other/trayIcon.png")), "云音乐");

			// 创建弹出菜单
			PopupMenu menu = new PopupMenu();
			menu.setFont(new Font("微软雅黑", Font.PLAIN, 20));
			// 添加一个用于退出的按钮
			MenuItem item = new MenuItem("退出");
			item.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					System.exit(0);
				}
			});
			menu.add(item);
			// 添加弹出菜单到托盘图标
			trayIcon.setPopupMenu(menu);
			SystemTray tray = SystemTray.getSystemTray();// 获取系统托盘
			try {
				tray.add(trayIcon); // 将托盘图表添加到系统托盘
			} catch (AWTException e1) {
				
			}
		}

		cardNames = new String[CARDNUM];
		for (int i = 0; i < cardNames.length; i++) {
			cardNames[i] = Integer.toString(i);// 给每个卡片赋予名字
		}

		setBounds(150, 50, screensize.width - 300, screensize.height - 63);// 1310, 771
		contentPane = new JPanel();
		contentPane.setBorder(null);
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		// 对窗口面板边沿进行阴影模糊处理
		contentPane.setBorder(BorderFactory.createCompoundBorder(ShadowBorder.newBuilder().shadowSize(3).top().build(),
				BorderFactory.createLineBorder(Color.WHITE)));// center()

		JPanel topPanel = new JPanel();
		topPanel.setBackground(MySkin.redColor);
		topPanel.setPreferredSize(new Dimension(contentPane.getWidth(), 75));
		contentPane.add(topPanel, BorderLayout.NORTH);

		topPanel.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evt) {
				if (evt.getClickCount() == 3) {
					// 处理鼠标三击
				} else if (evt.getClickCount() == 2) {
					// 处理鼠标双击
					// 这里暂时没有解决双击无法还原的问题
					if (frame.getExtendedState() == JFrame.NORMAL) {
						setExtendedState(JFrame.MAXIMIZED_BOTH);// 窗口最大化
						contentPane.setBorder(null);
						maxButton.setIcon(new ImageIcon(MusicDemo.class.getResource("/click/max.png")));
					}
				}
			}
		});

		/**
		 * 窗体鼠标移动窗口事件
		 */
		topPanel.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) { // 鼠标按下事件
				pressedPoint = e.getPoint(); // 记录鼠标坐标
			}
		});
		topPanel.addMouseMotionListener(new MouseAdapter() {
			public void mouseDragged(MouseEvent e) { // 鼠标拖拽事件
				Point point = e.getPoint();// 获取当前坐标
				Point locationPoint = getLocation();// 获取窗体坐标
				int x = locationPoint.x + point.x - pressedPoint.x;// 计算移动后的新坐标
				int y = locationPoint.y + point.y - pressedPoint.y;
				setLocation(x, y);// 改变窗体位置
			}
		});
		topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));

		JPanel panel = new JPanel();
		panel.setAlignmentX(Component.LEFT_ALIGNMENT);
		panel.setBackground(new Color(198, 47, 47));

		searchField = new JTextField();
		searchField.setBounds(385, 23, 321, 30);
		searchField.setForeground(Color.WHITE);
		searchField.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		searchField.setColumns(10);
		searchField.setBackground(redColor);// new Color(168, 40, 40)
		searchField.setSelectionColor(new Color(32,124,236));//选中字体时背景颜色
		searchField.setSelectedTextColor(Color.WHITE);//选中字体时字体颜色
		searchField.setBorder(borderTool);
		searchField.addKeyListener(new KeyAdapter() {

			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					if (!searchField.getText().equals("")) {
						if (searchText == null || !searchText.equals(searchField.getText())) {
							searchButton.doClick();
						}
					}
				}

			}
		});

		searchButton = new JButton("");
		searchButton.setBounds(706, 23, 40, 30);
		searchButton.setIcon(new ImageIcon(MusicDemo.class.getResource("/begin/search.png")));
		searchButton.setForeground(Color.WHITE);
		searchButton.setFont(new Font("微软雅黑", Font.PLAIN, 25));
		searchButton.setFocusPainted(false);
		searchButton.setBorderPainted(false);
		searchButton.setBackground(new Color(198, 47, 47));
		searchButton.setAlignmentX(0.5f);
		searchButton.setContentAreaFilled(false);// 取消按钮按压时的背景框
		searchButton.setCursor(new Cursor(Cursor.HAND_CURSOR));// 为按钮添加小手鼠标效果
		searchButton.addActionListener(cardAction);

		panel.setLayout(null);
		panel.add(searchField);
		panel.add(searchButton);
		topPanel.add(panel);

		backButton = new JButton("");
		backButton.setIcon(new ImageIcon(MusicDemo.class.getResource("/begin/back.png")));
		backButton.setForeground(Color.WHITE);
		backButton.setFont(new Font("微软雅黑", Font.PLAIN, 25));
		backButton.setFocusPainted(false);
		backButton.setBorderPainted(false);
		backButton.setBackground(new Color(198, 47, 47));// 198,47,47
		backButton.setBounds(290, 23, 40, 30);
		backButton.setEnabled(false);
		backButton.setContentAreaFilled(false);// 取消按钮按压时的背景框
		backButton.setCursor(new Cursor(Cursor.HAND_CURSOR));// 为按钮添加小手鼠标效果
		backButton.addActionListener(cardAction);
		panel.add(backButton);

		nextButton = new JButton("");
		nextButton.setIcon(new ImageIcon(MusicDemo.class.getResource("/begin/next.png")));
		nextButton.setForeground(Color.WHITE);
		nextButton.setFont(new Font("微软雅黑", Font.PLAIN, 25));
		nextButton.setFocusPainted(false);
		nextButton.setBorderPainted(false);
		nextButton.setBackground(new Color(198, 47, 47));
		nextButton.setBounds(330, 23, 40, 30);
		nextButton.setEnabled(false);
		nextButton.setContentAreaFilled(false);// 取消按钮按压时的背景框
		nextButton.setCursor(new Cursor(Cursor.HAND_CURSOR));// 为按钮添加小手鼠标效果
		nextButton.addActionListener(cardAction);
		panel.add(nextButton);

		JLabel titleLabel = new JLabel(MUSICTITLE);
		titleLabel.setIcon(new ImageIcon(MusicDemo.class.getResource("/other/ICON.png")));

		titleLabel.setForeground(Color.WHITE);
		titleLabel.setFont(new Font("微软雅黑", Font.PLAIN, 30));
		titleLabel.setBounds(47, 12, 160, 51);
		panel.add(titleLabel);
		
		searchLoadingLabel = new JLabel("");
		searchLoadingLabel.setIcon(new ImageIcon(MusicDemo.class.getResource("/tip/loading.gif")));
		searchLoadingLabel.setBounds(757, 12, 54, 51);
		searchLoadingLabel.setVisible(false);
		panel.add(searchLoadingLabel);

		JPanel panel_1 = new JPanel();
		topPanel.add(panel_1);
		panel_1.setBackground(new Color(198, 47, 47));

		JButton closebutton = new JButton("");
		closebutton.setToolTipText("");
		closebutton.setIcon(new ImageIcon(MusicDemo.class.getResource("/begin/close.png")));
		closebutton.setForeground(Color.WHITE);
		closebutton.setFont(new Font("微软雅黑", Font.PLAIN, 25));
		closebutton.setFocusPainted(false);
		closebutton.setBorderPainted(false);
		closebutton.setBackground(new Color(198, 47, 47));
		closebutton.setContentAreaFilled(false);// 取消按钮按压时的背景框
		closebutton.setCursor(new Cursor(Cursor.HAND_CURSOR));// 为按钮添加小手鼠标效果
		closebutton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO 自动生成的方法存根
				System.exit(0);
			}
		});

		maxButton = new JButton("");
		maxButton.setIcon(new ImageIcon(MusicDemo.class.getResource("/begin/max.png")));
		maxButton.setForeground(Color.WHITE);
		maxButton.setFont(new Font("微软雅黑", Font.PLAIN, 25));
		maxButton.setFocusPainted(false);
		maxButton.setBorderPainted(false);
		maxButton.setBackground(new Color(198, 47, 47));
		maxButton.setContentAreaFilled(false);// 取消按钮按压时的背景框
		maxButton.setCursor(new Cursor(Cursor.HAND_CURSOR));// 为按钮添加小手鼠标效果
		maxButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (frame.getExtendedState() == Frame.MAXIMIZED_BOTH) {
					maxButton.setIcon(new ImageIcon(MusicDemo.class.getResource("/begin/max.png")));
					frame.setExtendedState(Frame.NORMAL);
					contentPane.setBorder(
							BorderFactory.createCompoundBorder(ShadowBorder.newBuilder().shadowSize(5).top().build(),
									BorderFactory.createLineBorder(Color.WHITE)));// 对窗口面板边沿进行阴影模糊处理
				} else {
					maxButton.setIcon(new ImageIcon(MusicDemo.class.getResource("/click/max.png")));
					frame.setExtendedState(Frame.MAXIMIZED_BOTH);
					contentPane.setBorder(null);// 最大化后取消对窗口面板边沿进行去除阴影模糊处理
				}
			}
		});

		JButton minButton = new JButton("");
		minButton.setIcon(new ImageIcon(MusicDemo.class.getResource("/begin/min.png")));
		minButton.setForeground(Color.WHITE);
		minButton.setFont(new Font("微软雅黑", Font.PLAIN, 25));
		minButton.setFocusPainted(false);
		minButton.setBorderPainted(false);
		minButton.setBackground(new Color(198, 47, 47));
		minButton.setContentAreaFilled(false);// 取消按钮按压时的背景框
		minButton.setCursor(new Cursor(Cursor.HAND_CURSOR));// 为按钮添加小手鼠标效果
		minButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				setExtendedState(JFrame.ICONIFIED);
				// 对窗口面板边沿进行阴影模糊处理
				contentPane.setBorder(
						BorderFactory.createCompoundBorder(ShadowBorder.newBuilder().shadowSize(5).top().build(),
								BorderFactory.createLineBorder(Color.WHITE)));// center()
			}
		});
		panel_1.setLayout(new BoxLayout(panel_1, BoxLayout.X_AXIS));

		JButton skinButton = new JButton("");
		skinButton.setIcon(new ImageIcon(MusicDemo.class.getResource("/begin/skin.png")));
		skinButton.setSelectedIcon(new ImageIcon(MusicDemo.class.getResource("/click/skin.png")));
		panel_1.add(skinButton);

		skinButton.setForeground(Color.WHITE);
		skinButton.setFont(new Font("微软雅黑", Font.PLAIN, 25));
		skinButton.setFocusPainted(false);
		skinButton.setBorderPainted(false);
		skinButton.setBackground(new Color(198, 47, 47));
		skinButton.setContentAreaFilled(false);// 取消按钮按压时的背景框
		skinButton.setCursor(new Cursor(Cursor.HAND_CURSOR));// 为按钮添加小手鼠标效果

		panel_1.add(skinButton);

		JButton setButton = new JButton("");
		setButton.setFocusPainted(false);
		setButton.setBorderPainted(false);
		setButton.setBackground(new Color(198, 47, 47));
		setButton.setContentAreaFilled(false);// 取消按钮按压时的背景框
		setButton.setCursor(new Cursor(Cursor.HAND_CURSOR));// 为按钮添加小手鼠标效果
		setButton.setIcon(new ImageIcon(MusicDemo.class.getResource("/begin/set.png")));
		panel_1.add(setButton);
		panel_1.add(minButton);
		panel_1.add(maxButton);
		panel_1.add(closebutton);
		ImageIcon ii = new ImageIcon("icon/begin/skin.png");

		leftPanel = new JPanel();
		leftPanel.setBackground(Color.LIGHT_GRAY);
		contentPane.add(leftPanel, BorderLayout.WEST);
		leftPanel.setPreferredSize(new Dimension(300, contentPane.getHeight() - 75));
		leftPanel.setLayout(new BorderLayout(0, 0));
		// leftPanel.setVisible(false);

		songPanel = new JPanel();
		songPanel.setPreferredSize(new Dimension(300, 85));
		songPanel.setBorder(new EmptyBorder(0, 0, 2, 0));
		songPanel.setVisible(false);
		leftPanel.add(songPanel, BorderLayout.SOUTH);
		songPanel.setLayout(new BorderLayout(0, 0));

		JPanel songPhotoPanel = new JPanel();
		songPhotoPanel.setBackground(new Color(245, 245, 247));
		songPanel.add(songPhotoPanel, BorderLayout.CENTER);
		songPhotoPanel.setLayout(null);

		songPhotoButton = new JButton("");
		songPhotoButton.setFocusPainted(false);
		songPhotoButton.setBorderPainted(false);
		songPhotoButton.setContentAreaFilled(false);// 取消按钮按压时的背景框
		songPhotoButton.setCursor(new Cursor(Cursor.HAND_CURSOR));// 为按钮添加小手鼠标效果
		songPhotoButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		songPhotoButton.setBackground(Color.WHITE);
		songPhotoButton.setIcon(new ImageIcon(MusicDemo.class.getResource("/other/songPhoto.png")));
		songPhotoButton.setBounds(10, 10, 68, 68);
		songPhotoPanel.add(songPhotoButton);

		songTitleLabel = new JLabel("\u7EFF\u8272");
		songTitleLabel.setFont(new Font("微软雅黑", Font.PLAIN, 18));
		songTitleLabel.setBounds(90, 15, 194, 25);
		songPhotoPanel.add(songTitleLabel);

		songSingerLabel = new JLabel("\u9648\u96EA\u51DD");
		songSingerLabel.setFont(new Font("微软雅黑", Font.PLAIN, 18));
		songSingerLabel.setBounds(90, 50, 194, 25);
		songPhotoPanel.add(songSingerLabel);

		JPanel doLovePanel = new JPanel();
		doLovePanel.setBackground(new Color(245, 245, 247));
		songPanel.add(doLovePanel, BorderLayout.EAST);

		JScrollPane leftScrollPane = new JScrollPane();// 左面板滚动面板
		leftScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		leftScrollPane.getVerticalScrollBar().setUI(new DemoScrollBarUI());
		leftScrollPane.setBorder(new EmptyBorder(0, 0, 0, 2));
		// 下面三句话设置滚动条的灵敏度
		JScrollBar Bar1 = null;
		Bar1 = leftScrollPane.getVerticalScrollBar();
		Bar1.setUnitIncrement(40);
		leftPanel.add(leftScrollPane, BorderLayout.CENTER);

		JPanel optionPanel = new JPanel();
		optionPanel.setBackground(whiteColor);
		leftScrollPane.setViewportView(optionPanel);
		optionPanel.setLayout(null);

		JLabel onlineMusicabel = new JLabel("  \u5728\u7EBF\u97F3\u4E50");
		onlineMusicabel.setBounds(0, 0, 300, 50);
		onlineMusicabel.setPreferredSize(new Dimension(300, 50));
		onlineMusicabel.setFont(new Font("微软雅黑", Font.PLAIN, 21));
		onlineMusicabel.setForeground(new Color(92, 92, 92));
		optionPanel.add(onlineMusicabel);

		onlineMusicButton = new JButton("\u53D1\u73B0\u97F3\u4E50");
		onlineMusicButton.setIcon(new ImageIcon(MusicDemo.class.getResource("/begin/onlineMusic.png")));
		onlineMusicButton.setBounds(0, 50, 300, 50);
		onlineMusicButton.setHorizontalAlignment(JButton.LEFT);// 按钮居左对齐
		onlineMusicButton.setFocusPainted(false);
		onlineMusicButton.setBorderPainted(false);
		onlineMusicButton.setBackground(whiteColor);
		onlineMusicButton.setForeground(new Color(92, 92, 92));
		onlineMusicButton.setFont(new Font("微软雅黑", Font.PLAIN, 22));
		onlineMusicButton.setPreferredSize(new Dimension(300, 50));
		onlineMusicButton.setContentAreaFilled(false);// 取消按钮按压时的背景框
		onlineMusicButton.setCursor(new Cursor(Cursor.HAND_CURSOR));// 为按钮添加小手鼠标效果
		onlineMusicButton.addMouseListener(cursorListener);
		onlineMusicButton.addActionListener(cardAction);
		optionPanel.add(onlineMusicButton);

		JLabel localLabel = new JLabel("  \u6211\u7684\u97F3\u4E50");
		localLabel.setPreferredSize(new Dimension(300, 50));
		localLabel.setFont(new Font("微软雅黑", Font.PLAIN, 21));
		localLabel.setBounds(0, 100, 300, 50);
		localLabel.setForeground(new Color(92, 92, 92));
		optionPanel.add(localLabel);

		localMusicButton = new JButton("\u672C\u5730\u97F3\u4E50");
		localMusicButton.setIcon(new ImageIcon(MusicDemo.class.getResource("/begin/localMusic.png")));
		localMusicButton.setPreferredSize(new Dimension(300, 50));
		localMusicButton.setHorizontalAlignment(SwingConstants.LEFT);
		localMusicButton.setFont(new Font("微软雅黑", Font.PLAIN, 22));
		localMusicButton.setFocusPainted(false);
		localMusicButton.setBorderPainted(false);
		localMusicButton.setBackground(new Color(250, 250, 250));
		localMusicButton.setForeground(new Color(92, 92, 92));
		localMusicButton.setBounds(0, 150, 300, 50);
		localMusicButton.setContentAreaFilled(false);// 取消按钮按压时的背景框
		localMusicButton.setCursor(new Cursor(Cursor.HAND_CURSOR));// 为按钮添加小手鼠标效果
		localMusicButton.addMouseListener(cursorListener);
		localMusicButton.addActionListener(cardAction);
		optionPanel.add(localMusicButton);

		downloadButton = new JButton("\u4E0B\u8F7D\u7BA1\u7406");
		downloadButton.setIcon(new ImageIcon(MusicDemo.class.getResource("/begin/download.png")));
		downloadButton.setPreferredSize(new Dimension(300, 50));
		downloadButton.setHorizontalAlignment(SwingConstants.LEFT);
		downloadButton.setFont(new Font("微软雅黑", Font.PLAIN, 22));
		downloadButton.setFocusPainted(false);
		downloadButton.setBorderPainted(false);
		downloadButton.setBackground(new Color(250, 250, 250));
		downloadButton.setForeground(new Color(92, 92, 92));
		downloadButton.setBounds(0, 200, 300, 50);
		downloadButton.setContentAreaFilled(false);// 取消按钮按压时的背景框
		downloadButton.setCursor(new Cursor(Cursor.HAND_CURSOR));// 为按钮添加小手鼠标效果
		downloadButton.addMouseListener(cursorListener);
		downloadButton.addActionListener(cardAction);
		optionPanel.add(downloadButton);

		JLabel myCollectLabel = new JLabel("  \u6211\u7684\u6B4C\u5355");
		myCollectLabel.setPreferredSize(new Dimension(300, 50));
		myCollectLabel.setFont(new Font("微软雅黑", Font.PLAIN, 21));
		myCollectLabel.setBounds(0, 250, 300, 50);
		myCollectLabel.setForeground(new Color(92, 92, 92));
		optionPanel.add(myCollectLabel);

		myLoveButton = new JButton("\u6211\u559C\u6B22\u7684\u97F3\u4E50");
		myLoveButton.setIcon(new ImageIcon(MusicDemo.class.getResource("/begin/myLoveMusic.png")));
		myLoveButton.setPreferredSize(new Dimension(300, 50));
		myLoveButton.setHorizontalAlignment(SwingConstants.LEFT);
		myLoveButton.setFont(new Font("微软雅黑", Font.PLAIN, 22));
		myLoveButton.setFocusPainted(false);
		myLoveButton.setBorderPainted(false);
		myLoveButton.setBackground(new Color(250, 250, 250));
		myLoveButton.setForeground(new Color(92, 92, 92));
		myLoveButton.setBounds(0, 300, 300, 50);
		myLoveButton.setContentAreaFilled(false);// 取消按钮按压时的背景框
		myLoveButton.setCursor(new Cursor(Cursor.HAND_CURSOR));// 为按钮添加小手鼠标效果
		myLoveButton.addMouseListener(cursorListener);

		myLoveButton.addActionListener(cardAction);
		optionPanel.add(myLoveButton);

		JPanel bottomPanel = new JPanel();// 底部面板
		bottomPanel.setBorder(new EmptyBorder(2, 0, 0, 0));
		bottomPanel.setPreferredSize(new Dimension(300, 70));
		bottomPanel.setBackground(playStateColor);
		contentPane.add(bottomPanel, BorderLayout.SOUTH);
		bottomPanel.setLayout(new BorderLayout(0, 0));

		sliderPanel = slider.playerControlPane;
		bottomPanel.add(sliderPanel, BorderLayout.CENTER);

		netErrLabel = new JLabel();
		netErrLabel.setBorder(null);
		netErrLabel.setIcon(netErrIcon);// 没有网络的错误提示标签

		bodyScrollPane = new JScrollPane();// 主面板的滚动面板
		bodyScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		bodyScrollPane.getVerticalScrollBar().setUI(new DemoScrollBarUI());
		bodyScrollPane.setBorder(null);
		// 下面三句话设置滚动条的灵敏度
		JScrollBar Bar2 = null;
		Bar2 = bodyScrollPane.getVerticalScrollBar();
		Bar2.setUnitIncrement(40);
		contentPane.add(bodyScrollPane, BorderLayout.CENTER);

		// data = readfile.getMyMusicListData(new File("file//myMusicList.txt"));
		
		data = new ArrayList<List<String>>();
		data = readJson.getLocalMusicList();//歌曲信息集合
		urlData = new ArrayList<String>();// 
		urlData =  readJson.getLocalUrlList();//url集合
		// urlData =
		// readfile.getMyMusicUrlData(readfile.getMyMusicUrlFile());//初始化时将本地音乐的URL设置为默认URL集合
		

		card = new CardLayout();
		bodyPanel = new JPanel();
		bodyPanel.setLayout(card);
		// 播放列表双击播放事件
		musicList.table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					// 实现双击
					int row = ((JTable) e.getSource()).rowAtPoint(e.getPoint()); // 获得行位置
					slider.setNumber(row);
					// 将自适应调整后的图片设置到icon，再将Icon设置到歌曲图片按钮中

					MusicDemo.this.play();
					// musicPlayList.newPlayList(MusicList.LOCALMUSIC, row);

				} else
					return;
			}
		});

		// 暂停播放事件
		slider.pouseButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (slider.paused == true) {// 如果处于暂停状态
					slider.pouseButton.setIcon(new ImageIcon(SliderPanel.class.getResource("/click/player.png")));
					slider.timer.cancel();
					slider.getTimeSlider().setValue(0);
					timeCount = 1;
					slider.nowTimeLabel.setText("00:00");

					songTitleLabel.setText(data.get(slider.getNumber()).get(1));// 小面板更新歌曲信息
					songSingerLabel.setText(data.get(slider.getNumber()).get(2));
					songPanel.setVisible(true);

					musicList.table.setRowSelectionInterval(slider.getNumber(), slider.getNumber());

					slider.setTime(slider.timeToInt(data.get(slider.getNumber()).get(4)));
					slider.setMusicPath(urlData.get(slider.getNumber()));
					slider.musicBase.stop();
					slider.musicBase.playFile(slider.getMusicPath());
					slider.setTimeLenth(slider.getTime());
					slider.setEndTimelabel(slider.getTime());
					slider.getTimeSlider().setMaximum(slider.getTime());
					// slider.musicBase = new slider.musicBase();
					// slider.musicBase.playFile(slider.musicPath);
					slider.timer = new Timer();
					slider.task = new TimerTask() {
						@Override
						public void run() {
							slider.nowTimeLabel.setText(slider.getFormattime(timeCount));
							slider.getTimeSlider().setValue(timeCount);
							timeCount++;
							if (timeCount == slider.timeLenth) {// ||!paused

								slider.musicBase.stop();
								slider.timer.cancel();//
								slider.pouseButton
										.setIcon(new ImageIcon(SliderPanel.class.getResource("/begin/player.png")));
								slider.getTimeSlider().setValue(0);
								timeCount = 1;
								slider.nowTimeLabel.setText("00:00");// getFormattime(timeCount)
								slider.nextSongButton.doClick();
							}
						}
					};

					slider.timer.schedule(slider.task, 1000L, 1000L);

				} else {
					slider.pouseButton.setIcon(new ImageIcon(SliderPanel.class.getResource("/begin/player.png")));
					slider.musicBase.stop();
					slider.timer.cancel();
					slider.getTimeSlider().setValue(0);
					timeCount = 1;
					slider.nowTimeLabel.setText("00:00");// getFormattime(timeCount)
				}
				slider.paused = !slider.paused;
			}
		});
		// 上一首事件
		slider.lastSongButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				if (slider.getNumber() == 0) {
					slider.setNumber(urlData.size() - 1);
				} else {
					slider.setNumber(slider.getNumber() - 1);
				}

				MusicDemo.this.play();

			}
		});

		// 下一首事件
		slider.nextSongButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				if (slider.getNumber() == urlData.size() - 1) {
					slider.setNumber(0);
				} else {
					slider.setNumber(slider.getNumber() + 1);
				}

				MusicDemo.this.play();

			}
		});
		// 播放所有事件
		musicList.playAllButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				MusicDemo.this.play();
			}
		});

		myMusicPanel = musicList.myMusicListPane;
		bodyPanel.add(cardNames[count], myMusicPanel);
		card.show(bodyPanel, cardNames[count]);
		bodyScrollPane.setViewportView(bodyPanel);

	}

	/**
	 * 播放方法
	 */
	public void play() {

		slider.timer.cancel();
		slider.getTimeSlider().setValue(0);
		timeCount = 1;
		slider.nowTimeLabel.setText("00:00");

		musicList.table.setRowSelectionInterval(slider.getNumber(), slider.getNumber());// 选中当前播放歌曲
		slider.pouseButton.setIcon(new ImageIcon(SliderPanel.class.getResource("/click/player.png")));// 播放按钮显示为正在播放图标
		slider.setTime(slider.timeToInt(data.get(slider.getNumber()).get(4)));
		// 下面三句小面板更新歌曲信息
		songTitleLabel.setText(data.get(slider.getNumber()).get(1));
		songSingerLabel.setText(data.get(slider.getNumber()).get(2));
		songPanel.setVisible(true);
		
		setTitle(data.get(slider.getNumber()).get(1)+" - "+data.get(slider.getNumber()).get(2));

		slider.setMusicPath(urlData.get(slider.getNumber()));// 传递播放地址
		slider.musicBase.stop();// 关闭播放器
		slider.musicBase.playFile(slider.getMusicPath());// 从传递得到的地址开始播放
		slider.setTimeLenth(slider.getTime());
		slider.getTimeSlider().setMaximum(slider.getTime());// 设置进度条的最大长度
		slider.setEndTimelabel(slider.getTime());// 传递结束标签
		slider.timer = new Timer();
		slider.task = new TimerTask() {
			@Override
			public void run() {
				slider.nowTimeLabel.setText(slider.getFormattime(timeCount));//
				slider.getTimeSlider().setValue(timeCount);
				timeCount++;
				if (timeCount == slider.timeLenth) {// ||!paused

					slider.timer.cancel();//
					slider.pouseButton.setIcon(new ImageIcon(SliderPanel.class.getResource("/begin/player.png")));
					slider.getTimeSlider().setValue(0);
					timeCount = 1;
					slider.nowTimeLabel.setText("00:00");// getFormattime(timeCount)
					slider.musicBase.stop();
					slider.nextSongButton.doClick();// 自动下一曲
				}
			}
		};

		slider.timer.schedule(slider.task, 1000L, 1000L);
		slider.paused = !slider.paused;// 暂停控制反转

	}

	/**
	 * 内部类,监听按钮事件
	 * 
	 * @author Jack
	 *
	 */
	class ProduceCardPanelActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

			backButton.setEnabled(true);
			nextButton.setEnabled(true);

			if (e.getSource() == onlineMusicButton) {
				secondPanel = new JPanel();
				secondPanel.setBackground(Color.RED);
				bodyPanel.add(cardNames[count], secondPanel);
				// card.next(cardPanel);//这里改用显示下一个页面,解决了第一个页面不显示问题
				card.show(bodyPanel, cardNames[count]);
				count++;
				location++;
				nextButton.setEnabled(false);
				System.out.println("一共有:" + count + "张卡片");
				System.out.println("当前在第" + location + "张");
			}
			if (e.getSource() == localMusicButton) {
				myMusicPanel = musicList.myMusicListPane;
				//myMusicPanel.setBackground(Color.YELLOW);
				bodyPanel.add(cardNames[count], myMusicPanel);
				card.show(bodyPanel, cardNames[count]);
				count++;
				location++;
				nextButton.setEnabled(false);
				System.out.println("一共有:" + count + "张卡片");
				System.out.println("当前在第" + location + "张");
			}
			if (e.getSource() == downloadButton) {
				downloadPanel = download.downloadPane;
				thirdPanel = new JPanel();
				bodyPanel.add(cardNames[count], downloadPanel);
				card.show(bodyPanel, cardNames[count]);
				count++;
				location++;
				nextButton.setEnabled(false);
				System.out.println("一共有:" + count + "张卡片");
				System.out.println("当前在第" + location + "张");
			}
			if (e.getSource() == myLoveButton) {
				fourthPanel = new JPanel();
				fourthPanel.setBackground(Color.GREEN);
				bodyPanel.add(cardNames[count], fourthPanel);
				card.show(bodyPanel, cardNames[count]);
				count++;
				location++;
				nextButton.setEnabled(false);
				System.out.println("一共有:" + count + "张卡片");
				System.out.println("当前在第" + location + "张");
			}
			if (e.getSource() == searchButton) {// 搜索按钮事件,生成搜索页面
				if (!searchField.getText().equals("")) {
					if (searchText == null || !searchText.equals(searchField.getText())) {

						new Thread(new Runnable() {// 开启搜索线程

							@Override
							public void run() {
								
								searchLoadingLabel.setVisible(true);

								searchText = searchField.getText();
								post.inputString = searchText;
								try {
									post.search();
								} catch (IOException e2) {
									searchPanel.add(netErrLabel, BorderLayout.CENTER);
									System.err.println("啦啦啦,,网络断开");
									// e2.printStackTrace();
								} // 搜索并生成JOSN
								try {
									test.search();// 解析JOSN并生成歌曲信息集合
								} catch (Exception e2) {

									// e2.printStackTrace();
								}
								/**
								 * EDT处理界面事件
								 */
//								SwingUtilities.invokeLater(new Runnable() {
//									@Override
//									public void run() {
//										search.searchTextLabel.setText("\"" + searchText + "\"");// 显示搜索内容
//										search.resultLabel.setText("找到" + test.count + "首单曲");// 显示检索到歌曲数目
//									}
//								});
								SwingUtilities.invokeLater(() -> {
									search.searchTextLabel.setText("\"" + searchText + "\"");// 显示搜索内容
									search.resultLabel.setText("找到" + test.count + "首单曲");// 显示检索到歌曲数目
								});

								searchMusicData = new ArrayList<List<String>>();

								searchMusicData = test.allList;// 生成的信息集合传递给搜索面板对象
								for (int i = 0; i < searchMusicData.size(); i++) {
									for (int j = 0; j < searchMusicData.get(i).size(); j++) {
										System.out.println(i + " :  " + searchMusicData.get(i).get(j));
									}
								}
								search.dataModel = new SearchMusicTableModel();
								search.dataModel.data = new Vector(1, 1);
								if (searchMusicData != null) {
									for (int i = 0; i < searchMusicData.size(); i++) {
										for (int j = 0; j < 6; j++) {
											if (j == 0) {
												search.dataModel.data.add("   " + searchMusicData.get(i).get(j));// "手动"居中
											} else {
												search.dataModel.data.add(searchMusicData.get(i).get(j - 1));
											}
										}
									}
								} else {
									// 提示无歌曲
									System.out.println("当前歌曲为空");
								}

								search.dataModel.titles = new Vector(1, 1);
								for (int i = 0; i < 6; i++) {
									search.dataModel.titles.add(i);
								}

								search.table = new JTable(search.dataModel) {
									@Override
									public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
										Component comp = super.prepareRenderer(renderer, row, column);
										Point p = getMousePosition();
										if (p != null) {
											int rowUnderMouse = rowAtPoint(p);
											int columnUnderMouse = columnAtPoint(p);
											if (rowUnderMouse == row) {
												comp.setBackground(new Color(236, 237, 238));// 鼠标悬停颜色
											} else {
												// comp.setBackGround(DefaultLookup.getColor(this,ui,"Table.alternateRowColor"));
											}
											if (columnUnderMouse == 1) {
												setCursor(new Cursor(Cursor.HAND_CURSOR));
											} else {
												setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
											}
										}
										return comp;
									}
								};

								search.table.setModel(search.dataModel);

								search.table.addMouseListener(new MouseAdapter() {

									public void mouseClicked(MouseEvent e) {
										if (e.getClickCount() == 2) {
											// 在线搜索音乐表格双击事件
											int row = ((JTable) e.getSource()).rowAtPoint(e.getPoint()); // 获得行位置

											/**
											 * 主要双击控制线程
											 */
											new Thread(new Runnable() {

												int timeLength = 0;

												@Override
												public void run() {

													slider.timer.cancel();// 进度条清零
													slider.musicBase.stop();// 关闭音频

													// 小面板更新歌曲信息

													/**
													 * EDT处理界面事件
													 */
													SwingUtilities.invokeLater(() -> {
														songTitleLabel.setText(test.allList.get(row).get(1));// 小面板更新歌曲信息
														songSingerLabel.setText(test.allList.get(row).get(2));
														songPanel.setVisible(true);
														
														setTitle(test.allList.get(row).get(1)+" - "+test.allList.get(row).get(2));

														slider.pouseButton.setIcon(new ImageIcon(
																SliderPanel.class.getResource("/click/player.png")));
													});

													downloadFile.downloadFile(test.IdList.get(row), DownloadFile.MUSIC);// 缓存音乐
													slider.musicBase.playFile("file/tempMusic.mp3");// 播放在线的临时文件

													new Thread(new Runnable() {

														@Override
														public void run() {

															downloadFile.downloadFile(test.IdList.get(row),
																	DownloadFile.IMAGE);
															downloadFile.downloadFile(test.IdList.get(row),
																	DownloadFile.LYRIC);

															img = icon.getImage();
															img = img.getScaledInstance(68, 68, Image.SCALE_DEFAULT);
															// 将自适应调整后的图片设置到icon，再将Icon设置到歌曲图片按钮中
															icon.setImage(img);
															songPhotoButton.setIcon(icon);

															songPhotoButton.validate();
															songPhotoButton.repaint();// 更新音乐图片后一定要调用repaint()

															/**
															 * EDT处理界面事件
															 */
//													SwingUtilities.invokeLater(() -> {
//
//														
//
//													});

														}

													}).start();

													slider.getTimeSlider().setValue(0);
													timeCount = 1;
													slider.nowTimeLabel.setText("00:00");

													try {
														search.table.setRowSelectionInterval(row, row);
													} catch (Exception e) {

													}

													timeLength = SliderPanel.timeToInt(test.allList.get(row).get(4));

													slider.setTime(timeLength);// 设置歌曲长度
													slider.setTimeLenth(
															SliderPanel.timeToInt(test.allList.get(row).get(4)));
													slider.setEndTimelabel(timeLength);
													slider.getTimeSlider().setMaximum(timeLength);
													slider.timer = new Timer();
													slider.task = new TimerTask() {
														@Override
														public void run() {
															slider.nowTimeLabel
																	.setText(slider.getFormattime(timeCount));
															slider.getTimeSlider().setValue(timeCount);
															timeCount++;
															if (timeCount == timeLength) {// ||!paused

																slider.musicBase.stop();
																slider.timer.cancel();//
																slider.pouseButton
																		.setIcon(new ImageIcon(SliderPanel.class
																				.getResource("/begin/player.png")));
																slider.getTimeSlider().setValue(0);
																slider.getTimeSlider().repaint();
																timeCount = 1;
																slider.nowTimeLabel.setText("00:00");// getFormattime(timeCount)
																slider.setNumber(slider.getNumber() + 1);
																MusicDemo.this.play();// 自动接着播放列表播放
															}
														}
													};

													slider.timer.schedule(slider.task, 1000L, 1000L);

												}
											}).start();

										} else if (e.getClickCount() == 1) {
											System.out.println(555);
											int row = ((JTable) e.getSource()).rowAtPoint(e.getPoint()); // 获得行位置
											int column = ((JTable) e.getSource()).columnAtPoint(e.getPoint()); // 获得列位置
											if (column == 1) {
												
												 downloadFile.downloadFile(test.IdList.get(row), test.allList.get(row).get(1), test.allList.get(row).get(2));
												 System.out.println("下载好了!");
											}
										}else
											return;
									}
								});

								search.table.setDefaultRenderer(Object.class, search.dataModel);// 设置表格属性new
								search.table.setRowHeight(45);
								search.table.setShowVerticalLines(false);
								search.table.setShowHorizontalLines(false);
								search.table.setColumnModel(
										search.dataModel.getColumn(search.table, search.dataModel.columnWidth));
								search.table.setBackground(new Color(250, 250, 250));// 245, 245, 247
								search.table.setSelectionBackground(new Color(227, 227, 229));
								search.table.setFont(new Font("宋体", Font.PLAIN, 18));
								search.table.addMouseMotionListener(new MyTableMouseMotionListener());
								MyMusicTableModel.setColumnColor(search.table);// 设置间隔色

								try {
									SwingUtilities.invokeAndWait(() -> {
										new AddShowButton(search.table, 1);
										search.searchPanel.add(search.table, BorderLayout.CENTER);// 注意,这里一定要把表格添加到搜索对象的面板去

									});
								} catch (InvocationTargetException e1) {
									e1.printStackTrace();
								} catch (InterruptedException e1) {
									e1.printStackTrace();
								}

								try {// 测试网络
									post.search();
								} catch (IOException e2) {
									search.searchPanel.add(netErrLabel, BorderLayout.CENTER);
									//
								}

								searchPanel = search.searchPanel;
								searchPanel.repaint();
								search = new SearchPanel();
								bodyPanel.add(cardNames[count], searchPanel);//
								//panelList.get(panelList.size() - 1)
								searchLoadingLabel.setVisible(false);
								card.show(bodyPanel, cardNames[count]);
								count++;
								location++;
								nextButton.setEnabled(false);
								System.out.println("一共有:" + count + "张卡片");
								System.out.println("当前在第" + location + "张");

							}
						}).start();
					}
				}
			}
			if (e.getSource() == backButton) {
				card.previous(bodyPanel);
				location--;
				System.out.println("一共有:" + count + "张卡片");
				System.out.println("当前在第" + location + "张");
			}
			if (e.getSource() == nextButton) {
				card.next(bodyPanel);
				location++;
				System.out.println("一共有:" + count + "张卡片");
				System.out.println("当前在第" + location + "张");
			}
			if (location <= 1) {// 当前已经到第一页时
				backButton.setEnabled(false);
			}
			if (location >= count) {// 当前是最后一页时
				nextButton.setEnabled(false);
			}

		}

	}
}