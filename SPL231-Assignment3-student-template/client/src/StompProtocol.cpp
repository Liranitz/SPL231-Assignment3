#include <StompProtocol.h>
#include <stdlib.h>
#include "ConnectionHandler.h"
#include "../include/KeyBoard_imp.h"
#include "../include/event.h"

using namespace boost;
using std::string;

#include <boost/algorithm/string.hpp>
#include <ClientReader.h>

void StompProtocol::send_messages_by_event(string path_message ,ConnectionHandler& connectionHandler){
    names_and_events cur_events;
    cur_events = parseEventsFile(path_message);
    string direction = cur_events.team_a_name + "_" + cur_events.team_b_name;
    for(Event e : cur_events.events){
        //
    }
}

// gets an input of the client's data
std::string StompProtocol::parse_to_frame(string input_string , ConnectionHandler& connectionHandler){
        //ClientData cur_ch_client = connectionHandler.cur_client;
        //  lir = "Liran";
        //  map_of_subscribes.insert({lir , 5});
        std::string output_frame = "";
        std::string cur_input = input_string;
        std::vector<std::string> result;
        result = boost::split(result, cur_input, boost::is_any_of(" "));
        std::string typeMessage = result[0];
        if (typeMessage == "login"){
            //log in to the server
            //if(c_h.isConnect()) check if the res[1] (host) is already connected  
            // to another port.
            // socket error, cannoot

            //try to connect the CH , res[1] ,' : ' res[1]
            //c_h.connect();
            output_frame = "CONNECT\n";
            output_frame += "accept-vesion:1.2";
            output_frame += "\n";
            output_frame += "host:stomp.cs.bgu.ac.il";
            output_frame += "\n";
            output_frame += "login:";
            output_frame += result[2] ;
            output_frame += "\n";
            output_frame += "passcode:";
            output_frame += result[3];
            output_frame += "\n\n\0";
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

            //Not sure if needed
            //connectionHandler.cur_client_data().is_logged_in = true;
        }
        else if(typeMessage == "join"){
            int curSubIndex = connectionHandler.cur_client_data().subscribe_counter;
            string curSubIndexStr = "";
            //curSubIndexStr = curSubIndexStr + std::to_string(curSubIndex);
            int curRecIndex = connectionHandler.cur_client_data().receipts_counter;
            //string curRecIndexStr = "" + curRecIndex;
            output_frame = "SUBSCRIBE\n"; 
            output_frame += "destination:";
            output_frame += result[1];    
            output_frame += '\n';
            output_frame += "id:";
            output_frame += std::to_string(curSubIndex);
            output_frame += "\n";
            output_frame += "receipt:";
            output_frame += std::to_string(curRecIndex);
            output_frame += "\n";
            output_frame += "\n";
            output_frame += "\0";
            string action;
            //action = "ADD " + result[1] + " " + std::to_string(curSubIndex);
            //connectionHandler.cur_client_data().actions_by_receipt[curRecIndex] = action;
            connectionHandler.cur_client_data().topic_to_id_map[result[1]] = curSubIndex;
            connectionHandler.cur_client_data().subscribe_counter = connectionHandler.cur_client_data().subscribe_counter + 1;
            connectionHandler.cur_client_data().receipts_counter = connectionHandler.cur_client_data().receipts_counter + 1;
            //cur_subscribe++;  
        }
        else if(typeMessage == "exit"){
            int getSubIndexByTopic = connectionHandler.cur_client_data().topic_to_id_map[result[1]];
            int curRecIndex = connectionHandler.cur_client_data().receipts_counter;
            output_frame = "UNSUBSCRIBE\n"; 
            output_frame += "id:";
            output_frame += std::to_string(getSubIndexByTopic);
            output_frame += "\n";
            output_frame += "receipt:";
            output_frame += std::to_string(curRecIndex);
            output_frame += "\n"; 
            output_frame += "\n\0";
            //string action;
            //action = "REMOVE " + result[1] + " " + std::to_string(getSubIndexByTopic);
            try{
            connectionHandler.cur_client_data().topic_to_id_map.erase(result[1]);
            }
            catch (...){
                cout << "User is not subscribed to that topic";
                output_frame = "";
            }

            //connectionHandler.cur_client_data().actions_by_receipt[curRecIndex] = action;
            connectionHandler.cur_client_data().receipts_counter = connectionHandler.cur_client_data().receipts_counter + 1;
        }
        else if(typeMessage == "report" && connectionHandler.is_logged_in){
            try{
                //int exist = connectionHandler.cur_client_data().topic_to_id_map[result[1]];
                //send to each topic event
                string path;
                path = result[1];
                send_messages_by_event(path , connectionHandler);
                // output_frame = "SEND\n"; 
                // output_frame += "destination:";
                // output_frame += result[1];
                // output_frame += "\n";
                // output_frame += result[2];
                // output_frame += "\n";
                // output_frame += "\n\0";
                }
            catch(...){
                cout << "User is not subscribed to that topic";
            }
        }
        else if(typeMessage == "summary"){
            string game_name = result[1];
            string from_user_name = result[2];
            string file_name = result[3];
            //parse to the file_name the events that happend.
        }
        else if(typeMessage == "logout"){
            //gets the receipt login id from server
            int curRecIndex = connectionHandler.cur_client_data().receipts_counter;
            output_frame = "DISCONNECT\n"; 
            output_frame += "receipt:";
            output_frame += std::to_string(curRecIndex);
            output_frame += "\n"; 
            output_frame += "\n\0";
            connectionHandler.is_logged_in = false;
            connectionHandler.cur_client_data().actions_by_receipt[curRecIndex] = "CLOSE";
        }
        
        if(output_frame != "") // or otherways
        {
            result.clear();
            return output_frame;
        }
        else{
            string ret_out;
            ret_out =  "\0";
            return ret_out;
        }
    }

