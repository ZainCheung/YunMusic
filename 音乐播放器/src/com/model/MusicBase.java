package com.model;

import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.FloatControl;

public class MusicBase implements Runnable {

	// ���建������С
	private static final int BUFFER_SIZE = 64000;
	private String fileToPlay = "song//��ɫ.mp3";
	private static MusicBase musicBase;
	private FloatControl floatVoiceControl;
	// ������Ʊ���
//  private boolean pause = false;
//  private boolean stop = false;
	public static boolean threadExit = false;
	public static boolean stopped = true;
	public static boolean paused = false;
	public static boolean playing = false;
	public static Object synch = new Object();
	public int voiceValue = 0 ;//���ֵΪ6,��СֵΪ-80

	// ���岥���߳�
	private Thread playerThread = null;

	public MusicBase() {
	}

	public static MusicBase getMusicBase() {
		if (musicBase == null) {
			musicBase = new MusicBase();
		}
		return musicBase;
	}

	// ���岥�������߳�
	public void run() {
		while (!threadExit) {
			waitforSignal();
			if (!stopped)
				playMusic();
		}
	}

	// ���岥�������߳�
	public void endThread() {
		threadExit = true;
		synchronized (synch) {
			synch.notifyAll();
		}
		try {
			Thread.sleep(500);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	// �����߳�ͬ���ȴ�
	public void waitforSignal() {
		try {
			synchronized (synch) {
				synch.wait();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	// ���岥�ŷ���
	public void play() {
		if ((!stopped) || (paused))
			return;
		if (playerThread == null) {
			playerThread = new Thread(this);
			playerThread.start();
			try {
				Thread.sleep(500);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}

		synchronized (synch) {
			stopped = false;
			synch.notifyAll();
		}
	}

	public void setFileToPlay(String fname) {
		fileToPlay = fname;
	}

	public void playFile(String fname) {
		setFileToPlay(fname);
		play();
	}

	// ������Ƶ����������ʶ����Ƶ��ʽ����������������Ҫ�Ķ���
	public void playMusic() {
		byte[] audioData = new byte[BUFFER_SIZE];

		// ������Ƶ����������
		AudioInputStream ais = null;
		// ������Ƶ����������
		SourceDataLine line = null;
		// ������Ƶ��������ʽ����
		AudioFormat baseFormat = null;

		try {
			// ������Ƶ������
			// getAudioInputStream(File file) ���ṩ�� File �����Ƶ��������
			ais = AudioSystem.getAudioInputStream(new File(fileToPlay));
			// getAudioInputStream(URL url) ���ṩ�� URL �����Ƶ������
		} catch (Exception e) {
			
			//e.printStackTrace();
		}

		// ��������Ƶ�������ɹ�
		if (ais != null) {
			// getFormat()��ô���Ƶ���������������ݵ���Ƶ��ʽ��
			baseFormat = ais.getFormat();
//    	  line = getLine(baseFormat);

			// ���˸�ʽ����Ƶ���޷�����
			if (line == null) {
				// ������Ƶ����ƽ̨

				// ���벢��ʽת��
				// AudioFormat ������������ָ���ض����ݰ��ŵ��ࡣͨ���������Ƶ��ʽ�洢����Ϣ�����Է����ڶ��������������н���λ�ķ�ʽ��
				// AudioFormat ����Ӧ���ֳ��������ļ����뼼��
				/*
				 * ���캯���У�����˵���� encoding - ��Ƶ���뼼�� sampleRate - ÿ��������� sampleSizeInBits - ÿ�������е�λ��
				 * channels - �������������� 1 ���������� 2 �����ȵȣ� frameSize - ÿ֡�е��ֽ��� frameRate - ÿ���֡��
				 * bigEndian - ָʾ�Ƿ��� big-endian �ֽ�˳��洢���������е����ݣ�false ��ζ�� little-endian����
				 */

				AudioFormat decodedFormat = new AudioFormat(

						// �ֶ�encoding��ָ���˸�ʽʹ�õ���Ƶ���뼼��
						AudioFormat.Encoding.PCM_SIGNED,

						// getSampleRate() ��ȡ��������
						baseFormat.getSampleRate(),

						16, // ÿ�������е�λ��

						// getChannels() ��ȡ�ŵ���
						baseFormat.getChannels(), baseFormat.getChannels() * 2,

						baseFormat.getSampleRate(),

						false);

				// getAudioInputStream()ͨ��ת���ṩ����Ƶ�������������ָʾ�������Ƶ��������
				ais = AudioSystem.getAudioInputStream(decodedFormat, ais);

				// ��ȡ��������Ƶ��������ʽ
				line = getLine(decodedFormat);

				// ��ȡ�������Ŀؼ�
				floatVoiceControl = (FloatControl) line.getControl(FloatControl.Type.MASTER_GAIN);

				// ����minValue -80 maxValue 6
				// ����ʵĳ�ʼ����
				floatVoiceControl.setValue(voiceValue);
			}
		}

		// �����ɲ��ܲ��Ŵ˸�ʽ����Ƶ�����򷵻�
		if (line == null)
			return;

		playing = true;
		// start() ����ĳһ������ִ������ I/O
		line.start();

		int inBytes = 0;
		while ((inBytes != -1) && (!stopped) && (!threadExit)) {
			try {
				// ����Ƶ����ȡָ������������������ֽڣ����������������ֽ�������
				inBytes = ais.read(audioData, 0, BUFFER_SIZE);
			} catch (IOException e) {
				e.printStackTrace();
			}

			if (inBytes >= 0) {
				// ͨ����Դ�����н���Ƶ����д���Ƶ��
				int outBytes = line.write(audioData, 0, inBytes);
			}

			if (paused)
				waitforSignal();
		}
		// ͨ������������е��ڲ�������֮ǰ�������� I/O���ſ��������е��ж����ݡ�
		line.drain();
		// ֹͣ��
		line.stop();
		// �ر��У�ָʾ�����ͷŵĸ���ʹ�õ�����ϵͳ��Դ
		line.close();

		playing = false;
	}

	public void stop() {
		if (paused)
			return;
		stopped = true;
		waitForPlayToStop();
	}

	public void waitForPlayToStop() {
		while (playing)
			try {
				Thread.sleep(500);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
	}

	public void pause() {
		if (stopped)
			return;
		System.out.println("start paused+++++++++++" + paused);
		synchronized (synch) {
//       paused = !paused;
			synch.notifyAll();
		}
		System.out.println("end paused+++++++++++" + paused);
	}

	// ��Ƶ���ݴ�����ϣ�
	private SourceDataLine getLine(AudioFormat audioFormat) {
		// �ӿ� SourceDataLineԴ�������ǿ���д�����ݵ������С�
		// ���䵱���Ƶ����Դ��Ӧ�ó�����Ƶ�ֽ�д��Դ�����У������ɴ����ֽڻ��岢�����Ǵ��ݸ���Ƶ����
		// ��Ƶ�����Խ���Щ������ȡ������Դ���������������Ȼ�󽫸û���ﴫ�ݵ�����˿�֮���Ŀ�꣨���ɱ�ʾ�����ϵ���Ƶ����豸����
		SourceDataLine res = null;

		// �ӿ� DataLine���������صĹ�����ӵ��䳬�ӿ� Line��
		// ��Щ���ܰ���һЩ������Ʒ�������Щ��������������ֹͣ�����ĺ�ˢ��ͨ�������д������Ƶ���ݡ�
		// �����л����Ա�����ʵĵ�ǰλ�á���������Ƶ��ʽ��

		// ���˼̳����䳬�������Ϣ֮�⣬DataLine.Info ���ṩ�ض��������е�������Ϣ�������� ��������֧�ֵ���Ƶ��ʽ �����ڲ�����������С������С
		// ͨ���� DataLine.Info ���ʵ�ʵ����Ϊ�������ݵ�ĳһ�������� Mixer.getLine(Line.Info)�������Բ�ѯ�κ����͵��л�Ƶ����
		DataLine.Info info = new DataLine.Info(SourceDataLine.class, audioFormat, AudioSystem.NOT_SPECIFIED);
		try {
			res = (SourceDataLine) AudioSystem.getLine(info);

			// open(AudioFormat format) �򿪾���ָ����ʽ���У�������ʹ�л�����������ϵͳ��Դ����ÿɲ�����
			res.open(audioFormat);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}
}
