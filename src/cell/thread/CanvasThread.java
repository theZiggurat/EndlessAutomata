package cell.thread;

import java.util.concurrent.Callable;

import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class CanvasThread implements Callable<Image>{
	
	private Canvas write;
	private GraphicsContext g;
	private WritableImage i;
	private SnapshotParameters param;

	@Override
	public Image call() throws Exception {
		write = new Canvas();
		g = write.getGraphicsContext2D();
		
		draw(g);
		
		param = new SnapshotParameters();
		param.setFill(Color.TRANSPARENT);
		
		i = write.snapshot(param, null);
		return i;
	}
	
	public void draw(GraphicsContext g) {
		
	}

}
