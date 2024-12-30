package framework;

public class RectF {
	public Vec2F pos = new Vec2F(0, 0), size = new Vec2F(1, 1);
	public static final RectF zero = new RectF(0);
	public static final RectF whole = new RectF(0, 0, 1, 1);
	
	public RectF() {
		
	}
	
	@Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        RectF r = (RectF) obj;
        return pos.x == r.pos.x && pos.y == r.pos.y && size.x == r.size.x && size.y == r.size.y;
    }
	
	public void set(RectF from) {
		pos.set(from.pos);
		size.set(from.size);
	}
	
	public RectF(RectF from) {
		set(from);
	}
	
	@Override
	protected Object clone() throws CloneNotSupportedException {
		return new RectF(this);
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
	
	public Vec2F center() {
		return new Vec2F(pos.x + (size.x / 2), pos.y + (size.y / 2));
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
	
	public static RectF tween(RectF start, RectF end, float t) {
		Vec2F nPos = Vec2F.tween(start.pos, end.pos, t);
		Vec2F nSize = Vec2F.tween(start.size, end.size, t);
		return new RectF(nPos.x, nPos.y, nSize.x, nSize.y);
	}
}
