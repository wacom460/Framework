package framework.rendering;

import framework.Color;
import framework.RectF;
import framework.Vec2F;

import java.nio.FloatBuffer;
import java.util.ArrayList;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;

public class DrawList {
	public ArrayList<Triangle> workingTriangles = new ArrayList<>();
	public ArrayList<Triangle> trianglesFinal = new ArrayList<>();
	int /*vaoId = 0, */vboId = 0;
	public boolean debug = false;

	public void clear() {
		workingTriangles.clear();
		trianglesFinal.clear();
	}

	public Triangle add(Triangle t) {
		workingTriangles.add(t);
		return t;
	}

	public Triangle add(Vertex v1, Vertex v2, Vertex v3) {
		return add(new Triangle(v1, v2, v3));
	}

	public void line(Vec2F p1, Vec2F p2, 
			Color col1, Color col2, Color col3, Color col4, 
			Vec2F tc1, Vec2F tc2, Vec2F tc3, Vec2F tc4, 
			float thickness1, float thickness2, float thickness3, float thickness4) 
	{
		float px = -(p2.y - p1.y), py = p2.x - p1.x;
		float length = (float) Math.sqrt(px * px + py * py);
		px /= length;
		py /= length;

		float halfThickness1 = thickness1 / 2;
		float halfThickness2 = thickness2 / 2;
		float halfThickness3 = thickness3 / 2;
		float halfThickness4 = thickness4 / 2;

		float x11 = p1.x + halfThickness1 * px;
		float y11 = p1.y + halfThickness1 * py;
		
		float x12 = p1.x - halfThickness2 * px;
		float y12 = p1.y - halfThickness2 * py;

		float x21 = p2.x + halfThickness4 * px;
		float y21 = p2.y + halfThickness4 * py;

		float x22 = p2.x - halfThickness3 * px;
		float y22 = p2.y - halfThickness3 * py;

		add(
			new Vertex(x11, y11, tc1.x, tc1.y, col1), 
			new Vertex(x12, y12, tc2.x, tc2.y, col1), 
			new Vertex(x21, y21, tc3.x, tc3.y, col2)
		);
		
		add(
			new Vertex(x21, y21, tc3.x, tc3.y, col2), 
			new Vertex(x22, y22, tc4.x, tc4.y, col2), 
			new Vertex(x12, y12, tc2.x, tc2.y, col1)
		);
	}

	public void rect(RectF dest, RectF src, 
			Color c1, Color c2, Color c3, Color c4) 
	{
		add(new Vertex(dest.pos.x, dest.pos.y, src.pos.x, src.pos.y, c1),
				new Vertex(dest.pos.x + dest.size.x, dest.pos.y, src.pos.x
						+ src.size.x, src.pos.y, c2), new Vertex(dest.pos.x
						+ dest.size.x, dest.pos.y + dest.size.y, src.pos.x
						+ src.size.x, src.pos.y + src.size.y, c3));
		add(new Vertex(dest.pos.x, dest.pos.y, src.pos.x, src.pos.y, c1),
				new Vertex(dest.pos.x, dest.pos.y + dest.size.y, src.pos.x,
						src.pos.y + src.size.y, c4), new Vertex(dest.pos.x
						+ dest.size.x, dest.pos.y + dest.size.y, src.pos.x
						+ src.size.x, src.pos.y + src.size.y, c3));
	}

	//call when you want to make the previously added
	//triangles immutable to transforms
	public void checkpoint() {
		if(trianglesFinal.addAll(workingTriangles))
			workingTriangles.clear();
	}
	
	public void dupe() {
		for(Triangle t : workingTriangles)
			trianglesFinal.add(t.clone());
	}
	
	//call after supplying triangles
	public void transform(float originX, float originY, 
			float transX, float transY, 
			float scaleX, float scaleY, 
			float rotRad, 
			float opacityMultiplier) 
	{
		for(Triangle t : workingTriangles) {
			t.transform(originX, originY, transX, transY, scaleX, scaleY, rotRad, opacityMultiplier);
		}
	}
			
	public void transformCenter(float originOffsetNormalizedX, float originOffsetNormalizedY, 
			float transX, float transY, 
			float scaleX, float scaleY, 
			float rotRad, 
			float opacityMultiplier) 
	{
		if(workingTriangles.size() < 1) return;
		/*
		if(originOffsetNormalizedX < -1.0f) originOffsetNormalizedX = -1.0f;
		if(originOffsetNormalizedY < -1.0f) originOffsetNormalizedY = -1.0f;
		if(originOffsetNormalizedX > 1.0f) originOffsetNormalizedX = 1.0f;
		if(originOffsetNormalizedY > 1.0f) originOffsetNormalizedY = 1.0f;
		*/
		Vec2F center = Triangle.findCenter(workingTriangles);
		RectF bb = Triangle.getBoundingBox(workingTriangles);
		transform(center.x + ((bb.size.x / 2) * originOffsetNormalizedX), 
			center.y + ((bb.size.y / 2) * originOffsetNormalizedY), 
			transX, transY, scaleX, scaleY, rotRad, opacityMultiplier);
	}

