package projects.cyberDetective;

import java.util.HashSet;
import java.util.Set;
import java.util.Vector;

public class Story {
	public Vector<Vertex> visitedVertices = null;

	public Story() {
		visitedVertices = new Vector<Vertex>();
	}
	
	public void addVertex(Vertex v){
		visitedVertices.add(v);
	}
	
	public Vertex[] getStoryAsArray(){
		return visitedVertices.toArray(new Vertex[0]); 
	}
	
	public Vertex[] getVertexSetAsArray(){
		Set<Vertex> vSet = new HashSet<Vertex>();
		vSet.addAll(visitedVertices);
		return vSet.toArray(new Vertex[0]); 
	}
	              
	public void dump(){
		System.out.print("story: ");
		Vertex[] vs = getStoryAsArray();
		for(int i = 0; i < vs.length; i ++){
			System.out.print(vs[i].name + " ");
		}
		System.out.println();
	}
}
