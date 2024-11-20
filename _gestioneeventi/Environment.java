package _gestioneeventi;

import java.util.*;
import java.util.concurrent.*;
import java.util.logging.Logger;
import _log.*;

public final class Environment {
	static Logger log = Log.creaLogger(Environment.class.toString());

	private Environment() {
	}

	private static ConcurrentHashMap<Listener, LinkedBlockingQueue<Evento>> codeEventiDeiListener = 
		new ConcurrentHashMap<Listener, LinkedBlockingQueue<Evento>>();

	public static void addListener(Listener lr, EsecuzioneEnvironment e) {
		if (e == null)
			return;
		codeEventiDeiListener.put(lr, new LinkedBlockingQueue<Evento>());
	}
	
	public static Set<Listener> getInsiemeListener() {
		return codeEventiDeiListener.keySet();
	}

	public static void aggiungiEvento(Evento e) {
		log.info("inserimento evento: " + e);
		if (e == null)
			return;

		Listener destinatario = e.getDestinatario();
		if (destinatario != null
				&& codeEventiDeiListener.containsKey(destinatario)) {
			try {
				codeEventiDeiListener.get(destinatario).put(e);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		} else if (destinatario == null) {
			Iterator<Listener> itn = codeEventiDeiListener.keySet().iterator();
			while (itn.hasNext()) {
				Listener lr = itn.next();
				try {
					codeEventiDeiListener.get(lr).put(e);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			}
		}
	}

	public static Evento prossimoEvento(Listener lr)
			throws InterruptedException {
		Evento e = codeEventiDeiListener.get(lr).take();
		if (e.getClass() != Stop.class)
			log.info("prossimo evento: " + e);
		else
			log.info("prossimo evento: Stop -> " + lr);
		return e;
	}

	public static void resetListener(EsecuzioneEnvironment e) {
		if (e == null)
			return;
		Iterator<Listener> itn = codeEventiDeiListener.keySet().iterator();
		while (itn.hasNext()) {
			Listener lr = itn.next();
			codeEventiDeiListener.put(lr, new LinkedBlockingQueue<Evento>());
		}
	}
		
}