	private FloatBuffer generateVertArray() {
		final int vertexFloatStride = 8;
		final int triFloatCount = 3 * vertexFloatStride;
		checkpoint();
		FloatBuffer ret = BufferUtils.createFloatBuffer(trianglesFinal.size()
				* triFloatCount);
		int capacity = ret.capacity();
		for (int i = 0; i < capacity; i += triFloatCount) {
			Triangle t = trianglesFinal.get(i / triFloatCount);
			float[] data = { 
				t.verts[0].pos.x, t.verts[0].pos.y, 
				t.verts[0].texPos.x, t.verts[0].texPos.y, 
				t.verts[0].color.r, 
				t.verts[0].color.g, 
				t.verts[0].color.b,
				t.verts[0].color.a,

				t.verts[1].pos.x, t.verts[1].pos.y, 
				t.verts[1].texPos.x, t.verts[1].texPos.y,
				t.verts[1].color.r, 
				t.verts[1].color.g, 
				t.verts[1].color.b, 
				t.verts[1].color.a,

				t.verts[2].pos.x, t.verts[2].pos.y, 
				t.verts[2].texPos.x, t.verts[2].texPos.y,
				t.verts[2].color.r, 
				t.verts[2].color.g, 
				t.verts[2].color.b, 
				t.verts[2].color.a
			};
			ret.put(data);
		}
		ret.flip();
		return ret;
	}

	public void sendToGPU() {
		FloatBuffer vertexBuffer = generateVertArray();
		if (trianglesFinal.size() < 1) return;
		cleanup();
		//vaoId = GL30.glGenVertexArrays();
		vboId = GL15.glGenBuffers();
		/*final int stride = (2 + 2 + 4) * Float.BYTES;*/

		//GL30.glBindVertexArray(vaoId);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboId);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, vertexBuffer, GL15.GL_STATIC_DRAW);
/*		GL20.glVertexAttribPointer(0, 2, GL11.GL_FLOAT, false, stride, 0);
		GL20.glEnableVertexAttribArray(0);
		GL20.glVertexAttribPointer(1, 2, GL11.GL_FLOAT, false, stride,
				2 * Float.BYTES);
		GL20.glEnableVertexAttribArray(1);
		GL20.glVertexAttribPointer(2, 4, GL11.GL_FLOAT, false, stride,
				4 * Float.BYTES);
		GL20.glEnableVertexAttribArray(2);*/
	}

	public void render() {
		//if (vaoId == 0) return;
		//GL30.glBindVertexArray(vaoId);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboId);

        GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		
		GL11.glEnableClientState(GL11.GL_VERTEX_ARRAY);
        GL11.glVertexPointer(2, GL11.GL_FLOAT, 8 * Float.BYTES, 0);
        GL11.glEnableClientState(GL11.GL_TEXTURE_COORD_ARRAY);
        GL11.glTexCoordPointer(2, GL11.GL_FLOAT, 8 * Float.BYTES, 2 * Float.BYTES);
        GL11.glEnableClientState(GL11.GL_COLOR_ARRAY);
        GL11.glColorPointer(4, GL11.GL_FLOAT, 8 * Float.BYTES, 4 * Float.BYTES);
		
		GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, trianglesFinal.size() * 3);
		
//		GL11.glDisableClientState(GL11.GL_VERTEX_ARRAY);
//        GL11.glDisableClientState(GL11.GL_TEXTURE_COORD_ARRAY);
//        GL11.glDisableClientState(GL11.GL_COLOR_ARRAY);
		
		if(debug) renderDebug();
	}
	
	public void renderDebug() {
		checkpoint();
		DrawList debDl = new DrawList();
		Color col = new Color(1, 1, 0, 1);
		for(Triangle t : trianglesFinal) {
			debDl.line(t.verts[0].pos, t.verts[1].pos, col, col, col, col, Vec2F.zero, Vec2F.zero, Vec2F.zero, Vec2F.zero, 1, 1, 1, 1);
			debDl.line(t.verts[0].pos, t.verts[2].pos, col, col, col, col, Vec2F.zero, Vec2F.zero, Vec2F.zero, Vec2F.zero, 1, 1, 1, 1);
			debDl.line(t.verts[1].pos, t.verts[2].pos, col, col, col, col, Vec2F.zero, Vec2F.zero, Vec2F.zero, Vec2F.zero, 1, 1, 1, 1);
		}
		debDl.sendToGPU();
		debDl.render();
	}

	public void cleanup() {
		if (vboId != 0) {
			GL15.glDeleteBuffers(vboId);
			vboId = 0;
		}
/*		if (vaoId != 0) {
			GL30.glDeleteVertexArrays(vaoId);
			vaoId = 0;
		}*/
	}
}
