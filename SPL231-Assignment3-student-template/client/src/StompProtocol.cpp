#include <StompProtocol.h>
#include <stdlib.h>
#include "ConnectionHandler.h"
#include "../include/KeyBoard_imp.h"
#include "../include/event.h"
#include <sstream>
#include "../include/EventController.h"
#include "../include/GameEvents.h"
#include <fstream>
#include <string>
#include <iostream>

using namespace boost;
using std::string;

#include <boost/algorithm/string.hpp>
#include <ClientReader.h>

void StompProtocol::send_messages_by_event(string path_message, ConnectionHandler &connectionHandler, EventController &eventController)
{
    names_and_events cur_events;
    cur_events = parseEventsFile(path_message);
    string direction = cur_events.team_a_name + "_" + cur_events.team_b_name;
    string cur_name = connectionHandler.cur_client_data().get_name();
    eventController.storeEvent(cur_events, cur_name, direction);
    for (Event e : cur_events.events)
    {
        std::ostringstream output_frame;
        output_frame << "SEND\n";
        output_frame << "destination:" << direction << "\n\n";
        output_frame << "user:" << cur_name << "\n";
        output_frame << "team a:" << cur_events.team_a_name << "\n";
        output_frame << "team b:" << cur_events.team_b_name << "\n";
        output_frame << "time:" << e.get_time() << "\n";
        output_frame << "team a updates:"
                     << "\n";
        for (auto const &cur : e.get_team_a_updates())
        {
            output_frame << "   " << cur.first << ":" << cur.second << "\n";
        }
        output_frame << "\n";
        output_frame << "team b updates:"
                     << "\n";
        for (auto const &cur : e.get_team_b_updates())
        {
            output_frame << "   " << cur.first << ":" << cur.second << "\n";
        }
        output_frame << "\n";
        output_frame << "description:"
                     << "\n"
                     << e.get_discription() << "\n";
                     
        string output = output_frame.str();
        connectionHandler.sendLine(output);
        // send to ...
    }
}

