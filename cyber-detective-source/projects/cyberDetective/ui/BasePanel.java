package projects.cyberDetective.ui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Panel;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class BasePanel extends Panel implements	MouseListener {

	private static final long serialVersionUID = 1L;

	protected float scalingFactor = Geometry.SCALING_FACTOR;

	protected int minWidth = Geometry.MIN_WIDTH;

	protected int minHeight = Geometry.MIN_HEIGHT;

	protected int canvasWidth = Geometry.CANVAS_WIDTH;

	protected int canvasHeight = Geometry.CANVAS_HEIGHT;

	protected boolean autoResize = Geometry.AUTO_RESIZE;

	protected boolean doPaint = true;

	protected DrawingContext dc = new DrawingContext(Geometry.DEFAULT_DC);

	// Focus
	protected boolean windowActive = true;

	public BasePanel() {
		super();
		// Initially set preferred size
		setPreferredSize(new Dimension(canvasWidth, canvasHeight));

		// Set up background
		this.setBackground(new java.awt.Color(0xE0E0FF));
		this.setScalingFactor(this.getScalingFactor());

	}

	public void listeningEvents() {
		try {
			Thread.sleep(100);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}

		// Add mouse listener
		this.addMouseListener(this);

	}

	@Override
	public void update(Graphics g) {
		paint(g);
	}

	@Override
	public void paint(Graphics g) {
		// Only draws if doPaint is true
		if (doPaint) {
			Graphics2D g2d = (Graphics2D) g;
			predraw(g2d);
			draw(g2d);
			postdraw(g2d);
		}
	}

	public void setOrigion(int x, int y) {
		dc.X_ORIGION = x;
		dc.Y_ORIGION = y;
	}

	public float getScalingFactor() {
		return scalingFactor;
	}

	public void setScalingFactor(float scalingFactor) {
		this.scalingFactor = scalingFactor;
	}

	public DrawingContext getDc() {
		return dc;
	}

	public void setDc(DrawingContext dc) {
		this.dc = dc;
	}

	/**
	 * Setup the drawing area for drawing
	 * 
	 * @param g2d
	 */
	public void predraw(Graphics2D g2d) {
		
		
		// Set up canvas for drawing.
		g2d.scale(1.0, 1.0);
		g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
				RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g2d.setPaint(new java.awt.Color(0xFFFFD0));
		g2d.fillRect(0, 0, canvasWidth, canvasHeight);

		// Scale 
		g2d.scale(canvasWidth/scalingFactor, canvasWidth/scalingFactor);
	}

	/**
	 * Takes care of things happen after drawing. Should call base class methods
	 * if overwritten
	 * 
	 * @param g2d
	 */
	public void postdraw(Graphics2D g2d) {
	}

	/**
	 * Derived class should overwrite thie method to perform actual drawing of
	 * objects.
	 * 
	 * @param g2d
	 */
	public void draw(Graphics2D g2d) {
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public synchronized void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
