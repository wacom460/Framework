package framework.animation;

import java.util.ArrayList;
import java.util.List;

import framework.Clock;
import framework.MathStuff;
import framework.Window;
import framework.animation.easing.EaseType;
import framework.animation.easing.Easing;

public class Animated implements Runnable {
	private List<Keyframe> keyframes = new ArrayList<>();
	public Clock clock = new Clock();
	private boolean reversing, pingPong, valid = true;
	
	public Animated(boolean pingPong) {
		this.pingPong = pingPong;
		new Thread(this).start();		
	}

	public Keyframe add(float timeMs, float value, EaseType ease) {
		if(timeMs < 0) throw new RuntimeException("timeMs must be > 0!");
		Keyframe kf = new Keyframe(timeMs, value, ease);
		keyframes.add(kf);
		keyframes.sort((a, b) -> Float.compare(a.ms, b.ms));
		return kf;
	}
	
	public Animated frame(float timeMs, float value, EaseType ease) {
		add(timeMs, value, ease);
		return this;
	}
	
	public float get() {
		return get(clock.elapsedMs());
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
	
	public void cleanup() {
		valid = false;
	}

	@Override
	public void run() {
		while(Window.running && valid) {
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if(keyframes.size() < 1) continue;
			Keyframe last = keyframes.get(keyframes.size() - 1);
			if(clock.elapsedMs() > last.ms) {
				if(pingPong) reversing = !reversing;
				clock.reset();
				System.out.println("clock hit end");
			}
		}
	}
}
