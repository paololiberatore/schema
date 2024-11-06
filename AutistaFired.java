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
				this.autista.automobile = a;

				log.info(this.autista.stato + " -> " + Autista.Stato.ATTESA);
				this.autista.stato = Autista.Stato.ATTESA;
			}
			break;
		case ATTESA:
			if (this.evento.getClass() == Esito.class) {
				Esito e = (Esito) this.evento;
				ConfermaCessione c;
				c = new ConfermaCessione(this.autista, this.autista.richiedente, e.getEsito());
				Environment.aggiungiEvento(c);

				LinkAssegnato l = new LinkAssegnato(this.autista, this.autista.automobile);
				this.autista.eliminaAutomobile(l);

				log.info(this.autista.stato + " -> " + Autista.Stato.NORMALE);
				this.autista.stato = Autista.Stato.NORMALE;
			}
			break;
		}
	}
}
