package com.fileChooser;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.RenderingHints;
 
import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JButton;
 
/**
 * ��������Ͱ����Լ�ѡ��ʱ���л�������ɫ/�߿���ɫ/ǰ����ɫ��Button
 * 
 * @author tang
 * @since 2014/8/23
 * 
 */
public class RolloverBackgroundButton extends JButton {
 
	private static final long serialVersionUID = 1L;
 
	protected Color normalBackground;// Ĭ����״̬ʱ������ɫ,�����Ը������Ѷ���(background)
	protected Color pressedBackground;// ��갴��ʱ������ɫ
	protected Color rolloverBackground;// �������ʱ������ɫ
	protected Color selectedBackground;// ѡ��ʱ������ɫ
 
	protected Color normalBorderColor;// Ĭ����״̬ʱ�߿���ɫ
	protected Color pressedBorderColor;// ��갴��ʱ�߿���ɫ
	protected Color rolloverBorderColor;// �������ʱ�߿���ɫ
	protected Color selectedBorderColor;// ѡ��ʱ�߿���ɫ
 
	protected Color normalForeground;// Ĭ��ʱǰ����ɫ
	protected Color pressedForeground;// ��갴��ʱǰ����ɫ
	protected Color rolloverForeground;// �������ʱǰ����ɫ
	protected Color selectedForeground;// ѡ��ʱǰ����ɫ
 
	{
		initRolloverButton();
	}
 
	public RolloverBackgroundButton() {
	}
 
	public RolloverBackgroundButton(Icon icon) {
		super(icon);
	}
 
	public RolloverBackgroundButton(String text, Icon icon) {
		super(text, icon);
	}
 
	public RolloverBackgroundButton(String text) {
		super(text);
	}
 
	public RolloverBackgroundButton(Action a) {
		super(a);
	}
 
	private void initRolloverButton() {
		setRolloverEnabled(true);
		setBorderPainted(false);
		setContentAreaFilled(false);
		setFocusPainted(false);
		setFont(new Font(Font.DIALOG, Font.PLAIN, 14));//14
 
		setNormalBackground(new Color(216, 216, 216));
		setPressedBackground(new Color(216, 216, 216, 100));
		setNormalBorderColor(new Color(174, 174, 174));
		setRolloverBorderColor(new Color(95, 205, 245));
		setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
	}
 
	@Override
	public void paint(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
 
		Paint oldPaint = g2d.getPaint();
		if (isSelected() && selectedBackground != null) {// ѡ��ʱ
			g2d.setPaint(selectedBackground);
			g2d.fillRect(0, 0, getWidth(), getHeight());// ����
		} else if (getModel().isPressed() && pressedBackground != null) {// ��갴��ʱ
			g2d.setPaint(pressedBackground);
			g2d.fillRect(0, 0, getWidth(), getHeight());// ����
		} else if (getModel().isRollover() && rolloverBackground != null) {// �������ʱ
			g2d.setPaint(rolloverBackground);
			g2d.fillRect(0, 0, getWidth(), getHeight());// ����
		} else if (normalBackground != null) {// Ĭ����״̬ʱ
			g2d.setPaint(normalBackground);
			g2d.fillRect(0, 0, getWidth(), getHeight());// ����
		}
		g2d.setPaint(oldPaint);
 
		if (isSelected() && selectedForeground != null) {// ѡ��ʱ
			setForeground(selectedForeground);
		} else if (getModel().isPressed() && pressedForeground != null) {// ��갴��ʱ
			setForeground(pressedForeground);
		} else if (getModel().isRollover() && rolloverForeground != null) {// �������ʱ
			setForeground(rolloverForeground);
		} else if (normalForeground != null) {// Ĭ����״̬ʱ
			setForeground(normalForeground);
		}
 
		super.paint(g2d);
 
		if (isSelected() && selectedBorderColor != null) {// ѡ��ʱ
			g2d.setPaint(selectedBorderColor);
			g2d.drawRect(0, 0, getWidth() - 1, getHeight() - 1);// �߿�
		} else if (getModel().isPressed() && pressedBorderColor != null) {// ��갴��ʱ
			g2d.setPaint(pressedBorderColor);
			g2d.drawRect(0, 0, getWidth() - 1, getHeight() - 1);// �߿�
		} else if (getModel().isRollover() && rolloverBorderColor != null) {// �������ʱ
			g2d.setPaint(rolloverBorderColor);
			g2d.drawRect(0, 0, getWidth() - 1, getHeight() - 1);// �߿�
		} else if (normalBorderColor != null) {// Ĭ����״̬ʱ
			g2d.setPaint(normalBorderColor);
			g2d.drawRect(0, 0, getWidth() - 1, getHeight() - 1);// �߿�
		}
		g2d.setPaint(oldPaint);
	}
 
	/**
	 * ������õ�Ĭ������ֵ��<br>
	 * 
	 * setNormalBackground(null);<br>
	 * setPressedBackground(null); <br>
	 * setNormalBorderColor(null); <br>
	 * setRolloverBorderColor(null);
	 * 
	 */
	public void clearDefaultAttribute() {
		setNormalBackground(null);
		setPressedBackground(null);
		setNormalBorderColor(null);
		setRolloverBorderColor(null);
	}
 
	public Color getNormalBackground() {
		return normalBackground;
	}
 
	public void setNormalBackground(Color normalBackground) {
		this.normalBackground = normalBackground;
	}
 
	public Color getPressedBackground() {
		return pressedBackground;
	}
 
	public void setPressedBackground(Color pressedBackground) {
		this.pressedBackground = pressedBackground;
	}
 
	public Color getRolloverBackground() {
		return rolloverBackground;
	}
 
	public void setRolloverBackground(Color rolloverBackground) {
		this.rolloverBackground = rolloverBackground;
	}
 
	public Color getNormalBorderColor() {
		return normalBorderColor;
	}
 
	public void setNormalBorderColor(Color normalBorderColor) {
		this.normalBorderColor = normalBorderColor;
	}
 
	public Color getPressedBorderColor() {
		return pressedBorderColor;
	}
 
	public void setPressedBorderColor(Color pressedBorderColor) {
		this.pressedBorderColor = pressedBorderColor;
	}
 
	public Color getRolloverBorderColor() {
		return rolloverBorderColor;
	}
 
	public void setRolloverBorderColor(Color rolloverBorderColor) {
		this.rolloverBorderColor = rolloverBorderColor;
	}
 
	public Color getPressedForeground() {
		return pressedForeground;
	}
 
	public void setPressedForeground(Color pressedForeground) {
		this.pressedForeground = pressedForeground;
	}
 
	public Color getRolloverForeground() {
		return rolloverForeground;
	}
 
	public void setRolloverForeground(Color rolloverForeground) {
		this.rolloverForeground = rolloverForeground;
	}
 
	public Color getNormalForeground() {
		return normalForeground;
	}
 
	public void setNormalForeground(Color normalForeground) {
		this.normalForeground = normalForeground;
	}
 
	public Color getSelectedBackground() {
		return selectedBackground;
	}
 
	public void setSelectedBackground(Color selectedBackground) {
		this.selectedBackground = selectedBackground;
	}
 
	public Color getSelectedBorderColor() {
		return selectedBorderColor;
	}
 
	public void setSelectedBorderColor(Color selectedBorderColor) {
		this.selectedBorderColor = selectedBorderColor;
	}
 
	public Color getSelectedForeground() {
		return selectedForeground;
	}
 
	public void setSelectedForeground(Color selectedForeground) {
		this.selectedForeground = selectedForeground;
	}
}
