package cell.app;


import java.io.File;
import java.util.Set;

import cell.data.Cell;
import cell.data.ViewPort;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class MovableCanvas extends Canvas{
	
	ViewPort v;
	
	double pressedX;
	double pressedY;
	double lastX;
	double lastY;
	boolean clicked;
	double translateX;
	double translateY;
	
	Media sound = new Media(new File("click_04.wav").toURI().toString());
	MediaPlayer mediaPlayer = new MediaPlayer(sound);

	Font onScreen;
	GraphicsContext g;
	
	public MovableCanvas(ViewPort v) {
		this.v = v;
		
		this.setWidth(v.xSize);
		this.setHeight(v.ySize);
		g = this.getGraphicsContext2D();
		
		
		//**EVENT HANDLERS**//
		
		setOnMousePressed(e ->{
			
			pressedX = e.getX();
			pressedY = e.getY();
			lastX = v.getCurrX();
			lastY = v.getCurrY();
			
			if(GUI.editMap) {
			int [] wow = v.returnTuple(pressedX, pressedY);
			mediaPlayer.play();
			
			put(wow);
			redraw(g);
			}
		});
		
		setOnMouseDragged(e ->{
			if(GUI.editMap) {
				int [] wow = v.returnTuple(e.getX(), e.getY());
				System.out.println(wow[0] + " " + wow[1]);
				lastX = v.getCurrX(); lastY = v.getCurrY();
				put(wow);
				redraw(g);
			}
			else{
				translateX = (e.getX() - pressedX);
				translateY = (e.getY() - pressedY);
				v.translate(lastX - translateX, lastY - translateY);
			}
			e.consume();
			redraw(g);
		});
		
		this.setOnScroll(e -> {
			
			double newX = e.getX();
			double newY = e.getY();
			double oldXCoord = v.getCurrX();
			double oldYCoord = v.getCurrY();
			double tileRatio = (double) v.cellSize;
			
			if(e.getDeltaY()>0) {v.setTileSize(1);}
			if(e.getDeltaY()<0&&v.cellSize>7) {v.setTileSize(-1);}
			
			tileRatio = ((double)v.cellSize/tileRatio);
			newX*=1-tileRatio;
			newY*=1-tileRatio;
			
			v.translate(oldXCoord + newX,oldYCoord +  newY);
			redraw(g);
		});
		redraw(g);
	}
	
	
	
	
	
	public void put(int [] wow) {
		if(!v.containsKey(wow)) {
			v.addCell(wow);
			//v.grid.get(wow[0]+" "+wow[1]).fullDebug();
		}
	}
	
	
	public void redraw(GraphicsContext g) {
		g.clearRect(0, 0, 1200, 700);
		
		//**BORDER**//
		g.setFill(CB.borderCell);
		g.fillRect(0, 0, v.xSize, v.ySize);
		
		//**DEAD CELL**//
		g.setFill(CB.deadCell);
        Set<int[]> points =  v.gatherTuples();
        for(int [] point: points) {
        	g.fillRect(point[0]- v.getCurrX(), point[1] - v.getCurrY(), v.cellSize-1, v.cellSize-1);
        }
        
        //**ALIVE CELL**//
        Set<Cell> seen = v.gatherSeen();
        g.setFill(CB.aliveCell);
        for(Cell c: seen) {
        	g.fillRect((c.getX()*v.cellSize)- v.getCurrX(), (c.getY()*v.cellSize) - v.getCurrY(), v.cellSize-1, v.cellSize-1);
        }
        
        //**LOCATION**//
        g.setFill(Color.BLACK);
        g.setFont(new Font("Terminal", 13));
        g.fillText(String.valueOf(v.startX/v.cellSize)+"--"+String.valueOf((v.startX+v.xSize)/v.cellSize), v.xSize-50, v.ySize-30); 
        g.fillText(String.valueOf(v.startY/v.cellSize)+"--"+String.valueOf((v.startY+v.ySize)/v.cellSize), v.xSize-50, v.ySize-15);
        g.fillText(String.valueOf(v.cellSize), 100, 100);
	}
	
	public void iterate() {
		v.iterate();
		redraw(g);
	}
	

}