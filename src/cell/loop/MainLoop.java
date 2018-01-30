package cell.loop;

import cell.thread.CanvasThread;
import javafx.animation.AnimationTimer;

public class MainLoop extends AnimationTimer{
	
	CanvasThread ct = new CanvasThread();

	@Override
	public void handle(long arg0) {
		
		ct.call();
	}
	
	

}
