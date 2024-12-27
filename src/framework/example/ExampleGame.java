package framework.example;

import framework.Color;
import framework.RectF;
import framework.Window;
import framework.animation.Animated;
import framework.animation.easing.EaseType;

public class ExampleGame extends Window {
	Animated anim;
	
	public ExampleGame() {
		super("Example Game", width, height, "atlas.png", 64, 64, 0, 0);
	}

	@Override
	protected void init() {
		anim = new Animated(true, true)
			.frame(0, 0f, EaseType.None)
			.frame(1500, (float)Math.PI * 2.0f, EaseType.None)
			.frameDiff(500, -10, EaseType.None);
		dl.debug = true;
		showFps = true;
	}
	
	@Override
	protected void frame() {
		dl.rect(new RectF(150, 130, 100, 100), RectF.zero, Color.red, Color.red, Color.black, Color.red);
		dl.transformCenter(0, 0, 0, 0, 1, 1, anim.get(), 1);
	}

	public static void main(String[] args) {
		new ExampleGame();
	}	
}
