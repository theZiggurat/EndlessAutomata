package cell.app;



import javax.swing.text.html.ImageView;

import cell.data.Grid;
import cell.data.ViewPort;
import javafx.application.Application;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.Bloom;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.InnerShadow;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class GUI extends Application{
	
	//top bar: 60 px    side bar: 85 px
	
	private Stage mainWindow, exitWindow;
	private int GAME_WIDTH = 1285, GAME_HEIGHT = 760, CELL_SIZE = 15;
	private Ruleset CURRENT_RULE = Ruleset.CONWAYS_GAME_OF_LIFE;
	Grid grid;
	StringProperty sizeX;
	ViewPort v;
	MovableCanvas c;
	static Label top = new Label("");
	static boolean editMap = false;
	
	public static long TARGET_FPS = 60;
	
	public static void main(String[] args) {launch();}

	@Override
	public void start(Stage primaryStage) throws Exception {
		mainWindow = primaryStage;
		initialize(mainWindow);
	}
	
	public void initialize(Stage mainWindow) {
		mainWindow.setTitle("Endless Automata");
		mainWindow.getIcons().add(new Image("file:icon.png"));
		
		grid = new Grid(CURRENT_RULE);
		v = new ViewPort(CELL_SIZE, GAME_WIDTH-75, GAME_HEIGHT-50, grid);
		
	
		VBox sideMenu = initSideMenu();
		HBox topMenu = initTopMenu();
		Pane mainScreen = new Pane();
		c = new MovableCanvas(v);
		mainScreen.getChildren().add(c);
		
		InnerShadow innerShadow = new InnerShadow();
		innerShadow.setRadius(30);
		innerShadow.setOffsetY(-10);
		innerShadow.setBlurType(BlurType.GAUSSIAN);
	    innerShadow.setColor(Color.BLACK);
	    mainScreen.setEffect(new InnerShadow());
		
		BorderPane wholeScreen = new BorderPane();
		
		wholeScreen.setTop(topMenu);
		wholeScreen.setLeft(sideMenu);
		wholeScreen.setCenter(mainScreen);
		
		/*wholeScreen.setBorder(new Border(new BorderStroke(CB.topHL, 
				BorderStrokeStyle.SOLID, new CornerRadii(0), new BorderWidths(10, 3, 3,3))));*/
		
		
		Scene main = new Scene(wholeScreen, GAME_WIDTH, GAME_HEIGHT);
		mainWindow.setScene(main);
		mainWindow.setResizable(true);
		mainWindow.initStyle(StageStyle.TRANSPARENT);
		mainWindow.show();
		
	}
	
	public void reInit() {
		initialize(mainWindow);
	}
	
	
	static Border topBorder1 = new Border(new BorderStroke(CB.topHL, 
			BorderStrokeStyle.SOLID, new CornerRadii(0), new BorderWidths(0, 0, 10,0)), new BorderStroke(CB.aliveCell, 
					BorderStrokeStyle.SOLID, new CornerRadii(0), new BorderWidths(10, 0, 0,0)));
	static Background topBackground = new Background(new BackgroundFill(CB.topBG,null,null));
	
	
	
	double lastClickedX, lastClickedY, lastPosX, lastPosY;
	boolean firstSample = true;
	
	private HBox initTopMenu() {
		HBox h = new HBox();
		
		topButton run = new topButton("Run", true, false);
		topButton edit = new topButton("Edit", true, false);
		topButton iterate = new topButton("Iterate", false, false);
		topButton exit = new topButton("Exit", false, true);
		topButton color = new topButton("Color", false, true);
		
		h.setOnMouseDragged(e -> {
			
			if(firstSample) {
				lastClickedX = -e.getSceneX();
				lastClickedY = -e.getSceneY();
				firstSample = false;
			}
			mainWindow.setX(lastClickedX + e.getScreenX());
			mainWindow.setY(lastClickedY + e.getScreenY());
		});		
		
		h.setOnMouseReleased(e -> {
			firstSample = true;
		});
		
		// run button config
        run.setOnAction(e -> {
        	System.out.println("Running");
		});
        run.setImage("file:run.png");
        h.getChildren().add(run);
		
		// edit button config
		edit.setOnAction(e -> {
			if(editMap) {
				editMap = false;
			}
			else {
				editMap = true;
			}
		});
		edit.setImage("file:edit.png");
        h.getChildren().add(edit);
        
        // iterate button config
        iterate.setOnAction(e -> {
        	c.iterate();
		});
        iterate.setImage("file:iterate.png");
        h.getChildren().add(iterate);
        
        
        // exit button config
        /*color.setOnAction(e -> {
        	CB.switchContext();
        	mainWindow.close();
        	reInit();
		});*/
        color.setImage("file:color.png");
        color.setTranslateX(GAME_WIDTH-(85*5));
        h.getChildren().add(color);
        
        // exit button config
        exit.setOnAction(e -> {
        	mainWindow.close();
		});
        exit.setImage("file:exit.png");
        exit.setTranslateX(GAME_WIDTH-(85*5));
        h.getChildren().add(exit);
		
		
	
		h.getChildren().add(top);
		h.setBorder(topBorder1);
		h.setBackground(topBackground);
		h.setPrefHeight(70);
		return h;
	}
	
	//Border sideBorder = new 
	Background sideBackground = new Background(new BackgroundFill(CB.sideBG,null,null));
	

	private VBox initSideMenu() {
		VBox v = new VBox();
		//v.setBorder(topBorder);
		v.setBackground(sideBackground);
		v.setPrefWidth(85);
		return v;
	}

	public static void setTop(double d) {
		top.setText(String.valueOf(d));
	}
}


