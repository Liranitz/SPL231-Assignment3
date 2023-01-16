#include <iostream>
#include <fstream>
#include <string>
#include <map>
#include <vector>
#include <sstream>
#include "../include/EventController.h"

// login 127.0.0.1:7777 omer 1234
// login 127.0.0.1:7777 meital 1234
// login 127.0.0.1:7777 roy 1234
// login 127.0.0.1:7777 inbar 1234
// join Germany_Japan
// report /workspaces/SPL231-Assignment3-template/SPL231-Assignment3-student-template/client/data/events1.json
// summary Germany_Japan omer /workspaces/SPL231-Assignment3-template/SPL231-Assignment3-student-template/client/src/germant_japan

// login 127.0.0.1:7777 roy 1234
// join germany_japan
// report /workspaces/SPL231-Assignment3-template/SPL231-Assignment3-student-template/client/data/events1_partial.json
// summary germany_japan omer /workspaces/SPL231-Assignment3-template/SPL231-Assignment3-student-template/client/data/event.txt


EventController::EventController() : gameNameToUserEventMap(std::map<std::string, std::map<std::string, GameEvents> >())
{}

void EventController::storeEvent(std::string message)
{
    
std::vector<std::string> eventDetails = EventController::split(message, '\n');
    std::string userName = eventDetails[5];
    userName = EventController::split(userName, ':')[1];
    std::string topic = eventDetails[3];
    topic = EventController::split(topic, ':')[1];
    std::string teamAname = eventDetails[6];
    teamAname = EventController::split(teamAname, ':')[1];
    std::string teamBname = eventDetails[7];
    teamBname = EventController::split(teamBname, ':')[1];


    
    // Check if the topic is already in the gameNameToUserEventMap
    if (gameNameToUserEventMap.count(topic) == 0) // not have one, so create a new map with the userName as key and event as value
    {


        std::map<std::string, GameEvents> userEventMap;
        GameEvents gameEvents(teamAname, teamBname);
        gameEvents.addReport(message);
        userEventMap[userName] = gameEvents;
        gameNameToUserEventMap[topic] = userEventMap;
    }
    else
    { // already have one so just add it
        if (gameNameToUserEventMap[topic].count(userName) == 0)
        {
            GameEvents gameEvents(teamAname, teamBname);
            gameEvents.addReport(message);
            gameNameToUserEventMap[topic][userName] = gameEvents;
        }
        else
        {
            gameNameToUserEventMap[topic][userName].addReport(message);
        }
    }
}

std::vector<std::string> EventController::split(const std::string& str, char delimiter) {
    std::vector<std::string> result;
    std::stringstream ss(str);
    std::string item;
    while (getline(ss, item, delimiter)) {
        result.push_back(item);
    }
    return result;
}
std::map<std::string,  std::map<std::string, GameEvents>> EventController::getMap(){
    return this->gameNameToUserEventMap;
}
