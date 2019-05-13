package com.robot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import com.plugin.TranslateTool;

public class Robot {

	public String name;
	public String sex;
	public int age;
	int index_max = 0;
	Database base = new Database();
	private String reciveMessage = null;
	private String replyMessage = null;
	private boolean requestion=false;//����Ƿ���׷��,Ĭ�ϲ�׷��
	Random random = new Random();
	public TranslateTool translate;
	// int[] relevance = new int[base.getAnswers().size()];// ��ض�����,���������ݿⳤ����ͬ

	public boolean isRequestion() {
		return requestion;
	}

	public void setRequestion(String string) {
		if (queryBase(string, base.givenTranslate)) {
			
			this.requestion =true;
		}else {
			this.requestion = requestion;
		}
	}
	
	public void setRequestion(boolean bool) {
			this.requestion = bool;
	}

	public String getReciveMessage() {
		return reciveMessage;
	}

	public void setReciveMessage(String reciveMessage) {
		this.reciveMessage = reciveMessage;
		// getRelevance();
	}

	public String getReplyMessage() {
		return replyMessage;
	}

	public void setReplyMessage(String reciveMessage) {
		this.replyMessage = oneCharReplyMessage(reciveMessage);
		if (this.replyMessage == null) {
			this.replyMessage = givenReplyMessage(reciveMessage);
			if (this.replyMessage == null) {
				this.replyMessage = labelReplyMessage(reciveMessage);
				if (this.replyMessage == null) {
					this.replyMessage = getRelevance(reciveMessage);
					if (this.replyMessage == null) {
						this.replyMessage = randomReplyMessage(base.getUnknownAnswers());
					}
				}
			}
		}
	}

	/**
	 * �����յ���Ϣ���ַ���ضȳ�ȡ�ظ���Ϣ
	 * 
	 * @param reciveMessage
	 * @return
	 */
	public String getRelevance(String reciveMessage) {

		int length = reciveMessage.length();
		int[] relevance = new int[base.getAnswers().size()];// ��ض�����,���������ݿⳤ����ͬ
		List<String> answers = base.getAnswers();// ��ȡ���ݿ���Ϣ
		String[] strs = new String[reciveMessage.length()];// �յ�����Ϣת��Ϊ���ַ�����
		String[][] twoStrs = new String[length][length];// �յ�����Ϣ�����˫�ַ������浽˫�ַ�����
		List<String> twoStrsList = new ArrayList<String>();// ��˫�ַ�������ɸѡ��null�����浽˫�ַ�������
		String[][][] twoBases = new String[relevance.length][100][100];// ���ݿ��ڵ���Ϣ�����˫�ַ�
		List<String> twoBasesList = new ArrayList<String>();

		/**
		 * �����ַ���ƥ��
		 */
		for (int i = 0; i < reciveMessage.length(); i++) {
			strs[i] = reciveMessage.substring(i, (i + 1));
			for (int j = 0; j < relevance.length; j++) {
				for (int k = 0; k < base.getAnswers().get(j).length(); k++) {
					if (strs[i].equals(base.getAnswers().get(j).substring(k, k + 1))) {
						relevance[j]++;// �ҵ������,������ض�
					}
				}
			}
		}

		/**
		 * ˫�ַ���ƥ��
		 */
		for (int d = 0; d < answers.size(); d++) {// ѭ����¼����
			for (int b = 0; b < answers.get(d).length() - 1; b++) {// ��
				for (int c = 0; c < answers.get(d).length() - b - 1; c++) {// ��
					twoBases[d][b][c] = answers.get(d).substring(b, b + 1)
							+ answers.get(d).substring(b + c + 1, b + c + 2);

				}
			}
		}
		for (int i = 0; i < twoBases.length; i++) {// ѭ����¼����
			for (int j = 0; j < twoBases[i].length; j++) {// ��
				for (int k = 0; k < twoBases[i][j].length; k++) {// ��
					if (twoBases[i][j][k] != null) {
						twoBasesList.add(twoBases[i][j][k]);
					}
				}
			}
		}
		// ���´����յ�����Ϣ
		for (int i = 0; i < reciveMessage.length() - 1; i++) {
			for (int j = 0; j < reciveMessage.length() - 1 - i; j++) {
				twoStrs[i][j] = reciveMessage.substring(i, i + 1) + reciveMessage.substring(i + j + 1, i + j + 2);
			}
		}
		for (int i = 0; i < twoStrs.length; i++) {
			for (int j = 0; j < twoStrs[i].length; j++) {
				if (twoStrs[i][j] != null) {
					twoStrsList.add(twoStrs[i][j]);
				}

			}
		}
		// �������˫�ַ���Ȩ
		for (int i = 0; i < twoStrsList.size(); i++) {
			// ѭ��ÿ����¼
			for (int j = 0; j < twoBases.length; j++) {
				for (int k = 0; k < twoBases[j].length; k++) {
					for (int m = 0; m < twoBases[j][k].length; m++) {
						if (twoStrsList.get(i).equals(twoBases[j][k][m])) {
							relevance[j]++;
						}
					}

				}
			}
		}
		// ������ѡ��Ȩλ��ߵ�һ��,��¼�������ݿ�����±�

		index_max = 0;
		for (int i = 0; i < relevance.length; i++) {
			if (relevance[i] > relevance[index_max]) {
				index_max = i;
			}
		}
//		System.out.println(index_max);
//		System.out.println(relevance[index_max]);
		if (relevance[index_max] >= 1) {// ��ض���ֵ����
			return answers.get(index_max);
		} else {
			return null;
		}

		// setReplyMessage(answers.get(index_max));// ��ɻظ���Ϣ����ȡ

	}

