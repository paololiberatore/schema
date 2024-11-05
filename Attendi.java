import java.util.logging.Logger;

/**
 * classe fired per la ricezione di eventi
 */
class AttendiFired implements Task {
	private Logger log;
	private Attendi attendi;
	private Evento evento;

	public AttendiFired(Attendi attendi, Evento evento) {
		log = Log.creaLogger(AttendiFired.class.toString());
		this.attendi = attendi;
		this.evento = evento;
	}

	@Override
	public synchronized void esegui() {
		if (this.evento.getClass() == ConfermaCessione.class)
			attendi.attesa.interrupt();
	}
}

/**
 * classe attivita'
 * include ricezione eventi, che gira ad AttendiFired
 */
public class Attendi implements Task, Listener {
	private Logger log;
	public Thread attesa;
	private Autista autista;

	public Attendi(Autista autista) {
		log = Log.creaLogger(Attendi.class.toString());
		this.autista = autista;
	}

	/*
	 * task, attivita'
	 */
	@Override
	public synchronized void esegui() {
		// invio evento
		log.info("invio evento");
		Environment.aggiungiEvento(new CediMacchina(this, this.autista));

		// attesa risposta
		try {
			log.info("inizio attesa");
			this.wait();
		}
		catch (InterruptedException e) {
			log.info("attesa terminata");
		}
	}

	/* 
	 * listener, ricevitore eventi
	 */
	@Override
	public void fired(Evento evento) {
		this.attesa.interrupt();
	}
}

