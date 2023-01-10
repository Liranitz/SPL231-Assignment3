#include <stdlib.h>
#include "ConnectionHandler.h"
#include "../include/KeyBoard_imp.h"

using namespace boost;
using namespace std;

#include "boost/algorithm/string.hpp"
#include "ClientReader.h"
#include "StompProtocol.h"
#include <thread>
#include <boost/thread.hpp>



void input_from_keyboard(ConnectionHandler &connectionHandler){
    while (1) { // need to be like that?
        const short bufsize = 1024;
        char buf[bufsize];
        std::cin.getline(buf, bufsize);
		std::string line(buf);
		int len=line.length();
        string return_line = StompProtocol::parse_to_frame(line);
        if (!connectionHandler.sendLine(return_line)) { //figure out if need to delete him
            std::cout << "Disconnected. Exiting...\n" << std::endl;
            break;
        }
        //connectionHandler.sendBytes("\0" , 1);

        //std::cout << "Sent " << len+1 << " bytes to server" << std::endl; // need it?
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

        string output_frame = "";
        string cur_input= "";
        getline(cin , cur_input);
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
    int len;
        // Get back an answer: by using the expected number of bytes (len bytes + newline delimiter)
        // We could also use: connectionHandler.getline(answer) and then get the answer without the newline char at the end
        if(!connectionHandler.getLine(answer)) { //
            std::cout << "Disconnected. Exiting...\n" << std::endl;
            break; // wait for null charachter
        }
		len = answer.length();
		//
        
        // A C string must end with a 0 char delimiter.  When we filled the answer buffer from the socket
		// we filled up to the \n char - we must make sure now that a 0 char is also present. So we truncate last character.
        answer.resize(len-1);
        std::cout << "Reply: " << answer << " " << len << " bytes " << std::endl << std::endl;
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
    //  login 127.0.0.1:7777 liran 123456
    ConnectionHandler connectionHandler(host,port); // figure out how
    //KeyBoard_imp keybor(connectionHandler);
    if (!connectionHandler.connect()) {
        std::cerr << "Cannot connect to " << host << ":" << port << std::endl;
        return 1;
        // figure out how to get back to the function
    }
    string name;
    name  =  ret[2];
    string pass;
    pass = ret[3]; 
    string outer_frame;
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
    //outer_frame += "\n";

    //string send_line = '\0';

    connectionHandler.sendLine(outer_frame);

    //connectionHandler.sendBytes("\0" , 1);
    std::thread read_input_thread(&input_from_keyboard ,std::ref(connectionHandler));
    std::thread read_socket_thread(&read_from_socket ,std::ref(connectionHandler));
    read_input_thread.join();
    read_socket_thread.join();
    return 0;
}

