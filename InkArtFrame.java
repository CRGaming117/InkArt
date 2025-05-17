package inkArt;
import javax.swing.JFrame;
public class InkArtFrame {
	public static void main(String[] args) {
		//Controls
		System.out.println("TOOL CONTROLS (press key to use)");
		System.out.println("Brush\t\tB");
		System.out.println("Eraser\t\tE");
		System.out.println("Rectangle\tR");
		//System.out.println("Oval\t\tO");
		System.out.println("Polygon\t\tP");
		System.out.println("Clear\t\tC");
		int w=600;
		int h=600;
		JFrame frame=new JFrame("Ink Art");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(new InkArtPanel(w,h));
		frame.pack();
		frame.setVisible(true);
	}
}