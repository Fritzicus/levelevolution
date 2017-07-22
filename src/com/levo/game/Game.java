package com.levo.game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Stack;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.levo.gamestate.GameState;
import com.levo.gamestate.MainMenuState;
import com.levo.physics.Vec2;

/* Top level game class:
 *  Creates window (1000x1000 JFrame)
 *  Initializes a JPanel that fills entire JFrame and can be drawn on with Graphics object
 *  Implements game loop to draw and update game objects
 *  Manages gamestates and updates and draws current gamestate 
 * 
 */
public class Game extends JPanel implements Runnable, KeyListener, MouseListener, MouseMotionListener {
	private static final long serialVersionUID = 30L;

	public static final Font FONT = new Font("Courier", Font.PLAIN, 16); // Font that all Graphics is set to use by default
	public static final Vec2 GRAVITY = new Vec2(0, .2);
	
	private boolean running = false; // Bool for when Tread is running
	private Stack<GameState> gs; // Stack of running gamestates (operates like a call stack)
	
	public Game() {
		// Size of JFrame window
		Dimension size = new Dimension(1000, 1000);
			
		// Start in Main menu
		gs = new Stack<GameState>();
		gs.push(new MainMenuState());
		
		// Set size of this JPanel (Game extends JPanel)
		setPreferredSize(size);
		setMinimumSize(size);
		setMaximumSize(size);
		
		// Setup JPanel with evt listeners and such
		setBackground(Color.BLACK);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		addKeyListener(this);
		addMouseListener(this);
		addMouseMotionListener(this);
		
		// Create JFrame to house JPanel and size it to JPanel
		JFrame f = new JFrame("Level Evolution :)") {
			private static final long serialVersionUID = 1L;
		};
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.add(this);
		f.pack();
		f.setLocationRelativeTo(null);
		f.setVisible(true);
		
		running = true;
	}
	
	public void paintComponent(Graphics g) {
		// Init G2D and fill xcreen with black
		double width = getSize().getWidth(), height = getSize().getHeight();
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(Color.BLACK);
		g2d.fillRect(0, 0, (int) width, (int) height);
		
		// Rescale so that game is 400x400 and always displays in  square in JFrame
		if (width > height) {
			g2d.translate((width - height) / 2, 0);
		} else {
			g2d.translate(0, (height - width) / 2);			
		}
		g2d.scale(Math.min(width, height) / 400, Math.min(width, height) / 400);
		
		g2d.setFont(FONT);
		
		// Draw the current gamestate
		if (!gs.isEmpty()) gs.peek().draw(g2d);
		
		// Fill in black on the screen
		g2d.setColor(new Color(230, 230, 235));
		if (width > height) {
			g2d.fillRect((int) -width, 0, (int) width, 400);
			g2d.fillRect(400, 0, (int) width, 400);
		} else {
			g2d.fillRect(0, (int) -height, 400, (int) height);
			g2d.fillRect(0, 400, 400, (int) height);
		}
	}
	
	// Update runs 60 times a second
	public void update() {
		if (gs.isEmpty()) 
			return;
		GameState g = gs.peek();
		g.update();
		if (g.isDone())
			System.out.println("Popping" + gs.pop());
		if (g.nextState() != null) {
			System.out.println("Pushing: " + g.nextState());
			gs.push(g.nextState());
		}
	}
	
	public void run() {
		// Loops while running calling update 60 times per second
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();
		while (running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while (delta >= 1) {
				update();
				repaint(); // Calls the paintcomponent method
				delta--;
			}

			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				System.out.println(gs);
			}
		}
	}
	
	public static void drawStringCentered(Graphics2D g, String msg, int x, int y) {
		FontMetrics metrics = g.getFontMetrics(FONT);
		x -= metrics.stringWidth(msg) / 2;
		y += metrics.getHeight() / 3;
		g.drawString(msg, x, y);
	}

	public void keyPressed(KeyEvent e) { 
		if (!gs.isEmpty())
			gs.peek().keyPressed(e.getKeyCode()); 
	}

	public void keyReleased(KeyEvent e) { 
		if (!gs.isEmpty())
			gs.peek().keyReleased(e.getKeyCode()); 
	}
	
	public void keyTyped(KeyEvent e) { } // don't dispatch to gs because it isn't useful
	
	public void mouseClicked(MouseEvent e) {  }

	public void mouseEntered(MouseEvent e) {  }

	public void mouseExited(MouseEvent e) { }

	public void mousePressed(MouseEvent e) {  }
	
	public void mouseReleased(MouseEvent e) { }

	public void mouseDragged(MouseEvent e) { }

	public void mouseMoved(MouseEvent e) { }
}
