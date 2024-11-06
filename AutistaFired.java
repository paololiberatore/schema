import java.util.logging.Logger;

public class AutistaFired implements Task {
	private Logger log;
	Autista autista;
	Evento evento;
	boolean eseguito = false;

	public AutistaFired(Autista autista, Evento evento) {
		log = Log.creaLogger(Autista.class.toString());
		this.autista = autista;
		this.evento = evento;
	}

	@Override
	public void esegui() {
		if (eseguito)
			return;
		eseguito = true;

		log.info("stato: " + this.autista.getStato());
		log.info("evento ricevuto: " + this.evento);

		switch (this.autista.getStato()) {
		case NORMALE:
			if (evento.getClass() == CediMacchina.class) {
				CediMacchina cedi;
				cedi = (CediMacchina) this.evento;
				Automobile a = cedi.getAutomobile();
				Environment.aggiungiEvento(new Diagnostica(this.autista, a));
				this.autista.richiedente = this.evento.getMittente();

				log.info(this.autista.stato + " -> " + Autista.Stato.ATTESA);
				this.autista.stato = Autista.Stato.ATTESA;
			}
			break;
		case ATTESA:
			if (evento.getClass() == Esito.class) {
				ConfermaCessione c;
				c = new ConfermaCessione(this.autista, this.autista.richiedente);
				Environment.aggiungiEvento(c);

				log.info(this.autista.stato + " -> " + Autista.Stato.NORMALE);
				this.autista.stato = Autista.Stato.NORMALE;
			}
			break;
		}
	}
}
