/*
 * Mandelbrot.java
 * 
 * @version
 * $Id: Simulator1.java, Version 1.0 11/17/2014 $
 * 
 * @revision
 * $Log initial version $
 * 
 */


import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

/**
 * Class creates the initial mandelbrot set and gives functionality to zoom the 
 * image
 * 
 * @author Uday Vilas Wadhone
 * 
 *
 */
public class Mandelbrot1 extends JFrame implements MouseListener {

	//predefined variables given by Prof.HPB
	private static final long serialVersionUID = 1L;
	private final int MAX = 5000;
	private final int LENGTH = 800;
	private final double ZOOM = 1000;
	private BufferedImage I;
	private double zx, zy, cX, cY, tmp;
	private int[] colors = new int[MAX];
	
	//variables for implementing zoom function 
	private int newX, newY, newEndX, newEndY;
	private double ratio = 1;
	private int minx = 0;
	private int miny = 0;

	/**
	 * Constructor for class
	 */
	public Mandelbrot1() {
		super("Mandelbrot Set");

		initColors();
		setBounds(100, 100, LENGTH, LENGTH);
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

	}

	/**
	 * pre-defined method as given by Prof.HPB. to create initial Mandelbrot
	 * set.
	 */
	public void createSet() {
		I = new BufferedImage(getWidth(), getHeight(),
				BufferedImage.TYPE_INT_RGB);
		for (int y = 0; y < getHeight(); y++) {
			for (int x = 0; x < getWidth(); x++) {
				zx = zy = 0;
				cX = (minx + (x * ratio) - LENGTH) / ZOOM;
				cY = (miny + (y * ratio) - LENGTH) / ZOOM;
				int iter = 0;
				while ((zx * zx + zy * zy < 4) && (iter < MAX - 1)) {
					tmp = zx * zx - zy * zy + cX;
					zy = 2.0 * zx * zy + cY;
					zx = tmp;
					iter++;
				}
				if (iter > 0)
					I.setRGB(x, y, colors[iter]);
				else
					I.setRGB(x, y, iter | (iter << 8));
				repaint();
			}
		}
	}

	/**
	 * initialise colors for the mandelbrot
	 */
	public void initColors() {
		for (int index = 0; index < MAX; index++) {
			colors[index] = Color.HSBtoRGB(index / 256f, 1, index
					/ (index + 8f));
		}
	}

	@Override
	public void paint(Graphics g) {
		g.drawImage(I, 0, 0, this);
	}

	/**
	 * the main method
	 * 
	 * @param args commandline arguments
	 *            
	 */
	public static void main(String[] args) {
		Mandelbrot1 aMandelbrot = new Mandelbrot1();
		aMandelbrot.setVisible(true);
		aMandelbrot.addMouseListener(aMandelbrot);
		aMandelbrot.createSet();
	}

	@Override
	public void mouseClicked(MouseEvent paramMouseEvent) {
		// TODO Auto-generated method stub

	}

	@Override
	/**
	 * gets the x and y coordinates from when mouse is pressed
	 */
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		newX = e.getX();
		newY = e.getY();

	}

	@Override
	/**
	 * gets the x and y coordinates from when mouse is released
	 */
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		newEndX = e.getX();
		newEndY = e.getY();
		createNewSet();

	}

	/**
	 * calculates the ratio from the new x,y coordinates. and calls createSet
	 * again
	 */
	private void createNewSet() {
		//formula to create new set based on zoom
		int xfactor = Math.abs(newEndX - newX);
		int yfactor = Math.abs(newEndY - newY);
		minx += (ratio*Math.min(newX, newEndX));
		miny += (ratio*Math.min(newY, newEndY));

		ratio *= Math.max(xfactor, yfactor) / (double) LENGTH;
		createSet();

	}

	/**
	 * unused mouselistener methods
	 */
	@Override
	public void mouseEntered(MouseEvent paramMouseEvent) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent paramMouseEvent) {
		// TODO Auto-generated method stub

	}
}
