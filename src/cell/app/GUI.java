package cell.app;

import cell.data.Grid;
import cell.data.ViewPort;
import javafx.application.Application;
import javafx.beans.property.StringProperty;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.BlurType;
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
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class GUI extends Application{
	
	//top bar: 60 px    side bar: 85 px
	
	private Stage mainWindow;
	private int GAME_WIDTH = 1285, GAME_HEIGHT = 760, CELL_SIZE = 15;
	private Ruleset CURRENT_RULE = Ruleset.GNARL;
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
		mainWindow.getIcons().add(new Image("icon.png"));
		
		grid = new Grid(CURRENT_RULE);
		v = new ViewPort(CELL_SIZE, GAME_WIDTH-85, GAME_HEIGHT-60, grid);
		
	
		VBox sideMenu = initSideMenu();
		HBox topMenu = initTopMenu();
		Pane mainScreen = new Pane();
		c = new MovableCanvas(v);
		mainScreen.getChildren().add(c);
		
		InnerShadow innerShadow = new InnerShadow();
		innerShadow.setRadius(10);
		innerShadow.setOffsetY(-10);
		innerShadow.setOffsetY(-10);
		innerShadow.setBlurType(BlurType.GAUSSIAN);
	    innerShadow.setColor(CB.aliveCell);
	    mainScreen.setEffect(new InnerShadow());
		
		BorderPane wholeScreen = new BorderPane();
		
		wholeScreen.setTop(topMenu);
		wholeScreen.setLeft(sideMenu);
		wholeScreen.setCenter(mainScreen);
		
		Scene main = new Scene(wholeScreen, GAME_WIDTH, GAME_HEIGHT);
		mainWindow.setScene(main);
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
		
		
		h.setOnMouseClicked(e -> {
			if(e.getClickCount()>=2) {
				fullScreen();
			}
		});
		
		// run button config ----------------------
        run.setOnAction(e -> {
        	System.out.println("Running");
		});
        run.setImage("run.png");
        h.getChildren().add(run);
		
        
		// edit button config --------------------
		edit.setOnAction(e -> {
			if(editMap) {
				editMap = false;
			}
			else {
				editMap = true;
			}
		});
		edit.setImage("edit.png");
        h.getChildren().add(edit);
        
        
        
        // iterate button config -----------------
        iterate.setOnAction(e -> {
        	c.iterate();
		});
        iterate.setImage("iterate.png");
        h.getChildren().add(iterate);
        
        
        
        
        // color button config -------------------
        color.setOnAction(e -> {
        	CB.switchContext();
        	mainWindow.close();
        	reInit();
		});
        color.setImage("color.png");
        color.setTranslateX(GAME_WIDTH-(85*5));
        h.getChildren().add(color);
        
        
        
        // exit button config ------------------
        exit.setOnAction(e -> {
        	mainWindow.close();
		});
        exit.setImage("exit.png");
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
		
		Button heatMap = new Button("Heatmap");
		
		// exit button config ------------------
        heatMap.setOnAction(e -> {
        	c.toggleHeat();
		});
        
        v.getChildren().add(heatMap);
		v.setBackground(sideBackground);
		v.setPrefWidth(85);
		return v;
	}

	public static void setTop(double d) {
		top.setText(String.valueOf(d));
	}
	
	public void fullScreen() {
		Rectangle2D bounds = Screen.getPrimary().getVisualBounds();
		mainWindow.setMaxHeight(bounds.getHeight());
		mainWindow.setMaximized(!mainWindow.isMaximized());
	}
}


