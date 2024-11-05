import java.util.logging.Logger;

public class AutistaFired implements Task {
	private Logger log;
	Autista autista;
	Evento evento;

	public AutistaFired(Autista autista, Evento evento) {
		log = Log.creaLogger(Autista.class.toString());
		this.autista = autista;
		this.evento = evento;
	}

	@Override
	public void esegui() {
		log.info("evento ricevuto: " + this.evento);
		if (evento.getClass() == CediMacchina.class) {
			Automobile a = (Automobile) this.evento.getMittente();
			Environment.aggiungiEvento(new CediMacchina(this.autista, a));
		}
	}
}
