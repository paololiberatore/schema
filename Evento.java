public class Evento {
	private Listener mittente;
	private Listener destinatario;

	public Evento(Listener m, Listener d) {
	mittente = m;
	destinatario = d;
	}

	public Listener getMittente() { return mittente; }
	public Listener getDestinatario() { return destinatario; }

	public boolean equals(Object o) {
		if (o == null)
			return false;
		if (! getClass().equals(o.getClass()))
			return false;
		Evento e = (Evento) o;
		return mittente == e.mittente && destinatario == e.destinatario;
	}
 
	public int hashCode() {
		return mittente.hashCode() + destinatario.hashCode();  
	}
}
