package framework.example;

import org.lwjgl.input.Mouse;

import framework.Color;
import framework.RectF;
import framework.Window;
import framework.animation.Animated;
import framework.animation.easing.EaseType;
import framework.rendering.Text;

public class ExampleGame extends Window {
	Animated anim;
	
	public ExampleGame() {
		super("Example Game", width, height, "atlas.png", 32, 32, 0, 0);
	}

	@Override
	protected void init() {
		anim = new Animated(true)
			.frame(0, 1.0f, EaseType.None)
			.frame(5000, 5.0f, EaseType.None);
		dl.debug = true;
	}

	@Override
	protected void cleanup() {
		
	}
	
	@Override
	protected void frame() {
		Color c = new Color(1, 0, 0, 1);
		dl.rect(new RectF(50, 50, 100, 100), RectF.zero, c, c, c, new Color(0,
				0, 1, 1));
		dl.transformCenter(0, 0, 0, 0, 1, 1, anim.get(), 1);
		dl.checkpoint();
		Text.draw(dl, width / 2, height / 2, 1, 0.5f, 0.5f, Color.white, Color.black, "Middle");
		dl.transform(Mouse.getX(), Mouse.getY(), 0, 0, anim.get(), 1.2f, 0, 1);
		//dl.transform(Mouse.getX(), Mouse.getY(), 0, 0, anim.get(), anim.get(), anim.get(), 1);
		dl.checkpoint();
		Text.draw(dl, 100, 200, 1, 0, 0, c, c, "hello " + anim.get() + " " + anim.clock.elapsedMs());
		//System.out.println(anim.clock.elapsedMs());
		//System.out.println(anim.get());
		dl.transform(0, 0, 250, 0, 1.25f, 1.25f, 0.4f, 1);
	}

	public static void main(String[] args) {
		new ExampleGame();
	}	
}
