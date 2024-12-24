package framework.rendering;

import framework.Color;
import framework.RectF;
import framework.Window;

public class Text {
	public static char special(int i) {
		return (char)('~' + i + 1);
	}
	public static void draw(DrawList dl, float x, float y,
			float scale, float originX, float originY, Color colorTop, Color colorBottom, String str) {
		final float charH = 32;
		final float walk = ((charH / 2) - 2) * scale;
		final float ox = x;
		final float oy = y;
		float maxW = 0;
		float maxH = charH * scale;
		for (int i = 0; i < str.length(); ++i) {
			char c = str.charAt(i);
			if (c == '\n') {
				x = ox;
				y += charH * scale;
				if (y - oy > maxH)
					maxH = y - oy;
				continue;
			}
			x += walk;
			if (x - ox > maxW)
				maxW = x - ox;
		}

		x = ox - (maxW * originX);
		y = oy - (maxH * originY) - (walk / 2);

//		dl.line(
//			new Vec2F(x, y), 
//			new Vec2F(x + 1, y + 1),
//			new Color(1, 0, 0, 1),
//			new Color(1, 0, 0, 1),
//			new Color(1, 0, 0, 1),
//			new Color(1, 0, 0, 1),
//			Vec2F.zero,
//			Vec2F.zero,
//			Vec2F.zero,
//			Vec2F.zero,
//			3,
//			3
//		);
		
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
