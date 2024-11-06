import java.util.LinkedList;
import java.util.logging.Logger;

public class Libera implements Task {
	static Logger log;
	private boolean eseguita = false;
	private TaskExecutor executor = TaskExecutor.getInstance();

	private LinkedList<Automobile> automobili;
	private Autista autista;

	private boolean esito;

	public Libera(LinkedList<Automobile> automobili, Autista autista) {
		this.automobili = automobili;
		this.autista = autista;
		this.log = Log.creaLogger("Libera");
		log.info("creazione Libera");
	}

	@Override
	public synchronized void esegui() {
		if (eseguita)
			return;
		eseguita = true;

		log.info("run Libera");

		for (Automobile a : automobili) {
			if (a.getAssegnato() == null) {
				LinkAssegnato l;
				l = new LinkAssegnato(this.autista, a);
				this.autista.inserisciAutomobile(l);
				esito = true;
				log.info("macchina assegnata: " + a);
				return;
			}
		}

		log.info("nessuna macchina libera");
		esito = false;
	}

	public boolean getEsito() {
		if (! eseguita)
			throw new RuntimeException("Libera non eseguita");
		return this.esito;
	}
}

