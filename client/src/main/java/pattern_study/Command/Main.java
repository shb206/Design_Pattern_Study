package pattern_study.Command;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;

public class Main extends JFrame implements ActionListener, MouseMotionListener, WindowListener {

	//그림 그린 이력
	private MacroCommand history = new MacroCommand();
	//그림 그리는 영역
	private DrawCanvas canvas = new DrawCanvas(400, 400, history, Color.RED);
	// 제거 버튼
	private JButton clearButton = new JButton("clear");
	// undo 버튼
	private JButton undoButton = new JButton("undo");
	// 색 버튼
	private JButton redButton = new JButton("red");
	private JButton blueButton = new JButton("blue");
	private JButton yellowButton = new JButton("yellow");
	
	public Main(String title) {
		super(title);
		
		
		this.addWindowListener(this);
		canvas.addMouseMotionListener(this);
		clearButton.addActionListener(this);
		undoButton.addActionListener(this);
		redButton.addActionListener(this);
		blueButton.addActionListener(this);
		yellowButton.addActionListener(this);
		
		Box buttonBox = new Box(BoxLayout.X_AXIS);
		buttonBox.add(clearButton);
		buttonBox.add(undoButton);
		Box colorBox = new Box(BoxLayout.X_AXIS);
		colorBox.add(redButton);
		colorBox.add(blueButton);
		colorBox.add(yellowButton);
		Box mainBox = new Box(BoxLayout.Y_AXIS);
		mainBox.add(buttonBox);
		mainBox.add(colorBox);
		mainBox.add(canvas);
		getContentPane().add(mainBox);
		
		pack();
		show();
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == clearButton) {
			history.clear();
			canvas.repaint();
		}
		if(e.getSource() == undoButton) {
			history.undo();
			canvas.repaint();
		}
		
		if(e.getSource() == redButton) {
			Command cmd = new ColorCommand(canvas, Color.red);
			history.append(cmd);
			cmd.execute();
		}
		if(e.getSource() == blueButton) {
			Command cmd = new ColorCommand(canvas, Color.blue);
			history.append(cmd);
			cmd.execute();
		}
		if(e.getSource() == yellowButton) {
			Command cmd = new ColorCommand(canvas, Color.yellow);
			history.append(cmd);
			cmd.execute();
		}
	}
	@Override
	public void mouseDragged(MouseEvent e) {
		Command cmd = new DrawCommand(canvas, e.getPoint());
		history.append(cmd);
		cmd.execute();
	}
	@Override
	public void windowClosing(WindowEvent e) {
		System.exit(0);
	}
	public static void main(String[] args) {
		new Main("Command Pattern Sample");
	}
	
	@Override
	public void windowOpened(WindowEvent e) {}
	@Override
	public void windowClosed(WindowEvent e) {}
	@Override
	public void windowIconified(WindowEvent e) {}
	@Override
	public void windowDeiconified(WindowEvent e) {}
	@Override
	public void windowActivated(WindowEvent e) {}
	@Override
	public void windowDeactivated(WindowEvent e) {}
	@Override
	public void mouseMoved(MouseEvent e) {}
}
