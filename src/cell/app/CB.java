package cell.app;

import javafx.scene.paint.Color;

public class CB {
	
	public static int index = 1;
	
	// 500s
	public static Color[] topBGArr = 
		{Color.web("#F44336"), Color.web("#3F51B5")};
	
	// 400s
	public static Color[] topHLArr = 
		{Color.web("#E57373"), Color.web("#5C6BC0")};
	
	// 200s
	public static Color[] sideBGArr = 
		{Color.web("#EF9A9A"), Color.web("#9FA8DA")};
	
	// 100s
	public static Color[] sideHLArr = 
		{Color.web("#FFCDD2"),Color.web("#C5CAE9")};

	// 50s
	public static Color[] deadCellArr = 
		{Color.web("#FFEBEE"),Color.web("#E8EAF6")};
	
	// 800s
	public static Color[] aliveCellArr = 
		{Color.web("#D32F2F"),Color.web("#283593")};
	
	// 100s
	public static Color[] borderCellArr = 
		{Color.web("#FFCDD2"),Color.web("#C5CAE9")};
	
	public static Color topBG = topBGArr[index];
	public static Color topHL = topHLArr[index];
	public static Color sideBG = sideBGArr[index];
	public static Color sideHL = sideHLArr[index];
	public static Color deadCell = deadCellArr[index];
	public static Color aliveCell = aliveCellArr[index];
	public static Color borderCell = borderCellArr[index];
	
	
	
	/**
	 *  1: red
	 *  2: indigo
	 *  3: dark purple
	 *  4: 
	 *
	 */
	
	
	
	public static void switchContext() {
		index = index++;
	}
	
	public static int getColor() {
		return index;
	}
}
