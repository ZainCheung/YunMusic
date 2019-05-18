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

	// 定义缓冲区大小
	private static final int BUFFER_SIZE = 64000;
	private String fileToPlay = "song//绿色.mp3";
	private static MusicBase musicBase;
	private FloatControl floatVoiceControl;
	// 定义控制变量
//  private boolean pause = false;
//  private boolean stop = false;
	public static boolean threadExit = false;
	public static boolean stopped = true;
	public static boolean paused = false;
	public static boolean playing = false;
	public static Object synch = new Object();
	public int voiceValue = 0 ;//最大值为6,最小值为-80

	// 定义播放线程
	private Thread playerThread = null;

	public MusicBase() {
	}

	public static MusicBase getMusicBase() {
		if (musicBase == null) {
			musicBase = new MusicBase();
		}
		return musicBase;
	}

	// 定义播放声音线程
	public void run() {
		while (!threadExit) {
			waitforSignal();
			if (!stopped)
				playMusic();
		}
	}

	// 定义播放声音线程
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

	// 定义线程同步等待
	public void waitforSignal() {
		try {
			synchronized (synch) {
				synch.wait();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	// 定义播放方法
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

	// 加载音频数据流，并识别音频格式，创建播放声音需要的对象
	public void playMusic() {
		byte[] audioData = new byte[BUFFER_SIZE];

		// 定义音频数据流对象
		AudioInputStream ais = null;
		// 定义音频数据流变量
		SourceDataLine line = null;
		// 定义音频数据流格式对象
		AudioFormat baseFormat = null;

		try {
			// 加载音频数据流
			// getAudioInputStream(File file) 从提供的 File 获得音频输入流。
			ais = AudioSystem.getAudioInputStream(new File(fileToPlay));
			// getAudioInputStream(URL url) 从提供的 URL 获得音频输入流
		} catch (Exception e) {
			
			//e.printStackTrace();
		}

		// 若加载音频输入流成功
		if (ais != null) {
			// getFormat()获得此音频输入流中声音数据的音频格式。
			baseFormat = ais.getFormat();
//    	  line = getLine(baseFormat);

			// 若此格式的音频流无法播放
			if (line == null) {
				// 创建音频处理平台

				// 解码并格式转换
				// AudioFormat 是在声音流中指定特定数据安排的类。通过检查以音频格式存储的信息，可以发现在二进制声音数据中解释位的方式。
				// AudioFormat 类适应多种常见声音文件编码技术
				/*
				 * 构造函数中，参数说明： encoding - 音频编码技术 sampleRate - 每秒的样本数 sampleSizeInBits - 每个样本中的位数
				 * channels - 声道数（单声道 1 个，立体声 2 个，等等） frameSize - 每帧中的字节数 frameRate - 每秒的帧数
				 * bigEndian - 指示是否以 big-endian 字节顺序存储单个样本中的数据（false 意味着 little-endian）。
				 */

				AudioFormat decodedFormat = new AudioFormat(

						// 字段encoding，指出此格式使用的音频编码技术
						AudioFormat.Encoding.PCM_SIGNED,

						// getSampleRate() 获取样本速率
						baseFormat.getSampleRate(),

						16, // 每个样本中的位数

						// getChannels() 获取信道数
						baseFormat.getChannels(), baseFormat.getChannels() * 2,

						baseFormat.getSampleRate(),

						false);

				// getAudioInputStream()通过转换提供的音频输入流，获得所指示编码的音频输入流。
				ais = AudioSystem.getAudioInputStream(decodedFormat, ais);

				// 获取解码后和音频数据流格式
				line = getLine(decodedFormat);

				// 获取总音量的控件
				floatVoiceControl = (FloatControl) line.getControl(FloatControl.Type.MASTER_GAIN);

				// 音量minValue -80 maxValue 6
				// 设合适的初始音量
				floatVoiceControl.setValue(voiceValue);
			}
		}

		// 若依旧不能播放此格式的音频流，则返回
		if (line == null)
			return;

		playing = true;
		// start() 允许某一数据行执行数据 I/O
		line.start();

		int inBytes = 0;
		while ((inBytes != -1) && (!stopped) && (!threadExit)) {
			try {
				// 从音频流读取指定的最大数量的数据字节，并将其放入给定的字节数组中
				inBytes = ais.read(audioData, 0, BUFFER_SIZE);
			} catch (IOException e) {
				e.printStackTrace();
			}

			if (inBytes >= 0) {
				// 通过此源数据行将音频数据写入混频器
				int outBytes = line.write(audioData, 0, inBytes);
			}

			if (paused)
				waitforSignal();
		}
		// 通过在清空数据行的内部缓冲区之前继续数据 I/O，排空数据行中的列队数据。
		line.drain();
		// 停止行
		line.stop();
		// 关闭行，指示可以释放的该行使用的所有系统资源
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

	// 音频数据处理（混合）
	private SourceDataLine getLine(AudioFormat audioFormat) {
		// 接口 SourceDataLine源数据行是可以写入数据的数据行。
		// 它充当其混频器的源。应用程序将音频字节写入源数据行，这样可处理字节缓冲并将它们传递给混频器。
		// 混频器可以将这些样本与取自其他源的样本混合起来，然后将该混合物传递到输出端口之类的目标（它可表示声卡上的音频输出设备）。
		SourceDataLine res = null;

		// 接口 DataLine将与介质相关的功能添加到其超接口 Line。
		// 这些功能包括一些传输控制方法，这些方法可以启动、停止、消耗和刷新通过数据行传入的音频数据。
		// 数据行还可以报告介质的当前位置、音量和音频格式。

		// 除了继承自其超类的类信息之外，DataLine.Info 还提供特定于数据行的其他信息，包括： 受数据行支持的音频格式 、其内部缓冲区的最小和最大大小
		// 通过将 DataLine.Info 的适当实例作为参数传递到某一方法（如 Mixer.getLine(Line.Info)），可以查询任何类型的行混频器。
		DataLine.Info info = new DataLine.Info(SourceDataLine.class, audioFormat, AudioSystem.NOT_SPECIFIED);
		try {
			res = (SourceDataLine) AudioSystem.getLine(info);

			// open(AudioFormat format) 打开具有指定格式的行，这样可使行获得所有所需的系统资源并变得可操作。
			res.open(audioFormat);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}
}
