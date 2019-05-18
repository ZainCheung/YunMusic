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

	public final File myMusicUrlListFile = new File("file\\myMusicUrl.txt");// ��������URL�����ļ�

	List<String> myMusicUrlList = new ArrayList<String>();
	ReadAndWriteFile readAndWrite = new ReadAndWriteFile();

	/**
	 * ����ָ���ļ����������MP3�ļ�����·��
	 * 
	 * @param fileDirPath
	 * @return myMusicUrlList
	 */
	public List<String> getMyMusicUrlList(String fileDirPath) {
		List<File> fileList = new ArrayList<File>();
		File file = new File(fileDirPath);
		File[] files = file.listFiles();// ��ȡĿ¼�µ������ļ����ļ���
		if (files == null) {// ���Ŀ¼Ϊ�գ�ֱ���˳�
			return null;
		}
		// ������Ŀ¼�µ������ļ�
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
			myMusicUrlList.add(f1.getPath());// �õ�ÿ���ļ��ľ���·��
			// System.out.println(f1.getPath());
			// System.out.println(f1.getName().substring(f1.getName().length()-4));
		}
		return myMusicUrlList;
	}

	/**
	 * д��TXT�ļ�
	 */
	public void writeUrlFile(String filePath, List<String> list) {
		try {
			File file = new File(filePath);
			List<String> fileList = readAndWrite.getMyMusicFolder(file);
			List<String> allList = new ArrayList<String>();
			if (fileList != null) {// ������Ϊ��ʱ�ſ��Ժϲ�
				allList.addAll(fileList);
			}
			allList.addAll(list);
			allList = new ArrayList<String>(new LinkedHashSet<>(allList));// ���м伯�������ȥ��
			file.createNewFile(); // �������ļ�,��ͬ�����ļ��Ļ�ֱ�Ӹ���
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
				out.flush(); // �ѻ���������ѹ���ļ�
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * ɨ���ļ���TXT�ļ�,�õ�ÿ���ļ����µ�MP3����·����д��URL�ļ�
	 */
	public void writeUrlFile() {
		try {
			List<String> urlList = new ArrayList<String>();
			List<String> folderList = readAndWrite.getMyMusicFolder(new File("file//myMusicFolderCatalog.txt"));

			for (int i = 0; i < folderList.size(); i++) {
				urlList.addAll(getMyMusicUrlList(folderList.get(i)));
			}

//			List<String> allList = new ArrayList<String>();
//			if (urlList != null) {// ������Ϊ��ʱ�ſ��Ժϲ�
//				allList.addAll(urlList);
//			}
//			allList.addAll(list);
//			allList = new ArrayList<String>(new LinkedHashSet<>(allList));// ���м伯�������ȥ��

			myMusicUrlListFile.delete();// ����ļ�����,ɾ�����ؽ���
			myMusicUrlListFile.createNewFile();// �������ļ�,��ͬ�����ļ��Ļ�ֱ�Ӹ���
			urlList = new ArrayList<String>(new LinkedHashSet<>(urlList));// ȥ��,ɸѡ���ظ���URL
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
				out.flush(); // �ѻ���������ѹ���ļ�
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
