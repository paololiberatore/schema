/**
 * classe dati Autista
 *	- nome
 *	- lista di auto assegnate
 */

import java.util.HashSet;

public class Autista implements Listener {
	private String nome;
	private HashSet<LinkAssegnato> automobili;

	public Autista(String nome) {
		this.nome = nome;
		this.automobili = new HashSet<LinkAssegnato>();
	}

	public String getNome() {
		return this.nome;
	}

	public HashSet<LinkAssegnato> getAssegnato() {
		return (HashSet<LinkAssegnato>) this.automobili.clone();
	}

	public void inserisciAssegnato(LinkAssegnato link) {
		if (link != null && link.getAutista() == this)
			ManagerAssegnato.inserisciLink(link);
	}

	public void inserisciPerManagerAssegnato(ManagerAssegnato manager) {
		if (manager == null)
			return;
		this.automobili.add(manager.getLink());
	}

	public void eliminaAssegnato(LinkAssegnato link) {
		if (link != null && link.getAutista() == this)
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
	Automobile automobile;

	@Override
	public void fired(Evento evento) {
		TaskExecutor.getInstance().perform(new AutistaFired(this, evento));
	}

	@Override
	public String toString() {
		return "(Autista: " + this.nome + ")";
	}
}
