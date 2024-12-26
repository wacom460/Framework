package framework;

public class Color {
	public static final Color white = new Color(1, 1, 1, 1);
	public static final Color black = new Color(0, 0, 0, 1);
	public static final Color red = new Color(1, 0, 0, 1);
	public float r = 1, g = 1, b = 1, a = 1;
	public Color() {
		
	}
	public Color(float r, float g, float b, float a) {
		this.r = r;
		this.g = g;
		this.b = b;
		this.a = a;
	}
	
	public Color(double r, double g, double b, double a) {
		this.r = (float) r;
		this.g = (float) g;
		this.b = (float) b;
		this.a = (float) a;
	}
}
