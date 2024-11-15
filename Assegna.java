/**
 * attivita' principale Assegna (autisti, automobili, autista)
 *	assegna una macchina all'autista
 */

import java.util.HashSet;
import java.util.logging.Logger;

public class Assegna implements Runnable {
	static Logger log;
	private boolean eseguita = false;
	private TaskExecutor executor = TaskExecutor.getInstance();

	private HashSet<Automobile> automobili;
	private Autista ricevente;
	private Automobile automobile;

	public Assegna(HashSet<Automobile> automobili, Autista ricevente) {
		this.automobili = automobili;
		this.ricevente = ricevente;
		this.log = Log.creaLogger("Assegna");
		log.info("creazione Assegna");
	}

	@Override
	public void run() {
		Automobile disponibile;
		Autista cedente;

		log.finest("inizio");

		if (eseguita)
			return;
		eseguita = true;

		Libera l = new Libera(automobili, ricevente);
		executor.perform(l);
		if (l.getAutomobile() != null) {
			this.automobile = l.getAutomobile();
			return;
		}

		Disponibile d = new Disponibile(automobili);
		executor.perform(d);
		disponibile = d.getDisponibile();
		cedente = d.getCedente();
		if (disponibile == null) {
			log.info("nessuna automobile disponibile");
			return;
		}

		Cessione r = new Cessione(cedente, disponibile);
		Thread t = new Thread(r);
		t.start();

		Verifica v = new Verifica(cedente);
		Thread s = new Thread(v);
		s.start();

		try {
			t.join();
			log.info("fine Cessione");
			s.join();
			log.info("fine Verifica");
		}
		catch(InterruptedException e) {
			e.printStackTrace();
		}

		Fornisci f = new Fornisci(this.ricevente, disponibile);
		executor.perform(f);

		this.automobile = disponibile;

		log.finest("fine");
	}

	public boolean estEseguita() {
		return this.eseguita;
	}

	public Automobile getAutomobile() {
		if (! eseguita)
			throw new RuntimeException("attivita' non eseguita");
		return this.automobile;
	}
}

