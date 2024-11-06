import java.util.logging.Logger;
import java.util.logging.Level;
import java.util.logging.SimpleFormatter;
import java.util.logging.Handler;

public class Log {
	// static String logFormat = "%1 %2 %3 %4 %5 %6 %7 %8 %9\n";
	// static String logFormat = "%3$-20.20s %4$-10.10s %2$-30.30s %5$s\n";
	static String logFormat = "%4$-8.8s %2$-34.34s %5$s\n";

	public static Logger creaLogger(String name, Level level) {
		Logger log = Logger.getLogger(Main.class.getName() + "." + name);
		System.setProperty("java.util.logging.SimpleFormatter.format", logFormat);
		SimpleFormatter f = new SimpleFormatter();
		Handler[] h = log.getParent().getHandlers();
		h[0].setFormatter(f);
		log.setLevel(level);
		return log;
	}

	public static Logger creaLogger(String name) {
		return creaLogger(name, Level.INFO);
	}
}
