import java.util.LinkedList;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.logging.Logger;
import java.util.logging.Level;

public class Main {
	static Logger io, data, eventi;

	public static void leggiAutisti(String filename, LinkedList<Autista> autisti)
			throws FileNotFoundException, IOException {
		FileReader fr;
		Scanner sc;
		String linea;
		Autista a;

		io.info("leggiAutisti(" + filename + ")");

		fr = new FileReader(filename);
		sc = new Scanner(fr);

		while (sc.hasNextLine()) {
			linea = sc.nextLine();
			io.info(linea);
			String[] componenti = linea.split("	");
			a = new Autista(componenti[1]);
			autisti.add(a);
			EsecuzioneEnvironment.addListener(a);
		}

		sc.close();
		fr.close();
	}

	public static void leggiAutomobili(String filename, LinkedList<Automobile> automobili)
			throws FileNotFoundException, IOException {
		FileReader fr;
		Scanner sc;
		String linea;
		Automobile a;

		io.info("leggiAutomobili(" + filename + ")");

		fr = new FileReader(filename);
		sc = new Scanner(fr);

		while (sc.hasNextLine()) {
			linea = sc.nextLine();
			io.info(linea);
			String[] componenti = linea.split("	");
			a = new Automobile(componenti[1], componenti[2], Integer.parseInt(componenti[3]));
			automobili.add(a);
			EsecuzioneEnvironment.addListener(a);
		}

		sc.close();
		fr.close();
	}

	public static void leggiAssegnato(String filename,
			LinkedList<Autista> autista,
			LinkedList<Automobile> automobili)
			throws FileNotFoundException, IOException {
		FileReader fr;
		Scanner sc;
		String linea;
		int a, b;
		LinkAssegnato l;

		io.info("leggiAssegnato(" + filename + ")");

		fr = new FileReader(filename);
		sc = new Scanner(fr);

		while (sc.hasNextLine()) {
			linea = sc.nextLine();
			io.info(linea);
			String[] componenti = linea.split("	");
			a = Integer.parseInt(componenti[0]) - 1;
			b = Integer.parseInt(componenti[1]) - 1;
			l = new LinkAssegnato(autista.get(a), automobili.get(b));
			autista.get(a).inserisciAutomobile(l);
		}

		sc.close();
		fr.close();
	}

	public static void main(String[] args)
			throws FileNotFoundException, IOException {
		LinkedList<Autista> autisti;
		LinkedList<Automobile> automobili;

		// log

		io = Log.creaLogger("io");
		data = Log.creaLogger("data");
		eventi = Log.creaLogger("eventi");

		// lettura dati

		autisti = new LinkedList<Autista>();
		automobili = new LinkedList<Automobile>();

		leggiAutisti("autisti.txt", autisti);
		leggiAutomobili("automobili.txt", automobili);
		leggiAssegnato("assegnato.txt", autisti, automobili);

		data.info("AUTISTI");
		data.info(autisti.toString());
		data.info("AUTOMOBILI");
		data.info(automobili.toString());
		data.info("ASSEGNATO");
		for (Autista a : autisti)
			data.info(a.toString() + ": " + a.getAssegnato().toString());

		// gestione eventi

		for (Autista a : autisti) {
			eventi.info("inserimento listener di: " + a);
			EsecuzioneEnvironment.addListener(a);
		}
		EsecuzioneEnvironment.attivaListener();

		// attivita' principale

		Assegna a = new Assegna(autisti, automobili, autisti.get(0));
		a.run();

		EsecuzioneEnvironment.disattivaListener();
	}
}

