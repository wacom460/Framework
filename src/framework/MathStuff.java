package framework;

public class MathStuff {
	public static float scale(float value, float fromMin, float fromMax, float toMin, float toMax) {
		if(fromMin == fromMax) return toMin;
		float ret = ((toMax - toMin) * (value - fromMin) / (fromMax - fromMin)) + toMin;
		return ret;
	}
}