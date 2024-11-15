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

			while (sc.hasNextLine()) {
				String line = sc.nextLine();

				if (line.equals("autisti")) {
					for (Autista a : autisti)
						pr.println(a);
					pr.println("---");
					pr.flush();
				}
				else if (line.equals("assegna")) {
					int indice;
					indice = sc.nextInt();
					if (indice < 0 || indice >= autisti.size()) {
						pr.println("ERROR");
						pr.flush();
						break;
					}
					pr.println(autisti.get(indice));
					pr.flush();

					Autista nuovo = autisti.get(indice);

					Assegna a = new Assegna(new HashSet(automobili), nuovo);
					a.run();

					if (a.getAutomobile() == null) {
						pr.println("no");
						pr.flush();
					}
					pr.println(a.getAutomobile());
					pr.println("---");
					pr.flush();
					break;
				}
				else {
					pr.println("ERROR");
					pr.flush();
					break;
				}
			}

			sc.close();
			s.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
}

public class Server {
	private static Logger data, eventi, server;

	public static void main(String[] args) throws IOException {
		data = Log.creaLogger("data");
		eventi = Log.creaLogger("eventi");
		server = Log.creaLogger("server");

		server.finest("inizio");

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
			try {
				t.join();
			}
			catch (InterruptedException e) {
				e.printStackTrace();
			}
			break;
		}

		EsecuzioneEnvironment.disattivaListener();

		server.finest("fine");
	}
}

