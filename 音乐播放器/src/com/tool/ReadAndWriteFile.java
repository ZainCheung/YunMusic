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
 * ��д��Ϣ�ı���
 * 
 * @author Jack
 *
 */
public class ReadAndWriteFile {

	File myMusicListFile = new File("file//myMusicList.txt");// ����������Ϣ��д�ļ�
	File myMusicUrlFile = new File("file//myMusicUrl.txt");// ��������URL��д�ļ�
	File myMusicFolderCatalog = new File("file//myMusicFolderCatalog.txt");// ���������ļ��ж�д�ļ�
	StringBuffer string = new StringBuffer();
	List<List<String>> myMusicListData = new ArrayList<List<String>>();// ����������Ϣ���漯��
	List<String> myMusicUrlData = new ArrayList<String>();// ��������URL���漯��
	List<String> myMusicFolder = new ArrayList<String>();// ���������ļ��м���
	FileInputStream fis = null;
	InputStreamReader isr = null;
	BufferedReader br = null; // ���ڰ�װInputStreamReader,��ߴ������ܡ���ΪBufferedReader�л���ģ���InputStreamReaderû�С�

	public ReadAndWriteFile() {

	}

	/**
	 * ��ȡָ���ļ�����Ϣ,������һ���ַ���,��¼֮���û���ת���\n���� ���߷���
	 * 
	 * @param file
	 * @return
	 */
	public String readFile(File file) {
		try {
			String str = "";
			String str1 = "";
			fis = new FileInputStream(file);// FileInputStream
			// ���ļ�ϵͳ�е�ĳ���ļ��л�ȡ�ֽ�
			isr = new InputStreamReader(fis);// InputStreamReader ���ֽ���ͨ���ַ���������,
			br = new BufferedReader(isr);// ���ַ��������ж�ȡ�ļ��е�����,��װ��һ��new InputStreamReader�Ķ���
//			if ((str=br.readLine())==null) {//���ļ�Ϊ��ʱ����null
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
			// ����ȡ��һ�в�Ϊ��ʱ,�Ѷ�����str��ֵ����str1
			// System.out.println(str1);// ��ӡ��str1
			return str1;
		} catch (FileNotFoundException e) {
			System.out.println("�Ҳ���ָ���ļ�");
		} catch (IOException e) {
			System.out.println("��ȡ�ļ�ʧ��");
		} finally {
//			try {
//				br.close();
//				isr.close();
//				fis.close();
//				// �رյ�ʱ����ð����Ⱥ�˳��ر���󿪵��ȹر������ȹ�s,�ٹ�n,����m
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
		}
		return null;

	}

	/**
	 * д���ļ�,���ļ�����׷��
	 * 
	 * @param file
	 * @param string
	 */
	public void writeFile(File file, String string) {
		BufferedWriter out = null;
		try {
			out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, true)));// ���ϲ���true����׷��
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
	 * ���õ���������Ϣ����д���ļ���
	 * @param musicAllList
	 */
	public void writeMyMusicList(List<List<String>> musicAllList) {
		BufferedWriter out = null;
		
		try {
			if(myMusicListFile.exists())
			{
				myMusicListFile.delete();//����ļ�����,ɾ�����ؽ���
				myMusicListFile.createNewFile();
			}
			else {
				myMusicListFile.createNewFile(); // �������ļ�,��ͬ�����ļ��Ļ�ֱ�Ӹ���
			}
			//myMusicListFile.createNewFile();
			out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(myMusicListFile)));// ���ϲ���true����׷��
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
	 * ׷���ļ����б�����
	 * 
	 * @param aMusicFolder
	 */
	public void setMyMusicFolder(String aMusicFolder) {
		List<String> tempList = getMyMusicFolder(myMusicFolderCatalog);
		if (tempList == null) {
			writeFile(myMusicFolderCatalog, aMusicFolder);
		} else {
			for (int i = 0; i < tempList.size(); i++) {
				if (tempList.get(i).equals(aMusicFolder)) {//����Ƿ�����ͬ��
					return;
				}
			}
			writeFile(myMusicFolderCatalog, aMusicFolder);
		}

	}

	/**
	 * �õ������ɨ���ļ��ļ���
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
		myMusicFolder = new ArrayList<String>();//ÿ�β�������Ҫnewһ���¶���
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
	 * ��ָ���ļ����д���
	 * 
	 * @param file
	 * @return ��������������Ϣ�Ķ�ά����
	 */
	public List<List<String>> getMyMusicListData(File file) {
		try {
			String dataString = readFile(file);
			if (dataString == null) {// ���յ����ļ�Ϊ��ʱ���ؿ�
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
				//tempList.add(Integer.toString(i + 1));// ��������ÿ����¼�����
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
	 * �õ�һ��URL
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
	 * �õ�����URL
	 * 
	 * @return ����URL�ļ���
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
