package com.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.plaf.FontUIResource;
import javax.swing.table.TableCellRenderer;

import com.event.CurSorMouseListener;
import com.event.MyTableMouseMotionListener;
import com.event.PlayAllMouseActionListerner;
import com.model.MusicBase;
import com.model.MyMusicTableModel;
import com.model.WriteJSON;
import com.net.ReadJSON;
import com.tool.ReadAndWriteFile;
import com.view.SliderPanel.TimePlayAction;

public class MyMusicList extends JFrame {

	// public JPanel myMusicListPane;//�ҵ������б����
	public static JPanel myMusicListPane;// �ҵ������б����
	MusicBase musicBase = new MusicBase();
	CurSorMouseListener cursorListener = new CurSorMouseListener();// ��������
	// MusicDemo musicDemo= new MusicDemo();
	public SliderPanel sliderPanel = new SliderPanel();
	public Class<TimePlayAction> ActionClass = SliderPanel.TimePlayAction.class;
	public ActionListener playActionListener;
	public JButton playAllButton;// ȫ�����Ű�ť
	JButton chooseCatalogButton;
	public JTable table;
	public JTable titleLable;// ���������
	MyMusicTableModel tbModel;// MP3�б�ģ��
	MyMusicTableModel titleModel;// ����ģ��
	ReadAndWriteFile readfile = new ReadAndWriteFile();// �ļ�����������
	
	public WriteJSON writeJson = new WriteJSON();
	ReadJSON readJson = new ReadJSON();
	List<String> urlData;
	JLabel musicNumLabel;// ��ʾ��ǰ��������
	int row = 0; // ��¼������ƶ�������
	int column = 0; // ��¼������ƶ�������

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MyMusicList frame = new MyMusicList();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	private static void initGlobalFont() {
		FontUIResource fontUIResource = new FontUIResource(new Font("����", Font.PLAIN, 12));
		for (Enumeration keys = UIManager.getDefaults().keys(); keys.hasMoreElements();) {
			Object key = keys.nextElement();
			Object value = UIManager.get(key);
			if (value instanceof FontUIResource) {
				UIManager.put(key, fontUIResource);
			}
		}
	}

	/**
	 * Create the frame.
	 */
	public MyMusicList() {

		initGlobalFont();

		List<List<String>> data = new ArrayList<List<String>>();
		//data = readfile.getMyMusicListData(new File("file//myMusicList.txt"));
		
		List<String> urlList = readfile.getMyMusicFolder(new File("file\\myMusicUrl.txt"));// ��myMusicUrl�ļ��еõ�URL����
		//writeJson.getLocalMusicJson(urlList);//���ɱ�������JSON
		data = readJson.getLocalMusicList();
		urlData = new ArrayList<String>();// url����
		urlData = readfile.getMyMusicUrlData(readfile.getMyMusicUrlFile());

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1313, 846);
		myMusicListPane = new JPanel();
		myMusicListPane.setBorder(null);
		setContentPane(myMusicListPane);
		myMusicListPane.setLayout(new BorderLayout(0, 0));

		JPanel topPanel = new JPanel();
		topPanel.setPreferredSize(new Dimension(1313, 210));
		topPanel.setBackground(new Color(250, 250, 250));
		myMusicListPane.add(topPanel, BorderLayout.NORTH);
		topPanel.setLayout(new BorderLayout(0, 0));

