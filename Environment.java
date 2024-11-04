import java.util.*;
import java.util.concurrent.*;

public final class Environment {
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
		return codeEventiDeiListener.get(lr).take();
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
