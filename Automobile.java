/**
 * classe dati Automobile
 *	- modello
 *	- targa
 *	- anno
 *	- singolo autista assegnato
 */

import java.util.HashSet;

public class Automobile implements Listener {
	private String modello;
	private String targa;
	private int anno;
	private LinkAssegnato assegnato;

	public Automobile(String modello, String targa, int anno) {
		this.modello = modello;
		this.targa = targa;
		this.anno = anno;
	}

	public String getModello() {
		return this.modello;
	}

	public String getTarga() {
		return this.targa;
	}

	public int getAnno() {
		return this.anno;
	}

	public LinkAssegnato getAssegnato() {
		return this.assegnato;
	}

	public void inserisciAssegnato(LinkAssegnato link) {
		if (link != null && link.getAutomobile() == this)
			ManagerAssegnato.inserisciLink(link);
	}

	public void inserisciPerManagerAssegnato(ManagerAssegnato manager) {
		if (manager == null)
			return;
		this.assegnato = manager.getLink();
	}

	public void eliminaAssegnato(LinkAssegnato link) {
		if (link != null && link.getAutomobile() == this)
			ManagerAssegnato.inserisciLink(link);
	}

	public void eliminaPerManagerAssegnato(ManagerAssegnato manager) {
		if (manager == null)
			return;
		this.assegnato = null;
	}

	public static enum Stato { NORMALE };
	Stato stato = Stato.NORMALE;
	public Stato getStato() {
		return this.stato;
	}

	@Override
	public void fired(Evento evento) {
		TaskExecutor.getInstance().perform(new AutomobileFired(this, evento));
	}

	@Override
	public String toString() {
		return "(Automobile: " + this.modello + " " + this.targa + " " + this.anno + ")";
	}
}
