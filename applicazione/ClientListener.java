package applicazione;

import java.util.Scanner;
import java.io.PrintWriter;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JList;
import javax.swing.JButton;
import java.util.logging.Logger;
import _log.Log;

class ClientListenerThread implements Runnable {
	JButton assegna;
	Scanner sc;
	private static Logger log;

	public ClientListenerThread(Scanner sc, JButton assegna) {
		this.assegna = assegna;
		this.sc = sc;
		log = Log.creaLogger(ClientListenerThread.class.getName());
	}

	@Override
	public void run() {
		for (int i = 0; i < 3; i++) {
			if (! sc.hasNextLine())
				throw new RuntimeException("impossibile leggere la linea");
			log.info("ricevuto: " + sc.nextLine());
		}

		this.assegna.setEnabled(true);
	}
}

public class ClientListener implements ActionListener {
	JList<String> lista;
	JButton assegna;
	PrintWriter pw;
	Scanner sc;
	private static Logger log;

	public ClientListener(JList<String> lista, JButton assegna, PrintWriter pw, Scanner sc) {
		this.lista = lista;
		this.assegna = assegna;
		this.pw = pw;
		this.sc = sc;
		log = Log.creaLogger(ClientListener.class.getName());
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		log.info("evento: " + e);
		String comando = e.getActionCommand();
		log.info("comando: " + comando);

		if (comando.equals("assegna")) {
			String autista;
			ClientListenerThread r;
			Thread t;

			this.assegna.setEnabled(false);

			autista = lista.getSelectedValue();
			log.info("autista: " + autista);

			pw.println("assegna:" + autista);
			pw.flush();

			r = new ClientListenerThread(sc, assegna);
			t = new Thread(r);
			t.start();
		}
		else if (comando.equals("chiudi")) {
			System.exit(0);
		}
		else {
			log.info("comando non riconosciuto");
		}

		log.info("fine");
	}
}
