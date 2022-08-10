package pattern_study.Command;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;

public class DrawCanvas extends Canvas implements Drawable {
	private static final long serialVersionUID = 1L;
	
	// 색상
	private Color color;
	// 펜 두께
	private int radius = 6;
	private MacroCommand history;
	
	public DrawCanvas(int width, int height, MacroCommand history, Color color) {
		setSize(width, height);
		setBackground(Color.white);
		this.color = color;
		this.history = history;
	}
	public void paint(Graphics g) {
		history.execute();
	}
	@Override
	public void draw(int x, int y) {
		Graphics g = getGraphics();
		g.setColor(color);
		g.fillOval(x - radius, y - radius, radius * 2, radius * 2);
	}
	@Override
	public void setColor(Color color) {
		this.color = color;
	}
}
