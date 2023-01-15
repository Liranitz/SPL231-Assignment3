 #pragma once
 #include <string>
 #include <iostream>
 #include <map>
#include <vector>
//#include <Event.h>
#include "../include/GameEvents.h"




class EventController
{
private:
    
    //a hash-map from game name to the userNameToEventMap
    std::map<std::string,  std::map<std::string, GameEvents > > gameNameToUserEventMap;
    
   
public:
    EventController();
    void storeEvent(std::string message);
     std::vector<std::string> split(const std::string& str, char delimiter);
    std::map<std::string,  std::map<std::string, GameEvents>> getMap();
    
};