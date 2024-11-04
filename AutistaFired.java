public class AutistaFired implements Task {
	Autista autista;
	Evento evento;

	public AutistaFired(Autista autista, Evento evento) {
		this.autista = autista;
		this.evento = evento;
	}

	@Override
	public void esegui() {
	}
}
