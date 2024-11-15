/**
 * attivita' atomica Disponibile
 * trova una macchina assegnata a un autista che ne ha altre
 */

import java.util.HashSet;
import java.util.logging.Logger;

public class Disponibile implements Task {
	private static Logger log;
	private boolean eseguita = false;

	private HashSet<Automobile> automobili;
	private Automobile disponibile;
	private Autista cedente;

	public Disponibile(HashSet<Automobile> automobili) {
		this.automobili = automobili;
		this.log = Log.creaLogger("Disponibile");
		log.info("creazione Altro");
	}

	@Override
	public void esegui() {
		log.info("attivita' iniziata");

		if (eseguita)
			return;
		eseguita = true;

		for (Automobile l : automobili) {
			Autista a = l.getAssegnato().getAutista();
			if (a == null)
				continue;
			if (a.getAssegnato().size() > 1) {
				this.disponibile = l;
				this.cedente = a;
				log.info("automobile disponibile: " + this.disponibile);
				break;
			}
		}

		log.info("attivita' terminata");
	}

	public Automobile getDisponibile() {
		if (! eseguita)
			throw new RuntimeException("attivita' non eseguita");
		return this.disponibile;
	}

	public Autista getCedente() {
		if (! eseguita)
			throw new RuntimeException("attivita' non eseguita");
		return this.cedente;
	}
}
