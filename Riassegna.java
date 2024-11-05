import java.util.LinkedList;
import java.util.logging.Logger;

public class Riassegna implements Runnable {
	static Logger log;
	private boolean eseguita = false;
	private TaskExecutor executor = TaskExecutor.getInstance();

	public LinkedList<Autista> autisti;
	LinkedList<Automobile> automobili;

	public Riassegna(LinkedList<Autista> autisti, LinkedList<Automobile> automobili) {
		this.autisti = autisti;
		this.automobili = automobili;
		this.log = Log.creaLogger("Riassegna");
		log.info("creazione Riassegna");
	}

	@Override
	public void run() {
		Multiplo m;
		Attendi a;
		Autista multiplo;

		if (eseguita)
			return;
		eseguita = true;

		log.info("run Riassegna");

		for (Autista autista : autisti)
			log.info(autista.toString());


		m = new Multiplo(autisti);
		executor.perform(m);
		if (! m.esisteMultiplo()) {
			System.out.println("nessun autista ha piu' di una macchina assegnata");
			return;
		}
		multiplo = m.getMultiplo();
		log.info("autista con piu' automobili: " + multiplo);

		a = new Attendi(multiplo);
		executor.perform(a);
		log.info("ricevuta conferma");
	}
}
