import java.util.logging.Logger;

public class AutomobileFired implements Task {
	private Logger log;
	Automobile automobile;
	Evento evento;
	boolean eseguito = false;

	public AutomobileFired(Automobile automobile, Evento evento) {
		log = Log.creaLogger(Autista.class.toString());
		this.automobile = automobile;
		this.evento = evento;
	}

	@Override
	public void esegui() {
		boolean risultato;
		Esito esito;

		if (eseguito)
			return;
		eseguito = true;

		log.info("stato: " + this.automobile.getStato());
		log.info("evento ricevuto: " + this.evento);

		switch (this.automobile.getStato()) {
		case NORMALE:
			try {
				Thread.sleep(2000);
			}
			catch (InterruptedException e) {
			}

			Listener mittente = this.evento.getMittente();

			risultato = Math.random() > 0.2;
			esito = new Esito(this.automobile, mittente, risultato);
			Environment.aggiungiEvento(esito);

			break;
		}
	}
}
