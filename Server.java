import java.util.LinkedList;
import java.util.HashSet;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.PrintWriter;
import java.util.Scanner;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.logging.Logger;

class ServerThread implements Runnable {
	private LinkedList<Autista> autisti;
	private LinkedList<Automobile> automobili;
	private Socket s;
	private Logger log;

	public ServerThread(Socket s, LinkedList<Autista> autisti, LinkedList<Automobile> automobili) {
		this.s = s;
		this.autisti = autisti;
		this.automobili = automobili;
		log = Log.creaLogger(ServerThread.class.toString());
	}

	@Override
	public synchronized void run() {
		try {
			PrintWriter pr = new PrintWriter(this.s.getOutputStream());
			Scanner sc = new Scanner(this.s.getInputStream());

			log.info("ciclo di lettura");
			while (sc.hasNextLine()) {
				String line = sc.nextLine();
				String[] campi = line.split(":");
				Autista autista = null;

				log.info("comando: " + line);

				if (campi[0].equals("autisti")) {
					for (Autista a : autisti) {
						pr.println(a.getNome());
						log.info("inviato: " + a.getNome());
					}
					pr.println("END");
					pr.flush();
				}
				else if (campi[0].equals("assegna")) {
					for (Autista a : autisti) {
						if (a.getNome().equals(campi[1])) {
							autista = a;
							break;
						}
					}

					if (autista == null) {
						pr.println("NON TROVATO");
						pr.flush();
						break;
					}
					pr.println(autista);
					pr.flush();

					Assegna a = new Assegna(new HashSet(automobili), autista);
					a.run();

					if (a.getAutomobile() == null) {
						pr.println("no");
						pr.flush();
					}
					pr.println(a.getAutomobile());
					pr.println("END");
					pr.flush();
				}
				else {
					pr.println("ERRORE");
					pr.flush();
					break;
				}
			}

		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
}

public class Server {
	private static Logger data, eventi, server;

	public static void main(String[] args) throws IOException {
		boolean single = false;

		data = Log.creaLogger("data");
		eventi = Log.creaLogger("eventi");
		server = Log.creaLogger("server");

		server.finest("inizio");

		if (args.length >= 1 && args[0].equals("-s"))
			single = true;
		data.info("single: " + single);

		// lettura dati

		LinkedList<Autista> autisti = new LinkedList<Autista>();
		LinkedList<Automobile> automobili = new LinkedList<Automobile>();

		try {
			DB.log();
			DB.leggiAutisti("autisti.txt", autisti);
			DB.leggiAutomobili("automobili.txt", automobili);
			DB.leggiAssegnato("assegnato.txt", autisti, automobili);
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
			return;
		}
		catch (IOException e) {
			e.printStackTrace();
			return;
		}

		data.info("AUTISTI");
		data.info(autisti.toString());
		data.info("AUTOMOBILI");
		data.info(automobili.toString());
		data.info("ASSEGNATO");
		for (Autista a : autisti)
			data.info(a.toString() + ": " + a.getAssegnato().toString());

		// gestione eventi

		for (Autista a : autisti) {
			eventi.finest("inserimento listener di: " + a);
			EsecuzioneEnvironment.addListener(a);
		}
		for (Automobile a : automobili) {
			eventi.finest("inserimento listener di: " + a);
			EsecuzioneEnvironment.addListener(a);
		}
		EsecuzioneEnvironment.attivaListener();

		// server

		ServerSocket ss = new ServerSocket(8080);

		Socket s;

		while (ss == ss) {
			s = ss.accept();
			ServerThread st = new ServerThread(s, autisti, automobili);
			Thread t = new Thread(st);
			t.start();
			if (single) {
				try {
					t.join();
				}
				catch (InterruptedException e) {
					e.printStackTrace();
				}
				break;
			}
		}

		EsecuzioneEnvironment.disattivaListener();

		server.finest("fine");
	}
}

