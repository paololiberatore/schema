package dati;

public class ManagerAssegnato {
	private LinkAssegnato link;

	private ManagerAssegnato(LinkAssegnato link) {
		this.link = link;
	}

	public LinkAssegnato getLink() {
		return this.link;
	}

	public static void inserisciLink(LinkAssegnato link) {
		if (link == null)
			return;
		ManagerAssegnato manager;
		manager = new ManagerAssegnato(link);
		link.getAutista().inserisciPerManagerAssegnato(manager);
		link.getAutomobile().inserisciPerManagerAssegnato(manager);
	}

	public static void eliminaLink(LinkAssegnato link) {
		if (link == null)
			return;
		ManagerAssegnato manager;
		manager = new ManagerAssegnato(link);
		link.getAutista().eliminaPerManagerAssegnato(manager);
		link.getAutomobile().eliminaPerManagerAssegnato(manager);
	}
}
