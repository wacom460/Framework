package framework.animation;

import java.util.ArrayList;
import java.util.List;

import framework.MathStuff;
import framework.Timer;
import framework.animation.easing.EaseType;
import framework.animation.easing.Easing;

public class Animated extends Timer /*implements Runnable*/ {
	private List<Keyframe> keyframes = new ArrayList<>();
	//private boolean reversing, pingPong, repeat, valid = true;
	private Keyframe skf = new Keyframe(0, 0, EaseType.None);
	
	public Animated(boolean pingPong, boolean repeat) {		
		this.pingPong = pingPong;
		/*if(pingPong) repeat = true;*/
		this.repeat = repeat;
		/*new Thread(this).start();*/	
		keyframes.add(skf);
	}

	public Keyframe add(float timeMs, float value, EaseType ease) {
		if(timeMs < 0) throw new RuntimeException("timeMs must be > 0!");
		if(timeMs > maxMs) maxMs = timeMs;
		Keyframe last = keyframes.get(keyframes.size() - 1);
		if(timeMs < last.ms) throw new RuntimeException("kf time must be greater than or equal to 0 if no keyframes present, or the time of the last keyframe.");
		Keyframe kf = new Keyframe(timeMs, value, ease);
		if(keyframes.contains(skf)) keyframes.remove(skf);
		keyframes.add(kf);
		keyframes.sort((a, b) -> Float.compare(a.ms, b.ms));
		return kf;
	}
	
	public Animated frame(float timeMs, float value, EaseType ease) {
		add(timeMs, value, ease);
		return this;
	}
	
	public Animated frameDiff(float timeDelta, float valueDelta, EaseType ease) {
		Keyframe last = keyframes.get(keyframes.size() - 1);
		add(last.ms + timeDelta, last.value + valueDelta, ease);
		return this;
	}
	
	public void reverseOutro() {
		reversed = true;
		pingPong = false;
	}
	
	public float get() {
		return get(ms);
	}
	
	public float get(float timeMs) {
		if(keyframes.size() < 2) return keyframes.get(0).value;
		if(keyframes.size() < 1) return 0.0f;
		int i = 0;
		for(Keyframe kf : keyframes) {
			if(timeMs >= kf.ms) break;
			else i++;
		}
		Keyframe kf = keyframes.get(i);
		Keyframe nkf = i + 1 < keyframes.size() ? keyframes.get(i + 1) : null;
		if(nkf == null) return kf.value;
		return (float) Easing.apply(kf.ease, MathStuff.scale(timeMs, kf.ms, nkf.ms, kf.value, nkf.value), kf.value, nkf.value);
	}
	
	public void update() {
		if(keyframes.size() >= 0) {
			Keyframe last = keyframes.get(keyframes.size() - 1);
			maxMs = last.ms;
//			if(maxMs != last.ms) throw new RuntimeException("invalid state");
		}
		super.update();
	}
	
	/*public void cleanup() {
		valid = false;
	}*/
	
	

/*	@Override
	public void run() {
		while(Window.running && valid) {
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if(keyframes.size() < 1) continue;
			Keyframe last = keyframes.get(keyframes.size() - 1);
			if(timer.ms > last.ms) {
				if(pingPong) reversing = !reversing;
				if(repeat) timer.reset();
				//System.out.println("clock hit end");
			}
		}
	}*/
}
