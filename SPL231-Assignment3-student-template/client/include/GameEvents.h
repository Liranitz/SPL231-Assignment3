 #pragma once
#include <string>
#include <vector>
#include "../include/event.h"


class GameEvents
{
private:
    std::string teamAname;
    std::string teamBname;
    std::map< std::string,  std::string>  generalStats;
    std::map< std::string,  std::string > teamASats;
    std::map< std::string, std::string > teamBSats;
    std::vector<std::string> eventReport;   
public:
    GameEvents();
    GameEvents(const GameEvents &other);
    GameEvents(std::string _teamAname, std::string _teamBname);
    void addReport(std::string eventStr);
    std::vector<std::string> split(const std::string& str, char delimiter);
    std::string toString();
    std::string prinKeyBesideValue(std::map< std::string,  std::string > map);
    std::string printReports();
};