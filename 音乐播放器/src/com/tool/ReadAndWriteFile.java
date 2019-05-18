package com.tool;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

/**
 * 读写信息文本类
 * 
 * @author Jack
 *
 */
public class ReadAndWriteFile {

	File myMusicListFile = new File("file//myMusicList.txt");// 本地音乐信息读写文件
	File myMusicUrlFile = new File("file//myMusicUrl.txt");// 本地音乐URL读写文件
	File myMusicFolderCatalog = new File("file//myMusicFolderCatalog.txt");// 本地音乐文件夹读写文件
	StringBuffer string = new StringBuffer();
	List<List<String>> myMusicListData = new ArrayList<List<String>>();// 本地音乐信息储存集合
	List<String> myMusicUrlData = new ArrayList<String>();// 本地音乐URL储存集合
	List<String> myMusicFolder = new ArrayList<String>();// 本地音乐文件夹集合
	FileInputStream fis = null;
	InputStreamReader isr = null;
	BufferedReader br = null; // 用于包装InputStreamReader,提高处理性能。因为BufferedReader有缓冲的，而InputStreamReader没有。

	public ReadAndWriteFile() {

	}

	/**
	 * 读取指定文件的信息,并返回一个字符串,记录之间用换行转义符\n隔开 工具方法
	 * 
	 * @param file
	 * @return
	 */
	public String readFile(File file) {
		try {
			String str = "";
			String str1 = "";
			fis = new FileInputStream(file);// FileInputStream
			// 从文件系统中的某个文件中获取字节
			isr = new InputStreamReader(fis);// InputStreamReader 是字节流通向字符流的桥梁,
			br = new BufferedReader(isr);// 从字符输入流中读取文件中的内容,封装了一个new InputStreamReader的对象
//			if ((str=br.readLine())==null) {//当文件为空时返回null
//				System.out.println(777);
//				return null;
//			}
			while ((str = br.readLine()) != null) {
				if (str.equals("")) {
					// str1 += str;
				} else {
					str1 += str + "\n";//
				}
				//System.out.println(str1);
			}
			// 当读取的一行不为空时,把读到的str的值赋给str1
			// System.out.println(str1);// 打印出str1
			return str1;
		} catch (FileNotFoundException e) {
			System.out.println("找不到指定文件");
		} catch (IOException e) {
			System.out.println("读取文件失败");
		} finally {
//			try {
//				br.close();
//				isr.close();
//				fis.close();
//				// 关闭的时候最好按照先后顺序关闭最后开的先关闭所以先关s,再关n,最后关m
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
		}
		return null;

	}

