package cell.app;


import cell.canvas.Board;
import cell.canvas.ScrollableCanvas;
import cell.canvas.SelectorCanvas;
import cell.data.Grid;
import cell.data.ViewPort;
import cell.lib.CB;
import cell.lib.Ruleset;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
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
import javafx.scene.text.Font;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

// TODO: 
//  Grid button
//	Rule swticher/editor
//	Proper zoom
//	Stats page
//  Peek button
//  Save stage
//  Load stage (maybe in the form of a snapshot feature)
//	Better editing tools (blueprint or click&drag for square)
//  Maybe actually getting the run to work w/ FPS counter
//  Resizing implemented
//  Wall drawing
//
//..2D automata scene with render configurations
//  More expansive rulesets
//	Mobile port
//  

public class GUI extends Application{
	
	//top bar: 60 px    side bar: 85 px
	
	private Stage mainWindow;
	private int GAME_WIDTH = 1285, GAME_HEIGHT = 760, CELL_SIZE = 15;
	private static Ruleset CURRENT_RULE = Ruleset.CONWAYS_GAME_OF_LIFE;
	private static SelectorCanvas s1;
	private static SelectorCanvas s2;
	Grid grid;
	ViewPort v;
	Board c;
	static Label top = new Label("");
	public static boolean editMap = false;
	boolean isRunning = false;
	
	static int RATE_DEFAULT = 6;
	
	Timeline t;
	KeyFrame k;
	Duration d = Duration.millis(500);
	
	Slider timeSlider;
	
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
		c = new Board(v);
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
		
		k = new KeyFrame(Duration.millis(1000), e ->{
			v.iterate();
			c.draw();
			
		});
		
		
		t = new Timeline();
		t.setCycleCount(Timeline.INDEFINITE);
		t.getKeyFrames().add(k);
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
		topButton clear = new topButton("Clear", false, true);
		topButton border = new topButton("Border", true, false);
		
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
        	if(isRunning){
        		t.stop();
        		isRunning = false;
        	}
        	else{
        		t.play();
        		isRunning = true;
        	}
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
        
        topButton heatMap = new topButton("Heatmap", true, false);
        topButton age = new topButton("Age", true, false);
		
		// heat button config ------------------
        heatMap.setOnAction(e -> {
        	if(c.isAge()) {
        		c.toggleAge();
        		age.popOutDef();
        	}
        	c.toggleHeat();
		});
        heatMap.setImage("heatMap.png");
        h.getChildren().add(heatMap);
        
        
        
        
        // age button config --------------------
        age.setOnAction(e ->{
        	if(c.isHeat()) {
        		c.toggleHeat();
        		heatMap.popOutDef();
        	}
        	c.toggleAge();
        });
        //age.setImage("age.png");
        h.getChildren().add(age);
        
        // border button config ------------------
        border.setOnAction(e -> {
        	c.toggleBorder();
        });
        h.getChildren().add(border);
       
        
        
        // color button config -------------------
        clear.setOnAction(e -> {
        	grid.clearAll();
        	c.redraw(c.getGraphicsContext2D());
		});
        clear.setImage("clear.png");
        clear.setTranslateX(GAME_WIDTH-(85*8));
        h.getChildren().add(clear);
        
        
        
        // exit button config ------------------
        exit.setOnAction(e -> {
        	mainWindow.close();
		});
        exit.setImage("exit.png");
        exit.setTranslateX(GAME_WIDTH-(85*8));
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
		
		
        
        Label l1 = new Label("Ruleset");
        l1.setTranslateX(25);
        l1.setTranslateY(5);
        l1.setFont(new Font("Segoe UI", 10));
    	l1.setTextFill(CB.deadCell);
    	
    	Label l2 = new Label("Configuration");
        l2.setTranslateX(10);
        l2.setTranslateY(12);
        l2.setPadding(new Insets(3));
        l2.setFont(new Font("Segoe UI", 10));
    	l2.setTextFill(CB.deadCell);
    	
    	Label l3 = new Label("Simulation Speed");
        l3.setTranslateX(25);
        l3.setTranslateY(5);
        l3.setFont(new Font("Segoe UI", 10));
    	l3.setTextFill(CB.deadCell);
    	
        ScrollableCanvas c = new ScrollableCanvas(Ruleset.allRules);
        c.setTranslateX(5);
        c.setTranslateY(10);
        
        HBox selector = new HBox();
        VBox buttons = new VBox();
        buttons.setPrefWidth(25);
        buttons.setMinWidth(25);
        
        Button save = new Button("Save");
        Button reset = new Button("Reset");
        
        buttons.setTranslateX(10);
        
        save.resize(25, 30);
        reset.resize(25, 30);
        
        save.setBackground(sideBackground);
        reset.setBackground(sideBackground);
        
        
        
        
        reset.setTranslateY(15);
        
        buttons.getChildren().addAll(save, reset);
        
        s1 = new SelectorCanvas(CURRENT_RULE.survives);
        s2 = new SelectorCanvas(CURRENT_RULE.born);
        s2.setTranslateX(5);
   
        selector.getChildren().addAll(s1,s2, buttons);
        selector.setPadding(new Insets(15, 5, 5, 5));
        
        Label rate = new Label(String.valueOf(RATE_DEFAULT));
        rate.setTranslateX(25);
        rate.setTranslateY(5);
        rate.setFont(new Font("Segoe UI", 10));
    	rate.setTextFill(CB.deadCell);
        
        timeSlider = new Slider();
        timeSlider.setMin(1);
        timeSlider.setMax(20);
        timeSlider.setValue(5);
        timeSlider.setMinorTickCount(1);
        timeSlider.setBlockIncrement(1);
        timeSlider.valueProperty().addListener(new ChangeListener<Number>(){

			@Override
			public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
				changeDuration(arg2.intValue());
				timeSlider.setValue(arg2.intValue());
				rate.setText(String.valueOf(arg2.intValue()));
			}
        	
        });
        
        
        
        v.getChildren().addAll(l1, c, l2, selector, l3, timeSlider, rate);
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
	
	public static void changeRule(Ruleset r) {
		CURRENT_RULE = r;
		Grid.changeRules(CURRENT_RULE);
		s1.switchRule(r.survives);
		s2.switchRule(r.born);
	}
	
	public void changeDuration(int updates){
		t.setRate(updates);
	}
}


