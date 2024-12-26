package framework;

import org.lwjgl.input.Keyboard;

public class Input {
	public static boolean actionKey() {
		return Window.kbPressed[Keyboard.KEY_RETURN];
	}

	public static boolean up() {
		return Window.kbPressed[Keyboard.KEY_UP];
	}

	public static boolean down() {
		return Window.kbPressed[Keyboard.KEY_DOWN];
	}

	public static boolean left() {
		return Window.kbPressed[Keyboard.KEY_LEFT];
	}

	public static boolean right() {
		return Window.kbPressed[Keyboard.KEY_RIGHT];
	}

	public static boolean w() {
		return Window.kbHeld[Keyboard.KEY_W];
	}

	public static boolean a() {
		return Window.kbHeld[Keyboard.KEY_A];
	}

	public static boolean s() {
		return Window.kbHeld[Keyboard.KEY_S];
	}

	public static boolean d() {
		return Window.kbHeld[Keyboard.KEY_D];
	}
}
