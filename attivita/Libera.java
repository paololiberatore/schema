/*
 * attivita' atomica Libera(autisti, automobili)
 *
 * se trova una macchina non assegnata, la assegna all'autista
 */

package attivita;

import java.util.HashSet;
import java.util.logging.Logger;
import _framework.*;
import _log.Log;
import dati.*;

public class Libera implements Task {
	private static Logger log;
	private boolean eseguita = false;
	private TaskExecutor executor = TaskExecutor.getInstance();

	private HashSet<Automobile> automobili;
	private Autista autista;

	private Automobile automobile;

	public Libera(HashSet<Automobile> automobili, Autista autista) {
		this.automobili = automobili;
		this.autista = autista;
		this.log = Log.creaLogger("Libera");
		log.info("creazione Libera");
	}

	@Override
	public synchronized void esegui() {
		log.info("run Libera");

		if (eseguita)
			return;
		eseguita = true;

		this.automobile = null;

		for (Automobile a : automobili) {
			if (a.getAssegnato() == null) {
				LinkAssegnato l;
				l = new LinkAssegnato(this.autista, a);
				this.autista.inserisciAssegnato(l);
				this.automobile = a;
				log.info("macchina assegnata: " + a);
				return;
			}
		}

		log.info("nessuna macchina libera");
	}

	public boolean estEseguita() {
		return this.eseguita;
	}

	public Automobile getAutomobile() {
		if (! eseguita)
			throw new RuntimeException("Libera non eseguita");
		return this.automobile;
	}
}

