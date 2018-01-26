package cell.app;

import java.util.ArrayList;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.text.TextAlignment;

public class SelectorCanvas extends Canvas{
	
	ArrayList<Integer> values;
	
	public SelectorCanvas(ArrayList<Integer> values){
		this.values = values;
		this.setWidth(20);
		this.setHeight(20*8);
		draw(this.getGraphicsContext2D());
		
		this.setOnMouseClicked(e -> {
			int selected = (int)e.getY()/20;
			if(values.contains(selected)) {values.remove(values.indexOf(selected));}
			else {values.add(selected);}
			draw(this.getGraphicsContext2D());
		});
	}
	
	public void draw(GraphicsContext g) {
		g.setFill(CB.sideHL);
		g.fillRect(0, 0, 20, 20*8);
		g.setTextAlign(TextAlignment.CENTER);
		
		g.setFill(CB.topHL);
		for(int i: values) {
			g.fillRect(0, i*20, 20, 20);
		}
		
		for(int i = 0; i<9; i++) {
			g.setFill(CB.sideBG);
			g.fillRect(0, 20*(i+1), 20, 1);
			
			g.setFill(CB.deadCell);
			g.fillText(Integer.toString(i+1), 10, (i*20)+15);
		}
		
		g.setStroke(CB.aliveCell);
		g.strokeRect(0, 0, 20, 160);
	}
	
	public void switchRule(ArrayList<Integer> updated) {
		values = new ArrayList<Integer>();
		values.addAll(updated);
		draw(this.getGraphicsContext2D());
	}
	
	
	

}
