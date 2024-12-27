package framework.gui;

import framework.Input;
import framework.Vec2F;
import framework.Window;

import java.util.ArrayList;
import java.util.List;

public class Widget {
	public String text = new String();
	public int optionIdx = 0;
	public List<String> options = new ArrayList<>();
	public Vec2F pos = new Vec2F(0, 0);
	public Vec2F origin = new Vec2F(0, 0);
	public WidgetType type;
	public Widget up = null, down = null, left = null, right = null, last = null;
	private boolean clicked = false;

	public boolean wasClicked() {
		if (clicked) {
			clicked = false;
			return true;
		}
		return false;
	}
	
	public void clicked() {
		
	}
	
	public void decorations() {
		
	}

	public void update() {
		if (this == Window.widget) {
			if (Input.up() && up != null)
				Window.setWidget(up);
			if (Input.down() && down != null)
				Window.setWidget(down);
			if (Input.left() && left != null)
				Window.setWidget(left);
			if (Input.right() && right != null)
				Window.setWidget(right);
			if (Input.actionKey())
				clicked = true;
		}
	}
	
	public void render() {
		
	}

	public Widget(WidgetType type) {
		this.type = type;
		Window.widgets.add(this);
		options.add("None");
	}

	public Widget(WidgetType type, String text) {
		this(type);
		this.text = text;
	}

	public Widget setPos(float x, float y) {
		pos.set(x, y);
		return this;
	}

	public Widget setOrigin(float x, float y) {
		origin.set(x, y);
		return this;
	}
}
