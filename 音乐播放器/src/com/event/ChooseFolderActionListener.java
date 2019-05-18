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
		UIManager.put("ScrollBarUI", "com.sun.java.swing.plaf.windows.WindowsScrollBarUI");// 设置滚动条样式为window风格的滚动条样式
		// com.ui.DemoScrollBarUI
		// com.sun.java.swing.plaf.windows.WindowsScrollBarUI
		// 设置文件夹在swing中所显示的图标
		UIManager.put("FileView.directoryIcon",
				FileSystemView.getFileSystemView().getSystemIcon(new File(System.getProperty("user.dir"))));

		// 设置文件选择对话框的一系列图标
		UIManager.put("FileChooser.newFolderIcon", new ImageIcon("icon//fileChooser//newFolderIcon.png"));
		UIManager.put("FileChooser.upFolderIcon", new ImageIcon("icon//fileChooser//upFolderIcon.png"));
		UIManager.put("FileChooser.homeFolderIcon", new ImageIcon("icon//fileChooser//homeFolderIcon.png"));
		UIManager.put("FileChooser.detailsViewIcon", new ImageIcon("icon//fileChooser//listViewIco.png"));
		UIManager.put("FileChooser.listViewIcon", new ImageIcon("icon//fileChooser//detailsViewIcon.png"));

		// 设置工具提示的默认样式
		Color toolTipColor = new Color(80, 80, 80);
		UIManager.put("ToolTip.border", BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(toolTipColor),
				BorderFactory.createEmptyBorder(2, 3, 2, 3)));
		UIManager.put("ToolTip.background", Color.WHITE);
		UIManager.put("ToolTip.foreground", toolTipColor);

		CustomFileChooser.setUIFont(new Font("微软雅黑", Font.PLAIN, 20));
		AllPanel.fileChooser = new CustomFileChooser("D://");// 这里注意,美化选择器要在创建它之前完成
		AllPanel.fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);// 只能选择文件夹

		int val = AllPanel.fileChooser.showOpenDialog(null);
		AllPanel.fileChooser.getComponent(AllPanel.fileChooser.APPROVE_OPTION);
		if (val == JFileChooser.APPROVE_OPTION) {
			File selectedFile = AllPanel.fileChooser.getSelectedFile(); // 取得选中的文件
			readAndWrite.setMyMusicFolder(selectedFile.getPath());// 文件路径格式

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
