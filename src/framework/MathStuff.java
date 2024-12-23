package framework;

public class MathStuff {
	public static <T extends Number> double scale(T value, T fromMin, T fromMax, T toMin, T toMax) {
		if(fromMin == fromMax) return toMin.doubleValue();
		double ret = ((toMax.doubleValue() - toMin.doubleValue()) * (value.doubleValue() - fromMin.doubleValue()) / (fromMax.doubleValue() - fromMin.doubleValue())) + toMin.doubleValue();
		return ret;
	}
}