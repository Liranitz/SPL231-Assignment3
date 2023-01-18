
#include "../include/GameEvents.h"
#include <sstream>

// login 127.0.0.1:7777 omer 1234
// join Germany_Japan
// report /workspaces/SPL231-Assignment3-template/SPL231-Assignment3-student-template/client/data/events1.json
GameEvents::GameEvents()
    : teamAname(), teamBname(), generalStats(), teamASats(), teamBSats(), eventReport()
{
}

GameEvents::GameEvents(const GameEvents &other)
    : teamAname(other.teamAname), teamBname(other.teamBname), generalStats(other.generalStats), teamASats(other.teamASats), teamBSats(other.teamBSats), eventReport(other.eventReport) {}

GameEvents::GameEvents(std::string _teamAname, std::string _teamBname) : teamAname(_teamAname), teamBname(_teamBname),
                                                                         generalStats(std::map<std::string, std::string>()), teamASats(std::map<std::string, std::string>()), teamBSats(std::map<std::string, std::string>()),
                                                                         eventReport(std::vector<std::string>())
{
}

std::vector<std::string> GameEvents::split(const std::string &str, char delimiter)
{
    std::vector<std::string> result;
    std::stringstream ss(str);
    std::string item;
    while (getline(ss, item, delimiter))
    {
        result.push_back(item);
    }
    return result;
}

void GameEvents::addReport(std::string eventStr)
{
    //"MESSAGE\nsubscription:1\nmessage-id:0\ndestination:Germany_Japan\n\nuser:omer\nteam a:Germany\nteam b:Japan\ntime:0\nteam a updates:\n\nteam b updates:\n\ndescription:\nThe game has started! What an exciting evenin"...

    // Split the event string by '\n'
    std::vector<std::string> lines = GameEvents::split(eventStr, '\n');

    std::string user, teamA, teamB, eventName, time, description;

    bool isGeneralpdates = false;
    bool isTeamAUpdates = false;
    bool isTeamBUpdates = false;
    bool isDescription = false;

    for (auto const &line : lines)
    {
        
        if (line.find("event name:") != std::string::npos)
        {
            eventName = line.substr(line.find(":") + 1);
        }
        // Extract time
        else if (line.find("time:") != std::string::npos)
        {
            time = line.substr(line.find(":") + 1);
        }
        // Extract general game updates
        else if (line.find("general game updates:") != std::string::npos)
        {
            isGeneralpdates = true;
        }

        // Extract general game updates
        else if (line.find("team a updates:") != std::string::npos)
        {
            isGeneralpdates = false;
            isTeamAUpdates = true;
        }

        // Extract general game updates
        else if (line.find("team b updates:") != std::string::npos)
        {
            isTeamAUpdates = false;
            isTeamBUpdates = true;
        }

        // Extract description
        else if (line.find("description:") != std::string::npos)
        {
            isDescription = true;
            isTeamBUpdates = false;
        }

        else
        {
            if (isGeneralpdates && line.length() > 0)
            {
                // Extract the stat name and value
                std::vector<std::string> keyValue = GameEvents::split(line, ':');
                generalStats[keyValue[0]] = keyValue[1];
            }

            else if (isTeamAUpdates && line.length() > 0)
            {
                // Extract the stat name and value
                std::vector<std::string> keyValue = GameEvents::split(line, ':');
                teamASats[keyValue[0]] = keyValue[1];
            }

            else if (isTeamBUpdates && line.length() > 0)
            {
                // Extract the stat name and value
                std::vector<std::string> keyValue = GameEvents::split(line, ':');
                teamBSats[keyValue[0]] = keyValue[1];
            }

            else if (isDescription)
            {
                if (line == "\0")
                {
                    isDescription = false;
                    std::string report = time + " - " + eventName + ":" + "\n\n" + description + "\n\n";
                    eventReport.push_back(report);
                }
                else
                {
                    description += line;
                }
            }
        }
    }
    std::cout << toString() << std::endl;
}

//  for(Event e : eventsReport.events){
//     for (auto const& event : e.get_game_updates()) {
//         generalStats[event.first] = event.second;
//     }
//     for (auto const& event : e.get_team_a_updates()) {
//         teamASats[event.first] = event.second;
//     }

//     for (auto const& event : e.get_team_b_updates()) {
//         teamBSats[event.first] = event.second;
//     }

//     std::string report = std::to_string(e.get_time()) + " - " + e.get_name() +":"+ "\n\n" + e.get_discription() + "\n";
//     eventReport.push_back(report);
// }

std::string GameEvents::prinKeyBesideValue(std::map<std::string, std::string> map)
{
    std::string output;
    for (auto it = map.begin(); it != map.end(); ++it)
    {
        output += it->first + ": " + it->second + "\n";
    }
    return output;
}

std::string GameEvents::printReports()
{
    std::stringstream result;
    for (const auto &line : eventReport)
    {
        result << line << std::endl;
    }
    return result.str();
}

std::string GameEvents::toString()
{
    std::string output = teamAname + " VS " + teamBname + "\n" +
                         "Game stats: " + "\n" +
                         "General stats: " + "\n" +
                         prinKeyBesideValue(generalStats)  +
                         teamAname + " " + "stats:" + "\n" +
                         prinKeyBesideValue(teamASats) +
                         teamBname + " " + "stats :"  + "\n" + 
                         prinKeyBesideValue(teamBSats)  +
                         "Game event reports: " + "\n" +
                         printReports();
    return output;
}
