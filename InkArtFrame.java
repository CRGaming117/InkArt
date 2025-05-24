package InkArt;

import java.awt.*;
import javax.swing.*;

public class InkArtFrame {
  public static void main(String[] args) {
    // public InkArtFrame() {
    // super("Ink Art");
    // Controls
    System.out.println("TOOL CONTROLS (press key to use)");
    System.out.println("Brush\t\tB");
    System.out.println("Eraser\t\tE");
    System.out.println("Rectangle\tR");
    System.out.println("Oval\t\tO");
    System.out.println("Polygon\t\tP");
    System.out.println("Clear\t\tC");

    // Main frame
    int w = 600;
    int h = 600;

    Dimension fullScreen = Toolkit.getDefaultToolkit().getScreenSize();
    JFrame frame = new JFrame("Ink Art");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setBackground(Color.lightGray);
    // frame.setPreferredSize(new Dimension(w, h));

    frame.setPreferredSize(new Dimension(w, h));

    // Tool Bar Container
    JPanel tBC = new JPanel();
    tBC.setSize(new Dimension(60, fullScreen.height));
    tBC.setBackground(Color.green);

    // Canvas Container
    JPanel cC = new JPanel();
    cC.setPreferredSize(new Dimension(fullScreen.width - tBC.getWidth(), fullScreen.height));
    cC.setBackground(Color.blue);
    cC.add(new InkArtPanel(cC.getWidth(), cC.getHeight()));

    // Build all
    frame.add(tBC);
    frame.getContentPane().add(cC);
    frame.pack();
    frame.setVisible(true);
  }
}
