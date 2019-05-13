package com.plugin;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class TranslateTool {
	
	private String result = null;//���
	private String source = null;//Դ����ҳ��JSON
	private boolean error = false;//����״̬

	public String getResult() {
		return result;
	}
	public String getSource() {
		return source;
	}
	public boolean isError() {
		return error;
	}
	public TranslateTool(String string) {
		try {

            URL url = new URL("http://fanyi.youdao.com/openapi.do");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.addRequestProperty("encoding", "UTF-8");
            //�汾��1.1������ʽ��get�����뷽ʽ��utf-8
            connection.setDoInput(true);
            connection.setDoOutput(true);

            connection.setRequestMethod("POST");

            OutputStream os = connection.getOutputStream();
            OutputStreamWriter osw = new OutputStreamWriter(os);
            BufferedWriter bw = new BufferedWriter(osw);
            
           
           
            String text = "keyfrom=fadabvaa&key=522071532&type=data&doctype=json&version=1.1&q=" + string;
//            ����˵����
//
//            keyfrom=�ܳ�ID   key=����
//            
//           ��type - ���ؽ�������ͣ��̶�Ϊdata
//
//           ��doctype - ���ؽ�������ݸ�ʽ��xml��json��jsonp
//
//           ��version - �汾����ǰ���°汾Ϊ1.1
//
//           ��q - Ҫ������ı���������UTF-8���룬�ַ����Ȳ��ܳ���200���ַ�����Ҫ����urlencode����
//
//           ��only - ��ѡ������dict��ʾֻ��ȡ�ʵ����ݣ�translate��ʾֻ��ȡ�������ݣ�Ĭ��Ϊ����ȡ
//            errorCode��
//
//           ��0 - ����
//
//           ��20 - Ҫ������ı�����
//
//           ��30 - �޷�������Ч�ķ���
//
//           ��40 - ��֧�ֵ���������
//
//           ��50 - ��Ч��key
//
//           ��60 - �޴ʵ��������ڻ�ȡ�ʵ�����Ч
            
            bw.write(text);
            bw.flush();

            InputStream is = connection.getInputStream();
            InputStreamReader isr = new InputStreamReader(is, "UTF-8");
            BufferedReader br = new BufferedReader(isr);

            String line = null;
            StringBuilder builder = new StringBuilder();
            
            while ((line = br.readLine()) != null) {
            	source=line;
                String[] arr = line.split("]");
                for (int i = 0; i < arr.length; i++) {
                    if (arr[i].contains("translation")) {
                    	//��ȡ��ѯ���
                    	result=arr[i].substring(17, arr[i].length()-1);
                        //System.out.println(result);
                    }
                    
                    
                }
                if (result==null) {
					//System.out.println("��ѯ�޹�!");
					error=true;
				}
                if (string==null) {
					error=true;
				}
                
            }
            

            bw.close();
            osw.close();
            os.close();

            br.close();
            isr.close();
            is.close();

        } 
//        catch (MalformedURLException e) {
//            e.printStackTrace();
//        	System.err.println("�����Ѿ��Ͽ�");
//        }
        catch (IOException e) {
        	//System.err.println("����Ͽ�");
        	error=true;
        }
	}
	public static void main(String[] args) {
		TranslateTool tool =new TranslateTool("null");
		System.out.println(tool.result);
		System.out.println(tool.source);
		System.out.println(tool.error);
	}
}
