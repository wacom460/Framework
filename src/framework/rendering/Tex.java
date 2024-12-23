package framework.rendering;
import java.io.FileInputStream;
import java.io.IOException;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

import framework.RectF;

public class Tex {
	public Texture texture;
	public String path;
	public Tex(String path) {
		this.path = path;
		reload();
	}
	
	public void reload() {
		if(texture != null) {
			texture.release();
			texture = null;
		}
        try {
        	FileInputStream fis = new FileInputStream("./res/" + path);
			texture = TextureLoader.getTexture("PNG", fis);
			fis.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
        bind();
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, 33071);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, 33071);
	}

	public RectF getTexRectPixelCoords(float x, float y, float w, float h) {
		return new RectF(x / texture.getImageWidth(), y / texture.getImageWidth(), w / texture.getImageWidth(), h / texture.getImageWidth());
	}
	
	public void bind() {
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture.getTextureID());
	}
}