	/**
	 * �����ȡ�ش���Ϣ����
	 * 
	 * @param lists
	 * @return
	 */
	public String randomReplyMessage(List<String> lists) {
		int num = random.nextInt(lists.size());
		return lists.get(num);
	}

	/**
	 * ��¼һ���ַ������ֶ��ٴ�ָ���ַ���Ƭ��
	 * 
	 * @param text ĸ�ַ���
	 * @param sub  Ƭ��
	 * @return ���ֵĴ���
	 */
	public int getCount(String text, String sub) {
		int count = 0, start = 0;
		while ((start = text.indexOf(sub, start)) >= 0) {
			start += sub.length();
			count++;
		}
		return count;
	}

	/**
	 * ������Ϣ��ı�ǩ,�����������ֱ�ǩ
	 * 
	 * @param string
	 * @return
	 */
	public String[] getLabel_two(String string) {
		String[] labels = new String[string.length() - 1];
		for (int i = 0; i < string.length() - 1; i++) {
			labels[i] = string.substring(i, i + 2);
		}
		return labels;
	}

	/**
	 * ������Ϣ��ı�ǩ,�����������ֱ�ǩ
	 * 
	 * @param string
	 * @return
	 */
	public String[] getLabel_three(String string) {
		if (string.length() >= 3) {
			String[] labels = new String[string.length() - 2];
			for (int i = 0; i < string.length() - 2; i++) {
				labels[i] = string.substring(i, i + 3);
			}
			return labels;
		} else {
			return null;
		}
	}

	/**
	 * ����ǩ��ȡ�ش���Ϣ
	 * 
	 * @param string
	 * @return
	 */
	public String labelReplyMessage(String string) {
		int[] counts = new int[base.getAnswers().size()];
		String[] twoLabels = getLabel_two(string);
		String[] threeLabels = getLabel_three(string);
		String returnTranslateString="";
		//������з���ģ��
		for (int j = 0; j < twoLabels.length; j++) {
			if (twoLabels[j].equals("����")) {
					returnTranslateString+=string.substring(j+2, string.length()) ;
				System.out.println(returnTranslateString);
				translate=new TranslateTool(returnTranslateString);
				System.out.println(getTranslateText());
				return getTranslateText();
			}
		}
		for (int i = 0; i < base.getAnswers().size(); i++) {
			for (int j = 0; j < twoLabels.length; j++) {
				counts[i] += getCount(base.getAnswers().get(i), twoLabels[j]);
			}
			if (string.length() >= 3) {
				for (int k = 0; k < twoLabels.length; k++) {
					try {
						counts[i] += getCount(base.getAnswers().get(i), threeLabels[k]);
					} catch (Exception e) {
					}
				}
			}
		}

		index_max = 0;
		for (int i = 0; i < counts.length; i++) {
			if (counts[i] > counts[index_max]) {
				index_max = i;
			}
		}
//		System.out.println(index_max);
//		System.out.println("�����ض�Ϊ" + counts[index_max]);
		if (counts[index_max] >= 1) {// ��ض���ֵ����
			return base.getAnswers().get(index_max);
		} else {
			return null;
		}
	}

	/**
	 * �ж��ַ����Ƿ�����ض�����
	 * 
	 * @param string
	 * @param givenList
	 * @return
	 */
	public boolean queryBase(String string, List<String> givenList) {
		for (int i = 0; i < givenList.size(); i++) {
			if (string.equals(givenList.get(i))) {
				return true;
			}
		}
		return false;
	}

	/**
	 * �ض�������ض��ش�
	 * 
	 * @param string
	 * @return
	 */
	public String givenReplyMessage(String string) {

		if (queryBase(string, base.givenList_Hi)) {
			return randomReplyMessage(base.answersList_Hi);
		} else if (queryBase(string, base.givenList_KuaKua)) {
			return randomReplyMessage(base.answersList_KuaKua);
//		} else if (queryBase(string, base.givenTranslate)) {
//			return randomReplyMessage(base.answersTranslate);
		} else {
			return null;
		}

	}

	/**
	 * һ���ַ��ظ�����
	 * 
	 * @param string
	 * @return
	 */
	public String oneCharReplyMessage(String string) {
		// ������ܵ�����Ϣ����Ϊһ�Ҳ�Ϊ��������ĸ
		if (string.length() == 1 && !string.matches("\\p{Alnum}")) {
			return string + randomReplyMessage(base.oneCharAnswers);
		} else {
			return null;
		}

	}
	public String getTranslateText() {
		if (translate.isError()==true) {
			return randomReplyMessage(base.errorTranslate);
		}
		
		return translate.getResult();
	}
}
