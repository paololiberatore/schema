CLASS=Autista.class Automobile.class \
AutistaFired.class AutomobileFired.class \
LinkAssegnato.class ManagerAssegnato.class \
\
Assegna.class Libera.class Disponibile.class \
Cessione.class Verifica.class Fornisci.class \
\
CediMacchina.class Diagnostica.class Esito.class ConfermaCessione.class \
\
Client.class ClientListener.class Server.class Main.class DB.class \
\
Task.class TaskExecutor.class Evento.class Listener.class \
Environment.class EsecuzioneEnvironment.class EsecuzioneListener.class \
Log.class \
\
classi.jpg stati.jpg attivita.jpg

all: ${CLASS} README.html

%.class: %.java
	javac $<

%.jpg: %.fig
	fig2dev -L jpeg $< > $@

%.html: %.md
	md2html $< > $@

clean:
	rm -f ${CLASS} *.class *.bak README.html link

