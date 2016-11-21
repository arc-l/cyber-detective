package projects.cyberDetective;

import java.util.HashSet;
import java.util.Set;
import java.util.Vector;

public class Algorithms {

	public static Graph getSubGraph(Graph g, Vertex s, 
			Vertex[] storyVertices, Vertex[] vg)
	{
		Set<Vertex> vpSet = new HashSet<Vertex>();
		for(int i = 0; i < storyVertices.length; i ++){
			vpSet.add(storyVertices[i]);
		}
		vpSet.add(s);
		return getReachableSubgraph(g, s, vpSet, vg);
	}
	
	public static Graph getSubGraphMulti(Graph g, Vertex s, 
			Vertex[] occuSensors, Vertex[] storyVertices, Vertex[] vg)
	{
		Set<Vertex> vpSet = new HashSet<Vertex>();
		for(int i = 0; i < storyVertices.length; i ++){
			vpSet.add(storyVertices[i]);
		}
		for(int i = 0; i < occuSensors.length; i ++){
			vpSet.add(occuSensors[i]);
		}
		vpSet.add(s);
		Graph gp = getReachableSubgraph(g, s, vpSet, vg);
		
		for(int i = 0; i < occuSensors.length; i ++){
			if(gp.vertexMap.containsKey(occuSensors[i].id)){
				Vertex[] ns = gp.vertexMap.get(occuSensors[i].id).neighbors.toArray(new Vertex[0]);
				for(int j = 0; j < ns.length; j ++){
					for(int k = j + 1; k < ns.length; k ++){
						if(!gp.hasEdgeBetweenVertices(ns[j].id, ns[k].id)){
							gp.addEdge(new Edge(ns[j], ns[k]));
						}
					}
				}
			}
		}
		return gp;
	}
	
	public static Graph getReachableSubgraph(Graph g, Vertex s, 
			Set vpSet, Vertex[] vg)
	{
		Graph tempGraph = new Graph();
		Graph subGraph = new Graph();
		Edge[] es = g.edgeMap.values().toArray(new Edge[0]);
		
		// Add s to graph
		tempGraph.addCopyOfEdgelessVertex(s);
		subGraph.addCopyOfEdgelessVertex(s);
		// Add related edges to E'
		for(int i = 0; i < es.length; i ++)
		{
			if(vpSet.contains(es[i].vertices[0]) && vpSet.contains(es[i].vertices[1]))
			{
				tempGraph.addCopyOfEdge(es[i]);
			}
			
		}
		
		// tempGraph.dump();
		
		// Get the connected component containing s
		Set<Integer> vvid = new HashSet<Integer>();	// Visted vertices
		Vector<Integer> queue = new Vector<Integer>();	// Finished vertices
		queue.add(s.id);
		while(!queue.isEmpty()){
			Vertex cv = tempGraph.vertexMap.get(queue.get(0));
			vvid.add(cv.id);
			queue.remove(0);
			Vertex[] ns = cv.neighbors.toArray(new Vertex[0]);
			for(int i = 0; i < ns.length; i ++){
				if(tempGraph.hasEdgeBetweenVertices(cv, ns[i])){
					if(!subGraph.hasEdgeBetweenVertices(cv, ns[i])){
						subGraph.addCopyOfEdge(tempGraph.getEdgeBetweenVertices(cv, ns[i]));
					}
					if(!vvid.contains(ns[i].id)){
						queue.add(ns[i].id);
					}
				}
			}
		}
		
		// subGraph.dump();
		
		// Add goal vertices to graph
		Vertex[] sgvs = subGraph.vertexMap.values().toArray(new Vertex[0]);
		for(int i = 0; i < vg.length; i ++){
			for(int j = 0; j < sgvs.length; j ++){
				// Locate these vertices in g
				Vertex v1 = g.vertexMap.get(sgvs[j].id);
				Vertex v2 = vg[i];
				if(v1.isNeighbor(v2)){
					subGraph.addCopyOfEdge(g.getEdgeBetweenVertices(v1, v2));
				}
			}
		}
		
		// subGraph.dump();
		
		return subGraph;
	}
	
	private static boolean isDeactivation(SensorRecording sr){
		if(sr.sensor.type == Sensor.SENSOR_TYPE_OCCUPANCY && sr.event == SensorRecording.DEACTIVATION)
			return true;
		else
			return false;
	}
	
	private static boolean isActivation(SensorRecording sr){
		if(sr.sensor.type == Sensor.SENSOR_TYPE_OCCUPANCY && sr.event == SensorRecording.ACTIVATION)
			return true;
		else
			return false;
	}
	
