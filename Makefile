CLASS=Autista.class Automobile.class \
AutistaFired.class AutomobileFired.class \
LinkAssegnato.class ManagerAssegnato.class \
\
Assegna.class Libera.class Disponibile.class \
Cessione.class Verifica.class Fornisci.class \
\
CediMacchina.class Diagnostica.class Esito.class ConfermaCessione.class \
\
Client.class Server.class Main.class DB.class \
\
Task.class TaskExecutor.class Evento.class Listener.class \
Environment.class EsecuzioneEnvironment.class EsecuzioneListener.class \
Log.class \
\
classi.jpg stati.jpg attivita.jpg

all: ${CLASS}

%.class: %.java
	javac $<

%.jpg: %.fig
	fig2dev -L jpeg $< > $@

clean:
	rm -f ${CLASS} *.class *.bak

