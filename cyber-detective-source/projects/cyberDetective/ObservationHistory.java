package projects.cyberDetective;

import java.util.Vector;

public class ObservationHistory {
	
	public Vector<SensorRecording> sensorRecordings = null;

	public ObservationHistory() {
		sensorRecordings = new Vector<SensorRecording>();
	}
	
	public void addSensorRecording(SensorRecording v){
		sensorRecordings.add(v);
	}
	
	public SensorRecording[] getOHAsArray(){
		return sensorRecordings.toArray(new SensorRecording[0]); 
	}
	
	public void dump(){
		System.out.print("observation history: ");
		SensorRecording[] vs = getOHAsArray();
		for(int i = 0; i < vs.length; i ++){
			System.out.print(vs[i].sensor.name + (vs[i].event==SensorRecording.ACTIVATION?"[A]":"[D]"));
		}
		System.out.println();
	}
	
}
