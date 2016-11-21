package projects.cyberDetective;

public class SensorRecording {
	
	public static final int ACTIVATION = 1;
	public static final int DEACTIVATION = 2;
	
	public Sensor sensor;
	public int event;

	public SensorRecording(Sensor sensor, int event) {
		super();
		this.sensor = sensor;
		this.event = event;
	}
}
