package framework.rendering;

import framework.Color;
import framework.RectF;
import framework.Vec2F;
import framework.Window;

public class Text {
	public static final float charH = 32;
	public static final float charW = (charH / 2) - 2;
	
	public static char special(int i) {
		return (char)('~' + i + 1);
	}
	
	public static Vec2F measure(float scale, String str) {
		float maxW = 0, x = 0, y = 0, maxH = charH * scale;
		for (int i = 0; i < str.length(); ++i) {
			char c = str.charAt(i);
			if (c == '\n') {
				y += charH * scale;
				if (y > maxH) maxH = y;
				continue;
			}
			x += charW * scale;
			if (x > maxW) maxW = x;
		}

		return new Vec2F(maxW, maxH);
	}
	
	public static void draw(DrawList dl, float x, float y,
			float scale, float originX, float originY, Color colorTop, Color colorBottom, String str) {
		final float walk = charW * scale;
		final float ox = x;
		final float oy = y;
		Vec2F rect = measure(scale, str);
		x = ox - (rect.x * originX);
		y = oy - (rect.y * originY) - (walk / 2);

		/*dl.line(
			new Vec2F(x, y), 
			new Vec2F(x + 2, y),
			new Color(1, 0, 0, 1),
			new Color(1, 1, 0, 1),
			new Color(1, 0, 1, 1),
			new Color(0, 0, 1, 1),
			Vec2F.zero,
			Vec2F.zero,
			Vec2F.zero,
			Vec2F.zero,
			3,
			3,
			3,
			3
		);*/
		
		for (int i = 0; i < str.length(); ++i) {
			char c = str.charAt(i);
			if(c != ' ') {
				if (c == '\n') {
					x = ox;
					y += charH * scale;
					continue;
				}
				RectF aloc = Window.fontTileSheet.getTexRectChar(c);
				dl.rect(new RectF(x, y, walk, charH * scale), aloc, colorTop, colorTop, colorBottom, colorBottom);
			}
			x += walk;
		}
	}
}
