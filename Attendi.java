/**
 * attivita' IO Attendi
 *
 * invia un evento ad Autista
 * aspetta un messaggio
 *
 * non e' singleton, dato che durante la sua esecuzione deve essere possibile
 * eseguire i metodi fired() -> esegui():
 * lanciare con run() e non con executor.perform(a)
 */

import java.util.logging.Logger;

public class Attendi implements Task, Listener {
	private Logger log;
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
		Automobile a;
		a = this.autista.getAssegnato().get(0).getAutomobile();
		CediMacchina c = new CediMacchina(this, this.autista, a);
		log.info("evento creato: " + c);
		Environment.aggiungiEvento(c);

		// attesa
		try {
			log.info("wait " + this + " " + Thread.currentThread());
			this.wait();
		}
		catch (InterruptedException e) {
			log.info("wait interrotta" + this + " " + Thread.currentThread());
		}
		log.info("attesa terminata " + this + " " + Thread.currentThread());
	}

	/* 
	 * listener, ricevitore eventi
	 */
	@Override
	public synchronized void fired(Evento evento) {
		log.info("notify " + this + " " + Thread.currentThread());
		this.notify();
	}
}

