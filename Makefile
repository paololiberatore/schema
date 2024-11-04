CLASS=Autista.class Automobile.class \
LinkAssegnato.class ManagerAssegnato.class \
AutistaFired.class AutomobileFired.class \
Riassegna.class Multiplo.class \
Main.class \
Task.class TaskExecutor.class Evento.class Listener.class Environment.class \
Log.class

all: ${CLASS}

%.class: %.java
	javac $<

clean:
	rm -f ${CLASS} *.class

