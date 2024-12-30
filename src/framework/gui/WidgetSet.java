package framework.gui;

import java.util.ArrayList;
import java.util.List;

import framework.RectF;
import framework.rendering.Text;

public class WidgetSet {
	public WidgetSetType type = WidgetSetType.Vertical;
	public List<Widget> widgets = new ArrayList<>();
	public RectF rect;
	
	public WidgetSet(RectF rect, WidgetSetType type) {
		this.rect = rect;
		this.type = type;
	}
	
	public void decorations() {
		
	}
	
	public void update() {
		
	}
	
	public void render() {
		for(Widget w : widgets) w.render();
	}
	
	public WidgetSet add(Widget w) {
		widgets.add(w);
		return this;
	}
	
	public WidgetSet at(float x, float y, float originX, float originY) {
		rect.pos.x = x;
		rect.pos.y = y;
		return this;
	}
	
	public WidgetSet with(Widget w) {
		if(widgets.size() < 1) return add(w);
		Widget bw = widgets.get(widgets.size() - 1);
		widgets.add(w);
		if(type == WidgetSetType.Vertical) {
			bw.down = w;
			w.up = bw;
		} else if(type == WidgetSetType.Horizontal) {
			bw.right = w;
			w.left = bw;
		}
		return this;
	}
	
	public WidgetSet place() {
		float x = rect.pos.x, y = rect.pos.y;
		for(Widget w : widgets) {
			w.setPos(x, y);
			w.rect.setSize(w.text.length() * Text.charW, Text.charH);
			y += w.rect.size.y + 5;
		}
		return this;
	}
}
