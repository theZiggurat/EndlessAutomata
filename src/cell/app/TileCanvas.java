package cell.app;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

public class TileCanvas extends Canvas{
	
	GraphicsContext g;
	
	public TileCanvas() {
		this.setWidth(75);
		this.setHeight(100);
		g = this.getGraphicsContext2D();
		draw(g);
	}
	
	public void draw(GraphicsContext g){
		g.setFill(CB.deadCell);
		g.fillRect(0, 0, 75, 100);
		
		g.setFill(CB.sideBG);
		g.fillRect(25, 0, 1, 100); g.fillRect(50, 0, 1, 100);
		g.fillRect(0, 25, 75, 1); g.fillRect(0, 50, 75, 1); g.fillRect(0, 75, 75, 1);
		
		g.setStroke(CB.aliveCell);
		g.strokeRect(0, 0, 75, 100);
		
		
	}
	

}
