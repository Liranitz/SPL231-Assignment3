#include <iostream>
#include <fstream>
#include <string>
#include <map>
#include <vector>
#include <sstream>
#include "../include/EventController.h"





EventController::EventController() : gameNameToUserEventMap(std::map<std::string, std::map<std::string, GameEvents> >())
{}

void EventController::storeEvent(names_and_events &eve, std::string userName, std::string topic)
{
    // Check if the topic is already in the gameNameToUserEventMap
    if (gameNameToUserEventMap.count(topic) == 0) // not have one, so create a new map with the userName as key and event as value
    {
        std::map<std::string, GameEvents> userEventMap;
        GameEvents gameEvents(eve.team_a_name, eve.team_b_name);
        gameEvents.addReport(eve);
        userEventMap[userName] = gameEvents;
        gameNameToUserEventMap[topic] = userEventMap;
    }
    else
    { // already have one so just add it
        if (gameNameToUserEventMap[topic].count(userName) == 0)
        {
            GameEvents gameEvents(eve.team_a_name, eve.team_b_name);
            gameEvents.addReport(eve);
            gameNameToUserEventMap[topic][userName] = gameEvents;
        }
        else
        {
            gameNameToUserEventMap[topic][userName].addReport(eve);
        }
    }
}

std::map<std::string,  std::map<std::string, GameEvents>> EventController::getMap(){
    return this->gameNameToUserEventMap;
}
