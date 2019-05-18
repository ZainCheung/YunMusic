package com.tool;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.logging.Logger;
 
/**
 * Created by Administrator on 2018/6/26 0026.
 * ϵͳ������
 */
public class SystemUtils {
 
    private static final Logger logger = Logger.getGlobal();
 
    /**
     * ���Ƶ���ϵͳ����
     * <p/>
     * Լ����Ӧ�ø�Ŀ¼�µ� temp Ŀ¼�з���3��vbs�ļ�
     * volumeMute.vbs�����ھ���
     * volumeAdd.vbs����������
     * volumeMinus.vbs����С����
     * �ļ��Լ��ļ������ݲ��� Java ���붯̬���ɣ�������ʱ���½�������ʱ��ֱ�ӵ���
     *
     * @param type 0������/ȡ������    1����������  2����С����
     */
    public static void controlSystemVolume(String type) {
        try {
            if (type == null || "".equals(type.trim())) {
                logger.info("type ����Ϊ��,�����в���...");
            }
            /**tempFile��vbs �ļ�
             * vbsMessage��vbs �ļ�������*/
            String vbsMessage = "";
            File tempFile = null;
            Runtime runtime = Runtime.getRuntime();
            switch (type) {
                case "0":
                    tempFile = new File("temp", "volumeMute.vbs");
                    vbsMessage = !tempFile.exists() ? "CreateObject(\"Wscript.Shell\").Sendkeys \"��\"" : "";
                    break;
                case "1":
                    tempFile = new File("temp", "volumeAdd.vbs");
                    vbsMessage = !tempFile.exists() ? "CreateObject(\"Wscript.Shell\").Sendkeys \"��\"" : "";
                    break;
                case "2":
                    tempFile = new File("temp", "volumeMinus.vbs");
                    vbsMessage = !tempFile.exists() ? "CreateObject(\"Wscript.Shell\").Sendkeys \"��\"" : "";
                    break;
                default:
                    return;
            }
            /**
             * ��3��vbs�ļ�������ʱ���򴴽����ǣ�Ӧ��Ĭ�ϱ���Ϊ utf-8 ʱ�������� vbs �ű�����ʱ����
             * ����ʹ�� OutputStreamWriter �� vbs �ļ�����ĳ�gbd��������
             */
            if (!tempFile.exists() && !vbsMessage.equals("")) {
                if (!tempFile.getParentFile().exists()) {
                    tempFile.getParentFile().mkdirs();
                }
                tempFile.createNewFile();
                FileOutputStream fileOutputStream = new FileOutputStream(tempFile);
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream, "GBK");
                outputStreamWriter.write(vbsMessage);
                outputStreamWriter.flush();
                outputStreamWriter.close();
                logger.info("vbs �ļ������ڣ��½��ɹ���" + tempFile.getAbsolutePath());
            }
            runtime.exec("wscript " + tempFile.getAbsolutePath()).waitFor();
            logger.info("�����������.");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
 
    public static void main(String[] args) throws InterruptedException {
        logger.info("1 ���ʼ����.");
        Thread.sleep(1000);
        controlSystemVolume("0");
 
        logger.info("1 ���ʼȡ������.");
        Thread.sleep(1000);
        controlSystemVolume("0");
 
        logger.info("1 ���ʼ���� 2 ������������ʹ��ѭ����������������.");
        Thread.sleep(1000);
        controlSystemVolume("1");
 
        logger.info("1 ���ʼ��С����������ʹ��ѭ��������С.");
        Thread.sleep(1000);
        for (int i = 0; i < 30; i++) {
            controlSystemVolume("2");
            Thread.sleep(500);
        }
    }
}