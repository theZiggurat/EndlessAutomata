package cell.app;

import java.io.InputStream;

import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;



public class topButton extends Button {
	
	private Border topButtonDef = new Border(new BorderStroke(CB.topHL, 
			BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(0,1,0,0)));
	private Border topButtonPressed = new Border(new BorderStroke(CB.topHL, 
			BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(0,1,5,0)));
	static Background hover = new Background(new BackgroundFill(CB.aliveCell,null,null));
	
	private Border leftButtonDef = new Border(new BorderStroke(CB.topHL, 
			BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(0,0,0,1)));
	private Border leftButtonPressed = new Border(new BorderStroke(CB.topHL, 
			BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(0,0,5,1)));
	
	private Button topButton;
	Boolean push = false;
	
	topButton(String label, boolean toggle, boolean left){
		this.topButton = new Button();
		setText(label);
		setBackground(null);
    	setBorder(left? leftButtonDef:topButtonDef);
    	setPrefHeight(50);
    	setPrefWidth(85);
    	setFont(new Font("Segoe UI", 9));
    	setTextFill(Color.web("#ECEFF1"));
    	
    	setOnMousePressed(e -> {if(!toggle) {pushIn(left ? leftButtonPressed:topButtonPressed);}
    	else{if(push) {popOut(left? leftButtonDef:topButtonDef);}else{pushIn(left ? leftButtonPressed:topButtonPressed);}}});
    	setOnMouseClicked(e -> {if(!toggle) {popOut(left? leftButtonDef:topButtonDef);}});
    	setOnMouseEntered(e ->{entered();});
    	setOnMouseExited(e ->{exited();});
	}
	
	public void pushIn(Border b) {
		setBorder(b);
		setBackground(hover);
		push = true;
	}
	
	public void popOut(Border b) {
		setBorder(b);
		setBackground(Background.EMPTY);
		push = false;
	}
	
	public void entered() {
		setBackground(hover);
	}
	
	public void exited() {
		if(!push) {setBackground(Background.EMPTY);}
	}

	public void setImage(String string) {
		Image i = new Image(string);
		setGraphic(new ImageView(i));
		setContentDisplay(ContentDisplay.TOP);
	}
	
	
	
	
	

}



