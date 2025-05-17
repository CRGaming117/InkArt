package InkArt;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
public class InkArtPanel extends JPanel {
	private final int W,H;
	private ArrayList<Point> bp,pp;
	private String t,pt;
	private int px,py,mx,my,bw,pi;
	private Rectangle cr;
	private ArrayList<Rectangle> r,o;
	private ArrayList<Polygon> p;
	public InkArtPanel(int w,int h) {
		setPreferredSize(new Dimension(w,h));
		W=w;
		H=h;
		bw=10;
		px=w/2;
		py=h/2;
		pi=-1;
		t="brush";
		cr=new Rectangle();
		bp=new ArrayList<Point>();
		pp=new ArrayList<Point>();
		r=new ArrayList<Rectangle>();
		p=new ArrayList<Polygon>();
		addMouseListener(new Clicking());
		addMouseMotionListener(new PlayerMover());
		this.addKeyListener(new Tools());
		this.setFocusable(true);
	}
	public void paint(Graphics g) {
		super.paint(g);
		setBackground(Color.white);	
		//Cursor
		g.setColor(Color.black);
		g.drawOval(px-2,py-2,4,4);
		//Tool
		g.setColor(Color.black);
		//Brush
		for(int i=0;i<bp.size();i++) g.fillOval(bp.get(i).x-bw/2,bp.get(i).y-bw/2,bw,bw);
		//Polyline
		for(int i=pi+1;i<pp.size()-1;i++) g.drawLine(pp.get(i).x,pp.get(i).y,pp.get(i+1).x,pp.get(i+1).y);
		//Polygon
		for(int i=0;i<p.size();i++) g.drawPolygon(p.get(i));
		//Rectangle
		g.drawRect(cr.x,cr.y,cr.width,cr.height);
		//Previous rectangles
		for(int i=0;i<r.size()-1;i++) g.drawRect(r.get(i).x,r.get(i).y,r.get(i).width,r.get(i).height);
		//Oval
		//for(int i=0;i<o.size()-1;i++) g.drawOval(o.x,o.y,o.width,o.height);
	}
	private class PlayerMover implements MouseMotionListener 
	{
		public void mouseDragged(MouseEvent e) {
			switch(t) {
			case "brush": bp.add(new Point(e.getX(),e.getY()));
			break;
			case "eraser":
				for(int i=0;i<bp.size();i++) {
					int x=e.getX();
					int y=e.getY();
					Rectangle r=new Rectangle(bp.get(i).x-bw/2,bp.get(i).y-bw/2,bw,bw);
					if(r.intersects(new Rectangle(x-1,y-1,2,2)))
						bp.remove(i);
				}
			break;
			case "rectangle":
				cr.setSize(e.getX()-px,e.getY()-py);
				/*for(int i=0;i<r.size();i++) {
					r.add(new Rectangle(e.getX(),e.getY()));
				}*/
			break;
			}
			repaint();
		}
		public void mouseMoved(MouseEvent e) {
			px=e.getX();
			py=e.getY();
			repaint();
		}	
	}
	private class Clicking implements MouseListener {
		public void mousePressed(MouseEvent e) {
			switch(t) {
			case "polygon": 
				int x=e.getX();
				int y=e.getY();
				pp.add(new Point(x,y));
			break;
			case "rectangle":
			case "oval":
				mx=e.getX();
				my=e.getY();
				cr.setLocation(mx,my);
			break;	
			}
			repaint();
		}
		public void mouseReleased(MouseEvent e) {}
		public void mouseClicked(MouseEvent e) {}
		public void mouseEntered(MouseEvent e) {}
		public void mouseExited(MouseEvent e) {}
	}
	private class Tools implements KeyListener {
		public void keyPressed(KeyEvent e) {
			pt=t;
			switch(e.getKeyCode()) {
			case KeyEvent.VK_B: t="brush";
			break;
			case KeyEvent.VK_E: t="eraser";
			break;
			case KeyEvent.VK_R: t="rectangle";
			break;
			case KeyEvent.VK_O: t="oval";
			break;
			case KeyEvent.VK_P: t="polygon";
			break;
			case KeyEvent.VK_C: bp.clear();pp.clear();p.clear();cr.setLocation(W*-1,H*-1);
			break;
			case KeyEvent.VK_ESCAPE: System.exit(0);		    
			}
			switch (pt) {
			case "polygon":
				int[] xpoints=new int[pp.size()];
				for(int i=0;i<xpoints.length;i++) xpoints[i]=pp.get(i).x;
				int[] ypoints=new int[pp.size()];
				for(int i=0;i<ypoints.length;i++) ypoints[i]=pp.get(i).y;
				p.add(new Polygon(xpoints,ypoints,xpoints.length));
				pi=p.size();
			break;
			}
		}
		public void keyReleased(KeyEvent e) {}
		public void keyTyped(KeyEvent e) {}
	}
}
