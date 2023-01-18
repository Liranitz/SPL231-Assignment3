
#include "../include/GameEvents.h"
#include <sstream>

GameEvents::GameEvents()
        : teamAname(), teamBname(), generalStats(), teamASats(), teamBSats(),eventReport()
{}


GameEvents::GameEvents(const GameEvents &other)
        : teamAname(other.teamAname), teamBname(other.teamBname), generalStats(other.generalStats), teamASats(other.teamASats), teamBSats(other.teamBSats),eventReport(other.eventReport) {}

GameEvents::GameEvents(std::string _teamAname, std::string _teamBname) : teamAname(_teamAname) , teamBname(_teamBname),
    generalStats(std::map< std::string,  std::string >()), teamASats(std::map< std::string, std::string >()),teamBSats(std::map< std::string,  std::string >()),
    eventReport(std::vector<std::string> ())
{}

 void GameEvents::addReport(std::string  event){

     for(Event e : eventsReport.events){
        for (auto const& event : e.get_game_updates()) {
            generalStats[event.first] = event.second;
        }
        for (auto const& event : e.get_team_a_updates()) {
            teamASats[event.first] = event.second;
        }

        for (auto const& event : e.get_team_b_updates()) {
            teamBSats[event.first] = event.second;
        }


std::string name = event["event name"];
        int time = event["time"];



        std::string report = std::to_string(e.get_time()) + " - " + e.get_name() +":"+ "\n\n" + e.get_discription() + "\n";
        eventReport.push_back(report);
    }
 }

 std::string GameEvents::prinKeyBesideValue(std::map< std::string, std::string > map){
     std::string  output;
    for (auto it = map.begin(); it != map.end(); ++it) {
        output+= it->first + ": "+ it->second + "\n";
        }
        return output;
 }

std::string GameEvents::printReports(){
    std::stringstream result;
    for (const auto &line : eventReport) {
        result << line << std::endl;
        }
     return result.str();   
}


std::string GameEvents::toString(){
    std::string output = teamAname + " VS " + teamBname + "\n" +
                        "Game stats: " + "\n" + 
                        "General stats: " + "\n" + 
                         prinKeyBesideValue(generalStats) + "\n" + 
                         teamAname + " " + "stats:" + "\n" + 
                         prinKeyBesideValue(teamASats) + "\n" + 
                         teamBname + " " +"stats :" + "\n" + 
                         prinKeyBesideValue(teamBSats) + "\n" + 
                         "Game event reports: " + "\n" + 
                         printReports();  
    return output;
   
}


