#include <StompProtocol.h>
#include <stdlib.h>
#include "ConnectionHandler.h"
#include "../include/KeyBoard_imp.h"

using namespace boost;
using std::string;

#include <boost/algorithm/string.hpp>
#include <ClientReader.h>
<<<<<<< HEAD



// gets an input of the client's data
std::string StompProtocol::parse_to_frame(string input_string , ConnectionHandler& connectionHandler){
        //ClientData cur_ch_client = connectionHandler.cur_client;
        //  lir = "Liran";
        //  map_of_subscribes.insert({lir , 5});
=======
StompProtocol::StompProtocol(){
    cur_subscribe = 0;
}

std::string StompProtocol::parse_to_frame(string input_string){
        //map_of_subscribes["LIran"] = 5;
        
>>>>>>> 4c7755b723133ac4dc8e88437465dc635815b3ea
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
        }
        else if(typeMessage == "join"){
<<<<<<< HEAD
            int curSubIndex = connectionHandler.cur_client_data().subscribe_counter;
            string curSubIndexStr = "" + curSubIndex;
            int curRecIndex = connectionHandler.cur_client_data().receipts_counter;
            string curRecIndexStr = "" + curRecIndex;
=======
            
>>>>>>> 4c7755b723133ac4dc8e88437465dc635815b3ea
            output_frame = "SUBSCRIBE\n"; 
            output_frame += "destination:";
            output_frame += result[1];    
            output_frame += '\n';
            output_frame += "id:";
<<<<<<< HEAD
            output_frame += curSubIndexStr;
            output_frame += "\n";
            output_frame += "receipt:";
            output_frame += curRecIndexStr;
            output_frame += "\n";
            output_frame += "\n";
            output_frame += "\0";

            connectionHandler.cur_client_data().subscribe_counter = connectionHandler.cur_client_data().subscribe_counter + 1;
            connectionHandler.cur_client_data().receipts_counter = connectionHandler.cur_client_data().receipts_counter + 1;
            //cur_subscribe++;  
=======
            output_frame += "0";
            output_frame += "\n";
            output_frame += "receipt:1\n";
            output_frame += "num of rec\n"; 
            output_frame += "\n\n\0";
            cur_subscribe++;
>>>>>>> 4c7755b723133ac4dc8e88437465dc635815b3ea
        }
        else if(typeMessage == "exit"){
            int getSubIndexByTopic = connectionHandler.cur_client_data().topic_to_id_map[result[1]];
            int curRecIndex = connectionHandler.cur_client_data().receipts_counter;
            output_frame = "UNSUBSCRIBE\n"; 
<<<<<<< HEAD
            output_frame += "id:";
            output_frame += getSubIndexByTopic;
            output_frame += "\n";
            output_frame += "receipt:";
            output_frame += curRecIndex;
            output_frame += "\n"; 
=======
            output_frame += "id:0\n";
            //output_frame += "client's id\n";
            output_frame += "receipt:1\n";
            //output_frame += "num of rec\n"; 
>>>>>>> 4c7755b723133ac4dc8e88437465dc635815b3ea
            output_frame += "\n\0";
            connectionHandler.cur_client_data().receipts_counter = connectionHandler.cur_client_data().receipts_counter + 1;
        }
        else if(typeMessage == "report"){
            output_frame = "SEND\n"; 
            output_frame += "destination:";
            output_frame += result[1];
            output_frame += "\n";
             output_frame += result[2];
            output_frame += "\n";
            output_frame += "\n\0";
        }
        else if(typeMessage == "summary"){
            string game_name = result[1];
            string from_user_name = result[2];
            string file_name = result[3];
            //parse to the file_name the events that happend.
        }
        else if(typeMessage == "logout"){
            output_frame = "DISCONNECT\n"; 
<<<<<<< HEAD
            output_frame += "receipt:";
            output_frame += "1\n"; 
=======
            output_frame += "receipt:0\n";
            output_frame += "num of rec\n"; 
>>>>>>> 4c7755b723133ac4dc8e88437465dc635815b3ea
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
<<<<<<< HEAD
    }
=======
    //}
}
>>>>>>> 4c7755b723133ac4dc8e88437465dc635815b3ea

