package cell.canvas;


import java.text.DecimalFormat;
import java.util.Set;

import cell.app.GUI;
import cell.data.Cell;
import cell.data.ViewPort;
import cell.lib.CB;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;

/**
 * Infinitely scrolling canvas that ties with viewport to provide a grid that remembers cell position
 * @author Max Davatelis <theZiggurat>
 * @version Alpha 2.0
 */

public class Board extends Canvas{
	
	private double 
	pressedX, 
	pressedY, lastY, 
	lastX, translateX, 
	translateY, lastMouseX, 
	lastMouseY;
	private boolean 
	debugBar = true, 
	borderOn = true, 
	heatMap = false, 
	age = false;
	
	long totalTime = 0;

	Font onScreen;
	GraphicsContext g;
	ViewPort v;
	
	double tileRatio = 0;
	
	/**
	 * Object for the grid. May provide a secondary one for 
	 * @param v
	 */
	
	public Board(ViewPort v) {
		
		this.v = v;
		this.setWidth(v.xSize);
		this.setHeight(v.ySize);
		g = this.getGraphicsContext2D();
		
		
		//**EVENT HANDLERS**// ---------------------------------------------------
	
		 
		// draws a single cell on mouse press
		
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
		
		// draws many cells on mouse hold
		
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
		
		// zooms in and out of the canvas
		
		this.setOnScroll(e -> {
			
			if(e.getDeltaY()>0) {v.setTileSize(1);}
			if(e.getDeltaY()<0&&v.cellSize>3) {v.setTileSize(-1);}
		
			redraw(g);
		});
		redraw(g);
		
		// displays mouse coordinates in debug bar
		
		this.setOnMouseMoved(e ->{
			if(debugBar) {
			drawCoords(e.getX(), e.getY(), g);
			}
		});
	}
	
	
	
	
	// CLASS HELPER METHODS ----------------------------------------------------------------------------------
	
	/**
	 * Throws a coordinate from event or refresh to the viewport
	 * @param x,y Coord
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
		if(!heatMap){redraw(g);}
		v.grid.updateNeighborsAll();
		if(heatMap){redraw(g);}
	}
	
	/**
	 * Main rendering call for the canvas. 
	 * Handles pretty much everything on the main screen
	 * @param g Graphics Context
	 */
	
	public void redraw(GraphicsContext g) {
		long pretime = System.nanoTime();
		double size = v.getTileSize();
		g.setFont(Font.font("Helvetica",FontWeight.EXTRA_LIGHT, 13));
		g.clearRect(0, 0, v.getXsize(), v.getYsize());
		g.setTextAlign(TextAlignment.CENTER);
		
		//**BACKGROUND**// -----------------------------------------------------
		
		g.setFill(heatMap ? CB.heatMap[0]: CB.deadCell);
		g.fillRect(0, 0, v.xSize, v.ySize);
		
		//**BORDER**// ---------------------------------------------------------
		
		if(borderOn){
			g.setFill(heatMap ? CB.heatMap[1]:(borderOn ? CB.borderCell: CB.deadCell));
	        double currX = -v.getCurrX()%v.getTileSize();
	        double currY = -v.getCurrY()%v.getTileSize();
	        
	        while(currX<=v.getXsize()){
	        	g.fillRect(currX,0,1,v.getYsize());
	        	currX += v.getTileSize();
	        }
	        
	        while(currY<=v.getYsize()){
	        	g.fillRect(0,currY,v.getXsize(),1);
	        	currY += v.getTileSize();
	        }
		}
        
        //**ALIVE CELL**// -----------------------------------------------------
		
		Set<Cell> seen = v.gatherSeen();
		
		// if heat map is enabled
		
		if(heatMap){
	        for(Cell c: seen) {
	        	
	        	int heat = c.getNeighbors();
	        	g.setFill(CB.heatMap[heat]);
	        	g.fillRect((c.getX()*v.cellSize)- v.getCurrX(), (c.getY()*v.cellSize) 
	        	- v.getCurrY(), size, size);
	        	
	        }
		}
		
		// if heat map is disabled
		
		else{
	       
	        for(Cell c: seen) {

	        	if(c.getAge()!=-1){
	        		
	        		double x = (c.getX()*v.cellSize)- v.getCurrX();
	        		double y = (c.getY()*v.cellSize) - v.getCurrY();

	        		g.setFill(age ?CB.deadCell.interpolate(CB.aliveCell,((double)c.getAge())/10 +.1): CB.aliveCell);
	        		g.fillRect(x,y, size, size);
	        		
	        	}
	        }
        }
        
        //**BOTTOM BAR**//
        if(debugBar) {
        drawBottomBar(g);
        }
        
        totalTime = (System.nanoTime() - pretime)/1000000;
        System.out.println(totalTime);
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
	
	public void toggleHeat(){
		heatMap = !heatMap;
		redraw(g);
	}
	
	public void toggleAge() {
		age = !age;
		redraw(g);
	}
	
	public void toggleBorder(){
		borderOn = !borderOn;
		redraw(g);
	}
	
	public boolean isHeat() {
		return heatMap;
	}
	
	public boolean isAge() {
		return age;
	}
	
	public boolean isBorder(){
		return borderOn;
	}
	
	public void draw(){
		redraw(g);
	}
	


}