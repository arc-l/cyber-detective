package projects.cyberDetective;

public class BeamDetector extends Sensor {

	public BeamDetector(String name, Vertex[] sv){
		type = Sensor.SENSOR_TYPE_BEAM;
		this.name = name;
		sensorVertices = sv;
	}
}
