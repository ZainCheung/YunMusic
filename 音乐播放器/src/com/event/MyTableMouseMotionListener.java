package com.event;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.JTable;

/**
 * 这个类用来设置表格跟随鼠标变色
 * @author Jack
 *
 */
public 	class MyTableMouseMotionListener extends MouseMotionAdapter {

	private int rowUnderMouse = -1;

	public void mouseMoved(MouseEvent e) {

		JTable table = (JTable) e.getSource();

		Point p = table.getMousePosition();

		if (p != null) {

			rowUnderMouse = table.rowAtPoint(p);

			if (rowUnderMouse >= 0) {

				for (int i = 0; i < table.getColumnCount(); i++) {

					table.prepareRenderer(table.getCellRenderer(rowUnderMouse, i), rowUnderMouse, i);

					if (rowUnderMouse != 0) {

						table.prepareRenderer(table.getCellRenderer(rowUnderMouse - 1, i), rowUnderMouse - 1, i);

					}

					if (rowUnderMouse != table.getRowCount() - 1) {

						table.prepareRenderer(table.getCellRenderer(rowUnderMouse + 1, i), rowUnderMouse + 1, i);

					}

				}

				table.repaint(table.getVisibleRect());

			}

		}

	}

}
