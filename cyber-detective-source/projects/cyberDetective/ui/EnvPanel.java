package projects.cyberDetective.ui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

public class EnvPanel extends BasePanel {

	private static final long serialVersionUID = 5048182358859494135L;

	public Environment env = null;
	
	public EnvPanel(Environment env){
		super();
		this.env = env;
	}

	@Override
	public void draw(Graphics2D g) {
		env.paint(g);
	}

	public Graphics2D setupGraphics() {
		Graphics2D g = (Graphics2D) getGraphics();
		g.setFont(new java.awt.Font("SansSerif", java.awt.Font.PLAIN, (int)(20 * scalingFactor/1000.f)));
		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
				RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g.setStroke(new BasicStroke(scalingFactor / canvasWidth));
		return g;
	}

	public void drawText(Graphics2D g, Point2D point) {
		g.setXORMode(Color.gray);
		if (point != null) {
			g.drawString("" + ((int) point.getX() - dc.X_ORIGION) + "," + ((int) point.getY() + dc.Y_ORIGION),
					(int) point.getX() + 5, ((int) (scalingFactor - point
							.getY()) - 5));
		}
	}

	public void drawPoly(Graphics2D g, Line2D[] poly, boolean printPoints) {
		if (poly.length <= 0)
			return;

		if (printPoints) {
			int[] xPoints = new int[poly.length];
			int[] yPoints = new int[poly.length];
			for (int i = 0; i < poly.length; i++) {
				xPoints[i] = (int) poly[i].getX1();
				yPoints[i] = (int) (scalingFactor - poly[i].getY1());
			}

			for (int i = 0; i < poly.length; i++) {
				System.out.print("" + xPoints[i] + ",");
			}
			System.out.println();
			for (int i = 0; i < poly.length; i++) {
				System.out.print("" + yPoints[i] + ",");
			}
			System.out.println();
		}

	}

}
