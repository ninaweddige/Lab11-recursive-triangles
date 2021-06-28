import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Insets;
import java.awt.Polygon;
import java.awt.Toolkit;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.JFrame;

public class TriangleClass {
	private int height;
	private int[] x; //array of the x-coordinates of the outer triangle
	private int[] y; //array of the y-coordinates of the outer triangle
	Dimension d;
	
	public static void main(String[] args) {
		TriangleClass t = new TriangleClass();
	}
	
	//Constructor
	public TriangleClass() {
		makeFrame();
		x = new int[]{0, height, height / 2};
		y = new int[]{height, height, 0};
	}
	
	//Make the frame
	public void makeFrame() {
		CustomFrame frame = new CustomFrame();
	}
	
	//Calculate midpoints
	public int calculateMidpoint(int a, int b) {
		return (a + b) / 2;
	}
	
	/*Takes the x and y coordinates of a triangle and returns the triangles created by connecting its midpoints
	Index 0: left triangle, 1: right triangle, 2: top triangle, 3: center triangle*/
	public Polygon[] makeTriangles(int[]xCoords, int[]yCoords) {
		Polygon[] triangles = new Polygon[4];
		
		//Calculate the three midpoints of the triangle
		int[] xMidpoints = new int[3];
		int[] yMidpoints = new int[3];
		
		for(int i = 0; i < 2; i++) {
			xMidpoints[i] = calculateMidpoint(xCoords[i], xCoords[i + 1]);
			yMidpoints[i] = calculateMidpoint(yCoords[i], yCoords[i + 1]);
		}
		xMidpoints[2] = calculateMidpoint(xCoords[2], xCoords[0]);
		yMidpoints[2] = calculateMidpoint(yCoords[2], yCoords[0]);
		
		//Bottom left triangle
		int[] leftX = new int[3];
		leftX[0] = xCoords[0];
		leftX[1] = xMidpoints[0];
		leftX[2] = xMidpoints[2];
		int[] leftY = new int[3];
		leftY[0] = yCoords[0];
		leftY[1] = yMidpoints[0];
		leftY[2] = yMidpoints[2];
		Polygon left = new Polygon(leftX, leftY, 3);
		triangles[0] = left;
		
		//bottom right triangle
		int[] rightX = new int[3];
		rightX[0] = xMidpoints[0];
		rightX[1] = xCoords[1];
		rightX[2] = xMidpoints[1];
		int[] rightY = new int[3];
		rightY[0] = yMidpoints[0];
		rightY[1] = yCoords[1];
		rightY[2] = yMidpoints[1];
		Polygon right = new Polygon(rightX, rightY, 3);
		triangles[1] = right;
		
		//top triangle
		int[] topX = new int[3];
		topX[0] = xMidpoints[2];
		topX[1] = xMidpoints[1];
		topX[2] = xCoords[2];
		int[] topY = new int[3];
		topY[0] = yMidpoints[2];
		topY[1] = yMidpoints[2];
		topY[2] = yCoords[2];
		Polygon top = new Polygon(topX, topY, 3);
		triangles[2] = top;
		
		//center triangle
		Polygon center = new Polygon(xMidpoints, yMidpoints, 3);
		triangles[3] = center;
		
		return triangles;
	}
	
	class CustomFrame extends JFrame implements ComponentListener {
		
		public CustomFrame() {
			super();
			Dimension a = new Dimension(Toolkit.getDefaultToolkit().getScreenSize()); //get the screen's dimensions			
			height = (int) a.getHeight();
			//System.out.println(height);
			d = new Dimension(height, height); //dimensions of the frame
			
			this.setSize(d);
			addComponentListener(this);
			//this.setUndecorated(true);
			this.setVisible(true);
		}
		
		@Override
		public void paint(Graphics g) {
			super.paint(g);
			Polygon p = new Polygon(x, y, 3);
			recursive(p, 7, g);
		}
		
		//Draws a triangle and fills it with the given color
		public void paintTriangle(Graphics g, int[] x, int[] y, Color c) {
			Polygon p = new Polygon(x, y, 3);
			g.setColor(c);
			g.drawPolygon(p);
			g.fillPolygon(p);
		}
		
		public void recursive(Polygon p, int steps, Graphics g) {
			if(steps == 0) {return;}
			
			Color c = Color.BLACK;
			
			if(steps == 1) c = Color.BLACK;
			if(steps == 2) c = new Color(255, 199, 0); //yellow
			if(steps == 3) c = new Color(220, 136, 22); //orange
			if(steps == 4) c = Color.RED;
			if(steps == 5) c = new Color(75, 0, 130); //purple
			if(steps == 6) c = Color.BLUE;
			if(steps == 7) c = new Color(114, 174, 29); //green
			
			Polygon[] triangles = makeTriangles(p.xpoints, p.ypoints);
			
			//Paint and fill the center triangle
			paintTriangle(g, triangles[3].xpoints, triangles[3].ypoints, c);

			//Call method recursively for each of the remaining triangles
			recursive(triangles[0], steps - 1, g);
			recursive(triangles[1], steps - 1, g);
			recursive(triangles[2], steps - 1, g);
			
			g.setColor(c);
			g.drawPolygon(p);
			
		}

		@Override
		public void componentMoved(ComponentEvent e) {}

		@Override
		public void componentShown(ComponentEvent e) {}

		@Override
		public void componentHidden(ComponentEvent e) {}
		
		@Override
		public void componentResized(ComponentEvent e) {
			height = Math.min(getHeight(), getWidth());
			x[1] = height;
			x[2] = height / 2;
			y[0] = height;
			y[1] = height;
		}
		
	}

}
