package projects.cyberDetective;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class Vertex {
	public String name;
	public int id;

	public Set<Vertex> neighbors;
	public Vertex assoVertex = null; 

	public Vertex() {
		super();
		this.neighbors = new HashSet<Vertex>(); 
	}

	public Vertex(String name, int id) {
		this();
		this.name = name;
		this.id = id;
	}

	public boolean isNeighbor(Vertex v){
		return neighbors.contains(v);
	}
	
	public void addNeighbor(Vertex v){
		neighbors.add(v);
	}

	public void addNeighbors(Collection c){
		neighbors.addAll(c);
	}
	
	public void removeNeighbor(Vertex v){
		neighbors.remove(v);
	}
	
	public Vertex getCopy(){
		Vertex v = new Vertex();
		v.id = this.id;
		v.name = this.name;
		return v;
	}
	
}
