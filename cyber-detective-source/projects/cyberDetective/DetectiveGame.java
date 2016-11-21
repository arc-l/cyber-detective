package projects.cyberDetective;

import java.util.HashSet;
import java.util.Set;
import java.util.Vector;

import common.util.IDGenerator;

public class DetectiveGame {
	
	public Vertex[] storyVertices = null;
	public Vertex[] sensorVertices = null;
	public Graph graph = null;
	public Story story = null;
	public ObservationHistory obHis = null;
	public Set<Integer> roomIds = new HashSet<Integer>();
	public Set<Integer> beamIds = new HashSet<Integer>();
	public Set<Integer> occuIds = new HashSet<Integer>();
	
	public void updateStartingVertex(Vertex v){
		Vertex sv = graph.vertexNameMap.get("SV");
		Vertex svn = sv.neighbors.toArray(new Vertex[0])[0];
		svn.neighbors.remove(sv);
		sv.neighbors.remove(svn);
		int eid = Edge.getEdgeId(sv.id, svn.id);
		graph.edgeIds.remove(eid);
		graph.edgeMap.remove(eid);
		
		sv.addNeighbor(v);
		v.addNeighbor(sv);
		Edge e = new Edge(sv, v);
		graph.edgeIds.add(e.id);
		graph.edgeMap.put(e.id, e);
		
	}

	
	public static DetectiveGame getBasicGame(){
		IDGenerator idGen = new IDGenerator();
		idGen.getNextId();
		DetectiveGame game = new DetectiveGame();
		Graph g = new Graph();
		Vector<Vertex> stvs = new Vector<Vertex>();
		Vector<Vertex> sevs = new Vector<Vertex>();
		
		// Create vertices
		Vertex sv = new Vertex("SV", idGen.getNextId());
		Vertex a = new Vertex("A", idGen.getNextId());
		Vertex b = new Vertex("B", idGen.getNextId());
		Vertex c = new Vertex("C", idGen.getNextId());
		Vertex b1u = new Vertex("b1u", idGen.getNextId());
		Vertex b1d = new Vertex("b1d", idGen.getNextId());
		Vertex b2l = new Vertex("b2l", idGen.getNextId());
		Vertex b2r = new Vertex("b2r", idGen.getNextId());
		Vertex o1 = new Vertex("o1", idGen.getNextId());
		Vertex o2 = new Vertex("o2", idGen.getNextId());

		b1u.assoVertex = b1d;
		b1d.assoVertex = b1u;
		b2r.assoVertex = b2l;
		b2l.assoVertex = b2r;
		
		game.beamIds.add(b1u.id);
		game.beamIds.add(b1d.id);
		game.beamIds.add(b2r.id);
		game.beamIds.add(b2l.id);
		game.roomIds.add(a.id);
		game.roomIds.add(b.id);
		game.roomIds.add(c.id);
		game.occuIds.add(o1.id);
		game.occuIds.add(o2.id);
		
		stvs.add(sv);
		stvs.add(a);
		stvs.add(b);
		stvs.add(c);
		sevs.add(b1u);
		sevs.add(b1d);
		sevs.add(b2l);
		sevs.add(b2r);
		sevs.add(o1);
		sevs.add(o2);
		game.graph = g;
		game.storyVertices = stvs.toArray(new Vertex[0]);
		game.sensorVertices = sevs.toArray(new Vertex[0]);
		
		// Add vertices to graph
		g.vertexIds.add(sv.id);
		g.vertexIds.add(a.id);
		g.vertexIds.add(b.id);
		g.vertexIds.add(c.id);
		g.vertexIds.add(b1u.id);
		g.vertexIds.add(b1d.id);
		g.vertexIds.add(b2r.id);
		g.vertexIds.add(b2l.id);
		g.vertexIds.add(o1.id);
		g.vertexIds.add(o2.id);
		
		g.vertexMap.put(sv.id, sv);
		g.vertexMap.put(a.id, a);
		g.vertexMap.put(b.id, b);
		g.vertexMap.put(c.id, c);
		g.vertexMap.put(b1u.id, b1u);
		g.vertexMap.put(b1d.id, b1d);
		g.vertexMap.put(b2r.id, b2r);
		g.vertexMap.put(b2l.id, b2l);
		g.vertexMap.put(o1.id, o1);
		g.vertexMap.put(o2.id, o2);
		
		g.vertexNameMap.put(sv.name, sv);
		g.vertexNameMap.put(a.name, a);
		g.vertexNameMap.put(b.name, b);
		g.vertexNameMap.put(c.name, c);
		g.vertexNameMap.put(b1u.name, b1u);
		g.vertexNameMap.put(b1d.name, b1d);
		g.vertexNameMap.put(b2r.name, b2r);
		g.vertexNameMap.put(b2l.name, b2l);
		g.vertexNameMap.put(o1.name, o1);
		g.vertexNameMap.put(o2.name, o2);

		// Create neighbor list
		sv.addNeighbor(a);
		
		a.addNeighbor(sv);
		a.addNeighbor(b1u);
		a.addNeighbor(b1d);
		a.addNeighbor(b2l);
		a.addNeighbor(o1);
		a.addNeighbor(c);
		
		b.addNeighbor(b2r);
		b.addNeighbor(o2);
		
		c.addNeighbor(a);
		c.addNeighbor(b1d);
		c.addNeighbor(o1);
		
		b1u.addNeighbor(a);
		b1u.addNeighbor(b2l);
		b1u.addNeighbor(o1);
		
		b1d.addNeighbor(a);
		b1d.addNeighbor(c);
		b1d.addNeighbor(o1);
		
		b2l.addNeighbor(a);
		b2l.addNeighbor(b1u);
		b2l.addNeighbor(o1);
		
		b2r.addNeighbor(b);
		b2r.addNeighbor(o2);
		
		o1.addNeighbor(a);
		o1.addNeighbor(c);
		o1.addNeighbor(b2l);
		o1.addNeighbor(b1u);
		o1.addNeighbor(b1d);
		o1.addNeighbor(o2);
		
		o2.addNeighbor(o1);
		o2.addNeighbor(b2r);
		o2.addNeighbor(b);
		
		// Add edges to graph 
		Vertex[] vs = g.vertexMap.values().toArray(new Vertex[0]);
		for(int i = 0; i < vs.length; i++){
			Vertex[] ns = vs[i].neighbors.toArray(new Vertex[0]);
			for(int j = 0; j < ns.length; j ++){
				if(!g.hasEdgeBetweenVertices(vs[i], ns[j]))
				{
					Edge e = new Edge(vs[i], ns[j]);
					g.edgeIds.add(e.id);
					g.edgeMap.put(e.id, e);
				}
			}
		}
		
		game.story = new Story();
		game.obHis = new ObservationHistory();
		return game;
	}
	
