package framework.rendering;

import java.util.List;

import framework.RectF;
import framework.Vec2F;
import framework.Window;

public class Triangle {
	Vertex[] verts = new Vertex[3];
	
	public Triangle(Vertex v1, Vertex v2, Vertex v3) {
		verts[0] = v1;
		verts[1] = v2;
		verts[2] = v3;
	}
	
	public void transform(float originX, float originY, float transX, float transY, float scaleX, float scaleY, float rotRad, float opacityMultiplier) {
		originX = Window.width - originX; 
	    for (int i = 0; i < 3; ++i) {
	        float translatedX = verts[i].pos.x - originX + transX;
	        float translatedY = verts[i].pos.y - originY + transY;
	        float rotatedX = (float) (translatedX * Math.cos((double)rotRad) - translatedY * Math.sin((double)rotRad));
	        float rotatedY = (float) (translatedX * Math.sin((double)rotRad) + translatedY * Math.cos((double)rotRad));
	        rotatedX *= scaleX;
	        rotatedY *= scaleY;
	        verts[i].pos.x = rotatedX + originX;
	        verts[i].pos.y = rotatedY + originY;
	        verts[i].color.a *= opacityMultiplier;
	    }
	}
	
	public Vec2F getCenter() {
        float x = (verts[0].pos.x + verts[1].pos.x + verts[2].pos.x) / 3.0f;
        float y = (verts[0].pos.y + verts[1].pos.y + verts[2].pos.y) / 3.0f;
        return new Vec2F(x, y);
    }
	
	public Triangle clone() {
		return new Triangle(
			new Vertex(verts[0].pos.x, verts[0].pos.y, verts[0].texPos.x, verts[0].texPos.x, verts[0].color.r, verts[0].color.g, verts[0].color.b, verts[0].color.a),
			new Vertex(verts[1].pos.x, verts[1].pos.y, verts[1].texPos.x, verts[1].texPos.x, verts[1].color.r, verts[1].color.g, verts[1].color.b, verts[1].color.a),
			new Vertex(verts[2].pos.x, verts[2].pos.y, verts[2].texPos.x, verts[2].texPos.x, verts[2].color.r, verts[2].color.g, verts[2].color.b, verts[2].color.a)
		);
	}
	
	public static Vec2F findCenter(List<Triangle> triangles) {
		if(triangles.size() < 1) return Vec2F.zero;
		float sumX = 0, sumY = 0;
		final int count = triangles.size();
		for(Triangle t : triangles) {
			final Vec2F center = t.getCenter();
			sumX += center.x;
			sumY += center.y;			
		}
		return new Vec2F(sumX / count, sumY / count);
	}
	
	public static RectF getBoundingBox(List<Triangle> triangles) {
		float minX = Float.MAX_VALUE, maxX = Float.MIN_VALUE;
		float minY = Float.MAX_VALUE, maxY = Float.MIN_VALUE;
		for(Triangle t : triangles) {
			for (int i = 0; i < 3; ++i) {
				Vertex v = t.verts[i];
				if(v.pos.x < minX) minX = v.pos.x;
				if(v.pos.y < minY) minY = v.pos.y;
				
				if(v.pos.x > maxX) maxX = v.pos.x;
				if(v.pos.y > maxY) maxY = v.pos.y;
			}
		}
		return new RectF(minX, minY, maxX - minX, maxY - minY);
	}
}
