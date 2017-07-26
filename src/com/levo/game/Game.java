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
import com.levo.gamestate.PhysicsState;
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
	private double scale;
	
	public Game() {
		// Size of JFrame window
		Dimension size = new Dimension(1000, 1000);
			
		// Start in Main menu
		gs = new Stack<GameState>();
		gs.push(new PhysicsState());
		
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
		JFrame f = new JFrame("Level Evolution :)");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.add(this);
		f.pack();
		f.setLocationRelativeTo(null);
		f.setVisible(true);
		
		running = true;
		scale = Math.min(size.width, size.height) / 400;
	}
	
	public void paintComponent(Graphics g) {
		// Init G2D and fill screen with black
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
		scale = Math.min(width, height) / 400;
		g2d.scale(scale, scale);
		
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
	
	// Update runs 60 times a second causing a tick
	// dt parameter is the amounts of time elapsed since the last frame
	public void update(double dt) {
		// Terminate game if there is no gamestate
		if (gs.isEmpty()) 
			System.exit(0);
		
		GameState g = gs.peek();
		g.update(dt); // Update current gamestate
		
		// Pop a gamestate when it is finished, and push the next gamestate if there is one
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
		double dt = 1.0 / amountOfTicks;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();
		while (running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while (delta >= 1) {
				update(dt);
				repaint(); // Calls the paintcomponent method
				delta--;
			}

			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
			}
		}
	}
	
	// Static helper method to draw a string centered at an x and y coordinate
	public static void drawStringCentered(Graphics2D g, String msg, int x, int y) {
		FontMetrics metrics = g.getFontMetrics(FONT);
		x -= metrics.stringWidth(msg) / 2;
		y += metrics.getHeight() / 3;
		g.drawString(msg, x, y);
	}
	
	private void correctMouseEvent(MouseEvent e) {
		int x = (int) (e.getX() / scale);
		int y = (int) (e.getY() / scale);
		e.translatePoint(x - e.getX(), y - e.getY());
	}

	// Mouse and Key event handlers below. (Called when mouse does actions and keys are pressed and released)
	public void keyPressed(KeyEvent e) { 
		if (!gs.isEmpty())
			gs.peek().keyPressed(e.getKeyCode()); 
	}

	public void keyReleased(KeyEvent e) { 
		if (!gs.isEmpty())
			gs.peek().keyReleased(e.getKeyCode()); 
	}
	
	public void keyTyped(KeyEvent e) { } // don't dispatch to gs because it isn't useful
	
	public void mouseClicked(MouseEvent e) { 
		if (!gs.isEmpty())
			correctMouseEvent(e);
			gs.peek().mouseClicked(e); 
	}

	public void mouseEntered(MouseEvent e) { 
		if (!gs.isEmpty())
			correctMouseEvent(e);
			gs.peek().mouseEntered(e); 
	}

	public void mouseExited(MouseEvent e) { 
		if (!gs.isEmpty())
			correctMouseEvent(e);
			gs.peek().mouseExited(e); 
	}

	public void mousePressed(MouseEvent e) { 
		if (!gs.isEmpty())
			correctMouseEvent(e);
			gs.peek().mousePressed(e); 
	}
	
	public void mouseReleased(MouseEvent e) { 
		if (!gs.isEmpty())
			correctMouseEvent(e);
			gs.peek().mouseReleased(e); 
	}

	public void mouseDragged(MouseEvent e) { 
		if (!gs.isEmpty())
			correctMouseEvent(e);
			gs.peek().mouseDragged(e);
	}

	public void mouseMoved(MouseEvent e) { 
		if (!gs.isEmpty())
			correctMouseEvent(e);
			gs.peek().mouseMoved(e); 
	}
}
