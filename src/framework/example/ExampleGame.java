package framework.example;

import framework.Color;
import framework.RectF;
import framework.Window;
import framework.rendering.Text;

public class ExampleGame extends Window {

	public ExampleGame() {
		super("Example Game", width, height, "atlas.png", 32, 32, 0, 0);
	}

	@Override
	protected void init() {

	}

	@Override
	protected void cleanup() {
		
	}
	
	@Override
	protected void frame() {
		Color c = new Color(1, 0, 0, 1);
		dl.rect(new RectF(50, 50, 100, 100), RectF.zero, c, c, c, new Color(0,
				0, 1, 1));
		dl.checkpoint();
		Text.draw(dl, 100, 200, 1, 0, 0, c, c, "hello");
		dl.transform(0, 300, 100, 0, 1.25f, 1.25f, 0.4f, 1);
	}

	public static void main(String[] args) {
		new ExampleGame();
	}	
}
