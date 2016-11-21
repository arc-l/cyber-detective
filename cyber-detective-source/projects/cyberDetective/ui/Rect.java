package projects.cyberDetective.ui;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

import projects.cyberDetective.Vertex;

public class Rect extends AbstractDrawable {
	
	public Rectangle2D rect = null;
	public Vertex vertex = null;

	public Rect(double x, double y, double width, double height, Vertex v, DrawingContext dc){
		super(dc);
		rect = new Rectangle2D.Double(x, y, width, height);
		vertex = v;
		setLineColor(Color.WHITE);
	}
	
	public static Rect createRoom(double x, double y, double width, double height, Vertex v, DrawingContext dc){
		Rect rect = new Rect(x, y, width, height, v, dc);
		rect.setFillColor(Color.LIGHT_GRAY);
		return rect;
	}

	public static Rect createOccupancySensor(double x, double y, double width, double height, Vertex v, DrawingContext dc){
		Rect rect = new Rect(x, y, width, height, v, dc);
		rect.setFillColor(Color.ORANGE);
		return rect;
	}
	
	@Override
	public void draw(Graphics2D g) {
		g.setColor(getFillColor());
		g.fillRect((int) rect.getX(), (int)rect.getY(), (int)rect.getWidth(),(int)rect.getHeight());
	}
	
	@Override
	public void drawText(Graphics2D g){
		g.setFont(new java.awt.Font("SansSerif", java.awt.Font.PLAIN, 32));
		g.setColor(Color.BLACK);
		g.drawString(vertex.name, (int)(rect.getX() + rect.getWidth()/2) - 16,(int)(rect.getY() + rect.getHeight()/2) + 16);
	}
	
}
