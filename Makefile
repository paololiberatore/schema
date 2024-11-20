CLASS=\
dati/Autista.class dati/Automobile.class \
dati/AutistaFired.class dati/AutomobileFired.class \
dati/LinkAssegnato.class dati/ManagerAssegnato.class \
\
attivita/Assegna.class attivita/Libera.class attivita/Disponibile.class \
attivita/Cessione.class attivita/Verifica.class attivita/Fornisci.class \
\
eventi/CediMacchina.class eventi/Diagnostica.class \
eventi/Esito.class eventi/ConfermaCessione.class \
\
applicazione/Client.class applicazione/ClientListener.class \
applicazione/Server.class applicazione/Main.class applicazione/DB.class \
\
_framework/Task.class _framework/TaskExecutor.class \
_gestioneeventi/Evento.class _gestioneeventi/Listener.class \
_gestioneeventi/Environment.class _gestioneeventi/EsecuzioneEnvironment.class \
_gestioneeventi/EsecuzioneListener.class _gestioneeventi/Stop.class \
_log/Log.class \
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
	rm -f ${CLASS} *.class */*.class *.bak README.html link

