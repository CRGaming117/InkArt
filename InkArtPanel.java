package InkArt;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class InkArtPanel extends JPanel {
  private final int W, H;
  private ArrayList<Point> bp, pp;
  private String t, pt;
  private int px, py, mx, my, bw, pi;
  private Rectangle cr, co;
  private Rectangle tM;
  private ArrayList<Rectangle> r, o;
  private ArrayList<Polygon> p;

  public InkArtPanel(int w, int h) {
    setPreferredSize(new Dimension(w, h));
    W = w;
    H = h;
    bw = 10;
    px = w / 2;
    py = h / 2;
    pi = -1;
    t = "brush";
    cr = new Rectangle();
    co = new Rectangle();
    bp = new ArrayList<Point>();
    pp = new ArrayList<Point>();
    r = new ArrayList<Rectangle>();
    o = new ArrayList<Rectangle>();
    p = new ArrayList<Polygon>();

    // Tool Menu
    tM = new Rectangle(5, 5, 60, 590);

    addMouseListener(new Clicking());
    addMouseMotionListener(new PlayerMover());

    this.addKeyListener(new ToolKeys());
    this.setFocusable(true);

    // this.add(toolMenu);
  }

  public void paint(Graphics g) {
    super.paint(g);
    setBackground(Color.white);
    // Cursor
    g.setColor(Color.black);
    g.drawOval(px - 2, py - 2, 4, 4);
    // Tool
    g.setColor(Color.black);
    // Brush
    for (int i = 0; i < bp.size(); i++)
      g.fillOval(bp.get(i).x - bw / 2, bp.get(i).y - bw / 2, bw, bw);
    // Polyline
    for (int i = pi + 1; i < pp.size() - 1; i++)
      g.drawLine(pp.get(i).x, pp.get(i).y, pp.get(i + 1).x, pp.get(i + 1).y);
    // Polygon
    for (int i = 0; i < p.size(); i++)
      g.drawPolygon(p.get(i));
    // Rectangle
    g.drawRect(cr.x, cr.y, cr.width, cr.height);
    // Previous rectangles
    for (int i = 0; i < r.size() - 1; i++)
      g.drawRect(r.get(i).x, r.get(i).y, r.get(i).width, r.get(i).height);
    // Previous Oval
    for (int i = 0; i < o.size() - 1; i++)
      g.drawOval(o.get(i).x, o.get(i).y, o.get(i).width, o.get(i).height);
    // Oval
    g.drawOval(co.x, co.y, co.width, co.height);

    // Tool Menu
    g.setColor(Color.gray);
    g.fillRect(tM.x, tM.y, tM.width, tM.height);
  }

  private class PlayerMover implements MouseMotionListener {
    public void updateMouse(MouseEvent e) {
      px = e.getX();
      py = e.getY();
    }

    public void mouseDragged(MouseEvent e) {
      switch (t) {
        case "brush":
          updateMouse(e);
          bp.add(new Point(px, py));
          break;
        case "eraser":
          updateMouse(e);
          for (int i = 0; i < bp.size(); i++) {
            Rectangle r = new Rectangle(bp.get(i).x - bw / 2, bp.get(i).y - bw / 2, bw, bw);
            if (r.intersects(new Rectangle(px - 1, py - 1, 2, 2)))
              bp.remove(i);
          }
          break;
        case "rectangle":
          cr.setSize(e.getX() - px, e.getY() - py);
          /*
           * for(int i=0;i<r.size();i++) {
           * r.add(new Rectangle(e.getX(),e.getY()));
           * }
           */
          break;
        case "oval":
          co.setSize(e.getX() - px, e.getY() - py);
          break;
      }
      repaint();
    }

    public void mouseMoved(MouseEvent e) {
      px = e.getX();
      py = e.getY();
      repaint();
    }
  }

  private class Clicking implements MouseListener {
    public void mousePressed(MouseEvent e) {
      mx = e.getX();
      my = e.getY();
      switch (t) {
        case "polygon":
          pp.add(new Point(mx, my));
          break;
        case "oval":
          co.setLocation(mx, my);
          break;
        case "rectangle":
          cr.setLocation(mx, my);
          break;
      }
      repaint();
    }

    public void mouseReleased(MouseEvent e) {
      switch (t) {
        case "rectangle":
          r.add(cr);
        case "oval":
          o.add(co);
      }
    }

    public void mouseClicked(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }
  }

  private class ToolKeys implements KeyListener {
    public void keyPressed(KeyEvent e) {
      pt = t;
      switch (e.getKeyCode()) {
        case KeyEvent.VK_B:
          t = "brush";
          break;
        case KeyEvent.VK_E:
          t = "eraser";
          break;
        case KeyEvent.VK_R:
          t = "rectangle";
          break;
        case KeyEvent.VK_O:
          t = "oval";
          break;
        case KeyEvent.VK_P:
          t = "polygon";
          break;
        case KeyEvent.VK_C:
          bp.clear();
          pp.clear();
          p.clear();
          cr.setLocation(W * -1, H * -1);
          co.setLocation(W * -1, H * -1);
          break;
        case KeyEvent.VK_ESCAPE:
          System.exit(0);
      }
      switch (pt) {
        case "polygon":
          int[] xpoints = new int[pp.size()];
          for (int i = 0; i < xpoints.length; i++)
            xpoints[i] = pp.get(i).x;
          int[] ypoints = new int[pp.size()];
          for (int i = 0; i < ypoints.length; i++)
            ypoints[i] = pp.get(i).y;
          p.add(new Polygon(xpoints, ypoints, xpoints.length));
          pi = p.size();
          break;
      }
    }

    public void keyReleased(KeyEvent e) {
    }

    public void keyTyped(KeyEvent e) {
    }
  }
}
