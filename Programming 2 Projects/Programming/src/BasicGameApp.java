import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.*;

public class BasicGameApp implements Runnable, KeyListener {

	final int WIDTH = 1000;
	final int HEIGHT = 700;

	JFrame frame;
	Canvas canvas;
	BufferStrategy bufferStrategy;
	Image image;

	private int xpos = 300;
	private int ypos = 300;
	private boolean left, right, up, down;

	public static void main(String[] args) {
		BasicGameApp ex = new BasicGameApp();
		new Thread(ex).start();
	}

	public BasicGameApp() {
		frame = new JFrame("Basic Game");

		JPanel panel = (JPanel) frame.getContentPane();
		panel.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		panel.setLayout(null);

		canvas = new Canvas();
		canvas.setBounds(0, 0, WIDTH, HEIGHT);
		canvas.setIgnoreRepaint(true);

		panel.add(canvas);

		canvas.addKeyListener(this);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setResizable(false);
		frame.setVisible(true);

		image = Toolkit.getDefaultToolkit().getImage(
				"astronaut.png");

		canvas.createBufferStrategy(2);
		bufferStrategy = canvas.getBufferStrategy();

		canvas.requestFocus();
	}// BasicGameApp()


	//thread
	public void run() {

		while (true) {
			// paint the graphics
			render();

			// move things
			if (right)
				xpos +=2;
			if (left)
				xpos-=2;
			if (up)
				ypos-=2;
			if (down)
				ypos+=2;

			//sleep
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {

			}
		}
	}

	//paints things on the screen using bufferStrategy
	private void render() {
		Graphics2D g = (Graphics2D) bufferStrategy.getDrawGraphics();
		g.clearRect(0, 0, WIDTH, HEIGHT);
		// g.fillRect((int) x, 0, 200, 200);
		g.drawImage(image, xpos, ypos, 100, 100, null);

		g.dispose();

		bufferStrategy.show();
	}

	// REQUIRED KEYBOARD METHODS
	public void keyPressed(KeyEvent e) {
		char key = e.getKeyChar();
		System.out.println("Key Pressed: " + key);

		switch (key) {
		case 'w':
			up = true;
			break;

		case 's':
			down = true;
			break;

		case 'a':
			left = true;
			break;

		case 'd':
			right = true;
			break;
		}

	}

	public void keyReleased(KeyEvent e) {
		char key = e.getKeyChar();
		System.out.println("Key Pressed: " + key);

		switch (key) {
		case 'w':
			up = false;
			break;

		case 's':
			down = false;
			break;

		case 'a':
			left = false;
			break;

		case 'd':
			right = false;
			break;
		}
	}

	public void keyTyped(KeyEvent e) {
		// The getKeyModifiers method is a handy //way to get a String
		// representing the //modifier key.
		System.out.println("Key Typed: " + e.getKeyChar() + " "
				+ KeyEvent.getKeyModifiersText(e.getModifiers()) + "\n");
	}

}