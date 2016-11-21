package projects.cyberDetective.ui;

public class Geometry {
	public static float SCALING_FACTOR = 800.0F;

	public static final int CANVAS_WIDTH = 400;

	public static final int CANVAS_HEIGHT = 300;

	public static final boolean AUTO_RESIZE = false;

	public static final int MIN_WIDTH = 200;

	public static final int MIN_HEIGHT = 150;
	
	public static final DrawingContext DEFAULT_DC = new DrawingContext((int)SCALING_FACTOR, (int)(SCALING_FACTOR * CANVAS_WIDTH/CANVAS_HEIGHT), 0, 0);

//	static {
//		Properties geomProp = FileHelper.loadUrlProfile(
//				FileHelper.getBaseUrl(), "config/geometry.properties");
//		SCALING_FACTOR = Float.parseFloat(geomProp.getProperty("scaling-factor")
//				.trim());
//		CANVAS_WIDTH = Integer.parseInt(geomProp.getProperty("canvas-width")
//				.trim());
//		CANVAS_HEIGHT = Integer.parseInt(geomProp.getProperty("canvas-height")
//				.trim());
//		CANVAS_MARGIN = Integer.parseInt(geomProp.getProperty("canvas-margin")
//				.trim());
//		AUTO_RESIZE = (Integer.parseInt(geomProp.getProperty("auto-resize")
//				.trim()) == 1);
//		MIN_WIDTH = Integer.parseInt(geomProp.getProperty("minimum-width")
//				.trim());
//		MIN_HEIGHT = Integer.parseInt(geomProp.getProperty("minimum-height")
//				.trim());
//		DEFAULT_DC = new DrawingContext((int)SCALING_FACTOR, (int)SCALING_FACTOR, 0 , 0);
//	}

}
