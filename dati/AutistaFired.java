/**
 * classe transizioni di Autista
 *
 * NORMALE
 *	CediMacchina(automobile) -> NORMALE / Diagnostica
 * ATTESA
 *	Esito(esito) -> NORMALE / ConfermaCessione(esito)
 */

package dati;

import java.util.logging.Logger;
import eventi.*;
import _framework.*;
import _gestioneeventi.*;
import _log.Log;

public class AutistaFired implements Task {
	private Logger log;
	private Autista autista;
	private Evento evento;
	private boolean eseguita = false;

	public AutistaFired(Autista autista, Evento evento) {
		log = Log.creaLogger(Autista.class.toString());
		this.autista = autista;
		this.evento = evento;
	}

	@Override
	public void esegui() {
		log.info("stato: " + this.autista.getStato());
		log.info("evento ricevuto: " + this.evento);

		if (eseguita)
			return;
		eseguita = true;
		if (this.evento.getDestinatario() != this.autista &&
		    this.evento.getDestinatario() != null)
		    	return;

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
				this.autista.eliminaAssegnato(l);

				log.info(this.autista.stato + " -> " + Autista.Stato.NORMALE);
				this.autista.stato = Autista.Stato.NORMALE;
			}
			break;
		}

		log.info("fine");
	}

	public boolean estEseguita() {
		return eseguita;
	}
}