	public static DetectiveGame getSingleInfeasibleGame(){
		DetectiveGame game = getBasicGame();
		
		Graph g = game.graph;
		game.story.addVertex(g.vertexNameMap.get("A"));
		game.story.addVertex(g.vertexNameMap.get("C"));
		game.story.addVertex(g.vertexNameMap.get("B"));
		game.story.addVertex(g.vertexNameMap.get("A"));
		game.story.addVertex(g.vertexNameMap.get("C"));

		OccupancySensor O1 = new OccupancySensor(g.vertexNameMap.get("o1"));  
		OccupancySensor O2 = new OccupancySensor(g.vertexNameMap.get("o2"));
		BeamDetector B1 = new BeamDetector("b1", new Vertex[]{g.vertexNameMap.get("b1u"), g.vertexNameMap.get("b1d")});
		BeamDetector B2 = new BeamDetector("b2", new Vertex[]{g.vertexNameMap.get("b2r"), g.vertexNameMap.get("b2l")});
		
		game.obHis.addSensorRecording(new SensorRecording(B1, SensorRecording.ACTIVATION));
		game.obHis.addSensorRecording(new SensorRecording(O1, SensorRecording.ACTIVATION));
		game.obHis.addSensorRecording(new SensorRecording(O1, SensorRecording.DEACTIVATION));
		game.obHis.addSensorRecording(new SensorRecording(B2, SensorRecording.ACTIVATION));
		game.obHis.addSensorRecording(new SensorRecording(O2, SensorRecording.ACTIVATION));
		game.obHis.addSensorRecording(new SensorRecording(O2, SensorRecording.DEACTIVATION));
		
		return game;
	}

