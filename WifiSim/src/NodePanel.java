import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;

import javax.swing.JPanel;


public class NodePanel extends JPanel{
	private Node[] nodes;
	private int maxPosition;
	final private int radius = 10;
	final private int size = 450;
	
	public NodePanel(Node[] a)
	{
		nodes = a;
		maxPosition = 150;
	}
	
	public NodePanel(Node[] a, int maxPos)
	{
		nodes = a;
		maxPosition = maxPos;
	}
	
	public void setNodes(Node[] a)
	{
		nodes = a;
	}
	
	@Override
	protected void paintComponent(Graphics g)
	{
		Graphics2D g2 = (Graphics2D) g;
		double scaledRadius = (size / maxPosition) * radius;
		
		for (int i = 0; i < nodes.length; i++)
		{
			Location loc = nodes[i].getLocation();
			g2.draw(new Ellipse2D.Double(loc.x, loc.y, scaledRadius, scaledRadius));
			g2.drawString(nodes[i].getID().toString(), (int) (loc.x + scaledRadius / 2), (int) (loc.y + scaledRadius / 2));
		}
		
	}

}
