import java.util.LinkedList;
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
		log.info("creazione Fornisci");
	}

	@Override
	public void esegui() {
		if (eseguita)
			return;
		eseguita = true;

		log.info("run Fornisci");

		this.autista.inserisciAutomobile(new LinkAssegnato(autista, automobile));
	}
}