	public static DetectiveGame getSingleFeasibleGame(){
		DetectiveGame game = getBasicGame();
		
		Graph g = game.graph;
		game.story.addVertex(g.vertexNameMap.get("A"));
		game.story.addVertex(g.vertexNameMap.get("C"));
		game.story.addVertex(g.vertexNameMap.get("B"));
		game.story.addVertex(g.vertexNameMap.get("A"));
		game.story.addVertex(g.vertexNameMap.get("C"));

		OccupancySensor O1 = new OccupancySensor(g.vertexNameMap.get("o1"));  
		OccupancySensor O2 = new OccupancySensor(g.vertexNameMap.get("o2"));
		BeamDetector B1 = new BeamDetector("b1", new Vertex[]{g.vertexNameMap.get("b1u"), g.vertexNameMap.get("b1d")});
		BeamDetector B2 = new BeamDetector("b2", new Vertex[]{g.vertexNameMap.get("b2r"), g.vertexNameMap.get("b2l")});
		
		game.obHis.addSensorRecording(new SensorRecording(B1, SensorRecording.ACTIVATION));
		game.obHis.addSensorRecording(new SensorRecording(O1, SensorRecording.ACTIVATION));
		game.obHis.addSensorRecording(new SensorRecording(O1, SensorRecording.DEACTIVATION));
		game.obHis.addSensorRecording(new SensorRecording(O2, SensorRecording.ACTIVATION));
		game.obHis.addSensorRecording(new SensorRecording(O2, SensorRecording.DEACTIVATION));
		game.obHis.addSensorRecording(new SensorRecording(B2, SensorRecording.ACTIVATION));
		
		return game;
	}

	public static DetectiveGame getMultiFeasibleGame(){
		DetectiveGame game = getBasicGame();
		
		Graph g = game.graph;
		game.story.addVertex(g.vertexNameMap.get("A"));
		game.story.addVertex(g.vertexNameMap.get("C"));
		game.story.addVertex(g.vertexNameMap.get("B"));
		game.story.addVertex(g.vertexNameMap.get("A"));
		game.story.addVertex(g.vertexNameMap.get("C"));

		OccupancySensor O1 = new OccupancySensor(g.vertexNameMap.get("o1"));  
		OccupancySensor O2 = new OccupancySensor(g.vertexNameMap.get("o2"));
		BeamDetector B1 = new BeamDetector("b1", new Vertex[]{g.vertexNameMap.get("b1u"), g.vertexNameMap.get("b1d")});
		BeamDetector B2 = new BeamDetector("b2", new Vertex[]{g.vertexNameMap.get("b2r"), g.vertexNameMap.get("b2l")});
		
		game.obHis.addSensorRecording(new SensorRecording(B1, SensorRecording.ACTIVATION));
		game.obHis.addSensorRecording(new SensorRecording(O1, SensorRecording.ACTIVATION));
		game.obHis.addSensorRecording(new SensorRecording(O2, SensorRecording.ACTIVATION));
		game.obHis.addSensorRecording(new SensorRecording(B2, SensorRecording.ACTIVATION));
		game.obHis.addSensorRecording(new SensorRecording(O2, SensorRecording.DEACTIVATION));
		game.obHis.addSensorRecording(new SensorRecording(O1, SensorRecording.DEACTIVATION));
		
		return game;
	}
	
	public static DetectiveGame getMultiInfeasibleGame(){
		DetectiveGame game = getBasicGame();
		
		Graph g = game.graph;
		game.story.addVertex(g.vertexNameMap.get("A"));
		game.story.addVertex(g.vertexNameMap.get("C"));
		game.story.addVertex(g.vertexNameMap.get("B"));
		game.story.addVertex(g.vertexNameMap.get("A"));
		game.story.addVertex(g.vertexNameMap.get("C"));

		OccupancySensor O1 = new OccupancySensor(g.vertexNameMap.get("o1"));  
		OccupancySensor O2 = new OccupancySensor(g.vertexNameMap.get("o2"));
		BeamDetector B1 = new BeamDetector("b1", new Vertex[]{g.vertexNameMap.get("b1u"), g.vertexNameMap.get("b1d")});
		BeamDetector B2 = new BeamDetector("b2", new Vertex[]{g.vertexNameMap.get("b2r"), g.vertexNameMap.get("b2l")});
		
		game.obHis.addSensorRecording(new SensorRecording(B1, SensorRecording.ACTIVATION));
		game.obHis.addSensorRecording(new SensorRecording(O2, SensorRecording.ACTIVATION));
		game.obHis.addSensorRecording(new SensorRecording(O2, SensorRecording.DEACTIVATION));
		game.obHis.addSensorRecording(new SensorRecording(O1, SensorRecording.ACTIVATION));
		game.obHis.addSensorRecording(new SensorRecording(B2, SensorRecording.ACTIVATION));
		game.obHis.addSensorRecording(new SensorRecording(O1, SensorRecording.DEACTIVATION));
		
		return game;
	}
	
}
