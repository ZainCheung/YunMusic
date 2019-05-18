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
	Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();// ��Ļ�ߴ�
	ScanFolder scanfolder = new ScanFolder();// ɨ���ļ�����mp3�Ķ���
	ReadAndWriteFile readAndWrite = new ReadAndWriteFile();// �ļ�����������
	public WriteJSON writeJson = new WriteJSON();
	
	MyMusicList myMusicList = new MyMusicList();
	Mp3ToMessage mp3tomessage;
	public List<String> folderList = new ArrayList<String>();// �õ����ļ����б�
	public static String[] folderArray;// ���ļ����б�õ�������,�Ա�Ϊ�б��������ģ��
	List<String> urlList = new ArrayList<String>();// ���ֵ�URL�ļ�
	List<List<String>> musicAllList;// �õ��������б�����
	private Point pressedPoint;
	JScrollPane scrollPane;
	public static JList list;// ��ʾ�ļ����б�
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

		delate = new JMenuItem("ɾ��ѡ��");
		menu.add(delate);
//		  mCopy = new JMenuItem("����(C)");
//		  menu.add(mCopy);
//		  mCut = new JMenuItem("����(T)");
//		  menu.add(mCut);
//		  mPaste = new JMenuItem("ճ��(P)");
//		  menu.add(mPaste);
//		  mDel = new JMenuItem("ɾ��(D)");
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
			 * ��������ƶ������¼�
			 */
			topPanel.addMouseListener(new MouseAdapter() {
				public void mousePressed(MouseEvent e) { // ��갴���¼�
					pressedPoint = e.getPoint(); // ��¼�������
				}
			});
			topPanel.addMouseMotionListener(new MouseAdapter() {
				public void mouseDragged(MouseEvent e) { // �����ק�¼�
					Point point = e.getPoint();// ��ȡ��ǰ����
					Point locationPoint = getLocation();// ��ȡ��������
					int x = locationPoint.x + point.x - pressedPoint.x;// �����ƶ����������
					int y = locationPoint.y + point.y - pressedPoint.y;
					setLocation(x, y);// �ı䴰��λ��
				}
			});
			{
				JLabel lblNewLabel = new JLabel("\u9009\u62E9\u672C\u5730\u97F3\u4E50\u6587\u4EF6\u5939");
				lblNewLabel.setBounds(15, 21, 225, 34);
				lblNewLabel.setFont(new Font("΢���ź�", Font.PLAIN, 25));
				topPanel.add(lblNewLabel);
			}

			JButton exitButton = new JButton("X");
			exitButton.setFont(new Font("΢���ź�", Font.PLAIN, 25));
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
			lblNewLabel_1.setFont(new Font("����", Font.PLAIN, 22));
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
			// �������仰���ù�������������
			JScrollBar Bar1 = null;
			Bar1 = scrollPane.getVerticalScrollBar();
			Bar1.setUnitIncrement(40);
			contentPanel.add(bodyPanel, BorderLayout.WEST);
			contentPanel.add(scrollPane, BorderLayout.CENTER);
			{
				list = new JList();
				list.setFont(new Font("����", Font.PLAIN, 25));
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
//							// �����Ҽ��˵�
//							list.setSelectedIndex(list.locationToIndex(e.getPoint()));
//							//menu.show(list, e.getX() , e.getY()-20 );
//							 if (list.getSelectedIndex()!=-1) {//e.isPopupTrigger()&&
//						            
//						            //��ȡѡ�����ֵ
//						            Object selected = list.getModel().getElementAt(list.getSelectedIndex());
//						            System.out.println(selected);
//						            menu.show(e.getComponent(),e.getX(), e.getY()-20);
//						           }
//						}
//					}
//				});
//				delate.addActionListener(new ActionListener() {
//					public void actionPerformed(ActionEvent e) {
//						System.out.println("�����" + row);
//					}
//				});

				JPanel bottomPanel = new JPanel();
				bottomPanel.setLayout(null);
				bottomPanel.setPreferredSize(new Dimension(500, 90));
				bottomPanel.setBackground(new Color(245, 245, 247));
				contentPanel.add(bottomPanel, BorderLayout.SOUTH);

				okButton = new JButton("\u786E\u8BA4");
				okButton.setForeground(Color.WHITE);
				okButton.setFont(new Font("΢���ź�", Font.BOLD, 20));
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
							list.setListData(folderArray);// �����ļ���չʾ�б�,д���ļ��б����ļ�
							scanfolder.writeUrlFile();// �����µ��ļ����ļ�������MP3·��д�뵽URL�ļ�
							
							urlList = readAndWrite.getMyMusicFolder(new File("file\\myMusicUrl.txt"));// ��myMusicUrl�ļ��еõ�URL����
							
							writeJson.getLocalMusicJson(urlList);//���ɱ�������JSON
							
							mp3tomessage = new Mp3ToMessage(urlList);// ��һ��URL���ϴ���MP3����������
							musicAllList = mp3tomessage.getMusicAllList();// �õ�������������������mp3��Ϣ
							readAndWrite.writeMyMusicList(musicAllList);// ��������Ϣ��ά����д�뵽myMusicList�ļ���

						}
						AllPanel.addFolderDialog.dispose();// ���ر��ļ��д���
					}
				});
				bottomPanel.add(okButton);

				JButton addButton = new JButton("\u6DFB\u52A0\u6587\u4EF6\u5939");
				addButton.setForeground(Color.BLACK);
				addButton.setFont(new Font("΢���ź�", Font.BOLD, 20));
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
					list.setListData(folderArray);// �����б�����
					list.setSize(300, 150);

//					scanfolder.writeUrlFile();//�����µ��ļ����ļ�������MP3·��д�뵽URL�ļ�
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
