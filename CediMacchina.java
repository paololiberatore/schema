public class CediMacchina extends Evento {
	private Automobile automobile;

	public CediMacchina(Listener a, Listener b, Automobile automobile) {
		super(a, b);
		this.automobile = automobile;
	}

	public Automobile getAutomobile() {
		return this.automobile;
	}
}
