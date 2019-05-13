package com.event;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JTextArea;

import com.main.Chatroom;

public class SendRunnable implements Runnable {

	JTextArea myTextArea;
	final static int limitNum = 11;
	public static int row;
	int x = 730, y = 50, weight = 50, height = 50;
	public static ReplyActionThread replyMessageRunnable = new ReplyActionThread();
	Thread replyThread=new Thread(replyMessageRunnable);
	public static String translatetext=null;

	public static int chineseNum = 0;
	public static int signNum = 0;
	public static int spaceNum = 0;
	public static int lowCase = 0;
	public static int upCase = 0;
	public static int numberNum = 0;
	
	/**
	 * ����ַ�����ʽ
	 * 
	 * @param str
	 */
	public static void checkNum(String str) {
		String[] checkStrs = new String[str.length()];
		for (int i = 0; i < str.length(); i++) {
			checkStrs[i] = str.substring(1 * i, 1 * (i + 1));
			if (checkStrs[i].equals("@") || checkStrs[i].equals("%") || checkStrs[i].equals("&")) {
				chineseNum++;
			} else if (checkStrs[i].equals("\n")) {
				checkStrs[i] = "";
			} else if (checkStrs[i].equals("+") || checkStrs[i].equals("=") || checkStrs[i].equals("$")
					|| checkStrs[i].equals("#") || checkStrs[i].equals("^")) {
				upCase++;
			} else if (checkStrs[i].equals("(") || checkStrs[i].equals(")") || checkStrs[i].equals("[")
					|| checkStrs[i].equals("]") || checkStrs[i].equals("{") || checkStrs[i].equals("}")) {
				spaceNum++;
			} else if (checkStrs[i].matches("\\p{Punct}")) {
				signNum++;
			} else if (checkStrs[i].matches("\\d")) {
				numberNum++;
			} else if (checkStrs[i].matches("\\p{Lower}")) {
				lowCase++;
			} else if (checkStrs[i].matches("\\p{Upper}")) {
				upCase++;
			} else if (checkStrs[i].matches("\\p{Blank}")) {
				spaceNum++;
			} else {
				chineseNum++;
			}
		}
	}

	/**
	 * ��ʽ�ַ�����������
	 */
	public void zeroNum() {
		chineseNum = 0;
		signNum = 0;
		spaceNum = 0;
		lowCase = 0;
		upCase = 0;
		numberNum = 0;
	}
	
	@Override
	public void run() {

		String messageText = Chatroom.writeArea.getText();
		translatetext=messageText;
		int num = messageText.length() % limitNum;// ����
		row = (num == 0) ? (messageText.length() / limitNum) : ((messageText.length() / limitNum) + 1);
		String[] messages = new String[row];

		if (num == 0) {
			for (int i = 0; i < row; i++) {
				messages[i] = messageText.substring(limitNum * i, limitNum * (i + 1));
			}
		} else {
			for (int i = 0; i < row - 1; i++) {
				messages[i] = messageText.substring(limitNum * i, limitNum * (i + 1));
			}
			messages[row - 1] = messageText.substring(limitNum * (row - 1));
		}

		if (messages.length != 0 && (!messageText.equals("\n"))) { // ����������ݲ�Ϊ��
			if (messageText.length() <= 11) { // ���ݲ�����һ��
				checkNum(messageText);
				weight = 20 + 30 * chineseNum + 15 * signNum + 10 * spaceNum + 18 * lowCase + 20 * upCase
						+ 18 * numberNum;
			} else {
				weight = 350;
			}
			zeroNum();//�����ʽ�����

			height = 50 * (row);
			x = 780 - weight;
			y = Chatroom.getBeginY();

			myTextArea = new JTextArea();
			for (int i = 0; i < messages.length; i++) {
				myTextArea.append(" " + messages[i] + "\n");
			}

			myTextArea.setEditable(false);
			myTextArea.setBackground(new Color(50, 205, 50));
			myTextArea.setForeground(Color.BLACK);
			myTextArea.setBounds(x, y, weight, height);
			myTextArea.setFont(new Font("΢���ź�", Font.PLAIN, 30));
			Chatroom.chatPanel.add(myTextArea);
			myTextArea.setVisible(true);
			Chatroom.setAllMessageRow(row);
			Chatroom.setMyMessageCount();

			Chatroom.chatPanel.repaint();// ˢ�������¼���,������Ҫ

			
			
			Chatroom.setBeginY(row);
			
			Chatroom.stateLabel.setText("�Է���������...");
			

			Chatroom.stateLabel.setText(Chatroom.robot.name);
			
			if ((50 + 50 * Chatroom.getAllMessageRow() + (Chatroom.getAllMessageCount() - 1) * 20) > Chatroom
					.getBeginHeight()) {//�������������������Ƿ���Ҫ����
				Chatroom.setBeginHeight(
						(50 + 50 * Chatroom.getAllMessageRow() + (Chatroom.getAllMessageCount() - 1) * 20)
								- Chatroom.getBeginHeight());
				Chatroom.chatPanel.setPreferredSize(new Dimension(Chatroom.beginWeight, Chatroom.beginHeight));
			}
			Chatroom.writeArea.setText(null);
		}
		Chatroom.writeArea.setText(null);
		Chatroom.writeArea.repaint();
	}

}
