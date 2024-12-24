package framework.animation;

import framework.animation.easing.EaseType;

public class Keyframe {
	public float ms;
	public float value;
	public EaseType ease;
	
	public Keyframe(float ms, float value, EaseType ease) {
		this.ms = ms;
		this.value = value;
		this.ease = ease;
	}
	
	public Keyframe(Keyframe from) {
		this.ms = from.ms;
		this.value = from.value;
	}
}
