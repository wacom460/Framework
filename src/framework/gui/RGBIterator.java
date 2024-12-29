package framework.gui;

import java.util.Random;

import framework.Clock;
import framework.Color;

public class RGBIterator {
	public enum Operator {
		
		PLUS, MINUS, DIVIDE, SUBTRACT;

	}
	public enum ColorSel {
		
		R, G, B;

	}
	private Random rand = new Random();
	private ColorSel sel = ColorSel.R;
	private Operator op = Operator.PLUS;
	private Clock clock = new Clock();
	private int dr, dg, db, r, g, b, c, p;
	
	public RGBIterator(int sr, int sg, int sb) {
		dr = sr;
		dg = sg;
		db = sb;
		r = rand.nextInt(dr);
		g = rand.nextInt(dg);
		b = rand.nextInt(db);
	}
	
	public Color getNext(float alpha) {
		return getNext(alpha, 7);
	}
	
	public Color getNext(float alpha, int add) {
		if(clock.once(2)) {
			if(op == Operator.PLUS) {
				if(r > 254) sel = ColorSel.G;
				if(g > 254)  sel = ColorSel.B;
				if(b > 254) {
					sel = ColorSel.R;
					op = Operator.MINUS;
				}
			} else {
				if(r < 100) sel = ColorSel.G;
				if(g < 0) sel = ColorSel.B;
				if(b < 130) {
					sel = ColorSel.R;
					op = Operator.PLUS;
				}
			}
			if(op == Operator.PLUS) {
				if(sel == ColorSel.R) r += p;
				if(sel == ColorSel.G) g += p;
				if(sel == ColorSel.B) b += p;
			} else {
				if(sel == ColorSel.R) r -= p;
				if(sel == ColorSel.G) g -= p;
				if(sel == ColorSel.B) b -= p;
			}
		}
		r = r > 255 ? 255 : r;
		g = g > 255 ? 255 : g;
		b = b > 255 ? 255 : b;
		Color color = new Color((float)r / 255.0f, (float)g / 255.0f, (float)b / 255.0f, alpha);
		c = ((r + g + b) / 3) / 86;
		p = (c < 1 ? 1 : c) * add;
		return color;
	}

	public int getR() {
		return r;
	}
	
	public void setR(int nr) {
		r = nr;
	}
	
	 public int getG() {
		 return g;
	 }
	 
	public void setG(int ng) {
		g = ng;
	}
	 
	 public int getB() {
		 return b;
	 }
	 
	public void setB(int nb) {
		b = nb;
	}
	
	public void setAll(int nr, int ng, int nb) {
		r = nr;
		g = ng;
		b = nb;
	}
	 
	 public int getC() {
		 return c;
	 }
	
	public void reset() {
		r = rand.nextInt(dr);
		g = rand.nextInt(dg);
		b = rand.nextInt(db);
	}
}