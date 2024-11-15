/**
 * attivita' atomica Fornisci
 * assegna la macchina all'autista
 */

import java.util.logging.Logger;

public class Fornisci implements Task {
	static Logger log;
	private boolean eseguita = false;

	Autista autista;
	Automobile automobile;

	public Fornisci(Autista autista, Automobile automobile) {
		this.autista = autista;
		this.automobile = automobile;
		this.log = Log.creaLogger("Fornisci");
		log.info("creazione");
	}

	@Override
	public synchronized void esegui() {
		if (eseguita)
			return;
		eseguita = true;

		log.finest("inizio");
		this.autista.inserisciAssegnato(new LinkAssegnato(autista, automobile));
		log.finest("fine");
	}
}
