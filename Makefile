CLASS=Autista.class Automobile.class \
LinkAssegnato.class ManagerAssegnato.class \
AutistaFired.class AutomobileFired.class \
Assegna.class Libera.class Riassegna.class Multiplo.class Attendi.class Fornisci.class \
CediMacchina.class Diagnostica.class Esito.class ConfermaCessione.class \
Main.class \
Task.class TaskExecutor.class Evento.class Listener.class \
Environment.class EsecuzioneEnvironment.class \
Log.class

all: ${CLASS}

%.class: %.java
	javac $<

clean:
	rm -f ${CLASS} *.class

