package framework;

public class MathStuff {
	public static float scale(float value, float fromMin, float fromMax, float toMin, float toMax) {
		if(fromMin == fromMax) return toMin;
		float ret = ((toMax - toMin) * (value - fromMin) / (fromMax - fromMin)) + toMin;
		return clamp(ret, toMin, toMax);
	}
	
	public static float clamp(float value, float min, float max) {
		return value < min ? min : value > max ? max : value;
	}
}