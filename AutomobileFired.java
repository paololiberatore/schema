/**
 * classe transizioni di Automobile
 *
 * NORMALE
 *	Diagnostica -> NORMALE / Esito(esito)
 */

import java.util.logging.Logger;

public class AutomobileFired implements Task {
	private Logger log;
	private Automobile automobile;
	private Evento evento;
	private boolean eseguita = false;

	public AutomobileFired(Automobile automobile, Evento evento) {
		log = Log.creaLogger(Autista.class.toString());
		this.automobile = automobile;
		this.evento = evento;
	}

	@Override
	public void esegui() {
		boolean risultato;
		Esito esito;

		log.info("stato: " + this.automobile.getStato());
		log.info("evento ricevuto: " + this.evento);

		if (eseguita)
			return;
		eseguita = true;
		if (this.evento.getDestinatario() != this.automobile &&
		    this.evento.getDestinatario() != null)
			return;

		switch (this.automobile.getStato()) {
		case NORMALE:
			if (this.evento.getClass() == Diagnostica.class) {
				// simulazione esecuzione diagnostica
				try {
					Thread.sleep(4000);
				}
				catch (InterruptedException e) {
				}
				risultato = Math.random() > 0.2;

				Listener mittente = this.evento.getMittente();

				esito = new Esito(this.automobile, mittente, risultato);
				Environment.aggiungiEvento(esito);
			}

			break;
		}
	}

	public boolean estEseguita() {
		return eseguita;
	}
}
