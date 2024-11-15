import java.util.LinkedList;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JList;
import java.awt.BorderLayout;

public class Client {
	public static void main(String[] args) {
		JFrame finestra = new JFrame("auto");
		String[] x = {"primo", "secondo", "terzo"};

		JPanel top = new JPanel();
		finestra.add(top, BorderLayout.NORTH);

		JList autisti = new JList(x);
		finestra.add(autisti, BorderLayout.CENTER);

		JButton end = new JButton("end");
		finestra.add(end, BorderLayout.SOUTH);

		finestra.pack();
		finestra.setVisible(true);
		finestra.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
