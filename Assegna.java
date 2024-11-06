import java.util.LinkedList;
import java.util.logging.Logger;

public class Assegna implements Runnable {
	static Logger log;
	private boolean eseguita = false;
	private TaskExecutor executor = TaskExecutor.getInstance();

	private LinkedList<Autista> autisti;
	private LinkedList<Automobile> automobili;
	private Autista ricevente;

	public Assegna(LinkedList<Autista> autisti, LinkedList<Automobile> automobili, Autista ricevente) {
		this.autisti = autisti;
		this.automobili = automobili;
		this.ricevente = ricevente;
		this.log = Log.creaLogger("Assegna");
		log.info("creazione Assegna");
	}

	@Override
	public void run() {
		if (eseguita)
			return;
		eseguita = true;

		log.info("run Assegna");

		Libera l = new Libera(automobili, ricevente);
		executor.perform(l);
		if (l.getEsito())
			return;

		Riassegna r = new Riassegna(autisti, automobili, ricevente);
		r.run();
	}
}

