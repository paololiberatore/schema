import java.util.HashSet;

public class Automobile implements Listener {
	private String modello;
	private String targa;
	private int anno;
	private HashSet<LinkAssegnato> autisti;

	public Automobile(String modello, String targa, int anno) {
		this.modello = modello;
		this.targa = targa;
		this.anno = anno;
		autisti = new HashSet<LinkAssegnato>();
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

	public HashSet<Autista> getAutisti() {
		return (HashSet<Autista>) this.autisti.clone();
	}

	public void inserisciAutista(LinkAssegnato link) {
		ManagerAssegnato.inserisciLink(link);
	}

	public void inserisciPerManagerAssegnato(ManagerAssegnato manager) {
		if (manager == null)
			return;
		this.autisti.add(manager.getLink());
	}

	public void eliminaAutista(LinkAssegnato link) {
		ManagerAssegnato.inserisciLink(link);
	}

	public void eliminaPerManagerAssegnato(ManagerAssegnato manager) {
		if (manager == null)
			return;
		this.autisti.remove(manager.getLink());
	}

	enum Stato { NORMALE };
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
