package eventi;

import _gestioneeventi.*;

public class Esito extends Evento {
	private boolean esito;

	public boolean getEsito() {
		return this.esito;
	}

	public Esito(Listener a, Listener b, boolean esito) {
		super(a, b);
		this.esito = esito;
	}
}
