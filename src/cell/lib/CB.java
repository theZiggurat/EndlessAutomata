package cell.lib;

import javafx.scene.paint.Color;

/**
 * Color library using Google's palette 
 * https://material.io/guidelines/style/color.html#color-color-palette
 * @author Max Davatelis <theZiggurat>
 *
 * This class isnt very useful but in the future when I figure out how to hot-swap 
 * backgrounds in runtime, it will be convienent.
 * Dark-blue looks the best
 *
 */
public class CB {
	
	/**
	 * 0 : Red
	 * 1 : Dark-blue
	 * 2 : Blue-gray
	 * 3 : Gray
	 */
	public static int index = 1;
	
	/**
	 * The following numbers follow the color code guidelines in google's palette
	 * To add a new color, simply add the corresponding colors under each number from the site
	 * pasted above. Some tweaking may be required.
	 */
	
	// 500s
	public static final Color[] topBGArr = 
		{Color.web("#F44336"), Color.web("#3F51B5"), Color.web("#607D8B"), Color.web("#616161")};
	
	// 400s
	public static final Color[] topHLArr = 
		{Color.web("#E57373"), Color.web("#5C6BC0"), Color.web("#78909C"), Color.web("#757575")};
	
	// 200s
	public static final Color[] sideBGArr = 
		{Color.web("#EF9A9A"), Color.web("#9FA8DA"), Color.web("#B0BEC5"), Color.web("#9E9E9E")};
	
	// 100s
	public static final Color[] sideHLArr = 
		{Color.web("#FFCDD2"),Color.web("#C5CAE9"), Color.web("#CFD8DC"), Color.web("#F5F5F5")};

	// 50s
	public static final Color[] deadCellArr = 
		{Color.web("#FFEBEE"),Color.web("#E8EAF6"), Color.web("#ECEFF1"), Color.web("#FAFAFA")};
	
	// 800s
	public static final Color[] aliveCellArr = 
		{Color.web("#D32F2F"),Color.web("#283593"), Color.web("#37474F"), Color.web("#424242")};
	
	// 100s
	public static final Color[] borderCellArr = 
		{Color.web("#FFCDD2"),Color.web("#C5CAE9"), Color.web("#CFD8DC"), Color.web("#F5F5F5")};
	
	// colors to be called from other classes
	
	public static Color topBG = topBGArr[index];
	public static Color topHL = topHLArr[index];
	public static Color sideBG = sideBGArr[index];
	public static Color sideHL = sideHLArr[index];
	public static Color deadCell = deadCellArr[index];
	public static Color aliveCell = aliveCellArr[index];
	public static Color borderCell = borderCellArr[index];
	
	/**
	 * Color codes for heatmap taken from google images of a FLIR
	 */
	
	public static final Color[] heatMap = {
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
}
