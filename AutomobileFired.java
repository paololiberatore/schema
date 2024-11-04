public class AutomobileFired implements Task {
	Automobile automobile;
	Evento evento;

	public AutomobileFired(Automobile automobile, Evento evento) {
		this.automobile = automobile;
		this.evento = evento;
	}

	@Override
	public void esegui() {
	}
}
