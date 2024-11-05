import java.util.logging.Logger;
import java.util.HashSet;

public class Autista implements Listener {
	private Logger log;
	private String nome;
	private HashSet<LinkAssegnato> automobili;

	public Autista(String nome) {
		log = Log.creaLogger(Autista.class.toString());
		this.nome = nome;
		this.automobili = new HashSet<LinkAssegnato>();
	}

	public String getNome() {
		return this.nome;
	}

	public HashSet<LinkAssegnato> getAssegnato() {
		return (HashSet<LinkAssegnato>) this.automobili.clone();
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

	@Override
	public void fired(Evento evento) {
		TaskExecutor.getInstance().perform(new AutistaFired(this, evento));
	}

	@Override
	public String toString() {
		return "(Autista: " + this.nome + ")";
	}
}
