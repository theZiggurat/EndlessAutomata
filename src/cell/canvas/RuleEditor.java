package cell.canvas;

import java.util.ArrayList;

import cell.lib.CB;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

public class RuleEditor extends Canvas{
	
	private static int WIDTH = 30;
	private static int BOX_HEIGHT = 25;
	
	ArrayList<Integer> values;
	
	public RuleEditor(ArrayList<Integer> values){
		this.values = values;
		this.setWidth(WIDTH);
		this.setHeight(BOX_HEIGHT*8);
		draw(this.getGraphicsContext2D());
		
		this.setOnMouseClicked(e -> {
			int selected = (int)e.getY()/BOX_HEIGHT;
			System.out.println(selected);
			if(values.contains(selected)) {values.remove(values.indexOf(selected));System.out.println("REmoved");}
			else {values.add(selected);}
			
			draw(this.getGraphicsContext2D());
		});
	}
	
	public void draw(GraphicsContext g) {
		
		// main box
		
		g.setFill(CB.sideHL);
		g.fillRect(0, 0, WIDTH, BOX_HEIGHT*8);
		
		// text configurations
		
		g.setTextAlign(TextAlignment.CENTER);
		g.setFont(new Font(12));
		
		// selected boxes
		
		g.setFill(CB.topHL);
		for(int i: values) {
			g.fillRect(0, i*BOX_HEIGHT, WIDTH, BOX_HEIGHT);
		}
		
		// box borders
		
		for(int i = 0; i<9; i++) {
			g.setFill(CB.sideBG);
			g.fillRect(0, BOX_HEIGHT*(i+1), WIDTH, 1);
			
			g.setFill(CB.deadCell);
			g.fillText(Integer.toString(i+1), WIDTH/2, (i*BOX_HEIGHT)+17);
		}
		
		// outside stroke
		
		g.setStroke(CB.aliveCell);
		g.strokeRect(0, 0, WIDTH, BOX_HEIGHT*8);
	}
	
	public void switchRule(ArrayList<Integer> updated) {
		values = new ArrayList<Integer>();
		values.addAll(updated);
		draw(this.getGraphicsContext2D());
	}
	
	
	

}