		JPanel showPanel = new JPanel() {
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.setColor(Color.gray);
				g.drawLine(0, 80, getContentPane().getWidth(), 80);
			}
		};

		showPanel.setPreferredSize(new Dimension(1313, 210));
		showPanel.setBackground(new Color(250, 250, 250));
		topPanel.add(showPanel, BorderLayout.CENTER);// ����ӵ��м����
		showPanel.setLayout(null);

		JLabel label = new JLabel("\u672C\u5730\u97F3\u4E50");
		label.setBounds(57, 35, 120, 40);
		label.setFont(new Font("΢���ź�", Font.PLAIN, 30));
		showPanel.add(label);

		musicNumLabel = new JLabel("");
		musicNumLabel.setBounds(183, 45, 111, 28);
		musicNumLabel.setFont(new Font("΢���ź�", Font.PLAIN, 20));
		if (data == null) {
			musicNumLabel.setText("��ǰ�޸���");
		} else {
			musicNumLabel.setText(data.size() + "������,");
		}

		showPanel.add(musicNumLabel);

		chooseCatalogButton = new JButton("\u9009\u62E9\u76EE\u5F55");
		chooseCatalogButton.setBackground(Color.WHITE);
		chooseCatalogButton.setForeground(new Color(12, 115, 204));
		chooseCatalogButton.setBounds(289, 44, 120, 33);
		chooseCatalogButton.setFocusPainted(false);
		chooseCatalogButton.setBorderPainted(false);
		chooseCatalogButton.setFont(new Font("΢���ź�", Font.PLAIN, 18));
		chooseCatalogButton.setContentAreaFilled(false);// ȡ����ť��ѹʱ�ı�����
		chooseCatalogButton.setCursor(new Cursor(Cursor.HAND_CURSOR));// Ϊ��ť���С�����Ч��
		// chooseCatalogButton.addMouseListener(cursorListener);
		// chooseCatalogButton.addActionListener(new ChooseFolderActionListener());
		chooseCatalogButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				if (AllPanel.addFolderDialog == null) {
					AllPanel.addFolderDialog = new AddFolderDialog();
					// AllPanel.addFolderDialog.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
					AllPanel.addFolderDialog.setVisible(true);
				} else {
					AllPanel.addFolderDialog.setVisible(true);
				}

			}
		});
		showPanel.add(chooseCatalogButton);

		playAllButton = new JButton("\u64AD\u653E\u5168\u90E8");
		playAllButton.setForeground(Color.WHITE);
		playAllButton.setBackground(new Color(198, 47, 47));
		playAllButton.setFocusPainted(false);
		playAllButton.setBorderPainted(false);
		playAllButton.setFont(new Font("΢���ź�", Font.PLAIN, 20));
		playAllButton.setBounds(57, 103, 140, 45);
		// playAllButton.setContentAreaFilled(false);//ȡ����ť��ѹʱ�ı�����
		playAllButton.setCursor(new Cursor(Cursor.HAND_CURSOR));// Ϊ��ť���С�����Ч��
		playAllButton.addMouseListener(new PlayAllMouseActionListerner());
		showPanel.add(playAllButton);

		String[] columnNames = { "���", "���ֱ���", "����", "ר��", "ʱ��", "��С" };
		Object[][] obj = new Object[1][6];

		titleModel = new MyMusicTableModel();
		titleModel.data = new Vector(1, 1);
		titleModel.data.add("");
		titleModel.data.add("���ֱ���");
		titleModel.data.add("����");
		titleModel.data.add("ר��");
		titleModel.data.add("ʱ��");
		titleModel.data.add("��С");
		obj[0][0] = "";
		obj[0][1] = "���ֱ���";
		obj[0][2] = "����";
		obj[0][3] = "ר��";
		obj[0][4] = "ʱ��";
		obj[0][5] = "��С";

		titleModel.titles = new Vector(1, 1);

		for (int i = 0; i < 6; i++) {
			titleModel.titles.add(columnNames[i]);
		}

		tbModel = new MyMusicTableModel();
		tbModel.data = new Vector(1, 1);
		if (data != null) {
			for (int i = 0; i < data.size(); i++) {
				for (int j = 0; j < 6; j++) {
					if (j == 0) {
						tbModel.data.add("   " + data.get(i).get(j));// "�ֶ�"����
					} else {
						tbModel.data.add(data.get(i).get(j));
					}
				}
			}
		} else {
			// ��ʾ�޸���
			//System.out.println("��ǰ����Ϊ��");
		}

		tbModel.titles = new Vector(1, 1);
		for (int i = 0; i < 6; i++) {
			tbModel.titles.add(columnNames[i]);
		}

		// ���ɱ�����
		titleLable = new JTable(titleModel);
		// titleLable.setDefaultRenderer(Object.class, new MyMusicTableModel());//
		// ���ñ������new MyMusicTableModel()
		titleLable.setRowHeight(44);
		titleLable.setShowVerticalLines(false);
		titleLable.setShowHorizontalLines(false);
		titleLable.setColumnModel(titleModel.getColumn(titleLable, titleModel.columnWidth));
		titleLable.setBackground(new Color(245, 245, 247));
		titleLable.setSelectionBackground(new Color(250, 250, 250));
		titleLable.setFont(new Font("΢���ź�", Font.PLAIN, 25));
		titleLable.selectAll();
		topPanel.add(titleLable, BorderLayout.SOUTH);

		// ���������б�
		table = new JTable(tbModel){

		    /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override

		    public Component prepareRenderer(TableCellRenderer renderer, int row, int column){

		        Component comp = super.prepareRenderer(renderer,row,column);

		        Point p = getMousePosition();

		        if(p!=null){

		            int rowUnderMouse = rowAtPoint(p);

		            if(rowUnderMouse == row){

		                  comp.setBackground(new Color(236,237,238));

		           }
		        }

		        return comp;

		    }

		};
		
		
		table.setDefaultRenderer(Object.class, tbModel);// ���ñ������new MyMusicTableModel()
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.getTableHeader().setReorderingAllowed(false);
		table.setRowHeight(45);
		table.setShowVerticalLines(false);
		table.setShowHorizontalLines(false);
		table.setColumnModel(tbModel.getColumn(table, tbModel.columnWidth));
		table.setBackground(new Color(250, 250, 250));// 245, 245, 247
		table.setSelectionBackground(new Color(227, 227, 229));
		table.setFont(new Font("����", Font.PLAIN, 22));
		table.addMouseMotionListener(new MyTableMouseMotionListener());

		MyMusicTableModel.setColumnColor(table);// ���ü��ɫ

		myMusicListPane.add(table, BorderLayout.CENTER);

	}

