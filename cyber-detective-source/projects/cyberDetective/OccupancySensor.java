package projects.cyberDetective;

public class OccupancySensor extends Sensor {

	public OccupancySensor(Vertex sv){
		type = Sensor.SENSOR_TYPE_OCCUPANCY;
		name = sv.name;
		sensorVertices = new Vertex[]{sv};
	}

}
