package applicazione;

import java.util.LinkedList;
import java.util.Scanner;
import java.io.PrintWriter;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JList;
import java.awt.BorderLayout;
import java.util.logging.Logger;
import _log.Log;

public class Client {
	private static Logger log;

	public static void main(String[] args) {
		String server = "localhost";
		int port = 8080;
		Socket socket;
		PrintWriter pw;
		Scanner sc;
		LinkedList<String> lista;
		String autista;

		if (args.length >= 2)
			port = Integer.parseInt(args[1]);
		if (args.length >= 1)
			server = args[0];

		log = Log.creaLogger(Client.class.toString());

		try {
			log.info("connessione al server");
			socket = new Socket(server, port);
			log.info("connessione stabilita");
			pw = new PrintWriter(socket.getOutputStream());
			sc = new Scanner(socket.getInputStream());
			log.info("canale IO creato");
		}
		catch(UnknownHostException e) {
			System.out.println("non trovo il server");
			return;
		}
		catch(IOException e) {
			System.out.println("errore IO");
			return;
		}

		pw.println("autisti");
		pw.flush();

		lista = new LinkedList<String>();
		log.info("lettura lista");
		while (sc.hasNextLine()) {
			autista = sc.nextLine();
			log.info(autista);
			if (autista.equals("END"))
				break;
			lista.add(autista);
		}
		log.info("lista autisti: " + lista);
		if (lista.size() == 0)
			throw new RuntimeException("nessun autista");

		JFrame finestra = new JFrame("auto");
		JPanel top = new JPanel();
		finestra.add(top, BorderLayout.NORTH);

		String[] s = lista.toArray(new String[0]);
		JList<String> autisti = new JList<String>(s);
		autisti.setSelectedIndex(0);
		finestra.add(autisti, BorderLayout.CENTER);

		JPanel bottom = new JPanel();
		finestra.add(bottom, BorderLayout.SOUTH);

		JButton assegna = new JButton("assegna");
		bottom.add(assegna);

		JButton chiudi = new JButton("chiudi");
		bottom.add(chiudi);

		ClientListener cl;
		cl = new ClientListener(autisti, assegna, pw, sc);
		assegna.addActionListener(cl);
		chiudi.addActionListener(cl);

		finestra.pack();
		finestra.setVisible(true);
		finestra.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
