import java.util.LinkedList;
import java.util.HashSet;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.logging.Logger;
import java.util.logging.Level;

public class Main {
	private static Logger data, eventi;

	public static void main(String[] args)
			throws FileNotFoundException, IOException {
		LinkedList<Autista> autisti;
		LinkedList<Automobile> automobili;

		// log

		data = Log.creaLogger("data");
		eventi = Log.creaLogger("eventi");

		// lettura dati

		autisti = new LinkedList<Autista>();
		automobili = new LinkedList<Automobile>();

		DB.leggiAutisti("autisti.txt", autisti);
		DB.leggiAutomobili("automobili.txt", automobili);
		DB.leggiAssegnato("assegnato.txt", autisti, automobili);

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

		// attivita' principale

		Assegna a = new Assegna(new HashSet(automobili), autisti.get(0));
		a.run();

		EsecuzioneEnvironment.disattivaListener();
		eventi.finest("fine");
	}
}

