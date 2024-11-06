import java.util.LinkedList;
import java.util.logging.Logger;

public class Riassegna implements Runnable {
	static Logger log;
	private boolean eseguita = false;
	private TaskExecutor executor = TaskExecutor.getInstance();

	private LinkedList<Autista> autisti;
	private LinkedList<Automobile> automobili;
	private Autista ricevente;

	public Riassegna(LinkedList<Autista> autisti, LinkedList<Automobile> automobili, Autista ricevente) {
		this.autisti = autisti;
		this.automobili = automobili;
		this.ricevente = ricevente;
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

		m = new Multiplo(autisti);
		executor.perform(m);
		if (! m.esisteMultiplo()) {
			System.out.println("nessun autista ha piu' di una macchina assegnata");
			return;
		}
		multiplo = m.getMultiplo();
		log.info("autista con piu' automobili: " + multiplo);

		a = new Attendi(multiplo);
		EsecuzioneEnvironment.addListener(a);
		a.esegui();

		if (a.getEsito()) {
			Automobile libera;
			libera = multiplo.getAssegnato().get(0).getAutomobile();
			Fornisci f = new Fornisci(this.ricevente, libera);
			executor.perform(f);
		}

		log.info("ricevuta conferma");
	}
}

