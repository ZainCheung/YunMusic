package com.test;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.AbstractCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

import com.start.MusicDemo;

public class AddShowButton extends AbstractCellEditor implements TableCellRenderer, TableCellEditor, ActionListener {
	JTable table;
	public JButton renderButton;
	JButton editButton;
	String text = "测试按钮";//

	/**
	 * 添加下载按钮到表格里的指定列
	 * @param table
	 * @param column
	 */
	public AddShowButton(JTable table, int column) {
		super();
		this.table = table;
		renderButton = new JButton();//"按钮1"
		editButton = new JButton();//"按钮2"
		editButton.setFocusPainted(false);
		editButton.addActionListener(this);
		TableColumnModel columnModel = table.getColumnModel();
		columnModel.getColumn(column).setCellRenderer(this);
		columnModel.getColumn(column).setCellEditor(this);
		renderButton.addActionListener(this);
		renderButton.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO 自动生成的方法存根
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO 自动生成的方法存根
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO 自动生成的方法存根
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO 自动生成的方法存根
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO 自动生成的方法存根
				System.out.println(789);
				renderButton.setBackground(Color.BLACK);
			}
		});
	}

	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		
		if (row%2==0)
			renderButton.setBackground(new Color(250,250,250));
		else
			renderButton.setBackground(new Color(245,245,247));
		
		if (hasFocus) {
			renderButton.setForeground(table.getForeground());
			//renderButton.setBackground(UIManager.getColor("Button.background"));
		} else if (isSelected) {
			//renderButton.setForeground(table.getSelectionForeground());
			renderButton.setBackground(new Color(227, 227, 229));
		} else {
			renderButton.setForeground(table.getForeground());
			//renderButton.setBackground(UIManager.getColor("Button.background"));
		}
		// renderButton.setText( (value == null) ? "" : "下载" );
		renderButton.setIcon(new ImageIcon(MusicDemo.class.getResource("/begin/download.png")));
		renderButton.setFocusPainted(false);
		renderButton.setBorderPainted(false);
		//renderButton.setBackground(new Color(236,237,238));
		renderButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
		renderButton.setPreferredSize(new Dimension(30,30));
		
		return renderButton;
	}

	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
		text = (value == null) ? "" : value.toString();
		editButton.setText(text);
		return editButton;
	}

	public Object getCellEditorValue() {
		return text;
	}

	public void actionPerformed(ActionEvent e) {
		//fireEditingStopped();
		System.out.println(123);
		//System.out.println(e.getActionCommand() + " : " + table.getSelectedRow());
	}
}
