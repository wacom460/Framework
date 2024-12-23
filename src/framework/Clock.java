package framework;

public class Clock {
	private long time;
	private boolean reversing;
	private boolean pingPong;
	
	public Clock(boolean pingPong) {
		this.pingPong = pingPong;
		reset();
	}
	
	public void reset() {
		if(pingPong) reversing = !reversing;
		time = System.nanoTime();
	}
	
	public boolean once(float ms) {
		if(elapsedMs() >= ms) {
			reset();
			return true;
		}
		return false;
	}
	
	public float elapsedMs() {
		return (System.nanoTime() - time) / 1_000_000;
	}
}
