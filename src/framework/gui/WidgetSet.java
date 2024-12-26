package framework.gui;

import java.util.ArrayList;
import java.util.List;

public class WidgetSet {
	public WidgetSetType type = WidgetSetType.Vertical;
	public List<Widget> widgets = new ArrayList<>();
	
	public WidgetSet(WidgetSetType type) {
		this.type = type;
	}
	
	public void decorations() {
		
	}
	
	public void update() {
		
	}
	
	public void render() {
		
	}
	
	public WidgetSet add(Widget w) {
		widgets.add(w);
		return this;
	}
	
	public WidgetSet addUnder(Widget w) {
		if(widgets.size() < 1) return add(w);
		Widget bw = widgets.get(widgets.size() - 1);
		widgets.add(w);
		bw.down = w;
		return this;
	}
}
