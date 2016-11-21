package common.util;

public class IDGenerator {
	
	private int id = 0;

	public synchronized int getNextId() {
		id += 1;
		return id;
	}

	
}
