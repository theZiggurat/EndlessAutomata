package cell.app;



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
import javafx.scene.effect.DropShadow;
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
	static Label top = new Label("");
	static boolean editMap = false;
	
	public static void main(String[] args) {launch();}

	@Override
	public void start(Stage primaryStage) throws Exception {
		mainWindow = primaryStage;
		mainWindow.setTitle("Big Dick");
		
		grid = new Grid(CURRENT_RULE);
		v = new ViewPort(CELL_SIZE, GAME_WIDTH-75, GAME_HEIGHT-50, grid);
		
	
		VBox sideMenu = initSideMenu();
		HBox topMenu = initTopMenu();
		Pane mainScreen = new Pane();
		Canvas c = new MovableCanvas(v);
		mainScreen.getChildren().add(c);
		
		BorderPane wholeScreen = new BorderPane();
		
		wholeScreen.setTop(topMenu);
		wholeScreen.setLeft(sideMenu);
		wholeScreen.setCenter(mainScreen);
		
		
		Scene main = new Scene(wholeScreen, GAME_WIDTH, GAME_HEIGHT);
		mainWindow.setScene(main);
		mainWindow.setResizable(false);
		//mainWindow.initStyle(StageStyle.UNIFIED);
		mainWindow.show();
	}
	
	
	Border topBorder = new Border(new BorderStroke(Color.web("#757575"), 
			BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(0, 0, 10,0)));
	Background topBackground = new Background(new BackgroundFill(Color.web("#424242"),null,null));
	
	
	
	
	
	private HBox initTopMenu() {
		
		top = new Label("0");
		HBox h = new HBox();
		
		Button edit = new Button("Edit");
		edit.setOnAction(e -> {
			if(editMap) {
				editMap = false;
			}
			else {
				editMap = true;
			}
		});
        h.getChildren().add(edit);
		
		
	
		h.getChildren().add(top);
		h.setBorder(topBorder);
		h.setBackground(topBackground);
		h.setPrefHeight(60);
		return h;
	}
	
	//Border sideBorder = new 
	Background sideBackground = new Background(new BackgroundFill(Color.web("#757575"),null,null));

	private VBox initSideMenu() {
		VBox v = new VBox();
		v.setBorder(topBorder);
		v.setBackground(sideBackground);
		v.setPrefWidth(85);
		return v;
	}

	public static void setTop(double d) {
		top.setText(String.valueOf(d));
	}

	
	

}
