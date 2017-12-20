package cell.app;


import java.text.DecimalFormat;
import java.util.Set;

import cell.data.Cell;
import cell.data.ViewPort;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;

/**
 * 
 * @author Max Davatelis <theZiggurat>
 * @version 1.0
 */

public class MovableCanvas extends Canvas{
	
	
	
	double pressedX, pressedY, lastY, lastX, translateX, translateY, lastMouseX, lastMouseY;
	boolean clicked, debugBar = true;

	Font onScreen;
	GraphicsContext g;
	ViewPort v;
	
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
				if(throwCoord(pressedX, pressedY)) {
					redraw(g);
				}
			}
		});
		
		setOnMouseReleased(e -> {
		});
		
		setOnMouseDragged(e ->{
			if(GUI.editMap) {
				lastMouseX = e.getX();
				lastMouseY = e.getY();
				if(GUI.editMap) {
					if(throwCoord(e.getX(), e.getY())) {
						redraw(g);
					}
				}
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
			if(debugBar) {
			drawCoords(e.getX(), e.getY(), g);
			}
		});
	}
	
	
	
	
	// CLASS HELPER METHODS ----------------------------------------------------------------------------------
	
	/**
	 * Throws a coordinate from event or refresh to the viewport
	 * @param wow Coord
	 * @return Whether or not the cell was added
	 */
	public boolean throwCoord(double x, double y) {
		double [] tuple = {x,y};
		return v.catchCoord(tuple);
	}
	
	
	/**
	 * Method call from GUI or event to step forward one time unit in simulation
	 */
	public void iterate() {
		v.iterate();
		redraw(g);
	}
	
	
	/**
	 * Refreshes Canvas size to new constraints and updates viewport in the process
	 */
	public void resize(double w, double h) {
		// TODO
	}
	
	
	// RENDER CANVAS ----------------------------------------------------------------------------------
	
	public void redraw(GraphicsContext g) {
		g.setFont(Font.font("Helvetica",FontWeight.EXTRA_LIGHT, 13));
		g.clearRect(0, 0, v.getXsize(), v.getYsize());
		g.setTextAlign(TextAlignment.CENTER);
		
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
        	g.fillRect((c.getX()*v.cellSize)- v.getCurrX(), (c.getY()*v.cellSize) 
        	- v.getCurrY(), v.cellSize-1, v.cellSize-1);
        }
        
        //**BOTTOM BAR**//
        if(debugBar) {
        drawBottomBar(g);
        }
       
	}
	
	public void drawBottomBar(GraphicsContext g) {
		g.setFill(CB.topHL);
		g.fillRect(0, v.getYsize()-35, v.getXsize(),35);
		
		drawCoords(lastMouseX, lastMouseY, g);
		drawXRange(g);
		drawYRange(g);
	}
	
	public void drawXRange(GraphicsContext g) {
		
		g.setFill(CB.sideBG);
		g.fillRect(v.xSize-170, v.getYsize()-35, 1, 35);
		
		g.setFill(CB.sideHL);
		//g.rect(v.xSize-359, v.getYsize()-45 , 179, 45);
		
		g.fillText("["+new DecimalFormat("#").format(v.startX/v.cellSize)+"-"+
				new DecimalFormat("#").format((v.startX+v.xSize)/v.cellSize)+"]", v.xSize-130, v.getYsize()-19); 
	}
	
	public void drawYRange(GraphicsContext g) {
		g.setFill(CB.sideBG);
		g.fillRect(v.xSize-90, v.getYsize()-35, 1, 35);
		
		g.setFill(CB.sideHL);
		g.fillText("["+new DecimalFormat("#").format(v.startY/v.cellSize)+"-"+
				new DecimalFormat("#").format((v.startY+v.ySize)/v.cellSize)+"]", v.xSize-50, v.getYsize()-19); 
	}
	
	public void drawCoords(double x, double y, GraphicsContext g) {
		g.clearRect(0, v.getYsize()-33, 140,45);
		g.setFill(CB.topHL);
		g.fillRect(0, v.getYsize()-35, 140,45);
		g.setFill(CB.sideHL);
		
		
		g.fillText("Mouse: <"+x + ", "+ y + ">", 71.5, v.getYsize()-18);
		g.setFill(CB.sideBG);
		g.fillRect(140, v.getYsize()-35, 1, 35);
		
		lastMouseX = x;
		lastMouseY = y;
	}
	


}