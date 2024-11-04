import java.util.LinkedList;
import java.util.logging.Logger;

public class Multiplo implements Task {
	static Logger log;
	private boolean eseguita = false;

	LinkedList<Autista> autisti;
	Autista multiplo;

	public Multiplo(LinkedList<Autista> autisti) {
		this.autisti = autisti;
		this.log = Log.creaLogger("Multiplo");
		log.info("creazione Multiplo");
	}

	@Override
	public void esegui() {
		if (eseguita)
			return;
		eseguita = true;

		log.info("run Multiplo");

		multiplo = null;
		for (Autista a : autisti) {
			if (a.getAssegnato().size() > 1) {
				multiplo = a;
				break;
			}
		}
	}

	public boolean esisteMultiplo() {
		if (! eseguita)
			throw new RuntimeException("attivita' non eseguita");
		return multiplo != null;
	}

	public Autista getMultiplo() {
		if (! eseguita)
			throw new RuntimeException("attivita' non eseguita");
		return multiplo;
	}
}
