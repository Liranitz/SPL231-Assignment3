CFLAGS:=-c -Wall -Weffc++ -g -std=c++11 -Iinclude
LDFLAGS:=-lboost_system -lpthread

all: StompWCIClient

EchoClient: bin/ConnectionHandler.o bin/echoClient.o
	g++ -o bin/EchoClient bin/ConnectionHandler.o bin/echoClient.o $(LDFLAGS)

StompWCIClient: bin/ConnectionHandler.o bin/StompClient.o bin/event.o bin/StompProtocol.o bin/KeyBoard_imp.o bin/ClientReader.o bin/ClientData.o bin/EventController.o bin/GameEvents.o 
	g++ -o bin/StompWCIClient bin/ConnectionHandler.o bin/StompClient.o bin/event.o bin/StompProtocol.o bin/KeyBoard_imp.o bin/ClientReader.o  bin/ClientData.o bin/EventController.o bin/GameEvents.o $(LDFLAGS)

bin/ConnectionHandler.o: src/ConnectionHandler.cpp
	g++ $(CFLAGS) -o bin/ConnectionHandler.o src/ConnectionHandler.cpp

bin/echoClient.o: src/echoClient.cpp
	g++ $(CFLAGS) -o bin/echoClient.o src/echoClient.cpp

bin/event.o: src/event.cpp
	g++ $(CFLAGS) -o bin/event.o src/event.cpp

bin/StompClient.o: src/StompClient.cpp
	g++ $(CFLAGS) -o bin/StompClient.o src/StompClient.cpp

bin/StompProtocol.o: src/StompProtocol.cpp
	g++ $(CFLAGS) -o bin/StompProtocol.o src/StompProtocol.cpp

bin/KeyBoard_imp.o: src/KeyBoard_imp.cpp
	g++ $(CFLAGS) -o bin/KeyBoard_imp.o src/KeyBoard_imp.cpp

bin/ClientReader.o: src/ClientReader.cpp
	g++ $(CFLAGS) -o bin/ClientReader.o src/ClientReader.cpp

bin/ClientData.o: src/ClientData.cpp
	g++ $(CFLAGS) -o bin/ClientData.o src/ClientData.cpp

bin/GameEvents.o: src/GameEvents.cpp
	g++ $(CFLAGS) -o bin/GameEvents.o src/GameEvents.cpp

bin/EventController.o: src/EventController.cpp
	g++ $(CFLAGS) -o bin/EventController.o src/EventController.cpp

.PHONY: clean
clean:
	rm -f bin/*
	