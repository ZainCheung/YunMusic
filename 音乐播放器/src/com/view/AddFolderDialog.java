package com.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;

import com.event.ChooseFolderActionListener;
import com.sun.awt.AWTUtilities;
import com.tool.Mp3ToMessage;
import com.tool.ReadAndWriteFile;
import com.tool.ScanFolder;
import com.tool.ShadowBorder;
import com.tool.SystemUtils;
import com.ui.DemoScrollBarUI;
import com.model.WriteJSON;


public class AddFolderDialog extends JDialog {

	private JPanel contentPanel = new JPanel();
	Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();// 屏幕尺寸
	ScanFolder scanfolder = new ScanFolder();// 扫描文件夹内mp3的对象
	ReadAndWriteFile readAndWrite = new ReadAndWriteFile();// 文件处理器对象
	public WriteJSON writeJson = new WriteJSON();
	
	MyMusicList myMusicList = new MyMusicList();
	Mp3ToMessage mp3tomessage;
	public List<String> folderList = new ArrayList<String>();// 得到的文件夹列表
	public static String[] folderArray;// 由文件夹列表得到的数组,以便为列表添加数组模型
	List<String> urlList = new ArrayList<String>();// 音乐的URL文件
	List<List<String>> musicAllList;// 得到的音乐列表数据
	private Point pressedPoint;
	JScrollPane scrollPane;
	public static JList list;// 显示文件夹列表
	JButton okButton;
	private JPopupMenu menu = new JPopupMenu();
	static int row;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			AddFolderDialog dialog = new AddFolderDialog();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public AddFolderDialog() {

		int width = 0;
		int height = 0;
		width = (int) ((screensize.getWidth() - 500) / 2);
		height = (int) ((screensize.getHeight() - 580) / 2);
		setResizable(false);
		setBounds(width, height, 500, 580);// 400, 300, 500, 580
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setUndecorated(true);
		AWTUtilities.setWindowOpaque(this, false);

		JMenuItem delate, mCopy, mCut, mPaste, mDel;
		menu = new JPopupMenu();

		delate = new JMenuItem("删除选择");
		menu.add(delate);
//		  mCopy = new JMenuItem("复制(C)");
//		  menu.add(mCopy);
//		  mCut = new JMenuItem("剪切(T)");
//		  menu.add(mCut);
//		  mPaste = new JMenuItem("粘贴(P)");
//		  menu.add(mPaste);
//		  mDel = new JMenuItem("删除(D)");
//		  menu.add(mDel);

		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(null);
		contentPanel.setBackground(new Color(250, 250, 250));
		contentPanel.setBorder(BorderFactory.createCompoundBorder(ShadowBorder.newBuilder().shadowSize(5).top().build(),
				BorderFactory.createLineBorder(Color.WHITE)));// center()
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			JPanel topPanel = new JPanel() {
				public void paintComponent(Graphics g) {
					super.paintComponent(g);
					g.setColor(Color.gray);
					g.drawLine(0, 80, getContentPane().getWidth(), 80);
				}
			};
			topPanel.setPreferredSize(new Dimension(500, 130));
			topPanel.setBackground(new Color(250, 250, 250));
			contentPanel.add(topPanel, BorderLayout.NORTH);
			topPanel.setLayout(null);
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
			{
				JLabel lblNewLabel = new JLabel("\u9009\u62E9\u672C\u5730\u97F3\u4E50\u6587\u4EF6\u5939");
				lblNewLabel.setBounds(15, 21, 225, 34);
				lblNewLabel.setFont(new Font("微软雅黑", Font.PLAIN, 25));
				topPanel.add(lblNewLabel);
			}

			JButton exitButton = new JButton("X");
			exitButton.setFont(new Font("微软雅黑", Font.PLAIN, 25));
			exitButton.setForeground(Color.BLACK);
			exitButton.setBackground(Color.WHITE);
			exitButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					AllPanel.addFolderDialog.dispose();

				}
			});
			exitButton.setBounds(427, 21, 58, 29);
			topPanel.add(exitButton);

			JLabel lblNewLabel_1 = new JLabel(
					"\u5C06\u81EA\u52A8\u626B\u63CF\u60A8\u6DFB\u52A0\u7684\u6587\u4EF6\u5939,\u6587\u4EF6\u5220\u6539\u5B9E\u65F6\u540C\u6B65");
			lblNewLabel_1.setFont(new Font("楷体", Font.PLAIN, 22));
			lblNewLabel_1.setBounds(34, 103, 451, 32);
			topPanel.add(lblNewLabel_1);
		}

		{
			JPanel bodyPanel = new JPanel();
			bodyPanel.setPreferredSize(new Dimension(60, 300));
			bodyPanel.setBackground(new Color(250, 250, 250));

			scrollPane = new JScrollPane();
			scrollPane.setBorder(null);
			//scrollPane.setBounds(20, 130, 460, 100);
			scrollPane.getVerticalScrollBar().setUI(new DemoScrollBarUI());
			// 下面三句话设置滚动条的灵敏度
			JScrollBar Bar1 = null;
			Bar1 = scrollPane.getVerticalScrollBar();
			Bar1.setUnitIncrement(40);
			contentPanel.add(bodyPanel, BorderLayout.WEST);
			contentPanel.add(scrollPane, BorderLayout.CENTER);
			{
				list = new JList();
				list.setFont(new Font("黑体", Font.PLAIN, 25));
				list.setBackground(new Color(250, 250, 250));
				list.setSelectionBackground(new Color(227, 227, 229));
				list.add(menu);
				scrollPane.setViewportView(list);

//				list.addMouseListener(new MouseAdapter() {
//					
//
//					public void mouseClicked(MouseEvent e) {
//
//						
//						if (e.getButton() == MouseEvent.BUTTON3) {
//							// 弹出右键菜单
//							list.setSelectedIndex(list.locationToIndex(e.getPoint()));
//							//menu.show(list, e.getX() , e.getY()-20 );
//							 if (list.getSelectedIndex()!=-1) {//e.isPopupTrigger()&&
//						            
//						            //获取选择项的值
//						            Object selected = list.getModel().getElementAt(list.getSelectedIndex());
//						            System.out.println(selected);
//						            menu.show(e.getComponent(),e.getX(), e.getY()-20);
//						           }
//						}
//					}
//				});
//				delate.addActionListener(new ActionListener() {
//					public void actionPerformed(ActionEvent e) {
//						System.out.println("点击了" + row);
//					}
//				});

				JPanel bottomPanel = new JPanel();
				bottomPanel.setLayout(null);
				bottomPanel.setPreferredSize(new Dimension(500, 90));
				bottomPanel.setBackground(new Color(245, 245, 247));
				contentPanel.add(bottomPanel, BorderLayout.SOUTH);

				okButton = new JButton("\u786E\u8BA4");
				okButton.setForeground(Color.WHITE);
				okButton.setFont(new Font("微软雅黑", Font.BOLD, 20));
				okButton.setBackground(new Color(12, 115, 194));
				okButton.setActionCommand("OK");
				okButton.setBounds(122, 34, 77, 30);
				okButton.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {

						folderList = readAndWrite.getMyMusicFolder(new File("file//myMusicFolderCatalog.txt"));
						if (folderList != null) {
							folderArray = new String[folderList.size()];
							for (int i = 0; i < folderArray.length; i++) {
								folderArray[i] = folderList.get(i);
								
							}
							list.setListData(folderArray);// 更新文件夹展示列表,写入文件夹保存文件
							scanfolder.writeUrlFile();// 将最新的文件夹文件下所有MP3路径写入到URL文件
							
							urlList = readAndWrite.getMyMusicFolder(new File("file\\myMusicUrl.txt"));// 从myMusicUrl文件中得到URL集合
							
							writeJson.getLocalMusicJson(urlList);//生成本地音乐JSON
							
							mp3tomessage = new Mp3ToMessage(urlList);// 用一个URL集合创建MP3解码器对象
							musicAllList = mp3tomessage.getMusicAllList();// 得到解码器解析到的所有mp3信息
							readAndWrite.writeMyMusicList(musicAllList);// 将音乐信息二维集合写入到myMusicList文件中

						}
						AllPanel.addFolderDialog.dispose();// 最后关闭文件夹窗口
					}
				});
				bottomPanel.add(okButton);

				JButton addButton = new JButton("\u6DFB\u52A0\u6587\u4EF6\u5939");
				addButton.setForeground(Color.BLACK);
				addButton.setFont(new Font("微软雅黑", Font.BOLD, 20));
				addButton.setBackground(Color.WHITE);
				addButton.setBounds(253, 34, 146, 30);
				addButton.addActionListener(new ChooseFolderActionListener());
				bottomPanel.add(addButton);

				folderList = readAndWrite.getMyMusicFolder(new File("file//myMusicFolderCatalog.txt"));
				// System.out.println(folderList.toString());
				if (folderList != null) {
					folderArray = new String[folderList.size()];
					for (int i = 0; i < folderArray.length; i++) {
						folderArray[i] = folderList.get(i);
					}
					list.setListData(folderArray);// 更新列表数据
					list.setSize(300, 150);

//					scanfolder.writeUrlFile();//将最新的文件夹文件下所有MP3路径写入到URL文件
//					for (int j = 0; j < folderArray.length; j++) {
//						scanfolder.writeUrlFile("file\\myMusicUrl.txt", scanfolder.getMyMusicUrlList(folderArray[j]));
//					}
				}

			}
		}
	}

	public static void updateFolderList(List<String> folderList) {
		list.setListData(folderArray);
		list.revalidate();
	}
}