//	public void setTable(MyMusicTableModel tbModel) {
//		table = new JTable(tbModel);
//		// MyMusicTableModel model = new MyMusicTableModel();
//		table.setDefaultRenderer(Object.class, tbModel);// ���ñ������new MyMusicTableModel()
//		table.setRowHeight(45);
//		table.setShowVerticalLines(false);
//		table.setShowHorizontalLines(false);
//		table.setColumnModel(tbModel.getColumn(table, tbModel.columnWidth));
//
//		table.setBackground(new Color(245, 245, 247));
//		table.setSelectionBackground(new Color(227, 227, 229));
//		table.setFont(new Font("����", Font.PLAIN, 20));
//
//		MyMusicTableModel.setColumnColor(table);// ���ü��ɫ
//
//		table.addMouseListener(new MouseAdapter() {
//			public void mouseClicked(MouseEvent e) {
//				if (e.getClickCount() == 2) {
//					// ʵ��˫��
//					int row = ((JTable) e.getSource()).rowAtPoint(e.getPoint()); // �����λ��
//					int col = ((JTable) e.getSource()).columnAtPoint(e.getPoint()); // �����λ��
//					String cellVal = null;
//					int cellIntVal = 0;
//					try {
//						cellVal = (String) (tbModel.getValueAt(row, col));
//						System.out.println((row + 1) + "   " + (col + 1) + "   " + cellVal);
//					} catch (Exception e1) {
//						cellIntVal = ((Integer) (tbModel.getValueAt(row, col))).intValue();
//						System.out.println((row + 1) + "   " + (col + 1) + "   " + cellIntVal);
//					}
//
//				} else
//					return;
//			}
//		});
//
////		CellRendererAndMouseListener rendererAndListener = new CellRendererAndMouseListener();
////		table.addMouseMotionListener(rendererAndListener);
////		table.setDefaultRenderer(Object.class, rendererAndListener);
//
//		myMusicListPane.add(table, BorderLayout.CENTER);
//	}

//	/**
//	 * ����ڲ����������ñ���������ɫ
//	 * @author Jack
//	 *
//	 */
//	class MyTableMouseMotionListener extends MouseMotionAdapter {
//
//		private int rowUnderMouse = -1;
//
//		public void mouseMoved(MouseEvent e) {
//
//			JTable table = (JTable) e.getSource();
//
//			Point p = table.getMousePosition();
//
//			if (p != null) {
//
//				rowUnderMouse = table.rowAtPoint(p);
//
//				if (rowUnderMouse >= 0) {
//
//					for (int i = 0; i < table.getColumnCount(); i++) {
//
//						table.prepareRenderer(table.getCellRenderer(rowUnderMouse, i), rowUnderMouse, i);
//
//						if (rowUnderMouse != 0) {
//
//							table.prepareRenderer(table.getCellRenderer(rowUnderMouse - 1, i), rowUnderMouse - 1, i);
//
//						}
//
//						if (rowUnderMouse != table.getRowCount() - 1) {
//
//							table.prepareRenderer(table.getCellRenderer(rowUnderMouse + 1, i), rowUnderMouse + 1, i);
//
//						}
//
//					}
//
//					table.repaint(table.getVisibleRect());
//
//				}
//
//			}
//
//		}
//
//	}

//	class CellRendererAndMouseListener extends JLabel implements TableCellRenderer, MouseMotionListener {
//
//		public CellRendererAndMouseListener() {
//			setOpaque(true);
//		}
//
//		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
//				int row, int column) {
//			if (MyMusicList.this.row == row && (MyMusicList.this.column == column)) {
//				this.setBackground(Color.RED);
//			} else {
//				// this.setBackground(Color.WHITE);
//			}
//			return this;
//		}
//
//		public void mouseMoved(MouseEvent e) {
//			JTable table = (JTable) e.getSource();
//			row = table.rowAtPoint(e.getPoint());
//			column = table.columnAtPoint(e.getPoint());
//			table.repaint();
//		}
//
//		public void mouseDragged(MouseEvent e) {
//		}
//	}
}
