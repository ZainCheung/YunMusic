package com.event;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ImageIcon;

import com.view.SliderPanel;

public class TimePlayActionTest implements ActionListener {

	SliderPanel sliderPlanel = SliderPanel.newsliderPanel;

	@Override
	public void actionPerformed(ActionEvent e) {

		if (sliderPlanel.paused == true) {// 如果处于暂停状态
			sliderPlanel.pouseButton.setIcon(new ImageIcon(SliderPanel.class.getResource("/click/player.png")));
			sliderPlanel.timer = new Timer();
			sliderPlanel.task = new TimerTask() {
				@Override
				public void run() {
					sliderPlanel.nowTimeLabel.setText(sliderPlanel.getFormattime(sliderPlanel.getCount()));
					sliderPlanel.getTimeSlider().setValue(sliderPlanel.getCount());
					sliderPlanel.setCount((sliderPlanel.getCount() + 1));
					if (sliderPlanel.getCount() == sliderPlanel.getTimeLenth()) {// ||!paused
						sliderPlanel.timer.cancel();//
						sliderPlanel.getTimeSlider().setValue(0);
						sliderPlanel.setCount(1);
						sliderPlanel.nowTimeLabel.setText("00:00");// getFormattime(count)
					}
				}
			};
			sliderPlanel.timer.schedule(sliderPlanel.task, 1000L, 1000L);
		} else {
			sliderPlanel.pouseButton.setIcon(new ImageIcon(SliderPanel.class.getResource("/begin/player.png")));
			sliderPlanel.timer.cancel();
			sliderPlanel.getTimeSlider().setValue(0);
			sliderPlanel.setCount(1);
			sliderPlanel.nowTimeLabel.setText("00:00");// getFormattime(count)
		}
		sliderPlanel.paused = !sliderPlanel.paused;
	}

}
