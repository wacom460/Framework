package framework;

import java.io.Serializable;

public class Vec2I implements Serializable {
	private static final long serialVersionUID = -1775821520949968816L;
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
	
	@Override
	public Vec2I clone() {
		return new Vec2I(x, y);
	}
}
