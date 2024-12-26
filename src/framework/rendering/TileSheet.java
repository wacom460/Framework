package framework.rendering;

import framework.RectF;

public class TileSheet {
	public Tex tex;
	public int width, height;
	float iw, ih;
	
	public TileSheet(Tex tex, int width, int height) {
		this.tex = tex;
		this.width = width;
		this.height = height;
		iw = tex.texture.getImageWidth();
		ih = tex.texture.getImageHeight();
	}
	
	public RectF getTexRectChar(char ch) {
		return getTexRect(ch - ' ');
	}
	
	public RectF getTexRect(int id) {
		float tw = iw / width;
		float th = ih / height;
		float x = (id % width) * tw;
		float y = (id / width) * th;
		return new RectF(x / iw, y / ih, tw / iw, th / iw);
	}
}
