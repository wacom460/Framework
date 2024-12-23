package framework;

import org.lwjgl.input.Keyboard;

public class Input {
	public static boolean actionKey() {
		return Window.keyboardFF[Keyboard.KEY_RETURN];
	}

	public static boolean up() {
		return Window.keyboardFF[Keyboard.KEY_UP];
	}

	public static boolean down() {
		return Window.keyboardFF[Keyboard.KEY_DOWN];
	}

	public static boolean left() {
		return Window.keyboardFF[Keyboard.KEY_LEFT];
	}

	public static boolean right() {
		return Window.keyboardFF[Keyboard.KEY_RIGHT];
	}

	public static boolean w() {
		return Window.keyboardDown[Keyboard.KEY_W];
	}

	public static boolean a() {
		return Window.keyboardDown[Keyboard.KEY_A];
	}

	public static boolean s() {
		return Window.keyboardDown[Keyboard.KEY_S];
	}

	public static boolean d() {
		return Window.keyboardDown[Keyboard.KEY_D];
	}
}
