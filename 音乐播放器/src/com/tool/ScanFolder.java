package com.tool;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;

public class ScanFolder {

	public final File myMusicUrlListFile = new File("file\\myMusicUrl.txt");// 本地音乐URL保存文件

	List<String> myMusicUrlList = new ArrayList<String>();
	ReadAndWriteFile readAndWrite = new ReadAndWriteFile();

	/**
	 * 返回指定文件夹里的所有MP3文件绝对路径
	 * 
	 * @param fileDirPath
	 * @return myMusicUrlList
	 */
	public List<String> getMyMusicUrlList(String fileDirPath) {
		List<File> fileList = new ArrayList<File>();
		File file = new File(fileDirPath);
		File[] files = file.listFiles();// 获取目录下的所有文件或文件夹
		if (files == null) {// 如果目录为空，直接退出
			return null;
		}
		// 遍历，目录下的所有文件
		for (File f : files) {
			String name = f.getName();
			if (name.length() > 4) {
				if (f.isFile() && (name.substring(name.length() - 4).equals(".mp3"))) {
					fileList.add(f);//
				} else if (f.isDirectory()) {
					// System.out.println(f.getAbsolutePath());
					getMyMusicUrlList(f.getAbsolutePath());
				}
			}

		}
		for (File f1 : fileList) {
			myMusicUrlList.add(f1.getPath());// 得到每个文件的绝对路径
			// System.out.println(f1.getPath());
			// System.out.println(f1.getName().substring(f1.getName().length()-4));
		}
		return myMusicUrlList;
	}

	/**
	 * 写入TXT文件
	 */
	public void writeUrlFile(String filePath, List<String> list) {
		try {
			File file = new File(filePath);
			List<String> fileList = readAndWrite.getMyMusicFolder(file);
			List<String> allList = new ArrayList<String>();
			if (fileList != null) {// 当它不为空时才可以合并
				allList.addAll(fileList);
			}
			allList.addAll(list);
			allList = new ArrayList<String>(new LinkedHashSet<>(allList));// 用中间集合来完成去重
			file.createNewFile(); // 创建新文件,有同名的文件的话直接覆盖
			try (FileWriter writer = new FileWriter(file); BufferedWriter out = new BufferedWriter(writer)) {
				if (list != null) {
//					for (int i = 0; i < list.size(); i++) {
//						
//					out.write(list.get(i));
//					if (i < list.size() - 1) {
//						out.write("\n");
//					}
//				}
				}

				for (int i = 0; i < allList.size(); i++) {
					out.write(allList.get(i));
					if (i < allList.size() - 1) {
						out.write("\n");
					}
				}
				out.flush(); // 把缓存区内容压入文件
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 扫描文件夹TXT文件,得到每个文件夹下的MP3绝对路径并写入URL文件
	 */
	public void writeUrlFile() {
		try {
			List<String> urlList = new ArrayList<String>();
			List<String> folderList = readAndWrite.getMyMusicFolder(new File("file//myMusicFolderCatalog.txt"));

			for (int i = 0; i < folderList.size(); i++) {
				urlList.addAll(getMyMusicUrlList(folderList.get(i)));
			}

//			List<String> allList = new ArrayList<String>();
//			if (urlList != null) {// 当它不为空时才可以合并
//				allList.addAll(urlList);
//			}
//			allList.addAll(list);
//			allList = new ArrayList<String>(new LinkedHashSet<>(allList));// 用中间集合来完成去重

			myMusicUrlListFile.delete();// 如果文件存在,删除并重建它
			myMusicUrlListFile.createNewFile();// 创建新文件,有同名的文件的话直接覆盖
			urlList = new ArrayList<String>(new LinkedHashSet<>(urlList));// 去重,筛选掉重复的URL
			try (FileWriter writer = new FileWriter(myMusicUrlListFile);
					BufferedWriter out = new BufferedWriter(writer)) {
				if (urlList != null) {
					for (int i = 0; i < urlList.size(); i++) {

						out.write(urlList.get(i));
						if (i < urlList.size() - 1) {
							out.write("\n");
						}
					}
				}
				out.flush(); // 把缓存区内容压入文件
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
