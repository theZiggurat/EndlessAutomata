package cell.app;

import javafx.scene.paint.Color;

public class CB {
	
	public static int index = 1;
	
	// 500s
	public static Color[] topBGArr = 
		{Color.web("#F44336"), Color.web("#3F51B5"), Color.web("#607D8B"), Color.web("#616161")};
	
	// 400s
	public static Color[] topHLArr = 
		{Color.web("#E57373"), Color.web("#5C6BC0"), Color.web("#78909C"), Color.web("#757575")};
	
	// 200s
	public static Color[] sideBGArr = 
		{Color.web("#EF9A9A"), Color.web("#9FA8DA"), Color.web("#B0BEC5"), Color.web("#9E9E9E")};
	
	// 100s
	public static Color[] sideHLArr = 
		{Color.web("#FFCDD2"),Color.web("#C5CAE9"), Color.web("#CFD8DC"), Color.web("#F5F5F5")};

	// 50s
	public static Color[] deadCellArr = 
		{Color.web("#FFEBEE"),Color.web("#E8EAF6"), Color.web("#ECEFF1"), Color.web("#FAFAFA")};
	
	// 800s
	public static Color[] aliveCellArr = 
		{Color.web("#D32F2F"),Color.web("#283593"), Color.web("#37474F"), Color.web("#424242")};
	
	// 100s
	public static Color[] borderCellArr = 
		{Color.web("#FFCDD2"),Color.web("#C5CAE9"), Color.web("#CFD8DC"), Color.web("#F5F5F5")};
	
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
	
	public static Color[] heatMap = {
			Color.web("020003"),
			Color.web("330034"),
			Color.web("570054"),
			Color.web("8A0076"),
			Color.web("9C0065"),
			Color.web("E00021"),
			Color.web("FD7701"),
			Color.web("FEFF25"),
			Color.web("FFFFE9")
	};
	
	public static void switchContext() {
		index = index++;
	}
	
	public static int getColor() {
		return index;
	}
}
