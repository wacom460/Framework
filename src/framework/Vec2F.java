package framework;

public class Vec2F {
	public static final Vec2F zero = new Vec2F(0, 0);
	public float x = 0, y = 0;
	
	public Vec2F() {
		
	}
	
	public Vec2F(float x, float y) {
		set(x, y);
	}
	
	public void set(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public float distFrom(Vec2F point) {
		float dx = x - point.x;
        float dy = y - point.y;
		return (float) Math.sqrt(dx * dx + dy * dy);
	}
	
	public int hashCode() {
        return Float.hashCode(x) + Float.hashCode(y);
    }
	
	@Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Vec2F v = (Vec2F) obj;
        return x == v.x && y == v.y;
    }
	
	public void explode(float originX, float originY, float distX, float distY) {
		double angle = Math.atan2(y - originY, x - originX);
        x = (float) (originX + Math.cos(angle) * distX);
        y = (float) (originY + Math.sin(angle) * distY);
	}
}
