package eventi;

import _gestioneeventi.*;

public class ConfermaCessione extends Evento {
	private boolean esito;

	public boolean getEsito() {
		return this.esito;
	}

	public ConfermaCessione(Listener a, Listener b, boolean esito) {
		super(a, b);
		this.esito = esito;
	}
}
