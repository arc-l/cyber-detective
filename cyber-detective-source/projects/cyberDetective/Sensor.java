package projects.cyberDetective;

public abstract class Sensor {
	
	public static final int SENSOR_TYPE_OCCUPANCY = 1;
	public static final int SENSOR_TYPE_BEAM = 2;
	
	public int type;
	public String name = null;
	public Vertex[] sensorVertices = null;
	
	public Vertex[] getGraphVertices() {
		return sensorVertices;
	}

	public int getType() {
		return type;
	}

}
