package framework;

public class Timer {
	private Clock clock = new Clock();
	public float ms, minMs, maxMs = Float.MAX_VALUE, maxUpdateDelta = 20;
	public boolean reversed, pingPong, repeat;
	
	public Timer() {}
	
	public Timer(float ms, float minMs, float maxMs, boolean reversed) {
		this.ms = ms;
		this.minMs = minMs;
		this.maxMs = maxMs;
		this.reversed = reversed;
	}
	
	public void update() {
		final float bef = MathStuff.clamp(ms, minMs, maxMs);
		float eMs = MathStuff.clamp(clock.elapsedMs(), 0, maxUpdateDelta),
			oMs = bef;
		oMs += reversed ? -eMs : eMs;
		if(((oMs > maxMs && !reversed) || (oMs < minMs && reversed))) {
			if(pingPong) 
				reversed = !reversed;
			else if(repeat) {
				//reset();
				oMs = 0;
			}
		}
		ms = MathStuff.clamp(oMs, minMs, maxMs);
		clock.reset();
	}

	public void reset() {
		ms = 0;
		clock.reset();
	}
}
