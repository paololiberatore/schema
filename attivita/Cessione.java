/**
 * attivita' IO Cessione
 *
 * dice all'autista di lasciare la macchina
 * aspetta la risposta con l'esito
 *
 * non e' singleton, dato che durante la sua esecuzione deve essere possibile
 * eseguire i metodi fired() -> esegui(); e' synchronized solo per poter
 * chiamare wait()
 */

package attivita;

import java.util.logging.Logger;
import _gestioneeventi.*;
import _log.Log;
import dati.*;
import eventi.*;

public class Cessione implements Runnable, Listener {
	private Logger log;
	private Autista autista;
	private Automobile automobile;
	private boolean eseguita = false;
	private boolean esito;

	public Cessione(Autista autista, Automobile automobile) {
		log = Log.creaLogger(Cessione.class.toString());
		this.autista = autista;
		this.automobile = automobile;
	}

	/*
	 * runnable, attivita' IO
	 */
	@Override
	public synchronized void run() {
		log.info("inizio");

		// inserimento listener
		EsecuzioneEnvironment.addListener(this);

		// invio evento
		CediMacchina c = new CediMacchina(this, this.autista, this.automobile);
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
		this.eseguita = true;

		log.info("fine");
	}

	/* 
	 * listener, ricevitore eventi
	 */
	@Override
	public synchronized void fired(Evento evento) {
		log.info("inizio");
		if (evento.getClass() == ConfermaCessione.class) {
			ConfermaCessione c = (ConfermaCessione) evento;
			this.esito = c.getEsito();
			log.info("esito: " + this.esito);

			log.info("notify " + this + " " + Thread.currentThread());
			this.notify();
		}
		log.info("fine");
	}

	public boolean estEseguita() {
		return this.eseguita;
	}

	public boolean getEsito() {
		if (! this.eseguita)
			throw new RuntimeException("esito non disponibile");
		return this.esito;
	}
}

