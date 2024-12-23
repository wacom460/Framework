package framework;

public class Vec2I {
	public int x, y;
	
	public Vec2I() {
		
	}
	
	public Vec2I(int x, int y) {
		set(x, y);
	}
	
	public void set(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public int hashCode() {
        return Integer.hashCode(x) + Integer.hashCode(y);
    }
	
	@Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Vec2I v = (Vec2I) obj;
        return x == v.x && y == v.y;
    }
}
