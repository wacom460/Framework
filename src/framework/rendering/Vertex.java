package framework.rendering;

import framework.Color;
import framework.Vec2F;

public class Vertex {
	public Vec2F pos, texPos;
	public Color color;
	
	public Vertex(Vec2F pos, Vec2F texPos, Color color) {
		this.pos = pos;
		this.texPos = texPos;
		this.color = color;
	}
	
	public Vertex(float x, float y, float tx, float ty, float  r, float  g, float  b, float  a) {
		this.pos = new Vec2F(x, y);
		this.texPos = new Vec2F(tx, ty);
		this.color = new Color(r, g, b, a);
	}
	
	public Vertex(float x, float y, float tx, float ty, Color color) {
		this.pos = new Vec2F(x, y);
		this.texPos = new Vec2F(tx, ty);
		this.color = color;
	}
}
