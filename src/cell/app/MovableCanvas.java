package cell.app;


import java.io.File;
import java.util.Set;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Duration;

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
	
	Color background = Color.GRAY;
	Color alive = Color.CORNFLOWERBLUE;
	Color dead = Color.WHITE;
	Font onScreen;
	
	public MovableCanvas(ViewPort v) {
		this.v = v;
		
		this.setWidth(v.xSize);
		this.setHeight(v.ySize);
		GraphicsContext g = this.getGraphicsContext2D();
		
		
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
				int [] wow = v.returnTuple(pressedX, pressedY);
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
			if(e.getDeltaY()>0) {v.setTileSize(1);}
			if(e.getDeltaY()<0&&v.cellSize>10) {v.setTileSize(-1);}
			redraw(g);
		});
		redraw(g);
	}
	
	
	
	
	
	public void put(int [] wow) {
		if(!v.grid.ContainsKey(wow)) {
			Cell c = new Cell(wow);
			c.survive();
			v.grid.put(wow[0]+" "+wow[1], c);
			//v.grid.get(wow[0]+" "+wow[1]).fullDebug();
		}
	}
	
	
	public void redraw(GraphicsContext g) {
		g.clearRect(0, 0, 1200, 700);
		
		//**BORDER**//
		g.setFill(background);
		g.fillRect(0, 0, v.xSize, v.ySize);
		
		//**DEAD CELL**//
		g.setFill(dead);
        Set<int[]> points =  v.gatherTuples();
        for(int [] point: points) {
        	g.fillRect(point[0]- v.getCurrX(), point[1] - v.getCurrY(), v.cellSize-1, v.cellSize-1);
        }
        
        //**ALIVE CELL**//
        Set<Cell> seen = v.gatherSeen();
        g.setFill(alive);
        for(Cell c: seen) {
        	g.fillRect((c.getX()*v.cellSize)- v.getCurrX(), (c.getY()*v.cellSize) - v.getCurrY(), v.cellSize-1, v.cellSize-1);
        }
        
        //**LOCATION**//
        g.setFill(Color.BLACK);
        g.setFont(new Font("Terminal", 13));
        g.fillText(String.valueOf(v.startX/v.cellSize)+"--"+String.valueOf((v.startX+v.xSize)/v.cellSize), v.xSize-50, v.ySize-30); 
        g.fillText(String.valueOf(v.startY/v.cellSize)+"--"+String.valueOf((v.startY+v.ySize)/v.cellSize), v.xSize-50, v.ySize-15);
        
	}
	
	public void changeGridColor(Color c) {
		
	}
	
	public void changeDeadColor(Color c) {
		
	}
	
	public void changeAliveColor(Color c) {
		
	}
	

}
