#include <iostream>
#include <fstream>
#include <string>
#include <map>
#include <vector>
#include <sstream>
#include "../include/EventController.h"





EventController::EventController() : gameNameToUserEventMap(std::map<std::string, std::map<std::string, GameEvents> >())
{}

void EventController::storeEvent(std::string message)
{
    
std::vector<std::string> eventDetails = split(message, ':');
    std::string userName = eventDetails[0];
    std::string topic = eventDetails[1] + "_" +  eventDetails[2];


    
    // Check if the topic is already in the gameNameToUserEventMap
    if (gameNameToUserEventMap.count(topic) == 0) // not have one, so create a new map with the userName as key and event as value
    {

        std::map<std::string, GameEvents> userEventMap;
        GameEvents gameEvents(eventDetails[1], eventDetails[2]);
        gameEvents.addReport(message);
        userEventMap[userName] = gameEvents;
        gameNameToUserEventMap[topic] = userEventMap;
    }
    else
    { // already have one so just add it
        if (gameNameToUserEventMap[topic].count(userName) == 0)
        {
            GameEvents gameEvents(eventDetails[1], eventDetails[2]);
            gameEvents.addReport(message);
            gameNameToUserEventMap[topic][userName] = gameEvents;
        }
        else
        {
            gameNameToUserEventMap[topic][userName].addReport(message);
        }
    }
}

std::vector<std::string> split(const std::string& str, char delimiter) {
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
