#include <stdlib.h>
#include "ConnectionHandler.h"
#include "../include/KeyBoard_imp.h"

using namespace boost;
using namespace std;
//using namespace ClientData;


#include "boost/algorithm/string.hpp"
#include "ClientReader.h"
#include "StompProtocol.h"
#include <thread>

void parse_to_action(ConnectionHandler &connectionHandler , string message){
        std::string output_frame = "";
        std::string cur_input = message;
        std::vector<std::string> result_message;
        result_message = boost::split(result_message, cur_input, boost::is_any_of("\n"));
        std::string typeAct = result_message[1];
        //here there is a \n before the string
        if(typeAct == "RECIEPT"){
            std::vector<std::string> result_action;
            result_action = boost::split(result_action, result_message[2], boost::is_any_of(":"));
            string id_of_action = result_action[1];
            int num = stoi(id_of_action);
            string act;
            act = connectionHandler.cur_client_data().actions_by_receipt[num];
            std::vector<std::string> result_do;
            result_do = boost::split(result_do, act, boost::is_any_of(" "));
            std::string type_do = result_do[0];
            if(type_do == "ADD"){
                //params :
                // result_do[0] = add , result_do[1] = stringOfTopic , result_do[2] = ID_ofSub
                connectionHandler.cur_client_data().topic_to_id_map[result_do[1]] = stoi(result_do[2]);
            }
            else if (type_do == "REMOVE"){
                connectionHandler.cur_client_data().topic_to_id_map.erase(result_do[1]);
            }
            // else if(type_do == "CONNECT"){
            //     connectionHandler.is_logged_in = true;
            // }
            else if(type_do == "CLOSE"){
                //need to close only the socket?
                connectionHandler.close();
            }

        
            //find out which act it is and do it.
        }
        //attenation - here it is at the first place
        if (result_message[0] == "CONNECTED"){
            connectionHandler.is_logged_in = true;
        }
        if (result_message[0] == "MESSAGE"){
            connectionHandler.is_logged_in = true;
        }
    }

void input_from_keyboard(ConnectionHandler &connectionHandler){
    //ClientData cur_ch_client = connectionHandler.();
    //cout << cur_ch_client.get_name();
    //int numOfRec = 0;    
    while (1) { // need to be like that?

        const short bufsize = 1024;
        char buf[bufsize];
        std::cin.getline(buf, bufsize);
		std::string line(buf);
        string return_line = StompProtocol::parse_to_frame(line , connectionHandler );
        if (!connectionHandler.sendLine(return_line)) { //figure out if need to delete him
            std::cout << "Disconnected. Exiting...\n" << std::endl;
            break;
        }
    }
}

vector<string> wait_for_login(){
    while(1){ // figure out if need to be otherway
        // const short bufsize = 1024;
        // char buf[bufsize];
        // cin.getline(buf, bufsize); //
        // string line(buf);
        // vector<string> strs;
        // string output = "";
        // boost::split(strs, line, boost::is_any_of(" "));

        // string output_frame = "";
        // string cur_input= "";
        // getline(cin , cur_input);

        //mock of login
        string cur_input = "login 127.0.0.1:7777 lidan 123456";

        vector<string> result;
        result = boost::split(result, cur_input, boost::is_any_of(" "));
        string typeMessage = result[0];
        if (typeMessage == "login"){
            return result;
        }
        else{
            //print error message
            cout << "Please try again";
        }
    }
}


void read_from_socket(ConnectionHandler &connectionHandler){
    while(1){
    std::string answer;
    //int len;
        // Get back an answer: by using the expected number of bytes (len bytes + newline delimiter)
        // We could also use: connectionHandler.getline(answer) and then get the answer without the newline char at the end
        if(!connectionHandler.getLine(answer)) { //
            //std::cout << answer << std::endl;
            std::cout << "Disconnected. Exiting...\n" << std::endl;
            break; // wait for null charachter
        }
		//len = answer.length();
		//
        // A C string must end with a 0 char delimiter.  When we filled the answer buffer from the socket
		// we filled up to the \n char - we must make sure now that a 0 char is also present. So we truncate last character.
        //answer.resize(len-1);
        parse_to_action(connectionHandler , answer);
        std::cout << answer << std::endl;
        if (answer == "bye") {
            std::cout << "Exiting...\n" << std::endl;
            break;
        }
    }
}


/**
 * 
* This code assumes that the server replies the exact text the client sent it (as opposed to the practical session example)
*/
int main (int argc, char *argv[]) { // numb of parms, args[0] - name , 1 - ip , 2 - port
    //if (argc < 3) {
    //    std::cerr << "Usage: " << argv[0] << " host port" << std::endl << std::endl;
    //    return -1;
    //*
    vector<string> ret;
    ret = wait_for_login();
    vector<string> ports;
    ports = boost::split(ports, ret[1], boost::is_any_of(":"));
    std::string host = ports[0];
    const char* charArray = ports[1].c_str();
    short port = atoi(charArray);
    string name;
    name  =  ret[2];
    string pass;
    pass = ret[3]; 
    string outer_frame;
    //  join Germany_Japan
    //  login 127.0.0.1:7777 lidan 123456
    //  report /workspaces/SPL231-Assignment3-template/SPL231-Assignment3-student-template/client/data/events1.json
    ClientData cur_client = ClientData(name ,pass);
    ConnectionHandler connectionHandler(host,port, &cur_client); // figure out how
    //KeyBoard_imp keybor(connectionHandler);
    if (!connectionHandler.connect()) {
        std::cerr << "Cannot connect to " << host << ":" << port << std::endl;
        return 1;
        // figure out how to get back to the function
    }
    outer_frame = "CONNECT\n";
    outer_frame += "accept-vesion:1.2";
    outer_frame += "\n";
    outer_frame += "host:stomp.cs.bgu.ac.il";
    outer_frame += "\n";
    outer_frame += "login:";
    outer_frame += name ;
    outer_frame += "\n";
    outer_frame += "passcode:";
    outer_frame += pass;
    outer_frame += "\n\n\0";
    //outer_frame = StompProtocol::parse_to_frame()
    //cur_client.actions_by_receipt[cur_client.receipts_counter] = "CONNECT SERVER";
    cur_client.receipts_counter = cur_client.receipts_counter + 1;
    //outer_frame += "\n";

    //string send_line = '\0';

    connectionHandler.sendLine(outer_frame);

    std::thread read_input_thread(&input_from_keyboard ,  std::ref(connectionHandler));
    std::thread read_socket_thread(&read_from_socket , std::ref(connectionHandler));
    read_input_thread.join();
    read_socket_thread.join();
    return 0;
}

