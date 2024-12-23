package framework;

public class RectF {
	public Vec2F pos = new Vec2F(0, 0), size = new Vec2F(1, 1);
	public static final RectF zero = new RectF(0);
	
	public RectF() {
		
	}
	
	public void subPosFromSize() {
		size.x -= pos.x;
		size.y -= pos.y;
	}

	public RectF(float all) {
		set(all, all, all, all);
	}
	
	public RectF(float x, float y, float w, float h) {
		set(x, y, w, h);
	}
	
	public void set(float x, float y, float w, float h) {
		pos.set(x, y);
		size.set(w, h);
	}
	
	public void setPos(float x, float y) {
		pos.set(x, y);
	}
	
	public void setSize(float x, float y) {
		size.set(x, y);
	}
	
	public Vec2F topLeft() {
		return new Vec2F(pos.x, pos.y);
	}
	
	public Vec2F topRight() {
		return new Vec2F(pos.x + size.x, pos.y);
	}
	
	public Vec2F bottomLeft() {
		return new Vec2F(pos.x, pos.y + size.y);
	}
	
	public Vec2F bottomRight() {
		return new Vec2F(pos.x + size.x, pos.y + size.y);
	}
}
