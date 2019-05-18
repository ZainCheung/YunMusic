package com.event;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.UIManager;
import javax.swing.filechooser.FileSystemView;

import com.fileChooser.CustomFileChooser;
import com.tool.ReadAndWriteFile;
import com.view.AddFolderDialog;
import com.view.AllPanel;
import com.view.CardLayoutDemo;

public class ChooseFolderActionListener implements ActionListener {

	ReadAndWriteFile readAndWrite = new ReadAndWriteFile();
	//AddFolderDialog addFolderDialog = new AddFolderDialog();

	@Override
	public void actionPerformed(ActionEvent arg0) {
		UIManager.put("ScrollBarUI", "com.sun.java.swing.plaf.windows.WindowsScrollBarUI");// ���ù�������ʽΪwindow���Ĺ�������ʽ
		// com.ui.DemoScrollBarUI
		// com.sun.java.swing.plaf.windows.WindowsScrollBarUI
		// �����ļ�����swing������ʾ��ͼ��
		UIManager.put("FileView.directoryIcon",
				FileSystemView.getFileSystemView().getSystemIcon(new File(System.getProperty("user.dir"))));

		// �����ļ�ѡ��Ի����һϵ��ͼ��
		UIManager.put("FileChooser.newFolderIcon", new ImageIcon("icon//fileChooser//newFolderIcon.png"));
		UIManager.put("FileChooser.upFolderIcon", new ImageIcon("icon//fileChooser//upFolderIcon.png"));
		UIManager.put("FileChooser.homeFolderIcon", new ImageIcon("icon//fileChooser//homeFolderIcon.png"));
		UIManager.put("FileChooser.detailsViewIcon", new ImageIcon("icon//fileChooser//listViewIco.png"));
		UIManager.put("FileChooser.listViewIcon", new ImageIcon("icon//fileChooser//detailsViewIcon.png"));

		// ���ù�����ʾ��Ĭ����ʽ
		Color toolTipColor = new Color(80, 80, 80);
		UIManager.put("ToolTip.border", BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(toolTipColor),
				BorderFactory.createEmptyBorder(2, 3, 2, 3)));
		UIManager.put("ToolTip.background", Color.WHITE);
		UIManager.put("ToolTip.foreground", toolTipColor);

		CustomFileChooser.setUIFont(new Font("΢���ź�", Font.PLAIN, 20));
		AllPanel.fileChooser = new CustomFileChooser("D://");// ����ע��,����ѡ����Ҫ�ڴ�����֮ǰ���
		AllPanel.fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);// ֻ��ѡ���ļ���

		int val = AllPanel.fileChooser.showOpenDialog(null);
		AllPanel.fileChooser.getComponent(AllPanel.fileChooser.APPROVE_OPTION);
		if (val == JFileChooser.APPROVE_OPTION) {
			File selectedFile = AllPanel.fileChooser.getSelectedFile(); // ȡ��ѡ�е��ļ�
			readAndWrite.setMyMusicFolder(selectedFile.getPath());// �ļ�·����ʽ

//			new Thread(new Runnable() {
//
//				@Override
//				public void run() {
////					addFolderDialog.folderList = readAndWrite
////							.getMyMusicFolder(new File("file//myMusicFolderCatalog.txt"));
////					if (addFolderDialog.folderList != null) {
////						addFolderDialog.folderArray = new String[addFolderDialog.folderList.size()];
////						for (int i = 0; i < addFolderDialog.folderArray.length; i++) {
////							addFolderDialog.folderArray[i] = addFolderDialog.folderList.get(i);
////						}
////						addFolderDialog.list.setListData(addFolderDialog.folderArray);
////					}
//					List<String> folderList = readAndWrite.getMyMusicFolder(new File("file//myMusicFolderCatalog.txt"));
//					AddFolderDialog.updateFolderList(folderList);
//					AddFolderDialog.list.validate();
//					
//				}
//			}).start();
//			

		}

	}

}