// gets an input of the client's data
std::string StompProtocol::parse_to_frame(string input_string, ConnectionHandler &connectionHandler, EventController &eventController)
{
    // ClientData cur_ch_client = connectionHandler.cur_client;
    //   lir = "Liran";
    //   map_of_subscribes.insert({lir , 5});
    std::string output_frame_str = "";
    std::ostringstream output_frame;
    std::string cur_input = input_string;
    std::vector<std::string> result;
    result = boost::split(result, cur_input, boost::is_any_of(" "));
    std::string typeMessage = result[0];
    if (typeMessage == "login")
    {
        // log in to the server
        // if(c_h.isConnect()) check if the res[1] (host) is already connected
        //  to another port.
        //  socket error, cannoot
        // try to connect the CH , res[1] ,' : ' res[1]
        // c_h.connect();
        string name;
        string passcode;
        try
        {
            name = result[2];
            passcode = result[3];
        }
        catch (...)
        {
            cout << "Please insert valid name and password";
        }
        output_frame << "CONNECT\n";
        output_frame << "accept-vesion:1.2\n";
        output_frame << "host:stomp.cs.bgu.ac.il\n";
        output_frame << "login:" << result[2] << "\n";
        output_frame << "passcode:" << result[3] << "\n\n\0";
        output_frame_str = output_frame.str();
        // ADD A CONNECT TO THE CH
        // Figure out - if connect a new one ? then what happens? has to be in another window?
        // vector<string> ret;
        // ret = wait_for_login();
        // vector<string> ports;
        // ports = boost::split(ports, ret[1], boost::is_any_of(":"));
        // std::string host = ports[0];
        // const char* charArray = ports[1].c_str();
        // short port = atoi(charArray);
        // string name;
        // name  =  ret[2];
        // string pass;
        // pass = ret[3];
        // string outer_frame;
        // ClientData cur_client = ClientData(result[2] ,result[3]);
        // ConnectionHandler connectionHandler(result[3],result[3], &cur_client); // figure out how
        // //KeyBoard_imp keybor(connectionHandler);
        // if (!connectionHandler.connect()) {
        //     std::cerr << "Cannot connect to " << host << ":" << port << std::endl;
        //     return 1;
        //     // figure out how to get back to the function
        // }
        // Not sure if needed
        // connectionHandler.cur_client_data().is_logged_in = true;
    }
    else if (typeMessage == "join")
    {
        int curSubIndex = connectionHandler.cur_client_data().subscribe_counter;
        int curRecIndex = connectionHandler.cur_client_data().receipts_counter;
        output_frame << "SUBSCRIBE\n";
        output_frame << "destination:" << result[1] << "\n";
        output_frame << "id:" << curSubIndex << "\n";
        output_frame << "receipt:" << curRecIndex << "\n\n\0";
        output_frame_str = output_frame.str();
        connectionHandler.cur_client_data().topic_to_id_map[result[1]] = curSubIndex;
        connectionHandler.cur_client_data().subscribe_counter = connectionHandler.cur_client_data().subscribe_counter + 1;
        connectionHandler.cur_client_data().receipts_counter = connectionHandler.cur_client_data().receipts_counter + 1;
    }
    else if (typeMessage == "exit")
    {
        int getSubIndexByTopic = connectionHandler.cur_client_data().topic_to_id_map[result[1]];
        int curRecIndex = connectionHandler.cur_client_data().receipts_counter;
        output_frame << "UNSUBSCRIBE\n";
        output_frame << "id:" << getSubIndexByTopic << "\n";
        output_frame << "receipt:" << curRecIndex << "\n\n\0";
        try
        {
            connectionHandler.cur_client_data().topic_to_id_map.erase(result[1]);
            output_frame_str = output_frame.str();
        }
        catch (...)
        {
            cout << "User is not subscribed to that topic";
            output_frame_str = "";
        }
        connectionHandler.cur_client_data().receipts_counter = connectionHandler.cur_client_data().receipts_counter + 1;
    }
    else if (typeMessage == "report" && connectionHandler.is_logged_in)
    {
        try
        {
            // int exist = connectionHandler.cur_client_data().topic_to_id_map[result[1]];
            // send to each topic event
            string path;
            path = result[1];
            send_messages_by_event(path, connectionHandler, eventController);
        }
        catch (...)
        {
            cout << "User is not subscribed to that topic";
        }
    }
    else if (typeMessage == "summary")
    {
        std::string game_name = result[1];
        std::string from_user_name = result[2];
        std::string file = result[3];
        std::ifstream infile(file.c_str());
        try{
        std::map<string,GameEvents> curMap(eventController.getMap()[game_name]);
        GameEvents curGames(curMap[from_user_name]);
        if (infile.good())
        {
            // File exists, open it and write to it
            std::ofstream outfile;
            outfile.open(file);
            outfile << curGames.toString();
            outfile.close();

            //outfile << eventController.getMap()[game_name][from_user_name].toString();

        }
        else
        {
            // File does not exist, create a new file and write to it
            std::ofstream outfile(file);
            outfile << curGames.toString();
            //outfile << eventController.getMap()[game_name][from_user_name].toString();
            outfile.close();
        }
        }
        catch(...){
            cout << "No events for the current game";
        }
    }
    else if (typeMessage == "logout")
    {
        // gets the receipt login id from server
        int curRecIndex = connectionHandler.cur_client_data().receipts_counter;
        output_frame << "DISCONNECT\n";
        output_frame << "receipt:" << curRecIndex << "\n\n\0";
        output_frame_str = output_frame.str();
        connectionHandler.is_logged_in = false;
        connectionHandler.cur_client_data().actions_by_receipt[curRecIndex] = "CLOSE";
    }

    if (output_frame_str != "") // or otherways
    {
        result.clear();
        return output_frame_str;
    }
    else
    {
        return output_frame_str;
    }
}

void creatSummary(ofstream file, EventController &eventController)
{
}
