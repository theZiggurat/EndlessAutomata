package cell.canvas;

import java.util.List;

import cell.app.GUI;
import cell.lib.CB;
import cell.lib.Ruleset;
import javafx.scene.canvas.*;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

public class ScrollableCanvas extends Canvas{
	
	List<Ruleset> Rules; 
	double maxSize = Ruleset.allRules.size()*20;
	double currPos = 0;
	int selected = 0;
	
	public ScrollableCanvas(List <Ruleset> Rules) {
		this.Rules = Rules;
		this.setWidth(75);
		this.setHeight(200);
		redraw(this.getGraphicsContext2D());
		
		this.setOnScroll(e ->{
			currPos -= e.getDeltaY();
			if(currPos<0) {currPos = 0;}
			if(currPos>maxSize-200) {currPos=maxSize-200;}
			redraw(this.getGraphicsContext2D());
		});
		
		
		this.setOnMouseClicked(e ->{
			selected = (int)(e.getY()+ currPos)/20;
			GUI.changeRule(Ruleset.allRules.get(selected));
			redraw(this.getGraphicsContext2D());
		});
	}
	
	public void redraw(GraphicsContext g) {
		g.setFill(CB.sideHL);
		g.fillRect(0, 0, 75, 200);
		
		g.setStroke(CB.aliveCell);
		g.strokeRect(0, 0, 75, 200);
		
		double counter = 0;
		g.setTextAlign(TextAlignment.CENTER);
		g.setFont(new Font(10));
		
		g.setFill(CB.topHL);
		g.fillRect(0, selected*20 - currPos, 75, 20);
		g.setFill(CB.deadCell);
		g.fillText(Ruleset.allRules.get(selected).name, 37.5, (selected*20)+15 - currPos, 75);
		
		
		// adding blocks and names
		
		for(Ruleset r: Ruleset.allRules) {
			g.setFill(CB.sideBG);
			g.fillRect(0, counter*20-currPos, 75, 1);
			g.setFill(CB.topHL);
			if(counter != selected) {
				g.fillText(r.name, 37.5, (counter*20)+15-currPos, 75);
			}
			counter++;
		}
		
		// scroll bar
		
		//g.setFill(CB.topBG);
		//g.fillRect(73, (currPos/maxSize)*200, 2, (10/(double)Ruleset.allRules.size())*200);
		
		
	}

}
