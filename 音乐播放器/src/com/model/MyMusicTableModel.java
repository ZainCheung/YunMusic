package com.model;

import java.awt.Color;
import java.awt.Component;
import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

public class MyMusicTableModel extends AbstractTableModel implements TableCellRenderer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Vector data;

	public Vector titles;
	
	public int[] columnWidth = {77,376,262,350,122,116};//本地歌曲列表每列初始宽度
								//序号,标题.歌手,专辑,时长,大小
	public int getRowCount() {

		return data.size() / getColumnCount();

	}

	public int getColumnCount() {

		return titles.size();

	}

	public Object getValueAt(int rowIndex, int columnIndex) {

		return data.get((rowIndex * getColumnCount()) + columnIndex);

	}
	
	/**
	 * 设置表格内容的间隔色
	 * @param table
	 */
	public static void setColumnColor(JTable table) {
		try {
			DefaultTableCellRenderer tcr = new DefaultTableCellRenderer() {
				private static final long serialVersionUID = 1L;

				public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
						boolean hasFocus, int row, int column) {
					if (row % 2 == 0) {
						 
						setBackground(new Color(250, 250, 250));// 设置奇数行底色
					}
					else if (row % 2 == 1) {
						setBackground(new Color(245, 245, 247));// 设置偶数行底色
					}
					
					return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
				}
			};
			for (int i = 0; i < table.getColumnCount(); i++) {
				table.getColumn(table.getColumnName(i)).setCellRenderer(tcr);
			}
			tcr.setHorizontalAlignment(JLabel.LEFT);//居左对齐
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 设置选中的表格
	 */
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		Component renderer = getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		if (isSelected) {
			renderer.setBounds(null);
		}
		return renderer;
	}
	/**
	 * 设置每一列的宽度
	 * @param table
	 * @param width
	 * @return
	 */
	public  TableColumnModel getColumn(JTable table, int[] width) {
        TableColumnModel columns = table.getColumnModel();
        for (int i = 0; i < width.length; i++) {
            TableColumn column = columns.getColumn(i);
            column.setPreferredWidth(width[i]);
        }
        return columns;
    }

}
