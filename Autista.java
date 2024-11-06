import java.util.logging.Logger;
import java.util.LinkedList;

public class Autista implements Listener {
	private Logger log;
	private String nome;
	private LinkedList<LinkAssegnato> automobili;

	public Autista(String nome) {
		log = Log.creaLogger(Autista.class.toString());
		this.nome = nome;
		this.automobili = new LinkedList<LinkAssegnato>();
	}

	public String getNome() {
		return this.nome;
	}

	public LinkedList<LinkAssegnato> getAssegnato() {
		return (LinkedList<LinkAssegnato>) this.automobili.clone();
	}

	public void inserisciAutomobile(LinkAssegnato link) {
		ManagerAssegnato.inserisciLink(link);
	}

	public void inserisciPerManagerAssegnato(ManagerAssegnato manager) {
		if (manager == null)
			return;
		this.automobili.add(manager.getLink());
	}

	public void eliminaAutomobile(LinkAssegnato link) {
		ManagerAssegnato.eliminaLink(link);
	}

	public void eliminaPerManagerAssegnato(ManagerAssegnato manager) {
		if (manager == null)
			return;
		this.automobili.remove(manager.getLink());
	}

	public enum Stato { NORMALE, ATTESA };
	Stato stato = Stato.NORMALE;
	public Stato getStato() {
		return this.stato;
	}

	Listener richiedente;

	@Override
	public void fired(Evento evento) {
		TaskExecutor.getInstance().perform(new AutistaFired(this, evento));
	}

	@Override
	public String toString() {
		return "(Autista: " + this.nome + ")";
	}
}
