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
	
	double tileRatio = 0;
	
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
		
		setOnMouseReleased(e -> {
			mediaPlayer.stop();
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
			
			double oldX = e.getX();
			double oldY = e.getY();
			double dx, dy;
			double oldXCoord = v.getCurrX();
			double oldYCoord = v.getCurrY();
			tileRatio = (double) v.cellSize;
			
			if(e.getDeltaY()>0) {v.setTileSize(1);}
			if(e.getDeltaY()<0&&v.cellSize>7) {v.setTileSize(-1);}
			
			tileRatio = tileRatio/(double)v.getTileSize();
			dx = -(tileRatio-1) * oldX;
			dy = -(tileRatio-1) * oldY;
			
			v.translate(oldXCoord +dx,oldYCoord+dy);
			redraw(g);
		});
		redraw(g);
		
		this.setOnMouseMoved(e ->{
			drawCoords(e.getX(), e.getY(), g);
		});
	}
	
	public void drawCoords(double x, double y, GraphicsContext g) {
		g.clearRect(0, v.getYsize()-45, 140,45);
		g.setFill(CB.topHL);
		g.fillRect(0, v.getYsize()-45, 140,45);
		g.setFill(CB.deadCell);
		g.setFont(new Font("Sergoe UI", 13));
		g.fillText("Mouse: {"+x + ","+ y + "}", 10, v.getYsize()-27.5);
		lastMouseX = x;
		lastMouseY = y;
	}
	
	double lastMouseX = 0, lastMouseY = 0;
	
	
	
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
        Set<double[]> points =  v.gatherTuples();
        for(double [] point: points) {
        	g.fillRect(point[0]- v.getCurrX(), point[1] - v.getCurrY(), v.cellSize-1, v.cellSize-1);
        }
        
        //**ALIVE CELL**//
        Set<Cell> seen = v.gatherSeen();
        g.setFill(CB.aliveCell);
        for(Cell c: seen) {
        	g.fillRect((c.getX()*v.cellSize)- v.getCurrX(), (c.getY()*v.cellSize) - v.getCurrY(), v.cellSize-1, v.cellSize-1);
        }
        
        g.fillRect(0, 0, 5, 5);
        
        //**BOTTOM BAR**//
        
        g.setFill(CB.topHL);
		g.fillRect(0, v.getYsize()-45, v.getXsize(),45);
		
		drawCoords(lastMouseX, lastMouseY, g);
		
		
        
        
        g.setFill(Color.BLACK);
        g.setFont(new Font("Sergoe UI", 13));
        g.fillText(String.valueOf(v.startX/v.cellSize)+"--"+String.valueOf((v.startX+v.xSize)/v.cellSize), v.xSize-50, v.ySize-45); 
        g.fillText(String.valueOf(v.startY/v.cellSize)+"--"+String.valueOf((v.startY+v.ySize)/v.cellSize), v.xSize-50, v.ySize-30);
        g.fillText(String.valueOf(v.startX)+"--"+String.valueOf((v.startX+v.xSize)), v.xSize-150, v.ySize-45); 
        g.fillText(String.valueOf(v.startY)+"--"+String.valueOf((v.startY+v.ySize)), v.xSize-150, v.ySize-30);
        g.fillText(String.valueOf(v.cellSize), 100, 100);
        g.fillText(String.valueOf(tileRatio), 150, 100);
	}
	
	public void iterate() {
		v.iterate();
		redraw(g);
	}
	

}