package com.levo.game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
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

import com.levo.gamestates.GameState;
import com.levo.gamestates.MainMenuState;

/* Top level game class:
 *  Creates window (800x800 JFrame)
 *  Initializes a JPanel that fills entire JFrame and can be drawn on with Graphics object
 *  Implements game loop to draw and update game objects
 *  Manages gamestates and updates and draws current gamestate 
 * 
 */
public class Game extends JPanel implements Runnable, KeyListener, MouseListener, MouseMotionListener {
	private static final long serialVersionUID = 30L;

	private Font font; // Font that all Graphics is set to use by default
	private boolean running = false; // Bool for when Tread is running
	private Stack<GameState> gs; // Stack of running gamestates (operates like a call stack)
	
	public Game() {
		// Size of JFrame window
		Dimension size = new Dimension(800, 800);
			
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
		f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		f.add(this);
		f.pack();
		f.setLocationRelativeTo(null);
		f.setVisible(true);
		
		font = new Font("Courier", Font.PLAIN, 25);

		running = true;
	}
	
	public void paintComponent(Graphics g) {
		// Init G2D and fill xcreen with black
		double width = getSize().getWidth(), height = getSize().getHeight();
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(Color.BLACK);
		g2d.fillRect(0, 0, (int) width, (int) height);
		
		// Translate 0, 0 to be the middle of the screen
		if (width > height) {
			g2d.translate((width - height) / 2, 0);
		} else {
			g2d.translate(0, (height - width) / 2);			
		}
		
		// Rescale so that game is 400x400 and always displays in  square in JFrame
		g2d.scale(Math.min(width, height) / 400, Math.min(width, height) / 400);
		
		g2d.setFont(font);
		
		// Draw the current gamestate
		gs.peek().draw(g);
		
		// Fill in black on the screen
		g2d.setColor(Color.BLACK);
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
		GameState g = gs.peek();
		g.update();
		if (g.isDone())
			gs.pop();
		if (g.nextState() != null)
			gs.push(g.nextState());
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
				delta--;
			}
			repaint(); // Repaint (calls the paintComponent method) happens as often as possible

			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
			}
		}
	}

	public void keyPressed(KeyEvent e) { gs.peek().keyPressed(e);}

	public void keyReleased(KeyEvent e) { gs.peek().keyReleased(e); }
	
	public void keyTyped(KeyEvent e) { } // don't dispatch to gs because it isn't useful
	
	public void mouseClicked(MouseEvent e) { gs.peek().mouseClicked(e); }

	public void mouseEntered(MouseEvent e) { gs.peek().mouseEntered(e); }

	public void mouseExited(MouseEvent e) { gs.peek().mouseExited(e); }

	public void mousePressed(MouseEvent e) { gs.peek().mousePressed(e); }
	
	public void mouseReleased(MouseEvent e) { gs.peek().mouseReleased(e); }

	public void mouseDragged(MouseEvent e) { gs.peek().mouseDragged(e); }

	public void mouseMoved(MouseEvent e) { gs.peek().mouseMoved(e); }
}
