package projects.cyberDetective;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Graph {

	public Set<Integer> vertexIds = null;
	public Map<Integer, Vertex> vertexMap = null;
	public Map<String, Vertex> vertexNameMap = null;
	public Set<Integer> edgeIds = null;
	public Map<Integer, Edge> edgeMap = null;
	
	public Graph(){
		vertexIds = new HashSet<Integer>();
		vertexMap = new HashMap<Integer, Vertex>();
		vertexNameMap = new HashMap<String, Vertex>();
		edgeIds = new HashSet<Integer>();
		edgeMap = new HashMap<Integer, Edge>();
	}
	
	public boolean hasEdgeBetweenVertices(Vertex v1, Vertex v2){
		// Create id for look up
		return edgeMap.containsKey(Edge.getEdgeId(v1, v2));
	}
	
	public Edge getEdgeBetweenVertices(Vertex v1, Vertex v2){
		// Create id for look up
		return edgeMap.get(Edge.getEdgeId(v1, v2));
	}
	
	public boolean hasEdgeBetweenVertices(int v1, int v2){
		// Create id for look up
		return edgeMap.containsKey(Edge.getEdgeId(v1, v2));
	}
	
	public Edge getEdgeBetweenVertices(int v1, int v2){
		// Create id for look up
		return edgeMap.get(Edge.getEdgeId(v1, v2));
	}
	
	/**
	 * Add an edgeless vertex
	 * @param v
	 */
	public void addEdgelessVertex(Vertex v){
		vertexIds.add(v.id);
		vertexMap.put(v.id, v);
		vertexNameMap.put(v.name, v);
		
	}

	public void addCopyOfEdgelessVertex(Vertex v){
		Vertex copy = v.getCopy();
		addEdgelessVertex(copy);
	}
	
	public void addEdge(Edge ed){
		// Do we have the edge already?
		if(!edgeIds.contains(ed.id))
		{
			// Do we have the vertices?
			for(int i = 0; i < ed.vertices.length; i ++)
			if(!vertexIds.contains(ed.vertices[i].id)){
				vertexIds.add(ed.vertices[i].id);
				vertexMap.put(ed.vertices[i].id, ed.vertices[i]);
				vertexNameMap.put(ed.vertices[i].name, ed.vertices[i]);
			}
			else{
				ed.vertices[i] = vertexMap.get(ed.vertices[i].id);
			}
			
			// Update neighbors 
			ed.vertices[0].addNeighbor(ed.vertices[1]);
			ed.vertices[1].addNeighbor(ed.vertices[0]);
			
			// Add edge
			edgeIds.add(ed.id);
			edgeMap.put(ed.id, ed);
		}
	}

	/**
	 * Add a copy of an edge, possibly creating new vertices as well
	 * @param e
	 */
	public void addCopyOfEdge(Edge e){
		Edge ed = e.getCopy();
		addEdge(ed);
	}
	
	/**
	 * A simple print out
	 */
	public void dump(){
		System.out.println();
		Vertex[] vs = this.vertexMap.values().toArray(new Vertex[0]);
		System.out.println("vertices: ");
		for(int i = 0; i < vs.length; i ++){
			System.out.print(vs[i].name + ": ");
			Vertex[] ns = vs[i].neighbors.toArray(new Vertex[0]);
			for(int j = 0; j < ns.length; j ++){
				System.out.print(ns[j].name + " ");
			}
			System.out.println();
		}

		Edge[] es = this.edgeMap.values().toArray(new Edge[0]);
		System.out.println("edges: ");
		for(int i = 0; i < es.length; i ++){
			System.out.print(es[i].vertices[0].name + "--" +  es[i].vertices[1].name);
			System.out.print("  ");
			if(i > 0 && (i + 1) % 5 == 0){
				System.out.println();
			}
		}
		System.out.println();
	}
}
