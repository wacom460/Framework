package framework;

public class Clock {
	private long time = System.nanoTime();

	public void reset() {
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