	private static Vertex flip(Graph g, Vertex v, Sensor r){
		if(r.type == Sensor.SENSOR_TYPE_OCCUPANCY){
			return g.vertexMap.get(v.id);
		}
		else{
			if(v.name.equals(r.sensorVertices[0].name)){
				return g.vertexMap.get(r.sensorVertices[1].id);
			}
			else{
				return g.vertexMap.get(r.sensorVertices[0].id); 
			}
		}
	}
	
	private static void dumpStatus(Vertex[] p, Set<Vertex>[] status){
		System.out.print("search status: ");
		Vertex[] vs  = status[0].toArray(new Vertex[0]);
		if(vs.length > 0) System.out.print("[");
		for(int j = 0; j < vs.length; j ++){
			System.out.print(vs[j].name);
			if(j < vs.length - 1)System.out.print(",");
		}
		if(vs.length > 0) System.out.print("]");

		for(int i = 0; i < p.length; i ++){
			System.out.print(p[i].name);
			vs  = status[i + 1].toArray(new Vertex[0]);
			if(vs.length > 0) System.out.print("[");
			for(int j = 0; j < vs.length; j ++){
				System.out.print(vs[j].name);
				if(j < vs.length - 1)System.out.print(",");
			}
			if(vs.length > 0) System.out.print("]");
		}
		System.out.println();
	}
	
	private static boolean areNeighbors(Graph g, int v1, int v2){
		if(g.hasEdgeBetweenVertices(v1, v2) || v1 == v2) return true;
		return false;
	}
	
	public static boolean validateAgentStory(Graph g, Vertex sv, Story story, ObservationHistory obHis){
		Vertex[] p = story.getStoryAsArray();
		SensorRecording[] r = obHis.getOHAsArray();
		Set<Vertex>[] S = new HashSet[p.length + 1];
		Set<Vertex>[] SP = new HashSet[p.length + 1];
		for(int i = 0; i < S.length; i++){
			S[i] = new HashSet<Vertex>();
			SP[i] = new HashSet<Vertex>();
		}
		S[0].add(sv);
		Vertex[] Vg = new Vertex[0];
		
		for(int i = 0; i < r.length + 1; i ++){
			if(i < r.length && isDeactivation(r[i])){ 
				continue;
			}
			
			Vg = new Vertex[0];
			if(i < r.length){
				Vg = r[i].sensor.getGraphVertices();
			}
			
			for(int j = 0; j < S.length; j ++){
				Vertex[] Sj = S[j].toArray(new Vertex[0]);
				for(int k = 0; k < Sj.length; k ++){
					Vertex s = Sj[k];
					Graph GP = getSubGraph(g, s, story.getVertexSetAsArray(), Vg);
					s = GP.vertexMap.get(s.id);
					// GP.dump();
					for(int l = j; l < S.length; l ++){
						if(i == r.length && l == S.length - 1){
							return true;
						}
						for(int ii = 0; ii < Vg.length; ii++){
							Vertex v = GP.vertexMap.get(Vg[ii].id);
							if(v == null)continue;
							if(GP.hasEdgeBetweenVertices(v, s) || v==s){
								SP[l].add(flip(g, v, r[i].sensor));
							}
						}
						if(l < S.length - 1 && areNeighbors(GP, p[l].id, s.id)){
							s = GP.vertexMap.get(p[l].id);
						}
						else{
							break;
						}
					}
				}
			}
			S = SP;
			SP = new HashSet[p.length + 1];
			for(int j = 0; j < S.length; j++){
				SP[j] = new HashSet<Vertex>();
			}
			dumpStatus(p, S);
		}
		return false;
	}

