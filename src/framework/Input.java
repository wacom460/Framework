package framework;

import org.lwjgl.input.Keyboard;

public class Input {
	public static boolean actionKey() {
		return Window.kbFF[Keyboard.KEY_RETURN];
	}

	public static boolean up() {
		return Window.kbFF[Keyboard.KEY_UP];
	}

	public static boolean down() {
		return Window.kbFF[Keyboard.KEY_DOWN];
	}

	public static boolean left() {
		return Window.kbFF[Keyboard.KEY_LEFT];
	}

	public static boolean right() {
		return Window.kbFF[Keyboard.KEY_RIGHT];
	}

	public static boolean w() {
		return Window.kbDown[Keyboard.KEY_W];
	}

	public static boolean a() {
		return Window.kbDown[Keyboard.KEY_A];
	}

	public static boolean s() {
		return Window.kbDown[Keyboard.KEY_S];
	}

	public static boolean d() {
		return Window.kbDown[Keyboard.KEY_D];
	}
}
