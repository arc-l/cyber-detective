package projects.cyberDetective.ui;


import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;

import projects.cyberDetective.Vertex;

public class LineSegment extends AbstractDrawable {

	public Line2D seg;
	public Vertex vertex = null;

	public LineSegment(Line2D seg, Vertex v, DrawingContext dc) {
		super(dc);
		this.seg = seg;
		this.vertex = v;
		this.setLineColor(Color.BLACK);
	}

	public LineSegment(int x, int y, int ex, int ey, Vertex v, DrawingContext dc) {
		this(new Line2D.Double(x, y, ex, ey), v, dc);
	}

	public LineSegment(int x, int y, int ex, int ey, DrawingContext dc) {
		this(new Line2D.Double(x, y, ex, ey), null, dc);
	}

	@Override
	public void draw(Graphics2D g) {
		g.setColor(getLineColor());
		g.drawLine((int)seg.getX1(), (int)seg.getY1(), (int) seg.getX2(), (int)seg.getY2());
	}

	@Override
	public void drawText(Graphics2D g){
		if(vertex == null) return;
		g.setFont(new java.awt.Font("SansSerif", java.awt.Font.PLAIN, 32));
		g.setColor(Color.BLACK);
		if(vertex.name.charAt(2) == 'r'){
			g.drawString(vertex.name, (int)(seg.getX1()/2 + seg.getX2()/2) + 10,(int)(seg.getY1()/2 + seg.getY2()/2) + 10);
		}
		else if(vertex.name.charAt(2) == 'l'){
			g.drawString(vertex.name, (int)(seg.getX1()/2 + seg.getX2()/2) - 60,(int)(seg.getY1()/2 + seg.getY2()/2) + 10);
		}
		else{
			g.drawString(vertex.name, (int)(seg.getX1()/2 + seg.getX2()/2) - 22,(int)(seg.getY1()/2 + seg.getY2()/2) + 10);
		}
		
	}
	
}