	/**
	 * 写入文件,在文件后面追加
	 * 
	 * @param file
	 * @param string
	 */
	public void writeFile(File file, String string) {
		BufferedWriter out = null;
		try {
			out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, true)));// 加上参数true代表追加
			out.write(string + "\n");//
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * 将得到的音乐信息集合写入文件中
	 * @param musicAllList
	 */
	public void writeMyMusicList(List<List<String>> musicAllList) {
		BufferedWriter out = null;
		
		try {
			if(myMusicListFile.exists())
			{
				myMusicListFile.delete();//如果文件存在,删除并重建它
				myMusicListFile.createNewFile();
			}
			else {
				myMusicListFile.createNewFile(); // 创建新文件,有同名的文件的话直接覆盖
			}
			//myMusicListFile.createNewFile();
			out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(myMusicListFile)));// 加上参数true代表追加
			for (int i = 0; i < musicAllList.size(); i++) {
				for (int j = 0; j < musicAllList.get(i).size()-1; j++) {
					out.write(musicAllList.get(i).get(j)+";");
				}
				out.write(musicAllList.get(i).get(musicAllList.get(i).size()-1)+"\n");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 追加文件夹列表内容
	 * 
	 * @param aMusicFolder
	 */
	public void setMyMusicFolder(String aMusicFolder) {
		List<String> tempList = getMyMusicFolder(myMusicFolderCatalog);
		if (tempList == null) {
			writeFile(myMusicFolderCatalog, aMusicFolder);
		} else {
			for (int i = 0; i < tempList.size(); i++) {
				if (tempList.get(i).equals(aMusicFolder)) {//检测是否有相同项
					return;
				}
			}
			writeFile(myMusicFolderCatalog, aMusicFolder);
		}

	}

	/**
	 * 得到保存待扫描文件的集合
	 * 
	 * @param file
	 * @return
	 */
	public List<String> getMyMusicFolder(File file) {
		String folderString = readFile(file);
		if (folderString.equals("")) {
			return null;
		}
		String[] tempStringArray = folderString.split("\n");
		myMusicFolder = new ArrayList<String>();//每次操作都需要new一个新对象
		for (int i = 0; i < tempStringArray.length; i++) {
			myMusicFolder.add(tempStringArray[i]);
		}
		return myMusicFolder;
	}

	public File getMyMusicListFile() {
		return myMusicListFile;
	}

	public void setMyMusicListFile(File myMusicListFile) {
		this.myMusicListFile = myMusicListFile;
	}

	public File getMyMusicUrlFile() {
		return myMusicUrlFile;
	}

	public void setMyMusicUrlFile(File myMusicUrlFile) {
		this.myMusicUrlFile = myMusicUrlFile;
	}

	/**
	 * 对指定文件进行处理
	 * 
	 * @param file
	 * @return 包含本地音乐信息的二维集合
	 */
	public List<List<String>> getMyMusicListData(File file) {
		try {
			String dataString = readFile(file);
			if (dataString == null) {// 接收到的文件为空时返回空
				return null;
			}
			List<String> tempList = new ArrayList<String>();
			String[] tempStringArray = dataString.split("\n");
			for (int i = 0; i < tempStringArray.length; i++) {
				String[][] allLabels = new String[tempStringArray.length][6];
				String[] singleLabels = tempStringArray[i].split(";");
				for (int j = 0; j < 6; j++) {
					allLabels[i][j] = singleLabels[j];
				}
				//tempList.add(Integer.toString(i + 1));// 这里生成每条记录的序号
				for (int j = 0; j < 6; j++) {
					tempList.add(allLabels[i][j]);
				}
				myMusicListData.add(tempList);
				tempList = new ArrayList<String>();
			}
		} catch (Exception e) {
			// e.printStackTrace();
			return null;
		}
		return myMusicListData;
	}

	public void setMyMusicListData(List<List<String>> myMusicListData) {
		this.myMusicListData = myMusicListData;
	}

	/**
	 * 得到一条URL
	 * 
	 * @param index
	 * @return
	 */
	public String getMyMusicUrlData(File file, int index) {
		String url = getMyMusicUrlData(file).get(index);
		if (url != null) {
			return url;
		} else {
			return null;
		}

	}

	/**
	 * 得到所有URL
	 * 
	 * @return 包含URL的集合
	 */
	public List<String> getMyMusicUrlData(File file) {
		String urlString = readFile(file);
		String[] tempStringArray = urlString.split("\n");
		for (int i = 0; i < tempStringArray.length; i++) {
			myMusicUrlData.add(tempStringArray[i]);
		}
		return myMusicUrlData;
	}

	public void setMyMusicUrlData(List<String> myMusicUrlData) {
		this.myMusicUrlData = myMusicUrlData;
	}

	public static void main(String[] args) {
		ReadAndWriteFile readList = new ReadAndWriteFile();

//		List<List<String>> list = readList.getMyMusicListData(new File("file//myMusicList.txt"));
//		for (int i = 0; i < list.size(); i++) {
//			for (int j = 0; j < list.get(i).size(); j++) {
//				String string = list.get(i).get(j);
//				System.out.println(string);
//			}
//		}
	}
}
