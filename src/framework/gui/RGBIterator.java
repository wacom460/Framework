package framework.gui;

import java.util.Date;
import java.util.Random;

import framework.Color;

public class RGBIterator {
	
	private Random rand = new Random();
	private EColorSel sel = EColorSel.R;
	private EOperator op = EOperator.PLUS;
	private long colorInterval, colorTime = new Date().getTime() + colorInterval;
	private int dr, dg, db, r, g, b, c, p = 100;
	
	public RGBIterator(int sr, int sg, int sb) {
		dr = sr;
		dg = sg;
		db = sb;
		r = rand.nextInt(dr);
		g = rand.nextInt(dg);
		b = rand.nextInt(db);
	}
	
	public Color getNext(float alpha) {
		if(colorTime < new Date().getTime()) {
			colorTime = new Date().getTime() + colorInterval;
			if(op == EOperator.PLUS) {
				if(r > 254) sel = EColorSel.G;
				if(g > 254)  sel = EColorSel.B;
				if(b > 254) {
					sel = EColorSel.R;
					op = EOperator.MINUS;
				}
			} else {
				if(r < 100) sel = EColorSel.G;
				if(g < 0) sel = EColorSel.B;
				if(b < 130) {
					sel = EColorSel.R;
					op = EOperator.PLUS;
				}
			}
			if(op == EOperator.PLUS) {
				if(sel == EColorSel.R) r += p;
				if(sel == EColorSel.G) g += p;
				if(sel == EColorSel.B) b += p;
			} else {
				if(sel == EColorSel.R) r -= p;
				if(sel == EColorSel.G) g -= p;
				if(sel == EColorSel.B) b -= p;
			}
		}
		r = r > 255 ? 255 : r;
		g = g > 255 ? 255 : g;
		b = b > 255 ? 255 : b;
		Color color = new Color((float)r / 255.0f, (float)g / 255.0f, (float)b / 255.0f, alpha);
		c = ((r + g + b) / 3) / 86;
		p = c < 1 ? 1 : c;
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