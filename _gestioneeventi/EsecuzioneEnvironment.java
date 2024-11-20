package _gestioneeventi;

import java.util.*;
import java.util.logging.Logger;
import _log.Log;

public final class EsecuzioneEnvironment { //NB con final non si possono definire sottoclassi
	static Logger log = Log.creaLogger(EsecuzioneEnvironment.class.toString());

	private EsecuzioneEnvironment() {
	}

	public static enum Stato {
		Attesa, Esecuzione
	};

	private static Stato statocorrente = Stato.Attesa;

	private static HashMap<Listener, Thread> listenerAttivi = null;

	public static synchronized void addListener(Listener lr) {
		log.info("inserimento listener: " + lr);
		if (statocorrente == Stato.Attesa) {
			Environment.addListener(lr, new EsecuzioneEnvironment());
		}
		else {
			Environment.addListener(lr, new EsecuzioneEnvironment());
			attivaSingolo(lr);
		}
	}

	public static void attivaSingolo(Listener listener) {
		Thread t;
		log.info("attivazione listener " + listener);
		t = new Thread(new EsecuzioneListener(listener));
		listenerAttivi.put(listener, t);
		listenerAttivi.get(listener).start();
		listenerAttivi.get(listener).setName("Thread di " + listener.toString());
		log.info(listener + ": " + t);
	}

	public static synchronized void attivaListener() {
		Thread t;
		if (statocorrente == Stato.Attesa) {
			statocorrente = Stato.Esecuzione;
			log.info("attivazione di tutti i listener");
			listenerAttivi = new HashMap<Listener, Thread>();
			Iterator<Listener> it = Environment.getInsiemeListener().iterator();
			while (it.hasNext()) {
				Listener listener = it.next();
				t = new Thread(new EsecuzioneListener(listener));
				listenerAttivi.put(listener, t);
				log.info(listener + ": " + t);
			}
			Iterator<Listener> i = listenerAttivi.keySet().iterator();
			while (i.hasNext()) {
				Listener l = i.next();
				listenerAttivi.get(l).start();
				listenerAttivi.get(l).setName("Thread di " + l.toString());
			}
			log.info("tutti i listener attivati");
		}
		else {
			log.info("listener gia' attivati");
		}
	}

	public static synchronized void disattivaListener() {
		if (statocorrente == Stato.Esecuzione) {
			statocorrente = Stato.Attesa;
			log.info("arresto di tutti i listener");
			Environment.aggiungiEvento(new Stop(null, null));
			Iterator<Listener> it = listenerAttivi.keySet().iterator();
			while (it.hasNext()) {
				Listener listener = it.next();
				try {
					Thread thread = listenerAttivi.get(listener);
					thread.join();
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			}
			Environment.resetListener(new EsecuzioneEnvironment());
		}
	}

	public static synchronized Stato getStato() {
		return statocorrente;
	}
}
