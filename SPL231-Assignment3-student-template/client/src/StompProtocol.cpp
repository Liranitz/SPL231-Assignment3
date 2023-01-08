#include <StompProtocol.h>
#include <stdlib.h>
#include "ConnectionHandler.h"
#include "../include/KeyBoard_imp.h"
using namespace boost;
#include <boost/algorithm/string.hpp>;
#include <ClientReader.h>

string StompProtocol::parse_to_frame(string input_string){

    //while(1){ // figure out if need to be otherway
        string output_frame = "";
        string cur_input= "";
        getline(cin , cur_input);
        vector<string> result;
        result = boost::split(result, cur_input, boost::is_any_of(" "));
        string typeMessage = result[0];
        if (typeMessage == "login"){
            //log in to the server
            //if(c_h.isConnect()) check if the res[1] (host) is already connected  
            // to another port.
            // socket error, cannoot

            //try to connect the CH , res[1] ,' : ' res[1]
            //c_h.connect();
            output_frame =+"CONNECT"+'\n';
            output_frame =+"accept-version :1.2"+'\n'; // there is a " " between the 2?
            output_frame =+"host:stomp.cs.bgu.ac.il"+'\n';
            output_frame =+"login:"+result[2]+'\n';
            output_frame =+"password:"+result[3]+'\n';
            output_frame =+'\n';
            output_frame =+'^@';

        }
        else if(typeMessage == "join"){
            output_frame =+"SUBSCRIBE"+'\n'; // how to save or when i extract the receipt id?
            output_frame =+"destination"+result[1]+'\n';
            output_frame =+"id: client's id"+'\n'; // find the id
            output_frame =+"receipt: num of rec" +'\n'; // find the num rec
            output_frame =+'\n';
            output_frame =+'^@';
        }
        else if(typeMessage == "exit"){
            output_frame =+"UNSUBSCRIBE"+'\n';
            output_frame =+"id: client's id"+'\n'; // find the id
            output_frame =+"receipt: num of rec" +'\n'; // find the num rec
            output_frame =+'\n';
            output_frame =+'^@';    
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
            output_frame =+"DISCONNECT"+'\n';
            output_frame =+"receipt: num of rec" +'\n'; // find the num rec
            output_frame =+'\n';
            output_frame =+'^@';    
        }
        if(!output_frame._Equal("")) // or otherways
        {
            result.clear();
            return output_frame;
        }
    //}

}
