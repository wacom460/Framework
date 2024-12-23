package framework.animation;

public class Keyframe<T extends Number> {
	public float ms;
	public T value;
	
	public Keyframe(float ms, T value) {
		this.ms = ms;
		this.value = value;
	}
}