	public static String getAgentStory(Graph g, Vertex sv, Story story, ObservationHistory obHis){
		Vertex[] p = story.getStoryAsArray();
		SensorRecording[] r = obHis.getOHAsArray();
		Set<Vertex>[][] S = getAgentStoryStatuses(g,sv,story,obHis);
		Vector<Integer> srLocVec = new Vector<Integer>();
		Vector<Vertex> srVerVec = new Vector<Vertex>();
			
		Vertex[] Vg = new Vertex[0];
		boolean keepBreaking = false;
		int J =0;
		Vertex last = null;
		for(int i = r.length; i >= 0; i --){
			keepBreaking = false;
			if(i < r.length && isDeactivation(r[i])){ 
				continue;
			}
			
			Vg = new Vertex[0];
			if(i < r.length){
				Vg = r[i].sensor.getGraphVertices();
			}
			
			for(int j = 0; j < S[i].length; j ++){
				Vertex[] Sj = S[i][j].toArray(new Vertex[0]);
				for(int k = 0; k < Sj.length; k ++){
					Vertex s = Sj[k];
					Graph GP = getSubGraph(g, s, story.getVertexSetAsArray(), Vg);
					s = GP.vertexMap.get(s.id);
					// GP.dump();
					for(int l = j; l < S[i].length; l ++){
						if(i == r.length && l == S[i].length - 1){
							J = j;
							srLocVec.add(j);
							if(g.vertexMap.get(Sj[k].id).assoVertex != null)
								srVerVec.add(g.vertexMap.get(Sj[k].id).assoVertex);
							else
								srVerVec.add(Sj[k]);
							System.out.println(Sj[k].name + " " + j);
							last = Sj[k];
							keepBreaking = true;
							break;
						}
						for(int ii = 0; ii < Vg.length; ii++){
							Vertex v = GP.vertexMap.get(Vg[ii].id);
							if(v == null)continue;
							if(GP.hasEdgeBetweenVertices(v, s) || v==s){
								Vertex vp = flip(g, v, r[i].sensor);
								if(l == J && vp.id == last.id){
									J = j;
									last = Sj[k];
									srLocVec.add(j);
									if(g.vertexMap.get(Sj[k].id).assoVertex != null)
										srVerVec.add(g.vertexMap.get(Sj[k].id).assoVertex);
									else
										srVerVec.add(Sj[k]);
									System.out.println(Sj[k].name + " " + j);
									break;
								}
							}
						}
						if(l < S[i].length - 1 && areNeighbors(GP, p[l].id, s.id)){
							s = GP.vertexMap.get(p[l].id);
						}
						else{
							break;
						}
					}
					if(i == r.length && keepBreaking == true)break;
				}
				if(i == r.length && keepBreaking == true)break;
			}
			dumpStatus(p, S[i]);
		}
		
		Vector<String> path = new Vector<String>();
		for(int i = 0; i < p.length;i ++){
			path.add(p[i].name);
		}
		for(int i = 0; i < srLocVec.size() - 1; i++){
			int loc = srLocVec.get(i).intValue();
			path.insertElementAt("[" + srVerVec.get(i).name + "]", loc);
		}
		StringBuffer buf = new StringBuffer();
		for(int i = 0;i < path.size(); i ++){
			buf.append(path.get(i));
		}
		return buf.toString();
	}

	public static Set<Vertex>[][] getAgentStoryStatuses(Graph g, Vertex sv, Story story, ObservationHistory obHis){
		Vertex[] p = story.getStoryAsArray();
		SensorRecording[] r = obHis.getOHAsArray();
		Set<Vertex>[][] S = new HashSet[r.length + 1][p.length + 1];
		for(int i = 0; i < r.length + 1; i ++){
			for(int j = 0; j < p.length + 1; j ++){
				S[i][j] = new HashSet<Vertex>();
			}
		}
		S[0][0].add(sv);
		Vertex[] Vg = new Vertex[0];
		
		for(int i = 0; i < r.length + 1; i ++){
			if(i < r.length && isDeactivation(r[i])){ 
				S[i + 1] = S[i];
				continue;
			}
			
			Vg = new Vertex[0];
			if(i < r.length){
				Vg = r[i].sensor.getGraphVertices();
			}
			
			for(int j = 0; j < S[i].length; j ++){
				Vertex[] Sj = S[i][j].toArray(new Vertex[0]);
				for(int k = 0; k < Sj.length; k ++){
					Vertex s = Sj[k];
					Graph GP = getSubGraph(g, s, story.getVertexSetAsArray(), Vg);
					s = GP.vertexMap.get(s.id);
					// GP.dump();
					for(int l = j; l < S[i].length; l ++){
						if(i == r.length && l == S[i].length - 1){
							return S;
						}
						for(int ii = 0; ii < Vg.length; ii++){
							Vertex v = GP.vertexMap.get(Vg[ii].id);
							if(v == null)continue;
							if(GP.hasEdgeBetweenVertices(v, s) || v==s){
								S[i+1][l].add(flip(g, v, r[i].sensor));
							}
						}
						if(l < S[i].length - 1 && areNeighbors(GP, p[l].id, s.id)){
							s = GP.vertexMap.get(p[l].id);
						}
						else{
							break;
						}
					}
				}
			}
			dumpStatus(p, S[i + 1]);
		}
		return null;
	}

