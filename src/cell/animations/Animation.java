package cell.animations;

import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

import cell.app.GUI;
import javafx.scene.canvas.GraphicsContext;


public class Animation extends Timer {

	long duration;

	long CurrTime;
	long fractionTime;
	long y = 0;
	long range; 
	
	// initiate animation with time controls
	
	public void setSettings(long duration, long range, long y) {
		this.duration = duration;
		this.CurrTime = 0;
		this.fractionTime = 0;
		this.range = range;
		this.y = y;
	}

	public void run() {
		
		
		scheduleAtFixedRate(new TimerTask() {

			@Override
			public void run() {
				
				CurrTime+= GUI.TARGET_FPS;
				fractionTime = CurrTime/duration;
				y = range*fractionTime;
			
			}
			
		}, duration, GUI.TARGET_FPS);
	}

}
