package framework.gui;

import java.util.ArrayList;
import java.util.List;

import framework.Color;
import framework.Input;
import framework.RectF;
import framework.Vec2F;
import framework.Window;
import framework.rendering.Text;

public class Widget {
	public String text = new String();
	public int optionIdx = 0;
	public List<String> options = new ArrayList<>();
	public RectF rect = new RectF();
	public Vec2F origin = new Vec2F(0, 0);
	public WidgetType type;
	public Widget up = null, down = null, left = null, right = null, last = null;
//	private boolean clicked = false;

	/*
	 * public boolean wasClicked() { if (clicked) { clicked = false; return true; }
	 * return false; }
	 */
	
	public void init() {
		
	}
	
	public void clicked() {
		
	}
	
	public void decorations() {
		
	}

	public void update() {
		if (this == Window.getWidget()) {
			if (Input.up() && up != null)
				Window.setWidget(up);
			if (Input.down() && down != null)
				Window.setWidget(down);
			if (Input.left() && left != null)
				Window.setWidget(left);
			if (Input.right() && right != null)
				Window.setWidget(right);
			if (Input.actionKey()) {
				/*clicked = true;*/
				Window.audio.playSfx("menu_action");
				clicked();
			}
		}
	}
	
	public void render() {
		switch(type) {
		case TextButton:
			
		case Text:
			Text.draw(Window.dl, rect.pos.x, rect.pos.y, 1, 0, 0, Color.red, Color.white, text);
			break;
		default:
			break;		
		}
	}

	public Widget(WidgetType type) {
		this.type = type;
		Window.widgets.add(this);
		options.add("None");
		init();
	}

	public Widget(WidgetType type, String text) {
		this(type);
		this.text = text;
		init();
	}

	public Widget setPos(float x, float y) {
		rect.pos.set(x, y);
		return this;
	}

	public Widget setOrigin(float x, float y) {
		origin.set(x, y);
		return this;
	}
}
