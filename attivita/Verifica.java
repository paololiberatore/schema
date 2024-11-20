package attivita;
import dati.*;

import java.util.logging.Logger;

class Verifica implements Runnable {
	Logger log = Logger.getLogger(Verifica.class.toString());
	Autista autista;

	public Verifica(Autista autista) {
		this.autista = autista;
	}

	@Override
	public void run() {
		do {
			System.out.println("attesa cambiamento di stato");
			try {
				Thread.sleep(1000);
			}
			catch (InterruptedException e) {
			}
			System.out.println("stato: " + autista.getStato());
		}
		while (autista.getStato() == Autista.Stato.ATTESA);
		log.info("fine attesa");
	}
}
