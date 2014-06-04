package sc.plugin2015.gui.renderer.primitives;


/**
 * @author soeren
 * 
 */

public class GuiConstants {

	
	
	//not needed
	/*public static final float GUI_BOARD_START_X = 1;
	public static final float GUI_BOARD_START_Y = 1;*/
	/**
	 * Breite des GuiBoard 
	 * mit parent.width zu multiplizieren
	 */
	public static final float GUI_BOARD_WIDHT = 0.8f;
	/**
	 * Höhe des GuiBoard
	 * zu parent.height zu addieren
	 */
	public static final float GUI_BOARD_HEIGHT = -30;
	
	/**
	 * startwert der x-Koordinate der Sidebar
	 * mit parent.width zu multiplizieren
	 */
	public static final float SIDE_BAR_START_X = 0.8f;
	/**
	 * startwert der y-Koordinate der Sidebar
	 * zu 0 zu addieren, ist 0
	 * 
	 */
	public static final float SIDE_BAR_START_Y = 0;
	/**
	 * Breite der Sidebar
	 * mit parent.width zu multiplizieren
	 */
	public static final float SIDE_BAR_WIDTH = 0.2f;
	/**
	 * Höhe der Sidebar
	 * mit parent.heigth zu multiplizieren
	 */
	public static final float SIDE_BAR_HEIGHT = 0.875f;
	
	/**
	 * startwert der x-Koordinate der Progressbar
	 * zu 0 zu addieren
	 */
	public static final float PROGRESS_BAR_START_X = 50;
	/**
	 * startwert der y-Koordinate der Progressbar
	 * zu parent.height zu addieren
	 */
	public static final float PROGRESS_BAR_START_Y = -30;
	/**
	 * Breite der Progressbar
	 * zu parent.width zu addieren
	 */
	public static final float PROGRESS_BAR_WIDTH = -60;
	/**
	 * Höhe der Progressbar
	 * zu 0 zu addieren
	 */
	public static final float PROGRESS_BAR_HEIGHT= 20;
	
	/**
	 * Sourcepath to find the default blue Penguin picture
	 */
	public static final String BLUE_PENGUIN_IMAGE = "resource/game/TuxBlau.png";

	/**
	 * Sourcepath to find the default red Penguin picture
	 */
	public static final String RED_PENGUIN_IMAGE = "resource/game/TuxRot.png";
	
}