	public static boolean validateAgentStoryMulti(Graph g, Vertex sv, Story story, ObservationHistory obHis){
		Vertex[] p = story.getStoryAsArray();
		SensorRecording[] r = obHis.getOHAsArray();
		Set<Vertex>[] S = new HashSet[p.length + 1];
		Set<Vertex>[] SP = new HashSet[p.length + 1];
		for(int i = 0; i < S.length; i++){
			S[i] = new HashSet<Vertex>();
			SP[i] = new HashSet<Vertex>();
		}
		S[0].add(sv);
		Set<Vertex> O = new HashSet<Vertex>();
		
		for(int i = 0; i < r.length + 1; i ++){
			Vertex[] Vg = new Vertex[0];
			Vertex[] Vgp = new Vertex[0];
			if(i < r.length && isDeactivation(r[i])){
				O.remove(r[i].sensor.sensorVertices[0]);
				continue;
			}
			
			if(i < r.length && isActivation(r[i])){
				O.add(r[i].sensor.sensorVertices[0]);
				Vg = O.toArray(new Vertex[0]); 
			}
			
			if(i < r.length){
				Vg = r[i].sensor.sensorVertices;
			}

			if(i < r.length){
				Vg = r[i].sensor.getGraphVertices();
			}
			
			for(int j = 0; j < S.length; j ++){
				Vertex[] Sj = S[j].toArray(new Vertex[0]);
				for(int k = 0; k < Sj.length; k ++){
					Vertex s = Sj[k];
					Vgp = Vg;
					if(i < r.length && isActivation(r[i])){
						Vgp = new Vertex[Vg.length + 1];
						for(int ii = 0; ii < Vg.length; ii++){
							Vgp[ii] = Vg[ii];
						}
						Vgp[Vgp.length - 1] = s;
					}
						
					Graph GP = getSubGraphMulti(g, s, O.toArray(new Vertex[0]), story.getVertexSetAsArray(), Vgp);
					s = GP.vertexMap.get(s.id);
					GP.dump();
					for(int l = j; l < S.length; l ++){
						if(i == r.length && l == S.length - 1){
							return true;
						}
						for(int ii = 0; ii < Vg.length; ii++){
							Vertex v = GP.vertexMap.get(Vg[ii].id);
							if(v == null)continue;
							if(GP.hasEdgeBetweenVertices(v, s) || v==s){
								SP[l].add(flip(g, v, r[i].sensor));
							}
						}
						if(l < S.length - 1 && areNeighbors(GP, p[l].id, s.id)){
							s = GP.vertexMap.get(p[l].id);
						}
						else{
							break;
						}
					}
				}
			}
			for(int j = 0; j < SP.length; j ++){
				S[j].addAll(SP[j]);
			}
			SP = new HashSet[p.length + 1];
			for(int j = 0; j < S.length; j++){
				SP[j] = new HashSet<Vertex>();
			}
			dumpStatus(p, S);
		}
		return false;
	}


	
	public static void testGraphRoutines(){
		DetectiveGame g = DetectiveGame.getBasicGame();
		g.graph.dump();
		
		Algorithms.getSubGraph(g.graph, g.graph.vertexNameMap.get("A"), g.storyVertices, new Vertex[]{g.graph.vertexNameMap.get("b1d"), g.graph.vertexNameMap.get("b1u")}).dump();
		Algorithms.getSubGraph(g.graph, g.graph.vertexNameMap.get("b1u"), g.storyVertices, new Vertex[]{g.graph.vertexNameMap.get("o1")}).dump();
		Algorithms.getSubGraph(g.graph, g.graph.vertexNameMap.get("b1d"), g.storyVertices, new Vertex[]{g.graph.vertexNameMap.get("o1")}).dump();
		Algorithms.getSubGraph(g.graph, g.graph.vertexNameMap.get("o1"), g.storyVertices, new Vertex[]{g.graph.vertexNameMap.get("b2r"), g.graph.vertexNameMap.get("b2l")}).dump();
		Algorithms.getSubGraph(g.graph, g.graph.vertexNameMap.get("b2r"), g.storyVertices, new Vertex[]{g.graph.vertexNameMap.get("b2r"), g.graph.vertexNameMap.get("o2")}).dump();
		Algorithms.getSubGraph(g.graph, g.graph.vertexNameMap.get("o2"), g.storyVertices, new Vertex[]{g.graph.vertexNameMap.get("o2"), g.graph.vertexNameMap.get("B")}).dump();
	}

	public static void testStoryHistory() {
		DetectiveGame g = DetectiveGame.getSingleFeasibleGame();
		g.graph.dump();
		g.story.dump();
		g.obHis.dump();
	}

	public static void testSingleAgent(){
		DetectiveGame g = DetectiveGame.getSingleFeasibleGame();
		g.graph.dump();
		g.story.dump();
		g.obHis.dump();
		
		boolean good = validateAgentStory(g.graph, g.graph.vertexNameMap.get("SV"), g.story, g.obHis);
		System.out.println();
		System.out.println((good?"Valid story.":"Story inconsistent."));
	}
	
	public static void testMultiAgent(){
		DetectiveGame g = DetectiveGame.getMultiInfeasibleGame();
		g.graph.dump();
		g.story.dump();
		g.obHis.dump();
		
		boolean good = validateAgentStoryMulti(g.graph, g.graph.vertexNameMap.get("SV"), g.story, g.obHis);
		System.out.println();
		System.out.println((good?"Valid story.":"Story inconsistent."));
	}
	
	public static void main(String[] argv){
		testMultiAgent();
	}	
}
