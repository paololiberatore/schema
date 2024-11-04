import java.util.logging.Logger;
import java.util.logging.Level;
import java.util.logging.SimpleFormatter;
import java.util.logging.Handler;

public class Log {
	static String logFormat = "%3$-10.10s %4$-10.10s %2$-30.30s %5$s\n";

	public static Logger creaLogger(String name) {
		Logger log = Logger.getLogger(Main.class.getName() + "." + name);
		System.setProperty("java.util.logging.SimpleFormatter.format", logFormat);
		SimpleFormatter f = new SimpleFormatter();
		Handler[] h = log.getParent().getHandlers();
		h[0].setFormatter(f);
		log.setLevel(Level.INFO);
		return log;
	}
}
