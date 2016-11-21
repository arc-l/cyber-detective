package projects.cyberDetective;

public class Edge {

	public Vertex[] vertices = null;
	public int id;
	
	public Edge(Vertex v1, Vertex v2) {
		super();
		vertices = new Vertex[2];
		vertices[0] = v1;
		vertices[1] = v2;
		this.id = getEdgeId(v1, v2);
	}

	public static int getEdgeId(Vertex v1, Vertex v2){
		return getEdgeId(v1.id, v2.id);
	}
	
	public static int getEdgeId(int v1, int v2){
		if(v1 < v2){
			return v1*65536 + v2;
		}
		else{
			return v2*65536 + v1;
		}
	}
	
	/**
	 * Makes copy of the edge, duplicating the vertices as well
	 * @return
	 */
	public Edge getCopy(){
		return new Edge(vertices[0].getCopy(), vertices[1].getCopy());
	}
}
