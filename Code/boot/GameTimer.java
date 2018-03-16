package boot;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

class GameTimer extends JPanel implements ActionListener {
	
	private Timer timer;
	private JLabel timerLabel;
	private long startTime;
	
	public GameTimer(long startTime)  {
		this.startTime = startTime;
		
		timerLabel = new JLabel(createTime());
		timerLabel.setFont(new Font("Impact", Font.PLAIN, 14));
		timerLabel.setForeground(Color.WHITE);
		timerLabel.setPreferredSize(new Dimension(75,20));
		add(timerLabel);
		setBackground(Color.BLACK);
		
		timer = new Timer(1000, (ActionListener) this);
        timer.setInitialDelay(1);
        timer.start();
	}

	public Timer getTimer() {
		return timer;
	}

	public void setTimer(Timer timer) {
		this.timer = timer;
	}

	public JLabel getTimerLabel() {
		return timerLabel;
	}

	public void setTimerLabel(JLabel timerLabel) {
		this.timerLabel = timerLabel;
	}

	public long getStartTime() {
		return startTime;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		timerLabel.setText(createTime());
	}
	
	private String createTime()  {
		StringBuilder sb = new StringBuilder("time     ");
		
		int timeElapsed = (int) ((System.currentTimeMillis() - startTime)/1000);
		
		Integer hours = timeElapsed / 3600;
		Integer remainder = timeElapsed % 3600;
		Integer minutes = remainder / 60;
		Integer seconds = remainder % 60;
		
		if(hours > 0)  {
			sb.append(hours.toString()).append(":");
		}
		sb.append(minutes.toString());
		sb.append(":");
		if(seconds < 10)  {
			sb.append("0");
		}
		sb.append(seconds.toString());
		
		return sb.toString();
	}
}
