package com.ui;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
 
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JScrollBar;
import javax.swing.plaf.basic.BasicScrollBarUI;
 
//�Զ��������UI
public class DemoScrollBarUI extends BasicScrollBarUI {
 
    @Override
    protected void configureScrollBarColors() {
 
        // ����
 
        // thumbColor = Color.GRAY;
 
        // thumbHighlightColor = Color.BLUE;
 
        // thumbDarkShadowColor = Color.BLACK;
 
        // thumbLightShadowColor = Color.YELLOW;
 
        // ����
 
        trackColor = Color.black;
 
        setThumbBounds(0, 0, 3, 10);
 
        // trackHighlightColor = Color.GREEN;
 
    }
 
    /**
     * ���ù������Ŀ��
     */
 
    @Override
    public Dimension getPreferredSize(JComponent c) {
 
        // �������ù������Ŀ��
 
        c.setPreferredSize(new Dimension(13, 0));
 
        return super.getPreferredSize(c);
 
    }
 
 
    // �ػ滬��Ļ������򱳾�
 
    public void paintTrack(Graphics g, JComponent c, Rectangle trackBounds) {
 
        Graphics2D g2 = (Graphics2D) g;
 
        GradientPaint gp = null;
 
        //�жϹ������Ǵ�ֱ�� ����ˮƽ��
 
        if (this.scrollbar.getOrientation() == JScrollBar.VERTICAL) {
 
            //���û���
 
            gp = new GradientPaint(0, 0, new Color(250, 250, 250),
 
                    trackBounds.width, 0, new Color(250, 250, 250));
 
        }//������ĸ���ɫ�ǹ���������ɫ,��ʼΪColor(80, 80, 80)
        //���ڶ���ΪColor(250, 250, 250)
 
        if (this.scrollbar.getOrientation() == JScrollBar.HORIZONTAL) {
 
            gp = new GradientPaint(0, 0, new Color(250, 250, 250),
 
                    trackBounds.height, 0, new Color(250, 250, 250));
 
        }
 
 
        g2.setPaint(gp);
 
        //���Track
 
        g2.fillRect(trackBounds.x, trackBounds.y, trackBounds.width,
 
                trackBounds.height);
 
        //����Track�ı߿�
        /*       g2.setColor(new Color(175, 155, 95));
         g2.drawRect(trackBounds.x, trackBounds.y, trackBounds.width - 1,
                trackBounds.height - 1);
                */
 
        if (trackHighlight == BasicScrollBarUI.DECREASE_HIGHLIGHT)
 
            this.paintDecreaseHighlight(g);
 
        if (trackHighlight == BasicScrollBarUI.INCREASE_HIGHLIGHT)
 
            this.paintIncreaseHighlight(g);
 
    }
 
 
    @Override
    protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {
 
        // �ѻ�������x��y�����궨��Ϊ����ϵ��ԭ��
 
        // ���һ��һ��Ҫ���ϰ�����Ȼ�϶���ʧЧ��
 
        g.translate(thumbBounds.x, thumbBounds.y);
 
        // ���ð�����ɫ,���ǹ����İ��ֳ�ʼֵ��Color( 230,230,250)
 
        g.setColor(new Color( 128,128,125));//225,225,226
        
		
 
        // ��һ��Բ�Ǿ���
 
        // ������ǰ�ĸ������Ͳ��ི�ˣ�����Ϳ��
 
        // ������������Ҫע��һ�£����������ƽ����Բ�ǻ���
 
        // g.drawRoundRect(0, 0, 5, thumbBounds.height - 1, 5, 5);
        
        g.drawRoundRect(0, 0, 13, thumbBounds.height - 1, 25, 25);
 
        // �������
 
        Graphics2D g2 = (Graphics2D) g;
 
        RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING,
 
                RenderingHints.VALUE_ANTIALIAS_ON);
 
        g2.addRenderingHints(rh);
 
        // ��͸��
 
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
 
                0.2f));//0.5f
 
        // ���������ɫ�����������˽��䣬��������
 
        // g2.setPaint(new GradientPaint(c.getWidth() / 2, 1, Color.GRAY,
 
        // c.getWidth() / 2, c.getHeight(), Color.GRAY));
 
        // ���Բ�Ǿ���
 
        g2.fillRoundRect(0, 0, 40, thumbBounds.height - 1, 5, 5);
 
    }
 
 
    /**
     * �����������Ϸ��İ�ť
     */
 
    @Override
 
    protected JButton createIncreaseButton(int orientation) {
 
        JButton button = new JButton();
 
        button.setBorderPainted(false);
 
        button.setContentAreaFilled(false);
 
        button.setBorder(null);
 
        return button;
 
    }
 
    /**
     * �����������·��İ�ť
     */
 
    @Override
 
    protected JButton createDecreaseButton(int orientation) {
 
        JButton button = new JButton();
 
        button.setBorderPainted(false);
 
        button.setContentAreaFilled(false);
 
        button.setFocusable(false);
 
        button.setBorder(null);
 
        return button;
 
    }
 
}

