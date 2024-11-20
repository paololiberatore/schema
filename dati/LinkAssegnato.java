package dati;

public class LinkAssegnato {
	private Autista autista;
	private Automobile automobile;

	public LinkAssegnato(Autista autista, Automobile automobile) {
		this.autista = autista;
		this.automobile = automobile;
	}

	public Autista getAutista() {
		return this.autista;
	}

	public Automobile getAutomobile() {
		return this.automobile;
	}

	@Override
	public String toString() {
		return "(" + this.autista + " " + this.automobile + ")";
	}

	@Override
	public boolean equals(Object o) {
		LinkAssegnato l;
		if (o == null)
			return false;
		if (this.getClass() != o.getClass())
			return false;
		l = (LinkAssegnato) o;
		if (this.autista != l.autista)
			return false;
		if (this.automobile != l.automobile)
			return false;
		return true;
	}
}

