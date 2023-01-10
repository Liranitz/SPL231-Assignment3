#include <StompProtocol.h>
#include <stdlib.h>
#include "ConnectionHandler.h"
#include "../include/KeyBoard_imp.h"
using namespace boost;
using std::string;

#include <boost/algorithm/string.hpp>
#include <ClientReader.h>

std::string StompProtocol::parse_to_frame(string input_string){

    //while(1){ // figure out if need to be otherway
        std::string output_frame = "";
        std::string cur_input = input_string;
        //getline(cin , cur_input);
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
            output_frame ="CONNECT"+'\n';
            output_frame +="accept-version :1.2"+'\n'; // there is a " " between the 2?
            output_frame +="host:stomp.cs.bgu.ac.il"+'\n';
            output_frame +="login:"+result[2]+'\n';
            output_frame +="password:"+result[3]+'\n';
            output_frame +='\n';
            output_frame += '\0';
        }
        else if(typeMessage == "join"){
            ;
            output_frame = "SUBSCRIBE\n"; 
            output_frame += "destination:";
            output_frame += result[1];    
            output_frame += '\n';
            output_frame += "id:1\n";
            //output_frame += "client's id\n";
            output_frame += "receipt:1\n";
            //output_frame += "num of rec\n"; 
            output_frame += "\n\0";
        }
        else if(typeMessage == "exit"){
            output_frame = "UNSUBSCRIBE\n"; 
            output_frame += "id:1\n";
            //output_frame += "client's id\n";
            output_frame += "receipt:1\n";
            //output_frame += "num of rec\n"; 
            output_frame += "\n\0";
        }
        else if(typeMessage == "report"){
            //read to parser funciton

        }
        else if(typeMessage == "summary"){
            string game_name = result[1];
            string from_user_name = result[2];
            string file_name = result[3];
            //parse to the file_name the events that happend.
        }
        else if(typeMessage == "logout"){
            output_frame = "DISCONNECT\n"; 
            output_frame += "receipt:";
            output_frame += "num of rec\n"; 
            output_frame += "\n\0";
        }
        
        if(output_frame != "") // or otherways
        {
            result.clear();
            return output_frame;
        }
        else{
            string ret_out;
            ret_out =  "ERROR\ninvalid syntax";
            return ret_out;
        }
    //}
}
