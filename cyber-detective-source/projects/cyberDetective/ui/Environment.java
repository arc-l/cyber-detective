package projects.cyberDetective.ui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import projects.cyberDetective.DetectiveGame;
import projects.cyberDetective.Vertex;

public class Environment {

	public DetectiveGame game = null;
	public DrawingContext dc = Geometry.DEFAULT_DC;
	public Rectangle2D boundingRect = null;
	public Vector<LineSegment> walls = new Vector<LineSegment>();
	public Map<Integer, Rect> vertexIdRoomMap = new HashMap<Integer, Rect>(); 
	public Map<Integer, LineSegment> vertexIdBDMap = new HashMap<Integer, LineSegment>(); 
	public Map<Integer, Rect> vertexIdOCMap = new HashMap<Integer, Rect>();
	
	public LineSegment[] wallA = null;
	public LineSegment[] beamA = null;
	public Rect[] roomA = null;
	public Rect[] occuA = null;
	public Rectangle2D[] beamRect = null;
	
	private void createBeamDetector(String name, int x, int y, int ex, int ey, DetectiveGame game){
		Vertex v = game.graph.vertexNameMap.get(name);
		LineSegment b = new LineSegment(x, y, ex, ey, v, dc);
		b.setLineColor(Color.RED);
		vertexIdBDMap.put(v.id, b);
	}
	
	private void createOccupancySensor(String name, int x, int y, int width, int height, DetectiveGame game){
		Vertex v = game.graph.vertexNameMap.get(name);
		Rect o = Rect.createOccupancySensor(x, y, width, height, v, dc);
		vertexIdOCMap.put(v.id, o);
	}
	
	private void createRoom(String name, int x, int y, int width, int height, DetectiveGame game){
		Vertex v = game.graph.vertexNameMap.get(name);
		Rect o = Rect.createRoom(x, y, width, height, v, dc);
		vertexIdRoomMap.put(v.id, o);
	}
	
	public void paint(Graphics2D g){

		g.setStroke(new BasicStroke(15));
		for(int i = 0; i < beamA.length; i ++){
			beamA[i].paint(g);
		}

		for(int i = 0; i < occuA.length; i ++){
			occuA[i].paint(g);
		}

		for(int i = 0; i < roomA.length; i ++){
			roomA[i].paint(g);
		}

		g.setStroke(new BasicStroke(20));
		g.setColor(Color.black);
		g.drawRect((int)boundingRect.getX(), (int)boundingRect.getY(), 
				(int)boundingRect.getWidth(), (int)boundingRect.getHeight());
		
		g.setStroke(new BasicStroke(8));
		for(int i = 0; i < wallA.length; i ++){
			wallA[i].paint(g);
		}
		
	}

	public void initialize(){
		beamA =  vertexIdBDMap.values().toArray(new LineSegment[0]);
		occuA =  vertexIdOCMap.values().toArray(new Rect[0]);
		roomA =  vertexIdRoomMap.values().toArray(new Rect[0]);
		wallA = walls.toArray(new LineSegment[0]);
		
		// Make beam a bit wider for mouse test
		beamRect = new Rectangle2D[beamA.length];
		for(int i = 0; i < beamRect.length; i ++){
			if(beamA[i].seg.getX1() == beamA[i].seg.getX2()){
				beamRect[i] = new Rectangle2D.Double(beamA[i].seg.getX1() - 8, beamA[i].seg.getY1(), 16, beamA[i].seg.getY2() - beamA[i].seg.getY1());
			}
			else{
				beamRect[i] = new Rectangle2D.Double(beamA[i].seg.getX1(), beamA[i].seg.getY1() - 8, beamA[i].seg.getX2() - beamA[i].seg.getX1(), 16);
			}
		}
	}
	
	public Vertex getClickedVertex(int x, int y){
		for(int i = 0; i < occuA.length; i++){
			if(occuA[i].rect.contains(x,y))
				return occuA[i].vertex;
		}
			
		for(int i = 0; i < roomA.length; i++){
			if(roomA[i].rect.contains(x,y))
				return roomA[i].vertex;
		}

		for(int i = 0; i < beamRect.length; i++){
			
			if(beamRect[i].contains(x,y))
				return beamA[i].vertex;
		}
		return null;
	}

	public static Environment createExampleEnvironment(DetectiveGame game){
		
		Environment e = new Environment();

		e.game = game;
		
		// Bounding rectangle
		e.boundingRect = new Rectangle2D.Double(0, 0, 800, 600);
		
		// Walls
		e.walls.add(new LineSegment(210, 0, 210, 75, e.dc));
		e.walls.add(new LineSegment(210, 130, 210, 320, e.dc));
		e.walls.add(new LineSegment(210, 375, 210, 475, e.dc));
		e.walls.add(new LineSegment(210, 525, 210, 600, e.dc));
		
		e.walls.add(new LineSegment(295, 0, 295, 75,e.dc));
		e.walls.add(new LineSegment(295, 130, 295, 185,e.dc));
		e.walls.add(new LineSegment(295, 270, 295, 345,e.dc));
		e.walls.add(new LineSegment(295, 400, 295, 500,e.dc));
		e.walls.add(new LineSegment(295, 545, 295, 600,e.dc));
		
		e.walls.add(new LineSegment(530, 0, 530, 75,e.dc));
		e.walls.add(new LineSegment(530, 130, 530, 185,e.dc));
		e.walls.add(new LineSegment(530, 270, 530, 345,e.dc));
		e.walls.add(new LineSegment(530, 400, 530, 600,e.dc));
		
		e.walls.add(new LineSegment(610, 0, 610, 185,e.dc));
		e.walls.add(new LineSegment(610, 270, 610, 470,e.dc));
		e.walls.add(new LineSegment(610, 525, 610, 600,e.dc));

		e.walls.add(new LineSegment(0, 185, 85, 185,e.dc));
		e.walls.add(new LineSegment(140, 185, 210, 185,e.dc));
		e.walls.add(new LineSegment(295, 185, 530, 185,e.dc));
		e.walls.add(new LineSegment(610, 185, 680, 185,e.dc));
		e.walls.add(new LineSegment(730, 185, 800, 185,e.dc));
		
		e.walls.add(new LineSegment(295, 270, 380, 270,e.dc));
		e.walls.add(new LineSegment(430, 270, 680, 270,e.dc));
		e.walls.add(new LineSegment(730, 270, 800, 270,e.dc));
		
		e.walls.add(new LineSegment(0, 400, 210, 400,e.dc));
		
		e.walls.add(new LineSegment(295, 455, 380, 455,e.dc));
		e.walls.add(new LineSegment(430, 455, 530, 455,e.dc));
		
		// Beam detectors
		e.createBeamDetector("b1u", 215, 285, 290, 285, game);
		e.createBeamDetector("b1d", 215, 305, 290, 305, game);
		e.createBeamDetector("b2l", 625, 180, 625, 265, game);
		e.createBeamDetector("b2r", 645, 180, 645, 265, game);
		
		// Occupancy sensors
		e.createOccupancySensor("o1", 295, 270, 235, 185, game);
		e.createOccupancySensor("o2", 610, 270, 190, 330, game);
		
		// Rooms
		e.createRoom("A", 0, 0, 210, 185, game);
		e.createRoom("B", 610, 0, 190, 185, game);
		e.createRoom("C", 0, 400, 210, 200, game);
		
		e.initialize();
		
		return e;
		
	}
	
}
